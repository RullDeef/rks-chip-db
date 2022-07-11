package ru.spacecorp.mobdekbkp.service;


import com.haulmont.cuba.security.entity.User;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;

import java.util.List;
import java.util.UUID;

public interface OptimizationDBService {
    String NAME = "mobdekbkp_OptimizationDBService";

    void bodyTableOptimizate();

    List<ExtUser> getAndUpdateComponiFromUsers();

}