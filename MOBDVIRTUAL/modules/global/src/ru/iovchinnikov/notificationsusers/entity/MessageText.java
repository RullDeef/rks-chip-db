package ru.iovchinnikov.notificationsusers.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "NOTIFICATIONSUSERS_MESSAGE_TEXT")
@Entity(name = "notificationsusers$MessageText")
public class MessageText extends StandardEntity {
    private static final long serialVersionUID = 918621928687867270L;

    @Lob
    @Column(name = "TEXT")
    protected String text;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}