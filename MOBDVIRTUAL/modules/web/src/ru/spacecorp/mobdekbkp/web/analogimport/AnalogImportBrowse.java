package ru.spacecorp.mobdekbkp.web.analogimport;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.*;
import ru.spacecorp.mobdekbkp.web.typonominal.TnCardScreen;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.ImportFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnalogImportBrowse extends AbstractLookup implements ImportFrame {
    @Inject
    private Table<TyponominalAnalog> analogImportsTable;
    @Inject
    private CollectionDatasource<TyponominalAnalog, UUID> analogImportsDs;
    @Inject
    private ComponentsFactory componentsFactory;
    private Label noDsLabel;
    private AnalogImport analogImport;
    @Inject
    private Metadata metadata;
    private ImportDevice importDevice;
    private String labelValue;
    private AnalogImportBrowse thisframe;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        //vbox.add(noDsLabel);
    }

    @Override
    public void initFrame(ImportDevice importDevice) {
        if (importDevice != null) {
            analogImportsTable.setVisible(true);
            this.importDevice = importDevice;
            analogImportsDs.setQuery("select e from mobdekbkp$AnalogImport e where e.importDevice.id = '" +
                    importDevice.getId() + "'");
            analogImportsDs.refresh();
            analogImportsTable.setVisible(true);
            noDsLabel.setVisible(false);
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        //table.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onCreateBtnClick() {
        analogImport = metadata.create(AnalogImport.class);
        analogImport.setImportDevice(importDevice);
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("noedit", true);
        AbstractEditor ed = openEditor("mobdekbkp$AnalogImport.edit", analogImport, WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(importDevice));
    }

    public void onEdit(Component source) {
        AbstractEditor ed = openEditor("mobdekbkp$AnalogImport.edit", analogImportsDs.getItem(), WindowManager.OpenType.DIALOG);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(importDevice));
    }
}