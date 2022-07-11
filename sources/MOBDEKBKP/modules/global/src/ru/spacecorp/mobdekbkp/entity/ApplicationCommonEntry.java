package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.ManyToOne;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import java.util.List;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@Listeners("mobdekbkp_ApplicationCommonEntryEntityListener")
@NamePattern("%s|className")
@Table(name = "MOBDEKBKP_APPLICATION_COMMON_ENTRY")
@Entity(name = "mobdekbkp$ApplicationCommonEntry")
public class ApplicationCommonEntry extends StandardEntity {
    private static final long serialVersionUID = -7230698475119294289L;

    @Column(name = "CLASS_NAME")
    protected String className;

    @Lob
    @Column(name = "EVENTS")
    protected String events;

    @Lob
    @Column(name = "CHARACTERISTICS")
    protected String characteristics;

    @Lob
    @Column(name = "PROTOTYPE")
    protected String prototype;

    @Lob
    @Column(name = "POSSIBILITY")
    protected String possibility;


    @JoinTable(name = "MOBDEKBKP_APPLICATION_COMMON_ENTRY_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "APPLICATION_COMMON_ENTRY_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;

    @Column(name = "STATUS")
    protected String status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "APPLICATION_COMMON_DEV_ID")
    protected ApplicationCommonDev applicationCommonDev;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "applicationCommonEntry")
    protected List<ApplicationOkrInfo> okrInfo;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "applicationCommonEntry")
    protected List<ApplicationNewDevEntry> parents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public void setParents(List<ApplicationNewDevEntry> parents) {
        this.parents = parents;
    }

    public List<ApplicationNewDevEntry> getParents() {
        return parents;
    }


    public void setOkrInfo(List<ApplicationOkrInfo> okrInfo) {
        this.okrInfo = okrInfo;
    }

    public List<ApplicationOkrInfo> getOkrInfo() {
        return okrInfo;
    }


    public void setApplicationCommonDev(ApplicationCommonDev applicationCommonDev) {
        this.applicationCommonDev = applicationCommonDev;
    }

    public ApplicationCommonDev getApplicationCommonDev() {
        return applicationCommonDev;
    }


    public void setStatus(ApplicationCommonEntryStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public ApplicationCommonEntryStatus getStatus() {
        return status == null ? null : ApplicationCommonEntryStatus.fromId(status);
    }


    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getEvents() {
        return events;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setPrototype(String prototype) {
        this.prototype = prototype;
    }

    public String getPrototype() {
        return prototype;
    }

    public void setPossibility(String possibility) {
        this.possibility = possibility;
    }

    public String getPossibility() {
        return possibility;
    }


}