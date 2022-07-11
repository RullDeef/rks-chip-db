package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Lob;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;
import javax.persistence.OneToMany;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import java.util.Set;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import java.util.Collection;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import javax.persistence.OneToOne;
import com.haulmont.chile.core.annotations.MetaProperty;
import javax.persistence.Transient;
import com.haulmont.cuba.core.entity.FileDescriptor;

@NamePattern("%s|smartName")
@Table(name = "MOBDEKBKP_COMPANY")
@Entity(name = "mobdekbkp$Company")
public class Company extends StandardEntity {
    private static final long serialVersionUID = -7138158701760984404L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "NAME_SHORT")
    protected String nameShort;

    @Lob
    @Column(name = "ADDRESS_FACT", nullable = false)
    protected String addressFact;

    @Lob
    @Column(name = "ADDRESS_LEGAL", nullable = false)
    protected String addressLegal;

    @Column(name = "OGRN")
    protected String ogrn;

    @Column(name = "KPP")
    protected String kpp;

    @Column(name = "OKUD")
    protected String okud;

    @Column(name = "INN")
    protected String inn;

    @Column(name = "OKPO")
    protected String okpo;

    @Column(name = "AGENT")
    protected String agent;

    @NotNull
    @Column(name = "PHONE", nullable = false)
    protected String phone;

    @Column(name = "FAX")
    protected String fax;

    @Column(name = "EMAIL")
    protected String email;

    @Column(name = "WEBSITE")
    protected String website;

    @NotNull
    @Column(name = "IS_MILITARY", nullable = false)
    protected String isMilitary;


    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COUNTRY_ID")
    protected Country country;


    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "company")
    protected List<CompanyLicense> licenses;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "company")
    protected List<CompanyTypeEntry> types;

    @Column(name = "RATING")
    protected Double rating;

    @OneToMany(mappedBy = "company")
    protected List<CompanyNeed> needs;

    @OneToMany(mappedBy = "company")
    protected List<TyponominalPurchaseParameters> purchaseParameters;

    @Transient
    @MetaProperty
    protected String smartName;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOGO_ID")
    protected FileDescriptor logo;

    public void setLogo(FileDescriptor logo) {
        this.logo = logo;
    }

    public FileDescriptor getLogo() {
        return logo;
    }


    public void setSmartName(String smartName) {
        this.smartName = smartName;
    }

    public String getSmartName() {
        if (nameShort == null) {
            return name;
        } else if (nameShort.contentEquals("")) {
            return name;
        }
        return nameShort;
    }


    public void setPurchaseParameters(List<TyponominalPurchaseParameters> purchaseParameters) {
        this.purchaseParameters = purchaseParameters;
    }

    public List<TyponominalPurchaseParameters> getPurchaseParameters() {
        return purchaseParameters;
    }


    public void setNeeds(List<CompanyNeed> needs) {
        this.needs = needs;
    }

    public List<CompanyNeed> getNeeds() {
        return needs;
    }


    public List<CompanyTypeEntry> getTypes() {
        return types;
    }

    public void setTypes(List<CompanyTypeEntry> types) {
        this.types = types;
    }




    @Override
    public String toString(){
        return getName();
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getKpp() {
        return kpp;
    }



    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }



    public void setCountry(Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }

    public void setLicenses(List<CompanyLicense> licenses) {
        this.licenses = licenses;
    }

    public List<CompanyLicense> getLicenses() {
        return licenses;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setIsMilitary(ExtBoolean isMilitary) {
        this.isMilitary = isMilitary == null ? null : isMilitary.getId();
    }

    public ExtBoolean getIsMilitary() {
        return isMilitary == null ? null : ExtBoolean.fromId(isMilitary);
    }




    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOkud(String okud) {
        this.okud = okud;
    }

    public String getOkud() {
        return okud;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getInn() {
        return inn;
    }

    public void setOkpo(String okpo) {
        this.okpo = okpo;
    }

    public String getOkpo() {
        return okpo;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAgent() {
        return agent;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFax() {
        return fax;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setAddressFact(String addressFact) {
        this.addressFact = addressFact;
    }

    public String getAddressFact() {
        return addressFact;
    }

    public void setAddressLegal(String addressLegal) {
        this.addressLegal = addressLegal;
    }

    public String getAddressLegal() {
        return addressLegal;
    }

}