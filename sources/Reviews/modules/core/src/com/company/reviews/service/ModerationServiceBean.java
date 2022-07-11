package com.company.reviews.service;

import com.company.reviews.entity.EntitiesForModeration;
import com.company.reviews.entity.ModerationProperty;
import com.company.reviews.entity.ModerationType;
import com.company.reviews.entity.Review;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service(ModerationService.NAME)
public class ModerationServiceBean implements ModerationService {

    @Inject
    private Persistence persistence;


    private HashMap<String,ModerationProperty> mPropHashMap=null;

    @Override
    public boolean isPremoderationEnable(Review review) {
        Entity entity=loadEntity(review.getParentName(),review.getParent());
        if(entity==null){
            return false;
        }
        if (mPropHashMap==null){
            initModerationProperties();
        }
        ModerationProperty moderationProperty=mPropHashMap.get(entity.getMetaClass().getName());
        if(moderationProperty!=null){
            if((moderationProperty.getModerationType()== ModerationType.premoderation)||
            (moderationProperty.getModerationType()== ModerationType.allTypes)) {
                ArrayList<EntitiesForModeration> selectedEntities = new ArrayList<>(moderationProperty.getSelectedEntities());
                if (selectedEntities.size() != 0) {
                    for (EntitiesForModeration entitiesForModeration : selectedEntities) {
                        if (((UUID)entity.getId()).equals(entitiesForModeration.getEntityId())) {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isPostmoderationEnable(Review review) {
        Entity entity=loadEntity(review.getParentName(),review.getParent());
        if(entity==null){
            return false;
        }
        if (mPropHashMap==null){
            initModerationProperties();
        }
        ModerationProperty moderationProperty=mPropHashMap.get(entity.getMetaClass().getName());
        if(moderationProperty!=null){
            if((moderationProperty.getModerationType()== ModerationType.postmoderation)||
                    (moderationProperty.getModerationType()== ModerationType.allTypes)){
                ArrayList<EntitiesForModeration> selectedEntities = new ArrayList<>(moderationProperty.getSelectedEntities());
                if (selectedEntities.size() != 0) {
                    for (EntitiesForModeration entitiesForModeration : selectedEntities) {
                        if (((UUID)entity.getId()).equals(entitiesForModeration.getEntityId())) {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isAllowedForUser(Entity entity, User user){
        if((entity==null)||(user==null)){
            return false;
        }
        if (mPropHashMap==null){
            initModerationProperties();
        }
        ModerationProperty moderationProperty=mPropHashMap.get(entity.getMetaClass().getName());
        if(moderationProperty!=null){
            ArrayList<EntitiesForModeration> selectedEntities = new ArrayList<>(moderationProperty.getSelectedEntities());
            if (selectedEntities.size() != 0) {
                for (EntitiesForModeration entitiesForModeration : selectedEntities) {
                    if (((UUID) entity.getId()).equals(entitiesForModeration.getEntityId())) {
                        List<User> userList=entitiesForModeration.getModerators();
                        if(userList.contains(user)){
                            return true;
                        }
                        else {
                            if(userList.size()==0) {
                                return moderationProperty.getModerators().contains(user);
                            }
                        }
                    }
                }
            } else {
                List<User> userList=moderationProperty.getModerators();
                if(userList.contains(user)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Entity loadEntity(String entityName, UUID entityId){
        Metadata metadata=AppBeans.get(Metadata.class);
        MetaClass metaClass=metadata.getClass(entityName);
        Entity entity=null;
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager entityManager = persistence.getEntityManager();
            entity = (Entity) entityManager.find(metaClass.getJavaClass(), entityId);
        }
        return entity;
    }

    private void initModerationProperties(){
        DataManager dataManager= AppBeans.get(DataManager.class);

        LoadContext<ModerationProperty> mPropLoadContext = LoadContext.create(ModerationProperty.class);
        mPropLoadContext.setQuery(LoadContext.createQuery("select e from reviews$ModerationProperty e"));
        mPropLoadContext.setView("moderationProperty-view");
        ArrayList<ModerationProperty> moderationProperties=new ArrayList<>(dataManager.loadList(mPropLoadContext));
        mPropHashMap=new HashMap<>();
        for(ModerationProperty moderationProperty:moderationProperties){
            mPropHashMap.put(moderationProperty.getEntity(),moderationProperty);
        }
    }

    @Override
    public void resetModerationProperties(){
        mPropHashMap=null;
    }
}