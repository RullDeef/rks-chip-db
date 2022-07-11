package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.config.defaults.Default;
import com.haulmont.cuba.core.config.defaults.DefaultBoolean;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import java.util.List;

import org.eclipse.persistence.annotations.CascadeOnDelete;
import ru.spacecorp.documents.entity.Document;

@NamePattern("%s|designation")
@Table(name = "MOBDEKBKP_DEVICE")
@Entity(name = "mobdekbkp$Device")
public class Device extends StandardEntity {
    private static final long serialVersionUID = 4965805134257584031L;

    @Column(name = "DESIGNATION", nullable = false)
    protected String designation;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEVELOPER_ID")
    protected Company developer;

    @Column(name = "GENERAL_CONSTRUCTOR")
    protected String generalConstructor;

    @Lob
    @Column(name = "DEMANDS")
    protected String demands;


    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "device")
    protected List<DevicePartsEntry> parts;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEVICE_PROJECT_LIST_ID")
    protected DeviceListProject deviceProjectList;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "device")
    protected List<DeviceComplect> complects;

    @Column(name = "IS_APPROVED", nullable = false)
    protected Boolean isApproved = false;


    @JoinTable(name = "MOBDEKBKP_DEVICE_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "DEVICE_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;
    @JoinTable(name = "MOBDEKBKP_DEVICE_DEVICE_FILTER_CONDITIONS_LINK",
        joinColumns = @JoinColumn(name = "DEVICE_ID"),
        inverseJoinColumns = @JoinColumn(name = "DEVICE_FILTER_CONDITIONS_ID"))
    @ManyToMany
    protected List<DeviceFilterConditions> filterConditions;




    public void setFilterConditions(List<DeviceFilterConditions> filterConditions) {
        this.filterConditions = filterConditions;
    }

    public List<DeviceFilterConditions> getFilterConditions() {
        return filterConditions;
    }


    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }






    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }


    public void setComplects(List<DeviceComplect> complects) {
        this.complects = complects;
    }

    public List<DeviceComplect> getComplects() {
        return complects;
    }


    public List<DevicePartsEntry> getParts() {
        return parts;
    }

    public void setParts(List<DevicePartsEntry> parts) {
        this.parts = parts;
    }


    public void setDeviceProjectList(DeviceListProject deviceProjectList) {
        this.deviceProjectList = deviceProjectList;
    }

    public DeviceListProject getDeviceProjectList() {
        return deviceProjectList;
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

    public void setDeveloper(Company developer) {
        this.developer = developer;
    }

    public Company getDeveloper() {
        return developer;
    }

    public void setGeneralConstructor(String generalConstructor) {
        this.generalConstructor = generalConstructor;
    }

    public String getGeneralConstructor() {
        return generalConstructor;
    }


}