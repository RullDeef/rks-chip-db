package ru.spacecorp.mobdekbkp.web.devicefilterconditions;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.Datasource;
import ru.spacecorp.mobdekbkp.entity.*;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.*;

public class DeviceFilterConditionsEdit extends AbstractEditor<DeviceFilterConditions> {

    private ArrayList<ParamsAndAttributes> paramsAndAttrsList=new ArrayList<>();

    @Inject private DataManager dataManager;
    @Inject private Metadata metadata;
    @Inject private RichTextArea rtaArea;
    @Inject private Datasource<DeviceFilterConditions> deviceFilterConditionsDs;
    @Inject
    private GridLayout filterGrid;

    private HashMap<String,String> typeMap=new HashMap<>();
    private HashMap<String, String> pathMap=new HashMap<>();

    private final String[] exclude = {
            "sys$StandardEntity.deletedBy",
            "sys$StandardEntity.deleteTs",
            "sys$StandardEntity.createTs",
            "sys$BaseUuidEntity.id",
            "sys$StandardEntity.updatedBy",
            "sys$StandardEntity.version",
            "sys$StandardEntity.createdBy",
            "sys$StandardEntity.updateTs",
            "mobdekbkp$Typonominal.name",
            "mobdekbkp$Typonominal.body",
            "mobdekbkp$Typonominal.installParameters",
            "mobdekbkp$Typonominal.libAttributes",
            "mobdekbkp$Typonominal.type",
            "mobdekbkp$Typonominal.persistenceInfo",
            "mobdekbkp$Typonominal.analogs",
            "mobdekbkp$Typonominal.purchaseParameters",
            "mobdekbkp$Typonominal.qualityCaption",
            "mobdekbkp$TyponominalBody.name",
            "mobdekbkp$TyponominalBody.fixatorMarkup",
            "mobdekbkp$TyponominalBody.description",
            "mobdekbkp$TyponominalBody.documentsModel",
            "mobdekbkp$TyponominalBody.photo",
            "mobdekbkp$TyponominalBody.dimensionsAndBody",
            "mobdekbkp$TyponominalBody.pinFormingDesignation",
            "mobdekbkp$TyponominalBody.designation",
            "mobdekbkp$TyponominalBody.mass",
            "mobdekbkp$TyponominalBody.height",
            "mobdekbkp$TyponominalBody.length",
            "mobdekbkp$TyponominalBody.width",
            "mobdekbkp$TyponominalBody.maxHeight",
            "mobdekbkp$TyponominalBody.distanceLeads",
            "mobdekbkp$Typonominal.labeling",
            "mobdekbkp$Typonominal.isAutoAssemble",
            "mobdekbkp$TyponominalBody.pinsCount",
            "mobdekbkp$Type.functionalPurpose",
            "mobdekbkp$Type.mathModelParams",
            "mobdekbkp$Type.altClassMil",
            "mobdekbkp$Type.documnetsDelivery",
            "mobdekbkp$Type.documentsStandartAppScheme",
            "mobdekbkp$Type.altClassRel",
            "mobdekbkp$Type.paramValue",
            "mobdekbkp$Type.calicoholders",
            "mobdekbkp$Type.typonominal",
            "mobdekbkp$Type.amountUnit",
            "mobdekbkp$Type.altClassG56649",
            "mobdekbkp$Type.altClassG2710",
            "mobdekbkp$Type.typeClass",
            "mobdekbkp$Type.designation",
            "mobdekbkp$Type.providers",
            "mobdekbkp$TyponominalPurchaseParameters.cost",
            "mobdekbkp$TyponominalPurchaseParameters.hasSamples",
            "mobdekbkp$TyponominalPurchaseParameters.typonominal",
            "mobdekbkp$TyponominalPurchaseParameters.purchaseDesignation",
            "mobdekbkp$TyponominalPurchaseParameters.costRating",
            "mobdekbkp$TyponominalPurchaseParameters.marketAvailableIndex",
            "mobdekbkp$TyponominalPurchaseParameters.deliveryMax",
            "mobdekbkp$TyponominalPurchaseParameters.permissionGosdepTerm",
            "mobdekbkp$TyponominalPurchaseParameters.typicalDeliveryTerm",
            "mobdekbkp$TyponominalPurchaseParameters.company",
            "mobdekbkp$TyponominalPurchaseParameters.deliveryMin",
            "mobdekbkp$OuterPersistenceInfo.hasFiles",
            "mobdekbkp$OuterPersistenceInfo.typonominal",
            "mobdekbkp$OuterPersistenceInfo.infoSource",
            "mobdekbkp$OuterPersistenceInfo.manufacturer",
            "mobdekbkp$Substrate.name",
            "mobdekbkp$Substrate.color",
            "mobdekbkp$Substrate.model"
    };

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        HashMap<String, String> stringsMap = getFields(metadata.getClass(Typonominal.class));
        stringsMap = mergeMap(stringsMap, getFields(metadata.getClass(TyponominalBody.class)));
        stringsMap = mergeMap(stringsMap, getFields(metadata.getClass(TyponominalInstallParameters.class)));
        stringsMap = mergeMap(stringsMap, getFields(metadata.getClass(Type.class)));
        stringsMap = mergeMap(stringsMap, getFields(metadata.getClass(TyponominalPurchaseParameters.class)));
        stringsMap = mergeMap(stringsMap, getFields(metadata.getClass(OuterPersistenceInfo.class)));
        stringsMap = mergeMap(stringsMap, getFields(metadata.getClass(Substrate.class)));

