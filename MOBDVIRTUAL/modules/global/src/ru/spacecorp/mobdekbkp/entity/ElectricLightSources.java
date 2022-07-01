package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_ELECTRIC_LIGHT_SOURCES")
@Entity(name = "mobdekbkp$ElectricLightSources")
public class ElectricLightSources extends StandardEntity {
    private static final long serialVersionUID = 505118830070016397L;

    @Column(name = "VOLTAGE", length = 100)
    protected String voltage;

    @Column(name = "ELECTRIC_CURRENT", length = 100)
    protected String electricCurrent;

    @Column(name = "LUMINOUS_FLUX", length = 100)
    protected String luminousFlux;

    @Column(name = "MINIMUM_OPERATING_TIME", length = 100)
    protected String minimumOperatingTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

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

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}