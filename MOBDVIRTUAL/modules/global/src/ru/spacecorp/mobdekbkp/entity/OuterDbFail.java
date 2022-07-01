package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamePattern("%s|typonominal")
@Table(name = "MOBDEKBKP_OUTER_DB_FAIL")
@Entity(name = "mobdekbkp$OuterDbFail")
public class OuterDbFail extends StandardEntity {
    private static final long serialVersionUID = 8910243889164402750L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TYPONOMINAL_ID")
    protected Typonominal typonominal;

    @Column(name = "INDEX_", nullable = false)
    protected String index;

    @Temporal(TemporalType.DATE)
    @Column(name = "MAUFACTURE_DATE")
    protected Date maufactureDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "FAIL_DATE")
    protected Date failDate;

    @Column(name = "WORK_FACT")
    protected Double workFact;

    @Column(name = "WORK_GUARANTEE")
    protected Double workGuarantee;

    @Lob
    @Column(name = "CLAIM_DOCS")
    protected String claimDocs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANUFACTURER_ID")
    protected Company manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLAIMED_COMPANY_ID")
    protected Company claimedCompany;

    @Lob
    @Column(name = "FAIL_TYPE")
    protected String failType;

    @Lob
    @Column(name = "FAIL_TYPE_COMMENT")
    protected String failTypeComment;

    @Column(name = "REPEATING")
    protected String repeating;

    @Column(name = "PART")
    protected String part;

    @Lob
    @Column(name = "VISIBLE_FAIL")
    protected String visibleFail;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    @Lob
    @Column(name = "COMISSION_RESUME")
    protected String comissionResume;

    @Lob
    @Column(name = "PREVIOUS_RESUME")
    protected String previousResume;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SOURCE_ID")
    protected OuterInformationSource source;


    public Company getClaimedCompany() {
        return claimedCompany;
    }

    public void setClaimedCompany(Company claimedCompany) {
        this.claimedCompany = claimedCompany;
    }


    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public void setMaufactureDate(Date maufactureDate) {
        this.maufactureDate = maufactureDate;
    }

    public Date getMaufactureDate() {
        return maufactureDate;
    }

    public void setFailDate(Date failDate) {
        this.failDate = failDate;
    }

    public Date getFailDate() {
        return failDate;
    }

    public void setWorkFact(Double workFact) {
        this.workFact = workFact;
    }

    public Double getWorkFact() {
        return workFact;
    }

    public void setWorkGuarantee(Double workGuarantee) {
        this.workGuarantee = workGuarantee;
    }

    public Double getWorkGuarantee() {
        return workGuarantee;
    }

    public void setClaimDocs(String claimDocs) {
        this.claimDocs = claimDocs;
    }

    public String getClaimDocs() {
        return claimDocs;
    }

    public void setFailType(String failType) {
        this.failType = failType;
    }

    public String getFailType() {
        return failType;
    }

    public void setFailTypeComment(String failTypeComment) {
        this.failTypeComment = failTypeComment;
    }

    public String getFailTypeComment() {
        return failTypeComment;
    }

    public void setRepeating(String repeating) {
        this.repeating = repeating;
    }

    public String getRepeating() {
        return repeating;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getPart() {
        return part;
    }

    public void setVisibleFail(String visibleFail) {
        this.visibleFail = visibleFail;
    }

    public String getVisibleFail() {
        return visibleFail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setComissionResume(String comissionResume) {
        this.comissionResume = comissionResume;
    }

    public String getComissionResume() {
        return comissionResume;
    }

    public void setPreviousResume(String previousResume) {
        this.previousResume = previousResume;
    }

    public String getPreviousResume() {
        return previousResume;
    }


    public void setTyponominal(Typonominal typonominal) {
        this.typonominal = typonominal;
    }

    public Typonominal getTyponominal() {
        return typonominal;
    }

    public void setManufacturer(Company manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Company getManufacturer() {
        return manufacturer;
    }

    public void setSource(OuterInformationSource source) {
        this.source = source;
    }

    public OuterInformationSource getSource() {
        return source;
    }


}