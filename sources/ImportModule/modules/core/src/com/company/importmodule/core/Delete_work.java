package com.company.importmodule.core;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by Stepanov_ME on 23.01.2018.
 */
@Component
public class Delete_work {
    @Inject
    private Persistence persistence;

    @Inject
    Messages messages;

    @Inject
    Import_work import_work;

    @Inject
    Metadata metadata;

    /*@Inject
    private LogService logService;*/

    public ArrayList<String> deleteEntity(MetaClass metaClass)
    {
        ArrayList<String> errorList=new ArrayList<>();

        Class temp_class= metaClass.getJavaClass();
        String[] temp_str_arr=temp_class.getName().split("\\.");
        String temp_str=temp_str_arr[temp_str_arr.length-3]+"$"+temp_str_arr[temp_str_arr.length-1];
        //List<Object> existEntities=getAllEntitiesFromBD(metaClass.getJavaClass(),temp_str);

        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            Query query=em.createQuery("DELETE FROM "+temp_str+" o",metaClass.getJavaClass());
            query.executeUpdate();
            tx.commit();
        }
        catch (Exception e){
            //logService.logException(e,Delete_work.class,"ERROR");
            errorList.add(e.getMessage());
        }
        return errorList;
    }

    private List<Object> getAllEntitiesFromBD(Object obj, String table)
    {
        List<Object> entity=null;

        try(Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            Query query = em.createQuery("SELECT o FROM "+table+" o",obj.getClass());
            entity= query.getResultList();
            tx.commit();
        }
        catch (Exception e){
            //logService.logException(e,Delete_work.class,"ERROR");
        }
        return entity;
    }

    public ArrayList<String> deleteEntityWithParam(HashMap<String,FileDescriptor> fileMap, MetaClass metaClass, int findType, int actionType, int assocActionType,
                                                   boolean alwaysCreate, HashMap<String,String> propValue, HashMap<String,String> allowedValue, String parameter){
        ArrayList<String> errorList = new ArrayList<>();

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
            return errorList;
        }

        exelWork.setCorrectSize();
        for (int i = 0; i < exelWork.row_count; ++i) {
            ArrayList<String> errorListTemp=new ArrayList<>();
            exelWork.getData(entityLocalName,errorListTemp);
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
        View viewT=import_work.getFullView(metaClass);
        viewsList.add(viewT);
        String table=metaClass.getName();

        List<Object> existEntities = import_work.getAllEntitiesFromBD(metaClass, table, viewsList,parameter);

        Boolean exist;

        int dataCount=exelWork.getDataCount();
        for (int i = 0; i < dataCount; ++i) {
            ArrayList<String> errorListTemp = new ArrayList<>();

            Boolean idFound=false;
            //преобразование данных в запись
            Entity entity = import_work.convertDataWithParam(errorListTemp, metaClass, propertyList, exelWork, propValue,findType,assocActionType);

            exist = false;
            errorList.addAll(errorListTemp);
            boolean allowed=true;
            for (Object existEntity : existEntities) {
                switch (findType) {
                    //поиск по InstanceName
                    case 0: {

                        if (((Entity) existEntity).getInstanceName().contentEquals(entity.getInstanceName())) {
                            exist = true;
                            for(Map.Entry entry:allowedValue.entrySet()){
                                if(!((Entity)existEntity).getValue(entry.getKey().toString()).toString().contentEquals(entry.getValue().toString())){
                                    allowed=false;
                                }
                            }
                            if(!allowed){
                                continue;
                            }
                            //import_work.copyToEntity(entity, (Entity) existEntity, propertyList);
                            //TODO сделать отдельный сервис для сообщений
                            try (Transaction tx = persistence.createTransaction()) {
                                EntityManager em = persistence.getEntityManager();
                                Query query=em.createQuery("DELETE FROM "+metadata.getClass(existEntity.getClass()).getName() +
                                " e WHERE e.id=:entityId",metadata.getClass(existEntity.getClass()).getJavaClass());
                                query.setParameter("entityId",((Entity) existEntity).getId());
                                query.executeUpdate();
                                tx.commit();
                            } catch (Exception e) {
                                //logService.logException(e,Import_work.class,"ERROR");
                                errorList.add(metaClass.getName() + " " + e.getMessage());
                                continue;
                            }
                            errorList.add(metaClass.getName() + " найденная запись InstanceName=" + entity.getInstanceName() + " удалена");
                        }
                        break;
                    }
                }
            }
            if (!exist) {
                errorList.add(metaClass.getName() + " запись InstanceName=" + entity.getInstanceName() + " не найдена");
            }
        }
        return errorList;
    }
}
