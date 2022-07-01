package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_CURRENT_SOURCES")
@Entity(name = "mobdekbkp$CurrentSources")
public class CurrentSources extends StandardEntity {
    private static final long serialVersionUID = 1319601081852965194L;

    @Column(name = "FREQUENCY_RANGE", length = 100)
    protected String frequencyRange;

    @Column(name = "NOMINAL_VOLTAGE", length = 100)
    protected String nominalVoltage;

    @Column(name = "RATED_CAPACITY", length = 100)
    protected String ratedCapacity;

    @Column(name = "OVERALL_DIMENSIONS", length = 100)
    protected String overallDimensions;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setFrequencyRange(String frequencyRange) {
        this.frequencyRange = frequencyRange;
    }

    public String getFrequencyRange() {
        return frequencyRange;
    }

    public void setNominalVoltage(String nominalVoltage) {
        this.nominalVoltage = nominalVoltage;
    }

    public String getNominalVoltage() {
        return nominalVoltage;
    }

    public void setRatedCapacity(String ratedCapacity) {
        this.ratedCapacity = ratedCapacity;
    }

    public String getRatedCapacity() {
        return ratedCapacity;
    }

    public void setOverallDimensions(String overallDimensions) {
        this.overallDimensions = overallDimensions;
    }

    public String getOverallDimensions() {
        return overallDimensions;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}