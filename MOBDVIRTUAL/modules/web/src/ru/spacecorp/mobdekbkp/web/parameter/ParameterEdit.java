package ru.spacecorp.mobdekbkp.web.parameter;

import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.ValidationErrors;
import ru.spacecorp.mobdekbkp.entity.Parameter;
import ru.spacecorp.mobdekbkp.entity.UnitVal;

import javax.inject.Inject;

public class ParameterEdit extends AbstractEditor<Parameter> {

    @Inject
    private Messages messages;

    @Override
    protected void postValidate(ValidationErrors errors) {
        if (getItem().getUnit() == null)
            errors.add(messages.getMessage(getClass(), "unitIsEmpty"));
    }


    public void openLookupUnitVal() {
        openLookup("mobdekbkp$UnitVal.browse", items -> {
            if (!items.isEmpty())
                getItem().setUnit((UnitVal) items.iterator().next());
        }, WindowManager.OpenType.DIALOG);
    }

    public void onUnitValClearBtnClick() {
        getItem().setUnit(null);
    }
}