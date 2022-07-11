package ru.spacecorp.mobdekbkp.core.tc;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.TypeClass;
import ru.spacecorp.mobdekbkp.entity.TypeClassType;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Component("mobdekbkp_TypeClassCreator")
public class TypeClassCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;
    @Inject
    private MappingTableWorker mappingTableWorker;

    public void create() {
        List<TypeClassShell> listTypeClass = getTypeClass();
        Map<Integer, TypeClassType> mapTypeClassType = getTypeClassType();

        createTypeClassTree(listTypeClass, mapTypeClassType, null, 0);
    }

    private void createTypeClassTree(List<TypeClassShell> listTypeClass, Map<Integer, TypeClassType> mapTypeClassType, TypeClass parent, int level) {
        for (TypeClassShell typeClassShell : listTypeClass) {
            TypeClass typeClass = createTypeClass(typeClassShell.id);

            typeClass.setNumber(getLineNumber(typeClassShell.name));
            typeClass.setName(getNameForLine(typeClassShell.name));
            typeClass.setType(mapTypeClassType.get(level));

            if (parent != null)
                typeClass.setParent(parent);

            persistence.getEntityManager().persist(typeClass);
            mappingTableWorker.create(typeClass, typeClassShell.id);

            List<TypeClassShell> children = typeClassShell.getChildren();
            if (children.size() != 0)
                createTypeClassTree(children, mapTypeClassType, typeClass, level + 1);
        }
    }

    private TypeClass createTypeClass(String id) {
        TypeClass typeClass = mappingTableWorker.getTypeClass(id);
        if (typeClass == null)
            typeClass = metadata.create(TypeClass.class);

        return typeClass;
    }

    private String getNameForLine(String line) {
        String[] words = line.split(" ");
        String number = words[0];
        if (number.equals("для"))
            return line;
        return line.replaceAll(number, "").trim();
    }

    private String getLineNumber(String line) {
        String[] words = line.split(" ");
        String number = words[0];
        if (number.equals("для"))
            return "";
        else {
            if (number.charAt(number.length() - 1) == '.')
                number = number.substring(0, number.length() - 1);
            return number.trim();
        }
    }

    private List<TypeClassShell> getTypeClass() {
        Map<String, TypeClassShell> mapTypeClassShells = new HashMap<>();

        Query query = persistence.getEntityManager().createNativeQuery(
                "SELECT idtc, parentidtc, \"name\" FROM dictionarygroupsforteamcenter");
        List list = query.getResultList();
        for (Object aList : list) {
            Object[] row = (Object[]) aList;


            TypeClassShell typeClassShell = new TypeClassShell();
            typeClassShell.id = (String) row[0];
            typeClassShell.name = (String) row[2];
            typeClassShell.parent_id = (String) row[1];

            mapTypeClassShells.put((String) row[0], typeClassShell);
        }

        createTree(mapTypeClassShells);


        return mapTypeClassShells.values().stream()
                .filter(t -> t.parent == null)
                .collect(Collectors.toList());
    }

    private Map<Integer, TypeClassType> getTypeClassType() {
        Map<Integer, TypeClassType> integerTypeClassTypeMap = new HashMap<>();

        integerTypeClassTypeMap.put(0, getTypeClassTypeForName("Часть"));
        integerTypeClassTypeMap.put(1, getTypeClassTypeForName("Раздел"));
        integerTypeClassTypeMap.put(2, getTypeClassTypeForName("Группа"));
        integerTypeClassTypeMap.put(3, getTypeClassTypeForName("Подгруппа"));

        return integerTypeClassTypeMap;
    }

    private TypeClassType getTypeClassTypeForName(String name) {
        EntityManager em = persistence.getEntityManager();
        TypedQuery<TypeClassType> query = em.createQuery(
                "select e from mobdekbkp$TypeClassType e where e.name = :prmName",
                TypeClassType.class);
        query.setParameter("prmName", name);
        query.setViewName(View.LOCAL);

        TypeClassType typeClassType = query.getFirstResult();
        if (typeClassType != null) {
            return typeClassType;
        } else {
            TypeClassType newTypeClassType = metadata.create(TypeClassType.class);
            newTypeClassType.setName(name);
            em.persist(newTypeClassType);
            return newTypeClassType;
        }
    }

    private void createTree(Map<String, TypeClassShell> mapTypeClassShells) {
        for (Map.Entry<String, TypeClassShell> map : mapTypeClassShells.entrySet()) {
            TypeClassShell typeClassShell = map.getValue();

            typeClassShell.setParent(mapTypeClassShells.get(typeClassShell.parent_id));
        }
    }

    class TypeClassShell {
        public String name;
        public String id;
        public String parent_id;
        TypeClassShell parent;
        List<TypeClassShell> children = new ArrayList<>();

        public void setParent(TypeClassShell parent) {
            this.parent = parent;
            if (parent != null)
                parent.addChildren(this);
        }

        List<TypeClassShell> getChildren() {
            return children;
        }

        void addChildren(TypeClassShell children) {
            this.children.add(children);
        }
    }
}
