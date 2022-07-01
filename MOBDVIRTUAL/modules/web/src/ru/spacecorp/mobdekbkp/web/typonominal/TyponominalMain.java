package ru.spacecorp.mobdekbkp.web.typonominal;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.HierarchicalDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.entity.FilterEntity;
import com.haulmont.cuba.web.gui.components.WebHBoxLayout;
import com.haulmont.cuba.web.gui.components.WebLabel;
import com.haulmont.cuba.web.gui.components.WebLookupField;
import org.springframework.util.CollectionUtils;
import ru.spacecorp.mobdekbkp.entity.ParameterFilterRow;
import ru.spacecorp.mobdekbkp.entity.Type;
import ru.spacecorp.mobdekbkp.entity.TypeClass;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.web.SpacecorpWebFilter;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.*;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TyponominalMain extends AbstractLookup {

    @Inject
    private HierarchicalDatasource<TypeClass, UUID> typeClassesDs;
    @Inject
    private CollectionDatasource<Type, UUID> typesDs;
    @Inject
    private CollectionDatasource<Typonominal, UUID> typonominalsDs;
    @Inject
    private DataGrid<Typonominal> tblTn;
    @Inject
    private DataGrid<Type> tblType;
    @Inject
    private TnCardFrame cardFrame;

    private TyponominalFrame[] frames;

    //поиск
    @Inject
    private SpacecorpWebFilter typonominalsFilter;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Metadata metadata;
    private List<ParameterFilterRowFrame> parameterFilterRowFrames = new ArrayList<>();
    @Inject
    private DataManager dataManager;
    private GroupBoxLayout filterLayout;
    private FilterEntity openFilter;

    @Override
    public void init(Map<String, Object> params) {
        initFilterEntity(params);
        searchSetup();
        super.init(params);
        Map<String, Object> frameparams = new HashMap<>();
        frameparams.put(TnCardFrame.NO_INIT_TN_LABEL, getMessage("noDsLabel"));
        frameparams.put(TnCardFrame.OPTION_PARAM, TnCardFrame.MAIN_TAB);
        cardFrame.reinit(frameparams);
        tblTn.setRowDescriptionProvider(Typonominal::getName);
        tblType.setRowDescriptionProvider(Type::getDesignation);
        typonominalsFilter.setMaxResults(0);
        typonominalsDs.setMaxResults(0);
    }

    /**
     * Инициализирует значение фильтра
     *
     * @param params Параметры переданные в экран
     */
    private void initFilterEntity(Map<String, Object> params) {
        FilterEntity filterEntity = (FilterEntity) params.get("filter");
        if (filterEntity != null) {
            openFilter = filterEntity;
        }
    }


    public void addParameterFilter() {
        ParameterFilterRowFrame parameterFilterRowFrame =
                (ParameterFilterRowFrame) openFrame(null, "mobdekbkp$Parameter.filter.row.frame");
        parameterFilterRowFrame.setItem(metadata.create(ParameterFilterRow.class));
        filterLayout.add(parameterFilterRowFrame);
        parameterFilterRowFrames.add(parameterFilterRowFrame);
    }

    private void searchSetup() {
        //скрытие выпадающего списка для отображения строк и лэйбла "показывать строк"
        int s = 0;
        filterLayout = (GroupBoxLayout) typonominalsFilter.getDelegate().getLayout();
        for (Component component : filterLayout.getComponents()) {
            if (component.getClass() == WebLookupField.class) {
                component.setVisible(false);
            }
            if (component.getClass() == WebLabel.class) {
                if (s == 1) {
                    component.setVisible(false);
                }
                s++;
            }
        }

        //----------поиск по параметрам
//добавляем кнопку "Добавить поиск по параметру" и action к ней
        filterLayout = (GroupBoxLayout) typonominalsFilter.getDelegate().getLayout();
        Button addParameterFilter = componentsFactory.createComponent(Button.class);
        addParameterFilter.setAction(new AbstractAction("") {
            @Override
            public String getCaption() {
                return getMessage("caption.addParameterFilter");
            }

            @Override
            public void actionPerform(Component component) {
                addParameterFilter();
            }
        });
        filterLayout.add(addParameterFilter);
        filterLayout.setSpacing(true);
//добавляем кнопку "Добавить поиск по параметру" и action к ней
//реализация поиска
        typonominalsDs.addStateChangeListener(e -> {
            //начало - чистка данных в поисковом фрейме для нормальной работы таблиц после поиска
            for (int i = 0; i < parameterFilterRowFrames.size(); i++) {
                if (parameterFilterRowFrames.get(i).getComponent("parameterFilterRowDs") == null && !parameterFilterRowFrames.get(i).isVisible()) {
                    parameterFilterRowFrames.remove(i);
                }
                if (parameterFilterRowFrames.size() == 0) {
                    this.onBtnAllTNClick();
                }
            }
            //конец - чистка данных в поисковом фрейме для нормальной работы таблиц после поиска
            if (e.getState() != CollectionDatasource.State.INVALID || CollectionUtils.isEmpty(parameterFilterRowFrames))
                return;

            final String queryTemplate = "select e from mobdekbkp$Typonominal e join e.type t";

            StringBuilder joinBlock = new StringBuilder("");
            StringBuilder whereBlock = new StringBuilder("where 1=1 ");
            AtomicInteger counter = new AtomicInteger(0);

            parameterFilterRowFrames.stream()
                    .map(ParameterFilterRowFrame::getItem)
                    .filter(Objects::nonNull)
                    .filter(filterConjunct ->
                            filterConjunct.getParameter() != null
                                    && filterConjunct.getOperationType() != null
                                    && filterConjunct.getParameterType() != null)
                    .forEach(filterConjunct -> {
                        final StringBuilder condition = new StringBuilder();
                        final String operation = filterConjunct.getOperationTypeAsEnum().forJpql();

                        joinBlock.append(" join t.paramValue pv").append(counter.incrementAndGet());

                        switch (filterConjunct.getOperationTypeAsEnum()) {
                            case CONTAINS:
                            case DOES_NOT_CONTAIN:
                                condition.append("pv").append(counter.get())
                                        .append(".currentValueType = 'string' ")
                                        .append("and pv").append(counter.get())
                                        .append(".valString ")
                                        .append(operation)
                                        .append(" ")
                                        .append("'%" + filterConjunct.getValString() + "%'")
                                        .append(" or ")
                                        .append("pv").append(counter.get())
                                        .append(".currentValueType = 'bool' ")
                                        .append("and pv").append(counter.get())
                                        .append(".valString ")
                                        .append(operation)
                                        .append(" ")
                                        .append("'" + "Да".equals(filterConjunct.getValString()) + "'");
                                break;
                            default:
                                String value = String.format("%.0f", filterConjunct.getValFloat());
                                condition.append("pv").append(counter.get())
                                        .append(".currentValueType in ('integer', 'normal', 'gammaProbabilityValue') ")
                                        .append("and pv").append(counter.get())
                                        .append(".valFloat ")
                                        .append(operation)
                                        .append(" ")
                                        .append(value);

                                switch (filterConjunct.getOperationTypeAsEnum()) {
                                    case LESSER:
                                    case LESSER_OR_EQUAL:
                                        condition.append(" or (")
                                                .append("pv").append(counter.get())
                                                .append(".currentValueType = 'interval' ")
                                                .append("and pv").append(counter.get())
                                                .append(".valMin ")
                                                .append(operation)
                                                .append(" ")
                                                .append(value)
                                                .append(")")

                                                .append(" or (")
                                                .append("pv").append(counter.get())
                                                .append(".currentValueType = 'normalTolerated' ")
                                                .append("and pv").append(counter.get())
                                                .append(".valFloat ")
                                                .append(operation)
                                                .append(" (")
                                                .append(value)
                                                .append(" + ")
                                                .append("pv").append(counter.get())
                                                .append(".toleranceMinus))")

                                                .append(" or (")
                                                .append("pv").append(counter.get())
                                                .append(".currentValueType = 'normalToleratedPrecent' ")
                                                .append("and pv").append(counter.get())
                                                .append(".valFloat ")
                                                .append(operation)
                                                .append(" (")
                                                .append(value)
                                                .append(" + (")
                                                .append("pv").append(counter.get())
                                                .append(".valFloat ")
                                                .append(" * ")
                                                .append("pv").append(counter.get())
                                                .append(".toleranceMinus) ")
                                                .append("/ 100 ))");
                                        break;

                                    case GREATER:
                                    case GREATER_OR_EQUAL:
                                        condition.append(" or (")
                                                .append("pv").append(counter.get())
                                                .append(".currentValueType = 'interval' ")
                                                .append("and pv").append(counter.get())
                                                .append(".valMax ")
                                                .append(operation)
                                                .append(" ")
                                                .append(value)
                                                .append(")")

                                                .append(" or (")
                                                .append("pv").append(counter.get())
                                                .append(".currentValueType = 'normalTolerated' ")
                                                .append("and pv").append(counter.get())
                                                .append(".valFloat ")
                                                .append(operation)
                                                .append(" (")
                                                .append(value)
                                                .append(" - ")
                                                .append("pv").append(counter.get())
                                                .append(".tolerancePlus))")

                                                .append(" or (")
                                                .append("pv").append(counter.get())
                                                .append(".currentValueType = 'normalToleratedPrecent' ")
                                                .append("and pv").append(counter.get())
                                                .append(".valFloat ")
                                                .append(operation)
                                                .append(" (")
                                                .append(value)
                                                .append(" - (")
                                                .append("pv").append(counter.get())
                                                .append(".valFloat ")
                                                .append(" * ")
                                                .append("pv").append(counter.get())
                                                .append(".tolerancePlus) ")
                                                .append("/ 100 ))");
                                }
                        }

                        whereBlock
                                .append("and (")
                                .append("pv")
                                .append(counter.get())
                                .append(".parameter.id = '")
                                .append(filterConjunct.getParameter().getId())
                                .append("' and (")
                                .append(condition)
                                .append("))");

                    });
            //need pass query and all filters for correct query execute
            typonominalsDs.setQuery(
                    String.join(" ", queryTemplate, joinBlock, whereBlock, " order by e.name"),
                    typonominalsDs.getQueryFilter());
            typonominalsDs.setMaxResults(0);
        });
//----------поиск по параметрам
    }

    @Override
    public void ready() {

        //Рекурсивный показ всех типов, входящих в Класса ЭКБ
        typeClassesDs.addItemChangeListener(typeClass -> {
            if (typeClass.getItem() != null) {
                List<UUID> childrenList = new ArrayList<>();
                childrenList.clear();
                recDs(childrenList, typeClass.getItem().getId());
                childrenList.add(typeClass.getItem().getId());
                StringBuilder queryTypeClass = new StringBuilder("select e from mobdekbkp$Type e where e.typeClass.id = '");
                StringBuilder queryType = new StringBuilder("select e from mobdekbkp$Typonominal e where e.type.typeClass.id = '");
                boolean isFirstAddition = true;
                for (int j = 0; j < childrenList.size(); j++) {
                    if (isFirstAddition) {
                        queryTypeClass.append(childrenList.get(j).toString()).append("'");
                        queryType.append(childrenList.get(j).toString()).append("'");
                        isFirstAddition = false;
                    } else {
                        queryTypeClass.append(" or e.typeClass.id = '").append(childrenList.get(j).toString()).append("'");
                        queryType.append(" or e.type.typeClass.id = '").append(childrenList.get(j).toString()).append("'");
                    }
                }
                typesDs.setQuery(queryTypeClass.toString() + " order by e.designation");
                typesDs.refresh();
                typonominalsDs.setQuery(queryType.toString() + "order by e.name");
                typonominalsDs.refresh();
                typonominalsDs.setMaxResults(0);
                cardFrame.clearFrame(getMessage("noDsLabel"));
            }
        });

        // Выбор Типа ЭКБ и демонстрация всех изделий ЭКБ
        typesDs.addItemChangeListener(type -> {
            // Основная фильтрация типономиналов
            if (type.getItem() != null) {
                String queryType = "select e from mobdekbkp$Typonominal e where e.type.id = '" + type.getItem().getId() + "'";
                typonominalsDs.setQuery(queryType);
                typonominalsDs.refresh();
                typonominalsDs.setMaxResults(0);
                tblTn.requestFocus();
            }

            // Желательное улучшение: выбор первого ТН по клику на тип
            if (typonominalsDs.getItems().size() != 0) {
                Typonominal tn = (Typonominal) typonominalsDs.getItems().toArray()[0];
                tblTn.setSelected(tn);
                cardFrame.clearFrame(getMessage("noDsLabel"));
                cardFrame.initFrame(tn);
            }
        });

        // Выбор типономинала из таблицы
        typonominalsDs.addItemChangeListener(tn -> {
            Typonominal typonominal = typonominalsDs.getItem();
            cardFrame.clearFrame(getMessage("noDsLabel"));
            if (typonominal == null) return;
            cardFrame.initFrame(typonominal);
        });

        setFilter();
    }

    /**
     * Устанавливает фильтр если он был передан в параметрах
     */
    private void setFilter() {
        if (openFilter != null) {
            typonominalsFilter.setFilterEntity(openFilter);
            typonominalsFilter.apply(false);
        }
    }

    private void recDs(List<UUID> list, UUID itemId) {
        if (typeClassesDs.getChildren(itemId) != null) {
            list.addAll(typeClassesDs.getChildren(itemId));
            for (UUID o : typeClassesDs.getChildren(itemId))
                recDs(list, o);
        }
    }

    public void onBtnAllTNClick() {
        for (int i = 0; i < parameterFilterRowFrames.size(); i++) {
            parameterFilterRowFrames.get(i).clearParameterFilter();
            parameterFilterRowFrames.remove(i);
        }

        typeClassesDs.clear();
        typeClassesDs.setQuery("select e from mobdekbkp$TypeClass e");
        typeClassesDs.refresh();
        typesDs.clear();
        typesDs.setQuery("select e from mobdekbkp$Type e order by e.designation");
        typesDs.refresh();
        typonominalsDs.clear();
        typonominalsDs.setQuery("select e from mobdekbkp$Typonominal e order by e.name");
        typonominalsDs.refresh();
        typonominalsDs.setMaxResults(0);
    }

    public void onEdit(Component source) {
        Map<String, Object> screenMap = new HashMap<>();
        Typonominal tn = typonominalsDs.getItem();
        screenMap.put(TnCardScreen.TYPONOMINAL, tn);
        openWindow("tn-card-screen", WindowManager.OpenType.DIALOG, screenMap);
    }

    @Override
    protected boolean preClose(String actionId) {
        for (int i = 0; i < parameterFilterRowFrames.size(); i++) {
            parameterFilterRowFrames.remove(i);
        }
        return super.preClose(actionId);
    }
}