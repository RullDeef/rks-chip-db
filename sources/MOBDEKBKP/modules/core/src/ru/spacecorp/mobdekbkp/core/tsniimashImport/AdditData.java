package ru.spacecorp.mobdekbkp.core.tsniimashImport;

/**
 * Created by Stepanov_ME on 30.01.2019.
 */
public class AdditData {

    private String parentId;
    private String failType;
    private String failComment;

    public String getParentId() {
        return parentId;
    }

    public String getFailType() {
        return failType;
    }

    public String getFailComment() {
        return failComment;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setFailType(String failType) {
        this.failType = failType;
    }

    public void setFailComment(String failComment) {
        this.failComment = failComment;
    }
}
