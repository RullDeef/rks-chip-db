package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

@NamePattern("%s %s|typonominal,linkToListEntry")
@Table(name = "MOBDEKBKP_OUTER_LIST_ALLOWING_ENTRY")
@Entity(name = "mobdekbkp$OuterListAllowingEntry")
public class OuterListAllowingEntry extends StandardEntity {
    private static final long serialVersionUID = -6541838123698407877L;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @Column(name = "LINK_TO_LIST_ENTRY")
    protected String linkToListEntry;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OUTER_LIST_ALLOWING_ID")
    protected OuterListAllowing outerListAllowing;


    public void setOuterListAllowing(OuterListAllowing outerListAllowing) {
        this.outerListAllowing = outerListAllowing;
    }

    public OuterListAllowing getOuterListAllowing() {
        return outerListAllowing;
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }

    public void setLinkToListEntry(String linkToListEntry) {
        this.linkToListEntry = linkToListEntry;
    }

    public String getLinkToListEntry() {
        return linkToListEntry;
    }


}