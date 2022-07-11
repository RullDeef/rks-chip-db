package ru.spacecorp.mobdekbkp.core.entityWorker;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.Company;
import ru.spacecorp.mobdekbkp.entity.Type;
import ru.spacecorp.mobdekbkp.entity.TypeManufacturerEntry;

import javax.inject.Inject;
import java.util.List;

@Component("mobdekbkp_TypeManufacturerEntryCreator")
public class TypeManufacturerEntryCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;

    public TypeManufacturerEntry create(Type type, Company company) {
        TypeManufacturerEntry manufacturerEntry = getEntry(type, company);

        if (manufacturerEntry == null) {
            manufacturerEntry = metadata.create(TypeManufacturerEntry.class);
            manufacturerEntry.setName(company);
            manufacturerEntry.setTypeInverse(type);
            persistence.getEntityManager().persist(manufacturerEntry);
        }

        return manufacturerEntry;
    }

    public List<TypeManufacturerEntry> add(Type type, Company company) {
        List<TypeManufacturerEntry> typeManufacturerEntryList = getEntryList(type);
        TypeManufacturerEntry manufacturerEntry = create(type, company);

        if (!typeManufacturerEntryList.contains(manufacturerEntry)) {
            typeManufacturerEntryList.add(manufacturerEntry);
        }

        return typeManufacturerEntryList;
    }


    private TypeManufacturerEntry getEntry(Type type, Company company) {
        TypedQuery<TypeManufacturerEntry> query = persistence.getEntityManager().createQuery(
                "select e\n" +
                        "from mobdekbkp$TypeManufacturerEntry e\n" +
                        "where e.typeInverse.id = :type and e.name.id = :company ", TypeManufacturerEntry.class)
                .setParameter("type", type)
                .setParameter("company", company)
                .setViewName(View.LOCAL);

        return query.getFirstResult();
    }

    private List<TypeManufacturerEntry> getEntryList(Type type) {
        TypedQuery<TypeManufacturerEntry> query = persistence.getEntityManager().createQuery(
                "select e\n" +
                        "from mobdekbkp$TypeManufacturerEntry e\n" +
                        "where e.typeInverse.id = :type", TypeManufacturerEntry.class)
                .setParameter("type", type)
                .setViewName(View.LOCAL);

        return query.getResultList();
    }
}
