package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ParameterType implements EnumClass<String> {

    technical("technical"),
    electrical("electrical"),
    environmental("environmental"),
    temperatural("temperatural"),
    mechanical("mechanical"),
    acoustic("acoustic"),
    reliability("reliability"),
    stability("stability");

    private String id;

    ParameterType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ParameterType fromId(String id) {
        for (ParameterType at : ParameterType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}