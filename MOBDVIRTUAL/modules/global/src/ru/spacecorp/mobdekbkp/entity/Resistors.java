package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_RESISTORS")
@Entity(name = "mobdekbkp$Resistors")
public class Resistors extends StandardEntity {
    private static final long serialVersionUID = -5982068993935522796L;

    @Column(name = "RATED_POWER_DISSIPATION", length = 100)
    protected String ratedPowerDissipation;

    @Column(name = "NOMINAL_RESISTANCE", length = 100)
    protected String nominalResistance;

    @Column(name = "RESISTANCE_TOLERANCE", length = 100)
    protected String resistanceTolerance;

    @Column(name = "LIMITING_OPERATING_CURRENT", length = 100)
    protected String limitingOperatingCurrent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

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

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}