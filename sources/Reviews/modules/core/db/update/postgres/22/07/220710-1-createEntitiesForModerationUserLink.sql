create table REVIEWS_ENTITIES_FOR_MODERATION_USER_LINK (
    ENTITIES_FOR_MODERATION_ID uuid,
    USER_ID uuid,
    primary key (ENTITIES_FOR_MODERATION_ID, USER_ID)
);
