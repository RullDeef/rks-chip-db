package com.company.reviews.listener;

import com.company.reviews.service.ModerationService;
import com.haulmont.cuba.core.global.AppBeans;
import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.company.reviews.entity.ModerationProperty;
import com.haulmont.cuba.core.listener.BeforeDeleteEntityListener;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;

@Component("reviews_ModerationPropertyListener")
public class ModerationPropertyListener implements BeforeUpdateEntityListener<ModerationProperty>, BeforeDeleteEntityListener<ModerationProperty>, BeforeInsertEntityListener<ModerationProperty> {


    @Override
    public void onBeforeUpdate(ModerationProperty entity, EntityManager entityManager) {
        ModerationService moderationService= AppBeans.get(ModerationService.class);
        moderationService.resetModerationProperties();
    }


    @Override
    public void onBeforeDelete(ModerationProperty entity, EntityManager entityManager) {
        ModerationService moderationService= AppBeans.get(ModerationService.class);
        moderationService.resetModerationProperties();
    }

    @Override
    public void onBeforeInsert(ModerationProperty entity, EntityManager entityManager) {
        ModerationService moderationService= AppBeans.get(ModerationService.class);
        moderationService.resetModerationProperties();
    }


}