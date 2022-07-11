package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
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
    @Inject private Button reportButton;
    @Inject private Label lblNotPerspective;
    @Inject private Label lblEkbk;
    @Inject private Label lblMpt;
    @Inject private Label lblTnQualityLvl;
    @Inject private Label lblLifeCycleStage;
    @Inject private TextField tfTnCountry;
    @Inject private TextField tfTnQualityLvl;
    @Inject private ResizableTextArea taFuncPurpose;
    @Inject private DataService dataService;
    @Inject private HBoxLayout hbAlterGost2;
    @Inject private HBoxLayout hbAlterGost5;
    @Inject private HBoxLayout hbAlterMil;
    @Inject private HBoxLayout hbAlterRel;
    @Inject private TextField tfAltGost2;
    @Inject private TextField tfAltGost5;
    @Inject private TextField tfAltMil;
    @Inject private TextField tfAltRel;
    protected ReportGuiManager reportGuiManager = AppBeans.get(ReportGuiManager.class);

    @Inject private DataManager dataManager;

    private Typonominal tn;
    private Type type;

    @Override
    public void clearFrame(String labelValue) {
        lblNotPerspective.setValue(null);
        lblTnQualityLvl.setValue(null);
        tfTnCountry.setValue(null);
        tfTnQualityLvl.setValue(null);
        taFuncPurpose.setValue(labelValue);
        lifeCycleStage(false);
        reportButton.setVisible(false);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in TnBasicInfo frame is null");
        this.tn = tn;
        type = tn.getType();

        if (type == null) {
            LoadContext<Type> ctx = LoadContext.create(Type.class);
            ctx.setView("type-view");
            ctx.setQuery(LoadContext.createQuery("select tp from mobdekbkp$Typonominal tn join tn.type tp where tn.id = :tnId")
                            .setParameter("tnId", tn.getId()));
            type = dataManager.load(ctx);
        }

        getPerspective();
        getCountry();
        getPurpose();
        getQualityLevel();
        getOuterLists();
        lifeCycleStage(true);
        alterClasses();
        reportButton.setVisible(true);
    }

    private void alterClasses() {
        if (type.getAltClassRel() != null) {
            hbAlterRel.setVisible(true);
            tfAltRel.setValue(type.getAltClassRel());
        }
        if (type.getAltClassMil() != null) {
            hbAlterMil.setVisible(true);
            tfAltMil.setValue(type.getAltClassMil());
        }
        if (type.getAltClassG2710() != null) {
            hbAlterGost2.setVisible(true);
            tfAltGost2.setValue(type.getAltClassG2710());
        }
        if (type.getAltClassG56649() != null) {
            hbAlterGost5.setVisible(true);
            tfAltGost5.setValue(type.getAltClassG56649());
        }
    }

    private void lifeCycleStage(boolean show) {
        if (tn == null || tn.getLifeCycleStage() == null) return;
        lblLifeCycleStage.setValue(tn.getLifeCycleStage());
        lblLifeCycleStage.setVisible(show);
    }

    private void getPerspective() {
        if (tn.getNotPerspective() == ExtBoolean.yes)
            lblNotPerspective.setVisible(true);
        else
            lblNotPerspective.setVisible(false);
    }

    private void getCountry() {
        List<TypeManufacturerEntry> manufacturerEntryList = type.getManufacturers();
        if (manufacturerEntryList.size() == 0) throw new RuntimeException("Cannot get `Manufacturers`, data is corrupted, contact a system administrator!");
        Company company = manufacturerEntryList.get(0).getName();
        tfTnCountry.setValue(company.getCountry().getName());
    }

    private void getPurpose() {
        if (type.getFunctionalPurpose() != null)
            taFuncPurpose.setValue(type.getFunctionalPurpose());
    }

    private void getQualityLevel() {
            tfTnQualityLvl.setValue(tn.getQualityCaption());
            lblTnQualityLvl.setValue(getMessage("qlevelF"));
//            throw new RuntimeException(getMessage("noneOfQualityLevels"));
    }

    private void getOuterLists() {
        LoadContext<OuterListAllowing> ctx = LoadContext.create(OuterListAllowing.class);
        ctx.setView("outerListAllowing-view");
        ctx.setQuery(LoadContext.createQuery("select lst from mobdekbkp$OuterListAllowing lst join lst.entries e where e.typonominal.name = :tn")
                .setParameter("tn", tn.getName()));
        List<OuterListAllowing> olas = dataManager.loadList(ctx);
        Date currentDate = new Date(System.currentTimeMillis());
        lblMpt.setVisible(false);
        lblEkbk.setVisible(false);
        for (int i = 0; i < olas.size(); i++) {
            OuterListAllowing currentList = olas.get(i);
            if (currentList.getEndDate() == null || currentList.getEndDate().getTime() > currentDate.getTime()) {
                if (currentList.getType().getName().equals(PublicConstants.ALLOWING_LIST_TYPE_EKBK))
                    lblEkbk.setVisible(true);

                if (currentList.getType().getName().equals(PublicConstants.ALLOWING_LIST_TYPE_MOPMPT))
                    lblMpt.setVisible(true);
            }
        }
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

}