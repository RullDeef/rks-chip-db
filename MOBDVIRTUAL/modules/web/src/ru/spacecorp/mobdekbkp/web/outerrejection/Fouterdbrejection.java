package ru.spacecorp.mobdekbkp.web.outerrejection;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.OuterDbRefuse;
import ru.spacecorp.mobdekbkp.entity.OuterRejection;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.TyponominalFrame;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class Fouterdbrejection extends AbstractFrame implements TyponominalFrame {

    @Inject
    private Button btnCreate;
    @Inject
    private Label label;
    @Inject
    private Table<OuterRejection> tblOuterRejection;
    @Inject
    private VBoxLayout vbMain;
    @Inject
    private CollectionDatasource<OuterRejection, UUID> outerRejectionDs;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Metadata metadata;
    @Inject
    private Button btDelete;
    private Typonominal tn;
    private Label noDsLabel;
    private String labelValue;
    private Fouterdbrejection thisframe;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        thisframe = this;
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        vbMain.add(noDsLabel);
        label.setStyleName("label_tests");
        tblOuterRejection.setStyleName("table_tests");
        btDelete.setVisible(false);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in TnBasicInfo frame is null");

        this.tn = tn;

        outerRejectionDs.setQuery("select e from mobdekbkp$OuterRejection e where e.typonominal.id = '" + tn.getId() + "'");
        outerRejectionDs.refresh();
        if (outerRejectionDs.size() == 0) {
            clearFrame(labelValue);
            btnCreate.setVisible(true);
            return;
        }
        btnCreate.setVisible(true);
        btDelete.setVisible(true);
        tblOuterRejection.setVisible(true);
        noDsLabel.setVisible(false);

    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        tblOuterRejection.setVisible(false);
        btDelete.setVisible(false);
        btnCreate.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onBtnCreateClick() {
        OuterDbRefuse newItem = metadata.create(OuterDbRefuse.class);
        newItem.setTyponominal(tn);
        AbstractEditor ed = openEditor("mobdekbkp$OuterRejection.edit", newItem, WindowManager.OpenType.DIALOG);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn));

    }

    public void onEdit(Component source) {
        AbstractEditor ed = openEditor("mobdekbkp$OuterRejection.edit", outerRejectionDs.getItem(), WindowManager.OpenType.DIALOG);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn));
    }

}