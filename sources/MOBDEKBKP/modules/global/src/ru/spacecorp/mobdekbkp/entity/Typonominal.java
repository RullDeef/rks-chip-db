package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.OneToOne;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import javax.persistence.OneToMany;
import java.util.Collection;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_TYPONOMINAL")
@Entity(name = "mobdekbkp$Typonominal")
public class Typonominal extends StandardEntity {
    private static final long serialVersionUID = 1764703049234968127L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_QUALITY_LEVEL_NATIVE_ID")
    protected TyponominalQualityLevelNative typonominalQualityLevelNative;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_QUALITY_LEVEL_IMPORT_ID")
    protected TyponominalQualityLevelImport typonominalQualityLevelImport;

    @Column(name = "MASS")
    protected Double mass;

    @Column(name = "NOT_PERSPECTIVE", nullable = false)
    protected String notPerspective;

    @NotNull
    @Column(name = "HAS_SUBSTITUTE", nullable = false)
    protected String hasSubstitute;

    @Column(name = "SEAL_NEEDED", nullable = false)
    protected String sealNeeded;

    @Column(name = "IS_AUTO_ASSEMBLE", nullable = false)
    protected String isAutoAssemble;

    @Column(name = "MADE_USING_IMPORT_ITEMS", nullable = false)
    protected String madeUsingImportItems;

    @Column(name = "SHELF_LIFE")
    protected Integer shelfLife;

    @Column(name = "PERSISTENCE_CYCLE")
    protected Integer persistenceCycle;

    @Column(name = "HAS_BODY", nullable = false)
    protected String hasBody;

    @Column(name = "LIFE_CYCLE_STAGE", nullable = false)
    protected String lifeCycleStage;


    @Column(name = "LABELING")
    protected String labeling;

    @MetaProperty
    public String getQualityCaption() {
        if (typonominalQualityLevelImport != null)
            return typonominalQualityLevelImport.getName();
        else
            return typonominalQualityLevelNative.getName();
    }

    @MetaProperty
    public boolean isRussian() {
        return typonominalQualityLevelNative != null;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BODY_ID")
    protected TyponominalBody body;


    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSTALL_PARAMETERS_ID")
    protected TyponominalInstallParameters installParameters;


    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "typonominal")
    protected Collection<OuterPersistenceInfo> persistenceInfo;

    @OneToMany(mappedBy = "parentTyponominal")
    protected List<TyponominalAnalog> analogs;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPE_ID")
    protected Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LIB_ATTRIBUTES_ID")
    protected LibAttributes libAttributes;

    @OneToMany(mappedBy = "typonominal")
    protected List<TyponominalPurchaseParameters> purchaseParameters;

    public void setLabeling(String labeling) {
        this.labeling = labeling;
    }

    public String getLabeling() {
        return labeling;
    }


    public void setShelfLife(Integer shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Integer getShelfLife() {
        return shelfLife;
    }

    public void setPersistenceCycle(Integer persistenceCycle) {
        this.persistenceCycle = persistenceCycle;
    }

    public Integer getPersistenceCycle() {
        return persistenceCycle;
    }


    public void setPurchaseParameters(List<TyponominalPurchaseParameters> purchaseParameters) {
        this.purchaseParameters = purchaseParameters;
    }

    public List<TyponominalPurchaseParameters> getPurchaseParameters() {
        return purchaseParameters;
    }


    @Override
    public String toString(){
        return getName();
    }

    public void setLibAttributes(LibAttributes libAttributes) {
        this.libAttributes = libAttributes;
    }

    public LibAttributes getLibAttributes() {
        return libAttributes;
    }


    public void setHasSubstitute(ExtBoolean hasSubstitute) {
        this.hasSubstitute = hasSubstitute == null ? null : hasSubstitute.getId();
    }

    public ExtBoolean getHasSubstitute() {
        return hasSubstitute == null ? null : ExtBoolean.fromId(hasSubstitute);
    }

    public void setSealNeeded(ExtBoolean sealNeeded) {
        this.sealNeeded = sealNeeded == null ? null : sealNeeded.getId();
    }

    public ExtBoolean getSealNeeded() {
        return sealNeeded == null ? null : ExtBoolean.fromId(sealNeeded);
    }

    public void setIsAutoAssemble(ExtBoolean isAutoAssemble) {
        this.isAutoAssemble = isAutoAssemble == null ? null : isAutoAssemble.getId();
    }

    public ExtBoolean getIsAutoAssemble() {
        return isAutoAssemble == null ? null : ExtBoolean.fromId(isAutoAssemble);
    }


    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }


    public void setPersistenceInfo(Collection<OuterPersistenceInfo> persistenceInfo) {
        this.persistenceInfo = persistenceInfo;
    }

    public Collection<OuterPersistenceInfo> getPersistenceInfo() {
        return persistenceInfo;
    }


    public TyponominalBody getBody() {
        return body;
    }

    public void setBody(TyponominalBody body) {
        this.body = body;
    }


    public void setInstallParameters(TyponominalInstallParameters installParameters) {
        this.installParameters = installParameters;
    }

    public TyponominalInstallParameters getInstallParameters() {
        return installParameters;
    }


    public void setAnalogs(List<TyponominalAnalog> analogs) {
        this.analogs = analogs;
    }

    public List<TyponominalAnalog> getAnalogs() {
        return analogs;
    }


    public void setLifeCycleStage(TyponominalLifeCycleStage lifeCycleStage) {
        this.lifeCycleStage = lifeCycleStage == null ? null : lifeCycleStage.getId();
    }

    public TyponominalLifeCycleStage getLifeCycleStage() {
        return lifeCycleStage == null ? null : TyponominalLifeCycleStage.fromId(lifeCycleStage);
    }

    public void setHasBody(ExtBoolean hasBody) {
        this.hasBody = hasBody == null ? null : hasBody.getId();
    }

    public ExtBoolean getHasBody() {
        return hasBody == null ? null : ExtBoolean.fromId(hasBody);
    }

    public void setNotPerspective(ExtBoolean notPerspective) {
        this.notPerspective = notPerspective == null ? null : notPerspective.getId();
    }

    public ExtBoolean getNotPerspective() {
        return notPerspective == null ? null : ExtBoolean.fromId(notPerspective);
    }

    public void setMadeUsingImportItems(ExtBoolean madeUsingImportItems) {
        this.madeUsingImportItems = madeUsingImportItems == null ? null : madeUsingImportItems.getId();
    }

    public ExtBoolean getMadeUsingImportItems() {
        return madeUsingImportItems == null ? null : ExtBoolean.fromId(madeUsingImportItems);
    }




    public TyponominalQualityLevelImport getTyponominalQualityLevelImport() {
        return typonominalQualityLevelImport;
    }

    public void setTyponominalQualityLevelImport(TyponominalQualityLevelImport typonominalQualityLevelImport) {
        this.typonominalQualityLevelImport = typonominalQualityLevelImport;
    }

    public TyponominalQualityLevelNative getTyponominalQualityLevelNative() {
        return typonominalQualityLevelNative;
    }

    public void setTyponominalQualityLevelNative(TyponominalQualityLevelNative typonominalQualityLevelNative) {
        this.typonominalQualityLevelNative = typonominalQualityLevelNative;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getMass() {
        return mass;
    }


}