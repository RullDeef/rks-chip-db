package ru.spacecorp.mobdekbkp.web.parametersforpurchasing;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import ru.spacecorp.mobdekbkp.entity.ParametersForPurchasing;

import javax.inject.Inject;
import java.util.Map;

public class ParametersForPurchasingEdit extends AbstractEditor<ParametersForPurchasing> {

    @Inject
    private FieldGroup fieldGroup;

    @Override
    public void init(Map<String, Object> params) {
        if (params.get("productPrice") != null && params.get("productPrice").equals(true)) {
            fieldGroup.getField("deliveryTerm").setVisible(false);
            fieldGroup.getField("deliveryTime").setVisible(false);
            fieldGroup.getField("statusInProduction").setVisible(false);
        } else if (params.get("deliveryTerm") != null && params.get("deliveryTerm").equals(true)) {
            fieldGroup.getField("productPrice").setVisible(false);
            fieldGroup.getField("deliveryTime").setVisible(false);
            fieldGroup.getField("statusInProduction").setVisible(false);
        }
    }
}