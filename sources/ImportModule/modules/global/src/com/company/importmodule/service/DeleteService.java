package com.company.importmodule.service;


import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.entity.FileDescriptor;

import java.util.ArrayList;
import java.util.HashMap;

public interface DeleteService {
    String NAME = "importmodule_DeleteService";

    ArrayList<String> deleteAll(ArrayList<MetaClass> metaClasses);

    ArrayList<String> delete(String selected,ArrayList<MetaClass> metaClasses);

    ArrayList<String> deleteFromFile(HashMap<String,FileDescriptor> fileMap, String selected, ArrayList<MetaClass> metaClasses, int find, int merge,
                                     int assocActionType, boolean alwaysCreate, HashMap<String,String> param,
                                     HashMap<String,String> allowedValue, String parameter);
}