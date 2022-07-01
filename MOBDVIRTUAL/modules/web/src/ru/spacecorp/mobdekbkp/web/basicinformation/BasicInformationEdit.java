package ru.spacecorp.mobdekbkp.web.basicinformation;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import ru.spacecorp.mobdekbkp.entity.BasicInformation;

import javax.inject.Inject;
import java.util.Map;

public class BasicInformationEdit extends AbstractEditor<BasicInformation> {

    @Inject
    private FieldGroup fieldGroup;

    @Override
    public void init(Map<String, Object> params) {
        if (params.get("noedit") != null && params.get("noedit").equals(true)) {
            fieldGroup.getField("levelQuality").setVisible(false);
            fieldGroup.getField("levelQualityImport").setVisible(false);
            fieldGroup.getField("in_the_list_0122").setVisible(false);
            fieldGroup.getField("in_the_list_EKB_K").setVisible(false);
        } else {
            fieldGroup.getField("levelQuality").setVisible(false);
            fieldGroup.getField("in_the_list_0122").setVisible(false);
            fieldGroup.getField("in_the_list_EKB_K").setVisible(false);
        }
    }
}