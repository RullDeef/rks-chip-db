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

@NamePattern("%s %s|id,typonominal")
@Table(name = "MOBDEKBKP_OUTER_CERTIFICATE_TESTS")
@Entity(name = "mobdekbkp$OuterCertificateTests")
public class OuterCertificateTests extends StandardEntity {
    private static final long serialVersionUID = 1768395899628521789L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @Column(name = "INDEX_")
    protected String index;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_START")
    protected Date dateStart;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_END")
    protected Date dateEnd;

    @Lob
    @Column(name = "RESULT_")
    protected String result;

    @JoinTable(name = "MOBDEKBKP_OUTER_CERTIFICATE_TESTS_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "OUTER_CERTIFICATE_TESTS_ID"),
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

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }


}