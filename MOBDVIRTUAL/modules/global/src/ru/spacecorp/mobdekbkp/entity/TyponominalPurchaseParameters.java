package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Listeners;

import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.validation.constraints.NotNull;

@NamePattern("%s|purchaseDesignation")
@Table(name = "MOBDEKBKP_TYPONOMINAL_PURCHASE_PARAMETERS")
@Entity(name = "mobdekbkp$TyponominalPurchaseParameters")
public class TyponominalPurchaseParameters extends StandardEntity {
    private static final long serialVersionUID = 1122320890767564055L;

    @Column(name = "PURCHASE_DESIGNATION", nullable = false)
    protected String purchaseDesignation;

    @Lob
    @Column(name = "COST_RATING", nullable = false)
    protected String costRating;

    @NotNull
    @Column(name = "TYPICAL_DELIVERY_TERM", nullable = false)
    protected Integer typicalDeliveryTerm;

    @Lob
    @Column(name = "MARKET_AVAILABLE_INDEX")
    protected String marketAvailableIndex;

    @Column(name = "NEED_PERMISSIONS_GOSDEP", nullable = false)
    protected String needPermissionsGosdep;

    @Column(name = "PERMISSION_GOSDEP_TERM")
    protected Integer permissionGosdepTerm;

    @Column(name = "HAS_SAMPLES", nullable = false)
    protected String hasSamples;

    @Column(name = "DELIVERY_MIN")
    protected Integer deliveryMin;

    @Column(name = "DELIVERY_MAX")
    protected Integer deliveryMax;


    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COMPANY_ID")
    protected Company company;


    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COST_ID")
    protected Cost cost;

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public Cost getCost() {
        return cost;
    }


    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


    public void setPurchaseDesignation(String purchaseDesignation) {
        this.purchaseDesignation = purchaseDesignation;
    }

    public String getPurchaseDesignation() {
        return purchaseDesignation;
    }

    public void setCostRating(String costRating) {
        this.costRating = costRating;
    }

    public String getCostRating() {
        return costRating;
    }

    public void setTypicalDeliveryTerm(Integer typicalDeliveryTerm) {
        this.typicalDeliveryTerm = typicalDeliveryTerm;
    }

    public Integer getTypicalDeliveryTerm() {
        return typicalDeliveryTerm;
    }

    public void setMarketAvailableIndex(String marketAvailableIndex) {
        this.marketAvailableIndex = marketAvailableIndex;
    }

    public String getMarketAvailableIndex() {
        return marketAvailableIndex;
    }

    public void setNeedPermissionsGosdep(ExtBoolean needPermissionsGosdep) {
        this.needPermissionsGosdep = needPermissionsGosdep == null ? null : needPermissionsGosdep.getId();
    }

    public ExtBoolean getNeedPermissionsGosdep() {
        return needPermissionsGosdep == null ? null : ExtBoolean.fromId(needPermissionsGosdep);
    }

    public void setPermissionGosdepTerm(Integer permissionGosdepTerm) {
        this.permissionGosdepTerm = permissionGosdepTerm;
    }

    public Integer getPermissionGosdepTerm() {
        return permissionGosdepTerm;
    }

    public void setHasSamples(ExtBoolean hasSamples) {
        this.hasSamples = hasSamples == null ? null : hasSamples.getId();
    }

    public ExtBoolean getHasSamples() {
        return hasSamples == null ? null : ExtBoolean.fromId(hasSamples);
    }

    public void setDeliveryMin(Integer deliveryMin) {
        this.deliveryMin = deliveryMin;
    }

    public Integer getDeliveryMin() {
        return deliveryMin;
    }

    public void setDeliveryMax(Integer deliveryMax) {
        this.deliveryMax = deliveryMax;
    }

    public Integer getDeliveryMax() {
        return deliveryMax;
    }


}