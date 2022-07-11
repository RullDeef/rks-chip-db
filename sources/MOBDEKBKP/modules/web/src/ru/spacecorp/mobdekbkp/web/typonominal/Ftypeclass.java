package ru.spacecorp.mobdekbkp.web.typonominal;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.HierarchicalDatasource;
import ru.spacecorp.mobdekbkp.entity.Type;
import ru.spacecorp.mobdekbkp.entity.TypeClass;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Ftypeclass extends AbstractWindow {
    @Inject
    private HierarchicalDatasource<TypeClass, UUID> typeClassesDs;
    @Inject
    private CollectionDatasource<Type, UUID> typesDs;
    private String queryTypeRequest;

    @Override
    public void ready() {
        super.ready();
        typeClassesDs.addItemChangeListener(typeClass -> {
            if (typeClass.getItem() != null) {
                List<UUID> childrenList = new ArrayList<>();
                childrenList.clear();
                recDs(childrenList, typeClass.getItem().getId());
                childrenList.add(typeClass.getItem().getId());
                StringBuilder queryTypeClass = new StringBuilder("select e from mobdekbkp$Type e where e.typeClass.id = '");
                StringBuilder queryType = new StringBuilder("select e from mobdekbkp$Typonominal e where e.type.typeClass.id = '");
                boolean isFirstAddition = true;
                for (int j = 0; j < childrenList.size(); j++) {
                    if (isFirstAddition) {
                        queryTypeClass.append(childrenList.get(j).toString()).append("'");
                        queryType.append(childrenList.get(j).toString()).append("'");
                        isFirstAddition = false;
                    } else {
                        queryTypeClass.append(" or e.typeClass.id = '").append(childrenList.get(j).toString()).append("'");
                        queryType.append(" or e.type.typeClass.id = '").append(childrenList.get(j).toString()).append("'");
                    }
                }
                typesDs.setQuery(queryTypeClass.toString() + " order by e.designation");
                typesDs.refresh();
                queryTypeRequest = queryType.toString() + " order by e.name";

            }
        });
    }

    private void recDs(List<UUID> list, UUID itemId) {
        if (typeClassesDs.getChildren(itemId) != null) {
            list.addAll(typeClassesDs.getChildren(itemId));
            for (UUID o : typeClassesDs.getChildren(itemId))
                recDs(list, o);
        }
    }

    public void onCancelClick() {
        this.close("exit");
    }

    public void onOkClick() {
        if (typesDs.getItem() != null) {
            this.close("exit");
        } else {
            if (typeClassesDs.getItem() != null) {
                this.close("exit");
            } else {
                showNotification("Данные не выбраны!");
            }
        }
    }

    public Type getType() {
        return typesDs.getItem();
    }

    public String getTypeClassRequset() {
        return queryTypeRequest;
    }

    public TypeClass getTypeClass() {
        return typeClassesDs.getItem();
    }
}