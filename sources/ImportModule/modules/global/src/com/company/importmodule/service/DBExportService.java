package com.company.importmodule.service;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaModel;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.View;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public interface DBExportService {
    String NAME = "importmodule_DBExportService";

    //Object[] getEntities();

    Object[] getEntities(String packageName);

    /*List<Object> getRecords(String table, Object obj, View view);

    List<Object> getRecordsWithView(String table, String properties);

    List<Object> getRecordByParam(String expression, String table, Object obj);

    List<Object> getRecordByParam(String expression, String table, Object obj, View view);

    List<Object> getRecordByParam(String expression, String table, Object obj, ArrayList<View> viewsList);*/


    ArrayList<MetaModel> getRootPackages();

    //List<Object> getQueryResult(String query, MetaClass metaClass);
}
