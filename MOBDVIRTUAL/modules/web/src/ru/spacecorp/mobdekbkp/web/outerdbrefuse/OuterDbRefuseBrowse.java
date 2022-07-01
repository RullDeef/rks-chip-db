package ru.spacecorp.mobdekbkp.web.outerdbrefuse;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.ImportDevice;
import ru.spacecorp.mobdekbkp.entity.OuterDbRefuse;
import ru.spacecorp.mobdekbkp.entity.OuterRejection;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.ImportFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OuterDbRefuseBrowse extends AbstractLookup implements ImportFrame {
    @Inject
    private Table<OuterDbRefuse> outerDbRefusesTable;
    @Inject
    private CollectionDatasource<OuterDbRefuse, UUID> outerDbRefusesDs;
    @Inject
    private Table<OuterRejection> outerRejectionsTable;
    @Inject
    private CollectionDatasource<OuterRejection, UUID> outerRejectionsDs;
    @Inject
    private Label noDsLabel;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Metadata metadata;
    @Inject
    private Button createBtn;
    private OuterDbRefuse outerDbRefuse;
    @Inject
    private Button createButton;
    private OuterRejection outerRejection;
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
            this.importDevice = importDevice;
            outerDbRefusesDs.setQuery("select e from mobdekbkp$OuterDbRefuse e " +
                    "where e.importDevice.id = '" + importDevice.getId() + "'");
            outerDbRefusesDs.refresh();
            outerRejectionsDs.setQuery("select e from mobdekbkp$OuterRejection e " +
                    "where e.importDevice.id = '" + importDevice.getId() + "'");
            outerRejectionsDs.refresh();
            createBtn.setVisible(true);
            outerDbRefusesTable.setVisible(true);
            createButton.setVisible(true);
            outerRejectionsTable.setVisible(true);
            noDsLabel.setVisible(false);
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        createBtn.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onCreateOuterDbRefuseClick() {
        outerDbRefuse = metadata.create(OuterDbRefuse.class);
        outerDbRefuse.setImportDevice(importDevice);
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("import", true);
        AbstractEditor ed = openEditor("mobdekbkp$OuterDbRefuse.edit", outerDbRefuse,
                WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(importDevice));
    }

    public void onCreateOuterRejectionClick() {
        outerRejection = metadata.create(OuterRejection.class);
        outerRejection.setImportDevice(importDevice);
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("import", true);
        AbstractEditor ed = openEditor("mobdekbkp$OuterRejection.edit", outerRejection,
                WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(importDevice));
    }
}