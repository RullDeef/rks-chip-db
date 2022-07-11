package ru.spacecorp.mobdekbkp.web.typonominal;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import ru.spacecorp.mobdekbkp.entity.*;

import java.util.*;

/**
 * Created by Stepanov_ME on 21.08.2018.
 */
public class CustomFilter {

    private DataManager dataManager = AppBeans.get(DataManager.class);

    public HashMap<UUID, Integer> filterData(ArrayList<DeviceFilterConditions> filterConditionsList) {

        LoadContext<Type> typeLoadContext = LoadContext.create(Type.class);
        typeLoadContext.setQuery(LoadContext.createQuery("select e from mobdekbkp$Type e"));
        typeLoadContext.setView("type-filter-view");
        ArrayList<Type> types = new ArrayList<>(dataManager.loadList(typeLoadContext));

        HashMap<UUID, Integer> tnIdMap = new HashMap<>();

        for (DeviceFilterConditions filterConditions : filterConditionsList) {

            String attributePath = filterConditions.getPath();
            String[] pathParts = attributePath.split("\\.");
            String attribute = pathParts[0];

            switch (attribute) {
                case "mobdekbkp$Parameter": {
                    for (Type type : types) {
                        boolean found = false;
                        //List<ParameterValue> valueList = type.getParamValue();
                        ArrayList<ParameterValue> valueList = new ArrayList<>(type.getParamValue());
                        for (ParameterValue parameterValue : valueList) {
                            if (parameterValue.getParameter().getName().contentEquals(filterConditions.getAttribute())) {
                                found = true;
                                if (!checkDemands(filterConditions, parameterValue)) {
                                    Set<Typonominal> typonominalSet = type.getTyponominal();
                                    for (Typonominal x : typonominalSet) {
                                        tnIdMap.put(x.getId(), 1);
                                    }
                                    break;
                                }
                            }
                        }
                        if (!found) {
                            Set<Typonominal> typonominalSet = type.getTyponominal();
                            for (Typonominal x : typonominalSet) {
                                tnIdMap.put(x.getId(), 1);
                            }
                        }
                    }
                    break;
                }
                case "mobdekbkp$Typonominal": {
                    for (Type type : types) {
                        Set<Typonominal> typonominals = type.getTyponominal();
                        for (Typonominal typonominal : typonominals) {
                            Object value = typonominal.getValue(pathParts[1]);
                            try {
                                Entity entity = (Entity) value;
                                String nameStr = entity.getInstanceName();
                                value = nameStr;
                            } catch (Exception e) {
                                //do nothing
                            }
                            if (!checkDemands(filterConditions, value)) {
                                tnIdMap.put(typonominal.getId(), 1);
                                continue;
                            }
                        }
                    }
                    break;
                }
                case "mobdekbkp$TyponominalBody": {
                    for (Type type : types) {
                        Set<Typonominal> typonominals = type.getTyponominal();
                        for (Typonominal typonominal : typonominals) {
                            TyponominalBody typonominalBody = typonominal.getBody();
                            if (typonominalBody == null) {
                                tnIdMap.put(typonominal.getId(), 1);
                                continue;
                            }
                            Object value = typonominalBody.getValue(pathParts[1]);
                            try {
                                Entity entity = (Entity) value;
                                String nameStr = entity.getInstanceName();
                                value = nameStr;
                            } catch (Exception e) {
                                //do nothing
                            }
                            if (!checkDemands(filterConditions, value)) {
                                tnIdMap.put(typonominal.getId(), 1);
                                continue;
                            }
                        }
                    }
                    break;
                }
                case "mobdekbkp$Country": {
                    for (Type type : types) {
                        List<TypeManufacturerEntry> typeManufacturerEntries = type.getManufacturers();
                        if (typeManufacturerEntries == null) {
                            Set<Typonominal> typonominalSet = type.getTyponominal();
                            for (Typonominal typonominal : typonominalSet) {
                                tnIdMap.put(typonominal.getId(), 1);
                            }
                            continue;
                        }
                        boolean found = false;
                        for (TypeManufacturerEntry typeManufacturerEntry : typeManufacturerEntries) {
                            Company company = typeManufacturerEntry.getName();
                            Country country = company.getCountry();
                            Object value = country.getValue(pathParts[1]);
                            try {
                                Entity entity = (Entity) value;
                                String nameStr = entity.getInstanceName();
                                value = nameStr;
                            } catch (Exception e) {
                                //do nothing
                            }
                            if (checkDemands(filterConditions, value)) {
                                found = true;
                            }
                        }
                        if (!found) {
                            Set<Typonominal> typonominals = type.getTyponominal();
                            for (Typonominal typonominal : typonominals) {
                                tnIdMap.put(typonominal.getId(), 1);
                            }
                        }
                    }
                    break;
                }
                case "mobdekbkp$Type": {
                    for (Type type : types) {
                        Object value = type.getValue(pathParts[1]);
                        try {
                            Entity entity = (Entity) value;
                            String nameStr = entity.getInstanceName();
                            value = nameStr;
                        } catch (Exception e) {
                            //do nothing
                        }
                        if (!checkDemands(filterConditions, value)) {
                            Set<Typonominal> typonominals = type.getTyponominal();
                            for (Typonominal typonominal : typonominals) {
                                tnIdMap.put(typonominal.getId(), 1);
                            }
                        }
                    }
                    break;
                }
                case "mobdekbkp$TyponominalPurchaseParameters": {
                    for (Type type : types) {
                        Set<Typonominal> typonominals = type.getTyponominal();
                        for (Typonominal typonominal : typonominals) {
                            List<TyponominalPurchaseParameters> typonominalPurchaseParameters = typonominal.getPurchaseParameters();
                            if (typonominalPurchaseParameters == null) {
                                tnIdMap.put(typonominal.getId(), 1);
                                continue;
                            }
                            boolean found = false;
                            for (TyponominalPurchaseParameters typonominalPurchaseParameter : typonominalPurchaseParameters) {
                                Object value = typonominalPurchaseParameter.getValue(pathParts[1]);
                                try {
                                    Entity entity = (Entity) value;
                                    String nameStr = entity.getInstanceName();
                                    value = nameStr;
                                } catch (Exception e) {
                                    //do nothing
                                }
                                if (checkDemands(filterConditions, value)) {
                                    //TODO хотя бы одно выполнение условия или выполнение для всех? (сейчас условие для одного)
                                    found = true;
                                }
                            }
                            if (!found) {
                                tnIdMap.put(typonominal.getId(), 1);
                            }
                        }
                    }
                    break;
                }
                case "mobdekbkp$OuterPersistenceInfo": {
                    for (Type type : types) {
                        Set<Typonominal> typonominals = type.getTyponominal();
                        for (Typonominal typonominal : typonominals) {
                            List<OuterPersistenceInfo> outerPersistenceInfoList = new ArrayList<>(typonominal.getPersistenceInfo());
                            if (outerPersistenceInfoList == null) {
                                tnIdMap.put(typonominal.getId(), 1);
                                continue;
                            }
                            boolean found = false;
                            for (OuterPersistenceInfo persistenceInfo : outerPersistenceInfoList) {
                                Object value = persistenceInfo.getValue(pathParts[1]);
                                try {
                                    Entity entity = (Entity) value;
                                    String nameStr = entity.getInstanceName();
                                    value = nameStr;
                                } catch (Exception e) {
                                    //do nothing
                                }
                                if (checkDemands(filterConditions, value)) {
                                    //TODO хотя бы одно выполнение условия или выполнение для всех? (сейчас условие для одного)
                                    found = true;
                                }
                            }
                            if (!found) {
                                tnIdMap.put(typonominal.getId(), 1);
                            }
                        }
                    }
                    break;
                }
                case "mobdekbkp$Substrate": {
                    for (Type type : types) {
                        Set<Typonominal> typonominals = type.getTyponominal();
                        for (Typonominal typonominal : typonominals) {
                            TyponominalInstallParameters installParameters = typonominal.getInstallParameters();
                            if (installParameters == null) {
                                tnIdMap.put(typonominal.getId(), 1);
                                continue;
                            }
                            List<SubstrateEntry> substrateList = installParameters.getSubstrates();
                            boolean found = false;
                            for (SubstrateEntry substrateEntry : substrateList) {
                                Substrate substrate = substrateEntry.getSubstrate();
                                Object value = substrate.getValue(pathParts[1]);
                                try {
                                    Entity entity = (Entity) value;
                                    String nameStr = entity.getInstanceName();
                                    value = nameStr;
                                } catch (Exception e) {
                                    //do nothing
                                }
                                if (checkDemands(filterConditions, value)) {
                                    //TODO хотя бы одно выполнение условия или выполнение для всех? (сейчас условие для одного)
                                    found = true;
                                }
                            }
                            if (!found) {
                                tnIdMap.put(typonominal.getId(), 1);
                            }
                        }
                    }
                    break;
                }
            }
        }

        return tnIdMap;
    }

