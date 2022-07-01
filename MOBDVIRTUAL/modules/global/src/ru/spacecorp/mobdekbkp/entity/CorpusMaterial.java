package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum CorpusMaterial implements EnumClass<String> {

    ceramic("01"),
    metal("02"),
    metalCeramic("03"),
    glass("04"),
    metalGlass("05"),
    metalPlastic("06"),
    metalGlassCeramic("07"),
    plastic("08");

    private String id;

    CorpusMaterial(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CorpusMaterial fromId(String id) {
        for (CorpusMaterial at : CorpusMaterial.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}