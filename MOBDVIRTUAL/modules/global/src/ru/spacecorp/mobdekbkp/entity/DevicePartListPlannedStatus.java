package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum DevicePartListPlannedStatus implements EnumClass<String> {

    inDev("inDev"),
    approved("approved"),
    annulled("annulled");

    private String id;

    DevicePartListPlannedStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static DevicePartListPlannedStatus fromId(String id) {
        for (DevicePartListPlannedStatus at : DevicePartListPlannedStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}