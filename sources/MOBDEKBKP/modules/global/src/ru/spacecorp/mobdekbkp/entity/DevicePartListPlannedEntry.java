package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;

@NamePattern("%s|typonominal")
@Table(name = "MOBDEKBKP_DEVICE_PART_LIST_PLANNED_ENTRY")
@Entity(name = "mobdekbkp$DevicePartListPlannedEntry")
public class DevicePartListPlannedEntry extends StandardEntity {
    private static final long serialVersionUID = -6224579019186791111L;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @Column(name = "STATUS")
    protected String status;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEVICE_PART_LIST_PLANNED_INVERSE_ID")
    protected DevicePartListPlanned devicePartListPlannedInverse;

    @Override
    public String toString(){
        return typonominal.getName();
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }





    public void setDevicePartListPlannedInverse(DevicePartListPlanned devicePartListPlannedInverse) {
        this.devicePartListPlannedInverse = devicePartListPlannedInverse;
    }

    public DevicePartListPlanned getDevicePartListPlannedInverse() {
        return devicePartListPlannedInverse;
    }


    public void setStatus(DeviceListEntryStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public DeviceListEntryStatus getStatus() {
        return status == null ? null : DeviceListEntryStatus.fromId(status);
    }





}