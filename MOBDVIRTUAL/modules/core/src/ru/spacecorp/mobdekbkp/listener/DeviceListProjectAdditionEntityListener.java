package ru.spacecorp.mobdekbkp.listener;

import org.eclipse.persistence.internal.descriptors.changetracking.AttributeChangeListener;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import ru.spacecorp.mobdekbkp.entity.DeviceListProjectAddition;
import ru.spacecorp.mobdekbkp.service.EditStatusService;

@Component("mobdekbkp_DeviceListProjectAdditionEntityListener")
public class DeviceListProjectAdditionEntityListener implements BeforeInsertEntityListener<DeviceListProjectAddition>,
        BeforeUpdateEntityListener<DeviceListProjectAddition> {


    @Inject
    private EditStatusService statusService;

    @Override
    public void onBeforeInsert(DeviceListProjectAddition item, EntityManager entityManager) {
    }

    @Override
    public void onBeforeUpdate(DeviceListProjectAddition item, EntityManager entityManager) {
        AttributeChangeListener acl = (AttributeChangeListener) item._persistence_getPropertyChangeListener();
        if (acl.getObjectChangeSet().getChangesForAttributeNamed("status") != null) {
            statusService.editStatusDeviceListProjectAdditionEntry(item);
        }
    }

}