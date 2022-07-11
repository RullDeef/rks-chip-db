package com.company.reviews.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.security.entity.User;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import com.haulmont.chile.core.annotations.NamePattern;
import java.util.UUID;
import com.haulmont.cuba.core.entity.annotation.Listeners;

@Listeners("reviews_ReviewListener")
@NamePattern("%s %s %s|updatedBy,updateTs,grade")
@Table(name = "REVIEWS_REVIEW")
@Entity(name = "reviews$Review")
public class Review extends StandardEntity {
    private static final long serialVersionUID = -7564507296491735493L;

    @NotNull
    @Lob
    @Column(name = "REVIEW", nullable = false)
    protected String review;


    @Column(name = "MODERATION_REASON")
    protected String moderationReason;

    @Lob
    @Column(name = "MODERATED_REVIEW")
    protected String moderatedReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID")
    protected User author;

    @Column(name = "PARENT")
    protected UUID parent;

    @Column(name = "PARENT_NAME")
    protected String parentName;

    @Max(message = "{msg://maxValue}", value = 5)
    @Min(message = "{msg://minValue}", value = 1)
    @NotNull
    @Column(name = "GRADE", nullable = false)
    protected Integer grade;

    @Column(name = "STATUS")
    protected String status;

    public void setModerationReason(String moderationReason) {
        this.moderationReason = moderationReason;
    }

    public String getModerationReason() {
        return moderationReason;
    }


    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentName() {
        return parentName;
    }


    public void setStatus(ReviewStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public ReviewStatus getStatus() {
        return status == null ? null : ReviewStatus.fromId(status);
    }


    public void setModeratedReview(String moderatedReview) {
        this.moderatedReview = moderatedReview;
    }

    public String getModeratedReview() {
        return moderatedReview;
    }


    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }


    public void setParent(UUID parent) {
        this.parent = parent;
    }

    public UUID getParent() {
        return parent;
    }


    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getGrade() {
        return grade;
    }


    public void setReview(String review) {
        this.review = review;
    }

    public String getReview() {
        return review;
    }


}