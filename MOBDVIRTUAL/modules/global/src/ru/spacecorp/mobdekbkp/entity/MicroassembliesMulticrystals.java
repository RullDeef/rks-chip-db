package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_MICROASSEMBLIES_MULTICRYSTALS")
@Entity(name = "mobdekbkp$MicroassembliesMulticrystals")
public class MicroassembliesMulticrystals extends StandardEntity {
    private static final long serialVersionUID = 2568450364444752897L;

    @Column(name = "SWITCHING_VOLTAGE", length = 100)
    protected String switchingVoltage;

    @Column(name = "SWITCHED_CURRENT", length = 100)
    protected String switchedCurrent;

    @Column(name = "TECHNOLOGY", length = 100)
    protected String technology;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setSwitchingVoltage(String switchingVoltage) {
        this.switchingVoltage = switchingVoltage;
    }

    public String getSwitchingVoltage() {
        return switchingVoltage;
    }

    public void setSwitchedCurrent(String switchedCurrent) {
        this.switchedCurrent = switchedCurrent;
    }

    public String getSwitchedCurrent() {
        return switchedCurrent;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}