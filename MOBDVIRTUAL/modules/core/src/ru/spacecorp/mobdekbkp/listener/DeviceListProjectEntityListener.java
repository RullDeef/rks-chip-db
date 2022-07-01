package ru.spacecorp.mobdekbkp.listener;

import org.eclipse.persistence.internal.descriptors.changetracking.AttributeChangeListener;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import ru.spacecorp.mobdekbkp.entity.DeviceListProject;
import ru.spacecorp.mobdekbkp.entity.DeviceListProjectStatus;
import ru.spacecorp.mobdekbkp.entity.DevicePartListPlanned;
import ru.spacecorp.mobdekbkp.entity.DevicePartListPlannedStatus;
import ru.spacecorp.mobdekbkp.entity.DeviceListProjectEntry;

import java.util.List;

@Component("mobdekbkp_DeviceListProjectEntityListener")
public class DeviceListProjectEntityListener implements BeforeInsertEntityListener<DeviceListProject>,
        BeforeUpdateEntityListener<DeviceListProject> {

    @Override
    public void onBeforeInsert(DeviceListProject item, EntityManager entityManager) {
    }

    @Override
    public void onBeforeUpdate(DeviceListProject item, EntityManager entityManager) {
        AttributeChangeListener acl = (AttributeChangeListener) item._persistence_getPropertyChangeListener();
        if (item.getStatus() == DeviceListProjectStatus.annulled) {
            //удаляем все записи проектного перечня
            for (DeviceListProjectEntry projectEntry : item.getEntries()) {
                entityManager.remove(projectEntry);
            }
            //аннулируем все утверждённые планируемые перечни
            //TODO ПОПРАВЛЕНО --- переделать запрос, чтобы не ругался на возможную ошибку типов
            List listPlanned = entityManager
                    .createQuery("select e from mobdekbkp$DevicePartListPlanned e where e.device.id = :deviceId and e.status = :status")
                    .setParameter("deviceId", item.getDevice().getId())
                    .setParameter("status", DevicePartListPlannedStatus.approved)
                    .getResultList();
            if (listPlanned.size() > 0) {
                for (int i = 0; i < listPlanned.size(); i++) {
                    ((DevicePartListPlanned) listPlanned.get(i)).setStatus(DevicePartListPlannedStatus.annulled);
                }
            }
        }
    }

}