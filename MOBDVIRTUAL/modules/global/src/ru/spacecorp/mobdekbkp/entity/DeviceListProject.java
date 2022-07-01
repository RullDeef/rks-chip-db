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
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.Lob;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@Listeners("mobdekbkp_DeviceListProjectEntityListener")
@NamePattern("%s %s|name,status")
@Table(name = "MOBDEKBKP_DEVICE_LIST_PROJECT")
@Entity(name = "mobdekbkp$DeviceListProject")
public class DeviceListProject extends StandardEntity {
    private static final long serialVersionUID = 7469769204944880326L;

    @Column(name = "NAME")
    protected String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "APPROVAL_DATE")
    protected Date approvalDate;

    @Column(name = "STATUS")
    protected String status;


    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "deviceListProject")
    protected List<DeviceListProjectEntry> entries;

    @NotNull
    @OnDelete(DeletePolicy.UNLINK)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "deviceProjectList", optional = false)
    protected Device device;

    @OneToMany(mappedBy = "deviceListProject")
    protected List<DeviceListProjectAddition> additions;


    @JoinTable(name = "MOBDEKBKP_DEVICE_LIST_PROJECT_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "DEVICE_LIST_PROJECT_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;

    @Lob
    @Column(name = "DEMANDS")
    protected String demands;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }


    public void setDemands(String demands) {
        this.demands = demands;
    }

    public String getDemands() {
        return demands;
    }


    public List<DeviceListProjectEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<DeviceListProjectEntry> entries) {
        this.entries = entries;
    }


    public void setAdditions(List<DeviceListProjectAddition> additions) {
        this.additions = additions;
    }

    public List<DeviceListProjectAddition> getAdditions() {
        return additions;
    }


    public void setDevice(Device device) {
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStatus(DeviceListProjectStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public DeviceListProjectStatus getStatus() {
        return status == null ? null : DeviceListProjectStatus.fromId(status);
    }


}