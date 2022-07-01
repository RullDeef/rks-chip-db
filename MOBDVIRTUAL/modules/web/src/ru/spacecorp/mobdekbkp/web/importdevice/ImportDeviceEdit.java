package ru.spacecorp.mobdekbkp.web.importdevice;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import ru.spacecorp.mobdekbkp.entity.ImportDevice;

import javax.inject.Inject;
import java.util.Map;

public class ImportDeviceEdit extends AbstractEditor<ImportDevice> {

    @Inject
    private FieldGroup fieldGroup;

    @Override
    public void init(Map<String, Object> params) {
        if (params.get("noedit") != null && params.get("noedit").equals(true)) {
            fieldGroup.getField("importClass").setVisible(false);
        }
    }
}