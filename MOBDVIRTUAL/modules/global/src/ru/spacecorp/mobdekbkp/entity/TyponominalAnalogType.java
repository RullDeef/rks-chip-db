package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum TyponominalAnalogType implements EnumClass<String> {

    full("full"),
    closest("closest"),
    functional("functional"),
    possible("possible");

    private String id;

    TyponominalAnalogType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TyponominalAnalogType fromId(String id) {
        for (TyponominalAnalogType at : TyponominalAnalogType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}