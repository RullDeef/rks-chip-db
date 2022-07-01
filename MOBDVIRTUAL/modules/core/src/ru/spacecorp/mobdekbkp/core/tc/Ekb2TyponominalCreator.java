package ru.spacecorp.mobdekbkp.core.tc;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.core.entityWorker.*;
import ru.spacecorp.mobdekbkp.entity.*;
import ru.spacecorp.mobdekbkp.entity.tc.AttrEkbObject;
import ru.spacecorp.mobdekbkp.entity.tc.EkbObject;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;


@Component("mobdekbkp_Ekb2TyponominalCreator")
public class Ekb2TyponominalCreator {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;
    @Inject
    private MappingTableWorker mappingTableWorker;
    @Inject
    private CountryCreator countryCreator;
    @Inject
    private CompanyCreator companyCreator;
    @Inject
    private DocumentCreator documentCreator;
    @Inject
    private UnitDevCreator unitDevCreator;
    @Inject
    private TypeCreator typeCreator;
    @Inject
    private TyponominalCreator typonominalCreator;
    @Inject
    private TypeManufacturerEntryCreator typeManufacturerEntryCreator;
    @Inject
    private TypeCalicoholdersEntryCreator typeCalicoholdersEntryCreator;
    @Inject
    private TyponominalQualityLevelNativeCreator typonominalQualityLevelNativeCreator;
    @Inject
    private TyponominalBodyCreator typonominalBodyCreator;
    @Inject
    private MaterialCreator materialCreator;
    @Inject
    private ParameterValueCreator parameterValueCreator;
    private Country notCountry;

    private UnitDev unitDev;

    public String create(EkbObject ekbObject, HashMap<String, Parameter> parameters) {
        unitDev = unitDevCreator.create("штуки");
        LinkedHashMap<String, String> attrs = getAttrsMap(ekbObject.getAttrs());
        TypeClass typeClass = mappingTableWorker.getTypeClass(ekbObject.getParent());
        if (typeClass != null) {
            Country Страна_производитель = countryCreator.create(
                    attrs.get(AttrNameMap.Страна_производитель_20008),
                    CountryType.russian
            );
            Company Производитель = companyCreator.create(
                    attrs.get(AttrNameMap.Фирма_производитель_20006),
                    Страна_производитель,
                    "Производитель ЭКБ",
                    "нет данных", "нет данных", "нет данных"
            );
            Company Калькодержатель = companyCreator.create(
                    attrs.get(AttrNameMap.Фирма_калькодержатель_20007),
                    getCountryFromCompany(attrs.get(AttrNameMap.Фирма_калькодержатель_20007)),
                    null,
                    "нет данных", "нет данных", "нет данных"
            );


            Type Тип_ЭКБ = typeCreator.create(ekbObject.getName(), typeClass, "нет данных", unitDev, ExtBoolean.notSet, TypePlacementCategory.notSet);
            Тип_ЭКБ.setManufacturers(typeManufacturerEntryCreator.add(Тип_ЭКБ, Производитель));
            Тип_ЭКБ.setCalicoholders(typeCalicoholdersEntryCreator.add(Тип_ЭКБ, Калькодержатель));
            Тип_ЭКБ.setDocumnetsDelivery(documentCreator.create(attrs.get(AttrNameMap.Технические_условия_20005), "Технические условия"));

            Material материал_корпуса = materialCreator.create(attrs.get(AttrNameMap.Материал_корпуса_20151), MaterialType.bodies);
            Material материал_контактов = materialCreator.create(attrs.get(AttrNameMap.Материал_контактов_20150), MaterialType.pins);
            Material материал_покрытия_выводов = materialCreator.create(attrs.get(AttrNameMap.Покрытие_контактов_20152), MaterialType.pins);
            TyponominalBody Корпус = typonominalBodyCreator.create(
                    ekbObject.getName(), attrs.get(AttrNameMap.Корпуc_105010048), ExtBoolean.notSet
            );
            Корпус.setBodyMaterial(материал_корпуса);
            Корпус.setPinsMaterial(материал_контактов);
            Корпус.setCoatingMaterial(материал_покрытия_выводов);


            for (AttrEkbObject attrEkbObject : ekbObject.getAttrs()) {
                Parameter parameter = parameters.get(attrEkbObject.getAttrId());
                parameterValueCreator.create(Тип_ЭКБ, parameter, parameter.getDefaultValueType(), attrEkbObject.getValue());
            }


            TyponominalQualityLevelNative Уровень_качества = typonominalQualityLevelNativeCreator.create(attrs.get(AttrNameMap.Уровень_качества_20009));

            Typonominal typonominal = typonominalCreator.create(
                    ekbObject.getName(),
                    Тип_ЭКБ,
                    ExtBoolean.notSet,
                    ExtBoolean.notSet,
                    ExtBoolean.notSet,
                    ExtBoolean.notSet,
                    ExtBoolean.notSet,
                    ExtBoolean.notSet,
                    TyponominalLifeCycleStage.productionNotPlanStop
            );
            typonominal.setTyponominalQualityLevelNative(Уровень_качества);
            typonominal.setBody(Корпус);
            try {
                typonominal.setMass(Double.parseDouble(attrs.get(AttrNameMap.Масса__г_20072)));
            } catch (Exception e) {
            }

            return "0";

        }

        return "-1";
    }

