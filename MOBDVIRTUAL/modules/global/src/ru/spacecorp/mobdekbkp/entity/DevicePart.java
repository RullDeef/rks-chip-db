package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.List;
import javax.persistence.OneToMany;

import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@NamePattern("%s|designation")
@Table(name = "MOBDEKBKP_DEVICE_PART")
@Entity(name = "mobdekbkp$DevicePart")
public class DevicePart extends StandardEntity {
    private static final long serialVersionUID = -6730197907383753115L;

    @NotNull
    @Column(name = "DESIGNATION", nullable = false)
    protected String designation;

    @Column(name = "TYPE_", nullable = false)
    protected String type;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEVELOPER_ID")
    protected Company developer;

    @Column(name = "CONSTRUCTOR")
    protected String constructor;

    @Lob
    @Column(name = "DEMANDS")
    protected String demands;


    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "devicePart")
    protected List<DevicePartListPlanned> plannedLists;

    @OneToMany(mappedBy = "devicePart")
    protected List<DevicePartListComplect> complectLists;


    @JoinTable(name = "MOBDEKBKP_DEVICE_PART_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "DEVICE_PART_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public void setComplectLists(List<DevicePartListComplect> complectLists) {
        this.complectLists = complectLists;
    }

    public List<DevicePartListComplect> getComplectLists() {
        return complectLists;
    }


    @Override
    public String toString() {
        return getDesignation();
    }


    public void setPlannedLists(List<DevicePartListPlanned> plannedLists) {
        this.plannedLists = plannedLists;
    }

    public List<DevicePartListPlanned> getPlannedLists() {
        return plannedLists;
    }


    public void setDeveloper(Company developer) {
        this.developer = developer;
    }

    public Company getDeveloper() {
        return developer;
    }

    public void setConstructor(String constructor) {
        this.constructor = constructor;
    }

    public String getConstructor() {
        return constructor;
    }

    public void setDemands(String demands) {
        this.demands = demands;
    }

    public String getDemands() {
        return demands;
    }


    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


}