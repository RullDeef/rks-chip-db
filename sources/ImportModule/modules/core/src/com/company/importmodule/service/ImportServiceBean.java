package com.company.importmodule.service;

import com.company.importmodule.core.ExelWork;
import com.company.importmodule.core.Import_work;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.entity.*;
import com.haulmont.cuba.core.global.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import com.haulmont.cuba.core.entity.FileDescriptor;

import javax.inject.Inject;
import java.io.*;
import java.util.*;

@Service(ImportService.NAME)
public class ImportServiceBean implements ImportService {

    @Inject
    private Import_work import_work;

    @Inject
    private Persistence persistence;

    @Inject
    private Messages messages;

    @Inject
    private Metadata metadata;

    @Inject
    private FileLoader fileLoader;

    @Override
    public ArrayList<String> ImportData(HashMap<String,FileDescriptor> fileMap, String selected/*имена записей через запятую*/,
                                        ArrayList<MetaClass> metaClasses/*соответствующие метаклассы*/,int find,int merge, int assocActionType,boolean alwaysCreate)
    {
        ArrayList<String> errorList=new ArrayList<>();
        String[] entities=selected.split(",");

        for(MetaClass metaClass:metaClasses) {
            for(String entityName:entities) {
                import_work.createDataHolder();
                if (messages.getMessage(metaClass.getJavaClass(),metaClass.getName()).contentEquals(entityName)) {
                    errorList.addAll(import_work.smartImportEntity(fileMap, metaClass,find,merge,assocActionType,alwaysCreate));
                }
            }
        }
        return errorList;
    }

    @Override
    public ArrayList<String> ImportDataWithParam(HashMap<String,FileDescriptor> fileMap, String selected, ArrayList<MetaClass> metaClasses, int find, int merge,
                                                 int assocActionType, boolean alwaysCreate,
                                                 HashMap<String,String> defaultValue, HashMap<String, String> allowedValue,String parameter) {
        ArrayList<String> errorList=new ArrayList<>();
        String[] entities=selected.split(",");

        for(MetaClass metaClass:metaClasses) {
            for(String entityName:entities) {
                import_work.createDataHolder();
                if (messages.getMessage(metaClass.getJavaClass(),metaClass.getName()).contentEquals(entityName)) {
                    errorList.addAll(import_work.smartImportEntityWithParam(fileMap, metaClass,find,merge,assocActionType,alwaysCreate,defaultValue,allowedValue,parameter));
                }
            }
        }
        return errorList;
    }

