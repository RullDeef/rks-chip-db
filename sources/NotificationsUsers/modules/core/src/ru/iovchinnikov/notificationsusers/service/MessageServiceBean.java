package ru.iovchinnikov.notificationsusers.service;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Service;
import ru.iovchinnikov.notificationsusers.entity.Message;
import ru.iovchinnikov.notificationsusers.entity.MessageMeta;
import ru.iovchinnikov.notificationsusers.entity.MessageText;

import javax.inject.Inject;

@Service(MessageService.NAME)
public class MessageServiceBean implements MessageService {

    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;

    @Override
    public void send(String sender, String receiver, String subject, String text, boolean sent) {
        Message newMsg = createMessage(sender, receiver, subject, text, sent);
        dataManager.commit(newMsg);
    }

    @Override
    public void send(String sender, String receiver, String subject, String text, boolean sent, FileDescriptor file) {
        Message newMsg = createMessage(sender, receiver, subject, text, sent);
        newMsg.setAttachment(file);
        dataManager.commit(newMsg);
    }

    private Message createMessage(String sender, String receiver, String subject, String text, boolean sent) {
        Message newMsg = metadata.create(Message.class);
        newMsg.setMessageText(metadata.create(MessageText.class));
        newMsg.setMeta(metadata.create(MessageMeta.class));
        User receiverUser = getUser(receiver);
        User senderUser = getUser(sender);
        if (senderUser != null) newMsg.setSender(senderUser);
        newMsg.setIsSystem(senderUser == null);
        newMsg.setRecipient(receiverUser);
        newMsg.setSubject(subject);
        newMsg.getMessageText().setText(text);
        newMsg.setSent(sent);
        return newMsg;
    }

    private User getUser(String receiver) {
        LoadContext<User> loadContext = LoadContext.create(User.class)
                .setQuery(LoadContext.createQuery("select p from sec$User p where p.login = :username")
                        .setParameter("username", receiver));
        return dataManager.load(loadContext);
    }

}