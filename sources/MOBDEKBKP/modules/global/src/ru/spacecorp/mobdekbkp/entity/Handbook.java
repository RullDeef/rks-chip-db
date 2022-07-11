package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import java.util.List;
import javax.persistence.OneToMany;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

@NamePattern("%s|name")
@Table(name = "MOBDEKBKP_HANDBOOK")
@Entity(name = "mobdekbkp$Handbook")
public class Handbook extends StandardEntity {
    private static final long serialVersionUID = 5751360601338086411L;

    @Column(name = "NAME")
    protected String name;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "handbook")
    protected List<HandbookEntry> entries;

    @Column(name = "PUBLISHED")
    protected Boolean published;

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getPublished() {
        return published;
    }


    public void setEntries(List<HandbookEntry> entries) {
        this.entries = entries;
    }

    public List<HandbookEntry> getEntries() {
        return entries;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}