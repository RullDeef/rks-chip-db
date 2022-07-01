package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum CompanyNeedStatus implements EnumClass<Integer> {

    created(10),
    approved(20),
    rejected(30),
    rejectedByGnio(40),
    rejectedAfterApproveByGnio(50);

    private Integer id;

    CompanyNeedStatus(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static CompanyNeedStatus fromId(Integer id) {
        for (CompanyNeedStatus at : CompanyNeedStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}