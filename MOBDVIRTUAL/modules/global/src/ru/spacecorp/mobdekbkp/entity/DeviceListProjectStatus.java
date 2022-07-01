package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum DeviceListProjectStatus implements EnumClass<String> {

    inDev("inDev"),
    partsApproved("partsApproved"),
    onApproval("onApproval"),
    approved("approved"),
    readyForUse("readyForUse"),
    annulled("annulled");

    private String id;

    DeviceListProjectStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static DeviceListProjectStatus fromId(String id) {
        for (DeviceListProjectStatus at : DeviceListProjectStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}