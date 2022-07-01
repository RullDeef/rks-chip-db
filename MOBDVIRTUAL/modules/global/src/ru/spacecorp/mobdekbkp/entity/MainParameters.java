package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.OneToOne;

@Table(name = "MOBDEKBKP_MAIN_PARAMETERS")
@Entity(name = "mobdekbkp$MainParameters")
public class MainParameters extends StandardEntity {
    private static final long serialVersionUID = -3284795199759424266L;

    @Column(name = "T_PLUS")
    protected Integer tPlus;

    @Column(name = "T_MINUS")
    protected Integer tMinus;

    @Column(name = "RADIATION_RESISTANCE")
    protected String radiationResistance;

    @Column(name = "DISTINCTIVE_SIGN")
    protected String distinctiveSign;

    @Column(name = "OPERATING_FREQUENCYA_RANGE")
    protected String operatingFrequencyaRange;

    @Column(name = "ADJUSTMENT_FACTOR")
    protected String adjustmentFactor;

    @Column(name = "PHASE_NOISE_LEVEL")
    protected String phaseNoiseLevel;

    @Column(name = "OUTPUT_POWER")
    protected String outputPower;

    @Column(name = "SUPPLY_VOLTAGE")
    protected String supplyVoltage;

    @Column(name = "CONSUMPTION_CURRENT")
    protected String consumptionCurrent;

    @Column(name = "TECHNOLOGY")
    protected String technology;

    @Column(name = "MEMORY_CAPACITY")
    protected String memoryCapacity;

    @Column(name = "NUMBER_VALVES")
    protected String numberValves;

    @Column(name = "BUILT_MEMORY")
    protected String builtMemory;

    @Column(name = "MAXIMUM_ALLOWABLE_REVERSE_VOLTAGE")
    protected String maximumAllowableReverseVoltage;

    @Column(name = "MAXIMUM_PERMISSIBLE_AVERAGE_FORWARD_CURRENT")
    protected String maximumPermissibleAverageForwardCurrent;

    @Column(name = "MAXIMUM_ALLOWABLEDIRECT_CURRENT")
    protected String maximumAllowabledirectCurrent;

    @Column(name = "LIMITING_FREQUENCY")
    protected String limitingFrequency;

    @Column(name = "REVERSE_RECOVERY_TIME")
    protected String reverseRecoveryTime;

    @Column(name = "DIRECT_FORWARD_CURRENT")
    protected String directForwardCurrent;

    @Column(name = "CONSTANT_FORWARD_VOLTAGE")
    protected String constantForwardVoltage;

    @Column(name = "RADIATION_POWER")
    protected String radiationPower;

    @Column(name = "WAVELENGTH")
    protected String wavelength;

    @Column(name = "FALL_TIME")
    protected String fallTime;

    @Column(name = "AVERAGE_POWER_LASE_RADIATION")
    protected String averagePowerLaseRadiation;

    @Column(name = "LASER_WAVE_LENGTH")
    protected String laserWaveLength;

    @Column(name = "DIVERGENCE_LASER_RADIATION")
    protected String divergenceLaserRadiation;

    @Column(name = "PULSE_REPETITION_FREQUENCY_LASER_RADIATION")
    protected String pulseRepetitionFrequencyLaserRadiation;

    @Column(name = "OUTPUT_POWER2")
    protected String outputPower2;

    @Column(name = "WORKING_FREQUENCY")
    protected String workingFrequency;

    @Column(name = "POWER_DISSIPATED_ANODE")
    protected String powerDissipatedAnode;

    @Column(name = "SCREEN_BRIGHTNESS")
    protected String screenBrightness;

    @Column(name = "RESOLUTION")
    protected String resolution;

    @Column(name = "MODULATION_VOLTAGE")
    protected String modulationVoltage;

    @Column(name = "SPECTRAL_SENSITIVITY_REGION")
    protected String spectralSensitivityRegion;

