package com.company.importmodule.core;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.chile.core.model.Range;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Stepanov_ME on 23.01.2018.
 */
@Component
public class Import_work {

    @Inject
    private Persistence persistence;

    @Inject
    private Metadata metadata;

    @Inject
    private Messages messages;

    private DataHolder dataHolder;

    //импортирование сущностей из файла с различными параметрами импорта
    //TODO использовать отдельный класс для параметров импорта
    public ArrayList<String> smartImportEntity(HashMap<String, FileDescriptor> fileMap, MetaClass metaClass, int findType, int actionType, int assocActionType, boolean alwaysCreate) {
        ArrayList<String> errorList = new ArrayList<>();

        String[] temp = metaClass.getName().split("\\$");
        String entityName = temp[temp.length - 1];
        String entityLocalName = messages.getMessage(metaClass.getJavaClass(), entityName);
        String fileName = entityName + ".xls";
        //String fullPath = path + "\\" + fileName;
        FileDescriptor fileDescriptor = fileMap.get(fileName);
        ExelWork exelWork = null;

        try {
            exelWork = new ExelWork(fileDescriptor);
        } catch (Exception e) {
            errorList.add(e.getMessage() + " Ошибка считывания файла " + fileName);
            return errorList;
        }

        exelWork.setCorrectSize();
        for (int i = 0; i < exelWork.row_count; ++i) {
            ArrayList<String> errorListTemp = new ArrayList<>();
            exelWork.getData(entityLocalName, errorListTemp);
            errorList.addAll(errorListTemp);
        }

        Collection tempPropertyCollection = metaClass.getOwnProperties();
        Object metaPropertyT = metaClass.getProperty("id");

        Object[] metaProperties = new Object[tempPropertyCollection.size() + 1];
        int k = 0;
        for (Object obj : tempPropertyCollection) {
            metaProperties[k] = obj;
            ++k;
        }
        metaProperties[metaProperties.length - 1] = metaPropertyT;

        ArrayList<MetaProperty> propertyList = new ArrayList<>();
        for (Object obj : metaProperties) {
            propertyList.add((MetaProperty) obj);
        }

        ArrayList<View> viewsList = new ArrayList<>();
        //полное представление
        View viewT = getFullView(metaClass);
        viewsList.add(viewT);
        String table = metaClass.getName();

        if (!dataHolder.hasCache(table)) {
            List<Object> existEntities = getAllEntitiesFromBD(metaClass, table, viewsList);
            dataHolder.addCache(table, existEntities);
        }

        Boolean exist;

        int dataCount = exelWork.getDataCount();
        for (int i = 0; i < dataCount; ++i) {
            ArrayList<String> errorListTemp = new ArrayList<>();

            Boolean idFound = false;
            //преобразование данных в запись
            Entity entity = convertData(errorListTemp, metaClass, propertyList, exelWork, null, null, findType, assocActionType);

            if (alwaysCreate) {
                Entity tempEntity = metadata.create(metaClass);

                copyToEntity(entity, tempEntity, propertyList);
                try (Transaction tx = persistence.createTransaction()) {
                    EntityManager em = persistence.getEntityManager();
                    em.persist(tempEntity);
                    tx.commit();
                } catch (Exception e) {
                    //logService.logException(e,Import_work.class,"ERROR");
                    errorList.add(metaClass.getName() + " " + e.getMessage());
                }
                continue;
            }

            exist = false;
            errorList.addAll(errorListTemp);
            switch (findType) {
                //поиск по InstanceName
                case 0: {
                    Object data = dataHolder.getDataByName(table, entity.getInstanceName());
                    if (data != null) {
                        exist = true;
                        copyToEntity(entity, (Entity) data, propertyList);
                        //TODO сделать отдельный сервис для сообщений
                        if (actionType == 0) {
                            try (Transaction tx = persistence.createTransaction()) {
                                EntityManager em = persistence.getEntityManager();
                                em.merge((Entity) data);
                                tx.commit();
                            } catch (Exception e) {
                                //logService.logException(e,Import_work.class,"ERROR");
                                errorList.add(metaClass.getName() + " " + e.getMessage());
                            }
                            errorList.add(metaClass.getName() + " конфликт записи InstanceName=" + entity.getInstanceName() + " перезаписано");
                        } else {
                            errorList.add(metaClass.getName() + " конфликт записи InstanceName=" + entity.getInstanceName());
                        }
                    }
                    break;
                }

                //поиск по id
                case 1: {
                    if (entity.getId() == null) {
                        exist = true;
                        break;
                    }
                    Object data = dataHolder.getDataById(table, entity.getId().toString());
                    if (data != null) {
                        exist = true;

                        copyToEntity(entity, (Entity) data, propertyList);
                        if (actionType == 0) {
                            try (Transaction tx = persistence.createTransaction()) {
                                EntityManager em = persistence.getEntityManager();
                                em.merge((Entity) data);
                                tx.commit();
                            } catch (Exception e) {
                                //logService.logException(e,Import_work.class,"ERROR");
                                errorList.add(metaClass.getName() + " " + e.getMessage());
                            }
                            errorList.add(metaClass.getName() + " конфликт записи id=" + entity.getId() + " перезаписано");
                        } else {
                            errorList.add(metaClass.getName() + " конфликт записи id=" + entity.getId());
                        }
                    }
                    break;
                }
                case 2: {
                    if (entity.getId() == null) {
                        exist = true;
                        break;
                    }
                    Object data = dataHolder.getDataById(table, entity.getId().toString());
                    if (data != null) {
                        exist = true;

                        copyToEntity(entity, (Entity) data, propertyList);
                        if (actionType == 0) {
                            try (Transaction tx = persistence.createTransaction()) {
                                EntityManager em = persistence.getEntityManager();
                                em.merge((Entity) data);
                                tx.commit();
                            } catch (Exception e) {
                                //logService.logException(e,Import_work.class,"ERROR");
                                errorList.add(metaClass.getName() + " " + e.getMessage());
                            }
                            errorList.add(metaClass.getName() + " конфликт записи id=" + entity.getId() + " перезаписано");
                        } else {
                            errorList.add(metaClass.getName() + " конфликт записи id=" + entity.getId());
                        }
                    }
                    break;
                }
            }
            if ((entity.getId() == null) && ((findType == 1) || (findType == 2))) {
                errorList.add(metaClass.getName() + " неправильный формат id InstanceName=" + entity.getInstanceName());
            } else {
                if (!exist) {
                    Entity tempEntity = metadata.create(metaClass);

                    copyToEntity(entity, tempEntity, propertyList);
                    try (Transaction tx = persistence.createTransaction()) {
                        EntityManager em = persistence.getEntityManager();
                        em.persist(tempEntity);
                        tx.commit();
                    } catch (Exception e) {
                        //logService.logException(e,Import_work.class,"ERROR");
                        errorList.add(metaClass.getName() + " " + e.getMessage());
                    }
                }
            }
        }
        return errorList;
    }

