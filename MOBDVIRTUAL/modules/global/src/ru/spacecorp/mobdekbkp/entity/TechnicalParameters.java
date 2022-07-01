package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Table(name = "MOBDEKBKP_TECHNICAL_PARAMETERS")
@Entity(name = "mobdekbkp$TechnicalParameters")
public class TechnicalParameters extends StandardEntity {
    private static final long serialVersionUID = 6000466611214487908L;

    @Column(name = "FREQUENCY_RANGE_SINUSOIDAL_VIBRATION", length = 250)
    protected String frequencyRangeSinusoidalVibration;

    @Column(name = "ACCELERATION_AMPLITUDE", length = 250)
    protected String accelerationAmplitude;

    @Column(name = "EXPOSURE_TIME_SINUSOIDAL_VIBRATION", length = 250)
    protected String exposureTimeSinusoidalVibration;

    @Column(name = "FREQUENCY_RANGE_BROAD_VIBRATION", length = 250)
    protected String frequencyRangeBroadVibration;

    @Column(name = "ACCELERATION_SPECTRAL_DENSITY", length = 250)
    protected String accelerationSpectralDensity;

    @Column(name = "ROOT_MEAN_SQUARE_ACCELERATION", length = 250)
    protected String rootMeanSquareAcceleration;

    @Column(name = "EXPOSURE_TIME_BROAD_VIBRATION", length = 250)
    protected String exposureTimeBroadVibration;

    @Column(name = "PEAK_SHOCK_ACCELERATION_ONE", length = 250)
    protected String peakShockAccelerationOne;

    @Column(name = "DURATION_OF_IMPACT_ACCELERATION_ONE", length = 250)
    protected String durationOfImpactAccelerationOne;

    @Column(name = "SHOCK_SPECTRUM_FREQUENCY_RANGE", length = 250)
    protected String shockSpectrumFrequencyRange;

    @Column(name = "IMPACT_SPECTRUM_VALUE", length = 250)
    protected String impactSpectrumValue;

    @Column(name = "NUMBER_OF_IMPACTS_ONE", length = 250)
    protected String numberOfImpactsOne;

    @Column(name = "PEAK_SHOCK_ACCELERATION_MANY", length = 250)
    protected String peakShockAccelerationMany;

    @Column(name = "DURATION_OF_IMPACT_ACCELERATION_MANY", length = 250)
    protected String durationOfImpactAccelerationMany;

    @Column(name = "NUMBER_OF_IMPACTS_MANY", length = 250)
    protected String numberOfImpactsMany;

    @Column(name = "FREQUENCY_RANGE_ACOUSTIC_NOISE", length = 250)
    protected String frequencyRangeAcousticNoise;

    @Column(name = "SOUND_EXPOSURE_LEVEL", length = 250)
    protected String soundExposureLevel;

    @Column(name = "EXPOSURE_TIME_ACOUSTIC_NOISE", length = 250)
    protected String exposureTimeAcousticNoise;

    @Column(name = "ACCELERATION_AMPLITUDE_QUASI_STATIC_ACCELERATION", length = 250)
    protected String accelerationAmplitudeQuasiStaticAcceleration;

    @Column(name = "EXPOSURE_TIME_QUASI_STATIC_ACCELERATION", length = 250)
    protected String exposureTimeQuasiStaticAcceleration;

    @Column(name = "MAXIMUM_VALUE_DURING_OPERATION_UP", length = 250)
    protected String maximumValueDuringOperationUp;

    @Column(name = "MAXIMUM_VALUE_DURING_TRANSPORTATION_AND_STORAGE_UP", length = 250)
    protected String maximumValueDuringTransportationAndStorageUp;

    @Column(name = "MAXIMUM_VALUE_DURING_OPERATION_DOWN", length = 250)
    protected String maximumValueDuringOperationDown;

    @Column(name = "MAXIMUM_VALUE_DURING_TRANSPORTATION_AND_STORAGE_DOWN", length = 250)
    protected String maximumValueDuringTransportationAndStorageDown;

