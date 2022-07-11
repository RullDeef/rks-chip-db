package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ParameterValueType implements EnumClass<String> {

    integer("integer"),
    normal("normal"),
    normalTolerated("normalTolerated"),
    normalToleratedPrecent("normalToleratedPrecent"),
    interval("interval"),
    gammaProbabilityValue("gammaProbabilityValue"),
    bool("bool"),
    string("string"),
    stringLib("stringLib"),
    stringRec("stringRec");

    private String id;

    ParameterValueType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ParameterValueType fromId(String id) {
        for (ParameterValueType at : ParameterValueType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}