    @Column(name = "NUMBER_PHOTOSENSITIVE_ELEMENTS")
    protected String numberPhotosensitiveElements;

    @Column(name = "GEOMETRIC_DIMENSIONS_PHOTOSENSITIVE_ELEMENT")
    protected String geometricDimensionsPhotosensitiveElement;

    @Column(name = "SPECIFIC_THRESHOLD_FLUX")
    protected String specificThresholdFlux;

    @Column(name = "CURRENT_INTEGRAL_SENSITIVITY")
    protected String currentIntegralSensitivity;

    @Column(name = "GLOW_BRIGHTNESS")
    protected String glowBrightness;

    @Column(name = "GLOW_COLOR")
    protected String glowColor;

    @Column(name = "MAXIMUM_PERMISSIBLE_EXTERNAL_ILLUMINATION")
    protected String maximumPermissibleExternalIllumination;

    @Column(name = "FREQUENCY_RANGE")
    protected String frequencyRange;

    @Column(name = "TUNING_ACCURACY")
    protected String tuningAccuracy;

    @Column(name = "IN_RANGE_OPERATING_TEMPERATURES")
    protected String inRangeOperatingTemperatures;

    @Column(name = "RESONATOR_HOUSING_SYMBOL")
    protected String resonatorHousingSymbol;

    @Column(name = "RATED_POWER_DISSIPATION")
    protected String ratedPowerDissipation;

    @Column(name = "NOMINAL_RESISTANCE")
    protected String nominalResistance;

    @Column(name = "RESISTANCE_TOLERANCE")
    protected String resistanceTolerance;

    @Column(name = "LIMITING_OPERATING_CURRENT")
    protected String limitingOperatingCurrent;

    @Column(name = "RATED_VOLTAGE")
    protected String ratedVoltage;

    @Column(name = "RATED_CAPACITY")
    protected String ratedCapacity;

    @Column(name = "DIMENSIONS")
    protected String dimensions;

    @Column(name = "CAPACITY_TOLERANCE")
    protected String capacityTolerance;

    @Column(name = "IMPACT_SHEAR_FORCE")
    protected String impactShearForce;

    @Column(name = "MINIMUM_DUTY_CYCLE")
    protected String minimumDutyCycle;

    @Column(name = "PULSE_DURATION")
    protected String pulseDuration;

    @Column(name = "SWITCHED_CURRENT")
    protected String switchedCurrent;

    @Column(name = "SWITCHING_VOLTAGE")
    protected String switchingVoltage;

    @Column(name = "NUMBER_CONTACT_GROUPS")
    protected String numberContactGroups;

    @Column(name = "SWITCHING_CURRENT_FREQUENCY")
    protected String switchingCurrentFrequency;

    @Column(name = "ATTACHED_CABLE_BRAND")
    protected String attachedCableBrand;

    @Column(name = "STRUCTURAL_PERFORMANCE")
    protected String structuralPerformance;

    @Column(name = "PATH_TYPE")
    protected String pathType;

    @Column(name = "POWER_")
    protected String power;

    @Column(name = "ROTATION_FREQUENCY")
    protected String rotationFrequency;

    @Column(name = "FREQUENCY_RANGE2")
    protected String frequencyRange2;

    @Column(name = "NOMINAL_VOLTAGE")
    protected String nominalVoltage;

    @Column(name = "RATED_CAPACITY2")
    protected String ratedCapacity2;

    @Column(name = "OVERALL_DIMENSIONS")
    protected String overallDimensions;

    @Column(name = "TEST_VOLTAGE")
    protected String testVoltage;

    @Column(name = "ATTENUATION_COEFFICIENT")
    protected String attenuationCoefficient;

    @Column(name = "IPUT_VOLTAGE")
    protected String iputVoltage;

    @Column(name = "OUTPUT_VOLTAGE")
    protected String outputVoltage;

