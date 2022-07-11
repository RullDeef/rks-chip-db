package ru.spacecorp.mobdekbkp.web.typonominal.frames;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import ru.spacecorp.mobdekbkp.entity.ExtBoolean;
import ru.spacecorp.mobdekbkp.entity.Type;
import ru.spacecorp.mobdekbkp.entity.TypePlacementCategory;
import ru.spacecorp.mobdekbkp.entity.Typonominal;

import javax.inject.Inject;
import java.util.Map;

public class Tnextraparams extends AbstractFrame implements TyponominalFrame {

    @Inject
    private ComponentsFactory componentsFactory;

    @Inject private Label lblHasLongDeliverCycle;
    @Inject private VBoxLayout vbExtParams;
    @Inject private TextField tfClimaticImplementation;
    @Inject private HBoxLayout hbClimaticImplementation;
    @Inject private HBoxLayout hbPlacementCategory;
    @Inject private TextField tfPlacementCategory;
    @Inject private TextField tfMass;
    @Inject private HBoxLayout hbMass;
    @Inject private Label lblHasSubstitute;
    @Inject private Label lblSealNeeded;
    @Inject private Label lblIsAutoAssemble;
    @Inject private DataManager dataManager;

    private Typonominal tn;
    private Type type;
    private Label noDsLabel;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        if (noDsLabel == null)
            noDsLabel = componentsFactory.createComponent(Label.class);
        noDsLabel.setAlignment(Alignment.TOP_CENTER);
        vbExtParams.add(noDsLabel);
    }

    @Override
    public void initFrame(Typonominal tn) {
        if (tn == null)
            throw new RuntimeException("Unexpectedly typonominal in extra parameters frame is null");
        this.tn = tn;
        type = tn.getType();
        if (type == null) {
            LoadContext<Type> ctx = LoadContext.create(Type.class);
            ctx.setView("type-view");
            ctx.setQuery(LoadContext.createQuery("select tp from mobdekbkp$Typonominal tn join tn.type tp where tn.id = :tnId")
                    .setParameter("tnId", tn.getId()));
            type = dataManager.load(ctx);
        }
        noDsLabel.setVisible(false);
        showAll(true);

    }

    @Override
    public void clearFrame(String labelValue) {
        noDsLabel.setValue(labelValue);
        noDsLabel.setVisible(true);

        if (tn == null) return;
        if (type == null) return;
        showAll(false);
    }

    private void showAll(boolean show) {
        if (climaticImplement(show) != true &
        hasLongDelivery(show) != true &
        placement(show) != true &
        mass(show) != true &
        hasSub(show) != true &
        needSeal(show) != true &
        autoAssemble(show)!= true)
        noDsLabel.setVisible(true);
    }


    private boolean climaticImplement(boolean show) {
        if (type.getClimaticImplementation() == null) return false;
        tfClimaticImplementation.setValue(type.getClimaticImplementation());
        hbClimaticImplementation.setVisible(show);
        return true;
    }

    private boolean hasLongDelivery(boolean show) {
        if (type.getHasLongDeliverCycle() == ExtBoolean.yes)
            lblHasLongDeliverCycle.setValue(getMessage("deliveryYes"));
        else if (type.getHasLongDeliverCycle() == ExtBoolean.no)
            lblHasLongDeliverCycle.setValue(getMessage("deliveryNo"));
        else
           return false;

        lblHasLongDeliverCycle.setVisible(show);
        return true;
    }

    private boolean placement(boolean show) {
        if (type.getPlacementCategory() == TypePlacementCategory.notSet) return false;
        tfPlacementCategory.setValue(type.getPlacementCategory());
        hbPlacementCategory.setVisible(show);
        return true;
    }

    private boolean mass(boolean show) {
        if (tn.getMass() == null) return false;
        tfMass.setValue(tn.getMass());
        hbMass.setVisible(show);
        return true;
    }

    private boolean hasSub(boolean show) {
        if (tn.getHasSubstitute() != ExtBoolean.yes) return false;
        lblHasSubstitute.setValue(getMessage("hasSubstitute"));
        lblHasSubstitute.setVisible(show);
        return true;
    }

    private boolean needSeal(boolean show) {
        if (tn.getSealNeeded() == ExtBoolean.yes)
            lblSealNeeded.setValue(getMessage("sealYes"));
        else if (tn.getSealNeeded() == ExtBoolean.no)
            lblSealNeeded.setValue(getMessage("sealNo"));
        else
           return false;

        lblSealNeeded.setVisible(show);
        return true;
    }

    private boolean autoAssemble(boolean show) {
        if (tn.getIsAutoAssemble() == ExtBoolean.yes)
            lblIsAutoAssemble.setValue(getMessage("autoAssembleYes"));
        else if (tn.getIsAutoAssemble() == ExtBoolean.no)
            lblIsAutoAssemble.setValue(getMessage("autoAssembleNo"));
        else
            return false;

        lblIsAutoAssemble.setVisible(show);
        return true;
    }

}