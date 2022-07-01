package ru.spacecorp.mobdekbkp.service;


import com.haulmont.cuba.security.entity.FilterEntity;
import ru.spacecorp.mobdekbkp.entity.Company;
import ru.spacecorp.mobdekbkp.entity.CountryType;
import ru.spacecorp.mobdekbkp.entity.TypeClass;

import java.util.*;

public interface DashBoardService {
    String NAME = "mobdekbkp_DashBoardService";

    Map<Company, Integer> getCompanyPieData();

    Map<Date, Integer> getDaysLineData(int lastDay);

    Map<Date, Integer> getDaysChangeLineData(int lastDay);

    Map<CountryType, Integer> getDeviceContryCount();

    Map<TypeClass, Map<CountryType, Integer>> getTypeDeviceCount();

    FilterEntity getFilterName(String name);

    FilterEntity getFilterChange(Date date);

    FilterEntity getFilterCreate(Date date);

    FilterEntity getFilterCompany(Company company);

    FilterEntity getFilterTypeCountry(CountryType countryType);

    FilterEntity getFilterType(TypeClass typeClass);

    void updateData();
}