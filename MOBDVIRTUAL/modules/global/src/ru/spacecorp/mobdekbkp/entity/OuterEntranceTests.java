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
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.List;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@NamePattern("%s %s|typonominal,id")
@Table(name = "MOBDEKBKP_OUTER_ENTRANCE_TESTS")
@Entity(name = "mobdekbkp$OuterEntranceTests")
public class OuterEntranceTests extends StandardEntity {
    private static final long serialVersionUID = 8585275452192623462L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @Column(name = "INDEX_")
    protected String index;

    @Lob
    @Column(name = "DESCRIPTION", nullable = false)
    protected String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CERT_CENTER_ID")
    protected Company certCenter;

    @Column(name = "AMOUNT_CHECKED")
    protected Integer amountChecked;

    @Column(name = "AMOUNT_PASSED")
    protected Integer amountPassed;

    @Column(name = "AMOUNT_FAILED")
    protected Integer amountFailed;

    @Lob
    @Column(name = "FAIL_DESCRIPTION")
    protected String failDescription;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_START")
    protected Date dateStart;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_END")
    protected Date dateEnd;

    @Lob
    @Column(name = "TEST_RESULT")
    protected String testResult;

    @JoinTable(name = "MOBDEKBKP_OUTER_ENTRANCE_TESTS_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "OUTER_ENTRANCE_TESTS_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setAmountChecked(Integer amountChecked) {
        this.amountChecked = amountChecked;
    }

    public Integer getAmountChecked() {
        return amountChecked;
    }

    public void setAmountPassed(Integer amountPassed) {
        this.amountPassed = amountPassed;
    }

    public Integer getAmountPassed() {
        return amountPassed;
    }

    public void setAmountFailed(Integer amountFailed) {
        this.amountFailed = amountFailed;
    }

    public Integer getAmountFailed() {
        return amountFailed;
    }

    public void setFailDescription(String failDescription) {
        this.failDescription = failDescription;
    }

    public String getFailDescription() {
        return failDescription;
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

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getTestResult() {
        return testResult;
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }

    public void setCertCenter(Company certCenter) {
        this.certCenter = certCenter;
    }

    public Company getCertCenter() {
        return certCenter;
    }


}