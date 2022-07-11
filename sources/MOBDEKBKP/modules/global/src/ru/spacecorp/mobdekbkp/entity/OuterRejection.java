package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import java.util.List;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@NamePattern("%s %s|typonominal,date")
@Table(name = "MOBDEKBKP_OUTER_REJECTION")
@Entity(name = "mobdekbkp$OuterRejection")
public class OuterRejection extends StandardEntity {
    private static final long serialVersionUID = 3358984848859683196L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_", nullable = false)
    protected Date date;

    @Column(name = "CHECKED", nullable = false)
    protected Integer checked;

    @Column(name = "REJECTED", nullable = false)
    protected Integer rejected;

    @Lob
    @Column(name = "REASON", nullable = false)
    protected String reason;

    @Column(name = "FAILED_BY_CONSUMER", nullable = false)
    protected Integer failedByConsumer;

    @Column(name = "NOT_CHECKED", nullable = false)
    protected Integer notChecked;

    @Lob
    @Column(name = "COMMENT_", nullable = false)
    protected String comment;

    @JoinTable(name = "MOBDEKBKP_OUTER_REJECTION_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "OUTER_REJECTION_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public void setFailedByConsumer(Integer failedByConsumer) {
        this.failedByConsumer = failedByConsumer;
    }

    public Integer getFailedByConsumer() {
        return failedByConsumer;
    }

    public void setNotChecked(Integer notChecked) {
        this.notChecked = notChecked;
    }

    public Integer getNotChecked() {
        return notChecked;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setRejected(Integer rejected) {
        this.rejected = rejected;
    }

    public Integer getRejected() {
        return rejected;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }


}