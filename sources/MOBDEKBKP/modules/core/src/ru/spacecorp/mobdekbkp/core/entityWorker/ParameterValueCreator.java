package ru.spacecorp.mobdekbkp.core.entityWorker;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.*;

import javax.inject.Inject;

@Component("mobdekbkp_ParameterValueCreator")
public class ParameterValueCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;


    public ParameterValue create(Type type, Parameter parameter, ParameterValueType parameterValueType, String value) {
        EntityManager em = persistence.getEntityManager();

        TypedQuery<ParameterValue> query = em.createQuery(
                "select e\n" +
                        "from mobdekbkp$ParameterValue e\n" +
                        "where e.type.id = :type and e.parameter.id = :parameter",
                ParameterValue.class).
                setParameter("type", type).
                setParameter("parameter", parameter).
                setViewName(View.LOCAL);

        ParameterValue parameterValue = query.getFirstResult();

        if (parameterValue == null) {
            parameterValue = metadata.create(ParameterValue.class);
            parameterValue.setParameter(parameter);
            parameterValue.setType(type);
            parameterValue.setCurrentValueType(parameterValueType);
            parameterValue.setValString(value);
            em.persist(parameterValue);

        }

        return parameterValue;
    }
}
