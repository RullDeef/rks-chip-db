package ru.spacecorp.mobdekbkp.service;

import com.haulmont.cuba.core.Persistence;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import ru.spacecorp.mobdekbkp.entity.*;

@Service(EditStatusService.NAME)
public class EditStatusServiceBean implements EditStatusService {
    @Inject
    private Persistence persistence;

    @Override
    public void editStatusDevicePartListPlannedEntry(DevicePartListPlanned listPlanned) {
        if (listPlanned.getStatus() == DevicePartListPlannedStatus.inDev) {
            for (DevicePartListPlannedEntry listPlannedEntry : listPlanned.getEntries()) {
                listPlannedEntry.setStatus(DeviceListEntryStatus.inDev);
            }
        } else if (listPlanned.getStatus() == DevicePartListPlannedStatus.approved) {
            for (DevicePartListPlannedEntry listPlannedEntry : listPlanned.getEntries()) {
                if (listPlannedEntry.getStatus() == DeviceListEntryStatus.edit) {
                    listPlannedEntry.setStatus(DeviceListEntryStatus.approved);
                } else if (listPlannedEntry.getStatus() == DeviceListEntryStatus.inDev) {
                    listPlannedEntry.setStatus(DeviceListEntryStatus.approved);
                }
            }
        } else if (listPlanned.getStatus() == DevicePartListPlannedStatus.annulled) {
            for (DevicePartListPlannedEntry listPlannedEntry : listPlanned.getEntries()) {
                listPlannedEntry.setStatus(DeviceListEntryStatus.annulled);
            }
        }
    }

    @Override
    public void editStatusDeviceListProjectAdditionEntry(DeviceListProjectAddition listProjectAddition) {
        if (listProjectAddition.getStatus() == DeviceListProjectStatus.inDev) {
            for (DeviceListProjectAdditionEntry listProjectAdditionEntry : listProjectAddition.getEntries()) {
                listProjectAdditionEntry.setStatus(DeviceListEntryStatus.inDev);
            }
        } else if (listProjectAddition.getStatus() == DeviceListProjectStatus.partsApproved) {
            for (DeviceListProjectAdditionEntry listProjectAdditionEntry : listProjectAddition.getEntries()) {
                listProjectAdditionEntry.setStatus(DeviceListEntryStatus.inDev);
            }
        } else if (listProjectAddition.getStatus() == DeviceListProjectStatus.onApproval) {
            for (DeviceListProjectAdditionEntry listProjectAdditionEntry : listProjectAddition.getEntries()) {
                listProjectAdditionEntry.setStatus(DeviceListEntryStatus.inDev);
            }
        } else if (listProjectAddition.getStatus() == DeviceListProjectStatus.approved) {
            for (DeviceListProjectAdditionEntry listProjectAdditionEntry : listProjectAddition.getEntries()) {
                listProjectAdditionEntry.setStatus(DeviceListEntryStatus.approved);
            }
        } else if (listProjectAddition.getStatus() == DeviceListProjectStatus.annulled) {
            for (DeviceListProjectAdditionEntry listProjectAdditionEntry : listProjectAddition.getEntries()) {
                listProjectAdditionEntry.setStatus(DeviceListEntryStatus.annulled);
            }
        }
    }

    @Override
    public void editStatusDevicePartListComplectEntry(DevicePartListComplect listComplect) {
        if (listComplect.getStatus() == DevicePartListComplectStatus.inDev) {
            for (DevicePartListComplectEntry listComplectEntry : listComplect.getEntries()) {
                listComplectEntry.setStatus(DeviceListEntryStatus.inDev);
            }
        } else if (listComplect.getStatus() == DevicePartListComplectStatus.inProcess) {
            for (DevicePartListComplectEntry listComplectEntry : listComplect.getEntries()) {
                listComplectEntry.setStatus(DeviceListEntryStatus.inProcess);
            }
        } else if (listComplect.getStatus() == DevicePartListComplectStatus.done) {
            for (DevicePartListComplectEntry listComplectEntry : listComplect.getEntries()) {
                listComplectEntry.setStatus(DeviceListEntryStatus.approved);
            }
        } else if (listComplect.getStatus() == DevicePartListComplectStatus.canceled) {
            for (DevicePartListComplectEntry listComplectEntry : listComplect.getEntries()) {
                listComplectEntry.setStatus(DeviceListEntryStatus.annulled);
            }
        }
    }

    @Override
    public void editStatusProjectEntry(DeviceListProjectEntry projectEntry) {
        //TODO ПОПРАВЛЕНО --- переделать запрос, чтобы не ругался на возможную ошибку типов
        List plannedEntryList = persistence.getEntityManager()
                .createQuery("select e from mobdekbkp$DevicePartListPlannedEntry e where (e.typonominal.id = :typonominalId and e.devicePartListPlannedInverse.status = :status and e.devicePartListPlannedInverse.device.id = :deviceId )")
                .setParameter("typonominalId", projectEntry.getTyponominal().getId())
                .setParameter("status", DevicePartListPlannedStatus.approved)
                .setParameter("deviceId", projectEntry.getDeviceListProject().getDevice().getId())
                .getResultList();
        for (Object objPlannedEntry : plannedEntryList) {
            DevicePartListPlannedEntry plannedEntry = (DevicePartListPlannedEntry) objPlannedEntry;
            if (projectEntry.getStatus() == DeviceListEntryStatus.annulled) {
                plannedEntry.setStatus(DeviceListEntryStatus.annulled);
            } else if (projectEntry.getStatus() == DeviceListEntryStatus.edit) {
                plannedEntry.getDevicePartListPlannedInverse().setStatus(DevicePartListPlannedStatus.inDev);
                plannedEntry.setStatus(DeviceListEntryStatus.edit);
            } else if (projectEntry.getStatus() == DeviceListEntryStatus.approved) {
                plannedEntry.setStatus(DeviceListEntryStatus.approved);
            }
        }
    }
}