    @Column(name = "CHANGE_TEMPERATURE_RATE_CHANGE_TEMPERATURE", length = 250)
    protected String changeTemperatureRateChangeTemperature;

    @Column(name = "RELATIVE_HUMIDITY_AT_TEMPERATURE_UP", length = 250)
    protected String relativeHumidityAtTemperatureUp;

    @Column(name = "ABSOLUTE_AIR_HUMIDITY_UP", length = 250)
    protected String absoluteAirHumidityUp;

    @Column(name = "RELATIVE_HUMIDITY_AT_TEMPERATURE_DOWN", length = 250)
    protected String relativeHumidityAtTemperatureDown;

    @Column(name = "ABSOLUTE_AIR_HUMIDITY_DOWN", length = 250)
    protected String absoluteAirHumidityDown;

    @Column(name = "VALUE_DURING_OPERATION_DOWN", length = 250)
    protected String valueDuringOperationDown;

    @Column(name = "VALUE_DURING_TRANSPORTATION_AND_STORAGE_DOWN", length = 250)
    protected String valueDuringTransportationAndStorageDown;

    @Column(name = "VALUE_DURING_OPERATION_UP", length = 250)
    protected String valueDuringOperationUp;

    @Column(name = "VALUE_DURING_TRANSPORTATION_AND_STORAGE_UP", length = 250)
    protected String valueDuringTransportationAndStorageUp;

    @Column(name = "RATE_PRESSURE_CHANGE", length = 250)
    protected String ratePressureChange;

    @Column(name = "PRESSURE_CHANGE_RANGE", length = 250)
    protected String pressureChangeRange;

    @Column(name = "ESD_REQUIREMENTS", length = 250)
    protected String esdRequirements;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


    public void setExposureTimeQuasiStaticAcceleration(String exposureTimeQuasiStaticAcceleration) {
        this.exposureTimeQuasiStaticAcceleration = exposureTimeQuasiStaticAcceleration;
    }

    public String getExposureTimeQuasiStaticAcceleration() {
        return exposureTimeQuasiStaticAcceleration;
    }

    public void setMaximumValueDuringOperationUp(String maximumValueDuringOperationUp) {
        this.maximumValueDuringOperationUp = maximumValueDuringOperationUp;
    }

    public String getMaximumValueDuringOperationUp() {
        return maximumValueDuringOperationUp;
    }

    public void setMaximumValueDuringTransportationAndStorageUp(String maximumValueDuringTransportationAndStorageUp) {
        this.maximumValueDuringTransportationAndStorageUp = maximumValueDuringTransportationAndStorageUp;
    }

    public String getMaximumValueDuringTransportationAndStorageUp() {
        return maximumValueDuringTransportationAndStorageUp;
    }

    public void setMaximumValueDuringOperationDown(String maximumValueDuringOperationDown) {
        this.maximumValueDuringOperationDown = maximumValueDuringOperationDown;
    }

    public String getMaximumValueDuringOperationDown() {
        return maximumValueDuringOperationDown;
    }

    public void setMaximumValueDuringTransportationAndStorageDown(String maximumValueDuringTransportationAndStorageDown) {
        this.maximumValueDuringTransportationAndStorageDown = maximumValueDuringTransportationAndStorageDown;
    }

    public String getMaximumValueDuringTransportationAndStorageDown() {
        return maximumValueDuringTransportationAndStorageDown;
    }

    public void setChangeTemperatureRateChangeTemperature(String changeTemperatureRateChangeTemperature) {
        this.changeTemperatureRateChangeTemperature = changeTemperatureRateChangeTemperature;
    }

    public String getChangeTemperatureRateChangeTemperature() {
        return changeTemperatureRateChangeTemperature;
    }

    public void setRelativeHumidityAtTemperatureUp(String relativeHumidityAtTemperatureUp) {
        this.relativeHumidityAtTemperatureUp = relativeHumidityAtTemperatureUp;
    }

