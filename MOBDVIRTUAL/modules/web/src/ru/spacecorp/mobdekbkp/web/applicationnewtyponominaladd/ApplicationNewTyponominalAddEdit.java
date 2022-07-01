package ru.spacecorp.mobdekbkp.web.applicationnewtyponominaladd;

import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcRole;
import com.haulmont.bpm.gui.procactions.ProcActionsFrame;
import com.haulmont.bpm.service.ProcessRuntimeService;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.WindowParams;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;
import ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAdd;
import ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAddStatus;
import ru.spacecorp.mobdekbkp.web.PublicConstants;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

public class ApplicationNewTyponominalAddEdit extends AbstractEditor<ApplicationNewTyponominalAdd> {
    private static final String PROCESS_CODE = "addnewtnapplication";

    @Inject
    private DataManager dataManager;
    @Inject
    private UserSession userSession;
    @Inject
    private ProcActionsFrame procActionsFrame;
    @Named("applicationNewTyponominalAddFieldGroup.designation")
    private Field fldDesignation;
    @Named("applicationNewTyponominalAddFieldGroup.status")
    private Field fldStatus;
    @Named("applicationNewTyponominalAddFieldGroup.docInfo")
    private Field fldDocInfo;
    @Named("applicationNewTyponominalAddFieldGroup.classMop")
    private Field fldClassMop;
    @Named("applicationNewTyponominalAddFieldGroup.producer")
    private Field fldProducer;
    @Named("applicationNewTyponominalAddFieldGroup.desc")
    private Field fldDesc;
    @Inject
    private ButtonsPanel documentBtnPanel;
    @Inject
    private Table documentTable;

    @Inject
    private Button readyBtn;

    @Named("documentTable.create")
    private CreateAction createActionDoc;

    private boolean isNew = false;

    @Inject
    private ProcessRuntimeService processRuntimeService;

    @Inject
    private Metadata metadata;

    @Override
    public void init(Map<String, Object> params) {
        //TODO ExtUser проверить после доработки админ параметров
        LoadContext<ExtUser> ctx = LoadContext.create(ExtUser.class);
        ctx.setQuery(LoadContext.createQuery("select e from notificationsusers$ExtUser e where e.login = :login")
                .setParameter("login", userSession.getUser().getLogin()));
        ExtUser currentUser = dataManager.load(ctx);
        if (currentUser == null)
            throw new RuntimeException("Something went wrong loading current Extended user! (AppNewTnDevEd.java:45)");

        if (currentUser.getCompanyRef() == null)
            showNotification(getMessage("noCompanyWarning"), NotificationType.WARNING);

        ApplicationNewTyponominalAdd applicationNewTyponominalAdd = (ApplicationNewTyponominalAdd) WindowParams.ITEM.getEntity(params);
        if (applicationNewTyponominalAdd != null) {
            if (applicationNewTyponominalAdd.getCreatedBy() != null) {
                if (userSession.getUser().getLogin().contentEquals(applicationNewTyponominalAdd.getCreatedBy())) {
                    if (applicationNewTyponominalAdd.getStatus() == ApplicationNewTyponominalAddStatus.created) {
                        readyBtn.setEnabled(true);
                    }
                }
            }
        }
        //readyBtn.setEnabled(true);
    }


    private void setAllActive(boolean b) {
        Field[] allFields = {fldStatus, fldClassMop, fldProducer, fldDocInfo, fldDesignation, fldDesc};
        for (Field f : allFields) {
            f.setEditable(b);
        }
        documentBtnPanel.setEnabled(b);
        documentTable.removeAction("edit");
    }

    @Override
    protected void initNewItem(ApplicationNewTyponominalAdd item) {
        item.setStatus(ApplicationNewTyponominalAddStatus.created);
        isNew = true;
        readyBtn.setEnabled(true);
        super.initNewItem(item);
    }

    @Override
    public void ready() {
        if ((getItem().getStatus() != ApplicationNewTyponominalAddStatus.created)
                && (getItem().getStatus() != ApplicationNewTyponominalAddStatus.onApproval)) {
            setAllActive(false);
        }
    }

    @Override
    protected void postInit() {
        //if (!isNew) initProcActionsFrame();
        //isNew = false;
        if ((getItem().getStatus() != ApplicationNewTyponominalAddStatus.created)
                && (getItem().getStatus() != ApplicationNewTyponominalAddStatus.declined)
                && (getItem().getStatus() != ApplicationNewTyponominalAddStatus.accepted)) {
            initProcActionsFrame();
        }
    }

    private void initProcActionsFrame() {
        procActionsFrame.initializer()
                .setBeforeStartProcessPredicate(this::commit)
                .setAfterStartProcessListener(() -> {
                    showNotification(getMessage("processStarted"),
                            NotificationType.HUMANIZED);
                    close(COMMIT_ACTION_ID);
                })
                .setAfterClaimTaskListener(this::initProcActionsFrame)
                .setBeforeCompleteTaskPredicate(this::commit)
                .setAfterCompleteTaskListener(() -> {
                    showNotification(getMessage("taskCompleted"),
                            NotificationType.HUMANIZED);
                    close(COMMIT_ACTION_ID);
                })
                .init(PROCESS_CODE, getItem());
        /*procActionsFrame.initializer()
                .standard()
                .init(PROCESS_CODE, getItem());*/
    }

    private void startProc() {
        ProcInstance procInstance = procActionsFrame.getProcInstance();
        List<ProcRole> procRoles = procInstance.getProcDefinition().getProcRoles();

        ProcRole gnioRole = null;
        for (ProcRole procRole : procRoles) {
            if (procRole.getCode().contentEquals("GNIO")) {
                gnioRole = procRole;
                break;
            }
        }
        //TODO проверка gnioRole на null
        procInstance = dataManager.commit(procInstance);

        LoadContext<User> ctx = LoadContext.create(User.class);
        ctx.setQueryString("select u from sec$User u join u.userRoles ur where ur.role.name = :roleName")
                .setParameter("roleName", PublicConstants.SYS_ROLE_GNIO);
        List<User> gnioUsers = dataManager.loadList(ctx);

        if (gnioUsers == null) {
            processRuntimeService.cancelProcess(procInstance, "No GNIO users");
        } else {
            Set<ProcActor> procActorSet = new HashSet<>();
            for (User user : gnioUsers) {
                ProcActor procActor = metadata.create(ProcActor.class);
                procActor.setProcRole(gnioRole);
                procActor.setUser(user);
                procActor.setProcInstance(procInstance);
                procActor = dataManager.commit(procActor);
                procActorSet.add(procActor);
            }

            procInstance.setProcActors(procActorSet);
            procInstance.setActive(false);
            procInstance = dataManager.commit(procInstance);

            processRuntimeService.startProcess(procInstance, "Auto start", null);
            initProcActionsFrame();
        }
    }

    public void onReadyBtnClick() {
        getItem().setStatus(ApplicationNewTyponominalAddStatus.inGnio);
        setAllActive(false);
        readyBtn.setEnabled(false);
        if (commit()) {
            startProc();
        }
    }
}