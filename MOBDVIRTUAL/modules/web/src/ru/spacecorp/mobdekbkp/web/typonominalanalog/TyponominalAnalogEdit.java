package ru.spacecorp.mobdekbkp.web.typonominalanalog;

import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.Datasource;
import ru.spacecorp.mobdekbkp.entity.TyponominalAnalog;

import javax.inject.Inject;
import java.util.Map;

public class TyponominalAnalogEdit extends AbstractEditor<TyponominalAnalog> {

    @Inject
    private FieldGroup fieldGroup;

    @Inject
    private Datasource<TyponominalAnalog> typonominalAnalogDs;


    @Override
    public void init(Map<String, Object> params) {
        if (params.get("noedit") != null && params.get("noedit").equals(true)) {
            fieldGroup.getField("parentTyponominal").setVisible(false);
        }
    }
}