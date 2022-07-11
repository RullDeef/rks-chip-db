package ru.spacecorp.mobdekbkp.web.typonominal;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.spacecorp.rulesmodule.web.functionLib.FunctionLib;
import ru.spacecorp.mobdekbkp.entity.Typonominal;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Map;

public class Typonominalreliability extends AbstractWindow {

    private final static int MINIMIZED_VALUE_SIZE = 75;

    @Inject
    private GroupTable<Typonominal> typonominalTable;

    private FunctionLib functionLib = new FunctionLib();

    private ComponentsFactory componentsFactory = AppBeans.get(ComponentsFactory.class);

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        functionLib.refreshData();
    }

    @Override
    public void ready() {
        super.ready();
        typonominalTable.setStyleProvider(new GroupTable.GroupStyleProvider<Entity>() {
            @Nullable
            @Override
            public String getStyleName(GroupInfo info) {
                return null;
            }

            @Override
            public String getStyleName(Entity entity, @Nullable String property) {
                if (property != null) {
                    if (property.contentEquals("name")) {
                        return functionLib.getStyleName(entity, property);
                    } else {
                        return "";
                    }
                }
                return "";
            }
        });

        typonominalTable.addGeneratedColumn(getMessage("reliability"), new Table.PrintableColumnGenerator<Typonominal, String>() {
            @Override
            public Component generateCell(Typonominal entity) {
                return functionLib.getMsgField(entity);
            }

            @Override
            public String getValue(Typonominal item) {
                return functionLib.getMsg(item);
            }
        });

        typonominalTable.addGeneratedColumn(getMessage("missingData"), new Table.PrintableColumnGenerator<Typonominal, String>() {
            @Override
            public Component generateCell(Typonominal entity) {
                String additMsg = functionLib.getAdditionMsg(entity);
                PopupView popupView = componentsFactory.createComponent(PopupView.class);
                if (additMsg.length() < MINIMIZED_VALUE_SIZE) {
                    popupView.setMinimizedValue(additMsg.substring(0, additMsg.length()));
                } else {
                    popupView.setMinimizedValue(additMsg.substring(0, MINIMIZED_VALUE_SIZE));
                }
                popupView.setHeightFull();
                popupView.setWidthFull();

                TextArea textArea = componentsFactory.createComponent(TextArea.class);
                textArea.setEditable(false);
                textArea.setWidth("450px");
                textArea.setHeight("150px");
                textArea.setValue(additMsg);

                popupView.setPopupContent(textArea);
                return popupView;
            }

            @Override
            public String getValue(Typonominal item) {
                return functionLib.getAdditionMsg(item);
            }
        });
    }

    public void onRefreshBtnClick() {
        functionLib.refreshData();
        typonominalTable.getDatasource().refresh();
    }

    public void onReliabilityBtnClick() {
        GroupDatasource typonominalDatasource = typonominalTable.getDatasource();
        ArrayList<Entity> entityArrayList = new ArrayList<>(typonominalTable.getDatasource().getItems());
        typonominalDatasource.clear();
        for (Entity entity : entityArrayList) {
            if (functionLib.getMsg(entity).contentEquals(getMessage("reliable"))) {
                typonominalDatasource.addItem(entity);
            }
        }
    }
}