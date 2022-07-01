package ru.spacecorp.mobdekbkp.web.typonominal;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.AbstractFrame;
import com.haulmont.cuba.gui.components.Frame;
import com.haulmont.cuba.gui.components.TabSheet;
import com.haulmont.cuba.security.global.UserSession;
import ru.iovchinnikov.talks.web.comment.CommentBrowse;
import ru.spacecorp.mobdekbkp.entity.ReliabilityIndicators;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.web.advancedsetting.Tnextraparams;
import ru.spacecorp.mobdekbkp.web.applicabilitydevices.ApplicabilityDevicesBrowse;
import ru.spacecorp.mobdekbkp.web.customparameters.CustomParametersBrowse;
import ru.spacecorp.mobdekbkp.web.customparameters.CustomParametersEdit;
import ru.spacecorp.mobdekbkp.web.outercertificatetests.Foutercertificatetests;
import ru.spacecorp.mobdekbkp.web.outerdbfail.Fouterdbfail;
import ru.spacecorp.mobdekbkp.web.outerdbrefuse.Fouterdbrefuse;
import ru.spacecorp.mobdekbkp.web.outerentrancetests.Fouterentrancetests;
import ru.spacecorp.mobdekbkp.web.outerpersistenceinfo.OuterPersistenceInfoBrowse;
import ru.spacecorp.mobdekbkp.web.outerrejection.Fouterdbrejection;
import ru.spacecorp.mobdekbkp.web.parametersforpurchasing.TnParametersForPurchasing;
import ru.spacecorp.mobdekbkp.web.reliabilityIndicators.TnReliabilityindicators;
import ru.spacecorp.mobdekbkp.web.type.Typeframeenterprises;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.*;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.AnalogFrame.Analogframe;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Map;

public class TnCardFrame extends AbstractFrame implements TyponominalFrame {

    public static final String NO_INIT_TN_LABEL = "noInitTnLabel";
    public static final String TN_PARAM_NAME = "typonominal";
    public static final String OPTION_PARAM = "loadFrames";
    public static final String ALL_TABS = "allTabs";
    public static final String MAIN_TAB = "mainTab";
    public static final String DEV_LIST = "devList";

    @Inject
    private Metadata metadata;
    @Inject
    private Analogframe fAnalogframe;
    @Inject
    private TnBasicInfo fBasicInfo;
    @Inject
    private Tnbody fBody;
    @Inject
    private Tndocsschemes fDocsSchemes;
    @Inject
    private Foutercertificatetests foutercertificatetests;
    @Inject
    private Fouterentrancetests fEntranceControl;
    @Inject
    private Tnextraparams fExtraParams;
    @Inject
    private Tninstallparams fInstallParams;
    @Inject
    private CustomParametersBrowse fParametersMain;
    @Inject
    private OuterPersistenceInfoBrowse fPersistenceInfo;
    @Inject
    private TnParametersForPurchasing fPurchaseParameters;
    @Inject
    private Typeframeenterprises frameEnterprises;
    @Inject
    private Tnsapr frameSAPR;
    @Inject
    private FullHierarchyInfo fHierarchy;
    @Inject
    private Fouterdbrefuse fOuterDbRefuse;
    @Inject
    private Fouterdbrejection fReject;
    @Inject
    private TabSheet tbsInformation;
    @Inject
    private ApplicabilityDevicesBrowse fApplicabilityProducts;
    @Inject
    private TnReliabilityindicators fRelIndicators;
    @Inject
    private TnTechnicalParameters fTechParam;
    @Inject
    private Framecomment fComments;
    private Typonominal tn;
    private String noDsLabelCaption;
    private String option;
    private TyponominalFrame[] allFrames;
    private TyponominalFrame[] devListFrames;
    private String[] allTabs = {"tabParameters", "tabAddParams", "tabTnInstallParams", "tabOuterPersistenceInfo",
            "tabTnAnalogs", "tabBody", "tabEntranceControl", "tabDefects", "tabFail", "tabPurchParams", "tabDocsSchemes",
            "tabSapr", "tabEnterprises", "tabBasic", "tabDiscuss, fApplicabilityProducts, fRelIndicators, fParametersMain"};
    private String[] devListTabs = {"tabBasic", "tabParameters", "tabPurchParams"};
    @Inject
    private DataManager dataManager;
    @Inject
    private UserSession userSession;

    public void reinit(Map<String, Object> params) {
        allFrames = new TyponominalFrame[]{fHierarchy, fComments, fPurchaseParameters, frameEnterprises};
        devListFrames = new TyponominalFrame[]{/* fHierarchy, fBasicInfo, fParametersMain, fPurchaseParameters*/};

        // if no TN on init - we must have the caption
        if (params.get(NO_INIT_TN_LABEL) != null) {
            noDsLabelCaption = (String) params.get(NO_INIT_TN_LABEL);
        } else if (params.get(TN_PARAM_NAME) != null) {
// if we have no caption - we must have TN
            if (!(params.get(TN_PARAM_NAME) instanceof Typonominal))
                throw new RuntimeException("Error loading TnCard, parameter is not a typonominal");
            this.tn = reloadTn((Typonominal) params.get(TN_PARAM_NAME));
        }
// anyway we must have options
        option = (String) params.get(OPTION_PARAM);

        if ((noDsLabelCaption == null && tn == null) || option == null) {
            throw new RuntimeException("Error loading TnCard. One of mandatory parameters missing");
        }

// anyway start all frames
        initFrames(allFrames);
// anyway clear all frames
        changeFramesState(allFrames, false);

        if (tn != null) {
            showPickedFrames(true);
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

    private void initFrames(TyponominalFrame[] frames) {
        for (int i = 0; i < frames.length; i++) {
            ((AbstractFrame) frames[i]).init(null);
        }

    }

    private void showFrames(TyponominalFrame[] frames) {
        for (int i = 0; i < frames.length; i++) {
            frames[i].initFrame(tn);
        }
    }

    private void changeFramesState(TyponominalFrame[] frames, boolean show) {
        for (int i = 0; i < frames.length; i++) {
            if (show)
                frames[i].initFrame(tn);
            else if (noDsLabelCaption != null)
                frames[i].clearFrame(noDsLabelCaption);
            else
                frames[i].clearFrame(getMessage("noDsLabel"));
        }
    }

    private void showTabs(String[] tabnames, boolean show) {
        for (int i = 0; i < tabnames.length; i++) {
            tbsInformation.getTab(tabnames[i]).setVisible(show);
        }
    }

    @Override
    public void initFrame(Typonominal tn) {
        this.tn = reloadTn(tn);
        showPickedFrames(true);
    }

    @Override
    public void clearFrame(String labelValue) {
        noDsLabelCaption = labelValue;
        showPickedFrames(false);
    }

    private Typonominal reloadTn(Typonominal typonominal) {
        LoadContext<Typonominal> ctx = LoadContext.create(Typonominal.class);
        ctx.setView("typonominal-card-view");
        ctx.setQuery(LoadContext.createQuery("select tn from mobdekbkp$Typonominal tn where tn.id = :tnid")
                .setParameter("tnid", typonominal.getId()));
        typonominal = dataManager.load(ctx);
        return typonominal;
    }

}