    //возвращает true, если требования выполняются
    private boolean checkDemands(DeviceFilterConditions deviceFilterCondition, ParameterValue value) {
        CompareOperator compareOperator = deviceFilterCondition.getCompareOperator();
        ParameterValueType parameterValueType = deviceFilterCondition.getValueType();
        switch (compareOperator) {
            case less: {
                switch (parameterValueType) {
                    case normal: {
                        return value.getValFloat() < deviceFilterCondition.getValFloat();
                    }
                    case integer: {
                        return value.getValFloat() < deviceFilterCondition.getValFloat();
                    }
                    case interval: {
                        return value.getValMin() < deviceFilterCondition.getValFloat();
                    }
                }
                break;
            }
            case more: {
                switch (parameterValueType) {
                    case normal: {
                        //int i=(int)value;
                        return value.getValFloat() > deviceFilterCondition.getValFloat();
                    }
                    case integer: {
                        //int i=(int)value;
                        return value.getValFloat() > deviceFilterCondition.getValFloat();
                    }
                    case interval: {
                        return value.getValMax() > deviceFilterCondition.getValFloat();
                    }
                }
                break;
            }
            case equal: {
                int i;
                String s;
                boolean b;
                switch (parameterValueType) {
                    case integer:
                        //i=(int)value;
                        return value.getValFloat() == deviceFilterCondition.getValFloat();
                    case normal:
                        //i=(int)value;
                        return value.getValFloat() == deviceFilterCondition.getValFloat();
                    case normalTolerated:
                        break;
                    case normalToleratedPrecent:
                        break;
                    case interval:
                        break;
                    case gammaProbabilityValue:
                        break;
                    case string:
                        //s=value.toString();
                        return value.getValString().contentEquals(deviceFilterCondition.getValString());
                    case bool:
                        //b= (boolean)value;
                        //return value==deviceFilterCondition.getValBoolean();
                }
                break;
            }
            case notequal: {
                break;
            }
            case contain: {
                switch (parameterValueType) {
                    case string: {
                        //String s=value.toString();
                        return value.getValString().contains(deviceFilterCondition.getValString());
                    }
                    case interval: {
                        break;
                    }
                }
                break;
            }
            case lessequal: {
                switch (parameterValueType) {
                    case normal: {
                        //int i=(int)value;
                        return value.getValFloat() <= deviceFilterCondition.getValFloat();
                    }
                    case integer: {
                        //int i=(int)value;
                        return value.getValFloat() <= deviceFilterCondition.getValFloat();
                    }
                    case interval: {
                        return value.getValMin() <= deviceFilterCondition.getValFloat();
                    }
                }
                break;
            }
            case moreequal: {
                switch (parameterValueType) {
                    case normal: {
                        //int i=(int)value;
                        return value.getValFloat() >= deviceFilterCondition.getValFloat();
                    }
                    case integer: {
                        //int i=(int)value;
                        return value.getValFloat() >= deviceFilterCondition.getValFloat();
                    }
                    case interval: {
                        return value.getValMax() >= deviceFilterCondition.getValFloat();
                    }
                }
                break;
            }
            case notConain: {
                switch (parameterValueType) {
                    case string: {
                        //String s=value.toString();
                        return !value.getValString().contains(deviceFilterCondition.getValString());
                    }
                    case interval: {
                        break;
                    }
                }
                break;
            }
        }
        return false;
    }

