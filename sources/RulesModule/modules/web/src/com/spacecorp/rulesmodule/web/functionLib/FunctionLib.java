package com.spacecorp.rulesmodule.web.functionLib;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.spacecorp.rulesmodule.service.RuleService;

/**
 * Created by Stepanov_ME on 15.03.2018.
 */
public class FunctionLib {

    private ComponentsFactory componentsFactory= AppBeans.get(ComponentsFactory.class);

    private RuleService ruleService=AppBeans.get(RuleService.class);

    public Component getMsgField(Entity entity){
        String[] resultArr= ruleService.getResultArray(entity,"message");
        Label label=(Label)componentsFactory.createComponent(Label.NAME);
        label.setEditable(false);
        label.setSizeFull();
        label.setValue(resultArr[1]);
        return label;
    }

    public String getMsg(Entity entity){
        String[] resultArr= ruleService.getResultArray(entity,"message");
        return resultArr[1];
    }

    public String getAdditionMsg(Entity entity){
        String[] resultArr=ruleService.getResultArray(entity,"addMsg");
        return resultArr[2];
    }

    public String getStyleName(Entity entity,String property){
        String[] resultArr= ruleService.getResultArray(entity,property);
        return resultArr[0];
    }

    public void refreshData(){
        ruleService.refreshData();
    }
}
