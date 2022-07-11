package ru.spacecorp.mobdekbkp.web.device;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.security.global.UserSession;
import ru.spacecorp.mobdekbkp.entity.Device;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DeviceBrowse extends AbstractLookup {
    @Inject
    private GroupTable<Device> devicesTable;

    @Inject
    private UserSession userSession;

    @Inject
    private GroupDatasource<Device, UUID> devicesDs;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        devicesTable.addAction(new RemoveAction(devicesTable) {
            @Override
            protected boolean isApplicable() {
                boolean applicable = super.isApplicable();
                if (applicable)
                    for (Device item : devicesTable.getSelected())
                        if (!item.getCreatedBy().equals(userSession.getUser().getLogin()) && !userSession.getUser().getLogin().equals("admin"))
                            applicable = false;
                        return applicable;
            }
//            @Override
//            protected void remove(Set<Entity> selected) {
//                for (Entity item : selected){
//                    Device items = (Device) item;
//                    items.setIsArchive(true);
//                    devicesDs.updateItem(items);
//                    devicesDs.commit();
//                    devicesDs.refresh();
//                }
//                }
            }
        );

    }
}