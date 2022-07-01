package ru.spacecorp.mobdekbkp.web.applicationcommonentry;

import com.haulmont.cuba.gui.components.AbstractEditor;
import ru.spacecorp.mobdekbkp.entity.ApplicationCommonEntry;
import ru.spacecorp.mobdekbkp.entity.ApplicationCommonEntryStatus;

public class ApplicationCommonEntryEdit extends AbstractEditor<ApplicationCommonEntry> {

    @Override
    protected void initNewItem(ApplicationCommonEntry item) {
        item.setStatus(ApplicationCommonEntryStatus.inDev);
    }

}