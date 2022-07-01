package ru.spacecorp.mobdekbkp.core.entityWorker;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.*;

import javax.inject.Inject;

@Component("mobdekbkp_TypeCreator")
public class TypeCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;


    public Type create(String name,
                       TypeClass typeClass,
                       String functionNote,
                       UnitDev unitDev,
                       ExtBoolean hasLongCycle,
                       TypePlacementCategory placementCategory) {
        EntityManager em = persistence.getEntityManager();

        TypedQuery<Type> query = em.createQuery(
                "select e\n" +
                        "from mobdekbkp$Type e\n" +
                        "where e.designation = :name", Type.class)
                .setParameter("name", name)
                .setViewName(View.LOCAL);

        Type type = query.getFirstResult();

        if (type == null) {
            type = metadata.create(Type.class);
            type.setDesignation(name);
            type.setTypeClass(typeClass);
            type.setFunctionalPurpose(functionNote);
            type.setAmountUnit(unitDev);
            type.setHasLongDeliverCycle(hasLongCycle);
            type.setPlacementCategory(placementCategory);
            em.persist(type);
        }

        return type;
    }


}
