package ru.spacecorp.mobdekbkp.web.outerdbrefuse;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.OuterDbRefuse;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.TyponominalFrame;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class Fouterdbrefuse extends AbstractFrame implements TyponominalFrame {

    @Inject private CollectionDatasource<OuterDbRefuse, UUID> outerDbRefuseDs;
    @Inject private Table<OuterDbRefuse> tblOuterDbRefuse;
    @Inject private Metadata metadata;
    @Inject private ComponentsFactory componentsFactory;
    @Inject private VBoxLayout vbMain;
    @Inject private Label label;
    @Inject private Button btnCreate;
    @Inject private Button btDelete;
    private Typonominal tn;
    private Label noDsLabel;
    private String labelValue;
    private Fouterdbrefuse thisframe;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        thisframe = this;
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        vbMain.add(noDsLabel);
        label.setStyleName("label_tests");
        tblOuterDbRefuse.setStyleName("table_tests");
        btDelete.setVisible(false);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in TnBasicInfo frame is null");

        this.tn = tn;

        outerDbRefuseDs.setQuery("select e from mobdekbkp$OuterDbRefuse e where e.typonominal.id = '" + tn.getId() + "'");
        outerDbRefuseDs.refresh();
        if (outerDbRefuseDs.size() == 0) {
            clearFrame(labelValue);
            btnCreate.setVisible(true);
            return;
        }
        btnCreate.setVisible(true);
        btDelete.setVisible(true);
        tblOuterDbRefuse.setVisible(true);
        noDsLabel.setVisible(false);
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        tblOuterDbRefuse.setVisible(false);
        btnCreate.setVisible(false);
        btDelete.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onBtnCreateClick() {
        OuterDbRefuse newItem = metadata.create(OuterDbRefuse.class);
        newItem.setTyponominal(tn);
        AbstractEditor ed = openEditor("mobdekbkp$OuterDbRefuse.edit", newItem, WindowManager.OpenType.DIALOG);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn) );

    }

    public void onEdit(Component source) {
        AbstractEditor ed = openEditor("mobdekbkp$OuterDbRefuse.edit", outerDbRefuseDs.getItem(), WindowManager.OpenType.DIALOG);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn) );
    }

}