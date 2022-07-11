package ru.spacecorp.mobdekbkp.listener;

import org.eclipse.persistence.internal.descriptors.changetracking.AttributeChangeListener;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;

import org.springframework.stereotype.Component;
import javax.inject.Inject;

import ru.spacecorp.mobdekbkp.entity.DeviceListEntryStatus;
import ru.spacecorp.mobdekbkp.entity.DeviceListProjectEntry;
import ru.spacecorp.mobdekbkp.service.AddDeviceListProjectService;
import ru.spacecorp.mobdekbkp.service.EditStatusService;

@Component("mobdekbkp_DeviceListProjectEntryEntityListener")
public class DeviceListProjectEntryEntityListener implements BeforeInsertEntityListener<DeviceListProjectEntry>,
                                                                BeforeUpdateEntityListener<DeviceListProjectEntry> {


    @Inject
    private EditStatusService statusService;

    @Inject
    private AddDeviceListProjectService deviceListProjectService;

    @Override
    public void onBeforeInsert(DeviceListProjectEntry item, EntityManager entityManager) {
        DeviceListProjectEntry projectEntry = (DeviceListProjectEntry) entityManager.createQuery("select e from mobdekbkp$DeviceListProjectEntry e where (e.deviceListProject.id = :deviceListProjectId and e.typonominal.id = :typonominalId)")
                .setParameter("deviceListProjectId", item.getDeviceListProject().getId())
                .setParameter("typonominalId", item.getTyponominal().getId())
                .getFirstResult();
        if(projectEntry == null) {
            item.setStatus(DeviceListEntryStatus.inDev);
        } //если такая запись есть, то выводим сообщение и не добавляем
    }

    @Override
    public void onBeforeUpdate(DeviceListProjectEntry item, EntityManager entityManager) {
        AttributeChangeListener acl = (AttributeChangeListener) item._persistence_getPropertyChangeListener();
        if (acl.getObjectChangeSet().getChangesForAttributeNamed("status") != null) {
            statusService.editStatusProjectEntry(item);
        }
    }
}