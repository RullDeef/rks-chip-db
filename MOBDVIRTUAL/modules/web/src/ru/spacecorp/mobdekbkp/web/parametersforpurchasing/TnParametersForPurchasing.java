package ru.spacecorp.mobdekbkp.web.parametersforpurchasing;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.AbstractFrame;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.components.TextField;
import ru.spacecorp.mobdekbkp.entity.ParametersForPurchasing;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.web.typonominal.frames.TyponominalFrame;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class TnParametersForPurchasing extends AbstractFrame implements TyponominalFrame {
    @Inject
    private TextArea tadeliveryTerm;
    @Inject
    private TextField tfdeliveryTime;
    @Inject
    private TextField tfproductPrice;
    @Inject
    private DataManager dataManager;

    ParametersForPurchasing parameters;
    private String labelValue;
    Typonominal tn;

    @Override
    public void initFrame(Typonominal tn) {
        if (tn != null) {
            this.tn = tn;
        }
        LoadContext<ParametersForPurchasing> ctx = LoadContext.create(ParametersForPurchasing.class);
        ctx.setView("parametersForPurchasing-view");
        ctx.setQuery(LoadContext.createQuery("select p from mobdekbkp$ParametersForPurchasing p join p.typonominal tn where tn.id = :tnId")
                .setParameter("tnId", tn.getId()));
        parameters = dataManager.load(ctx);
        if (parameters != null) {
            getDeliveryTerm();
            getDeliveryTime();
            getProductPrice();
        }
    }

    @Override
    public void clearFrame(String labelValue) {
        tfproductPrice.setValue(false);
        tadeliveryTerm.setValue(false);
        tfdeliveryTime.setValue(false);
        this.labelValue = labelValue;
    }

    private void getProductPrice() {
        if (parameters.getProductPrice() != null) {
            tfproductPrice.setValue(parameters.getProductPrice());
        }
    }

    private void getDeliveryTerm() {
        if (parameters.getDeliveryTerm() != null) {
            tadeliveryTerm.setValue(parameters.getDeliveryTerm());
        }
    }

    private void getDeliveryTime() {
        if (parameters.getDeliveryTime() != null) {
            tfdeliveryTime.setValue(parameters.getDeliveryTime());
        }
    }

    public void onBtproductPriceClick() {
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("productPrice", true);
        AbstractEditor ed = openEditor("mobdekbkp$ParametersForPurchasing.edit", parameters, WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(tn));
    }

    public void onBtdeliveryTermClick() {
        Map<String, Object> paramOpen = new HashMap<>();
        paramOpen.put("deliveryTerm", true);
        AbstractEditor ed = openEditor("mobdekbkp$ParametersForPurchasing.edit", parameters, WindowManager.OpenType.DIALOG, paramOpen);
        ed.addCloseWithCommitListener(() -> this.initFrame(tn));
    }
}