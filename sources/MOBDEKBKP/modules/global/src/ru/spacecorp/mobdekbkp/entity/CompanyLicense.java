package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import javax.validation.constraints.NotNull;

import javax.persistence.OneToOne;
import ru.spacecorp.documents.entity.Document;

@NamePattern("%s|number")
@Table(name = "MOBDEKBKP_COMPANY_LICENSE")
@Entity(name = "mobdekbkp$CompanyLicense")
public class CompanyLicense extends StandardEntity {
    private static final long serialVersionUID = -3191501570370083190L;

    @NotNull
    @Column(name = "NUMBER_", nullable = false)
    protected String number;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OBTAINED")
    protected Date dateObtained;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_EXPIRE")
    protected Date dateExpire;

    @NotNull
    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPE_ID")
    protected CompanyLicenseType type;

    @NotNull
    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COMPANY_ID")
    protected Company company;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_ID")
    protected Document document;
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }






    @Override
    public String toString() {
        return getNumber();
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }


    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setDateObtained(Date dateObtained) {
        this.dateObtained = dateObtained;
    }

    public Date getDateObtained() {
        return dateObtained;
    }

    public void setDateExpire(Date dateExpire) {
        this.dateExpire = dateExpire;
    }

    public Date getDateExpire() {
        return dateExpire;
    }

    public void setType(CompanyLicenseType type) {
        this.type = type;
    }

    public CompanyLicenseType getType() {
        return type;
    }


}