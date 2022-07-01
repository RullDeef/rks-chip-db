package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_ALTER_CLASS_GOST56649")
@Entity(name = "mobdekbkp$AlterClassGost56649")
public class AlterClassGost56649 extends StandardEntity {
    private static final long serialVersionUID = 524379047142944891L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "TYPE_SAMPLES")
    protected String typeSamples;

    @Column(name = "CODE")
    protected String code;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTypeSamples(String typeSamples) {
        this.typeSamples = typeSamples;
    }

    public String getTypeSamples() {
        return typeSamples;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


}