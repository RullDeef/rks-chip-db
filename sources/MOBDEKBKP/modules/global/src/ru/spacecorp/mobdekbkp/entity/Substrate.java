package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

@Table(name = "MOBDEKBKP_SUBSTRATE")
@Entity(name = "mobdekbkp$Substrate")
public class Substrate extends StandardEntity {
    private static final long serialVersionUID = 7460010786517329271L;

    @Column(name = "MODEL", nullable = false)
    protected String model;

    @Column(name = "NAME")
    protected String name;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MATERIAL_ID")
    protected Material material;

    @Column(name = "THICKNESS", nullable = false)
    protected Double thickness;

    @Column(name = "COLOR")
    protected String color;

    @Column(name = "ROUGHNESS")
    protected Double roughness;

    @Column(name = "DENSITY")
    protected Double density;

    @Column(name = "MOISTURE_ABSORB")
    protected Double moistureAbsorb;

    @Column(name = "FLEX_STRENGTH")
    protected Double flexStrength;

    @Column(name = "ELASTIC_MODULE")
    protected Double elasticModule;

    @Column(name = "HEAT_CONDUCT_COEFF")
    protected Double heatConductCoeff;

    @Column(name = "HEAT_CAPACITY")
    protected Double heatCapacity;

    @Column(name = "LINE_EXT_COEFF300")
    protected Double lineExtCoeff300;

    @Column(name = "LINE_EXT_COEFF600")
    protected Double lineExtCoeff600;

    @Column(name = "LINE_EXT_COEFF1000")
    protected Double lineExtCoeff1000;

    @Column(name = "DIELECTRIC_CONSTANT_M")
    protected String dielectricConstantM;

    @Column(name = "DIELECTRIC_CONSTANT_G")
    protected String dielectricConstantG;

    @Column(name = "DIELECTRIC_LOSS_COEFF")
    protected Double dielectricLossCoeff;

    @Column(name = "BREAKDOWN_VOLTAGE")
    protected Double breakdownVoltage;

    @Column(name = "RESISTIVITY_LEVEL")
    protected Double resistivityLevel;

    public void setBreakdownVoltage(Double breakdownVoltage) {
        this.breakdownVoltage = breakdownVoltage;
    }

    public Double getBreakdownVoltage() {
        return breakdownVoltage;
    }

    public void setResistivityLevel(Double resistivityLevel) {
        this.resistivityLevel = resistivityLevel;
    }

    public Double getResistivityLevel() {
        return resistivityLevel;
    }


    public void setLineExtCoeff300(Double lineExtCoeff300) {
        this.lineExtCoeff300 = lineExtCoeff300;
    }

    public Double getLineExtCoeff300() {
        return lineExtCoeff300;
    }

    public void setLineExtCoeff600(Double lineExtCoeff600) {
        this.lineExtCoeff600 = lineExtCoeff600;
    }

    public Double getLineExtCoeff600() {
        return lineExtCoeff600;
    }

    public void setLineExtCoeff1000(Double lineExtCoeff1000) {
        this.lineExtCoeff1000 = lineExtCoeff1000;
    }

    public Double getLineExtCoeff1000() {
        return lineExtCoeff1000;
    }

    public void setDielectricConstantM(String dielectricConstantM) {
        this.dielectricConstantM = dielectricConstantM;
    }

    public String getDielectricConstantM() {
        return dielectricConstantM;
    }

    public void setDielectricConstantG(String dielectricConstantG) {
        this.dielectricConstantG = dielectricConstantG;
    }

    public String getDielectricConstantG() {
        return dielectricConstantG;
    }

    public void setDielectricLossCoeff(Double dielectricLossCoeff) {
        this.dielectricLossCoeff = dielectricLossCoeff;
    }

    public Double getDielectricLossCoeff() {
        return dielectricLossCoeff;
    }


    public void setHeatConductCoeff(Double heatConductCoeff) {
        this.heatConductCoeff = heatConductCoeff;
    }

    public Double getHeatConductCoeff() {
        return heatConductCoeff;
    }

    public void setHeatCapacity(Double heatCapacity) {
        this.heatCapacity = heatCapacity;
    }

    public Double getHeatCapacity() {
        return heatCapacity;
    }


    public void setFlexStrength(Double flexStrength) {
        this.flexStrength = flexStrength;
    }

    public Double getFlexStrength() {
        return flexStrength;
    }

    public void setElasticModule(Double elasticModule) {
        this.elasticModule = elasticModule;
    }

    public Double getElasticModule() {
        return elasticModule;
    }


    public void setMoistureAbsorb(Double moistureAbsorb) {
        this.moistureAbsorb = moistureAbsorb;
    }

    public Double getMoistureAbsorb() {
        return moistureAbsorb;
    }


    public void setDensity(Double density) {
        this.density = density;
    }

    public Double getDensity() {
        return density;
    }


    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public void setThickness(Double thickness) {
        this.thickness = thickness;
    }

    public Double getThickness() {
        return thickness;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setRoughness(Double roughness) {
        this.roughness = roughness;
    }

    public Double getRoughness() {
        return roughness;
    }


}