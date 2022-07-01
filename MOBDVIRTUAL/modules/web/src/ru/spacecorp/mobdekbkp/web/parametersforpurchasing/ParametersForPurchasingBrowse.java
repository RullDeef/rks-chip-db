package ru.spacecorp.mobdekbkp.web.parametersforpurchasing;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.ImportDevice;
import ru.spacecorp.mobdekbkp.entity.ParametersForPurchasing;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.ImportFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParametersForPurchasingBrowse extends AbstractLookup implements ImportFrame {
    @Inject
    private Table<ParametersForPurchasing> parametersForPurchasingsTable;
    @Inject
    private CollectionDatasource<ParametersForPurchasing, UUID> parametersForPurchasingsDs;
    @Inject
    private Label noDsLabel;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Metadata metadata;
    @Inject
    private Button createBtn;
    private ParametersForPurchasing parameters;
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
            parametersForPurchasingsTable.setVisible(true);
            this.importDevice = importDevice;
            parametersForPurchasingsDs.setQuery("select e from mobdekbkp$ParametersForPurchasing e " +
                    "where e.importDevice.id = '" + importDevice.getId() + "'");
            parametersForPurchasingsDs.refresh();
            createBtn.setVisible(true);
            parametersForPurchasingsTable.setVisible(true);
            noDsLabel.setVisible(false);
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        parametersForPurchasingsTable.setVisible(false);
        createBtn.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onCreateBtnClick() {
        parameters = metadata.create(ParametersForPurchasing.class);
        parameters.setImportDevice(importDevice);
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("import", true);
        AbstractEditor ed = openEditor("mobdekbkp$ParametersForPurchasing.edit", parameters,
                WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(importDevice));
    }
}