    private LinkedHashMap<String, String> getAttrsMap(LinkedHashSet<AttrEkbObject> attrsSet) {
        LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
        for (AttrEkbObject attrEkbObject : attrsSet) {
            attrs.put(attrEkbObject.getAttrId(), attrEkbObject.getValue());
        }
        return attrs;
    }


    private Country getCountryFromCompany(String companyName) {
        TypedQuery<Company> query = persistence.getEntityManager().createQuery(
                "select e \n" +
                        "from mobdekbkp$Company e\n" +
                        "where e.name = :nameCompany",
                Company.class);
        query.setParameter("nameCompany", companyName);
        query.setViewName(View.LOCAL);

        Company company = query.getFirstResult();

        return company != null ? company.getCountry() : getNullCountry();
    }

    private Country getNullCountry() {
        if (notCountry != null)
            return notCountry;

        TypedQuery<Country> query = persistence.getEntityManager().createQuery(
                "select e \n" +
                        "from mobdekbkp$Country e\n" +
                        "where e.name = '_'", Country.class
        );

        Country country = query.getFirstResult();
        if (country == null) {
            country = metadata.create(Country.class);
            country.setName("_");
            country.setCountryType(CountryType.russian);
            persistence.getEntityManager().persist(country);

            notCountry = country;
        }

        return country;
    }

    public void createCompanyHoldDocs() {
        Query query = persistence.getEntityManager().createNativeQuery(
                "select ekb.valueattrib\n" +
                        "from public.ekb\n" +
                        "where ekb.idattrib = '20007'\n" +
                        "group by ekb.valueattrib");
        List list = query.getResultList();
        for (Object aList : list) {
            String name = (String) aList;
            companyCreator.create(name, getCountryFromCompany(name), null, "нет данных", "нет данных", "нет данных");
        }
    }

    public void createCompany() {
        Query query = persistence.getEntityManager().createNativeQuery(
                "select ekb.valueattrib, ekb2.valueattrib\n" +
                        "from public.ekb ekb\n" +
                        "join public.ekb ekb2 on ekb.idtc = ekb2.idtc\n" +
                        "where ekb.idattrib = '20006' and ekb2.idattrib = '20008'\n" +
                        "group by ekb.valueattrib, ekb2.valueattrib");
        List list = query.getResultList();
        for (Object aList : list) {
            Object[] row = (Object[]) aList;
            Country Страна_производитель = countryCreator.create((String) row[1], CountryType.russian);
            companyCreator.create((String) row[0], Страна_производитель, "Производитель ЭКБ", "нет данных", "нет данных", "нет данных");
        }
    }

    /**
     * Создает страны на основе всех записей в базе
     */
    public void createCountry() {
        Query query = persistence.getEntityManager().createNativeQuery(
                "select ekb.valueattrib\n" +
                        "from public.ekb ekb\n" +
                        "where ekb.idattrib = '20008'\n" +
                        "group by ekb.valueattrib");
        List list = query.getResultList();
        for (Object aList : list) {
            String name = (String) aList;
            countryCreator.create(name, CountryType.russian);
        }
    }
}


