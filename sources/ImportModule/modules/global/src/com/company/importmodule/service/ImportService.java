package com.company.importmodule.service;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface ImportService {
    String NAME = "importmodule_ImportService";

    ArrayList<String> ImportData(HashMap<String,FileDescriptor> fileMap, String selected,ArrayList<MetaClass> metaClasses, int find, int merge, int assocActionType, boolean alwaysCreate);

    ArrayList<String> ImportDataWithParam(HashMap<String,FileDescriptor> fileMap, String selected, ArrayList<MetaClass> metaClasses, int find, int merge,
                                          int assocActionType, boolean alwaysCreate,
                                          HashMap<String,String> param, HashMap<String,String> allowedValue,String parameter);

    HashMap<String,ArrayList<String>> checkExsistence(HashMap<String,FileDescriptor> fileMap, String selected, ArrayList<MetaClass> metaClasses,int findType);

    FileDescriptor getInfoFile(MetaClass metaClass,ArrayList<MetaProperty> selectedProperties) throws IOException;

    FileDescriptor getImportTable(HashMap<String,FileDescriptor> fileMap, MetaClass metaClass, ArrayList<MetaProperty> selectedProperties, int findType) throws IOException;

    boolean persistEntity(Entity entity);
}