package ru.spacecorp.mobdekbkp.listener;

import org.eclipse.persistence.internal.descriptors.changetracking.AttributeChangeListener;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;

import org.springframework.stereotype.Component;
import javax.inject.Inject;

import ru.spacecorp.mobdekbkp.entity.*;
import ru.spacecorp.mobdekbkp.service.AddDeviceListProjectService;
import ru.spacecorp.mobdekbkp.service.EditStatusService;

@Component("mobdekbkp_DevicePartListPlannedEntityListener")
public class DevicePartListPlannedEntityListener implements BeforeInsertEntityListener<DevicePartListPlanned>,
                                                            BeforeUpdateEntityListener<DevicePartListPlanned> {


    @Inject
    private EditStatusService statusService;

    @Inject
    private AddDeviceListProjectService deviceListProjectService;

    @Override
    public void onBeforeInsert(DevicePartListPlanned item, EntityManager entityManager) {
        if(item.getEntries() != null) {
            for (DevicePartListPlannedEntry plannedEntry : item.getEntries()) {
                entityManager.persist(plannedEntry);
            }
        }
    }

    @Override
    public void onBeforeUpdate(DevicePartListPlanned item, EntityManager entityManager) {
        AttributeChangeListener acl = (AttributeChangeListener) item._persistence_getPropertyChangeListener();


        if (acl.getObjectChangeSet().getChangesForAttributeNamed("status") != null) {
            //проверяем что старый статус был утверждён - для внесения изменений при возврате из проектного перечня
            if (!acl.getObjectChangeSet().getChangesForAttributeNamed("status")
                        .getOldValue().equals(DevicePartListPlannedStatus.approved.toString())) {
                //изменяем статус записей перечня в зависимости от статуса перечня
                statusService.editStatusDevicePartListPlannedEntry(item);
                //добавляем записи в проектный перечень
                if (item.getStatus() == DevicePartListPlannedStatus.approved) {
                    editStatusDevicePartListPlanned(item);
                    deviceListProjectService.addDeviceListProjectEntry(item);
                    //убрать проверку после анулирования, утверждения и изменения записи в проектном перечне
                    //начало - проверка все ли плановые перечни изделия РКТ утверждены и выставление статуса проектному перечню
                    Long numbListPlanned = (Long) entityManager
                            .createQuery("select count(e) from mobdekbkp$DevicePartListPlanned e where e.device.id = :deviceId and e.status = :status")
                            .setParameter("deviceId", item.getDevice().getId()).setParameter("status", DevicePartListPlannedStatus.approved)
                            .getSingleResult();
                    if (numbListPlanned.intValue() == item.getDevice().getParts().size() - 1) {
                        item.getDevice().getDeviceProjectList().setStatus(DeviceListProjectStatus.partsApproved);
                    }
                    //конец
                }
            }
        }
    }

    //выставяем статус Аннулирован другим перечням данной СЧ
    private void editStatusDevicePartListPlanned(DevicePartListPlanned devicePartListPlanned) {
        for (DevicePartListPlanned listPlanned : devicePartListPlanned.getDevicePart().getPlannedLists()) {
            if (listPlanned.getStatus() != DevicePartListPlannedStatus.approved && listPlanned.getDevice() == devicePartListPlanned.getDevice()) {
                listPlanned.setStatus(DevicePartListPlannedStatus.annulled);
            }
        }
    }
}