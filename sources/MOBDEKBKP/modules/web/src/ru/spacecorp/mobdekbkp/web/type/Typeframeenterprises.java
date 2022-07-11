package ru.spacecorp.mobdekbkp.web.type;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.*;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.TyponominalFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Typeframeenterprises extends AbstractFrame implements TyponominalFrame {
    @Inject
    private Table<TypeCalicoholderEntry> tableCalculatorHolder;
    @Inject
    private Table<TypeManufacturerEntry> tableManufacturers;
    @Inject
    private Table<TypeProviderEntry> tableProviders;
    @Inject
    private CollectionDatasource<TypeManufacturerEntry, UUID> typeManufacturerEntriesDs;
    @Inject
    private CollectionDatasource<TypeProviderEntry, UUID> typeProviderEntriesDs;
    @Inject
    private CollectionDatasource<TypeCalicoholderEntry, UUID> typeCalicoholderEntriesDs;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject private HBoxLayout hBoxLayout;
    private Label label = null;
    @Inject
    private DataManager dataManager;
    private Type type;
    private Typonominal tn;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        if (label==null){
            label = componentsFactory.createComponent(Label.class);
            label.setAlignment(Alignment.TOP_CENTER);
            hBoxLayout.add(label);
        }
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn.getType() == null) {
            LoadContext<Type> ctx = LoadContext.create(Type.class);
            ctx.setView("type-view");
            ctx.setQuery(LoadContext.createQuery("select tp from mobdekbkp$Typonominal tn join tn.type tp where tn.id = :tnId")
                    .setParameter("tnId", tn.getId()));
            type = dataManager.load(ctx);
        } else{
            type = tn.getType();
        }
        if (tn!=null){
            typeManufacturerEntriesDs.setQuery("select e from mobdekbkp$TypeManufacturerEntry e where e.typeInverse.id = '"+type.getId()+"'");
            typeManufacturerEntriesDs.refresh();
            tableManufacturers.setDatasource(typeManufacturerEntriesDs);

            typeProviderEntriesDs.setQuery("select e from mobdekbkp$TypeProviderEntry e where e.typeInverse.id = '"+type.getId()+"'");
            typeProviderEntriesDs.refresh();
            tableProviders.setDatasource(typeProviderEntriesDs);

            typeCalicoholderEntriesDs.setQuery("select e from mobdekbkp$TypeCalicoholderEntry e where e.typeInverse.id = '"+type.getId()+"'");
            typeCalicoholderEntriesDs.refresh();
            tableCalculatorHolder.setDatasource(typeCalicoholderEntriesDs);

            tableCalculatorHolder.setVisible(true);
            tableProviders.setVisible(true);
            tableManufacturers.setVisible(true);
            label.setVisible(false);
        } else{
            showNotification("Отсутствует входящий типономинал");
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        label.setValue(labelValue);
        tableCalculatorHolder.setVisible(false);
        tableProviders.setVisible(false);
        tableManufacturers.setVisible(false);
        label.setVisible(true);
    }

    public void onEditManufacturers() {
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("noedit", true);
        openEditor("mobdekbkp$Company.edit",typeManufacturerEntriesDs.getItem().getName(), WindowManager.OpenType.THIS_TAB,paramOpen);
    }

    public void onEditHolders() {
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("noedit", true);
        openEditor("mobdekbkp$Company.edit",typeCalicoholderEntriesDs.getItem().getName(),WindowManager.OpenType.THIS_TAB,paramOpen);
    }

    public void onEditProviders() {
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("noedit", true);
        openEditor("mobdekbkp$Company.edit",typeProviderEntriesDs.getItem().getName(),WindowManager.OpenType.THIS_TAB,paramOpen);
    }
}
