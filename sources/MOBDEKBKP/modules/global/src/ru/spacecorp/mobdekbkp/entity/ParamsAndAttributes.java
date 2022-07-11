package ru.spacecorp.mobdekbkp.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@MetaClass(name = "mobdekbkp$ParamsAndAttributes")
public class ParamsAndAttributes extends BaseUuidEntity {
    private static final long serialVersionUID = -4447432588672194896L;

    @MetaProperty
    protected String name;

    @MetaProperty
    protected String type;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


}