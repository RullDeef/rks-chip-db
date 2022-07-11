package ru.iovchinnikov.notificationsusers.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "NOTIFICATIONSUSERS_MESSAGE_META")
@Entity(name = "notificationsusers$MessageMeta")
public class MessageMeta extends StandardEntity {
    private static final long serialVersionUID = -4584706574547025283L;
    @Column(name = "IS_READ", nullable = false)
    protected Boolean isRead = false;

    @Column(name = "IS_SENDER_REC", nullable = false)
    protected Boolean isSenderRec = false;

    @Column(name = "IS_RECEIVER_REC", nullable = false)
    protected Boolean isReceiverRec = false;

    @Column(name = "IS_SENDER_DEL", nullable = false)
    protected Boolean isSenderDel = false;

    @Column(name = "IS_RECEIVER_DEL", nullable = false)
    protected Boolean isReceiverDel = false;

    @Column(name = "IS_SENDER_FAV", nullable = false)
    protected Boolean isSenderFav = false;

    @Column(name = "IS_RECEIVER_FAV", nullable = false)
    protected Boolean isReceiverFav = false;

    public void setIsSenderRec(Boolean isSenderRec) {
        this.isSenderRec = isSenderRec;
    }

    public Boolean getIsSenderRec() {
        return isSenderRec;
    }

    public void setIsReceiverRec(Boolean isReceiverRec) {
        this.isReceiverRec = isReceiverRec;
    }

    public Boolean getIsReceiverRec() {
        return isReceiverRec;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsSenderDel(Boolean isSenderDel) {
        this.isSenderDel = isSenderDel;
    }

    public Boolean getIsSenderDel() {
        return isSenderDel;
    }

    public void setIsReceiverDel(Boolean isReceiverDel) {
        this.isReceiverDel = isReceiverDel;
    }

    public Boolean getIsReceiverDel() {
        return isReceiverDel;
    }

    public void setIsSenderFav(Boolean isSenderFav) {
        this.isSenderFav = isSenderFav;
    }

    public Boolean getIsSenderFav() {
        return isSenderFav;
    }

    public void setIsReceiverFav(Boolean isReceiverFav) {
        this.isReceiverFav = isReceiverFav;
    }

    public Boolean getIsReceiverFav() {
        return isReceiverFav;
    }
}