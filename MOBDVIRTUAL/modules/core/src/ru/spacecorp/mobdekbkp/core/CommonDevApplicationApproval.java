package ru.spacecorp.mobdekbkp.core;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.ApplicationCommonDev;
import ru.spacecorp.mobdekbkp.entity.ApplicationCommonDevStatus;

import javax.inject.Inject;
import java.util.UUID;

@Component("mobdekbkp_CommonDevApplication")
public class CommonDevApplicationApproval {

    @Inject
    private Persistence persistence;

    public void update(UUID entityId, String state) {
        try (Transaction transaction = persistence.getTransaction()) {
            ApplicationCommonDev list = persistence.getEntityManager()
                    .find(ApplicationCommonDev.class, entityId);
            if (list != null) {
                list.setStatus(ApplicationCommonDevStatus.fromId(state));
            }
            transaction.commit();
        }
    }

    public void sendMsg(String theme) {
        //TODO собрать всех, кто вовлечён в процесс на данный момент, и послать сообщения
    }
}