        for(Map.Entry<String,String> s:stringsMap.entrySet()){
            ParamsAndAttributes paramsAndAttributes=metadata.create(ParamsAndAttributes.class);
            paramsAndAttributes.setName(s.getKey());
            paramsAndAttributes.setType(s.getValue());
            paramsAndAttrsList.add(paramsAndAttributes);
        }
        //System.out.println(strings);
        //strings.sort(String::compareToIgnoreCase);

        LoadContext<Parameter> ctx = LoadContext.create(Parameter.class);
        ctx.setQuery(LoadContext.createQuery("select p from mobdekbkp$Parameter p"));
        List<Parameter> parameters = dataManager.loadList(ctx);
        for (Parameter parameter : parameters) {
            String name = parameter.getName();
            String units = parameter.getDefaultValueType().name();

            typeMap.put(name, units);
            pathMap.put(name, "mobdekbkp$Parameter");
            ParamsAndAttributes paramsAndAttributes = metadata.create(ParamsAndAttributes.class);
            paramsAndAttributes.setName(name);
            paramsAndAttributes.setType(units);
            paramsAndAttrsList.add(paramsAndAttributes);
        }

        deviceFilterConditionsDs.addItemPropertyChangeListener(e -> {
            if(e.getProperty().contentEquals("valueType")){
                ParameterValueType value =(ParameterValueType) e.getValue();
                if(value==null){
                    value=ParameterValueType.valueOf("string");
                }

                setVisibleComponent(new String[]{
                        "valFloatLbl",
                        "valFloatHbox",
                        "valStringLbl",
                        "valStringHbox",
                        "boolLbl",
                        "boolCheckBox"
                }, false);

                getItem().setValFloat(null);
                getItem().setValString(null);
                getItem().setValBoolean(null);
                valueTypeVisible(value);
            }
        });

