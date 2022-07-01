package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Lob;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_DEVICE_FILTER_CONDITIONS")
@Entity(name = "mobdekbkp$DeviceFilterConditions")
public class DeviceFilterConditions extends StandardEntity {
    private static final long serialVersionUID = -924592721913370249L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Lob
    @Column(name = "PATH")
    protected String path;


    @Column(name = "ATTRIBUTE")
    protected String attribute;


    @Column(name = "VALUE_TYPE")
    protected String valueType;

    @NotNull
    @Column(name = "COMPARE_OPERATOR", nullable = false)
    protected Integer compareOperator;

    @Column(name = "VAL_FLOAT")
    protected Double valFloat;

    @Column(name = "VAL_STRING")
    protected String valString;

    @Column(name = "VAL_BOOLEAN")
    protected Boolean valBoolean;


    @JoinTable(name = "MOBDEKBKP_DEVICE_DEVICE_FILTER_CONDITIONS_LINK",
            joinColumns = @JoinColumn(name = "DEVICE_FILTER_CONDITIONS_ID"),
            inverseJoinColumns = @JoinColumn(name = "DEVICE_ID"))
    @ManyToMany
    protected List<Device> devices;

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public List<Device> getDevices() {
        return devices;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }


    public ParameterValueType getValueType() {
        return valueType == null ? null : ParameterValueType.fromId(valueType);
    }

    public void setValueType(ParameterValueType valueType) {
        this.valueType = valueType == null ? null : valueType.getId();
    }


    public Boolean getValBoolean() {
        return valBoolean;
    }

    public void setValBoolean(Boolean valBoolean) {
        this.valBoolean = valBoolean;
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


    public CompareOperator getCompareOperator() {
        return compareOperator == null ? null : CompareOperator.fromId(compareOperator);
    }

    public void setCompareOperator(CompareOperator compareOperator) {
        this.compareOperator = compareOperator == null ? null : compareOperator.getId();
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }


}