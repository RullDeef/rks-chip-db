package ru.spacecorp.mobdekbkp.entity.tc;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import java.util.LinkedHashSet;
import java.util.List;

@NamePattern("%s|name")
@MetaClass(name = "mobdekbkp$EkbObject")
public class EkbObject extends BaseUuidEntity {
    private static final long serialVersionUID = 6377905735230808461L;

    @MetaProperty
    protected String idtc;

    @MetaProperty
    protected String name;

    @MetaProperty
    protected String parent;


    @MetaProperty(related = "id")
    protected LinkedHashSet<AttrEkbObject> attrs;

    public LinkedHashSet<AttrEkbObject> getAttrs() {
        return attrs;
    }

    public void setAttrs(LinkedHashSet<AttrEkbObject> attrs) {
        this.attrs = attrs;
    }


    public void setIdtc(String idtc) {
        this.idtc = idtc;
    }

    public String getIdtc() {
        return idtc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }


}