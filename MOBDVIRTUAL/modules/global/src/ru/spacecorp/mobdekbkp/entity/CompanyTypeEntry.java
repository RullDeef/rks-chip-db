package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.*;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.annotation.Listeners;

@Listeners("mobdekbkp_CompanyTypeEntryEntityListener")
@NamePattern("%s|type")
@Table(name = "MOBDEKBKP_COMPANY_TYPE_ENTRY")
@Entity(name = "mobdekbkp$CompanyTypeEntry")
public class CompanyTypeEntry extends StandardEntity {
    private static final long serialVersionUID = 4884085076405267292L;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPE_ID")
    protected CompanyType type;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COMPANY_ID")
    protected Company company;

    @Override
    public String toString() {
        return getType().getName();
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }


    public void setType(CompanyType type) {
        this.type = type;
    }

    public CompanyType getType() {
        return type;
    }


}