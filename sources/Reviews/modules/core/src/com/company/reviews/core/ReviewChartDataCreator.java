package com.company.reviews.core;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Stepanov_ME on 27.07.2018.
 */
@Component("reviews_ReviewChartDataCreator")
public class ReviewChartDataCreator {
    @Inject
    private DataManager dataManager;

    public Map<Integer,Integer> getAverageRating(Object object){
        HashMap<Integer,Integer> hashMap=new HashMap<>();

        StandardEntity st = (StandardEntity) object;
        UUID objId = st.getId();

        ValueLoadContext context = ValueLoadContext.create()
                .setQuery(
                        ValueLoadContext.createQuery(
                                "select r.grade, count(r) " +
                                        "from reviews$Review r " +
                                        "where r.parent = :parentId and " +
                                        "(r.status='accepted' or r.status='moderated') " +
                                        "group by r.grade " +
                                        "order by r.grade"
                        ).setParameter("parentId", objId)
                                    /*.setParameter("gradeVal", i)*/
                )
                .addProperty("grade")
                .addProperty("count");
        List<KeyValueEntity> value = dataManager.loadValues(context);
        for(KeyValueEntity keyVE:value){
            int grade=keyVE.getValue("grade");
            long count=keyVE.getValue("count");
            hashMap.put(grade,(int)count);
        }
        return hashMap;
    }
}
