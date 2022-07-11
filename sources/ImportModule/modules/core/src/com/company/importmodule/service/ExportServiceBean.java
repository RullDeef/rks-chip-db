package com.company.importmodule.service;

import com.company.importmodule.core.Import_work;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.chile.core.model.Range;
import com.haulmont.cuba.core.app.ConfigStorageService;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.*;
import java.util.*;

@Service(ExportService.NAME)
public class ExportServiceBean implements ExportService {

    @Inject
    private Import_work import_work;

    @Inject
    private Messages messages;

    @Inject
    private ConfigStorageService configStorageService;

    @Inject
    private FileLoader fileLoader;

    @Inject
    private Metadata metadata;

    private static final String openTnCard  = "/open?screen=tn-card-screen&params=typonominalId:";

    private String serverAddr="";

    @Override
    public FileDescriptor getExportFileWithParam(MetaClass metaClass, ArrayList<MetaProperty> selectedProperties, int findType,
                                         boolean keyAndName, boolean withDict, String parameter) throws IOException {
        return getExportFileWithParam(metaClass, selectedProperties, findType, keyAndName, withDict, parameter, "");
    }

    @Override
    public FileDescriptor getExportFileWithParam(MetaClass metaClass, ArrayList<MetaProperty> selectedProperties, int findType,
                                boolean keyAndName, boolean withDict, String parameter,
                                         String additParam) throws IOException {
        String[] temp=metaClass.getName().split("\\$");
        String name=temp[temp.length-1];
        HashMap<String,Integer> propertyMap=new HashMap<>();
        HSSFWorkbook wb= import_work.getTemplateWorkbook(metaClass,selectedProperties,propertyMap);

        if (!withDict) {
            wb = addDataToWorkbook(wb, metaClass, selectedProperties, propertyMap, findType, keyAndName, parameter, additParam);
        } else {
            wb = addDataToWorkbookWithDictionaries(wb, metaClass, selectedProperties, propertyMap);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            wb.write(byteArrayOutputStream);
        }
        finally {
            byteArrayOutputStream.close();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        FileDescriptor fileDescriptor = metadata.create(FileDescriptor.class);
        fileDescriptor.setName(name+".xls");
        fileDescriptor.setExtension("xls");
        fileDescriptor.setSize((long) bytes.length);
        fileDescriptor.setCreateDate(new Date());
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        hssfWorkbook.getBytes();
        try {
            fileLoader.saveStream(fileDescriptor, () -> new ByteArrayInputStream(bytes));
        } catch (FileStorageException e) {
            throw new RuntimeException("Error loading file "+e);
        }
        return fileDescriptor;
    }



    private HSSFWorkbook addDataToWorkbook(HSSFWorkbook wb,MetaClass metaClass,
                                           ArrayList<MetaProperty> selectedProperties,HashMap<String,Integer> propertyMap,
                                           int findType,boolean keyAndName){
        ArrayList<View> views=new ArrayList<>();
        views.add(import_work.getFullView(metaClass));
        String table=metaClass.getName();
        Collection entityCollection=import_work.getAllEntitiesFromBD(metaClass,table,views);
        HSSFSheet sheet=wb.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        int rowNum=3;
        Row row=sheet.createRow(rowNum);
        for(Object obj:entityCollection){
            for(MetaProperty metaProperty:selectedProperties){
                Object o=((Entity)obj).getValue(metaProperty.getName());
                String propertyValue="";
                if(metaProperty.getType()== MetaProperty.Type.ENUM){
                    if(o!=null) {
                        String temp = ((Enum) o).getClass().getName();
                        String[] enumName = ((Enum) o).getClass().getName().split("\\.");
                        String enumLocalName = messages.getMessage(((Enum) o).getClass(), enumName[enumName.length - 1] + "." + ((Enum) o).name());
                        propertyValue = enumLocalName;
                    }
                }
                else {
                    try {
                        Entity propEntity = (Entity) o;
                        if (keyAndName) {
                            propertyValue = propEntity.getId().toString() + " " + propEntity.getInstanceName();
                        } else {
                            if (findType == 1) {
                                propertyValue = propEntity.getId().toString();
                            } else {
                                propertyValue = propEntity.getInstanceName();
                            }
                        }
                    } catch (Exception e) {
                        propertyValue = String.valueOf(o);
                    }
                }
                String[] key = (metaClass.getName() + "." + metaProperty.getName()).split("\\$");
                String localName=messages.getMessage(metaClass.getJavaClass(),key[key.length - 1]);
                int cellNum=propertyMap.get(localName);
                //row.getCell(cellNum).setCellValue(propertyValue);
                row.createCell(cellNum).setCellValue(propertyValue);
            }
            ++rowNum;
            row=sheet.createRow(rowNum);
        }
        return wb;
    }

    //добавление данных в книгу excel
    //parameter - выражение WHERE для выгружаемой сущности
    //findType - экспорт внешних сущностей по id или по InstanceName (1 - по id, остальное - InstanceName)
    //keyAndName - экспорт по InstanceName и id одновременно
    private HSSFWorkbook addDataToWorkbook(HSSFWorkbook wb,MetaClass metaClass,
                                           ArrayList<MetaProperty> selectedProperties,HashMap<String,Integer> propertyMap,
                                           int findType,boolean keyAndName, String parameter, String additParam){
        ArrayList<View> views=new ArrayList<>();
        views.add(import_work.getFullView(metaClass));
        String table=metaClass.getName();
        Collection entityCollection=import_work.getAllEntitiesFromBD(metaClass,table,views,parameter);
        HSSFSheet sheet=wb.getSheetAt(0);
        int rowNum=3;
        Row row=sheet.createRow(rowNum);
        for(Object obj:entityCollection){
            for(MetaProperty metaProperty:selectedProperties){
                if((metaProperty.getRange().getCardinality()== Range.Cardinality.MANY_TO_MANY) || (metaProperty.getRange().getCardinality()== Range.Cardinality.ONE_TO_MANY)){
                    continue;
                }
                Object o=((Entity)obj).getValue(metaProperty.getName());
                String propertyValue="";
                if(metaProperty.getType()== MetaProperty.Type.ENUM){
                    if(o!=null) {
                        String temp = ((Enum) o).getClass().getName();
                        String[] enumName = ((Enum) o).getClass().getName().split("\\.");
                        String enumLocalName = messages.getMessage(((Enum) o).getClass(), enumName[enumName.length - 1] + "." + ((Enum) o).name());
                        propertyValue = enumLocalName;
                    }
                }
                else {
                    try {
                        Entity propEntity = (Entity) o;
                        if (keyAndName) {
                            propertyValue = propEntity.getId().toString() + " " + propEntity.getInstanceName();
                        } else {
                            if (findType == 1) {
                                propertyValue = propEntity.getId().toString();
                            } else {
                                propertyValue = propEntity.getInstanceName();
                            }
                        }
                    } catch (Exception e) {
                            if(o!=null) {
                                propertyValue = o.toString();
                            }
                    }
                }
                String[] key = (metaClass.getName() + "." + metaProperty.getName()).split("\\$");
                String localName=messages.getMessage(metaClass.getJavaClass(),key[key.length - 1]);
                int cellNum=propertyMap.get(localName);
                //row.getCell(cellNum).setCellValue(propertyValue);
                row.createCell(cellNum).setCellValue(propertyValue);
            }
            /*List<AppPropertyEntity> list=configStorageService.getAppProperties();
            list.forEach(appPropertyEntity -> {
                if(appPropertyEntity.getName().contentEquals("cuba.webAppUrl")){
                    //serverAddr= appPropertyEntity.getCurrentValue();
                    //TODO проработать вопрос получения имени хоста
                    //использовать свойсто приложения?
                    //получить средствами Java?

                }
            });*/
            serverAddr="http://toquads:8080/app";
            if(additParam.contentEquals("typonominal")&&(metaClass.getName().contentEquals("mobdekbkp$Typonominal"))){
                String url=serverAddr+openTnCard;
                url+=((Entity)obj).getId().toString();
                row.createCell(propertyMap.get("row_maxVal")).setCellValue(url);
            }
            ++rowNum;
            row=sheet.createRow(rowNum);
        }
        return wb;
    }

    private HSSFWorkbook addDataToWorkbookWithDictionaries(HSSFWorkbook wb,MetaClass metaClass,
                                                           ArrayList<MetaProperty> selectedProperties,HashMap<String,Integer> propertyMap) {
        ArrayList<View> views = new ArrayList<>();
        views.add(import_work.getFullView(metaClass));
        String table = metaClass.getName();
        Collection entityCollection = import_work.getAllEntitiesFromBD(metaClass, table, views);
        HSSFSheet sheet = wb.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        int rowNum = 3;
        Row row = sheet.createRow(rowNum);
        for (Object obj : entityCollection) {
            for (MetaProperty metaProperty : selectedProperties) {
                Object o = ((Entity) obj).getValue(metaProperty.getName());
                String propertyValue = "";
                if (metaProperty.getType() == MetaProperty.Type.ENUM) {
                    if (o != null) {
                        String temp = ((Enum) o).getClass().getName();
                        String[] enumName = ((Enum) o).getClass().getName().split("\\.");
                        String enumLocalName = messages.getMessage(((Enum) o).getClass(), enumName[enumName.length - 1] + "." + ((Enum) o).name());
                        propertyValue = enumLocalName;
                    }
                } else {
                    try {
                        Entity propEntity = (Entity) o;
                        propertyValue = propEntity.getId().toString();

                        /*Sheet tempSheet=wb.getSheet(propEntity.getMetaClass().getName());
                        if(tempSheet==null) {
                            wb.createSheet(propEntity.getMetaClass().getName());
                        }*/
                        ArrayList<MetaProperty> metaPropertyList=new ArrayList<>();
                        metaPropertyList.addAll(propEntity.getMetaClass().getOwnProperties());
                        HashMap<String,Integer> tempMap=new HashMap<>();
                        import_work.addTemplateToWorkbook(wb,propEntity.getMetaClass(),metaPropertyList,tempMap);
                        addOneEntityToWorkbook(wb,propEntity.getMetaClass(),propEntity,rowNum);
                    } catch (Exception e) {
                        propertyValue = String.valueOf(o);
                    }
                }
                String[] key = (metaClass.getName() + "." + metaProperty.getName()).split("\\$");
                String localName = messages.getMessage(metaClass.getJavaClass(), key[key.length - 1]);
                int cellNum = propertyMap.get(localName);
                //row.getCell(cellNum).setCellValue(propertyValue);
                row.createCell(cellNum).setCellValue(propertyValue);
            }
            ++rowNum;
            row = sheet.createRow(rowNum);
        }
        return wb;
    }

    private HSSFWorkbook addOneEntityToWorkbook(HSSFWorkbook wb, MetaClass metaClass, Entity entity, int rowNum){
        ArrayList<MetaProperty> metaPropertyList=new ArrayList<>();
        metaPropertyList.addAll(metaClass.getOwnProperties());
        Sheet sheet=wb.getSheet(metaClass.getName());
        Row row=sheet.createRow(rowNum);
        for(int i=0;i<metaPropertyList.size();++i){
            String propertyValue="";
            Cell cell=row.createCell(i);
            Object o=entity.getValue(metaPropertyList.get(i).getName());
            try {
                Entity propEntity = (Entity) o;
                propertyValue = propEntity.getInstanceName();
            } catch (Exception e) {
                propertyValue = String.valueOf(o);
            }
            cell.setCellValue(propertyValue);
        }
        return wb;
    }



}

