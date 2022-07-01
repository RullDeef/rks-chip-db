package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ParameterCharacteristicType implements EnumClass<Integer> {

    All(10),
    Main(20),
    Not_main(30);

    private Integer id;

    ParameterCharacteristicType(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static ParameterCharacteristicType fromId(Integer id) {
        for (ParameterCharacteristicType at : ParameterCharacteristicType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}