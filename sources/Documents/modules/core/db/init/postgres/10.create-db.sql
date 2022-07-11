-- begin DOCUMENTS_DOCUMENT
create table DOCUMENTS_DOCUMENT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    DESIGNATION text,
    DOCUMENT_TYPE_ID uuid,
    --
    primary key (ID)
)^
-- end DOCUMENTS_DOCUMENT
-- begin DOCUMENTS_DOCUMENT_TYPE
create table DOCUMENTS_DOCUMENT_TYPE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    PARENT_ID uuid,
    --
    primary key (ID)
)^
-- end DOCUMENTS_DOCUMENT_TYPE
-- begin DOCUMENTS_DOCUMENT_FILE_DESCRIPTOR_LINK
create table DOCUMENTS_DOCUMENT_FILE_DESCRIPTOR_LINK (
    DOCUMENT_ID uuid,
    FILE_DESCRIPTOR_ID uuid,
    primary key (DOCUMENT_ID, FILE_DESCRIPTOR_ID)
)^
-- end DOCUMENTS_DOCUMENT_FILE_DESCRIPTOR_LINK

-- begin DOCUMENTS_DOCUMENT_RESTRICTION
create table DOCUMENTS_DOCUMENT_RESTRICTION (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    KEY_ varchar(255) not null,
    --
    primary key (ID)
)^
-- end DOCUMENTS_DOCUMENT_RESTRICTION
-- begin DOCUMENTS_DOCUMENT_RESTRICTION_DOCUMENT_TYPE_LINK
create table DOCUMENTS_DOCUMENT_RESTRICTION_DOCUMENT_TYPE_LINK (
    DOCUMENT_RESTRICTION_ID uuid,
    DOCUMENT_TYPE_ID uuid,
    primary key (DOCUMENT_RESTRICTION_ID, DOCUMENT_TYPE_ID)
)^
-- end DOCUMENTS_DOCUMENT_RESTRICTION_DOCUMENT_TYPE_LINK
