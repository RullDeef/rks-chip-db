package ru.spacecorp.mobdekbkp.web.parametervalue;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import ru.spacecorp.mobdekbkp.entity.*;

import javax.inject.Inject;
import java.util.*;

public class ParameterValueEdit extends AbstractEditor<ParameterValue> {
    @Inject
    private DataManager dataManager;
    @Inject
    private Datasource<ParameterValue> parameterValueDs;
    @Inject
    private Metadata metadata;
    @Inject
    private GridLayout parametersGrid;
    @Inject
    private LookupField currentValueTypeField;
    @Inject
    private LookupField paramTypeField;
    @Inject
    private OptionsGroup boolOptionsGroup;
    @Inject
    private RichTextArea parameterValueLabel;
    @Inject
    private Button changeBtn;

    @Inject
    private TextField valFloatField;
    @Inject
    private TextField valStringField;
    @Inject
    private TextField valMinField;
    @Inject
    private TextField valMaxField;
    @Inject
    private TextField toleranceMinusField;
    @Inject
    private TextField tolerancePlusField;
    @Inject
    private TextField gammaField;

    @Inject
    private Label valFloatLbl;
    @Inject
    private Label valMaxLbl;
    @Inject
    private Label valMinLbl;
    @Inject
    private Label valStringLbl;
    @Inject
    private Label toleranceMinusLbl;
    @Inject
    private Label tolerancePlusLbl;
    @Inject
    private Label gammaLbl;

    @Inject
    private Label boolLbl;
    @Inject
    private Button clearBtn;
    @Inject
    private Label currentValueTypeLbl;
    @Inject
    private Label mainFloatLbl;
    @Inject
    private Label mainStringLbl;
    @Inject
    private Label parameterLbl;
    @Inject
    private HBoxLayout parameterValueHbox;
    @Inject
    private Label paramTypeLbl;
    @Inject
    private HBoxLayout toleranceMinusHbox;
    @Inject
    private HBoxLayout toleranceMinusPlusHbox;
    @Inject
    private Label toleranceMinusPlusLbl;
    @Inject
    private HBoxLayout tolerancePlusHbox;
    @Inject
    private TextField typeField;
    @Inject
    private Label typeLbl;
    @Inject
    private HBoxLayout valFloatHbox;
    @Inject
    private HBoxLayout valMaxHbox;
    @Inject
    private HBoxLayout valMinHbox;
    @Inject
    private HBoxLayout valMinMaxHbox;
    @Inject
    private Label valMinMaxLbl;
    @Inject
    private LookupField lookupFieldLib;
    @Inject
    private LookupField lookupFieldRec;
    @Inject
    private Label valStringLbl1;
    @Inject
    private Label valStringLbl2;
    @Inject
    private CollectionDatasource<StrRec, UUID> strRecsDs;
    @Inject
    private CollectionDatasource<StrRecSettings, UUID> strRecSettingsesDs;
    private Type type;
    private HashMap<String, Object> paramMap;

    private boolean isValSet = false;

    @Override
    public void init(Map<String, Object> params) {
        List<String> boolList = new ArrayList<>();
        boolList.add(getMessage("optYes"));
        boolList.add(getMessage("optNo"));
        boolOptionsGroup.setOptionsList(boolList);

        if (params.get("type") != null) {
            type = (Type) params.get("type");
        }

        parameterValueDs.addItemPropertyChangeListener(e -> {
            if (e.getProperty().contentEquals("parameter")) {
                Parameter parameter = getItem().getParameter();
                if (parameter != null) {
                    getItem().setCurrentValueType(parameter.getDefaultValueType());
                }
            }
        });
        lookupFieldRec.setNewOptionAllowed(true);
        lookupFieldRec.setNewOptionHandler(caption -> {
            StrRec str = metadata.create(StrRec.class);
            str.setText(caption);
            dataManager.commit(str);
            strRecSettingsesDs.refresh();
            if (strRecSettingsesDs.getItems().toArray().length != 0) {
                StrRecSettings setRec = (StrRecSettings) strRecSettingsesDs.getItems().toArray()[0];
                setRec.getStrRec().add(str);
                dataManager.commit(setRec);
            } else {
                StrRecSettings setRec = metadata.create(StrRecSettings.class);
                setRec.setTypeClass(parameterValueDs.getItem().getType().getTypeClass());
                List<StrRec> list = new ArrayList();
                list.add(str);
                setRec.setStrRec(list);
                dataManager.commit(setRec);
            }
            lookupFieldRec.setValue(str);
            isValSet = true;
        });

    }

    @Override
    protected void postInit() {
        if (getItem().getType() == null) {
            getItem().setType(type);
        }
        if (getItem().getParameter() != null) {
            if (getItem().getParameter().getParamType() != null) {
                paramTypeField.setValue(getItem().getParameter().getParamType());

                queryParameter();

                valueTypeVisible(currentValueTypeField.getValue());
                currentValueTypeField.setEditable(true);
            }
        }
    }

    private void setFieldValue(Object value, Field... fields) {
        for (int i = 0; i < fields.length; i++) {
            fields[i].setValue(value);
        }
    }

    private void setFieldEditable(boolean e, Field... fields) {
        for (int i = 0; i < fields.length; i++) {
            fields[i].setEditable(e);
        }
    }

    private void setComponentEnabled(boolean e, Component... components) {
        for (int i = 0; i < components.length; i++) {
            components[i].setEnabled(e);
        }
    }

    private void setComponentVisible(boolean visible, Component... components) {
        for (Component key : components) {
//            Component component = parametersGrid.getComponent(key);
//            if (component == null) {
//                String errorLog = String.format("Cannot find row with key %s in grid layout %s", key, parametersGrid.getId());
//                throw new RuntimeException(errorLog);
//            }
            key.setVisible(visible);
        }
    }

