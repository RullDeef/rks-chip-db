package ru.spacecorp.mobdekbkp.web.bpm.screens

import com.haulmont.bpm.entity.ProcActor
import com.haulmont.bpm.entity.ProcRole
import com.haulmont.bpm.exception.BpmException
import com.haulmont.bpm.gui.form.standard.StandardProcForm
import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.LoadContext
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.gui.components.TextArea
import com.haulmont.cuba.gui.data.CollectionDatasource
import com.haulmont.cuba.security.entity.User
import com.haulmont.cuba.security.global.UserSession
import ru.spacecorp.mobdekbkp.web.PublicConstants

import javax.annotation.Nullable
import javax.inject.Inject
import javax.inject.Named

class GnioAddOthersProcForm extends StandardProcForm {

    @Named("procActorsFrame.procActorsDs")
    private CollectionDatasource<ProcActor, UUID> procActorsDs

    @Inject
    private DataManager dataManager

    @Inject
    private Metadata metadata

    @Inject
    private UserSession userSession

    @Inject
    protected TextArea comment

    void onBtnAddMniiripClick() {
        setActors("others", PublicConstants.SYS_ROLE_MNIIRIP)
    }

    void onBtnAddNiikpClick() {
        setActors("others", PublicConstants.SYS_ROLE_NIIKP)
    }

    void setActors(String bpmRoleName, String sysRole) {
        ProcRole procRole = findProcRole(bpmRoleName)
        if (procRole == null) {
            throw new BpmException("procRole not found, check outcome form parameters: " + bpmRoleName)
        }

        LoadContext<User> ctx = new LoadContext<>(User.class)
        ctx.setQueryString('SELECT u FROM sec$User u ' +
                            'JOIN u.userRoles ur JOIN u.userRoles r ' +
                            'WHERE ur.role.name = :roleName')
                .setParameter("roleName", sysRole)
        List<User> usersWithRole = dataManager.loadList(ctx)
        if (usersWithRole.size() == 0) {
            showNotification(String.format(getMessage("userAddFail"), sysRole))
            return
        }
        for (User user : usersWithRole) {
            ProcActor procActor = metadata.create(ProcActor.class)
            procActor.setProcInstance(procInstance)
            procActor.setUser(user)
            procActor.setProcRole(procRole)
            procActorsDs.addItem(procActor)
        }
        procActorsDs.commit()
        showNotification(String.format(getMessage("usersAdded"), sysRole))
    }

    @Nullable
    private ProcRole findProcRole(String procRoleCode) {
        LoadContext ctx = LoadContext.create(ProcRole.class)
                .setQuery(LoadContext.createQuery('select pr from bpm$ProcRole pr where pr.code = :roleCode')
                .setParameter("roleCode", procRoleCode))
        ProcRole procRole = dataManager.load(ctx)
        return procRole
    }


}