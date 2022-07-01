package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_SIGN_SYNTHESIZING_INDICATORS")
@Entity(name = "mobdekbkp$SignSynthesizingIndicators")
public class SignSynthesizingIndicators extends StandardEntity {
    private static final long serialVersionUID = 7087123163177673040L;

    @Column(name = "GLOW_BRIGHTNESS", length = 100)
    protected String glowBrightness;

    @Column(name = "GLOW_COLOR", length = 100)
    protected String glowColor;

    @Column(name = "MAXIMUM_PERMISSIBLE_EXTERNAL_ILLUMINATION", length = 100)
    protected String maximumPermissibleExternalIllumination;

    @Column(name = "SUPPLY_VOLTAGE", length = 100)
    protected String supplyVoltage;

    @Column(name = "CONSUMPTION_CURRENT", length = 100)
    protected String consumptionCurrent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

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

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}