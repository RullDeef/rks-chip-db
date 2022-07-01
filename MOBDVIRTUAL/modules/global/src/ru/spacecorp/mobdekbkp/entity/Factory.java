package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Table(name = "MOBDEKBKP_FACTORY")
@Entity(name = "mobdekbkp$Factory")
public class Factory extends StandardEntity {
    private static final long serialVersionUID = -4636250850219194400L;

    @Column(name = "PRODUCER", length = 100)
    protected String producer;

    @Column(name = "PRODUCING_COUNTRY", length = 50)
    protected String producingCountry;

    @Column(name = "CERTIFICATION_CMK_ORGANIZATION")
    protected String certificationCMKOrganization;

    @Column(name = "CALCULATOR_HOLDER", length = 100)
    protected String calculatorHolder;

    @Column(name = "PROVIDER")
    protected String provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_DEVICE_ID")
    protected ImportDevice importDevice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }


    public OrganizationSMKCertification getCertificationCMKOrganization() {
        return certificationCMKOrganization == null ? null : OrganizationSMKCertification.fromId(certificationCMKOrganization);
    }

    public void setCertificationCMKOrganization(OrganizationSMKCertification certificationCMKOrganization) {
        this.certificationCMKOrganization = certificationCMKOrganization == null ? null : certificationCMKOrganization.getId();
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


    public void setImportDevice(ImportDevice importDevice) {
        this.importDevice = importDevice;
    }

    public ImportDevice getImportDevice() {
        return importDevice;
    }


    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducingCountry(String producingCountry) {
        this.producingCountry = producingCountry;
    }

    public String getProducingCountry() {
        return producingCountry;
    }

    public void setCalculatorHolder(String calculatorHolder) {
        this.calculatorHolder = calculatorHolder;
    }

    public String getCalculatorHolder() {
        return calculatorHolder;
    }


}