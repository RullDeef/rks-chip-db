package ru.spacecorp.mobdekbkp.web.devicecomplect;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.DeviceComplect;
import ru.spacecorp.mobdekbkp.entity.DevicePartListComplectEntry;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

public class DeviceComplectEdit extends AbstractEditor<DeviceComplect> {

    @Named("documentTable.create")
    private CreateAction documentCreateBtn;
    @Inject
    private Table<DevicePartListComplectEntry> tableDevComplect;
    @Inject
    private ComponentsFactory componentsFactory;

    public Component progressCell(DevicePartListComplectEntry dc) {
        Label l = componentsFactory.createComponent(Label.class);
        String current = ((int) (dc.getProgress() * 100) % 101) + "%";
        l.setValue(current);
        return l;
    }

    @Override
    public void ready() {
        super.ready();
        tableDevComplect.setStyleProvider(new Table.StyleProvider<DevicePartListComplectEntry>() {
            @Nullable
            @Override
            public String getStyleName(DevicePartListComplectEntry entity, @Nullable String property) {
                if (property == null) {
                    return null;
                } else if (property.equals("progress")) {
                    if (entity.getProgress() == null) {
                        return null;
                    } else {
                        return "some-color-" + precentRounder((int) (entity.getProgress() * 100) % 101);
                    }
                }
                return null;
            }
        });
    }

    // Метод, округляющий текущее значение процентов готовности перечня до 5
    private static int precentRounder(int i) {
        if (i == 1 || i == 2) return 5;
        return (i > 94 && i < 100) ? 95 : ((i + 2) / 5) * 5;
    }
}