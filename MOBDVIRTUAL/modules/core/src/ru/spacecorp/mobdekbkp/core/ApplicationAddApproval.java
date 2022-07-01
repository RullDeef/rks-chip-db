package ru.spacecorp.mobdekbkp.core;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAdd;
import ru.spacecorp.mobdekbkp.entity.ApplicationNewTyponominalAddStatus;

import javax.inject.Inject;
import java.util.UUID;

@Component("mobdekbkp_ApplicationAddApproval")
public class ApplicationAddApproval {
    @Inject
    private Persistence persistence;

    public void update(UUID entityId, String status) {
        try (Transaction tx = persistence.getTransaction()) {
            ApplicationNewTyponominalAdd item = persistence.getEntityManager().find(ApplicationNewTyponominalAdd.class, entityId);
            if (item != null) {
                ApplicationNewTyponominalAddStatus statusEnum = ApplicationNewTyponominalAddStatus.fromId(status);
                if (statusEnum != null) {
                    item.setStatus(statusEnum);
                }
            }
            tx.commit();
        }
    }
}
