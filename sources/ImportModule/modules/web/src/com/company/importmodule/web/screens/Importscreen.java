package com.company.importmodule.web.screens;

import com.company.importmodule.service.DBExportService;
import com.company.importmodule.service.DeleteService;
import com.company.importmodule.service.ImportService;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaModel;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileLoader;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.AppConfig;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;

import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

import java.io.*;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.MetadataTools;

public class Importscreen extends AbstractWindow {

    private String selected;
    private ArrayList<MetaClass> metaClasses;

    @Inject
    private ImportService importService;

    @Inject
    private DeleteService deleteService;

    @Inject
    private DBExportService dbExportService;

    @Inject
    private com.haulmont.cuba.core.global.Messages messages;

    @Inject
    TextArea errorArea;
    @Inject
    TextArea infoArea;
    @Inject
    TextField searchField;
    @Inject
    LookupField rootPackageLookup;
    @Inject
    TwinColumn twinColumn;
    @Inject
    OptionsGroup actionOptionsGroup;
    @Inject
    OptionsGroup findTypeGroup;
    @Inject
    OptionsGroup analysOptionList;
    @Inject
    CheckBox overwriteCheck;
    //@Inject CheckBox byIdCheck;
    @Inject
    CheckBox alwaysCreateCheck;

    @Inject
    private Metadata metadata;

    @Inject
    private FileLoader fileLoader;

    @Inject
    private FileMultiUploadField multiUpload;
    @Inject
    private TextArea fileNamesArea;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private DataSupplier dataSupplier;

    private HashMap<String, FileDescriptor> fileMap = new HashMap<>();

    @Override
    public void init(Map<String, Object> params) {
        initComponents();
        multiUpload.addQueueUploadCompleteListener(() -> {
            StringBuilder fileNames = new StringBuilder();
            fileMap = new HashMap<>();
            fileNamesArea.setValue("");
            for (Map.Entry<UUID, String> entry : multiUpload.getUploadsMap().entrySet()) {
                UUID fileId = entry.getKey();
                String fileName = entry.getValue();
                FileDescriptor fd = fileUploadingAPI.getFileDescriptor(fileId, fileName);
                try {
                    fileUploadingAPI.putFileIntoStorage(fileId, fd);
                } catch (FileStorageException e) {
                    throw new RuntimeException("Error saving file to FileStorage", e);
                }
                // save file descriptor to database
                dataSupplier.commit(fd);

                String fileNameFix = fileName;
                if (fileNameFix.contains("TypeClass")) {
                    String tempName = fileNameFix.replace("TypeClass", "");
                    tempName = tempName.replace(".xls", "");
                    try {
                        int number = Integer.valueOf(tempName);
                        fileNameFix = "TypeClass.xls";
                    } catch (Exception e) {
                        //do nothing
                    }
                }
                fileMap.put(fileNameFix, fd);
                if (!fileNames.toString().contains(fileNameFix)) {
                    fileNames.append(fileNameFix);
                    fileNames.append('\n');
                }
            }
            fileNamesArea.setValue(fileNames);
            multiUpload.clearUploads();
        });

        twinColumn.addValueChangeListener(e -> {
            Collection<Object> collection = (Collection) e.getValue();
            StringBuilder tempStr = new StringBuilder();
            if (collection != null) {
                for (Object item : collection) {
                    if (!tempStr.toString().contentEquals("")) {
                        tempStr.append(",");
                    }
                    tempStr.append(((MetaClass) item).getName());
                }
            }
            selected = tempStr.toString();
        });

        if (rootPackageLookup == null) {
            showNotification(getMessage("ErrorAdmin"), NotificationType.ERROR);
            return;
        }
        Map<String, Object> mapRoot = new HashMap<>();
        ArrayList<MetaModel> packagesList = dbExportService.getRootPackages();
        for (MetaModel item : packagesList) {
            mapRoot.put(item.getName(), item);
        }
        rootPackageLookup.setOptionsMap(mapRoot);
        rootPackageLookup.addValueChangeListener(value -> {
            //заполнение HashMap сущностями
            setTwinColumn();
        });

        selected = "";
    }

