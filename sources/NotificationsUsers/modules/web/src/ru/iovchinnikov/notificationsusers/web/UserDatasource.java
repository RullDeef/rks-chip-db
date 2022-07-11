package ru.iovchinnikov.notificationsusers.web;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.data.impl.CustomCollectionDatasource;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;

import java.util.*;

/**
 * Created by Stepanov_ME on 24.04.2018.
 */
public class UserDatasource extends CustomCollectionDatasource<ExtUser, UUID> {

    private LoadContext<ExtUser> loadContext;
    private DataManager dataManager = AppBeans.get(DataManager.NAME);

    @Override
    protected Collection<ExtUser> getEntities(Map params) {
        return (loadContext != null) ? dataManager.loadList(loadContext) : null;
    }

    @Override
    public Collection<ExtUser> getItems() {
        return (loadContext != null) ? dataManager.loadList(loadContext) : null;
    }

    @Override
    public MetaClass getMetaClass() {
        return metadata.getClass(ExtUser.class);
    }

    public void setLoadContext(LoadContext<ExtUser> loadContext) {
        this.loadContext = loadContext;
    }
}
