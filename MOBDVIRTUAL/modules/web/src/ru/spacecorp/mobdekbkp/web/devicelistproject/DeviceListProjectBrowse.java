package ru.spacecorp.mobdekbkp.web.devicelistproject;

import com.google.common.collect.ImmutableMap;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.spacecorp.rulesmodule.web.functionLib.FunctionLib;
import ru.spacecorp.mobdekbkp.entity.DeviceListProject;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class DeviceListProjectBrowse extends AbstractLookup {
    @Inject
    GroupTable<DeviceListProject> deviceListProjectsTable;
    @Inject
    GroupDatasource<DeviceListProject, UUID> deviceListProjectsDs;
    @Inject
    Button readBtn;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        readBtn.setEnabled(false);
        deviceListProjectsDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                readBtn.setEnabled(true);
            } else {
                readBtn.setEnabled(false);
            }
        });

        FunctionLib functionLib = new FunctionLib();
        deviceListProjectsTable.setStyleProvider(new GroupTable.GroupStyleProvider<Entity>() {
            @Nullable
            @Override
            public String getStyleName(GroupInfo info) {
                return null;
            }

            @Override
            public String getStyleName(Entity entity, @Nullable String property) {
                return functionLib.getStyleName(entity, property);
            }
        });

        deviceListProjectsTable.addGeneratedColumn(getMessage("recommendations"), functionLib::getMsgField);
    }


    public void onReadBtnClick() {
        if (deviceListProjectsDs.getItem() != null) {
            openEditor(deviceListProjectsDs.getItem(),
                    WindowManager.OpenType.NEW_WINDOW, ImmutableMap.of("readOnly", true));
        }
    }
}