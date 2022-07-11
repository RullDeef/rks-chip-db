package com.company.importmodule.web.screens;

import com.company.importmodule.service.DBExportService;
import com.company.importmodule.service.ExportService;
import com.company.importmodule.service.ImportService;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaModel;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.AppConfig;
import com.haulmont.cuba.gui.components.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

public class Exportscreen extends AbstractWindow {

    @Inject
    private
    DBExportService dbExportService;

    @Inject
    private
    ExportService exportService;

    @Inject
    LookupField rootPackageLkup;

    @Inject
    LookupField entityLkup;

    @Inject
    TwinColumn propertyColumn;

    @Inject
    CheckBox findTypeCheck;

    @Inject
    CheckBox typonominalLinkCheck;

    @Inject
    TextField pathField;

    @Inject
    OptionsGroup exportOptions;

    private ArrayList<MetaClass> metaClasses;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        ArrayList<String> optionsList=new ArrayList<>();
        optionsList.add("Не используется");
        optionsList.add(messages.getMessage(getClass(),"Key_and_name"));
        optionsList.add("Экспорт со справочниками");
        exportOptions.setOptionsList(optionsList);
        exportOptions.setValue("Не используется");
        Map mapRoot=new HashMap<String,Object>();
        ArrayList<MetaModel> packagesList=dbExportService.getRootPackages();
        for (MetaModel item: packagesList) {
            mapRoot.put(item.getName(),item);
        }
        rootPackageLkup.setOptionsMap(mapRoot);
        rootPackageLkup.addValueChangeListener(value->{
            //заполнение HashMap сущностями
            //Map map=new HashMap<String,Object>();
            MetaModel metaModel=rootPackageLkup.getValue();
            Map map = new HashMap();
            if(metaModel!=null) {
                //Object[] mcl = dbExportService.getEntities();
                Object[] mcl = dbExportService.getEntities(metaModel.getName());
                metaClasses = new ArrayList<>();

                for (Object obj : mcl) {
                    String regex = "\\$";
                    String temp[] = ((MetaClass) obj).getName().split(regex);
                    metaClasses.add((MetaClass) obj);
                    String metaClassName = messages.getMessage(((MetaClass) obj).getJavaClass(), temp[temp.length - 1]);
                    //map.put(temp[temp.length-1],(MetaClass)obj);
                    map.put(metaClassName, (MetaClass) obj);
                }
            }
            entityLkup.setOptionsMap(map);
        });

        entityLkup.addValueChangeListener(e -> {
            Collection<MetaProperty> metaProperties=((MetaClass)e.getValue()).getOwnProperties();
            HashMap<String,MetaProperty> propertyHashMap = new HashMap<>();
            propertyHashMap.put("id",((MetaClass)e.getValue()).getProperty("id"));
            for(MetaProperty metaProperty:metaProperties){
                propertyHashMap.put(metaProperty.getName(),metaProperty);
            }
            propertyColumn.setOptionsMap(propertyHashMap);
        });
    }


    public void onExportBtnClick() {
        ArrayList<MetaProperty> propertyList = new ArrayList<>();
        propertyList.addAll(propertyColumn.getValue());
        int findType=0;
        if(findTypeCheck.getValue()) {
            findType =1;
        }
        boolean keyAndName=false;
        boolean withDict=false;
        String typonominalLink="";
        if(exportOptions.getValue().toString().contentEquals("Экспорт ссылок по ключу и имени")){
            keyAndName=true;
        }
        if(exportOptions.getValue().toString().contentEquals("Экспорт со справочниками")){
            withDict=true;
        }
        if(typonominalLinkCheck.getValue()){
            typonominalLink="typonominal";
        }
        try {
            FileDescriptor fileDescriptor=exportService.getExportFileWithParam(entityLkup.getValue(),propertyList,findType,keyAndName,withDict,
                    "",typonominalLink);
            AppConfig.createExportDisplay(this).show(fileDescriptor);
        }
        catch (IOException e){
            //TODO логгирование
            e.printStackTrace();
        }
    }
}