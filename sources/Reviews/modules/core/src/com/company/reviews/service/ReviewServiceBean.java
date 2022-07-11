package com.company.reviews.service;

import com.company.reviews.core.AverageRatingCount;
import com.company.reviews.core.ReviewChartDataCreator;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Map;

@Service(ReviewService.NAME)
public class ReviewServiceBean implements ReviewService {
    @Inject
    private AverageRatingCount averageRatingCount;

    @Inject
    private ReviewChartDataCreator reviewChartDataCreator;

    @Override
    @Transactional
    public Double calculateRating(Object object) {
        return averageRatingCount.countAverage(object);
    }

    @Override
    public Map<Integer, Integer> getAverageRating(Object object) {
        return reviewChartDataCreator.getAverageRating(object);
    }
}