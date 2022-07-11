package com.company.importmodule.service;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaModel;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

@Service(DBExportService.NAME)
public class DBExportServiceBean implements DBExportService {

    @Inject
    private Metadata metadata;

    //получение сущностей из БД
    @Override
    public Object[] getEntities(String packageName){
        MetaModel metmod=metadata.getModel(packageName);
        return metmod.getClasses().toArray();
    }

    //получение названий модулей
    @Override
    public ArrayList<MetaModel> getRootPackages()
    {
        return new ArrayList(metadata.getModels());
    }

}