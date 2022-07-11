package com.company.reviews.web.screens;

import com.company.reviews.entity.Review;
import com.company.reviews.web.review.ReviewDatasource;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class Reviewsframe extends AbstractFrame {
    @Named("reviewsTable.create")
    private CreateAction reviewsTableCreate;
    @Inject
    private ReviewDatasource reviewsDs;
    @Inject
    private Table<Review> reviewsTable;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Button addReviewBtn;
    @Inject
    private Button editReviewBtn;
    @Inject
    private Button removeReviewBtn;
    @Inject
    private UserSession userSession;
    @Inject
    private Label personalReview;

    private UUID parentId;

    private Entity parent;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        StandardEntity parentObject = (StandardEntity) params.get("ITEM");
        this.parentId = parentObject.getId();
        this.parent=parentObject;

        initDs();
        initTable();
        initReviewGroup();
    }

    /**
     * Инициализация данных в зависимости от объекта
     */
    private void initDs() {
        reviewsDs.setQuery(
                String.format("select e from reviews$Review e where e.parent = '%s' order by e.updateTs desc", this.parentId)
        );
        reviewsDs.init(userSession.getUser(),false);
    }

    /**
     * Настройка таблицы
     */
    private void initTable() {
        reviewsTable.addGeneratedColumn("reviewArea", entity -> {

            Label richTextArea = componentsFactory.createComponent(Label.class);
            richTextArea.setEditable(false);
            richTextArea.setId("reviewArea");
            richTextArea.setWidth("100%");
            richTextArea.setResponsive(true);
            richTextArea.setHtmlEnabled(true);

            richTextArea.setValue(getReviewText(entity));

            return richTextArea;
        });

        reviewsTableCreate.setInitialValuesSupplier(
                () ->ParamsMap.of("parent", parentId,"parentName",parent.getMetaClass().getName())
        );
    }


    /**
     * Настройка панели "Мои отзыв"
     */
    private void initReviewGroup() {
        reviewsDs.addCollectionChangeListener(e -> {
            if(e.getOperation()== CollectionDatasource.Operation.ADD){
                ArrayList<Review> reviewArrayList=new ArrayList<>(e.getItems());
                for(Review review:reviewArrayList){
                    if(review.getAuthor().equals(userSession.getUser())){
                        reviewsDs.setPersonalReview(review);
                    }
                }
            }
            Review review = getPersonalReview();

            if (review == null) {
                // Настройка кнопок
                addReviewBtn.setVisible(true);
                editReviewBtn.setVisible(false);
                removeReviewBtn.setVisible(false);

                // Настройка поля отзыва
                personalReview.setVisible(false);
            } else {
                // Настройка кнопок
                addReviewBtn.setVisible(false);
                editReviewBtn.setVisible(true);
                removeReviewBtn.setVisible(true);

                // Настройка отзыва
                personalReview.setValue(getPersonalReviewText(review));
                personalReview.setVisible(true);
            }
        });
    }

    /**
     * Форматирование текста для персонального отзыва
     *
     * @param personalReviewItem Отзыв
     * @return Форматированный текст
     */
    private String getPersonalReviewText(Review personalReviewItem) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String gradeLabel = messages.getMessage(getClass(), "ratingLabel");
        String dateLabel = messages.getMessage(getClass(), "dateReview");

        if(personalReviewItem.getGrade()==null){
            return "<hr>"
                    + "<em>" + dateLabel + ": " + dateFormat.format(personalReviewItem.getUpdateTs()) + "</em><br><br>"
                    + personalReviewItem.getReview();
        }
        else {
            return "<hr>"
                    + "<em>" + gradeLabel + ": " + personalReviewItem.getGrade() + "</em><br>"
                    + "<em>" + dateLabel + ": " + dateFormat.format(personalReviewItem.getUpdateTs()) + "</em><br><br>"
                    + personalReviewItem.getReview();
        }
    }

    /**
     * Форматирование текста отзыва
     *
     * @param reviewItem Отзыв
     * @return Форматированный текст
     */
    private String getReviewText(Review reviewItem) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String gradeLabel = messages.getMessage(getClass(), "ratingLabel");
        String dateLabel = messages.getMessage(getClass(), "dateReview");

        if(reviewItem.getGrade()==null){
            return "<br><b>" + reviewItem.getAuthor().getName() + "</b><br>"
                    + "<em>" + dateLabel + ": " + dateFormat.format(reviewItem.getUpdateTs()) + "</em><br><br>"
                    + reviewItem.getReview() + "<br><br>";
        }
        else {
            return "<br><b>" + reviewItem.getAuthor().getName() + "</b><br>"
                    + "<em>" + gradeLabel + ": " + reviewItem.getGrade() + "</em><br>"
                    + "<em>" + dateLabel + ": " + dateFormat.format(reviewItem.getUpdateTs()) + "</em><br><br>"
                    + reviewItem.getReview() + "<br><br>";
        }
    }


    /**
     * Получение комментария текущего пользователя
     *
     * @return комментарий или null
     */
    private Review getPersonalReview() {
        return reviewsDs.getPersonalReview();
    }

    /**
     * Открываем персональный отзыв для редактирования
     */
    public void onEditReviewBtnClick() {
        Review personalReview = getPersonalReview();
        AbstractFrame editor = openEditor(personalReview, WindowManager.OpenType.DIALOG);
        ((AbstractEditor) editor).addCloseListener(actionId -> {
            reviewsTable.refresh();
        });
    }

    /**
     * Позволяет удалить персональный отзыв
     */
    public void onRemoveReviewBtnClick() {
        showOptionDialog(
                messages.getMessage(getClass(), "deleteReviewDialog"),
                messages.getMessage(getClass(), "deleteReview"),
                MessageType.CONFIRMATION,
                new Action[]{
                        new DialogAction(DialogAction.Type.YES) {
                            @Override
                            public void actionPerform(Component component) {
                                super.actionPerform(component);
                                Review personalReview = getPersonalReview();
                                reviewsDs.removeItem(personalReview);
                                reviewsDs.commit();
                            }
                        },
                        new DialogAction(DialogAction.Type.NO)
                });

    }
}