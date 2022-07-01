package ru.spacecorp.mobdekbkp.service;


import com.haulmont.cuba.security.entity.FilterEntity;
import ru.spacecorp.mobdekbkp.entity.Type;
import ru.spacecorp.mobdekbkp.entity.TypeClass;

public interface TyponominalService {
    String NAME = "mobdekbkp_TyponominalService";

    FilterEntity getFilterTypeClass(TypeClass typeClass);

    FilterEntity getFilterType(Type type);

}