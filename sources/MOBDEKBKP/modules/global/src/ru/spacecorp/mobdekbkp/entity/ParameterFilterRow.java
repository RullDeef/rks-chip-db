package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.AbstractNotPersistentEntity;
import com.haulmont.cuba.core.global.filter.Op;

import java.util.UUID;

@MetaClass(name = "mobdekbkp$ParameterFilterRow")
public class ParameterFilterRow extends AbstractNotPersistentEntity {
    private static final long serialVersionUID = 4965065217581316376L;

    @MetaProperty
    protected UUID uuid;

    @MetaProperty
    protected String parameterType;

    @MetaProperty
    protected Parameter parameter;

    @MetaProperty
    protected String operationType;

    @MetaProperty
    protected Double valFloat;

    @MetaProperty
    protected String valString;

    @MetaProperty
    protected Double valMax;

    @MetaProperty
    protected Double valMin;

    @MetaProperty
    protected Double tolerancePlus;

    @MetaProperty
    protected Double toleranceMinus;

    @MetaProperty
    protected Integer gamma;


    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType == null ? null : parameterType.getId();
    }

    public ParameterType getParameterType() {
        return parameterType == null ? null : ParameterType.fromId(parameterType);
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public void setOperationType(Op operationType) {
        setOperationType(operationType.name());
    }

    public String getOperationType() {
        return operationType;
    }

    public Op getOperationTypeAsEnum() {
        return Op.valueOf(operationType);
    }


    public Double getValFloat() {
        return valFloat;
    }

    public void setValFloat(Double valFloat) {
        this.valFloat = valFloat;
    }

    public String getValString() {
        return valString;
    }

    public void setValString(String valString) {
        this.valString = valString;
    }

    public Double getValMax() {
        return valMax;
    }

    public void setValMax(Double valMax) {
        this.valMax = valMax;
    }

    public Double getValMin() {
        return valMin;
    }

    public void setValMin(Double valMin) {
        this.valMin = valMin;
    }

    public Double getTolerancePlus() {
        return tolerancePlus;
    }

    public void setTolerancePlus(Double tolerancePlus) {
        this.tolerancePlus = tolerancePlus;
    }

    public Double getToleranceMinus() {
        return toleranceMinus;
    }

    public void setToleranceMinus(Double toleranceMinus) {
        this.toleranceMinus = toleranceMinus;
    }

    public Integer getGamma() {
        return gamma;
    }

    public void setGamma(Integer gamma) {
        this.gamma = gamma;
    }
}