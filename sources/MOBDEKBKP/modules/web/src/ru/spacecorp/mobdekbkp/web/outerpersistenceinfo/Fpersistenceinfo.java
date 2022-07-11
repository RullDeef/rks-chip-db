package ru.spacecorp.mobdekbkp.web.outerpersistenceinfo;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.OuterPersistenceInfo;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.TyponominalFrame;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class Fpersistenceinfo extends AbstractFrame implements TyponominalFrame {
    @Inject private CollectionDatasource<OuterPersistenceInfo, UUID> outerPersistenceInfoesDs;
    @Inject private VBoxLayout vbMain;
    @Inject private Table<OuterPersistenceInfo> tblPersistenceInfo;
    @Inject private Button btnCreate;
    @Inject private ComponentsFactory componentsFactory;
    @Inject private Metadata metadata;

    private Label noDsLabel;
    private Typonominal tn;
    private Fpersistenceinfo thisframe;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        thisframe = this;
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
//        noDsLabel.setVisible(true);
        vbMain.add(noDsLabel);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in Persistence info frame is null");
        this.tn = tn;

        tblPersistenceInfo.setVisible(true);
        outerPersistenceInfoesDs.setQuery("select e from mobdekbkp$OuterPersistenceInfo e where e.typonominal.id = '" + tn.getId() + "'");
        outerPersistenceInfoesDs.refresh();
        if (outerPersistenceInfoesDs.size() == 0) {
            clearFrame(getMessage("noDsLabel"));
            btnCreate.setVisible(true);
            return;
        }
        btnCreate.setVisible(true);
        noDsLabel.setVisible(false);
    }

    @Override
    public void clearFrame(String labelValue) {
        tblPersistenceInfo.setVisible(false);
        btnCreate.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onBtnCreateClick() {
        OuterPersistenceInfo newItem = metadata.create(OuterPersistenceInfo.class);
        newItem.setTyponominal(tn);
        AbstractEditor ed = openEditor("mobdekbkp$OuterPersistenceInfo.edit", newItem, WindowManager.OpenType.DIALOG);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn) );
    }
}