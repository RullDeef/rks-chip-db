package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.Column;
import javax.persistence.Lob;

@NamePattern("%s %s|id,typonominal")
@Table(name = "MOBDEKBKP_OUTER_DB_REFUSE")
@Entity(name = "mobdekbkp$OuterDbRefuse")
public class OuterDbRefuse extends StandardEntity {
    private static final long serialVersionUID = -7740290638857338432L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @Column(name = "PROVIDER", length = 100)
    protected String provider;

    @Column(name = "PRODUCE_DATE")
    protected String produceDate;

    @Column(name = "CHECKED")
    protected Integer checked;

    @Column(name = "REFUSED")
    protected Integer refused;

    @Column(name = "FAILED")
    protected Integer failed;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_DEVICE_ID")
    protected ImportDevice importDevice;

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public void setImportDevice(ImportDevice importDevice) {
        this.importDevice = importDevice;
    }

    public ImportDevice getImportDevice() {
        return importDevice;
    }


    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setRefused(Integer refused) {
        this.refused = refused;
    }

    public Integer getRefused() {
        return refused;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public Integer getFailed() {
        return failed;
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}