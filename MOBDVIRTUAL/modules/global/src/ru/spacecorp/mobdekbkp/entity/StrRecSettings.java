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

@Table(name = "MOBDEKBKP_STR_REC_SETTINGS")
@Entity(name = "mobdekbkp$StrRecSettings")
public class StrRecSettings extends StandardEntity {
    private static final long serialVersionUID = 1870299515957201039L;

    @JoinTable(name = "MOBDEKBKP_STR_REC_SETTINGS_STR_REC_LINK",
            joinColumns = @JoinColumn(name = "STR_REC_SETTINGS_ID"),
            inverseJoinColumns = @JoinColumn(name = "STR_REC_ID"))
    @ManyToMany
    protected List<StrRec> strRec;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_CLASS_ID")
    protected TypeClass typeClass;

    public void setStrRec(List<StrRec> strRec) {
        this.strRec = strRec;
    }

    public List<StrRec> getStrRec() {
        return strRec;
    }

    public void setTypeClass(TypeClass typeClass) {
        this.typeClass = typeClass;
    }

    public TypeClass getTypeClass() {
        return typeClass;
    }


}