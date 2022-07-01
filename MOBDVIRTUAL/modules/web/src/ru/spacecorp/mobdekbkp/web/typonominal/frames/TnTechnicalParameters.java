package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.AbstractFrame;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.MontageParameters;
import ru.spacecorp.mobdekbkp.entity.TechnicalParameters;
import ru.spacecorp.mobdekbkp.entity.Typonominal;

import javax.inject.Inject;
import java.util.Map;

public class TnTechnicalParameters extends AbstractFrame implements TyponominalFrame {

    @Inject
    private TextField tffrequencyRangeSin;
    @Inject
    private TextField tfaccelerationAmplitudeSin;
    @Inject
    private TextField tfexposureTimeSin;
    @Inject
    private TextField tffrequencyRangebroad;
    @Inject
    private TextField tfaccelerationSpectralDensitybroad;
    @Inject
    private TextField tfrootMeanSquareAccelerationbroad;
    @Inject
    private TextField tfexposureTimebroad;
    @Inject
    private TextField tfpeakShockAccelerationSingle;
    @Inject
    private TextField tfdurationOfImpactAccelerationSingle;
    @Inject
    private TextField tfshockSpectrumFrequencyRangeSingle;
    @Inject
    private TextField tfimpactSpectrumValueSingle;
    @Inject
    private TextField tfnumberOfImpactsSingle;
    @Inject
    private TextField tfpeakShockAccelerationRepeat;
    @Inject
    private TextField tfdurationOfImpactAccelerationRepeat;
    @Inject
    private TextField tfnumberOfImpactsRepeat;
    @Inject
    private TextField tffrequencyRangeNoise;
    @Inject
    private TextField tfsoundExposureLevelNoise;
    @Inject
    private TextField tfexposureTimeNoise;
    @Inject
    private TextField tfaccelerationAmplitudeLine;
    @Inject
    private TextField tfexposureTimeLine;
    @Inject
    private TextField tfmaximumValueDuringOperationInctemp;
    @Inject
    private TextField tfmaximumValueDuringTransportationAndStorageInctemp;
    @Inject
    private TextField tfmaximumValueDuringOperationRedtemp;
    @Inject
    private TextField tfmaximumValueDuringTransportationAndStorageRedtemp;
    @Inject
    private TextField tfchangeTemperatureRateChangeTemperatureRedtemp;
    @Inject
    private TextField tfrelativeHumidityAtTemperatureHighair;
    @Inject
    private TextField tfabsoluteAirHumidityHighair;
    @Inject
    private TextField tfrelativeHumidityAtTemperatureRedair;
    @Inject
    private TextField absoluteAirHumidityRedair;
    @Inject
    private TextField tfvalueDuringOperationRedatm;
    @Inject
    private TextField tfvalueDuringTransportationAndStorageRedatm;
    @Inject
    private TextField tfvalueDuringOperationIncatm;
    @Inject
    private TextField valueDuringTransportationAndStorageIncatm;
    @Inject
    private TextField tfratePressureChange;
    @Inject
    private TextField tfpressureChangeRange;
    @Inject
    private TextField tfesdRequirements;

    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private DataManager dataManager;

