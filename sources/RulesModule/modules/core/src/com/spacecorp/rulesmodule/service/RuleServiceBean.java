package com.spacecorp.rulesmodule.service;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.Scripting;
import com.haulmont.cuba.core.global.View;
import com.spacecorp.rulesmodule.entity.RuleDataScript;
import com.spacecorp.rulesmodule.entity.RuleManager;
import com.spacecorp.rulesmodule.entity.RuleScript;
import groovy.lang.Binding;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service(RuleService.NAME)
public class RuleServiceBean implements RuleService {
    @Inject
    private DBExportService dbExportService;

    @Inject
    private Metadata metadata;

    @Inject
    private Scripting scripting;

    private Binding binding;
    private ArrayList<RuleDataScript> sqlScriptList;

    private List<Object> getRuleManagers(String entity) {
        View view = metadata.getViewRepository().getView(RuleManager.class, "ruleManager-view");
        //return dbExportService.getRecords(entity,metadata.getClass(RuleManager.class),view);
        return dbExportService.getRecordByParam("o.entity='" + entity + "'", "rulesmodule$RuleManager",
                metadata.getClass(RuleManager.class), view);
    }

    @Override
    public List<Object> performRules(String entityName, String parameter) {
        List<Object> managersList = getRuleManagers(entityName);
        List<Object> resultList = new ArrayList<>();
        if (managersList == null) {
            return resultList;
        }
        for (Object item : managersList) {

            //проверка на наличие уже загруженных данных
            if (sqlScriptList == null) {
                sqlScriptList = new ArrayList<>(((RuleManager) item).getDataScript());
                binding = new Binding();

                for (RuleDataScript aSqlScriptList : sqlScriptList) {
                    List<Object> objectList = dbExportService.getQueryResult(aSqlScriptList.getSql(),
                            metadata.getClass(aSqlScriptList.getEntity()), aSqlScriptList.getView());

                    List<Entity> entities = new ArrayList<>();
                    HashMap<String, Entity> entityMap = new HashMap<>();
                    for (Object record : objectList) {
                        entities.add((Entity) record);
                        entityMap.put(((Entity) record).getId().toString(), (Entity) record);
                    }
                    binding.setVariable(aSqlScriptList.getName(), entities);
                    binding.setVariable(aSqlScriptList.getName() + "Map", entityMap);
                }
            } else {
                //если данные уже загружены и совпадают с необходимыми, то выход из ветвления
                if (!sqlScriptList.equals(new ArrayList<>(((RuleManager) item).getDataScript()))) {
                    sqlScriptList = new ArrayList<>(((RuleManager) item).getDataScript());
                    binding = new Binding();

                    for (RuleDataScript aSqlScriptList : sqlScriptList) {
                        List<Object> objectList = dbExportService.getQueryResult(aSqlScriptList.getSql(),
                                metadata.getClass(aSqlScriptList.getEntity()), aSqlScriptList.getView());

                        List<Entity> entities = new ArrayList<>();
                        HashMap<String, Entity> entityMap = new HashMap<>();
                        for (Object record : objectList) {
                            entities.add((Entity) record);
                            entityMap.put(((Entity) record).getId().toString(), (Entity) record);
                        }
                        binding.setVariable(aSqlScriptList.getName(), entities);
                        binding.setVariable(aSqlScriptList.getName() + "Map", entityMap);
                    }
                }
            }
            binding.setVariable("parameter", parameter);
            List<RuleScript> ruleScripts = ((RuleManager) item).getScript();
            for (RuleScript ruleScript : ruleScripts) {
                resultList.add(scripting.evaluateGroovy(ruleScript.getScript(), binding));
            }
        }
        return resultList;
    }


    @Override
    public String[] getResultArray(Entity entity, String property) {
        String[] resultArr = new String[3];
        ArrayList resultList = new ArrayList();
        resultArr[0] = "";
        resultArr[1] = "";
        resultArr[2] = "";
        if (property != null) {
            resultList.addAll(performRules(entity.getMetaClass().getName(), entity.getId().toString()));
            for (Object o : resultList) {
                String styleName = ((Object[]) o)[0].toString();
                String msg0 = "";
                String msg1 = "";
                try {
                    msg0 = ((String[]) o)[1];
                } catch (Exception e) {
                    //TODO логгирование
                }
                try {
                    msg1 = ((String[]) o)[2];
                } catch (Exception e) {
                    //TODO логгирование
                }
                if (styleName != null) {
                    if (styleName.startsWith("red")) {
                        resultArr[0] = styleName;
                        resultArr[1] = msg0;
                        resultArr[2] = msg1;
                        return resultArr;
                    }
                    if (styleName.startsWith("orange")) {
                        resultArr[0] = styleName;
                    }
                    if (styleName.startsWith("yellow")) {
                        if (!resultArr[0].startsWith("orange"))
                            resultArr[0] = styleName;
                    }
                    if (styleName.startsWith("green")) {
                        if ((!resultArr[0].startsWith("yellow")) && (!resultArr[0].startsWith("orange"))) {
                            resultArr[0] = styleName;
                        }
                    }
                }
                resultArr[1] = msg0;
                resultArr[2] = msg1;
            }
        }
        return resultArr;
    }

    @Override
    public void refreshData() {
        sqlScriptList = null;
    }
}