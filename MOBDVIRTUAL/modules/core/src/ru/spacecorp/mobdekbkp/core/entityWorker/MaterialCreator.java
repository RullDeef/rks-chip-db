package ru.spacecorp.mobdekbkp.core.entityWorker;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.Material;
import ru.spacecorp.mobdekbkp.entity.MaterialType;

import javax.inject.Inject;

@Component("mobdekbkp_MaterialCreator")
public class MaterialCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;


    public Material create(String name, MaterialType materialType) {
        if (name == null || name.equals(""))
            return null;

        EntityManager em = persistence.getEntityManager();

        TypedQuery<Material> query = em.createQuery(
                "select e\n" +
                        "from mobdekbkp$Material e\n" +
                        "where e.name = :name and e.type = :materialType", Material.class)
                .setParameter("name", name)
                .setParameter("materialType", materialType)
                .setViewName(View.LOCAL);

        Material material = query.getFirstResult();

        if (material == null) {
            material = metadata.create(Material.class);
            material.setName(name);
            material.setType(materialType);
            em.persist(material);
        }

        return material;
    }
}
