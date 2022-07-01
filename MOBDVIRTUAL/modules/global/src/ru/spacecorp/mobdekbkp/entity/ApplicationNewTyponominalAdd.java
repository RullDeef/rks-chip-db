package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import java.util.List;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@NamePattern("%s %s|designation,producer")
@Table(name = "MOBDEKBKP_APPLICATION_NEW_TYPONOMINAL_ADD")
@Entity(name = "mobdekbkp$ApplicationNewTyponominalAdd")
public class ApplicationNewTyponominalAdd extends StandardEntity {
    private static final long serialVersionUID = 8389830668086453413L;

    @Lob
    @Column(name = "DESIGNATION")
    protected String designation;

    @Column(name = "STATUS")
    protected String status;

    @Lob
    @Column(name = "DELIVERY_DOC_DESIGNATION")
    protected String deliveryDocDesignation;

    @Column(name = "CLASS_MOP")
    protected String classMop;

    @Column(name = "PRODUCER")
    protected String producer;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;


    @JoinTable(name = "MOBDEKBKP_APPLICATION_NEW_TYPONOMINAL_ADD_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "APPLICATION_NEW_TYPONOMINAL_ADD_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public void setStatus(ApplicationNewTyponominalAddStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public ApplicationNewTyponominalAddStatus getStatus() {
        return status == null ? null : ApplicationNewTyponominalAddStatus.fromId(status);
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDeliveryDocDesignation(String deliveryDocDesignation) {
        this.deliveryDocDesignation = deliveryDocDesignation;
    }

    public String getDeliveryDocDesignation() {
        return deliveryDocDesignation;
    }

    public void setClassMop(String classMop) {
        this.classMop = classMop;
    }

    public String getClassMop() {
        return classMop;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getProducer() {
        return producer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}