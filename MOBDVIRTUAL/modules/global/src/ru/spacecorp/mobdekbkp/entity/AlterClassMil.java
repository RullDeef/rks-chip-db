package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_ALTER_CLASS_MIL")
@Entity(name = "mobdekbkp$AlterClassMil")
public class AlterClassMil extends StandardEntity {
    private static final long serialVersionUID = -636622205189767479L;

    @Column(name = "NAME")
    protected String name;

    @NotNull
    @Column(name = "SHORT_NAME", nullable = false)
    protected String shortName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    protected AlterClassMil parent;

    public void setParent(AlterClassMil parent) {
        this.parent = parent;
    }

    public AlterClassMil getParent() {
        return parent;
    }


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