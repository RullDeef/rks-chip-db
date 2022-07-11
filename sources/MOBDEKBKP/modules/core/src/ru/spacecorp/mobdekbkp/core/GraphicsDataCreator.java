package ru.spacecorp.mobdekbkp.core;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.global.ValueLoadContext;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.*;

import javax.inject.Inject;
import java.util.*;
import java.util.List;


@Component("mobdekbkp_GraphicsDataCreator")
public class GraphicsDataCreator {

    @Inject
    private Persistence persistence;

    @Inject
    private DataManager dataManager;

    @Inject
    private TimeSource timeSource;

    private Map<Company, Integer> companyMap;
    private Map<Date, Integer> daysMap;
    private Map<Date, Integer> daysChangeMap;
    private Map<CountryType, Integer> deviceCountryMap;
    private Map<TypeClass, Map<CountryType, Integer>> typeDeviceMap;


    /**
     * Формирует каталог для круговой диаграммы
     *
     * @return Каталог(Компания, Количетсво внесенных ЭКБ в базу)
     */
    public Map<Company, Integer> calcCompanyPieData() {
        try (Transaction tx = persistence.createTransaction()) {
            Map<Company, Integer> map = new HashedMap<>();
            EntityManager em = persistence.getEntityManager();

            //TODO(terekhov): Сделать одним запросом через GROUP BY
            TypedQuery<Company> queryCompany = em.createQuery(
                    "select c from mobdekbkp$Company c", Company.class
            );


            List<Company> list = queryCompany.getResultList();
            for (Company company : list) {
                Long countL = (Long) em.createQuery(
                        "select count(ekb) " +
                                "from mobdekbkp$Typonominal ekb " +
                                "join ekb.type ekbType " +
                                "join ekbType.manufacturers manufacturer " +
                                "where manufacturer.name.id = :company")
                        .setParameter("company", company.getId())
                        .getSingleResult();
                map.put(company, countL.intValue());
            }

            companyMap = map;
            tx.commit();
        }
        return companyMap;
    }

    /**
     * Количество ЭКБ введенных за последнюю неделю по дням
     *
     * @param lastDay Количество дней выборки
     * @return Список <день, количество>
     */
    public Map<Date, Integer> calcDaysLineData(int lastDay) {
        Map<Date, Integer> map = new LinkedHashMap<>();

        Date currentDate = timeSource.currentTimestamp();

        Calendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, -lastDay);
        Date startDate = cal.getTime();

        ValueLoadContext context = ValueLoadContext.create()
                .setQuery(
                        ValueLoadContext.createQuery(
                                "select EXTRACT(DAY FROM ekb.createTs), " +
                                        "count(ekb) " +
                                        "from mobdekbkp$Typonominal ekb " +
                                        "where ekb.createTs >= :startDate " +
                                        "group by EXTRACT(DAY FROM ekb.createTs)"
                        ).setParameter("startDate", startDate)
                )
                .addProperty("create")
                .addProperty("count");
        List<KeyValueEntity> list = dataManager.loadValues(context);

        while (!(startDate.compareTo(currentDate) > 0)) {
            map.put(startDate, getCountToDate(cal.get(Calendar.DAY_OF_MONTH), list));
            cal.add(Calendar.DATE, 1);
            startDate = cal.getTime();
        }

