package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_CUSTOM_PARAMETERS")
@Entity(name = "mobdekbkp$CustomParameters")
public class CustomParameters extends StandardEntity {
    private static final long serialVersionUID = 3709737214210123772L;

    @Column(name = "NAME", length = 100)
    protected String name;

    @Column(name = "PARAM_VALUE", length = 100)
    protected String paramValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_DEVICE_ID")
    protected ImportDevice importDevice;

    public void setImportDevice(ImportDevice importDevice) {
        this.importDevice = importDevice;
    }

    public ImportDevice getImportDevice() {
        return importDevice;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}