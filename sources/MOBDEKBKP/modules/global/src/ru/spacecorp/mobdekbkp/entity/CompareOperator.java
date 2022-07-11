package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum CompareOperator implements EnumClass<Integer> {

    less(10),
    lessequal(20),
    equal(30),
    notequal(40),
    moreequal(50),
    more(60),
    contain(70),
    notConain(80);

    private Integer id;

    CompareOperator(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static CompareOperator fromId(Integer id) {
        for (CompareOperator at : CompareOperator.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}