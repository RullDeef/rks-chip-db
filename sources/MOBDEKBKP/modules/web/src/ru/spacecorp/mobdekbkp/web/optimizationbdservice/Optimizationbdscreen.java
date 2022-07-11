package ru.spacecorp.mobdekbkp.web.optimizationbdservice;

import com.haulmont.cuba.core.global.DataManager;

import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.security.entity.User;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;
import ru.spacecorp.mobdekbkp.service.OptimizationDBService;

import javax.inject.Inject;
import javax.management.Query;
import java.util.Map;
import java.util.UUID;
import com.haulmont.cuba.gui.components.Component;

public class Optimizationbdscreen extends AbstractWindow {
    @Inject private OptimizationDBService optimizationDBService;
    @Inject private CollectionDatasource<ExtUser, UUID> usersDs;
    @Inject private Table<ExtUser> tbUsers;
    @Inject private DataManager dataManager;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        tbUsers.setVisible(false);
    }

    public void onBtCompanyUsersClick() {
        dsUpdate();
        tbUsers.setVisible(true);
    }

    public void onEdit(Component source) {
        ExtUser extUser = usersDs.getItem();
        Companyscreen aw = (Companyscreen) openWindow("companyScreen", WindowManager.OpenType.DIALOG);
        aw.addCloseListener(actionId -> {
            if (aw.getCompany()!=null){
                extUser.setCompanyId(aw.getCompany().getId());
                extUser.setCompanyRef(aw.getCompany().getSmartName());
                usersDs.updateItem(extUser);
                usersDs.commit();
                dsUpdate();
            }
        });
    }

    private void  dsUpdate(){
        usersDs.clear();
        for (ExtUser user :optimizationDBService.getAndUpdateComponiFromUsers()) {
            usersDs.addItem(user);
        }
    }
}