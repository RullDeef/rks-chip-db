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
);