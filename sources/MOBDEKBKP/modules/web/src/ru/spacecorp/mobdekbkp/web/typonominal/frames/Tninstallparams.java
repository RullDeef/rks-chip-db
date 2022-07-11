package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.ExtBoolean;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.entity.TyponominalInstallParameters;

import javax.inject.Inject;
import java.util.Map;

public class Tninstallparams extends AbstractFrame implements TyponominalFrame {

    @Inject private VBoxLayout vbTnInstParams;
    @Inject private TextField tfInstallMethod;
    @Inject private HBoxLayout hbInstMethod;
    @Inject private ResizableTextArea taDescription;
    @Inject private TextField tfTemperatureMode;
    @Inject private HBoxLayout hbTemperatureMode;
    @Inject private HBoxLayout hbInstallationOption;
    @Inject private TextField tfInstallationOption;
    @Inject private TextField tfInstallCountAllowed;
    @Inject private HBoxLayout hbInstallCountAllowed;
    @Inject private TextField tfGasketSize;
    @Inject private Label lblHasGasket;
    @Inject private HBoxLayout hbGasketSize;
    @Inject private HBoxLayout hbGlueType;
    @Inject private Label lblHasGlue;
    @Inject private TextField tfGlueType;
    @Inject private HBoxLayout hbSolderBrand;
    @Inject private TextField tfSolderBrand;
    @Inject private HBoxLayout hbFluxBrand;
    @Inject private TextField tfFluxBrand;
    @Inject private Label lblSolderTech;
    @Inject private Label lblGasEnvAvail;
    @Inject private Label lblAutoInstall;
    @Inject private LinkButton btnBodyInstalDocLink;
    @Inject private LinkButton btnPinFormDocLink;
    @Inject private HBoxLayout hbPinFormDesignation;
    @Inject private TextField tfPinFormDesignation;
    @Inject private ComponentsFactory componentsFactory;

    private Typonominal tn;
    private TyponominalInstallParameters parameters;
    private Label noDsLabel;
    private Tninstallparams thisframe;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        thisframe = this;
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        noDsLabel.setVisible(true);
        vbTnInstParams.add(noDsLabel);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in install parameters frame is null");
        this.tn = tn;
        parameters = tn.getInstallParameters();
        if (parameters == null) return;

