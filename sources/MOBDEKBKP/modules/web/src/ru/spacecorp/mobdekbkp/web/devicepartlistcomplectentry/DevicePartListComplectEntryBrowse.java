package ru.spacecorp.mobdekbkp.web.devicepartlistcomplectentry;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.spacecorp.rulesmodule.service.RuleService;
import com.spacecorp.rulesmodule.web.functionLib.FunctionLib;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Map;

public class DevicePartListComplectEntryBrowse extends AbstractLookup {
    @Inject
    GroupTable devicePartListComplectEntriesTable;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        FunctionLib functionLib=new FunctionLib();
        devicePartListComplectEntriesTable.setStyleProvider(new GroupTable.GroupStyleProvider() {
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

        devicePartListComplectEntriesTable.addGeneratedColumn("Рекомендации", new Table.ColumnGenerator() {
            @Override
            public Component generateCell(Entity entity) {
                return functionLib.getMsgField(entity);
            }
        });
    }

}