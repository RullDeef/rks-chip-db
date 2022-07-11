package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_TYPE_CALICOHOLDER_ENTRY")
@Entity(name = "mobdekbkp$TypeCalicoholderEntry")
public class TypeCalicoholderEntry extends StandardEntity {
    private static final long serialVersionUID = 5334110763381978600L;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NAME_ID")
    protected Company name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPE_INVERSE_ID")
    protected Type typeInverse;

    public void setTypeInverse(Type typeInverse) {
        this.typeInverse = typeInverse;
    }

    public Type getTypeInverse() {
        return typeInverse;
    }


    public void setName(Company name) {
        this.name = name;
    }

    public Company getName() {
        return name;
    }


}