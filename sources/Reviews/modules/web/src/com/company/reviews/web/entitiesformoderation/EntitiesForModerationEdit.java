package com.company.reviews.web.entitiesformoderation;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.reviews.entity.EntitiesForModeration;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.Window;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class EntitiesForModerationEdit extends AbstractEditor<EntitiesForModeration> {

    @Inject
    private Metadata metadata;

    @Inject
    private PickerField entityPicker;

    @Inject
    private DataManager dataManager;

    private MetaClass metaClass;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        if(params.get("entity")!=null) {
            metaClass = metadata.getClass(params.get("entity").toString());
            entityPicker.setMetaClass(metaClass);
            entityPicker.addValueChangeListener(e -> {
                getItem().setEntityId((UUID) ((Entity) e.getValue()).getId());
            });
        }
    }

    @Override
    protected void postInit() {
        super.postInit();
        if(metaClass!=null) {
            if(getItem().getEntityId()!=null) {
                LoadContext loadContext = LoadContext.create(metaClass.getJavaClass());
                loadContext.setQuery(LoadContext.createQuery(String.format("select e from %s e where e.id='%s'", metaClass.getName(), getItem().getEntityId())));
                Entity entity = dataManager.load(loadContext);
                entityPicker.setValue(entity);
            }
        }
    }

    @Override
    public boolean commit() {
        return super.commit();
    }

    @Override
    public void commitAndClose() {
        close(Window.COMMIT_ACTION_ID,true);
    }
}