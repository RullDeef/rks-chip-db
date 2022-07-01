package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_MATERIAL")
@Entity(name = "mobdekbkp$Material")
public class Material extends StandardEntity {
    private static final long serialVersionUID = -5832134879567388607L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "TYPE_", nullable = false)
    protected String type;

    public void setType(MaterialType type) {
        this.type = type == null ? null : type.getId();
    }

    public MaterialType getType() {
        return type == null ? null : MaterialType.fromId(type);
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}