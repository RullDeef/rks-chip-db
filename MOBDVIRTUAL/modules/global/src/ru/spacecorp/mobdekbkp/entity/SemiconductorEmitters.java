package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_SEMICONDUCTOR_EMITTERS")
@Entity(name = "mobdekbkp$SemiconductorEmitters")
public class SemiconductorEmitters extends StandardEntity {
    private static final long serialVersionUID = 2355441415812222702L;

    @Column(name = "DIRECT_FORWARD_CURRENT", length = 100)
    protected String directForwardCurrent;

    @Column(name = "CONSTANT_FORWARD_VOLTAGE", length = 100)
    protected String constantForwardVoltage;

    @Column(name = "RADIATION_POWER", length = 100)
    protected String radiationPower;

    @Column(name = "WAVELENGTH", length = 100)
    protected String wavelength;

    @Column(name = "FALL_TIME", length = 100)
    protected String fallTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

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

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}