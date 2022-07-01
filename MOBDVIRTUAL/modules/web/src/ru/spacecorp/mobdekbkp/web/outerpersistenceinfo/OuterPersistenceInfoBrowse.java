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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OuterPersistenceInfoBrowse extends AbstractLookup implements TyponominalFrame {
    @Inject
    private CollectionDatasource<OuterPersistenceInfo, UUID> outerPersistenceInfoesDs;
    @Inject
    private Table<OuterPersistenceInfo> outerPersistenceInfoesTable;
    @Inject
    private Button btnCreate;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Metadata metadata;

    private Label noDsLabel;
    private Typonominal tn;
    private OuterPersistenceInfoBrowse thisframe;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        thisframe = this;
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
//        noDsLabel.setVisible(true);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in Persistence info frame is null");
        this.tn = tn;

        outerPersistenceInfoesTable.setVisible(true);
        outerPersistenceInfoesDs.setQuery("select e from mobdekbkp$OuterPersistenceInfo e where e.typonominal.id = '" + tn.getId() + "'");
        outerPersistenceInfoesDs.refresh();
        btnCreate.setVisible(true);
        outerPersistenceInfoesTable.setVisible(true);
        noDsLabel.setVisible(false);
    }

    @Override
    public void clearFrame(String labelValue) {
        outerPersistenceInfoesTable.setVisible(false);
        btnCreate.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onCreateBtnClick() {
        OuterPersistenceInfo newItem = metadata.create(OuterPersistenceInfo.class);
        newItem.setTyponominal(tn);
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("noedit", true);
        AbstractEditor ed = openEditor("mobdekbkp$OuterPersistenceInfo.edit", newItem, WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn));
    }

    public void onEdit(Component source) {
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("noedit", true);
        AbstractEditor ed = openEditor("mobdekbkp$OuterPersistenceInfo.edit", outerPersistenceInfoesDs.getItem(), WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn));
    }
}