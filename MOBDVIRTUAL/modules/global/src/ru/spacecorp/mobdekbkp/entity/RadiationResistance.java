package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum RadiationResistance implements EnumClass<String> {

    pc("01"),
    no("02");

    private String id;

    RadiationResistance(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static RadiationResistance fromId(String id) {
        for (RadiationResistance at : RadiationResistance.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}