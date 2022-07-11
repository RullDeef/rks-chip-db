package ru.spacecorp.mobdekbkp.web.typonominalpurchaseparameters;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.cuba.web.gui.components.WebPickerField;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;
import ru.spacecorp.mobdekbkp.entity.Company;
import ru.spacecorp.mobdekbkp.entity.ExtBoolean;
import ru.spacecorp.mobdekbkp.entity.TyponominalPurchaseParameters;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class TyponominalPurchaseParametersEdit extends AbstractEditor<TyponominalPurchaseParameters> {

    @Inject
    private FieldGroup typonominalPurchaseParametersFieldGroup;

    @Inject
    private UserSession userSession;

    @Inject
    private DataManager dataManager;

    @Override
    public void init(Map<String, Object> params) {
        if (params.get("noedit") != null) {
            if (params.get("noedit").equals(true)) {
                typonominalPurchaseParametersFieldGroup.setEditable(false);
                //setEditable(false) все равно позволяет редактировать поле цены
                //код ниже убирает элемент выбора цены из компонента
                FieldGroup.FieldConfig priceFieldConfig = typonominalPurchaseParametersFieldGroup.getField("cost");
                if (priceFieldConfig != null) {
                    try {
                        WebPickerField pickerField = (WebPickerField) priceFieldConfig.getComponent();
                        if (pickerField != null) {
                            pickerField.removeAllActions();
                        }
                    } catch (ClassCastException e) {
                        throw new RuntimeException("FieldGroup config cast error");
                    }
                }
            }
        }

    }

    @Override
    protected void initNewItem(TyponominalPurchaseParameters item) {
        item.setNeedPermissionsGosdep(ExtBoolean.notSet);
        item.setHasSamples(ExtBoolean.notSet);
        UUID companyId = ((ExtUser) userSession.getUser()).getCompanyId();
        if (companyId == null) {
            //не должно произойти при ожидаемом поведении
            showNotification(getMessage("NoCompany"), NotificationType.WARNING);
            close(Window.CLOSE_ACTION_ID, true);
        } else {
            Company company = dataManager.load(Company.class).id(companyId).one();
            item.setCompany(company);
        }
    }
}