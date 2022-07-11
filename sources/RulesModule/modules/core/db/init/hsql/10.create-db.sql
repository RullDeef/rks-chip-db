-- begin RULESMODULE_RULE_SCRIPT
create table RULESMODULE_RULE_SCRIPT (
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
    SCRIPT longvarchar,
    COMMENT_ longvarchar,
    --
    primary key (ID)
)^
-- end RULESMODULE_RULE_SCRIPT
-- begin RULESMODULE_RULE_DATA_SCRIPT
create table RULESMODULE_RULE_DATA_SCRIPT (
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
    ENTITY varchar(255),
    SQL_ longvarchar,
    COMMENT_ longvarchar,
    --
    primary key (ID)
)^
-- end RULESMODULE_RULE_DATA_SCRIPT
-- begin RULESMODULE_RULE_MANAGER
create table RULESMODULE_RULE_MANAGER (
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
    DATA_SCRIPT_ID varchar(36),
    ENTITY varchar(255),
    --
    primary key (ID)
)^
-- end RULESMODULE_RULE_MANAGER
-- begin RULESMODULE_RULE_MANAGER_RULE_SCRIPT_LINK
create table RULESMODULE_RULE_MANAGER_RULE_SCRIPT_LINK (
    RULE_MANAGER_ID varchar(36) not null,
    RULE_SCRIPT_ID varchar(36) not null,
    primary key (RULE_MANAGER_ID, RULE_SCRIPT_ID)
)^
-- end RULESMODULE_RULE_MANAGER_RULE_SCRIPT_LINK
-- begin RULESMODULE_TEST
create table RULESMODULE_TEST (
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
    VALUE_ integer,
    --
    primary key (ID)
)^
-- end RULESMODULE_TEST
