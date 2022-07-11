package com.spacecorp.rulesmodule.service;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaModel;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

@Service(DBExportService.NAME)
public class DBExportServiceBean implements DBExportService {
    @Inject
    private Persistence persistence;

    @Inject
    private Metadata metadata;

    @Inject
    private ViewRepository viewRepository;

    //получение названий модулей
    @Override
    public ArrayList<MetaModel> getRootPackages()
    {
        return new ArrayList(metadata.getModels());
    }

    //получение сущностей из БД
    @Override
    public Object[] getEntities(String packageName){
        MetaModel metmod=metadata.getModel(packageName);
        return metmod.getClasses().toArray();
    }


    @Override
    public List<Object> getQueryResult(String queryString, MetaClass metaClass, String viewString)
    {
        List<Object> objLst=null;
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            Query query = em.createQuery(queryString, metaClass.getJavaClass());
            if(viewString!=null) {
                query.setView(viewRepository.getView(metaClass, viewString));
            }
            objLst = query.getResultList();
            tx.commit();
        }
        catch (Exception e){
            //logService.logException(e,DBExportServiceBean.class,"ERROR");
            return null;
        }
        return objLst;
    }

    @Override
    public List<Object> getRecordByParam(String expression, String table, Object obj, View view) {
        List<Object> objLst;
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            Query query = em.createQuery("SELECT o FROM " + table + " o WHERE " + expression, obj.getClass());
            query.addView(view);
            objLst = query.getResultList();
            tx.commit();
        }
        catch (Exception e){
            //logService.logException(e,DBExportServiceBean.class,"ERROR");
            return null;
        }
        return objLst;
    }
}