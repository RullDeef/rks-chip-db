package ru.spacecorp.mobdekbkp.web.typeclass;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.AddAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.Parameter;
import ru.spacecorp.mobdekbkp.entity.TypeClass;
import ru.spacecorp.mobdekbkp.entity.TypeClassCharacteristic;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class TypeClassEdit extends AbstractEditor<TypeClass> {
    @Inject
    private GroupTable<TypeClassCharacteristic> characteristicsTable;
    @Inject
    private GroupDatasource<TypeClassCharacteristic, UUID> characteristicsDs;
    @Inject
    private Datasource<Parameter> parameterDs;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Metadata metadata;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        initCharacteristicsTable();

    }

    /**
     * Инициализация таблици параметров
     */
    private void initCharacteristicsTable() {
        characteristicsTable.addGeneratedColumn("paramsName", entity -> {
            Label labelValue = componentsFactory.createComponent(Label.class);
            labelValue.setHtmlEnabled(true);
            labelValue.setValue(entity.getParameter().getValue("name"));
            return labelValue;
        });

        characteristicsTable.setColumnCaption("paramsName", messages.getMessage(getClass(), "paramsName"));

        characteristicsTable.addAction(new EditAction(characteristicsTable) {
            @Override
            public void actionPerform(Component component) {
                TypeClassCharacteristic typeClassCharacteristic = characteristicsTable.getSingleSelected();
                if (typeClassCharacteristic != null) {
                    AbstractEditor editor = openEditor(
                            parameterDs.getItem(),
                            WindowManager.OpenType.DIALOG,
                            null,
                            parameterDs);

                    editor.addCloseWithCommitListener(() ->
                            characteristicsDs.refresh()
                    );
                }
            }
        });

        characteristicsTable.addAction(new AddAction(characteristicsTable) {
            @Override
            public void actionPerform(Component component) {
                openLookup("mobdekbkp$Parameter.browse",
                        items -> {
                            if (items != null && items.size() != 0) {
                                for (Object item : items) {
                                    Parameter parameter = (Parameter) item;
                                    TypeClassCharacteristic typeClassCharacteristic = metadata.create(TypeClassCharacteristic.class);
                                    typeClassCharacteristic.setParameter(parameter);
                                    typeClassCharacteristic.setTypeClass(getItem());

                                    characteristicsDs.addItem(typeClassCharacteristic);
                                }
                            }
                        },
                        WindowManager.OpenType.DIALOG);
            }
        });

    }
}