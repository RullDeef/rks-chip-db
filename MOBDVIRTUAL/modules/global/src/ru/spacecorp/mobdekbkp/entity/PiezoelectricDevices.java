package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_PIEZOELECTRIC_DEVICES")
@Entity(name = "mobdekbkp$PiezoelectricDevices")
public class PiezoelectricDevices extends StandardEntity {
    private static final long serialVersionUID = 5563612059681793826L;

    @Column(name = "FREQUENCY_RANGE", length = 100)
    protected String frequencyRange;

    @Column(name = "TUNING_ACCURACY", length = 100)
    protected String tuningAccuracy;

    @Column(name = "IN_RANGE_OPERATING_TEMPERATURES", length = 100)
    protected String inRangeOperatingTemperatures;

    @Column(name = "RESONATOR_HOUSING_SYMBOL", length = 100)
    protected String resonatorHousingSymbol;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

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

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}