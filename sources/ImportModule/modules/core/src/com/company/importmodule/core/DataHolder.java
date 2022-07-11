package com.company.importmodule.core;

import com.haulmont.cuba.core.entity.Entity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Stepanov_ME on 11.03.2019.
 */
public class DataHolder {

    private HashMap<String, HashMap<String, Object>> dataNameCaches;
    private HashMap<String, HashMap<String, Object>> dataIdCaches;

    public DataHolder() {
        dataNameCaches = new HashMap<>();
        dataIdCaches = new HashMap<>();
    }

    public void addCache(String tableName, List<Object> dataList) {
        HashMap<String, Object> cacheName = new HashMap<>();
        HashMap<String, Object> cacheId = new HashMap<>();
        for (Object obj : dataList) {
            Entity entity = (Entity) obj;
            String name = entity.getInstanceName();
            String id = entity.getId().toString();
            cacheName.put(name, obj);
            cacheId.put(id, obj);
        }
        dataNameCaches.put(tableName, cacheName);
        dataIdCaches.put(tableName, cacheId);
    }

    public Object getDataByName(String tableName, String instanceName) {
        HashMap<String, Object> cache = dataNameCaches.get(tableName);
        if (cache == null) {
            return null;
        }
        Object entityObj = cache.get(instanceName);
        return entityObj;
    }

    public Object getDataById(String tableName, String id) {
        HashMap<String, Object> cache = dataIdCaches.get(tableName);
        if (cache == null) {
            return null;
        }
        Object entityObj = cache.get(id);
        return entityObj;
    }

    public boolean hasCache(String tableName) {
        HashMap<String, Object> cacheName = dataNameCaches.get(tableName);
        HashMap<String, Object> cacheId = dataIdCaches.get(tableName);
        return !((cacheName == null) || (cacheId == null));
    }
}
