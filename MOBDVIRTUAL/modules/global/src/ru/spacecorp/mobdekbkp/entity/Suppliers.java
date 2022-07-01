package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import java.util.List;

@Table(name = "MOBDEKBKP_SUPPLIERS")
@Entity(name = "mobdekbkp$Suppliers")
public class Suppliers extends StandardEntity {
    private static final long serialVersionUID = 4332556561421400392L;
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

    @Transient
    @MetaProperty
    protected String smartName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOGO_ID")
    protected FileDescriptor logo;

    @Column(name = "GEN_DIRECTOR")
    protected String genDirector;

    @Column(name = "OKATO")
    protected String okato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    protected Country country;

    public void setCountry(Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }


    public void setGenDirector(String genDirector) {
        this.genDirector = genDirector;
    }

    public String getGenDirector() {
        return genDirector;
    }

    public void setOkato(String okato) {
        this.okato = okato;
    }

    public String getOkato() {
        return okato;
    }


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


    @Override
    public String toString() {
        return getName();
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getKpp() {
        return kpp;
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