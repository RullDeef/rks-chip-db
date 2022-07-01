package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum NationalLevelQuality implements EnumClass<String> {
    VP("01"),
    OS("02"),
    OSM("03"),
    OSD("04"),
    N("05");

    private String id;

    NationalLevelQuality(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static NationalLevelQuality fromId(String id) {
        for (NationalLevelQuality at : NationalLevelQuality.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}