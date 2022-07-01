package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum OuterReceivedEntryStatus implements EnumClass<String> {

    notProcessed("notProcessed"),
    requiredFieldsMissing("requiredFieldsMissing"),
    incorrectValueFields("incorrectValueFields"),
    duplicateEntry("duplicateEntry"),
    processedCorrectly("processedCorrectly");

    private String id;

    OuterReceivedEntryStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static OuterReceivedEntryStatus fromId(String id) {
        for (OuterReceivedEntryStatus at : OuterReceivedEntryStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}