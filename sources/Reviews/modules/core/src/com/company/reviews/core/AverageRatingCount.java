package com.company.reviews.core;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.ValueLoadContext;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Component("reviews_AverageRatingCount")
public class AverageRatingCount {

    @Inject
    private DataManager dataManager;

    /**
     * Сервис возвращает среднюю оценку по отзывам
     *
     * @param object Объект для которого нужно вычислить оценку
     * @return Оценка
     */
    public Double countAverage(Object object) {

        StandardEntity st = (StandardEntity) object;
        UUID objId = st.getId();

        ValueLoadContext context = ValueLoadContext.create()
                .setQuery(
                        ValueLoadContext.createQuery(
                                "select avg(r.grade) " +
                                        "from reviews$Review r " +
                                        "where r.parent = :parentId and " +
                                        "(r.status='accepted' or r.status='moderated') " +
                                        "group by r.parent "
                        ).setParameter("parentId", objId)
                )
                .addProperty("avg");

        List<KeyValueEntity> value = dataManager.loadValues(context);

        if (value.size() == 0)
            return (double) 0;

        return value.get(0).getValue("avg");

    }

}
