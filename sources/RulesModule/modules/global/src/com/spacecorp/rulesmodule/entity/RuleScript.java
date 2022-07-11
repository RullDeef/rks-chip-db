package com.spacecorp.rulesmodule.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Lob;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@NamePattern("%s|name")
@Table(name = "RULESMODULE_RULE_SCRIPT")
@Entity(name = "rulesmodule$RuleScript")
public class RuleScript extends StandardEntity {
    private static final long serialVersionUID = -5083290215714791553L;

    @Column(name = "NAME")
    protected String name;

    @Lob
    @Column(name = "SCRIPT")
    protected String script;

    @Lob
    @Column(name = "COMMENT_")
    protected String comment;

    @JoinTable(name = "RULESMODULE_RULE_MANAGER_RULE_SCRIPT_LINK",
        joinColumns = @JoinColumn(name = "RULE_SCRIPT_ID"),
        inverseJoinColumns = @JoinColumn(name = "RULE_MANAGER_ID"))
    @ManyToMany
    protected List<RuleManager> ruleManagers;

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

    public void setScript(String script) {
        this.script = script;
    }

    public String getScript() {
        return script;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }


}