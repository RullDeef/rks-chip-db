package ru.spacecorp.mobdekbkp.web.bpm.screens

import com.google.common.base.Strings
import com.haulmont.bpm.entity.ProcActor
import com.haulmont.bpm.entity.ProcRole
import com.haulmont.bpm.exception.BpmException
import com.haulmont.bpm.form.ProcFormParam
import com.haulmont.bpm.gui.form.standard.StandardProcForm
import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.LoadContext
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.gui.components.TextArea
import com.haulmont.cuba.gui.components.TextField
import com.haulmont.cuba.gui.data.CollectionDatasource
import com.haulmont.cuba.security.entity.User
import com.haulmont.cuba.security.global.UserSession
import ru.spacecorp.mobdekbkp.web.PublicConstants

import javax.annotation.Nullable
import javax.inject.Inject
import javax.inject.Named

/**
 * Process start form controller
 *
 * Sets current user as a process actor 'head'
 * process actor name is taken from 'bpmRoleName' defined as a form parameter
 * form parameters are defined in the app-bpm-forms.xml file
 * */
class ProcFormStart extends StandardProcForm {

    @Named("procActorsFrame.procActorsDs") private CollectionDatasource<ProcActor, UUID> procActorsDs
    @Inject private TextField tfBpmExec
    @Inject private DataManager dataManager
    @Inject private Metadata metadata
    @Inject private UserSession userSession
    @Inject protected TextArea comment

    private String getRoleCode(String parameter) {
        ProcFormParam roleCodeParameter = formDefinition.getParam(parameter)
        if (roleCodeParameter == null || Strings.isNullOrEmpty(roleCodeParameter.getValue())) {
            throw new BpmException("roleCodeParameter is not defined")
        }
        return roleCodeParameter.getValue()
    }

    private void addNewProcActorToDs(User user, ProcRole procRole) {
        ProcActor procActor = metadata.create(ProcActor.class)
        procActor.setProcInstance(procInstance)
        procActor.setUser(user)
        procActor.setProcRole(procRole)
        procActorsDs.addItem(procActor)
    }

    private void setMultipleRolesBySysRole(String roleCode) {
        //bpmExecutor default form param is defined in the app-bpm-forms.xml file
        String procRoleCode = getRoleCode(roleCode)
        String currentProcess = procInstance.getProcDefinition().getName()
        ProcRole procRole = findProcRole(procRoleCode, currentProcess)

        String secRole = (tfBpmExec.getValue() != null) ? tfBpmExec.getValue() :
                                (tfBpmExec.getInputPrompt() != null) ? tfBpmExec.getInputPrompt() :
                                        PublicConstants.SYS_ROLE_GNIO

        LoadContext<User> ctx = new LoadContext<>(User.class)
        ctx.setQueryString('select u from sec$User u join u.userRoles ur where ur.role.name = :roleName')
                .setParameter("roleName", secRole)
        List<User> usersWithRole = dataManager.loadList(ctx)

        if (usersWithRole.size() == 0)
            throw new RuntimeException(String.format(getMessage("excNoUserWithRole"), secRole, roleCode))

        for (User user : usersWithRole) {
            addNewProcActorToDs(user, procRole)
        }
        procActorsDs.commit()
    }

    private void setSingleRoleByLogin(String roleCode) {
        String procRoleCode = roleCode
        String procName = this.procInstance.getProcDefinition().getName()
        ProcRole procRole = findProcRole(procRoleCode, procName)

        String currentLogin = userSession.getUser().getLogin()
        LoadContext ctx = LoadContext.create(User.class)
                .setQuery(LoadContext.createQuery('select u from sec$User u where u.login = :userLogin')
                    .setParameter("userLogin", currentLogin))
        User user = dataManager.load(ctx)

        addNewProcActorToDs(user, procRole)
        procActorsDs.commit()
    }

    @Nullable
    private ProcRole findProcRole(String procRoleCode, String currProc) {
        LoadContext ctx = LoadContext.create(ProcRole.class)
                .setQuery(LoadContext.createQuery('SELECT pr ' +
                                                    'FROM bpm$ProcRole pr ' +
                                                    'WHERE pr.code = :roleCode ' +
                                                    'AND pr.procDefinition.name = :procDef')
                .setParameter("roleCode", procRoleCode)
                .setParameter("procDef", currProc))
        ProcRole procRole = dataManager.load(ctx)

        if (procRole == null)
            throw new BpmException("Process role " + procRoleCode  + "not found, check Start Form parameters")

        return procRole
    }

    @Override
    void init(Map<String, Object> params) {
        super.init(params)

        ProcFormParam paramSetExec = formDefinition.getParam("setHead")
        if (paramSetExec == null || Strings.isNullOrEmpty(paramSetExec.getValue())) {
            //throw new BpmException("Parameter 'Set Head' is not defined")
            println 'Startform where parameter \'Set Head\' is not defined opened'
        } else if ("true" == paramSetExec.getValue()) {
            setSingleRoleByLogin('head')
        }


        paramSetExec = formDefinition.getParam("setExec")
        if (paramSetExec == null || Strings.isNullOrEmpty(paramSetExec.getValue())) {
            //throw new BpmException("Parameter 'Set Executor' is not defined")
            println 'Startform where parameter \'Set Executor\' is not defined opened'
        } else if ("true" == paramSetExec.getValue()) {
            setMultipleRolesBySysRole('bpmExecutor')
        }
    }


}