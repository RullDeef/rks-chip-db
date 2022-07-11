package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ApplicationNewTyponominalAddStatus implements EnumClass<String> {

    created("created"),
    inGnio("inGnio"),
    onEdit("onEdit"),
    onApproval("onApproval"),
    accepted("accepted"),
    declined("declined");

    private String id;

    ApplicationNewTyponominalAddStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ApplicationNewTyponominalAddStatus fromId(String id) {
        for (ApplicationNewTyponominalAddStatus at : ApplicationNewTyponominalAddStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}