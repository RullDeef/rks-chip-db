package ru.spacecorp.mobdekbkp.web.cost;

import com.haulmont.cuba.gui.components.AbstractEditor;
import ru.spacecorp.mobdekbkp.entity.Cost;

import java.util.Date;
import java.util.Map;

public class CostEdit extends AbstractEditor<Cost> {
    @Override
    protected void initNewItem(Cost item) {
        item.setSetupDate(new Date());
    }
}