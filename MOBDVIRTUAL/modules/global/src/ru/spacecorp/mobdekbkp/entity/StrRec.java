package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;

import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Table(name = "MOBDEKBKP_STR_REC")
@Entity(name = "mobdekbkp$StrRec")
public class StrRec extends StandardEntity {
    private static final long serialVersionUID = 4802587379963375130L;

    @Column(name = "TEXT", unique = true)
    protected String text;

    @JoinTable(name = "MOBDEKBKP_STR_REC_SETTINGS_STR_REC_LINK",
            joinColumns = @JoinColumn(name = "STR_REC_ID"),
            inverseJoinColumns = @JoinColumn(name = "STR_REC_SETTINGS_ID"))
    @ManyToMany
    protected List<StrRecSettings> strRecSettingses;

    public void setStrRecSettingses(List<StrRecSettings> strRecSettingses) {
        this.strRecSettingses = strRecSettingses;
    }

    public List<StrRecSettings> getStrRecSettingses() {
        return strRecSettingses;
    }


    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


}