    public ArrayList<String> smartImportEntityWithParam(HashMap<String, FileDescriptor> fileMap, MetaClass metaClass, int findType, int actionType, int assocActionType,
                                                        boolean alwaysCreate, HashMap<String, String> propValue, HashMap<String,
            String> allowedValue, String parameter) {
        ArrayList<String> errorList = new ArrayList<>();

        String[] temp = metaClass.getName().split("\\$");
        String entityName = temp[temp.length - 1];
        String entityLocalName = messages.getMessage(metaClass.getJavaClass(), entityName);
        String fileName = entityName + ".xls";
        //String fullPath = path + "\\" + fileName;
        FileDescriptor fileDescriptor = fileMap.get(fileName);
        ExelWork exelWork = null;

        try {
            exelWork = new ExelWork(fileDescriptor);
        } catch (Exception e) {
            errorList.add(e.getMessage() + " Ошибка считывания файла " + fileName);
            return errorList;
        }

        exelWork.setCorrectSize();
        for (int i = 0; i < exelWork.row_count; ++i) {
            ArrayList<String> errorListTemp = new ArrayList<>();
            exelWork.getData(entityLocalName, errorListTemp);
            errorList.addAll(errorListTemp);
        }

        //Object[] metaProperties = metaClass.getOwnProperties().toArray();
        Collection tempPropertyCollection = metaClass.getOwnProperties();
        Object metaPropertyT = metaClass.getProperty("id");

        Object[] metaProperties = new Object[tempPropertyCollection.size() + 1];
        int k = 0;
        for (Object obj : tempPropertyCollection) {
            metaProperties[k] = obj;
            ++k;
        }
        metaProperties[metaProperties.length - 1] = metaPropertyT;

        ArrayList<MetaProperty> propertyList = new ArrayList<>();
        for (Object obj : metaProperties) {
            propertyList.add((MetaProperty) obj);
        }

        ArrayList<View> viewsList = new ArrayList<>();
        //полное представление
        View viewT = getFullView(metaClass);
        viewsList.add(viewT);
        String table = metaClass.getName();

        if (!dataHolder.hasCache(table)) {
            List<Object> existEntities = getAllEntitiesFromBD(metaClass, table, viewsList, parameter);
            dataHolder.addCache(table, existEntities);
        }

        Boolean exist;

        int dataCount = exelWork.getDataCount();
        for (int i = 0; i < dataCount; ++i) {
            ArrayList<String> errorListTemp = new ArrayList<>();

            Boolean idFound = false;
            //преобразование данных в запись
            Entity entity = convertDataWithParam(errorListTemp, metaClass, propertyList, exelWork, propValue, findType, assocActionType);

            if (alwaysCreate) {
                Entity tempEntity = metadata.create(metaClass);

                copyToEntity(entity, tempEntity, propertyList);
                try (Transaction tx = persistence.createTransaction()) {
                    EntityManager em = persistence.getEntityManager();
                    em.persist(tempEntity);
                    tx.commit();
                } catch (Exception e) {
                    //logService.logException(e,Import_work.class,"ERROR");
                    errorList.add(metaClass.getName() + " " + e.getMessage());
                }
                continue;
            }

            exist = false;
            errorList.addAll(errorListTemp);
            boolean allowed = true;
            switch (findType) {
                //поиск по InstanceName
                case 0: {

                    Object data = dataHolder.getDataByName(table, entity.getInstanceName());
                    if (data != null) {
                        exist = true;
                        for (Map.Entry entry : allowedValue.entrySet()) {
                            if (!((Entity) data).getValue(entry.getKey().toString()).toString().contentEquals(entry.getValue().toString())) {
                                allowed = false;
                            }
                        }
                        if (!allowed) {
                            continue;
                        }
                        copyToEntity(entity, (Entity) data, propertyList);
                        //TODO сделать отдельный сервис для сообщений
                        if (actionType == 0) {
                            try (Transaction tx = persistence.createTransaction()) {
                                EntityManager em = persistence.getEntityManager();
                                em.merge((Entity) data);
                                tx.commit();
                            } catch (Exception e) {
                                //logService.logException(e,Import_work.class,"ERROR");
                                errorList.add(metaClass.getName() + " " + e.getMessage());
                            }
                            errorList.add(metaClass.getName() + " конфликт записи InstanceName=" + entity.getInstanceName() + " перезаписано");
                        } else {
                            errorList.add(metaClass.getName() + " конфликт записи InstanceName=" + entity.getInstanceName());
                        }
                    }
                    break;
                }

                //поиск по id
                case 1: {
                    if (entity.getId() == null) {
                        exist = true;
                        break;
                    }
                    Object data = dataHolder.getDataById(table, entity.getId().toString());
                    if (data != null) {
                        exist = true;

                        copyToEntity(entity, (Entity) data, propertyList);
                        if (actionType == 0) {
                            try (Transaction tx = persistence.createTransaction()) {
                                EntityManager em = persistence.getEntityManager();
                                em.merge((Entity) data);
                                tx.commit();
                            } catch (Exception e) {
                                //logService.logException(e,Import_work.class,"ERROR");
                                errorList.add(metaClass.getName() + " " + e.getMessage());
                            }
                            errorList.add(metaClass.getName() + " конфликт записи id=" + entity.getId() + " перезаписано");
                        } else {
                            errorList.add(metaClass.getName() + " конфликт записи id=" + entity.getId());
                        }
                    }
                    break;
                }
                case 2: {
                    if (entity.getId() == null) {
                        exist = true;
                        break;
                    }
                    Object data = dataHolder.getDataById(table, entity.getId().toString());
                    if (data != null) {
                        exist = true;

                        copyToEntity(entity, (Entity) data, propertyList);
                        if (actionType == 0) {
                            try (Transaction tx = persistence.createTransaction()) {
                                EntityManager em = persistence.getEntityManager();
                                em.merge((Entity) data);
                                tx.commit();
                            } catch (Exception e) {
                                //logService.logException(e,Import_work.class,"ERROR");
                                errorList.add(metaClass.getName() + " " + e.getMessage());
                            }
                            errorList.add(metaClass.getName() + " конфликт записи id=" + entity.getId() + " перезаписано");
                        } else {
                            errorList.add(metaClass.getName() + " конфликт записи id=" + entity.getId());
                        }
                    }
                    break;
                }
            }
            if ((entity.getId() == null) && ((findType == 1) || (findType == 2))) {
                errorList.add(metaClass.getName() + " неправильный формат id InstanceName=" + entity.getInstanceName());
            } else {
                if (!exist) {
                    Entity tempEntity = metadata.create(metaClass);

                    copyToEntity(entity, tempEntity, propertyList);
                    try (Transaction tx = persistence.createTransaction()) {
                        EntityManager em = persistence.getEntityManager();
                        em.persist(tempEntity);
                        tx.commit();
                    } catch (Exception e) {
                        //logService.logException(e,Import_work.class,"ERROR");
                        errorList.add(metaClass.getName() + " " + e.getMessage());
                    }
                }
            }
        }
        return errorList;
    }


