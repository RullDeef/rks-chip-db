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

@NamePattern("%s %s|typeGroup,code")
@Table(name = "MOBDEKBKP_ALTER_CLASS_GOST2710")
@Entity(name = "mobdekbkp$AlterClassGost2710")
public class AlterClassGost2710 extends StandardEntity {
    private static final long serialVersionUID = 2751751234836401187L;

    @NotNull
    @Column(name = "TYPE_GROUP", nullable = false)
    protected String typeGroup;

    @Column(name = "TYPE_SAMPLES")
    protected String typeSamples;

    @NotNull
    @Column(name = "CODE", nullable = false)
    protected String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    protected AlterClassGost2710 parent;

    public void setParent(AlterClassGost2710 parent) {
        this.parent = parent;
    }

    public AlterClassGost2710 getParent() {
        return parent;
    }


    public void setTypeGroup(String typeGroup) {
        this.typeGroup = typeGroup;
    }

    public String getTypeGroup() {
        return typeGroup;
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