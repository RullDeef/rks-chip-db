package ru.spacecorp.mobdekbkp.core.tc;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.core.entityWorker.ParameterCreator;
import ru.spacecorp.mobdekbkp.core.entityWorker.UnitValCreator;
import ru.spacecorp.mobdekbkp.entity.*;
import ru.spacecorp.mobdekbkp.entity.tc.AttrEkbObject;
import ru.spacecorp.mobdekbkp.entity.tc.EkbObject;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

@Component("mobdekbkp_TCDataExtractor")
public class TCDataExtractor {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;
    @Inject
    private ParameterCreator parameterCreator;
    @Inject
    private UnitValCreator unitValCreator;
    @Inject
    private MappingTableWorker mappingTableWorker;

    public List<String> getEkbId() {
        List<String> listId = new ArrayList<>();
        Query query = persistence.getEntityManager().createNativeQuery(
                "select idtc\n" +
                        "from public.ekb\n" +
                        "group by idtc");

        List list = query.getResultList();
        for (Object aList : list) {
            String id = (String) aList;
            listId.add(id);
        }

        return listId;
    }

    public EkbObject getEkb(String id) {
        Query query = persistence.getEntityManager().createNativeQuery(
                "select objectname, parentidtc, idattrib, valueattrib\n" +
                        "from public.ekb\n" +
                        "where idtc = #id").
                setParameter("id", id);

        List list = query.getResultList();

        Object[] first = (Object[]) list.get(0);
        EkbObject ekbObject = metadata.create(EkbObject.class);
        ekbObject.setIdtc(id);
        ekbObject.setName((String) first[0]);
        ekbObject.setParent((String) first[1]);
        LinkedHashSet<AttrEkbObject> attrEkbObjects = new LinkedHashSet<>();

        for (Object aList : list) {
            Object[] row = (Object[]) aList;
            AttrEkbObject attrEkbObject = metadata.create(AttrEkbObject.class);
            attrEkbObject.setAttrId((String) row[2]);
            attrEkbObject.setValue((String) row[3]);
            attrEkbObjects.add(attrEkbObject);
        }

        ekbObject.setAttrs(attrEkbObjects);

        return ekbObject;
    }


    public HashMap<String, Parameter> getParameters() {
        UnitVal unitVal = unitValCreator.create("нет");
        HashMap<String, Parameter> stringParameterHashMap = new HashMap<>();

        Query query = persistence.getEntityManager().createNativeQuery(
                "select attr.idattr, attr.name\n" +
                        "from public.attrekb attr");

        List list = query.getResultList();

        for (Object aList : list) {
            Object[] row = (Object[]) aList;
            String key = (String) row[0];
            Parameter parameter = mappingTableWorker.getParameter(key);
            if (parameter == null) {
                parameter = parameterCreator.create(
                        (String) row[1],
                        key,
                        ParameterValueType.string,
                        ParameterType.technical,
                        unitVal);

            }
            stringParameterHashMap.put(key, parameter);
        }

        return stringParameterHashMap;
    }
}
