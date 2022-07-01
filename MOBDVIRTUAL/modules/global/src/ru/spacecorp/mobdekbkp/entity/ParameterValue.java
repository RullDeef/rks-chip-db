package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.entity.annotation.Listeners;

import javax.persistence.OneToOne;

@NamePattern("%s|type")
@Table(name = "MOBDEKBKP_PARAMETER_VALUE")
@Entity(name = "mobdekbkp$ParameterValue")
public class ParameterValue extends StandardEntity {
    private static final long serialVersionUID = 71295753392010363L;

    @Column(name = "CURRENT_VALUE_TYPE", nullable = false)
    protected String currentValueType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VAL_STR_REC_ID")
    protected StrRec valStrRec;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VAL_STR_LIB_ID")
    protected StrLib valStrLib;

    @Column(name = "VAL_FLOAT")
    protected Double valFloat;

    @Column(name = "VAL_STRING")
    protected String valString;

    @Column(name = "VAL_MAX")
    protected Double valMax;

    @Column(name = "VAL_MIN")
    protected Double valMin;

    @Column(name = "TOLERANCE_PLUS")
    protected Double tolerancePlus;

    @Column(name = "TOLERANCE_MINUS")
    protected Double toleranceMinus;

    @Column(name = "GAMMA")
    protected Integer gamma;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPE_ID")
    protected Type type;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARAMETER_ID")
    protected Parameter parameter;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATION_CONDITION_ID")
    protected OperationConditions operationCondition;

    public void setValStrRec(StrRec valStrRec) {
        this.valStrRec = valStrRec;
    }

    public StrRec getValStrRec() {
        return valStrRec;
    }


    public void setValStrLib(StrLib valStrLib) {
        this.valStrLib = valStrLib;
    }

    public StrLib getValStrLib() {
        return valStrLib;
    }


    public void setOperationCondition(OperationConditions operationCondition) {
        this.operationCondition = operationCondition;
    }

    public OperationConditions getOperationCondition() {
        return operationCondition;
    }


    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }


    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public Parameter getParameter() {
        return parameter;
    }


    public void setCurrentValueType(ParameterValueType currentValueType) {
        this.currentValueType = currentValueType == null ? null : currentValueType.getId();
    }

    public ParameterValueType getCurrentValueType() {
        return currentValueType == null ? null : ParameterValueType.fromId(currentValueType);
    }


    public void setValFloat(Double valFloat) {
        this.valFloat = valFloat;
    }

    public Double getValFloat() {
        return valFloat;
    }

    public void setValString(String valString) {
        this.valString = valString;
    }

    public String getValString() {
        return valString;
    }

    public void setValMax(Double valMax) {
        this.valMax = valMax;
    }

    public Double getValMax() {
        return valMax;
    }

    public void setValMin(Double valMin) {
        this.valMin = valMin;
    }

    public Double getValMin() {
        return valMin;
    }

    public void setTolerancePlus(Double tolerancePlus) {
        this.tolerancePlus = tolerancePlus;
    }

    public Double getTolerancePlus() {
        return tolerancePlus;
    }

    public void setToleranceMinus(Double toleranceMinus) {
        this.toleranceMinus = toleranceMinus;
    }

    public Double getToleranceMinus() {
        return toleranceMinus;
    }

    public void setGamma(Integer gamma) {
        this.gamma = gamma;
    }

    public Integer getGamma() {
        return gamma;
    }


}