    //проверка наличия всех записей
    public ArrayList<String> checkAllEntities(HashMap<String, FileDescriptor> fileMap, MetaClass metaClass, ArrayList<String> existList, ArrayList<String> notExistList, int findType) {
        ArrayList<String> errorList = new ArrayList<>();

        String[] temp = metaClass.getName().split("\\$");
        String entityName = temp[temp.length - 1];
        String entityLocalName = messages.getMessage(metaClass.getJavaClass(), entityName);
        String fileName = entityName + ".xls";
        //String fullPath = path + "\\" + fileName;
        FileDescriptor fileDescriptor = fileMap.get(fileName);
        ExelWork exelWork = null;

        try {
            exelWork = new ExelWork(fileDescriptor);
        } catch (Exception e) {
            errorList.add(e.getMessage() + " Ошибка считывания файла " + fileName);
            return errorList;
        }
        exelWork.setCorrectSize();
        for (int i = 0; i < exelWork.row_count; ++i) {
            //exelWork.getData(entityName,i);
            ArrayList<String> errorListTemp = new ArrayList<>();
            exelWork.getData(entityLocalName, errorListTemp);
            errorList.addAll(errorListTemp);
        }

        Collection tempPropertyCollection = metaClass.getOwnProperties();
        Object metaPropertyT = metaClass.getProperty("id");

        Object[] metaProperties = new Object[tempPropertyCollection.size() + 1];
        int k = 0;
        for (Object obj : tempPropertyCollection) {
            metaProperties[k] = obj;
            ++k;
        }
        metaProperties[metaProperties.length - 1] = metaPropertyT;

        ArrayList<MetaProperty> propertyList = new ArrayList<>();
        for (Object obj : metaProperties) {
            propertyList.add((MetaProperty) obj);
        }

        ViewRepository viewRepository = metadata.getViewRepository();
        Collection<String> viewArr = viewRepository.getViewNames(metaClass);

        ArrayList<View> viewsList = new ArrayList<>();
        View view = getFullView(metaClass);
        viewsList.add(view);

        String table = metaClass.getName();

        if (!dataHolder.hasCache(table)) {
            List<Object> existEntities = getAllEntitiesFromBD(metaClass, table, viewsList);
            dataHolder.addCache(table, existEntities);
        }

        int dataCount = exelWork.getDataCount();
        for (int i = 0; i < dataCount; ++i) {
            ArrayList<String> errorListTemp = new ArrayList<>();

            Entity entity = convertData(errorListTemp, metaClass, propertyList, exelWork, null, null, 0, 0);

            errorList.addAll(errorListTemp);
            boolean found = false;
            boolean correctId = true;
            if ((findType == 1) || (findType == 2)) {
                if ((entity).getId() != null) {
                    Object data = dataHolder.getDataById(table, entity.getId().toString());
                    if (data != null) {
                        existList.add(((Entity) data).getId() + " запись уже добавлена");
                        found = true;
                    }
                } else {
                    correctId = false;
                }
            } else {
                Object data = dataHolder.getDataByName(table, entity.getInstanceName());
                if (data != null) {
                    existList.add(((Entity) data).getInstanceName() + " запись уже добавлена");
                    found = true;
                }
            }
            if (!found) {
                if (correctId) {
                    notExistList.add(entity.getInstanceName() + " записи нет в БД");
                } else {
                    errorList.add("Некорректный id");
                }
            }
        }
        existList.add(String.valueOf(existList.size()) + " конфликтов");
        notExistList.add(String.valueOf(notExistList.size()) + " уникальных записей");
        return errorList;
    }

    //проверка наличия одной записи
    public Entity checkOneEntity(MetaClass metaClass, Entity newEntity, int findType) {
        ArrayList<View> viewsList = new ArrayList<>();
        View view = getFullView(metaClass);
        viewsList.add(view);

        String table = metaClass.getName();

        if (!dataHolder.hasCache(table)) {
            List<Object> existEntities = getAllEntitiesFromBD(metaClass, table, viewsList);
            dataHolder.addCache(table, existEntities);
        }
        switch (findType) {
            case 0: {
                Object data = dataHolder.getDataByName(table, newEntity.getInstanceName());
                if (data != null) {
                    return (Entity) data;
                }
                break;
            }
            case 1: {
                if (((Entity) newEntity).getId() != null) {
                    Object data = dataHolder.getDataById(table, newEntity.getId().toString());
                    if (data != null) {
                        return (Entity) data;
                    }
                }
                break;
            }
            case 2: {
                if (((Entity) newEntity).getId() != null) {
                    Object data = dataHolder.getDataById(table, newEntity.getId().toString());
                    if (data != null) {
                        return (Entity) data;
                    }
                }
                break;
            }
        }
        return null;
    }

