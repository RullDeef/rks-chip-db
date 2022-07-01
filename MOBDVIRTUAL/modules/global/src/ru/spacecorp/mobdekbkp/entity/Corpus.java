package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Table(name = "MOBDEKBKP_CORPUS")
@Entity(name = "mobdekbkp$Corpus")
public class Corpus extends StandardEntity {
    private static final long serialVersionUID = 5837695025751492424L;

    @Column(name = "CORPUS_TYPE", length = 100)
    protected String corpusType;

    @Column(name = "CORPUS_MATERIAL")
    protected String corpusMaterial;

    @Column(name = "COVER_COVER", length = 100)
    protected String coverCover;

    @Column(name = "CONTACT_TYPE")
    protected String contactType;

    @Column(name = "CONTACT_COVER")
    protected String contactCover;

    @Column(name = "DESIGNATION_OUTPUT_FORMING_OPTION", length = 100)
    protected String designationOutputFormingOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_DEVICE_ID")
    protected ImportDevice importDevice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    public ContactType getContactType() {
        return contactType == null ? null : ContactType.fromId(contactType);
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType == null ? null : contactType.getId();
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


    public CorpusMaterial getCorpusMaterial() {
        return corpusMaterial == null ? null : CorpusMaterial.fromId(corpusMaterial);
    }

    public void setCorpusMaterial(CorpusMaterial corpusMaterial) {
        this.corpusMaterial = corpusMaterial == null ? null : corpusMaterial.getId();
    }


    public ContactCoverage getContactCover() {
        return contactCover == null ? null : ContactCoverage.fromId(contactCover);
    }

    public void setContactCover(ContactCoverage contactCover) {
        this.contactCover = contactCover == null ? null : contactCover.getId();
    }


    public void setImportDevice(ImportDevice importDevice) {
        this.importDevice = importDevice;
    }

    public ImportDevice getImportDevice() {
        return importDevice;
    }


    public void setCorpusType(String corpusType) {
        this.corpusType = corpusType;
    }

    public String getCorpusType() {
        return corpusType;
    }

    public void setCoverCover(String coverCover) {
        this.coverCover = coverCover;
    }

    public String getCoverCover() {
        return coverCover;
    }

    public void setDesignationOutputFormingOption(String designationOutputFormingOption) {
        this.designationOutputFormingOption = designationOutputFormingOption;
    }

    public String getDesignationOutputFormingOption() {
        return designationOutputFormingOption;
    }


}