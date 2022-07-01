package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Table(name = "MOBDEKBKP_PARAMETERS_FOR_PURCHASING")
@Entity(name = "mobdekbkp$ParametersForPurchasing")
public class ParametersForPurchasing extends StandardEntity {
    private static final long serialVersionUID = 5119170577060964116L;

    @Column(name = "PRODUCT_PRICE", length = 50)
    protected String productPrice;

    @Column(name = "DELIVERY_TIME", length = 100)
    protected String deliveryTime;

    @Column(name = "DELIVERY_TERM", length = 250)
    protected String deliveryTerm;

    @Column(name = "STATUS_IN_PRODUCTION")
    protected String statusInProduction;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_DEVICE_ID")
    protected ImportDevice importDevice;

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


    public StatusInProduction getStatusInProduction() {
        return statusInProduction == null ? null : StatusInProduction.fromId(statusInProduction);
    }

    public void setStatusInProduction(StatusInProduction statusInProduction) {
        this.statusInProduction = statusInProduction == null ? null : statusInProduction.getId();
    }


    public void setImportDevice(ImportDevice importDevice) {
        this.importDevice = importDevice;
    }

    public ImportDevice getImportDevice() {
        return importDevice;
    }


    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTerm(String deliveryTerm) {
        this.deliveryTerm = deliveryTerm;
    }

    public String getDeliveryTerm() {
        return deliveryTerm;
    }


}