package com.company.reviews.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.security.entity.User;
import java.util.List;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import com.haulmont.cuba.core.entity.annotation.Listeners;

@Listeners("reviews_EntitiesForModerationListener")
@NamePattern("%s|entityId")
@Table(name = "REVIEWS_ENTITIES_FOR_MODERATION")
@Entity(name = "reviews$EntitiesForModeration")
public class EntitiesForModeration extends StandardEntity {
    private static final long serialVersionUID = -2474902449856065761L;

    @Column(name = "ENTITY_ID")
    protected UUID entityId;

    @JoinTable(name = "REVIEWS_ENTITIES_FOR_MODERATION_USER_LINK",
        joinColumns = @JoinColumn(name = "ENTITIES_FOR_MODERATION_ID"),
        inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    @ManyToMany
    protected List<User> moderators;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODERATION_PROPERTY_ID")
    protected ModerationProperty moderationProperty;

    public void setModerators(List<User> moderators) {
        this.moderators = moderators;
    }

    public List<User> getModerators() {
        return moderators;
    }


    public void setModerationProperty(ModerationProperty moderationProperty) {
        this.moderationProperty = moderationProperty;
    }

    public ModerationProperty getModerationProperty() {
        return moderationProperty;
    }


    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public UUID getEntityId() {
        return entityId;
    }


}