package ru.spacecorp.mobdekbkp.web.devicepartlistplanned;

import com.google.common.collect.ImmutableMap;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.data.GroupDatasource;
import ru.spacecorp.mobdekbkp.entity.DevicePartListPlanned;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class DevicePartListPlannedBrowse extends AbstractLookup {

    @Inject private GroupDatasource<DevicePartListPlanned,UUID> devicePartListPlannedsDs;
    @Inject private Button readBtn;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        readBtn.setEnabled(false);
        devicePartListPlannedsDs.addItemChangeListener(e -> {
            if(e.getItem()!=null){
                readBtn.setEnabled(true);
            }
            else {
                readBtn.setEnabled(false);
            }
        });
    }

    public void onReadBtnClick() {
        if(devicePartListPlannedsDs.getItem()!=null){
            openEditor(devicePartListPlannedsDs.getItem(),
                    WindowManager.OpenType.NEW_WINDOW, ImmutableMap.of("readOnly",true));
        }
    }
}