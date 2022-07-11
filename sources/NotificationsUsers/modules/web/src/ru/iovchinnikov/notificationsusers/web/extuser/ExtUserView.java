package ru.iovchinnikov.notificationsusers.web.extuser;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.gui.components.MaskedField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;

import javax.inject.Inject;
import javax.inject.Named;

public class ExtUserView extends AbstractEditor<ExtUser> {
    @Named("fieldGroup.login") private TextField loginField;
    @Named("fieldGroup.companyRef") private TextField companyRefField;
    @Named("fieldGroup.email") private TextField emailField;
    @Named("fieldGroup.firstName") private TextField firstNameField;
    @Named("fieldGroup.lastName") private TextField lastNameField;
    @Named("fieldGroup.middleName") private TextField middleNameField;
    @Named("fieldGroup.name") private TextField nameField;
    @Named("fieldGroup.position") private TextField positionField;
    @Inject private TextField phAdd;
    @Inject private MaskedField phone;
    @Inject private UserSession userSession;


    void setEditable() {
        TextField[] all = {companyRefField, emailField, firstNameField,
                lastNameField, middleNameField, nameField,
                positionField, phAdd};

        for (int i = 0; i < all.length; i++) {
            if ("".equals(all[i].getRawValue()))
                all[i].setEditable(true);
        }
        phone.setEditable(true);
    }

    @Override
    public void ready() {
        super.ready();
        setEditable();
        if (userSession.getUser().getLogin().equals(loginField.getRawValue())) {
            phone.setEditable(true);
            emailField.setEditable(true);
        }
    }
}