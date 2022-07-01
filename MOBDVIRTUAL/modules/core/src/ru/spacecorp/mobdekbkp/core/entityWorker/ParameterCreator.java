package ru.spacecorp.mobdekbkp.core.entityWorker;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.core.tc.MappingTableWorker;
import ru.spacecorp.mobdekbkp.entity.*;

import javax.inject.Inject;

@Component("mobdekbkp_ParameterCreator")
public class ParameterCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;

    @Inject
    private MappingTableWorker mappingTableWorker;

    public Parameter create(String name, String description, ParameterValueType parameterValueType, ParameterType parameterType, UnitVal unitVal) {
        EntityManager em = persistence.getEntityManager();

        TypedQuery<Parameter> query = em.createQuery(
                "select e\n" +
                        "from mobdekbkp$Parameter e\n" +
                        "where e.name = :name and e.description = :description",
                Parameter.class).
                setParameter("name", name).
                setParameter("description", description).
                setViewName(View.LOCAL);

        Parameter parameter = query.getFirstResult();

        if (parameter == null) {
            parameter = metadata.create(Parameter.class);
            parameter.setName(name);
            parameter.setDescription(description);
            parameter.setDefaultValueType(parameterValueType);
            parameter.setParamType(parameterType);
            parameter.setUnit(unitVal);
            em.persist(parameter);

            mappingTableWorker.create(parameter, description);
        }


        return parameter;
    }
}
