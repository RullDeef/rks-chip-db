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

@NamePattern("%s |typonominal")
@Table(name = "MOBDEKBKP_OUTER_REJECTION")
@Entity(name = "mobdekbkp$OuterRejection")
public class OuterRejection extends StandardEntity {
    private static final long serialVersionUID = 3358984848859683196L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @Column(name = "CHECKED")
    protected Integer checked;

    @Column(name = "REJECTED")
    protected Integer rejected;

    @Lob
    @Column(name = "REASON")
    protected String reason;

    @Lob
    @Column(name = "COMMENT_")
    protected String comment;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMPORT_DEVICE_ID")
    protected ImportDevice importDevice;

    public void setImportDevice(ImportDevice importDevice) {
        this.importDevice = importDevice;
    }

    public ImportDevice getImportDevice() {
        return importDevice;
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