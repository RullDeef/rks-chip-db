package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum CountryType implements EnumClass<String> {

    russian("russian"),
    foreign("foreign"),
    friendly("friendly");

    private String id;

    CountryType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CountryType fromId(String id) {
        for (CountryType at : CountryType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}