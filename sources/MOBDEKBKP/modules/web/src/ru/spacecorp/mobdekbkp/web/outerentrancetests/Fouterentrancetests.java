package ru.spacecorp.mobdekbkp.web.outerentrancetests;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.impl.DatasourceImplementation;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.OuterCertificateTests;
import ru.spacecorp.mobdekbkp.entity.OuterEntranceTests;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.web.outercertificatetests.Foutercertificatetests;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.TyponominalFrame;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class Fouterentrancetests extends AbstractFrame implements TyponominalFrame{
    @Inject private ComponentsFactory componentsFactory;
    @Inject private Metadata metadata;
    @Inject private CollectionDatasource<OuterEntranceTests, UUID> outerEntranceTestsesDs;
    @Inject private Button btnCreate;
    @Inject private Table<OuterEntranceTests> tblOuterentrancetests;
    @Inject private VBoxLayout vbMain;
    @Inject private Label label;
    @Inject private Button btDelete;

    private Typonominal tn;
    private Label noDsLabel;
    private String labelValue;
    private Fouterentrancetests thisframe;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        thisframe = this;
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        vbMain.add(noDsLabel);
        label.setStyleName("label_tests");
        tblOuterentrancetests.setStyleName("table_tests");
        btDelete.setVisible(false);

    }


    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in TnBasicInfo frame is null");
        this.tn = tn;
        outerEntranceTestsesDs.setQuery("select e from mobdekbkp$OuterEntranceTests e where e.typonominal.id = '" + tn.getId() + "'");
        outerEntranceTestsesDs.refresh();
        if (outerEntranceTestsesDs.size() == 0) {
            clearFrame(labelValue);
            btnCreate.setVisible(true);
            return;
        }
        btnCreate.setVisible(true);
        tblOuterentrancetests.setStyleName("table-size");
        btDelete.setVisible(true);
        tblOuterentrancetests.setVisible(true);
        noDsLabel.setVisible(false);
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        tblOuterentrancetests.setVisible(false);
        btDelete.setVisible(false);
        btnCreate.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }


    public void onBtnCreateClick() {
        OuterEntranceTests newItem = metadata.create(OuterEntranceTests.class);
        newItem.setTyponominal(tn);
        AbstractEditor ed = openEditor("mobdekbkp$OuterEntranceTests.edit", newItem, WindowManager.OpenType.DIALOG);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn) );
    }

    public void onEdit(Component source) {
        AbstractEditor ed = openEditor("mobdekbkp$OuterEntranceTests.edit", outerEntranceTestsesDs.getItem(), WindowManager.OpenType.DIALOG);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn) );
    }

}