package ru.spacecorp.mobdekbkp.web.screens;

import com.google.common.collect.ImmutableMap;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.charts.gui.amcharts.model.Graph;
import com.haulmont.charts.gui.amcharts.model.GraphType;
import com.haulmont.charts.gui.amcharts.model.Label;
import com.haulmont.charts.gui.components.charts.Chart;
import com.haulmont.charts.gui.components.charts.PieChart;
import com.haulmont.charts.gui.components.charts.SerialChart;
import com.haulmont.charts.gui.data.ListDataProvider;
import com.haulmont.charts.gui.data.MapDataItem;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.WindowParams;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.config.MenuConfig;
import com.haulmont.cuba.security.entity.UserRole;
import ru.spacecorp.documents.web.document.DocumentInitKey;
import ru.spacecorp.mobdekbkp.entity.Company;
import ru.spacecorp.mobdekbkp.entity.CountryType;
import ru.spacecorp.mobdekbkp.entity.TypeClass;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.service.DashBoardService;
import com.haulmont.cuba.security.global.UserSession;
import ru.spacecorp.mobdekbkp.web.PublicConstants;

import javax.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.*;

public class Dashboardscreen extends AbstractWindow {

    @Inject
    private DashBoardService dashBoardService;
    @Inject
    private PieChart deviceInCountryDonutChart;
    @Inject
    private PieChart companyPie;
    @Inject
    private SerialChart typeDeviceStackedChart;
    @Inject
    private SerialChart daysLineChart;
    @Inject
    private SerialChart daysChangeLineChart;
    @Inject
    private Button typonominalBtn;
    @Inject
    private Button deviceBtn;
    @Inject
    private Button devicePartListComplectBtn;
    @Inject
    private Button deviceComplectListBtn;
    @Inject
    private Button requestNewTyponominalDevBtn;
    @Inject
    private Button requestTyponominalDevBtn;
    @Inject
    private Button reportBtn;
    @Inject
    private Button normativDocumentBtn;
    @Inject
    private Button companyBtn;
    @Inject
    private UserSession userSession;
    @Inject
    private Table<Typonominal> lastEkbTable;
    @Inject
    private Messages messages;
    @Inject
    private MenuConfig menuConfig;

    @Override
    public void init(Map<String, Object> params) {
        setVisibleBtn();
        initCharts();
        setTableDeviceActions();
    }

    /**
     * ?????????????????????????? ????????????????
     */
    private void initCharts() {
        setCompanyPieChart();
        setDaysLineChart();
        setDaysChangeLineChart();
        setDeviceCountContry();
        setTypeDeviceChart();
    }

