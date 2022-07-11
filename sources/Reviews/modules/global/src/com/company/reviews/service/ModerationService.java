package com.company.reviews.service;


import com.company.reviews.entity.Review;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.security.entity.User;

import java.util.UUID;

public interface ModerationService {
    String NAME = "reviews_ModerationService";

    boolean isPremoderationEnable(Review review);

    boolean isPostmoderationEnable(Review review);

    boolean isAllowedForUser(Entity entity, User user);

    Entity loadEntity(String entityName, UUID entityId);

    void resetModerationProperties();
}