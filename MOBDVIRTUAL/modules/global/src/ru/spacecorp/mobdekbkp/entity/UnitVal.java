package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s [%s]|name,shortName")
@Table(name = "MOBDEKBKP_UNIT_VAL")
@Entity(name = "mobdekbkp$UnitVal")
public class UnitVal extends StandardEntity {
    private static final long serialVersionUID = 5411850244467414743L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "SHORT_NAME", nullable = false)
    protected String shortName;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    @MetaProperty
    public String getFullName() {
        String fullName = String.format("%s [%s]", getName(), getShortName());
        return fullName;
    }

}