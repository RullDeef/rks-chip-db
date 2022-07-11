package com.company.reviews.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.security.entity.User;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.OneToMany;
import com.haulmont.cuba.core.entity.annotation.Listeners;

@Listeners("reviews_ModerationPropertyListener")
@NamePattern("%s|entity")
@Table(name = "REVIEWS_MODERATION_PROPERTY")
@Entity(name = "reviews$ModerationProperty")
public class ModerationProperty extends StandardEntity {
    private static final long serialVersionUID = 4671436959808859357L;

    @Column(name = "ENTITY", unique = true)
    protected String entity;

    @Column(name = "MODERATION_TYPE")
    protected String moderationType;

    @OneToMany(mappedBy = "moderationProperty")
    protected List<EntitiesForModeration> selectedEntities;

    @JoinTable(name = "REVIEWS_MODERATION_PROPERTY_USER_LINK",
        joinColumns = @JoinColumn(name = "MODERATION_PROPERTY_ID"),
        inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    @ManyToMany
    protected List<User> moderators;

    public void setSelectedEntities(List<EntitiesForModeration> selectedEntities) {
        this.selectedEntities = selectedEntities;
    }

    public List<EntitiesForModeration> getSelectedEntities() {
        return selectedEntities;
    }


    public ModerationType getModerationType() {
        return moderationType == null ? null : ModerationType.fromId(moderationType);
    }

    public void setModerationType(ModerationType moderationType) {
        this.moderationType = moderationType == null ? null : moderationType.getId();
    }


    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }

    public void setModerators(List<User> moderators) {
        this.moderators = moderators;
    }

    public List<User> getModerators() {
        return moderators;
    }


}