package ru.spacecorp.mobdekbkp.web.screens;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.GroupBoxLayout;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.TextField;
import ru.spacecorp.mobdekbkp.entity.SupportInfo;

import javax.inject.Inject;
import java.util.Map;

public class Loginsupportinfo extends AbstractWindow {

    @Inject
    private Label noDataLabel;

    @Inject
    private GroupBoxLayout infoBox;

    @Inject
    private TextField phoneField;

    @Inject
    private TextField mailField;

    @Inject
    private DataManager dataManager;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        SupportInfo supportInfo = dataManager.load(SupportInfo.class).one();
        if (supportInfo != null) {
            phoneField.setValue(supportInfo.getPhone());
            mailField.setValue(supportInfo.getMail());
        } else {
            infoBox.setVisible(false);
            noDataLabel.setVisible(true);
        }
    }
}