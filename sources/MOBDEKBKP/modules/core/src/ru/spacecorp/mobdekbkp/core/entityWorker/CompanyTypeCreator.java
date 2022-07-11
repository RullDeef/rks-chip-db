package ru.spacecorp.mobdekbkp.core.entityWorker;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.Company;
import ru.spacecorp.mobdekbkp.entity.CompanyType;
import ru.spacecorp.mobdekbkp.entity.CompanyTypeEntry;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@Component("mobdekbkp_CompanyTypeCreator")
public class CompanyTypeCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;

    public List<CompanyTypeEntry> create(Company company, String companyType) {
        return create(company, createCompanyType(companyType));
    }

    public List<CompanyTypeEntry> create(Company company, CompanyType companyType) {
        EntityManager em = persistence.getEntityManager();

        TypedQuery<CompanyTypeEntry> query = em.createQuery(
                "select e\n" +
                        "from mobdekbkp$CompanyTypeEntry e\n" +
                        "where e.company.id = :company and " +
                        "e.type.id = :type",
                CompanyTypeEntry.class)
                .setParameter("company", company)
                .setParameter("type", companyType)
                .setViewName(View.LOCAL);

        CompanyTypeEntry companyTypeEntry = query.getFirstResult();
        if (companyTypeEntry == null) {
            companyTypeEntry = metadata.create(CompanyTypeEntry.class);
            companyTypeEntry.setCompany(company);
            companyTypeEntry.setType(companyType);
            em.persist(companyTypeEntry);

        }

        return Collections.singletonList(companyTypeEntry);
    }

    private CompanyType createCompanyType(String name) {
        EntityManager em = persistence.getEntityManager();

        TypedQuery<CompanyType> query = em.createQuery(
                "select e \n" +
                        "from mobdekbkp$CompanyType e\n" +
                        "where e.name = :name", CompanyType.class
        ).setParameter("name", name);

        CompanyType companyType = query.getFirstResult();
        if (companyType == null) {
            companyType = metadata.create(CompanyType.class);
            companyType.setName(name);
            em.persist(companyType);
        }

        return companyType;
    }

}
