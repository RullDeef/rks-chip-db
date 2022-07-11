package ru.spacecorp.mobdekbkp.core.entityWorker;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.UnitVal;

import javax.inject.Inject;

@Component("mobdekbkp_UnitValCreator")
public class UnitValCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;

    public UnitVal create(String name) {
        EntityManager em = persistence.getEntityManager();

        TypedQuery<UnitVal> query = em.createQuery(
                "select e\n" +
                        "from mobdekbkp$UnitVal e\n" +
                        "where e.name = :name", UnitVal.class)
                .setParameter("name", name)
                .setViewName(View.LOCAL);

        UnitVal unitVal = query.getFirstResult();

        if (unitVal == null) {
            unitVal = metadata.create(UnitVal.class);
            unitVal.setName(name);
            em.persist(unitVal);
        }

        return unitVal;
    }
}
