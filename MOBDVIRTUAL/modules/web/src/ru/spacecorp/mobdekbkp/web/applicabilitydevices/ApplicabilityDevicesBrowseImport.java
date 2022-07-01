package ru.spacecorp.mobdekbkp.web.applicabilitydevices;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.ApplicabilityDevices;
import ru.spacecorp.mobdekbkp.entity.ImportDevice;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.ImportFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ApplicabilityDevicesBrowseImport extends AbstractLookup implements ImportFrame {
    @Inject
    private Table<ApplicabilityDevices> table;
    @Inject
    private CollectionDatasource<ApplicabilityDevices, UUID> applicabilityDevicesesDs;
    @Inject
    private Label noDsLabel;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Metadata metadata;
    @Inject
    private Button createBtn;
    private ApplicabilityDevices applicabilityDevices;
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
            table.setVisible(true);
            this.importDevice = importDevice;
            applicabilityDevicesesDs.setQuery("select e from mobdekbkp$ApplicabilityDevices e " +
                    "where e.importDevice.id = '" + importDevice.getId() + "'");
            applicabilityDevicesesDs.refresh();
            createBtn.setVisible(true);
            table.setVisible(true);
            noDsLabel.setVisible(false);
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        table.setVisible(false);
        createBtn.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onCreateBtnClick() {
        applicabilityDevices = metadata.create(ApplicabilityDevices.class);
        applicabilityDevices.setImportDevice(importDevice);
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("import", true);
        AbstractEditor ed = openEditor("mobdekbkp$ApplicabilityDevices.edit", applicabilityDevices,
                WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(importDevice));
    }
}