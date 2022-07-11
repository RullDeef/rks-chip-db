package ru.spacecorp.mobdekbkp.web.handbook;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;

import javax.inject.Named;

public class HandbookEdit extends AbstractEditor {

    private static final String ID = "id";

    @Named("treeTable.edit")
    private EditAction editAction;

    @Named("treeTable.create")
    private CreateAction createAction;

    @Override
    protected void postInit() {
        super.postInit();
        editAction.setWindowParams(ParamsMap.of(ID, getItem().getId()));
        createAction.setWindowParams(ParamsMap.of(ID, getItem().getId()));
    }
}