package ru.spacecorp.mobdekbkp.service;


import ru.spacecorp.mobdekbkp.entity.*;

import java.util.ArrayList;
import java.util.List;

public interface AddDeviceComplectService {
    String NAME = "mobdekbkp_AddDeviceComplectService";

    List<DeviceComplect> addDeviceComplect(Device device, Integer numderDeviceComplect);

    List<DevicePartListComplectEntry> addPartListComplectEntry(DevicePartListComplect listComplect);
}