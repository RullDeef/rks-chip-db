package ru.spacecorp.mobdekbkp.web.outercertificatetests;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.OuterCertificateTests;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.TyponominalFrame;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class Foutercertificatetests extends AbstractFrame implements TyponominalFrame {

    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionDatasource<OuterCertificateTests, UUID> outerCertificateTestsesDs;
    @Inject
    private Button btnCreate;
    @Inject
    private Table<OuterCertificateTests> tblOuterCertificateTests;
    @Inject
    private VBoxLayout vbMain;
    @Inject
    private Label label;
    @Inject
    private Button btDelete;
    private Typonominal tn;
    private Label noDsLabel;
    private String labelValue;
    private Foutercertificatetests thisframe;


    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        thisframe = this;
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        vbMain.add(noDsLabel);
        label.setStyleName("label_tests");
        tblOuterCertificateTests.setStyleName("table_tests");
        btDelete.setVisible(false);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in TnBasicInfo frame is null");

        this.tn = tn;

        outerCertificateTestsesDs.setQuery("select e from mobdekbkp$OuterCertificateTests e where e.typonominal.id = '" + tn.getId() + "'");
        outerCertificateTestsesDs.refresh();
        if (outerCertificateTestsesDs.size() == 0) {
            clearFrame(labelValue);
            btnCreate.setVisible(true);
            return;
        }
        btnCreate.setVisible(true);
        tblOuterCertificateTests.setVisible(true);
        btDelete.setVisible(true);
        noDsLabel.setVisible(false);
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        tblOuterCertificateTests.setVisible(false);
        btDelete.setVisible(false);
        btnCreate.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onBtnCreateClick() {
        OuterCertificateTests newItem = metadata.create(OuterCertificateTests.class);
        newItem.setTyponominal(tn);
        AbstractEditor ed = openEditor("mobdekbkp$OuterCertificateTests.edit", newItem, WindowManager.OpenType.DIALOG);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn));

    }

    public void onEdit(Component source) {
        AbstractEditor ed = openEditor("mobdekbkp$OuterCertificateTests.edit", outerCertificateTestsesDs.getItem(), WindowManager.OpenType.DIALOG);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn));
    }

}