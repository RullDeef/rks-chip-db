package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Table(name = "MOBDEKBKP_RELIABILITY_INDICATORS")
@Entity(name = "mobdekbkp$ReliabilityIndicators")
public class ReliabilityIndicators extends StandardEntity {
    private static final long serialVersionUID = -2488506818022075920L;

    @Column(name = "RELIABILITY_INDICATOR", length = 100)
    protected String reliabilityIndicator;

    @Column(name = "RETENTION_RATE", length = 100)
    protected String retentionRate;

    @Column(name = "GAMMA_PERCENT_OPERATING_MAINTENANCE", length = 100)
    protected String gammaPercentOperatingMaintenance;

    @Column(name = "GAMMA_PERCENT_OPERATING_LIGHT", length = 100)
    protected String gammaPercentOperatingLight;

    @Column(name = "GAMMAPERCENT_STORAGEABILITY_TIME", length = 100)
    protected String gammapercentStorageabilityTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


    public void setReliabilityIndicator(String reliabilityIndicator) {
        this.reliabilityIndicator = reliabilityIndicator;
    }

    public String getReliabilityIndicator() {
        return reliabilityIndicator;
    }

    public void setRetentionRate(String retentionRate) {
        this.retentionRate = retentionRate;
    }

    public String getRetentionRate() {
        return retentionRate;
    }

    public void setGammaPercentOperatingMaintenance(String gammaPercentOperatingMaintenance) {
        this.gammaPercentOperatingMaintenance = gammaPercentOperatingMaintenance;
    }

    public String getGammaPercentOperatingMaintenance() {
        return gammaPercentOperatingMaintenance;
    }

    public void setGammaPercentOperatingLight(String gammaPercentOperatingLight) {
        this.gammaPercentOperatingLight = gammaPercentOperatingLight;
    }

    public String getGammaPercentOperatingLight() {
        return gammaPercentOperatingLight;
    }

    public void setGammapercentStorageabilityTime(String gammapercentStorageabilityTime) {
        this.gammapercentStorageabilityTime = gammapercentStorageabilityTime;
    }

    public String getGammapercentStorageabilityTime() {
        return gammapercentStorageabilityTime;
    }


}