    /**
     * ?????????????????????????? ?????????????????? ???????????? ?? ?????????????????????? ???? ????????
     * ???????? ??????????????????????
     */
    private void setVisibleBtn() {
        if (userSession.getUser().getUserRoles() != null && userSession.getUser().getUserRoles().size() > 0) {
            List<Button> listBtn = new ArrayList<>();
            for (UserRole userRole : userSession.getUser().getUserRoles()) {
                switch (userRole.getRole().getName()) {
                    case PublicConstants.SYS_ROLE_BPM_RESPONSIBLE: //???????????????? ??????????????????????
                        listBtn.addAll(Arrays.asList(
                                typonominalBtn,
                                deviceBtn,
                                devicePartListComplectBtn,
                                requestNewTyponominalDevBtn,
                                reportBtn,
                                normativDocumentBtn
                        ));
                        break;
                    case PublicConstants.SYS_ROLE_PART_DEVELOPER: //?????????????????????? ????
                        listBtn.addAll(Arrays.asList(
                                typonominalBtn,
                                deviceBtn,
                                devicePartListComplectBtn,
                                requestNewTyponominalDevBtn,
                                reportBtn,
                                normativDocumentBtn
                        ));
                        break;
                    case PublicConstants.SYS_ROLE_GNIO:
                        listBtn.addAll(Arrays.asList(
                                typonominalBtn,
                                deviceBtn,
                                devicePartListComplectBtn,
                                requestTyponominalDevBtn,
                                reportBtn,
                                normativDocumentBtn
                        ));
                        break;
                    case PublicConstants.SYS_ROLE_NIIKP:
                        listBtn.addAll(Arrays.asList(
                                typonominalBtn,
                                deviceBtn,
                                devicePartListComplectBtn,
                                deviceComplectListBtn,
                                requestNewTyponominalDevBtn,
                                companyBtn
                        ));
                        break;
                    case PublicConstants.SYS_ROLE_MNIIRIP:
                        listBtn.addAll(Arrays.asList(
                                typonominalBtn,
                                deviceBtn,
                                devicePartListComplectBtn,
                                deviceComplectListBtn,
                                requestNewTyponominalDevBtn,
                                reportBtn
                        ));
                        break;
                    case PublicConstants.SYS_ROLE_GKRK:
                        listBtn.addAll(Arrays.asList(
                                typonominalBtn,
                                deviceBtn,
                                devicePartListComplectBtn,
                                requestNewTyponominalDevBtn,
                                reportBtn,
                                normativDocumentBtn
                        ));
                        break;
                    case PublicConstants.SYS_ROLE_DEMO:
                        listBtn.addAll(Arrays.asList(
                                typonominalBtn,
                                deviceBtn,
                                devicePartListComplectBtn,
                                requestTyponominalDevBtn,
                                reportBtn,
                                normativDocumentBtn
                        ));
                        break;
                }
            }
            if (!listBtn.isEmpty())
                setVisibleBtn(listBtn);
        }
    }

    /**
     * ?????????????????????????? ?????? ???????????? button ?????????????? ??????????????????
     *
     * @param listBtn ???????????? button ?????????????? ???????????????????? ????????????????????????
     */
    private void setVisibleBtn(List<Button> listBtn) {
        HashSet<Button> uniqueButtons = new HashSet<>(listBtn);
        for (Button btn : uniqueButtons) {
            btn.setVisible(true);
        }
    }

    /**
     * ?????????????????? ???????????????? ???? ???????????????? ?????????? ?????????? ???? ??????????????????
     * ?????????????? ??????????????
     */
    private void setTableDeviceActions() {
        lastEkbTable.setItemClickAction(new BaseAction("tableClickAction") {
            @Override
            public void actionPerform(Component component) {
                Typonominal typonominal = lastEkbTable.getSingleSelected();

                if (typonominal != null) {
                    Map<String, Object> params = ParamsMap.of("filter", dashBoardService.getFilterName(typonominal.getName()));
                    openWindow("mobdekbkp$Typonominal.browse", WindowManager.OpenType.NEW_TAB, params);
                }
            }
        });
    }

