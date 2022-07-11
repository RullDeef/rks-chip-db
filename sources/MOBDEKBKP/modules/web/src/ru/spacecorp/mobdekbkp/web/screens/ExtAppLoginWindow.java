package ru.spacecorp.mobdekbkp.web.screens;

import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.web.app.loginwindow.AppLoginWindow;

public class ExtAppLoginWindow extends AppLoginWindow {

    public void onRegisterBtnClick() {
        openWindow("registerScreen", WindowManager.OpenType.DIALOG);
    }

    public void onHelpBtnClick() {
        openWindow("loginSupportInfo", WindowManager.OpenType.DIALOG);
    }
}