    private Typonominal tn;
    private TechnicalParameters parameters;
    private Label noDsLabel;
    private TnTechnicalParameters thisframe;


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
            throw new RuntimeException("Unexpectedly typonominal in TechnicalParameters frame is null");
        this.tn = tn;
        LoadContext<TechnicalParameters> ctx = LoadContext.create(TechnicalParameters.class);
        ctx.setView("technicalParameters-view");
        ctx.setQuery(LoadContext.createQuery("select c from mobdekbkp$TechnicalParameters c join c.typonominal tn where tn.id = :tnId")
                .setParameter("tnId", tn.getId()));
        parameters = dataManager.load(ctx);
        if (parameters != null) {
            getFrequencyRangeSin();
            getAccelerationAmplitudeSin();
            getExposureTimeSin();
            getFrequencyRangebroad();
            getAccelerationSpectralDensitybroad();
            getRootMeanSquareAccelerationbroad();
            getExposureTimebroad();
            getPeakShockAccelerationSingle();
            getDurationOfImpactAccelerationSingle();
            getShockSpectrumFrequencyRangeSingle();
            getImpactSpectrumValueSingle();
            getNumberOfImpactsSingle();
            getPeakShockAccelerationRepeat();
            getDurationOfImpactAccelerationRepeat();
            getNumberOfImpactsRepeat();
            getFrequencyRangeNoise();
            getSoundExposureLevelNoise();
            getExposureTimeNoise();
            getAccelerationAmplitudeLine();
            getExposureTimeLine();
            getMaximumValueDuringOperationInctemp();
            getMaximumValueDuringTransportationAndStorageInctemp();
            getMaximumValueDuringOperationRedtemp();
            getMaximumValueDuringTransportationAndStorageRedtemp();
            getChangeTemperatureRateChangeTemperatureRedtemp();
            getRelativeHumidityAtTemperatureHighair();
            getAbsoluteAirHumidityHighair();
            getRelativeHumidityAtTemperatureRedair();
            getAbsoluteAirHumidityRedair();
            getValueDuringOperationRedatm();
            getValueDuringTransportationAndStorageRedatm();
            getValueDuringOperationIncatm();
            getValueDuringTransportationAndStorageIncatm();
            getRatePressureChange();
            getPressureChangeRange();
            getEsdRequirements();
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
        tffrequencyRangeSin.setValue(false);
        tfaccelerationAmplitudeSin.setValue(false);
        tfexposureTimeSin.setValue(false);
        tffrequencyRangebroad.setValue(false);
        tfaccelerationSpectralDensitybroad.setValue(false);
        tfrootMeanSquareAccelerationbroad.setValue(false);
        tfexposureTimebroad.setValue(false);
        tfpeakShockAccelerationSingle.setValue(false);
        tfdurationOfImpactAccelerationSingle.setValue(false);
        tfshockSpectrumFrequencyRangeSingle.setValue(false);
        tfimpactSpectrumValueSingle.setValue(false);
        tfnumberOfImpactsSingle.setValue(false);
        tfpeakShockAccelerationRepeat.setValue(false);
        tfdurationOfImpactAccelerationRepeat.setValue(false);
        tfnumberOfImpactsRepeat.setValue(false);
        tffrequencyRangeNoise.setValue(false);
        tfsoundExposureLevelNoise.setValue(false);
        tfexposureTimeNoise.setValue(false);
        tfaccelerationAmplitudeLine.setValue(false);
        tfexposureTimeLine.setValue(false);
        tfmaximumValueDuringOperationInctemp.setValue(false);
        tfmaximumValueDuringTransportationAndStorageInctemp.setValue(false);
        tfmaximumValueDuringOperationRedtemp.setValue(false);
        tfmaximumValueDuringTransportationAndStorageRedtemp.setValue(false);
        tfchangeTemperatureRateChangeTemperatureRedtemp.setValue(false);
        tfrelativeHumidityAtTemperatureHighair.setValue(false);
        tfabsoluteAirHumidityHighair.setValue(false);
        tfrelativeHumidityAtTemperatureRedair.setValue(false);
        absoluteAirHumidityRedair.setValue(false);
        tfvalueDuringOperationRedatm.setValue(false);
        tfvalueDuringTransportationAndStorageRedatm.setValue(false);
        tfvalueDuringOperationIncatm.setValue(false);
        valueDuringTransportationAndStorageIncatm.setValue(false);
        tfratePressureChange.setValue(false);
        tfpressureChangeRange.setValue(false);
        tfesdRequirements.setValue(false);
    }

    private void getFrequencyRangeSin() {
        if (parameters.getFrequencyRangeSinusoidalVibration() != null) {
            tffrequencyRangeSin.setValue(parameters.getFrequencyRangeSinusoidalVibration());
        }
    }

    private void getAccelerationAmplitudeSin() {
        if (parameters.getAccelerationAmplitude() != null) {
            tfaccelerationAmplitudeSin.setValue(parameters.getAccelerationAmplitude());
        }
    }

