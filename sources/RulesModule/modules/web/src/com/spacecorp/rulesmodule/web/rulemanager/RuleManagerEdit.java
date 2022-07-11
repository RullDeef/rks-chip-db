package com.spacecorp.rulesmodule.web.rulemanager;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaModel;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.LookupField;
import com.spacecorp.rulesmodule.entity.RuleManager;
import com.spacecorp.rulesmodule.service.DBExportService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RuleManagerEdit extends AbstractEditor<RuleManager> {
    @Inject
    private DBExportService dbExportService;

    @Inject
    LookupField lookupFieldEntity;
    @Inject
    LookupField lookupFieldRoot;

    @Override
    public void init(Map<String, Object> params) {


        Map mapRoot = new HashMap<String, Object>();
        ArrayList<MetaModel> packagesList = dbExportService.getRootPackages();
        for (MetaModel item : packagesList) {
            mapRoot.put(item.getName(), item);
        }
        lookupFieldRoot.setOptionsMap(mapRoot);
        lookupFieldRoot.addValueChangeListener(value -> {
            //заполнение HashMap сущностями
            Map map = new HashMap<String, Object>();
            MetaModel metaModel = lookupFieldRoot.getValue();
            if (metaModel != null) {
                Object[] mcl = dbExportService.getEntities(metaModel.getName());
                for (Object obj : mcl) {
                    map.put(((MetaClass) obj).getName(), obj);
                }
            }
            lookupFieldEntity.setOptionsMap(map);
        });

        lookupFieldEntity.addValueChangeListener(value->{
            getItem().setEntity(((MetaClass)lookupFieldEntity.getValue()).getName());
        });
    }
}