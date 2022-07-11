package com.company.reviews.web.review;

import com.company.reviews.entity.ReviewStatus;
import com.company.reviews.web.toolkit.ui.RatingFieldServerComponent;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.reviews.entity.Review;
import com.haulmont.cuba.gui.components.HBoxLayout;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.security.global.UserSession;
import com.vaadin.ui.ComponentContainer;

import javax.inject.Inject;
import java.util.Map;

public class ReviewEdit extends AbstractEditor<Review> {

    @Inject
    private HBoxLayout ratingBox;

    @Inject
    private Datasource<Review> reviewDs;

    @Inject
    private UserSession userSession;

    private RatingFieldServerComponent ratingFieldServerComponent;

    @Override
    protected void initNewItem(Review item) {
        super.initNewItem(item);

        item.setAuthor(userSession.getUser());
        item.setStatus(ReviewStatus.premoderation);
    }

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        reviewDs.addItemPropertyChangeListener(e -> {
            if(e.getProperty().contentEquals("review")||e.getProperty().contentEquals("grade")){
                reviewDs.getItem().setStatus(ReviewStatus.premoderation);
            }
        });
        initRatingField();
    }

    /**
     * Инициализация поля рейтинга
     */
    private void initRatingField() {
        ComponentContainer containerLayout = ratingBox.unwrap(ComponentContainer.class);
        ratingFieldServerComponent = new RatingFieldServerComponent();

        ratingFieldServerComponent.setId("ratingfield");
        ratingFieldServerComponent.setDescription(messages.getMessage(getClass(), "rateThis"));

        containerLayout.addComponent(ratingFieldServerComponent);


        ratingFieldServerComponent.addValueChangeListener(
                event -> reviewDs.getItem().setGrade(ratingFieldServerComponent.getValue())
        );
    }

    @Override
    protected void postValidate(ValidationErrors errors) {
        if(getItem().getGrade() == null) {
            errors.add(messages.getMessage(getClass(), "ratingFieldValidation"));
        }
    }

    @Override
    public void ready() {
        super.ready();

        Integer valueRating = reviewDs.getItem().getGrade();
        if (valueRating != null)
            ratingFieldServerComponent.setValue(valueRating);

    }
}
