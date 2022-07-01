package ru.spacecorp.mobdekbkp.service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.security.entity.RoleType;
import org.activiti.bpmn.model.parse.Warning;
import org.eclipse.persistence.internal.descriptors.changetracking.AttributeChangeListener;
import ru.spacecorp.mobdekbkp.entity.*;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

@Service(AddDeviceListProjectService.NAME)
public class AddDeviceListProjectServiceBean implements AddDeviceListProjectService {

    @Inject
    private Metadata metadata;

    @Inject
    private Persistence persistence;


    private DeviceListProject listProject;
    private DeviceListProjectEntry listProjectEntry;

    //TODO переписать: если запись проектного перечня отправлена обратно в перечень планирования, то необходимо в записи проектного перечня изменить статус и оставить комментарий. Пока так, до переписания админки
    @Override
    public void addDeviceListProjectEntry(DevicePartListPlanned devicePartListPlanned) {
        Transaction transaction = persistence.getTransaction();

        Device device = devicePartListPlanned.getDevice(); //(1)начало проверяем есть ли в изделии РКТ проектный перечень и при необходимости создаём его
        DeviceListProject dListProject = device.getDeviceProjectList();
        if (dListProject == null) {
            listProject = metadata.create(DeviceListProject.class);
            listProject.setName("Проектный перечень " + device.getDesignation());
            listProject.setStatus(DeviceListProjectStatus.inDev);
        } else {
            listProject = dListProject;
        } //(1)конец проверяем есть ли в изделии РКТ проектный перечень и при необходимости создаём его

        if (null != listProject.getEntries()) {
            for (DevicePartListPlannedEntry listPlannedEntry : devicePartListPlanned.getEntries()) {
                //переводим планированный перечень СЧ в проектный
                if (listPlannedEntry.getStatus() == DeviceListEntryStatus.approved) {
                    listProjectEntry = loadProjectEntry(listProject, listPlannedEntry.getTyponominal());
                    if (listProjectEntry == null) {
                        listProject.getEntries().add(addProjectEntry(listPlannedEntry));
                    } //else если есть такая запись с любым статусом то выводим отчёт
                }
            }
        } else {
            List<DeviceListProjectEntry> listProjectEntryList = new LinkedList<>();
            for (DevicePartListPlannedEntry listPlannedEntry : devicePartListPlanned.getEntries()) {
                listProjectEntryList.add(addProjectEntry(listPlannedEntry));
            }
            listProject.setEntries(listProjectEntryList);
        }
        //(2)конец

        listProject.setDevice(devicePartListPlanned.getDevice());
        devicePartListPlanned.getDevice().setDeviceProjectList(listProject);
        persistence.getEntityManager().persist(listProject);
        transaction.commit();
    }

    private DeviceListProjectEntry loadProjectEntry(DeviceListProject deviceListProject, Typonominal typonominal) {
        listProjectEntry = (DeviceListProjectEntry) persistence.getEntityManager()
                .createQuery("select e from mobdekbkp$DeviceListProjectEntry e where (e.deviceListProject.id = :deviceListProjectId and e.typonominal.id = :typonominalId)")
                .setParameter("deviceListProjectId", deviceListProject.getId())
                .setParameter("typonominalId", typonominal.getId())
                .getFirstResult();
        return listProjectEntry;
    }

    private DeviceListProjectEntry addProjectEntry(DevicePartListPlannedEntry listPlannedEntry) {
        listProjectEntry = metadata.create(DeviceListProjectEntry.class);
        listProjectEntry.setTyponominal(listPlannedEntry.getTyponominal());
        listProjectEntry.setStatus(DeviceListEntryStatus.inDev);
        listProjectEntry.setDeviceListProject(listProject);
        persistence.getEntityManager().persist(listProjectEntry);
        return listProjectEntry;
    }
}