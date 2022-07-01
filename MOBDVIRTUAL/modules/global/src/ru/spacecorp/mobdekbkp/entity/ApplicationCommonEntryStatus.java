package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ApplicationCommonEntryStatus implements EnumClass<String> {

    inDev("inDev"),
    onApproval("onApproval"),
    inOkr("inOkr"),
    declined("declined"),
    closed("closed");

    private String id;

    ApplicationCommonEntryStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ApplicationCommonEntryStatus fromId(String id) {
        for (ApplicationCommonEntryStatus at : ApplicationCommonEntryStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}