    /**
     * ?????????????????? Stacked Column ?????????????????? ???????????????????? ?????? ???? ????????(????????????)
     * key - ????????????
     * value - ???????????????????? ???? ???????? ???????????? ????????????????????????
     */
    private void setTypeDeviceChart() {
        Map<TypeClass, Map<CountryType, Integer>> map = dashBoardService.getTypeDeviceCount();
        if (map == null || map.isEmpty())
            return;

        ListDataProvider typeDeviceCountDataProvider = new ListDataProvider();

        for (Map.Entry<TypeClass, Map<CountryType, Integer>> mapElement : map.entrySet()) {
            String typeClass = mapElement.getKey().getName();
            Integer countRussian = mapElement.getValue().get(CountryType.russian);
            Integer countForeign = mapElement.getValue().get(CountryType.foreign);
            Integer countFriendly = mapElement.getValue().get(CountryType.friendly);

            typeDeviceCountDataProvider.addItem(
                    MapDataItem.of(
                            "key", mapElement.getKey(),
                            "name", typeClass,
                            messages.getMessage(CountryType.russian), countRussian,
                            messages.getMessage(CountryType.foreign), countForeign,
                            messages.getMessage(CountryType.friendly), countFriendly
                    )
            );
        }

        typeDeviceStackedChart.setDataProvider(typeDeviceCountDataProvider);
        typeDeviceStackedChart.addGraphs(
                new Graph()
                        .setFillAlphas(0.6)
                        .setLineAlpha(0.4)
                        .setType(GraphType.COLUMN)
                        .setValueField(messages.getMessage(CountryType.russian))
                        .setBalloonText(String.format
                                (
                                        "%s - [[%s]]",
                                        messages.getMessage(CountryType.russian),
                                        messages.getMessage(CountryType.russian)
                                )),
                new Graph()
                        .setFillAlphas(0.6)
                        .setLineAlpha(0.4)
                        .setType(GraphType.COLUMN)
                        .setValueField(messages.getMessage(CountryType.foreign))
                        .setBalloonText(String.format
                                (
                                        "%s - [[%s]]",
                                        messages.getMessage(CountryType.foreign),
                                        messages.getMessage(CountryType.foreign)
                                )),
                new Graph()
                        .setFillAlphas(0.6)
                        .setLineAlpha(0.4)
                        .setType(GraphType.COLUMN)
                        .setValueField(messages.getMessage(CountryType.friendly))
                        .setBalloonText(String.format
                                (
                                        "%s - [[%s]]",
                                        messages.getMessage(CountryType.friendly),
                                        messages.getMessage(CountryType.friendly)
                                ))
        );

        typeDeviceStackedChart.addGraphItemClickListener(event -> {
            Map<String, Object> params = ParamsMap.of("filter",
                    dashBoardService.getFilterType((TypeClass) event.getDataItem().getValue("key"))
            );
            openWindow("mobdekbkp$Typonominal.browse", WindowManager.OpenType.NEW_TAB, params);
        });
    }

    /**
     * ?????????????????? donut ??????????????????
     * key - ????????/????????????
     * value - ???????????????????? ???? ?????????????? ????????
     */
    private void setDeviceCountContry() {
        //TODO(terekhov): ????????????????????. 1) ?????????????????? ???? ????????????. 2) ???????????? switch
        class CountCountryType {
            private int countRussian = 0;
            private int countForeign = 0;
            private int countFriendly = 0;

            private String getCounts() {
                return String.valueOf(countRussian) + "/" +
                        String.valueOf(countForeign) + "/" +
                        String.valueOf(countFriendly);
            }
        }
        CountCountryType countCountryType = new CountCountryType();

        Map<CountryType, Integer> map = dashBoardService.getDeviceContryCount();
        if (map == null || map.isEmpty())
            return;

        ListDataProvider deviceCountCountryDataProvider = new ListDataProvider();

        for (Map.Entry<CountryType, Integer> mapElement : map.entrySet()) {
            String key = String.valueOf(mapElement.getKey());
            Integer value = mapElement.getValue();
            //TODO(terekhov): ???????? ???? ??????????
            switch (key) {
                case "russian":
                    countCountryType.countRussian = value;
                    break;
                case "foreign":
                    countCountryType.countForeign = value;
                    break;
                case "friendly":
                    countCountryType.countFriendly = value;
                    break;
            }

            deviceCountCountryDataProvider.addItem(
                    MapDataItem.of(
                            "key", CountryType.valueOf(key),
                            "name", messages.getMessage(CountryType.valueOf(key)),
                            "value", value
                    )
            );
        }

        deviceInCountryDonutChart.setDataProvider(deviceCountCountryDataProvider);
        deviceInCountryDonutChart.addLabels(
                new Label()
                        .setText(countCountryType.getCounts())
                        .setBold(false)
                        .setSize(20)
                        .setX(String.valueOf(10))
                        .setY(String.valueOf(30))
        );
        deviceInCountryDonutChart.addSliceClickListener(event -> {
                    Map<String, Object> params = ParamsMap.of("filter",
                            dashBoardService.getFilterTypeCountry((CountryType) event.getDataItem().getValue("key"))
                    );
                    openWindow("mobdekbkp$Typonominal.browse", WindowManager.OpenType.NEW_TAB, params);
                }
        );
    }

