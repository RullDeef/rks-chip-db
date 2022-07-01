package ru.spacecorp.mobdekbkp.core.entityWorker;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.*;

import javax.inject.Inject;

@Component("mobdekbkp_TyponominalBodyCreator")
public class TyponominalBodyCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;


    public TyponominalBody create(String name, String designation, ExtBoolean sealingDemand) {
        EntityManager em = persistence.getEntityManager();
        designation = designation == null ? name : designation;

        TypedQuery<TyponominalBody> query = em.createQuery(
                "select e\n" +
                        "from mobdekbkp$TyponominalBody e\n" +
                        "where e.name = :name", TyponominalBody.class)
                .setParameter("name", name)
                .setViewName(View.LOCAL);

        TyponominalBody typonominalBody = query.getFirstResult();

        if (typonominalBody == null) {
            typonominalBody = metadata.create(TyponominalBody.class);
            typonominalBody.setName(name);
            typonominalBody.setDesignation(designation);
            typonominalBody.setSealingDemand(sealingDemand);
            em.persist(typonominalBody);
        }

        return typonominalBody;
    }
}
