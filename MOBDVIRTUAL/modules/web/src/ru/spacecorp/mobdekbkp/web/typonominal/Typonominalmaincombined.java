package ru.spacecorp.mobdekbkp.web.typonominal;

import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.HierarchicalDatasource;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.entity.FilterEntity;
import com.haulmont.cuba.web.gui.components.WebHBoxLayout;
import com.haulmont.cuba.web.gui.components.WebLabel;
import com.haulmont.cuba.web.gui.components.WebLookupField;
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.gui.ReportGuiManager;
import ru.spacecorp.mobdekbkp.entity.*;
import ru.spacecorp.mobdekbkp.service.UtilLoaderService;
import ru.spacecorp.mobdekbkp.web.PublicConstants;
import ru.spacecorp.mobdekbkp.web.SpacecorpWebFilter;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.*;


import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Typonominalmaincombined extends AbstractLookup {
    @Inject
    private FileMultiUploadField multiUploadField;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private DataSupplier dataSupplier;
    @Inject
    private HierarchicalDatasource<TypeClass, UUID> typeClassesDs;
    @Inject
    private CollectionDatasource<Type, UUID> typesDs;
    @Inject
    private CustTyponominalDs typonominalsDs;
    @Inject
    private UtilUploadFiles utilUploadFiles = new UtilUploadFiles();
    @Inject
    private DataGrid<Typonominal> tblTn;
    @Inject
    private DataGrid<Type> tblType;
    @Inject
    private TnCardFrame cardFrame;
    @Inject
    private PopupButton allBtns;
    @Inject
    private DataService dataService;
    protected ReportGuiManager reportGuiManager = AppBeans.get(ReportGuiManager.class);
    private TyponominalFrame[] frames;
    //поиск
    @Inject
    private SpacecorpWebFilter typonominalsFilter;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Metadata metadata;
    @Inject
    private UtilLoaderService loader;
    private List<ParameterFilterRowFrame> parameterFilterRowFrames = new ArrayList<>();
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

        if (params.get("filterConditions") != null) {
            typonominalsDs.init("select e from mobdekbkp$Typonominal e order by e.name",
                    new ArrayList<DeviceFilterConditions>((Collection) params.get("filterConditions")));
        }

        //добавляем кнопку "Добавить поиск по параметру" и action к ней
        //реализация поиска
        typonominalsFilter.setBeforeFilterAppliedHandler(() -> {
            for (int i = 0; i < parameterFilterRowFrames.size(); i++) {
                if (parameterFilterRowFrames.get(i).getComponent("parameterFilterRowDs") == null && !parameterFilterRowFrames.get(i).isVisible()) {
                    parameterFilterRowFrames.remove(i);
                }
                if (parameterFilterRowFrames.size() == 0) {
                    this.showAllTN();
                }
            }
            //конец - чистка данных в поисковом фрейме для нормальной работы таблиц после поиска
                /*if (e.getState() != CollectionDatasource.State.INVALID || CollectionUtils.isEmpty(parameterFilterRowFrames))
                    return;*/

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

                        if ((filterConjunct.getValFloat() == null) &&
                                (filterConjunct.getGamma() == null) &&
                                (filterConjunct.getToleranceMinus() == null) &&
                                (filterConjunct.getTolerancePlus() == null) &&
                                (filterConjunct.getValMax() == null) &&
                                (filterConjunct.getValMin() == null) &&
                                (filterConjunct.getValString() == null)) {
                            return;
                        }

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
            return true;
        });
        //----------поиск по параметрам
        typonominalsDs.refresh();

        multiUploadField.addQueueUploadCompleteListener(() -> {
            for (Map.Entry<UUID, String> entry : multiUploadField.getUploadsMap().entrySet()) {
                UUID fileId = entry.getKey();
                String fileName = entry.getValue();
                FileDescriptor fd = fileUploadingAPI.getFileDescriptor(fileId, fileName);
                // save file to FileStorage
                loader.getUploadFile(fileUploadingAPI.getFile(fileId));
                typonominalsDs.refresh();
                try {
                    fileUploadingAPI.putFileIntoStorage(fileId, fd);
                } catch (FileStorageException e) {
                    throw new RuntimeException("Error saving file to FileStorage", e);
                }

                // save file descriptor to database
                dataSupplier.commit(fd);
            }
            showNotification("Uploaded files: " + multiUploadField.getUploadsMap().values(), NotificationType.HUMANIZED);
            multiUploadField.clearUploads();
        });

        multiUploadField.addFileUploadErrorListener(event ->
                showNotification("File upload error", NotificationType.HUMANIZED));

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

        HBoxLayout hBoxLayout = componentsFactory.createComponent(HBoxLayout.class);
        hBoxLayout.setSpacing(true);

//добавляем кнопку "Показать все изделия ЭКБ" и action к ней
        Button allTN = componentsFactory.createComponent(Button.class);
        allTN.setDescription(getMessage("cancelFilters"));
        allTN.setIcon("icons/erase.png");

        for (Component component : filterLayout.getComponents()) {
            if (component.getClass() == WebLookupField.class) {
                component.setVisible(false);
                WebHBoxLayout whbox = (WebHBoxLayout) component.getParent();
                whbox.add(allTN);
            }
        }
        allTN.setAction(new AbstractAction("") {
            @Override
            public void actionPerform(Component component) {
                showAllTN();
            }
        });
//добавляем кнопку "Показать все изделия ЭКБ" и action к ней

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
        hBoxLayout.add(addParameterFilter);
//добавляем кнопку "Добавить поиск по параметру" и action к ней
        filterLayout.add(hBoxLayout);
        filterLayout.setSpacing(true);
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
                if (typonominalsDs != null) {
                    typonominalsDs.setQuery(queryType.toString() + "order by e.name");
                    typonominalsDs.refresh();
                    typonominalsDs.setMaxResults(0);
                }
                cardFrame.clearFrame(getMessage("noDsLabel"));
            }
        });

        // Выбор Типа ЭКБ и демонстрация всех изделий ЭКБ
        typesDs.addItemChangeListener(type -> {
            // Основная фильтрация типономиналов
            if (type.getItem() != null) {
                String queryType = "select e from mobdekbkp$Typonominal e where e.type.id = '" + type.getItem().getId() + "'";

                if (typonominalsDs != null) {
                    typonominalsDs.setQuery(queryType);
                    typonominalsDs.refresh();
                    typonominalsDs.setMaxResults(0);
                }

                tblTn.requestFocus();
            }

            // Желательное улучшение: выбор первого ТН по клику на тип
            if (typonominalsDs != null) {
                if (typonominalsDs.getItems().size() != 0) {
                    Typonominal tn = (Typonominal) typonominalsDs.getItems().toArray()[0];
                    tblTn.setSelected(tn);
                    cardFrame.clearFrame(getMessage("noDsLabel"));
                    cardFrame.initFrame(tn);
                }
            }
        });

        // Выбор типономинала из таблицы
        if (typonominalsDs != null) {
            typonominalsDs.addItemChangeListener(tn -> {
                Typonominal typonominal = typonominalsDs.getItem();
                cardFrame.clearFrame(getMessage("noDsLabel"));
                if (typonominal == null) return;
                cardFrame.initFrame(typonominal);
            });
        }

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

    public void showAllTN() {
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

    public void onExcel(Component source) {
        tblTn.getAction("excel").actionPerform(this);
    }

    public void onReport(Component source) {
        showNotification(getMessage("reportIsGenerating"), NotificationType.TRAY);
        Map<String, Object> reportParams = new HashMap<>();

        LoadContext<Typonominal> tctx = new LoadContext<>(Typonominal.class);
        tctx.setQueryString("select e from mobdekbkp$Typonominal e where e.id = '" + typonominalsDs.getItem().getId() + "'");
        tctx.setView("typonominal-report");
        Typonominal tn = dataService.load(tctx);
        reportParams.put("element", tn);

        LoadContext<Report> ctx = new LoadContext<>(Report.class);
        ctx.setQueryString("select r from report$Report r where r.name like '%" + PublicConstants.REPORT_NAME_TYPONOMINAL_USE + "'");
        List<Report> reports = dataService.loadList(ctx);

        for (Report r : reports)
            reportGuiManager.printReport(r, reportParams);
    }
}