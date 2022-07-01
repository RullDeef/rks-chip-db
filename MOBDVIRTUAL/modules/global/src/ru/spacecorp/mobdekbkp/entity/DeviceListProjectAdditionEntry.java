package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Listeners;

import javax.persistence.OneToOne;

@NamePattern("%s %s|edited,newtyponominal")
@Table(name = "MOBDEKBKP_DEVICE_LIST_PROJECT_ADDITION_ENTRY")
@Entity(name = "mobdekbkp$DeviceListProjectAdditionEntry")
public class DeviceListProjectAdditionEntry extends StandardEntity {
    private static final long serialVersionUID = -1994177026945669266L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EDITED_ID")
    protected Typonominal edited;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NEWTYPONOMINAL_ID")
    protected Typonominal newtyponominal;

    @Column(name = "ADDITION_TYPE")
    protected String additionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEVICE_LIST_PROJECT_ADDITION_ID")
    protected DeviceListProjectAddition deviceListProjectAddition;

    @Column(name = "STATUS")
    protected String status;


    public void setStatus(DeviceListEntryStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public DeviceListEntryStatus getStatus() {
        return status == null ? null : DeviceListEntryStatus.fromId(status);
    }


    public void setAdditionType(DeviceListProjectAdditionType additionType) {
        this.additionType = additionType == null ? null : additionType.getId();
    }

    public DeviceListProjectAdditionType getAdditionType() {
        return additionType == null ? null : DeviceListProjectAdditionType.fromId(additionType);
    }


    public void setDeviceListProjectAddition(DeviceListProjectAddition deviceListProjectAddition) {
        this.deviceListProjectAddition = deviceListProjectAddition;
    }

    public DeviceListProjectAddition getDeviceListProjectAddition() {
        return deviceListProjectAddition;
    }


    public void setEdited(Typonominal edited) {
        this.edited = edited;
    }

    public Typonominal getEdited() {
        return edited;
    }

    public void setNewtyponominal(Typonominal newtyponominal) {
        this.newtyponominal = newtyponominal;
    }

    public Typonominal getNewtyponominal() {
        return newtyponominal;
    }


}