package ru.iovchinnikov.notificationsusers.web.message;

import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;
import ru.iovchinnikov.notificationsusers.service.MessageService;

import javax.inject.Inject;
import java.util.*;

public class MessageMultipleRecipients extends AbstractWindow {
    @Inject private TwinColumn tcSelectedUsers;
    @Inject private MessageService messageService;
    @Inject private UserSession userSession;
    @Inject private TextField tfTheme;
    @Inject private RichTextArea rtaText;
    @Inject private LookupField companies;

    LoadContext<ExtUser> extUserLoadContext = LoadContext.create(ExtUser.class);

    public void onBtnSendClick() {
        Set l = tcSelectedUsers.getValue();
        for (Object o : l) {
            User current = (User) o;
            messageService.send(userSession.getUser().getLogin(), current.getLogin(), tfTheme.getRawValue(), rtaText.getValue(), true);
        }
        this.close("Sent");
    }

    public void onBtnCancelClick() {
        this.close("Cancelled");
    }

    @Override
    public void ready() {
        //для ввода фильра по предприятиям необходимо задать кастомный класс реализации для userDs
        /*LoadContext.Query queryTemp = LoadContext.createQuery("select e from notificationsusers$ExtUser e " +
                "where e.login <> :session$userLogin " +
                "and e.login <> 'anonymous'");
        queryTemp.setParameter("session$userLogin", userSession.getAttribute("userLogin"));
        extUserLoadContext.setQuery(queryTemp);
        queryService.setExtUserLoadContext(extUserLoadContext);
        userDs.refresh();
        List<String> list;
        Set<String> set = new HashSet<>();
        for (ExtUser u : userDs.getItems()) {
            if (u.getCompanyRef() != null)
                set.add(u.getCompanyRef());
        }
        set.add(((ExtUser) userSession.getUser()).getCompanyRef());
        list = new ArrayList<>(set);
        companies.setOptionsList(list);
        companies.addValueChangeListener(e -> {
            String company = (companies.getValue() != null) ? companies.getValue() : "";
            LoadContext.Query query;
            switch (company) {
                case "":
                    query = LoadContext.createQuery("select e from notificationsusers$ExtUser e " +
                            "where e.login <> :session$userLogin " +
                            "and e.login <> 'anonymous'");
                    query.setParameter("session$userLogin", userSession.getAttribute("userLogin"));
                    extUserLoadContext.setQuery(query);
                    queryService.setExtUserLoadContext(extUserLoadContext);
                    break;
                default:
                    query = LoadContext.createQuery("select e from notificationsusers$ExtUser e " +
                            "where e.login <> :session$userLogin " +
                            "and e.login <> 'anonymous' " +
                            "and e.companyRef =:company ");
                    query.setParameter("company", company);
                    query.setParameter("session$userLogin", userSession.getAttribute("userLogin"));
                    extUserLoadContext.setQuery(query);
                    queryService.setExtUserLoadContext(extUserLoadContext);
            }
            userDs.refresh();
            tcSelectedUsers.setValue(null);
        });*/
        super.ready();
    }
}