package com.spacecorp.rulesmodule.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Lob;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.validation.constraints.NotNull;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@NamePattern("%s|name")
@Table(name = "RULESMODULE_RULE_DATA_SCRIPT")
@Entity(name = "rulesmodule$RuleDataScript")
public class RuleDataScript extends StandardEntity {
    private static final long serialVersionUID = -8920586660399554374L;

    @NotNull
    @Column(name = "NAME", nullable = false, unique = true)
    protected String name;

    @Column(name = "ENTITY")
    protected String entity;

    @Lob
    @Column(name = "SQL_")
    protected String sql;

    @Lob
    @Column(name = "COMMENT_")
    protected String comment;

    @JoinTable(name = "RULESMODULE_RULE_MANAGER_RULE_DATA_SCRIPT_LINK",
        joinColumns = @JoinColumn(name = "RULE_DATA_SCRIPT_ID"),
        inverseJoinColumns = @JoinColumn(name = "RULE_MANAGER_ID"))
    @ManyToMany
    protected List<RuleManager> ruleManagers;

    @Column(name = "VIEW_")
    protected String view;

    public void setView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }


    public void setRuleManagers(List<RuleManager> ruleManagers) {
        this.ruleManagers = ruleManagers;
    }

    public List<RuleManager> getRuleManagers() {
        return ruleManagers;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }


}