package com.spacecorp.rulesmodule.service;


import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaModel;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.View;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public interface DBExportService {
    String NAME = "rulesmodule_DBExportService";

    Object[] getEntities(String packageName);

    ArrayList<MetaModel> getRootPackages();

    List<Object> getQueryResult(String query, MetaClass metaClass, String viewString);

    List<Object> getRecordByParam(String expression,String table,Object obj,View view);
}