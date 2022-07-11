package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.ManyToOne;
import java.util.List;
import javax.persistence.OneToMany;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@NamePattern("%s %s|name,number")
@Table(name = "MOBDEKBKP_OUTER_LIST_ALLOWING")
@Entity(name = "mobdekbkp$OuterListAllowing")
public class OuterListAllowing extends StandardEntity {
    private static final long serialVersionUID = 5334071089772430956L;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPE_ID")
    protected OuterListType type;

    @Column(name = "NUMBER_", nullable = false)
    protected String number;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "START_DATE", nullable = false)
    protected Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE")
    protected Date endDate;

    @Column(name = "SUBSTITUTING")
    protected String substituting;

    @Column(name = "RELEASE_YEAR", nullable = false)
    protected String releaseYear;

    @Column(name = "IS_MODIFICATION", nullable = false)
    protected String isModification;


    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "outerListAllowing")
    protected List<OuterListAllowingEntry> entries;


    @JoinTable(name = "MOBDEKBKP_OUTER_LIST_ALLOWING_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "OUTER_LIST_ALLOWING_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public void setEntries(List<OuterListAllowingEntry> entries) {
        this.entries = entries;
    }

    public List<OuterListAllowingEntry> getEntries() {
        return entries;
    }


    public void setType(OuterListType type) {
        this.type = type;
    }

    public OuterListType getType() {
        return type;
    }


    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getReleaseYear() {
        return releaseYear;
    }


    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setSubstituting(String substituting) {
        this.substituting = substituting;
    }

    public String getSubstituting() {
        return substituting;
    }

    public void setIsModification(ExtBoolean isModification) {
        this.isModification = isModification == null ? null : isModification.getId();
    }

    public ExtBoolean getIsModification() {
        return isModification == null ? null : ExtBoolean.fromId(isModification);
    }


}