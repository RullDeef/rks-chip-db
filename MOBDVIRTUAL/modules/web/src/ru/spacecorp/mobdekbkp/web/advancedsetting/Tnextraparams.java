package ru.spacecorp.mobdekbkp.web.advancedsetting;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.AdvancedSetting;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.TyponominalFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class Tnextraparams extends AbstractFrame implements TyponominalFrame {

    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private TextField tfMass;
    @Inject
    private TextField tfPRP;
    @Inject
    private TextField tfNameTechnicalSpecification;
    @Inject
    private TextField tfTechnicalSpecificationValue;
    @Inject
    private TextField tfWorkingLife;
    @Inject
    private TextField tfShelfLife;
    @Inject
    private TextField tfTightness;
    @Inject
    private DataManager dataManager;

    private Typonominal tn;
    private AdvancedSetting advancedSetting;
    private Label noDsLabel;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in extra parameters frame is null");
        this.tn = tn;

        LoadContext<AdvancedSetting> ctx = LoadContext.create(AdvancedSetting.class);
        ctx.setView("advancedSetting-view");
        ctx.setQuery(LoadContext.createQuery("select a from mobdekbkp$AdvancedSetting a join a.typonominal tn where tn.id = :tnId")
                .setParameter("tnId", tn.getId()));
        advancedSetting = dataManager.load(ctx);
        if (advancedSetting != null) {
            noDsLabel.setVisible(false);
            getMass();
            getPRP();
            getNameTechnicalSpecification();
            getTechnicalSpecificationValue();
            getWorkingLife();
            getShelfLife();
            getTightness();
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        tfMass.setValue(false);
        tfPRP.setValue(false);
        tfNameTechnicalSpecification.setValue(false);
        tfTechnicalSpecificationValue.setValue(false);
        tfWorkingLife.setValue(false);
        tfShelfLife.setValue(false);
        tfTightness.setValue(false);
    }

    private void getMass() {
        if (advancedSetting.getMass() != null) {
            tfMass.setValue(advancedSetting.getMass());
        }
    }

    private void getPRP() {
        if (advancedSetting.getNumberPRP() != null) {
            tfPRP.setValue(advancedSetting.getNumberPRP());
        }
    }

    private void getNameTechnicalSpecification() {
        if (advancedSetting.getTechnicalSpecificationName() != null) {
            tfNameTechnicalSpecification.setValue(advancedSetting.getTechnicalSpecificationName());
        }
    }

    private void getTechnicalSpecificationValue() {
        if (advancedSetting.getTechnicalSpecificationValue() != null) {
            tfTechnicalSpecificationValue.setValue(advancedSetting.getTechnicalSpecificationValue());
        }
    }

    private void getWorkingLife() {
        if (advancedSetting.getWorkingLife() != null) {
            tfWorkingLife.setValue(advancedSetting.getWorkingLife());
        }
    }

    private void getShelfLife() {
        if (advancedSetting.getWorkingLife() != null) {
            tfShelfLife.setValue(advancedSetting.getWorkingLife());
        }
    }

    private void getTightness() {
        if (advancedSetting.getTightness() != null) {
            tfTightness.setValue(advancedSetting.getTightness());
        }
    }

    public void onBtPRPClick() {
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("numberPRP", true);
        AbstractEditor ed = openEditor("mobdekbkp$AdvancedSetting.edit", advancedSetting, WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(tn));
    }

    public void onBtNameTechnicalSpecificationClick() {
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("technicalSpecificationName", true);
        AbstractEditor ed = openEditor("mobdekbkp$AdvancedSetting.edit", advancedSetting, WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(tn));
    }

    public void onBtTechnicalSpecificationValueClick() {
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("technicalSpecificationValue", true);
        AbstractEditor ed = openEditor("mobdekbkp$AdvancedSetting.edit", advancedSetting, WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(tn));
    }
}