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
@Table(name = "MOBDEKBKP_ALTER_CLASS_RELIABILITY")
@Entity(name = "mobdekbkp$AlterClassReliability")
public class AlterClassReliability extends StandardEntity {
    private static final long serialVersionUID = 86933242368470837L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    protected AlterClassReliability parent;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPE_ID")
    protected TypeClassType type;

    public void setParent(AlterClassReliability parent) {
        this.parent = parent;
    }

    public AlterClassReliability getParent() {
        return parent;
    }

    public void setType(TypeClassType type) {
        this.type = type;
    }

    public TypeClassType getType() {
        return type;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}