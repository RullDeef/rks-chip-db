package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum DeviceListEntryStatus implements EnumClass<String> {

    inDev("inDev"),
    onApprove("onApprove"),
    edit("edit"),
    approved("approved"),
    annulled("annulled"),
    inProcess("inProcess");

    private String id;

    DeviceListEntryStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static DeviceListEntryStatus fromId(String id) {
        for (DeviceListEntryStatus at : DeviceListEntryStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}