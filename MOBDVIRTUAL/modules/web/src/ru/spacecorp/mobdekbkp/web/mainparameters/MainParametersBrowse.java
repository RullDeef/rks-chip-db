package ru.spacecorp.mobdekbkp.web.mainparameters;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.ApplicabilityDevices;
import ru.spacecorp.mobdekbkp.entity.CustomParameters;
import ru.spacecorp.mobdekbkp.entity.ImportDevice;
import ru.spacecorp.mobdekbkp.entity.MainParameters;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.ImportFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainParametersBrowse extends AbstractLookup implements ImportFrame {
    @Inject
    private Table<MainParameters> mainParametersesTable;
    @Inject
    private CollectionDatasource<MainParameters, UUID> mainParametersesDs;
    @Inject
    private Table<CustomParameters> customParametersesTable;
    @Inject
    private CollectionDatasource<CustomParameters, UUID> customParametersesDs;
    @Inject
    private Label noDsLabel;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Metadata metadata;
    @Inject
    private Button createBtn;
    @Inject
    private Button createButton;
    private MainParameters mainParameters;
    private CustomParameters customParameters;
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
            mainParametersesTable.setVisible(true);
            customParametersesTable.setVisible(true);
            this.importDevice = importDevice;
            mainParametersesDs.setQuery("select e from mobdekbkp$MainParameters e " +
                    "where e.importDevice.id = '" + importDevice.getId() + "'");
            mainParametersesDs.refresh();
            customParametersesDs.setQuery("select e from mobdekbkp$CustomParameters e " +
                    "where e.importDevice.id = '" + importDevice.getId() + "'");
            customParametersesDs.refresh();
            createBtn.setVisible(true);
            createButton.setVisible(true);
            noDsLabel.setVisible(false);
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        mainParametersesTable.setVisible(false);
        createBtn.setVisible(false);
        customParametersesTable.setVisible(false);
        createButton.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onCreateMainClick() {
        mainParameters = metadata.create(MainParameters.class);
        mainParameters.setImportDevice(importDevice);
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("noedit", true);
        AbstractEditor ed = openEditor("mobdekbkp$MainParameters.edit", mainParameters,
                WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(importDevice));
        mainParametersesDs.refresh();
    }

    public void onCreateCustomClick() {
        customParameters = metadata.create(CustomParameters.class);
        customParameters.setImportDevice(importDevice);
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("noedit", true);
        AbstractEditor ed = openEditor("mobdekbkp$CustomParameters.edit", customParameters,
                WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(importDevice));
        customParametersesDs.refresh();
    }
}