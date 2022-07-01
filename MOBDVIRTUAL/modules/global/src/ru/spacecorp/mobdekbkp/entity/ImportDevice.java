package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.OneToOne;
import java.util.List;
import javax.persistence.OneToMany;

@Table(name = "MOBDEKBKP_IMPORT_DEVICE")
@Entity(name = "mobdekbkp$ImportDevice")
public class ImportDevice extends StandardEntity {
    private static final long serialVersionUID = 1467477422557552624L;

    @Column(name = "NAME", length = 250)
    protected String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_CLASS_ID")
    protected ImportClass importClass;

    @OneToMany(mappedBy = "importDevice")
    protected List<ImportDocsSchemes> importDocsSchemes;

    @OneToMany(mappedBy = "importDevice")
    protected List<Factory> factoryImport;

    @OneToMany(mappedBy = "importDevice")
    protected List<ParametersForPurchasing> parametersForPurchasing;

    @OneToMany(mappedBy = "importDevice")
    protected List<AdvancedSetting> advancedSetting;

    @OneToMany(mappedBy = "importDevice")
    protected List<OuterPersistenceInfo> outerPersistenceInfo;

    @OneToMany(mappedBy = "importDevice")
    protected List<Corpus> corpus;

    @OneToMany(mappedBy = "importDevice")
    protected List<ApplicabilityDevices> applicabilityDevices;

    @OneToMany(mappedBy = "importDevice")
    protected List<SaprData> saprData;

    @OneToMany(mappedBy = "importDevice")
    protected List<MainParameters> mainParameters;

    @OneToMany(mappedBy = "importDevice")
    protected List<CustomParameters> customParameters;

    @OneToMany(mappedBy = "importDevice")
    protected List<OuterRejection> outerRejection;

    @OneToMany(mappedBy = "importDevice")
    protected List<OuterDbRefuse> outerDbRefuse;


    @OneToMany(mappedBy = "importDevice")
    protected List<AnalogImport> analogImport;

    public void setAnalogImport(List<AnalogImport> analogImport) {
        this.analogImport = analogImport;
    }

    public List<AnalogImport> getAnalogImport() {
        return analogImport;
    }


    public void setOuterRejection(List<OuterRejection> outerRejection) {
        this.outerRejection = outerRejection;
    }

    public List<OuterRejection> getOuterRejection() {
        return outerRejection;
    }

    public void setOuterDbRefuse(List<OuterDbRefuse> outerDbRefuse) {
        this.outerDbRefuse = outerDbRefuse;
    }

    public List<OuterDbRefuse> getOuterDbRefuse() {
        return outerDbRefuse;
    }


    public void setMainParameters(List<MainParameters> mainParameters) {
        this.mainParameters = mainParameters;
    }

    public List<MainParameters> getMainParameters() {
        return mainParameters;
    }

    public void setCustomParameters(List<CustomParameters> customParameters) {
        this.customParameters = customParameters;
    }

    public List<CustomParameters> getCustomParameters() {
        return customParameters;
    }


    public void setSaprData(List<SaprData> saprData) {
        this.saprData = saprData;
    }

    public List<SaprData> getSaprData() {
        return saprData;
    }


    public void setApplicabilityDevices(List<ApplicabilityDevices> applicabilityDevices) {
        this.applicabilityDevices = applicabilityDevices;
    }

    public List<ApplicabilityDevices> getApplicabilityDevices() {
        return applicabilityDevices;
    }


    public void setCorpus(List<Corpus> corpus) {
        this.corpus = corpus;
    }

    public List<Corpus> getCorpus() {
        return corpus;
    }


    public void setOuterPersistenceInfo(List<OuterPersistenceInfo> outerPersistenceInfo) {
        this.outerPersistenceInfo = outerPersistenceInfo;
    }

    public List<OuterPersistenceInfo> getOuterPersistenceInfo() {
        return outerPersistenceInfo;
    }


    public void setAdvancedSetting(List<AdvancedSetting> advancedSetting) {
        this.advancedSetting = advancedSetting;
    }

    public List<AdvancedSetting> getAdvancedSetting() {
        return advancedSetting;
    }


    public List<ParametersForPurchasing> getParametersForPurchasing() {
        return parametersForPurchasing;
    }

    public void setParametersForPurchasing(List<ParametersForPurchasing> parametersForPurchasing) {
        this.parametersForPurchasing = parametersForPurchasing;
    }


    public void setFactoryImport(List<Factory> factoryImport) {
        this.factoryImport = factoryImport;
    }

    public List<Factory> getFactoryImport() {
        return factoryImport;
    }


    public void setImportDocsSchemes(List<ImportDocsSchemes> importDocsSchemes) {
        this.importDocsSchemes = importDocsSchemes;
    }

    public List<ImportDocsSchemes> getImportDocsSchemes() {
        return importDocsSchemes;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setImportClass(ImportClass importClass) {
        this.importClass = importClass;
    }

    public ImportClass getImportClass() {
        return importClass;
    }


}