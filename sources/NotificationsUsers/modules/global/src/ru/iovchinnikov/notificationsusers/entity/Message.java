package ru.iovchinnikov.notificationsusers.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.core.entity.FileDescriptor;

@NamePattern("%s: |sender,subject")
@Table(name = "NOTIFICATIONSUSERS_MESSAGE")
@Entity(name = "notificationsusers$Message")
public class Message extends StandardEntity {
    private static final long serialVersionUID = -8923539306918602084L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID")
    protected User sender;

    @Column(name = "SENT")
    protected Boolean sent;

    @NotNull
    @Column(name = "IS_SYSTEM", nullable = false)
    protected Boolean isSystem = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECIPIENT_ID")
    protected User recipient;

    @Column(name = "SUBJECT")
    protected String subject;

    @Column(name = "ENTITY_REFERENCE")
    protected String entityReference;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MESSAGE_TEXT_ID")
    protected MessageText messageText;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "META_ID")
    protected MessageMeta meta;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ATTACHMENT_ID")
    protected FileDescriptor attachment;

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public Boolean getSent() {
        return sent;
    }


    public void setAttachment(FileDescriptor attachment) {
        this.attachment = attachment;
    }

    public FileDescriptor getAttachment() {
        return attachment;
    }


    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public Boolean getIsSystem() {
        return isSystem;
    }


    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getSender() {
        return sender;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setMeta(MessageMeta meta) {
        this.meta = meta;
    }

    public MessageMeta getMeta() {
        return meta;
    }

    public void setMessageText(MessageText messageText) {
        this.messageText = messageText;
    }

    public MessageText getMessageText() {
        return messageText;
    }

    public void setEntityReference(String entityReference) {
        this.entityReference = entityReference;
    }

    public String getEntityReference() {
        return entityReference;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

}