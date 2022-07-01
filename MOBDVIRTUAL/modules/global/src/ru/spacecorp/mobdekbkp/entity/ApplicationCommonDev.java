package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@Listeners("mobdekbkp_ApplicationCommonDevEntityListener")
@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_APPLICATION_COMMON_DEV")
@Entity(name = "mobdekbkp$ApplicationCommonDev")
public class ApplicationCommonDev extends StandardEntity {
    private static final long serialVersionUID = -1618941915378340222L;

    @Column(name = "NAME")
    protected String name;

    @NotNull
    @Column(name = "STATUS", nullable = false)
    protected String status;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "applicationCommonDev")
    protected List<ApplicationCommonEntry> entries;


    @JoinTable(name = "MOBDEKBKP_APPLICATION_COMMON_DEV_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "APPLICATION_COMMON_DEV_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStatus(ApplicationCommonDevStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public ApplicationCommonDevStatus getStatus() {
        return status == null ? null : ApplicationCommonDevStatus.fromId(status);
    }

    public void setEntries(List<ApplicationCommonEntry> entries) {
        this.entries = entries;
    }

    public List<ApplicationCommonEntry> getEntries() {
        return entries;
    }


}