    @Override
    public FileDescriptor getInfoFile(MetaClass metaClass,ArrayList<MetaProperty> selectedProperties) throws IOException{
        String[] temp=metaClass.getName().split("\\$");
        String name=temp[temp.length-1];
        HSSFWorkbook wb=import_work.getTemplateWorkbook(metaClass,selectedProperties,null);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            wb.write(byteArrayOutputStream);
        }
        finally {
            byteArrayOutputStream.close();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        com.haulmont.cuba.core.entity.FileDescriptor fileDescriptor = metadata.create(com.haulmont.cuba.core.entity.FileDescriptor.class);
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

    @Override
    public HashMap<String,ArrayList<String>> checkExsistence(HashMap<String,FileDescriptor> fileMap, String selected, ArrayList<MetaClass> metaClasses,int findType)
    {
        HashMap<String,ArrayList<String>> messageMap=new HashMap<>();
        ArrayList<String> errorList=new ArrayList<>();
        ArrayList<String> existList=new ArrayList<>();
        ArrayList<String> notExsistList=new ArrayList<>();
        String[] entities=selected.split(",");

        for(MetaClass metaClass:metaClasses) {
            for(String entityName:entities) {
                import_work.createDataHolder();
                if (messages.getMessage(metaClass.getJavaClass(),metaClass.getName()).contentEquals(entityName)) {
                    errorList.addAll(import_work.checkAllEntities(fileMap, metaClass,existList,notExsistList,findType));
                }
            }
        }
        messageMap.put("error",errorList);
        messageMap.put("exist",existList);
        messageMap.put("notExist",notExsistList);
        return messageMap;
    }

    @Override
    public FileDescriptor getImportTable(HashMap<String,FileDescriptor> fileMap, MetaClass metaClass, ArrayList<MetaProperty> selectedProperties, int findType) throws IOException{

        ArrayList<String> errorList=new ArrayList<>();

        HashMap<String,Integer> propertyPosMap=new HashMap<>();
        HSSFWorkbook wb=import_work.getTemplateWorkbook(metaClass,selectedProperties,propertyPosMap);

        CellStyle cellStyleYellow=wb.createCellStyle();
        cellStyleYellow.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyleYellow.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle cellStyleGreen=wb.createCellStyle();
        cellStyleGreen.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyleGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle cellStyleBlue=wb.createCellStyle();
        cellStyleBlue.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        cellStyleBlue.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle cellStyleRed=wb.createCellStyle();
        cellStyleRed.setFillForegroundColor(HSSFColor.RED.index);
        cellStyleRed.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        int curRow=3;
        String[] temp = metaClass.getName().split("\\$");
        String entityName = temp[temp.length - 1];
        String entityLocalName = messages.getMessage(metaClass.getJavaClass(), entityName);
        String fileName = entityName + ".xls";
        //String fullPath = path + "\\" + fileName;
        FileDescriptor fileDescriptor=fileMap.get(fileName);
        ExelWork exelWork = null;

        try {
            exelWork = new ExelWork(fileDescriptor);
        } catch (Exception e) {
            //logService.logException(e,Import_work.class,"ERROR");
            errorList.add(e.getMessage() + " Ошибка считывания файла " + fileName);
            return null;
        }

        exelWork.setCorrectSize();
        for (int i = 0; i < exelWork.row_count; ++i) {
            exelWork.getData(entityLocalName,errorList);
        }

        Row row;
        Sheet sheet=wb.getSheetAt(0);
        int dataCount=exelWork.getDataCount();
        for (int i = 0; i < dataCount; ++i) {
            HashMap<String,String> convertMap=new HashMap<>();
            Entity newEntity=import_work.getConflictEntities(metaClass,selectedProperties,exelWork,findType,convertMap);
            Entity existEntity=import_work.checkOneEntity(metaClass,newEntity,findType);

            import_work.findMatches(existEntity,newEntity,selectedProperties,convertMap);

            curRow += 2;
            sheet.createRow(curRow-1);
            row = sheet.createRow(curRow);
            if(existEntity!=null) {
                for (MetaProperty metaProperty : selectedProperties) {
                    String[] key = (metaClass.getName() + "." + metaProperty.getName()).split("\\$");
                    String localName = messages.getMessage(metaClass.getJavaClass(), key[key.length - 1]);
                    int cellNum = propertyPosMap.get(localName);
                    Cell cell = row.createCell(cellNum);
                    Object o = (existEntity).getValue(metaProperty.getName());

                    String propertyValue="";
                    if(metaProperty.getType()== MetaProperty.Type.ENUM){
                        if(o!=null) {
                            //String temp = ((Enum) o).getClass().getName();
                            String[] enumName = ((Enum) o).getClass().getName().split("\\.");
                            propertyValue = messages.getMessage(((Enum) o).getClass(), enumName[enumName.length - 1] + "." + ((Enum) o).name());
                        }
                    }
                    else {
                        try {
                            Entity propEntity = (Entity) o;
                            if (findType == 0) {
                                propertyValue = propEntity != null ? propEntity.getInstanceName() : null;
                            } else {
                                propertyValue = propEntity != null ? propEntity.getId().toString() : null;
                            }
                        } catch (Exception e) {
                            propertyValue = String.valueOf(o);
                        }
                    }
                    cell.setCellValue(propertyValue);
                }
            }
            curRow += 1;
            row = sheet.createRow(curRow);
            for (MetaProperty metaProperty : selectedProperties) {
                String[] key = (metaClass.getName() + "." + metaProperty.getName()).split("\\$");
                String localName = messages.getMessage(metaClass.getJavaClass(), key[key.length - 1]);
                int cellNum = propertyPosMap.get(localName);
                Cell cell = row.createCell(cellNum);
                Object o = (newEntity).getValue(metaProperty.getName());
                String propertyValue="";
                if(metaProperty.getType()== MetaProperty.Type.ENUM){
                    if(o!=null) {
                        //String temp = ((Enum) o).getClass().getName();
                        String[] enumName = ((Enum) o).getClass().getName().split("\\.");
                        propertyValue = messages.getMessage(((Enum) o).getClass(), enumName[enumName.length - 1] + "." + ((Enum) o).name());
                    }
                }
                else {
                    try {
                        Entity propEntity = (Entity) o;
                        if (findType == 0) {
                            propertyValue = propEntity != null ? propEntity.getInstanceName() : null;
                        } else {
                            propertyValue = propEntity != null ? propEntity.getId().toString() : null;
                        }
                    } catch (Exception e) {
                        propertyValue = String.valueOf(o);
                    }
                }
                String convertRes = convertMap.get(metaProperty.getName());
                if(convertRes!=null) {
                    switch (convertRes) {
                        case "exist": {
                            cell.setCellStyle(cellStyleYellow);
                            break;
                        }
                        case "not exist": {
                            if((metaProperty.getRange().isClass())&&(findType==1)){
                                cell.setCellStyle(cellStyleRed);
                            }
                            else {
                                cell.setCellStyle(cellStyleBlue);
                            }
                            break;
                        }
                        case "error": {
                            cell.setCellStyle(cellStyleRed);
                            break;
                        }
                        case "not correct": {
                            cell.setCellStyle(cellStyleRed);
                            break;
                        }
                        case "match":{
                            cell.setCellStyle(cellStyleGreen);
                            break;
                        }
                        case "not match":{
                            cell.setCellStyle(cellStyleYellow);
                            break;
                        }
                    }
                }
                cell.setCellValue(propertyValue);
            }
        }

        String[] temp1=metaClass.getName().split("\\$");
        String name=temp1[temp1.length-1];

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            wb.write(byteArrayOutputStream);
        }
        finally {
            byteArrayOutputStream.close();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        FileDescriptor fileDescriptorToLoad = metadata.create(com.haulmont.cuba.core.entity.FileDescriptor.class);
        fileDescriptorToLoad.setName(name+".xls");
        fileDescriptorToLoad.setExtension("xls");
        fileDescriptorToLoad.setSize((long) bytes.length);
        fileDescriptorToLoad.setCreateDate(new Date());
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        hssfWorkbook.getBytes();
        try {
            fileLoader.saveStream(fileDescriptorToLoad, () -> new ByteArrayInputStream(bytes));
        } catch (FileStorageException e) {
            throw new RuntimeException("Error loading file "+e);
        }
        return fileDescriptorToLoad;
    }


    private HSSFWorkbook addDataToWorkbook(HSSFWorkbook wb,MetaClass metaClass,
                                           ArrayList<MetaProperty> selectedProperties,HashMap<String,Integer> propertyMap){
        ArrayList<View> views=new ArrayList<>();
        views.add(import_work.getFullView(metaClass));
        String table=metaClass.getName();
        Collection entityCollection=import_work.getAllEntitiesFromBD(metaClass,table,views);
        HSSFSheet sheet=wb.getSheetAt(0);
        int rowNum=3;
        Row row=sheet.createRow(rowNum);
        for(Object obj:entityCollection){
            for(MetaProperty metaProperty:selectedProperties){
                Object o=((Entity)obj).getValue(metaProperty.getName());
                String propertyValue;
                try{
                    Entity propEntity=(Entity)o;
                    propertyValue=propEntity.getInstanceName();
                }
                catch (Exception e){
                    propertyValue=String.valueOf(o);
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

    @Override
    public boolean persistEntity(Entity entity){
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            em.persist(entity);
            tx.commit();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}