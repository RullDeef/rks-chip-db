package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.cuba.core.entity.FileDescriptor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|handbook")
@Table(name = "MOBDEKBKP_HANDBOOK_CAD")
@Entity(name = "mobdekbkp$HandbookCad")
public class HandbookCad extends StandardEntity {
    private static final long serialVersionUID = 7802718907928334078L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HANDBOOK_ID")
    protected FileDescriptor handbook;

    public void setHandbook(FileDescriptor handbook) {
        this.handbook = handbook;
    }

    public FileDescriptor getHandbook() {
        return handbook;
    }


}