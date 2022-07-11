package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_TYPONOMINAL_QUALITY_LEVEL_IMPORT")
@Entity(name = "mobdekbkp$TyponominalQualityLevelImport")
public class TyponominalQualityLevelImport extends StandardEntity {
    private static final long serialVersionUID = -5491887381531044642L;

    @Column(name = "NAME", nullable = false)
    protected String name;


    @JoinTable(name = "MOBDEKBKP_TYPONOMINAL_Q_L_IMPORT_SET_TYPONOMINAL_Q_L_IMPORT",
        joinColumns = @JoinColumn(name = "TYPONOMINAL_QUALITY_LEVEL_IMPORT_ID"),
        inverseJoinColumns = @JoinColumn(name = "TYPONOMINAL_QUALITY_LEVEL_IMPORT_SETTINGS_ID"))
    @ManyToMany
    protected List<TyponominalQualityLevelImportSettings> typonominalQualityLevelImportSettingses;

    public void setTyponominalQualityLevelImportSettingses(List<TyponominalQualityLevelImportSettings> typonominalQualityLevelImportSettingses) {
        this.typonominalQualityLevelImportSettingses = typonominalQualityLevelImportSettingses;
    }

    public List<TyponominalQualityLevelImportSettings> getTyponominalQualityLevelImportSettingses() {
        return typonominalQualityLevelImportSettingses;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}