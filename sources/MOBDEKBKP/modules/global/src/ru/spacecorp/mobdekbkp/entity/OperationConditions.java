package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Lob;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_OPERATION_CONDITIONS")
@Entity(name = "mobdekbkp$OperationConditions")
public class OperationConditions extends StandardEntity {
    private static final long serialVersionUID = -8278960207921814997L;

    @NotNull
    @Column(name = "NAME", nullable = false, unique = true)
    protected String name;

    @Lob
    @Column(name = "TEXT")
    protected String text;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


}