-- begin MOBDEKBKP_MATERIAL
create table MOBDEKBKP_MATERIAL (
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
    TYPE_ varchar(50) not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_MATERIAL
-- begin MOBDEKBKP_TYPONOMINAL_BODY
create table MOBDEKBKP_TYPONOMINAL_BODY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DESIGNATION varchar(255) not null,
    NAME varchar(255) not null,
    DESCRIPTION varchar(255),
    LENGTH double precision,
    WIDTH double precision,
    HEIGHT double precision,
    PINS_COUNT integer,
    BODY_MATERIAL_ID uuid,
    PINS_MATERIAL_ID uuid,
    COATING_MATERIAL_ID uuid,
    DISTANCE_LEADS double precision,
    MASS double precision,
    MAX_HEIGHT double precision,
    PIN_FORMING_DESIGNATION_ID uuid,
    PHOTO_ID uuid,
    SEALING_DEMAND varchar(50) not null,
    DISPERSE_POWER double precision,
    OUTPUT_POWER double precision,
    THERMAL_RESISTANCE double precision,
    DIMENSIONS_AND_BODY_ID uuid,
    FIXATOR_MARKUP_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPONOMINAL_BODY
-- begin MOBDEKBKP_COUNTRY
create table MOBDEKBKP_COUNTRY (
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
    COUNTRY_TYPE varchar(50) not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_COUNTRY
-- begin MOBDEKBKP_COMPANY_TYPE
create table MOBDEKBKP_COMPANY_TYPE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME text not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_COMPANY_TYPE
-- begin MOBDEKBKP_LICENSE_TYPE
create table MOBDEKBKP_LICENSE_TYPE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME text not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_LICENSE_TYPE
-- begin MOBDEKBKP_COMPANY_LICENSE
create table MOBDEKBKP_COMPANY_LICENSE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER_ varchar(255) not null,
    DATE_OBTAINED date,
    DATE_EXPIRE date,
    TYPE_ID uuid not null,
    COMPANY_ID uuid not null,
    DOCUMENT_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_COMPANY_LICENSE
-- begin MOBDEKBKP_COMPANY
create table MOBDEKBKP_COMPANY (
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
    NAME_SHORT varchar(255),
    ADDRESS_FACT text not null,
    ADDRESS_LEGAL text not null,
    OGRN varchar(255),
    KPP varchar(255),
    OKUD varchar(255),
    INN varchar(255),
    OKPO varchar(255),
    AGENT varchar(255),
    PHONE varchar(255) not null,
    FAX varchar(255),
    EMAIL varchar(255),
    WEBSITE varchar(255),
    IS_MILITARY varchar(50) not null,
    COUNTRY_ID uuid not null,
    RATING double precision,
    LOGO_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_COMPANY
-- begin MOBDEKBKP_TYPONOMINAL_QUALITY_LEVEL_NATIVE
create table MOBDEKBKP_TYPONOMINAL_QUALITY_LEVEL_NATIVE (
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
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPONOMINAL_QUALITY_LEVEL_NATIVE
-- begin MOBDEKBKP_TYPONOMINAL_QUALITY_LEVEL_IMPORT
create table MOBDEKBKP_TYPONOMINAL_QUALITY_LEVEL_IMPORT (
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
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPONOMINAL_QUALITY_LEVEL_IMPORT
-- begin MOBDEKBKP_BRAND_SOLDER
create table MOBDEKBKP_BRAND_SOLDER (
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
    --
    primary key (ID)
)^
-- end MOBDEKBKP_BRAND_SOLDER
-- begin MOBDEKBKP_BRAND_FLUX
create table MOBDEKBKP_BRAND_FLUX (
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
    --
    primary key (ID)
)^
-- end MOBDEKBKP_BRAND_FLUX
-- begin MOBDEKBKP_GLUE_TYPE
create table MOBDEKBKP_GLUE_TYPE (
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
    --
    primary key (ID)
)^
-- end MOBDEKBKP_GLUE_TYPE
-- begin MOBDEKBKP_INSTALL_METHOD
create table MOBDEKBKP_INSTALL_METHOD (
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
    --
    primary key (ID)
)^
-- end MOBDEKBKP_INSTALL_METHOD
-- begin MOBDEKBKP_TYPONOMINAL_INSTALL_PARAMETERS
create table MOBDEKBKP_TYPONOMINAL_INSTALL_PARAMETERS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DESCRIPTION text not null,
    NON_PB_SOLDER_TECH varchar(50) not null,
    TEMPERATURE_MODE varchar(255),
    GAS_ENVIRONMENT_AVAILABLE varchar(50) not null,
    BODY_INSTALLATION_DOCUMENT_ID uuid,
    INSTALLATION_OPTION_DESIGNATION varchar(255),
    PIN_FORMING_DOCUMENT_ID uuid,
    PIN_FORMING_DESIGNATION varchar(255),
    HAS_GASKET varchar(50) not null,
    GASKET_SIZE varchar(255),
    GASKET_MATERIAL_ID uuid,
    HAS_GLUE varchar(50) not null,
    INSTALLATION_COUNT_ALLOWED integer,
    AUTO_INSTALLATION varchar(50) not null,
    GLUE_TYPE_ID uuid,
    INSTALL_METHOD_ID uuid not null,
    SOLDER_BRAND_ID uuid,
    FLUX_BRAND_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPONOMINAL_INSTALL_PARAMETERS
-- begin MOBDEKBKP_TYPONOMINAL
create table MOBDEKBKP_TYPONOMINAL (
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
    TYPONOMINAL_QUALITY_LEVEL_NATIVE_ID uuid,
    TYPONOMINAL_QUALITY_LEVEL_IMPORT_ID uuid,
    MASS double precision,
    NOT_PERSPECTIVE varchar(50) not null,
    HAS_SUBSTITUTE varchar(50) not null,
    SEAL_NEEDED varchar(50) not null,
    IS_AUTO_ASSEMBLE varchar(50) not null,
    MADE_USING_IMPORT_ITEMS varchar(50) not null,
    SHELF_LIFE integer,
    PERSISTENCE_CYCLE integer,
    HAS_BODY varchar(50) not null,
    LIFE_CYCLE_STAGE varchar(50) not null,
    LABELING varchar(255),
    BODY_ID uuid,
    INSTALL_PARAMETERS_ID uuid,
    TYPE_ID uuid not null,
    LIB_ATTRIBUTES_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPONOMINAL
-- begin MOBDEKBKP_TYPONOMINAL_ANALOG
create table MOBDEKBKP_TYPONOMINAL_ANALOG (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPONOMINAL_ID uuid not null,
    TYPE_ANALOG varchar(50) not null,
    IS_RECOMMENDED_GNIO varchar(50) not null,
    PARENT_TYPONOMINAL_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPONOMINAL_ANALOG
-- begin MOBDEKBKP_OUTER_INFORMATION_SOURCE
create table MOBDEKBKP_OUTER_INFORMATION_SOURCE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DATABASE_NAME varchar(255) not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_OUTER_INFORMATION_SOURCE
-- begin MOBDEKBKP_OUTER_PERSISTENCE_INFO
create table MOBDEKBKP_OUTER_PERSISTENCE_INFO (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TIPONOMINAL_ID uuid not null,
    MANUFACTURER_ID uuid not null,
    INFO_SOURCE_ID uuid not null,
    SINGLE_EFFECT_DATA varchar(255),
    DOSE_EFFECT_DATA varchar(255),
    HAS_FILES varchar(50) not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_OUTER_PERSISTENCE_INFO
-- begin MOBDEKBKP_COMPANY_TYPE_ENTRY
create table MOBDEKBKP_COMPANY_TYPE_ENTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPE_ID uuid not null,
    COMPANY_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_COMPANY_TYPE_ENTRY
-- begin MOBDEKBKP_TYPE_MATH_MODEL_PARAMETERS
create table MOBDEKBKP_TYPE_MATH_MODEL_PARAMETERS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    EL_PROC_MODEL_NAME varchar(255) not null,
    EL_PROC_DESCRIPTION varchar(255),
    EL_PROC_MODEL_CATEGORY varchar(255),
    EL_PROC_MODEL_SUBCATEGORY varchar(255),
    EL_PROC_MODEL_CONNECTION_LIST varchar(255),
    EL_PROC_MODEL_PREFIX varchar(255),
    EL_PROC_MODEL_TO_VIEW_COMPARE varchar(255),
    APPLY_BOUNDS varchar(255),
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPE_MATH_MODEL_PARAMETERS
-- begin MOBDEKBKP_TYPE
create table MOBDEKBKP_TYPE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DESIGNATION varchar(255) not null,
    RELIABILITY_HANDBOOK varchar(50),
    ALT_CLASS_REL_ID uuid,
    ALT_CLASS_MIL_ID uuid,
    ALT_CLASS_G56649_ID uuid,
    ALT_CLASS_G2710_ID uuid,
    HAS_LONG_DELIVER_CYCLE varchar(50) not null,
    FUNCTIONAL_PURPOSE text not null,
    PLACEMENT_CATEGORY varchar(50) not null,
    CLIMATIC_IMPLEMENTATION_ID uuid,
    MATH_MODEL_PARAMS_ID uuid,
    TYPE_CLASS_ID uuid not null,
    AMOUNT_UNIT_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPE
-- begin MOBDEKBKP_PARAMETER
create table MOBDEKBKP_PARAMETER (
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
    DESCRIPTION text,
    UNIT_ID uuid not null,
    DEFAULT_VALUE_TYPE varchar(50) not null,
    PARAM_TYPE varchar(50) not null,
    PARAMETER_CATEGORY_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_PARAMETER
-- begin MOBDEKBKP_PARAMETER_VALUE
create table MOBDEKBKP_PARAMETER_VALUE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CURRENT_VALUE_TYPE varchar(50) not null,
    VAL_STR_REC_ID uuid,
    VAL_STR_LIB_ID uuid,
    VAL_FLOAT double precision,
    VAL_STRING varchar(255),
    VAL_MAX double precision,
    VAL_MIN double precision,
    TOLERANCE_PLUS double precision,
    TOLERANCE_MINUS double precision,
    GAMMA integer,
    TYPE_ID uuid not null,
    PARAMETER_ID uuid not null,
    OPERATION_CONDITION_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_PARAMETER_VALUE
-- begin MOBDEKBKP_TYPE_CLASS
create table MOBDEKBKP_TYPE_CLASS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER_ varchar(255) not null,
    NAME varchar(255) not null,
    TYPE_ID uuid,
    PARENT_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPE_CLASS
-- begin MOBDEKBKP_TYPE_CLASS_TYPE
create table MOBDEKBKP_TYPE_CLASS_TYPE (
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
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPE_CLASS_TYPE
-- begin MOBDEKBKP_TYPE_CLASS_CHARACTERISTIC
create table MOBDEKBKP_TYPE_CLASS_CHARACTERISTIC (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    IS_MAIN boolean not null,
    PARAMETER_ID uuid not null,
    TYPE_CLASS_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPE_CLASS_CHARACTERISTIC
-- begin MOBDEKBKP_TYPE_PROVIDER_ENTRY
create table MOBDEKBKP_TYPE_PROVIDER_ENTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME_ID uuid not null,
    TYPE_INVERSE_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPE_PROVIDER_ENTRY
-- begin MOBDEKBKP_TYPE_MANUFACTURER_ENTRY
create table MOBDEKBKP_TYPE_MANUFACTURER_ENTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME_ID uuid not null,
    TYPE_INVERSE_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPE_MANUFACTURER_ENTRY
-- begin MOBDEKBKP_UNIT_DEV
create table MOBDEKBKP_UNIT_DEV (
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
    SHORT_NAME varchar(255) not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_UNIT_DEV
-- begin MOBDEKBKP_DEVICE
create table MOBDEKBKP_DEVICE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DESIGNATION varchar(255) not null,
    DEVELOPER_ID uuid not null,
    GENERAL_CONSTRUCTOR varchar(255),
    DEMANDS text,
    DEVICE_PROJECT_LIST_ID uuid,
    IS_APPROVED boolean not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_DEVICE
-- begin MOBDEKBKP_DEVICE_PART
create table MOBDEKBKP_DEVICE_PART (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DESIGNATION varchar(255) not null,
    TYPE_ varchar(255) not null,
    DEVELOPER_ID uuid not null,
    CONSTRUCTOR varchar(255),
    DEMANDS text,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_DEVICE_PART
-- begin MOBDEKBKP_TYPE_CALICOHOLDER_ENTRY
create table MOBDEKBKP_TYPE_CALICOHOLDER_ENTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME_ID uuid not null,
    TYPE_INVERSE_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPE_CALICOHOLDER_ENTRY
-- begin MOBDEKBKP_DEVICE_PART_LIST_PLANNED
create table MOBDEKBKP_DEVICE_PART_LIST_PLANNED (
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
    STATUS varchar(50),
    DEVICE_PART_ID uuid,
    DEVICE_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_DEVICE_PART_LIST_PLANNED
-- begin MOBDEKBKP_DEVICE_PART_LIST_PLANNED_ENTRY
create table MOBDEKBKP_DEVICE_PART_LIST_PLANNED_ENTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPONOMINAL_ID uuid not null,
    STATUS varchar(50),
    DEVICE_PART_LIST_PLANNED_INVERSE_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_DEVICE_PART_LIST_PLANNED_ENTRY
-- begin MOBDEKBKP_DEVICE_LIST_PROJECT
create table MOBDEKBKP_DEVICE_LIST_PROJECT (
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
    APPROVAL_DATE timestamp,
    STATUS varchar(50),
    DEMANDS text,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_DEVICE_LIST_PROJECT
-- begin MOBDEKBKP_DEVICE_LIST_PROJECT_ENTRY
create table MOBDEKBKP_DEVICE_LIST_PROJECT_ENTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPONOMINAL_ID uuid,
    STATUS varchar(50),
    DEVICE_LIST_PROJECT_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_DEVICE_LIST_PROJECT_ENTRY
-- begin MOBDEKBKP_DEVICE_LIST_PROJECT_ADDITION
create table MOBDEKBKP_DEVICE_LIST_PROJECT_ADDITION (
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
    STATUS varchar(50),
    DEVICE_LIST_PROJECT_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_DEVICE_LIST_PROJECT_ADDITION
-- begin MOBDEKBKP_DEVICE_LIST_PROJECT_ADDITION_ENTRY
create table MOBDEKBKP_DEVICE_LIST_PROJECT_ADDITION_ENTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    EDITED_ID uuid,
    NEWTYPONOMINAL_ID uuid,
    ADDITION_TYPE varchar(50),
    DEVICE_LIST_PROJECT_ADDITION_ID uuid,
    STATUS varchar(50),
    --
    primary key (ID)
)^
-- end MOBDEKBKP_DEVICE_LIST_PROJECT_ADDITION_ENTRY
-- begin MOBDEKBKP_OUTER_LIST_ALLOWING
create table MOBDEKBKP_OUTER_LIST_ALLOWING (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPE_ID uuid not null,
    NUMBER_ varchar(255) not null,
    NAME varchar(255) not null,
    START_DATE date not null,
    END_DATE date,
    SUBSTITUTING varchar(255),
    RELEASE_YEAR varchar(255) not null,
    IS_MODIFICATION varchar(50) not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_OUTER_LIST_ALLOWING
-- begin MOBDEKBKP_OUTER_LIST_TYPE
create table MOBDEKBKP_OUTER_LIST_TYPE (
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
    IS_ALLOWING varchar(50) not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_OUTER_LIST_TYPE
-- begin MOBDEKBKP_OUTER_LIST_ALLOWING_ENTRY
create table MOBDEKBKP_OUTER_LIST_ALLOWING_ENTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPONOMINAL_ID uuid not null,
    LINK_TO_LIST_ENTRY varchar(255),
    OUTER_LIST_ALLOWING_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_OUTER_LIST_ALLOWING_ENTRY
-- begin MOBDEKBKP_DEVICE_PARTS_ENTRY
create table MOBDEKBKP_DEVICE_PARTS_ENTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    AMOUNT integer not null,
    PART_ID uuid not null,
    DEVICE_ID uuid not null,
    IN_DEVICE_PART_COMLECT_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_DEVICE_PARTS_ENTRY
-- begin MOBDEKBKP_DEVICE_COMPLECT
create table MOBDEKBKP_DEVICE_COMPLECT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    COMPLECT_NUMBER varchar(255) not null,
    PROGRESS double precision,
    DEVICE_ID uuid,
    STATUS varchar(50) not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_DEVICE_COMPLECT
-- begin MOBDEKBKP_DEVICE_COMPLECT_LIST
create table MOBDEKBKP_DEVICE_COMPLECT_LIST (
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
    STATUS varchar(50),
    --
    primary key (ID)
)^
-- end MOBDEKBKP_DEVICE_COMPLECT_LIST
-- begin MOBDEKBKP_DEVICE_PART_LIST_COMPLECT
create table MOBDEKBKP_DEVICE_PART_LIST_COMPLECT (
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
    STATUS varchar(50),
    DEVICE_COMPLECT_ID uuid not null,
    DEVICE_PART_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_DEVICE_PART_LIST_COMPLECT
-- begin MOBDEKBKP_DEVICE_PART_LIST_COMPLECT_ENTRY
create table MOBDEKBKP_DEVICE_PART_LIST_COMPLECT_ENTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPONOMINAL_ID uuid not null,
    STATUS varchar(50) not null,
    AMOUNT_IN_DEVICE integer,
    AMOUNT_TOTAL integer not null,
    AMOUNT_DELIVERED integer,
    DATE_PLANNED date,
    DATE_FACT date,
    QUESTIONS text,
    BY_HEAD_EXECUTOR varchar(50) not null,
    DEVICE_PART_LIST_COMPLECT_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_DEVICE_PART_LIST_COMPLECT_ENTRY
-- begin MOBDEKBKP_APPLICATION_NEW_TYPONOMINAL_ADD
create table MOBDEKBKP_APPLICATION_NEW_TYPONOMINAL_ADD (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DESIGNATION text,
    STATUS varchar(50),
    DELIVERY_DOC_DESIGNATION text,
    CLASS_MOP varchar(255),
    PRODUCER varchar(255),
    DESCRIPTION text,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_APPLICATION_NEW_TYPONOMINAL_ADD
-- begin MOBDEKBKP_APPLICATION_NEW_TYPONOMINAL_DEV
create table MOBDEKBKP_APPLICATION_NEW_TYPONOMINAL_DEV (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CLASS_NAME varchar(255),
    EVENT text,
    CHARACTERISTICS text,
    PROTOTYPE text,
    POSSIBILITY text,
    DOCUMENT_ID uuid,
    STATUS varchar(50) not null,
    COMMON_APPLICATION_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_APPLICATION_NEW_TYPONOMINAL_DEV
-- begin MOBDEKBKP_APPLICATION_OKR_INFO
create table MOBDEKBKP_APPLICATION_OKR_INFO (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    RESPONSIBLE_ID uuid not null,
    DESCRIPTION text not null,
    DATE_START date not null,
    DATE_APPROX date,
    DATE_END date,
    CONDITION_ varchar(50) not null,
    RESULT_ varchar(255),
    APPLICATION_COMMON_ENTRY_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_APPLICATION_OKR_INFO
-- begin MOBDEKBKP_APPLICATION_COMMON_ENTRY
create table MOBDEKBKP_APPLICATION_COMMON_ENTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CLASS_NAME varchar(255),
    EVENTS text,
    CHARACTERISTICS text,
    PROTOTYPE text,
    POSSIBILITY text,
    STATUS varchar(50),
    APPLICATION_COMMON_DEV_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_APPLICATION_COMMON_ENTRY
-- begin MOBDEKBKP_APPLICATION_COMMON_DEV
create table MOBDEKBKP_APPLICATION_COMMON_DEV (
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
    STATUS varchar(50) not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_APPLICATION_COMMON_DEV
-- begin MOBDEKBKP_CURRENCY
create table MOBDEKBKP_CURRENCY (
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
    NAME_SHORT varchar(255) not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_CURRENCY
-- begin MOBDEKBKP_COST
create table MOBDEKBKP_COST (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    VALUE_ double precision not null,
    CURRENCY_ID uuid not null,
    SETUP_DATE date not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_COST
-- begin MOBDEKBKP_TYPONOMINAL_PURCHASE_PARAMETERS
create table MOBDEKBKP_TYPONOMINAL_PURCHASE_PARAMETERS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PURCHASE_DESIGNATION varchar(255) not null,
    COST_RATING text not null,
    TYPICAL_DELIVERY_TERM integer not null,
    MARKET_AVAILABLE_INDEX text,
    NEED_PERMISSIONS_GOSDEP varchar(50) not null,
    PERMISSION_GOSDEP_TERM integer,
    HAS_SAMPLES varchar(50) not null,
    DELIVERY_MIN integer,
    DELIVERY_MAX integer,
    TYPONOMINAL_ID uuid not null,
    COMPANY_ID uuid not null,
    COST_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPONOMINAL_PURCHASE_PARAMETERS
-- begin MOBDEKBKP_DEVICE_LIST_PROJECT_APPLICATION
create table MOBDEKBKP_DEVICE_LIST_PROJECT_APPLICATION (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    SUGGESTED_ID uuid not null,
    RATIONALE text not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_DEVICE_LIST_PROJECT_APPLICATION
-- begin MOBDEKBKP_TYPE_CLIMATIC_IMPLEMENTATION
create table MOBDEKBKP_TYPE_CLIMATIC_IMPLEMENTATION (
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
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPE_CLIMATIC_IMPLEMENTATION
-- begin MOBDEKBKP_UNIT_VAL
create table MOBDEKBKP_UNIT_VAL (
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
    SHORT_NAME varchar(255) not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_UNIT_VAL
-- begin MOBDEKBKP_TERMS_AND_DEFINITIONS
create table MOBDEKBKP_TERMS_AND_DEFINITIONS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TERM text not null,
    DEFINITION_ text not null,
    SOURCE text,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TERMS_AND_DEFINITIONS
-- begin MOBDEKBKP_SUBSTRATE
create table MOBDEKBKP_SUBSTRATE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    MODEL varchar(255) not null,
    NAME varchar(255),
    MATERIAL_ID uuid not null,
    THICKNESS double precision not null,
    COLOR varchar(255),
    ROUGHNESS double precision,
    DENSITY double precision,
    MOISTURE_ABSORB double precision,
    FLEX_STRENGTH double precision,
    ELASTIC_MODULE double precision,
    HEAT_CONDUCT_COEFF double precision,
    HEAT_CAPACITY double precision,
    LINE_EXT_COEFF300 double precision,
    LINE_EXT_COEFF600 double precision,
    LINE_EXT_COEFF1000 double precision,
    DIELECTRIC_CONSTANT_M varchar(255),
    DIELECTRIC_CONSTANT_G varchar(255),
    DIELECTRIC_LOSS_COEFF double precision,
    BREAKDOWN_VOLTAGE double precision,
    RESISTIVITY_LEVEL double precision,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_SUBSTRATE
-- begin MOBDEKBKP_SUBSTRATE_ENTRY
create table MOBDEKBKP_SUBSTRATE_ENTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    SUBSTRATE_ID uuid not null,
    TYPONOMINAL_INSTALL_PARAMETERS_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_SUBSTRATE_ENTRY
-- begin MOBDEKBKP_COMPANY_NEED
create table MOBDEKBKP_COMPANY_NEED (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPONOMINAL_ID uuid not null,
    AMOUNT integer not null,
    WANTED_DELIVER_DATE date not null,
    COMPANY_ID uuid not null,
    STATUS integer,
    COMPANY_NEED_APPLICATION_ID uuid,
    REQUIREMENTS text,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_COMPANY_NEED
-- begin MOBDEKBKP_LIB_ATTRIBUTES
create table MOBDEKBKP_LIB_ATTRIBUTES (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPE_ varchar(255) not null,
    SERIES varchar(255),
    VIEW_MODEL_ID uuid,
    VIEW_NAME varchar(255),
    ALTER_VIEW varchar(255),
    POSITION_PREFIX varchar(255),
    KEY_ATTRIBUTE varchar(255),
    RANGE_DEFINITION varchar(255),
    LAND_MODEL_ID uuid,
    LAND_NAME varchar(255),
    ALTER_LAND varchar(255),
    IDE_ATTRIB_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_LIB_ATTRIBUTES
-- begin MOBDEKBKP_OUTER_REJECTION
create table MOBDEKBKP_OUTER_REJECTION (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPONOMINAL_ID uuid not null,
    DATE_ date not null,
    CHECKED integer not null,
    REJECTED integer not null,
    REASON text not null,
    FAILED_BY_CONSUMER integer not null,
    NOT_CHECKED integer not null,
    COMMENT_ text not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_OUTER_REJECTION
-- begin MOBDEKBKP_OUTER_DB_FAIL
create table MOBDEKBKP_OUTER_DB_FAIL (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPONOMINAL_ID uuid not null,
    INDEX_ varchar(255) not null,
    MAUFACTURE_DATE date,
    FAIL_DATE date,
    WORK_FACT double precision,
    WORK_GUARANTEE double precision,
    CLAIM_DOCS text,
    MANUFACTURER_ID uuid,
    CLAIMED_COMPANY_ID uuid,
    FAIL_TYPE text,
    FAIL_TYPE_COMMENT text,
    REPEATING varchar(255),
    PART varchar(255),
    VISIBLE_FAIL text,
    DESCRIPTION text,
    COMISSION_RESUME text,
    PREVIOUS_RESUME text,
    SOURCE_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_OUTER_DB_FAIL
-- begin MOBDEKBKP_OUTER_ENTRANCE_TESTS
create table MOBDEKBKP_OUTER_ENTRANCE_TESTS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPONOMINAL_ID uuid not null,
    INDEX_ varchar(255),
    DESCRIPTION text not null,
    CERT_CENTER_ID uuid not null,
    AMOUNT_CHECKED integer,
    AMOUNT_PASSED integer,
    AMOUNT_FAILED integer,
    FAIL_DESCRIPTION text,
    DATE_START date,
    DATE_END date,
    TEST_RESULT text,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_OUTER_ENTRANCE_TESTS
-- begin MOBDEKBKP_OUTER_CERTIFICATE_TESTS
create table MOBDEKBKP_OUTER_CERTIFICATE_TESTS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPONOMINAL_ID uuid not null,
    INDEX_ varchar(255),
    DESCRIPTION text,
    DATE_START date,
    DATE_END date,
    RESULT_ text,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_OUTER_CERTIFICATE_TESTS
-- begin MOBDEKBKP_OUTER_DB_REFUSE_TESTS
create table MOBDEKBKP_OUTER_DB_REFUSE_TESTS (
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
    --
    primary key (ID)
)^
-- end MOBDEKBKP_OUTER_DB_REFUSE_TESTS
-- begin MOBDEKBKP_OUTER_DB_REFUSE_DEFECTS
create table MOBDEKBKP_OUTER_DB_REFUSE_DEFECTS (
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
    --
    primary key (ID)
)^
-- end MOBDEKBKP_OUTER_DB_REFUSE_DEFECTS
-- begin MOBDEKBKP_OUTER_DB_REFUSE
create table MOBDEKBKP_OUTER_DB_REFUSE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPONOMINAL_ID uuid not null,
    INDEX_ varchar(255),
    NAME varchar(255),
    DEV_INDEX varchar(255),
    FACTORY_NUMBER varchar(255),
    PRODUCE_DATE varchar(255),
    PROVIDER_ID uuid,
    CHECKED integer,
    REFUSED integer,
    FAILED integer,
    REFUSE_PLACE varchar(255),
    ACTIONS varchar(255),
    CONCLUSION text,
    SHORT_ACTIONS text,
    TESTS_ID uuid,
    DEFECTS_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_OUTER_DB_REFUSE
-- begin MOBDEKBKP_OUTER_FAIL_AND_REFUSES
create table MOBDEKBKP_OUTER_FAIL_AND_REFUSES (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPONOMINAL_ID uuid not null,
    RELEASE_DATE date not null,
    REFUSE_DATE date not null,
    BEFORE_REFUSE double precision not null,
    ACCEPTED_DEFECT integer not null,
    DENY_REASONS varchar(255) not null,
    DEFECT_CLASS text not null,
    DEFECT_REPEAT text not null,
    USER_BLAME integer not null,
    PREVENT text not null,
    ACTIONS_DATE date not null,
    AMOUNT_AT_PROVIDER integer not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_OUTER_FAIL_AND_REFUSES
-- begin MOBDEKBKP_APPLICATION_NEW_DEV_ENTRY
create table MOBDEKBKP_APPLICATION_NEW_DEV_ENTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    APPLICATION_ID uuid not null,
    APPLICATION_COMMON_ENTRY_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_APPLICATION_NEW_DEV_ENTRY
-- begin MOBDEKBKP_SUPPORT_INFO
create table MOBDEKBKP_SUPPORT_INFO (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PHONE varchar(255) not null,
    INSTRUCTIONS_ID uuid,
    MAIL varchar(255) not null,
    MESSAGE text,
    EXPIRATION timestamp,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_SUPPORT_INFO
-- begin MOBDEKBKP_OPERATION_CONDITIONS
create table MOBDEKBKP_OPERATION_CONDITIONS (
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
    TEXT text,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_OPERATION_CONDITIONS
-- begin MOBDEKBKP_ALTER_CLASS_RELIABILITY
create table MOBDEKBKP_ALTER_CLASS_RELIABILITY (
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
    TYPE_ID uuid not null,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_ALTER_CLASS_RELIABILITY
-- begin MOBDEKBKP_ALTER_CLASS_MIL
create table MOBDEKBKP_ALTER_CLASS_MIL (
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
    SHORT_NAME varchar(255) not null,
    PARENT_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_ALTER_CLASS_MIL
-- begin MOBDEKBKP_ALTER_CLASS_GOST2710
create table MOBDEKBKP_ALTER_CLASS_GOST2710 (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPE_GROUP varchar(255) not null,
    TYPE_SAMPLES varchar(255),
    CODE varchar(255) not null,
    PARENT_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_ALTER_CLASS_GOST2710
-- begin MOBDEKBKP_ALTER_CLASS_GOST56649
create table MOBDEKBKP_ALTER_CLASS_GOST56649 (
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
    TYPE_SAMPLES varchar(255),
    CODE varchar(255),
    --
    primary key (ID)
)^
-- end MOBDEKBKP_ALTER_CLASS_GOST56649
-- begin MOBDEKBKP_DEVICE_FILTER_CONDITIONS
create table MOBDEKBKP_DEVICE_FILTER_CONDITIONS (
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
    PATH text,
    ATTRIBUTE varchar(255),
    VALUE_TYPE varchar(50),
    COMPARE_OPERATOR integer not null,
    VAL_FLOAT double precision,
    VAL_STRING varchar(255),
    VAL_BOOLEAN boolean,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_DEVICE_FILTER_CONDITIONS
-- begin MOBDEKBKP_HANDBOOK
create table MOBDEKBKP_HANDBOOK (
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
    PUBLISHED boolean,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_HANDBOOK
-- begin MOBDEKBKP_HANDBOOK_ENTRY
create table MOBDEKBKP_HANDBOOK_ENTRY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    KEY_ varchar(255),
    VALUE_ text,
    PARENT_ID uuid,
    HANDBOOK_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_HANDBOOK_ENTRY
-- begin MOBDEKBKP_HANDBOOK_CAD
create table MOBDEKBKP_HANDBOOK_CAD (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    HANDBOOK_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_HANDBOOK_CAD
-- begin MOBDEKBKP_OUTER_ENTRANCE_TESTS_DOCUMENT_LINK
create table MOBDEKBKP_OUTER_ENTRANCE_TESTS_DOCUMENT_LINK (
    OUTER_ENTRANCE_TESTS_ID uuid,
    DOCUMENT_ID uuid,
    primary key (OUTER_ENTRANCE_TESTS_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_OUTER_ENTRANCE_TESTS_DOCUMENT_LINK
-- begin MOBDEKBKP_DEVICE_LIST_PROJECT_APPLICATION_DOCUMENT_LINK
create table MOBDEKBKP_DEVICE_LIST_PROJECT_APPLICATION_DOCUMENT_LINK (
    DEVICE_LIST_PROJECT_APPLICATION_ID uuid,
    DOCUMENT_ID uuid,
    primary key (DEVICE_LIST_PROJECT_APPLICATION_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_DEVICE_LIST_PROJECT_APPLICATION_DOCUMENT_LINK
-- begin MOBDEKBKP_DEVICE_PART_DOCUMENT_LINK
create table MOBDEKBKP_DEVICE_PART_DOCUMENT_LINK (
    DEVICE_PART_ID uuid,
    DOCUMENT_ID uuid,
    primary key (DEVICE_PART_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_DEVICE_PART_DOCUMENT_LINK
-- begin MOBDEKBKP_TYPE_MATH_MODEL_PARAMETERS_DOCUMENT_LINK
create table MOBDEKBKP_TYPE_MATH_MODEL_PARAMETERS_DOCUMENT_LINK (
    TYPE_MATH_MODEL_PARAMETERS_ID uuid,
    DOCUMENT_ID uuid,
    primary key (TYPE_MATH_MODEL_PARAMETERS_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_TYPE_MATH_MODEL_PARAMETERS_DOCUMENT_LINK
-- begin MOBDEKBKP_TYPONOMINAL_BODY_DOCUMENT_LINK
create table MOBDEKBKP_TYPONOMINAL_BODY_DOCUMENT_LINK (
    TYPONOMINAL_BODY_ID uuid,
    DOCUMENT_ID uuid,
    primary key (TYPONOMINAL_BODY_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_TYPONOMINAL_BODY_DOCUMENT_LINK
-- begin MOBDEKBKP_APPLICATION_NEW_TYPONOMINAL_ADD_DOCUMENT_LINK
create table MOBDEKBKP_APPLICATION_NEW_TYPONOMINAL_ADD_DOCUMENT_LINK (
    APPLICATION_NEW_TYPONOMINAL_ADD_ID uuid,
    DOCUMENT_ID uuid,
    primary key (APPLICATION_NEW_TYPONOMINAL_ADD_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_APPLICATION_NEW_TYPONOMINAL_ADD_DOCUMENT_LINK
-- begin MOBDEKBKP_OUTER_REJECTION_DOCUMENT_LINK
create table MOBDEKBKP_OUTER_REJECTION_DOCUMENT_LINK (
    OUTER_REJECTION_ID uuid,
    DOCUMENT_ID uuid,
    primary key (OUTER_REJECTION_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_OUTER_REJECTION_DOCUMENT_LINK
-- begin MOBDEKBKP_LIB_ATTRIBUTES_DOCUMENT_LINK
create table MOBDEKBKP_LIB_ATTRIBUTES_DOCUMENT_LINK (
    LIB_ATTRIBUTES_ID uuid,
    DOCUMENT_ID uuid,
    primary key (LIB_ATTRIBUTES_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_LIB_ATTRIBUTES_DOCUMENT_LINK
-- begin MOBDEKBKP_DEVICE_DOCUMENT_LINK
create table MOBDEKBKP_DEVICE_DOCUMENT_LINK (
    DEVICE_ID uuid,
    DOCUMENT_ID uuid,
    primary key (DEVICE_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_DEVICE_DOCUMENT_LINK
-- begin MOBDEKBKP_TYPE_DOCUMENT_LINK_DELIVERY
create table MOBDEKBKP_TYPE_DOCUMENT_LINK_DELIVERY (
    TYPE_ID uuid,
    DOCUMENT_ID uuid,
    primary key (TYPE_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_TYPE_DOCUMENT_LINK_DELIVERY
-- begin MOBDEKBKP_DEVICE_COMPLECT_DOCUMENT_LINK
create table MOBDEKBKP_DEVICE_COMPLECT_DOCUMENT_LINK (
    DEVICE_COMPLECT_ID uuid,
    DOCUMENT_ID uuid,
    primary key (DEVICE_COMPLECT_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_DEVICE_COMPLECT_DOCUMENT_LINK
-- begin MOBDEKBKP_DEVICE_PART_LIST_COMPLECT_DOCUMENT_LINK
create table MOBDEKBKP_DEVICE_PART_LIST_COMPLECT_DOCUMENT_LINK (
    DEVICE_PART_LIST_COMPLECT_ID uuid,
    DOCUMENT_ID uuid,
    primary key (DEVICE_PART_LIST_COMPLECT_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_DEVICE_PART_LIST_COMPLECT_DOCUMENT_LINK
-- begin MOBDEKBKP_TYPE_DOCUMENT_LINK_APPSCHEME
create table MOBDEKBKP_TYPE_DOCUMENT_LINK_APPSCHEME (
    TYPE_ID uuid,
    DOCUMENT_ID uuid,
    primary key (TYPE_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_TYPE_DOCUMENT_LINK_APPSCHEME
-- begin MOBDEKBKP_OUTER_LIST_ALLOWING_DOCUMENT_LINK
create table MOBDEKBKP_OUTER_LIST_ALLOWING_DOCUMENT_LINK (
    OUTER_LIST_ALLOWING_ID uuid,
    DOCUMENT_ID uuid,
    primary key (OUTER_LIST_ALLOWING_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_OUTER_LIST_ALLOWING_DOCUMENT_LINK
-- begin MOBDEKBKP_OUTER_CERTIFICATE_TESTS_DOCUMENT_LINK
create table MOBDEKBKP_OUTER_CERTIFICATE_TESTS_DOCUMENT_LINK (
    OUTER_CERTIFICATE_TESTS_ID uuid,
    DOCUMENT_ID uuid,
    primary key (OUTER_CERTIFICATE_TESTS_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_OUTER_CERTIFICATE_TESTS_DOCUMENT_LINK
-- begin MOBDEKBKP_DEVICE_DEVICE_FILTER_CONDITIONS_LINK
create table MOBDEKBKP_DEVICE_DEVICE_FILTER_CONDITIONS_LINK (
    DEVICE_FILTER_CONDITIONS_ID uuid,
    DEVICE_ID uuid,
    primary key (DEVICE_FILTER_CONDITIONS_ID, DEVICE_ID)
)^
-- end MOBDEKBKP_DEVICE_DEVICE_FILTER_CONDITIONS_LINK
-- begin MOBDEKBKP_DEVICE_PART_LIST_PLANNED_DOCUMENT_LINK
create table MOBDEKBKP_DEVICE_PART_LIST_PLANNED_DOCUMENT_LINK (
    DEVICE_PART_LIST_PLANNED_ID uuid,
    DOCUMENT_ID uuid,
    primary key (DEVICE_PART_LIST_PLANNED_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_DEVICE_PART_LIST_PLANNED_DOCUMENT_LINK
-- begin MOBDEKBKP_DEVICE_LIST_PROJECT_DOCUMENT_LINK
create table MOBDEKBKP_DEVICE_LIST_PROJECT_DOCUMENT_LINK (
    DEVICE_LIST_PROJECT_ID uuid,
    DOCUMENT_ID uuid,
    primary key (DEVICE_LIST_PROJECT_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_DEVICE_LIST_PROJECT_DOCUMENT_LINK
-- begin MOBDEKBKP_DEVICE_LIST_PROJECT_ADDITION_DOCUMENT_LINK
create table MOBDEKBKP_DEVICE_LIST_PROJECT_ADDITION_DOCUMENT_LINK (
    DEVICE_LIST_PROJECT_ADDITION_ID uuid,
    DOCUMENT_ID uuid,
    primary key (DEVICE_LIST_PROJECT_ADDITION_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_DEVICE_LIST_PROJECT_ADDITION_DOCUMENT_LINK
-- begin MOBDEKBKP_APPLICATION_COMMON_ENTRY_DOCUMENT_LINK
create table MOBDEKBKP_APPLICATION_COMMON_ENTRY_DOCUMENT_LINK (
    APPLICATION_COMMON_ENTRY_ID uuid,
    DOCUMENT_ID uuid,
    primary key (APPLICATION_COMMON_ENTRY_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_APPLICATION_COMMON_ENTRY_DOCUMENT_LINK
-- begin MOBDEKBKP_OUTER_FAIL_AND_REFUSES_DOCUMENT_LINK
create table MOBDEKBKP_OUTER_FAIL_AND_REFUSES_DOCUMENT_LINK (
    OUTER_FAIL_AND_REFUSES_ID uuid,
    DOCUMENT_ID uuid,
    primary key (OUTER_FAIL_AND_REFUSES_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_OUTER_FAIL_AND_REFUSES_DOCUMENT_LINK
-- begin MOBDEKBKP_APPLICATION_COMMON_DEV_DOCUMENT_LINK
create table MOBDEKBKP_APPLICATION_COMMON_DEV_DOCUMENT_LINK (
    APPLICATION_COMMON_DEV_ID uuid,
    DOCUMENT_ID uuid,
    primary key (APPLICATION_COMMON_DEV_ID, DOCUMENT_ID)
)^
-- end MOBDEKBKP_APPLICATION_COMMON_DEV_DOCUMENT_LINK
-- begin MOBDEKBKP_PARAMETER_CATEGORY
create table MOBDEKBKP_PARAMETER_CATEGORY (
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
    --
    primary key (ID)
)^
-- end MOBDEKBKP_PARAMETER_CATEGORY
-- begin MOBDEKBKP_TYPONOMINAL_QUALITY_LEVEL_IMPORT_SETTINGS
create table MOBDEKBKP_TYPONOMINAL_QUALITY_LEVEL_IMPORT_SETTINGS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    IMPORT_CLASS_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_TYPONOMINAL_QUALITY_LEVEL_IMPORT_SETTINGS
-- begin MOBDEKBKP_TYPONOMINAL_Q_L_IMPORT_SET_TYPONOMINAL_Q_L_IMPORT
create table MOBDEKBKP_TYPONOMINAL_Q_L_IMPORT_SET_TYPONOMINAL_Q_L_IMPORT (
    TYPONOMINAL_QUALITY_LEVEL_IMPORT_SETTINGS_ID uuid,
    TYPONOMINAL_QUALITY_LEVEL_IMPORT_ID uuid,
    primary key (TYPONOMINAL_QUALITY_LEVEL_IMPORT_SETTINGS_ID, TYPONOMINAL_QUALITY_LEVEL_IMPORT_ID)
)^
-- end MOBDEKBKP_TYPONOMINAL_Q_L_IMPORT_SET_TYPONOMINAL_Q_L_IMPORT
-- begin MOBDEKBKP_STR_LIB
create table MOBDEKBKP_STR_LIB (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TEXT varchar(255),
    --
    primary key (ID)
)^
-- end MOBDEKBKP_STR_LIB
-- begin MOBDEKBKP_STR_REC
create table MOBDEKBKP_STR_REC (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TEXT varchar(255),
    --
    primary key (ID)
)^
-- end MOBDEKBKP_STR_REC
-- begin MOBDEKBKP_STR_LIB_SETTINGS
create table MOBDEKBKP_STR_LIB_SETTINGS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPE_CLASS_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_STR_LIB_SETTINGS
-- begin MOBDEKBKP_STR_LIB_SETTINGS_STR_LIB_LINK
create table MOBDEKBKP_STR_LIB_SETTINGS_STR_LIB_LINK (
    STR_LIB_SETTINGS_ID uuid,
    STR_LIB_ID uuid,
    primary key (STR_LIB_SETTINGS_ID, STR_LIB_ID)
)^
-- end MOBDEKBKP_STR_LIB_SETTINGS_STR_LIB_LINK
-- begin MOBDEKBKP_STR_REC_SETTINGS
create table MOBDEKBKP_STR_REC_SETTINGS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPE_CLASS_ID uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_STR_REC_SETTINGS
-- begin MOBDEKBKP_STR_REC_SETTINGS_STR_REC_LINK
create table MOBDEKBKP_STR_REC_SETTINGS_STR_REC_LINK (
    STR_REC_SETTINGS_ID uuid,
    STR_REC_ID uuid,
    primary key (STR_REC_SETTINGS_ID, STR_REC_ID)
)^
-- end MOBDEKBKP_STR_REC_SETTINGS_STR_REC_LINK
-- begin MOBDEKBKP_MAPING_ENTITY
create table MOBDEKBKP_MAPING_ENTITY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    IDTC text,
    IDCUBA uuid,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_MAPING_ENTITY
-- begin MOBDEKBKP_COMPANY_NEED_APPLICATION
create table MOBDEKBKP_COMPANY_NEED_APPLICATION (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER_ integer,
    REQUIREMENT text,
    --
    primary key (ID)
)^
-- end MOBDEKBKP_COMPANY_NEED_APPLICATION
