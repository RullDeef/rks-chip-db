package ru.spacecorp.mobdekbkp.web.typeclasscharacteristic;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.TypeClassCharacteristic;

import javax.inject.Inject;
import java.util.Map;

public class TypeClassCharacteristicBrowse extends AbstractLookup {

    @Inject
    private GroupTable<TypeClassCharacteristic> typeClassCharacteristicsTable;

    @Inject
    private ComponentsFactory componentsFactory;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        initTypeClassCharacteristicsTable();
    }

    private void initTypeClassCharacteristicsTable() {
        typeClassCharacteristicsTable.addGeneratedColumn("labelParameter", entity -> {
            Label labelValue = componentsFactory.createComponent(Label.class);
            labelValue.setHtmlEnabled(true);
            labelValue.setValue(entity.getParameter().getValue("name"));
            return  labelValue;
        });

        typeClassCharacteristicsTable.setColumnCaption("labelParameter", messages.getMessage(getClass(), "paramsName"));
    }
}