    //получение всех записей соответствующего метакласса из БД
    public List<Object> getAllEntitiesFromBD(MetaClass obj, String table, ArrayList<View> viewsList) {
        List<Object> entityList = null;
        //name=checkStr(name);

        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            Query query = em.createQuery("SELECT o FROM " + table + " o", obj.getClass());
            for (View view : viewsList) {
                query.addView(view);
            }
            entityList = query.getResultList();
            tx.commit();
        } catch (Exception e) {
            //logService.logException(e,Import_work.class,"ERROR");
        }
        if (entityList == null) {
            return new ArrayList<>();
        } else {
            return entityList;
        }
    }

    public List<Object> getAllEntitiesFromBD(MetaClass obj, String table, ArrayList<View> viewsList, String parameter) {
        List<Object> entityList = null;
        //name=checkStr(name);

        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            Query query = em.createQuery("SELECT o FROM " + table + " o " + parameter, obj.getClass());
            for (View view : viewsList) {
                query.addView(view);
            }
            entityList = query.getResultList();
            tx.commit();
        } catch (Exception e) {
            //logService.logException(e,Import_work.class,"ERROR");
        }
        if (entityList == null) {
            return new ArrayList<>();
        } else {
            return entityList;
        }
    }

    public void copyToEntity(Entity entitySource, Entity entityDest, ArrayList<MetaProperty> metaProperties) {
        for (MetaProperty metaProperty : metaProperties) {
            try {
                if (metaProperty.getName().contentEquals("id")) {
                    continue;
                }
                entityDest.setValue(metaProperty.getName(), entitySource.getValue(metaProperty.getName()));
            } catch (Exception e) {
                //logService.logException(e,Import_work.class,"WARN");
                //continue;
            }
        }
        return;
    }

    //преобразование сущности
    //TODO использовать отдельный класс для параметров импорта
    private Entity convertData(ArrayList<String> errorList, MetaClass metaClass, ArrayList<MetaProperty> propertyList,
                               ExelWork exelWork, String paramName, String paramValue, int findType, int actionType) {
        Entity newEntity;
        newEntity = (Entity) metadata.create(metaClass.getJavaClass());

        for (MetaProperty metaProperty : propertyList) {
            Boolean found = false;

            String[] key = (metaClass.getName() + "." + metaProperty.getName()).split("\\$");
            String localName = messages.getMessage(metaClass.getJavaClass(), key[key.length - 1]);
            //получение значения поля сущности по его назанию(выталкивается верхний элемент из стека)
            String propertyValue = exelWork.getProperty(localName);
            if (paramName != null) {
                if (metaProperty.getName().contentEquals(paramName)) {
                    propertyValue = paramValue;
                }
            }
            try {
                switch (metaProperty.getType()) {
                    case ASSOCIATION: {
                        getRefEntity(metaClass, metaProperty, propertyValue, errorList, newEntity, findType, actionType);
                        break;
                    }
                    case COMPOSITION: {
                        getRefEntity(metaClass, metaProperty, propertyValue, errorList, newEntity, findType, actionType);
                        break;
                    }
                    case DATATYPE: {
                        //String s1=metaProperty.getJavaType().getName();
                        switch (metaProperty.getJavaType().getName()) {
                            case "java.lang.String": {
                                newEntity.setValue(metaProperty.getName(), propertyValue);
                                break;
                            }
                            case "java.lang.Integer": {
                                String tempStr = propertyValue.split("\\.")[0];
                                newEntity.setValue(metaProperty.getName(), Integer.valueOf(tempStr));
                                break;
                            }
                            case "java.lang.Double": {
                                String doubleValue = propertyValue.replace(",", ".");
                                newEntity.setValue(metaProperty.getName(), Double.valueOf(doubleValue));
                                break;
                            }
                            case "java.lang.Boolean": {
                                newEntity.setValue(metaProperty.getName(), Boolean.valueOf(propertyValue));
                                break;
                            }
                            case "java.util.Date": {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                Date date = simpleDateFormat.parse(propertyValue);
                                newEntity.setValue(metaProperty.getName(), date);
                                break;
                            }
                            case "java.util.UUID": {
                                try {
                                    newEntity.setValue(metaProperty.getName(), UUID.fromString(propertyValue));
                                } catch (Exception e) {
                                    newEntity.setValue(metaProperty.getName(), null);
                                }
                                break;
                            }
                            default: {
                                if (propertyValue.contentEquals("")) {
                                    errorList.add(metaClass.getName() + " Неподдерживаемый тип " + metaProperty.getName() + "-> <Пустая строка>");
                                } else {
                                    errorList.add(metaClass.getName() + " Неподдерживаемый тип " + metaProperty.getName() + "-> " + propertyValue);
                                }
                            }
                        }
                        break;
                    }
                    case ENUM: {
                        Class enumClass = metaProperty.getJavaType();
                        Object[] objects = enumClass.getEnumConstants();

                        for (Object o : objects) {
                            String[] enumName = enumClass.getName().split("\\.");
                            String enumLocalName = messages.getMessage(((Enum) o).getClass(), enumName[enumName.length - 1] + "." + ((Enum) o).name());
                            String[] s = propertyValue.split("\\.");
                            if ((enumLocalName.contentEquals(s[0]))) {
                                found = true;
                                newEntity.setValue(metaProperty.getName(), (Enum) o);
                            }
                        }

                        if (!found) {
                            if (propertyValue.contentEquals("")) {
                                errorList.add(metaClass.getName() + " " + "Не найдена соответствующая константа перечисления " + metaProperty.getName() + "-> <Пустая строка>");
                            } else {
                                errorList.add(metaClass.getName() + " " + "Не найдена соответствующая константа перечисления " + metaProperty.getName() + "-> " + propertyValue);
                            }
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                if (propertyValue.contentEquals("")) {
                    errorList.add(metaClass.getName() + " Неправильный формат данных " + metaProperty.getName() + "-> <Пустая строка>");
                } else {
                    errorList.add(metaClass.getName() + " Неправильный формат данных " + metaProperty.getName() + "-> " + propertyValue);
                }
                //logService.logException(e,Import_work.class,"WARN");
            }
        }
        return newEntity;
    }

    public Entity convertDataWithParam(ArrayList<String> errorList, MetaClass metaClass, ArrayList<MetaProperty> propertyList,
                                       ExelWork exelWork, HashMap<String, String> propValue, int findType, int actionType) {
        Entity newEntity;
        newEntity = (Entity) metadata.create(metaClass.getJavaClass());

        for (MetaProperty metaProperty : propertyList) {
            Boolean found = false;

            String[] key = (metaClass.getName() + "." + metaProperty.getName()).split("\\$");
            String localName = messages.getMessage(metaClass.getJavaClass(), key[key.length - 1]);
            //получение значения поля сущности по его назанию(выталкивается верхний элемент из стека)
            String propertyValue = exelWork.getProperty(localName);
            if (propValue != null) {
                for (Map.Entry entry : propValue.entrySet()) {
                    if (entry.getKey().toString().contentEquals(metaProperty.getName())) {
                        propertyValue = entry.getValue().toString();
                    }
                }
            }
            try {
                switch (metaProperty.getType()) {
                    case ASSOCIATION: {
                        getRefEntity(metaClass, metaProperty, propertyValue, errorList, newEntity, findType, actionType);
                        break;
                    }
                    case COMPOSITION: {
                        getRefEntity(metaClass, metaProperty, propertyValue, errorList, newEntity, findType, actionType);
                        break;
                    }
                    case DATATYPE: {
                        //String s1=metaProperty.getJavaType().getName();
                        switch (metaProperty.getJavaType().getName()) {
                            case "java.lang.String": {
                                newEntity.setValue(metaProperty.getName(), propertyValue);
                                break;
                            }
                            case "java.lang.Integer": {
                                String tempStr = propertyValue.split("\\.")[0];
                                newEntity.setValue(metaProperty.getName(), Integer.valueOf(tempStr));
                                break;
                            }
                            case "java.lang.Double": {
                                String doubleValue = propertyValue.replace(",", ".");
                                newEntity.setValue(metaProperty.getName(), Double.valueOf(doubleValue));
                                break;
                            }
                            case "java.lang.Boolean": {
                                newEntity.setValue(metaProperty.getName(), Boolean.valueOf(propertyValue));
                                break;
                            }
                            case "java.util.Date": {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                Date date = simpleDateFormat.parse(propertyValue);
                                newEntity.setValue(metaProperty.getName(), date);
                                break;
                            }
                            case "java.util.UUID": {
                                try {
                                    newEntity.setValue(metaProperty.getName(), UUID.fromString(propertyValue));
                                } catch (Exception e) {
                                    newEntity.setValue(metaProperty.getName(), null);
                                }
                                break;
                            }
                            default: {
                                if (propertyValue.contentEquals("")) {
                                    errorList.add(metaClass.getName() + " Неподдерживаемый тип " + metaProperty.getName() + "-> <Пустая строка>");
                                } else {
                                    errorList.add(metaClass.getName() + " Неподдерживаемый тип " + metaProperty.getName() + "-> " + propertyValue);
                                }
                            }
                        }
                        break;
                    }
                    case ENUM: {
                        Class enumClass = metaProperty.getJavaType();
                        Object[] objects = enumClass.getEnumConstants();

                        for (Object o : objects) {
                            String[] enumName = enumClass.getName().split("\\.");
                            String enumLocalName = messages.getMessage(((Enum) o).getClass(), enumName[enumName.length - 1] + "." + ((Enum) o).name());
                            String[] s = propertyValue.split("\\.");
                            if ((enumLocalName.contentEquals(s[0]))) {
                                found = true;
                                newEntity.setValue(metaProperty.getName(), (Enum) o);
                            }
                        }

                        if (!found) {
                            if (propertyValue.contentEquals("")) {
                                errorList.add(metaClass.getName() + " " + "Не найдена соответствующая константа перечисления " + metaProperty.getName() + "-> <Пустая строка>");
                            } else {
                                errorList.add(metaClass.getName() + " " + "Не найдена соответствующая константа перечисления " + metaProperty.getName() + "-> " + propertyValue);
                            }
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                if (propertyValue.contentEquals("")) {
                    errorList.add(metaClass.getName() + " Неправильный формат данных " + metaProperty.getName() + "-> <Пустая строка>");
                } else {
                    errorList.add(metaClass.getName() + " Неправильный формат данных " + metaProperty.getName() + "-> " + propertyValue);
                }
                //logService.logException(e,Import_work.class,"WARN");
            }
        }
        return newEntity;
    }

    private void getRefEntity(MetaClass metaClass, MetaProperty metaProperty, String propertyValue, ArrayList<String> errorList,
                              Entity newEntity, int findType, int actionType) {
        boolean found = false;
        ViewRepository viewRepository = metadata.getViewRepository();
        ArrayList<View> viewsList = new ArrayList<>();
        Range range = metaProperty.getRange();
        MetaClass metaClassTemp = range.asClass();

        View viewFull = getFullView(metaClassTemp);
        viewsList.add(viewFull);

        MetaClass jt = metaProperty.getRange().asClass();
        String table = jt.getName();

        if (table.contentEquals("mobdekbkp$DocumentEntry")) {
            newEntity.setValue(metaProperty.getName(), null);
            return;
        }
        if (!dataHolder.hasCache(table)) {
            List<Object> objList = getAllEntitiesFromBD(jt, table, viewsList);
            dataHolder.addCache(table, objList);
        }


        if (actionType == 0) {
            switch (findType) {
                //поиск по InstanceName
                case 0: {
                    Object data = dataHolder.getDataByName(table, propertyValue);
                    if (data != null) {
                        found = true;
                        if (metaProperty.getRange().getCardinality().isMany()) {
                            Object o = newEntity.getValue(metaProperty.getName());
                            List list = new ArrayList();
                            list.add(data);
                            newEntity.setValue(metaProperty.getName(), list);
                        } else {
                            newEntity.setValue(metaProperty.getName(), data);
                        }
                    }
                    break;
                }
                //Поиск по id
                case 1: {
                    Object data = dataHolder.getDataById(table, propertyValue);
                    if (data != null) {
                        if (metaProperty.getRange().getCardinality().isMany()) {
                            Object o = newEntity.getValue(metaProperty.getName());
                            List list = new ArrayList();
                            list.add(data);
                            //newEntity.setValue(metaProperty.getName(),new ArrayList<Object>().add(item));
                            newEntity.setValue(metaProperty.getName(), list);
                        } else {
                            newEntity.setValue(metaProperty.getName(), data);
                        }
                    }
                    break;
                }
                //Поиск по id основной сущности
                //Идентично поиску по InstanceName
                case 2: {
                    Object data = dataHolder.getDataByName(table, propertyValue);
                    if (data != null) {
                        found = true;
                        if (metaProperty.getRange().getCardinality().isMany()) {
                            Object o = newEntity.getValue(metaProperty.getName());
                            List list = new ArrayList();
                            list.add(data);
                            newEntity.setValue(metaProperty.getName(), list);
                        } else {
                            newEntity.setValue(metaProperty.getName(), data);
                        }
                    }
                    break;
                }
            }

            if (!found) {
                if (findType == 0) {
                    switch (actionType) {
                        //Уведомление
                        case 0: {
                            if (!metaProperty.getName().contentEquals("adminParameters")) {
                                if (propertyValue.contentEquals("")) {
                                    errorList.add(metaClass.getName() + " " + "Не найдена соответствующая сущность в БД " + metaProperty.getName() + "-> <Пустая строка>");
                                } else {
                                    errorList.add(metaClass.getName() + " " + "Не найдена соответствующая сущность в БД " + metaProperty.getName() + "-> " + propertyValue);
                                }
                            }
                            break;
                        }
                        //создать новую сущность
                        case 1: {
                            Boolean error = false;
                            Range entityRange = metaProperty.getRange();
                            Entity entity = metadata.create(entityRange.asClass());
                            View view = viewRepository.getView(entityRange.asClass(), "_minimal");
                            Object[] instanceProperties = view.getProperties().toArray();
                            entity.setValue(((ViewProperty) instanceProperties[0]).getName(), propertyValue);
                            try (Transaction tx = persistence.createTransaction()) {
                                EntityManager em = persistence.getEntityManager();
                                em.persist(entity);
                                tx.commit();
                            } catch (Exception e) {
                                error = true;
                                //logService.logException(e,Import_work.class,"ERROR");
                                errorList.add(metaClass.getName() + " " + e.getMessage() + " ошибка создания сущности");
                            }
                            if (!error) {
                                newEntity.setValue(metaProperty.getName(), entity);
                            }
                            break;
                        }
                    }
                } else {
                    errorList.add("Невозможно создание новой сущности по id");
                }
            }
        } else {
            if ((findType != 1) && (findType != 2)) {
                Boolean error = false;
                Range entityRange = metaProperty.getRange();
                Entity entity = metadata.create(entityRange.asClass());
                View view = viewRepository.getView(entityRange.asClass(), "_minimal");
                Object[] instanceProperties = view.getProperties().toArray();
                entity.setValue(((ViewProperty) instanceProperties[0]).getName(), propertyValue);
                try (Transaction tx = persistence.createTransaction()) {
                    EntityManager em = persistence.getEntityManager();
                    em.persist(entity);
                    tx.commit();
                } catch (Exception e) {
                    error = true;
                    //logService.logException(e,Import_work.class,"ERROR");
                    errorList.add(metaClass.getName() + " " + e.getMessage() + " ошибка создания сущности");
                }
                if (!error) {
                    newEntity.setValue(metaProperty.getName(), entity);
                }
            } else {
                errorList.add("Невозможно создание новой сущности по id");
            }
        }
    }

    //TODO конвертация с записью результата
    public Entity getConflictEntities(MetaClass metaClass, ArrayList<MetaProperty> propertyList,
                                      ExelWork exelWork, int findType, HashMap<String, String> convertMap) {
        Entity newEntity;
        newEntity = (Entity) metadata.create(metaClass.getJavaClass());
        Entity conflictEntity = null;

        for (MetaProperty metaProperty : propertyList) {
            Boolean found = false;

            String[] key = (metaClass.getName() + "." + metaProperty.getName()).split("\\$");
            String localName = messages.getMessage(metaClass.getJavaClass(), key[key.length - 1]);
            //получение значения поля сущности по его назанию(выталкивается верхний элемент из стека)
            String propertyValue = exelWork.getProperty(localName);

            try {
                switch (metaProperty.getType()) {
                    case ASSOCIATION: {
                        ArrayList<View> viewsList = new ArrayList<>();
                        Range range = metaProperty.getRange();
                        MetaClass metaClassTemp = range.asClass();
                        View viewFull = getFullView(metaClassTemp);
                        viewsList.add(viewFull);

                        MetaClass metaClassAssoc = metadata.getClass(metaProperty.getJavaType());
                        String table = metaClassAssoc.getName();

                        if (!dataHolder.hasCache(table)) {
                            List<Object> existList = getAllEntitiesFromBD(metaClassAssoc, table, viewsList);
                            dataHolder.addCache(table, existList);
                        }

                        //выбор режима поиска
                        switch (findType) {
                            //поиск по InstanceName
                            case 0: {
                                Object data = dataHolder.getDataByName(table, propertyValue);
                                if (data != null) {
                                    found = true;
                                    //conflictEntity=(Entity) item;
                                    newEntity.setValue(metaProperty.getName(), data);
                                }
                                break;
                            }
                            //Поиск по id
                            case 1: {
                                Object data = dataHolder.getDataById(table, propertyValue);
                                if (data != null) {
                                    found = true;
                                    //conflictEntity=(Entity) item;
                                    newEntity.setValue(metaProperty.getName(), data);
                                }
                                break;
                            }
                            //поиск по InstanceName
                            case 2: {
                                Object data = dataHolder.getDataByName(table, propertyValue);
                                if (data != null) {
                                    found = true;
                                    //conflictEntity=(Entity) item;
                                    newEntity.setValue(metaProperty.getName(), data);
                                }
                                break;
                            }
                        }
                        if (found) {
                            convertMap.put(metaProperty.getName(), "exist");
                        } else {
                            ViewRepository viewRepository = metadata.getViewRepository();
                            View view = viewRepository.getView(metaClass, "_minimal");
                            Object[] instanceProperties = view.getProperties().toArray();
                            Entity tempEntity = metadata.create(metaClassAssoc);
                            tempEntity.setValue(((ViewProperty) instanceProperties[0]).getName(), propertyValue);
                            newEntity.setValue(metaProperty.getName(), tempEntity);
                            convertMap.put(metaProperty.getName(), "not exist");
                        }
                        break;
                    }
                    case COMPOSITION: {
                        ArrayList<View> viewsList = new ArrayList<>();
                        Range range = metaProperty.getRange();
                        MetaClass metaClassTemp = range.asClass();
                        View viewFull = getFullView(metaClassTemp);
                        viewsList.add(viewFull);

                        MetaClass metaClassAssoc = metadata.getClass(metaProperty.getJavaType());
                        String table = metaClassAssoc.getName();

                        if (!dataHolder.hasCache(table)) {
                            List<Object> existList = getAllEntitiesFromBD(metaClassAssoc, table, viewsList);
                            dataHolder.addCache(table, existList);
                        }

                        //выбор режима поиска
                        switch (findType) {
                            //поиск по InstanceName
                            case 0: {
                                Object data = dataHolder.getDataByName(table, propertyValue);
                                if (data != null) {
                                    found = true;
                                    newEntity.setValue(metaProperty.getName(), data);
                                }
                                break;
                            }
                            //Поиск по id
                            case 1: {
                                Object data = dataHolder.getDataById(table, propertyValue);
                                if (data != null) {
                                    found = true;
                                    //conflictEntity=(Entity) item;
                                    newEntity.setValue(metaProperty.getName(), data);
                                }
                                break;
                            }
                            case 2: {
                                Object data = dataHolder.getDataByName(table, propertyValue);
                                if (data != null) {
                                    found = true;
                                    newEntity.setValue(metaProperty.getName(), data);
                                }
                                break;
                            }
                        }
                        if (found) {
                            convertMap.put(metaProperty.getName(), "exist");
                        } else {
                            ViewRepository viewRepository = metadata.getViewRepository();
                            View view = viewRepository.getView(metaClass, "_minimal");
                            Object[] instanceProperties = view.getProperties().toArray();
                            Entity tempEntity = metadata.create(metaClassAssoc);
                            tempEntity.setValue(((ViewProperty) instanceProperties[0]).getName(), propertyValue);
                            newEntity.setValue(metaProperty.getName(), tempEntity);
                            convertMap.put(metaProperty.getName(), "not exist");
                        }
                        break;
                    }
                    case DATATYPE: {
                        switch (metaProperty.getJavaType().getName()) {
                            case "java.lang.String": {
                                newEntity.setValue(metaProperty.getName(), propertyValue);
                                convertMap.put(metaProperty.getName(), "correct");
                                break;
                            }
                            case "java.lang.Integer": {
                                String tempStr = propertyValue.split("\\.")[0];
                                newEntity.setValue(metaProperty.getName(), Integer.valueOf(tempStr));
                                convertMap.put(metaProperty.getName(), "correct");
                                break;
                            }
                            case "java.lang.Double": {
                                newEntity.setValue(metaProperty.getName(), Double.valueOf(propertyValue));
                                convertMap.put(metaProperty.getName(), "correct");
                                break;
                            }
                            case "java.lang.Boolean": {
                                newEntity.setValue(metaProperty.getName(), Boolean.valueOf(propertyValue));
                                convertMap.put(metaProperty.getName(), "correct");
                                break;
                            }
                            case "java.util.Date": {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                Date date = simpleDateFormat.parse(propertyValue);
                                newEntity.setValue(metaProperty.getName(), date);
                                convertMap.put(metaProperty.getName(), "correct");
                                break;
                            }
                            case "java.util.UUID": {
                                try {
                                    newEntity.setValue(metaProperty.getName(), UUID.fromString(propertyValue));
                                    convertMap.put(metaProperty.getName(), "correct");
                                } catch (Exception e) {
                                    newEntity.setValue(metaProperty.getName(), null);
                                    convertMap.put(metaProperty.getName(), "not correct");
                                }
                                break;
                            }
                            default: {
                                convertMap.put(metaProperty.getName(), "not correct");
                            }
                        }
                        break;
                    }
                    case ENUM: {
                        Class enumClass = metaProperty.getJavaType();
                        Object[] objects = enumClass.getEnumConstants();

                        for (Object o : objects) {
                            String[] enumName = enumClass.getName().split("\\.");
                            String enumLocalName = messages.getMessage(((Enum) o).getClass(), enumName[enumName.length - 1] + "." + ((Enum) o).name());
                            if ((enumLocalName.contentEquals(propertyValue))) {
                                found = true;
                                convertMap.put(metaProperty.getName(), "exist");
                                newEntity.setValue(metaProperty.getName(), (Enum) o);
                            }
                        }

                        if (!found) {
                            convertMap.put(metaProperty.getName(), "error");
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                convertMap.put(metaProperty.getName(), "error");
            }
        }
        //ArrayList<Entity> conflictEntities=new ArrayList<>();
        //conflictEntities.add(conflictEntity);
        //conflictEntities.add(newEntity);
        return newEntity;
    }

    public void findMatches(Entity existEntity, Entity newEntity, ArrayList<MetaProperty> selectedProperties, HashMap<String, String> convertMap) {
        for (MetaProperty metaProperty : selectedProperties) {
            String curKey = convertMap.get(metaProperty.getName());
            if ((curKey == null) || (curKey.contentEquals("correct")) || (curKey.contentEquals("exist"))) {
                String existValue = null;
                String newValue = null;
                try {
                    existValue = existEntity.getValue(metaProperty.getName()).toString();
                    newValue = newEntity.getValue(metaProperty.getName()).toString();
                } catch (NullPointerException e) {
                    continue;
                }
                if (existValue.contentEquals(newValue)) {
                    convertMap.put(metaProperty.getName(), "match");
                } else {
                    convertMap.put(metaProperty.getName(), "not match");
                }
            }
        }
    }

    public View getFullView(MetaClass metaClass) {
        Collection<MetaProperty> properties = metaClass.getOwnProperties();
        View view = new View(metaClass.getJavaClass());
        for (MetaProperty metaProperty : properties) {
            view.addProperty(metaProperty.getName());
        }
        return view;
    }

    public HSSFWorkbook getTemplateWorkbook(MetaClass metaClass, ArrayList<MetaProperty> selectedProperties, HashMap<String, Integer> propertyMap) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        Row row0 = sheet.createRow(0);
        Row row1 = sheet.createRow(1);
        Row row2 = sheet.createRow(2);

        Collection tempPropertyCollection = metaClass.getOwnProperties();
        Object metaPropertyT = metaClass.getProperty("id");
        Object[] metaProperties = new Object[tempPropertyCollection.size() + 1];
        int k = 0;
        for (Object obj : tempPropertyCollection) {
            metaProperties[k] = obj;
            ++k;
        }
        metaProperties[metaProperties.length - 1] = metaPropertyT;

        ArrayList<MetaProperty> propertyList = new ArrayList<>();
        for (Object obj : metaProperties) {
            //если выбраны атрибуты, то добавить только выбранные и обязательные
            if (selectedProperties != null) {
                if (((MetaProperty) obj).isMandatory() || selectedProperties.contains(obj)) {
                    propertyList.add((MetaProperty) obj);
                }
            }
            //если атрибуты не выбраны, то добавить все
            else {
                propertyList.add((MetaProperty) obj);
            }
        }

        int i = 0;
        for (MetaProperty metaProperty : propertyList) {
            String tempName = metaProperty.getName();
            String[] tempName2 = (metaClass.getName() + "." + tempName).split("\\$");
            String localName = messages.getMessage(metaClass.getJavaClass(), tempName2[tempName2.length - 1]);
            row0.createCell(i).setCellValue(localName);
            if (propertyMap != null) {
                propertyMap.put(localName, i);
            }
            String typeStr = "";
            if (metaProperty.getRange().isDatatype()) {
                typeStr = metaProperty.getRange().asDatatype().getName();
            }
            if (metaProperty.getRange().isEnum()) {
                Class enumClass = metaProperty.getJavaType();
                Object[] objects = enumClass.getEnumConstants();
                String valuesStr = "";

                for (Object o : objects) {
                    String[] enumName = enumClass.getName().split("\\.");
                    String enumLocalName = messages.getMessage(((Enum) o).getClass(), enumName[enumName.length - 1] + "." + ((Enum) o).name());
                    if (!valuesStr.contentEquals("")) {
                        valuesStr += ", " + enumLocalName;
                    } else {
                        valuesStr += enumLocalName;
                    }
                }
                typeStr = valuesStr;

            }
            if (metaProperty.getRange().isClass()) {
                typeStr = metaProperty.getRange().asClass().getName();
            }
            row1.createCell(i).setCellValue(typeStr);
            if (metaProperty.isMandatory()) {
                row2.createCell(i).setCellValue("Обязательный");
            } else {
                row2.createCell(i).setCellValue("Не обязательный");
            }
            ++i;
        }
        if (propertyMap != null) {
            propertyMap.put("row_maxVal", i);
        }
        return wb;
    }

    public void addTemplateToWorkbook(HSSFWorkbook wb, MetaClass metaClass, ArrayList<MetaProperty> selectedProperties, HashMap<String, Integer> propertyMap) {
        HSSFSheet sheet = wb.getSheet(metaClass.getName());
        if (sheet == null) {
            sheet = wb.createSheet(metaClass.getName());
        } else {
            return;
        }
        Row row0 = sheet.createRow(0);
        Row row1 = sheet.createRow(1);
        Row row2 = sheet.createRow(2);

        Collection tempPropertyCollection = metaClass.getOwnProperties();
        Object metaPropertyT = metaClass.getProperty("id");
        Object[] metaProperties = new Object[tempPropertyCollection.size() + 1];
        int k = 0;
        for (Object obj : tempPropertyCollection) {
            metaProperties[k] = obj;
            ++k;
        }
        metaProperties[metaProperties.length - 1] = metaPropertyT;

        ArrayList<MetaProperty> propertyList = new ArrayList<>();
        for (Object obj : metaProperties) {
            //если выбраны атрибуты, то добавить только выбранные и обязательные
            if (selectedProperties != null) {
                if (((MetaProperty) obj).isMandatory() || selectedProperties.contains(obj)) {
                    propertyList.add((MetaProperty) obj);
                }
            }
            //если атрибуты не выбраны, то добавить все
            else {
                propertyList.add((MetaProperty) obj);
            }
        }

        int i = 0;
        for (MetaProperty metaProperty : propertyList) {
            String tempName = metaProperty.getName();
            String[] tempName2 = (metaClass.getName() + "." + tempName).split("\\$");
            String localName = messages.getMessage(metaClass.getJavaClass(), tempName2[tempName2.length - 1]);
            row0.createCell(i).setCellValue(localName);
            if (propertyMap != null) {
                propertyMap.put(localName, i);
            }
            String typeStr = "";
            if (metaProperty.getRange().isDatatype()) {
                typeStr = metaProperty.getRange().asDatatype().getName();
            }
            if (metaProperty.getRange().isEnum()) {
                typeStr = metaProperty.getRange().asEnumeration().getName();
            }
            if (metaProperty.getRange().isClass()) {
                typeStr = metaProperty.getRange().asClass().getName();
            }
            row1.createCell(i).setCellValue(typeStr);
            if (metaProperty.isMandatory()) {
                row2.createCell(i).setCellValue("Обязательный");
            } else {
                row2.createCell(i).setCellValue("Не обязательный");
            }
            ++i;
        }
        return;
    }

    public void createDataHolder() {
        dataHolder = new DataHolder();
    }
}
