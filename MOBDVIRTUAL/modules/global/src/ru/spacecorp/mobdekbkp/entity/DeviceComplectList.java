package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.List;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.haulmont.cuba.core.entity.annotation.Listeners;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_DEVICE_COMPLECT_LIST")
@Entity(name = "mobdekbkp$DeviceComplectList")
public class DeviceComplectList extends StandardEntity {
    private static final long serialVersionUID = 3457202519344939973L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "STATUS")
    protected String status;


    public void setStatus(DeviceComplectListStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public DeviceComplectListStatus getStatus() {
        return status == null ? null : DeviceComplectListStatus.fromId(status);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
