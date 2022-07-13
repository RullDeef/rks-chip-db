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
);