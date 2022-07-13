-- begin REVIEWS_MODERATION_PROPERTY
create table REVIEWS_MODERATION_PROPERTY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ENTITY varchar(255),
    MODERATION_TYPE varchar(50),
    --
    primary key (ID)
)^
-- end REVIEWS_MODERATION_PROPERTY
-- begin REVIEWS_ENTITIES_FOR_MODERATION
create table REVIEWS_ENTITIES_FOR_MODERATION (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ENTITY_ID uuid,
    MODERATION_PROPERTY_ID uuid,
    --
    primary key (ID)
)^
-- end REVIEWS_ENTITIES_FOR_MODERATION
-- begin REVIEWS_REVIEW
create table REVIEWS_REVIEW (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    REVIEW text not null,
    MODERATION_REASON varchar(255),
    MODERATED_REVIEW text,
    AUTHOR_ID uuid,
    PARENT uuid,
    PARENT_NAME varchar(255),
    GRADE integer not null,
    STATUS varchar(50),
    --
    primary key (ID)
)^
-- end REVIEWS_REVIEW
-- begin REVIEWS_MODERATION_PROPERTY_USER_LINK
create table REVIEWS_MODERATION_PROPERTY_USER_LINK (
    MODERATION_PROPERTY_ID uuid,
    USER_ID uuid,
    primary key (MODERATION_PROPERTY_ID, USER_ID)
)^
-- end REVIEWS_MODERATION_PROPERTY_USER_LINK
-- begin REVIEWS_ENTITIES_FOR_MODERATION_USER_LINK
create table REVIEWS_ENTITIES_FOR_MODERATION_USER_LINK (
    ENTITIES_FOR_MODERATION_ID uuid,
    USER_ID uuid,
    primary key (ENTITIES_FOR_MODERATION_ID, USER_ID)
)^
-- end REVIEWS_ENTITIES_FOR_MODERATION_USER_LINK
