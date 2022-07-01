package ru.spacecorp.mobdekbkp.web.tc;

import com.haulmont.cuba.gui.components.AbstractWindow;
import ru.spacecorp.mobdekbkp.entity.Parameter;
import ru.spacecorp.mobdekbkp.entity.tc.EkbObject;
import ru.spacecorp.mobdekbkp.service.TeamCenterIntegrationService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

public class Teamcenterimport extends AbstractWindow {
    @Inject
    private TeamCenterIntegrationService teamCenterIntegrationService;

    public void onStartClick() {
        teamCenterIntegrationService.createTypeClass();

        List<String> ekbIdList = teamCenterIntegrationService.getTeamCenterData_EkbId();
        HashMap<String, Parameter> parameterHashMap = teamCenterIntegrationService.getTeamCenterParameters();
        Integer i = 0;
        for (String id : ekbIdList) {
            EkbObject ekbObject = teamCenterIntegrationService.getTeamCenterData_Ekb(id);
            if (teamCenterIntegrationService.createTyponominal(ekbObject, parameterHashMap).equals("-1")) {
                System.out.println("Проблема с объектом" + ekbObject.getName());
            } else {
                System.out.println(i++);
            }
        }


    }

    private class ekbObject {

    }

    public void onCreateCompanyHoldDocsClick() {
        teamCenterIntegrationService.createCompanyHoldDocs();
    }

    public void onCreateCompanyClick() {
        teamCenterIntegrationService.createCompany();
    }

    public void onCreateCountryClick() {
        teamCenterIntegrationService.createCountry();
    }
}