package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_CAPACITORS")
@Entity(name = "mobdekbkp$Capacitors")
public class Capacitors extends StandardEntity {
    private static final long serialVersionUID = -4521784646416057119L;

    @Column(name = "RATED_VOLTAGE", length = 100)
    protected String ratedVoltage;

    @Column(name = "RATED_CAPACITY", length = 100)
    protected String ratedCapacity;

    @Column(name = "DIMENSIONS", length = 100)
    protected String dimensions;

    @Column(name = "CAPACITY_TOLERANCE", length = 100)
    protected String capacityTolerance;

    @Column(name = "IMPACT_SHEAR_FORCE", length = 100)
    protected String impactShearForce;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setRatedVoltage(String ratedVoltage) {
        this.ratedVoltage = ratedVoltage;
    }

    public String getRatedVoltage() {
        return ratedVoltage;
    }

    public void setRatedCapacity(String ratedCapacity) {
        this.ratedCapacity = ratedCapacity;
    }

    public String getRatedCapacity() {
        return ratedCapacity;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setCapacityTolerance(String capacityTolerance) {
        this.capacityTolerance = capacityTolerance;
    }

    public String getCapacityTolerance() {
        return capacityTolerance;
    }

    public void setImpactShearForce(String impactShearForce) {
        this.impactShearForce = impactShearForce;
    }

    public String getImpactShearForce() {
        return impactShearForce;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}