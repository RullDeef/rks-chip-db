package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.*;

import javax.inject.Inject;
import java.util.*;

public class Tnparameters extends AbstractFrame implements TyponominalFrame {

    @Inject
    private LookupField lfParamType;
    @Inject
    private LookupField lfCharacteristics;
    @Inject
    private ScrollBoxLayout vbMain;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private DataManager dataManager;

    private Typonominal tn;
    private Type type;

    HashSet<ParameterCategory> categorySet;
    private ArrayList<GroupBoxLayout> groupBoxLayouts;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        Map<String, Object> listCharacteristic = new HashMap<>();
        listCharacteristic.put(getMessage("allParams"), false);
        listCharacteristic.put(getMessage("mainParams"), true);
        lfCharacteristics.setOptionsMap(listCharacteristic);
        lfCharacteristics.setValue(false);
    }

    @Override
    public void clearFrame(String labelValue) {
        vbMain.removeAll();
        Label noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        vbMain.add(noDsLabel);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null) throw new RuntimeException("Unexpectedly typonominal in TnParameters frame is null");
        this.tn = tn;
        type = tn.getType();
        if (type == null) {
            LoadContext<Type> ctx = LoadContext.create(Type.class);
            ctx.setView("type-view");
            ctx.setQuery(LoadContext.createQuery("select tp from mobdekbkp$Typonominal tn join tn.type tp where tn.id = :tnId")
                    .setParameter("tnId", tn.getId()));
            type = dataManager.load(ctx);
        }


        basicSetup();
    }

    private void basicSetup() {
        lfCharacteristics.addValueChangeListener(eIsMain -> {
            vbMain.removeAll();
            screenParameter(type);
        });

        lfParamType.addValueChangeListener(eTypeParam -> {
            vbMain.removeAll();
            screenParameter(type);
        });

        vbMain.removeAll();
        screenParameter(type);
    }

    private void screenParameter(Type type) {
        boolean isAll = lfCharacteristics.getValue().toString().equals("false");

        if (type.getParamValue().size() > 0) {
            List<ParameterValue> parameterValueCollection = type.getParamValue();
            List<TypeClassCharacteristic> characteristicCollection = type.getTypeClass().getCharacteristics();
            List<ParameterValue> conditionOperationValue = new ArrayList<>();

            categorySet = new HashSet<>();
            groupBoxLayouts = null;

            if (isAll) {
                //todo change to fori
                for (ParameterValue parameterValue : parameterValueCollection) {
                    if (parameterValue.getOperationCondition() == null) {
                        if (lfParamType.getValue() == null || parameterValue.getParameter().getParamType() == lfParamType.getValue()) {

                            //создание GroupBox если еще не создана для категории
                            genGroupBox(parameterValue);

                            screenParameterDisplay(parameterValue);
                        }
                    } else conditionOperationValue.add(parameterValue);
                }
            } else if (!isAll) {
                //todo change to fori
                for (TypeClassCharacteristic classCharacteristic : characteristicCollection) {
                    if (classCharacteristic.getIsMain().toString().equals("true")) {
                        for (ParameterValue parameterValue : parameterValueCollection) {
                            if (parameterValue.getOperationCondition() == null) {
                                if ((lfParamType.getValue() == null || parameterValue.getParameter().getParamType() == lfParamType.getValue())
                                        && (parameterValue.getParameter().getId().toString().equals(classCharacteristic.getParameter().getId().toString()))) {

                                    //создание GroupBox если еще не создана для категории
                                    genGroupBox(parameterValue);

                                    screenParameterDisplay(parameterValue);
                                }
                            } else conditionOperationValue.add(parameterValue);
                        }
                    }
                }
            }

            conditionOperationValue.sort(new Comparator<ParameterValue>() {
                @Override
                public int compare(ParameterValue o1, ParameterValue o2) {
                    return o1.getOperationCondition().getName().compareTo(o2.getOperationCondition().getName());
                }
            });
            OperationConditions operationConditionsValuePrevious = null;
            for (ParameterValue parameterValue : conditionOperationValue) {
                if (parameterValue.getOperationCondition() == operationConditionsValuePrevious) {
                    screenParameterDisplay(parameterValue);
                } else {
                    Label parameterLbl = componentsFactory.createComponent(Label.class);
                    parameterLbl.setValue(parameterValue.getOperationCondition().getName());
                    parameterLbl.setAlignment(Alignment.MIDDLE_CENTER);
                    vbMain.add(parameterLbl);

                    screenParameterDisplay(parameterValue);
                }
                operationConditionsValuePrevious = parameterValue.getOperationCondition();
            }
        }

    }

    private void screenParameterDisplay(ParameterValue parameterValue) {
        HBoxLayout hBoxContainer = componentsFactory.createComponent(HBoxLayout.class);
        hBoxContainer.setId("hBoxContainerID" + vbMain.getId());
        hBoxContainer.setSpacing(true);
        hBoxContainer.setWidth("100%");

        Label parameterLbl = componentsFactory.createComponent(Label.class);
        parameterLbl.setId("parameterLblID" + hBoxContainer.getId());
        parameterLbl.setValue(parameterValue.getParameter().getName() + ": ");
        parameterLbl.setAlignment(Alignment.MIDDLE_LEFT);
        parameterLbl.setWidth("90%");
        parameterLbl.setHtmlEnabled(true);
        hBoxContainer.add(parameterLbl);

        RichTextArea parameterField = componentsFactory.createComponent(RichTextArea.class);
        parameterField.setId("parameterFieldID" + hBoxContainer.getId());
        parameterField.setWidth("100%");
        parameterField.setEditable(false);
        hBoxContainer.add(parameterField);

        if (parameterValue.getCurrentValueType() == ParameterValueType.integer) {
            parameterField.setValue(String.format("%d", parameterValue.getValFloat().intValue()) + " " + parameterValue.getParameter().getUnit().getShortName());
        }

        if (parameterValue.getCurrentValueType() == ParameterValueType.normal) {
            parameterField.setValue(fractionalTest(parameterValue.getValFloat()) + " " + parameterValue.getParameter().getUnit().getShortName());
        }

        if (parameterValue.getCurrentValueType() == ParameterValueType.normalTolerated) {
            if (Math.abs(parameterValue.getToleranceMinus()) == Math.abs(parameterValue.getTolerancePlus())) {
                parameterField.setValue(fractionalTest(parameterValue.getValFloat()) + " \u00B1" + fractionalTest(parameterValue.getToleranceMinus()) + " " + parameterValue.getParameter().getUnit().getShortName());
            } else {
                parameterField.setValue(fractionalTest(parameterValue.getValFloat()) + " -" + fractionalTest(parameterValue.getToleranceMinus()) + " +" + fractionalTest(parameterValue.getTolerancePlus()) + " " + parameterValue.getParameter().getUnit().getShortName());
            }
        }

        if (parameterValue.getCurrentValueType() == ParameterValueType.normalToleratedPrecent) {
            if (Math.abs(parameterValue.getToleranceMinus()) == Math.abs(parameterValue.getTolerancePlus())) {
                parameterField.setValue(fractionalTest(parameterValue.getValFloat()) + " " + parameterValue.getParameter().getUnit().getShortName() + " \u00B1" + fractionalTest(parameterValue.getToleranceMinus()) + "%");
            } else {
                parameterField.setValue(fractionalTest(parameterValue.getValFloat()) + " " + parameterValue.getParameter().getUnit().getShortName() + " -" + fractionalTest(parameterValue.getToleranceMinus()) + " +" + fractionalTest(parameterValue.getTolerancePlus()) + "%");
            }
        }

        if (parameterValue.getCurrentValueType() == ParameterValueType.interval) {
            parameterField.setValue("От " + fractionalTest(parameterValue.getValMin()) + " до " + fractionalTest(parameterValue.getValMax()) + " " + parameterValue.getParameter().getUnit().getShortName());
        }

        if (parameterValue.getCurrentValueType() == ParameterValueType.gammaProbabilityValue) {
            parameterField.setValue(fractionalTest(parameterValue.getValFloat()) + " " + parameterValue.getParameter().getUnit().getShortName() + " с гамма-процентной вероятностью " + parameterValue.getGamma() + "%");
        }

        if (parameterValue.getCurrentValueType() == ParameterValueType.string) {
            parameterField.setValue(parameterValue.getValString() + " " + parameterValue.getParameter().getUnit().getShortName());
        }

        if (parameterValue.getCurrentValueType() == ParameterValueType.bool) {
            if (parameterValue.getValString().equals("true")) {
                parameterField.setValue("Да");
            } else if (parameterValue.getValString().equals("false")) {
                parameterField.setValue("Нет");
            }
        }
        ParameterCategory parameterCategory = parameterValue.getParameter().getParameterCategory();
        if (parameterCategory != null) {
            GroupBoxLayout groupBoxLayout = null;
            if (groupBoxLayouts != null) {
                groupBoxLayout = groupBoxLayouts.stream().filter(groupBoxLayoutTemp ->
                        groupBoxLayoutTemp.getCaption().contentEquals(parameterCategory.getName()))
                        .findFirst().orElse(null);
            }
            if (groupBoxLayout != null) {
                groupBoxLayout.add(hBoxContainer);
            } else {
                vbMain.add(hBoxContainer);
            }
        } else {
            vbMain.add(hBoxContainer);
        }
    } //формирование визуального представления параметров


    private void genGroupBox(ParameterValue parameterValue) {

        Parameter parameter = parameterValue.getParameter();
        ParameterCategory parameterCategory = parameter.getParameterCategory();
        if (parameterCategory != null) {
            if (!categorySet.contains(parameterCategory)) {
                categorySet.add(parameterCategory);

                if (groupBoxLayouts == null) {
                    groupBoxLayouts = new ArrayList<>();
                }
                GroupBoxLayout groupBoxLayout = componentsFactory.createComponent(GroupBoxLayout.class);
                groupBoxLayout.setCaption(parameterCategory.getName());
                groupBoxLayout.setSpacing(true);
                groupBoxLayouts.add(groupBoxLayout);
                vbMain.add(groupBoxLayout);
            }
        }
    }

    private String fractionalTest(Double doubleValue) {
        String strValue;
        if (doubleValue % 1 != 0) {
            strValue = String.format("%.2f", doubleValue);
        } else {
            strValue = String.format("%d", doubleValue.intValue());
        }
        return strValue;
    }
}