    private void getExposureTimeSin() {
        if (parameters.getExposureTimeSinusoidalVibration() != null) {
            tfexposureTimeSin.setValue(parameters.getExposureTimeSinusoidalVibration());
        }
    }

    private void getFrequencyRangebroad() {
        if (parameters.getFrequencyRangeBroadVibration() != null) {
            tffrequencyRangebroad.setValue(parameters.getFrequencyRangeBroadVibration());
        }
    }

    private void getAccelerationSpectralDensitybroad() {
        if (parameters.getAccelerationSpectralDensity() != null) {
            tfaccelerationSpectralDensitybroad.setValue(parameters.getAccelerationSpectralDensity());
        }
    }

    private void getRootMeanSquareAccelerationbroad() {
        if (parameters.getRootMeanSquareAcceleration() != null) {
            tfrootMeanSquareAccelerationbroad.setValue(parameters.getRootMeanSquareAcceleration());
        }
    }

    private void getExposureTimebroad() {
        if (parameters.getExposureTimeBroadVibration() != null) {
            tfexposureTimebroad.setValue(parameters.getExposureTimeBroadVibration());
        }
    }

    private void getPeakShockAccelerationSingle() {
        if (parameters.getPeakShockAccelerationOne() != null) {
            tfpeakShockAccelerationSingle.setValue(parameters.getPeakShockAccelerationOne());
        }
    }

    private void getDurationOfImpactAccelerationSingle() {
        if (parameters.getDurationOfImpactAccelerationOne() != null) {
            tfdurationOfImpactAccelerationSingle.setValue(parameters.getDurationOfImpactAccelerationOne());
        }
    }

    private void getShockSpectrumFrequencyRangeSingle() {
        if (parameters.getShockSpectrumFrequencyRange() != null) {
            tfshockSpectrumFrequencyRangeSingle.setValue(parameters.getShockSpectrumFrequencyRange());
        }
    }

    private void getImpactSpectrumValueSingle() {
        if (parameters.getImpactSpectrumValue() != null) {
            tfimpactSpectrumValueSingle.setValue(parameters.getImpactSpectrumValue());
        }
    }

    private void getNumberOfImpactsSingle() {
        if (parameters.getNumberOfImpactsOne() != null) {
            tfnumberOfImpactsSingle.setValue(parameters.getNumberOfImpactsOne());
        }
    }

    private void getPeakShockAccelerationRepeat() {
        if (parameters.getPeakShockAccelerationMany() != null) {
            tfpeakShockAccelerationRepeat.setValue(parameters.getPeakShockAccelerationMany());
        }
    }

    private void getDurationOfImpactAccelerationRepeat() {
        if (parameters.getDurationOfImpactAccelerationMany() != null) {
            tfdurationOfImpactAccelerationRepeat.setValue(parameters.getDurationOfImpactAccelerationMany());
        }
    }

    private void getNumberOfImpactsRepeat() {
        if (parameters.getNumberOfImpactsMany() != null) {
            tfnumberOfImpactsRepeat.setValue(parameters.getNumberOfImpactsMany());
        }
    }

    private void getFrequencyRangeNoise() {
        if (parameters.getFrequencyRangeAcousticNoise() != null) {
            tffrequencyRangeNoise.setValue(parameters.getFrequencyRangeAcousticNoise());
        }
    }

    private void getSoundExposureLevelNoise() {
        if (parameters.getSoundExposureLevel() != null) {
            tfsoundExposureLevelNoise.setValue(parameters.getSoundExposureLevel());
        }
    }

    private void getExposureTimeNoise() {
        if (parameters.getExposureTimeAcousticNoise() != null) {
            tfexposureTimeNoise.setValue(parameters.getExposureTimeAcousticNoise());
        }
    }

    private void getAccelerationAmplitudeLine() {
        if (parameters.getAccelerationAmplitude() != null) {
            tfaccelerationAmplitudeLine.setValue(parameters.getAccelerationAmplitude());
        }
    }

    private void getExposureTimeLine() {
        if (parameters.getExposureTimeQuasiStaticAcceleration() != null) {
            tfexposureTimeLine.setValue(parameters.getExposureTimeQuasiStaticAcceleration());
        }
    }