    /**
     * ?????????????????? ???????????????? ?????????????????? ?????????? ?????? ?? ????
     * key - ???????? ??????????
     * value - ???????????????????? ?????????????????? ?????? ???? ?????? ????????
     */
    private void setDaysLineChart() {
        int lastDay = 15;
        Map<Date, Integer> map = dashBoardService.getDaysLineData(lastDay);
        if (map == null || map.isEmpty())
            return;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");
        ListDataProvider daysDataProvider = new ListDataProvider();
        int countElements = 0;
        for (Map.Entry<Date, Integer> mapElement : map.entrySet()) {
            int count = mapElement.getValue();
            daysDataProvider.addItem(
                    MapDataItem.of(
                            "key", mapElement.getKey(),
                            "name", dateFormat.format(mapElement.getKey()),
                            "value", count
                    )
            );
            countElements += count;
        }
        daysLineChart.setDataProvider(daysDataProvider);
        daysLineChart.addLabels(
                new Label()
                        .setText(String.valueOf(countElements))
                        .setBold(false)
                        .setSize(20)
                        .setX(String.valueOf(10))
                        .setY(String.valueOf(30))
        );
        daysLineChart.addGraphItemClickListener(event -> {
                    Map<String, Object> params = ParamsMap.of("filter",
                            dashBoardService.getFilterCreate((Date) event.getDataItem().getValue("key"))
                    );
                    openWindow("mobdekbkp$Typonominal.browse", WindowManager.OpenType.NEW_TAB, params);
                }
        );
    }

    /**
     * ?????????????????? ???????????????? ?????????????????? ?????????????????? ?????? ?? ????
     * key - ???????? ??????????
     * value - ???????????????????? ???????????????????? ?????? ???? ?????? ????????
     */
    private void setDaysChangeLineChart() {
        int lastDay = 15;
        Map<Date, Integer> map = dashBoardService.getDaysChangeLineData(lastDay);
        if (map == null || map.isEmpty())
            return;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");
        ListDataProvider daysDataProvider = new ListDataProvider();
        int countElements = 0;
        for (Map.Entry<Date, Integer> mapElement : map.entrySet()) {
            int count = mapElement.getValue();
            daysDataProvider.addItem(
                    MapDataItem.of(
                            "key", mapElement.getKey(),
                            "name", dateFormat.format(mapElement.getKey()),
                            "value", count
                    )
            );
            countElements += count;
        }
        daysChangeLineChart.setDataProvider(daysDataProvider);
        daysChangeLineChart.addLabels(
                new Label()
                        .setText(String.valueOf(countElements))
                        .setBold(false)
                        .setSize(20)
                        .setX(String.valueOf(10))
                        .setY(String.valueOf(30))
        );
        daysChangeLineChart.addGraphItemClickListener(event -> {
                    Map<String, Object> params = ParamsMap.of("filter",
                            dashBoardService.getFilterChange((Date) event.getDataItem().getValue("key"))
                    );
                    openWindow("mobdekbkp$Typonominal.browse", WindowManager.OpenType.NEW_TAB, params);
                }
        );
    }


