package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@Listeners("mobdekbkp_DevicePartListPlannedEntityListener")
@NamePattern("%s %s|name,createdBy")
@Table(name = "MOBDEKBKP_DEVICE_PART_LIST_PLANNED")
@Entity(name = "mobdekbkp$DevicePartListPlanned")
public class DevicePartListPlanned extends StandardEntity {
    private static final long serialVersionUID = 1150364203730358078L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "STATUS")
    protected String status;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "devicePartListPlannedInverse")
    protected List<DevicePartListPlannedEntry> entries;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEVICE_PART_ID")
    protected DevicePart devicePart;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEVICE_ID")
    protected Device device;


    @JoinTable(name = "MOBDEKBKP_DEVICE_PART_LIST_PLANNED_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "DEVICE_PART_LIST_PLANNED_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public void setDevice(Device device) {
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

    @Override
    public String toString() {
        return getName();
    }


    public void setDevicePart(DevicePart devicePart) {
        this.devicePart = devicePart;
    }

    public DevicePart getDevicePart() {
        return devicePart;
    }


    public void setEntries(List<DevicePartListPlannedEntry> entries) {
        this.entries = entries;
    }

    public List<DevicePartListPlannedEntry> getEntries() {
        return entries;
    }


    public void setStatus(DevicePartListPlannedStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public DevicePartListPlannedStatus getStatus() {
        return status == null ? null : DevicePartListPlannedStatus.fromId(status);
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}