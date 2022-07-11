package ru.spacecorp.mobdekbkp.web.outerlisttype;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.gui.data.Datasource;
import ru.spacecorp.mobdekbkp.entity.ExtBoolean;
import ru.spacecorp.mobdekbkp.entity.OuterListType;

import javax.inject.Inject;
import java.util.Map;

public class OuterListTypeEdit extends AbstractEditor<OuterListType> {
    @Override
    protected void initNewItem(OuterListType item) {
        item.setIsAllowing(ExtBoolean.notSet);
    }
}