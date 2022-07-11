package ru.spacecorp.mobdekbkp.core.entityWorker;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.Company;
import ru.spacecorp.mobdekbkp.entity.Type;
import ru.spacecorp.mobdekbkp.entity.TypeCalicoholderEntry;

import javax.inject.Inject;
import java.util.List;

@Component("mobdekbkp_TypeCalicoholdersEntryCreator")
public class TypeCalicoholdersEntryCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;

    public TypeCalicoholderEntry create(Type type, Company company) {
        TypeCalicoholderEntry calicoholderEntry = getEntry(type, company);

        if (calicoholderEntry == null) {
            calicoholderEntry = metadata.create(TypeCalicoholderEntry.class);
            calicoholderEntry.setName(company);
            calicoholderEntry.setTypeInverse(type);
            persistence.getEntityManager().persist(calicoholderEntry);
        }

        return calicoholderEntry;
    }

    public List<TypeCalicoholderEntry> add(Type type, Company company) {
        List<TypeCalicoholderEntry> typeCalicoholderEntries = getEntryList(type);
        TypeCalicoholderEntry calicoholderEntry = create(type, company);

        if (!typeCalicoholderEntries.contains(calicoholderEntry)) {
            typeCalicoholderEntries.add(calicoholderEntry);
        }

        return typeCalicoholderEntries;
    }


    private TypeCalicoholderEntry getEntry(Type type, Company company) {
        TypedQuery<TypeCalicoholderEntry> query = persistence.getEntityManager().createQuery(
                "select e\n" +
                        "from mobdekbkp$TypeCalicoholderEntry e\n" +
                        "where e.typeInverse.id = :type and e.name.id = :company ", TypeCalicoholderEntry.class)
                .setParameter("type", type)
                .setParameter("company", company)
                .setViewName(View.LOCAL);

        return query.getFirstResult();
    }

    private List<TypeCalicoholderEntry> getEntryList(Type type) {
        TypedQuery<TypeCalicoholderEntry> query = persistence.getEntityManager().createQuery(
                "select e\n" +
                        "from mobdekbkp$TypeCalicoholderEntry e\n" +
                        "where e.typeInverse.id = :type", TypeCalicoholderEntry.class)
                .setParameter("type", type)
                .setViewName(View.LOCAL);

        return query.getResultList();
    }
}
