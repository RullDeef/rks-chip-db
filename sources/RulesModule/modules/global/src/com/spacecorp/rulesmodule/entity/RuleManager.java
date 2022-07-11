package com.spacecorp.rulesmodule.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@NamePattern("%s|name")
@Table(name = "RULESMODULE_RULE_MANAGER")
@Entity(name = "rulesmodule$RuleManager")
public class RuleManager extends StandardEntity {
    private static final long serialVersionUID = -9050760520476118316L;

    @Column(name = "NAME")
    protected String name;

    @JoinTable(name = "RULESMODULE_RULE_MANAGER_RULE_SCRIPT_LINK",
        joinColumns = @JoinColumn(name = "RULE_MANAGER_ID"),
        inverseJoinColumns = @JoinColumn(name = "RULE_SCRIPT_ID"))
    @ManyToMany
    protected List<RuleScript> script;

    @JoinTable(name = "RULESMODULE_RULE_MANAGER_RULE_DATA_SCRIPT_LINK",
        joinColumns = @JoinColumn(name = "RULE_MANAGER_ID"),
        inverseJoinColumns = @JoinColumn(name = "RULE_DATA_SCRIPT_ID"))
    @ManyToMany
    protected List<RuleDataScript> dataScript;

    @Column(name = "ENTITY")
    protected String entity;
    public List<RuleDataScript> getDataScript() {
        return dataScript;
    }

    public void setDataScript(List<RuleDataScript> dataScript) {
        this.dataScript = dataScript;
    }



    public void setScript(List<RuleScript> script) {
        this.script = script;
    }

    public List<RuleScript> getScript() {
        return script;
    }



    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}