package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.documents.entity.Document;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.entity.TyponominalBody;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class Tnbody extends AbstractFrame implements TyponominalFrame {

    @Inject
    private CollectionDatasource<Document, UUID> modelDocsDs;
    @Inject private ScrollBoxLayout vbMain;
    @Inject private HBoxLayout hbDesignation;
    @Inject private TextField tfDesignation;
    @Inject private Label lblName;
    @Inject private ResizableTextArea taDescription;
    @Inject private HBoxLayout hbDimensions;
    @Inject private Label lblHeight;
    @Inject private Label lblLenght;
    @Inject private Label lblWidth;
    @Inject private TextField tfPinsCount;
    @Inject private TextField tfPinsMaterial;
    @Inject private HBoxLayout hbPinsCount;
    @Inject private HBoxLayout hbPinsMaterial;
    @Inject private HBoxLayout hbCoatingMaterial;
    @Inject private HBoxLayout hbBodyMaterial;
    @Inject private TextField tfBodyMaterial;
    @Inject private TextField tfCoatingMaterial;
    @Inject private HBoxLayout hbDistanceLeads;
    @Inject private TextField tfDistanceLeads;
    @Inject private HBoxLayout hbBodyMass;
    @Inject private TextField tfBodyMass;
    @Inject private HBoxLayout hbDispersePower;
    @Inject private TextField tfDispersePower;
    @Inject private HBoxLayout hbMaxHeight;
    @Inject private TextField tfMaxHeight;
    @Inject private HBoxLayout hbOutputPower;
    @Inject private TextField tfOutputPower;
    @Inject private TextField tfThermalResistance;
    @Inject private HBoxLayout hbThermalResistance;
    @Inject private HBoxLayout hbMarking;
    @Inject private TextField tfMarking;
    @Inject private Label lblSealingDemand;
    @Inject private LinkButton lbtnDimensionsAndBody;
    @Inject private LinkButton lbtnFixatorMarkup;
    @Inject private LinkButton lbtnPhoto;
    @Inject private LinkButton lbtnPinFormingDesignation;
    @Inject private Table<Document> tblModels;
    @Inject private ComponentsFactory componentsFactory;

    private Typonominal tn;
    private TyponominalBody body;
    private Label noDsLabel;
    private Tnbody thisframe;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        thisframe = this;
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setVisible(true);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        vbMain.add(noDsLabel);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in TnBasicInfo frame is null");

        this.tn = tn;
        //if (tn.getHasBody() == ExtBoolean.no) return;
        //if (tn.getBody() == null) return;

        if (tn.getBody() != null) {
            body = tn.getBody();
            modelDocsDs.setQuery("select docs from mobdekbkp$TyponominalBody t join t.documentsModel docs where t.id = '" + body.getId() + "'");
            modelDocsDs.refresh();
        }

        switch (tn.getHasBody()) {
            case no:
                noDsLabel.setValue(getMessage("noBodyLabel"));
                break;
            case notSet:
                if (tn.getBody() == null) {
                    noDsLabel.setValue(getMessage("noInformationAvailsbilityLabel"));
                } else {
                    showAll(true);
                    noDsLabel.setVisible(false);
                }
                break;
            case yes:
                if (tn.getBody() == null) {
                    noDsLabel.setValue(getMessage("noInformationLabel"));
                } else {
                    showAll(true);
                    noDsLabel.setVisible(false);
                }
                break;
        }

        //noDsLabel.setVisible(false);
    }

    @Override
    public void clearFrame(String labelValue) {
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);
        if (tn == null) return;
        if (body == null) return;
        showAll(false);
    }

    private void showAll(boolean show) {
        if (body == null) return;
        header(show);
        pinsMaterials(show);
        physicalParams(show);
        developParams(show);
        buttonsTables(show);
    }

    private void header(boolean show) {
        if (show) {
            tfDesignation.setValue(body.getDesignation());
            lblName.setValue(body.getName());
            taDescription.setValue(body.getDescription());
            if (body.getLength() != null) lblLenght.setValue(String.format(getMessage("lblLen"), body.getLength()));
            if (body.getHeight() != null) lblHeight.setValue(String.format(getMessage("lblHei"), body.getHeight()));
            if (body.getWidth() != null) lblWidth.setValue(String.format(getMessage("lblWid"), body.getWidth()));
        }
        if (body.getDesignation() != null) hbDesignation.setVisible(show);
        if (body.getName() != null) lblName.setVisible(show);
        if (body.getDescription() != null) taDescription.setVisible(show);
        hbDimensions.setVisible(show);
    }

    private void pinsMaterials(boolean show) {
        if (show) {
            tfPinsCount.setValue(body.getPinsCount());
            tfPinsMaterial.setValue(body.getPinsMaterial());
            tfBodyMaterial.setValue(body.getBodyMaterial());
            tfCoatingMaterial.setValue(body.getCoatingMaterial());
            tfDistanceLeads.setValue(body.getDistanceLeads());
        }
        if (body.getPinsCount() != null) hbPinsCount.setVisible(show);
        if (body.getPinsMaterial() != null) hbPinsMaterial.setVisible(show);
        if (body.getBodyMaterial() != null) hbBodyMaterial.setVisible(show);
        if (body.getCoatingMaterial() != null) hbCoatingMaterial.setVisible(show);
        if (body.getDistanceLeads() != null) hbDistanceLeads.setVisible(show);
    }

    private void physicalParams(boolean show) {
        if (show) {
            tfBodyMass.setValue(body.getMass());
            tfMaxHeight.setValue(body.getMaxHeight());
            tfDispersePower.setValue(body.getDispersePower());
            tfOutputPower.setValue(body.getOutputPower());
            tfThermalResistance.setValue(body.getThermalResistance());
        }
        if (body.getMass() != null) hbBodyMass.setVisible(show);
        if (body.getMaxHeight() != null) hbMaxHeight.setVisible(show);
        if (body.getDispersePower() != null) hbDispersePower.setVisible(show);
        if (body.getOutputPower() != null) hbOutputPower.setVisible(show);
        if (body.getThermalResistance() != null) hbThermalResistance.setVisible(show);
    }

    private void developParams(boolean show) {
        if (show) {
            // TODO переместить отображение маркировки в ттипономинал (поле переместилось туда)
//            tfMarking.setValue(body.getMarking());
            if (body.getSealingDemand() == null)
                lblSealingDemand.setValue(getMessage("hasSealingDemand"));
            else
                lblSealingDemand.setValue(getMessage("noSealingDemand"));
        }
        lblSealingDemand.setVisible(show);
//        if (body.getMarking() != null) hbMarking.setVisible(show);
    }

    private void buttonsTables(boolean show) {
        if (body.getPinFormingDesignation() != null)
            lbtnPinFormingDesignation.setVisible(show);
        if (body.getDimensionsAndBody() != null)
            lbtnDimensionsAndBody.setVisible(show);
        if (body.getPhoto() != null)
            lbtnPhoto.setVisible(show);
        if (body.getFixatorMarkup() != null)
            lbtnFixatorMarkup.setVisible(show);

        if (body.getDocumentsModel() != null &&
                body.getDocumentsModel().size() > 0)
            tblModels.setVisible(show);
    }

    public void onPinFormingDesignationLinkBtnClick() {
        AbstractEditor ed = openEditor("documents$Document.edit", body.getPinFormingDesignation(), WindowManager.OpenType.NEW_TAB);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn));
    }

    public void onPhotolinkBtnClick() {
        AbstractEditor ed = openEditor("documents$Document.edit", body.getPhoto(), WindowManager.OpenType.NEW_TAB);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn));
    }

    public void onDimensionsAndBodyLinkBtnClick() {
        AbstractEditor ed = openEditor("documents$Document.edit", body.getDimensionsAndBody(), WindowManager.OpenType.NEW_TAB);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn));
    }

    public void onFixatorMarkupLinkBtnClick() {
        AbstractEditor ed = openEditor("documents$Document.edit", body.getFixatorMarkup(), WindowManager.OpenType.NEW_TAB);
        ed.addCloseWithCommitListener(() -> thisframe.initFrame(tn));
    }

}