package ru.spacecorp.mobdekbkp.web.unitval;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.UnitVal;

import javax.inject.Inject;
import java.util.Map;

public class UnitValBrowse extends AbstractLookup {

    @Inject
    private GroupTable<UnitVal> unitValsTable;
    @Inject
    private ComponentsFactory componentsFactory;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        unitValsTable();
    }

    private void unitValsTable() {
        unitValsTable.addGeneratedColumn("labelUnitShortName", entity -> {
            Label labelValue = componentsFactory.createComponent(Label.class);
            labelValue.setWidth("100%");
            labelValue.setHtmlEnabled(true);
            labelValue.setValue(entity.getShortName());
            return labelValue;
        });

        unitValsTable.setColumnCaption("labelUnitShortName", messages.getMessage("ru.spacecorp.mobdekbkp.entity", "UnitVal.shortName"));

    }
}