package ru.spacecorp.mobdekbkp.web.typonominalbody;

import com.haulmont.cuba.gui.components.AbstractEditor;
import ru.spacecorp.mobdekbkp.entity.ExtBoolean;
import ru.spacecorp.mobdekbkp.entity.TyponominalBody;

public class TyponominalBodyEdit extends AbstractEditor<TyponominalBody> {

    @Override
    protected void initNewItem(TyponominalBody item) {
        item.setSealingDemand(ExtBoolean.notSet);
    }

}