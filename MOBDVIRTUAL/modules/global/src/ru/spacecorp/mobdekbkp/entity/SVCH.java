package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Table(name = "MOBDEKBKP_SVCH")
@Entity(name = "mobdekbkp$SVCH")
public class SVCH extends StandardEntity {
    private static final long serialVersionUID = 3318226400670011606L;

    @Column(name = "OPERATING_FREQUENCYA_RANGE", length = 100)
    protected String operatingFrequencyaRange;

    @Column(name = "ADJUSTMENT_FACTOR", length = 100)
    protected String adjustmentFactor;

    @Column(name = "PHASE_NOISE_LEVEL", length = 100)
    protected String phaseNoiseLevel;

    @Column(name = "OUTPUT_POWER", length = 100)
    protected String outputPower;

    @Column(name = "SUPPLY_VOLTAGE", length = 100)
    protected String supplyVoltage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


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


}