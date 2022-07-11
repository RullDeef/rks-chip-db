package ru.spacecorp.mobdekbkp.web.devicefilterconditions;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import ru.spacecorp.mobdekbkp.entity.ParamsAndAttributes;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class Paramandattrlookup extends AbstractLookup {

    @Inject
    private CollectionDatasource<ParamsAndAttributes, UUID> paramsAndAttributesesDs;

    @Inject
    private DataGrid<ParamsAndAttributes> attrGrid;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        if (params.get("values") != null) {
            ArrayList<ParamsAndAttributes> paramsAndAttributes = new ArrayList<>((Collection<ParamsAndAttributes>) params.get("values"));
            for (ParamsAndAttributes item : paramsAndAttributes) {
                paramsAndAttributesesDs.addItem(item);
            }
        }
        DataGrid.Column column = attrGrid.getColumn("name");
        if (column != null) {
            column.setRenderer(attrGrid.createRenderer(DataGrid.HtmlRenderer.class));
        }
    }

    @Override
    protected boolean preClose(String actionId) {
        return close(Window.CLOSE_ACTION_ID, true);
    }
}