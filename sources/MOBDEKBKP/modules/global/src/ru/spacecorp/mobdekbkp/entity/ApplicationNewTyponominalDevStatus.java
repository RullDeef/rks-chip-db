package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ApplicationNewTyponominalDevStatus implements EnumClass<String> {

    inDev("inDev"),
    ready("ready"),
    isInCommon("isInCommon"),
    mptApproval("mptApproval"),
    inOkr("inOkr"),
    declined("declined"),
    closed("closed");

    private String id;

    ApplicationNewTyponominalDevStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ApplicationNewTyponominalDevStatus fromId(String id) {
        for (ApplicationNewTyponominalDevStatus at : ApplicationNewTyponominalDevStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}