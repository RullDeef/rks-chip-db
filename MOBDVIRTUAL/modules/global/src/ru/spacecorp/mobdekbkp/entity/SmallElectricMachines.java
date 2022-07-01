package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_SMALL_ELECTRIC_MACHINES")
@Entity(name = "mobdekbkp$SmallElectricMachines")
public class SmallElectricMachines extends StandardEntity {
    private static final long serialVersionUID = 8036894762601723833L;

    @Column(name = "POWER_", length = 100)
    protected String power;

    @Column(name = "ROTATION_FREQUENCY", length = 100)
    protected String rotationFrequency;

    @Column(name = "SUPPLY_VOLTAGE", length = 100)
    protected String supplyVoltage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setPower(String power) {
        this.power = power;
    }

    public String getPower() {
        return power;
    }

    public void setRotationFrequency(String rotationFrequency) {
        this.rotationFrequency = rotationFrequency;
    }

    public String getRotationFrequency() {
        return rotationFrequency;
    }

    public void setSupplyVoltage(String supplyVoltage) {
        this.supplyVoltage = supplyVoltage;
    }

    public String getSupplyVoltage() {
        return supplyVoltage;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}