        daysMap = map;
        return daysMap;
    }

    /**
     * Количество ЭКБ изменненых за последнюю неделю по дням
     *
     * @param lastDay Количество дней выборки
     * @return Список <день, количество>
     */
    public Map<Date, Integer> calcDaysChangeLineData(int lastDay) {
        //TODO(terekhov): Подумать над тем чтобы сделать одним методом с getDaysLineData
        Map<Date, Integer> map = new LinkedHashMap<>();

        Date currentDate = timeSource.currentTimestamp();

        Calendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, -lastDay);
        Date startDate = cal.getTime();

        ValueLoadContext context = ValueLoadContext.create()
                .setQuery(
                        ValueLoadContext.createQuery(
                                "select EXTRACT(DAY FROM ekb.updateTs), " +
                                        "count(ekb) " +
                                        "from mobdekbkp$Typonominal ekb " +
                                        "where ekb.updateTs >= :startDate and ekb.updateTs <> ekb.createTs " +
                                        "group by EXTRACT(DAY FROM ekb.updateTs)"
                        ).setParameter("startDate", startDate)
                )
                .addProperty("create")
                .addProperty("count");
        List<KeyValueEntity> list = dataManager.loadValues(context);

        while (!(startDate.compareTo(currentDate) > 0)) {
            map.put(startDate, getCountToDate(cal.get(Calendar.DAY_OF_MONTH), list));
            cal.add(Calendar.DATE, 1);
            startDate = cal.getTime();
        }

        daysChangeMap = map;

        return daysChangeMap;
    }

    /**
     * Получение количества за определенный день
     *
     * @param findDay искомая дата
     * @param list    Список <день, количество>
     * @return Количетсво ЭКБ за определенный день
     */
    private Integer getCountToDate(int findDay, List<KeyValueEntity> list) {
        int count = 0;
        for (KeyValueEntity element : list) {
            Double day = element.getValue("create");
            if (day == null)
                continue;
            if (day.intValue() == findDay) {
                Long countL = element.getValue("count");
                if (countL == null)
                    count = 0;
                else
                    count = countL.intValue();
                return count;
            }
        }
        return count;
    }

    /**
     * Получения количества ЭКБ в зависимости от типа предприятия
     *
     * @return Количество ЭКБ по типу предприятия (отеч/импорт)
     */
    public Map<CountryType, Integer> calcDeviceContryCount() {
        Map<CountryType, Integer> map = new LinkedHashMap<>();

        ValueLoadContext context = ValueLoadContext.create()
                .setQuery(
                        ValueLoadContext.createQuery(
                                "select country.countryType, count(ekb) " +
                                        "from mobdekbkp$Typonominal ekb " +
                                        "join ekb.type.manufacturers man " +
                                        "join man.name.country country " +
                                        "group by country.countryType"
                        )
                )
                .addProperty("countryType")
                .addProperty("count");

        List<KeyValueEntity> list = dataManager.loadValues(context);

        for (KeyValueEntity element : list) {
            Long countL = element.getValue("count");
            int count = countL != null ? countL.intValue() : 0;

            map.put(element.getValue("countryType"), count);
        }

        deviceCountryMap = map;
        return deviceCountryMap;
    }

    /**
     * Получение количетсва ЭКБ по типу страны производства для определенного класса
     *
     * @return Возвращаем список классов с списком элементов ЭКБ по типу страны изготовителя
     */
    public Map<TypeClass, Map<CountryType, Integer>> calcTypeDeviceCount() {
        Map<TypeClass, Map<CountryType, Integer>> map = new LinkedHashMap<>();
        /*
        Получаем интересующие нас классы экб
         */
        List<TypeClass> typeClassList = dataManager.loadList(
                LoadContext.create(TypeClass.class)
                        .setQuery(
                                LoadContext.createQuery(
                                        "select tc " +
                                                "from mobdekbkp$TypeClass tc " +
                                                "where tc.parent is null"
                                )
                        ).setView("typeClass-parent-view")
        );
        for (TypeClass typeClass : typeClassList) {
            Map<CountryType, Integer> countryTypeIntegerMap = new HashedMap<>();
            for (CountryType ct : CountryType.values()) {
                countryTypeIntegerMap.put(ct, 0);
            }
            map.put(typeClass, countryTypeIntegerMap);
        }

        List<Typonominal> typonominalList = dataManager.loadList(
                LoadContext.create(Typonominal.class)
                        .setQuery(
                                LoadContext.createQuery(
                                        "select t " +
                                                "from mobdekbkp$Typonominal t"
                                )
                        ).setView("typonominal-dashboard-view")
        );

        for (Typonominal typonominal : typonominalList) {
            // Верхний класс для элемента ЭКБ
            TypeClass typonominalTypeClass = getRootTypeClass(typonominal.getType().getTypeClass());
            // Берем интересующие нас параметры для этого класса
            Map<CountryType, Integer> countryTypeIntegerMap = map.get(typonominalTypeClass);

            HashSet<CountryType> shotType = new HashSet<>();
            for (TypeManufacturerEntry manufacturerEntry : typonominal.getType().getManufacturers()) {
                CountryType countryType = manufacturerEntry.getName().getCountry().getCountryType();
                /*
                Формально тут можно работать без цикла, так как тип страны изготовителя всегда один
                Однако не понятно, что будет если изменить тип страны когда предприятие уже добаленно
                к типу как изготовитель...
                 */
                if (!shotType.contains(countryType)) {
                    Integer currentValue = countryTypeIntegerMap.get(countryType);
                    Integer newValue = currentValue + 1;
                    countryTypeIntegerMap.put(countryType, newValue);
                    shotType.add(countryType);
                }
            }

            map.put(typonominalTypeClass, countryTypeIntegerMap);
        }

        typeDeviceMap = map;
        return typeDeviceMap;
    }

    /**
     * Поиск родительского класса для элемента ЭКБ
     *
     * @param typeClass тип класса
     * @return возврат типа класса
     */
    private TypeClass getRootTypeClass(TypeClass typeClass) {
        TypeClass resultTypeClass = null;
        if (typeClass.getParent() == null) {
            return typeClass;
        } else {
            resultTypeClass = getRootTypeClass(typeClass.getParent());
        }
        return resultTypeClass;
    }

    public void updateData(int lastDay) {
        calcCompanyPieData();
        calcDaysLineData(lastDay);
        calcDaysChangeLineData(lastDay);
        calcDeviceContryCount();
        calcTypeDeviceCount();
    }

    public Map<Company, Integer> CompanyPieData() {
        if (companyMap != null) {
            return companyMap;
        } else {
            return calcCompanyPieData();
        }
    }

    public Map<Date, Integer> getDaysLineData(int lastDay) {
        if (daysMap != null) {
            return daysMap;
        } else {
            return calcDaysLineData(lastDay);
        }
    }

    public Map<Date, Integer> getDaysChangeLineData(int lastDay) {
        if (daysChangeMap != null) {
            return daysChangeMap;
        } else {
            return calcDaysChangeLineData(lastDay);
        }
    }

    public Map<CountryType, Integer> getDeviceContryCount() {
        if (deviceCountryMap != null) {
            return deviceCountryMap;
        } else {
            return calcDeviceContryCount();
        }
    }

    public Map<TypeClass, Map<CountryType, Integer>> getTypeDeviceCount() {
        if (typeDeviceMap != null) {
            return typeDeviceMap;
        } else {
            return calcTypeDeviceCount();
        }
    }
}


