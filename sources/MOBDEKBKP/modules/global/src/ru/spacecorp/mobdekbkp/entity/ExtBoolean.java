package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ExtBoolean implements EnumClass<String> {

    yes("yes"),
    no("no"),
    notSet("notSet");

    private String id;

    ExtBoolean(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ExtBoolean fromId(String id) {
        for (ExtBoolean at : ExtBoolean.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}