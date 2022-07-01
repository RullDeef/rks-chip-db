package ru.spacecorp.mobdekbkp.core;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.security.entity.FilterEntity;
import org.springframework.stereotype.Component;
import ru.spacecorp.mobdekbkp.entity.Company;
import ru.spacecorp.mobdekbkp.entity.CountryType;
import ru.spacecorp.mobdekbkp.entity.Type;
import ru.spacecorp.mobdekbkp.entity.TypeClass;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.*;

@Component("mobdekbkp_FiltersCreator")
public class FiltersCreator {

    @Inject
    private Metadata metadata;

    @Inject
    private DataManager dataManager;

    @Inject
    private Messages messages;

    /**
     * Настраиваем фильтр на поиск изделий по значению атрибута "Условное обозначение"
     * Экран: mobdekbkp$Typonominal.browse
     *
     * @param name Условное обозначение
     * @return Настройки фильтра
     */
    public FilterEntity getFilterName(String name) {
        FilterEntity filterEntity = metadata.create(FilterEntity.class);
        filterEntity.setName(messages.getMessage(getClass(), "designation"));
        String filterXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<filter>\n" +
                " <and>\n" +
                "  <c name=\"name\" class=\"java.lang.String\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.name = :component$typonominalsFilter.name96792]]>\n" +
                "   <param name=\"component$typonominalsFilter.name96792\" javaClass=\"java.lang.String\">" + name + "</param>\n" +
                "  </c>\n" +
                " </and>\n" +
                "</filter>";
        filterEntity.setXml(filterXml);
        return filterEntity;
    }

    /**
     * Настраивает фильтр на поиск изделий по дате изменения
     * Экран: mobdekbkp$Typonominal.browse
     *
     * @param date Дата изменение
     * @return Настройки фильтра
     */
    public FilterEntity getFilterChange(Date date) {
        Calendar calStart = new GregorianCalendar();
        calStart.setTime(date);
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);

