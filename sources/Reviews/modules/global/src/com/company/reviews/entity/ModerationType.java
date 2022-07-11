package com.company.reviews.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ModerationType implements EnumClass<String> {

    premoderation("premoderation"),
    postmoderation("postmoderation"),
    allTypes("allTypes");

    private String id;

    ModerationType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ModerationType fromId(String id) {
        for (ModerationType at : ModerationType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}