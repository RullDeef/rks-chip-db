package com.company.reviews.web.review;

import com.company.reviews.entity.Review;
import com.company.reviews.entity.ReviewStatus;
import com.company.reviews.service.ModerationService;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.data.impl.CustomCollectionDatasource;
import com.haulmont.cuba.security.entity.User;

import java.util.*;

/**
 * Created by Stepanov_ME on 29.08.2018.
 */
public class ReviewDatasource extends CustomCollectionDatasource<Review,UUID>{

    private boolean moderator=false;
    private User curUser =null;
    private Review personalReview=null;

    @Override
    protected Collection<Review> getEntities(Map<String, Object> params) {

        Messages messages = AppBeans.get(Messages.class);

        ModerationService moderationService = AppBeans.get(ModerationService.class);
        ArrayList<Review> reviews = new ArrayList<>(dataSupplier.loadList(getCompiledLoadContext()));

        for (Review item : reviews) {
            if (userSession.getUser().equals(item.getAuthor())) {
                this.personalReview = item;
            }
        }

        //Изменение выдачи, если вызов происходит не из экрана модерирования
        if(!moderator) {
            for (Review review : reviews) {
                ReviewStatus reviewStatus = review.getStatus();
                switch (reviewStatus) {
                    case accepted: {
                        break;
                    }
                    case declined: {
                        review.setGrade(null);
                        review.setReview(String.format("<abbr title=\"%s\">%s</abbr>",review.getModerationReason(),messages.getMessage(getClass(), "declined")));
                        break;
                    }
                    case moderated: {
                        review.setReview(String.format("%s <abbr title=\"%s %s\">%s</abbr>",review.getModeratedReview(),messages.getMessage(getClass(), "reason"),
                                review.getModerationReason(),messages.getMessage(getClass(), "moderated")));
                        break;
                    }
                    case premoderation: {
                        review.setGrade(null);
                        if(moderationService.isPremoderationEnable(review)) {
                            review.setReview(messages.getMessage(getClass(), "premoderation"));
                        }
                        break;
                    }
                    case hidden:{
                        review.setGrade(null);
                        review.setReview(String.format("<abbr title=\"%s\">%s</abbr>",review.getModerationReason(),messages.getMessage(getClass(), "hidden")));
                        break;
                    }
                }
            }
            reviews.removeIf(review -> review.getStatus() == ReviewStatus.hidden);
        }
        else {
            HashMap<UUID,Integer> deleteMap=new HashMap<>();
            for (Review review : reviews) {
                Entity entity=moderationService.loadEntity(review.getParentName(),review.getParent());
                if(!moderationService.isAllowedForUser(entity,curUser)){
                    deleteMap.put(review.getId(),1);
                }
                else {
                    if((review.getStatus()!=ReviewStatus.premoderation)&&(review.getStatus()!=ReviewStatus.hidden)){
                        if(!moderationService.isPostmoderationEnable(review)){
                            deleteMap.put(review.getId(),1);
                        }
                    }
                }
            }
            reviews.removeIf(review -> deleteMap.get(review.getId())!=null);
        }
        return reviews;

    }

    @Override
    public void removeItem(Review item) {
        item.setGrade(1);
        if(item.equals(personalReview)){
            personalReview=null;
        }
        super.removeItem(item);
    }

    public void init(User curUser, boolean moderator){
        this.curUser =curUser;
        this.moderator=moderator;
    }

    public Review getPersonalReview(){
        return personalReview;
    }

    public void setPersonalReview(Review personalReview){
        this.personalReview=personalReview;
    }

}
