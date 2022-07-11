package com.spacecorp.rulesmodule.web.ruledatascript;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaModel;
import com.haulmont.cuba.core.global.ViewRepository;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.SourceCodeEditor;
import com.spacecorp.rulesmodule.entity.RuleDataScript;
import com.spacecorp.rulesmodule.service.DBExportService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RuleDataScriptEdit extends AbstractEditor<RuleDataScript> {
    @Inject
    DBExportService dbExportService;

    @Inject
    ViewRepository  viewRepository;

    @Inject
    SourceCodeEditor sourceCodeEditor;

    @Inject
    LookupField lookupFieldView;

    @Override
    public void init(Map<String, Object> params) {
        sourceCodeEditor.setHandleTabKey(true);
        sourceCodeEditor.setHighlightActiveLine(true);
        sourceCodeEditor.setMode(SourceCodeEditor.Mode.Groovy);
        sourceCodeEditor.addValueChangeListener(e -> getItem().setSql(sourceCodeEditor.getValue()));

        LookupField lookupFieldEntity = (LookupField) getComponent("lookupFieldEntity");
        LookupField lookupFieldRoot = (LookupField) getComponent("lookupFieldRoot");

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

        lookupFieldEntity.addValueChangeListener(value -> {
            Map map=new HashMap<String,Object>();
            Collection<String> collection=viewRepository.getViewNames((MetaClass) lookupFieldEntity.getValue());
            for(String viewName:collection){
                map.put(viewName,viewName);
            }
            lookupFieldView.setOptionsMap(map);
        });

        lookupFieldEntity.addValueChangeListener(value->{
            getItem().setEntity(((MetaClass)lookupFieldEntity.getValue()).getName());
        });

        lookupFieldView.addValueChangeListener(value ->{
            getItem().setView(lookupFieldView.getValue());
        });
    }

    @Override
    public void postInit() {
        sourceCodeEditor.setValue(getItem().getSql());
    }
}

