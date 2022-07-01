package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.persistence.Lob;
import java.util.List;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@NamePattern("%s %s|id,typonominal")
@Table(name = "MOBDEKBKP_OUTER_FAIL_AND_REFUSES")
@Entity(name = "mobdekbkp$OuterFailAndRefuses")
public class OuterFailAndRefuses extends StandardEntity {
    private static final long serialVersionUID = 6166212027931879907L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "RELEASE_DATE", nullable = false)
    protected Date releaseDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "REFUSE_DATE", nullable = false)
    protected Date refuseDate;

    @Column(name = "BEFORE_REFUSE", nullable = false)
    protected Double beforeRefuse;

    @NotNull
    @Column(name = "ACCEPTED_DEFECT", nullable = false)
    protected Integer acceptedDefect;

    @Column(name = "DENY_REASONS", nullable = false)
    protected String denyReasons;

    @Lob
    @Column(name = "DEFECT_CLASS", nullable = false)
    protected String defectClass;

    @Lob
    @Column(name = "DEFECT_REPEAT", nullable = false)
    protected String defectRepeat;

    @Column(name = "USER_BLAME", nullable = false)
    protected Integer userBlame;

    @Lob
    @Column(name = "PREVENT", nullable = false)
    protected String prevent;

    @Temporal(TemporalType.DATE)
    @Column(name = "ACTIONS_DATE", nullable = false)
    protected Date actionsDate;

    @Column(name = "AMOUNT_AT_PROVIDER", nullable = false)
    protected Integer amountAtProvider;

    @JoinTable(name = "MOBDEKBKP_OUTER_FAIL_AND_REFUSES_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "OUTER_FAIL_AND_REFUSES_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public void setBeforeRefuse(Double beforeRefuse) {
        this.beforeRefuse = beforeRefuse;
    }

    public Double getBeforeRefuse() {
        return beforeRefuse;
    }

    public void setAcceptedDefect(Integer acceptedDefect) {
        this.acceptedDefect = acceptedDefect;
    }

    public Integer getAcceptedDefect() {
        return acceptedDefect;
    }

    public void setDenyReasons(String denyReasons) {
        this.denyReasons = denyReasons;
    }

    public String getDenyReasons() {
        return denyReasons;
    }

    public void setDefectClass(String defectClass) {
        this.defectClass = defectClass;
    }

    public String getDefectClass() {
        return defectClass;
    }

    public void setDefectRepeat(String defectRepeat) {
        this.defectRepeat = defectRepeat;
    }

    public String getDefectRepeat() {
        return defectRepeat;
    }

    public void setUserBlame(Integer userBlame) {
        this.userBlame = userBlame;
    }

    public Integer getUserBlame() {
        return userBlame;
    }

    public void setPrevent(String prevent) {
        this.prevent = prevent;
    }

    public String getPrevent() {
        return prevent;
    }

    public void setActionsDate(Date actionsDate) {
        this.actionsDate = actionsDate;
    }

    public Date getActionsDate() {
        return actionsDate;
    }

    public void setAmountAtProvider(Integer amountAtProvider) {
        this.amountAtProvider = amountAtProvider;
    }

    public Integer getAmountAtProvider() {
        return amountAtProvider;
    }


    public void setRefuseDate(Date refuseDate) {
        this.refuseDate = refuseDate;
    }

    public Date getRefuseDate() {
        return refuseDate;
    }


    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}