package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.HandbookCad;
import ru.spacecorp.mobdekbkp.entity.Typonominal;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public class Tnsapr extends AbstractFrame implements TyponominalFrame {
    @Inject
    private HBoxLayout hbox;
    private Button bt;
    private Label lb;
    @Inject
    private ComponentsFactory componentsFactory;
    private Label noDsLabel;

    @Inject
    private ExportDisplay exportDisplay;
    @Inject
    private DataManager dataManager;

    private HandbookCad cad;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        if (bt == null)
            bt = componentsFactory.createComponent(Button.class);
        bt.setCaption("Загрузить электронную базу библиотек для САПР");

        LoadContext<HandbookCad> loadContext = LoadContext.create(HandbookCad.class);
        loadContext.setQuery(LoadContext.createQuery("select e from mobdekbkp$HandbookCad e"));
        loadContext.setView("handbookCad-view");
        ArrayList<HandbookCad> cads = new ArrayList<>(dataManager.loadList(loadContext));
        if (cads.size() != 0) {
            cad = cads.get(0);
        }

        bt.setAction(new BaseAction("downloadHandbook") {
            @Override
            public void actionPerform(Component component) {
                super.actionPerform(component);
                if (cad != null) {
                    exportDisplay.show(cads.get(0).getHandbook());
                }
            }
        });
        if (lb == null)
            lb = componentsFactory.createComponent(Label.class);
        if (cad != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            String lbValue = "Актуальная версия от " + formatter.format(cad.getCreateTs());
            lb.setValue(lbValue);
        }
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        hbox.add(bt);
        hbox.add(lb);
        hbox.add(noDsLabel);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn != null) {
            bt.setVisible(true);
            lb.setVisible(true);
            noDsLabel.setVisible(false);
        }

    }

    @Override
    public void clearFrame(String labelValue) {
        lb.setVisible(false);
        bt.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }
}