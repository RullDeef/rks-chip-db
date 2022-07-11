package ru.spacecorp.mobdekbkp.core;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;

import org.springframework.stereotype.Component;
import javax.inject.Inject;
import java.util.UUID;

import ru.iovchinnikov.notificationsusers.service.MessageService;

import ru.spacecorp.mobdekbkp.entity.DeviceListProject;
import ru.spacecorp.mobdekbkp.entity.DeviceListProjectStatus;

@Component("mobdekbkp_DevListPrjApproval")
public class DeviceListProjectApproval {

    @Inject
    private Persistence persistence;

    @Inject
    private MessageService messageService;

    public void update(UUID entityId, String state) {
        try(Transaction transaction = persistence.getTransaction()) {
            DeviceListProject list = persistence.getEntityManager()
                    .find(DeviceListProject.class, entityId);
            if (list != null) {
                list.setStatus(DeviceListProjectStatus.fromId(state));
            }
            transaction.commit();
        }
    }

    public void sendMsg(String theme) {
        //TODO собрать всех, кто вовлечён в процесс на данный момент, и послать сообщения
        //messageService.send("System", "admin", "Device N List approval", theme + " case");
    }
}
