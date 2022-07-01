package ru.spacecorp.mobdekbkp.core.tc;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.Parameter;
import ru.spacecorp.mobdekbkp.entity.TypeClass;
import ru.spacecorp.mobdekbkp.entity.tc.MappingEntity;

import javax.inject.Inject;

@Component("mobdekbkp_MappingTableWorker")
public class MappingTableWorker {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;

    public void create(StandardEntity entity, String id) {
        MappingEntity mappingEntity = metadata.create(MappingEntity.class);

        mappingEntity.setIdcuba(entity.getUuid());
        mappingEntity.setIdtc(id);

        persistence.getEntityManager().persist(mappingEntity);
    }

    public TypeClass getTypeClass(String id) {

        EntityManager em = persistence.getEntityManager();
        Query query = em.createQuery("select e.idcuba from mobdekbkp$MappingEntity e where e.idtc = :_id");
        query.setParameter("_id", id);

        Object typeClassUUID = query.getFirstResult();

        if (typeClassUUID == null) {
            return null;
        }

        TypedQuery<TypeClass> query2 = em.createQuery(
                "select e\n" +
                        "from mobdekbkp$TypeClass e\n" +
                        "where e.id = :_id",
                TypeClass.class);
        query2.setParameter("_id", typeClassUUID);
        query2.setViewName(View.LOCAL);

        TypeClass typeClass = query2.getFirstResult();

        if (typeClass == null) {
            return null;
        }

        return typeClass;
    }

    public Parameter getParameter(String id) {
        EntityManager em = persistence.getEntityManager();
        Query query = em.createQuery("select e.idcuba from mobdekbkp$MappingEntity e where e.idtc = :_id");
        query.setParameter("_id", id);

        Object parameterId = query.getFirstResult();

        if (parameterId == null) {
            return null;
        }

        TypedQuery<Parameter> query2 = em.createQuery(
                "select e\n" +
                        "from mobdekbkp$Parameter e\n" +
                        "where e.id = :parameterId",
                Parameter.class).
                setParameter("parameterId", parameterId)
                .setViewName(View.LOCAL);

        Parameter parameter = query2.getFirstResult();

        if (parameter == null) {
            return null;
        }

        return parameter;
    }
}
