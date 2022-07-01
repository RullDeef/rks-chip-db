package ru.spacecorp.mobdekbkp.web.basicinformation;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.BasicInformation;
import ru.spacecorp.mobdekbkp.entity.ImportDevice;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.ImportFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BasicInformationBrowse extends AbstractLookup implements ImportFrame {
    @Inject
    private Table<BasicInformation> basicInformationsTable;
    @Inject
    private CollectionDatasource<BasicInformation, UUID> basicInformationsDs;
    @Inject
    private Label noDsLabel;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Metadata metadata;
    @Inject
    private Button createBtn;
    private BasicInformation basicInformation;
    private ImportDevice importDevice;
    private String labelValue;
    private BasicInformationBrowse thisframe;

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
            basicInformationsTable.setVisible(true);
            this.importDevice = importDevice;
            basicInformationsDs.setQuery("select e from mobdekbkp$BasicInformation e " +
                    "where e.importDevice.id = '" + importDevice.getId() + "'");
            basicInformationsDs.refresh();
            createBtn.setVisible(true);
            basicInformationsTable.setVisible(true);
            noDsLabel.setVisible(false);
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        basicInformationsTable.setVisible(false);
        createBtn.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onCreateBtnClick() {
        basicInformation = metadata.create(BasicInformation.class);
        basicInformation.setImportDevice(importDevice);
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("import", true);
        AbstractEditor ed = openEditor("mobdekbkp$BasicInformation.edit", basicInformation,
                WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(importDevice));
    }
}