package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_SEMICONDUCTOR_DIODES")
@Entity(name = "mobdekbkp$SemiconductorDiodes")
public class SemiconductorDiodes extends StandardEntity {
    private static final long serialVersionUID = 3543578778356314805L;

    @Column(name = "MAXIMUM_ALLOWABLE_REVERSE_VOLTAGE", length = 100)
    protected String maximumAllowableReverseVoltage;

    @Column(name = "MAXIMUM_PERMISSIBLE_AVERAGE_FORWARD_CURRENT", length = 100)
    protected String maximumPermissibleAverageForwardCurrent;

    @Column(name = "MAXIMUM_ALLOWABLEDIRECT_CURRENT", length = 100)
    protected String maximumAllowabledirectCurrent;

    @Column(name = "LIMITING_FREQUENCY", length = 100)
    protected String limitingFrequency;

    @Column(name = "REVERSE_RECOVERY_TIME", length = 100)
    protected String reverseRecoveryTime;

    public void setMaximumAllowableReverseVoltage(String maximumAllowableReverseVoltage) {
        this.maximumAllowableReverseVoltage = maximumAllowableReverseVoltage;
    }

    public String getMaximumAllowableReverseVoltage() {
        return maximumAllowableReverseVoltage;
    }

    public void setMaximumPermissibleAverageForwardCurrent(String maximumPermissibleAverageForwardCurrent) {
        this.maximumPermissibleAverageForwardCurrent = maximumPermissibleAverageForwardCurrent;
    }

    public String getMaximumPermissibleAverageForwardCurrent() {
        return maximumPermissibleAverageForwardCurrent;
    }

    public void setMaximumAllowabledirectCurrent(String maximumAllowabledirectCurrent) {
        this.maximumAllowabledirectCurrent = maximumAllowabledirectCurrent;
    }

    public String getMaximumAllowabledirectCurrent() {
        return maximumAllowabledirectCurrent;
    }

    public void setLimitingFrequency(String limitingFrequency) {
        this.limitingFrequency = limitingFrequency;
    }

    public String getLimitingFrequency() {
        return limitingFrequency;
    }

    public void setReverseRecoveryTime(String reverseRecoveryTime) {
        this.reverseRecoveryTime = reverseRecoveryTime;
    }

    public String getReverseRecoveryTime() {
        return reverseRecoveryTime;
    }


}