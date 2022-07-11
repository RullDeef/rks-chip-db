package com.company.reviews.listener;

import com.company.reviews.entity.ReviewStatus;
import com.company.reviews.service.ModerationService;
import com.haulmont.cuba.core.global.AppBeans;
import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.company.reviews.entity.Review;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;

@Component("reviews_ReviewListener")
public class ReviewListener implements BeforeUpdateEntityListener<Review>, BeforeInsertEntityListener<Review>{


    @Override
    public void onBeforeUpdate(Review entity, EntityManager entityManager) {
        setStatus(entity);
    }
    @Override
    public void onBeforeInsert(Review entity, EntityManager entityManager) {
        setStatus(entity);
    }

    private void setStatus(Review entity){
        ModerationService moderationService= AppBeans.get(ModerationService.class);
        ReviewStatus reviewStatus=entity.getStatus();

        if(reviewStatus==ReviewStatus.premoderation) {
            if (!moderationService.isPremoderationEnable(entity)) {
                entity.setStatus(ReviewStatus.accepted);
                entity.setModeratedReview(null);
                entity.setModerationReason(null);
                return;
            }
        }
        if((entity.getStatus()==ReviewStatus.moderated)||(entity.getStatus()==ReviewStatus.accepted)) {
            if (entity.getModeratedReview() != null) {
                entity.setStatus(ReviewStatus.moderated);
            } else {
                entity.setStatus(ReviewStatus.accepted);
            }
        }
    }
}