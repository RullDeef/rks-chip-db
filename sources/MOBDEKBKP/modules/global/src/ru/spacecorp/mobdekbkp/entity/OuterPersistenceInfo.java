package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.entity.annotation.Listeners;

@NamePattern("%s %s|manufacturer,typonominal")
@Table(name = "MOBDEKBKP_OUTER_PERSISTENCE_INFO")
@Entity(name = "mobdekbkp$OuterPersistenceInfo")
public class OuterPersistenceInfo extends StandardEntity {
    private static final long serialVersionUID = 2041035828091128727L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TIPONOMINAL_ID")
    protected Typonominal typonominal;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MANUFACTURER_ID")
    protected Company manufacturer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "INFO_SOURCE_ID")
    protected OuterInformationSource infoSource;

    @Column(name = "SINGLE_EFFECT_DATA")
    protected String singleEffectData;

    @Column(name = "DOSE_EFFECT_DATA")
    protected String doseEffectData;

    @Column(name = "HAS_FILES", nullable = false)
    protected String hasFiles;


    public void setDoseEffectData(String doseEffectData) {
        this.doseEffectData = doseEffectData;
    }

    public String getDoseEffectData() {
        return doseEffectData;
    }


    public void setSingleEffectData(String singleEffectData) {
        this.singleEffectData = singleEffectData;
    }

    public String getSingleEffectData() {
        return singleEffectData;
    }


    public OuterInformationSource getInfoSource() {
        return infoSource;
    }

    public void setInfoSource(OuterInformationSource infoSource) {
        this.infoSource = infoSource;
    }





    public void setHasFiles(ExtBoolean hasFiles) {
        this.hasFiles = hasFiles == null ? null : hasFiles.getId();
    }

    public ExtBoolean getHasFiles() {
        return hasFiles == null ? null : ExtBoolean.fromId(hasFiles);
    }




    public void setManufacturer(Company manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Company getManufacturer() {
        return manufacturer;
    }






    public Typonominal getTyponominal() {
        return typonominal;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }



}