    /**
     * ?????????????????? ???????????????? ?????????????????? ??????????????.
     * key - ??????????????????????
     * value - ???????????????????? ?????????????????????????????? ?????????????? ??????
     */
    private void setCompanyPieChart() {
        Map<Company, Integer> map = dashBoardService.getCompanyPieData();
        if (map == null || map.isEmpty())
            return;
        ListDataProvider companyPieDataProvider = new ListDataProvider();

        for (Map.Entry<Company, Integer> mapElement : map.entrySet()) {
            companyPieDataProvider.addItem(
                    MapDataItem.of(
                            "key", mapElement.getKey(),
                            "name", mapElement.getKey().getName(),
                            "value", mapElement.getValue()
                    )
            );
        }

        companyPie.setDataProvider(companyPieDataProvider);
        companyPie.addSliceClickListener(event -> {
            Map<String, Object> params = ParamsMap.of("filter",
                    dashBoardService.getFilterCompany((Company) event.getDataItem().getValue("key"))
            );
            openWindow("mobdekbkp$Typonominal.browse", WindowManager.OpenType.NEW_TAB, params);
        });
    }

    public void onTyponominalBtnClick() {
        String windowId = "mobdekbkp$Typonominal.browse";
        openWindow(
                windowId,
                WindowManager.OpenType.NEW_TAB,
                ImmutableMap.of(
                        WindowParams.CAPTION.name(), menuConfig.getItemCaption(windowId)
                )
        );
    }

    public void onDeviceBtnClick() {
        String windowId = "mobdekbkp$Device.browse";
        openWindow(
                windowId,
                WindowManager.OpenType.NEW_TAB,
                ImmutableMap.of(
                        WindowParams.CAPTION.name(), menuConfig.getItemCaption(windowId)
                )
        );
    }

    public void onDevicePartListComplectBtnClick() {
        String windowId = "mobdekbkp$DevicePartListComplect.browse";
        openWindow(
                windowId,
                WindowManager.OpenType.NEW_TAB,
                ImmutableMap.of(
                        WindowParams.CAPTION.name(), menuConfig.getItemCaption(windowId)
                )
        );
    }

    public void onDeviceComplectListBtnClick() {
        String windowId = "mobdekbkp$DeviceComplectList.browse";
        openWindow(
                windowId,
                WindowManager.OpenType.NEW_TAB,
                ImmutableMap.of(
                        WindowParams.CAPTION.name(), menuConfig.getItemCaption(windowId)
                )
        );
    }

    public void onRequestNewTyponominalDevBtnClick() {
        String windowId = "mobdekbkp$ApplicationNewTyponominalDev.browse";
        openWindow(
                windowId,
                WindowManager.OpenType.NEW_TAB,
                ImmutableMap.of(
                        WindowParams.CAPTION.name(), menuConfig.getItemCaption(windowId)
                )
        );
    }

    public void onRequestTyponominalDevBtnClick() {
        String windowId = "mobdekbkp$ApplicationNewTyponominalDev.browseReady";
        openWindow(
                windowId,
                WindowManager.OpenType.NEW_TAB,
                ImmutableMap.of(
                        WindowParams.CAPTION.name(), menuConfig.getItemCaption(windowId)
                )
        );
    }

    public void onCompanyBtnClick() {
        String windowId = "mobdekbkp$Company.browse";
        openWindow(
                windowId,
                WindowManager.OpenType.NEW_TAB,
                ImmutableMap.of(
                        WindowParams.CAPTION.name(), menuConfig.getItemCaption(windowId)
                )
        );
    }

    public void onNormativDocumentBtnClick() {
        String windowId = "documents$Document.browse";
        openWindow(
                windowId,
                WindowManager.OpenType.NEW_TAB,
                ImmutableMap.of(
                        WindowParams.CAPTION.name(), menuConfig.getItemCaption(windowId),
                        DocumentInitKey.SET_CAPTION, "????????????????????-?????????????????????? ??????????????????",
                        DocumentInitKey.GLOBAL_TYPE_RESTRICTIONS, "ntd")
        );
    }

    public void onReportBtnClick() {
        String windowId = "report$Report.run";
        openWindow(
                windowId,
                WindowManager.OpenType.NEW_TAB,
                ImmutableMap.of(
                        WindowParams.CAPTION.name(), menuConfig.getItemCaption(windowId)
                )
        );
    }
}