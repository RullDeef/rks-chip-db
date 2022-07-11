package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum DeviceListProjectAdditionType implements EnumClass<String> {

    add("add"),
    edit("edit"),
    remove("remove");

    private String id;

    DeviceListProjectAdditionType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static DeviceListProjectAdditionType fromId(String id) {
        for (DeviceListProjectAdditionType at : DeviceListProjectAdditionType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}