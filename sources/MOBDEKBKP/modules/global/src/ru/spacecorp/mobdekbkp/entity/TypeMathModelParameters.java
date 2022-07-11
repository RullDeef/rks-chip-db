package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import javax.persistence.JoinColumn;

import java.util.List;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@NamePattern("%s|elProcModelName")
@Table(name = "MOBDEKBKP_TYPE_MATH_MODEL_PARAMETERS")
@Entity(name = "mobdekbkp$TypeMathModelParameters")
public class TypeMathModelParameters extends StandardEntity {
    private static final long serialVersionUID = 4813408623385791525L;

    @Column(name = "EL_PROC_MODEL_NAME", nullable = false)
    protected String elProcModelName;

    @Column(name = "EL_PROC_DESCRIPTION")
    protected String elProcDescription;

    @Column(name = "EL_PROC_MODEL_CATEGORY")
    protected String elProcModelCategory;

    @Column(name = "EL_PROC_MODEL_SUBCATEGORY")
    protected String elProcModelSubcategory;

    @Column(name = "EL_PROC_MODEL_CONNECTION_LIST")
    protected String elProcModelConnectionList;

    @Column(name = "EL_PROC_MODEL_PREFIX")
    protected String elProcModelPrefix;

    @Column(name = "EL_PROC_MODEL_TO_VIEW_COMPARE")
    protected String elProcModelToViewCompare;

    @JoinTable(name = "MOBDEKBKP_TYPE_MATH_MODEL_PARAMETERS_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "TYPE_MATH_MODEL_PARAMETERS_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> heatModelParameters;

    @Column(name = "APPLY_BOUNDS")
    protected String applyBounds;

    public List<Document> getHeatModelParameters() {
        return heatModelParameters;
    }

    public void setHeatModelParameters(List<Document> heatModelParameters) {
        this.heatModelParameters = heatModelParameters;
    }


    public void setElProcModelName(String elProcModelName) {
        this.elProcModelName = elProcModelName;
    }

    public String getElProcModelName() {
        return elProcModelName;
    }

    public void setElProcDescription(String elProcDescription) {
        this.elProcDescription = elProcDescription;
    }

    public String getElProcDescription() {
        return elProcDescription;
    }

    public void setElProcModelCategory(String elProcModelCategory) {
        this.elProcModelCategory = elProcModelCategory;
    }

    public String getElProcModelCategory() {
        return elProcModelCategory;
    }

    public void setElProcModelSubcategory(String elProcModelSubcategory) {
        this.elProcModelSubcategory = elProcModelSubcategory;
    }

    public String getElProcModelSubcategory() {
        return elProcModelSubcategory;
    }

    public void setElProcModelConnectionList(String elProcModelConnectionList) {
        this.elProcModelConnectionList = elProcModelConnectionList;
    }

    public String getElProcModelConnectionList() {
        return elProcModelConnectionList;
    }

    public void setElProcModelPrefix(String elProcModelPrefix) {
        this.elProcModelPrefix = elProcModelPrefix;
    }

    public String getElProcModelPrefix() {
        return elProcModelPrefix;
    }

    public void setElProcModelToViewCompare(String elProcModelToViewCompare) {
        this.elProcModelToViewCompare = elProcModelToViewCompare;
    }

    public String getElProcModelToViewCompare() {
        return elProcModelToViewCompare;
    }

    public void setApplyBounds(String applyBounds) {
        this.applyBounds = applyBounds;
    }

    public String getApplyBounds() {
        return applyBounds;
    }


}