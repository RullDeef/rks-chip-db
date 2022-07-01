package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Table(name = "MOBDEKBKP_ELECTRIC_VACUUM_LAMPS")
@Entity(name = "mobdekbkp$ElectricVacuumLamps")
public class ElectricVacuumLamps extends StandardEntity {
    private static final long serialVersionUID = -3017213353717244885L;

    @Column(name = "OUTPUT_POWER", length = 100)
    protected String outputPower;

    @Column(name = "WORKING_FREQUENCY", length = 100)
    protected String workingFrequency;

    @Column(name = "POWER_DISSIPATED_ANODE", length = 100)
    protected String powerDissipatedAnode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


    public void setOutputPower(String outputPower) {
        this.outputPower = outputPower;
    }

    public String getOutputPower() {
        return outputPower;
    }

    public void setWorkingFrequency(String workingFrequency) {
        this.workingFrequency = workingFrequency;
    }

    public String getWorkingFrequency() {
        return workingFrequency;
    }

    public void setPowerDissipatedAnode(String powerDissipatedAnode) {
        this.powerDissipatedAnode = powerDissipatedAnode;
    }

    public String getPowerDissipatedAnode() {
        return powerDissipatedAnode;
    }


}