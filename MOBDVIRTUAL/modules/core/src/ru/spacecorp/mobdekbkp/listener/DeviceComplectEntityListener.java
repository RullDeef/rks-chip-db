package ru.spacecorp.mobdekbkp.listener;

import org.eclipse.persistence.internal.descriptors.changetracking.AttributeChangeListener;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import ru.spacecorp.mobdekbkp.entity.DeviceComplect;
import ru.spacecorp.mobdekbkp.entity.DeviceComplectListStatus;

@Component("mobdekbkp_DeviceComplectEntityListener")
public class DeviceComplectEntityListener implements BeforeInsertEntityListener<DeviceComplect>,
        BeforeUpdateEntityListener<DeviceComplect> {


    @Override
    public void onBeforeInsert(DeviceComplect item, EntityManager entityManager) {
        item.setStatus(DeviceComplectListStatus.inDev);
    }

    @Override
    public void onBeforeUpdate(DeviceComplect item, EntityManager entityManager) {
    }


}