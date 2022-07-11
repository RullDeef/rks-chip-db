package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum DevicePartListComplectStatus implements EnumClass<String> {

    inDev("inDev"),
    inProcess("inProcess"),
    done("done"),
    canceled("canceled");

    private String id;

    DevicePartListComplectStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static DevicePartListComplectStatus fromId(String id) {
        for (DevicePartListComplectStatus at : DevicePartListComplectStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}