package ru.spacecorp.mobdekbkp.web.advancedsetting;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import ru.spacecorp.mobdekbkp.entity.AdvancedSetting

import javax.inject.Inject

public class AdvancedSettingEdit extends AbstractEditor<AdvancedSetting> {

    @Inject
    private FieldGroup fieldGroup;

    @Override
    public void init(Map<String, Object> params) {
        fieldGroup.getField("shelfLife").setVisible(false);
        fieldGroup.getField("tightness").setVisible(false);
        if (params.get("numberPRP") != null && params.get("numberPRP").equals(true)) {
            fieldGroup.getField("technicalSpecificationName").setVisible(false);
            fieldGroup.getField("technicalSpecificationValue").setVisible(false);
            fieldGroup.getField("mass").setVisible(false);
            fieldGroup.getField("workingLife").setVisible(false);
            fieldGroup.getField("technology").setVisible(false);
        } else if (params.get("technicalSpecificationName") != null && params.get("technicalSpecificationName").equals(true)) {
            fieldGroup.getField("numberPRP").setVisible(false);
            fieldGroup.getField("technicalSpecificationValue").setVisible(false);
            fieldGroup.getField("mass").setVisible(false);
            fieldGroup.getField("workingLife").setVisible(false);
            fieldGroup.getField("technology").setVisible(false);
        } else if (params.get("technicalSpecificationValue") != null && params.get("technicalSpecificationValue").equals(true)) {
            fieldGroup.getField("numberPRP").setVisible(false);
            fieldGroup.getField("technicalSpecificationName").setVisible(false);
            fieldGroup.getField("mass").setVisible(false);
            fieldGroup.getField("workingLife").setVisible(false);
            fieldGroup.getField("technology").setVisible(false);
        } else {
            fieldGroup.getField("numberPRP").setVisible(false);
            fieldGroup.getField("technicalSpecificationName").setVisible(false);
            fieldGroup.getField("technicalSpecificationValue").setVisible(false);
        }
    }
}