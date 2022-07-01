package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.OneToOne;


import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import java.util.List;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@NamePattern("%s %s %s|designation,name,description")
@Table(name = "MOBDEKBKP_TYPONOMINAL_BODY")
@Entity(name = "mobdekbkp$TyponominalBody")
public class TyponominalBody extends StandardEntity {
    private static final long serialVersionUID = 7520662168046460848L;

    @Column(name = "DESIGNATION", nullable = false)
    protected String designation;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "LENGTH")
    protected Double length;

    @Column(name = "WIDTH")
    protected Double width;

    @Column(name = "HEIGHT")
    protected Double height;

    @Column(name = "PINS_COUNT")
    protected Integer pinsCount;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "clear"})
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BODY_MATERIAL_ID")
    protected Material bodyMaterial;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "clear"})
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PINS_MATERIAL_ID")
    protected Material pinsMaterial;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "clear"})
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COATING_MATERIAL_ID")
    protected Material coatingMaterial;

    @Column(name = "DISTANCE_LEADS")
    protected Double distanceLeads;

    @Column(name = "MASS")
    protected Double mass;

    @Column(name = "MAX_HEIGHT")
    protected Double maxHeight;


    @JoinTable(name = "MOBDEKBKP_TYPONOMINAL_BODY_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "TYPONOMINAL_BODY_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documentsModel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PIN_FORMING_DESIGNATION_ID")
    protected Document pinFormingDesignation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PHOTO_ID")
    protected Document photo;

    @NotNull
    @Column(name = "SEALING_DEMAND", nullable = false)
    protected String sealingDemand;

    @Column(name = "DISPERSE_POWER")
    protected Double dispersePower;

    @Column(name = "OUTPUT_POWER")
    protected Double outputPower;

    @Column(name = "THERMAL_RESISTANCE")
    protected Double thermalResistance;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIMENSIONS_AND_BODY_ID")
    protected Document dimensionsAndBody;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIXATOR_MARKUP_ID")
    protected Document fixatorMarkup;

    public List<Document> getDocumentsModel() {
        return documentsModel;
    }

    public void setDocumentsModel(List<Document> documentsModel) {
        this.documentsModel = documentsModel;
    }


    public Document getPinFormingDesignation() {
        return pinFormingDesignation;
    }

    public void setPinFormingDesignation(Document pinFormingDesignation) {
        this.pinFormingDesignation = pinFormingDesignation;
    }


    public Document getPhoto() {
        return photo;
    }

    public void setPhoto(Document photo) {
        this.photo = photo;
    }


    public Document getDimensionsAndBody() {
        return dimensionsAndBody;
    }

    public void setDimensionsAndBody(Document dimensionsAndBody) {
        this.dimensionsAndBody = dimensionsAndBody;
    }

    public Document getFixatorMarkup() {
        return fixatorMarkup;
    }

    public void setFixatorMarkup(Document fixatorMarkup) {
        this.fixatorMarkup = fixatorMarkup;
    }


    public void setLength(Double length) {
        this.length = length;
    }

    public Double getLength() {
        return length;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getWidth() {
        return width;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getHeight() {
        return height;
    }


    public void setPinsMaterial(Material pinsMaterial) {
        this.pinsMaterial = pinsMaterial;
    }

    public Material getPinsMaterial() {
        return pinsMaterial;
    }


    public void setBodyMaterial(Material bodyMaterial) {
        this.bodyMaterial = bodyMaterial;
    }

    public Material getBodyMaterial() {
        return bodyMaterial;
    }


    public void setPinsCount(Integer pinsCount) {
        this.pinsCount = pinsCount;
    }

    public Integer getPinsCount() {
        return pinsCount;
    }


    public ExtBoolean getSealingDemand() {
        return sealingDemand == null ? null : ExtBoolean.fromId(sealingDemand);
    }

    public void setSealingDemand(ExtBoolean sealingDemand) {
        this.sealingDemand = sealingDemand == null ? null : sealingDemand.getId();
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getMass() {
        return mass;
    }

    public void setMaxHeight(Double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public Double getMaxHeight() {
        return maxHeight;
    }


    public void setCoatingMaterial(Material coatingMaterial) {
        this.coatingMaterial = coatingMaterial;
    }

    public Material getCoatingMaterial() {
        return coatingMaterial;
    }

    public void setDistanceLeads(Double distanceLeads) {
        this.distanceLeads = distanceLeads;
    }

    public Double getDistanceLeads() {
        return distanceLeads;
    }

    public void setDispersePower(Double dispersePower) {
        this.dispersePower = dispersePower;
    }

    public Double getDispersePower() {
        return dispersePower;
    }

    public void setOutputPower(Double outputPower) {
        this.outputPower = outputPower;
    }

    public Double getOutputPower() {
        return outputPower;
    }

    public void setThermalResistance(Double thermalResistance) {
        this.thermalResistance = thermalResistance;
    }

    public Double getThermalResistance() {
        return thermalResistance;
    }


    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}