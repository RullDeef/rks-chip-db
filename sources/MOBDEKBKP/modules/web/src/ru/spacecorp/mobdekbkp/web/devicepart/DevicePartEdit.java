package ru.spacecorp.mobdekbkp.web.devicepart;

import com.haulmont.cuba.gui.components.*;

import javax.inject.Inject;
import javax.inject.Named;

import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.GroupDatasource;
import ru.spacecorp.mobdekbkp.entity.DevicePart;
import ru.spacecorp.mobdekbkp.entity.DevicePartListPlanned;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DevicePartEdit extends AbstractEditor<DevicePart> {
    @Named("documentTable.create")
    private CreateAction documentCreateBtn;
    @Named("plannedListsTable.create")
    private CreateAction plannedCreateBtn;
    @Named("plannedListsTable.edit")
    private EditAction plannedListsTableEdit;
    @Inject
    private GroupBoxLayout complectListGroupBox;
    @Inject
    private Datasource<DevicePart> devicePartDs;
    @Inject
    private GroupDatasource<DevicePartListPlanned, UUID> plannedListsDs;

    @Override
    protected void postInit() {
        
        if (getItem().getComplectLists() != null && getItem().getComplectLists().size() > 0) {
            complectListGroupBox.setVisible(true);
        }
        if (getItem().getCreatedBy() == null) {
            plannedCreateBtn.setEnabled(false);
        }

        plannedListsTableEdit.setAfterCommitHandler(actionId -> {
            devicePartDs.refresh();
            plannedListsDs.refresh();
        });
    }
}