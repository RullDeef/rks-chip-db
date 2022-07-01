package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|key")
@Table(name = "MOBDEKBKP_HANDBOOK_ENTRY")
@Entity(name = "mobdekbkp$HandbookEntry")
public class HandbookEntry extends StandardEntity {
    private static final long serialVersionUID = -636020744336521566L;

    @Column(name = "KEY_")
    protected String key;

    @Lob
    @Column(name = "VALUE_")
    protected String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    protected HandbookEntry parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HANDBOOK_ID")
    protected Handbook handbook;

    public void setParent(HandbookEntry parent) {
        this.parent = parent;
    }

    public HandbookEntry getParent() {
        return parent;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setHandbook(Handbook handbook) {
        this.handbook = handbook;
    }

    public Handbook getHandbook() {
        return handbook;
    }


}