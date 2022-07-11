package ru.iovchinnikov.notificationsusers.gui.extuser;

import com.haulmont.cuba.gui.app.security.user.edit.UserEditor;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import java.util.Map;

public class ExtUserEditor extends UserEditor {
    @Inject
    LookupPickerField pfCompany;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        if (params.get("company") == null)
            return;

        pfCompany.setDatasource((CollectionDatasource) params.get("company"), "companyRef");
    }
}
