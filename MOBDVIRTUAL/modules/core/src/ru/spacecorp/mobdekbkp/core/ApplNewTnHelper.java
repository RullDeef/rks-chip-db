package ru.spacecorp.mobdekbkp.core;

import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcTask;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.Messages;
import org.springframework.stereotype.Component;
import ru.iovchinnikov.notificationsusers.service.MessageService;
import ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAdd;
import ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAddStatus;

import javax.inject.Inject;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Stepanov_ME on 27.08.2018.
 */

@Component("mobd_ApplNewTnHelper")
public class ApplNewTnHelper {
    @Inject
    private Persistence persistence;

    @Inject
    private MessageService messageService;

    @Inject
    private Messages messages;

    public void updateState(UUID entityId, String state) {
        try (Transaction tx = persistence.getTransaction()) {
            ApplicationNewTyponominalAdd newTyponominalAdd = persistence.getEntityManager().find(ApplicationNewTyponominalAdd.class, entityId);
            if (newTyponominalAdd != null) {
                newTyponominalAdd.setStatus(ApplicationNewTyponominalAddStatus.valueOf(state));
            }
            tx.commit();
        }
    }

    public void sendInfo(UUID entityId, UUID procInstanceId, String state) {
        try (Transaction tx = persistence.getTransaction()) {
            ApplicationNewTyponominalAdd newTyponominalAdd = persistence.getEntityManager().find(ApplicationNewTyponominalAdd.class, entityId);
            ProcInstance procInstance = persistence.getEntityManager().find(ProcInstance.class, procInstanceId);
            if (newTyponominalAdd != null) {
                String creator = newTyponominalAdd.getCreatedBy();
                switch (state) {
                    case "declined": {
                        String reason = "";
                        Collection<ProcTask> procTasks = procInstance.getProcTasks();
                        for (ProcTask procTask : procTasks) {
                            if (procTask.getOutcome().contentEquals("declined")) {
                                reason = procTask.getComment();
                            }
                        }
                        messageService.send(null, creator,
                                messages.getMessage(getClass(), "typonominalAdd"),
                                messages.getMessage(getClass(), "application") + " " +
                                        newTyponominalAdd.getDesignation() + " " + messages.getMessage(getClass(), "declined") +
                                        " " + messages.getMessage(getClass(), "reason") + " " + reason + ".", true);
                        break;
                    }
                    case "accepted": {
                        messageService.send(null, creator,
                                messages.getMessage(getClass(), "typonominalAdd"),
                                messages.getMessage(getClass(), "application") + " " +
                                        newTyponominalAdd.getDesignation() + " " + messages.getMessage(getClass(), "accepted"), true);
                        break;
                    }
                    case "onApproval": {
                        String reason = "";
                        Collection<ProcTask> procTasks = procInstance.getProcTasks();
                        for (ProcTask procTask : procTasks) {
                            if (procTask.getOutcome().contentEquals("onApproval")) {
                                reason = procTask.getComment();
                            }
                        }
                        messageService.send(null, creator,
                                messages.getMessage(getClass(), "typonominalAdd"),
                                messages.getMessage(getClass(), "application") + " " +
                                        newTyponominalAdd.getDesignation() + " " + messages.getMessage(getClass(), "onApproval") +
                                        " " + messages.getMessage(getClass(), "reason") + " " + reason + ".", true);
                        break;
                    }
                }
            }
            tx.commit();
        }
    }
}
