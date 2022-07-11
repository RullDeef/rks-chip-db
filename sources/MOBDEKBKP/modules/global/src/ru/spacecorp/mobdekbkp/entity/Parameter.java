package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import javax.persistence.ManyToOne;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_PARAMETER")
@Entity(name = "mobdekbkp$Parameter")
public class Parameter extends StandardEntity {
    private static final long serialVersionUID = -4862150375243073419L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UNIT_ID")
    protected UnitVal unit;

    @Column(name = "DEFAULT_VALUE_TYPE", nullable = false)
    protected String defaultValueType;

    @Column(name = "PARAM_TYPE", nullable = false)
    protected String paramType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARAMETER_CATEGORY_ID")
    protected ParameterCategory parameterCategory;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "parameter")
    protected List<ParameterValue> value;

    public void setParameterCategory(ParameterCategory parameterCategory) {
        this.parameterCategory = parameterCategory;
    }

    public ParameterCategory getParameterCategory() {
        return parameterCategory;
    }


    public UnitVal getUnit() {
        return unit;
    }

    public void setUnit(UnitVal unit) {
        this.unit = unit;
    }

    public void setValue(List<ParameterValue> value) {
        this.value = value;
    }

    public List<ParameterValue> getValue() {
        return value;
    }


    public void setDefaultValueType(ParameterValueType defaultValueType) {
        this.defaultValueType = defaultValueType == null ? null : defaultValueType.getId();
    }

    public ParameterValueType getDefaultValueType() {
        return defaultValueType == null ? null : ParameterValueType.fromId(defaultValueType);
    }

    public String getName() {
        return name;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setParamType(ParameterType paramType) {
        this.paramType = paramType == null ? null : paramType.getId();
    }

    public ParameterType getParamType() {
        return paramType == null ? null : ParameterType.fromId(paramType);
    }



}