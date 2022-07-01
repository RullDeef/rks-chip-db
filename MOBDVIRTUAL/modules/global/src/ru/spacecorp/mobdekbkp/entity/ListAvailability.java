package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ListAvailability implements EnumClass<String> {

    Present("1"),
    Absent("0");

    private String id;

    ListAvailability(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ListAvailability fromId(String id) {
        for (ListAvailability at : ListAvailability.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}