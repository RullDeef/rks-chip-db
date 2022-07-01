package ru.spacecorp.mobdekbkp.service;

import com.haulmont.cuba.security.entity.FilterEntity;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.springframework.stereotype.Service;
import ru.spacecorp.mobdekbkp.core.FiltersCreator;
import ru.spacecorp.mobdekbkp.core.GraphicsDataCreator;
import ru.spacecorp.mobdekbkp.entity.Company;
import ru.spacecorp.mobdekbkp.entity.CountryType;
import ru.spacecorp.mobdekbkp.entity.TypeClass;

import javax.inject.Inject;

// cheating
// import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Dictionary;
import java.util.Map;
import java.util.UUID;

@Service(DashBoardService.NAME)
public class DashBoardServiceBean implements DashBoardService {

    @Inject
    private GraphicsDataCreator graphicsDataCreator;

    @Inject
    private FiltersCreator filtersCreator;

    @Override
    @Transactional
    public Map<Company, Integer> getCompanyPieData() {
        return graphicsDataCreator.CompanyPieData();
    }

    @Override
    @Transactional
    public Map<Date, Integer> getDaysLineData(int lastDay) {
        return graphicsDataCreator.getDaysLineData(lastDay);
    }

    @Override
    @Transactional
    public Map<Date, Integer> getDaysChangeLineData(int lastDay) {
        return graphicsDataCreator.getDaysChangeLineData(lastDay);
    }

    @Override
    @Transactional
    public Map<CountryType, Integer> getDeviceContryCount() {
        return graphicsDataCreator.getDeviceContryCount();
    }

    @Override
    @Transactional
    public Map<TypeClass, Map<CountryType, Integer>> getTypeDeviceCount() {
        return graphicsDataCreator.getTypeDeviceCount();
    }

    @Override
    public FilterEntity getFilterName(String name) {
        return filtersCreator.getFilterName(name);
    }

    @Override
    public FilterEntity getFilterChange(Date date) {
        return filtersCreator.getFilterChange(date);
    }

    @Override
    public FilterEntity getFilterCreate(Date date) {
        return filtersCreator.getFilterCreate(date);
    }

    @Override
    public FilterEntity getFilterCompany(Company company) {
        return filtersCreator.getFilterCompany(company);
    }

    @Override
    public FilterEntity getFilterTypeCountry(CountryType countryType) {
        return filtersCreator.getFilterTypeCountry(countryType);
    }

    @Override
    @Transactional
    public FilterEntity getFilterType(TypeClass typeClass) {
        return filtersCreator.getFilterType(typeClass);
    }

    @Override
    public void updateData() {
        int lastDay = 15;
        graphicsDataCreator.updateData(lastDay);
    }


}