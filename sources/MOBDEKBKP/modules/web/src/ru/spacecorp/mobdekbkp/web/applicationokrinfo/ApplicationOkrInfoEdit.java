package ru.spacecorp.mobdekbkp.web.applicationokrinfo;

import com.haulmont.cuba.gui.components.AbstractEditor;
import ru.spacecorp.mobdekbkp.entity.ApplicationOkrInfo;
import ru.spacecorp.mobdekbkp.entity.ApplicationOkrInfoCondition;

public class ApplicationOkrInfoEdit extends AbstractEditor<ApplicationOkrInfo> {
    @Override
    protected void initNewItem(ApplicationOkrInfo item) {
        super.initNewItem(item);
        item.setCondition(ApplicationOkrInfoCondition.inDev);
    }
}