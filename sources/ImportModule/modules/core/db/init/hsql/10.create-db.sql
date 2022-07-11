-- begin IMPORTMODULE_TEST
create table IMPORTMODULE_TEST (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    NUMBER_ integer,
    CHILD_ID varchar(36),
    MANDATORY varchar(255) not null,
    TEST_COMP_ID varchar(36),
    ENUMER integer,
    --
    primary key (ID)
)^
-- end IMPORTMODULE_TEST
-- begin IMPORTMODULE_CHILD
create table IMPORTMODULE_CHILD (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    ADDITIONAL_FIELD varchar(255),
    --
    primary key (ID)
)^
-- end IMPORTMODULE_CHILD
-- begin IMPORTMODULE_TEST_COMP
create table IMPORTMODULE_TEST_COMP (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end IMPORTMODULE_TEST_COMP
