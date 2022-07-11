package com.company.reviews.web.review;

import com.company.reviews.entity.Review;
import com.company.reviews.entity.ReviewStatus;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class Reviewmoderatorscreen extends AbstractWindow {
    @Inject
    Table<Review> reviewPostTable;

    @Inject
    Table<Review> reviewPreTable;

    @Inject
    ReviewDatasource reviewsPostDs;

    @Inject
    ReviewDatasource reviewsPreDs;

    @Inject
    Button declineBtn;

    @Inject
    Button hideBtn;

    @Inject
    Button acceptBtn;

    @Inject
    private DataManager dataManager;

    @Inject
    private UserSession userSession;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        initDs();

        EditAction editPostAction=(EditAction) reviewPostTable.getAction("edit");
        editPostAction.setWindowId("reviews$ReviewModerator.edit");

        EditAction editPreAction=(EditAction) reviewPreTable.getAction("edit");
        editPreAction.setWindowId("reviews$ReviewModerator.edit");

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("openType","view");
        editPreAction.setWindowParams(hashMap);
    }

    private void initDs(){
        reviewsPreDs.init(userSession.getUser(),true);
        reviewsPostDs.init(userSession.getUser(), true);

        reviewsPreDs.addItemChangeListener(e -> {
            if(e.getItem()==null){
                acceptBtn.setEnabled(false);
                hideBtn.setEnabled(false);
            }
            else {
                acceptBtn.setEnabled(true);
                hideBtn.setEnabled(true);
            }
        });
        reviewsPostDs.addItemChangeListener(e -> {
            if(e.getItem()==null){
                declineBtn.setEnabled(false);
            }
            else {
                if(e.getItem().getStatus()!=ReviewStatus.declined) {
                    declineBtn.setEnabled(true);
                }
                else {
                    declineBtn.setEnabled(false);
                }
            }
        });
    }

    //Заменяет текст отзыва на "Отзыв не прошел модерацию"
    public void onDeclineBtnClick() {
        Review review=reviewsPostDs.getItem();
        HashMap<String,Object> reviewHashMap=new HashMap<>();
        reviewHashMap.put("review",review);
        openWindow("reasonWindow", WindowManager.OpenType.DIALOG,reviewHashMap)
                .addCloseListener(actionId ->{
                    if(actionId.contentEquals("commit")){
                        review.setStatus(ReviewStatus.declined);
                        dataManager.commit(review);
                        reviewsPreDs.refresh();
                        reviewsPostDs.refresh();
                    }
                });
    }

    //скрывает отзыв
    public void onHideBtnClick() {
        Review review=reviewsPreDs.getItem();
        HashMap<String,Object> reviewHashMap=new HashMap<>();
        reviewHashMap.put("review",review);
        openWindow("reasonWindow", WindowManager.OpenType.DIALOG,reviewHashMap)
                .addCloseListener(actionId ->{
                    if(actionId.contentEquals("commit")){
                        review.setStatus(ReviewStatus.hidden);
                        dataManager.commit(review);
                        reviewsPreDs.refresh();
                        reviewsPostDs.refresh();
                    }
                });
    }

    //подтверждает отзыв
    public void onAcceptBtnClick() {
        Review review=reviewsPreDs.getItem();
        review.setStatus(ReviewStatus.accepted);
        review.setModeratedReview(null);
        review.setModerationReason(null);
        dataManager.commit(review);
        reviewsPreDs.refresh();
        reviewsPostDs.refresh();
    }
}