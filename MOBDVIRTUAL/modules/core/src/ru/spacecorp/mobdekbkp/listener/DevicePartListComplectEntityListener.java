package ru.spacecorp.mobdekbkp.listener;

import org.eclipse.persistence.internal.descriptors.changetracking.AttributeChangeListener;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import ru.spacecorp.mobdekbkp.entity.DevicePartListComplect;
import ru.spacecorp.mobdekbkp.entity.DevicePartListComplectStatus;
import ru.spacecorp.mobdekbkp.service.EditStatusService;

@Component("mobdekbkp_DevicePartListComplectEntityListener")
public class DevicePartListComplectEntityListener implements BeforeInsertEntityListener<DevicePartListComplect>,
        BeforeUpdateEntityListener<DevicePartListComplect> {


    @Inject
    public EditStatusService statusService;

    @Override
    public void onBeforeInsert(DevicePartListComplect item, EntityManager entityManager) {
        item.setStatus(DevicePartListComplectStatus.inDev);
    }

    @Override
    public void onBeforeUpdate(DevicePartListComplect item, EntityManager entityManager) {
        AttributeChangeListener acl = (AttributeChangeListener) item._persistence_getPropertyChangeListener();
        if (acl.getObjectChangeSet().getChangesForAttributeNamed("status") != null) {
            //если меняется статус, то ВОЗМОЖНО изменяем статус в комплекта, но ТОЧНО изменяем прогресс
            //statusService.editStatusDevicePartListComplectEntry(devicePartListComplect); - не нужно
        }
    }


}