        Calendar calEnd = new GregorianCalendar();
        calEnd.setTime(date);
        calEnd.set(Calendar.HOUR_OF_DAY, 23);
        calEnd.set(Calendar.MINUTE, 59);
        calEnd.set(Calendar.SECOND, 59);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        FilterEntity filterEntity = metadata.create(FilterEntity.class);
        filterEntity.setName(messages.getMessage(getClass(), "dateChange"));
        String filterXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<filter>\n" +
                "  <and>\n" +
                "    <c name=\"updateTs\" class=\"java.util.Date\" operatorType=\"GREATER_OR_EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.updateTs >= :component$typonominalsFilter.updateTs02006]]>\n" +
                "      <param name=\"component$typonominalsFilter.updateTs02006\" javaClass=\"java.util.Date\">" + dateFormat.format(calStart.getTime()) + "</param>\n" +
                "    </c>\n" +
                "    <c name=\"updateTs\" class=\"java.util.Date\" operatorType=\"LESSER_OR_EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.updateTs <= :component$typonominalsFilter.updateTs52044]]>\n" +
                "      <param name=\"component$typonominalsFilter.updateTs52044\" javaClass=\"java.util.Date\">" + dateFormat.format(calEnd.getTime()) + "</param>\n" +
                "    </c>\n" +
                "  </and>\n" +
                "</filter>";
        filterEntity.setXml(filterXml);
        return filterEntity;
    }

    /**
     * Настраивает фильтр на поиск изделий по дате создания
     * Экран: mobdekbkp$Typonominal.browse
     *
     * @param date Дата создания
     * @return Настройки фильтра
     */
    public FilterEntity getFilterCreate(Date date) {
        Calendar calStart = new GregorianCalendar();
        calStart.setTime(date);
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);

        Calendar calEnd = new GregorianCalendar();
        calEnd.setTime(date);
        calEnd.set(Calendar.HOUR_OF_DAY, 23);
        calEnd.set(Calendar.MINUTE, 59);
        calEnd.set(Calendar.SECOND, 59);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        FilterEntity filterEntity = metadata.create(FilterEntity.class);
        filterEntity.setName(messages.getMessage(getClass(), "dateCreate"));
        String filterXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<filter>\n" +
                "  <and>\n" +
                "    <c name=\"createTs\" class=\"java.util.Date\" operatorType=\"GREATER_OR_EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.createTs >= :component$typonominalsFilter.createTs68806]]>\n" +
                "      <param name=\"component$typonominalsFilter.createTs68806\" javaClass=\"java.util.Date\">" + dateFormat.format(calStart.getTime()) + "</param>\n" +
                "    </c>\n" +
                "    <c name=\"createTs\" class=\"java.util.Date\" operatorType=\"LESSER_OR_EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.createTs <= :component$typonominalsFilter.createTs49884]]>\n" +
                "      <param name=\"component$typonominalsFilter.createTs49884\" javaClass=\"java.util.Date\">" + dateFormat.format(calEnd.getTime()) + "</param>\n" +
                "    </c>\n" +
                "  </and>\n" +
                "</filter>";
        filterEntity.setXml(filterXml);
        return filterEntity;
    }

    /**
     * Настраивает фильтр на поиск изделий по предприятию изготовителю
     * Экран: mobdekbkp$Typonominal.browse
     *
     * @param company Предприятие
     * @return Настройки фильтра
     */
    public FilterEntity getFilterCompany(Company company) {
        FilterEntity filterEntity = metadata.create(FilterEntity.class);
        String companyManufacture = messages.getMessage(getClass(), "companyManufacture");
        filterEntity.setName(companyManufacture);
        String filterXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<filter>\n" +
                "  <and>\n" +
                "    <c name=\"JyGBMgnPzu\" class=\"java.lang.String\" width=\"1\" type=\"CUSTOM\" locCaption=\"" + companyManufacture + "\" entityAlias=\"e\"><![CDATA[man.name.name like :component$typonominalsFilter.JyGBMgnPzu00376]]>\n" +
                "      <param name=\"component$typonominalsFilter.JyGBMgnPzu00376\" javaClass=\"java.lang.String\">" + company.getName() + "</param>\n" +
                "      <join><![CDATA[{E}.type.manufacturers man]]></join>\n" +
                "    </c>\n" +
                "  </and>\n" +
                "</filter>\n";
        filterEntity.setXml(filterXml);
        return filterEntity;
    }

    /**
     * Настраивает фильтр на поиск изделий по типу страны изготовителя
     * Экран: mobdekbkp$Typonominal.browse
     *
     * @param countryType Тип страны изготовителя
     * @return Настройки фильтра
     */
    public FilterEntity getFilterTypeCountry(CountryType countryType) {
        FilterEntity filterEntity = metadata.create(FilterEntity.class);
        String typeCountry = messages.getMessage(getClass(), "typeCountry");
        filterEntity.setName(typeCountry);
        String filterXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<filter>\n" +
                "  <and>\n" +
                "    <c name=\"McJZoVxOHm\" class=\"ru.spacecorp.mobdekbkp.entity.CountryType\" width=\"1\" type=\"CUSTOM\" locCaption=\"" + typeCountry + "\" entityAlias=\"e\"><![CDATA[ct.countryType = :component$typonominalsFilter.McJZoVxOHm21908]]>\n" +
                "      <param name=\"component$typonominalsFilter.McJZoVxOHm21908\" javaClass=\"ru.spacecorp.mobdekbkp.entity.CountryType\">" + countryType + "</param>\n" +
                "      <join><![CDATA[{E}.type.manufacturers man\n" +
                "join man.name.country ct]]></join>\n" +
                "    </c>\n" +
                "  </and>\n" +
                "</filter>\n";
        filterEntity.setXml(filterXml);
        return filterEntity;
    }

    /**
     * Настраивает фильтр на поиск изделий по классам и типам стран изготовителей
     * Экран: mobdekbkp$Typonominal.browse
     *
     * @param typeClass Класс изделий ЭКБ
     * @return Настройки фильтра
     */
    public FilterEntity getFilterType(TypeClass typeClass) {
        StringBuilder childrenBuilder = new StringBuilder();
        List<TypeClass> typeClassList = new ArrayList<>();
        typeClassList.add(typeClass);
        getChildrens(typeClassList, childrenBuilder);

        String typesCountry = messages.getMessage(getClass(), "typesCountry");

        FilterEntity filterEntity = metadata.create(FilterEntity.class);
        filterEntity.setName(messages.getMessage(getClass(), "deviceForTypeClass"));
        String filterXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<filter>\n" +
                "  <and>\n" +
                "    <c name=\"type.typeClass\" class=\"ru.spacecorp.mobdekbkp.entity.TypeClass\" inExpr=\"true\" operatorType=\"IN\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.type.typeClass.id in :component$typonominalsFilter.type_typeClass86702]]>\n" +
                "      <param name=\"component$typonominalsFilter.type_typeClass86702\" javaClass=\"ru.spacecorp.mobdekbkp.entity.TypeClass\">" + childrenBuilder.deleteCharAt(0).toString() + "</param>\n" +
                "    </c>\n" +
                "    <c name=\"WffaEKYHyr\" class=\"ru.spacecorp.mobdekbkp.entity.CountryType\" inExpr=\"true\" width=\"1\" type=\"CUSTOM\" locCaption=\"" + typesCountry + "\" entityAlias=\"e\"><![CDATA[ct.countryType in :component$typonominalsFilter.WffaEKYHyr86247]]>\n" +
                "      <param name=\"component$typonominalsFilter.WffaEKYHyr86247\" javaClass=\"ru.spacecorp.mobdekbkp.entity.CountryType\">friendly,russian,foreign</param>\n" +
                "      <join><![CDATA[{E}.type.manufacturers man\n" +
                "join man.name.country ct]]></join>\n" +
                "    </c>\n" +
                "  </and>\n" +
                "</filter>\n";
        filterEntity.setXml(filterXml);
        return filterEntity;
    }


    /**
     * Настраивает фильтр на поиск изделий по классам
     * Экран: mobdekbkp$Typonominal.browse
     *
     * @param typeClass Класс изделий ЭКБ
     * @return Настройки фильтра
     */
    public FilterEntity getFilterTypeClass(TypeClass typeClass) {
        StringBuilder childrenBuilder = new StringBuilder();
        List<TypeClass> typeClassList = new ArrayList<>();
        typeClassList.add(typeClass);
        getChildrens(typeClassList, childrenBuilder);

        FilterEntity filterEntity = metadata.create(FilterEntity.class);
        filterEntity.setName(messages.getMessage(getClass(), "deviceForTypeClass"));
        String filterXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<filter>\n" +
                "  <and>\n" +
                "    <c name=\"type.typeClass\" class=\"ru.spacecorp.mobdekbkp.entity.TypeClass\" inExpr=\"true\" operatorType=\"IN\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.type.typeClass.id in :component$typonominalsFilter.type_typeClass86702]]>\n" +
                "      <param name=\"component$typonominalsFilter.type_typeClass86702\" javaClass=\"ru.spacecorp.mobdekbkp.entity.TypeClass\">" + childrenBuilder.deleteCharAt(0).toString() + "</param>\n" +
                "    </c>\n" +
                "  </and>\n" +
                "</filter>\n";
        filterEntity.setXml(filterXml);
        return filterEntity;
    }


    /**
     * Настраивает фильтр на поиск изделий по типам
     * Экран: mobdekbkp$Typonominal.browse
     *
     * @param type Класс изделий ЭКБ
     * @return Настройки фильтра
     */
    public FilterEntity getFilterTypes(Type type) {
        FilterEntity filterEntity = metadata.create(FilterEntity.class);
        //  filterEntity.setName(messages.getMessage(getClass(), "Types"));
        String filterXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<filter>\n" +
                "  <and>\n" +
                "<c name=\"type\" class=\"ru.spacecorp.mobdekbkp.entity.Type\" operatorType=\"EQUAL\" width=\"1\" type=\"PROPERTY\"><![CDATA[e.type.id = :component$typonominalsFilter.type86159]]>\n" +
                "<param name=\"component$typonominalsFilter.type86159\" javaClass=\"ru.spacecorp.mobdekbkp.entity.Type\">" + type.getId() + "</param>\n" +
                "</c>\n" +
                "  </and>\n" +
                "</filter>\n";
        filterEntity.setXml(filterXml);
        return filterEntity;
    }

    /**
     * Получение всех детей по примой ветке наследования включая родителя
     *
     * @param objects         Объекты класса
     * @param childrenBuilder Список детей
     */
    private void getChildrens(List<TypeClass> objects, StringBuilder childrenBuilder) {
        List<TypeClass> typeClassList = dataManager.loadList(
                LoadContext.create(TypeClass.class)
                        .setQuery(
                                LoadContext.createQuery(
                                        "select tc " +
                                                "from mobdekbkp$TypeClass tc " +
                                                "where tc in :listId")
                                        .setParameter("listId", objects)
                        ).setView("typeClass-children-view"));

        for (TypeClass o : typeClassList) {
            List<TypeClass> childrens = o.getChildren();
            childrenBuilder.append(",").append(o.getId().toString());
            if (childrens != null && childrens.size() != 0) {
                getChildrens(childrens, childrenBuilder);
            }
        }
    }
}
