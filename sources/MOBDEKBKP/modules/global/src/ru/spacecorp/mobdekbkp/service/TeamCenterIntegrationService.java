package ru.spacecorp.mobdekbkp.service;


import ru.spacecorp.mobdekbkp.entity.Parameter;
import ru.spacecorp.mobdekbkp.entity.tc.EkbObject;

import java.util.HashMap;
import java.util.List;

public interface TeamCenterIntegrationService {
    String NAME = "mobdekbkp_TeamCenterIntegrationService";

    void createTypeClass();

    String createTyponominal(EkbObject ekbObject,  HashMap<String, Parameter> params);

    HashMap<String, Parameter> getTeamCenterParameters();

    EkbObject getTeamCenterData_Ekb(String id);

    List<String> getTeamCenterData_EkbId();

    void createCountry();

    void createCompany();

    void createCompanyHoldDocs();


}