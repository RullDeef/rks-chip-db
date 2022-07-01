package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Table(name = "MOBDEKBKP_MONTAGE_PARAMETERS")
@Entity(name = "mobdekbkp$MontageParameters")
public class MontageParameters extends StandardEntity {
    private static final long serialVersionUID = 4341277894769496890L;

    @Column(name = "MAX_TEMPERATURE_WP")
    protected String maxTemperatureWP;

    @Column(name = "MAX_SOLDERING_TIME_OUTPUT")
    protected String maxSolderingTimeOutput;

    @Column(name = "MAX_CASE_TEMPERATURE_HAND")
    protected String maxCaseTemperatureHand;

    @Column(name = "MIN_DISTANCE_FROM_HOUSING_TO_SOLDERING")
    protected String minDistanceFromHousingToSoldering;

    @Column(name = "FLUX", length = 100)
    protected String flux;

    @Column(name = "SOLDER", length = 100)
    protected String solder;

    @Column(name = "MAX_PRE_HEATING_TEMPERATURE")
    protected String maxPreHeatingTemperature;

    @Column(name = "MAX_PREHEATING_TIME")
    protected String maxPreheatingTime;

    @Column(name = "MAX_TEMPERATURE_SOLDERING")
    protected String maxTemperatureSoldering;

    @Column(name = "MAX_SOLDERING_TIME")
    protected String maxSolderingTime;

    @Column(name = "MAX_CASE_TEMPERATURE_AUTO")
    protected String maxCaseTemperatureAuto;

    @Column(name = "THERMAL_PROFILE", length = 100)
    protected String thermalProfile;

    @Column(name = "SOLDERING_PASTE_FLUX", length = 100)
    protected String solderingPasteFlux;

    @Column(name = "METHOD_", length = 100)
    protected String method;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public String getMaxTemperatureWP() {
        return maxTemperatureWP;
    }

    public void setMaxTemperatureWP(String maxTemperatureWP) {
        this.maxTemperatureWP = maxTemperatureWP;
    }


    public String getMaxSolderingTimeOutput() {
        return maxSolderingTimeOutput;
    }

    public void setMaxSolderingTimeOutput(String maxSolderingTimeOutput) {
        this.maxSolderingTimeOutput = maxSolderingTimeOutput;
    }


    public String getMaxCaseTemperatureHand() {
        return maxCaseTemperatureHand;
    }

    public void setMaxCaseTemperatureHand(String maxCaseTemperatureHand) {
        this.maxCaseTemperatureHand = maxCaseTemperatureHand;
    }


    public String getMinDistanceFromHousingToSoldering() {
        return minDistanceFromHousingToSoldering;
    }

    public void setMinDistanceFromHousingToSoldering(String minDistanceFromHousingToSoldering) {
        this.minDistanceFromHousingToSoldering = minDistanceFromHousingToSoldering;
    }


    public String getMaxPreHeatingTemperature() {
        return maxPreHeatingTemperature;
    }

    public void setMaxPreHeatingTemperature(String maxPreHeatingTemperature) {
        this.maxPreHeatingTemperature = maxPreHeatingTemperature;
    }


    public String getMaxPreheatingTime() {
        return maxPreheatingTime;
    }

    public void setMaxPreheatingTime(String maxPreheatingTime) {
        this.maxPreheatingTime = maxPreheatingTime;
    }


    public String getMaxTemperatureSoldering() {
        return maxTemperatureSoldering;
    }

    public void setMaxTemperatureSoldering(String maxTemperatureSoldering) {
        this.maxTemperatureSoldering = maxTemperatureSoldering;
    }


    public String getMaxSolderingTime() {
        return maxSolderingTime;
    }

    public void setMaxSolderingTime(String maxSolderingTime) {
        this.maxSolderingTime = maxSolderingTime;
    }


    public String getMaxCaseTemperatureAuto() {
        return maxCaseTemperatureAuto;
    }

    public void setMaxCaseTemperatureAuto(String maxCaseTemperatureAuto) {
        this.maxCaseTemperatureAuto = maxCaseTemperatureAuto;
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


    public void setFlux(String flux) {
        this.flux = flux;
    }

    public String getFlux() {
        return flux;
    }

    public void setSolder(String solder) {
        this.solder = solder;
    }

    public String getSolder() {
        return solder;
    }

    public void setThermalProfile(String thermalProfile) {
        this.thermalProfile = thermalProfile;
    }

    public String getThermalProfile() {
        return thermalProfile;
    }

    public void setSolderingPasteFlux(String solderingPasteFlux) {
        this.solderingPasteFlux = solderingPasteFlux;
    }

    public String getSolderingPasteFlux() {
        return solderingPasteFlux;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }


}