-- begin NOTIFICATIONSUSERS_MESSAGE
create table NOTIFICATIONSUSERS_MESSAGE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    SENDER_ID uuid,
    SENT boolean,
    IS_SYSTEM boolean not null,
    RECIPIENT_ID uuid,
    SUBJECT varchar(255),
    ENTITY_REFERENCE varchar(255),
    MESSAGE_TEXT_ID uuid,
    META_ID uuid not null,
    ATTACHMENT_ID uuid,
    --
    primary key (ID)
)^
-- end NOTIFICATIONSUSERS_MESSAGE
-- begin NOTIFICATIONSUSERS_MESSAGE_META
create table NOTIFICATIONSUSERS_MESSAGE_META (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    IS_READ boolean not null,
    IS_SENDER_REC boolean not null,
    IS_RECEIVER_REC boolean not null,
    IS_SENDER_DEL boolean not null,
    IS_RECEIVER_DEL boolean not null,
    IS_SENDER_FAV boolean not null,
    IS_RECEIVER_FAV boolean not null,
    --
    primary key (ID)
)^
-- end NOTIFICATIONSUSERS_MESSAGE_META
-- begin NOTIFICATIONSUSERS_MESSAGE_TEXT
create table NOTIFICATIONSUSERS_MESSAGE_TEXT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TEXT text,
    --
    primary key (ID)
)^
-- end NOTIFICATIONSUSERS_MESSAGE_TEXT
-- begin SEC_USER
alter table SEC_USER add column COMPANY_REF varchar(255) ^
alter table SEC_USER add column COMPANY_ID uuid ^
alter table SEC_USER add column PHONE varchar(50) ^
alter table SEC_USER add column PH_ADD varchar(255) ^
alter table SEC_USER add column FILE_ID uuid ^
alter table SEC_USER add column DTYPE varchar(100) ^
update SEC_USER set DTYPE = 'notificationsusers$ExtUser' where DTYPE is null ^
-- end SEC_USER
