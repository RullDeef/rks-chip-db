package ru.spacecorp.mobdekbkp.web.type;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
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
    private ComponentsFactory componentsFactory;
    private Label label = null;
    @Inject
    private DataManager dataManagerFactory;
    @Inject
    private TextField tfManufacturers;
    @Inject
    private TextField tfproducingCountry;
    @Inject
    private TextField tfcertificationOrganizationQMS;
    @Inject
    private TextField tfCalculatorHolder;
    @Inject
    private TextField tfProvider;
    @Inject
    private Metadata metadata;
    private Typonominal tn;
    private Factory factory;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        if (label == null) {
            label = componentsFactory.createComponent(Label.class);
            label.setAlignment(Alignment.TOP_CENTER);
        }
    }

    @Override
    public void initFrame(Typonominal tn) {
        this.tn = tn;
        LoadContext<Factory> ctx = LoadContext.create(Factory.class);
        ctx.setView("factory-view");
        ctx.setQuery(LoadContext.createQuery("select f from mobdekbkp$Factory f join f.typonominal tn where tn.id = :tnId")
                .setParameter("tnId", tn.getId()));
        factory = dataManagerFactory.load(ctx);

        if (factory != null) {
            getManufacturers();
            getProducingCountry();
            getCertificationOrganizationQMS();
            getCalculatorHolder();
            getProvider();
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        tfManufacturers.setValue(false);
        tfproducingCountry.setValue(false);
        tfcertificationOrganizationQMS.setValue(false);
        tfCalculatorHolder.setValue(false);
        tfProvider.setValue(false);
        label.setValue(labelValue);
        label.setVisible(true);
    }

    private void getManufacturers() {
        if (factory.getProducer() != null) {
            tfManufacturers.setValue(factory.getProducer());
        }
    }

    private void getProducingCountry() {
        if (factory.getProducingCountry() != null) {
            tfproducingCountry.setValue(factory.getProducingCountry());
        }
    }

    private void getCertificationOrganizationQMS() {
        if (factory.getCertificationCMKOrganization() != null) {
            tfcertificationOrganizationQMS.setValue(factory.getCertificationCMKOrganization());
        }
    }

    private void getCalculatorHolder() {
        if (factory.getCalculatorHolder() != null) {
            tfCalculatorHolder.setValue(factory.getCalculatorHolder());
        }
    }

    private void getProvider() {
        if (factory.getProvider() != null) {
            tfProvider.setValue(factory.getProvider());
        }
    }

    public void onButtonClick() {
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("provider", true);
        AbstractEditor ed = openEditor("mobdekbkp$Factory.edit", factory,
                WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(tn));
    }
}