        noDsLabel.setVisible(false);
        showAll(true);
    }

    @Override
    public void clearFrame(String labelValue) {
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);

        if (tn == null) return;
        if (parameters == null) return;
        showAll(false);
    }

    private void showAll(boolean show) {
        method(show);
        description(show);
        temperature(show);
        option(show);
        countAllowed(show);
        gasket(show);           // only gasket size is shown, no gasket material information
        glue(show);
        solderBrand(show);
        solderTech(show);
        fluxBrand(show);
        gasEnvAvail(show);
        autoInstall(show);
        bodyDoc(show);
        pinDoc(show);
        pinFormDesignation(show);
    }

    private void method(boolean show) {
        if(parameters.getInstallMethod() == null) return;
        tfInstallMethod.setValue(parameters.getInstallMethod());
        hbInstMethod.setVisible(show);
    }

    private void description(boolean show) {
        if (parameters.getDescription() == null) return;
        taDescription.setValue(parameters.getDescription());
        taDescription.setVisible(show);

    }

    private void pinFormDesignation(boolean show) {
        if (parameters.getPinFormingDesignation() == null) return;
        tfPinFormDesignation.setValue(parameters.getPinFormingDesignation());
        hbPinFormDesignation.setVisible(show);
    }

    private void temperature(boolean show) {
        if (parameters.getTemperatureMode() == null) return;
        tfTemperatureMode.setValue(parameters.getTemperatureMode());
        hbTemperatureMode.setVisible(show);
    }

    private void option(boolean show) {
        if (parameters.getInstallationOptionDesignation() == null) return;
        tfInstallationOption.setValue(parameters.getInstallationOptionDesignation());
        hbInstallationOption.setVisible(show);
    }

    private void countAllowed(boolean show) {
        if (parameters.getInstallationCountAllowed() == null) return;
        tfInstallCountAllowed.setValue(parameters.getInstallationCountAllowed());
        hbInstallCountAllowed.setVisible(show);

    }

    private void gasket(boolean show) {
        if (show) {
            if (parameters.getHasGasket() == ExtBoolean.no) {
                lblHasGasket.setValue(getMessage("noGasketNeeded"));
                lblHasGasket.setVisible(true);
            } else if (parameters.getHasGasket() == ExtBoolean.yes) {
                lblHasGasket.setVisible(false);
                tfGasketSize.setValue(parameters.getGasketSize());
                hbGasketSize.setVisible(true);
            }
        } else {
            lblHasGasket.setVisible(false);
            hbGasketSize.setVisible(false);
        }
    }

    private void glue(boolean show) {
        if (show) {
            if (parameters.getHasGlue() == ExtBoolean.no) {
                lblHasGlue.setVisible(true);
                lblHasGlue.setValue(getMessage("noGlueNeeded"));
            } else if (parameters.getHasGlue() == ExtBoolean.yes) {
                tfGlueType.setValue(parameters.getGlueType());
                hbGlueType.setVisible(true);
                lblHasGlue.setVisible(false);
            }
        } else {
            lblHasGlue.setVisible(false);
            hbGlueType.setVisible(false);
        }

    }

    private void solderBrand(boolean show) {
        if (parameters.getSolderBrand() == null) return;
        tfSolderBrand.setValue(parameters.getSolderBrand());
        hbSolderBrand.setVisible(show);

    }

    private void solderTech(boolean show) {
        if (show) {
            if (parameters.getNonPbSolderTech() == ExtBoolean.no)
                lblSolderTech.setValue(getMessage("nonPbTechDiscard"));
            else if (parameters.getNonPbSolderTech() == ExtBoolean.yes)
                lblSolderTech.setValue(getMessage("nonPbTechApply"));
            else
                lblSolderTech.setValue("");
        }
        lblSolderTech.setVisible(show);
    }

    private void fluxBrand(boolean show) {
        if (parameters.getFluxBrand() == null) return;
        tfFluxBrand.setValue(parameters.getFluxBrand());
        hbFluxBrand.setVisible(show);

    }

    private void gasEnvAvail(boolean show) {
        if (show) {
            if (parameters.getGasEnvironmentAvailable() == ExtBoolean.no) {
                lblGasEnvAvail.setValue(getMessage("gasNotAvail"));
            } else if (parameters.getGasEnvironmentAvailable() == ExtBoolean.yes) {
                lblGasEnvAvail.setValue(getMessage("gasAvail"));
            } else
                lblGasEnvAvail.setValue("");
        }
        lblGasEnvAvail.setVisible(show);

    }

    private void autoInstall(boolean show) {
        if (show) {
            if (parameters.getAutoInstallation() == ExtBoolean.no) {
                lblAutoInstall.setValue(getMessage("autoInstallNo"));
            } else if (parameters.getAutoInstallation() == ExtBoolean.yes) {
                lblAutoInstall.setValue(getMessage("autoInstallYes"));
            }
        }
        lblAutoInstall.setVisible(show);
    }

    private void bodyDoc(boolean show) {
        if (parameters.getBodyInstallationDocument() == null) return;
        btnBodyInstalDocLink.setVisible(show);
    }

    private void pinDoc(boolean show) {
        if (parameters.getPinFormingDocument() == null) return;
        btnPinFormDocLink.setVisible(show);
    }

    public void onBodyInstalDocLinkBtnClick() {
        AbstractEditor ed = openEditor("documents$Document.edit", parameters.getBodyInstallationDocument(), WindowManager.OpenType.NEW_TAB);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn) );
    }

    public void onPinFormDocLinkBtnClick() {
        AbstractEditor ed = openEditor("documents$Document.edit", parameters.getPinFormingDocument(), WindowManager.OpenType.NEW_TAB);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn) );
    }

}