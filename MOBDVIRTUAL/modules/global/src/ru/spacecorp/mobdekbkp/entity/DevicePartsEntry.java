package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.annotation.Listeners;

@NamePattern("%s %s|part,amount")
@Table(name = "MOBDEKBKP_DEVICE_PARTS_ENTRY")
@Entity(name = "mobdekbkp$DevicePartsEntry")
public class DevicePartsEntry extends StandardEntity {
    private static final long serialVersionUID = 488250633548171813L;

    @Column(name = "AMOUNT", nullable = false)
    protected Integer amount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PART_ID")
    protected DevicePart part;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEVICE_ID")
    protected Device device;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IN_DEVICE_PART_COMLECT_ID")
    protected DevicePartListComplect inDevicePartComlect;

    @Override
    public String toString() {
        return getPart().getDesignation();
    }

    public void setInDevicePartComlect(DevicePartListComplect inDevicePartComlect) {
        this.inDevicePartComlect = inDevicePartComlect;
    }

    public DevicePartListComplect getInDevicePartComlect() {
        return inDevicePartComlect;
    }


    public void setDevice(Device device) {
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setPart(DevicePart part) {
        this.part = part;
    }

    public DevicePart getPart() {
        return part;
    }


}
