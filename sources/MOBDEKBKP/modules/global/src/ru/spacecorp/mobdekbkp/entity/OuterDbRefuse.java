package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.Column;
import javax.persistence.Lob;

@NamePattern("%s %s|id,typonominal")
@Table(name = "MOBDEKBKP_OUTER_DB_REFUSE")
@Entity(name = "mobdekbkp$OuterDbRefuse")
public class OuterDbRefuse extends StandardEntity {
    private static final long serialVersionUID = -7740290638857338432L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @Column(name = "INDEX_")
    protected String index;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "DEV_INDEX")
    protected String devIndex;

    @Column(name = "FACTORY_NUMBER")
    protected String factoryNumber;

    @Column(name = "PRODUCE_DATE")
    protected String produceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROVIDER_ID")
    protected Company provider;

    @Column(name = "CHECKED")
    protected Integer checked;

    @Column(name = "REFUSED")
    protected Integer refused;

    @Column(name = "FAILED")
    protected Integer failed;

    @Column(name = "REFUSE_PLACE")
    protected String refusePlace;

    @Column(name = "ACTIONS")
    protected String actions;

    @Lob
    @Column(name = "CONCLUSION")
    protected String conclusion;

    @Lob
    @Column(name = "SHORT_ACTIONS")
    protected String shortActions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TESTS_ID")
    protected OuterDbRefuseTests tests;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEFECTS_ID")
    protected OuterDbRefuseDefects defects;


    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDevIndex(String devIndex) {
        this.devIndex = devIndex;
    }

    public String getDevIndex() {
        return devIndex;
    }

    public void setFactoryNumber(String factoryNumber) {
        this.factoryNumber = factoryNumber;
    }

    public String getFactoryNumber() {
        return factoryNumber;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setRefused(Integer refused) {
        this.refused = refused;
    }

    public Integer getRefused() {
        return refused;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setRefusePlace(String refusePlace) {
        this.refusePlace = refusePlace;
    }

    public String getRefusePlace() {
        return refusePlace;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public String getActions() {
        return actions;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setShortActions(String shortActions) {
        this.shortActions = shortActions;
    }

    public String getShortActions() {
        return shortActions;
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }

    public void setProvider(Company provider) {
        this.provider = provider;
    }

    public Company getProvider() {
        return provider;
    }

    public void setTests(OuterDbRefuseTests tests) {
        this.tests = tests;
    }

    public OuterDbRefuseTests getTests() {
        return tests;
    }

    public void setDefects(OuterDbRefuseDefects defects) {
        this.defects = defects;
    }

    public OuterDbRefuseDefects getDefects() {
        return defects;
    }




}