package ru.spacecorp.mobdekbkp.web.typonominal;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.ItemTrackingAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.web.gui.components.WebHBoxLayout;
import com.haulmont.cuba.web.gui.components.WebLabel;
import com.haulmont.cuba.web.gui.components.WebLookupField;
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.entity.ReportInputParameter;
import com.haulmont.reports.gui.ReportGuiManager;
import com.haulmont.reports.gui.actions.EditorPrintFormAction;
import com.haulmont.reports.gui.actions.RunReportAction;
import com.haulmont.reports.gui.report.browse.ReportBrowser;
import com.haulmont.reports.gui.report.run.ReportRun;
import org.springframework.util.CollectionUtils;
import ru.spacecorp.mobdekbkp.entity.ParameterFilterRow;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.service.TyponominalService;
import ru.spacecorp.mobdekbkp.web.SpacecorpWebFilter;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Typonominalmaintest extends AbstractWindow {

    @Inject
    private CollectionDatasource<Typonominal, UUID> typonominalsDs;
    @Inject
    private TnCardFrame cardFrame;
    //поиск
    private Button btnAllTN;
    @Inject
    private TyponominalService typonominalService;
    @Inject
    private SpacecorpWebFilter typonominalsFilter;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Metadata metadata;
    private List<ParameterFilterRowFrame> parameterFilterRowFrames = new ArrayList<>();
    private GroupBoxLayout filterLayout;
    @Inject
    private Button testReport;
    @Inject
    private DataService dataService;
    protected ReportGuiManager reportGuiManager = AppBeans.get(ReportGuiManager.class);
    @Inject
    private Table<Typonominal> tblTn;

    //поиск
    @Override
    public void init(Map<String, Object> params) {
        searchSetup();
        super.init(params);
        Map<String, Object> frameparams = new HashMap<>();
        frameparams.put(TnCardFrame.NO_INIT_TN_LABEL, getMessage("noDsLabel"));
        frameparams.put(TnCardFrame.OPTION_PARAM, TnCardFrame.MAIN_TAB);
        cardFrame.reinit(frameparams);
        cardFrame.clearFrame(getMessage("noDsLabel"));
        typonominalsFilter.setMaxResults(0);
        typonominalsDs.setMaxResults(0);


        testReport.setAction(new ItemTrackingAction("runReport") {
            @Override
            public void actionPerform(Component component) {
                LoadContext<Report> loadContext = new LoadContext<>(Report.class);
                loadContext.setQueryString("select r from report$Report r where r.id='64799b1c-d05b-f5f3-1253-3e7063be8393'");
                Report report = dataService.load(loadContext);
                if (report != null) {
                    //  report = getDsContext().getDataSupplier().reload(report, "report.edit");
                    if (report.getInputParameters() != null && report.getInputParameters().size() > 0) {
                        Window paramsWindow = openWindow("report$inputParameters", WindowManager.OpenType.DIALOG,
                                ParamsMap.of("report", report));
                        // paramsWindow.addCloseListener(actionId ->
                        //         target.requestFocus()
                        // );
                    } else {
                        //  reportGuiManager.printReport(report, Collections.emptyMap(), ReportBrowser.this);
                    }
                }
            }
        });

    }

    public void addParameterFilter() {
        ParameterFilterRowFrame parameterFilterRowFrame =
                (ParameterFilterRowFrame) openFrame(null, "mobdekbkp$Parameter.filter.row.frame");
        parameterFilterRowFrame.setItem(metadata.create(ParameterFilterRow.class));
        filterLayout.add(parameterFilterRowFrame);
        parameterFilterRowFrames.add(parameterFilterRowFrame);
    }

    private void searchSetup() {
        //----------поиск по параметрам
//добавляем кнопку "Добавить поиск по параметру" и action к ней
        filterLayout = (GroupBoxLayout) typonominalsFilter.getDelegate().getLayout();
        btnAllTN = componentsFactory.createComponent(Button.class);
        int i = 0;
        for (Component component : filterLayout.getComponents()) {
            if (component.getClass() == WebLookupField.class) {
                component.setVisible(false);
                WebHBoxLayout whbox = (WebHBoxLayout) component.getParent();
                whbox.add(btnAllTN);
            }
            if (component.getClass() == WebLabel.class) {
                if (i == 1) {
                    component.setVisible(false);
                }
                i++;
            }
        }

        btnAllTN.setCaption(getMessage("ClearFilter"));
        btnAllTN.setDescription(getMessage("cancelSelection"));
        btnAllTN.setIcon("icons/item-remove.png");
        btnAllTN.setAction(new AbstractAction("") {
            @Override
            public void actionPerform(Component component) {
                onBtnAllTNClick();
            }
        });
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
                                        .append(filterConjunct.getValString())
                                        .append(" or ")
                                        .append("pv").append(counter.get())
                                        .append(".currentValueType = 'bool' ")
                                        .append("and pv").append(counter.get())
                                        .append(".valString ")
                                        .append(operation)
                                        .append(" ")
                                        .append("Да".equals(filterConjunct.getValString()));
                                break;
                            default:
                                String value = String.format("%.0f", filterConjunct.getValFloat());
                                condition.append("pv").append(counter.get())
                                        .append(".currentValueType in ('integer', 'normal', 'gammaProbabilityValue', 'normalToleratedPrecent', 'normalTolerated') ")
                                        .append("and pv").append(counter.get())
                                        .append(".valFloat ")
                                        .append(operation)
                                        .append(" ")
                                        .append(value);

                                switch (filterConjunct.getOperationTypeAsEnum()) {
                                    case LESSER:
                                    case LESSER_OR_EQUAL:
                                        condition.append(" or ")
                                                .append("pv").append(counter.get())
                                                .append(".currentValueType = 'interval' ")
                                                .append("and pv").append(counter.get())
                                                .append(".valMin ")
                                                .append(operation)
                                                .append(" ")
                                                .append(value);
                                        break;

                                    case GREATER:
                                    case GREATER_OR_EQUAL:
                                        condition.append(" or ")
                                                .append("pv").append(counter.get())
                                                .append(".currentValueType = 'interval' ")
                                                .append("and pv").append(counter.get())
                                                .append(".valMax ")
                                                .append(operation)
                                                .append(" ")
                                                .append(value);
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

        //Выбор типономинала из таблицы
        typonominalsDs.addItemChangeListener(tn -> {
            cardFrame.clearFrame(getMessage("noDsLabel"));
            if (tn.getItem() == null) return;
            cardFrame.initFrame(tn.getItem());
        });
    }


    public void onBtnAllTNClick() {
        for (ParameterFilterRowFrame pfr : parameterFilterRowFrames) {
            pfr.setItem(null);
            pfr.setVisible(false);
        }
        typonominalsFilter.setMaxResults(0);
        typonominalsFilter.loadFiltersAndApplyDefault();
        typonominalsFilter.apply(false);
        cardFrame.clearFrame(getMessage("noDsLabel"));
    }

    public void onEdit(Component source) {
        Map<String, Object> screenMap = new HashMap<>();
        screenMap.put(TnCardScreen.TYPONOMINAL, typonominalsDs.getItem());
        openWindow("tn-card-screen", WindowManager.OpenType.DIALOG, screenMap);
    }

    @Override
    protected boolean preClose(String actionId) {
        for (ParameterFilterRowFrame parameterFilterRowFrame : parameterFilterRowFrames) {
            parameterFilterRowFrame.setItem(null);
        }
        return super.preClose(actionId);
    }

    public void onBtnClassTypeClick() {
        Ftypeclass aw = (Ftypeclass) openWindow("fTypeClass", WindowManager.OpenType.DIALOG);
        aw.addCloseListener(actionId -> {
            if (aw.getType() != null) {
                parameterFilterRowFrames.clear();
                typonominalsFilter.setFilterEntity(typonominalService.getFilterType(aw.getType()));
                typonominalsFilter.setMaxResults(0);
                typonominalsFilter.apply(false);
            } else if (aw.getTypeClassRequset() != null) {
                parameterFilterRowFrames.clear();
                typonominalsFilter.setFilterEntity(typonominalService.getFilterTypeClass(aw.getTypeClass()));
                typonominalsFilter.setMaxResults(0);
                typonominalsFilter.apply(false);
            }
            typonominalsDs.refresh();
        });
    }


  /*  public void onTestReportClick() {

        Map<String, Object> reportParams = new HashMap<>();
        reportParams.put("yr", "2018");

        LoadContext<Report> loadContext = new LoadContext<>(Report.class);
        loadContext.setQueryString("select r from report$Report r where r.id='64799b1c-d05b-f5f3-1253-3e7063be8393'");
        Report report = dataService.load(loadContext);
        String templateCode = "DEFAULT";




       // reportGuiManager.printReport(report,reportParams,templateCode,null);
        //System.out.println(report);
    }*/


}