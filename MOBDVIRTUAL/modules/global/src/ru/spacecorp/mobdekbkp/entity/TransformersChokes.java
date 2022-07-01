package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_TRANSFORMERS_CHOKES")
@Entity(name = "mobdekbkp$TransformersChokes")
public class TransformersChokes extends StandardEntity {
    private static final long serialVersionUID = -362902087671681622L;

    @Column(name = "MINIMUM_DUTY_CYCLE", length = 100)
    protected String minimumDutyCycle;

    @Column(name = "PULSE_DURATION", length = 100)
    protected String pulseDuration;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setMinimumDutyCycle(String minimumDutyCycle) {
        this.minimumDutyCycle = minimumDutyCycle;
    }

    public String getMinimumDutyCycle() {
        return minimumDutyCycle;
    }

    public void setPulseDuration(String pulseDuration) {
        this.pulseDuration = pulseDuration;
    }

    public String getPulseDuration() {
        return pulseDuration;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}