    public String getRelativeHumidityAtTemperatureUp() {
        return relativeHumidityAtTemperatureUp;
    }

    public void setAbsoluteAirHumidityUp(String absoluteAirHumidityUp) {
        this.absoluteAirHumidityUp = absoluteAirHumidityUp;
    }

    public String getAbsoluteAirHumidityUp() {
        return absoluteAirHumidityUp;
    }

    public void setRelativeHumidityAtTemperatureDown(String relativeHumidityAtTemperatureDown) {
        this.relativeHumidityAtTemperatureDown = relativeHumidityAtTemperatureDown;
    }

    public String getRelativeHumidityAtTemperatureDown() {
        return relativeHumidityAtTemperatureDown;
    }

    public void setAbsoluteAirHumidityDown(String absoluteAirHumidityDown) {
        this.absoluteAirHumidityDown = absoluteAirHumidityDown;
    }

    public String getAbsoluteAirHumidityDown() {
        return absoluteAirHumidityDown;
    }

    public void setValueDuringOperationDown(String valueDuringOperationDown) {
        this.valueDuringOperationDown = valueDuringOperationDown;
    }

    public String getValueDuringOperationDown() {
        return valueDuringOperationDown;
    }

    public void setValueDuringTransportationAndStorageDown(String valueDuringTransportationAndStorageDown) {
        this.valueDuringTransportationAndStorageDown = valueDuringTransportationAndStorageDown;
    }

    public String getValueDuringTransportationAndStorageDown() {
        return valueDuringTransportationAndStorageDown;
    }

    public void setValueDuringOperationUp(String valueDuringOperationUp) {
        this.valueDuringOperationUp = valueDuringOperationUp;
    }

    public String getValueDuringOperationUp() {
        return valueDuringOperationUp;
    }

    public void setValueDuringTransportationAndStorageUp(String valueDuringTransportationAndStorageUp) {
        this.valueDuringTransportationAndStorageUp = valueDuringTransportationAndStorageUp;
    }

    public String getValueDuringTransportationAndStorageUp() {
        return valueDuringTransportationAndStorageUp;
    }

    public void setRatePressureChange(String ratePressureChange) {
        this.ratePressureChange = ratePressureChange;
    }

    public String getRatePressureChange() {
        return ratePressureChange;
    }

    public void setPressureChangeRange(String pressureChangeRange) {
        this.pressureChangeRange = pressureChangeRange;
    }

    public String getPressureChangeRange() {
        return pressureChangeRange;
    }

    public void setEsdRequirements(String esdRequirements) {
        this.esdRequirements = esdRequirements;
    }

    public String getEsdRequirements() {
        return esdRequirements;
    }


    public void setNumberOfImpactsMany(String numberOfImpactsMany) {
        this.numberOfImpactsMany = numberOfImpactsMany;
    }

    public String getNumberOfImpactsMany() {
        return numberOfImpactsMany;
    }

    public void setFrequencyRangeAcousticNoise(String frequencyRangeAcousticNoise) {
        this.frequencyRangeAcousticNoise = frequencyRangeAcousticNoise;
    }

    public String getFrequencyRangeAcousticNoise() {
        return frequencyRangeAcousticNoise;
    }

    public void setSoundExposureLevel(String soundExposureLevel) {
        this.soundExposureLevel = soundExposureLevel;
    }

    public String getSoundExposureLevel() {
        return soundExposureLevel;
    }

    public void setExposureTimeAcousticNoise(String exposureTimeAcousticNoise) {
        this.exposureTimeAcousticNoise = exposureTimeAcousticNoise;
    }

    public String getExposureTimeAcousticNoise() {
        return exposureTimeAcousticNoise;
    }

    public void setAccelerationAmplitudeQuasiStaticAcceleration(String accelerationAmplitudeQuasiStaticAcceleration) {
        this.accelerationAmplitudeQuasiStaticAcceleration = accelerationAmplitudeQuasiStaticAcceleration;
    }

