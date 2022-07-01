package ru.spacecorp.mobdekbkp.core.entityWorker;


import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.Company;
import ru.spacecorp.mobdekbkp.entity.Country;
import ru.spacecorp.mobdekbkp.entity.ExtBoolean;

import javax.inject.Inject;

@Component("mobdekbkp_CompanyCreator")
public class CompanyCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;
    @Inject
    private CompanyTypeCreator compantTypeCreator;


    public Company create(String name, Country country, String companyType, String addressFact, String addressLegal, String phone) {
        EntityManager em = persistence.getEntityManager();
        name = name.equals("") ? "_" : name;

        TypedQuery<Company> query = em.createQuery(
                "select e \n" +
                        "from mobdekbkp$Company e\n" +
                        "where e.name = :nameCompany",
                Company.class);
        query.setParameter("nameCompany", name);
        query.setViewName(View.LOCAL);

        Company company = query.getFirstResult();
        if (company == null) {
            company = metadata.create(Company.class);
            company.setName(name);
            company.setNameShort(name);
            company.setCountry(country);
            company.setAddressFact(addressFact);
            company.setAddressLegal(addressLegal);
            company.setPhone(phone);
            if (companyType != null) //TODO: companyType сделать бы списком
                company.setTypes(compantTypeCreator.create(company, companyType));
            em.persist(company);
        }

        return company;
    }

}
