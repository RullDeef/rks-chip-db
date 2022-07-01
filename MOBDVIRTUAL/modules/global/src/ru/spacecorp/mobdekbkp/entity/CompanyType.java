package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_COMPANY_TYPE")
@Entity(name = "mobdekbkp$CompanyType")
public class CompanyType extends StandardEntity {
    private static final long serialVersionUID = -7860359067989813463L;

    @NotNull
    @Lob
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Override
    public String toString() {
        return getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}