    public String getAccelerationAmplitudeQuasiStaticAcceleration() {
        return accelerationAmplitudeQuasiStaticAcceleration;
    }


    public void setPeakShockAccelerationOne(String peakShockAccelerationOne) {
        this.peakShockAccelerationOne = peakShockAccelerationOne;
    }

    public String getPeakShockAccelerationOne() {
        return peakShockAccelerationOne;
    }

    public void setDurationOfImpactAccelerationOne(String durationOfImpactAccelerationOne) {
        this.durationOfImpactAccelerationOne = durationOfImpactAccelerationOne;
    }

    public String getDurationOfImpactAccelerationOne() {
        return durationOfImpactAccelerationOne;
    }

    public void setShockSpectrumFrequencyRange(String shockSpectrumFrequencyRange) {
        this.shockSpectrumFrequencyRange = shockSpectrumFrequencyRange;
    }

    public String getShockSpectrumFrequencyRange() {
        return shockSpectrumFrequencyRange;
    }

    public void setImpactSpectrumValue(String impactSpectrumValue) {
        this.impactSpectrumValue = impactSpectrumValue;
    }

    public String getImpactSpectrumValue() {
        return impactSpectrumValue;
    }

    public void setNumberOfImpactsOne(String numberOfImpactsOne) {
        this.numberOfImpactsOne = numberOfImpactsOne;
    }

    public String getNumberOfImpactsOne() {
        return numberOfImpactsOne;
    }

    public void setPeakShockAccelerationMany(String peakShockAccelerationMany) {
        this.peakShockAccelerationMany = peakShockAccelerationMany;
    }

    public String getPeakShockAccelerationMany() {
        return peakShockAccelerationMany;
    }

    public void setDurationOfImpactAccelerationMany(String durationOfImpactAccelerationMany) {
        this.durationOfImpactAccelerationMany = durationOfImpactAccelerationMany;
    }

    public String getDurationOfImpactAccelerationMany() {
        return durationOfImpactAccelerationMany;
    }


    public void setFrequencyRangeSinusoidalVibration(String frequencyRangeSinusoidalVibration) {
        this.frequencyRangeSinusoidalVibration = frequencyRangeSinusoidalVibration;
    }

    public String getFrequencyRangeSinusoidalVibration() {
        return frequencyRangeSinusoidalVibration;
    }

    public void setAccelerationAmplitude(String accelerationAmplitude) {
        this.accelerationAmplitude = accelerationAmplitude;
    }

    public String getAccelerationAmplitude() {
        return accelerationAmplitude;
    }

    public void setExposureTimeSinusoidalVibration(String exposureTimeSinusoidalVibration) {
        this.exposureTimeSinusoidalVibration = exposureTimeSinusoidalVibration;
    }

    public String getExposureTimeSinusoidalVibration() {
        return exposureTimeSinusoidalVibration;
    }

    public void setFrequencyRangeBroadVibration(String frequencyRangeBroadVibration) {
        this.frequencyRangeBroadVibration = frequencyRangeBroadVibration;
    }

    public String getFrequencyRangeBroadVibration() {
        return frequencyRangeBroadVibration;
    }

    public void setAccelerationSpectralDensity(String accelerationSpectralDensity) {
        this.accelerationSpectralDensity = accelerationSpectralDensity;
    }

    public String getAccelerationSpectralDensity() {
        return accelerationSpectralDensity;
    }

    public void setRootMeanSquareAcceleration(String rootMeanSquareAcceleration) {
        this.rootMeanSquareAcceleration = rootMeanSquareAcceleration;
    }

    public String getRootMeanSquareAcceleration() {
        return rootMeanSquareAcceleration;
    }

    public void setExposureTimeBroadVibration(String exposureTimeBroadVibration) {
        this.exposureTimeBroadVibration = exposureTimeBroadVibration;
    }

    public String getExposureTimeBroadVibration() {
        return exposureTimeBroadVibration;
    }


}