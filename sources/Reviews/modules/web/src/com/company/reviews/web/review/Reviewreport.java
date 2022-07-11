package com.company.reviews.web.review;

import com.company.reviews.entity.Review;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import java.util.UUID;

public class Reviewreport extends AbstractWindow {

    @Inject
    CollectionDatasource<Review,UUID> reviewsDs;

    @Inject
    private TimeSource timeSource;

    public void onHiddenAndDeclinedBtnClick() {
        reviewsDs.setQuery("select e from reviews$Review e " +
                "where e.status='hidden' or e.status='declined'");
        reviewsDs.refresh();
    }

    public void onNotModeratedBtnClick() {
        Long curTime=timeSource.currentTimeMillis();
        reviewsDs.setQuery("select e from reviews$Review e " +
                "where e.status='premoderation' order by e.updateTs");
        reviewsDs.refresh();
    }
}