package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.annotation.Listeners;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_TYPE_MANUFACTURER_ENTRY")
@Entity(name = "mobdekbkp$TypeManufacturerEntry")
public class TypeManufacturerEntry extends StandardEntity {
    private static final long serialVersionUID = 7540471140941723089L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NAME_ID")
    protected Company name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPE_INVERSE_ID")
    protected Type typeInverse;

    @Override
    public String toString() {
        return this.name.getName();
    }

    public void setName(Company name) {
        this.name = name;
    }

    public Company getName() {
        return name;
    }


    public void setTypeInverse(Type typeInverse) {
        this.typeInverse = typeInverse;
    }

    public Type getTypeInverse() {
        return typeInverse;
    }


}