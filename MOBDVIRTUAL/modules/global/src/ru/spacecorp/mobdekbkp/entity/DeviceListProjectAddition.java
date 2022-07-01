package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.annotation.Listeners;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@Listeners("mobdekbkp_DeviceListProjectAdditionEntityListener")
@NamePattern("%s %s|name,status")
@Table(name = "MOBDEKBKP_DEVICE_LIST_PROJECT_ADDITION")
@Entity(name = "mobdekbkp$DeviceListProjectAddition")
public class DeviceListProjectAddition extends StandardEntity {
    private static final long serialVersionUID = 2847822486281691184L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "STATUS")
    protected String status;


    @OneToMany(mappedBy = "deviceListProjectAddition")
    protected List<DeviceListProjectAdditionEntry> entries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEVICE_LIST_PROJECT_ID")
    protected DeviceListProject deviceListProject;


    @JoinTable(name = "MOBDEKBKP_DEVICE_LIST_PROJECT_ADDITION_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "DEVICE_LIST_PROJECT_ADDITION_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public DeviceListProjectStatus getStatus() {
        return status == null ? null : DeviceListProjectStatus.fromId(status);
    }

    public void setStatus(DeviceListProjectStatus status) {
        this.status = status == null ? null : status.getId();
    }


    public void setDeviceListProject(DeviceListProject deviceListProject) {
        this.deviceListProject = deviceListProject;
    }

    public DeviceListProject getDeviceListProject() {
        return deviceListProject;
    }


    public void setEntries(List<DeviceListProjectAdditionEntry> entries) {
        this.entries = entries;
    }

    public List<DeviceListProjectAdditionEntry> getEntries() {
        return entries;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}