package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Lob;

import com.haulmont.chile.core.annotations.NamePattern;

import java.util.List;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@NamePattern("%s %s %s|id,suggested,rationale")
@Table(name = "MOBDEKBKP_DEVICE_LIST_PROJECT_APPLICATION")
@Entity(name = "mobdekbkp$DeviceListProjectApplication")
public class DeviceListProjectApplication extends StandardEntity {
    private static final long serialVersionUID = -7260709616733859095L;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SUGGESTED_ID")
    protected Typonominal suggested;

    @Lob
    @Column(name = "RATIONALE", nullable = false)
    protected String rationale;


    @JoinTable(name = "MOBDEKBKP_DEVICE_LIST_PROJECT_APPLICATION_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "DEVICE_LIST_PROJECT_APPLICATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public void setRationale(String rationale) {
        this.rationale = rationale;
    }

    public String getRationale() {
        return rationale;
    }


    public void setSuggested(Typonominal suggested) {
        this.suggested = suggested;
    }

    public Typonominal getSuggested() {
        return suggested;
    }


}