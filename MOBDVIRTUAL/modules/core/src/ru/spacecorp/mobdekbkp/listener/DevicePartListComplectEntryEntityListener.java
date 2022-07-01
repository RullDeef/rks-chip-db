package ru.spacecorp.mobdekbkp.listener;

import org.eclipse.persistence.internal.descriptors.changetracking.AttributeChangeListener;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import ru.spacecorp.mobdekbkp.entity.DeviceListEntryStatus;
import ru.spacecorp.mobdekbkp.entity.DevicePartListComplectEntry;

@Component("mobdekbkp_DevicePartListComplectEntryEntityListener")
public class DevicePartListComplectEntryEntityListener implements BeforeInsertEntityListener<DevicePartListComplectEntry>,
        BeforeUpdateEntityListener<DevicePartListComplectEntry> {


    @Override
    public void onBeforeInsert(DevicePartListComplectEntry item, EntityManager entityManager) {
        item.setStatus(DeviceListEntryStatus.inDev);
    }

    @Override
    public void onBeforeUpdate(DevicePartListComplectEntry item, EntityManager entityManager) {
        //если меняется статус, то ВОЗМОЖНО изменяем статус в перечне СЧ для комплектования, но ТОЧНО изменяем прогресс
    }

}