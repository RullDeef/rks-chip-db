package ru.spacecorp.mobdekbkp.web.handbookentry;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import ru.spacecorp.mobdekbkp.entity.HandbookEntry;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class HandbookEntryEdit extends AbstractEditor<HandbookEntry> {

    private static final String ID = "id";

    private UUID handbookId = null;

    @Inject
    private CollectionDatasource<HandbookEntry, UUID> handbookEntriesDs;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        Object idObj = params.get(ID);
        if (idObj != null) {
            if (idObj instanceof UUID) {
                handbookId = (UUID) idObj;
            }
        }
    }

    @Override
    protected void postInit() {
        super.postInit();
        if (handbookId != null) {
            handbookEntriesDs.setQuery(String.format("select e from mobdekbkp$HandbookEntry e " +
                    "where e.handbook.id = '%s' and e.id <> '%s'", handbookId, getItem().getId()));
            handbookEntriesDs.refresh();
        }
    }
}