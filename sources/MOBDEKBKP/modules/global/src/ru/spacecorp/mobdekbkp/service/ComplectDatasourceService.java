package ru.spacecorp.mobdekbkp.service;


import java.util.ArrayList;

public interface ComplectDatasourceService {
    String NAME = "mobdekbkp_ComplectDatasourceService";

    void setObject(Object datasource);

    Object getObject();

    ArrayList getDeviceProjectEntries();
}