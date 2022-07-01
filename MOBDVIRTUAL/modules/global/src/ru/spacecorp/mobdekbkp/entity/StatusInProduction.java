package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum StatusInProduction implements EnumClass<String> {

    inCurrentProduction("inCurrentProduction"),
    notNewDevelopments("notNewDevelopments"),
    discontinued("discontinued");

    private String id;

    StatusInProduction(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static StatusInProduction fromId(String id) {
        for (StatusInProduction at : StatusInProduction.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}