package ru.spacecorp.documents.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.validation.constraints.NotNull;

@NamePattern(" |key")
@Table(name = "DOCUMENTS_DOCUMENT_RESTRICTION")
@Entity(name = "documents$DocumentRestriction")
public class DocumentRestriction extends StandardEntity {
    private static final long serialVersionUID = -3568887247503168394L;

    @NotNull
    @Column(name = "KEY_", nullable = false, unique = true)
    protected String key;

    @JoinTable(name = "DOCUMENTS_DOCUMENT_RESTRICTION_DOCUMENT_TYPE_LINK",
        joinColumns = @JoinColumn(name = "DOCUMENT_RESTRICTION_ID"),
        inverseJoinColumns = @JoinColumn(name = "DOCUMENT_TYPE_ID"))
    @ManyToMany
    protected List<DocumentType> restriction;


    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


    public void setRestriction(List<DocumentType> restriction) {
        this.restriction = restriction;
    }

    public List<DocumentType> getRestriction() {
        return restriction;
    }


}