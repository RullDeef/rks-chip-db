package com.spacecorp.rulesmodule.web.rulescript;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.spacecorp.rulesmodule.web.functionLib.FunctionLib;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Map;

public class RuleScriptBrowse extends AbstractLookup {

    @Inject
    GroupTable ruleScriptsTable;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        FunctionLib functionLib=new FunctionLib();
        ruleScriptsTable.setStyleProvider(new GroupTable.GroupStyleProvider() {
            @Nullable
            @Override
            public String getStyleName(GroupInfo info) {
                return null;
            }

            @Override
            public String getStyleName(Entity entity, @Nullable String property) {
                return functionLib.getStyleName(entity,property);
            }
        });
    }
}