    @Override
    public void ready() {
        //слушатели для всех полей определены здесь
        paramTypeField.addValueChangeListener(type -> {
            boolean typeHasValue = type.getValue() != null;
            if (typeHasValue) queryParameter();
            setComponentEnabled(typeHasValue, changeBtn);
            setFieldValue(null, parameterValueLabel, currentValueTypeField);
            setFieldEditable(false, currentValueTypeField);
        });
        parameterValueLabel.addValueChangeListener(param -> {
            boolean paramHasValue = param.getValue() != null;
            setFieldEditable(paramHasValue, currentValueTypeField);
            setFieldValue(null, currentValueTypeField);
        });
        currentValueTypeField.addValueChangeListener(e -> {
            setComponentVisible(false,
                    valFloatLbl, valFloatHbox, valStringLbl, valStringField, valStringLbl1, valStringLbl2, lookupFieldLib, lookupFieldRec,
                    valMinMaxLbl, valMinMaxHbox, toleranceMinusPlusLbl, toleranceMinusPlusHbox,
                    gammaLbl, gammaField, boolLbl, boolOptionsGroup);

            setFieldValue(null,
                    valFloatField, valStringField, valMinField, valMaxField,
                    toleranceMinusField, tolerancePlusField, gammaField, boolOptionsGroup);

            valueTypeVisible(e.getComponent().getValue());
        });
    }


    private void valueTypeVisible(ParameterValueType parameterValueType) {
        if (parameterValueType == null) return;

        switch (parameterValueType) {
            case integer:
                setComponentVisible(true, valFloatLbl, valFloatHbox);
                break;
            case string:
                setComponentVisible(true, valStringLbl, valStringField);
                break;
            case bool:
                setComponentVisible(true, boolLbl, boolOptionsGroup);
                break;
            case interval:
                setComponentVisible(true, valMinMaxLbl, valMinMaxHbox);
                break;
            case normal:
                setComponentVisible(true, valFloatLbl, valFloatHbox);
                break;
            case normalTolerated:
                setComponentVisible(true, valFloatLbl, valFloatHbox, toleranceMinusPlusLbl, toleranceMinusPlusHbox);
                break;
            case normalToleratedPrecent:
                setComponentVisible(true, valFloatLbl, valFloatHbox, toleranceMinusPlusLbl, toleranceMinusPlusHbox);
                break;
            case gammaProbabilityValue:
                setComponentVisible(true, valFloatLbl, valFloatHbox, gammaLbl, gammaField);
                break;
            case stringLib:
                setComponentVisible(true, valStringLbl2, lookupFieldLib);
                break;
            case stringRec:
                setComponentVisible(true, valStringLbl1, lookupFieldRec);
                break;

        }
    }

    private void queryParameter() {
        TypeClass typeClassChild = getItem().getType().getTypeClass();
        List<Parameter> parameterList = new ArrayList<>();

        List<Parameter> parameterInValue = new LinkedList<>();
        for (ParameterValue parameterValue : getItem().getType().getParamValue()) {
            parameterInValue.add(parameterValue.getParameter());
        }
        if (paramTypeField.getValue() != null) {
            while (typeClassChild != null) {
                for (TypeClassCharacteristic characteristic : typeClassChild.getCharacteristics())
                    if ((paramTypeField.getValue().equals(characteristic.getParameter().getParamType())) &&
                            (parameterInValue.indexOf(characteristic.getParameter()) < 0))
                        parameterList.add(characteristic.getParameter());
                typeClassChild = (typeClassChild.getParent() != null)
                        ? dataManager.reload(typeClassChild.getParent(), "typeClass-view")
                        : null;
            }
            for (int i = 0; i < parameterList.size(); ++i) {
                Parameter parameter = parameterList.get(i);
                parameter = dataManager.reload(parameter, "parameter-view");
                parameterList.set(i, parameter);
            }
            paramMap = new HashMap<>();
            paramMap.put("values", parameterList);
        } else {
            paramMap = null;
        }
    }


    @Override
    protected boolean preCommit() {
        Boolean comm = super.preCommit();

        getItem().setCurrentValueType(currentValueTypeField.getValue());

        if (getItem().getCurrentValueType() == ParameterValueType.bool) {
            if (boolOptionsGroup.getValue().equals("да")) {
                getItem().setValString("true");
            } else if (boolOptionsGroup.getValue().equals("нет")) {
                getItem().setValString("false");
            }
        }

        if (getItem().getCurrentValueType() != null) {
            if (getItem().getValFloat() != null || getItem().getValString() != null || (getItem().getValMin() != null && getItem().getValMax() != null || lookupFieldRec.getValue() != null)) {
                comm = true;
            } else {
                showNotification(messages.getMessage("ru.spacecorp.mobdekbkp.web.parametervalue", "enterParameterValue"), NotificationType.WARNING);
                comm = false;
            }
        } else {
            showNotification(messages.getMessage("ru.spacecorp.mobdekbkp.web.parametervalue", "enterParameterType"), NotificationType.WARNING);
            comm = false;
        }

        return comm;
    }

    public void onChangeBtnClick() {
        openLookup("mobdekbkp$Parameter.browse", items -> {
            if (!items.isEmpty()) {
                Parameter parameter = (Parameter) items.iterator().next();
                getItem().setParameter(parameter);
            }
        }, WindowManager.OpenType.DIALOG, paramMap);
    }

    public void onClearBtnClick() {
        getItem().setParameter(null);
    }

}