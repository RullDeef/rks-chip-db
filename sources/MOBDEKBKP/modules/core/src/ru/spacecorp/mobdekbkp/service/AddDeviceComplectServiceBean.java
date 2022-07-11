package ru.spacecorp.mobdekbkp.service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Service;
import ru.spacecorp.mobdekbkp.entity.*;


import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service(AddDeviceComplectService.NAME)
public class AddDeviceComplectServiceBean implements AddDeviceComplectService {
    @Inject
    private Persistence persistence;
    @Inject
    private Metadata metadata;
    @Inject
    private ComplectDatasourceService complectDatasourceService;

    @Override  //добавляем комплекты и перечни для комплектования СЧ
    public List<DeviceComplect> addDeviceComplect(Device device, Integer deviceComplectCount) {
        Transaction transaction = persistence.getTransaction();
        for (int i = 0; i < deviceComplectCount; i++) {
            DeviceComplect deviceComplect = metadata.create(DeviceComplect.class);
            deviceComplect.setComplectNumber("Комплект "+ (i + 1) + " " + device.getDesignation());
            deviceComplect.setDevice(device);
            deviceComplect.setPartListComplect(new LinkedList<>());
            for (DevicePartsEntry partsEntry : device.getParts()) {
                Integer numberList = 1;
                for (int j = 0; j < partsEntry.getAmount(); j++) {
                    DevicePartListComplect listComplect = metadata.create(DevicePartListComplect.class);
                    listComplect.setName("Перечень для комплектования " + numberList + " СЧ " +
                            partsEntry.getPart().getDesignation() + " комплекта " +
                            deviceComplect.getComplectNumber());
                    listComplect.setDeviceComplect(deviceComplect);
                    listComplect.setDevicePart(partsEntry.getPart());

                    deviceComplect.getPartListComplect().add(listComplect);
                    persistence.getEntityManager().persist(listComplect);
                    numberList++;
                }
            }
            device.getComplects().add(deviceComplect);
            persistence.getEntityManager().persist(deviceComplect);
        }
        transaction.commit();
        return device.getComplects();
    }

    @Override
    //копируем записи планового переченя в перечень для комплектования, если запись планового перечня утверждена в проектном
    public List<DevicePartListComplectEntry> addPartListComplectEntry(DevicePartListComplect listComplect) {
        Transaction transaction = persistence.getTransaction();
        if (listComplect.getEntries() == null) {
            listComplect.setEntries(new LinkedList<>());
        }
        //TODO ПОПРАВЛЕНО --- переделать запрос, чтобы не ругался на возможную ошибку типов
        List plannedEntryListTemp =persistence.getEntityManager()
                .createQuery("select e from mobdekbkp$DevicePartListPlannedEntry e where" +
                        " (e.devicePartListPlannedInverse.device.id = :deviceId and " +
                        "e.devicePartListPlannedInverse.devicePart.id = :devicePartId and e.status = :statusPlanned)")
                .setParameter("deviceId", listComplect.getDeviceComplect().getDevice().getId())
                .setParameter("devicePartId", listComplect.getDevicePart().getId())
                .setParameter("statusPlanned", DeviceListEntryStatus.approved)
                .addView(DevicePartListPlannedEntry.class, "devicePartListPlannedEntry-view_small")
                .getResultList();
        ArrayList<DevicePartListPlannedEntry> plannedEntryList=new ArrayList<>(plannedEntryListTemp);

        ArrayList<DeviceListProjectEntry> deviceListProjectEntries=complectDatasourceService.getDeviceProjectEntries();

        deviceListProjectEntries.removeIf(deviceListProjectEntry -> deviceListProjectEntry.getStatus()!=DeviceListEntryStatus.approved);

        ArrayList<Integer> toDeleteList=new ArrayList<>();
        for (int i = 0; i < plannedEntryList.size(); ++i) {
            boolean found = false;
            for (DeviceListProjectEntry deviceListProjectEntry : deviceListProjectEntries) {
                if (plannedEntryList.get(i).getTyponominal().getId().equals(deviceListProjectEntry.getTyponominal().getId())) {
                    found = true;
                    toDeleteList.add(i);
                    break;
                }
            }
            if (!found) {
                plannedEntryList.remove(i);
                --i;
            }
        }

        for (DevicePartListPlannedEntry objProjectEntryList : plannedEntryList) {
            DevicePartListPlannedEntry aProjectEntryList = objProjectEntryList;
            DevicePartListComplectEntry complectEntry = metadata.create(DevicePartListComplectEntry.class);
            complectEntry.setTyponominal(aProjectEntryList.getTyponominal());
            complectEntry.setStatus(DeviceListEntryStatus.inDev);
            complectEntry.setAmountTotal(0);
            complectEntry.setByHeadExecutor(ExtBoolean.notSet);
            complectEntry.setDevicePartListComplect(listComplect);
            listComplect.getEntries().add(complectEntry);
            persistence.getEntityManager().persist(complectEntry);
        }
        transaction.commit();
        return listComplect.getEntries();
    }
}