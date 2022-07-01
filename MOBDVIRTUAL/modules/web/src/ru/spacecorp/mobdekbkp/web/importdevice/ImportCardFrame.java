package ru.spacecorp.mobdekbkp.web.importdevice;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.AbstractFrame;
import com.haulmont.cuba.gui.components.TabSheet;
import com.haulmont.cuba.gui.components.TextField;
import ru.spacecorp.mobdekbkp.entity.ImportDevice;
import ru.spacecorp.mobdekbkp.web.advancedsetting.AdvancedSettingBrowse;
import ru.spacecorp.mobdekbkp.web.analogimport.AnalogImportBrowse;
import ru.spacecorp.mobdekbkp.web.applicabilitydevices.ApplicabilityDevicesBrowseImport;
import ru.spacecorp.mobdekbkp.web.basicinformation.BasicInformationBrowse;
import ru.spacecorp.mobdekbkp.web.corpus.CorpusBrowse;
import ru.spacecorp.mobdekbkp.web.factory.FactoryBrowse;
import ru.spacecorp.mobdekbkp.web.importdocsschemes.ImportDocsSchemesBrowse;
import ru.spacecorp.mobdekbkp.web.mainparameters.MainParametersBrowse;
import ru.spacecorp.mobdekbkp.web.outerdbrefuse.OuterDbRefuseBrowse;
import ru.spacecorp.mobdekbkp.web.outerpersistenceinfo.OuterPersistenceInfoImportBrowse;
import ru.spacecorp.mobdekbkp.web.parametersforpurchasing.ParametersForPurchasingBrowse;
import ru.spacecorp.mobdekbkp.web.saprdata.SaprDataBrowse;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.FramecommentImport;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.ImportFrame;

import javax.inject.Inject;
import java.util.Map;

public class ImportCardFrame extends AbstractFrame implements ImportFrame {

    public static final String NO_INIT_TN_LABEL = "noInitTnLabel";
    public static final String TN_PARAM_NAME = "typonominal";
    public static final String OPTION_PARAM = "loadFrames";
    public static final String ALL_TABS = "allTabs";
    public static final String MAIN_TAB = "mainTab";
    public static final String DEV_LIST = "devList";

    @Inject
    private ImportDocsSchemesBrowse fDocsSchemes;
    @Inject
    private ParametersForPurchasingBrowse fParamForPurchaseImport;
    @Inject
    private ApplicabilityDevicesBrowseImport fApplicabilityDevicesImport;
    @Inject
    private FactoryBrowse fFactory;
    @Inject
    private BasicInformationBrowse fBasicImport;
    @Inject
    private MainParametersBrowse fMainParam;
    @Inject
    private AnalogImportBrowse fAnalog;
    @Inject
    private AdvancedSettingBrowse fAdvancedParams;
    @Inject
    private OuterPersistenceInfoImportBrowse fOuterPersistenceInfo;
    @Inject
    private CorpusBrowse fCorpus;
    @Inject
    private SaprDataBrowse fSapr;
    @Inject
    private OuterDbRefuseBrowse fRefuseRefuses;
    @Inject
    private FramecommentImport fComment;
    private ImportDevice importDevice;
    private String noDsLabelCaption;
    private String option;
    @Inject
    private TabSheet tbImportInfo;
    @Inject
    private TextField tfDevice;
    @Inject
    private DataManager dataManager;
    private ImportFrame[] allFrames;
    private ImportFrame[] devListFrames;
    private String[] allTabs = {"tabDocsSchemes", "tabPurchParams", "tabEnterprises", "tabBasic",
            "tabAnalogs", "tabParameters", "tabAddParams", "tabOuterPersistenceInfo", "tabDefects", "tabBody",
            "tabSapr", "tabApplicabilityProducts", "tabDiscuss"};
    private String[] devListTabs = {};

    public void reinit(Map<String, Object> params) {
        allFrames = new ImportFrame[]{fParamForPurchaseImport, fFactory, fComment};

        devListFrames = new ImportFrame[]{};

        // if no TN on init - we must have the caption
        if (params.get(NO_INIT_TN_LABEL) != null) {
            noDsLabelCaption = (String) params.get(NO_INIT_TN_LABEL);
        } else if (params.get(TN_PARAM_NAME) != null) {
// if we have no caption - we must have TN
            if (!(params.get(TN_PARAM_NAME) instanceof ImportDevice))
                throw new RuntimeException("Error loading TnCard, parameter is not a ImportDevice");
            this.importDevice = reloadTn((ImportDevice) params.get(TN_PARAM_NAME));
        }
// anyway we must have options
        option = (String) params.get(OPTION_PARAM);

        if ((noDsLabelCaption == null && importDevice == null) || option == null) {
            throw new RuntimeException("Error loading TnCard. One of mandatory parameters missing");
        }

// anyway start all frames
        initFrames(allFrames);
// anyway clear all frames
        changeFramesState(allFrames, false);

        if (importDevice != null) {
            showPickedFrames(true);
        }

    }

    @Override
    public void initFrame(ImportDevice importDevice) {
        this.importDevice = reloadTn(importDevice);
        showPickedFrames(true);
    }

    @Override
    public void clearFrame(String labelValue) {
        noDsLabelCaption = labelValue;
        showPickedFrames(false);
    }

    private void initFrames(ImportFrame[] frames) {
        for (int i = 0; i < frames.length; i++) {
            ((AbstractFrame) frames[i]).init(null);
        }
    }

    private void changeFramesState(ImportFrame[] frames, boolean show) {
        for (int i = 0; i < frames.length; i++) {
            if (show)
                frames[i].initFrame(importDevice);
            else if (noDsLabelCaption != null)
                frames[i].clearFrame(noDsLabelCaption);
            else
                frames[i].clearFrame(getMessage("noDsLabel"));
        }
    }

    private void showPickedFrames(boolean show) {
        switch (option) {
            case ALL_TABS:
                changeFramesState(allFrames, show);
                break;
            case MAIN_TAB:
                changeFramesState(allFrames, show);
                break;
            case DEV_LIST:
                showTabs(allTabs, false);
                if (show)
                    showTabs(devListTabs, true);
                changeFramesState(devListFrames, show);
                break;
            default:
                throw new RuntimeException("Error loading TnCard, show option is illegal");
        }
    }

    private void showTabs(String[] tabnames, boolean show) {
        for (int i = 0; i < tabnames.length; i++) {
            tbImportInfo.getTab(tabnames[i]).setVisible(show);
        }
    }

    private ImportDevice reloadTn(ImportDevice importDevice) {
        LoadContext<ImportDevice> ctx = LoadContext.create(ImportDevice.class);
        ctx.setView("importDevice-view");
        ctx.setQuery(LoadContext.createQuery("select d from mobdekbkp$ImportDevice d where d.id = :devid")
                .setParameter("devid", importDevice.getId()));
        importDevice = dataManager.load(ctx);
        if (importDevice != null) {
            tfDevice.setValue(importDevice.getName());
        }
        return importDevice;
    }
}