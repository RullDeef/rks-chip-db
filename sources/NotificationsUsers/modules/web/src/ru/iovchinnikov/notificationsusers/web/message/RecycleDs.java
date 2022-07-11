package ru.iovchinnikov.notificationsusers.web.message;

import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.data.impl.CustomGroupDatasource;
import ru.iovchinnikov.notificationsusers.entity.Message;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Stepanov_ME on 28.05.2019.
 */
public class RecycleDs extends CustomGroupDatasource<Message, UUID> {

    @Override
    protected Collection<Message> getEntities(Map<String, Object> params) {
        LoadContext<Message> loadContext = getCompiledLoadContext();
        List<Message> messageList = dataSupplier.loadList(loadContext);
        messageList = messageList.stream().filter(message ->
                ((message.getSender() != null) && (message.getSender().getId().equals(userSession.getUser().getId())
                        && (message.getMeta().getIsSenderRec())))
                        || ((message.getRecipient() != null)
                        && ((message.getRecipient().getId().equals(userSession.getUser().getId())) && message.getMeta().getIsReceiverRec())))
                .sorted((o1, o2) -> {
                    long o1Time = o1.getMeta().getCreateTs().getTime();
                    long o2Time = o2.getMeta().getCreateTs().getTime();
                    if (o1Time != o2Time) {
                        if (o1Time > o2Time) {
                            return -1;
                        } else {
                            return 1;
                        }
                    } else {
                        if (o1.getMeta().getIsRead()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                })
                .collect(Collectors.toList());
        return messageList;
    }
}
