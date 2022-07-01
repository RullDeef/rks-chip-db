package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_UNIT_DEV")
@Entity(name = "mobdekbkp$UnitDev")
public class UnitDev extends StandardEntity {
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


}