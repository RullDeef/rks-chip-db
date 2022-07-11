package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Table(name = "MOBDEKBKP_STR_LIB")
@Entity(name = "mobdekbkp$StrLib")
public class StrLib extends StandardEntity {
    private static final long serialVersionUID = 3075753638510475814L;

    @Column(name = "TEXT", unique = true)
    protected String text;


    @JoinTable(name = "MOBDEKBKP_STR_LIB_SETTINGS_STR_LIB_LINK",
        joinColumns = @JoinColumn(name = "STR_LIB_ID"),
        inverseJoinColumns = @JoinColumn(name = "STR_LIB_SETTINGS_ID"))
    @ManyToMany
    protected List<StrLibSettings> strLibSettingses;

    public void setStrLibSettingses(List<StrLibSettings> strLibSettingses) {
        this.strLibSettingses = strLibSettingses;
    }

    public List<StrLibSettings> getStrLibSettingses() {
        return strLibSettingses;
    }


    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


}