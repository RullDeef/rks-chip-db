package ru.iovchinnikov.notificationsusers.web.message;

import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;
import ru.iovchinnikov.notificationsusers.entity.Message;
import ru.iovchinnikov.notificationsusers.web.UserDatasource;

import javax.inject.Inject;
import java.util.*;

public class MessageRecoverEdit extends AbstractEditor<Message> {

    @Inject
    private UserSession userSession;
    @Inject
    private LookupField companies;
    @Inject
    private UserDatasource usersDs;
    @Inject
    private LookupField recipient;

    private LoadContext<ExtUser> extUserLoadContext = LoadContext.create(ExtUser.class);

    @Override
    public void ready() {
        Set<String> set = new HashSet<>();
        LoadContext.Query queryTemp = LoadContext.createQuery("select e from notificationsusers$ExtUser e " +
                "where e.login <> :session$userLogin " +
                "and e.login <> 'anonymous'");
        queryTemp.setParameter("session$userLogin", userSession.getAttribute("userLogin"));
        refreshUserDs(queryTemp);

        for (Object u : usersDs.getItems()) {
            if (((ExtUser) u).getCompanyRef() != null && !"".equals(((ExtUser) u).getCompanyRef()))
                set.add(((ExtUser) u).getCompanyRef());
        }
        set.add(((ExtUser) userSession.getUser()).getCompanyRef());

        if (!(set.contains(null) || set.contains(""))) {
            companies.setOptionsList(new ArrayList<>(set));
        }

        companies.addValueChangeListener(e -> {
            String company = (companies.getValue() != null) ? companies.getValue() : "";
            if (companies.getOptionsList().size() == 0) {
                return;
            }
            LoadContext.Query query;
            switch (company) {
                case "": {
                    query = LoadContext.createQuery("select e from notificationsusers$ExtUser e " +
                            "where e.login <> :session$userLogin " +
                            "and e.login <> 'anonymous'");
                    query.setParameter("session$userLogin", userSession.getAttribute("userLogin"));
                    break;
                }
                default: {
                    query = LoadContext.createQuery("select e from notificationsusers$ExtUser e " +
                            "where e.login <> :session$userLogin " +
                            "and e.login <> 'anonymous' " +
                            "and e.companyRef = :company ");
                    query.setParameter("company", company);
                    query.setParameter("session$userLogin", userSession.getAttribute("userLogin"));
                }
            }
            refreshUserDs(query);
            recipient.setValue(null);
        });
        super.ready();
    }

    private void refreshUserDs(LoadContext.Query queryTemp) {
        extUserLoadContext.setQuery(queryTemp);
        usersDs.setLoadContext(extUserLoadContext);
        usersDs.refresh();
    }

    public void onCancelBtnClick() {
        close(CLOSE_ACTION_ID);
    }

    public void onRecycleBtnClick() {
        recover();
        commitAndClose();
    }

    private void recover() {
        String currentLogin = userSession.getUser().getLogin();
        Message m = getItem();
        boolean isUserReceiver = m.getRecipient().getLogin().equals(currentLogin);
        if (isUserReceiver) {
            m.getMeta().setIsRead(true);
            m.getMeta().setIsReceiverRec(false);
        } else {
            m.getMeta().setIsSenderRec(false);
        }
    }
}