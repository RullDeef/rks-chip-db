package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Table(name = "MOBDEKBKP_APPLICABILITY_DEVICES")
@Entity(name = "mobdekbkp$ApplicabilityDevices")
public class ApplicabilityDevices extends StandardEntity {
    private static final long serialVersionUID = -1546442000359407760L;

    @Column(name = "EQUIPMENT", length = 100)
    protected String equipment;

    @Column(name = "COMPANY_DEVELOPER_HARDWARE", length = 100)
    protected String companyDeveloperHardware;

    @Column(name = "PRODUCT_RKT", length = 100)
    protected String productRKT;

    @Column(name = "PRIME_DEVELOPER", length = 100)
    protected String primeDeveloper;

    @Column(name = "APPROVAL_YEAR", length = 10)
    protected String approvalYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_DEVICE_ID")
    protected ImportDevice importDevice;

    public String getApprovalYear() {
        return approvalYear;
    }

    public void setApprovalYear(String approvalYear) {
        this.approvalYear = approvalYear;
    }


    public void setImportDevice(ImportDevice importDevice) {
        this.importDevice = importDevice;
    }

    public ImportDevice getImportDevice() {
        return importDevice;
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setCompanyDeveloperHardware(String companyDeveloperHardware) {
        this.companyDeveloperHardware = companyDeveloperHardware;
    }

    public String getCompanyDeveloperHardware() {
        return companyDeveloperHardware;
    }

    public void setProductRKT(String productRKT) {
        this.productRKT = productRKT;
    }

    public String getProductRKT() {
        return productRKT;
    }

    public void setPrimeDeveloper(String primeDeveloper) {
        this.primeDeveloper = primeDeveloper;
    }

    public String getPrimeDeveloper() {
        return primeDeveloper;
    }


}