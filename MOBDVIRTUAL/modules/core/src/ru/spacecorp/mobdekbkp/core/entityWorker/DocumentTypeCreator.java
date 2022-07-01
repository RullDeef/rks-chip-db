package ru.spacecorp.mobdekbkp.core.entityWorker;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.documents.entity.DocumentType;

import javax.inject.Inject;


@Component("mobdekbkp_DocumentTypeCreator")
public class DocumentTypeCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;


    public DocumentType create(String name) {
        EntityManager em = persistence.getEntityManager();

        TypedQuery<DocumentType> query = em.createQuery(
                "select d\n" +
                        "from documents$DocumentType d\n" +
                        "where d.name = :name", DocumentType.class)
                .setParameter("name", name)
                .setViewName(View.LOCAL);

        DocumentType documentType = query.getFirstResult();

        if (documentType == null) {
            documentType = metadata.create(DocumentType.class);
            documentType.setName(name);
            em.persist(documentType);
        }

        return documentType;
    }
}
