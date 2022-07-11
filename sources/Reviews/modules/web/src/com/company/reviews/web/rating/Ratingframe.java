package com.company.reviews.web.rating;

import com.company.reviews.service.ReviewService;
import com.haulmont.charts.gui.components.charts.SerialChart;
import com.haulmont.charts.gui.data.DataItem;
import com.haulmont.charts.gui.data.ListDataProvider;
import com.haulmont.charts.gui.data.MapDataItem;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.AbstractFrame;
import com.haulmont.cuba.gui.components.Frame;
import com.haulmont.cuba.gui.components.Label;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Map;

public class Ratingframe extends AbstractFrame {

    @Inject
    private SerialChart serialChart;

    @Inject
    private Label ratingLabel;

    @Inject
    private Label totalLabel;

    @Inject
    private
    ReviewService reviewService;

    private Entity entity;

    private float height;

    public void setData(Entity entity) {
        this.entity = entity;
        refresh();
    }

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        this.height = ((Frame) this.getComponent()).getHeight();
    }

    public void refresh() {
        if (entity != null) {
            int total = 0;
            Map<Integer, Integer> map = reviewService.getAverageRating(entity);
            ListDataProvider dataProvider = new ListDataProvider();
            for (int i = 5; i >= 1; --i) {
                if (map.get(i) != null) {
                    dataProvider.addItem(ratingCount(i, map.get(i)));
                    total += map.get(i);
                } else {
                    dataProvider.addItem(ratingCount(i, 0));
                }
            }
            serialChart.setDataProvider(dataProvider);

            double rating = reviewService.calculateRating(entity);
            rating = BigDecimal.valueOf(rating).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();

            StringBuilder labelRatingValue = new StringBuilder();
            StringBuilder labelTotalValue = new StringBuilder();


            ratingLabel.setValue(
                    labelRatingValue
                            .append(rating)
            );
            totalLabel.setValue(
                    labelTotalValue
                            .append(messages.getMessage(Ratingframe.class, "total"))
                            .append(" ")
                            .append(total)
            );
        }
    }

    private DataItem ratingCount(int rating, int value) {
        MapDataItem item = new MapDataItem();
        item.add("rating", rating);
        item.add("value" + String.valueOf(rating), value);
        return item;
    }

}