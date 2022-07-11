-- begin RULESMODULE_RULE_SCRIPT
create table RULESMODULE_RULE_SCRIPT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    SCRIPT text,
    COMMENT_ text,
    --
    primary key (ID)
)^
-- end RULESMODULE_RULE_SCRIPT
-- begin RULESMODULE_RULE_DATA_SCRIPT
create table RULESMODULE_RULE_DATA_SCRIPT (
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
    ENTITY varchar(255),
    SQL_ text,
    COMMENT_ text,
    VIEW_ varchar(255),
    --
    primary key (ID)
)^
-- end RULESMODULE_RULE_DATA_SCRIPT
-- begin RULESMODULE_RULE_MANAGER
create table RULESMODULE_RULE_MANAGER (
    ID uuid,
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
    --
    primary key (ID)
)^
-- end RULESMODULE_RULE_MANAGER

-- begin RULESMODULE_RULE_MANAGER_RULE_DATA_SCRIPT_LINK
create table RULESMODULE_RULE_MANAGER_RULE_DATA_SCRIPT_LINK (
    RULE_MANAGER_ID uuid,
    RULE_DATA_SCRIPT_ID uuid,
    primary key (RULE_MANAGER_ID, RULE_DATA_SCRIPT_ID)
)^
-- end RULESMODULE_RULE_MANAGER_RULE_DATA_SCRIPT_LINK
-- begin RULESMODULE_RULE_MANAGER_RULE_SCRIPT_LINK
create table RULESMODULE_RULE_MANAGER_RULE_SCRIPT_LINK (
    RULE_MANAGER_ID uuid,
    RULE_SCRIPT_ID uuid,
    primary key (RULE_MANAGER_ID, RULE_SCRIPT_ID)
)^
-- end RULESMODULE_RULE_MANAGER_RULE_SCRIPT_LINK
