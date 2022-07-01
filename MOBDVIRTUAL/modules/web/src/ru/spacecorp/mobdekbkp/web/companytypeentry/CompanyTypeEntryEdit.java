package ru.spacecorp.mobdekbkp.web.companytypeentry;

import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.data.GroupDatasource;

import javax.inject.Inject;

import ru.spacecorp.mobdekbkp.entity.CompanyType;
import ru.spacecorp.mobdekbkp.entity.CompanyTypeEntry;
import ru.spacecorp.mobdekbkp.web.companytype.CompanyTypeEdit;

public class CompanyTypeEntryEdit extends AbstractEditor<CompanyTypeEntry> {

    @Inject
    private Metadata metadata;

    @Inject
    private GroupDatasource typesDs;

    @Inject
    Messages messages;

    public void onCreateButtonClick() {
        CompanyType companyType = metadata.create(CompanyType.class);
        CompanyTypeEdit companyTypeEdit = (CompanyTypeEdit) openEditor(companyType, WindowManager.OpenType.DIALOG);
        //вызываем экран с добавление "Типа предприятия" и вводим новый тип предприятия
        companyTypeEdit.addCloseListener(e -> {
            typesDs.refresh();
        });
        //добавляем к экрану слушателя, который срабатывает после закрытия экрана и обновляем данные на экране выбора типа предприятия
    }

    @Override
    protected boolean preCommit() { //запрет на добавление повторяющихся значений типа предприятия
        Boolean commited = true;
        if (getItem().getCompany().getTypes() != null) {
            for (CompanyTypeEntry typeEntry : getItem().getCompany().getTypes()) {
                if (typeEntry.getType().toString().equals(getItem().getType().toString())) {
                    showNotification(messages.getMessage("ru.spacecorp.mobdekbkp.web.companytypeentry", "companyTypeExist"), NotificationType.WARNING);
                    commited = false;
                    break;
                } else {
                    commited = true;
                }
            }
        }
        return commited;
    }
}