    @Column(name = "OUTPUT_CURRENT_CHANNEL")
    protected String outputCurrentChannel;

    @Column(name = "DIAMETER_PITCH_NUT")
    protected String diameterPitchNut;

    @Column(name = "INSERTED_OPTICAL_LOSS")
    protected String insertedOpticalLoss;

    @Column(name = "NUMBER_ARTICULATIONS_DISMEMBERMENTS")
    protected String numberArticulationsDismemberments;

    @Column(name = "NUMBER_OPTICAL_POLES")
    protected String numberOpticalPoles;

    @Column(name = "VOLTAGE")
    protected String voltage;

    @Column(name = "ELECTRIC_CURRENT")
    protected String electricCurrent;

    @Column(name = "LUMINOUS_FLUX")
    protected String luminousFlux;

    @Column(name = "MINIMUM_OPERATING_TIME")
    protected String minimumOperatingTime;

    @Column(name = "INITIAL_RELATIVE_MAGNETIC_PERMEABILITY")
    protected String initialRelativeMagneticPermeability;

    @Column(name = "RELATIVE_GOODNESS")
    protected String relativeGoodness;

    @Column(name = "QFACTOR_MEASUREMENT_FREQUENCY")
    protected String qfactorMeasurementFrequency;

    @Column(name = "COEFFICIENT_ADJUSTMENT_ARMOR_CORES")
    protected String coefficientAdjustmentArmorCores;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_DEVICE_ID")
    protected ImportDevice importDevice;

    public void setOperatingFrequencyaRange(String operatingFrequencyaRange) {
        this.operatingFrequencyaRange = operatingFrequencyaRange;
    }

    public String getOperatingFrequencyaRange() {
        return operatingFrequencyaRange;
    }

    public void setAdjustmentFactor(String adjustmentFactor) {
        this.adjustmentFactor = adjustmentFactor;
    }

    public String getAdjustmentFactor() {
        return adjustmentFactor;
    }

    public void setPhaseNoiseLevel(String phaseNoiseLevel) {
        this.phaseNoiseLevel = phaseNoiseLevel;
    }

    public String getPhaseNoiseLevel() {
        return phaseNoiseLevel;
    }

    public void setOutputPower(String outputPower) {
        this.outputPower = outputPower;
    }

    public String getOutputPower() {
        return outputPower;
    }

    public void setSupplyVoltage(String supplyVoltage) {
        this.supplyVoltage = supplyVoltage;
    }

    public String getSupplyVoltage() {
        return supplyVoltage;
    }

    public void setConsumptionCurrent(String consumptionCurrent) {
        this.consumptionCurrent = consumptionCurrent;
    }