    public void onImportBtnClick() {
        if (fileMap.isEmpty()) {
            showNotification(getMessage("DownloadFiles"), NotificationType.WARNING);
            return;
        }

        if ((metaClasses == null) || (selected.contentEquals(""))) {
            showNotification(getMessage("EnterMetaClassesImport"), NotificationType.WARNING);
            return;
        }

        boolean alwaysCreate = false;
        int findById;
        int merge = 0;
        findById = getFindType();

        if (overwriteCheck.getValue()) {
            merge = 1;
        }
        if (alwaysCreateCheck.getValue()) {
            alwaysCreate = true;
        }

        int assocActionType;
        assocActionType = getActionType();

        //вся логика импорта выполняется здесь
        ArrayList<String> errorList = importService.ImportData(fileMap, selected, metaClasses, findById, merge, assocActionType, alwaysCreate);
        StringBuilder temp = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");
        for (String error : errorList) {
            temp.append(error);
            temp.append(lineSeparator);
        }
        if (temp.toString().contentEquals("")) {
            temp.append("Выполнено без ошибок");
        }
        errorArea.setValue(temp.toString());

        //TODO загрузка лога
        //String fileWriterError = "Записано в " + path + "\\log.txt";
            /*if (!temp.toString().contentEquals("")) {
                try {
                    FileWriter fileWriter = new FileWriter(path + "\\log.txt", false);
                    fileWriter.write(temp.toString());
                    fileWriter.close();
                } catch (IOException e) {
                    fileWriterError = "Ошибка записи лога " + e.getMessage();
                }
            }*/

        showNotification(getMessage("import"), getMessage("CompletedInfoInLog"), NotificationType.HUMANIZED);

    }

    public void onDeleteBtnClick() {
        errorArea.setValue("");
        //ArrayList<String> errorList=null;
        Action act_0 = new DialogAction(DialogAction.Type.YES) {
            @Override
            public void actionPerform(Component component) {
                ArrayList<String> errorList = deleteService.deleteAll(metaClasses);
                StringBuilder temp = new StringBuilder();
                String lineSeparator = System.getProperty("line.separator");
                for (String error : errorList) {
                    temp.append(error);
                    temp.append(lineSeparator);

                }
                errorArea.setValue(temp.toString());

                if (errorList.size() != 0) {
                    showNotification("Удаление", "Ошибка удаления!", NotificationType.ERROR);
                } else {
                    showNotification("Удаление", "Удаление выполнено успешно", NotificationType.HUMANIZED);
                }
            }
        };
        Action act_1 = new DialogAction(DialogAction.Type.NO);
        Action[] act_array = new Action[2];
        act_array[0] = act_0;
        act_array[1] = act_1;
        showOptionDialog("Внимание!", "Вы уверены, что хотите удалить все данные?", MessageType.WARNING, act_array);
    }

    public void onDeleteEntityBtnClick() {
        ArrayList<String> errorList = deleteService.delete(selected, metaClasses);
        //TextArea textArea = (TextArea) getComponent("errorArea");
        StringBuilder temp = new StringBuilder();
        for (String error : errorList) {
            temp.append(error);
            temp.append("\n");

        }
        errorArea.setValue(temp.toString());
        if (errorList.size() == 0) {
            showNotification("Удаление", "Выполнено без ошибок", NotificationType.HUMANIZED);
        } else {
            showNotification("Удаление", "Выполнено с ошибками", NotificationType.WARNING);
        }
    }

    public void onCheckBtnClick() {
        if (fileMap.isEmpty()) {
            showNotification(getMessage("DownloadFilesToCheck"), NotificationType.WARNING);
        } else {
            if (selected.contentEquals("")) {
                showNotification(getMessage("EnterMetaClassesCheck"), NotificationType.WARNING);
            } else {
                int findById = 0;
                findById = getFindType();

                HashMap<String, ArrayList<String>> messageMap = importService.checkExsistence(fileMap, selected, metaClasses, findById);
                StringBuilder temp = new StringBuilder();
                String lineSeparator = System.getProperty("line.separator");
                String analysType = analysOptionList.getValue();
                ArrayList<String> messageList = null;
                if(analysType.contentEquals(getMessage("Duplicates"))){
                    messageList = messageMap.get("exist");
                }
                if(analysType.contentEquals(getMessage("NoConflicts"))){
                    messageList = messageMap.get("notExist");
                }
                /*switch (analysType) {
                    case "Дубликаты": {     //Duplicates
                        messageList = messageMap.get("exist");
                        break;
                    }
                    case "Записи без конфликтов": {
                        messageList = messageMap.get("notExist");
                        break;
                    }
                }*/
                messageList.addAll(messageMap.get("error"));

                for (String message : messageList) {
                    temp.append(message);
                    temp.append(lineSeparator);

                }
                if (messageList.size() == 0) {
                    infoArea.setValue(getMessage("NotFound"));
                } else {
                    infoArea.setValue(temp.toString());
                }

                //TODO загрузка лога
                /*String fileWriterError = "Записано в " + path + "\\log.txt";
                if (!temp.toString().contentEquals("")) {
                    try {
                        FileWriter fileWriter = new FileWriter(path + "\\log.txt", false);
                        fileWriter.write(temp.toString());
                        fileWriter.close();
                    } catch (IOException e) {
                        fileWriterError = "Ошибка записи лога " + e.getMessage();
                    }
                }*/
            }
        }
    }

