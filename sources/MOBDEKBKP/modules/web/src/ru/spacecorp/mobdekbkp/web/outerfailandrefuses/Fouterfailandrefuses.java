package ru.spacecorp.mobdekbkp.web.outerfailandrefuses;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.OuterFailAndRefuses;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.web.outerdbfail.Fouterdbfail;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.TyponominalFrame;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class Fouterfailandrefuses extends AbstractFrame implements TyponominalFrame{
    @Inject private Button btnCreate;
    @Inject private Label label;
    @Inject private Table tblOuterFailAndRefuses;
    @Inject private VBoxLayout vbMain;
    @Inject private CollectionDatasource<OuterFailAndRefuses, UUID> outerFailAndRefusesesDs;
    @Inject private ComponentsFactory componentsFactory;
    @Inject private Metadata metadata;
    private Typonominal tn;
    private Label noDsLabel;
    private String labelValue;
    private Fouterfailandrefuses thisframe;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        thisframe = this;
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        vbMain.add(noDsLabel);
        label.setStyleName("label_tests");
        tblOuterFailAndRefuses.setStyleName("table_tests");
    }


    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in TnBasicInfo frame is null");
        this.tn = tn;
        outerFailAndRefusesesDs.setQuery("select e from mobdekbkp$OuterFailAndRefuses e where e.typonominal.id = '" + tn.getId() + "'");
        outerFailAndRefusesesDs.refresh();
        if (outerFailAndRefusesesDs.size() == 0) {
            clearFrame(labelValue);
            btnCreate.setVisible(true);
            return;
        }
        btnCreate.setVisible(true);
        tblOuterFailAndRefuses.setStyleName("table-size");
        tblOuterFailAndRefuses.setVisible(true);
        noDsLabel.setVisible(false);
    }


    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        tblOuterFailAndRefuses.setVisible(false);
        btnCreate.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onBtnCreateClick() {
        OuterFailAndRefuses newItem = metadata.create(OuterFailAndRefuses.class);
        newItem.setTyponominal(tn);
        AbstractEditor ed = openEditor("mobdekbkp$OuterFailAndRefuses.edit", newItem, WindowManager.OpenType.DIALOG);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn) );
    }

    public void onEdit() {
        AbstractEditor ed = openEditor("mobdekbkp$OuterFailAndRefuses.edit", outerFailAndRefusesesDs.getItem(), WindowManager.OpenType.DIALOG);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn) );

    }


}