package ru.spacecorp.mobdekbkp.web.factory;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import ru.spacecorp.mobdekbkp.entity.Factory;

import javax.inject.Inject;
import java.util.Map;

public class FactoryEdit extends AbstractEditor<Factory> {

    @Inject
    private FieldGroup fieldGroup;

    @Override
    public void init(Map<String, Object> params) {
        if (params.get("provider") != null && params.get("provider").equals(true)) {
            fieldGroup.getField("producer").setVisible(false);
            fieldGroup.getField("producingCountry").setVisible(false);
        }
    }
}