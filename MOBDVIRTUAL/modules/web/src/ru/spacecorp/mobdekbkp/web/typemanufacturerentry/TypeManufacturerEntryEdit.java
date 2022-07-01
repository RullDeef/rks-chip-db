package ru.spacecorp.mobdekbkp.web.typemanufacturerentry;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.gui.data.Datasource;
import ru.spacecorp.mobdekbkp.entity.TypeManufacturerEntry;

import javax.inject.Inject;
import java.util.Map;

public class TypeManufacturerEntryEdit extends AbstractEditor<TypeManufacturerEntry> {

    @Inject
    private FieldGroup fieldGroup;

    @Override
    public void init(Map<String, Object> params) {
        if (params.get("noedit") != null) {
            if (params.get("noedit").equals(true)) {
                fieldGroup.setEditable(false);
            }
        }
    }
}