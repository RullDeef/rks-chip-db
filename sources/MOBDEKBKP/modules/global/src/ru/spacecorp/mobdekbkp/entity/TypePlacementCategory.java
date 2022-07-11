package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum TypePlacementCategory implements EnumClass<String> {

    one("one"),
    two("two"),
    three("three"),
    four("four"),
    five("five"),
    notSet("notSet");

    private String id;

    TypePlacementCategory(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TypePlacementCategory fromId(String id) {
        for (TypePlacementCategory at : TypePlacementCategory.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}