package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_FUNCTIONAL_DEVICES")
@Entity(name = "mobdekbkp$FunctionalDevices")
public class FunctionalDevices extends StandardEntity {
    private static final long serialVersionUID = 83066597148542139L;

    @Column(name = "IPUT_VOLTAGE", length = 100)
    protected String iputVoltage;

    @Column(name = "OUTPUT_VOLTAGE", length = 100)
    protected String outputVoltage;

    @Column(name = "OUTPUT_CURRENT_CHANNEL", length = 100)
    protected String outputCurrentChannel;

    @Column(name = "POWER_", length = 100)
    protected String power;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setIputVoltage(String iputVoltage) {
        this.iputVoltage = iputVoltage;
    }

    public String getIputVoltage() {
        return iputVoltage;
    }

    public void setOutputVoltage(String outputVoltage) {
        this.outputVoltage = outputVoltage;
    }

    public String getOutputVoltage() {
        return outputVoltage;
    }

    public void setOutputCurrentChannel(String outputCurrentChannel) {
        this.outputCurrentChannel = outputCurrentChannel;
    }

    public String getOutputCurrentChannel() {
        return outputCurrentChannel;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getPower() {
        return power;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}