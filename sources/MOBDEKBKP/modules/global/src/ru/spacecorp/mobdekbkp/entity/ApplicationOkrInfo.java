package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Listeners;

import javax.persistence.ManyToOne;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@NamePattern("%s|description")
@Table(name = "MOBDEKBKP_APPLICATION_OKR_INFO")
@Entity(name = "mobdekbkp$ApplicationOkrInfo")
public class ApplicationOkrInfo extends StandardEntity {
    private static final long serialVersionUID = 481770567668151167L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RESPONSIBLE_ID")
    protected Company responsible;

    @NotNull
    @Lob
    @Column(name = "DESCRIPTION", nullable = false)
    protected String description;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_START", nullable = false)
    protected Date dateStart;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_APPROX")
    protected Date dateApprox;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_END")
    protected Date dateEnd;

    @Column(name = "CONDITION_", nullable = false)
    protected String condition;

    @Column(name = "RESULT_")
    protected String result;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "APPLICATION_COMMON_ENTRY_ID")
    protected ApplicationCommonEntry applicationCommonEntry;

    public Company getResponsible() {
        return responsible;
    }

    public void setResponsible(Company responsible) {
        this.responsible = responsible;
    }


    public void setCondition(ApplicationOkrInfoCondition condition) {
        this.condition = condition == null ? null : condition.getId();
    }

    public ApplicationOkrInfoCondition getCondition() {
        return condition == null ? null : ApplicationOkrInfoCondition.fromId(condition);
    }


    public void setDateApprox(Date dateApprox) {
        this.dateApprox = dateApprox;
    }

    public Date getDateApprox() {
        return dateApprox;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    public void setApplicationCommonEntry(ApplicationCommonEntry applicationCommonEntry) {
        this.applicationCommonEntry = applicationCommonEntry;
    }

    public ApplicationCommonEntry getApplicationCommonEntry() {
        return applicationCommonEntry;
    }


    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateEnd() {
        return dateEnd;
    }




}