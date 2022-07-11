package ru.spacecorp.mobdekbkp.core.entityWorker;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.UnitDev;

import javax.inject.Inject;

@Component("mobdekbkp_UnitDevCreator")
public class UnitDevCreator {

    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;

    public UnitDev create(String name) {
        EntityManager em = persistence.getEntityManager();

        TypedQuery<UnitDev> query = em.createQuery(
                "select e\n" +
                        "from mobdekbkp$UnitDev e\n" +
                        "where e.name = :name", UnitDev.class)
                .setParameter("name", name)
                .setViewName(View.LOCAL);

        UnitDev unitDev = query.getFirstResult();

        if (unitDev == null) {
            unitDev = metadata.create(UnitDev.class);
            unitDev.setName(name);
            em.persist(unitDev);
        }

        return unitDev;
    }
}
