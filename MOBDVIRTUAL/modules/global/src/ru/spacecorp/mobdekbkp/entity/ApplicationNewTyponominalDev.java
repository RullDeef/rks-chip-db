package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.validation.constraints.NotNull;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import ru.spacecorp.documents.entity.Document;

@NamePattern("%s|characteristics")
@Table(name = "MOBDEKBKP_APPLICATION_NEW_TYPONOMINAL_DEV")
@Entity(name = "mobdekbkp$ApplicationNewTyponominalDev")
public class ApplicationNewTyponominalDev extends StandardEntity {
    private static final long serialVersionUID = 1765039253117504545L;

    @Column(name = "CLASS_NAME")
    protected String className;

    @Lob
    @Column(name = "EVENT")
    protected String event;

    @Lob
    @Column(name = "CHARACTERISTICS")
    protected String characteristics;

    @Lob
    @Column(name = "PROTOTYPE")
    protected String prototype;

    @Lob
    @Column(name = "POSSIBILITY")
    protected String possibility;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_ID")
    protected Document document;


    @NotNull
    @Column(name = "STATUS", nullable = false)
    protected String status;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMON_APPLICATION_ID")
    protected ApplicationCommonEntry commonApplication;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }


    public ApplicationCommonEntry getCommonApplication() {
        return commonApplication;
    }

    public void setCommonApplication(ApplicationCommonEntry commonApplication) {
        this.commonApplication = commonApplication;
    }


    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getCharacteristics() {
        return characteristics;
    }


    public void setStatus(ApplicationNewTyponominalDevStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public ApplicationNewTyponominalDevStatus getStatus() {
        return status == null ? null : ApplicationNewTyponominalDevStatus.fromId(status);
    }


    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setPossibility(String possibility) {
        this.possibility = possibility;
    }

    public String getPossibility() {
        return possibility;
    }

    public void setPrototype(String prototype) {
        this.prototype = prototype;
    }

    public String getPrototype() {
        return prototype;
    }


}