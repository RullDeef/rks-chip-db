package com.company.reviews.service;


import java.util.Map;

public interface ReviewService {
    String NAME = "reviews_ReviewService";

    Double calculateRating(Object object);

    Map<Integer,Integer> getAverageRating(Object object);
}