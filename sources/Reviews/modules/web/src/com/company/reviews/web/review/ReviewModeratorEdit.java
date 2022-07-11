package com.company.reviews.web.review;

import com.company.reviews.entity.ReviewStatus;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.reviews.entity.Review;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.gui.data.Datasource;

import javax.inject.Inject;
import java.util.Map;

public class ReviewModeratorEdit extends AbstractEditor<Review> {

    @Inject
    FieldGroup fieldGroup;

    @Inject
    Datasource reviewDs;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        reviewDs.addItemPropertyChangeListener(e -> {
            if(e.getProperty().contentEquals("moderatedReview")){
                if(e.getValue()!=null){
                    if(!e.getValue().toString().contentEquals("")){
                        fieldGroup.getField("moderationReason").setEditable(true);
                        fieldGroup.getField("moderationReason").setRequired(true);
                    }
                    else {
                        fieldGroup.getField("moderationReason").setEditable(false);
                        getItem().setModerationReason(null);
                        fieldGroup.getField("moderationReason").setRequired(false);
                    }
                }
                else {
                    fieldGroup.getField("moderationReason").setEditable(false);
                    getItem().setModerationReason(null);
                    fieldGroup.getField("moderationReason").setRequired(false);
                }
            }
        });

        if(params.get("openType")!=null){
            if(((String)params.get("openType")).contentEquals("view")){
                fieldGroup.getField("moderatedReview").setEditable(false);
            }
        }
    }

    @Override
    protected void postInit() {
        super.postInit();
        if((getItem().getStatus()!=ReviewStatus.accepted)&&(getItem().getStatus()!=ReviewStatus.moderated)){
            fieldGroup.getField("moderatedReview").setEditable(false);
        }
    }

    @Override
    protected boolean preCommit() {
        return super.preCommit();
    }
}