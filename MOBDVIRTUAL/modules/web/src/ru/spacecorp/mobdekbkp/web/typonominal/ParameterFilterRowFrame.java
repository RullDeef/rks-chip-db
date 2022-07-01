package ru.spacecorp.mobdekbkp.web.typonominal;

import com.haulmont.cuba.core.global.filter.Op;
import com.haulmont.cuba.gui.components.AbstractFrame;
import com.haulmont.cuba.gui.components.HBoxLayout;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import ru.spacecorp.mobdekbkp.entity.Parameter;
import ru.spacecorp.mobdekbkp.entity.ParameterFilterRow;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ParameterFilterRowFrame extends AbstractFrame {
    @Inject
    private Datasource<ParameterFilterRow> parameterFilterRowDs;
    @Inject
    private CollectionDatasource<Parameter, UUID> parametersDs;
    @Inject
    protected HBoxLayout mainBox;
    @Inject
    private LookupField operationLookup;
    @Inject
    private HBoxLayout valFloatHbox;
    @Inject
    private HBoxLayout valStringHbox;


    private static final List<Op> OPERATIONS = Arrays.asList(
            Op.EQUAL,
            Op.NOT_EQUAL,
            Op.GREATER,
            Op.GREATER_OR_EQUAL,
            Op.LESSER,
            Op.LESSER_OR_EQUAL,
            Op.CONTAINS,
            Op.DOES_NOT_CONTAIN);
//            Op.DATE_INTERVAL
//            Op.STARTS_WITH,
//            Op.ENDS_WITH,

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        operationLookup.setOptionsList(OPERATIONS);

        operationLookup.addValueChangeListener(e -> {
            getItem().setOperationType((Op) e.getValue());
        });
        parametersDs.refresh();

        parameterFilterRowDs.addItemPropertyChangeListener(e -> {
            if ("parameterType".equals(e.getProperty())) {
                parametersDs.refresh();
            }
            if ("operationType".equals(e.getProperty())) {
                Op operationType = e.getItem().getOperationTypeAsEnum();
                switch (operationType) {
                    case CONTAINS:
                    case DOES_NOT_CONTAIN:
                        valStringHbox.setVisible(true);
                        valFloatHbox.setVisible(false);
                        e.getItem().setValFloat(null);
                        break;
                    default:
                        valFloatHbox.setVisible(true);
                        valStringHbox.setVisible(false);
                        e.getItem().setValString(null);
                }
            }
        });
    }

    public void setItem(ParameterFilterRow parameterFilterRow) {
        parameterFilterRowDs.setItem(parameterFilterRow);
    }

    public ParameterFilterRow getItem() {
        return parameterFilterRowDs.getItem();
    }

    public void clearParameterFilter() {
        parameterFilterRowDs.setItem(null);
        this.setVisible(false);
    }


}