package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_DIGITAL_CHIPS")
@Entity(name = "mobdekbkp$DigitalChips")
public class DigitalChips extends StandardEntity {
    private static final long serialVersionUID = 607628440330086005L;

    @Column(name = "SUPPLY_VOLTAGE", length = 100)
    protected String supplyVoltage;

    @Column(name = "CONSUMPTION_CURRENT", length = 100)
    protected String consumptionCurrent;

    @Column(name = "MEMORY_CAPACITY", length = 100)
    protected String memoryCapacity;

    @Column(name = "TECHNOLOGY", length = 100)
    protected String technology;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;


    public void setSupplyVoltage(String supplyVoltage) {
        this.supplyVoltage = supplyVoltage;
    }

    public String getSupplyVoltage() {
        return supplyVoltage;
    }

    public void setConsumptionCurrent(String consumptionCurrent) {
        this.consumptionCurrent = consumptionCurrent;
    }

    public String getConsumptionCurrent() {
        return consumptionCurrent;
    }

    public void setMemoryCapacity(String memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    public String getMemoryCapacity() {
        return memoryCapacity;
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