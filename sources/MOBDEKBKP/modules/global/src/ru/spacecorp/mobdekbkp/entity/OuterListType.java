package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_OUTER_LIST_TYPE")
@Entity(name = "mobdekbkp$OuterListType")
public class OuterListType extends StandardEntity {
    private static final long serialVersionUID = -2723688292842603925L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "IS_ALLOWING", nullable = false)
    protected String isAllowing;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIsAllowing(ExtBoolean isAllowing) {
        this.isAllowing = isAllowing == null ? null : isAllowing.getId();
    }

    public ExtBoolean getIsAllowing() {
        return isAllowing == null ? null : ExtBoolean.fromId(isAllowing);
    }


}