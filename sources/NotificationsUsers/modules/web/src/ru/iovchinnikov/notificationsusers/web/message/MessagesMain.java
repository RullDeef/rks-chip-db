package ru.iovchinnikov.notificationsusers.web.message;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.notificationsusers.entity.Message;
import ru.iovchinnikov.notificationsusers.service.MessageService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Set;

public class MessagesMain extends AbstractWindow {

    private static final String TAB_SENT = "tabSent";
    private static final String TAB_INBOX = "tabInbox";
    private static final String TAB_RECYCLE = "tabRecycle";
    private static final String TAB_DRAFT = "tabDraft";
    private static final String TABLE_SENT = "sentTable";
    private static final String TABLE_SENT_CREATE = "sentTable.create";
    private static final String TABLE_SENT_EDIT = "sentTable.edit";
    private static final String TABLE_INBOX = "inboxTable";
    private static final String TABLE_INBOX_CREATE = "inboxTable.create";
    private static final String TABLE_INBOX_EDIT = "inboxTable.edit";
    private static final String TABLE_DRAFT = "draftTable";
    private static final String TABLE_DRAFT_EDIT = "draftTable.edit";
    private static final String TABLE_RECYCLE = "recycleTable";
    private static final String TABLE_RECYCLE_CREATE = "recycleTable.create";
    private static final String TABLE_RECYCLE_EDIT = "recycleTable.edit";

    private static final String MESSAGE_VIEW_WINDOW = "notificationsusers$Message.view";
    private static final String MESSAGE_RECYCLE_EDIT_WINDOW = "notificationsusers$Message.recover_edit";
    private static final String MESSAGE_EDIT_WINDOW = "notificationsusers$Message.edit";

    @Named("allTabs") private TabSheet tabSheet;
    @Named(TABLE_INBOX_CREATE) private CreateAction createInbox;
    @Named(TABLE_INBOX_EDIT) private EditAction editInbox;
    @Named(TABLE_SENT_CREATE) private CreateAction createSent;
    @Named(TABLE_SENT_EDIT) private EditAction editSent;
    @Named(TABLE_DRAFT_EDIT) private EditAction editDraft;
    @Named(TABLE_RECYCLE_CREATE) private CreateAction createRecycle;
    @Named(TABLE_RECYCLE_EDIT) private EditAction editRecycle;

    @Named(TABLE_INBOX) private GroupTable<Message> inboxTable;
    @Named(TABLE_SENT) private GroupTable<Message> sentTable;
    @Named(TABLE_DRAFT) private GroupTable<Message> draftTable;
    @Named(TABLE_RECYCLE) private GroupTable<Message> recycleTable;

    @Inject private UserSession userSession;
    @Inject private MessageService messageService;
    @Inject private DataManager dataManager;
    @Inject private ComponentsFactory componentsFactory;

    private void screenRefresh(String id) {
        inboxTable.getDatasource().refresh();
        sentTable.getDatasource().refresh();
        draftTable.getDatasource().refresh();
        recycleTable.getDatasource().refresh();
    }

    @Override
    public void init(Map<String, Object> params) {
        editInbox.setWindowId(MESSAGE_VIEW_WINDOW);
        editSent.setWindowId(MESSAGE_VIEW_WINDOW);
        editDraft.setWindowId(MESSAGE_EDIT_WINDOW);
        editRecycle.setWindowId(MESSAGE_RECYCLE_EDIT_WINDOW);

        createInbox.setEditorCloseListener(this::screenRefresh);
        editInbox.setEditorCloseListener(this::screenRefresh);
        createSent.setEditorCloseListener(this::screenRefresh);
        editSent.setEditorCloseListener(this::screenRefresh);
        editDraft.setEditorCloseListener(this::screenRefresh);
        createRecycle.setEditorCloseListener(this::screenRefresh);
        editRecycle.setAfterCommitHandler(actionId -> screenRefresh("RECOVER"));

        inboxTable.addGeneratedColumn("sender", entity -> {
            Label label = componentsFactory.createComponent(Label.class);
            if (entity.getIsSystem()) {
                label.setValue(messages.getMessage(getClass(), "system"));
            } else {
                label.setValue(entity.getSender());
            }
            return label;
        });
        recycleTable.addGeneratedColumn("sender", entity -> {
            Label label = componentsFactory.createComponent(Label.class);
            if (entity.getIsSystem()) {
                label.setValue(messages.getMessage(getClass(), "system"));
            } else {
                label.setValue(entity.getSender());
            }
            return label;
        });
    }

    private void doRecycle(boolean isUserReceiver, Table<Message> table) {
        Set<Message> selected = table.getSelected();
        for (Message m : selected) {
            if (isUserReceiver) {
                m.getMeta().setIsRead(true);
                m.getMeta().setIsReceiverRec(true);
            } else {
                m.getMeta().setIsSenderRec(true);
            }
            getDsContext().commit();
        }
    }

    private void doDelete(Table<Message> table) {
        String currentLogin = userSession.getUser().getLogin();
        Set<Message> selected = table.getSelected();
        for (Message m : selected) {
            boolean isUserReceiver = m.getRecipient().getLogin().equals(currentLogin);
            if (isUserReceiver) {
                m.getMeta().setIsRead(true);
                m.getMeta().setIsReceiverRec(false);
                m.getMeta().setIsReceiverDel(true);
            } else {
                m.getMeta().setIsSenderRec(false);
                m.getMeta().setIsSenderDel(true);
            }
            getDsContext().commit();
        }
    }


    public void recover() {
        String currentLogin = userSession.getUser().getLogin();
        Set<Message> selected = recycleTable.getSelected();
        for (Message m : selected) {
            boolean isUserReceiver = m.getRecipient().getLogin().equals(currentLogin);
            if (isUserReceiver) {
                m.getMeta().setIsRead(true);
                m.getMeta().setIsReceiverRec(false);
            } else {
                m.getMeta().setIsSenderRec(false);
            }
            getDsContext().commit();
        }
        screenRefresh("RECOVER");
    }

    public void recycle() {
        String source = tabSheet.getTab().getName();
        switch (source) {
            case TAB_SENT:
                doRecycle(false, sentTable);
                break;
            case TAB_INBOX:
                doRecycle(true, inboxTable);
                break;
            case TAB_DRAFT:
                doRecycle(false, draftTable);
                break;
            case TAB_RECYCLE:
                doDelete(recycleTable);
                break;
            default:
                throw new RuntimeException("Unknown tab name: " + source);
        }
        screenRefresh("RECYCLE");
    }

    public void onMultipleBtnRxClick() {
        openWindow("message-multiple-recipients", WindowManager.OpenType.THIS_TAB)
                .addCloseListener(actionId -> screenRefresh("SpamSent"));
    }
}