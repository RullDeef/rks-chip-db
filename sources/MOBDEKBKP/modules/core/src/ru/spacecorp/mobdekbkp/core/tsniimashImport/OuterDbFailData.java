package ru.spacecorp.mobdekbkp.core.tsniimashImport;

import java.util.Date;

/**
 * Created by Stepanov_ME on 28.01.2019.
 */
public class OuterDbFailData {

    private String typonominal;
    private String index;
    private Date manufactureDate;
    private Date failDate;
    private String workFact;
    private String workGuarantee;
    private String claimDocs;
    private Date docDate;
    private String manufacturer;
    private String claimedCompany;
    private String failType;
    private String failTypeComment;
    private String repeating;
    private String part;
    private String visibleFail;
    private String description;
    private String comissionResume;
    private String previousResume;
    private String source;


    public OuterDbFailData() {
    }

    public void setTyponominal(String typonominal) {
        this.typonominal = typonominal;
    }

    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getTyponominal() {
        return typonominal;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }

    public String getIndex() {
        return index;
    }

    public Date getFailDate() {
        return failDate;
    }

    public String getWorkFact() {
        return workFact;
    }

    public String getWorkGuarantee() {
        return workGuarantee;
    }

    public String getClaimDocs() {
        return claimDocs;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getClaimedCompany() {
        return claimedCompany;
    }

    public String getFailType() {
        return failType;
    }

    public String getFailTypeComment() {
        return failTypeComment;
    }

    public String getRepeating() {
        return repeating;
    }

    public String getPart() {
        return part;
    }

    public String getVisibleFail() {
        return visibleFail;
    }

    public String getDescription() {
        return description;
    }

    public String getComissionResume() {
        return comissionResume;
    }

    public String getPreviousResume() {
        return previousResume;
    }

    public String getSource() {
        return source;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setFailDate(Date failDate) {
        this.failDate = failDate;
    }

    public void setWorkFact(String workFact) {
        this.workFact = workFact;
    }

    public void setWorkGuarantee(String workGuarantee) {
        this.workGuarantee = workGuarantee;
    }

    public void setClaimDocs(String claimDocs) {
        this.claimDocs = claimDocs;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setClaimedCompany(String claimedCompany) {
        this.claimedCompany = claimedCompany;
    }

    public void setFailType(String failType) {
        this.failType = failType;
    }

    public void setFailTypeComment(String failTypeComment) {
        this.failTypeComment = failTypeComment;
    }

    public void setRepeating(String repeating) {
        this.repeating = repeating;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public void setVisibleFail(String visibleFail) {
        this.visibleFail = visibleFail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComissionResume(String comissionResume) {
        this.comissionResume = comissionResume;
    }

    public void setPreviousResume(String previousResume) {
        this.previousResume = previousResume;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }
}
