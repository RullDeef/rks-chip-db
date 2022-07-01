package ru.spacecorp.mobdekbkp.web.customparameters;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.CustomParameters;
import ru.spacecorp.mobdekbkp.entity.MainParameters;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.TyponominalFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomParametersBrowse extends AbstractFrame implements TyponominalFrame {

    @Inject
    private Table<MainParameters> mainParametersesTable;
    @Inject
    private CollectionDatasource<MainParameters, UUID> mainParametersesDs;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private DataManager dataManager;

    @Inject
    private TextField tfInstallMethod;
    @Inject
    private Table<CustomParameters> table;
    @Inject
    private CollectionDatasource<CustomParameters, UUID> customParametersDs;
    CustomParameters customParameters;
    private Typonominal tn;
    private Label noDsLabel;
    private String labelValue;
    @Inject
    private Metadata metadata;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        noDsLabel.setVisible(true);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn != null) {
            table.setVisible(true);
            mainParametersesTable.setVisible(true);
            this.tn = tn;
            mainParametersesDs.setQuery("select e from mobdekbkp$MainParameters e " +
                    "where e.typonominal.id = '" + tn.getId() + "'");
            mainParametersesDs.refresh();
            customParametersDs.setQuery("select e from mobdekbkp$CustomParameters e " +
                    "where e.typonominal.id = '" + tn.getId() + "'");
            customParametersDs.refresh();
            table.setVisible(true);
            noDsLabel.setVisible(false);
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);

        if (tn == null) return;
        //showAll(false);
    }

    public void onCreateParamClick() {
        customParameters = metadata.create(CustomParameters.class);
        customParameters.setTyponominal(tn);
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("noedit", true);
        AbstractEditor ed = openEditor("mobdekbkp$CustomParameters.edit", customParameters,
                WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(tn));
    }
}