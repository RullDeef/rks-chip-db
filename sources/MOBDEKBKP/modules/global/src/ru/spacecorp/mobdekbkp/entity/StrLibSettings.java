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

@Table(name = "MOBDEKBKP_STR_LIB_SETTINGS")
@Entity(name = "mobdekbkp$StrLibSettings")
public class StrLibSettings extends StandardEntity {
    private static final long serialVersionUID = -4543813500646157549L;

    @JoinTable(name = "MOBDEKBKP_STR_LIB_SETTINGS_STR_LIB_LINK",
        joinColumns = @JoinColumn(name = "STR_LIB_SETTINGS_ID"),
        inverseJoinColumns = @JoinColumn(name = "STR_LIB_ID"))
    @ManyToMany
    protected List<StrLib> strLib;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_CLASS_ID")
    protected TypeClass typeClass;

    public void setStrLib(List<StrLib> strLib) {
        this.strLib = strLib;
    }

    public List<StrLib> getStrLib() {
        return strLib;
    }

    public void setTypeClass(TypeClass typeClass) {
        this.typeClass = typeClass;
    }

    public TypeClass getTypeClass() {
        return typeClass;
    }


}