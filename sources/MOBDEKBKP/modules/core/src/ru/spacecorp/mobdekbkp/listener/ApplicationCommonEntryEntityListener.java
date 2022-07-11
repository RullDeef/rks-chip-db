package ru.spacecorp.mobdekbkp.listener;

import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import ru.spacecorp.mobdekbkp.entity.ApplicationCommonEntry;
import ru.spacecorp.mobdekbkp.entity.ApplicationNewDevEntry;

@Component("mobdekbkp_ApplicationCommonEntryEntityListener")
public class ApplicationCommonEntryEntityListener implements BeforeInsertEntityListener<ApplicationCommonEntry> {


    @Override
    public void onBeforeInsert(ApplicationCommonEntry entity, EntityManager entityManager) {
        if (entity.getParents() != null) {
            for (ApplicationNewDevEntry i : entity.getParents())
                entityManager.persist(i);
        }
    }


}