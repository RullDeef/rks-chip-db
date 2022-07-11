package ru.spacecorp.mobdekbkp.service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Service;
import ru.spacecorp.mobdekbkp.entity.*;

import javax.inject.Inject;
import java.util.LinkedList;

@Service(AddEntriesService.NAME)
public class AddEntriesServiceBean implements AddEntriesService {
    @Inject
    private Metadata metadata;

    @Inject
    private Persistence persistence;

    @Override
    public DevicePartListPlannedEntry addPlannedEntry(DevicePartListPlanned devicePartListPlanned, Typonominal typonominal) {
        Transaction transaction = persistence.getTransaction();
        DevicePartListPlannedEntry plannedEntry = metadata.create(DevicePartListPlannedEntry.class);
        plannedEntry.setTyponominal(typonominal);
        plannedEntry.setStatus(DeviceListEntryStatus.inDev);
        plannedEntry.setDevicePartListPlannedInverse(devicePartListPlanned);
        persistence.getEntityManager().persist(plannedEntry);
        transaction.commit();
        return plannedEntry;
    }

    @Override
    public TypeManufacturerEntry addManufacturerEntry(Type type, Company company) {
        Transaction transaction = persistence.getTransaction();
        TypeManufacturerEntry manufacturerEntry = metadata.create(TypeManufacturerEntry.class);
        manufacturerEntry.setName(company);
        manufacturerEntry.setTypeInverse(type);
        persistence.getEntityManager().persist(manufacturerEntry);
        transaction.commit();
        return manufacturerEntry;
    }

    @Override
    public TypeCalicoholderEntry addCalicoholderEntry(Type type, Company company) {
        Transaction transaction = persistence.getTransaction();
        TypeCalicoholderEntry calicoholderEntry = metadata.create(TypeCalicoholderEntry.class);
        calicoholderEntry.setName(company);
        calicoholderEntry.setTypeInverse(type);
        persistence.getEntityManager().persist(calicoholderEntry);
        transaction.commit();
        return calicoholderEntry;
    }

    @Override
    public TypeProviderEntry addProviderEntry(Type type, Company company) {
        Transaction transaction = persistence.getTransaction();
        TypeProviderEntry providerEntry = metadata.create(TypeProviderEntry.class);
        providerEntry.setName(company);
        providerEntry.setTypeInverse(type);
        persistence.getEntityManager().persist(providerEntry);
        transaction.commit();
        return providerEntry;
    }

    @Override
    public SubstrateEntry addSubstrateEntry(TyponominalInstallParameters installParameters, Substrate substrate) {
        Transaction transaction = persistence.getTransaction();
        SubstrateEntry substrateEntry = metadata.create(SubstrateEntry.class);
        substrateEntry.setSubstrate(substrate);
        substrateEntry.setTyponominalInstallParameters(installParameters);
        persistence.getEntityManager().persist(substrateEntry);
        transaction.commit();
        return substrateEntry;
        }

    @Override
    public ApplicationNewDevEntry addParentEntry(ApplicationCommonEntry where, ApplicationNewTyponominalDev what) {
        Transaction transaction = persistence.getTransaction();
        ApplicationNewDevEntry newEntry = metadata.create(ApplicationNewDevEntry.class);
        newEntry.setApplication(what);
        newEntry.setApplicationCommonEntry(where);
        persistence.getEntityManager().persist(newEntry);
        transaction.commit();
        return newEntry;
    }


}