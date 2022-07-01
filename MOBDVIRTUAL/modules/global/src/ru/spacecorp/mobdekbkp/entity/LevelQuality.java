package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum LevelQuality implements EnumClass<String> {

    industrial("01"),
    hiRel("02"),
    military("03"),
    space("04");

    private String id;

    LevelQuality(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static LevelQuality fromId(String id) {
        for (LevelQuality at : LevelQuality.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}