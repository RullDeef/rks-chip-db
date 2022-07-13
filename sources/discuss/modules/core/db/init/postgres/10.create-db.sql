-- begin DISCUSS_COMMENT
create table DISCUSS_COMMENT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PARENT_ID uuid,
    HAS_ANSWER boolean,
    CONTENTS text,
    MODERATED_CONTENT text,
    MODERATED boolean,
    AUTHOR_ID uuid not null,
    ENTITY uuid,
    ENTITY_NAME varchar(255),
    COMMENT_STATUS varchar(50),
    --
    primary key (ID)
)^
-- end DISCUSS_COMMENT
-- begin DISCUSS_COMMENT_PREFERENCE
create table DISCUSS_COMMENT_PREFERENCE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    MODERATION_TYPE varchar(50),
    --
    primary key (ID)
)^
-- end DISCUSS_COMMENT_PREFERENCE