    private void initComponents() {
        actionOptionsGroup.setOrientation(OptionsGroup.Orientation.VERTICAL);
        ArrayList<String> optionsList = new ArrayList<>();
        //TODO ошибка при создании новой сущности по ключу, разрешить созданиесущности только по InstanceName
        optionsList.add(getMessage("Note"));
        optionsList.add(getMessage("CreateNewEntity"));
        optionsList.add(getMessage("AlwaysCreateNewEntity"));
        actionOptionsGroup.setOptionsList(optionsList);
        actionOptionsGroup.setValue(getMessage("Note"));

        ArrayList<String> findTypeList = new ArrayList<>();
        findTypeList.add(getMessage("FindByName"));
        findTypeList.add(getMessage("FindById"));
        findTypeList.add(getMessage("FindByMainId"));
        findTypeGroup.setOptionsList(findTypeList);
        findTypeGroup.setValue(getMessage("FindByName"));

        ArrayList<String> analysOptionsList = new ArrayList<>();
        analysOptionsList.add(getMessage("Duplicates"));
        analysOptionsList.add(getMessage("NoConflicts"));
        analysOptionList.setOptionsList(analysOptionsList);
        analysOptionList.setValue(getMessage("Duplicates"));
    }

    private boolean nullCheck(Component component) {
        if (component == null) {
            showNotification("Ошибка! Обратитесь к администратору.", NotificationType.ERROR);
            return false;
        } else {
            return true;
        }
    }

    public void onInfoFileBtnClick() {
        ArrayList<String> errorList = new ArrayList<>();
        if (selected.contentEquals("")) {
            showNotification(getMessage("EnterMetaClass"), NotificationType.WARNING);
            return;
        }
        String[] entities = selected.split(",");

        for (MetaClass metaClass : metaClasses) {
            for (String entityName : entities) {
                if (messages.getMessage(metaClass.getJavaClass(), metaClass.getName()).contentEquals(entityName)) {
                    try {
                        com.haulmont.cuba.core.entity.FileDescriptor fileDescriptor = importService.getInfoFile(metaClass, null);
                        AppConfig.createExportDisplay(this).show(fileDescriptor);
                    } catch (IOException e) {
                        showNotification(getMessage("ErrorCreatingTemplate"), NotificationType.WARNING);
                    }
                }
            }
        }
    }

    public void onCustTemplClick() {
        openWindow("templateScreen", WindowManager.OpenType.DIALOG);
    }

    public void onSmartImportClick() {
        openWindow("smartImportScreen", WindowManager.OpenType.DIALOG);
    }

    private int getFindType() {
        String findType = findTypeGroup.getValue();
        if (findType == null) {
            return 0;
        } else {
            String findByName = getMessage("FindByName");
            String findById = getMessage("FindById");
            String findByMainId = getMessage("FindByMainId");

            if(findType.contentEquals(findByName)){
                return 0;
            }
            if(findType.contentEquals(findById)){
                return 1;
            }
            if(findType.contentEquals(findByMainId)){
                return 2;
            }
            return 0;
        }
    }

    private int getActionType() {
        String optionValue = actionOptionsGroup.getValue();
        if (optionValue == null) {
            return 0;
        } else {
            String note = getMessage("Note");
            String create = getMessage("CreateNewEntity");
            String alwaysCreate = getMessage("AlwaysCreateNewEntity");

            if(optionValue.contentEquals(note)){
                return 0;
            }
            if(optionValue.contentEquals(create)){
                return 1;
            }
            if(optionValue.contentEquals(alwaysCreate)){
                return 2;
            }
            return 0;
        }
    }

