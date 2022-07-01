package ru.spacecorp.mobdekbkp.web.parameter;

import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Filter;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.Parameter;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class ParameterBrowse extends AbstractLookup {

    @Inject
    private Filter filter;
    @Inject
    private ParameterDatasource parametersDs;
    @Inject
    private GroupTable<Parameter> parametersTable;

    @Inject
    private ComponentsFactory componentsFactory;

    @Inject
    private Messages messages;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        filter.setMaxResults(0);
        if (params.get("values") != null) {
            List<Parameter> parameterList = (List) params.get("values");
            parametersDs.initParams(parameterList);
            parametersDs.refresh();
        }
        initParametersTable();

    }

    private void initParametersTable() {
        parametersTable.addGeneratedColumn("labelName", entity -> {
            Label labelValue = componentsFactory.createComponent(Label.class);
            labelValue.setHtmlEnabled(true);
            labelValue.setValue(entity.getName());
            return labelValue;
        });

        parametersTable.addGeneratedColumn("labelUnit", entity -> {
            Label labelValue = componentsFactory.createComponent(Label.class);
            labelValue.setHtmlEnabled(true);
            labelValue.setValue(entity.getUnit());
            return labelValue;
        });


        parametersTable.setColumnCaption(
                "labelName",
                messages.getMessage("ru.spacecorp.mobdekbkp.entity", "Parameter.name")
        );
        parametersTable.setColumnCaption(
                "labelUnit",
                messages.getMessage("ru.spacecorp.mobdekbkp.entity", "UnitVal.shortName")
        );

    }


}