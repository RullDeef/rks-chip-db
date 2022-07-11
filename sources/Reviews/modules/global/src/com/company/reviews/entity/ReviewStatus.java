package com.company.reviews.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ReviewStatus implements EnumClass<String> {

    premoderation("premoderation"),
    accepted("accepted"),
    declined("declined"),
    moderated("moderated"),
    hidden("hidden");

    private String id;

    ReviewStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ReviewStatus fromId(String id) {
        for (ReviewStatus at : ReviewStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}