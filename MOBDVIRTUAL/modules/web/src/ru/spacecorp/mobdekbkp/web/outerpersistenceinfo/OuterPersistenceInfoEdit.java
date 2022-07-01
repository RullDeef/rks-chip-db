package ru.spacecorp.mobdekbkp.web.outerpersistenceinfo;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import ru.spacecorp.mobdekbkp.entity.OuterPersistenceInfo;

import javax.inject.Inject;
import java.util.Map;

public class OuterPersistenceInfoEdit extends AbstractEditor<OuterPersistenceInfo> {

    @Inject
    private FieldGroup fieldGroup;

    @Override
    public void init(Map<String, Object> params) {
        if (params.get("noedit") != null && params.get("noedit").equals(true)) {
            fieldGroup.getField("resistanceInfluenceTZC").setVisible(false);
            fieldGroup.getField("resistanceDose").setVisible(false);
        } else {
            fieldGroup.getField("manufacturer").setVisible(false);
            fieldGroup.getField("infoSource").setVisible(false);
            fieldGroup.getField("singleEffectData").setVisible(false);
            fieldGroup.getField("doseEffectData").setVisible(false);
            fieldGroup.getField("hasFiles").setVisible(false);
        }
    }
}