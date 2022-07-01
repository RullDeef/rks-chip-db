package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Table(name = "MOBDEKBKP_ADVANCED_SETTING")
@Entity(name = "mobdekbkp$AdvancedSetting")
public class AdvancedSetting extends StandardEntity {
    private static final long serialVersionUID = 3064785154733837971L;

    @Column(name = "MASS", length = 50)
    protected String mass;

    @Column(name = "NUMBER_PRP", length = 100)
    protected String numberPRP;

    @Column(name = "TECHNICAL_SPECIFICATION_NAME", length = 100)
    protected String technicalSpecificationName;

    @Column(name = "TECHNICAL_SPECIFICATION_VALUE", length = 100)
    protected String technicalSpecificationValue;

    @Column(name = "WORKING_LIFE")
    protected String workingLife;

    @Column(name = "SHELF_LIFE")
    protected String shelfLife;

    @Column(name = "TIGHTNESS")
    protected String tightness;

    @Column(name = "TECHNOLOGY", length = 50)
    protected String technology;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_DEVICE_ID")
    protected ImportDevice importDevice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public String getWorkingLife() {
        return workingLife;
    }

    public void setWorkingLife(String workingLife) {
        this.workingLife = workingLife;
    }


    public String getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }


    public Tightness getTightness() {
        return tightness == null ? null : Tightness.fromId(tightness);
    }

    public void setTightness(Tightness tightness) {
        this.tightness = tightness == null ? null : tightness.getId();
    }


    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getTechnology() {
        return technology;
    }

    public void setImportDevice(ImportDevice importDevice) {
        this.importDevice = importDevice;
    }

    public ImportDevice getImportDevice() {
        return importDevice;
    }


    public void setTechnicalSpecificationName(String technicalSpecificationName) {
        this.technicalSpecificationName = technicalSpecificationName;
    }

    public String getTechnicalSpecificationName() {
        return technicalSpecificationName;
    }

    public void setTechnicalSpecificationValue(String technicalSpecificationValue) {
        this.technicalSpecificationValue = technicalSpecificationValue;
    }

    public String getTechnicalSpecificationValue() {
        return technicalSpecificationValue;
    }


    public void setNumberPRP(String numberPRP) {
        this.numberPRP = numberPRP;
    }

    public String getNumberPRP() {
        return numberPRP;
    }


}