    public String getConsumptionCurrent() {
        return consumptionCurrent;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getTechnology() {
        return technology;
    }

    public void setMemoryCapacity(String memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    public String getMemoryCapacity() {
        return memoryCapacity;
    }

    public void setNumberValves(String numberValves) {
        this.numberValves = numberValves;
    }

    public String getNumberValves() {
        return numberValves;
    }

    public void setBuiltMemory(String builtMemory) {
        this.builtMemory = builtMemory;
    }

    public String getBuiltMemory() {
        return builtMemory;
    }

    public void setMaximumAllowableReverseVoltage(String maximumAllowableReverseVoltage) {
        this.maximumAllowableReverseVoltage = maximumAllowableReverseVoltage;
    }

    public String getMaximumAllowableReverseVoltage() {
        return maximumAllowableReverseVoltage;
    }

    public void setMaximumPermissibleAverageForwardCurrent(String maximumPermissibleAverageForwardCurrent) {
        this.maximumPermissibleAverageForwardCurrent = maximumPermissibleAverageForwardCurrent;
    }

    public String getMaximumPermissibleAverageForwardCurrent() {
        return maximumPermissibleAverageForwardCurrent;
    }

    public void setMaximumAllowabledirectCurrent(String maximumAllowabledirectCurrent) {
        this.maximumAllowabledirectCurrent = maximumAllowabledirectCurrent;
    }

    public String getMaximumAllowabledirectCurrent() {
        return maximumAllowabledirectCurrent;
    }

    public void setLimitingFrequency(String limitingFrequency) {
        this.limitingFrequency = limitingFrequency;
    }

    public String getLimitingFrequency() {
        return limitingFrequency;
    }

    public void setReverseRecoveryTime(String reverseRecoveryTime) {
        this.reverseRecoveryTime = reverseRecoveryTime;
    }

    public String getReverseRecoveryTime() {
        return reverseRecoveryTime;
    }

    public void setDirectForwardCurrent(String directForwardCurrent) {
        this.directForwardCurrent = directForwardCurrent;
    }

    public String getDirectForwardCurrent() {
        return directForwardCurrent;
    }

    public void setConstantForwardVoltage(String constantForwardVoltage) {
        this.constantForwardVoltage = constantForwardVoltage;
    }

    public String getConstantForwardVoltage() {
        return constantForwardVoltage;
    }

    public void setRadiationPower(String radiationPower) {
        this.radiationPower = radiationPower;
    }

    public String getRadiationPower() {
        return radiationPower;
    }

    public void setWavelength(String wavelength) {
        this.wavelength = wavelength;
    }

    public String getWavelength() {
        return wavelength;
    }

    public void setFallTime(String fallTime) {
        this.fallTime = fallTime;
    }

    public String getFallTime() {
        return fallTime;
    }

    public void setAveragePowerLaseRadiation(String averagePowerLaseRadiation) {
        this.averagePowerLaseRadiation = averagePowerLaseRadiation;
    }

    public String getAveragePowerLaseRadiation() {
        return averagePowerLaseRadiation;
    }

    public void setLaserWaveLength(String laserWaveLength) {
        this.laserWaveLength = laserWaveLength;
    }

    public String getLaserWaveLength() {
        return laserWaveLength;
    }

    public void setDivergenceLaserRadiation(String divergenceLaserRadiation) {
        this.divergenceLaserRadiation = divergenceLaserRadiation;
    }

    public String getDivergenceLaserRadiation() {
        return divergenceLaserRadiation;
    }

    public void setPulseRepetitionFrequencyLaserRadiation(String pulseRepetitionFrequencyLaserRadiation) {
        this.pulseRepetitionFrequencyLaserRadiation = pulseRepetitionFrequencyLaserRadiation;
    }

    public String getPulseRepetitionFrequencyLaserRadiation() {
        return pulseRepetitionFrequencyLaserRadiation;
    }

    public void setOutputPower2(String outputPower2) {
        this.outputPower2 = outputPower2;
    }

    public String getOutputPower2() {
        return outputPower2;
    }

    public void setWorkingFrequency(String workingFrequency) {
        this.workingFrequency = workingFrequency;
    }

    public String getWorkingFrequency() {
        return workingFrequency;
    }

    public void setPowerDissipatedAnode(String powerDissipatedAnode) {
        this.powerDissipatedAnode = powerDissipatedAnode;
    }

    public String getPowerDissipatedAnode() {
        return powerDissipatedAnode;
    }

    public void setScreenBrightness(String screenBrightness) {
        this.screenBrightness = screenBrightness;
    }

    public String getScreenBrightness() {
        return screenBrightness;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getResolution() {
        return resolution;
    }

    public void setModulationVoltage(String modulationVoltage) {
        this.modulationVoltage = modulationVoltage;
    }

    public String getModulationVoltage() {
        return modulationVoltage;
    }

    public void setSpectralSensitivityRegion(String spectralSensitivityRegion) {
        this.spectralSensitivityRegion = spectralSensitivityRegion;
    }

    public String getSpectralSensitivityRegion() {
        return spectralSensitivityRegion;
    }

    public void setNumberPhotosensitiveElements(String numberPhotosensitiveElements) {
        this.numberPhotosensitiveElements = numberPhotosensitiveElements;
    }

    public String getNumberPhotosensitiveElements() {
        return numberPhotosensitiveElements;
    }

    public void setGeometricDimensionsPhotosensitiveElement(String geometricDimensionsPhotosensitiveElement) {
        this.geometricDimensionsPhotosensitiveElement = geometricDimensionsPhotosensitiveElement;
    }

    public String getGeometricDimensionsPhotosensitiveElement() {
        return geometricDimensionsPhotosensitiveElement;
    }

    public void setSpecificThresholdFlux(String specificThresholdFlux) {
        this.specificThresholdFlux = specificThresholdFlux;
    }

    public String getSpecificThresholdFlux() {
        return specificThresholdFlux;
    }

    public void setCurrentIntegralSensitivity(String currentIntegralSensitivity) {
        this.currentIntegralSensitivity = currentIntegralSensitivity;
    }

    public String getCurrentIntegralSensitivity() {
        return currentIntegralSensitivity;
    }

    public void setGlowBrightness(String glowBrightness) {
        this.glowBrightness = glowBrightness;
    }

    public String getGlowBrightness() {
        return glowBrightness;
    }

    public void setGlowColor(String glowColor) {
        this.glowColor = glowColor;
    }

    public String getGlowColor() {
        return glowColor;
    }

    public void setMaximumPermissibleExternalIllumination(String maximumPermissibleExternalIllumination) {
        this.maximumPermissibleExternalIllumination = maximumPermissibleExternalIllumination;
    }

    public String getMaximumPermissibleExternalIllumination() {
        return maximumPermissibleExternalIllumination;
    }

    public void setFrequencyRange(String frequencyRange) {
        this.frequencyRange = frequencyRange;
    }

    public String getFrequencyRange() {
        return frequencyRange;
    }

    public void setTuningAccuracy(String tuningAccuracy) {
        this.tuningAccuracy = tuningAccuracy;
    }

    public String getTuningAccuracy() {
        return tuningAccuracy;
    }

    public void setInRangeOperatingTemperatures(String inRangeOperatingTemperatures) {
        this.inRangeOperatingTemperatures = inRangeOperatingTemperatures;
    }

    public String getInRangeOperatingTemperatures() {
        return inRangeOperatingTemperatures;
    }

    public void setResonatorHousingSymbol(String resonatorHousingSymbol) {
        this.resonatorHousingSymbol = resonatorHousingSymbol;
    }

    public String getResonatorHousingSymbol() {
        return resonatorHousingSymbol;
    }

    public void setRatedPowerDissipation(String ratedPowerDissipation) {
        this.ratedPowerDissipation = ratedPowerDissipation;
    }

    public String getRatedPowerDissipation() {
        return ratedPowerDissipation;
    }

    public void setNominalResistance(String nominalResistance) {
        this.nominalResistance = nominalResistance;
    }

    public String getNominalResistance() {
        return nominalResistance;
    }

    public void setResistanceTolerance(String resistanceTolerance) {
        this.resistanceTolerance = resistanceTolerance;
    }

    public String getResistanceTolerance() {
        return resistanceTolerance;
    }

    public void setLimitingOperatingCurrent(String limitingOperatingCurrent) {
        this.limitingOperatingCurrent = limitingOperatingCurrent;
    }

    public String getLimitingOperatingCurrent() {
        return limitingOperatingCurrent;
    }

    public void setRatedVoltage(String ratedVoltage) {
        this.ratedVoltage = ratedVoltage;
    }

    public String getRatedVoltage() {
        return ratedVoltage;
    }

    public void setRatedCapacity(String ratedCapacity) {
        this.ratedCapacity = ratedCapacity;
    }

    public String getRatedCapacity() {
        return ratedCapacity;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setCapacityTolerance(String capacityTolerance) {
        this.capacityTolerance = capacityTolerance;
    }

    public String getCapacityTolerance() {
        return capacityTolerance;
    }

    public void setImpactShearForce(String impactShearForce) {
        this.impactShearForce = impactShearForce;
    }

    public String getImpactShearForce() {
        return impactShearForce;
    }

    public void setMinimumDutyCycle(String minimumDutyCycle) {
        this.minimumDutyCycle = minimumDutyCycle;
    }

    public String getMinimumDutyCycle() {
        return minimumDutyCycle;
    }

    public void setPulseDuration(String pulseDuration) {
        this.pulseDuration = pulseDuration;
    }

    public String getPulseDuration() {
        return pulseDuration;
    }

    public void setSwitchedCurrent(String switchedCurrent) {
        this.switchedCurrent = switchedCurrent;
    }

    public String getSwitchedCurrent() {
        return switchedCurrent;
    }

    public void setSwitchingVoltage(String switchingVoltage) {
        this.switchingVoltage = switchingVoltage;
    }

    public String getSwitchingVoltage() {
        return switchingVoltage;
    }

    public void setNumberContactGroups(String numberContactGroups) {
        this.numberContactGroups = numberContactGroups;
    }

    public String getNumberContactGroups() {
        return numberContactGroups;
    }

    public void setSwitchingCurrentFrequency(String switchingCurrentFrequency) {
        this.switchingCurrentFrequency = switchingCurrentFrequency;
    }

    public String getSwitchingCurrentFrequency() {
        return switchingCurrentFrequency;
    }

    public void setAttachedCableBrand(String attachedCableBrand) {
        this.attachedCableBrand = attachedCableBrand;
    }

    public String getAttachedCableBrand() {
        return attachedCableBrand;
    }

    public void setStructuralPerformance(String structuralPerformance) {
        this.structuralPerformance = structuralPerformance;
    }

    public String getStructuralPerformance() {
        return structuralPerformance;
    }

    public void setPathType(String pathType) {
        this.pathType = pathType;
    }

    public String getPathType() {
        return pathType;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getPower() {
        return power;
    }

    public void setRotationFrequency(String rotationFrequency) {
        this.rotationFrequency = rotationFrequency;
    }

    public String getRotationFrequency() {
        return rotationFrequency;
    }

    public void setFrequencyRange2(String frequencyRange2) {
        this.frequencyRange2 = frequencyRange2;
    }

    public String getFrequencyRange2() {
        return frequencyRange2;
    }

    public void setNominalVoltage(String nominalVoltage) {
        this.nominalVoltage = nominalVoltage;
    }

    public String getNominalVoltage() {
        return nominalVoltage;
    }

    public void setRatedCapacity2(String ratedCapacity2) {
        this.ratedCapacity2 = ratedCapacity2;
    }

    public String getRatedCapacity2() {
        return ratedCapacity2;
    }

    public void setOverallDimensions(String overallDimensions) {
        this.overallDimensions = overallDimensions;
    }

    public String getOverallDimensions() {
        return overallDimensions;
    }

    public void setTestVoltage(String testVoltage) {
        this.testVoltage = testVoltage;
    }

    public String getTestVoltage() {
        return testVoltage;
    }

    public void setAttenuationCoefficient(String attenuationCoefficient) {
        this.attenuationCoefficient = attenuationCoefficient;
    }

    public String getAttenuationCoefficient() {
        return attenuationCoefficient;
    }

    public void setIputVoltage(String iputVoltage) {
        this.iputVoltage = iputVoltage;
    }

    public String getIputVoltage() {
        return iputVoltage;
    }

    public void setOutputVoltage(String outputVoltage) {
        this.outputVoltage = outputVoltage;
    }

    public String getOutputVoltage() {
        return outputVoltage;
    }

    public void setOutputCurrentChannel(String outputCurrentChannel) {
        this.outputCurrentChannel = outputCurrentChannel;
    }

    public String getOutputCurrentChannel() {
        return outputCurrentChannel;
    }

    public void setDiameterPitchNut(String diameterPitchNut) {
        this.diameterPitchNut = diameterPitchNut;
    }

    public String getDiameterPitchNut() {
        return diameterPitchNut;
    }

    public void setInsertedOpticalLoss(String insertedOpticalLoss) {
        this.insertedOpticalLoss = insertedOpticalLoss;
    }

    public String getInsertedOpticalLoss() {
        return insertedOpticalLoss;
    }

    public void setNumberArticulationsDismemberments(String numberArticulationsDismemberments) {
        this.numberArticulationsDismemberments = numberArticulationsDismemberments;
    }

    public String getNumberArticulationsDismemberments() {
        return numberArticulationsDismemberments;
    }

    public void setNumberOpticalPoles(String numberOpticalPoles) {
        this.numberOpticalPoles = numberOpticalPoles;
    }

    public String getNumberOpticalPoles() {
        return numberOpticalPoles;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setElectricCurrent(String electricCurrent) {
        this.electricCurrent = electricCurrent;
    }

    public String getElectricCurrent() {
        return electricCurrent;
    }

    public void setLuminousFlux(String luminousFlux) {
        this.luminousFlux = luminousFlux;
    }

    public String getLuminousFlux() {
        return luminousFlux;
    }

    public void setMinimumOperatingTime(String minimumOperatingTime) {
        this.minimumOperatingTime = minimumOperatingTime;
    }

    public String getMinimumOperatingTime() {
        return minimumOperatingTime;
    }

    public void setInitialRelativeMagneticPermeability(String initialRelativeMagneticPermeability) {
        this.initialRelativeMagneticPermeability = initialRelativeMagneticPermeability;
    }

    public String getInitialRelativeMagneticPermeability() {
        return initialRelativeMagneticPermeability;
    }

    public void setRelativeGoodness(String relativeGoodness) {
        this.relativeGoodness = relativeGoodness;
    }

    public String getRelativeGoodness() {
        return relativeGoodness;
    }

    public void setQfactorMeasurementFrequency(String qfactorMeasurementFrequency) {
        this.qfactorMeasurementFrequency = qfactorMeasurementFrequency;
    }

    public String getQfactorMeasurementFrequency() {
        return qfactorMeasurementFrequency;
    }

    public void setCoefficientAdjustmentArmorCores(String coefficientAdjustmentArmorCores) {
        this.coefficientAdjustmentArmorCores = coefficientAdjustmentArmorCores;
    }

    public String getCoefficientAdjustmentArmorCores() {
        return coefficientAdjustmentArmorCores;
    }


    public void setRadiationResistance(RadiationResistance radiationResistance) {
        this.radiationResistance = radiationResistance == null ? null : radiationResistance.getId();
    }

    public RadiationResistance getRadiationResistance() {
        return radiationResistance == null ? null : RadiationResistance.fromId(radiationResistance);
    }

    public void setDistinctiveSign(DistinctiveSign distinctiveSign) {
        this.distinctiveSign = distinctiveSign == null ? null : distinctiveSign.getId();
    }

    public DistinctiveSign getDistinctiveSign() {
        return distinctiveSign == null ? null : DistinctiveSign.fromId(distinctiveSign);
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


    public void setTPlus(Integer tPlus) {
        this.tPlus = tPlus;
    }

    public Integer getTPlus() {
        return tPlus;
    }

    public void setTMinus(Integer tMinus) {
        this.tMinus = tMinus;
    }

    public Integer getTMinus() {
        return tMinus;
    }

    public void setImportDevice(ImportDevice importDevice) {
        this.importDevice = importDevice;
    }

    public ImportDevice getImportDevice() {
        return importDevice;
    }


}