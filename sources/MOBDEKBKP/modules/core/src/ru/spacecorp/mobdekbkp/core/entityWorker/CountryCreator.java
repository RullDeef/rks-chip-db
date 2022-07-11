package ru.spacecorp.mobdekbkp.core.entityWorker;


import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.Country;
import ru.spacecorp.mobdekbkp.entity.CountryType;

import javax.inject.Inject;
import java.util.HashMap;

@Component("mobdekbkp_CountryCreator")
public class CountryCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;

    /**
     * Создает страну по названию и типу страны, предварительно проверив не созданна ли она уже
     *
     * @param name        Наименование страны
     * @param countryType Тип страны
     * @return Новая страна или страна уже созданная ранее
     */
    public Country create(String name, CountryType countryType) {
        EntityManager em = persistence.getEntityManager();
        name = name.equals("") ? "_" : name;

        TypedQuery<Country> query = em.createQuery(
                "select e \n" +
                        "from mobdekbkp$Country e\n" +
                        "where e.name = :name",
                Country.class);
        query.setParameter("name", name);
        query.setViewName(View.LOCAL);

        Country country = query.getFirstResult();
        if (country == null) {
            country = metadata.create(Country.class);
            country.setName(name);
            country.setCountryType(countryType);
            em.persist(country);
        }

        return country;
    }
}
