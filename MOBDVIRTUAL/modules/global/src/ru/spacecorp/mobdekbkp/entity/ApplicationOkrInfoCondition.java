package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ApplicationOkrInfoCondition implements EnumClass<String> {

    inDev("inDev"),
    done("done"),
    cancelled("cancelled");

    private String id;

    ApplicationOkrInfoCondition(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ApplicationOkrInfoCondition fromId(String id) {
        for (ApplicationOkrInfoCondition at : ApplicationOkrInfoCondition.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}