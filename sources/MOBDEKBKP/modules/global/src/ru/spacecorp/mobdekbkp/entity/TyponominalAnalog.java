package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Listeners;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.annotation.Listeners;

@NamePattern("%s %s %s|typonominal,typonominalAnalogType,parentTyponominal")
@Table(name = "MOBDEKBKP_TYPONOMINAL_ANALOG")
@Entity(name = "mobdekbkp$TyponominalAnalog")
public class TyponominalAnalog extends StandardEntity {
    private static final long serialVersionUID = 5896793350100988403L;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @Column(name = "TYPE_ANALOG", nullable = false)
    protected String typonominalAnalogType;

    @Column(name = "IS_RECOMMENDED_GNIO", nullable = false)
    protected String isRecommendedGnio;



    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARENT_TYPONOMINAL_ID")
    protected Typonominal parentTyponominal;

    public void setIsRecommendedGnio(ExtBoolean isRecommendedGnio) {
        this.isRecommendedGnio = isRecommendedGnio == null ? null : isRecommendedGnio.getId();
    }

    public ExtBoolean getIsRecommendedGnio() {
        return isRecommendedGnio == null ? null : ExtBoolean.fromId(isRecommendedGnio);
    }


    public void setParentTyponominal(Typonominal parentTyponominal) {
        this.parentTyponominal = parentTyponominal;
    }

    public Typonominal getParentTyponominal() {
        return parentTyponominal;
    }


    public Typonominal getTyponominal() {
        return typonominal;
    }

    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public void setTyponominalAnalogType(TyponominalAnalogType typonominalAnalogType) {
        this.typonominalAnalogType = typonominalAnalogType == null ? null : typonominalAnalogType.getId();
    }

    public TyponominalAnalogType getTyponominalAnalogType() {
        return typonominalAnalogType == null ? null : TyponominalAnalogType.fromId(typonominalAnalogType);
    }




}