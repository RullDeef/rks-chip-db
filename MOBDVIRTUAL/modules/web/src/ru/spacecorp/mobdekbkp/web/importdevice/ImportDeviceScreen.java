package ru.spacecorp.mobdekbkp.web.importdevice;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.AbstractFrame;
import ru.spacecorp.mobdekbkp.entity.ImportDevice;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ImportDeviceScreen extends AbstractFrame {

    public static final String IMPORTDEVICE = "importDevice";
    public static final String IMPORTDEVICE_ID = "importDeviceId";

    @Inject
    private ImportCardFrame cardFrame;
    DataManager dataManager = AppBeans.get(DataManager.class);


    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        ImportDevice importDevice;
        if (params.get(IMPORTDEVICE_ID) != null) {
            LoadContext<ImportDevice> loadContext = LoadContext.create(ImportDevice.class);
            loadContext.setQuery(LoadContext.createQuery("select e from mobdekbkp$ImportDevice e " +
                    "where e.id=:param")
                    .setParameter("param", UUID.fromString(params.get(IMPORTDEVICE_ID).toString())));
            importDevice = dataManager.load(loadContext);
            if (importDevice == null) {
                throw new RuntimeException("Error loading TnCard, importDevice with id " + params.get(IMPORTDEVICE_ID) + " not found");
            }
        } else {
            if (params.get(IMPORTDEVICE) == null)
                throw new RuntimeException("Error loading TnCard, empty parameter");
            if (!(params.get(IMPORTDEVICE) instanceof ImportDevice))
                throw new RuntimeException("Error loading TnCard, parameter is not a importDevice");
            importDevice = (ImportDevice) params.get(IMPORTDEVICE);
        }


        Map<String, Object> frameparams = new HashMap<>();
        frameparams.put(ImportCardFrame.TN_PARAM_NAME, importDevice);
        frameparams.put(ImportCardFrame.OPTION_PARAM, ImportCardFrame.ALL_TABS);
        cardFrame.reinit(frameparams);
    }
}