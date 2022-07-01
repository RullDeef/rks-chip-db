package ru.spacecorp.mobdekbkp.web.typonominal.frames.AnalogFrame;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.entity.TyponominalAnalog;
import ru.spacecorp.mobdekbkp.web.typonominal.TnCardScreen;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.TyponominalFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Analogframe extends AbstractFrame implements TyponominalFrame {

    @Inject
    private Table<TyponominalAnalog> table;
    @Inject
    private CollectionDatasource<TyponominalAnalog, UUID> typonominalAnalogsDs;
    @Inject
    private VBoxLayout vbox;
    @Inject
    private ComponentsFactory componentsFactory;
    private Label noDsLabel;
    private TyponominalAnalog typonominalAnalog;
    @Inject
    private Metadata metadata;
    @Inject
    private Button openEditorBtn;
    private Typonominal tn;
    private String labelValue;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        //vbox.add(noDsLabel);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn != null) {
            table.setVisible(true);
            this.tn = tn;
            typonominalAnalogsDs.setQuery("select e from mobdekbkp$TyponominalAnalog e where e.parentTyponominal.id = '" + tn.getId() + "'");
            typonominalAnalogsDs.refresh();
            if (typonominalAnalogsDs.size() == 0) {
                clearFrame(labelValue);
                openEditorBtn.setVisible(true);
                return;
            }
            openEditorBtn.setVisible(true);
            table.setVisible(true);
            noDsLabel.setVisible(false);
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        this.labelValue = labelValue;
        //table.setVisible(false);
        openEditorBtn.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }

    public void onOpenEditorBtnClick() {
        typonominalAnalog = metadata.create(TyponominalAnalog.class);
        typonominalAnalog.setParentTyponominal(tn);
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("noedit", true);
        AbstractEditor ed = openEditor("mobdekbkp$TyponominalAnalog.edit", typonominalAnalog, WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(tn));
    }

    public void onEdit(Component source) {
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put(TnCardScreen.TYPONOMINAL, typonominalAnalogsDs.getItem().getTyponominal());
        openWindow("tn-card-screen", WindowManager.OpenType.DIALOG, paramOpen);
    }
}