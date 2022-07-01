package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;

import com.haulmont.chile.core.annotations.NamePattern;

import java.util.List;
import javax.persistence.OneToMany;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_IMPORT_CLASS")
@Entity(name = "mobdekbkp$ImportClass")
public class ImportClass extends StandardEntity {
    private static final long serialVersionUID = 4470483696107659921L;

    @Column(name = "NAME", length = 250)
    protected String name;

    @Column(name = "NUMBER_")
    protected Integer number_;

    @OneToMany(mappedBy = "importClass")
    protected List<ImportDevice> importDevice;

    public void setImportDevice(List<ImportDevice> importDevice) {
        this.importDevice = importDevice;
    }

    public List<ImportDevice> getImportDevice() {
        return importDevice;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNumber_(Integer number_) {
        this.number_ = number_;
    }

    public Integer getNumber_() {
        return number_;
    }


}