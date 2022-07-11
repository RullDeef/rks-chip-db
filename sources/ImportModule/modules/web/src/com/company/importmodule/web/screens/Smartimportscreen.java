package com.company.importmodule.web.screens;

import com.company.importmodule.service.DBExportService;
import com.company.importmodule.service.ImportService;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaModel;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.AppConfig;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

public class Smartimportscreen extends AbstractWindow {

    @Inject
    private DBExportService dbExportService;

    @Inject
    private ImportService importService;

    @Inject
    TextField pathField;

    @Inject
    LookupField rootPackageLkup;

    @Inject
    LookupField entityLkup;

    @Inject
    TwinColumn propertyColumn;

    @Inject
    CheckBox findTypeCheck;

    @Inject
    private FileMultiUploadField multiUpload;
    @Inject
    private TextArea fileNamesArea;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private DataSupplier dataSupplier;

    private HashMap<String,FileDescriptor> fileMap;

    private ArrayList<MetaClass> metaClasses;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        multiUpload.addQueueUploadCompleteListener(() -> {
            StringBuilder fileNames=new StringBuilder();
            fileMap=new HashMap<>();
            fileNamesArea.setValue("");
            for(Map.Entry<UUID,String> entry:multiUpload.getUploadsMap().entrySet()){
                UUID fileId = entry.getKey();
                String fileName = entry.getValue();
                FileDescriptor fd=fileUploadingAPI.getFileDescriptor(fileId,fileName);
                try {
                    fileUploadingAPI.putFileIntoStorage(fileId, fd);
                } catch (FileStorageException e) {
                    throw new RuntimeException("Error saving file to FileStorage", e);
                }
                // save file descriptor to database
                dataSupplier.commit(fd);
                fileMap.put(fileName,fd);
                fileNames.append(fileName);
                fileNames.append('\n');
            }
            fileNamesArea.setValue(fileNames);
            multiUpload.clearUploads();
        });

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

    public void onCreateImportTableClick() {
        ArrayList<MetaProperty> propertyList=new ArrayList<>();
        propertyList.addAll(propertyColumn.getValue());
        int findType=0;
        if(findTypeCheck.getValue()){
            findType=1;
        }
        try {
            FileDescriptor fileDescriptor=importService.getImportTable(fileMap,entityLkup.getValue(),propertyList,findType);
            AppConfig.createExportDisplay(this).show(fileDescriptor);
        }
        catch (IOException e){
            //TODO логгирование
        }

    }
}