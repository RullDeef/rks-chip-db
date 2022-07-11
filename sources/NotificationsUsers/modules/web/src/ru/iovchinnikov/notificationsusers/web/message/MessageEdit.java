package ru.iovchinnikov.notificationsusers.web.message;

import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;
import ru.iovchinnikov.notificationsusers.entity.Message;
import ru.iovchinnikov.notificationsusers.entity.MessageMeta;
import ru.iovchinnikov.notificationsusers.entity.MessageText;
import ru.iovchinnikov.notificationsusers.web.UserDatasource;

import javax.inject.Inject;
import java.util.*;

public class MessageEdit extends AbstractEditor<Message> {

    @Inject
    private Metadata metadata;
    @Inject
    private UserSession userSession;
    @Inject
    private FieldGroup fieldGroup;
    @Inject
    private RichTextArea messageTextTextArea;
    @Inject
    private LookupField companies;
    @Inject
    private UserDatasource usersDs;
    @Inject
    private LookupField recipient;
    @Inject
    private Datasource<Message> messageDs;

    private LoadContext<ExtUser> extUserLoadContext = LoadContext.create(ExtUser.class);

    private boolean isNew = false;

    //необходим для игнорирования установки предприятия в лукапе при выборе пользователя
    //TODO переделать без дополнительных маркеров
    private boolean systemChange = false;

    @Override
    protected void initNewItem(Message item) {
        isNew = true;
        fieldGroup.setEditable(true);
        messageTextTextArea.setEditable(true);
        //TODO определить зачем происходит отключение от датасорса
        messageTextTextArea.setDatasource(null, null);
        /*в случае пересылания сообщения создается новое сообщение,
         необходимо перенести значение MessageText, так как TextArea отключается от датасорса*/
        MessageText messageText = item.getMessageText();
        if (messageText != null) {
            messageTextTextArea.setValue(messageText.getText());
        }
        item.setSent(false);
    }

    @Override
    protected void postInit() {
        super.postInit();
        if (!getItem().getSent()) {
            fieldGroup.setEditable(true);
            messageTextTextArea.setEditable(true);
        }
    }

    @Override
    public boolean preCommit() {
        if (isNew) {
            MessageText mt = metadata.create(MessageText.class);
            mt.setText(messageTextTextArea.getValue());
            getItem().setMessageText(mt);
            getItem().setMeta(metadata.create(MessageMeta.class));
            getItem().setSender(userSession.getUser());
            getItem().setIsSystem(false);
        }
        return super.preCommit();
    }

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
            if (!systemChange) {
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
            } else {
                systemChange = false;
            }
        });

        /*Используется маркер, определяющий как было изменено предприятие получателя.
            В случае системного изменения маркер используется для предотвращения исполнения слушателя изменения предприятия.*/
        recipient.addValueChangeListener(e -> {
            ExtUser user = (ExtUser) messageDs.getItem().getRecipient();
            if (user != null) {
                String company = user.getCompanyRef();
                if (company != null) {
                    //проверка на случай рассинхронизации значений в лукапе и данных о пользователях
                    //TODO проверить жизненный цикл и убрать
                    List<String> valuesList = companies.getOptionsList();
                    String companyValue = valuesList.stream()
                            .filter(o -> o.contentEquals(company))
                            .findFirst().orElse("");
                    if ((companies.getValue() == null) || (!companies.getValue().toString().contentEquals(companyValue))) {
                        systemChange = true;
                        companies.setValue(companyValue);
                    }
                } else {
                    if (companies.getValue() != null) {
                        systemChange = true;
                        companies.setValue(null);
                    }
                }
            }
        });
        super.ready();
    }

    private void refreshUserDs(LoadContext.Query queryTemp) {
        extUserLoadContext.setQuery(queryTemp);
        usersDs.setLoadContext(extUserLoadContext);
        usersDs.refresh();
    }


    public void onSendBtnClick() {
        //проверка заполненности обязательных полей (выполняется только при отправке, а не сохранении в черновики)
        if ((getItem().getRecipient() != null) && ((getItem().getSubject() != null) && !getItem().getSubject().contentEquals(""))
                && ((messageTextTextArea.getValue() != null) && !messageTextTextArea.getValue().contentEquals(""))) {
            getItem().setSent(true);
            commitAndClose();
        } else {
            showNotification(getMessage("enterData"), NotificationType.WARNING);
        }
    }

    public void onSaveBtnClick() {
        commitAndClose();
    }

    public void onCancelBtnClick() {
        close(CLOSE_ACTION_ID);
    }
}