    public void onMetaModelBtnClick() {

        /*if (pathField.getRawValue().length() == 0) {
            showNotification("Введите значение пути!", NotificationType.WARNING);
            return;
        }*/

        if (rootPackageLookup.getValue() == null) {
            showNotification("Выберите Корневой проект!");
            return;
        }


        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet1 = wb.createSheet("Entities");
            HSSFSheet sheet2 = wb.createSheet("Enumerations");
            String name = "MetaModel";
            //FileOutputStream out = new FileOutputStream(new File(pathField.getRawValue() + "\\" + name + ".xls"));
            //String path = name + ".xls";

            Row row;

            row = sheet1.createRow(0);
            row.createCell(0).setCellValue("Entity");
            row.createCell(1).setCellValue("Entity ru");
            row.createCell(2).setCellValue("Property");
            row.createCell(3).setCellValue("Property ru");
            row.createCell(4).setCellValue("Type");
            row.createCell(5).setCellValue("Cardinality");
            row.createCell(6).setCellValue("Mandatory");
            row.createCell(7).setCellValue("Readonly");
            row.createCell(8).setCellValue("Annotations");

            row = sheet2.createRow(0);
            row.createCell(0).setCellValue("Enumeration");
            row.createCell(1).setCellValue("Value");
            row.createCell(2).setCellValue("Value ru");


            MetaModel metaModel = rootPackageLookup.getValue();

            // Entities
            Object[] mcl = dbExportService.getEntities(metaModel.getName());
            int j = 1;
            for (Object obj : mcl) {

                MetaClass metaClass = (MetaClass) obj;

                for (MetaProperty mpr : metaClass.getProperties()) {
                    row = sheet1.createRow(j);

                    // Entity
                    row.createCell(0).setCellValue(metaClass.getName());

                    // Entity ru
                    String temp[] = metaClass.getName().split("\\$");
                    String metaClassName = messages.getMessage(metaClass.getJavaClass(), temp[temp.length - 1]);
                    row.createCell(1).setCellValue(metaClassName);

                    // Property
                    row.createCell(2).setCellValue(mpr.getName());

                    // Property ru
                    String tempName = mpr.getName();
                    String[] tempName2 = (metaClass.getName() + "." + tempName).split("\\$");
                    String localName = messages.getMessage(metaClass.getJavaClass(), tempName2[tempName2.length - 1]);
                    row.createCell(3).setCellValue(localName);

                    // Type
                    row.createCell(4).setCellValue(mpr.getJavaType().getName());

                    // Cardinality
                    row.createCell(5).setCellValue(mpr.getRange().getCardinality().toString());

                    // Mandatory
                    row.createCell(6).setCellValue(mpr.isMandatory());

                    // Readonly
                    row.createCell(7).setCellValue(mpr.isReadOnly());

                    // Annotations
                    row.createCell(8).setCellValue(mpr.getAnnotations().toString());

                    j++;
                }
            }

            // Enumerations
            MetadataTools tools = metadata.getTools();
            j = 1;
            for (Class enumClass : tools.getAllEnums()) {
                for (Object con : enumClass.getEnumConstants()) {
                    row = sheet2.createRow(j);

                    // Enumeration
                    row.createCell(0).setCellValue(enumClass.getName());

                    // Value
                    row.createCell(1).setCellValue(((Enum) con).name());

                    // Value ru
                    String[] enumName = enumClass.getName().split("\\.");
                    String enumLocalName = messages.getMessage(((Enum) con).getClass(), enumName[enumName.length - 1] + "." + ((Enum) con).name());
                    row.createCell(2).setCellValue(enumLocalName);

                    j++;
                }
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                wb.write(byteArrayOutputStream);
            } finally {
                byteArrayOutputStream.close();
            }
            byte[] bytes = byteArrayOutputStream.toByteArray();
            com.haulmont.cuba.core.entity.FileDescriptor fileDescriptor = metadata.create(com.haulmont.cuba.core.entity.FileDescriptor.class);
            fileDescriptor.setName(name + ".xls");
            fileDescriptor.setExtension("xls");
            fileDescriptor.setSize((long) bytes.length);
            fileDescriptor.setCreateDate(new Date());
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            hssfWorkbook.getBytes();
            try {
                fileLoader.saveStream(fileDescriptor, () -> new ByteArrayInputStream(bytes));
            } catch (FileStorageException e) {
                throw new RuntimeException("Error loading file " + e);
            }

            AppConfig.createExportDisplay(this).show(fileDescriptor);

        } catch (IOException e) {
            //TODO логгирование
            showNotification("Ошибка создания модели", NotificationType.WARNING);
        }
    }

    public void onSearchBtnClick() {
        Object value = twinColumn.getValue();
        setTwinColumn();
        if (searchField.getValue() != null) {
            String searchStr = searchField.getValue().toString().toUpperCase();
            Map<String, MetaClass> map = (Map) twinColumn.getOptionsMap();
            if (map != null) {
                ArrayList<MetaClass> curValues = new ArrayList<>((Collection) value);
                map.entrySet().removeIf(stringMetaClassEntry -> {
                            if (curValues.contains(stringMetaClassEntry.getValue())) {
                                return false;
                            }
                            return !stringMetaClassEntry.getKey().toUpperCase().contains(searchStr);
                        }
                );
                twinColumn.setOptionsMap(map);
            }
        }
        twinColumn.setValue(value);
    }

    private void setTwinColumn() {
        //заполнение HashMap сущностями
        //Map map=new HashMap<String,Object>();
        MetaModel metaModel = rootPackageLookup.getValue();
        Map<String, MetaClass> map = new HashMap<>();
        if (metaModel != null) {
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
        twinColumn.setOptionsMap(map);
    }
}