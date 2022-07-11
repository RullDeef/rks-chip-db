package ru.iovchinnikov.notificationsusers.web.message;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.notificationsusers.entity.Message;
import ru.iovchinnikov.notificationsusers.entity.MessageText;
import ru.iovchinnikov.notificationsusers.service.MessageService;

import javax.inject.Inject;

public class MessageView extends AbstractEditor<Message> {

    private static final String MSG_TEXT_WROTE = "<div></div><div>__________</div><div> %s wrote: </div><div> %s </div>";

    private Message currentMsg;
    private boolean isUserReceiver;

    @Inject private UserSession userSession;
    @Inject private MessageService messageService;
    @Inject private Metadata metadata;
    @Inject private Button btnReport;
    @Inject private Button btnReply;
    @Inject private Button btnForward;
    @Inject private Button btnFavorites;
    @Inject private TextField tfSender;

    @Override
    public void ready() {
        currentMsg = getItem();
        isUserReceiver = isUserReceiver();
        tfSender.setEditable(false);
        if (!isUserReceiver)
            btnReport.setVisible(false);
        if (currentMsg.getIsSystem()) {
            tfSender.setValue("System");
            btnReport.setVisible(false);
            btnReply.setVisible(false);
            btnForward.setVisible(false);
            btnFavorites.setVisible(false);
        } else {
            tfSender.setValue(currentMsg.getSender());
        }
    }

    private void setIsRead() {
        if (isUserReceiver) {
            currentMsg.getMeta().setIsRead(true);
            super.commit();
        }
    }

    @Override
    public boolean close(String actionId) {
        setIsRead();
        return super.close(actionId);
    }

    @Override
    public void commitAndClose() {
        setIsRead();
        super.commitAndClose();
    }

    private boolean isUserReceiver() {
        return currentMsg.getRecipient().getLogin().equals(userSession.getUser().getLogin());
    }

    public void onBtnReplyClick() {
        //TODO add attachments replying
        this.commitAndClose();
        Message newMsg = metadata.create(Message.class);
        MessageText messageText = metadata.create(MessageText.class);
        messageText.setText(String.format(MSG_TEXT_WROTE,
                currentMsg.getSender().getName(), currentMsg.getMessageText().getText()));
        newMsg.setMessageText(messageText);
        newMsg.setSender(userSession.getUser());
        newMsg.setRecipient(currentMsg.getSender());
        newMsg.setSubject("Re: " + currentMsg.getSubject());
        openEditor(newMsg, WindowManager.OpenType.DIALOG);
    }

    public void onBtnRemoveClick() {
        if (isUserReceiver) {
            currentMsg.getMeta().setIsRead(true);
            currentMsg.getMeta().setIsReceiverRec(true);
        } else if (!isUserReceiver) {
            currentMsg.getMeta().setIsSenderRec(true);
            this.commitAndClose();
        } else
            throw new RuntimeException("Unexpected behavior");
        this.close("OK");
    }

    public void onBtnReportClick() {
        String strSystem = getMessage("system");

        String sender = currentMsg.getSender().getLogin();
        String receiver = currentMsg.getRecipient().getLogin();
        String text = String.format(getMessage("msgTxtReport"), receiver, sender, currentMsg.getMessageText().getText());
        FileDescriptor attachment = currentMsg.getAttachment();
        messageService.send(strSystem, "admin", getMessage("msgSpamReport"), text, true, attachment);
        onBtnRemoveClick();
    }

    public void onBtnFwdClick() {
        Message newMsg = metadata.create(Message.class);
        MessageText messageText = metadata.create(MessageText.class);
        messageText.setText(String.format(MSG_TEXT_WROTE,
                currentMsg.getSender().getName(), currentMsg.getMessageText().getText()));
        newMsg.setMessageText(messageText);
        newMsg.setSender(userSession.getUser());
        newMsg.setSubject("Fw: " + currentMsg.getSubject());
        newMsg.setAttachment(currentMsg.getAttachment());
        AbstractEditor editor = openEditor(newMsg, WindowManager.OpenType.DIALOG);
        editor.addCloseListener(actionId -> commitAndClose());
    }

    public void onBtnFavClick() {
        //TODO view archive/favorites method
    }
}