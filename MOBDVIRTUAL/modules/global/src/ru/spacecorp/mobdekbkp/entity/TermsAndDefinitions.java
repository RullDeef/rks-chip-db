package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Lob;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s %s|term,source")
@Table(name = "MOBDEKBKP_TERMS_AND_DEFINITIONS")
@Entity(name = "mobdekbkp$TermsAndDefinitions")
public class TermsAndDefinitions extends StandardEntity {
    private static final long serialVersionUID = 3686550582081508903L;

    @Lob
    @Column(name = "TERM", nullable = false)
    protected String term;

    @Lob
    @Column(name = "DEFINITION_", nullable = false)
    protected String definition;

    @Lob
    @Column(name = "SOURCE")
    protected String source;

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getDefinition() {
        return definition;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }


}