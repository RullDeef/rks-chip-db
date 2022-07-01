package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_ELECTRICAL_CONNECTORS")
@Entity(name = "mobdekbkp$ElectricalConnectors")
public class ElectricalConnectors extends StandardEntity {
    private static final long serialVersionUID = 7152068355659569832L;

    @Column(name = "FREQUENCY_RANGE", length = 100)
    protected String frequencyRange;

    @Column(name = "ATTACHED_CABLE_BRAND", length = 100)
    protected String attachedCableBrand;

    @Column(name = "STRUCTURAL_PERFORMANCE", length = 100)
    protected String structuralPerformance;

    @Column(name = "PATH_TYPE", length = 100)
    protected String pathType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setFrequencyRange(String frequencyRange) {
        this.frequencyRange = frequencyRange;
    }

    public String getFrequencyRange() {
        return frequencyRange;
    }

    public void setAttachedCableBrand(String attachedCableBrand) {
        this.attachedCableBrand = attachedCableBrand;
    }

    public String getAttachedCableBrand() {
        return attachedCableBrand;
    }

    public void setStructuralPerformance(String structuralPerformance) {
        this.structuralPerformance = structuralPerformance;
    }

    public String getStructuralPerformance() {
        return structuralPerformance;
    }

    public void setPathType(String pathType) {
        this.pathType = pathType;
    }

    public String getPathType() {
        return pathType;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}