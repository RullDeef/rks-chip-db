package com.spacecorp.rulesmodule.service;


import com.haulmont.cuba.core.entity.Entity;

import java.util.List;

public interface RuleService {
    String NAME = "rulesmodule_RuleService";

    List<Object> performRules(String entity, String parameter);

    String[] getResultArray(Entity entity, String property);

    void refreshData();
}