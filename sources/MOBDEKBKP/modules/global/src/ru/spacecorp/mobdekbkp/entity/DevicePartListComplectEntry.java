package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

@Listeners("mobdekbkp_DevicePartListComplectEntryEntityListener")
@NamePattern("%s %s|typonominal,amountDelivered")
@Table(name = "MOBDEKBKP_DEVICE_PART_LIST_COMPLECT_ENTRY")
@Entity(name = "mobdekbkp$DevicePartListComplectEntry")
public class DevicePartListComplectEntry extends StandardEntity {
    private static final long serialVersionUID = -5670433994266869228L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @NotNull
    @Column(name = "STATUS", nullable = false)
    protected String status;

    @Column(name = "AMOUNT_IN_DEVICE")
    protected Integer amountInDevice;

    @NotNull
    @Column(name = "AMOUNT_TOTAL", nullable = false)
    protected Integer amountTotal;

    @Column(name = "AMOUNT_DELIVERED")
    protected Integer amountDelivered;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_PLANNED")
    protected Date datePlanned;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_FACT")
    protected Date dateFact;

    @Lob
    @Column(name = "QUESTIONS")
    protected String questions;

    @Column(name = "BY_HEAD_EXECUTOR", nullable = false)
    protected String byHeadExecutor;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEVICE_PART_LIST_COMPLECT_ID")
    protected DevicePartListComplect devicePartListComplect;

    @Override
    public String toString(){
        return typonominal.getName();
    }



    public void setAmountTotal(Integer amountTotal) {
        this.amountTotal = amountTotal;
    }

    public Integer getAmountTotal() {
        return amountTotal;
    }

    public void setAmountInDevice(Integer amountInDevice) {
        this.amountInDevice = amountInDevice;
    }

    public Integer getAmountInDevice() {
        return amountInDevice;
    }

    public void setDevicePartListComplect(DevicePartListComplect devicePartListComplect) {
        this.devicePartListComplect = devicePartListComplect;
    }

    public DevicePartListComplect getDevicePartListComplect() {
        return devicePartListComplect;
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

    public void setAmountDelivered(Integer amountDelivered) {
        this.amountDelivered = amountDelivered;
    }

    public Integer getAmountDelivered() {
        return amountDelivered;
    }

    public void setDatePlanned(Date datePlanned) {
        this.datePlanned = datePlanned;
    }

    public Date getDatePlanned() {
        return datePlanned;
    }

    public void setDateFact(Date dateFact) {
        this.dateFact = dateFact;
    }

    public Date getDateFact() {
        return dateFact;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getQuestions() {
        return questions;
    }

    public void setByHeadExecutor(ExtBoolean byHeadExecutor) {
        this.byHeadExecutor = byHeadExecutor == null ? null : byHeadExecutor.getId();
    }

    public ExtBoolean getByHeadExecutor() {
        return byHeadExecutor == null ? null : ExtBoolean.fromId(byHeadExecutor);
    }

    @MetaProperty
    public Double getProgress() {
        return anyToDouble(amountDelivered, false) / anyToDouble(amountTotal, true);
    }

    private Double anyToDouble(Integer i, boolean isDivisor) {
        if (i == null || i == 0)
            return isDivisor ? 1.0 : 0.0;
        else
            return i.doubleValue();
    }
}