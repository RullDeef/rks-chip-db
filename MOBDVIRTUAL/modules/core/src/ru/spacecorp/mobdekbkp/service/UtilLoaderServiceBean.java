package ru.spacecorp.mobdekbkp.service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.spacecorp.mobdekbkp.entity.*;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.Bidi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(UtilLoaderService.NAME)
public class UtilLoaderServiceBean implements UtilLoaderService {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;

    public void getUploadFile(File uploadfile) {
        BidiMap<String, String> map = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(uploadfile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("plmxml_cl:ICO");
            for (int i = 0; i < nodeList.getLength(); i++) {
                map = new TreeBidiMap<>();
                Element element = (Element) nodeList.item(i);
                for (int j = 0; j < element.getChildNodes().getLength(); j++) {
                    NodeList list = element.getChildNodes();
                    Node element1 = list.item(j);
                    if (element1.getNodeName() == "plmxml_cl:Property") {
                        String value = element1.getTextContent().replaceAll("\n", "");
                        String id = element1.getAttributes().getNamedItem("attributeId").getNodeValue();
                        if (value != null && id != null && value != "" && id != "") {
                            map.put(id, value);
                        }
                    }
                }
                insertType(map, uploadfile.getName());
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void insertType(BidiMap<String, String> map, String fileName) {
        String nameOrType = map.get("20004");
        String name2 = map.get("20003");
        String name3 = map.get("20002");
        String device = map.get("20001");
        String technicalCondition = map.get("20005");
        if (nameOrType != null && name2 != null && name3 != null && device != null) {
            String newNameOrType = escaping(nameOrType);
            String newDevice = escaping(device);
            TypeClass typeClass = null;
            TypeClass typeClassWithoutFourClass = null;
            Type type = null;
            try (Transaction tx = persistence.createTransaction()) {
                TypedQuery<TypeClass> queryClass = persistence.getEntityManager().createQuery(
                        "select c1 from mobdekbkp$TypeClass c1 left join c1.parent c2 " +
                                "left join c2.parent c3 where c1.name = '" + newNameOrType + "' and c2.name = '" +
                                name2 + "' and c3.name = '" + name3 + "'", TypeClass.class);
                typeClass = queryClass.getFirstResult();
                if (typeClass == null) {
                    queryClass = persistence.getEntityManager().createQuery(
                            "select c1 from mobdekbkp$TypeClass c1 left join c1.parent c2 " +
                                    "left join c2.parent c3 where c1.name = '" + name2 + "' and c2.name = '" +
                                    name3 + "'", TypeClass.class);
                    typeClass = queryClass.getFirstResult();
                }
                if (typeClassWithoutFourClass != null || typeClass != null) {
                    TypedQuery<Type> queryType = persistence.getEntityManager().createQuery(
                            "select e from mobdekbkp$Type e join e.typeClass c where e.designation = '" +
                                    newNameOrType + "'", Type.class);
                    type = queryType.getFirstResult();
                    if (type == null) {
                        type = metadata.create(Type.class);
                        type.setTypeClass(typeClass);
                        type.setDesignation(newNameOrType);
                        persistence.getEntityManager().persist(type);
                    }

                    TypedQuery<Typonominal> queryDevice = persistence.getEntityManager().createQuery(
                            "select e from mobdekbkp$Typonominal e join e.type t where e.name = '" +
                                    newDevice + "'", Typonominal.class);
                    Typonominal typonominal = queryDevice.getFirstResult();
                    if (typonominal == null) {
                        typonominal = metadata.create(Typonominal.class);
                        typonominal.setType(type);
                        typonominal.setName(newDevice);
                        typonominal.setTechnicalCondition(technicalCondition);
                        persistence.getEntityManager().persist(typonominal);
                    }
                    tx.commit();
                } else {
                    logFileData(fileName, newDevice, newNameOrType, name2, name3);
                }
            }
            if (type != null) {
                try (Transaction tx = persistence.createTransaction()) {
                    TypedQuery<Typonominal> queryDevice = persistence.getEntityManager().createQuery(
                            "select e from mobdekbkp$Typonominal e join e.type t where e.name = '" +
                                    newDevice + "'", Typonominal.class);
                    Typonominal typonominal = queryDevice.getFirstResult();
                    getFactoryFromFile(map, typonominal.getFactory(), typonominal);
                    getBasicInfoFromFile(map, typonominal.getBasicInformation(), typonominal);
                    getAdvancedSettingFromFile(map, typonominal.getAdvancedSetting(), typonominal);
                    getParametersForPurchasingFromFile(map, typonominal.getParametersForPurchasing(), typonominal);
                    getReliabilityIndicatorsFromFile(map, typonominal.getReliabilityIndicators(), typonominal);
                    getCorpusFromFile(map, typonominal.getCorpus(), typonominal);
                    getmontageParametersFromFile(map, typonominal.getMontageParameters(), typonominal);
                    getTechnicalParametersFromFile(map, typonominal.getTechnicalParameters(), typonominal);
                    getMainParametersFromFile(map, typonominal.getMainParameters(), typonominal);
                    tx.commit();
                }
            }
        } else {
            logFileData(fileName, device, nameOrType, name2, name3);
        }
    }

    private void getMainParametersFromFile(BidiMap<String, String> map, MainParameters mainParameters, Typonominal typonominal) {
        int tPlus = 0;
        if (map.get("20012") != null) {
            tPlus = Integer.parseInt(map.get("20012"));
        }
        int tMinus = 0;
        if (map.get("20013") != null) {
            tMinus = Integer.parseInt(map.get("20013"));
        }
        String radiationResistance = map.get("20014");
        String distinctiveSign = map.get("20016");
        String operatingFrequencyaRange = map.get("20142");
        String adjustmentFactor = map.get("20143");
        String phaseNoiseLevel = map.get("20144");
        String outputPower = map.get("20145");
        String supplyVoltage = map.get("20069");
        String consumptionCurrent = map.get("20070");
        String technology = map.get("20071");
        String memoryCapacity = map.get("105010034");
        String numberValves = map.get("105010079");
        String builtMemory = map.get("105010039");
        String maximumAllowableReverseVoltage = map.get("20073");
        String maximumPermissibleAverageForwardCurrent = map.get("20074");
        String maximumAllowabledirectCurrent = map.get("20075");
        String limitingFrequency = map.get("20076");
        String reverseRecoveryTime = map.get("20077");
        String directForwardCurrent = map.get("20078");
        String constantForwardVoltage = map.get("20079");
        String radiationPower = map.get("20080");
        String wavelength = map.get("20081");
        String fallTime = map.get("20082");
        String averagePowerLaseRadiation = map.get("20137");
        String laserWaveLength = map.get("20138");
        String divergenceLaserRadiation = map.get("20139");
        String pulseRepetitionFrequencyLaserRadiation = map.get("20140");
        String outputPower2 = map.get("20083");
        String workingFrequency = map.get("20084");
        String powerDissipatedAnode = map.get("20085");
        String screenBrightness = map.get("20086");
        String resolution = map.get("20087");
        String modulationVoltage = map.get("20088");
        String spectralSensitivityRegion = map.get("20089");
        String numberPhotosensitiveElements = map.get("20090");
        String geometricDimensionsPhotosensitiveElement = map.get("20091");
        String specificThresholdFlux = map.get("20092");
        String currentIntegralSensitivity = map.get("20093");
        String glowBrightness = map.get("20094");
        String glowColor = map.get("20095");
        String maximumPermissibleExternalIllumination = map.get("20096");
        String frequencyRange = map.get("20097");
        String tuningAccuracy = map.get("20098");
        String inRangeOperatingTemperatures = map.get("20099");
        String resonatorHousingSymbol = map.get("20100");
        String ratedPowerDissipation = map.get("20101");
        String nominalResistance = map.get("20102");
        String resistanceTolerance = map.get("20103");
        String limitingOperatingCurrent = map.get("20104");
        String ratedVoltage = map.get("5024");
        String ratedCapacity = map.get("105010096");
        String dimensions = map.get("105010051");
        String capacityTolerance = map.get("105010097");
        String impactShearForce = map.get("105010098");
        String minimumDutyCycle = map.get("20106");
        String pulseDuration = map.get("20107");
        String switchedCurrent = map.get("20108");
        String switchingVoltage = map.get("20109");
        String numberContactGroups = map.get("20110");
        String switchingCurrentFrequency = map.get("20111");
        String attachedCableBrand = map.get("20112");
        String structuralPerformance = map.get("20113");
        String pathType = map.get("20114");
        String power = map.get("20115");
        String rotationFrequency = map.get("20116");
        String frequencyRange2 = map.get("5063");
        String nominalVoltage = map.get("20117");
        String ratedCapacity2 = map.get("20118");
        String overallDimensions = map.get("20119");
        String testVoltage = map.get("20120");
        String attenuationCoefficient = map.get("20121");
        String iputVoltage = map.get("20122");
        String outputVoltage = map.get("20123");
        String outputCurrentChannel = map.get("20124");
        String diameterPitchNut = map.get("20125");
        String insertedOpticalLoss = map.get("20126");
        String numberArticulationsDismemberments = map.get("20127");
        String numberOpticalPoles = map.get("20128");
        String voltage = map.get("20129");
        String electricCurrent = map.get("20130");
        String luminousFlux = map.get("20131");
        String minimumOperatingTime = map.get("20132");
        String initialRelativeMagneticPermeability = map.get("20133");
        String relativeGoodness = map.get("20134");
        String qfactorMeasurementFrequency = map.get("20135");
        String coefficientAdjustmentArmorCores = map.get("20136");

        try (Transaction tx = persistence.createTransaction()) {
            if (mainParameters != null) {
                mainParameters.setTPlus(tPlus);
                mainParameters.setTMinus(tMinus);
                mainParameters.setRadiationResistance(RadiationResistance.fromId(radiationResistance));
                mainParameters.setDistinctiveSign(DistinctiveSign.fromId(distinctiveSign));
                mainParameters.setOperatingFrequencyaRange(operatingFrequencyaRange);
                mainParameters.setAdjustmentFactor(adjustmentFactor);
                mainParameters.setPhaseNoiseLevel(phaseNoiseLevel);
                mainParameters.setOutputPower(outputPower);
                mainParameters.setSupplyVoltage(supplyVoltage);
                mainParameters.setConsumptionCurrent(consumptionCurrent);
                mainParameters.setTechnology(technology);
                mainParameters.setMemoryCapacity(memoryCapacity);
                mainParameters.setNumberValves(numberValves);
                mainParameters.setBuiltMemory(builtMemory);
                mainParameters.setMaximumAllowableReverseVoltage(maximumAllowableReverseVoltage);
                mainParameters.setMaximumPermissibleAverageForwardCurrent(maximumPermissibleAverageForwardCurrent);
                mainParameters.setMaximumAllowabledirectCurrent(maximumAllowabledirectCurrent);
                mainParameters.setLimitingFrequency(limitingFrequency);
                mainParameters.setReverseRecoveryTime(reverseRecoveryTime);
                mainParameters.setDirectForwardCurrent(directForwardCurrent);
                mainParameters.setConstantForwardVoltage(constantForwardVoltage);
                mainParameters.setRadiationPower(radiationPower);
                mainParameters.setWavelength(wavelength);
                mainParameters.setFallTime(fallTime);
                mainParameters.setAveragePowerLaseRadiation(averagePowerLaseRadiation);
                mainParameters.setLaserWaveLength(laserWaveLength);
                mainParameters.setDivergenceLaserRadiation(divergenceLaserRadiation);
                mainParameters.setPulseRepetitionFrequencyLaserRadiation(pulseRepetitionFrequencyLaserRadiation);
                mainParameters.setOutputPower2(outputPower2);
                mainParameters.setWorkingFrequency(workingFrequency);
                mainParameters.setPowerDissipatedAnode(powerDissipatedAnode);
                mainParameters.setScreenBrightness(screenBrightness);
                mainParameters.setResolution(resolution);
                mainParameters.setModulationVoltage(modulationVoltage);
                mainParameters.setSpectralSensitivityRegion(spectralSensitivityRegion);
                mainParameters.setNumberPhotosensitiveElements(numberPhotosensitiveElements);
                mainParameters.setGeometricDimensionsPhotosensitiveElement(geometricDimensionsPhotosensitiveElement);
                mainParameters.setSpecificThresholdFlux(specificThresholdFlux);
                mainParameters.setCurrentIntegralSensitivity(currentIntegralSensitivity);
                mainParameters.setGlowBrightness(glowBrightness);
                mainParameters.setGlowColor(glowColor);
                mainParameters.setMaximumPermissibleExternalIllumination(maximumPermissibleExternalIllumination);
                mainParameters.setFrequencyRange(frequencyRange);
                mainParameters.setTuningAccuracy(tuningAccuracy);
                mainParameters.setInRangeOperatingTemperatures(inRangeOperatingTemperatures);
                mainParameters.setResonatorHousingSymbol(resonatorHousingSymbol);
                mainParameters.setRatedPowerDissipation(ratedPowerDissipation);
                mainParameters.setNominalResistance(nominalResistance);
                mainParameters.setResistanceTolerance(resistanceTolerance);
                mainParameters.setLimitingOperatingCurrent(limitingOperatingCurrent);
                mainParameters.setRatedVoltage(ratedVoltage);
                mainParameters.setRatedCapacity(ratedCapacity);
                mainParameters.setDimensions(dimensions);
                mainParameters.setCapacityTolerance(capacityTolerance);
                mainParameters.setImpactShearForce(impactShearForce);
                mainParameters.setMinimumDutyCycle(minimumDutyCycle);
                mainParameters.setPulseDuration(pulseDuration);
                mainParameters.setSwitchedCurrent(switchedCurrent);
                mainParameters.setSwitchingVoltage(switchingVoltage);
                mainParameters.setNumberContactGroups(numberContactGroups);
                mainParameters.setSwitchingCurrentFrequency(switchingCurrentFrequency);
                mainParameters.setAttachedCableBrand(attachedCableBrand);
                mainParameters.setStructuralPerformance(structuralPerformance);
                mainParameters.setPathType(pathType);
                mainParameters.setPower(power);
                mainParameters.setRotationFrequency(rotationFrequency);
                mainParameters.setFrequencyRange2(frequencyRange2);
                mainParameters.setNominalVoltage(nominalVoltage);
                mainParameters.setRatedCapacity2(ratedCapacity2);
                mainParameters.setOverallDimensions(overallDimensions);
                mainParameters.setTestVoltage(testVoltage);
                mainParameters.setAttenuationCoefficient(attenuationCoefficient);
                mainParameters.setIputVoltage(iputVoltage);
                mainParameters.setOutputVoltage(outputVoltage);
                mainParameters.setOutputCurrentChannel(outputCurrentChannel);
                mainParameters.setDiameterPitchNut(diameterPitchNut);
                mainParameters.setInsertedOpticalLoss(insertedOpticalLoss);
                mainParameters.setNumberArticulationsDismemberments(numberArticulationsDismemberments);
                mainParameters.setNumberOpticalPoles(numberOpticalPoles);
                mainParameters.setVoltage(voltage);
                mainParameters.setElectricCurrent(electricCurrent);
                mainParameters.setLuminousFlux(luminousFlux);
                mainParameters.setMinimumOperatingTime(minimumOperatingTime);
                mainParameters.setInitialRelativeMagneticPermeability(initialRelativeMagneticPermeability);
                mainParameters.setRelativeGoodness(relativeGoodness);
                mainParameters.setQfactorMeasurementFrequency(qfactorMeasurementFrequency);
                mainParameters.setCoefficientAdjustmentArmorCores(coefficientAdjustmentArmorCores);
                persistence.getEntityManager().merge(mainParameters);
            } else {
                mainParameters = metadata.create(MainParameters.class);
                try (Transaction txTransaction = persistence.createTransaction()) {
                    typonominal.setMainParameters(mainParameters);
                    persistence.getEntityManager().merge(typonominal);
                    txTransaction.commit();
                }
                mainParameters.setTPlus(tPlus);
                mainParameters.setTMinus(tMinus);
                mainParameters.setRadiationResistance(RadiationResistance.fromId(radiationResistance));
                mainParameters.setDistinctiveSign(DistinctiveSign.fromId(distinctiveSign));
                mainParameters.setOperatingFrequencyaRange(operatingFrequencyaRange);
                mainParameters.setAdjustmentFactor(adjustmentFactor);
                mainParameters.setPhaseNoiseLevel(phaseNoiseLevel);
                mainParameters.setOutputPower(outputPower);
                mainParameters.setSupplyVoltage(supplyVoltage);
                mainParameters.setConsumptionCurrent(consumptionCurrent);
                mainParameters.setTechnology(technology);
                mainParameters.setMemoryCapacity(memoryCapacity);
                mainParameters.setNumberValves(numberValves);
                mainParameters.setBuiltMemory(builtMemory);
                mainParameters.setMaximumAllowableReverseVoltage(maximumAllowableReverseVoltage);
                mainParameters.setMaximumPermissibleAverageForwardCurrent(maximumPermissibleAverageForwardCurrent);
                mainParameters.setMaximumAllowabledirectCurrent(maximumAllowabledirectCurrent);
                mainParameters.setLimitingFrequency(limitingFrequency);
                mainParameters.setReverseRecoveryTime(reverseRecoveryTime);
                mainParameters.setDirectForwardCurrent(directForwardCurrent);
                mainParameters.setConstantForwardVoltage(constantForwardVoltage);
                mainParameters.setRadiationPower(radiationPower);
                mainParameters.setWavelength(wavelength);
                mainParameters.setFallTime(fallTime);
                mainParameters.setAveragePowerLaseRadiation(averagePowerLaseRadiation);
                mainParameters.setLaserWaveLength(laserWaveLength);
                mainParameters.setDivergenceLaserRadiation(divergenceLaserRadiation);
                mainParameters.setPulseRepetitionFrequencyLaserRadiation(pulseRepetitionFrequencyLaserRadiation);
                mainParameters.setOutputPower2(outputPower2);
                mainParameters.setWorkingFrequency(workingFrequency);
                mainParameters.setPowerDissipatedAnode(powerDissipatedAnode);
                mainParameters.setScreenBrightness(screenBrightness);
                mainParameters.setResolution(resolution);
                mainParameters.setModulationVoltage(modulationVoltage);
                mainParameters.setSpectralSensitivityRegion(spectralSensitivityRegion);
                mainParameters.setNumberPhotosensitiveElements(numberPhotosensitiveElements);
                mainParameters.setGeometricDimensionsPhotosensitiveElement(geometricDimensionsPhotosensitiveElement);
                mainParameters.setSpecificThresholdFlux(specificThresholdFlux);
                mainParameters.setCurrentIntegralSensitivity(currentIntegralSensitivity);
                mainParameters.setGlowBrightness(glowBrightness);
                mainParameters.setGlowColor(glowColor);
                mainParameters.setMaximumPermissibleExternalIllumination(maximumPermissibleExternalIllumination);
                mainParameters.setFrequencyRange(frequencyRange);
                mainParameters.setTuningAccuracy(tuningAccuracy);
                mainParameters.setInRangeOperatingTemperatures(inRangeOperatingTemperatures);
                mainParameters.setResonatorHousingSymbol(resonatorHousingSymbol);
                mainParameters.setRatedPowerDissipation(ratedPowerDissipation);
                mainParameters.setNominalResistance(nominalResistance);
                mainParameters.setResistanceTolerance(resistanceTolerance);
                mainParameters.setLimitingOperatingCurrent(limitingOperatingCurrent);
                mainParameters.setRatedVoltage(ratedVoltage);
                mainParameters.setRatedCapacity(ratedCapacity);
                mainParameters.setDimensions(dimensions);
                mainParameters.setCapacityTolerance(capacityTolerance);
                mainParameters.setImpactShearForce(impactShearForce);
                mainParameters.setMinimumDutyCycle(minimumDutyCycle);
                mainParameters.setPulseDuration(pulseDuration);
                mainParameters.setSwitchedCurrent(switchedCurrent);
                mainParameters.setSwitchingVoltage(switchingVoltage);
                mainParameters.setNumberContactGroups(numberContactGroups);
                mainParameters.setSwitchingCurrentFrequency(switchingCurrentFrequency);
                mainParameters.setAttachedCableBrand(attachedCableBrand);
                mainParameters.setStructuralPerformance(structuralPerformance);
                mainParameters.setPathType(pathType);
                mainParameters.setPower(power);
                mainParameters.setRotationFrequency(rotationFrequency);
                mainParameters.setFrequencyRange2(frequencyRange2);
                mainParameters.setNominalVoltage(nominalVoltage);
                mainParameters.setRatedCapacity2(ratedCapacity2);
                mainParameters.setOverallDimensions(overallDimensions);
                mainParameters.setTestVoltage(testVoltage);
                mainParameters.setAttenuationCoefficient(attenuationCoefficient);
                mainParameters.setIputVoltage(iputVoltage);
                mainParameters.setOutputVoltage(outputVoltage);
                mainParameters.setOutputCurrentChannel(outputCurrentChannel);
                mainParameters.setDiameterPitchNut(diameterPitchNut);
                mainParameters.setInsertedOpticalLoss(insertedOpticalLoss);
                mainParameters.setNumberArticulationsDismemberments(numberArticulationsDismemberments);
                mainParameters.setNumberOpticalPoles(numberOpticalPoles);
                mainParameters.setVoltage(voltage);
                mainParameters.setElectricCurrent(electricCurrent);
                mainParameters.setLuminousFlux(luminousFlux);
                mainParameters.setMinimumOperatingTime(minimumOperatingTime);
                mainParameters.setInitialRelativeMagneticPermeability(initialRelativeMagneticPermeability);
                mainParameters.setRelativeGoodness(relativeGoodness);
                mainParameters.setQfactorMeasurementFrequency(qfactorMeasurementFrequency);
                mainParameters.setCoefficientAdjustmentArmorCores(coefficientAdjustmentArmorCores);
                mainParameters.setTyponominal(typonominal);
                persistence.getEntityManager().persist(mainParameters);
            }
            tx.commit();
        }
    }

    private void getTechnicalParametersFromFile(BidiMap<String, String> map, TechnicalParameters technicalParameters, Typonominal typonominal) {
        String frequencyRangeSin = map.get("20023");
        String accelerationAmplitudeSin = map.get("20024");
        String exposureTimeSin = map.get("20025");
        String frequencyRangebroad = map.get("20026");
        String accelerationSpectralDensitybroad = map.get("20027");
        String rootMeanSquareAccelerationbroad = map.get("20028");
        String exposureTimebroad = map.get("20029");
        String peakShockAccelerationSingle = map.get("20030");
        String durationOfImpactAccelerationSingle = map.get("20031");
        String shockSpectrumFrequencyRangeSingle = map.get("20032");
        String impactSpectrumValueSingle = map.get("20033");
        String numberOfImpactsSingle = map.get("20034");
        String peakShockAccelerationRepeat = map.get("20035");
        String durationOfImpactAccelerationRepeat = map.get("20036");
        String numberOfImpactsRepeat = map.get("20037");
        String frequencyRangeNoise = map.get("20038");
        String soundExposureLevelNoise = map.get("20039");
        String exposureTimeNoise = map.get("20040");
        String accelerationAmplitudeLine = map.get("20041");
        String exposureTimeLine = map.get("20042");
        String maximumValueDuringOperationInctemp = map.get("20043");
        String maximumValueDuringTransportationAndStorageInctemp = map.get("20044");
        String maximumValueDuringOperationRedtemp = map.get("20045");
        String maximumValueDuringTransportationAndStorageRedtemp = map.get("20046");
        String changeTemperatureRateChangeTemperatureRedtemp = map.get("20047");
        String relativeHumidityAtTemperatureHighair = map.get("20048");
        String absoluteAirHumidityHighair = map.get("20049");
        String relativeHumidityAtTemperatureRedair = map.get("20050");
        String soluteAirHumidityRedair = map.get("20051");
        String valueDuringOperationRedatm = map.get("20052");
        String valueDuringTransportationAndStorageRedatm = map.get("20053");
        String valueDuringOperationIncatm = map.get("20054");
        String valueDuringTransportationAndStorageIncatm = map.get("20055");
        String ratePressureChange = map.get("20056");
        String pressureChangeRange = map.get("20057");
        String esdRequirements = map.get("20066");

        try (Transaction tx = persistence.createTransaction()) {
            if (technicalParameters != null) {
                technicalParameters.setFrequencyRangeSinusoidalVibration(frequencyRangeSin);
                technicalParameters.setAccelerationAmplitude(accelerationAmplitudeSin);
                technicalParameters.setExposureTimeSinusoidalVibration(exposureTimeSin);
                technicalParameters.setFrequencyRangeBroadVibration(frequencyRangebroad);
                technicalParameters.setAccelerationSpectralDensity(accelerationSpectralDensitybroad);
                technicalParameters.setRootMeanSquareAcceleration(rootMeanSquareAccelerationbroad);
                technicalParameters.setExposureTimeBroadVibration(exposureTimebroad);
                technicalParameters.setPeakShockAccelerationOne(peakShockAccelerationSingle);
                technicalParameters.setDurationOfImpactAccelerationOne(durationOfImpactAccelerationSingle);
                technicalParameters.setShockSpectrumFrequencyRange(shockSpectrumFrequencyRangeSingle);
                technicalParameters.setImpactSpectrumValue(impactSpectrumValueSingle);
                technicalParameters.setNumberOfImpactsOne(numberOfImpactsSingle);
                technicalParameters.setPeakShockAccelerationOne(peakShockAccelerationRepeat);
                technicalParameters.setDurationOfImpactAccelerationMany(durationOfImpactAccelerationRepeat);
                technicalParameters.setNumberOfImpactsMany(numberOfImpactsRepeat);
                technicalParameters.setFrequencyRangeAcousticNoise(frequencyRangeNoise);
                technicalParameters.setSoundExposureLevel(soundExposureLevelNoise);
                technicalParameters.setExposureTimeAcousticNoise(exposureTimeNoise);
                technicalParameters.setAccelerationAmplitudeQuasiStaticAcceleration(accelerationAmplitudeLine);
                technicalParameters.setExposureTimeQuasiStaticAcceleration(exposureTimeLine);
                technicalParameters.setMaximumValueDuringOperationUp(maximumValueDuringOperationInctemp);
                technicalParameters.setMaximumValueDuringTransportationAndStorageUp(maximumValueDuringTransportationAndStorageInctemp);
                technicalParameters.setMaximumValueDuringOperationDown(maximumValueDuringOperationRedtemp);
                technicalParameters.setMaximumValueDuringTransportationAndStorageDown(maximumValueDuringTransportationAndStorageRedtemp);
                technicalParameters.setChangeTemperatureRateChangeTemperature(changeTemperatureRateChangeTemperatureRedtemp);
                technicalParameters.setRelativeHumidityAtTemperatureUp(relativeHumidityAtTemperatureHighair);
                technicalParameters.setAbsoluteAirHumidityUp(absoluteAirHumidityHighair);
                technicalParameters.setRelativeHumidityAtTemperatureDown(relativeHumidityAtTemperatureRedair);
                technicalParameters.setAbsoluteAirHumidityDown(soluteAirHumidityRedair);
                technicalParameters.setValueDuringOperationDown(valueDuringOperationRedatm);
                technicalParameters.setValueDuringTransportationAndStorageDown(valueDuringTransportationAndStorageRedatm);
                technicalParameters.setValueDuringOperationUp(valueDuringOperationIncatm);
                technicalParameters.setValueDuringTransportationAndStorageUp(valueDuringTransportationAndStorageIncatm);
                technicalParameters.setPressureChangeRange(pressureChangeRange);
                technicalParameters.setRatePressureChange(ratePressureChange);
                technicalParameters.setEsdRequirements(esdRequirements);
                persistence.getEntityManager().merge(technicalParameters);
            } else {
                technicalParameters = metadata.create(TechnicalParameters.class);
                try (Transaction txTransaction = persistence.createTransaction()) {
                    typonominal.setTechnicalParameters(technicalParameters);
                    persistence.getEntityManager().merge(typonominal);
                    txTransaction.commit();
                }
                technicalParameters.setFrequencyRangeSinusoidalVibration(frequencyRangeSin);
                technicalParameters.setAccelerationAmplitude(accelerationAmplitudeSin);
                technicalParameters.setExposureTimeSinusoidalVibration(exposureTimeSin);
                technicalParameters.setFrequencyRangeBroadVibration(frequencyRangebroad);
                technicalParameters.setAccelerationSpectralDensity(accelerationSpectralDensitybroad);
                technicalParameters.setRootMeanSquareAcceleration(rootMeanSquareAccelerationbroad);
                technicalParameters.setExposureTimeBroadVibration(exposureTimebroad);
                technicalParameters.setPeakShockAccelerationOne(peakShockAccelerationSingle);
                technicalParameters.setDurationOfImpactAccelerationOne(durationOfImpactAccelerationSingle);
                technicalParameters.setShockSpectrumFrequencyRange(shockSpectrumFrequencyRangeSingle);
                technicalParameters.setImpactSpectrumValue(impactSpectrumValueSingle);
                technicalParameters.setNumberOfImpactsOne(numberOfImpactsSingle);
                technicalParameters.setPeakShockAccelerationOne(peakShockAccelerationRepeat);
                technicalParameters.setDurationOfImpactAccelerationMany(durationOfImpactAccelerationRepeat);
                technicalParameters.setNumberOfImpactsMany(numberOfImpactsRepeat);
                technicalParameters.setFrequencyRangeAcousticNoise(frequencyRangeNoise);
                technicalParameters.setSoundExposureLevel(soundExposureLevelNoise);
                technicalParameters.setExposureTimeAcousticNoise(exposureTimeNoise);
                technicalParameters.setAccelerationAmplitudeQuasiStaticAcceleration(accelerationAmplitudeLine);
                technicalParameters.setExposureTimeQuasiStaticAcceleration(exposureTimeLine);
                technicalParameters.setMaximumValueDuringOperationUp(maximumValueDuringOperationInctemp);
                technicalParameters.setMaximumValueDuringTransportationAndStorageUp(maximumValueDuringTransportationAndStorageInctemp);
                technicalParameters.setMaximumValueDuringOperationDown(maximumValueDuringOperationRedtemp);
                technicalParameters.setMaximumValueDuringTransportationAndStorageDown(maximumValueDuringTransportationAndStorageRedtemp);
                technicalParameters.setChangeTemperatureRateChangeTemperature(changeTemperatureRateChangeTemperatureRedtemp);
                technicalParameters.setRelativeHumidityAtTemperatureUp(relativeHumidityAtTemperatureHighair);
                technicalParameters.setAbsoluteAirHumidityUp(absoluteAirHumidityHighair);
                technicalParameters.setRelativeHumidityAtTemperatureDown(relativeHumidityAtTemperatureRedair);
                technicalParameters.setAbsoluteAirHumidityDown(soluteAirHumidityRedair);
                technicalParameters.setValueDuringOperationDown(valueDuringOperationRedatm);
                technicalParameters.setValueDuringTransportationAndStorageDown(valueDuringTransportationAndStorageRedatm);
                technicalParameters.setValueDuringOperationUp(valueDuringOperationIncatm);
                technicalParameters.setValueDuringTransportationAndStorageUp(valueDuringTransportationAndStorageIncatm);
                technicalParameters.setPressureChangeRange(pressureChangeRange);
                technicalParameters.setRatePressureChange(ratePressureChange);
                technicalParameters.setEsdRequirements(esdRequirements);
                technicalParameters.setTyponominal(typonominal);
                persistence.getEntityManager().persist(technicalParameters);
            }
            tx.commit();
        }
    }

    private void getmontageParametersFromFile(BidiMap<String, String> map, MontageParameters montageParameters, Typonominal typonominal) {
        String maxTemperatureWP = map.get("20166");
        String maxSolderingTimeOutput = map.get("20168");
        String maxCaseTemperatureHand = map.get("20172");
        String minDistanceFromHousingToSoldering = map.get("20171");
        String flux = map.get("20174");
        String solder = map.get("20175");
        String maxPreHeatingTemperature = map.get("20178");
        String maxPreheatingTime = map.get("20179");
        String maxTemperatureSoldering = map.get("20180");
        String maxSolderingTime = map.get("20181");
        String maxCaseTemperatureAuto = map.get("20182");
        String thermalProfile = map.get("20183");
        String solderingPasteFlux = map.get("20184");
        String method = map.get("20185");

        try (Transaction tx = persistence.createTransaction()) {
            if (montageParameters != null) {
                montageParameters.setMaxTemperatureWP(maxTemperatureWP);
                montageParameters.setMaxSolderingTimeOutput(maxSolderingTimeOutput);
                montageParameters.setMaxCaseTemperatureHand(maxCaseTemperatureHand);
                montageParameters.setMinDistanceFromHousingToSoldering(minDistanceFromHousingToSoldering);
                montageParameters.setFlux(flux);
                montageParameters.setSolder(solder);
                montageParameters.setMaxPreHeatingTemperature(maxPreHeatingTemperature);
                montageParameters.setMaxPreheatingTime(maxPreheatingTime);
                montageParameters.setMaxTemperatureSoldering(maxTemperatureSoldering);
                montageParameters.setMaxSolderingTime(maxSolderingTime);
                montageParameters.setMaxCaseTemperatureAuto(maxCaseTemperatureAuto);
                montageParameters.setThermalProfile(thermalProfile);
                montageParameters.setSolderingPasteFlux(solderingPasteFlux);
                montageParameters.setMaxTemperatureWP(maxTemperatureWP);
                montageParameters.setMethod(method);
                persistence.getEntityManager().merge(montageParameters);
            } else {
                montageParameters = metadata.create(MontageParameters.class);
                try (Transaction txTransaction = persistence.createTransaction()) {
                    typonominal.setMontageParameters(montageParameters);
                    persistence.getEntityManager().merge(typonominal);
                    txTransaction.commit();
                }
                montageParameters.setMaxTemperatureWP(maxTemperatureWP);
                montageParameters.setMaxSolderingTimeOutput(maxSolderingTimeOutput);
                montageParameters.setMaxCaseTemperatureHand(maxCaseTemperatureHand);
                montageParameters.setMinDistanceFromHousingToSoldering(minDistanceFromHousingToSoldering);
                montageParameters.setFlux(flux);
                montageParameters.setSolder(solder);
                montageParameters.setMaxPreHeatingTemperature(maxPreHeatingTemperature);
                montageParameters.setMaxPreheatingTime(maxPreheatingTime);
                montageParameters.setMaxTemperatureSoldering(maxTemperatureSoldering);
                montageParameters.setMaxSolderingTime(maxSolderingTime);
                montageParameters.setMaxCaseTemperatureAuto(maxCaseTemperatureAuto);
                montageParameters.setThermalProfile(thermalProfile);
                montageParameters.setSolderingPasteFlux(solderingPasteFlux);
                montageParameters.setMaxTemperatureWP(maxTemperatureWP);
                montageParameters.setMethod(method);
                montageParameters.setTyponominal(typonominal);
                persistence.getEntityManager().persist(montageParameters);
            }
            tx.commit();
        }
    }

    private void getCorpusFromFile(BidiMap<String, String> map, Corpus corpus, Typonominal typonominal) {
        String corpusType = map.get("20149");
        String corpusMaterial = map.get("20151");
        String coverCover = map.get("105010027");
        String contactType = map.get("105010026");
        String contactCover = map.get("20152");
        String designationOutputFormingOption = map.get("20204");

        try (Transaction tx = persistence.createTransaction()) {
            if (corpus != null) {
                corpus.setCorpusType(corpusType);
                corpus.setCorpusMaterial(CorpusMaterial.fromId(corpusMaterial));
                corpus.setCoverCover(coverCover);
                corpus.setContactType(ContactType.fromId(contactType));
                corpus.setContactCover(ContactCoverage.fromId(contactCover));
                corpus.setDesignationOutputFormingOption(designationOutputFormingOption);
                corpus.setTyponominal(typonominal);
                persistence.getEntityManager().merge(corpus);
            } else {
                corpus = metadata.create(Corpus.class);
                try (Transaction txTransaction = persistence.createTransaction()) {
                    typonominal.setCorpus(corpus);
                    persistence.getEntityManager().merge(typonominal);
                    txTransaction.commit();
                }
                corpus.setCorpusType(corpusType);
                corpus.setCorpusMaterial(CorpusMaterial.fromId(corpusMaterial));
                corpus.setCoverCover(coverCover);
                corpus.setContactType(ContactType.fromId(contactType));
                corpus.setContactCover(ContactCoverage.fromId(contactCover));
                corpus.setDesignationOutputFormingOption(designationOutputFormingOption);
                corpus.setTyponominal(typonominal);
                persistence.getEntityManager().persist(corpus);
            }
            tx.commit();
        }
    }

    private void getReliabilityIndicatorsFromFile(
            BidiMap<String, String> map, ReliabilityIndicators reliabilityIndicators, Typonominal typonominal) {
        String reliabilityIndicator = map.get("20020");
        String retentionRate = map.get("20022");

        try (Transaction tx = persistence.createTransaction()) {
            if (reliabilityIndicators != null) {
                reliabilityIndicators.setReliabilityIndicator(reliabilityIndicator);
                reliabilityIndicators.setRetentionRate(retentionRate);
                reliabilityIndicators.setTyponominal(typonominal);
                persistence.getEntityManager().merge(reliabilityIndicators);
            } else {
                reliabilityIndicators = metadata.create(ReliabilityIndicators.class);
                try (Transaction txTransaction = persistence.createTransaction()) {
                    typonominal.setReliabilityIndicators(reliabilityIndicators);
                    persistence.getEntityManager().merge(typonominal);
                    txTransaction.commit();
                }
                reliabilityIndicators.setReliabilityIndicator(reliabilityIndicator);
                reliabilityIndicators.setRetentionRate(retentionRate);
                reliabilityIndicators.setTyponominal(typonominal);
                persistence.getEntityManager().persist(reliabilityIndicators);
            }
            tx.commit();
        }
    }

    private void getParametersForPurchasingFromFile(BidiMap<String, String> map, ParametersForPurchasing parameters, Typonominal typonominal) {
        String deliveryTime = map.get("20017");

        try (Transaction tx = persistence.createTransaction()) {
            if (parameters != null) {
                parameters.setDeliveryTime(deliveryTime);
                parameters.setTyponominal(typonominal);
                persistence.getEntityManager().merge(parameters);
            } else {
                parameters = metadata.create(ParametersForPurchasing.class);
                try (Transaction txTransaction = persistence.createTransaction()) {
                    typonominal.setParametersForPurchasing(parameters);
                    persistence.getEntityManager().merge(typonominal);
                    txTransaction.commit();
                }
                parameters.setDeliveryTime(deliveryTime);
                parameters.setTyponominal(typonominal);
                persistence.getEntityManager().persist(parameters);
            }
            tx.commit();
        }
    }

    private void getAdvancedSettingFromFile(BidiMap<String, String> map, AdvancedSetting advancedSetting, Typonominal typonominal) {
        String mass = map.get("20072");
        String workingLife = map.get("20019");
        String shelfLife = map.get("20021");
        String tightness = map.get("21065");

        try (Transaction tx = persistence.createTransaction()) {
            if (advancedSetting != null) {
                advancedSetting.setMass(mass);
                advancedSetting.setWorkingLife(workingLife);
                advancedSetting.setShelfLife(shelfLife);
                advancedSetting.setTightness(Tightness.fromId(tightness));
                advancedSetting.setTyponominal(typonominal);
                persistence.getEntityManager().merge(advancedSetting);
                typonominal.setAdvancedSetting(advancedSetting);
                persistence.getEntityManager().merge(typonominal);
            } else {
                advancedSetting = metadata.create(AdvancedSetting.class);
                try (Transaction txTransaction = persistence.createTransaction()) {
                    typonominal.setAdvancedSetting(advancedSetting);
                    persistence.getEntityManager().merge(typonominal);
                    txTransaction.commit();
                }
                advancedSetting.setMass(mass);
                advancedSetting.setWorkingLife(workingLife);
                advancedSetting.setShelfLife(shelfLife);
                advancedSetting.setTightness(Tightness.fromId(tightness));
                advancedSetting.setTyponominal(typonominal);
                persistence.getEntityManager().persist(advancedSetting);
            }
            tx.commit();
        }
    }

    private void getBasicInfoFromFile(BidiMap<String, String> map, BasicInformation basicInformation, Typonominal typonominal) {
        String levelQuality = map.get("20009");
        String inTheList0122 = map.get("20010");
        String inTheListEKBK = map.get("20011");

        try (Transaction tx = persistence.createTransaction()) {
            if (basicInformation != null) {
                basicInformation.setLevelQuality(NationalLevelQuality.fromId(levelQuality));
                basicInformation.setIn_the_list_0122(ListAvailability.fromId(inTheList0122));
                basicInformation.setIn_the_list_EKB_K(ListAvailability.fromId(inTheListEKBK));
                basicInformation.setTyponominal(typonominal);
                persistence.getEntityManager().merge(basicInformation);

            } else {
                basicInformation = metadata.create(BasicInformation.class);
                try (Transaction txTransaction = persistence.createTransaction()) {
                    typonominal.setBasicInformation(basicInformation);
                    persistence.getEntityManager().merge(typonominal);
                    txTransaction.commit();
                }
                basicInformation.setLevelQuality(NationalLevelQuality.fromId(levelQuality));
                basicInformation.setIn_the_list_0122(ListAvailability.fromId(inTheList0122));
                basicInformation.setIn_the_list_EKB_K(ListAvailability.fromId(inTheListEKBK));
                basicInformation.setTyponominal(typonominal);
                persistence.getEntityManager().persist(basicInformation);
            }
            tx.commit();
        }
    }

    private void getFactoryFromFile(BidiMap<String, String> map, Factory factory, Typonominal typonominal) {
        String producer = map.get("20006");
        String producingCountry = map.get("20008");
        String certificationCMKOrganization = map.get("20015");
        String calculatorHolder = map.get("20007");
        try (Transaction tx = persistence.createTransaction()) {
            if (factory != null) {
                factory.setProducer(producer);
                factory.setProducingCountry(producingCountry);
                factory.setCertificationCMKOrganization(OrganizationSMKCertification.fromId(certificationCMKOrganization));
                factory.setCalculatorHolder(calculatorHolder);
                factory.setTyponominal(typonominal);
                persistence.getEntityManager().merge(factory);
            } else {
                factory = metadata.create(Factory.class);
                try (Transaction txTransaction = persistence.createTransaction()) {
                    typonominal.setFactory(factory);
                    persistence.getEntityManager().merge(typonominal);
                    txTransaction.commit();
                }
                factory.setProducer(producer);
                factory.setProducingCountry(producingCountry);
                factory.setCertificationCMKOrganization(OrganizationSMKCertification.fromId(certificationCMKOrganization));
                factory.setCalculatorHolder(calculatorHolder);
                factory.setTyponominal(typonominal);
                persistence.getEntityManager().persist(factory);
            }
            tx.commit();
        }
    }

    private void logFileData(String fileName, String product, String nameOrType, String name2, String name3) {
        try (Transaction tx = persistence.createTransaction()) {
            LogFileData logFileData = metadata.create(LogFileData.class);
            logFileData.setFileName(fileName);
            logFileData.setProduct(product);
            logFileData.setName1OrType(nameOrType);
            logFileData.setName2(name2);
            logFileData.setName3(name3);
            persistence.getEntityManager().persist(logFileData);
            tx.commit();
        }
    }

    private String escaping(String s) {
        s.replaceAll("\"", "");
        s.replaceAll("'", "");
        return s;
    }
}
