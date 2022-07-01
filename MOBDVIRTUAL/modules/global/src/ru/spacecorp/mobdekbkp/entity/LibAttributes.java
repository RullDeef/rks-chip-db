package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

import java.util.List;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ru.spacecorp.documents.entity.Document;

@NamePattern("%s|type")
@Table(name = "MOBDEKBKP_LIB_ATTRIBUTES")
@Entity(name = "mobdekbkp$LibAttributes")
public class LibAttributes extends StandardEntity {
    private static final long serialVersionUID = 2255148894647174972L;

    @Column(name = "TYPE_", nullable = false)
    protected String type;

    @Column(name = "SERIES")
    protected String series;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VIEW_MODEL_ID")
    protected Document viewModel;

    @Column(name = "VIEW_NAME")
    protected String viewName;

    @Column(name = "ALTER_VIEW")
    protected String alterView;

    @Column(name = "POSITION_PREFIX")
    protected String positionPrefix;

    @Column(name = "KEY_ATTRIBUTE")
    protected String keyAttribute;

    @Column(name = "RANGE_DEFINITION")
    protected String rangeDefinition;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LAND_MODEL_ID")
    protected Document landModel;

    @Column(name = "LAND_NAME")
    protected String landName;

    @Column(name = "ALTER_LAND")
    protected String alterLand;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDE_ATTRIB_ID")
    protected Document ideAttrib;


    @JoinTable(name = "MOBDEKBKP_LIB_ATTRIBUTES_DOCUMENT_LINK",
            joinColumns = @JoinColumn(name = "LIB_ATTRIBUTES_ID"),
            inverseJoinColumns = @JoinColumn(name = "DOCUMENT_ID"))
    @ManyToMany
    protected List<Document> documents;

    public Document getViewModel() {
        return viewModel;
    }

    public void setViewModel(Document viewModel) {
        this.viewModel = viewModel;
    }


    public Document getLandModel() {
        return landModel;
    }

    public void setLandModel(Document landModel) {
        this.landModel = landModel;
    }


    public Document getIdeAttrib() {
        return ideAttrib;
    }

    public void setIdeAttrib(Document ideAttrib) {
        this.ideAttrib = ideAttrib;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSeries() {
        return series;
    }


    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setAlterView(String alterView) {
        this.alterView = alterView;
    }

    public String getAlterView() {
        return alterView;
    }

    public void setPositionPrefix(String positionPrefix) {
        this.positionPrefix = positionPrefix;
    }

    public String getPositionPrefix() {
        return positionPrefix;
    }

    public void setKeyAttribute(String keyAttribute) {
        this.keyAttribute = keyAttribute;
    }

    public String getKeyAttribute() {
        return keyAttribute;
    }

    public void setRangeDefinition(String rangeDefinition) {
        this.rangeDefinition = rangeDefinition;
    }

    public String getRangeDefinition() {
        return rangeDefinition;
    }


    public void setLandName(String landName) {
        this.landName = landName;
    }

    public String getLandName() {
        return landName;
    }

    public void setAlterLand(String alterLand) {
        this.alterLand = alterLand;
    }

    public String getAlterLand() {
        return alterLand;
    }


}