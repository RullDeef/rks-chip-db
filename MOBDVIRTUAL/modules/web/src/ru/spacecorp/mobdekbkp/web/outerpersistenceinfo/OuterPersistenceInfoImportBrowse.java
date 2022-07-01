package ru.spacecorp.mobdekbkp.web.outerpersistenceinfo;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.ImportDevice;
import ru.spacecorp.mobdekbkp.entity.OuterPersistenceInfo;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.ImportFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OuterPersistenceInfoImportBrowse extends AbstractLookup implements ImportFrame {
    @Inject
    private Table<OuterPersistenceInfo> outerPersistenceInfoesTable;
    @Inject
    private CollectionDatasource<OuterPersistenceInfo, UUID> outerPersistenceInfoesDs;
    @Inject
    private Label noDsLabel;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Metadata metadata;
    @Inject
    private Button createBtn;
    private OuterPersistenceInfo outerPersistenceInfo;
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
            outerPersistenceInfoesTable.setVisible(true);
            this.importDevice = importDevice;
            outerPersistenceInfoesDs.setQuery("select e from mobdekbkp$OuterPersistenceInfo e " +
                    "where e.importDevice.id = '" + importDevice.getId() + "'");
            outerPersistenceInfoesDs.refresh();
            createBtn.setVisible(true);
            outerPersistenceInfoesTable.setVisible(true);
            noDsLabel.setVisible(false);
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        outerPersistenceInfoesTable.setVisible(false);
        createBtn.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onCreateBtnClick() {
        outerPersistenceInfo = metadata.create(OuterPersistenceInfo.class);
        outerPersistenceInfo.setImportDevice(importDevice);
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("import", true);
        AbstractEditor ed = openEditor("mobdekbkp$OuterPersistenceInfo.edit", outerPersistenceInfo,
                WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(importDevice));
    }
}