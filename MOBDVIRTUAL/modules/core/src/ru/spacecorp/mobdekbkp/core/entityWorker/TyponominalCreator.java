package ru.spacecorp.mobdekbkp.core.entityWorker;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.ExtBoolean;
import ru.spacecorp.mobdekbkp.entity.Type;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.entity.TyponominalLifeCycleStage;

import javax.inject.Inject;

@Component("mobdekbkp_TyponominalCreator")
public class TyponominalCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;

    public Typonominal create(String name,
                              Type type,
                              ExtBoolean notPerspective,
                              ExtBoolean hasSubstitute,
                              ExtBoolean sealNeeded,
                              ExtBoolean isAutoAssemble,
                              ExtBoolean madeUsingImportItems,
                              ExtBoolean hasBody,
                              TyponominalLifeCycleStage typonominalLifeCycleStage) {
        EntityManager em = persistence.getEntityManager();

        TypedQuery<Typonominal> query = em.createQuery(
                "select t\n" +
                        "from mobdekbkp$Typonominal t\n" +
                        "where t.name = :name", Typonominal.class)
                .setParameter("name", name)
                .setViewName(View.LOCAL);

        Typonominal typonominal = query.getFirstResult();

        if (typonominal == null) {
            typonominal = metadata.create(Typonominal.class);
            typonominal.setName(name);
            typonominal.setType(type);
            typonominal.setNotPerspective(notPerspective);
            typonominal.setHasSubstitute(hasSubstitute);
            typonominal.setSealNeeded(sealNeeded);
            typonominal.setIsAutoAssemble(isAutoAssemble);
            typonominal.setMadeUsingImportItems(madeUsingImportItems);
            typonominal.setHasBody(hasBody);
            typonominal.setLifeCycleStage(typonominalLifeCycleStage);
            em.persist(typonominal);
        }

        return typonominal;
    }
}
