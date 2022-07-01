package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_TYPE_CLIMATIC_IMPLEMENTATION")
@Entity(name = "mobdekbkp$TypeClimaticImplementation")
public class TypeClimaticImplementation extends StandardEntity {
    private static final long serialVersionUID = 5291329557715388431L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}