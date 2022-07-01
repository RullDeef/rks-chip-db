package ru.spacecorp.mobdekbkp.web.devicelistproject;

import com.haulmont.cuba.gui.components.AbstractFrame;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.TextField;

import javax.inject.Inject;
import java.util.Map;

public class Faddname extends AbstractWindow {
    @Inject
    private Label labelName;
    @Inject
    private TextField textField;
    private String name = "";

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        labelName.setValue(params.get("name"));
    }

    public String getAddName() {
        return name;
    }

    public void onBtOkClick() {
        name = textField.getValue();
        this.close("exit");
    }

    public void onBtCancelClick() {
        this.close("exit");
    }
}