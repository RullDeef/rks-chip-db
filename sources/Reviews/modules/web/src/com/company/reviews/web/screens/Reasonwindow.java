package com.company.reviews.web.screens;

import com.company.reviews.entity.Review;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.TextArea;

import javax.inject.Inject;
import java.util.Map;

public class Reasonwindow extends AbstractWindow {

    @Inject
    TextArea reasonArea;

    private Review review=null;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        review=(Review) params.get("review");
    }

    public void onOkBtnClick() {
        if(reasonArea.getValue()!=null){
            review.setModerationReason(reasonArea.getValue());
            close(COMMIT_ACTION_ID);
        }
    }

    public void onCancelBtnClick() {
        close(CLOSE_ACTION_ID);
    }
}