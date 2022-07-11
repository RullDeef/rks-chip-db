package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.AbstractFrame;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.gui.ReportGuiManager;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.web.PublicConstants;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Tnuse extends AbstractFrame implements TyponominalFrame {

    private Typonominal tn;

    @Inject
    private Button reportButton;
    @Inject
    private DataService dataService;
    @Inject
    private ComponentsFactory componentsFactory;

    protected ReportGuiManager reportGuiManager = AppBeans.get(ReportGuiManager.class);

    private Label noDsLabel;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        add(noDsLabel);
    }

    public void runReport() {
        Map<String, Object> reportParams = new HashMap<>();
        reportParams.put("element", tn);

        LoadContext<Report> ctx = new LoadContext<>(Report.class);
        ctx.setQueryString("select r from report$Report r where r.name like '%" + PublicConstants.REPORT_NAME_TYPONOMINAL_USE + "'");
        List<Report> reports = dataService.loadList(ctx);

        for (Report r : reports)
            reportGuiManager.printReport(r, reportParams);

        reportButton.setVisible(true);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in TnUse frame is null");

        this.tn = tn;

        reportButton.setVisible(true);
        noDsLabel.setVisible(false);
    }
    
    @Override
    public void clearFrame(String labelValue) {
        reportButton.setVisible(false);
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
    }
}