    //возвращает true, если требования выполняются
    private boolean checkDemands(DeviceFilterConditions deviceFilterCondition, Object value) {
        if (value == null) {
            return false;
        }
        ParameterValueType type = deviceFilterCondition.getValueType();
        CompareOperator compareOperator = deviceFilterCondition.getCompareOperator();
        switch (compareOperator) {
            case contain: {
                switch (type) {
                    case string: {
                        return value.toString().contains(deviceFilterCondition.getValString());
                    }
                }
                break;
            }
            case notConain: {
                switch (type) {
                    case string: {
                        return !value.toString().contains(deviceFilterCondition.getValString());
                    }
                }
                break;
            }
            case moreequal: {
                switch (type) {
                    case integer: {
                        return (int) value >= deviceFilterCondition.getValFloat();
                    }
                    case normal: {
                        return (double) value >= deviceFilterCondition.getValFloat();
                    }
                }
                break;
            }
            case lessequal: {
                switch (type) {
                    case integer: {
                        return (int) value <= deviceFilterCondition.getValFloat();
                    }
                    case normal: {
                        return (double) value <= deviceFilterCondition.getValFloat();
                    }
                }
                break;
            }
            case more: {
                switch (type) {
                    case integer: {
                        return (int) value > deviceFilterCondition.getValFloat();
                    }
                    case normal: {
                        return (double) value > deviceFilterCondition.getValFloat();
                    }
                }
                break;
            }
            case less: {
                switch (type) {
                    case integer: {
                        return (int) value < deviceFilterCondition.getValFloat();
                    }
                    case normal: {
                        return (double) value < deviceFilterCondition.getValFloat();
                    }
                }
                break;
            }
            case equal: {
                switch (type) {
                    case string: {
                        return value.toString().contentEquals(deviceFilterCondition.getValString());
                    }
                    case integer: {
                        return (int) value == deviceFilterCondition.getValFloat();
                    }
                    case normal: {
                        return (double) value == deviceFilterCondition.getValFloat();
                    }
                    case bool: {
                        return (boolean) value == deviceFilterCondition.getValBoolean();
                    }
                }
                break;
            }
            case notequal: {
                switch (type) {
                    case string: {
                        return !value.toString().contentEquals(deviceFilterCondition.getValString());
                    }
                    case integer: {
                        return (int) value != deviceFilterCondition.getValFloat();
                    }
                    case normal: {
                        return (double) value != deviceFilterCondition.getValFloat();
                    }
                    case bool: {
                        return (boolean) value != deviceFilterCondition.getValBoolean();
                    }
                }
                break;
            }
        }
        return true;
    }
}
