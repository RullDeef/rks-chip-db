package ru.spacecorp.mobdekbkp.web.devicepartlistcomplect;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.spacecorp.rulesmodule.web.functionLib.FunctionLib;
import ru.spacecorp.mobdekbkp.entity.*;
import ru.spacecorp.mobdekbkp.service.AddDeviceComplectService;
import ru.spacecorp.mobdekbkp.service.ComplectDatasourceService;
import ru.spacecorp.mobdekbkp.web.typonominal.TnCardFrame;
import ru.spacecorp.mobdekbkp.web.typonominal.TnCardScreen;

import java.util.*;

public class DevicePartListComplectEdit extends AbstractEditor<DevicePartListComplect> {

    @Inject private AddDeviceComplectService deviceComplectService;
    @Named("documentTableComplect.create") private CreateAction createActionComplect;
    @Inject private Table<DevicePartListComplectEntry> partListComplectEntriesTable;
    @Inject private GroupDatasource<DevicePartListComplectEntry, UUID> entriesDs;
    @Inject private Datasource<DevicePartListComplect> devicePartListComplectDs;
    @Inject private CollectionDatasource typonominalsCustDs;
    @Inject private TextField amountDeliveredField;
    @Inject private TextField amountInDeviceField;
    @Inject private TextField amountTotalField;
    @Inject private DateField dateFactField;
    @Inject private DateField datePlannedField;
    @Inject private ResizableTextArea questionsArea;
    @Inject private ComplectDatasourceService complectDatasourceService;

    private DevicePartListComplectEdit thiswindow;

    public void onCopyProjectListButtonClick() {
        getItem().setEntries(deviceComplectService.addPartListComplectEntry(getItem()));
    }

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        FunctionLib functionLib=new FunctionLib();
        partListComplectEntriesTable.setStyleProvider(new GroupTable.GroupStyleProvider<Entity>() {
            @Nullable
            @Override
            public String getStyleName(GroupInfo info) {
                return null;
            }

            @Override
            public String getStyleName(Entity entity, @Nullable String property) {
                return functionLib.getStyleName(entity,property);
            }
        });

        partListComplectEntriesTable.addGeneratedColumn(getMessage("recommendations"), functionLib::getMsgField);
    }

    @Override
    protected void postInit() {
        thiswindow = this;

        complectDatasourceService.setObject(devicePartListComplectDs.getItem().getId());
    }


    public void onViewTn(Component source) {
        Map<String, Object> valueTyponominal = new HashMap<>();
        Typonominal tn = partListComplectEntriesTable.getSingleSelected().getTyponominal();
        valueTyponominal.put(TnCardScreen.TYPONOMINAL, tn);
        valueTyponominal.put(TnCardFrame.OPTION_PARAM, TnCardFrame.ALL_TABS);
        openWindow("tn-card-screen", WindowManager.OpenType.DIALOG, valueTyponominal);
    }


    public void onEditEntry(Component source) {
        DevicePartListComplectEntry entry = partListComplectEntriesTable.getSingleSelected();
        if (entry == null) return;
        AbstractEditor ed = openEditor(entry, WindowManager.OpenType.DIALOG);
        ed.addCloseWithCommitListener(() -> {
            devicePartListComplectDs.refresh();
            entriesDs.refresh();
            amountDeliveredField.setValue(entriesDs.getItem(entry.getId()).getAmountDelivered());
            amountInDeviceField.setValue(entriesDs.getItem(entry.getId()).getAmountInDevice());
            amountTotalField.setValue(entriesDs.getItem(entry.getId()).getAmountTotal());
            dateFactField.setValue(entriesDs.getItem(entry.getId()).getDateFact());
            datePlannedField.setValue(entriesDs.getItem(entry.getId()).getDatePlanned());
            questionsArea.setValue(entriesDs.getItem(entry.getId()).getQuestions());
//            thiswindow.close("externally");
        });
    }
}