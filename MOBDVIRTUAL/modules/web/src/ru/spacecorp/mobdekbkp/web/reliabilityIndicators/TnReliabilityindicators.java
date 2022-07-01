package ru.spacecorp.mobdekbkp.web.reliabilityIndicators;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.AbstractFrame;
import com.haulmont.cuba.gui.components.TextField;
import ru.spacecorp.mobdekbkp.entity.ReliabilityIndicators;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.TyponominalFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class TnReliabilityindicators extends AbstractFrame implements TyponominalFrame {

    @Inject
    private DataManager dataManager;
    @Inject
    private TextField tfreliabilityIndicator;
    @Inject
    private TextField tfretentionRate;
    @Inject
    private TextField tfgammaPercentOperating;
    @Inject
    private TextField tfgammaPercentOperatingLight;
    @Inject
    private TextField tfgammapercentStorageabilitytime;

    private Typonominal tn;
    private ReliabilityIndicators reliabilityindicators;

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in TnBasicInfo frame is null");
        this.tn = tn;

        LoadContext<ReliabilityIndicators> ctx = LoadContext.create(ReliabilityIndicators.class);
        ctx.setView("reliabilityIndicators-view");
        ctx.setQuery(LoadContext.createQuery("select r from mobdekbkp$ReliabilityIndicators r " +
                "join r.typonominal tn where tn.id = :tnId")
                .setParameter("tnId", tn.getId()));
        reliabilityindicators = dataManager.load(ctx);
        if (reliabilityindicators != null) {
            getReliabilityIndicator();
            getRetentionRate();
            getGammaPercentOperating();
            getGammaPercentOperatingLight();
            getGammapercentStorageabilitytime();
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        tfreliabilityIndicator.setValue(labelValue);
        tfretentionRate.setValue(labelValue);
        tfgammaPercentOperating.setValue(labelValue);
        tfgammaPercentOperatingLight.setValue(labelValue);
        tfgammapercentStorageabilitytime.setValue(labelValue);
    }

    private void getReliabilityIndicator() {
        if (reliabilityindicators.getReliabilityIndicator() != null) {
            tfreliabilityIndicator.setValue(reliabilityindicators.getReliabilityIndicator());
        }
    }

    private void getRetentionRate() {
        if (reliabilityindicators.getRetentionRate() != null) {
            tfretentionRate.setValue(reliabilityindicators.getRetentionRate());
        }
    }

    private void getGammaPercentOperating() {
        if (reliabilityindicators.getGammaPercentOperatingMaintenance() != null) {
            tfgammaPercentOperating.setValue(reliabilityindicators.getGammaPercentOperatingMaintenance());
        }
    }

    private void getGammaPercentOperatingLight() {
        if (reliabilityindicators.getGammaPercentOperatingLight() != null) {
            tfgammaPercentOperatingLight.setValue(reliabilityindicators.getGammaPercentOperatingLight());
        }
    }

    private void getGammapercentStorageabilitytime() {
        if (reliabilityindicators.getGammapercentStorageabilityTime() != null) {
            tfgammapercentStorageabilitytime.setValue(reliabilityindicators.getGammapercentStorageabilityTime());
        }
    }

    public void onBtgammaPercentOperatingMaintenanceClick() {
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("gammaPercentOperatingMaintenance", true);
        AbstractEditor ed = openEditor("mobdekbkp$ReliabilityIndicators.edit", reliabilityindicators, WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(tn));
    }

    public void onBtgammaPercentOperatingLightClick() {
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("gammaPercentOperatingLight", true);
        AbstractEditor ed = openEditor("mobdekbkp$ReliabilityIndicators.edit", reliabilityindicators, WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(tn));
    }

    public void onBtgammapercentStorageabilitytimeClick() {
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("gammapercentStorageabilityTime", true);
        AbstractEditor ed = openEditor("mobdekbkp$ReliabilityIndicators.edit", reliabilityindicators, WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(tn));
    }
}