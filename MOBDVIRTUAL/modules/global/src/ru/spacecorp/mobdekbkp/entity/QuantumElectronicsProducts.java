package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_QUANTUM_ELECTRONICS_PRODUCTS")
@Entity(name = "mobdekbkp$QuantumElectronicsProducts")
public class QuantumElectronicsProducts extends StandardEntity {
    private static final long serialVersionUID = -427234868224198650L;

    @Column(name = "AVERAGE_POWER_LASE_RADIATION", length = 100)
    protected String averagePowerLaseRadiation;

    @Column(name = "LASER_WAVE_LENGTH", length = 100)
    protected String laserWaveLength;

    @Column(name = "DIVERGENCE_LASER_RADIATION", length = 100)
    protected String divergenceLaserRadiation;

    @Column(name = "PULSE_REPETITION_FREQUENCY_LASER_RADIATION", length = 100)
    protected String pulseRepetitionFrequencyLaserRadiation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setAveragePowerLaseRadiation(String averagePowerLaseRadiation) {
        this.averagePowerLaseRadiation = averagePowerLaseRadiation;
    }

    public String getAveragePowerLaseRadiation() {
        return averagePowerLaseRadiation;
    }

    public void setLaserWaveLength(String laserWaveLength) {
        this.laserWaveLength = laserWaveLength;
    }

    public String getLaserWaveLength() {
        return laserWaveLength;
    }

    public void setDivergenceLaserRadiation(String divergenceLaserRadiation) {
        this.divergenceLaserRadiation = divergenceLaserRadiation;
    }

    public String getDivergenceLaserRadiation() {
        return divergenceLaserRadiation;
    }

    public void setPulseRepetitionFrequencyLaserRadiation(String pulseRepetitionFrequencyLaserRadiation) {
        this.pulseRepetitionFrequencyLaserRadiation = pulseRepetitionFrequencyLaserRadiation;
    }

    public String getPulseRepetitionFrequencyLaserRadiation() {
        return pulseRepetitionFrequencyLaserRadiation;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}