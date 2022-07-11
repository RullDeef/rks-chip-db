package ru.spacecorp.mobdekbkp.listener;

import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import ru.spacecorp.mobdekbkp.entity.SubstrateEntry;
import ru.spacecorp.mobdekbkp.entity.TyponominalInstallParameters;

@Component("mobdekbkp_TyponominalInstallParametersEntityListener")
public class TyponominalInstallParametersEntityListener implements BeforeInsertEntityListener<TyponominalInstallParameters> {


    @Override
    public void onBeforeInsert(TyponominalInstallParameters item, EntityManager entityManager) {
        if(item.getSubstrates() != null) {
            for (SubstrateEntry substrateEntry : item.getSubstrates()) {
                entityManager.persist(substrateEntry);
            }
        }
    }


}