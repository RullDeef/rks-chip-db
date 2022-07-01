package ru.spacecorp.mobdekbkp.web.outerdbrefuse;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import ru.spacecorp.mobdekbkp.entity.OuterDbRefuse;

import javax.inject.Inject;
import java.util.Map;

public class OuterDbRefuseEdit extends AbstractEditor<OuterDbRefuse> {

    @Inject
    private FieldGroup fieldGroup;

    @Override
    public void init(Map<String, Object> params) {
        if (params.get("noedit") != null && params.get("noedit").equals(true)) {
            fieldGroup.getField("provider").setVisible(false);
        }
    }
}