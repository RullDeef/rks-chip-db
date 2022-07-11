package ru.spacecorp.mobdekbkp.web.typonominal;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.data.HierarchicalDatasource;
import ru.spacecorp.mobdekbkp.entity.Type;
import ru.spacecorp.mobdekbkp.entity.TypeClass;
import ru.spacecorp.mobdekbkp.entity.Typonominal;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TyponominalReportBrowse extends AbstractLookup {
    @Inject
    private GroupDatasource<Typonominal,UUID> typonominalsDs;
    @Inject
    private CollectionDatasource<Type,UUID> typesDs;
    @Inject
    private HierarchicalDatasource<TypeClass,UUID> typeClassesDs;

    @Override
    public void ready() {
        super.ready();
        //Рекурсивный показ всех типов, входящих в Класса ЭКБ
        typeClassesDs.addItemChangeListener(typeClass -> {
            if (typeClass.getItem() != null) {
                List<UUID> childrenList = new ArrayList<>();
                childrenList.clear();
                recDs(childrenList, typeClass.getItem().getId());
                childrenList.add(typeClass.getItem().getId());
                StringBuilder queryTypeClass = new StringBuilder("select e from mobdekbkp$Type e where e.typeClass.id = '");
                StringBuilder queryType = new StringBuilder("select e from mobdekbkp$Typonominal e where e.type.typeClass.id = '");
                boolean isFirstAddition = true;
                for (UUID aChildrenList : childrenList) {
                    if (isFirstAddition) {
                        queryTypeClass.append(aChildrenList.toString()).append("'");
                        queryType.append(aChildrenList.toString()).append("'");
                        isFirstAddition = false;
                    } else {
                        queryTypeClass.append(" or e.typeClass.id = '").append(aChildrenList.toString()).append("'");
                        queryType.append(" or e.type.typeClass.id = '").append(aChildrenList.toString()).append("'");
                    }
                }
                typesDs.setQuery(queryTypeClass.toString() + " order by e.designation");
                typesDs.refresh();
                if (typonominalsDs != null) {
                    typonominalsDs.setQuery(queryType.toString() + "order by e.name");
                    typonominalsDs.refresh();
                    typonominalsDs.setMaxResults(0);
                }
            }
        });

        // Выбор Типа ЭКБ и демонстрация всех изделий ЭКБ
        typesDs.addItemChangeListener(type -> {
            // Основная фильтрация типономиналов
            if (type.getItem() != null) {
                String queryType = "select e from mobdekbkp$Typonominal e where e.type.id = '" + type.getItem().getId() + "'";

                if (typonominalsDs != null) {
                    typonominalsDs.setQuery(queryType);
                    typonominalsDs.refresh();
                    typonominalsDs.setMaxResults(0);
                }
            }
        });

    }
    public void onAllTyponominalBtnClick() {
        typeClassesDs.clear();
        typeClassesDs.setQuery("select e from mobdekbkp$TypeClass e");
        typeClassesDs.refresh();
        typesDs.clear();
        typesDs.setQuery("select e from mobdekbkp$Type e");
        typesDs.refresh();
        typonominalsDs.clear();
        typonominalsDs.setQuery("select e from mobdekbkp$Typonominal e");
        typonominalsDs.refresh();
    }

    private void recDs(List<UUID> list, UUID itemId) {
        if (typeClassesDs.getChildren(itemId) != null) {
            list.addAll(typeClassesDs.getChildren(itemId));
            for (UUID o : typeClassesDs.getChildren(itemId))
                recDs(list, o);
        }
    }
}