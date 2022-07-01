package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_TYPE_PROVIDER_ENTRY")
@Entity(name = "mobdekbkp$TypeProviderEntry")
public class TypeProviderEntry extends StandardEntity {
    private static final long serialVersionUID = -9201464629311059218L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NAME_ID")
    protected Company name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_INVERSE_ID")
    protected Type typeInverse;

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