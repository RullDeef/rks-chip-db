package ru.spacecorp.mobdekbkp.service;


import ru.spacecorp.mobdekbkp.entity.*;

public interface AddEntriesService {
    String NAME = "mobdekbkp_AddEntriesService";
    DevicePartListPlannedEntry addPlannedEntry(DevicePartListPlanned devicePartListPlanned, Typonominal typonominal);
    TypeManufacturerEntry addManufacturerEntry(Type type, Company company);
    TypeCalicoholderEntry addCalicoholderEntry(Type type, Company company);
    TypeProviderEntry addProviderEntry(Type type, Company company);
    SubstrateEntry addSubstrateEntry(TyponominalInstallParameters installParameters, Substrate substrate);
    ApplicationNewDevEntry addParentEntry (ApplicationCommonEntry where, ApplicationNewTyponominalDev what);
}