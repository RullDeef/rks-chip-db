package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.*;

import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_ANALOG_IMPORT")
@Entity(name = "mobdekbkp$AnalogImport")
public class AnalogImport extends StandardEntity {
    private static final long serialVersionUID = 9020149049942903763L;

    @Column(name = "TYPE_ANALOG")
    protected String typonominalAnalogType;

    @Column(name = "IS_RECOMMENDED_GNIO")
    protected String isRecommendedGnio;

    @Column(name = "ANALOG_NAME")
    protected String analogName;

    @Column(name = "PRODUCER", length = 100)
    protected String producer;

    @Column(name = "PRODUCING_COUNTRY", length = 100)
    protected String producingCountry;

    @Column(name = "OKR_MIN_PROM_TORG", length = 100)
    protected String okrMinPromTorg;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_DEVICE_ID")
    protected ImportDevice importDevice;

    public void setAnalogName(String analogName) {
        this.analogName = analogName;
    }

    public String getAnalogName() {
        return analogName;
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

    public void setOkrMinPromTorg(String okrMinPromTorg) {
        this.okrMinPromTorg = okrMinPromTorg;
    }

    public String getOkrMinPromTorg() {
        return okrMinPromTorg;
    }


    public void setIsRecommendedGnio(ExtBoolean isRecommendedGnio) {
        this.isRecommendedGnio = isRecommendedGnio == null ? null : isRecommendedGnio.getId();
    }

    public ExtBoolean getIsRecommendedGnio() {
        return isRecommendedGnio == null ? null : ExtBoolean.fromId(isRecommendedGnio);
    }


    public void setTyponominalAnalogType(TyponominalAnalogType typonominalAnalogType) {
        this.typonominalAnalogType = typonominalAnalogType == null ? null : typonominalAnalogType.getId();
    }

    public TyponominalAnalogType getTyponominalAnalogType() {
        return typonominalAnalogType == null ? null : TyponominalAnalogType.fromId(typonominalAnalogType);
    }

}