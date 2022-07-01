package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum Tightness implements EnumClass<String> {

    da("01"),
    net("02");

    private String id;

    Tightness(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Tightness fromId(String id) {
        for (Tightness at : Tightness.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}