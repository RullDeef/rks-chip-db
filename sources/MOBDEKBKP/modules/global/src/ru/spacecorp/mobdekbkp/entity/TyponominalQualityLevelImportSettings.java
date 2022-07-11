package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "MOBDEKBKP_TYPONOMINAL_QUALITY_LEVEL_IMPORT_SETTINGS")
@Entity(name = "mobdekbkp$TyponominalQualityLevelImportSettings")
public class TyponominalQualityLevelImportSettings extends StandardEntity {
    private static final long serialVersionUID = -1538572201684510870L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_CLASS_ID", unique = true)
    protected TypeClass importClass;

    @JoinTable(name = "MOBDEKBKP_TYPONOMINAL_Q_L_IMPORT_SET_TYPONOMINAL_Q_L_IMPORT",
        joinColumns = @JoinColumn(name = "TYPONOMINAL_QUALITY_LEVEL_IMPORT_SETTINGS_ID"),
        inverseJoinColumns = @JoinColumn(name = "TYPONOMINAL_QUALITY_LEVEL_IMPORT_ID"))
    @ManyToMany
    protected List<TyponominalQualityLevelImport> qualityLevel;

    public void setQualityLevel(List<TyponominalQualityLevelImport> qualityLevel) {
        this.qualityLevel = qualityLevel;
    }

    public List<TyponominalQualityLevelImport> getQualityLevel() {
        return qualityLevel;
    }


    public void setImportClass(TypeClass importClass) {
        this.importClass = importClass;
    }

    public TypeClass getImportClass() {
        return importClass;
    }


}