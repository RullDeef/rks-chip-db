package ru.spacecorp.mobdekbkp.web.screens;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.WindowParams;
import com.haulmont.cuba.gui.config.MenuConfig;
import ru.iovchinnikov.notificationsusers.web.screens.ExtAppMainWindow;

import javax.inject.Inject;
import java.util.Map;

public class MainAppMainWindow extends ExtAppMainWindow {

    @Inject
    private MenuConfig menuConfig;

    @Override
    public void ready() {
        super.ready();
        String windowId = "dashboardScreen";
        Map<String, Object> params = ParamsMap.of(
                WindowParams.CAPTION.name(),
                menuConfig.getItemCaption(windowId)
        );
        openWindow(windowId, WindowManager.OpenType.NEW_TAB, params);
    }
}