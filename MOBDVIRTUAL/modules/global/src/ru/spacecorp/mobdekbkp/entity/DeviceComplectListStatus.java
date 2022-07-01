package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum DeviceComplectListStatus implements EnumClass<String> {

    inDev("inDev"),
    done("done"),
    inProcess("inProcess"),
    canceled("canceled");

    private String id;

    DeviceComplectListStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static DeviceComplectListStatus fromId(String id) {
        for (DeviceComplectListStatus at : DeviceComplectListStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}