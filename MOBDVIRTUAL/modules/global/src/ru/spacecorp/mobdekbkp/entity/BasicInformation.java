package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Table(name = "MOBDEKBKP_BASIC_INFORMATION")
@Entity(name = "mobdekbkp$BasicInformation")
public class BasicInformation extends StandardEntity {
    private static final long serialVersionUID = 3158639229346530617L;

    @Column(name = "FUNCTIONALITY")
    protected String functionality;

    @Column(name = "LEVEL_QUALITY")
    protected String levelQuality;

    @Column(name = "LEVEL_QUALITY_IMPORT")
    protected String levelQualityImport;

    @Column(name = "IN_THE_LIST_0122")
    protected String in_the_list_0122;

    @Column(name = "IN_THE_LIST_EKB_K")
    protected String in_the_list_EKB_K;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_DEVICE_ID")
    protected ImportDevice importDevice;

    public ListAvailability getIn_the_list_0122() {
        return in_the_list_0122 == null ? null : ListAvailability.fromId(in_the_list_0122);
    }

    public void setIn_the_list_0122(ListAvailability in_the_list_0122) {
        this.in_the_list_0122 = in_the_list_0122 == null ? null : in_the_list_0122.getId();
    }


    public ListAvailability getIn_the_list_EKB_K() {
        return in_the_list_EKB_K == null ? null : ListAvailability.fromId(in_the_list_EKB_K);
    }

    public void setIn_the_list_EKB_K(ListAvailability in_the_list_EKB_K) {
        this.in_the_list_EKB_K = in_the_list_EKB_K == null ? null : in_the_list_EKB_K.getId();
    }


    public NationalLevelQuality getLevelQuality() {
        return levelQuality == null ? null : NationalLevelQuality.fromId(levelQuality);
    }

    public void setLevelQuality(NationalLevelQuality levelQuality) {
        this.levelQuality = levelQuality == null ? null : levelQuality.getId();
    }


    public void setLevelQualityImport(LevelQuality levelQualityImport) {
        this.levelQualityImport = levelQualityImport == null ? null : levelQualityImport.getId();
    }

    public LevelQuality getLevelQualityImport() {
        return levelQualityImport == null ? null : LevelQuality.fromId(levelQualityImport);
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


    public void setFunctionality(String functionality) {
        this.functionality = functionality;
    }

    public String getFunctionality() {
        return functionality;
    }


}