package ru.iovchinnikov.talks.listener;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Messages;
import org.springframework.stereotype.Component;
import ru.iovchinnikov.notificationsusers.service.MessageService;
import ru.iovchinnikov.talks.entity.Comment;
import ru.iovchinnikov.talks.entity.CommentPreference;
import ru.iovchinnikov.talks.entity.CommentPreferenceType;
import ru.iovchinnikov.talks.entity.CommentStatus;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;

import javax.inject.Inject;

import com.haulmont.cuba.core.listener.BeforeAttachEntityListener;

import java.util.UUID;

@Component("discuss_CommentListener")
public class CommentListener implements BeforeInsertEntityListener<Comment>, BeforeUpdateEntityListener<Comment>, BeforeAttachEntityListener<Comment> {


    @Inject
    private MessageService messageService;
    @Inject
    private Messages messages;
    @Inject
    private DataManager dataManager;

    @Override
    public void onBeforeInsert(Comment entity, EntityManager entityManager) {
        setStatus(entity);
        entity.setModerated(false);
    }

    @Override
    public void onBeforeUpdate(Comment entity, EntityManager entityManager) {
        //проверка, был ли комментарий модерирован
        if (!entity.getModeratedContent().contentEquals(entity.getContents())) {
            entity.setModerated(true);
        }
        else {
            if (isEditedByAuthor(entity)) {
                setStatus(entity);
            }
        }
    }


    @Override
    public void onBeforeAttach(Comment entity) {
        Comment old = getOldComment(entity.getId());
        if (old.getCommentStatus() != entity.getCommentStatus()) {
            switch (entity.getCommentStatus()) {
                case deleted: {
                    messageService.send(null, entity.getCreatedBy(), messages.getMainMessage("CommentDeleted"),
                            messages.getMainMessage("CommentDeleted") + " " + entity.getContents(),true);
                    break;
                }
                case rejected: {
                    messageService.send(null, entity.getCreatedBy(), messages.getMainMessage("CommentRejected"),
                            messages.getMainMessage("CommentRejected") + " " + entity.getContents(), true);
                    break;
                }
            }
        }
    }

    private void setStatus(Comment entity){
        DataManager dataManager = AppBeans.get(DataManager.class);
        LoadContext<CommentPreference> cplc = LoadContext.create(CommentPreference.class);
        cplc.setQuery(LoadContext.createQuery("select e from discuss$CommentPreference e"));
        CommentPreference commentPreference = dataManager.load(cplc);
        if (commentPreference != null) {
            if (commentPreference.getModerationType() == CommentPreferenceType.Premoderation) {
                entity.setCommentStatus(CommentStatus.notApproved);
            } else {
                entity.setCommentStatus(CommentStatus.approved);
            }
        }
    }

    private Comment getOldComment(UUID id){
        LoadContext<Comment> commentLoadContext = LoadContext.create(Comment.class);
        commentLoadContext.setId(id);
        return dataManager.load(commentLoadContext);
    }

    private boolean isEditedByAuthor(Comment entity){
        //ниже обрабатывается случай изменения комментария его автором, за исключением случая удаленного комментария
        Comment old=getOldComment(entity.getId());
        //если комментарий не был подтвержден ранее, то ничего не менять
        if(old.getCommentStatus()!=CommentStatus.notApproved) {
            //проверка, что комментарий не был удален или отклонен
            if (entity.getCommentStatus() == CommentStatus.approved) {
                //если комментарий не был восстановлен после удаления
                if (old.getCommentStatus() != CommentStatus.deleted) {
                    //установка статуса в соответствии с типом модерации (только для случая изменения автором)
                    return true;
                }
            }
        }
        return false;
    }
}