package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.annotation.Listeners;

import java.util.List;
import javax.persistence.OneToMany;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@Listeners("mobdekbkp_DeviceComplectEntityListener")
@NamePattern("%s|complectNumber")
@Table(name = "MOBDEKBKP_DEVICE_COMPLECT")
@Entity(name = "mobdekbkp$DeviceComplect")
public class DeviceComplect extends StandardEntity {
    private static final long serialVersionUID = 6975854985475469862L;

    @Column(name = "COMPLECT_NUMBER", nullable = false)
    protected String complectNumber;

    @Column(name = "PROGRESS")
    protected Double progress;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEVICE_ID")
    protected Device device;

    @OneToMany(mappedBy = "deviceComplect")
    protected List<DevicePartListComplect> partListComplect;

    @Column(name = "STATUS", nullable = false)
    protected String status;


    @JoinTable(name = "MOBDEKBKP_DEVICE_COMPLECT_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "DEVICE_COMPLECT_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public void setStatus(DeviceComplectListStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public DeviceComplectListStatus getStatus() {
        return status == null ? null : DeviceComplectListStatus.fromId(status);
    }

    public void setPartListComplect(List<DevicePartListComplect> partListComplect) {
        this.partListComplect = partListComplect;
    }

    public List<DevicePartListComplect> getPartListComplect() {
        return partListComplect;
    }


    public void setDevice(Device device) {
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

    public void setComplectNumber(String complectNumber) {
        this.complectNumber = complectNumber;
    }

    public String getComplectNumber() {
        return complectNumber;
    }

    @MetaProperty
    public Double getProgress() {
        if (getPartListComplect() == null || getPartListComplect().size() == 0) return 0.0;
        Double rslt = 0.0;
        for (DevicePartListComplect e : getPartListComplect()) {
            rslt += (e.getProgress() != null) ? e.getProgress() : 0;
        }
        return rslt / getPartListComplect().size();
    }
}