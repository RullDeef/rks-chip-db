package ru.spacecorp.mobdekbkp.web.advancedsetting;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.AdvancedSetting;
import ru.spacecorp.mobdekbkp.entity.ImportDevice;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.ImportFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AdvancedSettingBrowse extends AbstractLookup implements ImportFrame {
    @Inject
    private Table<AdvancedSetting> advancedSettingsTable;
    @Inject
    private CollectionDatasource<AdvancedSetting, UUID> advancedSettingsDs;
    @Inject
    private Label noDsLabel;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Metadata metadata;
    @Inject
    private Button createBtn;
    private AdvancedSetting advancedSetting;
    private ImportDevice importDevice;
    private String labelValue;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
    }

    @Override
    public void initFrame(ImportDevice importDevice) {
        if (importDevice != null) {
            advancedSettingsTable.setVisible(true);
            this.importDevice = importDevice;
            advancedSettingsDs.setQuery("select e from mobdekbkp$AdvancedSetting e " +
                    "where e.importDevice.id = '" + importDevice.getId() + "'");
            advancedSettingsDs.refresh();
            createBtn.setVisible(true);
            advancedSettingsTable.setVisible(true);
            noDsLabel.setVisible(false);
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        advancedSettingsTable.setVisible(false);
        createBtn.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onCreateBtnClick() {
        advancedSetting = metadata.create(AdvancedSetting.class);
        advancedSetting.setImportDevice(importDevice);
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("noedit", true);
        AbstractEditor ed = openEditor("mobdekbkp$AdvancedSetting.edit", advancedSetting,
                WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(importDevice));
    }
}