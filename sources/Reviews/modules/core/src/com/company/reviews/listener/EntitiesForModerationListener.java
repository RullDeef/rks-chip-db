package com.company.reviews.listener;

import com.company.reviews.service.ModerationService;
import com.haulmont.cuba.core.global.AppBeans;
import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.company.reviews.entity.EntitiesForModeration;
import com.haulmont.cuba.core.listener.BeforeDeleteEntityListener;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;

@Component("reviews_EntitiesForModerationListener")
public class EntitiesForModerationListener implements BeforeUpdateEntityListener<EntitiesForModeration>, BeforeDeleteEntityListener<EntitiesForModeration>, BeforeInsertEntityListener<EntitiesForModeration> {


    @Override
    public void onBeforeUpdate(EntitiesForModeration entity, EntityManager entityManager) {
        ModerationService moderationService= AppBeans.get(ModerationService.class);
        moderationService.resetModerationProperties();
    }


    @Override
    public void onBeforeDelete(EntitiesForModeration entity, EntityManager entityManager) {
        ModerationService moderationService= AppBeans.get(ModerationService.class);
        moderationService.resetModerationProperties();
    }


    @Override
    public void onBeforeInsert(EntitiesForModeration entity, EntityManager entityManager) {
        ModerationService moderationService= AppBeans.get(ModerationService.class);
        moderationService.resetModerationProperties();
    }


}