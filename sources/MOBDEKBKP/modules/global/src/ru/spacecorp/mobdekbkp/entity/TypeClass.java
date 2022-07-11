package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;
import javax.persistence.OneToMany;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.validation.constraints.NotNull;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import javax.persistence.OneToOne;

@NamePattern("%s %s|number,name")
@Table(name = "MOBDEKBKP_TYPE_CLASS")
@Entity(name = "mobdekbkp$TypeClass")
public class TypeClass extends StandardEntity {
    private static final long serialVersionUID = 1982978462800862182L;

    @Column(name = "NUMBER_", nullable = false)
    protected String number;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @NotNull
    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_ID")
    protected TypeClassType type;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    protected TypeClass parent;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "typeClass")
    protected List<TypeClassCharacteristic> characteristics;

    @OneToMany(mappedBy = "parent")
    protected List<TypeClass> children;


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "importClass")
    protected TyponominalQualityLevelImportSettings typonominalQualityLevelImportSettings;


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "typeClass")
    protected StrLibSettings strLibSettings;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "typeClass")
    protected StrRecSettings strRecSettings;

    public void setStrRecSettings(StrRecSettings strRecSettings) {
        this.strRecSettings = strRecSettings;
    }

    public StrRecSettings getStrRecSettings() {
        return strRecSettings;
    }


    public void setStrLibSettings(StrLibSettings strLibSettings) {
        this.strLibSettings = strLibSettings;
    }

    public StrLibSettings getStrLibSettings() {
        return strLibSettings;
    }


    public void setTyponominalQualityLevelImportSettings(TyponominalQualityLevelImportSettings typonominalQualityLevelImportSettings) {
        this.typonominalQualityLevelImportSettings = typonominalQualityLevelImportSettings;
    }

    public TyponominalQualityLevelImportSettings getTyponominalQualityLevelImportSettings() {
        return typonominalQualityLevelImportSettings;
    }


    public void setChildren(List<TypeClass> children) {
        this.children = children;
    }

    public List<TypeClass> getChildren() {
        return children;
    }


    public void setCharacteristics(List<TypeClassCharacteristic> characteristics) {
        this.characteristics = characteristics;
    }

    public List<TypeClassCharacteristic> getCharacteristics() {
        return characteristics;
    }


    public TypeClassType getType() {
        return type;
    }

    public void setType(TypeClassType type) {
        this.type = type;
    }


    public TypeClass getParent() {
        return parent;
    }

    public void setParent(TypeClass parent) {
        this.parent = parent;
    }


    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
