package com.company.reviews.web.toolkit.ui;

import com.company.reviews.web.toolkit.ui.client.ratingfield.RatingFieldServerRpc;
import com.company.reviews.web.toolkit.ui.client.ratingfield.RatingFieldState;
import com.vaadin.ui.AbstractField;

public class RatingFieldServerComponent extends AbstractField<Integer> {
    public RatingFieldServerComponent() {
        registerRpc((RatingFieldServerRpc) value -> setValue(value, true));
    }

    // field value type
    @Override
    public Class<? extends Integer> getType() {
        return Integer.class;
    }

    // define own state class
    @Override
    protected RatingFieldState getState() {
        return (RatingFieldState) super.getState();
    }

    @Override
    protected RatingFieldState getState(boolean markAsDirty) {
        return (RatingFieldState) super.getState(markAsDirty);
    }

    // we need to refresh the state when setValue is invoked from the application code
    @Override
    protected void setInternalValue(Integer newValue) {
        super.setInternalValue(newValue);
        if (newValue == null) {
            newValue = 0;
        }
        getState().value = newValue;
    }
}