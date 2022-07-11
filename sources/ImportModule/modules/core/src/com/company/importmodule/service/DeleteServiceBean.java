package com.company.importmodule.service;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.Messages;
import org.springframework.stereotype.Service;
import  com.company.importmodule.core.Delete_work;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;

@Service(DeleteService.NAME)
public class DeleteServiceBean implements DeleteService {

    @Inject
    private Delete_work delete_work;

    @Inject
    private Messages messages;

    @Override
    public ArrayList<String> deleteAll(ArrayList<MetaClass> metaClasses)
    {
        ArrayList<String> errorList=new ArrayList<>();
        for(MetaClass metaClass:metaClasses)
        {
            errorList.addAll(delete_work.deleteEntity(metaClass));
        }
        return errorList;
    }

    @Override
    public ArrayList<String> delete(String selected,ArrayList<MetaClass> metaClasses)
    {
        ArrayList<String> errorList=new ArrayList<>();
        String[] entities=selected.split(",");

        for(MetaClass metaClass:metaClasses) {
            for(String entityName:entities) {
                if (metaClass.getName().contentEquals(entityName)) {
                    errorList.addAll(delete_work.deleteEntity(metaClass));
                }
            }
        }
        return errorList;
    }

    @Override
    public ArrayList<String> deleteFromFile(HashMap<String,FileDescriptor> fileMap, String selected, ArrayList<MetaClass> metaClasses, int find,
                                            int merge, int assocActionType, boolean alwaysCreate, HashMap<String,String> defaultValue,
                                            HashMap<String, String> allowedValue, String parameter) {
        ArrayList<String> errorList=new ArrayList<>();
        String[] entities=selected.split(",");

        for(MetaClass metaClass:metaClasses) {
            for(String entityName:entities) {
                if (messages.getMessage(metaClass.getJavaClass(),metaClass.getName()).contentEquals(entityName)) {
                    errorList.addAll(delete_work.deleteEntityWithParam(fileMap, metaClass,find,merge,assocActionType,alwaysCreate,defaultValue,allowedValue,parameter));
                }
            }
        }
        return errorList;
    }
}