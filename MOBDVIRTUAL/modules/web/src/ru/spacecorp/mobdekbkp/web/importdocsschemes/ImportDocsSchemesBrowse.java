package ru.spacecorp.mobdekbkp.web.importdocsschemes;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.ImportDevice;
import ru.spacecorp.mobdekbkp.entity.ImportDocsSchemes;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.ImportFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ImportDocsSchemesBrowse extends AbstractLookup implements ImportFrame {
    @Inject
    private Table<ImportDocsSchemes> importDocsSchemesesTable;
    @Inject
    private CollectionDatasource<ImportDocsSchemes, UUID> importDocsSchemesesDs;
    @Inject
    private Label noDsLabel;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Metadata metadata;
    @Inject
    private Button createBtn;
    private ImportDocsSchemes docsSchemes;
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
            importDocsSchemesesTable.setVisible(true);
            this.importDevice = importDevice;
            importDocsSchemesesDs.setQuery("select e from mobdekbkp$ImportDocsSchemes e " +
                    "where e.importDevice.id = '" + importDevice.getId() + "'");
            importDocsSchemesesDs.refresh();
            createBtn.setVisible(true);
            importDocsSchemesesTable.setVisible(true);
            noDsLabel.setVisible(false);
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        importDocsSchemesesTable.setVisible(false);
        createBtn.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onCreateBtnClick() {
        docsSchemes = metadata.create(ImportDocsSchemes.class);
        docsSchemes.setImportDevice(importDevice);
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("noedit", true);
        AbstractEditor ed = openEditor("mobdekbkp$ImportDocsSchemes.edit", docsSchemes,
                WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(importDevice));
    }
}