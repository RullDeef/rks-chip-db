package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.*;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import org.eclipse.persistence.annotations.CascadeOnDelete;

@Listeners("mobdekbkp_DeviceListProjectEntryEntityListener")
@NamePattern("%s|typonominal")
@Table(name = "MOBDEKBKP_DEVICE_LIST_PROJECT_ENTRY")
@Entity(name = "mobdekbkp$DeviceListProjectEntry")
public class DeviceListProjectEntry extends StandardEntity {
    private static final long serialVersionUID = 6018097149165644075L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @Column(name = "STATUS")
    protected String status;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEVICE_LIST_PROJECT_ID")
    protected DeviceListProject deviceListProject;

    @Override
    public String toString() {
        return typonominal.getName();
    }


    public void setDeviceListProject(DeviceListProject deviceListProject) {
        this.deviceListProject = deviceListProject;
    }

    public DeviceListProject getDeviceListProject() {
        return deviceListProject;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }

    public void setStatus(DeviceListEntryStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public DeviceListEntryStatus getStatus() {
        return status == null ? null : DeviceListEntryStatus.fromId(status);
    }


}