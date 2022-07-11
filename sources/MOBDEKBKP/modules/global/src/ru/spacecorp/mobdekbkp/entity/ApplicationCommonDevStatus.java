package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ApplicationCommonDevStatus implements EnumClass<String> {

    workGnio("workGnio"),
    approvalMpt("approvalMpt"),
    workMpt("workMpt"),
    closed("closed"),
    declined("declined");

    private String id;

    ApplicationCommonDevStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ApplicationCommonDevStatus fromId(String id) {
        for (ApplicationCommonDevStatus at : ApplicationCommonDevStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}