        rtaArea.addValueChangeListener(e -> {
            String strValue=typeMap.get(e.getValue());
            if(strValue==null){
                strValue="string";
            }
            String path=pathMap.get(e.getValue());
            getItem().setValueType(ParameterValueType.valueOf(strValue));
            getItem().setPath(path);
        });
    }

    @Override
    public void ready() {
        super.ready();
        if(getItem()!=null) {
            if (getItem().getValueType() != null) {
                valueTypeVisible(getItem().getValueType());
            }
        }
    }

    @NotNull
    private HashMap<String,String> getFields(MetaClass metaClass) {
        ArrayList<String> strings = new ArrayList<>();
        HashMap<String,String> localized = new HashMap<>();
        for(MetaProperty property : metaClass.getProperties()) {
            strings.add(property.toString());
            String[] temp=property.getJavaType().getTypeName().split("\\.");
            String type=temp[temp.length-1];
            String prop = property.toString().split("\\$")[1];
            String key=messages.getMessage("ru.spacecorp.mobdekbkp.entity", prop);
            switch (type){
                case "String":{
                    typeMap.put(key,"string");
                    break;
                }
                case "Double":{
                    typeMap.put(key,"normal");
                    break;
                }
                case "Integer":{
                    typeMap.put(key,"integer");
                    break;
                }
                case "Boolean":{
                    typeMap.put(key,"bool");
                    break;
                }
                default:{
                    typeMap.put(key,"string");
                }
            }
        }

        strings.removeAll(Arrays.asList(exclude));
        for (String string : strings) {
            String prop = string.split("\\$")[1];

            String key=messages.getMessage("ru.spacecorp.mobdekbkp.entity", prop);
            localized.put(key, typeMap.get(key));
            pathMap.put(key, string);
        }
        return localized;
    }

    public void onPickBtnClick() {
        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put("values",paramsAndAttrsList);
        openLookup("paramAndAttrLookup",items -> {
            String value=((ParamsAndAttributes)items.iterator().next()).getName();
            getItem().setAttribute(value);
        }, WindowManager.OpenType.DIALOG,hashMap);
    }

    private void valueTypeVisible(ParameterValueType parameterValueType) {

        if (parameterValueType == null)
            return;

        //TODO поправить для актуальной модели
        switch (parameterValueType) {
            case integer:
                setVisibleComponent(new String[]{
                        "valFloatLbl",
                        "valFloatHbox"
                }, true);
                break;
            case string:
                setVisibleComponent(new String[]{
                        "valStringLbl",
                        "valStringHbox"
                }, true);
                break;
            case bool:
                setVisibleComponent(new String[]{
                        "boolLbl",
                        "boolCheckBox"
                }, true);
                break;
            case interval:
                setVisibleComponent(new String[]{
                        "valFloatLbl",
                        "valFloatHbox"
                }, true);
                break;
            case normal:
                setVisibleComponent(new String[]{
                        "valFloatLbl",
                        "valFloatHbox"
                }, true);
                break;
            case normalTolerated:
                setVisibleComponent(new String[]{
                        "valFloatLbl",
                        "valFloatHbox",
                }, true);
                break;
            case normalToleratedPrecent:
                setVisibleComponent(new String[]{
                        "valFloatLbl",
                        "valFloatHbox",
                }, true);
                break;
            case gammaProbabilityValue:
                setVisibleComponent(new String[]{
                        "valFloatLbl",
                        "valFloatHbox",
                }, true);
                break;
        }
    }

    private void setVisibleComponent(String[] keys, Boolean aBoolean) {
        for (String key : keys) {
            Component component = filterGrid.getComponent(key);
            if (component == null) {
                String errorLog = String.format("Cannot find row with key %s in grid layout %s", key, filterGrid.getId());
                throw new RuntimeException(errorLog);
            }
            component.setVisible(aBoolean);
        }
    }

    private HashMap<String,String> mergeMap(HashMap<String, String> map1, HashMap<String, String> map2){
        HashMap<String,String> mapRes=new HashMap<>();
        mapRes.putAll(map1);
        for(Map.Entry<String,String> item:map2.entrySet()){
            mapRes.merge(item.getKey(), item.getValue(), (s, s2) -> {
                if(s.contentEquals("string")){
                    return s2;
                }
                else {
                    return s;
                }
            });
        }
        return mapRes;
    }
}