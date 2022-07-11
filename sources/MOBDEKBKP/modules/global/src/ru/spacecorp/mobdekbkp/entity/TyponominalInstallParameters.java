package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import javax.persistence.OneToOne;
import java.util.List;
import javax.persistence.OneToMany;

import com.haulmont.cuba.core.entity.annotation.Listeners;

import javax.persistence.Lob;

import ru.spacecorp.documents.entity.Document;

@Listeners("mobdekbkp_TyponominalInstallParametersEntityListener")
@NamePattern("%s|description")
@Table(name = "MOBDEKBKP_TYPONOMINAL_INSTALL_PARAMETERS")
@Entity(name = "mobdekbkp$TyponominalInstallParameters")
public class TyponominalInstallParameters extends StandardEntity {
    private static final long serialVersionUID = -1400730033109940492L;

    @Lob
    @Column(name = "DESCRIPTION", nullable = false)
    protected String description;

    @NotNull
    @Column(name = "NON_PB_SOLDER_TECH", nullable = false)
    protected String nonPbSolderTech;

    @Column(name = "TEMPERATURE_MODE")
    protected String temperatureMode;

    @NotNull
    @Column(name = "GAS_ENVIRONMENT_AVAILABLE", nullable = false)
    protected String gasEnvironmentAvailable;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BODY_INSTALLATION_DOCUMENT_ID")
    protected Document bodyInstallationDocument;

    @Column(name = "INSTALLATION_OPTION_DESIGNATION")
    protected String installationOptionDesignation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PIN_FORMING_DOCUMENT_ID")
    protected Document pinFormingDocument;

    @Column(name = "PIN_FORMING_DESIGNATION")
    protected String pinFormingDesignation;

    @NotNull
    @Column(name = "HAS_GASKET", nullable = false)
    protected String hasGasket;

    @Column(name = "GASKET_SIZE")
    protected String gasketSize;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GASKET_MATERIAL_ID")
    protected Material gasketMaterial;

    @NotNull
    @Column(name = "HAS_GLUE", nullable = false)
    protected String hasGlue;

    @Column(name = "INSTALLATION_COUNT_ALLOWED")
    protected Integer installationCountAllowed;

    @Column(name = "AUTO_INSTALLATION", nullable = false)
    protected String autoInstallation;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GLUE_TYPE_ID")
    protected GlueType glueType;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "INSTALL_METHOD_ID")
    protected InstallMethod installMethod;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLDER_BRAND_ID")
    protected SolderBrand solderBrand;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLUX_BRAND_ID")
    protected FluxBrand fluxBrand;

    @OneToMany(mappedBy = "typonominalInstallParameters")
    protected List<SubstrateEntry> substrates;

    public Document getBodyInstallationDocument() {
        return bodyInstallationDocument;
    }

    public void setBodyInstallationDocument(Document bodyInstallationDocument) {
        this.bodyInstallationDocument = bodyInstallationDocument;
    }

    public Document getPinFormingDocument() {
        return pinFormingDocument;
    }

    public void setPinFormingDocument(Document pinFormingDocument) {
        this.pinFormingDocument = pinFormingDocument;
    }


    public void setPinFormingDesignation(String pinFormingDesignation) {
        this.pinFormingDesignation = pinFormingDesignation;
    }

    public String getPinFormingDesignation() {
        return pinFormingDesignation;
    }


    public void setSubstrates(List<SubstrateEntry> substrates) {
        this.substrates = substrates;
    }

    public List<SubstrateEntry> getSubstrates() {
        return substrates;
    }


    public void setGasketMaterial(Material gasketMaterial) {
        this.gasketMaterial = gasketMaterial;
    }

    public Material getGasketMaterial() {
        return gasketMaterial;
    }


    public InstallMethod getInstallMethod() {
        return installMethod;
    }

    public void setInstallMethod(InstallMethod installMethod) {
        this.installMethod = installMethod;
    }


    public GlueType getGlueType() {
        return glueType;
    }

    public void setGlueType(GlueType glueType) {
        this.glueType = glueType;
    }


    public void setHasGasket(ExtBoolean hasGasket) {
        this.hasGasket = hasGasket == null ? null : hasGasket.getId();
    }

    public ExtBoolean getHasGasket() {
        return hasGasket == null ? null : ExtBoolean.fromId(hasGasket);
    }

    public void setGasketSize(String gasketSize) {
        this.gasketSize = gasketSize;
    }

    public String getGasketSize() {
        return gasketSize;
    }

    public void setHasGlue(ExtBoolean hasGlue) {
        this.hasGlue = hasGlue == null ? null : hasGlue.getId();
    }

    public ExtBoolean getHasGlue() {
        return hasGlue == null ? null : ExtBoolean.fromId(hasGlue);
    }

    public void setInstallationCountAllowed(Integer installationCountAllowed) {
        this.installationCountAllowed = installationCountAllowed;
    }

    public Integer getInstallationCountAllowed() {
        return installationCountAllowed;
    }

    public void setAutoInstallation(ExtBoolean autoInstallation) {
        this.autoInstallation = autoInstallation == null ? null : autoInstallation.getId();
    }

    public ExtBoolean getAutoInstallation() {
        return autoInstallation == null ? null : ExtBoolean.fromId(autoInstallation);
    }


    public void setGasEnvironmentAvailable(ExtBoolean gasEnvironmentAvailable) {
        this.gasEnvironmentAvailable = gasEnvironmentAvailable == null ? null : gasEnvironmentAvailable.getId();
    }

    public ExtBoolean getGasEnvironmentAvailable() {
        return gasEnvironmentAvailable == null ? null : ExtBoolean.fromId(gasEnvironmentAvailable);
    }

    public void setInstallationOptionDesignation(String installationOptionDesignation) {
        this.installationOptionDesignation = installationOptionDesignation;
    }

    public String getInstallationOptionDesignation() {
        return installationOptionDesignation;
    }


    public void setNonPbSolderTech(ExtBoolean nonPbSolderTech) {
        this.nonPbSolderTech = nonPbSolderTech == null ? null : nonPbSolderTech.getId();
    }

    public ExtBoolean getNonPbSolderTech() {
        return nonPbSolderTech == null ? null : ExtBoolean.fromId(nonPbSolderTech);
    }

    public void setTemperatureMode(String temperatureMode) {
        this.temperatureMode = temperatureMode;
    }

    public String getTemperatureMode() {
        return temperatureMode;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    public void setSolderBrand(SolderBrand solderBrand) {
        this.solderBrand = solderBrand;
    }

    public SolderBrand getSolderBrand() {
        return solderBrand;
    }

    public void setFluxBrand(FluxBrand fluxBrand) {
        this.fluxBrand = fluxBrand;
    }

    public FluxBrand getFluxBrand() {
        return fluxBrand;
    }


}