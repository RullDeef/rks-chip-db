package ru.spacecorp.mobdekbkp.web.reliabilityIndicators;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import ru.spacecorp.mobdekbkp.entity.ReliabilityIndicators;

import javax.inject.Inject;
import java.util.Map;

public class ReliabilityIndicatorsEdit extends AbstractEditor<ReliabilityIndicators> {

    @Inject
    private FieldGroup fieldGroup;

    @Override
    public void init(Map<String, Object> params) {
        fieldGroup.getField("reliabilityIndicator").setVisible(false);
        fieldGroup.getField("retentionRate").setVisible(false);
        if (params.get("gammaPercentOperatingMaintenance") != null && params.get("gammaPercentOperatingMaintenance").equals(true)) {
            fieldGroup.getField("gammaPercentOperatingLight").setVisible(false);
            fieldGroup.getField("gammapercentStorageabilityTime").setVisible(false);
        } else if (params.get("gammaPercentOperatingLight") != null && params.get("gammaPercentOperatingLight").equals(true)) {
            fieldGroup.getField("gammaPercentOperatingMaintenance").setVisible(false);
            fieldGroup.getField("gammapercentStorageabilityTime").setVisible(false);
        } else {
            fieldGroup.getField("gammaPercentOperatingMaintenance").setVisible(false);
            fieldGroup.getField("gammaPercentOperatingLight").setVisible(false);
        }
    }
}