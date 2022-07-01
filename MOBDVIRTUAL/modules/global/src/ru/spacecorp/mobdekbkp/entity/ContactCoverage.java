package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ContactCoverage implements EnumClass<String> {

    no("01"),
    tinTtinning("02"),
    gold("03"),
    goldOrTin("04"),
    silver("05"),
    nickel("06"),
    electroplating("07");

    private String id;

    ContactCoverage(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ContactCoverage fromId(String id) {
        for (ContactCoverage at : ContactCoverage.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}