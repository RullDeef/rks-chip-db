package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum MaterialType implements EnumClass<String> {

    pins("pins"),
    substrates("substrates"),
    gaskets("gaskets"),
    bodies("bodies");

    private String id;

    MaterialType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static MaterialType fromId(String id) {
        for (MaterialType at : MaterialType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}