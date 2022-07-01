package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import java.util.Date;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

@NamePattern("%s %s|value,currency")
@Table(name = "MOBDEKBKP_COST")
@Entity(name = "mobdekbkp$Cost")
public class Cost extends StandardEntity {
    private static final long serialVersionUID = -8233952129400868644L;

    @Column(name = "VALUE_", nullable = false)
    protected Double value;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CURRENCY_ID")
    protected Currency currency;

    @Temporal(TemporalType.DATE)
    @Column(name = "SETUP_DATE", nullable = false)
    protected Date setupDate;


    public void setSetupDate(Date setupDate) {
        this.setupDate = setupDate;
    }

    public Date getSetupDate() {
        return setupDate;
    }


    public void setValue(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }


}