package ru.spacecorp.mobdekbkp.web.devicepartlistcomplectentry.complectDatasource;

import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.data.impl.CustomCollectionDatasource;
import ru.spacecorp.mobdekbkp.entity.DeviceListProjectAdditionEntry;
import ru.spacecorp.mobdekbkp.entity.Typonominal;
import ru.spacecorp.mobdekbkp.service.ComplectDatasourceService;

import java.util.*;

/**
 * Created by Stepanov_ME on 06.07.2018.
 */
public class complectDatasource extends CustomCollectionDatasource<Typonominal, UUID> {

    private ViewRepository viewRepository = AppBeans.get(ViewRepository.class);

    private DataManager dataManager = AppBeans.get(DataManager.NAME);

    private ComplectDatasourceService complectDatasourceService = AppBeans.get(ComplectDatasourceService.class);

    @Override
    protected Collection<Typonominal> getEntities(Map<String, Object> params) {
        LoadContext<Typonominal> loadContext = LoadContext.create(Typonominal.class);
        LoadContext.Query query = LoadContext.createQuery("select e.typonominal from mobdekbkp$DeviceListProjectEntry e where e.deviceListProject.device " +
                "in (select e.deviceComplect.device from mobdekbkp$DevicePartListComplect e where e.id=:id)");
        query.setParameter("id", complectDatasourceService.getObject());
        loadContext.setQuery(query);
        List<Typonominal> typonominalTemp = dataManager.loadList(loadContext);
        ArrayList<Typonominal> typonominalList = new ArrayList<>(typonominalTemp);

        LoadContext<DeviceListProjectAdditionEntry> loadContextAdd = LoadContext.create(DeviceListProjectAdditionEntry.class);
        LoadContext.Query queryAdd = LoadContext.createQuery("select e from mobdekbkp$DeviceListProjectAdditionEntry e where e.deviceListProjectAddition" +
                ".deviceListProject in " +
                "(select e.deviceListProject from mobdekbkp$DeviceListProjectEntry e where e.deviceListProject.device " +
                "in (select e.deviceComplect.device from mobdekbkp$DevicePartListComplect e where e.id=:id))");
        queryAdd.setParameter("id", complectDatasourceService.getObject());
        loadContextAdd.setQuery(queryAdd);
        View view = viewRepository.getView(DeviceListProjectAdditionEntry.class, "deviceListProjectAdditionEntry-view");
        loadContextAdd.setView(view);
        List<DeviceListProjectAdditionEntry> temp = dataManager.loadList(loadContextAdd);
        ArrayList<DeviceListProjectAdditionEntry> deviceListProjectAdditionEntries1 = new ArrayList<>(temp);

        for (DeviceListProjectAdditionEntry deviceListProjectAdditionEntry : deviceListProjectAdditionEntries1) {
            if (deviceListProjectAdditionEntry.getAdditionType() == null) {
                continue;
            }
            switch (deviceListProjectAdditionEntry.getAdditionType()) {
                case add: {
                    if (deviceListProjectAdditionEntry.getNewtyponominal() != null) {
                        typonominalList.add(deviceListProjectAdditionEntry.getNewtyponominal());
                    }
                    break;
                }
                case edit: {
                    if ((deviceListProjectAdditionEntry.getEdited() != null) && (deviceListProjectAdditionEntry.getNewtyponominal() != null)) {
                        if (typonominalList.remove(deviceListProjectAdditionEntry.getEdited())) {
                            typonominalList.add(deviceListProjectAdditionEntry.getNewtyponominal());
                        }
                    }
                    break;
                }
                case remove: {
                    if (deviceListProjectAdditionEntry.getEdited() != null) {
                        typonominalList.remove(deviceListProjectAdditionEntry.getEdited());
                    }
                    break;
                }
            }
        }
        View viewTyponominal = viewRepository.getView(Typonominal.class, "typonominal-view");
        for (int i = 0; i < typonominalList.size(); ++i) {
            typonominalList.set(i, dataManager.reload(typonominalList.get(i), viewTyponominal));
        }
        return typonominalList;
    }
}