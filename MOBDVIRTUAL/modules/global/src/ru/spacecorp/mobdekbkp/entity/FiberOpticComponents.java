package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_FIBER_OPTIC_COMPONENTS")
@Entity(name = "mobdekbkp$FiberOpticComponents")
public class FiberOpticComponents extends StandardEntity {
    private static final long serialVersionUID = -3640006012464198120L;

    @Column(name = "DIAMETER_PITCH_NUT", length = 100)
    protected String diameterPitchNut;

    @Column(name = "INSERTED_OPTICAL_LOSS", length = 100)
    protected String insertedOpticalLoss;

    @Column(name = "NUMBER_ARTICULATIONS_DISMEMBERMENTS", length = 100)
    protected String numberArticulationsDismemberments;

    @Column(name = "NUMBER_OPTICAL_POLES", length = 100)
    protected String numberOpticalPoles;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setDiameterPitchNut(String diameterPitchNut) {
        this.diameterPitchNut = diameterPitchNut;
    }

    public String getDiameterPitchNut() {
        return diameterPitchNut;
    }

    public void setInsertedOpticalLoss(String insertedOpticalLoss) {
        this.insertedOpticalLoss = insertedOpticalLoss;
    }

    public String getInsertedOpticalLoss() {
        return insertedOpticalLoss;
    }

    public void setNumberArticulationsDismemberments(String numberArticulationsDismemberments) {
        this.numberArticulationsDismemberments = numberArticulationsDismemberments;
    }

    public String getNumberArticulationsDismemberments() {
        return numberArticulationsDismemberments;
    }

    public void setNumberOpticalPoles(String numberOpticalPoles) {
        this.numberOpticalPoles = numberOpticalPoles;
    }

    public String getNumberOpticalPoles() {
        return numberOpticalPoles;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}