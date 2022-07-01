package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.Lob;

@NamePattern("%s %s %s|typonominal,amount,wantedDeliverDate")
@Table(name = "MOBDEKBKP_COMPANY_NEED")
@Entity(name = "mobdekbkp$CompanyNeed")
public class CompanyNeed extends StandardEntity {
    private static final long serialVersionUID = 8522409315212295708L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @Column(name = "AMOUNT", nullable = false)
    protected Integer amount;

    @Temporal(TemporalType.DATE)
    @Future
    @NotNull
    @Column(name = "WANTED_DELIVER_DATE", nullable = false)
    protected Date wantedDeliverDate;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COMPANY_ID")
    protected Company company;

    @Column(name = "STATUS")
    protected Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_NEED_APPLICATION_ID")
    protected CompanyNeedApplication companyNeedApplication;

    @Lob
    @Column(name = "REQUIREMENTS")
    protected String requirements;

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getRequirements() {
        return requirements;
    }


    public void setCompanyNeedApplication(CompanyNeedApplication companyNeedApplication) {
        this.companyNeedApplication = companyNeedApplication;
    }

    public CompanyNeedApplication getCompanyNeedApplication() {
        return companyNeedApplication;
    }


    public void setStatus(CompanyNeedStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public CompanyNeedStatus getStatus() {
        return status == null ? null : CompanyNeedStatus.fromId(status);
    }


    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setWantedDeliverDate(Date wantedDeliverDate) {
        this.wantedDeliverDate = wantedDeliverDate;
    }

    public Date getWantedDeliverDate() {
        return wantedDeliverDate;
    }


}