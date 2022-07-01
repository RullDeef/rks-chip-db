package ru.spacecorp.mobdekbkp.entity.tc;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|value")
@MetaClass(name = "mobdekbkp$AttrEkbObject")
public class AttrEkbObject extends BaseUuidEntity {
    private static final long serialVersionUID = 3891834847498747432L;

    @MetaProperty
    protected String attrId;

    @MetaProperty
    protected String value;

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}