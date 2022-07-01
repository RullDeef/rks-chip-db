package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Table(name = "MOBDEKBKP_PLIS")
@Entity(name = "mobdekbkp$Plis")
public class Plis extends StandardEntity {
    private static final long serialVersionUID = 3261421932433629049L;

    @Column(name = "SUPPLY_VOLTAGE", length = 100)
    protected String supplyVoltage;

    @Column(name = "CONSUMPTION_CURRENT", length = 100)
    protected String consumptionCurrent;

    @Column(name = "TECHNOLOGY", length = 100)
    protected String technology;

    @Column(name = "NUMBER_VALVES", length = 100)
    protected String numberValves;

    @Column(name = "BUILT_MEMORY", length = 100)
    protected String builtMemory;

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

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getTechnology() {
        return technology;
    }

    public void setNumberValves(String numberValves) {
        this.numberValves = numberValves;
    }

    public String getNumberValves() {
        return numberValves;
    }

    public void setBuiltMemory(String builtMemory) {
        this.builtMemory = builtMemory;
    }

    public String getBuiltMemory() {
        return builtMemory;
    }


}