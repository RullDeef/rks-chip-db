package ru.spacecorp.mobdekbkp.service;


import ru.spacecorp.mobdekbkp.entity.DevicePartListPlanned;

public interface AddDeviceListProjectService {
    String NAME = "mobdekbkp_AddDeviceListProjectService";

    void addDeviceListProjectEntry(DevicePartListPlanned devicePartListPlanned);
}