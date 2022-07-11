package com.company.reviews.web.moderationproperty;

import com.company.reviews.entity.EntitiesForModeration;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaModel;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.ViewRepository;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.reviews.entity.ModerationProperty;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModerationPropertyEdit extends AbstractEditor<ModerationProperty> {

    @Inject
    LookupField packageLookup;

    @Inject
    LookupField entityLookup;

    @Inject
    private Metadata metadata;

    @Inject
    private Table<EntitiesForModeration> entityTable;

    @Inject
    private DataManager dataManager;

    @Inject
    private Datasource<ModerationProperty> moderationPropertyDs;

    @Inject
    private CollectionDatasource selectedEntitiesDs;

    @Inject
    ViewRepository viewRepository;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        //заполнение лукапа пакетов
        Map mapRoot = new HashMap<String, Object>();
        ArrayList<MetaModel> packagesList = getRootPackages();
        for (MetaModel item : packagesList) {
            mapRoot.put(item.getName(), item);
        }
        packageLookup.setOptionsMap(mapRoot);
        //заполнение лукапа пакетов

        packageLookup.addValueChangeListener(value -> {
            getItem().setEntity(null);
            //заполнение лукапа сущностями
            MetaModel metaModel = packageLookup.getValue();
            Map<String,String> map = new HashMap();
            if (metaModel != null) {
                Object[] mcl = getEntities(metaModel.getName());
                for (Object obj : mcl) {
                    String regex = "\\$";
                    String temp[] = ((MetaClass) obj).getName().split(regex);
                    String metaClassName = messages.getMessage(((MetaClass) obj).getJavaClass(), temp[temp.length - 1]);
                    map.put(metaClassName, ((MetaClass) obj).getName());
                }
            }
            entityLookup.setOptionsMap(map);
            //заполнение лукапа сущностями
        });
        entityLookup.addValueChangeListener(e -> {
            setEnityForModerationParams();
        });
    }

    @Override
    protected void postInit() {
        super.postInit();
        if(moderationPropertyDs.getItem().getEntity()!=null) {
            MetaClass metaClass = metadata.getClass(moderationPropertyDs.getItem().getEntity());
            String regex = "\\$";
            String temp[] = (metaClass.getName().split(regex));
            String metaClassName = messages.getMessage(metaClass.getJavaClass(), temp[temp.length - 1]);

            HashMap<String, String> map = new HashMap<>();
            map.put(metaClassName, moderationPropertyDs.getItem().getEntity());
            entityLookup.setOptionsMap(map);

            setEnityForModerationParams();
        }
    }

    private Object[] getEntities(String packageName){
        MetaModel metmod=metadata.getModel(packageName);
        return metmod.getClasses().toArray();
    }

    //получение названий модулей
    private ArrayList<MetaModel> getRootPackages()
    {
        return new ArrayList(metadata.getModels());
    }

    private void setEnityForModerationParams(){
        HashMap<String,Object> paramsMap=new HashMap<>();
        paramsMap.put("entity",getItem().getEntity());
        CreateAction createAction=(CreateAction) entityTable.getAction("create");
        createAction.setWindowParams(paramsMap);
        EditAction editAction=(EditAction) entityTable.getAction("edit");
        editAction.setWindowParams(paramsMap);
    }

    @Override
    protected boolean preCommit() {
        if(getItem().getSelectedEntities()!=null) {
            List<EntitiesForModeration> entitiesForModerations= getItem().getSelectedEntities();
            if (entitiesForModerations.size()!=0){
                selectedEntitiesDs.setAllowCommit(false);
            }
            for (Entity o : entitiesForModerations) {
                o.setValue("moderationProperty", null);
                dataManager.commit(o);
            }
        }
        return super.preCommit();
    }

    @Override
    protected boolean postCommit(boolean committed, boolean close) {
        if(getItem().getSelectedEntities()!=null) {
            ArrayList<EntitiesForModeration> entities = new ArrayList<>(getItem().getSelectedEntities());
            for (int i = 0; i < entities.size(); ++i) {
                EntitiesForModeration entitiesForModeration=dataManager.reload(entities.get(i),
                        viewRepository.getView(EntitiesForModeration.class, "entitiesForModeration-view"));
                entitiesForModeration.setValue("moderationProperty", getItem());
                entities.set(i, entitiesForModeration);
            }
            CommitContext commitContext = new CommitContext(entities);
            dataManager.commit(commitContext);
        }
        return super.postCommit(true, true);
    }
}