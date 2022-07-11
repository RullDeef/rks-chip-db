package ru.spacecorp.mobdekbkp.web.outerdbfail;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.OuterDbFail;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.TyponominalFrame;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class Fouterdbfail extends AbstractFrame implements TyponominalFrame {
    @Inject private Button btnCreate;
    @Inject private Metadata metadata;
    @Inject private Label label;
    @Inject private Table<OuterDbFail> tblOuterDbFail;
    @Inject private VBoxLayout vbMain;
    @Inject private CollectionDatasource<OuterDbFail, UUID> outerDbFailsDs;
    @Inject private ComponentsFactory componentsFactory;
    private Typonominal tn;
    private Label noDsLabel;
    private String labelValue;
    private Fouterdbfail thisframe;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        thisframe = this;
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        vbMain.add(noDsLabel);
        label.setStyleName("label_tests");
        tblOuterDbFail.setStyleName("table_tests");
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in TnBasicInfo frame is null");
        this.tn = tn;
        outerDbFailsDs.setQuery("select e from mobdekbkp$OuterDbFail e where e.typonominal.id = '" + tn.getId() + "'");
        outerDbFailsDs.refresh();
        if (outerDbFailsDs.size() == 0) {
            clearFrame(labelValue);
            btnCreate.setVisible(true);
            return;
        }
        btnCreate.setVisible(true);
        tblOuterDbFail.setStyleName("table-size");
        tblOuterDbFail.setVisible(true);
        noDsLabel.setVisible(false);
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        tblOuterDbFail.setVisible(false);
        btnCreate.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onBtnCreateClick() {
        OuterDbFail newItem = metadata.create(OuterDbFail.class);
        newItem.setTyponominal(tn);
        AbstractEditor ed = openEditor("mobdekbkp$OuterDbFail.edit", newItem, WindowManager.OpenType.DIALOG);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn) );
    }

    public void onEdit() {
        AbstractEditor ed = openEditor("mobdekbkp$OuterDbFail.edit", outerDbFailsDs.getItem(), WindowManager.OpenType.DIALOG);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn) );
    }
}