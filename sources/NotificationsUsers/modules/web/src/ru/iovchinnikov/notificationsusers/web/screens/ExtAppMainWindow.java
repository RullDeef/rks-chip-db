package ru.iovchinnikov.notificationsusers.web.screens;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;
import ru.iovchinnikov.notificationsusers.entity.Message;

import javax.inject.Inject;
import java.util.List;

public class ExtAppMainWindow extends AppMainWindow {
    @Inject
    private UserSession userSession;
    @Inject
    private DataManager dataManager;

    @Override
    public void ready() {
        LoadContext<ExtUser> loadContext = LoadContext.create(ExtUser.class)
                .setQuery(LoadContext.createQuery("select e from notificationsusers$ExtUser e where e.login = :login")
                        .setParameter("login", userSession.getUser().getLogin()));
        ExtUser e = dataManager.load(loadContext);
        if (e == null) return;
        if (e.getCompanyRef() == null || e.getEmail() == null || e.getFirstName() == null ||
                e.getLastName() == null || e.getMiddleName() == null || e.getName() == null ||
                e.getPosition() == null || e.getPhone() == null) {
            showNotification(getMessage("userInfo"), NotificationType.WARNING);
        }
        
        LoadContext<Message> ctx = LoadContext.create(Message.class)
                .setQuery(LoadContext.createQuery("select e from notificationsusers$Message e " +
                                                    "where e.recipient.login = :login " +
                                                    "and e.meta.isRead = false " +
                                                    "and e.sent = true " +
                                                    "and (e.meta.isReceiverRec = false or e.meta.isReceiverDel = false)")
                        .setParameter("login", userSession.getUser().getLogin()));
        List<Message> messages = dataManager.loadList(ctx);
        if (messages.size() != 0)
            showNotification(getMessage("unreadMsg"), NotificationType.TRAY);

        super.ready();
    }

    public void messagesBrowse() {
        openWindow("messages-main", WindowManager.OpenType.DIALOG);
    }

    public void onBtnProfileClick() {
        User user = userSession.getUser();
        openEditor("notificationsusers$ExtUser.view", user, WindowManager.OpenType.DIALOG);
    }
}