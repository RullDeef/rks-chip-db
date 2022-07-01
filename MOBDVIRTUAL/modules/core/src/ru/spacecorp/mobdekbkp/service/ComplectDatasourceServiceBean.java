package ru.spacecorp.mobdekbkp.service;

import com.haulmont.cuba.core.global.*;
import org.springframework.stereotype.Service;
import ru.spacecorp.mobdekbkp.entity.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service(ComplectDatasourceService.NAME)
public class ComplectDatasourceServiceBean implements ComplectDatasourceService {

    @Inject
    private ViewRepository viewRepository;
    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;

    private Object object;

    @Override
    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public Object getObject() {
        return object;
    }

    public ArrayList<DeviceListProjectEntry> getDeviceProjectEntries() {
        LoadContext loadContext = LoadContext.create(DeviceListProjectEntry.class);
        LoadContext.Query query = LoadContext.createQuery("select e from mobdekbkp$DeviceListProjectEntry e where e.deviceListProject.device " +
                "in (select e.deviceComplect.device from mobdekbkp$DevicePartListComplect e where e.id=:id)");
        query.setParameter("id", this.getObject());
        loadContext.setQuery(query);
        loadContext.setView(viewRepository.getView(DeviceListProjectEntry.class, "deviceListProjectEntry-view_small"));
        List<DeviceListProjectEntry> entryTemp = dataManager.loadList(loadContext);
        ArrayList<DeviceListProjectEntry> entriesList = new ArrayList<>(entryTemp);

        LoadContext loadContextAdd = LoadContext.create(DeviceListProjectAdditionEntry.class);
        LoadContext.Query queryAdd = LoadContext.createQuery("select e from mobdekbkp$DeviceListProjectAdditionEntry e where e.deviceListProjectAddition" +
                ".deviceListProject in " +
                "(select e.deviceListProject from mobdekbkp$DeviceListProjectEntry e where e.deviceListProject.device " +
                "in (select e.deviceComplect.device from mobdekbkp$DevicePartListComplect e where e.id=:id))");
        queryAdd.setParameter("id", this.getObject());
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
                        DeviceListProjectEntry deviceListProjectEntry = metadata.create(DeviceListProjectEntry.class);
                        deviceListProjectEntry.setTyponominal(deviceListProjectAdditionEntry.getNewtyponominal());
                        deviceListProjectEntry.setStatus(DeviceListEntryStatus.approved);
                        entriesList.add(deviceListProjectEntry);
                    }
                    break;
                }
                case edit: {
                    if ((deviceListProjectAdditionEntry.getEdited() != null) && (deviceListProjectAdditionEntry.getNewtyponominal() != null)) {
                        entriesList.removeIf(deviceListProjectEntry -> deviceListProjectEntry.getTyponominal().getId()
                                .equals(deviceListProjectAdditionEntry.getEdited().getId()));
                        DeviceListProjectEntry deviceListProjectEntry = metadata.create(DeviceListProjectEntry.class);
                        deviceListProjectEntry.setTyponominal(deviceListProjectAdditionEntry.getNewtyponominal());
                        deviceListProjectEntry.setStatus(DeviceListEntryStatus.approved);
                        entriesList.add(deviceListProjectEntry);
                    }
                    break;
                }
                case remove: {
                    if (deviceListProjectAdditionEntry.getEdited() != null) {
                        entriesList.removeIf(deviceListProjectEntry -> deviceListProjectEntry.getTyponominal().getId().toString()
                                .contentEquals(deviceListProjectAdditionEntry.getEdited().getId().toString()));
                    }
                    break;
                }
            }
        }
        return entriesList;
    }

}