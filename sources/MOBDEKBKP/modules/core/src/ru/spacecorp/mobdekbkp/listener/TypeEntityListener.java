package ru.spacecorp.mobdekbkp.listener;

import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import ru.spacecorp.mobdekbkp.entity.Type;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import ru.spacecorp.mobdekbkp.entity.TypeCalicoholderEntry;
import ru.spacecorp.mobdekbkp.entity.TypeManufacturerEntry;
import ru.spacecorp.mobdekbkp.entity.TypeProviderEntry;

import javax.inject.Inject;

@Component("mobdekbkp_TypeEntityListener")
public class TypeEntityListener implements BeforeInsertEntityListener<Type>{


    @Override
    public void onBeforeInsert(Type item, EntityManager entityManager) {
        if(item.getManufacturers() != null){
            for (TypeManufacturerEntry manufactEntry : item.getManufacturers()) {
                entityManager.persist(manufactEntry);
            }
        }
        if(item.getCalicoholders() != null){
            for (TypeCalicoholderEntry calicoholderEntry : item.getCalicoholders()) {
                entityManager.persist(calicoholderEntry);
            }
        }
        if(item.getProviders() != null){
            for (TypeProviderEntry providerEntry : item.getProviders()) {
                entityManager.persist(providerEntry);
            }
        }

    }



}