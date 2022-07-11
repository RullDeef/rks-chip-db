package ru.spacecorp.mobdekbkp.web.outerlistallowing;

import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import ru.spacecorp.mobdekbkp.entity.ExtBoolean;
import ru.spacecorp.mobdekbkp.entity.OuterListAllowing;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

public class OuterListAllowingEdit extends AbstractEditor<OuterListAllowing> {
    @Named("documentTableOuterList.create")
    private CreateAction createBtnDocOuterList;

    @Inject
    private FieldGroup outerListAllowingFieldGroup;

    @Inject
    private TimeSource timeSource;

    @Override
    protected void initNewItem(OuterListAllowing item) {
        item.setIsModification(ExtBoolean.notSet);
    }

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        //валидация данных на экране
        outerListAllowingFieldGroup.getFields().forEach(fieldConfig -> {
            if (fieldConfig.getProperty().contentEquals("releaseYear")) {
                fieldConfig.addValidator(value -> {
                    int year;
                    try {
                        year = Integer.valueOf((String) value);
                    } catch (Exception e) {
                        throw new ValidationException(getMessage("yearFormatError"));
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(timeSource.currentTimeMillis());
                    if (year > calendar.get(Calendar.YEAR)) {
                        throw new ValidationException(getMessage("dateError"));
                    }
                });
            }
            if (fieldConfig.getProperty().contentEquals("endDate")) {
                fieldConfig.addValidator(value -> {
                    if (((Date) value).getTime() <= getItem().getStartDate().getTime()) {
                        throw new ValidationException(getMessage("endDateError"));
                    }
                });
            }
        });
    }
}
