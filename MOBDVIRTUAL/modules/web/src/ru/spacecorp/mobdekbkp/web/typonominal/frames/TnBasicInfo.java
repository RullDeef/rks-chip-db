package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.gui.ReportGuiManager;
import ru.spacecorp.mobdekbkp.entity.*;
import ru.spacecorp.mobdekbkp.web.PublicConstants;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TnBasicInfo extends AbstractFrame implements TyponominalFrame {
    @Inject
    private Button reportButton;
    @Inject
    private ResizableTextArea taFuncPurpose;
    @Inject
    private DataService dataService;
    @Inject
    private TextField tfQualityLevel;
    @Inject
    private TextField thItem0122;
    @Inject
    private TextField tfItemEKBK;
    @Inject
    private Metadata metadata;
    protected ReportGuiManager reportGuiManager = AppBeans.get(ReportGuiManager.class);

    @Inject
    private DataManager dataManager;

    private Typonominal tn;
    private BasicInformation basic;

    @Override
    public void clearFrame(String labelValue) {

        taFuncPurpose.setValue(labelValue);
        tfQualityLevel.setValue(false);
        thItem0122.setValue(false);
        tfItemEKBK.setValue(false);
        reportButton.setVisible(false);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in TnBasicInfo frame is null");
        this.tn = tn;

        LoadContext<BasicInformation> ctx = LoadContext.create(BasicInformation.class);
        ctx.setView("basic-information-view");
        ctx.setQuery(LoadContext.createQuery("select b from mobdekbkp$BasicInformation b " +
                "join b.typonominal tn where tn.id = :tnId")
                .setParameter("tnId", tn.getId()));
        basic = dataManager.load(ctx);

        if (basic != null) {
            getPurpose();
            getLevelQuality();
            getIn_the_list_0122();
            getIn_the_list_EKB_K();
        }
        reportButton.setVisible(true);
    }

    private void getPurpose() {
        if (basic.getFunctionality() != null)
            taFuncPurpose.setValue(basic.getFunctionality());
    }

    private void getLevelQuality() {
        if (basic.getLevelQuality() != null)
            tfQualityLevel.setValue(basic.getLevelQuality());
    }

    private void getIn_the_list_0122() {
        if (basic.getIn_the_list_0122() != null)
            thItem0122.setValue(basic.getIn_the_list_0122());
    }

    private void getIn_the_list_EKB_K() {
        if (basic.getIn_the_list_EKB_K() != null)
            tfItemEKBK.setValue(basic.getIn_the_list_EKB_K());
    }

    public void runReport() {
        Map<String, Object> reportParams = new HashMap<>();
        reportParams.put("element", tn);
        showNotification(getMessage("reportIsGenerating"), NotificationType.TRAY);

        LoadContext<Report> ctx = new LoadContext<>(Report.class);
        ctx.setQueryString("select r from report$Report r where r.name like '%" + PublicConstants.REPORT_NAME_TYPONOMINAL_USE + "'");
        List<Report> reports = dataService.loadList(ctx);

        for (Report r : reports)
            reportGuiManager.printReport(r, reportParams);

        reportButton.setVisible(true);
    }


    public void onBtFuncPurposeClick() {
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("noedit", true);
        AbstractEditor ed = openEditor("mobdekbkp$BasicInformation.edit", basic, WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(tn));
    }

}