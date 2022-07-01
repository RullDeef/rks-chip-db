package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_SWITCHING_PRODUCTS")
@Entity(name = "mobdekbkp$SwitchingProducts")
public class SwitchingProducts extends StandardEntity {
    private static final long serialVersionUID = 4113608838978556410L;

    @Column(name = "SWITCHED_CURRENT", length = 100)
    protected String switchedCurrent;

    @Column(name = "SWITCHING_VOLTAGE", length = 100)
    protected String switchingVoltage;

    @Column(name = "NUMBER_CONTACT_GROUPS", length = 100)
    protected String numberContactGroups;

    @Column(name = "SWITCHING_CURRENT_FREQUENCY", length = 100)
    protected String switchingCurrentFrequency;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setSwitchedCurrent(String switchedCurrent) {
        this.switchedCurrent = switchedCurrent;
    }

    public String getSwitchedCurrent() {
        return switchedCurrent;
    }

    public void setSwitchingVoltage(String switchingVoltage) {
        this.switchingVoltage = switchingVoltage;
    }

    public String getSwitchingVoltage() {
        return switchingVoltage;
    }

    public void setNumberContactGroups(String numberContactGroups) {
        this.numberContactGroups = numberContactGroups;
    }

    public String getNumberContactGroups() {
        return numberContactGroups;
    }

    public void setSwitchingCurrentFrequency(String switchingCurrentFrequency) {
        this.switchingCurrentFrequency = switchingCurrentFrequency;
    }

    public String getSwitchingCurrentFrequency() {
        return switchingCurrentFrequency;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}