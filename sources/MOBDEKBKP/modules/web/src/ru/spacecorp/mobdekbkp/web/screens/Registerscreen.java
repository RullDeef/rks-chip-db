package ru.spacecorp.mobdekbkp.web.screens;

import com.haulmont.cuba.gui.components.*;
import ru.iovchinnikov.notificationsusers.service.MessageService;

import javax.inject.Inject;

public class Registerscreen extends AbstractWindow {

    @Inject
    private TextField nameField;
    @Inject
    private TextField surnameField;
    @Inject
    private TextField patronymicField;
    @Inject
    private TextField emailField;
    @Inject
    private TextField phoneField;
    @Inject
    private TextField companyField;
    @Inject
    private TextField positionField;
    @Inject
    private MessageService messageService;

    public void onSendBtnClick() throws ValidationException {
        if (isAllValid()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getMessage("Name"))
                    .append(" ")
                    .append(nameField.getValue().toString())
                    .append(System.lineSeparator())
                    .append(getMessage("Surname"))
                    .append(" ")
                    .append(surnameField.getValue().toString())
                    .append(System.lineSeparator());
            if (patronymicField.getValue() != null) {
                stringBuilder.append(getMessage("Patronymic"))
                        .append(" ")
                        .append(patronymicField.getValue().toString())
                        .append(System.lineSeparator());
            }
            stringBuilder.append(getMessage("Email"))
                    .append(" ")
                    .append(emailField.getValue().toString())
                    .append(System.lineSeparator())
                    .append(getMessage("Phone"))
                    .append(" ")
                    .append(phoneField.getValue().toString())
                    .append(System.lineSeparator())
                    .append(getMessage("Company"))
                    .append(" ")
                    .append(companyField.getValue().toString())
                    .append(System.lineSeparator())
                    .append(getMessage("Position"))
                    .append(" ")
                    .append(positionField.getValue().toString());

            messageService.send(null, "admin", getMessage("Registration"), stringBuilder.toString(), true);
            this.close(Window.COMMIT_ACTION_ID);
        }
    }

    public void onCancelBtnClick() {
        this.close(Window.CLOSE_ACTION_ID);
    }

    private boolean isAllValid() {
        validateAll();
        return nameField.isValid() && surnameField.isValid() && emailField.isValid()
                && patronymicField.isValid() && phoneField.isValid() && companyField.isValid()
                && positionField.isValid();
    }
}