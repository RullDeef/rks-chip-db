package ru.spacecorp.mobdekbkp.web.typonominal;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.AbstractWindow;
import ru.spacecorp.mobdekbkp.entity.Typonominal;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TnCardScreen extends AbstractWindow {

    public static final String TYPONOMINAL = "typonominal";
    public static final String TYPONOMINAL_ID = "typonominalId";

    @Inject
    private TnCardFrame cardFrame;

    DataManager dataManager= AppBeans.get(DataManager.class);

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        Typonominal tn;
        if (params.get(TYPONOMINAL_ID) != null) {
            LoadContext<Typonominal> loadContext = LoadContext.create(Typonominal.class);
            loadContext.setQuery(LoadContext.createQuery("select e from mobdekbkp$Typonominal e " +
                    "where e.id=:param")
                    .setParameter("param", UUID.fromString(params.get(TYPONOMINAL_ID).toString())));
            tn = dataManager.load(loadContext);
            if (tn == null) {
                throw new RuntimeException("Error loading TnCard, typonominal with id " + params.get(TYPONOMINAL_ID) + " not found");
            }
        } else {
            if (params.get(TYPONOMINAL) == null)
                throw new RuntimeException("Error loading TnCard, empty parameter");
            if (!(params.get(TYPONOMINAL) instanceof Typonominal))
                throw new RuntimeException("Error loading TnCard, parameter is not a typonominal");
            tn = (Typonominal) params.get(TYPONOMINAL);
        }


        Map<String, Object> frameparams = new HashMap<>();
        frameparams.put(TnCardFrame.TN_PARAM_NAME, tn);
        frameparams.put(TnCardFrame.OPTION_PARAM, TnCardFrame.ALL_TABS);
        cardFrame.reinit(frameparams);
    }
}