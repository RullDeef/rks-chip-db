package ru.spacecorp.mobdekbkp.service;

import com.haulmont.cuba.security.entity.FilterEntity;
import org.springframework.stereotype.Service;
import ru.spacecorp.mobdekbkp.core.FiltersCreator;
import ru.spacecorp.mobdekbkp.entity.Type;
import ru.spacecorp.mobdekbkp.entity.TypeClass;

import javax.inject.Inject;

@Service(TyponominalService.NAME)
public class TyponominalServiceBean implements TyponominalService {
    @Inject
    private FiltersCreator filtersCreator;

    @Override
    public FilterEntity getFilterTypeClass(TypeClass typeClass) {
        return filtersCreator.getFilterTypeClass(typeClass);
    }

    @Override
    public FilterEntity getFilterType(Type type) {
        return filtersCreator.getFilterTypes(type);
    }
}