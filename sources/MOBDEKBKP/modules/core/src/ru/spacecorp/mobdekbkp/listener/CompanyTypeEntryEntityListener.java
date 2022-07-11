package ru.spacecorp.mobdekbkp.listener;

import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeDeleteEntityListener;
import com.haulmont.cuba.core.EntityManager;
import ru.spacecorp.mobdekbkp.entity.CompanyTypeEntry;

@Component("mobdekbkp_CompanyTypeEntryEntityListener")
public class CompanyTypeEntryEntityListener implements BeforeDeleteEntityListener<CompanyTypeEntry> {


    @Override
    public void onBeforeDelete(CompanyTypeEntry entity, EntityManager entityManager) {
        entity.getCompany();
    }
}