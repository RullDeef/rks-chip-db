package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.haulmont.cuba.core.entity.annotation.Listeners;

import javax.persistence.ManyToOne;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@Listeners("mobdekbkp_DevicePartListComplectEntityListener")
@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_DEVICE_PART_LIST_COMPLECT")
@Entity(name = "mobdekbkp$DevicePartListComplect")
public class DevicePartListComplect extends StandardEntity {
    private static final long serialVersionUID = -4770699420106993630L;

    @Column(name = "NAME")
    protected String name;

    @OneToMany(mappedBy = "devicePartListComplect")
    protected List<DevicePartListComplectEntry> entries;


    @Column(name = "STATUS")
    protected String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEVICE_COMPLECT_ID")
    protected DeviceComplect deviceComplect;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEVICE_PART_ID")
    protected DevicePart devicePart;


    @JoinTable(name = "MOBDEKBKP_DEVICE_PART_LIST_COMPLECT_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "DEVICE_PART_LIST_COMPLECT_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public void setDevicePart(DevicePart devicePart) {
        this.devicePart = devicePart;
    }

    public DevicePart getDevicePart() {
        return devicePart;
    }


    public void setDeviceComplect(DeviceComplect deviceComplect) {
        this.deviceComplect = deviceComplect;
    }

    public DeviceComplect getDeviceComplect() {
        return deviceComplect;
    }


    public DevicePartListComplectStatus getStatus() {
        return status == null ? null : DevicePartListComplectStatus.fromId(status);
    }

    public void setStatus(DevicePartListComplectStatus status) {
        this.status = status == null ? null : status.getId();
    }


    public void setEntries(List<DevicePartListComplectEntry> entries) {
        this.entries = entries;
    }

    public List<DevicePartListComplectEntry> getEntries() {
        return entries;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @MetaProperty
    public Double getProgress() {
        if (getEntries() == null || getEntries().size() == 0) return 0.0;
        Double rslt = 0.0;
        for (DevicePartListComplectEntry e : getEntries()) {
            rslt += anyToDouble(e.amountDelivered, false) / anyToDouble(e.amountTotal, true);
        }
        return rslt / getEntries().size();
    }

    private Double anyToDouble(Integer i, boolean isDivisor) {
        if (i == null || i == 0)
            return isDivisor ? 1.0 : 0.0;
        else
            return i.doubleValue();
    }

}