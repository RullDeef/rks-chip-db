package ru.spacecorp.mobdekbkp.service;


import ru.spacecorp.mobdekbkp.entity.*;

public interface EditStatusService {
    String NAME = "mobdekbkp_EditStatusService";

    void editStatusDevicePartListPlannedEntry(DevicePartListPlanned listPlanned);

    void editStatusDeviceListProjectAdditionEntry(DeviceListProjectAddition listProjectAddition);

    void editStatusDevicePartListComplectEntry(DevicePartListComplect listComplect);

    void editStatusProjectEntry(DeviceListProjectEntry projectEntry);
}