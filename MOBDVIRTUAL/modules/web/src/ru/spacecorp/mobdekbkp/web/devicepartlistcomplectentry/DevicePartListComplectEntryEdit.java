package ru.spacecorp.mobdekbkp.web.devicepartlistcomplectentry;

import com.haulmont.cuba.gui.components.AbstractEditor;

import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import ru.spacecorp.mobdekbkp.entity.DeviceListEntryStatus;
import ru.spacecorp.mobdekbkp.entity.DevicePartListComplectEntry;
import ru.spacecorp.mobdekbkp.entity.ExtBoolean;
import ru.spacecorp.mobdekbkp.service.ComplectDatasourceService;

import javax.inject.Inject;
import java.util.UUID;

public class DevicePartListComplectEntryEdit extends AbstractEditor<DevicePartListComplectEntry> {

    @Inject
    Datasource devicePartListComplectEntryDs;

    @Inject
    ComplectDatasourceService complectDatasourceService;

    @Override
    protected void initNewItem(DevicePartListComplectEntry item) {
        super.initNewItem(item);
        item.setByHeadExecutor(ExtBoolean.notSet);
        item.setStatus(DeviceListEntryStatus.inProcess);
    }

    @Override
    protected void postInit() {
        super.postInit();
        UUID uuid = ((DevicePartListComplectEntry) devicePartListComplectEntryDs.getItem()).getDevicePartListComplect().getId();
        complectDatasourceService.setObject(uuid);
    }
}