package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum DistinctiveSign implements EnumClass<String> {

    A("01"),
    G("02"),
    PR("03"),
    NP("04"),
    OZ("05"),
    star("06"),
    Z("07"),
    AG("08");

    private String id;

    DistinctiveSign(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static DistinctiveSign fromId(String id) {
        for (DistinctiveSign at : DistinctiveSign.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}