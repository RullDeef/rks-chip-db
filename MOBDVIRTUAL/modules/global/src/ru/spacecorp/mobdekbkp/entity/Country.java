package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_COUNTRY")
@Entity(name = "mobdekbkp$Country")
public class Country extends StandardEntity {
    private static final long serialVersionUID = -5812048548701043827L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "COUNTRY_TYPE", nullable = false)
    protected String countryType;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCountryType(CountryType countryType) {
        this.countryType = countryType == null ? null : countryType.getId();
    }

    public CountryType getCountryType() {
        return countryType == null ? null : CountryType.fromId(countryType);
    }


}