package com.company.importmodule.web.screens;

import com.company.importmodule.service.ExportService;
import com.company.importmodule.service.ImportService;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.FileMultiUploadField;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Smallimportscreen extends AbstractWindow {

    @Inject
    ExportService exportService;

    @Inject
    ImportService importService;

    @Inject
    TextField pathField;

    @Inject
    TextArea messageArea;

    @Inject
    TextArea errorArea;

    @Inject
    FileMultiUploadField multiUpload;

    @Inject
    TextArea fileNamesArea;

    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private DataSupplier dataSupplier;

    MetaClass metaClass;
    String parameter;
    HashMap<String,String> defaultValue;
    HashMap<String,String> allowedValue;
    String message;

    private HashMap<String,FileDescriptor> fileMap;

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

        metaClass=(MetaClass) params.get("entity");
        parameter=(String)params.get("parameter");
        defaultValue=(HashMap<String, String>) params.get("defaultValue");
        allowedValue=(HashMap<String,String>)params.get("allowedValue");
        message=(String)params.get("message");
        messageArea.setValue(message);
    }

    public void onTemplateBtnClick() {
        int findType=0;
        String path=pathField.getRawValue();
        if (path.length() == 0) {
            showNotification("?????????????? ???????????????? ????????!", NotificationType.WARNING);
        }
        else {
            try {
                exportService.getExportFileWithParam(metaClass, getPropertyList(metaClass), findType, false, false, path, parameter);
            } catch (Exception e) {
                showNotification("????????????????", "?? ???????????????? ???????????????? ?????????????????? ????????????", NotificationType.WARNING);
                return;
            }
            showNotification("????????????????", "??????????????????. ???????? ???????????????? ???? ???????????????????? ????????????.", NotificationType.HUMANIZED);
        }
    }

    public void onImportBtnClick() {
        String path = pathField.getRawValue();
        if (path.length() == 0) {
            showNotification("?????????????? ???????????????? ????????!", NotificationType.WARNING);
        } else {

            boolean alwaysCreate=false;
            int findById;
            int merge=0;
            findById=0;

            int assocActionType;
            assocActionType=0;

            ArrayList<MetaClass> metaClasses=new ArrayList<>();
            metaClasses.add(metaClass);

            //?????? ???????????? ?????????????? ?????????????????????? ??????????
            ArrayList<String> errorList = importService.ImportDataWithParam(fileMap, metaClass.getName(),
                    metaClasses,findById,merge,assocActionType,alwaysCreate,defaultValue,allowedValue,parameter);
            String temp = "";
            String lineSeparator = System.getProperty("line.separator");
            for (String error : errorList) {
                temp += error;
                temp += lineSeparator;
            }
            if(temp.contentEquals("")){
                temp+="?????????????????? ?????? ????????????";
            }

            String fileWriterError="???????????????? ?? " + path + "\\log.txt";
            if (!temp.contentEquals("")) {
                try {
                    FileWriter fileWriter = new FileWriter(path + "\\log.txt", false);
                    fileWriter.write(temp);
                    fileWriter.close();
                } catch (IOException e) {
                    fileWriterError="???????????? ???????????? ???????? "+e.getMessage();
                }
            }
            errorArea.setValue(temp);
            showNotification("????????????", "??????????????????. ?????????????????? ???????????????????? ???????????????? ?? ????????", NotificationType.HUMANIZED);
        }
    }

    private ArrayList<MetaProperty> getPropertyList(MetaClass metaClass){
        ArrayList<MetaProperty> propertyList=new ArrayList<>(metaClass.getOwnProperties());
        return propertyList;
    }
}