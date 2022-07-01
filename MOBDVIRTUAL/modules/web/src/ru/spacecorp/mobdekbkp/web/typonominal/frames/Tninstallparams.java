package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.*;

import javax.inject.Inject;
import java.util.Map;

public class Tninstallparams extends AbstractFrame implements TyponominalFrame {

    @Inject
    private TextField tfMaxTemperatureWP;
    @Inject
    private TextField tfMaxSolderingTimeOutput;
    @Inject
    private TextField tfMaxCaseTemperatureManual;
    @Inject
    private TextField tfMinDistanceFromHousingToSoldering;
    @Inject
    private TextField tfFlux;
    @Inject
    private TextField tfSolder;
    @Inject
    private TextField tfMaxPreHeatingTemperature;
    @Inject
    private TextField tfMaxPreheatingTime;
    @Inject
    private TextField tfMaxTemperatureSoldering;
    @Inject
    private TextField tfMaxSolderingTime;
    @Inject
    private TextField tfMaxCaseTemperatureAuto;
    @Inject
    private TextField tfThermalProfile;
    @Inject
    private TextField tfSolderingPasteFlux;
    @Inject
    private TextField tfMethod;

    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private DataManager dataManager;

    private Typonominal tn;
    private MontageParameters parameters;
    private Label noDsLabel;
    private Tninstallparams thisframe;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        thisframe = this;
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        noDsLabel.setVisible(true);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in install parameters frame is null");
        this.tn = tn;
        LoadContext<MontageParameters> ctx = LoadContext.create(MontageParameters.class);
        ctx.setView("montageParameters-view");
        ctx.setQuery(LoadContext.createQuery("select c from mobdekbkp$MontageParameters c join c.typonominal tn where tn.id = :tnId")
                .setParameter("tnId", tn.getId()));
        parameters = dataManager.load(ctx);
        if (parameters != null) {
            noDsLabel.setVisible(false);
            getMaxTemperatureWP();
            getMaxSolderingTimeOutput();
            getMaxCaseTemperatureManual();
            getMinDistanceFromHousingToSoldering();
            getFlux();
            getSolder();
            getMaxPreHeatingTemperature();
            getMaxPreheatingTime();
            getMaxTemperatureSoldering();
            getMaxSolderingTime();
            getMaxCaseTemperatureAuto();
            getThermalProfile();
            getSolderingPasteFlux();
            getMethod();
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
        tfMaxTemperatureWP.setValue(false);
        tfMaxSolderingTimeOutput.setValue(false);
        tfMaxCaseTemperatureManual.setValue(false);
        tfMinDistanceFromHousingToSoldering.setValue(false);
        tfFlux.setValue(false);
        tfSolder.setValue(false);
        tfMaxPreHeatingTemperature.setValue(false);
        tfMaxPreheatingTime.setValue(false);
        tfMaxTemperatureSoldering.setValue(false);
        tfMaxSolderingTime.setValue(false);
        tfMaxCaseTemperatureAuto.setValue(false);
        tfThermalProfile.setValue(false);
        tfSolderingPasteFlux.setValue(false);
        tfMaxTemperatureWP.setValue(false);
        tfMethod.setValue(false);
    }

    private void getMaxTemperatureWP() {
        if (parameters.getMaxTemperatureWP() != null) {
            tfMaxTemperatureWP.setValue(parameters.getMaxTemperatureWP());
        }
    }

    private void getMaxSolderingTimeOutput() {
        if (parameters.getMaxSolderingTimeOutput() != null) {
            tfMaxSolderingTimeOutput.setValue(parameters.getMaxSolderingTimeOutput());
        }
    }

    private void getMaxCaseTemperatureManual() {
        if (parameters.getMaxCaseTemperatureHand() != null) {
            tfMaxCaseTemperatureManual.setValue(parameters.getMaxCaseTemperatureHand());
        }
    }

    private void getMinDistanceFromHousingToSoldering() {
        if (parameters.getMinDistanceFromHousingToSoldering() != null) {
            tfMinDistanceFromHousingToSoldering.setValue(parameters.getMinDistanceFromHousingToSoldering());
        }
    }

    private void getFlux() {
        if (parameters.getFlux() != null) {
            tfFlux.setValue(parameters.getFlux());
        }
    }

    private void getSolder() {
        if (parameters.getSolder() != null) {
            tfSolder.setValue(parameters.getSolder());
        }
    }

    private void getMaxPreHeatingTemperature() {
        if (parameters.getMaxPreHeatingTemperature() != null) {
            tfMaxPreHeatingTemperature.setValue(parameters.getMaxPreHeatingTemperature());
        }
    }

    private void getMaxPreheatingTime() {
        if (parameters.getMaxPreheatingTime() != null) {
            tfMaxPreheatingTime.setValue(parameters.getMaxPreheatingTime());
        }
    }

    private void getMaxTemperatureSoldering() {
        if (parameters.getMaxTemperatureSoldering() != null) {
            tfMaxTemperatureSoldering.setValue(parameters.getMaxTemperatureSoldering());
        }
    }

    private void getMaxSolderingTime() {
        if (parameters.getMaxSolderingTime() != null) {
            tfMaxSolderingTime.setValue(parameters.getMaxSolderingTime());
        }
    }

    private void getMaxCaseTemperatureAuto() {
        if (parameters.getMaxCaseTemperatureAuto() != null) {
            tfMaxCaseTemperatureAuto.setValue(parameters.getMaxCaseTemperatureAuto());
        }
    }

    private void getThermalProfile() {
        if (parameters.getThermalProfile() != null) {
            tfThermalProfile.setValue(parameters.getThermalProfile());
        }
    }

    private void getSolderingPasteFlux() {
        if (parameters.getSolderingPasteFlux() != null) {
            tfSolderingPasteFlux.setValue(parameters.getSolderingPasteFlux());
        }
    }

    private void getMethod() {
        if (parameters.getMethod() != null) {
            tfMethod.setValue(parameters.getMethod());
        }
    }
}