package ru.spacecorp.mobdekbkp.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.spacecorp.mobdekbkp.core.tc.Ekb2TyponominalCreator;
import ru.spacecorp.mobdekbkp.core.tc.TCDataExtractor;
import ru.spacecorp.mobdekbkp.core.tc.TypeClassCreator;
import ru.spacecorp.mobdekbkp.entity.tc.EkbObject;
import ru.spacecorp.mobdekbkp.entity.Parameter;

@Service(TeamCenterIntegrationService.NAME)
public class TeamCenterIntegrationServiceBean implements TeamCenterIntegrationService {
    @Inject
    private TypeClassCreator typeClassCreator;

    @Inject
    private Ekb2TyponominalCreator typonominalCreator;

    @Inject
    private TCDataExtractor tCDataExtractor;

    @Override
    @Transactional
    public void createTypeClass() {
        typeClassCreator.create();
    }

    @Override
    @Transactional
    public String createTyponominal(EkbObject ekbObject, HashMap <String, Parameter> parameters) {
        return typonominalCreator.create(ekbObject, parameters);
    }

    @Override
    @Transactional
    public HashMap<String, Parameter> getTeamCenterParameters() {
        return tCDataExtractor.getParameters();
    }

    @Override
    @Transactional
    public EkbObject getTeamCenterData_Ekb(String id) {
        return tCDataExtractor.getEkb(id);
    }

    @Override
    @Transactional
    public List<String> getTeamCenterData_EkbId() {
        return tCDataExtractor.getEkbId();
    }

    @Override
    @Transactional
    public void createCountry() {
        typonominalCreator.createCountry();

    }

    @Override
    @Transactional
    public void createCompany() {
        typonominalCreator.createCompany();
    }

    @Override
    @Transactional
    public void createCompanyHoldDocs() {
        typonominalCreator.createCompanyHoldDocs();
    }


}