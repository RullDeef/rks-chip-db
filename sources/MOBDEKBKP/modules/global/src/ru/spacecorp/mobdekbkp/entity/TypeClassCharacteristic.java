package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

@NamePattern("%s %s|parameter,isMain")
@Table(name = "MOBDEKBKP_TYPE_CLASS_CHARACTERISTIC")
@Entity(name = "mobdekbkp$TypeClassCharacteristic")
public class TypeClassCharacteristic extends StandardEntity {
    private static final long serialVersionUID = -2415815576876891234L;

    @Column(name = "IS_MAIN", nullable = false)
    protected Boolean isMain = false;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARAMETER_ID")
    protected Parameter parameter;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPE_CLASS_ID")
    protected TypeClass typeClass;

    public void setTypeClass(TypeClass typeClass) {
        this.typeClass = typeClass;
    }

    public TypeClass getTypeClass() {
        return typeClass;
    }


    public void setIsMain(Boolean isMain) {
        this.isMain = isMain;
    }

    public Boolean getIsMain() {
        return isMain;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public Parameter getParameter() {
        return parameter;
    }


}