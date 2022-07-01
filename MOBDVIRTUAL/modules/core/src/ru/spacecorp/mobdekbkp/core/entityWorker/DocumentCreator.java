package ru.spacecorp.mobdekbkp.core.entityWorker;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.documents.entity.Document;
import ru.spacecorp.documents.entity.DocumentType;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component("mobdekbkp_DocumentCreator")
public class DocumentCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;
    @Inject
    private DocumentTypeCreator documentTypeCreator;

    public List<Document> create(String name, String type) {
        String[] listName = name.split(",");
        List<Document> documentList = new ArrayList<>();
        for (String nameDoc : listName) {
            String normaliseName = nameDoc.trim();

            Document document = createDoc(normaliseName, type);
            if (document != null)
                documentList.add(document);
        }

        return documentList;
    }

    private Document createDoc(String name, String type) {
        if (name.equals(""))
            return null;

        return createDoc(name, documentTypeCreator.create(type));
    }

    private Document createDoc(String name, DocumentType type) {
        EntityManager em = persistence.getEntityManager();
        TypedQuery<Document> query = em.createQuery(
                "select d\n" +
                        "from documents$Document d\n" +
                        "where d.name = :name and d.documentType.id = :type", Document.class)
                .setParameter("name", name)
                .setParameter("type", type)
                .setViewName(View.LOCAL);

        Document document = query.getFirstResult();

        if (document == null) {
            document = metadata.create(Document.class);
            document.setName(name);
            document.setDocumentType(type);
            em.persist(document);
        }

        return document;
    }
}
