package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum OrganizationSMKCertification implements EnumClass<String> {

    VP("01"),
    OS("02"),
    OSM("03"),
    OSD("04"),
    VPOS("05"),
    VPOSM("06"),
    VPOSD("07"),
    VPOSOSM("08");

    private String id;

    OrganizationSMKCertification(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static OrganizationSMKCertification fromId(String id) {
        for (OrganizationSMKCertification at : OrganizationSMKCertification.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}