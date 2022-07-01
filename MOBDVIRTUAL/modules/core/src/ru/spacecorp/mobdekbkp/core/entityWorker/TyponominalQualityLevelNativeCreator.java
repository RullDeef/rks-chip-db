package ru.spacecorp.mobdekbkp.core.entityWorker;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.TyponominalQualityLevelNative;

import javax.inject.Inject;

@Component("mobdekbkp_TyponominalQualityLevelNativeCreator")
public class TyponominalQualityLevelNativeCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;

    public TyponominalQualityLevelNative create(String name) {
        EntityManager em = persistence.getEntityManager();

        name = name.equals("") ? "нет данных" : name;

        TypedQuery<TyponominalQualityLevelNative> query = em.createQuery(
                "select e\n" +
                        "from mobdekbkp$TyponominalQualityLevelNative e\n" +
                        "where e.name = :name", TyponominalQualityLevelNative.class)
                .setParameter("name", name)
                .setViewName(View.LOCAL);

        TyponominalQualityLevelNative typonominalQualityLevelNative = query.getFirstResult();

        if (typonominalQualityLevelNative == null) {
            typonominalQualityLevelNative = metadata.create(TyponominalQualityLevelNative.class);
            typonominalQualityLevelNative.setName(name);
            em.persist(typonominalQualityLevelNative);
        }

        return typonominalQualityLevelNative;
    }
}