    private void getMaximumValueDuringOperationInctemp() {
        if (parameters.getMaximumValueDuringOperationUp() != null) {
            tfmaximumValueDuringOperationInctemp.setValue(parameters.getMaximumValueDuringOperationUp());
        }
    }

    private void getMaximumValueDuringTransportationAndStorageInctemp() {
        if (parameters.getMaximumValueDuringTransportationAndStorageUp() != null) {
            tfmaximumValueDuringTransportationAndStorageInctemp.setValue(parameters.getMaximumValueDuringTransportationAndStorageUp());
        }
    }

    private void getMaximumValueDuringOperationRedtemp() {
        if (parameters.getMaximumValueDuringOperationDown() != null) {
            tfmaximumValueDuringOperationRedtemp.setValue(parameters.getMaximumValueDuringOperationDown());
        }
    }

    private void getMaximumValueDuringTransportationAndStorageRedtemp() {
        if (parameters.getMaximumValueDuringTransportationAndStorageDown() != null) {
            tfmaximumValueDuringTransportationAndStorageRedtemp.setValue(parameters.getMaximumValueDuringTransportationAndStorageDown());
        }
    }

    private void getChangeTemperatureRateChangeTemperatureRedtemp() {
        if (parameters.getChangeTemperatureRateChangeTemperature() != null) {
            tfchangeTemperatureRateChangeTemperatureRedtemp.setValue(parameters.getChangeTemperatureRateChangeTemperature());
        }
    }

    private void getRelativeHumidityAtTemperatureHighair() {
        if (parameters.getRelativeHumidityAtTemperatureUp() != null) {
            tfrelativeHumidityAtTemperatureHighair.setValue(parameters.getRelativeHumidityAtTemperatureUp());
        }
    }

    private void getAbsoluteAirHumidityHighair() {
        if (parameters.getAbsoluteAirHumidityUp() != null) {
            tfabsoluteAirHumidityHighair.setValue(parameters.getAbsoluteAirHumidityUp());
        }
    }

    private void getRelativeHumidityAtTemperatureRedair() {
        if (parameters.getRelativeHumidityAtTemperatureDown() != null) {
            tfrelativeHumidityAtTemperatureRedair.setValue(parameters.getRelativeHumidityAtTemperatureDown());
        }
    }

    private void getAbsoluteAirHumidityRedair() {
        if (parameters.getAbsoluteAirHumidityDown() != null) {
            absoluteAirHumidityRedair.setValue(parameters.getAbsoluteAirHumidityDown());
        }
    }

    private void getValueDuringOperationRedatm() {
        if (parameters.getValueDuringOperationDown() != null) {
            tfvalueDuringOperationRedatm.setValue(parameters.getValueDuringOperationDown());
        }
    }

    private void getValueDuringTransportationAndStorageRedatm() {
        if (parameters.getValueDuringTransportationAndStorageDown() != null) {
            tfvalueDuringTransportationAndStorageRedatm.setValue(parameters.getValueDuringTransportationAndStorageDown());
        }
    }

    private void getValueDuringOperationIncatm() {
        if (parameters.getValueDuringOperationUp() != null) {
            tfvalueDuringOperationIncatm.setValue(parameters.getValueDuringOperationUp());
        }
    }

    private void getValueDuringTransportationAndStorageIncatm() {
        if (parameters.getValueDuringTransportationAndStorageUp() != null) {
            valueDuringTransportationAndStorageIncatm.setValue(parameters.getValueDuringTransportationAndStorageUp());
        }
    }

    private void getRatePressureChange() {
        if (parameters.getRatePressureChange() != null) {
            tfratePressureChange.setValue(parameters.getRatePressureChange());
        }
    }

    private void getPressureChangeRange() {
        if (parameters.getPressureChangeRange() != null) {
            tfpressureChangeRange.setValue(parameters.getPressureChangeRange());
        }
    }

    private void getEsdRequirements() {
        if (parameters.getEsdRequirements() != null) {
            tfesdRequirements.setValue(parameters.getEsdRequirements());
        }
    }

}