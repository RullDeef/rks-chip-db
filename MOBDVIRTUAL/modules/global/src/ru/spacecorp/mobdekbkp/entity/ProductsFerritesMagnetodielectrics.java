package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_PRODUCTS_FERRITES_MAGNETODIELECTRICS")
@Entity(name = "mobdekbkp$ProductsFerritesMagnetodielectrics")
public class ProductsFerritesMagnetodielectrics extends StandardEntity {
    private static final long serialVersionUID = -7749384173480479393L;

    @Column(name = "INITIAL_RELATIVE_MAGNETIC_PERMEABILITY", length = 100)
    protected String initialRelativeMagneticPermeability;

    @Column(name = "RELATIVE_GOODNESS", length = 100)
    protected String relativeGoodness;

    @Column(name = "QFACTOR_MEASUREMENT_FREQUENCY", length = 100)
    protected String qfactorMeasurementFrequency;

    @Column(name = "COEFFICIENT_ADJUSTMENT_ARMOR_CORES", length = 100)
    protected String coefficientAdjustmentArmorCores;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public void setInitialRelativeMagneticPermeability(String initialRelativeMagneticPermeability) {
        this.initialRelativeMagneticPermeability = initialRelativeMagneticPermeability;
    }

    public String getInitialRelativeMagneticPermeability() {
        return initialRelativeMagneticPermeability;
    }

    public void setRelativeGoodness(String relativeGoodness) {
        this.relativeGoodness = relativeGoodness;
    }

    public String getRelativeGoodness() {
        return relativeGoodness;
    }

    public void setQfactorMeasurementFrequency(String qfactorMeasurementFrequency) {
        this.qfactorMeasurementFrequency = qfactorMeasurementFrequency;
    }

    public String getQfactorMeasurementFrequency() {
        return qfactorMeasurementFrequency;
    }

    public void setCoefficientAdjustmentArmorCores(String coefficientAdjustmentArmorCores) {
        this.coefficientAdjustmentArmorCores = coefficientAdjustmentArmorCores;
    }

    public String getCoefficientAdjustmentArmorCores() {
        return coefficientAdjustmentArmorCores;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}