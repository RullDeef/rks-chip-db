package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.OneToOne;
import java.util.List;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import java.util.Set;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.Listeners;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import ru.spacecorp.documents.entity.Document;

@Listeners("mobdekbkp_TypeEntityListener")
@NamePattern("%s|designation")
@Table(name = "MOBDEKBKP_TYPE")
@Entity(name = "mobdekbkp$Type")
public class Type extends StandardEntity {
    private static final long serialVersionUID = 7751003203558598485L;

    @NotNull
    @Column(name = "DESIGNATION", nullable = false)
    protected String designation;

    @Column(name = "RELIABILITY_HANDBOOK")
    protected String reliabilityHandbook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALT_CLASS_REL_ID")
    protected AlterClassReliability altClassRel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALT_CLASS_MIL_ID")
    protected AlterClassMil altClassMil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALT_CLASS_G56649_ID")
    protected AlterClassGost56649 altClassG56649;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALT_CLASS_G2710_ID")
    protected AlterClassGost2710 altClassG2710;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "typeInverse")
    protected List<TypeManufacturerEntry> manufacturers;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "typeInverse")
    protected List<TypeCalicoholderEntry> calicoholders;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "typeInverse")
    protected List<TypeProviderEntry> providers;


    @JoinTable(name = "MOBDEKBKP_TYPE_DOCUMENT_LINK_DELIVERY",
            joinColumns = @JoinColumn(name = "TYPE_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documnetsDelivery;

    @JoinTable(name = "MOBDEKBKP_TYPE_DOCUMENT_LINK_APPSCHEME",
            joinColumns = @JoinColumn(name = "TYPE_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documentsStandartAppScheme;

    @Column(name = "HAS_LONG_DELIVER_CYCLE", nullable = false)
    protected String hasLongDeliverCycle;

    @NotNull
    @Lob
    @Column(name = "FUNCTIONAL_PURPOSE", nullable = false)
    protected String functionalPurpose;

    @NotNull
    @Column(name = "PLACEMENT_CATEGORY", nullable = false)
    protected String placementCategory;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIMATIC_IMPLEMENTATION_ID")
    protected TypeClimaticImplementation climaticImplementation;


    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATH_MODEL_PARAMS_ID")
    protected TypeMathModelParameters mathModelParams;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @JoinColumn(name = "TYPE_CLASS_ID")
    protected TypeClass typeClass;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "type")
    protected List<ParameterValue> paramValue;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AMOUNT_UNIT_ID")
    protected UnitDev amountUnit;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "type")
    protected Set<Typonominal> typonominal;

    public void setReliabilityHandbook(ExtBoolean reliabilityHandbook) {
        this.reliabilityHandbook = reliabilityHandbook == null ? null : reliabilityHandbook.getId();
    }

    public ExtBoolean getReliabilityHandbook() {
        return reliabilityHandbook == null ? null : ExtBoolean.fromId(reliabilityHandbook);
    }


    public void setAltClassRel(AlterClassReliability altClassRel) {
        this.altClassRel = altClassRel;
    }

    public AlterClassReliability getAltClassRel() {
        return altClassRel;
    }

    public void setAltClassMil(AlterClassMil altClassMil) {
        this.altClassMil = altClassMil;
    }

    public AlterClassMil getAltClassMil() {
        return altClassMil;
    }

    public void setAltClassG56649(AlterClassGost56649 altClassG56649) {
        this.altClassG56649 = altClassG56649;
    }

    public AlterClassGost56649 getAltClassG56649() {
        return altClassG56649;
    }

    public void setAltClassG2710(AlterClassGost2710 altClassG2710) {
        this.altClassG2710 = altClassG2710;
    }

    public AlterClassGost2710 getAltClassG2710() {
        return altClassG2710;
    }


    public List<Document> getDocumnetsDelivery() {
        return documnetsDelivery;
    }

    public void setDocumnetsDelivery(List<Document> documnetsDelivery) {
        this.documnetsDelivery = documnetsDelivery;
    }


    public List<Document> getDocumentsStandartAppScheme() {
        return documentsStandartAppScheme;
    }

    public void setDocumentsStandartAppScheme(List<Document> documentsStandartAppScheme) {
        this.documentsStandartAppScheme = documentsStandartAppScheme;
    }



    public List<ParameterValue> getParamValue() {
        return paramValue;
    }

    public void setParamValue(List<ParameterValue> paramValue) {
        this.paramValue = paramValue;
    }

    public List<TypeManufacturerEntry> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<TypeManufacturerEntry> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<TypeCalicoholderEntry> getCalicoholders() {
        return calicoholders;
    }

    public void setCalicoholders(List<TypeCalicoholderEntry> calicoholders) {
        this.calicoholders = calicoholders;
    }

    public List<TypeProviderEntry> getProviders() {
        return providers;
    }

    public void setProviders(List<TypeProviderEntry> providers) {
        this.providers = providers;
    }

    public UnitDev getAmountUnit() {
        return amountUnit;
    }

    public void setAmountUnit(UnitDev amountUnit) {
        this.amountUnit = amountUnit;
    }


    public void setClimaticImplementation(TypeClimaticImplementation climaticImplementation) {
        this.climaticImplementation = climaticImplementation;
    }

    public TypeClimaticImplementation getClimaticImplementation() {
        return climaticImplementation;
    }


    public Set<Typonominal> getTyponominal() {
        return typonominal;
    }

    public void setTyponominal(Set<Typonominal> typonominal) {
        this.typonominal = typonominal;
    }

    public void setHasLongDeliverCycle(ExtBoolean hasLongDeliverCycle) {
        this.hasLongDeliverCycle = hasLongDeliverCycle == null ? null : hasLongDeliverCycle.getId();
    }

    public ExtBoolean getHasLongDeliverCycle() {
        return hasLongDeliverCycle == null ? null : ExtBoolean.fromId(hasLongDeliverCycle);
    }


    public void setTypeClass(TypeClass typeClass) {
        this.typeClass = typeClass;
    }

    public TypeClass getTypeClass() {
        return typeClass;
    }


    public void setMathModelParams(TypeMathModelParameters mathModelParams) {
        this.mathModelParams = mathModelParams;
    }

    public TypeMathModelParameters getMathModelParams() {
        return mathModelParams;
    }


    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setFunctionalPurpose(String functionalPurpose) {
        this.functionalPurpose = functionalPurpose;
    }

    public String getFunctionalPurpose() {
        return functionalPurpose;
    }

    public void setPlacementCategory(TypePlacementCategory placementCategory) {
        this.placementCategory = placementCategory == null ? null : placementCategory.getId();
    }

    public TypePlacementCategory getPlacementCategory() {
        return placementCategory == null ? null : TypePlacementCategory.fromId(placementCategory);
    }


}