package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

@Table(name = "MOBDEKBKP_IMPORT_DOCS_SCHEMES")
@Entity(name = "mobdekbkp$ImportDocsSchemes")
public class ImportDocsSchemes extends StandardEntity {
    private static final long serialVersionUID = -4689424054307292778L;

    @Column(name = "ITM", length = 100)
    protected String iTM;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_DEVICE_ID")
    protected ImportDevice importDevice;

    public void setImportDevice(ImportDevice importDevice) {
        this.importDevice = importDevice;
    }

    public ImportDevice getImportDevice() {
        return importDevice;
    }


    public void setITM(String iTM) {
        this.iTM = iTM;
    }

    public String getITM() {
        return iTM;
    }


}