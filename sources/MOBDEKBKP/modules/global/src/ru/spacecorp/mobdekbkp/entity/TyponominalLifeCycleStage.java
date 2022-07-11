package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum TyponominalLifeCycleStage implements EnumClass<String> {

    createNewItem("createNewItem"),
    approvalGnioEkb("approvalGnioEkb"),
    developNewItem("developNewItem"),
    approvalMinpromtorg("approvalMinpromtorg"),
    inOkr("inOkr"),
    notAllowedTillMastering("notAllowedTillMastering"),
    productionButPlanStop("productionButPlanStop"),
    productionNotPlanStop("productionNotPlanStop"),
    productionStopButCanBuy("productionStopButCanBuy"),
    productionStopped("productionStopped");

    private String id;

    TyponominalLifeCycleStage(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TyponominalLifeCycleStage fromId(String id) {
        for (TyponominalLifeCycleStage at : TyponominalLifeCycleStage.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}