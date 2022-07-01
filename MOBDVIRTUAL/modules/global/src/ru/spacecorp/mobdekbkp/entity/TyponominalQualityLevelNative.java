package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_TYPONOMINAL_QUALITY_LEVEL_NATIVE")
@Entity(name = "mobdekbkp$TyponominalQualityLevelNative")
public class TyponominalQualityLevelNative extends StandardEntity {
    private static final long serialVersionUID = 138919732635436395L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}