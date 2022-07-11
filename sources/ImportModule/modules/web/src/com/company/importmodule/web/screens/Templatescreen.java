package com.company.importmodule.web.screens;

import com.company.importmodule.service.DBExportService;
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

public class Templatescreen extends AbstractWindow {

    @Inject
    DBExportService dbExportService;

    @Inject
    ImportService importService;

    @Inject
    LookupField rootPackageLkup;

    @Inject
    LookupField entityLkup;

    @Inject
    TwinColumn propertyColumn;

    ArrayList<MetaClass> metaClasses;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
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

    public void onCreateTemplateClick() {
        ArrayList<MetaProperty> propertyList=new ArrayList<>();
        propertyList.addAll(propertyColumn.getValue());
        try{
            FileDescriptor fileDescriptor=importService.getInfoFile(entityLkup.getValue(),propertyList);
            AppConfig.createExportDisplay(this).show(fileDescriptor);
        }
        catch (IOException e){
            showNotification("Ошибка создания шаблона",NotificationType.WARNING);
        }
    }
}