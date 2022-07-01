--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.1
-- Dumped by pg_dump version 10.10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: newid(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.newid() RETURNS uuid
    LANGUAGE sql
    AS $$select md5(random()::text || clock_timestamp()::text)::uuid$$;


ALTER FUNCTION public.newid() OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: Manufacturers_technical; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Manufacturers_technical" (
    "Name" text,
    "Country" text,
    "General_director" text,
    "Shotr_name" text,
    "Phone_number" text,
    "Fax" text,
    "Email" text,
    "Legal_address" text,
    "Actual_address" text,
    "Official_site" text,
    "Ogrn" text,
    "Kpp" text,
    "Okato" text,
    inn text,
    "Okpo" text,
    "Number" text,
    "Issue_date" text,
    "Expiration_date" text,
    "Issued_by" text,
    "Comment" text
);


ALTER TABLE public."Manufacturers_technical" OWNER TO postgres;

--
-- Name: act_evt_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_evt_log (
    log_nr_ integer NOT NULL,
    type_ character varying(64),
    proc_def_id_ character varying(64),
    proc_inst_id_ character varying(64),
    execution_id_ character varying(64),
    task_id_ character varying(64),
    time_stamp_ timestamp without time zone NOT NULL,
    user_id_ character varying(255),
    data_ bytea,
    lock_owner_ character varying(255),
    lock_time_ timestamp without time zone,
    is_processed_ smallint DEFAULT 0
);


ALTER TABLE public.act_evt_log OWNER TO postgres;

--
-- Name: act_evt_log_log_nr__seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.act_evt_log_log_nr__seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.act_evt_log_log_nr__seq OWNER TO postgres;

--
-- Name: act_evt_log_log_nr__seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.act_evt_log_log_nr__seq OWNED BY public.act_evt_log.log_nr_;


--
-- Name: act_ge_bytearray; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_ge_bytearray (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    name_ character varying(255),
    deployment_id_ character varying(64),
    bytes_ bytea,
    generated_ boolean
);


ALTER TABLE public.act_ge_bytearray OWNER TO postgres;

--
-- Name: act_ge_property; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_ge_property (
    name_ character varying(64) NOT NULL,
    value_ character varying(300),
    rev_ integer
);


ALTER TABLE public.act_ge_property OWNER TO postgres;

--
-- Name: act_hi_actinst; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_hi_actinst (
    id_ character varying(64) NOT NULL,
    proc_def_id_ character varying(64) NOT NULL,
    proc_inst_id_ character varying(64) NOT NULL,
    execution_id_ character varying(64) NOT NULL,
    act_id_ character varying(255) NOT NULL,
    task_id_ character varying(64),
    call_proc_inst_id_ character varying(64),
    act_name_ character varying(255),
    act_type_ character varying(255) NOT NULL,
    assignee_ character varying(255),
    start_time_ timestamp without time zone NOT NULL,
    end_time_ timestamp without time zone,
    duration_ bigint,
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE public.act_hi_actinst OWNER TO postgres;

--
-- Name: act_hi_attachment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_hi_attachment (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    user_id_ character varying(255),
    name_ character varying(255),
    description_ character varying(4000),
    type_ character varying(255),
    task_id_ character varying(64),
    proc_inst_id_ character varying(64),
    url_ character varying(4000),
    content_id_ character varying(64),
    time_ timestamp without time zone
);


ALTER TABLE public.act_hi_attachment OWNER TO postgres;

--
-- Name: act_hi_comment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_hi_comment (
    id_ character varying(64) NOT NULL,
    type_ character varying(255),
    time_ timestamp without time zone NOT NULL,
    user_id_ character varying(255),
    task_id_ character varying(64),
    proc_inst_id_ character varying(64),
    action_ character varying(255),
    message_ character varying(4000),
    full_msg_ bytea
);


ALTER TABLE public.act_hi_comment OWNER TO postgres;

--
-- Name: act_hi_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_hi_detail (
    id_ character varying(64) NOT NULL,
    type_ character varying(255) NOT NULL,
    proc_inst_id_ character varying(64),
    execution_id_ character varying(64),
    task_id_ character varying(64),
    act_inst_id_ character varying(64),
    name_ character varying(255) NOT NULL,
    var_type_ character varying(64),
    rev_ integer,
    time_ timestamp without time zone NOT NULL,
    bytearray_id_ character varying(64),
    double_ double precision,
    long_ bigint,
    text_ character varying(4000),
    text2_ character varying(4000)
);


ALTER TABLE public.act_hi_detail OWNER TO postgres;

--
-- Name: act_hi_identitylink; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_hi_identitylink (
    id_ character varying(64) NOT NULL,
    group_id_ character varying(255),
    type_ character varying(255),
    user_id_ character varying(255),
    task_id_ character varying(64),
    proc_inst_id_ character varying(64)
);


ALTER TABLE public.act_hi_identitylink OWNER TO postgres;

--
-- Name: act_hi_procinst; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_hi_procinst (
    id_ character varying(64) NOT NULL,
    proc_inst_id_ character varying(64) NOT NULL,
    business_key_ character varying(255),
    proc_def_id_ character varying(64) NOT NULL,
    start_time_ timestamp without time zone NOT NULL,
    end_time_ timestamp without time zone,
    duration_ bigint,
    start_user_id_ character varying(255),
    start_act_id_ character varying(255),
    end_act_id_ character varying(255),
    super_process_instance_id_ character varying(64),
    delete_reason_ character varying(4000),
    tenant_id_ character varying(255) DEFAULT ''::character varying,
    name_ character varying(255)
);


ALTER TABLE public.act_hi_procinst OWNER TO postgres;

--
-- Name: act_hi_taskinst; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_hi_taskinst (
    id_ character varying(64) NOT NULL,
    proc_def_id_ character varying(64),
    task_def_key_ character varying(255),
    proc_inst_id_ character varying(64),
    execution_id_ character varying(64),
    name_ character varying(255),
    parent_task_id_ character varying(64),
    description_ character varying(4000),
    owner_ character varying(255),
    assignee_ character varying(255),
    start_time_ timestamp without time zone NOT NULL,
    claim_time_ timestamp without time zone,
    end_time_ timestamp without time zone,
    duration_ bigint,
    delete_reason_ character varying(4000),
    priority_ integer,
    due_date_ timestamp without time zone,
    form_key_ character varying(255),
    category_ character varying(255),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE public.act_hi_taskinst OWNER TO postgres;

--
-- Name: act_hi_varinst; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_hi_varinst (
    id_ character varying(64) NOT NULL,
    proc_inst_id_ character varying(64),
    execution_id_ character varying(64),
    task_id_ character varying(64),
    name_ character varying(255) NOT NULL,
    var_type_ character varying(100),
    rev_ integer,
    bytearray_id_ character varying(64),
    double_ double precision,
    long_ bigint,
    text_ character varying(4000),
    text2_ character varying(4000),
    create_time_ timestamp without time zone,
    last_updated_time_ timestamp without time zone
);


ALTER TABLE public.act_hi_varinst OWNER TO postgres;

--
-- Name: act_id_group; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_id_group (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    name_ character varying(255),
    type_ character varying(255)
);


ALTER TABLE public.act_id_group OWNER TO postgres;

--
-- Name: act_id_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_id_info (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    user_id_ character varying(64),
    type_ character varying(64),
    key_ character varying(255),
    value_ character varying(255),
    password_ bytea,
    parent_id_ character varying(255)
);


ALTER TABLE public.act_id_info OWNER TO postgres;

--
-- Name: act_id_membership; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_id_membership (
    user_id_ character varying(64) NOT NULL,
    group_id_ character varying(64) NOT NULL
);


ALTER TABLE public.act_id_membership OWNER TO postgres;

--
-- Name: act_id_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_id_user (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    first_ character varying(255),
    last_ character varying(255),
    email_ character varying(255),
    pwd_ character varying(255),
    picture_id_ character varying(64)
);


ALTER TABLE public.act_id_user OWNER TO postgres;

--
-- Name: act_procdef_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_procdef_info (
    id_ character varying(64) NOT NULL,
    proc_def_id_ character varying(64) NOT NULL,
    rev_ integer,
    info_json_id_ character varying(64)
);


ALTER TABLE public.act_procdef_info OWNER TO postgres;

--
-- Name: act_re_deployment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_re_deployment (
    id_ character varying(64) NOT NULL,
    name_ character varying(255),
    category_ character varying(255),
    tenant_id_ character varying(255) DEFAULT ''::character varying,
    deploy_time_ timestamp without time zone
);


ALTER TABLE public.act_re_deployment OWNER TO postgres;

--
-- Name: act_re_model; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_re_model (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    name_ character varying(255),
    key_ character varying(255),
    category_ character varying(255),
    create_time_ timestamp without time zone,
    last_update_time_ timestamp without time zone,
    version_ integer,
    meta_info_ character varying(4000),
    deployment_id_ character varying(64),
    editor_source_value_id_ character varying(64),
    editor_source_extra_value_id_ character varying(64),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE public.act_re_model OWNER TO postgres;

--
-- Name: act_re_procdef; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_re_procdef (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    category_ character varying(255),
    name_ character varying(255),
    key_ character varying(255) NOT NULL,
    version_ integer NOT NULL,
    deployment_id_ character varying(64),
    resource_name_ character varying(4000),
    dgrm_resource_name_ character varying(4000),
    description_ character varying(4000),
    has_start_form_key_ boolean,
    has_graphical_notation_ boolean,
    suspension_state_ integer,
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE public.act_re_procdef OWNER TO postgres;

--
-- Name: act_ru_event_subscr; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_ru_event_subscr (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    event_type_ character varying(255) NOT NULL,
    event_name_ character varying(255),
    execution_id_ character varying(64),
    proc_inst_id_ character varying(64),
    activity_id_ character varying(64),
    configuration_ character varying(255),
    created_ timestamp without time zone NOT NULL,
    proc_def_id_ character varying(64),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE public.act_ru_event_subscr OWNER TO postgres;

--
-- Name: act_ru_execution; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_ru_execution (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    proc_inst_id_ character varying(64),
    business_key_ character varying(255),
    parent_id_ character varying(64),
    proc_def_id_ character varying(64),
    super_exec_ character varying(64),
    act_id_ character varying(255),
    is_active_ boolean,
    is_concurrent_ boolean,
    is_scope_ boolean,
    is_event_scope_ boolean,
    suspension_state_ integer,
    cached_ent_state_ integer,
    tenant_id_ character varying(255) DEFAULT ''::character varying,
    name_ character varying(255),
    lock_time_ timestamp without time zone
);


ALTER TABLE public.act_ru_execution OWNER TO postgres;

--
-- Name: act_ru_identitylink; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_ru_identitylink (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    group_id_ character varying(255),
    type_ character varying(255),
    user_id_ character varying(255),
    task_id_ character varying(64),
    proc_inst_id_ character varying(64),
    proc_def_id_ character varying(64)
);


ALTER TABLE public.act_ru_identitylink OWNER TO postgres;

--
-- Name: act_ru_job; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_ru_job (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    type_ character varying(255) NOT NULL,
    lock_exp_time_ timestamp without time zone,
    lock_owner_ character varying(255),
    exclusive_ boolean,
    execution_id_ character varying(64),
    process_instance_id_ character varying(64),
    proc_def_id_ character varying(64),
    retries_ integer,
    exception_stack_id_ character varying(64),
    exception_msg_ character varying(4000),
    duedate_ timestamp without time zone,
    repeat_ character varying(255),
    handler_type_ character varying(255),
    handler_cfg_ character varying(4000),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE public.act_ru_job OWNER TO postgres;

--
-- Name: act_ru_task; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_ru_task (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    execution_id_ character varying(64),
    proc_inst_id_ character varying(64),
    proc_def_id_ character varying(64),
    name_ character varying(255),
    parent_task_id_ character varying(64),
    description_ character varying(4000),
    task_def_key_ character varying(255),
    owner_ character varying(255),
    assignee_ character varying(255),
    delegation_ character varying(64),
    priority_ integer,
    create_time_ timestamp without time zone,
    due_date_ timestamp without time zone,
    category_ character varying(255),
    suspension_state_ integer,
    tenant_id_ character varying(255) DEFAULT ''::character varying,
    form_key_ character varying(255)
);


ALTER TABLE public.act_ru_task OWNER TO postgres;

--
-- Name: act_ru_variable; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.act_ru_variable (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    type_ character varying(255) NOT NULL,
    name_ character varying(255) NOT NULL,
    execution_id_ character varying(64),
    proc_inst_id_ character varying(64),
    task_id_ character varying(64),
    bytearray_id_ character varying(64),
    double_ double precision,
    long_ bigint,
    text_ character varying(4000),
    text2_ character varying(4000)
);


ALTER TABLE public.act_ru_variable OWNER TO postgres;

--
-- Name: bpm_proc_actor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bpm_proc_actor (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    user_id uuid,
    proc_instance_id uuid NOT NULL,
    proc_role_id uuid NOT NULL,
    order_ integer
);


ALTER TABLE public.bpm_proc_actor OWNER TO postgres;

--
-- Name: bpm_proc_attachment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bpm_proc_attachment (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    file_id uuid,
    type_id uuid,
    comment_ text,
    proc_instance_id uuid,
    proc_task_id uuid,
    author_id uuid
);


ALTER TABLE public.bpm_proc_attachment OWNER TO postgres;

--
-- Name: bpm_proc_attachment_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bpm_proc_attachment_type (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    code character varying(255)
);


ALTER TABLE public.bpm_proc_attachment_type OWNER TO postgres;

--
-- Name: bpm_proc_definition; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bpm_proc_definition (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255),
    code character varying(255),
    act_id character varying(255),
    active boolean,
    model_id uuid,
    deployment_date timestamp without time zone
);


ALTER TABLE public.bpm_proc_definition OWNER TO postgres;

--
-- Name: bpm_proc_instance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bpm_proc_instance (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    entity_name character varying(255),
    entity_id uuid,
    string_entity_id character varying(255),
    int_entity_id integer,
    long_entity_id bigint,
    active boolean,
    cancelled boolean,
    act_process_instance_id character varying(255),
    start_date timestamp without time zone,
    end_date timestamp without time zone,
    proc_definition_id uuid NOT NULL,
    started_by_id uuid,
    start_comment text,
    cancel_comment text,
    entity_editor_name character varying(255),
    description text
);


ALTER TABLE public.bpm_proc_instance OWNER TO postgres;

--
-- Name: bpm_proc_model; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bpm_proc_model (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    act_model_id character varying(255),
    description text
);


ALTER TABLE public.bpm_proc_model OWNER TO postgres;

--
-- Name: bpm_proc_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bpm_proc_role (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    code character varying(255) NOT NULL,
    proc_definition_id uuid NOT NULL,
    order_ integer
);


ALTER TABLE public.bpm_proc_role OWNER TO postgres;

--
-- Name: bpm_proc_task; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bpm_proc_task (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    proc_instance_id uuid NOT NULL,
    start_date timestamp without time zone,
    end_date timestamp without time zone,
    outcome character varying(255),
    proc_actor_id uuid,
    act_execution_id character varying(255) NOT NULL,
    name character varying(255),
    act_task_id character varying(255),
    comment_ text,
    cancelled boolean,
    claim_date timestamp without time zone,
    act_process_definition_id character varying(255),
    act_task_definition_key character varying(255)
);


ALTER TABLE public.bpm_proc_task OWNER TO postgres;

--
-- Name: bpm_proc_task_user_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bpm_proc_task_user_link (
    proc_task_id uuid NOT NULL,
    user_id uuid NOT NULL
);


ALTER TABLE public.bpm_proc_task_user_link OWNER TO postgres;

--
-- Name: bpm_stencil_set; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bpm_stencil_set (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    json_data text NOT NULL
);


ALTER TABLE public.bpm_stencil_set OWNER TO postgres;

--
-- Name: cubaat_ssh_credentials; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cubaat_ssh_credentials (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    hostname character varying(255) NOT NULL,
    session_name character varying(255) NOT NULL,
    is_for_everyone boolean,
    port integer NOT NULL,
    login character varying(255) NOT NULL,
    private_key_id uuid
);


ALTER TABLE public.cubaat_ssh_credentials OWNER TO postgres;

--
-- Name: ddcrd_diagnose_execution_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ddcrd_diagnose_execution_log (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    execution_successful boolean NOT NULL,
    execution_timestamp timestamp without time zone NOT NULL,
    execution_user character varying(255),
    execution_result_file_id uuid,
    diagnose_type character varying(255) NOT NULL,
    execution_type character varying(255) NOT NULL
);


ALTER TABLE public.ddcrd_diagnose_execution_log OWNER TO postgres;

--
-- Name: discuss_comment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.discuss_comment (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    parent_id uuid,
    has_answer boolean,
    contents text,
    moderated_content text,
    moderated boolean,
    author_id uuid NOT NULL,
    entity uuid,
    entity_name character varying(255),
    comment_status character varying(50)
);


ALTER TABLE public.discuss_comment OWNER TO postgres;

--
-- Name: discuss_comment_preference; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.discuss_comment_preference (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    moderation_type character varying(50)
);


ALTER TABLE public.discuss_comment_preference OWNER TO postgres;

--
-- Name: documents_document; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.documents_document (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    designation text,
    document_type_id uuid
);


ALTER TABLE public.documents_document OWNER TO postgres;

--
-- Name: documents_document_file_descriptor_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.documents_document_file_descriptor_link (
    document_id uuid NOT NULL,
    file_descriptor_id uuid NOT NULL
);


ALTER TABLE public.documents_document_file_descriptor_link OWNER TO postgres;

--
-- Name: documents_document_restriction; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.documents_document_restriction (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    key_ character varying(255) NOT NULL
);


ALTER TABLE public.documents_document_restriction OWNER TO postgres;

--
-- Name: documents_document_restriction_document_type_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.documents_document_restriction_document_type_link (
    document_restriction_id uuid NOT NULL,
    document_type_id uuid NOT NULL
);


ALTER TABLE public.documents_document_restriction_document_type_link OWNER TO postgres;

--
-- Name: documents_document_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.documents_document_type (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    parent_id uuid
);


ALTER TABLE public.documents_document_type OWNER TO postgres;

--
-- Name: mobdekbkp_advanced_setting; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_advanced_setting (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    mass character varying(50),
    number_prp character varying(100),
    technical_specification_name character varying(100),
    technical_specification_value character varying(100),
    working_life integer,
    shelf_life integer,
    tightness character varying(10),
    technology character varying(50),
    import_device_id uuid,
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_advanced_setting OWNER TO postgres;

--
-- Name: mobdekbkp_alter_class_gost2710; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_alter_class_gost2710 (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    type_group character varying(255) NOT NULL,
    type_samples character varying(255),
    code character varying(255) NOT NULL,
    parent_id uuid
);


ALTER TABLE public.mobdekbkp_alter_class_gost2710 OWNER TO postgres;

--
-- Name: mobdekbkp_alter_class_gost56649; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_alter_class_gost56649 (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    type_samples character varying(255),
    code character varying(255)
);


ALTER TABLE public.mobdekbkp_alter_class_gost56649 OWNER TO postgres;

--
-- Name: mobdekbkp_alter_class_mil; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_alter_class_mil (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255),
    short_name character varying(255) NOT NULL,
    parent_id uuid
);


ALTER TABLE public.mobdekbkp_alter_class_mil OWNER TO postgres;

--
-- Name: mobdekbkp_alter_class_reliability; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_alter_class_reliability (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    parent_id uuid,
    type_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_alter_class_reliability OWNER TO postgres;

--
-- Name: mobdekbkp_applicability_devices; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_applicability_devices (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    equipment character varying(100),
    company_developer_hardware character varying(100),
    product_rkt character varying(100),
    prime_developer character varying(100),
    approval_year character varying(10),
    typonominal_id uuid,
    import_device_id uuid
);


ALTER TABLE public.mobdekbkp_applicability_devices OWNER TO postgres;

--
-- Name: mobdekbkp_application_common_dev; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_application_common_dev (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255),
    status character varying(50) NOT NULL
);


ALTER TABLE public.mobdekbkp_application_common_dev OWNER TO postgres;

--
-- Name: mobdekbkp_application_common_dev_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_application_common_dev_document_link (
    application_common_dev_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_application_common_dev_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_application_common_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_application_common_entry (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    class_name character varying(255),
    events text,
    characteristics text,
    prototype text,
    possibility text,
    status character varying(50),
    application_common_dev_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_application_common_entry OWNER TO postgres;

--
-- Name: mobdekbkp_application_common_entry_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_application_common_entry_document_link (
    application_common_entry_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_application_common_entry_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_application_new_dev_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_application_new_dev_entry (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    application_id uuid NOT NULL,
    application_common_entry_id uuid
);


ALTER TABLE public.mobdekbkp_application_new_dev_entry OWNER TO postgres;

--
-- Name: mobdekbkp_application_new_typonominal_add; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_application_new_typonominal_add (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    designation text,
    status character varying(50),
    delivery_doc_designation text,
    class_mop character varying(255),
    producer character varying(255),
    description text
);


ALTER TABLE public.mobdekbkp_application_new_typonominal_add OWNER TO postgres;

--
-- Name: mobdekbkp_application_new_typonominal_add_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_application_new_typonominal_add_document_link (
    application_new_typonominal_add_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_application_new_typonominal_add_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_application_new_typonominal_dev; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_application_new_typonominal_dev (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    class_name character varying(255),
    event text,
    characteristics text,
    prototype text,
    possibility text,
    document_id uuid,
    status character varying(50) NOT NULL,
    common_application_id uuid
);


ALTER TABLE public.mobdekbkp_application_new_typonominal_dev OWNER TO postgres;

--
-- Name: mobdekbkp_application_okr_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_application_okr_info (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    responsible_id uuid NOT NULL,
    description text NOT NULL,
    date_start date NOT NULL,
    date_approx date,
    date_end date,
    condition_ character varying(50) NOT NULL,
    result_ character varying(255),
    application_common_entry_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_application_okr_info OWNER TO postgres;

--
-- Name: mobdekbkp_basic_information; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_basic_information (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    functionality character varying(255),
    level_quality character varying(50),
    in_the_list_0122 character varying(10),
    in_the_list_ekb_k character varying(10),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_basic_information OWNER TO postgres;

--
-- Name: mobdekbkp_basic_information_import; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_basic_information_import (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    functionality character varying(255),
    level_quality character varying(50),
    import_device_id uuid
);


ALTER TABLE public.mobdekbkp_basic_information_import OWNER TO postgres;

--
-- Name: mobdekbkp_brand_flux; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_brand_flux (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL
);


ALTER TABLE public.mobdekbkp_brand_flux OWNER TO postgres;

--
-- Name: mobdekbkp_brand_solder; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_brand_solder (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL
);


ALTER TABLE public.mobdekbkp_brand_solder OWNER TO postgres;

--
-- Name: mobdekbkp_cables_wires_cords; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_cables_wires_cords (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    test_voltage character varying(100),
    attenuation_coefficient character varying(100),
    overall_dimensions character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_cables_wires_cords OWNER TO postgres;

--
-- Name: mobdekbkp_capacitors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_capacitors (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    rated_voltage character varying(100),
    rated_capacity character varying(100),
    dimensions character varying(100),
    capacity_tolerance character varying(100),
    impact_shear_force character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_capacitors OWNER TO postgres;

--
-- Name: mobdekbkp_cathode_ray_tubes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_cathode_ray_tubes (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    screen_brightness character varying(100),
    resolution character varying(100),
    modulation_voltage character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_cathode_ray_tubes OWNER TO postgres;

--
-- Name: mobdekbkp_company; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_company (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    name_short character varying(255),
    address_fact text NOT NULL,
    address_legal text NOT NULL,
    ogrn character varying(255),
    kpp character varying(255),
    inn character varying(255),
    okpo character varying(255),
    phone character varying(255) NOT NULL,
    fax character varying(255),
    email character varying(255),
    website character varying(255),
    country_id uuid NOT NULL,
    rating double precision,
    logo_id uuid,
    okato character varying(255),
    gen_dir character varying(255)
);


ALTER TABLE public.mobdekbkp_company OWNER TO postgres;

--
-- Name: mobdekbkp_company_license; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_company_license (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    number_ character varying(255) NOT NULL,
    date_obtained date,
    date_expire date,
    type_id uuid NOT NULL,
    company_id uuid NOT NULL,
    document_id uuid
);


ALTER TABLE public.mobdekbkp_company_license OWNER TO postgres;

--
-- Name: mobdekbkp_company_need; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_company_need (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    typonominal_id uuid NOT NULL,
    amount integer NOT NULL,
    wanted_deliver_date date NOT NULL,
    company_id uuid NOT NULL,
    status integer,
    company_need_application_id uuid,
    requirements text
);


ALTER TABLE public.mobdekbkp_company_need OWNER TO postgres;

--
-- Name: mobdekbkp_company_need_application; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_company_need_application (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    number_ integer,
    requirement text
);


ALTER TABLE public.mobdekbkp_company_need_application OWNER TO postgres;

--
-- Name: mobdekbkp_company_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_company_type (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name text NOT NULL
);


ALTER TABLE public.mobdekbkp_company_type OWNER TO postgres;

--
-- Name: mobdekbkp_company_type_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_company_type_entry (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    type_id uuid NOT NULL,
    company_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_company_type_entry OWNER TO postgres;

--
-- Name: mobdekbkp_corpus; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_corpus (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    corpus_type character varying(100),
    corpus_material character varying(50),
    cover_cover character varying(100),
    contact_type character varying(100),
    contact_cover character varying(50),
    designation_output_forming_option character varying(100),
    import_device_id uuid,
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_corpus OWNER TO postgres;

--
-- Name: mobdekbkp_cost; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_cost (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    value_ double precision NOT NULL,
    currency_id uuid NOT NULL,
    setup_date date NOT NULL
);


ALTER TABLE public.mobdekbkp_cost OWNER TO postgres;

--
-- Name: mobdekbkp_country; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_country (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    country_type character varying(50) NOT NULL
);


ALTER TABLE public.mobdekbkp_country OWNER TO postgres;

--
-- Name: mobdekbkp_currency; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_currency (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    name_short character varying(255) NOT NULL
);


ALTER TABLE public.mobdekbkp_currency OWNER TO postgres;

--
-- Name: mobdekbkp_current_sources; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_current_sources (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    frequency_range character varying(100),
    nominal_voltage character varying(100),
    rated_capacity character varying(100),
    overall_dimensions character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_current_sources OWNER TO postgres;

--
-- Name: mobdekbkp_custom_parameters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_custom_parameters (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(100),
    param_value character varying(100),
    typonominal_id uuid,
    import_device_id uuid
);


ALTER TABLE public.mobdekbkp_custom_parameters OWNER TO postgres;

--
-- Name: mobdekbkp_device; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    designation character varying(255) NOT NULL,
    developer_id uuid NOT NULL,
    general_constructor character varying(255),
    demands text,
    device_project_list_id uuid,
    is_approved boolean NOT NULL
);


ALTER TABLE public.mobdekbkp_device OWNER TO postgres;

--
-- Name: mobdekbkp_device_complect; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_complect (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    complect_number character varying(255) NOT NULL,
    progress double precision,
    device_id uuid,
    status character varying(50) NOT NULL
);


ALTER TABLE public.mobdekbkp_device_complect OWNER TO postgres;

--
-- Name: mobdekbkp_device_complect_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_complect_document_link (
    device_complect_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_device_complect_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_device_complect_list; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_complect_list (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255),
    status character varying(50)
);


ALTER TABLE public.mobdekbkp_device_complect_list OWNER TO postgres;

--
-- Name: mobdekbkp_device_device_filter_conditions_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_device_filter_conditions_link (
    device_filter_conditions_id uuid NOT NULL,
    device_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_device_device_filter_conditions_link OWNER TO postgres;

--
-- Name: mobdekbkp_device_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_document_link (
    device_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_device_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_device_filter_conditions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_filter_conditions (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    path text,
    attribute character varying(255),
    value_type character varying(50),
    compare_operator integer NOT NULL,
    val_float double precision,
    val_string character varying(255),
    val_boolean boolean
);


ALTER TABLE public.mobdekbkp_device_filter_conditions OWNER TO postgres;

--
-- Name: mobdekbkp_device_list_project; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_list_project (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255),
    approval_date timestamp without time zone,
    status character varying(50),
    demands text
);


ALTER TABLE public.mobdekbkp_device_list_project OWNER TO postgres;

--
-- Name: mobdekbkp_device_list_project_addition; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_list_project_addition (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255),
    status character varying(50),
    device_list_project_id uuid
);


ALTER TABLE public.mobdekbkp_device_list_project_addition OWNER TO postgres;

--
-- Name: mobdekbkp_device_list_project_addition_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_list_project_addition_document_link (
    device_list_project_addition_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_device_list_project_addition_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_device_list_project_addition_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_list_project_addition_entry (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    edited_id uuid,
    newtyponominal_id uuid,
    addition_type character varying(50),
    device_list_project_addition_id uuid,
    status character varying(50)
);


ALTER TABLE public.mobdekbkp_device_list_project_addition_entry OWNER TO postgres;

--
-- Name: mobdekbkp_device_list_project_application; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_list_project_application (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    suggested_id uuid NOT NULL,
    rationale text NOT NULL
);


ALTER TABLE public.mobdekbkp_device_list_project_application OWNER TO postgres;

--
-- Name: mobdekbkp_device_list_project_application_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_list_project_application_document_link (
    device_list_project_application_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_device_list_project_application_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_device_list_project_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_list_project_document_link (
    device_list_project_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_device_list_project_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_device_list_project_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_list_project_entry (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    typonominal_id uuid,
    status character varying(50),
    device_list_project_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_device_list_project_entry OWNER TO postgres;

--
-- Name: mobdekbkp_device_part; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_part (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    designation character varying(255) NOT NULL,
    type_ character varying(255) NOT NULL,
    developer_id uuid NOT NULL,
    constructor character varying(255),
    demands text
);


ALTER TABLE public.mobdekbkp_device_part OWNER TO postgres;

--
-- Name: mobdekbkp_device_part_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_part_document_link (
    device_part_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_device_part_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_device_part_list_complect; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_part_list_complect (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255),
    status character varying(50),
    device_complect_id uuid NOT NULL,
    device_part_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_device_part_list_complect OWNER TO postgres;

--
-- Name: mobdekbkp_device_part_list_complect_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_part_list_complect_document_link (
    device_part_list_complect_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_device_part_list_complect_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_device_part_list_complect_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_part_list_complect_entry (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    typonominal_id uuid NOT NULL,
    status character varying(50) NOT NULL,
    amount_in_device integer,
    amount_total integer NOT NULL,
    amount_delivered integer,
    date_planned date,
    date_fact date,
    questions text,
    by_head_executor character varying(50) NOT NULL,
    device_part_list_complect_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_device_part_list_complect_entry OWNER TO postgres;

--
-- Name: mobdekbkp_device_part_list_planned; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_part_list_planned (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    status character varying(50),
    device_part_id uuid,
    device_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_device_part_list_planned OWNER TO postgres;

--
-- Name: mobdekbkp_device_part_list_planned_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_part_list_planned_document_link (
    device_part_list_planned_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_device_part_list_planned_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_device_part_list_planned_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_part_list_planned_entry (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    typonominal_id uuid NOT NULL,
    status character varying(50),
    device_part_list_planned_inverse_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_device_part_list_planned_entry OWNER TO postgres;

--
-- Name: mobdekbkp_device_parts_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_device_parts_entry (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    amount integer NOT NULL,
    part_id uuid NOT NULL,
    device_id uuid NOT NULL,
    in_device_part_comlect_id uuid
);


ALTER TABLE public.mobdekbkp_device_parts_entry OWNER TO postgres;

--
-- Name: mobdekbkp_digital_chips; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_digital_chips (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    supply_voltage character varying(100),
    consumption_current character varying(100),
    memory_capacity character varying(100),
    technology character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_digital_chips OWNER TO postgres;

--
-- Name: mobdekbkp_electric_light_sources; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_electric_light_sources (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    voltage character varying(100),
    electric_current character varying(100),
    luminous_flux character varying(100),
    minimum_operating_time character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_electric_light_sources OWNER TO postgres;

--
-- Name: mobdekbkp_electric_vacuum_lamps; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_electric_vacuum_lamps (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    output_power character varying(100),
    working_frequency character varying(100),
    power_dissipated_anode character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_electric_vacuum_lamps OWNER TO postgres;

--
-- Name: mobdekbkp_electrical_connectors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_electrical_connectors (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    frequency_range character varying(100),
    attached_cable_brand character varying(100),
    structural_performance character varying(100),
    path_type character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_electrical_connectors OWNER TO postgres;

--
-- Name: mobdekbkp_factory; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_factory (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    producer character varying(100),
    import_device_id uuid,
    producing_country character varying(50),
    certification_cmk_organization character varying(10),
    calculator_holder character varying(100),
    provider character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_factory OWNER TO postgres;

--
-- Name: mobdekbkp_fiber_optic_components; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_fiber_optic_components (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    diameter_pitch_nut character varying(100),
    inserted_optical_loss character varying(100),
    number_articulations_dismemberments character varying(100),
    number_optical_poles character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_fiber_optic_components OWNER TO postgres;

--
-- Name: mobdekbkp_functional_devices; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_functional_devices (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    iput_voltage character varying(100),
    output_voltage character varying(100),
    output_current_channel character varying(100),
    power_ character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_functional_devices OWNER TO postgres;

--
-- Name: mobdekbkp_glue_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_glue_type (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL
);


ALTER TABLE public.mobdekbkp_glue_type OWNER TO postgres;

--
-- Name: mobdekbkp_handbook; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_handbook (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255),
    published boolean
);


ALTER TABLE public.mobdekbkp_handbook OWNER TO postgres;

--
-- Name: mobdekbkp_handbook_cad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_handbook_cad (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    handbook_id uuid
);


ALTER TABLE public.mobdekbkp_handbook_cad OWNER TO postgres;

--
-- Name: mobdekbkp_handbook_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_handbook_entry (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    key_ character varying(255),
    value_ text,
    parent_id uuid,
    handbook_id uuid
);


ALTER TABLE public.mobdekbkp_handbook_entry OWNER TO postgres;

--
-- Name: mobdekbkp_import_class; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_import_class (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(250),
    number_ integer
);


ALTER TABLE public.mobdekbkp_import_class OWNER TO postgres;

--
-- Name: mobdekbkp_import_device; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_import_device (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(250),
    import_class_id uuid
);


ALTER TABLE public.mobdekbkp_import_device OWNER TO postgres;

--
-- Name: mobdekbkp_import_docs_schemes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_import_docs_schemes (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    itm character varying(100),
    import_device_id uuid
);


ALTER TABLE public.mobdekbkp_import_docs_schemes OWNER TO postgres;

--
-- Name: mobdekbkp_install_method; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_install_method (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL
);


ALTER TABLE public.mobdekbkp_install_method OWNER TO postgres;

--
-- Name: mobdekbkp_integrated_circuits; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_integrated_circuits (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    supply_voltage character varying(100),
    consumption_current character varying(100),
    technology character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_integrated_circuits OWNER TO postgres;

--
-- Name: mobdekbkp_lib_attributes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_lib_attributes (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    type_ character varying(255) NOT NULL,
    series character varying(255),
    view_model_id uuid,
    view_name character varying(255),
    alter_view character varying(255),
    position_prefix character varying(255),
    key_attribute character varying(255),
    range_definition character varying(255),
    land_model_id uuid,
    land_name character varying(255),
    alter_land character varying(255),
    ide_attrib_id uuid
);


ALTER TABLE public.mobdekbkp_lib_attributes OWNER TO postgres;

--
-- Name: mobdekbkp_lib_attributes_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_lib_attributes_document_link (
    lib_attributes_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_lib_attributes_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_license_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_license_type (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name text NOT NULL
);


ALTER TABLE public.mobdekbkp_license_type OWNER TO postgres;

--
-- Name: mobdekbkp_log_file_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_log_file_data (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    file_name character varying(255),
    product character varying(255),
    name1_or_type character varying(255),
    name2 character varying(255),
    name3 character varying(255)
);


ALTER TABLE public.mobdekbkp_log_file_data OWNER TO postgres;

--
-- Name: mobdekbkp_main_parameters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_main_parameters (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    t_plus integer,
    t_minus integer,
    import_device_id uuid,
    radiation_resistance character varying(100),
    distinctive_sign character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_main_parameters OWNER TO postgres;

--
-- Name: mobdekbkp_maping_entity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_maping_entity (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    idtc text,
    idcuba uuid
);


ALTER TABLE public.mobdekbkp_maping_entity OWNER TO postgres;

--
-- Name: mobdekbkp_material; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_material (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    type_ character varying(50) NOT NULL
);


ALTER TABLE public.mobdekbkp_material OWNER TO postgres;

--
-- Name: mobdekbkp_microassemblies_multicrystals; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_microassemblies_multicrystals (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    switching_voltage character varying(100),
    switched_current character varying(100),
    technology character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_microassemblies_multicrystals OWNER TO postgres;

--
-- Name: mobdekbkp_montage_parameters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_montage_parameters (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    max_temperature_wp integer,
    max_soldering_time_output integer,
    max_case_temperature_hand integer,
    min_distance_from_housing_to_soldering integer,
    flux character varying(100),
    solder character varying(100),
    max_pre_heating_temperature integer,
    max_preheating_time integer,
    max_temperature_soldering integer,
    max_soldering_time integer,
    max_case_temperature_auto integer,
    thermal_profile character varying(100),
    soldering_paste_flux character varying(100),
    method_ character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_montage_parameters OWNER TO postgres;

--
-- Name: mobdekbkp_operation_conditions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_operation_conditions (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    text text
);


ALTER TABLE public.mobdekbkp_operation_conditions OWNER TO postgres;

--
-- Name: mobdekbkp_outer_certificate_tests; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_certificate_tests (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    typonominal_id uuid NOT NULL,
    index_ character varying(255),
    description text,
    date_start date,
    date_end date,
    result_ text
);


ALTER TABLE public.mobdekbkp_outer_certificate_tests OWNER TO postgres;

--
-- Name: mobdekbkp_outer_certificate_tests_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_certificate_tests_document_link (
    outer_certificate_tests_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_outer_certificate_tests_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_outer_db_fail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_db_fail (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    typonominal_id uuid NOT NULL,
    index_ character varying(255) NOT NULL,
    maufacture_date date,
    fail_date date,
    work_fact double precision,
    work_guarantee double precision,
    claim_docs text,
    manufacturer_id uuid,
    claimed_company_id uuid,
    fail_type text,
    fail_type_comment text,
    repeating character varying(255),
    part character varying(255),
    visible_fail text,
    description text,
    comission_resume text,
    previous_resume text,
    source_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_outer_db_fail OWNER TO postgres;

--
-- Name: mobdekbkp_outer_db_refuse; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_db_refuse (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    typonominal_id uuid NOT NULL,
    produce_date character varying(255),
    checked integer,
    refused integer,
    failed integer,
    provider character varying(100),
    import_device_id uuid
);


ALTER TABLE public.mobdekbkp_outer_db_refuse OWNER TO postgres;

--
-- Name: mobdekbkp_outer_db_refuse_defects; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_db_refuse_defects (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL
);


ALTER TABLE public.mobdekbkp_outer_db_refuse_defects OWNER TO postgres;

--
-- Name: mobdekbkp_outer_db_refuse_tests; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_db_refuse_tests (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL
);


ALTER TABLE public.mobdekbkp_outer_db_refuse_tests OWNER TO postgres;

--
-- Name: mobdekbkp_outer_entrance_tests; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_entrance_tests (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    typonominal_id uuid NOT NULL,
    index_ character varying(255),
    description text NOT NULL,
    cert_center_id uuid NOT NULL,
    amount_checked integer,
    amount_passed integer,
    amount_failed integer,
    fail_description text,
    date_start date,
    date_end date,
    test_result text
);


ALTER TABLE public.mobdekbkp_outer_entrance_tests OWNER TO postgres;

--
-- Name: mobdekbkp_outer_entrance_tests_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_entrance_tests_document_link (
    outer_entrance_tests_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_outer_entrance_tests_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_outer_fail_and_refuses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_fail_and_refuses (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    typonominal_id uuid NOT NULL,
    release_date date NOT NULL,
    refuse_date date NOT NULL,
    before_refuse double precision NOT NULL,
    accepted_defect integer NOT NULL,
    deny_reasons character varying(255) NOT NULL,
    defect_class text NOT NULL,
    defect_repeat text NOT NULL,
    user_blame integer NOT NULL,
    prevent text NOT NULL,
    actions_date date NOT NULL,
    amount_at_provider integer NOT NULL
);


ALTER TABLE public.mobdekbkp_outer_fail_and_refuses OWNER TO postgres;

--
-- Name: mobdekbkp_outer_fail_and_refuses_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_fail_and_refuses_document_link (
    outer_fail_and_refuses_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_outer_fail_and_refuses_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_outer_information_source; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_information_source (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    database_name character varying(255) NOT NULL
);


ALTER TABLE public.mobdekbkp_outer_information_source OWNER TO postgres;

--
-- Name: mobdekbkp_outer_list_allowing; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_list_allowing (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    type_id uuid NOT NULL,
    number_ character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    start_date date NOT NULL,
    end_date date,
    substituting character varying(255),
    release_year character varying(255) NOT NULL,
    is_modification character varying(50) NOT NULL
);


ALTER TABLE public.mobdekbkp_outer_list_allowing OWNER TO postgres;

--
-- Name: mobdekbkp_outer_list_allowing_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_list_allowing_document_link (
    outer_list_allowing_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_outer_list_allowing_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_outer_list_allowing_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_list_allowing_entry (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    typonominal_id uuid NOT NULL,
    link_to_list_entry character varying(255),
    outer_list_allowing_id uuid
);


ALTER TABLE public.mobdekbkp_outer_list_allowing_entry OWNER TO postgres;

--
-- Name: mobdekbkp_outer_list_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_list_type (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    is_allowing character varying(50) NOT NULL
);


ALTER TABLE public.mobdekbkp_outer_list_type OWNER TO postgres;

--
-- Name: mobdekbkp_outer_persistence_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_persistence_info (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    tiponominal_id uuid,
    manufacturer_id uuid,
    info_source_id uuid,
    single_effect_data character varying(255),
    dose_effect_data character varying(255),
    has_files character varying(50),
    resistance_influence_tzc character varying(50),
    resistance_dose character varying(50),
    import_device_id uuid
);


ALTER TABLE public.mobdekbkp_outer_persistence_info OWNER TO postgres;

--
-- Name: mobdekbkp_outer_rejection; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_rejection (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    typonominal_id uuid NOT NULL,
    checked integer NOT NULL,
    rejected integer NOT NULL,
    reason text NOT NULL,
    comment_ text NOT NULL,
    import_device_id uuid
);


ALTER TABLE public.mobdekbkp_outer_rejection OWNER TO postgres;

--
-- Name: mobdekbkp_outer_rejection_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_outer_rejection_document_link (
    outer_rejection_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_outer_rejection_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_parameter; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_parameter (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    description text,
    unit_id uuid NOT NULL,
    default_value_type character varying(50) NOT NULL,
    param_type character varying(50) NOT NULL,
    parameter_category_id uuid
);


ALTER TABLE public.mobdekbkp_parameter OWNER TO postgres;

--
-- Name: mobdekbkp_parameter_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_parameter_category (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255)
);


ALTER TABLE public.mobdekbkp_parameter_category OWNER TO postgres;

--
-- Name: mobdekbkp_parameter_value; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_parameter_value (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    current_value_type character varying(50) NOT NULL,
    val_str_rec_id uuid,
    val_str_lib_id uuid,
    val_float double precision,
    val_string character varying(255),
    val_max double precision,
    val_min double precision,
    tolerance_plus double precision,
    tolerance_minus double precision,
    gamma integer,
    type_id uuid NOT NULL,
    parameter_id uuid NOT NULL,
    operation_condition_id uuid
);


ALTER TABLE public.mobdekbkp_parameter_value OWNER TO postgres;

--
-- Name: mobdekbkp_parameters_for_purchasing; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_parameters_for_purchasing (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    product_price character varying(50),
    delivery_time character varying(100),
    delivery_term character varying(250),
    status_in_production character varying(50),
    import_device_id uuid,
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_parameters_for_purchasing OWNER TO postgres;

--
-- Name: mobdekbkp_photosensitive_devices; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_photosensitive_devices (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    spectral_sensitivity_region character varying(100),
    number_photosensitive_elements character varying(100),
    geometric_dimensions_photosensitive_element character varying(100),
    specific_threshold_flux character varying(100),
    current_integral_sensitivity character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_photosensitive_devices OWNER TO postgres;

--
-- Name: mobdekbkp_piezoelectric_devices; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_piezoelectric_devices (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    frequency_range character varying(100),
    tuning_accuracy character varying(100),
    in_range_operating_temperatures character varying(100),
    resonator_housing_symbol character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_piezoelectric_devices OWNER TO postgres;

--
-- Name: mobdekbkp_plis; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_plis (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    supply_voltage character varying(100),
    consumption_current character varying(100),
    technology character varying(100),
    number_valves character varying(100),
    built_memory character varying(100)
);


ALTER TABLE public.mobdekbkp_plis OWNER TO postgres;

--
-- Name: mobdekbkp_products_ferrites_magnetodielectrics; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_products_ferrites_magnetodielectrics (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    initial_relative_magnetic_permeability character varying(100),
    relative_goodness character varying(100),
    qfactor_measurement_frequency character varying(100),
    coefficient_adjustment_armor_cores character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_products_ferrites_magnetodielectrics OWNER TO postgres;

--
-- Name: mobdekbkp_quantum_electronics_products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_quantum_electronics_products (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    average_power_lase_radiation character varying(100),
    laser_wave_length character varying(100),
    divergence_laser_radiation character varying(100),
    pulse_repetition_frequency_laser_radiation character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_quantum_electronics_products OWNER TO postgres;

--
-- Name: mobdekbkp_reliability_indicators; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_reliability_indicators (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    reliability_indicator character varying(100),
    retention_rate character varying(100),
    gamma_percent_operating_maintenance character varying(100),
    gamma_percent_operating_light character varying(100),
    gammapercent_storageability_time character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_reliability_indicators OWNER TO postgres;

--
-- Name: mobdekbkp_resistors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_resistors (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    rated_power_dissipation character varying(100),
    nominal_resistance character varying(100),
    resistance_tolerance character varying(100),
    limiting_operating_current character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_resistors OWNER TO postgres;

--
-- Name: mobdekbkp_sapr_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_sapr_data (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    library_graphical_symbols character varying(255),
    electronic_geometric_library character varying(255),
    import_device_id uuid
);


ALTER TABLE public.mobdekbkp_sapr_data OWNER TO postgres;

--
-- Name: mobdekbkp_semiconductor_diodes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_semiconductor_diodes (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    maximum_allowable_reverse_voltage character varying(100),
    maximum_permissible_average_forward_current character varying(100),
    maximum_allowabledirect_current character varying(100),
    limiting_frequency character varying(100),
    reverse_recovery_time character varying(100)
);


ALTER TABLE public.mobdekbkp_semiconductor_diodes OWNER TO postgres;

--
-- Name: mobdekbkp_semiconductor_emitters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_semiconductor_emitters (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    direct_forward_current character varying(100),
    constant_forward_voltage character varying(100),
    radiation_power character varying(100),
    wavelength character varying(100),
    fall_time character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_semiconductor_emitters OWNER TO postgres;

--
-- Name: mobdekbkp_sign_synthesizing_indicators; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_sign_synthesizing_indicators (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    glow_brightness character varying(100),
    glow_color character varying(100),
    maximum_permissible_external_illumination character varying(100),
    supply_voltage character varying(100),
    consumption_current character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_sign_synthesizing_indicators OWNER TO postgres;

--
-- Name: mobdekbkp_small_electric_machines; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_small_electric_machines (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    power_ character varying(100),
    rotation_frequency character varying(100),
    supply_voltage character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_small_electric_machines OWNER TO postgres;

--
-- Name: mobdekbkp_str_lib; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_str_lib (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    text character varying(255)
);


ALTER TABLE public.mobdekbkp_str_lib OWNER TO postgres;

--
-- Name: mobdekbkp_str_lib_settings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_str_lib_settings (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    type_class_id uuid
);


ALTER TABLE public.mobdekbkp_str_lib_settings OWNER TO postgres;

--
-- Name: mobdekbkp_str_lib_settings_str_lib_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_str_lib_settings_str_lib_link (
    str_lib_settings_id uuid NOT NULL,
    str_lib_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_str_lib_settings_str_lib_link OWNER TO postgres;

--
-- Name: mobdekbkp_str_rec; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_str_rec (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    text character varying(255)
);


ALTER TABLE public.mobdekbkp_str_rec OWNER TO postgres;

--
-- Name: mobdekbkp_str_rec_settings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_str_rec_settings (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    type_class_id uuid
);


ALTER TABLE public.mobdekbkp_str_rec_settings OWNER TO postgres;

--
-- Name: mobdekbkp_str_rec_settings_str_rec_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_str_rec_settings_str_rec_link (
    str_rec_settings_id uuid NOT NULL,
    str_rec_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_str_rec_settings_str_rec_link OWNER TO postgres;

--
-- Name: mobdekbkp_substrate; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_substrate (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    model character varying(255) NOT NULL,
    name character varying(255),
    material_id uuid NOT NULL,
    thickness double precision NOT NULL,
    color character varying(255),
    roughness double precision,
    density double precision,
    moisture_absorb double precision,
    flex_strength double precision,
    elastic_module double precision,
    heat_conduct_coeff double precision,
    heat_capacity double precision,
    line_ext_coeff300 double precision,
    line_ext_coeff600 double precision,
    line_ext_coeff1000 double precision,
    dielectric_constant_m character varying(255),
    dielectric_constant_g character varying(255),
    dielectric_loss_coeff double precision,
    breakdown_voltage double precision,
    resistivity_level double precision
);


ALTER TABLE public.mobdekbkp_substrate OWNER TO postgres;

--
-- Name: mobdekbkp_substrate_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_substrate_entry (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    substrate_id uuid NOT NULL,
    typonominal_install_parameters_id uuid
);


ALTER TABLE public.mobdekbkp_substrate_entry OWNER TO postgres;

--
-- Name: mobdekbkp_suppliers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_suppliers (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    name_short character varying(255),
    address_fact text NOT NULL,
    address_legal text NOT NULL,
    ogrn character varying(255),
    kpp character varying(255),
    okud character varying(255),
    inn character varying(255),
    okpo character varying(255),
    agent character varying(255),
    phone character varying(255) NOT NULL,
    fax character varying(255),
    email character varying(255),
    website character varying(255),
    logo_id uuid,
    gen_director character varying(255),
    okato character varying(255),
    country_id uuid
);


ALTER TABLE public.mobdekbkp_suppliers OWNER TO postgres;

--
-- Name: mobdekbkp_support_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_support_info (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    phone character varying(255) NOT NULL,
    instructions_id uuid,
    mail character varying(255) NOT NULL,
    message text,
    expiration timestamp without time zone
);


ALTER TABLE public.mobdekbkp_support_info OWNER TO postgres;

--
-- Name: mobdekbkp_svch; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_svch (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    operating_frequencya_range character varying(100),
    adjustment_factor character varying(100),
    phase_noise_level character varying(100),
    output_power character varying(100),
    supply_voltage character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_svch OWNER TO postgres;

--
-- Name: mobdekbkp_switching_products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_switching_products (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    switched_current character varying(100),
    switching_voltage character varying(100),
    number_contact_groups character varying(100),
    switching_current_frequency character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_switching_products OWNER TO postgres;

--
-- Name: mobdekbkp_technical_parameters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_technical_parameters (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    frequency_range_sinusoidal_vibration character varying(50),
    acceleration_amplitude character varying(50),
    exposure_time_sinusoidal_vibration character varying(50),
    frequency_range_broad_vibration character varying(50),
    acceleration_spectral_density character varying(50),
    root_mean_square_acceleration character varying(50),
    exposure_time_broad_vibration character varying(50),
    peak_shock_acceleration_one character varying(50),
    duration_of_impact_acceleration_one character varying(50),
    shock_spectrum_frequency_range character varying(50),
    impact_spectrum_value character varying(50),
    number_of_impacts_one character varying(50),
    peak_shock_acceleration_many character varying(255),
    duration_of_impact_acceleration_many character varying(50),
    number_of_impacts_many character varying(50),
    frequency_range_acoustic_noise character varying(50),
    sound_exposure_level character varying(50),
    exposure_time_acoustic_noise character varying(50),
    acceleration_amplitude_quasi_static_acceleration character varying(50),
    exposure_time_quasi_static_acceleration character varying(50),
    maximum_value_during_operation_up character varying(50),
    maximum_value_during_transportation_and_storage_up character varying(50),
    maximum_value_during_operation_down character varying(50),
    maximum_value_during_transportation_and_storage_down character varying(50),
    change_temperature_rate_change_temperature character varying(50),
    relative_humidity_at_temperature_up character varying(50),
    absolute_air_humidity_up character varying(50),
    relative_humidity_at_temperature_down character varying(50),
    absolute_air_humidity_down character varying(50),
    value_during_operation_down character varying(50),
    value_during_transportation_and_storage_down character varying(50),
    value_during_operation_up character varying(50),
    value_during_transportation_and_storage_up character varying(50),
    rate_pressure_change character varying(50),
    pressure_change_range character varying(50),
    esd_requirements character varying(50),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_technical_parameters OWNER TO postgres;

--
-- Name: mobdekbkp_terms_and_definitions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_terms_and_definitions (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    term text NOT NULL,
    definition_ text NOT NULL,
    source text
);


ALTER TABLE public.mobdekbkp_terms_and_definitions OWNER TO postgres;

--
-- Name: mobdekbkp_transformers_chokes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_transformers_chokes (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    minimum_duty_cycle character varying(100),
    pulse_duration character varying(100),
    typonominal_id uuid
);


ALTER TABLE public.mobdekbkp_transformers_chokes OWNER TO postgres;

--
-- Name: mobdekbkp_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_type (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    designation character varying(255) NOT NULL,
    reliability_handbook character varying(50),
    alt_class_rel_id uuid,
    alt_class_mil_id uuid,
    alt_class_g56649_id uuid,
    alt_class_g2710_id uuid,
    has_long_deliver_cycle character varying(50) NOT NULL,
    functional_purpose text NOT NULL,
    placement_category character varying(50) NOT NULL,
    climatic_implementation_id uuid,
    math_model_params_id uuid,
    type_class_id uuid NOT NULL,
    amount_unit_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_type OWNER TO postgres;

--
-- Name: mobdekbkp_type_calicoholder_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_type_calicoholder_entry (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name_id uuid NOT NULL,
    type_inverse_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_type_calicoholder_entry OWNER TO postgres;

--
-- Name: mobdekbkp_type_class; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_type_class (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    number_ character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    parent_id uuid,
    type_id uuid
);


ALTER TABLE public.mobdekbkp_type_class OWNER TO postgres;

--
-- Name: mobdekbkp_type_class_characteristic; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_type_class_characteristic (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    is_main boolean NOT NULL,
    parameter_id uuid NOT NULL,
    type_class_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_type_class_characteristic OWNER TO postgres;

--
-- Name: mobdekbkp_type_class_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_type_class_type (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL
);


ALTER TABLE public.mobdekbkp_type_class_type OWNER TO postgres;

--
-- Name: mobdekbkp_type_climatic_implementation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_type_climatic_implementation (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL
);


ALTER TABLE public.mobdekbkp_type_climatic_implementation OWNER TO postgres;

--
-- Name: mobdekbkp_type_document_link_appscheme; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_type_document_link_appscheme (
    type_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_type_document_link_appscheme OWNER TO postgres;

--
-- Name: mobdekbkp_type_document_link_delivery; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_type_document_link_delivery (
    type_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_type_document_link_delivery OWNER TO postgres;

--
-- Name: mobdekbkp_type_manufacturer_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_type_manufacturer_entry (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name_id uuid NOT NULL,
    type_inverse_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_type_manufacturer_entry OWNER TO postgres;

--
-- Name: mobdekbkp_type_math_model_parameters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_type_math_model_parameters (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    el_proc_model_name character varying(255) NOT NULL,
    el_proc_description character varying(255),
    el_proc_model_category character varying(255),
    el_proc_model_subcategory character varying(255),
    el_proc_model_connection_list character varying(255),
    el_proc_model_prefix character varying(255),
    el_proc_model_to_view_compare character varying(255),
    apply_bounds character varying(255)
);


ALTER TABLE public.mobdekbkp_type_math_model_parameters OWNER TO postgres;

--
-- Name: mobdekbkp_type_math_model_parameters_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_type_math_model_parameters_document_link (
    type_math_model_parameters_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_type_math_model_parameters_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_type_provider_entry; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_type_provider_entry (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name_id uuid NOT NULL,
    type_inverse_id uuid
);


ALTER TABLE public.mobdekbkp_type_provider_entry OWNER TO postgres;

--
-- Name: mobdekbkp_typonominal; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_typonominal (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    typonominal_quality_level_native_id uuid,
    typonominal_quality_level_import_id uuid,
    mass double precision,
    not_perspective character varying(50) NOT NULL,
    has_substitute character varying(50) NOT NULL,
    seal_needed character varying(50) NOT NULL,
    is_auto_assemble character varying(50) NOT NULL,
    made_using_import_items character varying(50) NOT NULL,
    shelf_life integer,
    persistence_cycle integer,
    has_body character varying(50) NOT NULL,
    life_cycle_stage character varying(50) NOT NULL,
    labeling character varying(255),
    body_id uuid,
    install_parameters_id uuid,
    type_id uuid NOT NULL,
    lib_attributes_id uuid,
    technical_condition character varying(255),
    basicinformation_id uuid,
    factory_id uuid,
    parameters_for_purchasing_id uuid,
    advanced_setting_id uuid,
    corpus_id uuid,
    reliability_indicators_id uuid,
    montage_parameters_id uuid,
    technical_parameters_id uuid,
    main_parameters_id uuid
);


ALTER TABLE public.mobdekbkp_typonominal OWNER TO postgres;

--
-- Name: mobdekbkp_typonominal_analog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_typonominal_analog (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    typonominal_id uuid,
    type_analog character varying(50),
    is_recommended_gnio character varying(50),
    parent_typonominal_id uuid,
    producer character varying(100),
    producing_country character varying(100),
    okr_min_prom_torg character varying(100),
    import_device_id uuid,
    parent_import_device_id uuid
);


ALTER TABLE public.mobdekbkp_typonominal_analog OWNER TO postgres;

--
-- Name: mobdekbkp_typonominal_body; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_typonominal_body (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    designation character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255),
    length double precision,
    width double precision,
    height double precision,
    pins_count integer,
    body_material_id uuid,
    pins_material_id uuid,
    coating_material_id uuid,
    distance_leads double precision,
    mass double precision,
    max_height double precision,
    pin_forming_designation_id uuid,
    photo_id uuid,
    sealing_demand character varying(50) NOT NULL,
    disperse_power double precision,
    output_power double precision,
    thermal_resistance double precision,
    dimensions_and_body_id uuid,
    fixator_markup_id uuid
);


ALTER TABLE public.mobdekbkp_typonominal_body OWNER TO postgres;

--
-- Name: mobdekbkp_typonominal_body_document_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_typonominal_body_document_link (
    typonominal_body_id uuid NOT NULL,
    document_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_typonominal_body_document_link OWNER TO postgres;

--
-- Name: mobdekbkp_typonominal_install_parameters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_typonominal_install_parameters (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    description text NOT NULL,
    non_pb_solder_tech character varying(50) NOT NULL,
    temperature_mode character varying(255),
    gas_environment_available character varying(50) NOT NULL,
    body_installation_document_id uuid,
    installation_option_designation character varying(255),
    pin_forming_document_id uuid,
    pin_forming_designation character varying(255),
    has_gasket character varying(50) NOT NULL,
    gasket_size character varying(255),
    gasket_material_id uuid,
    has_glue character varying(50) NOT NULL,
    installation_count_allowed integer,
    auto_installation character varying(50) NOT NULL,
    glue_type_id uuid,
    install_method_id uuid NOT NULL,
    solder_brand_id uuid,
    flux_brand_id uuid
);


ALTER TABLE public.mobdekbkp_typonominal_install_parameters OWNER TO postgres;

--
-- Name: mobdekbkp_typonominal_purchase_parameters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_typonominal_purchase_parameters (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    purchase_designation character varying(255) NOT NULL,
    cost_rating text NOT NULL,
    typical_delivery_term integer NOT NULL,
    market_available_index text,
    need_permissions_gosdep character varying(50) NOT NULL,
    permission_gosdep_term integer,
    has_samples character varying(50) NOT NULL,
    delivery_min integer,
    delivery_max integer,
    typonominal_id uuid NOT NULL,
    company_id uuid NOT NULL,
    cost_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_typonominal_purchase_parameters OWNER TO postgres;

--
-- Name: mobdekbkp_typonominal_q_l_import_set_typonominal_q_l_import; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_typonominal_q_l_import_set_typonominal_q_l_import (
    typonominal_quality_level_import_settings_id uuid NOT NULL,
    typonominal_quality_level_import_id uuid NOT NULL
);


ALTER TABLE public.mobdekbkp_typonominal_q_l_import_set_typonominal_q_l_import OWNER TO postgres;

--
-- Name: mobdekbkp_typonominal_quality_level_import; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_typonominal_quality_level_import (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL
);


ALTER TABLE public.mobdekbkp_typonominal_quality_level_import OWNER TO postgres;

--
-- Name: mobdekbkp_typonominal_quality_level_import_settings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_typonominal_quality_level_import_settings (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    import_class_id uuid
);


ALTER TABLE public.mobdekbkp_typonominal_quality_level_import_settings OWNER TO postgres;

--
-- Name: mobdekbkp_typonominal_quality_level_native; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_typonominal_quality_level_native (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL
);


ALTER TABLE public.mobdekbkp_typonominal_quality_level_native OWNER TO postgres;

--
-- Name: mobdekbkp_unit_dev; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_unit_dev (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    short_name character varying(255) NOT NULL
);


ALTER TABLE public.mobdekbkp_unit_dev OWNER TO postgres;

--
-- Name: mobdekbkp_unit_val; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mobdekbkp_unit_val (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    short_name character varying(255) NOT NULL
);


ALTER TABLE public.mobdekbkp_unit_val OWNER TO postgres;

--
-- Name: modb_loader; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.modb_loader (
    id uuid,
    number_ character varying(15),
    name character varying(250),
    partner_id uuid
);


ALTER TABLE public.modb_loader OWNER TO postgres;

--
-- Name: notificationsusers_message; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notificationsusers_message (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    sender_id uuid,
    sent boolean,
    is_system boolean NOT NULL,
    recipient_id uuid,
    subject character varying(255),
    entity_reference character varying(255),
    message_text_id uuid,
    meta_id uuid NOT NULL,
    attachment_id uuid
);


ALTER TABLE public.notificationsusers_message OWNER TO postgres;

--
-- Name: notificationsusers_message_meta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notificationsusers_message_meta (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    is_read boolean NOT NULL,
    is_sender_rec boolean NOT NULL,
    is_receiver_rec boolean NOT NULL,
    is_sender_del boolean NOT NULL,
    is_receiver_del boolean NOT NULL,
    is_sender_fav boolean NOT NULL,
    is_receiver_fav boolean NOT NULL
);


ALTER TABLE public.notificationsusers_message_meta OWNER TO postgres;

--
-- Name: notificationsusers_message_text; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notificationsusers_message_text (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    text text
);


ALTER TABLE public.notificationsusers_message_text OWNER TO postgres;

--
-- Name: report_group; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.report_group (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    title character varying(255) NOT NULL,
    code character varying(255),
    locale_names text
);


ALTER TABLE public.report_group OWNER TO postgres;

--
-- Name: report_report; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.report_report (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    code character varying(255),
    description character varying(500),
    locale_names text,
    group_id uuid NOT NULL,
    report_type integer,
    default_template_id uuid,
    xml text,
    roles_idx character varying(1000),
    screens_idx character varying(1000),
    input_entity_types_idx character varying(1000)
);


ALTER TABLE public.report_report OWNER TO postgres;

--
-- Name: report_template; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.report_template (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    report_id uuid NOT NULL,
    code character varying(50),
    output_type integer DEFAULT 0 NOT NULL,
    is_default boolean DEFAULT false,
    is_custom boolean DEFAULT false,
    is_alterable_output boolean DEFAULT false,
    custom_defined_by integer DEFAULT 100,
    custom_class character varying,
    output_name_pattern character varying(255),
    name character varying(500),
    content bytea
);


ALTER TABLE public.report_template OWNER TO postgres;

--
-- Name: reviews_entities_for_moderation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reviews_entities_for_moderation (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    entity_id uuid,
    moderation_property_id uuid
);


ALTER TABLE public.reviews_entities_for_moderation OWNER TO postgres;

--
-- Name: reviews_entities_for_moderation_user_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reviews_entities_for_moderation_user_link (
    entities_for_moderation_id uuid NOT NULL,
    user_id uuid NOT NULL
);


ALTER TABLE public.reviews_entities_for_moderation_user_link OWNER TO postgres;

--
-- Name: reviews_moderation_property; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reviews_moderation_property (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    entity character varying(255),
    moderation_type character varying(50)
);


ALTER TABLE public.reviews_moderation_property OWNER TO postgres;

--
-- Name: reviews_moderation_property_user_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reviews_moderation_property_user_link (
    moderation_property_id uuid NOT NULL,
    user_id uuid NOT NULL
);


ALTER TABLE public.reviews_moderation_property_user_link OWNER TO postgres;

--
-- Name: reviews_review; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reviews_review (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    review text NOT NULL,
    moderation_reason character varying(255),
    moderated_review text,
    author_id uuid,
    parent uuid,
    parent_name character varying(255),
    grade integer NOT NULL,
    status character varying(50)
);


ALTER TABLE public.reviews_review OWNER TO postgres;

--
-- Name: rulesmodule_rule_data_script; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rulesmodule_rule_data_script (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    entity character varying(255),
    sql_ text,
    comment_ text,
    view_ character varying(255)
);


ALTER TABLE public.rulesmodule_rule_data_script OWNER TO postgres;

--
-- Name: rulesmodule_rule_manager; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rulesmodule_rule_manager (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255),
    entity character varying(255)
);


ALTER TABLE public.rulesmodule_rule_manager OWNER TO postgres;

--
-- Name: rulesmodule_rule_manager_rule_data_script_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rulesmodule_rule_manager_rule_data_script_link (
    rule_manager_id uuid NOT NULL,
    rule_data_script_id uuid NOT NULL
);


ALTER TABLE public.rulesmodule_rule_manager_rule_data_script_link OWNER TO postgres;

--
-- Name: rulesmodule_rule_manager_rule_script_link; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rulesmodule_rule_manager_rule_script_link (
    rule_manager_id uuid NOT NULL,
    rule_script_id uuid NOT NULL
);


ALTER TABLE public.rulesmodule_rule_manager_rule_script_link OWNER TO postgres;

--
-- Name: rulesmodule_rule_script; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rulesmodule_rule_script (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255),
    script text,
    comment_ text
);


ALTER TABLE public.rulesmodule_rule_script OWNER TO postgres;

--
-- Name: sec_constraint; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_constraint (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    code character varying(255),
    check_type character varying(50) DEFAULT 'db'::character varying,
    operation_type character varying(50) DEFAULT 'read'::character varying,
    entity_name character varying(255) NOT NULL,
    join_clause character varying(500),
    where_clause character varying(1000),
    groovy_script text,
    filter_xml text,
    is_active boolean DEFAULT true,
    group_id uuid
);


ALTER TABLE public.sec_constraint OWNER TO postgres;

--
-- Name: sec_entity_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_entity_log (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    event_ts timestamp without time zone,
    user_id uuid,
    change_type character(1),
    entity character varying(100),
    entity_id uuid,
    string_entity_id character varying(255),
    int_entity_id integer,
    long_entity_id bigint,
    changes text
);


ALTER TABLE public.sec_entity_log OWNER TO postgres;

--
-- Name: sec_filter; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_filter (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    component character varying(200),
    name character varying(255),
    code character varying(200),
    xml text,
    user_id uuid,
    global_default boolean
);


ALTER TABLE public.sec_filter OWNER TO postgres;

--
-- Name: sec_group; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_group (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    parent_id uuid
);


ALTER TABLE public.sec_group OWNER TO postgres;

--
-- Name: sec_group_hierarchy; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_group_hierarchy (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    group_id uuid,
    parent_id uuid,
    hierarchy_level integer
);


ALTER TABLE public.sec_group_hierarchy OWNER TO postgres;

--
-- Name: sec_localized_constraint_msg; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_localized_constraint_msg (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    entity_name character varying(255) NOT NULL,
    operation_type character varying(50) NOT NULL,
    values_ text
);


ALTER TABLE public.sec_localized_constraint_msg OWNER TO postgres;

--
-- Name: sec_logged_attr; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_logged_attr (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    entity_id uuid,
    name character varying(50)
);


ALTER TABLE public.sec_logged_attr OWNER TO postgres;

--
-- Name: sec_logged_entity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_logged_entity (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    name character varying(100),
    auto boolean,
    manual boolean
);


ALTER TABLE public.sec_logged_entity OWNER TO postgres;

--
-- Name: sec_permission; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_permission (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    permission_type integer,
    target character varying(100),
    value_ integer,
    role_id uuid
);


ALTER TABLE public.sec_permission OWNER TO postgres;

--
-- Name: sec_presentation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_presentation (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    component character varying(200),
    name character varying(255),
    xml character varying(7000),
    user_id uuid,
    is_auto_save boolean
);


ALTER TABLE public.sec_presentation OWNER TO postgres;

--
-- Name: sec_remember_me; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_remember_me (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    user_id uuid NOT NULL,
    token character varying(32) NOT NULL
);


ALTER TABLE public.sec_remember_me OWNER TO postgres;

--
-- Name: sec_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_role (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    loc_name character varying(255),
    description character varying(1000),
    is_default_role boolean,
    role_type integer
);


ALTER TABLE public.sec_role OWNER TO postgres;

--
-- Name: sec_screen_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_screen_history (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    user_id uuid,
    caption character varying(255),
    url text,
    entity_id uuid,
    substituted_user_id uuid
);


ALTER TABLE public.sec_screen_history OWNER TO postgres;

--
-- Name: sec_search_folder; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_search_folder (
    folder_id uuid NOT NULL,
    filter_component character varying(200),
    filter_xml character varying(7000),
    user_id uuid,
    presentation_id uuid,
    apply_default boolean,
    is_set boolean,
    entity_type character varying(50)
);


ALTER TABLE public.sec_search_folder OWNER TO postgres;

--
-- Name: sec_session_attr; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_session_attr (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(50),
    str_value character varying(1000),
    datatype character varying(20),
    group_id uuid
);


ALTER TABLE public.sec_session_attr OWNER TO postgres;

--
-- Name: sec_session_log; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_session_log (
    id uuid NOT NULL,
    version integer NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    session_id uuid NOT NULL,
    user_id uuid NOT NULL,
    substituted_user_id uuid,
    user_data text,
    last_action integer NOT NULL,
    client_info character varying(512),
    client_type character varying(10),
    address character varying(255),
    started_ts timestamp without time zone,
    finished_ts timestamp without time zone,
    server_id character varying(128)
);


ALTER TABLE public.sec_session_log OWNER TO postgres;

--
-- Name: sec_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_user (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    login character varying(50) NOT NULL,
    login_lc character varying(50) NOT NULL,
    password character varying(255),
    name character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    middle_name character varying(255),
    position_ character varying(255),
    email character varying(100),
    language_ character varying(20),
    time_zone character varying(50),
    time_zone_auto boolean,
    active boolean,
    group_id uuid NOT NULL,
    ip_mask character varying(200),
    change_password_at_logon boolean,
    company_ref character varying(255),
    company_id uuid,
    phone character varying(50),
    ph_add character varying(255),
    file_id uuid,
    dtype character varying(100)
);


ALTER TABLE public.sec_user OWNER TO postgres;

--
-- Name: sec_user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_user_role (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    user_id uuid,
    role_id uuid
);


ALTER TABLE public.sec_user_role OWNER TO postgres;

--
-- Name: sec_user_setting; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_user_setting (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    user_id uuid,
    client_type character(1),
    name character varying(255),
    value_ text
);


ALTER TABLE public.sec_user_setting OWNER TO postgres;

--
-- Name: sec_user_substitution; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sec_user_substitution (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    user_id uuid NOT NULL,
    substituted_user_id uuid NOT NULL,
    start_date timestamp without time zone,
    end_date timestamp without time zone
);


ALTER TABLE public.sec_user_substitution OWNER TO postgres;

--
-- Name: sys_access_token; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_access_token (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    token_value character varying(255),
    token_bytes bytea,
    authentication_key character varying(255),
    authentication_bytes bytea,
    expiry timestamp without time zone,
    user_login character varying(50),
    locale character varying(200),
    refresh_token_value character varying(255)
);


ALTER TABLE public.sys_access_token OWNER TO postgres;

--
-- Name: sys_app_folder; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_app_folder (
    folder_id uuid NOT NULL,
    filter_component character varying(200),
    filter_xml character varying(7000),
    visibility_script text,
    quantity_script text,
    apply_default boolean
);


ALTER TABLE public.sys_app_folder OWNER TO postgres;

--
-- Name: sys_attr_value; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_attr_value (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    category_attr_id uuid NOT NULL,
    code character varying(100),
    entity_id uuid,
    string_entity_id character varying(255),
    int_entity_id integer,
    long_entity_id bigint,
    string_value character varying,
    integer_value integer,
    double_value numeric(36,6),
    date_value timestamp without time zone,
    boolean_value boolean,
    entity_value uuid,
    string_entity_value character varying(255),
    int_entity_value integer,
    long_entity_value bigint,
    parent_id uuid
);


ALTER TABLE public.sys_attr_value OWNER TO postgres;

--
-- Name: sys_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_category (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(255) NOT NULL,
    special character varying(50),
    entity_type character varying(100) NOT NULL,
    is_default boolean,
    discriminator integer,
    locale_names character varying(1000)
);


ALTER TABLE public.sys_category OWNER TO postgres;

--
-- Name: sys_category_attr; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_category_attr (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    category_entity_type character varying(4000),
    name character varying(255),
    code character varying(100) NOT NULL,
    category_id uuid NOT NULL,
    entity_class character varying(500),
    data_type character varying(200),
    default_string character varying,
    default_int integer,
    default_double numeric(36,6),
    default_date timestamp without time zone,
    default_date_is_current boolean,
    default_boolean boolean,
    default_entity_value uuid,
    default_str_entity_value character varying(255),
    default_int_entity_value integer,
    default_long_entity_value bigint,
    enumeration character varying(500),
    order_no integer,
    screen character varying(255),
    required boolean,
    lookup boolean,
    target_screens character varying(4000),
    width character varying(20),
    rows_count integer,
    is_collection boolean,
    join_clause character varying(4000),
    where_clause character varying(4000),
    filter_xml text,
    locale_names character varying(1000),
    enumeration_locales character varying(5000)
);


ALTER TABLE public.sys_category_attr OWNER TO postgres;

--
-- Name: sys_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_config (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    name character varying(255) NOT NULL,
    value_ text NOT NULL
);


ALTER TABLE public.sys_config OWNER TO postgres;

--
-- Name: sys_db_changelog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_db_changelog (
    script_name character varying(300) NOT NULL,
    create_ts timestamp without time zone DEFAULT now(),
    is_init integer DEFAULT 0
);


ALTER TABLE public.sys_db_changelog OWNER TO postgres;

--
-- Name: sys_entity_snapshot; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_entity_snapshot (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    entity_meta_class character varying(50) NOT NULL,
    entity_id uuid,
    string_entity_id character varying(255),
    int_entity_id integer,
    long_entity_id bigint,
    author_id uuid NOT NULL,
    view_xml text NOT NULL,
    snapshot_xml text NOT NULL,
    snapshot_date timestamp without time zone NOT NULL
);


ALTER TABLE public.sys_entity_snapshot OWNER TO postgres;

--
-- Name: sys_entity_statistics; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_entity_statistics (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    name character varying(50),
    instance_count bigint,
    fetch_ui integer,
    max_fetch_ui integer,
    lazy_collection_threshold integer,
    lookup_screen_threshold integer
);


ALTER TABLE public.sys_entity_statistics OWNER TO postgres;

--
-- Name: sys_file; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_file (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    name character varying(500) NOT NULL,
    ext character varying(20),
    file_size bigint,
    create_date timestamp without time zone
);


ALTER TABLE public.sys_file OWNER TO postgres;

--
-- Name: sys_folder; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_folder (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    folder_type character(1),
    parent_id uuid,
    name character varying(100),
    tab_name character varying(100),
    sort_order integer
);


ALTER TABLE public.sys_folder OWNER TO postgres;

--
-- Name: sys_fts_queue; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_fts_queue (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    entity_id uuid,
    string_entity_id character varying(255),
    int_entity_id integer,
    long_entity_id bigint,
    entity_name character varying(200),
    change_type character(1),
    source_host character varying(255),
    indexing_host character varying(255),
    fake boolean
);


ALTER TABLE public.sys_fts_queue OWNER TO postgres;

--
-- Name: sys_jmx_instance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_jmx_instance (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    node_name character varying(255),
    address character varying(500) NOT NULL,
    login character varying(50) NOT NULL,
    password character varying(255) NOT NULL
);


ALTER TABLE public.sys_jmx_instance OWNER TO postgres;

--
-- Name: sys_lock_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_lock_config (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    name character varying(100),
    timeout_sec integer
);


ALTER TABLE public.sys_lock_config OWNER TO postgres;

--
-- Name: sys_query_result_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sys_query_result_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sys_query_result_seq OWNER TO postgres;

--
-- Name: sys_query_result; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_query_result (
    id bigint DEFAULT nextval('public.sys_query_result_seq'::regclass) NOT NULL,
    session_id uuid NOT NULL,
    query_key integer NOT NULL,
    entity_id uuid,
    string_entity_id character varying(255),
    int_entity_id integer,
    long_entity_id bigint
);


ALTER TABLE public.sys_query_result OWNER TO postgres;

--
-- Name: sys_refresh_token; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_refresh_token (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    token_value character varying(255),
    token_bytes bytea,
    authentication_bytes bytea,
    expiry timestamp without time zone,
    user_login character varying(50)
);


ALTER TABLE public.sys_refresh_token OWNER TO postgres;

--
-- Name: sys_scheduled_execution; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_scheduled_execution (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    task_id uuid,
    server character varying(512),
    start_time timestamp with time zone,
    finish_time timestamp with time zone,
    result text
);


ALTER TABLE public.sys_scheduled_execution OWNER TO postgres;

--
-- Name: sys_scheduled_task; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_scheduled_task (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    defined_by character varying(1) DEFAULT 'B'::character varying,
    class_name character varying(500),
    script_name character varying(500),
    bean_name character varying(50),
    method_name character varying(50),
    method_params character varying(1000),
    user_name character varying(50),
    is_singleton boolean,
    is_active boolean,
    period integer,
    timeout integer,
    start_date timestamp without time zone,
    time_frame integer,
    start_delay integer,
    permitted_servers character varying(4096),
    log_start boolean,
    log_finish boolean,
    last_start_time timestamp with time zone,
    last_start_server character varying(512),
    description character varying(1000),
    cron character varying(100),
    scheduling_type character varying(1) DEFAULT 'P'::character varying
);


ALTER TABLE public.sys_scheduled_task OWNER TO postgres;

--
-- Name: sys_sending_attachment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_sending_attachment (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp without time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    message_id uuid,
    content bytea,
    content_file_id uuid,
    content_id character varying(50),
    name character varying(500),
    disposition character varying(50),
    text_encoding character varying(50)
);


ALTER TABLE public.sys_sending_attachment OWNER TO postgres;

--
-- Name: sys_sending_message; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_sending_message (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    version integer,
    update_ts timestamp with time zone,
    updated_by character varying(50),
    delete_ts timestamp without time zone,
    deleted_by character varying(50),
    address_to text,
    address_from character varying(100),
    caption character varying(500),
    email_headers character varying(500),
    content_text text,
    content_text_file_id uuid,
    deadline timestamp with time zone,
    status integer,
    date_sent timestamp without time zone,
    attempts_count integer,
    attempts_made integer,
    attachments_name text,
    body_content_type character varying(50)
);


ALTER TABLE public.sys_sending_message OWNER TO postgres;

--
-- Name: sys_server; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sys_server (
    id uuid NOT NULL,
    create_ts timestamp without time zone,
    created_by character varying(50),
    update_ts timestamp without time zone,
    updated_by character varying(50),
    name character varying(255),
    is_running boolean,
    data text
);


ALTER TABLE public.sys_server OWNER TO postgres;

--
-- Name: act_evt_log log_nr_; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_evt_log ALTER COLUMN log_nr_ SET DEFAULT nextval('public.act_evt_log_log_nr__seq'::regclass);


--
-- Name: act_evt_log act_evt_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_evt_log
    ADD CONSTRAINT act_evt_log_pkey PRIMARY KEY (log_nr_);


--
-- Name: act_ge_bytearray act_ge_bytearray_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ge_bytearray
    ADD CONSTRAINT act_ge_bytearray_pkey PRIMARY KEY (id_);


--
-- Name: act_ge_property act_ge_property_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ge_property
    ADD CONSTRAINT act_ge_property_pkey PRIMARY KEY (name_);


--
-- Name: act_hi_actinst act_hi_actinst_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_hi_actinst
    ADD CONSTRAINT act_hi_actinst_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_attachment act_hi_attachment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_hi_attachment
    ADD CONSTRAINT act_hi_attachment_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_comment act_hi_comment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_hi_comment
    ADD CONSTRAINT act_hi_comment_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_detail act_hi_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_hi_detail
    ADD CONSTRAINT act_hi_detail_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_identitylink act_hi_identitylink_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_hi_identitylink
    ADD CONSTRAINT act_hi_identitylink_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_procinst act_hi_procinst_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_hi_procinst
    ADD CONSTRAINT act_hi_procinst_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_procinst act_hi_procinst_proc_inst_id__key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_hi_procinst
    ADD CONSTRAINT act_hi_procinst_proc_inst_id__key UNIQUE (proc_inst_id_);


--
-- Name: act_hi_taskinst act_hi_taskinst_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_hi_taskinst
    ADD CONSTRAINT act_hi_taskinst_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_varinst act_hi_varinst_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_hi_varinst
    ADD CONSTRAINT act_hi_varinst_pkey PRIMARY KEY (id_);


--
-- Name: act_id_group act_id_group_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_id_group
    ADD CONSTRAINT act_id_group_pkey PRIMARY KEY (id_);


--
-- Name: act_id_info act_id_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_id_info
    ADD CONSTRAINT act_id_info_pkey PRIMARY KEY (id_);


--
-- Name: act_id_membership act_id_membership_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_id_membership
    ADD CONSTRAINT act_id_membership_pkey PRIMARY KEY (user_id_, group_id_);


--
-- Name: act_id_user act_id_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_id_user
    ADD CONSTRAINT act_id_user_pkey PRIMARY KEY (id_);


--
-- Name: act_procdef_info act_procdef_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_procdef_info
    ADD CONSTRAINT act_procdef_info_pkey PRIMARY KEY (id_);


--
-- Name: act_re_deployment act_re_deployment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_re_deployment
    ADD CONSTRAINT act_re_deployment_pkey PRIMARY KEY (id_);


--
-- Name: act_re_model act_re_model_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_re_model
    ADD CONSTRAINT act_re_model_pkey PRIMARY KEY (id_);


--
-- Name: act_re_procdef act_re_procdef_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_re_procdef
    ADD CONSTRAINT act_re_procdef_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_event_subscr act_ru_event_subscr_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_event_subscr
    ADD CONSTRAINT act_ru_event_subscr_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_execution act_ru_execution_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_execution
    ADD CONSTRAINT act_ru_execution_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_identitylink act_ru_identitylink_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_identitylink
    ADD CONSTRAINT act_ru_identitylink_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_job act_ru_job_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_job
    ADD CONSTRAINT act_ru_job_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_task act_ru_task_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_task
    ADD CONSTRAINT act_ru_task_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_variable act_ru_variable_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_variable
    ADD CONSTRAINT act_ru_variable_pkey PRIMARY KEY (id_);


--
-- Name: act_procdef_info act_uniq_info_procdef; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_procdef_info
    ADD CONSTRAINT act_uniq_info_procdef UNIQUE (proc_def_id_);


--
-- Name: act_re_procdef act_uniq_procdef; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_re_procdef
    ADD CONSTRAINT act_uniq_procdef UNIQUE (key_, version_, tenant_id_);


--
-- Name: bpm_proc_actor bpm_proc_actor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_actor
    ADD CONSTRAINT bpm_proc_actor_pkey PRIMARY KEY (id);


--
-- Name: bpm_proc_attachment bpm_proc_attachment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_attachment
    ADD CONSTRAINT bpm_proc_attachment_pkey PRIMARY KEY (id);


--
-- Name: bpm_proc_attachment_type bpm_proc_attachment_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_attachment_type
    ADD CONSTRAINT bpm_proc_attachment_type_pkey PRIMARY KEY (id);


--
-- Name: bpm_proc_definition bpm_proc_definition_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_definition
    ADD CONSTRAINT bpm_proc_definition_pkey PRIMARY KEY (id);


--
-- Name: bpm_proc_instance bpm_proc_instance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_instance
    ADD CONSTRAINT bpm_proc_instance_pkey PRIMARY KEY (id);


--
-- Name: bpm_proc_model bpm_proc_model_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_model
    ADD CONSTRAINT bpm_proc_model_pkey PRIMARY KEY (id);


--
-- Name: bpm_proc_role bpm_proc_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_role
    ADD CONSTRAINT bpm_proc_role_pkey PRIMARY KEY (id);


--
-- Name: bpm_proc_task bpm_proc_task_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_task
    ADD CONSTRAINT bpm_proc_task_pkey PRIMARY KEY (id);


--
-- Name: bpm_proc_task_user_link bpm_proc_task_user_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_task_user_link
    ADD CONSTRAINT bpm_proc_task_user_link_pkey PRIMARY KEY (proc_task_id, user_id);


--
-- Name: bpm_stencil_set bpm_stencil_set_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_stencil_set
    ADD CONSTRAINT bpm_stencil_set_pkey PRIMARY KEY (id);


--
-- Name: cubaat_ssh_credentials cubaat_ssh_credentials_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cubaat_ssh_credentials
    ADD CONSTRAINT cubaat_ssh_credentials_pkey PRIMARY KEY (id);


--
-- Name: ddcrd_diagnose_execution_log ddcrd_diagnose_execution_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ddcrd_diagnose_execution_log
    ADD CONSTRAINT ddcrd_diagnose_execution_log_pkey PRIMARY KEY (id);


--
-- Name: discuss_comment discuss_comment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discuss_comment
    ADD CONSTRAINT discuss_comment_pkey PRIMARY KEY (id);


--
-- Name: discuss_comment_preference discuss_comment_preference_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discuss_comment_preference
    ADD CONSTRAINT discuss_comment_preference_pkey PRIMARY KEY (id);


--
-- Name: documents_document_file_descriptor_link documents_document_file_descriptor_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents_document_file_descriptor_link
    ADD CONSTRAINT documents_document_file_descriptor_link_pkey PRIMARY KEY (document_id, file_descriptor_id);


--
-- Name: documents_document documents_document_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents_document
    ADD CONSTRAINT documents_document_pkey PRIMARY KEY (id);


--
-- Name: documents_document_restriction_document_type_link documents_document_restriction_document_type_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents_document_restriction_document_type_link
    ADD CONSTRAINT documents_document_restriction_document_type_link_pkey PRIMARY KEY (document_restriction_id, document_type_id);


--
-- Name: documents_document_restriction documents_document_restriction_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents_document_restriction
    ADD CONSTRAINT documents_document_restriction_pkey PRIMARY KEY (id);


--
-- Name: documents_document_type documents_document_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents_document_type
    ADD CONSTRAINT documents_document_type_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_advanced_setting mobdekbkp_advanced_setting_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_advanced_setting
    ADD CONSTRAINT mobdekbkp_advanced_setting_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_alter_class_gost2710 mobdekbkp_alter_class_gost2710_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_alter_class_gost2710
    ADD CONSTRAINT mobdekbkp_alter_class_gost2710_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_alter_class_gost56649 mobdekbkp_alter_class_gost56649_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_alter_class_gost56649
    ADD CONSTRAINT mobdekbkp_alter_class_gost56649_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_alter_class_mil mobdekbkp_alter_class_mil_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_alter_class_mil
    ADD CONSTRAINT mobdekbkp_alter_class_mil_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_alter_class_reliability mobdekbkp_alter_class_reliability_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_alter_class_reliability
    ADD CONSTRAINT mobdekbkp_alter_class_reliability_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_applicability_devices mobdekbkp_applicability_devices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_applicability_devices
    ADD CONSTRAINT mobdekbkp_applicability_devices_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_application_common_dev_document_link mobdekbkp_application_common_dev_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_common_dev_document_link
    ADD CONSTRAINT mobdekbkp_application_common_dev_document_link_pkey PRIMARY KEY (application_common_dev_id, document_id);


--
-- Name: mobdekbkp_application_common_dev mobdekbkp_application_common_dev_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_common_dev
    ADD CONSTRAINT mobdekbkp_application_common_dev_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_application_common_entry_document_link mobdekbkp_application_common_entry_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_common_entry_document_link
    ADD CONSTRAINT mobdekbkp_application_common_entry_document_link_pkey PRIMARY KEY (application_common_entry_id, document_id);


--
-- Name: mobdekbkp_application_common_entry mobdekbkp_application_common_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_common_entry
    ADD CONSTRAINT mobdekbkp_application_common_entry_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_application_new_dev_entry mobdekbkp_application_new_dev_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_new_dev_entry
    ADD CONSTRAINT mobdekbkp_application_new_dev_entry_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_application_new_typonominal_add_document_link mobdekbkp_application_new_typonominal_add_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_new_typonominal_add_document_link
    ADD CONSTRAINT mobdekbkp_application_new_typonominal_add_document_link_pkey PRIMARY KEY (application_new_typonominal_add_id, document_id);


--
-- Name: mobdekbkp_application_new_typonominal_add mobdekbkp_application_new_typonominal_add_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_new_typonominal_add
    ADD CONSTRAINT mobdekbkp_application_new_typonominal_add_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_application_new_typonominal_dev mobdekbkp_application_new_typonominal_dev_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_new_typonominal_dev
    ADD CONSTRAINT mobdekbkp_application_new_typonominal_dev_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_application_okr_info mobdekbkp_application_okr_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_okr_info
    ADD CONSTRAINT mobdekbkp_application_okr_info_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_basic_information_import mobdekbkp_basic_information_import_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_basic_information_import
    ADD CONSTRAINT mobdekbkp_basic_information_import_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_basic_information mobdekbkp_basic_information_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_basic_information
    ADD CONSTRAINT mobdekbkp_basic_information_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_brand_flux mobdekbkp_brand_flux_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_brand_flux
    ADD CONSTRAINT mobdekbkp_brand_flux_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_brand_solder mobdekbkp_brand_solder_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_brand_solder
    ADD CONSTRAINT mobdekbkp_brand_solder_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_cables_wires_cords mobdekbkp_cables_wires_cords_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_cables_wires_cords
    ADD CONSTRAINT mobdekbkp_cables_wires_cords_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_capacitors mobdekbkp_capacitors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_capacitors
    ADD CONSTRAINT mobdekbkp_capacitors_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_cathode_ray_tubes mobdekbkp_cathode_ray_tubes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_cathode_ray_tubes
    ADD CONSTRAINT mobdekbkp_cathode_ray_tubes_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_company_license mobdekbkp_company_license_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company_license
    ADD CONSTRAINT mobdekbkp_company_license_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_company_need_application mobdekbkp_company_need_application_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company_need_application
    ADD CONSTRAINT mobdekbkp_company_need_application_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_company_need mobdekbkp_company_need_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company_need
    ADD CONSTRAINT mobdekbkp_company_need_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_company mobdekbkp_company_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company
    ADD CONSTRAINT mobdekbkp_company_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_company_type_entry mobdekbkp_company_type_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company_type_entry
    ADD CONSTRAINT mobdekbkp_company_type_entry_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_company_type mobdekbkp_company_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company_type
    ADD CONSTRAINT mobdekbkp_company_type_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_corpus mobdekbkp_corpus_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_corpus
    ADD CONSTRAINT mobdekbkp_corpus_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_cost mobdekbkp_cost_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_cost
    ADD CONSTRAINT mobdekbkp_cost_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_country mobdekbkp_country_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_country
    ADD CONSTRAINT mobdekbkp_country_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_currency mobdekbkp_currency_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_currency
    ADD CONSTRAINT mobdekbkp_currency_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_current_sources mobdekbkp_current_sources_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_current_sources
    ADD CONSTRAINT mobdekbkp_current_sources_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_custom_parameters mobdekbkp_custom_parameters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_custom_parameters
    ADD CONSTRAINT mobdekbkp_custom_parameters_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_device_complect_document_link mobdekbkp_device_complect_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_complect_document_link
    ADD CONSTRAINT mobdekbkp_device_complect_document_link_pkey PRIMARY KEY (device_complect_id, document_id);


--
-- Name: mobdekbkp_device_complect_list mobdekbkp_device_complect_list_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_complect_list
    ADD CONSTRAINT mobdekbkp_device_complect_list_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_device_complect mobdekbkp_device_complect_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_complect
    ADD CONSTRAINT mobdekbkp_device_complect_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_device_device_filter_conditions_link mobdekbkp_device_device_filter_conditions_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_device_filter_conditions_link
    ADD CONSTRAINT mobdekbkp_device_device_filter_conditions_link_pkey PRIMARY KEY (device_filter_conditions_id, device_id);


--
-- Name: mobdekbkp_device_document_link mobdekbkp_device_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_document_link
    ADD CONSTRAINT mobdekbkp_device_document_link_pkey PRIMARY KEY (device_id, document_id);


--
-- Name: mobdekbkp_device_filter_conditions mobdekbkp_device_filter_conditions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_filter_conditions
    ADD CONSTRAINT mobdekbkp_device_filter_conditions_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_device_list_project_addition_document_link mobdekbkp_device_list_project_addition_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_addition_document_link
    ADD CONSTRAINT mobdekbkp_device_list_project_addition_document_link_pkey PRIMARY KEY (device_list_project_addition_id, document_id);


--
-- Name: mobdekbkp_device_list_project_addition_entry mobdekbkp_device_list_project_addition_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_addition_entry
    ADD CONSTRAINT mobdekbkp_device_list_project_addition_entry_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_device_list_project_addition mobdekbkp_device_list_project_addition_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_addition
    ADD CONSTRAINT mobdekbkp_device_list_project_addition_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_device_list_project_application_document_link mobdekbkp_device_list_project_application_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_application_document_link
    ADD CONSTRAINT mobdekbkp_device_list_project_application_document_link_pkey PRIMARY KEY (device_list_project_application_id, document_id);


--
-- Name: mobdekbkp_device_list_project_application mobdekbkp_device_list_project_application_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_application
    ADD CONSTRAINT mobdekbkp_device_list_project_application_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_device_list_project_document_link mobdekbkp_device_list_project_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_document_link
    ADD CONSTRAINT mobdekbkp_device_list_project_document_link_pkey PRIMARY KEY (device_list_project_id, document_id);


--
-- Name: mobdekbkp_device_list_project_entry mobdekbkp_device_list_project_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_entry
    ADD CONSTRAINT mobdekbkp_device_list_project_entry_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_device_list_project mobdekbkp_device_list_project_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project
    ADD CONSTRAINT mobdekbkp_device_list_project_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_device_part_document_link mobdekbkp_device_part_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_document_link
    ADD CONSTRAINT mobdekbkp_device_part_document_link_pkey PRIMARY KEY (device_part_id, document_id);


--
-- Name: mobdekbkp_device_part_list_complect_document_link mobdekbkp_device_part_list_complect_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_complect_document_link
    ADD CONSTRAINT mobdekbkp_device_part_list_complect_document_link_pkey PRIMARY KEY (device_part_list_complect_id, document_id);


--
-- Name: mobdekbkp_device_part_list_complect_entry mobdekbkp_device_part_list_complect_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_complect_entry
    ADD CONSTRAINT mobdekbkp_device_part_list_complect_entry_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_device_part_list_complect mobdekbkp_device_part_list_complect_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_complect
    ADD CONSTRAINT mobdekbkp_device_part_list_complect_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_device_part_list_planned_document_link mobdekbkp_device_part_list_planned_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_planned_document_link
    ADD CONSTRAINT mobdekbkp_device_part_list_planned_document_link_pkey PRIMARY KEY (device_part_list_planned_id, document_id);


--
-- Name: mobdekbkp_device_part_list_planned_entry mobdekbkp_device_part_list_planned_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_planned_entry
    ADD CONSTRAINT mobdekbkp_device_part_list_planned_entry_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_device_part_list_planned mobdekbkp_device_part_list_planned_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_planned
    ADD CONSTRAINT mobdekbkp_device_part_list_planned_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_device_part mobdekbkp_device_part_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part
    ADD CONSTRAINT mobdekbkp_device_part_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_device_parts_entry mobdekbkp_device_parts_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_parts_entry
    ADD CONSTRAINT mobdekbkp_device_parts_entry_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_device mobdekbkp_device_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device
    ADD CONSTRAINT mobdekbkp_device_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_digital_chips mobdekbkp_digital_chips_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_digital_chips
    ADD CONSTRAINT mobdekbkp_digital_chips_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_electric_light_sources mobdekbkp_electric_light_sources_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_electric_light_sources
    ADD CONSTRAINT mobdekbkp_electric_light_sources_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_electric_vacuum_lamps mobdekbkp_electric_vacuum_lamps_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_electric_vacuum_lamps
    ADD CONSTRAINT mobdekbkp_electric_vacuum_lamps_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_electrical_connectors mobdekbkp_electrical_connectors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_electrical_connectors
    ADD CONSTRAINT mobdekbkp_electrical_connectors_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_factory mobdekbkp_factory_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_factory
    ADD CONSTRAINT mobdekbkp_factory_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_fiber_optic_components mobdekbkp_fiber_optic_components_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_fiber_optic_components
    ADD CONSTRAINT mobdekbkp_fiber_optic_components_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_functional_devices mobdekbkp_functional_devices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_functional_devices
    ADD CONSTRAINT mobdekbkp_functional_devices_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_glue_type mobdekbkp_glue_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_glue_type
    ADD CONSTRAINT mobdekbkp_glue_type_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_handbook_cad mobdekbkp_handbook_cad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_handbook_cad
    ADD CONSTRAINT mobdekbkp_handbook_cad_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_handbook_entry mobdekbkp_handbook_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_handbook_entry
    ADD CONSTRAINT mobdekbkp_handbook_entry_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_handbook mobdekbkp_handbook_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_handbook
    ADD CONSTRAINT mobdekbkp_handbook_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_import_class mobdekbkp_import_class_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_import_class
    ADD CONSTRAINT mobdekbkp_import_class_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_import_device mobdekbkp_import_device_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_import_device
    ADD CONSTRAINT mobdekbkp_import_device_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_import_docs_schemes mobdekbkp_import_docs_schemes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_import_docs_schemes
    ADD CONSTRAINT mobdekbkp_import_docs_schemes_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_install_method mobdekbkp_install_method_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_install_method
    ADD CONSTRAINT mobdekbkp_install_method_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_integrated_circuits mobdekbkp_integrated_circuits_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_integrated_circuits
    ADD CONSTRAINT mobdekbkp_integrated_circuits_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_lib_attributes_document_link mobdekbkp_lib_attributes_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_lib_attributes_document_link
    ADD CONSTRAINT mobdekbkp_lib_attributes_document_link_pkey PRIMARY KEY (lib_attributes_id, document_id);


--
-- Name: mobdekbkp_lib_attributes mobdekbkp_lib_attributes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_lib_attributes
    ADD CONSTRAINT mobdekbkp_lib_attributes_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_license_type mobdekbkp_license_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_license_type
    ADD CONSTRAINT mobdekbkp_license_type_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_log_file_data mobdekbkp_log_file_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_log_file_data
    ADD CONSTRAINT mobdekbkp_log_file_data_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_main_parameters mobdekbkp_main_parameters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_main_parameters
    ADD CONSTRAINT mobdekbkp_main_parameters_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_maping_entity mobdekbkp_maping_entity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_maping_entity
    ADD CONSTRAINT mobdekbkp_maping_entity_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_material mobdekbkp_material_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_material
    ADD CONSTRAINT mobdekbkp_material_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_microassemblies_multicrystals mobdekbkp_microassemblies_multicrystals_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_microassemblies_multicrystals
    ADD CONSTRAINT mobdekbkp_microassemblies_multicrystals_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_montage_parameters mobdekbkp_montage_parameters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_montage_parameters
    ADD CONSTRAINT mobdekbkp_montage_parameters_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_operation_conditions mobdekbkp_operation_conditions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_operation_conditions
    ADD CONSTRAINT mobdekbkp_operation_conditions_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_outer_certificate_tests_document_link mobdekbkp_outer_certificate_tests_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_certificate_tests_document_link
    ADD CONSTRAINT mobdekbkp_outer_certificate_tests_document_link_pkey PRIMARY KEY (outer_certificate_tests_id, document_id);


--
-- Name: mobdekbkp_outer_certificate_tests mobdekbkp_outer_certificate_tests_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_certificate_tests
    ADD CONSTRAINT mobdekbkp_outer_certificate_tests_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_outer_db_fail mobdekbkp_outer_db_fail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_db_fail
    ADD CONSTRAINT mobdekbkp_outer_db_fail_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_outer_db_refuse_defects mobdekbkp_outer_db_refuse_defects_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_db_refuse_defects
    ADD CONSTRAINT mobdekbkp_outer_db_refuse_defects_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_outer_db_refuse mobdekbkp_outer_db_refuse_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_db_refuse
    ADD CONSTRAINT mobdekbkp_outer_db_refuse_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_outer_db_refuse_tests mobdekbkp_outer_db_refuse_tests_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_db_refuse_tests
    ADD CONSTRAINT mobdekbkp_outer_db_refuse_tests_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_outer_entrance_tests_document_link mobdekbkp_outer_entrance_tests_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_entrance_tests_document_link
    ADD CONSTRAINT mobdekbkp_outer_entrance_tests_document_link_pkey PRIMARY KEY (outer_entrance_tests_id, document_id);


--
-- Name: mobdekbkp_outer_entrance_tests mobdekbkp_outer_entrance_tests_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_entrance_tests
    ADD CONSTRAINT mobdekbkp_outer_entrance_tests_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_outer_fail_and_refuses_document_link mobdekbkp_outer_fail_and_refuses_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_fail_and_refuses_document_link
    ADD CONSTRAINT mobdekbkp_outer_fail_and_refuses_document_link_pkey PRIMARY KEY (outer_fail_and_refuses_id, document_id);


--
-- Name: mobdekbkp_outer_fail_and_refuses mobdekbkp_outer_fail_and_refuses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_fail_and_refuses
    ADD CONSTRAINT mobdekbkp_outer_fail_and_refuses_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_outer_information_source mobdekbkp_outer_information_source_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_information_source
    ADD CONSTRAINT mobdekbkp_outer_information_source_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_outer_list_allowing_document_link mobdekbkp_outer_list_allowing_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_list_allowing_document_link
    ADD CONSTRAINT mobdekbkp_outer_list_allowing_document_link_pkey PRIMARY KEY (outer_list_allowing_id, document_id);


--
-- Name: mobdekbkp_outer_list_allowing_entry mobdekbkp_outer_list_allowing_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_list_allowing_entry
    ADD CONSTRAINT mobdekbkp_outer_list_allowing_entry_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_outer_list_allowing mobdekbkp_outer_list_allowing_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_list_allowing
    ADD CONSTRAINT mobdekbkp_outer_list_allowing_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_outer_list_type mobdekbkp_outer_list_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_list_type
    ADD CONSTRAINT mobdekbkp_outer_list_type_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_outer_persistence_info mobdekbkp_outer_persistence_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_persistence_info
    ADD CONSTRAINT mobdekbkp_outer_persistence_info_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_outer_rejection_document_link mobdekbkp_outer_rejection_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_rejection_document_link
    ADD CONSTRAINT mobdekbkp_outer_rejection_document_link_pkey PRIMARY KEY (outer_rejection_id, document_id);


--
-- Name: mobdekbkp_outer_rejection mobdekbkp_outer_rejection_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_rejection
    ADD CONSTRAINT mobdekbkp_outer_rejection_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_parameter_category mobdekbkp_parameter_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_parameter_category
    ADD CONSTRAINT mobdekbkp_parameter_category_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_parameter mobdekbkp_parameter_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_parameter
    ADD CONSTRAINT mobdekbkp_parameter_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_parameter_value mobdekbkp_parameter_value_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_parameter_value
    ADD CONSTRAINT mobdekbkp_parameter_value_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_parameters_for_purchasing mobdekbkp_parameters_for_purchasing_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_parameters_for_purchasing
    ADD CONSTRAINT mobdekbkp_parameters_for_purchasing_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_photosensitive_devices mobdekbkp_photosensitive_devices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_photosensitive_devices
    ADD CONSTRAINT mobdekbkp_photosensitive_devices_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_piezoelectric_devices mobdekbkp_piezoelectric_devices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_piezoelectric_devices
    ADD CONSTRAINT mobdekbkp_piezoelectric_devices_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_plis mobdekbkp_plis_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_plis
    ADD CONSTRAINT mobdekbkp_plis_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_products_ferrites_magnetodielectrics mobdekbkp_products_ferrites_magnetodielectrics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_products_ferrites_magnetodielectrics
    ADD CONSTRAINT mobdekbkp_products_ferrites_magnetodielectrics_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_quantum_electronics_products mobdekbkp_quantum_electronics_products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_quantum_electronics_products
    ADD CONSTRAINT mobdekbkp_quantum_electronics_products_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_reliability_indicators mobdekbkp_reliability_indicators_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_reliability_indicators
    ADD CONSTRAINT mobdekbkp_reliability_indicators_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_resistors mobdekbkp_resistors_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_resistors
    ADD CONSTRAINT mobdekbkp_resistors_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_sapr_data mobdekbkp_sapr_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_sapr_data
    ADD CONSTRAINT mobdekbkp_sapr_data_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_semiconductor_diodes mobdekbkp_semiconductor_diodes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_semiconductor_diodes
    ADD CONSTRAINT mobdekbkp_semiconductor_diodes_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_semiconductor_emitters mobdekbkp_semiconductor_emitters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_semiconductor_emitters
    ADD CONSTRAINT mobdekbkp_semiconductor_emitters_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_sign_synthesizing_indicators mobdekbkp_sign_synthesizing_indicators_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_sign_synthesizing_indicators
    ADD CONSTRAINT mobdekbkp_sign_synthesizing_indicators_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_small_electric_machines mobdekbkp_small_electric_machines_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_small_electric_machines
    ADD CONSTRAINT mobdekbkp_small_electric_machines_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_str_lib mobdekbkp_str_lib_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_str_lib
    ADD CONSTRAINT mobdekbkp_str_lib_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_str_lib_settings mobdekbkp_str_lib_settings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_str_lib_settings
    ADD CONSTRAINT mobdekbkp_str_lib_settings_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_str_lib_settings_str_lib_link mobdekbkp_str_lib_settings_str_lib_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_str_lib_settings_str_lib_link
    ADD CONSTRAINT mobdekbkp_str_lib_settings_str_lib_link_pkey PRIMARY KEY (str_lib_settings_id, str_lib_id);


--
-- Name: mobdekbkp_str_rec mobdekbkp_str_rec_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_str_rec
    ADD CONSTRAINT mobdekbkp_str_rec_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_str_rec_settings mobdekbkp_str_rec_settings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_str_rec_settings
    ADD CONSTRAINT mobdekbkp_str_rec_settings_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_str_rec_settings_str_rec_link mobdekbkp_str_rec_settings_str_rec_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_str_rec_settings_str_rec_link
    ADD CONSTRAINT mobdekbkp_str_rec_settings_str_rec_link_pkey PRIMARY KEY (str_rec_settings_id, str_rec_id);


--
-- Name: mobdekbkp_substrate_entry mobdekbkp_substrate_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_substrate_entry
    ADD CONSTRAINT mobdekbkp_substrate_entry_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_substrate mobdekbkp_substrate_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_substrate
    ADD CONSTRAINT mobdekbkp_substrate_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_suppliers mobdekbkp_suppliers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_suppliers
    ADD CONSTRAINT mobdekbkp_suppliers_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_support_info mobdekbkp_support_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_support_info
    ADD CONSTRAINT mobdekbkp_support_info_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_svch mobdekbkp_svch_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_svch
    ADD CONSTRAINT mobdekbkp_svch_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_switching_products mobdekbkp_switching_products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_switching_products
    ADD CONSTRAINT mobdekbkp_switching_products_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_technical_parameters mobdekbkp_technical_parameters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_technical_parameters
    ADD CONSTRAINT mobdekbkp_technical_parameters_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_terms_and_definitions mobdekbkp_terms_and_definitions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_terms_and_definitions
    ADD CONSTRAINT mobdekbkp_terms_and_definitions_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_transformers_chokes mobdekbkp_transformers_chokes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_transformers_chokes
    ADD CONSTRAINT mobdekbkp_transformers_chokes_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_type_calicoholder_entry mobdekbkp_type_calicoholder_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_calicoholder_entry
    ADD CONSTRAINT mobdekbkp_type_calicoholder_entry_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_type_class_characteristic mobdekbkp_type_class_characteristic_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_class_characteristic
    ADD CONSTRAINT mobdekbkp_type_class_characteristic_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_type_class mobdekbkp_type_class_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_class
    ADD CONSTRAINT mobdekbkp_type_class_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_type_class_type mobdekbkp_type_class_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_class_type
    ADD CONSTRAINT mobdekbkp_type_class_type_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_type_climatic_implementation mobdekbkp_type_climatic_implementation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_climatic_implementation
    ADD CONSTRAINT mobdekbkp_type_climatic_implementation_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_type_document_link_appscheme mobdekbkp_type_document_link_appscheme_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_document_link_appscheme
    ADD CONSTRAINT mobdekbkp_type_document_link_appscheme_pkey PRIMARY KEY (type_id, document_id);


--
-- Name: mobdekbkp_type_document_link_delivery mobdekbkp_type_document_link_delivery_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_document_link_delivery
    ADD CONSTRAINT mobdekbkp_type_document_link_delivery_pkey PRIMARY KEY (type_id, document_id);


--
-- Name: mobdekbkp_type_manufacturer_entry mobdekbkp_type_manufacturer_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_manufacturer_entry
    ADD CONSTRAINT mobdekbkp_type_manufacturer_entry_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_type_math_model_parameters_document_link mobdekbkp_type_math_model_parameters_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_math_model_parameters_document_link
    ADD CONSTRAINT mobdekbkp_type_math_model_parameters_document_link_pkey PRIMARY KEY (type_math_model_parameters_id, document_id);


--
-- Name: mobdekbkp_type_math_model_parameters mobdekbkp_type_math_model_parameters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_math_model_parameters
    ADD CONSTRAINT mobdekbkp_type_math_model_parameters_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_type mobdekbkp_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type
    ADD CONSTRAINT mobdekbkp_type_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_type_provider_entry mobdekbkp_type_provider_entry_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_provider_entry
    ADD CONSTRAINT mobdekbkp_type_provider_entry_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_typonominal_analog mobdekbkp_typonominal_analog_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_analog
    ADD CONSTRAINT mobdekbkp_typonominal_analog_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_typonominal_body_document_link mobdekbkp_typonominal_body_document_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_body_document_link
    ADD CONSTRAINT mobdekbkp_typonominal_body_document_link_pkey PRIMARY KEY (typonominal_body_id, document_id);


--
-- Name: mobdekbkp_typonominal_body mobdekbkp_typonominal_body_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_body
    ADD CONSTRAINT mobdekbkp_typonominal_body_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_typonominal_install_parameters mobdekbkp_typonominal_install_parameters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_install_parameters
    ADD CONSTRAINT mobdekbkp_typonominal_install_parameters_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_typonominal mobdekbkp_typonominal_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal
    ADD CONSTRAINT mobdekbkp_typonominal_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_typonominal_purchase_parameters mobdekbkp_typonominal_purchase_parameters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_purchase_parameters
    ADD CONSTRAINT mobdekbkp_typonominal_purchase_parameters_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_typonominal_q_l_import_set_typonominal_q_l_import mobdekbkp_typonominal_q_l_import_set_typonominal_q_l_impor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_q_l_import_set_typonominal_q_l_import
    ADD CONSTRAINT mobdekbkp_typonominal_q_l_import_set_typonominal_q_l_impor_pkey PRIMARY KEY (typonominal_quality_level_import_settings_id, typonominal_quality_level_import_id);


--
-- Name: mobdekbkp_typonominal_quality_level_import mobdekbkp_typonominal_quality_level_import_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_quality_level_import
    ADD CONSTRAINT mobdekbkp_typonominal_quality_level_import_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_typonominal_quality_level_import_settings mobdekbkp_typonominal_quality_level_import_settings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_quality_level_import_settings
    ADD CONSTRAINT mobdekbkp_typonominal_quality_level_import_settings_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_typonominal_quality_level_native mobdekbkp_typonominal_quality_level_native_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_quality_level_native
    ADD CONSTRAINT mobdekbkp_typonominal_quality_level_native_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_unit_dev mobdekbkp_unit_dev_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_unit_dev
    ADD CONSTRAINT mobdekbkp_unit_dev_pkey PRIMARY KEY (id);


--
-- Name: mobdekbkp_unit_val mobdekbkp_unit_val_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_unit_val
    ADD CONSTRAINT mobdekbkp_unit_val_pkey PRIMARY KEY (id);


--
-- Name: notificationsusers_message_meta notificationsusers_message_meta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notificationsusers_message_meta
    ADD CONSTRAINT notificationsusers_message_meta_pkey PRIMARY KEY (id);


--
-- Name: notificationsusers_message notificationsusers_message_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notificationsusers_message
    ADD CONSTRAINT notificationsusers_message_pkey PRIMARY KEY (id);


--
-- Name: notificationsusers_message_text notificationsusers_message_text_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notificationsusers_message_text
    ADD CONSTRAINT notificationsusers_message_text_pkey PRIMARY KEY (id);


--
-- Name: report_group report_group_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.report_group
    ADD CONSTRAINT report_group_pkey PRIMARY KEY (id);


--
-- Name: report_report report_report_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.report_report
    ADD CONSTRAINT report_report_pkey PRIMARY KEY (id);


--
-- Name: report_template report_template_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.report_template
    ADD CONSTRAINT report_template_pkey PRIMARY KEY (id);


--
-- Name: reviews_entities_for_moderation reviews_entities_for_moderation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews_entities_for_moderation
    ADD CONSTRAINT reviews_entities_for_moderation_pkey PRIMARY KEY (id);


--
-- Name: reviews_entities_for_moderation_user_link reviews_entities_for_moderation_user_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews_entities_for_moderation_user_link
    ADD CONSTRAINT reviews_entities_for_moderation_user_link_pkey PRIMARY KEY (entities_for_moderation_id, user_id);


--
-- Name: reviews_moderation_property reviews_moderation_property_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews_moderation_property
    ADD CONSTRAINT reviews_moderation_property_pkey PRIMARY KEY (id);


--
-- Name: reviews_moderation_property_user_link reviews_moderation_property_user_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews_moderation_property_user_link
    ADD CONSTRAINT reviews_moderation_property_user_link_pkey PRIMARY KEY (moderation_property_id, user_id);


--
-- Name: reviews_review reviews_review_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews_review
    ADD CONSTRAINT reviews_review_pkey PRIMARY KEY (id);


--
-- Name: rulesmodule_rule_data_script rulesmodule_rule_data_script_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rulesmodule_rule_data_script
    ADD CONSTRAINT rulesmodule_rule_data_script_pkey PRIMARY KEY (id);


--
-- Name: rulesmodule_rule_manager rulesmodule_rule_manager_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rulesmodule_rule_manager
    ADD CONSTRAINT rulesmodule_rule_manager_pkey PRIMARY KEY (id);


--
-- Name: rulesmodule_rule_manager_rule_data_script_link rulesmodule_rule_manager_rule_data_script_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rulesmodule_rule_manager_rule_data_script_link
    ADD CONSTRAINT rulesmodule_rule_manager_rule_data_script_link_pkey PRIMARY KEY (rule_manager_id, rule_data_script_id);


--
-- Name: rulesmodule_rule_manager_rule_script_link rulesmodule_rule_manager_rule_script_link_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rulesmodule_rule_manager_rule_script_link
    ADD CONSTRAINT rulesmodule_rule_manager_rule_script_link_pkey PRIMARY KEY (rule_manager_id, rule_script_id);


--
-- Name: rulesmodule_rule_script rulesmodule_rule_script_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rulesmodule_rule_script
    ADD CONSTRAINT rulesmodule_rule_script_pkey PRIMARY KEY (id);


--
-- Name: sec_constraint sec_constraint_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_constraint
    ADD CONSTRAINT sec_constraint_pkey PRIMARY KEY (id);


--
-- Name: sec_entity_log sec_entity_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_entity_log
    ADD CONSTRAINT sec_entity_log_pkey PRIMARY KEY (id);


--
-- Name: sec_filter sec_filter_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_filter
    ADD CONSTRAINT sec_filter_pkey PRIMARY KEY (id);


--
-- Name: sec_group_hierarchy sec_group_hierarchy_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_group_hierarchy
    ADD CONSTRAINT sec_group_hierarchy_pkey PRIMARY KEY (id);


--
-- Name: sec_group sec_group_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_group
    ADD CONSTRAINT sec_group_pkey PRIMARY KEY (id);


--
-- Name: sec_localized_constraint_msg sec_localized_constraint_msg_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_localized_constraint_msg
    ADD CONSTRAINT sec_localized_constraint_msg_pkey PRIMARY KEY (id);


--
-- Name: sec_logged_attr sec_logged_attr_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_logged_attr
    ADD CONSTRAINT sec_logged_attr_pkey PRIMARY KEY (id);


--
-- Name: sec_logged_attr sec_logged_attr_uniq_name; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_logged_attr
    ADD CONSTRAINT sec_logged_attr_uniq_name UNIQUE (entity_id, name);


--
-- Name: sec_logged_entity sec_logged_entity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_logged_entity
    ADD CONSTRAINT sec_logged_entity_pkey PRIMARY KEY (id);


--
-- Name: sec_logged_entity sec_logged_entity_uniq_name; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_logged_entity
    ADD CONSTRAINT sec_logged_entity_uniq_name UNIQUE (name);


--
-- Name: sec_permission sec_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_permission
    ADD CONSTRAINT sec_permission_pkey PRIMARY KEY (id);


--
-- Name: sec_presentation sec_presentation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_presentation
    ADD CONSTRAINT sec_presentation_pkey PRIMARY KEY (id);


--
-- Name: sec_remember_me sec_remember_me_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_remember_me
    ADD CONSTRAINT sec_remember_me_pkey PRIMARY KEY (id);


--
-- Name: sec_role sec_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_role
    ADD CONSTRAINT sec_role_pkey PRIMARY KEY (id);


--
-- Name: sec_screen_history sec_screen_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_screen_history
    ADD CONSTRAINT sec_screen_history_pkey PRIMARY KEY (id);


--
-- Name: sec_search_folder sec_search_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_search_folder
    ADD CONSTRAINT sec_search_folder_pkey PRIMARY KEY (folder_id);


--
-- Name: sec_session_attr sec_session_attr_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_session_attr
    ADD CONSTRAINT sec_session_attr_pkey PRIMARY KEY (id);


--
-- Name: sec_session_log sec_session_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_session_log
    ADD CONSTRAINT sec_session_log_pkey PRIMARY KEY (id);


--
-- Name: sec_user sec_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_user
    ADD CONSTRAINT sec_user_pkey PRIMARY KEY (id);


--
-- Name: sec_user_role sec_user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_user_role
    ADD CONSTRAINT sec_user_role_pkey PRIMARY KEY (id);


--
-- Name: sec_user_setting sec_user_setting_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_user_setting
    ADD CONSTRAINT sec_user_setting_pkey PRIMARY KEY (id);


--
-- Name: sec_user_setting sec_user_setting_uniq; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_user_setting
    ADD CONSTRAINT sec_user_setting_uniq UNIQUE (user_id, name, client_type);


--
-- Name: sec_user_substitution sec_user_substitution_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_user_substitution
    ADD CONSTRAINT sec_user_substitution_pkey PRIMARY KEY (id);


--
-- Name: sys_access_token sys_access_token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_access_token
    ADD CONSTRAINT sys_access_token_pkey PRIMARY KEY (id);


--
-- Name: sys_app_folder sys_app_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_app_folder
    ADD CONSTRAINT sys_app_folder_pkey PRIMARY KEY (folder_id);


--
-- Name: sys_attr_value sys_attr_value_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_attr_value
    ADD CONSTRAINT sys_attr_value_pkey PRIMARY KEY (id);


--
-- Name: sys_category_attr sys_category_attr_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_category_attr
    ADD CONSTRAINT sys_category_attr_pkey PRIMARY KEY (id);


--
-- Name: sys_category sys_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_category
    ADD CONSTRAINT sys_category_pkey PRIMARY KEY (id);


--
-- Name: sys_config sys_config_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_config
    ADD CONSTRAINT sys_config_pkey PRIMARY KEY (id);


--
-- Name: sys_db_changelog sys_db_changelog_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_db_changelog
    ADD CONSTRAINT sys_db_changelog_pkey PRIMARY KEY (script_name);


--
-- Name: sys_entity_snapshot sys_entity_snapshot_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_entity_snapshot
    ADD CONSTRAINT sys_entity_snapshot_pkey PRIMARY KEY (id);


--
-- Name: sys_entity_statistics sys_entity_statistics_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_entity_statistics
    ADD CONSTRAINT sys_entity_statistics_pkey PRIMARY KEY (id);


--
-- Name: sys_file sys_file_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_file
    ADD CONSTRAINT sys_file_pkey PRIMARY KEY (id);


--
-- Name: sys_folder sys_folder_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_folder
    ADD CONSTRAINT sys_folder_pkey PRIMARY KEY (id);


--
-- Name: sys_fts_queue sys_fts_queue_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_fts_queue
    ADD CONSTRAINT sys_fts_queue_pkey PRIMARY KEY (id);


--
-- Name: sys_jmx_instance sys_jmx_instance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_jmx_instance
    ADD CONSTRAINT sys_jmx_instance_pkey PRIMARY KEY (id);


--
-- Name: sys_lock_config sys_lock_config_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_lock_config
    ADD CONSTRAINT sys_lock_config_pkey PRIMARY KEY (id);


--
-- Name: sys_query_result sys_query_result_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_query_result
    ADD CONSTRAINT sys_query_result_pkey PRIMARY KEY (id);


--
-- Name: sys_refresh_token sys_refresh_token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_refresh_token
    ADD CONSTRAINT sys_refresh_token_pkey PRIMARY KEY (id);


--
-- Name: sys_scheduled_execution sys_scheduled_execution_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_scheduled_execution
    ADD CONSTRAINT sys_scheduled_execution_pkey PRIMARY KEY (id);


--
-- Name: sys_scheduled_task sys_scheduled_task_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_scheduled_task
    ADD CONSTRAINT sys_scheduled_task_pkey PRIMARY KEY (id);


--
-- Name: sys_sending_attachment sys_sending_attachment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_sending_attachment
    ADD CONSTRAINT sys_sending_attachment_pkey PRIMARY KEY (id);


--
-- Name: sys_sending_message sys_sending_message_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_sending_message
    ADD CONSTRAINT sys_sending_message_pkey PRIMARY KEY (id);


--
-- Name: act_idx_athrz_procedef; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_athrz_procedef ON public.act_ru_identitylink USING btree (proc_def_id_);


--
-- Name: act_idx_bytear_depl; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_bytear_depl ON public.act_ge_bytearray USING btree (deployment_id_);


--
-- Name: act_idx_event_subscr; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_event_subscr ON public.act_ru_event_subscr USING btree (execution_id_);


--
-- Name: act_idx_event_subscr_config_; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_event_subscr_config_ ON public.act_ru_event_subscr USING btree (configuration_);


--
-- Name: act_idx_exe_parent; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_exe_parent ON public.act_ru_execution USING btree (parent_id_);


--
-- Name: act_idx_exe_procdef; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_exe_procdef ON public.act_ru_execution USING btree (proc_def_id_);


--
-- Name: act_idx_exe_procinst; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_exe_procinst ON public.act_ru_execution USING btree (proc_inst_id_);


--
-- Name: act_idx_exe_super; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_exe_super ON public.act_ru_execution USING btree (super_exec_);


--
-- Name: act_idx_exec_buskey; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_exec_buskey ON public.act_ru_execution USING btree (business_key_);


--
-- Name: act_idx_hi_act_inst_end; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_act_inst_end ON public.act_hi_actinst USING btree (end_time_);


--
-- Name: act_idx_hi_act_inst_exec; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_act_inst_exec ON public.act_hi_actinst USING btree (execution_id_, act_id_);


--
-- Name: act_idx_hi_act_inst_procinst; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_act_inst_procinst ON public.act_hi_actinst USING btree (proc_inst_id_, act_id_);


--
-- Name: act_idx_hi_act_inst_start; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_act_inst_start ON public.act_hi_actinst USING btree (start_time_);


--
-- Name: act_idx_hi_detail_act_inst; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_detail_act_inst ON public.act_hi_detail USING btree (act_inst_id_);


--
-- Name: act_idx_hi_detail_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_detail_name ON public.act_hi_detail USING btree (name_);


--
-- Name: act_idx_hi_detail_proc_inst; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_detail_proc_inst ON public.act_hi_detail USING btree (proc_inst_id_);


--
-- Name: act_idx_hi_detail_task_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_detail_task_id ON public.act_hi_detail USING btree (task_id_);


--
-- Name: act_idx_hi_detail_time; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_detail_time ON public.act_hi_detail USING btree (time_);


--
-- Name: act_idx_hi_ident_lnk_procinst; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_ident_lnk_procinst ON public.act_hi_identitylink USING btree (proc_inst_id_);


--
-- Name: act_idx_hi_ident_lnk_task; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_ident_lnk_task ON public.act_hi_identitylink USING btree (task_id_);


--
-- Name: act_idx_hi_ident_lnk_user; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_ident_lnk_user ON public.act_hi_identitylink USING btree (user_id_);


--
-- Name: act_idx_hi_pro_i_buskey; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_pro_i_buskey ON public.act_hi_procinst USING btree (business_key_);


--
-- Name: act_idx_hi_pro_inst_end; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_pro_inst_end ON public.act_hi_procinst USING btree (end_time_);


--
-- Name: act_idx_hi_procvar_name_type; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_procvar_name_type ON public.act_hi_varinst USING btree (name_, var_type_);


--
-- Name: act_idx_hi_procvar_proc_inst; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_procvar_proc_inst ON public.act_hi_varinst USING btree (proc_inst_id_);


--
-- Name: act_idx_hi_procvar_task_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_procvar_task_id ON public.act_hi_varinst USING btree (task_id_);


--
-- Name: act_idx_hi_task_inst_procinst; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_hi_task_inst_procinst ON public.act_hi_taskinst USING btree (proc_inst_id_);


--
-- Name: act_idx_ident_lnk_group; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_ident_lnk_group ON public.act_ru_identitylink USING btree (group_id_);


--
-- Name: act_idx_ident_lnk_user; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_ident_lnk_user ON public.act_ru_identitylink USING btree (user_id_);


--
-- Name: act_idx_idl_procinst; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_idl_procinst ON public.act_ru_identitylink USING btree (proc_inst_id_);


--
-- Name: act_idx_job_exception; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_job_exception ON public.act_ru_job USING btree (exception_stack_id_);


--
-- Name: act_idx_memb_group; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_memb_group ON public.act_id_membership USING btree (group_id_);


--
-- Name: act_idx_memb_user; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_memb_user ON public.act_id_membership USING btree (user_id_);


--
-- Name: act_idx_model_deployment; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_model_deployment ON public.act_re_model USING btree (deployment_id_);


--
-- Name: act_idx_model_source; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_model_source ON public.act_re_model USING btree (editor_source_value_id_);


--
-- Name: act_idx_model_source_extra; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_model_source_extra ON public.act_re_model USING btree (editor_source_extra_value_id_);


--
-- Name: act_idx_procdef_info_json; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_procdef_info_json ON public.act_procdef_info USING btree (info_json_id_);


--
-- Name: act_idx_procdef_info_proc; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_procdef_info_proc ON public.act_procdef_info USING btree (proc_def_id_);


--
-- Name: act_idx_task_create; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_task_create ON public.act_ru_task USING btree (create_time_);


--
-- Name: act_idx_task_exec; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_task_exec ON public.act_ru_task USING btree (execution_id_);


--
-- Name: act_idx_task_procdef; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_task_procdef ON public.act_ru_task USING btree (proc_def_id_);


--
-- Name: act_idx_task_procinst; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_task_procinst ON public.act_ru_task USING btree (proc_inst_id_);


--
-- Name: act_idx_tskass_task; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_tskass_task ON public.act_ru_identitylink USING btree (task_id_);


--
-- Name: act_idx_var_bytearray; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_var_bytearray ON public.act_ru_variable USING btree (bytearray_id_);


--
-- Name: act_idx_var_exe; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_var_exe ON public.act_ru_variable USING btree (execution_id_);


--
-- Name: act_idx_var_procinst; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_var_procinst ON public.act_ru_variable USING btree (proc_inst_id_);


--
-- Name: act_idx_variable_task_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX act_idx_variable_task_id ON public.act_ru_variable USING btree (task_id_);


--
-- Name: idx_bpm_proc_actor_proc_instance; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_bpm_proc_actor_proc_instance ON public.bpm_proc_actor USING btree (proc_instance_id);


--
-- Name: idx_bpm_proc_actor_proc_role; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_bpm_proc_actor_proc_role ON public.bpm_proc_actor USING btree (proc_role_id);


--
-- Name: idx_bpm_proc_actor_user; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_bpm_proc_actor_user ON public.bpm_proc_actor USING btree (user_id);


--
-- Name: idx_bpm_proc_attachment_author; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_bpm_proc_attachment_author ON public.bpm_proc_attachment USING btree (author_id);


--
-- Name: idx_bpm_proc_attachment_file; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_bpm_proc_attachment_file ON public.bpm_proc_attachment USING btree (file_id);


--
-- Name: idx_bpm_proc_attachment_proc_instance; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_bpm_proc_attachment_proc_instance ON public.bpm_proc_attachment USING btree (proc_instance_id);


--
-- Name: idx_bpm_proc_attachment_proc_task; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_bpm_proc_attachment_proc_task ON public.bpm_proc_attachment USING btree (proc_task_id);


--
-- Name: idx_bpm_proc_attachment_type; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_bpm_proc_attachment_type ON public.bpm_proc_attachment USING btree (type_id);


--
-- Name: idx_bpm_proc_definition_model; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_bpm_proc_definition_model ON public.bpm_proc_definition USING btree (model_id);


--
-- Name: idx_bpm_proc_instance_proc_definition; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_bpm_proc_instance_proc_definition ON public.bpm_proc_instance USING btree (proc_definition_id);


--
-- Name: idx_bpm_proc_instance_started_by; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_bpm_proc_instance_started_by ON public.bpm_proc_instance USING btree (started_by_id);


--
-- Name: idx_bpm_proc_model_uniq_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_bpm_proc_model_uniq_name ON public.bpm_proc_model USING btree (name) WHERE (delete_ts IS NULL);


--
-- Name: idx_bpm_proc_role_proc_definition; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_bpm_proc_role_proc_definition ON public.bpm_proc_role USING btree (proc_definition_id);


--
-- Name: idx_bpm_proc_task_proc_actor; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_bpm_proc_task_proc_actor ON public.bpm_proc_task USING btree (proc_actor_id);


--
-- Name: idx_bpm_proc_task_proc_instance; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_bpm_proc_task_proc_instance ON public.bpm_proc_task USING btree (proc_instance_id);


--
-- Name: idx_bpm_stencil_set_uniq_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_bpm_stencil_set_uniq_name ON public.bpm_stencil_set USING btree (name) WHERE (delete_ts IS NULL);


--
-- Name: idx_cat_attr_ent_type_and_code; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_cat_attr_ent_type_and_code ON public.sys_category_attr USING btree (category_entity_type, code) WHERE (delete_ts IS NULL);


--
-- Name: idx_ddcrd_diagnose_execution_log_execution_result_file; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_ddcrd_diagnose_execution_log_execution_result_file ON public.ddcrd_diagnose_execution_log USING btree (execution_result_file_id);


--
-- Name: idx_discuss_comment_on_author; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_discuss_comment_on_author ON public.discuss_comment USING btree (author_id);


--
-- Name: idx_discuss_comment_on_parent; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_discuss_comment_on_parent ON public.discuss_comment USING btree (parent_id);


--
-- Name: idx_documents_document_on_document_type; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_documents_document_on_document_type ON public.documents_document USING btree (document_type_id);


--
-- Name: idx_documents_document_restriction_uk_key_; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_documents_document_restriction_uk_key_ ON public.documents_document_restriction USING btree (key_) WHERE (delete_ts IS NULL);


--
-- Name: idx_documents_document_type_on_parent; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_documents_document_type_on_parent ON public.documents_document_type USING btree (parent_id);


--
-- Name: idx_mobdekbkp_advanced_setting_import_device; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_advanced_setting_import_device ON public.mobdekbkp_advanced_setting USING btree (import_device_id);


--
-- Name: idx_mobdekbkp_alter_class_gost2710_on_parent; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_alter_class_gost2710_on_parent ON public.mobdekbkp_alter_class_gost2710 USING btree (parent_id);


--
-- Name: idx_mobdekbkp_alter_class_mil_on_parent; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_alter_class_mil_on_parent ON public.mobdekbkp_alter_class_mil USING btree (parent_id);


--
-- Name: idx_mobdekbkp_alter_class_reliability_on_parent; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_alter_class_reliability_on_parent ON public.mobdekbkp_alter_class_reliability USING btree (parent_id);


--
-- Name: idx_mobdekbkp_alter_class_reliability_on_type; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_alter_class_reliability_on_type ON public.mobdekbkp_alter_class_reliability USING btree (type_id);


--
-- Name: idx_mobdekbkp_applicability_devices_import_device; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_applicability_devices_import_device ON public.mobdekbkp_applicability_devices USING btree (import_device_id);


--
-- Name: idx_mobdekbkp_applicability_devices_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_applicability_devices_typonominal ON public.mobdekbkp_applicability_devices USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_application_new_dev_entry_on_application; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_application_new_dev_entry_on_application ON public.mobdekbkp_application_new_dev_entry USING btree (application_id);


--
-- Name: idx_mobdekbkp_application_okr_info_on_application_common_entry; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_application_okr_info_on_application_common_entry ON public.mobdekbkp_application_okr_info USING btree (application_common_entry_id);


--
-- Name: idx_mobdekbkp_application_okr_info_on_responsible; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_application_okr_info_on_responsible ON public.mobdekbkp_application_okr_info USING btree (responsible_id);


--
-- Name: idx_mobdekbkp_appliccommonentry_on_application_common_dev; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_appliccommonentry_on_application_common_dev ON public.mobdekbkp_application_common_entry USING btree (application_common_dev_id);


--
-- Name: idx_mobdekbkp_applinewdeventry_on_application_common_entry; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_applinewdeventry_on_application_common_entry ON public.mobdekbkp_application_new_dev_entry USING btree (application_common_entry_id);


--
-- Name: idx_mobdekbkp_basic_information_import_import_device; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_basic_information_import_import_device ON public.mobdekbkp_basic_information_import USING btree (import_device_id);


--
-- Name: idx_mobdekbkp_company_license_on_company; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_company_license_on_company ON public.mobdekbkp_company_license USING btree (company_id);


--
-- Name: idx_mobdekbkp_company_license_on_type; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_company_license_on_type ON public.mobdekbkp_company_license USING btree (type_id);


--
-- Name: idx_mobdekbkp_company_need_on_company; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_company_need_on_company ON public.mobdekbkp_company_need USING btree (company_id);


--
-- Name: idx_mobdekbkp_company_need_on_company_need_application; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_company_need_on_company_need_application ON public.mobdekbkp_company_need USING btree (company_need_application_id);


--
-- Name: idx_mobdekbkp_company_need_on_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_company_need_on_typonominal ON public.mobdekbkp_company_need USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_company_on_country; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_company_on_country ON public.mobdekbkp_company USING btree (country_id);


--
-- Name: idx_mobdekbkp_company_type_entry_on_company; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_company_type_entry_on_company ON public.mobdekbkp_company_type_entry USING btree (company_id);


--
-- Name: idx_mobdekbkp_corpus_import_device; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_corpus_import_device ON public.mobdekbkp_corpus USING btree (import_device_id);


--
-- Name: idx_mobdekbkp_cost_on_currency; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_cost_on_currency ON public.mobdekbkp_cost USING btree (currency_id);


--
-- Name: idx_mobdekbkp_custom_parameters_import_device; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_custom_parameters_import_device ON public.mobdekbkp_custom_parameters USING btree (import_device_id);


--
-- Name: idx_mobdekbkp_custom_parameters_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_custom_parameters_typonominal ON public.mobdekbkp_custom_parameters USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_device_complect_on_device; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_device_complect_on_device ON public.mobdekbkp_device_complect USING btree (device_id);


--
-- Name: idx_mobdekbkp_device_list_project_addition_entry_on_edited; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_device_list_project_addition_entry_on_edited ON public.mobdekbkp_device_list_project_addition_entry USING btree (edited_id);


--
-- Name: idx_mobdekbkp_device_list_project_entry_on_device_list_project; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_device_list_project_entry_on_device_list_project ON public.mobdekbkp_device_list_project_entry USING btree (device_list_project_id);


--
-- Name: idx_mobdekbkp_device_list_project_entry_on_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_device_list_project_entry_on_typonominal ON public.mobdekbkp_device_list_project_entry USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_device_on_developer; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_device_on_developer ON public.mobdekbkp_device USING btree (developer_id);


--
-- Name: idx_mobdekbkp_device_part_list_complect_entry_on_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_device_part_list_complect_entry_on_typonominal ON public.mobdekbkp_device_part_list_complect_entry USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_device_part_list_complect_on_device_complect; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_device_part_list_complect_on_device_complect ON public.mobdekbkp_device_part_list_complect USING btree (device_complect_id);


--
-- Name: idx_mobdekbkp_device_part_list_complect_on_device_part; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_device_part_list_complect_on_device_part ON public.mobdekbkp_device_part_list_complect USING btree (device_part_id);


--
-- Name: idx_mobdekbkp_device_part_list_planned_entry_on_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_device_part_list_planned_entry_on_typonominal ON public.mobdekbkp_device_part_list_planned_entry USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_device_part_list_planned_on_device_part; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_device_part_list_planned_on_device_part ON public.mobdekbkp_device_part_list_planned USING btree (device_part_id);


--
-- Name: idx_mobdekbkp_device_part_on_developer; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_device_part_on_developer ON public.mobdekbkp_device_part USING btree (developer_id);


--
-- Name: idx_mobdekbkp_device_parts_entry_on_device; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_device_parts_entry_on_device ON public.mobdekbkp_device_parts_entry USING btree (device_id);


--
-- Name: idx_mobdekbkp_device_parts_entry_on_part; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_device_parts_entry_on_part ON public.mobdekbkp_device_parts_entry USING btree (part_id);


--
-- Name: idx_mobdekbkp_devicelistprojecadditientry_on_newtyponominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_devicelistprojecadditientry_on_newtyponominal ON public.mobdekbkp_device_list_project_addition_entry USING btree (newtyponominal_id);


--
-- Name: idx_mobdekbkp_deviclistprojeaddit_on_device_list_project; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_deviclistprojeaddit_on_device_list_project ON public.mobdekbkp_device_list_project_addition USING btree (device_list_project_id);


--
-- Name: idx_mobdekbkp_devilistprojaddientr_on_devilistprojaddi; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_devilistprojaddientr_on_devilistprojaddi ON public.mobdekbkp_device_list_project_addition_entry USING btree (device_list_project_addition_id);


--
-- Name: idx_mobdekbkp_devipartlistcompentr_on_devipartlistcomp; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_devipartlistcompentr_on_devipartlistcomp ON public.mobdekbkp_device_part_list_complect_entry USING btree (device_part_list_complect_id);


--
-- Name: idx_mobdekbkp_devipartlistplanentr_on_devipartlistplaninve; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_devipartlistplanentr_on_devipartlistplaninve ON public.mobdekbkp_device_part_list_planned_entry USING btree (device_part_list_planned_inverse_id);


--
-- Name: idx_mobdekbkp_factory_import_device; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_factory_import_device ON public.mobdekbkp_factory USING btree (import_device_id);


--
-- Name: idx_mobdekbkp_handbook_entry_on_handbook; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_handbook_entry_on_handbook ON public.mobdekbkp_handbook_entry USING btree (handbook_id);


--
-- Name: idx_mobdekbkp_handbook_entry_on_parent; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_handbook_entry_on_parent ON public.mobdekbkp_handbook_entry USING btree (parent_id);


--
-- Name: idx_mobdekbkp_import_device_import_class; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_import_device_import_class ON public.mobdekbkp_import_device USING btree (import_class_id);


--
-- Name: idx_mobdekbkp_import_docs_schemes_import_device; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_import_docs_schemes_import_device ON public.mobdekbkp_import_docs_schemes USING btree (import_device_id);


--
-- Name: idx_mobdekbkp_main_parameters_import_device; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_main_parameters_import_device ON public.mobdekbkp_main_parameters USING btree (import_device_id);


--
-- Name: idx_mobdekbkp_operation_conditions_uk_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_mobdekbkp_operation_conditions_uk_name ON public.mobdekbkp_operation_conditions USING btree (name) WHERE (delete_ts IS NULL);


--
-- Name: idx_mobdekbkp_outer_certificate_tests_on_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_certificate_tests_on_typonominal ON public.mobdekbkp_outer_certificate_tests USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_outer_db_fail_on_claimed_company; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_db_fail_on_claimed_company ON public.mobdekbkp_outer_db_fail USING btree (claimed_company_id);


--
-- Name: idx_mobdekbkp_outer_db_fail_on_manufacturer; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_db_fail_on_manufacturer ON public.mobdekbkp_outer_db_fail USING btree (manufacturer_id);


--
-- Name: idx_mobdekbkp_outer_db_fail_on_source; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_db_fail_on_source ON public.mobdekbkp_outer_db_fail USING btree (source_id);


--
-- Name: idx_mobdekbkp_outer_db_fail_on_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_db_fail_on_typonominal ON public.mobdekbkp_outer_db_fail USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_outer_db_refuse_import_device; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_db_refuse_import_device ON public.mobdekbkp_outer_db_refuse USING btree (import_device_id);


--
-- Name: idx_mobdekbkp_outer_db_refuse_on_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_db_refuse_on_typonominal ON public.mobdekbkp_outer_db_refuse USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_outer_db_refuse_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_db_refuse_typonominal ON public.mobdekbkp_outer_db_refuse USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_outer_entrance_tests_on_cert_center; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_entrance_tests_on_cert_center ON public.mobdekbkp_outer_entrance_tests USING btree (cert_center_id);


--
-- Name: idx_mobdekbkp_outer_entrance_tests_on_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_entrance_tests_on_typonominal ON public.mobdekbkp_outer_entrance_tests USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_outer_fail_and_refuses_on_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_fail_and_refuses_on_typonominal ON public.mobdekbkp_outer_fail_and_refuses USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_outer_list_allowing_entry_on_outer_list_allowing; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_list_allowing_entry_on_outer_list_allowing ON public.mobdekbkp_outer_list_allowing_entry USING btree (outer_list_allowing_id);


--
-- Name: idx_mobdekbkp_outer_list_allowing_entry_on_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_list_allowing_entry_on_typonominal ON public.mobdekbkp_outer_list_allowing_entry USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_outer_list_allowing_on_type; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_list_allowing_on_type ON public.mobdekbkp_outer_list_allowing USING btree (type_id);


--
-- Name: idx_mobdekbkp_outer_persistence_info_on_info_source; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_persistence_info_on_info_source ON public.mobdekbkp_outer_persistence_info USING btree (info_source_id);


--
-- Name: idx_mobdekbkp_outer_persistence_info_on_tiponominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_persistence_info_on_tiponominal ON public.mobdekbkp_outer_persistence_info USING btree (tiponominal_id);


--
-- Name: idx_mobdekbkp_outer_rejection_import_device; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_rejection_import_device ON public.mobdekbkp_outer_rejection USING btree (import_device_id);


--
-- Name: idx_mobdekbkp_outer_rejection_on_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_rejection_on_typonominal ON public.mobdekbkp_outer_rejection USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_outer_rejection_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_outer_rejection_typonominal ON public.mobdekbkp_outer_rejection USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_parameter_on_parameter_category; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_parameter_on_parameter_category ON public.mobdekbkp_parameter USING btree (parameter_category_id);


--
-- Name: idx_mobdekbkp_parameter_value_on_parameter; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_parameter_value_on_parameter ON public.mobdekbkp_parameter_value USING btree (parameter_id);


--
-- Name: idx_mobdekbkp_parameter_value_on_type; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_parameter_value_on_type ON public.mobdekbkp_parameter_value USING btree (type_id);


--
-- Name: idx_mobdekbkp_parameter_value_on_val_str_lib; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_parameter_value_on_val_str_lib ON public.mobdekbkp_parameter_value USING btree (val_str_lib_id);


--
-- Name: idx_mobdekbkp_parameter_value_on_val_str_rec; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_parameter_value_on_val_str_rec ON public.mobdekbkp_parameter_value USING btree (val_str_rec_id);


--
-- Name: idx_mobdekbkp_parameters_for_purchasing_import_device; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_parameters_for_purchasing_import_device ON public.mobdekbkp_parameters_for_purchasing USING btree (import_device_id);


--
-- Name: idx_mobdekbkp_sapr_data_import_device; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_sapr_data_import_device ON public.mobdekbkp_sapr_data USING btree (import_device_id);


--
-- Name: idx_mobdekbkp_str_lib_uk_text; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_mobdekbkp_str_lib_uk_text ON public.mobdekbkp_str_lib USING btree (text) WHERE (delete_ts IS NULL);


--
-- Name: idx_mobdekbkp_str_rec_uk_text; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_mobdekbkp_str_rec_uk_text ON public.mobdekbkp_str_rec USING btree (text) WHERE (delete_ts IS NULL);


--
-- Name: idx_mobdekbkp_substraentry_on_typonominal_install_parameters; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_substraentry_on_typonominal_install_parameters ON public.mobdekbkp_substrate_entry USING btree (typonominal_install_parameters_id);


--
-- Name: idx_mobdekbkp_substrate_entry_on_substrate; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_substrate_entry_on_substrate ON public.mobdekbkp_substrate_entry USING btree (substrate_id);


--
-- Name: idx_mobdekbkp_type_calicoholder_entry_on_type_inverse; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_type_calicoholder_entry_on_type_inverse ON public.mobdekbkp_type_calicoholder_entry USING btree (type_inverse_id);


--
-- Name: idx_mobdekbkp_type_class_characteristic_on_parameter; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_type_class_characteristic_on_parameter ON public.mobdekbkp_type_class_characteristic USING btree (parameter_id);


--
-- Name: idx_mobdekbkp_type_class_characteristic_on_type_class; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_type_class_characteristic_on_type_class ON public.mobdekbkp_type_class_characteristic USING btree (type_class_id);


--
-- Name: idx_mobdekbkp_type_class_on_parent; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_type_class_on_parent ON public.mobdekbkp_type_class USING btree (parent_id);


--
-- Name: idx_mobdekbkp_type_manufacturer_entry_on_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_type_manufacturer_entry_on_name ON public.mobdekbkp_type_manufacturer_entry USING btree (name_id);


--
-- Name: idx_mobdekbkp_type_manufacturer_entry_on_type_inverse; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_type_manufacturer_entry_on_type_inverse ON public.mobdekbkp_type_manufacturer_entry USING btree (type_inverse_id);


--
-- Name: idx_mobdekbkp_type_on_alt_class_g2710; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_type_on_alt_class_g2710 ON public.mobdekbkp_type USING btree (alt_class_g2710_id);


--
-- Name: idx_mobdekbkp_type_on_alt_class_g56649; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_type_on_alt_class_g56649 ON public.mobdekbkp_type USING btree (alt_class_g56649_id);


--
-- Name: idx_mobdekbkp_type_on_alt_class_mil; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_type_on_alt_class_mil ON public.mobdekbkp_type USING btree (alt_class_mil_id);


--
-- Name: idx_mobdekbkp_type_on_alt_class_rel; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_type_on_alt_class_rel ON public.mobdekbkp_type USING btree (alt_class_rel_id);


--
-- Name: idx_mobdekbkp_type_on_amount_unit; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_type_on_amount_unit ON public.mobdekbkp_type USING btree (amount_unit_id);


--
-- Name: idx_mobdekbkp_type_on_type_class; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_type_on_type_class ON public.mobdekbkp_type USING btree (type_class_id);


--
-- Name: idx_mobdekbkp_type_provider_entry_on_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_type_provider_entry_on_name ON public.mobdekbkp_type_provider_entry USING btree (name_id);


--
-- Name: idx_mobdekbkp_type_provider_entry_on_type_inverse; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_type_provider_entry_on_type_inverse ON public.mobdekbkp_type_provider_entry USING btree (type_inverse_id);


--
-- Name: idx_mobdekbkp_typonominal_analog_import_device; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_analog_import_device ON public.mobdekbkp_typonominal_analog USING btree (import_device_id);


--
-- Name: idx_mobdekbkp_typonominal_analog_on_parent_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_analog_on_parent_typonominal ON public.mobdekbkp_typonominal_analog USING btree (parent_typonominal_id);


--
-- Name: idx_mobdekbkp_typonominal_analog_parent_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_analog_parent_typonominal ON public.mobdekbkp_typonominal_analog USING btree (parent_typonominal_id);


--
-- Name: idx_mobdekbkp_typonominal_body; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_body ON public.mobdekbkp_typonominal USING btree (body_id);


--
-- Name: idx_mobdekbkp_typonominal_body_on_body_material; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_body_on_body_material ON public.mobdekbkp_typonominal_body USING btree (body_material_id);


--
-- Name: idx_mobdekbkp_typonominal_body_on_coating_material; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_body_on_coating_material ON public.mobdekbkp_typonominal_body USING btree (coating_material_id);


--
-- Name: idx_mobdekbkp_typonominal_body_on_pins_material; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_body_on_pins_material ON public.mobdekbkp_typonominal_body USING btree (pins_material_id);


--
-- Name: idx_mobdekbkp_typonominal_install_parameters_on_flux_brand; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_install_parameters_on_flux_brand ON public.mobdekbkp_typonominal_install_parameters USING btree (flux_brand_id);


--
-- Name: idx_mobdekbkp_typonominal_install_parameters_on_glue_type; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_install_parameters_on_glue_type ON public.mobdekbkp_typonominal_install_parameters USING btree (glue_type_id);


--
-- Name: idx_mobdekbkp_typonominal_install_parameters_on_install_method; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_install_parameters_on_install_method ON public.mobdekbkp_typonominal_install_parameters USING btree (install_method_id);


--
-- Name: idx_mobdekbkp_typonominal_install_parameters_on_solder_brand; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_install_parameters_on_solder_brand ON public.mobdekbkp_typonominal_install_parameters USING btree (solder_brand_id);


--
-- Name: idx_mobdekbkp_typonominal_lib_attributes; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_lib_attributes ON public.mobdekbkp_typonominal USING btree (lib_attributes_id);


--
-- Name: idx_mobdekbkp_typonominal_on_body; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_on_body ON public.mobdekbkp_typonominal USING btree (body_id);


--
-- Name: idx_mobdekbkp_typonominal_on_lib_attributes; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_on_lib_attributes ON public.mobdekbkp_typonominal USING btree (lib_attributes_id);


--
-- Name: idx_mobdekbkp_typonominal_on_type; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_on_type ON public.mobdekbkp_typonominal USING btree (type_id);


--
-- Name: idx_mobdekbkp_typonominal_on_typonominal_quality_level_import; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_on_typonominal_quality_level_import ON public.mobdekbkp_typonominal USING btree (typonominal_quality_level_import_id);


--
-- Name: idx_mobdekbkp_typonominal_on_typonominal_quality_level_native; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_on_typonominal_quality_level_native ON public.mobdekbkp_typonominal USING btree (typonominal_quality_level_native_id);


--
-- Name: idx_mobdekbkp_typonominal_purchase_parameters_on_company; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_purchase_parameters_on_company ON public.mobdekbkp_typonominal_purchase_parameters USING btree (company_id);


--
-- Name: idx_mobdekbkp_typonominal_purchase_parameters_on_typonominal; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_purchase_parameters_on_typonominal ON public.mobdekbkp_typonominal_purchase_parameters USING btree (typonominal_id);


--
-- Name: idx_mobdekbkp_typonominal_type; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_type ON public.mobdekbkp_typonominal USING btree (type_id);


--
-- Name: idx_mobdekbkp_typonominal_typonominal_quality_level_import; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_typonominal_quality_level_import ON public.mobdekbkp_typonominal USING btree (typonominal_quality_level_import_id);


--
-- Name: idx_mobdekbkp_typonominal_typonominal_quality_level_native; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_mobdekbkp_typonominal_typonominal_quality_level_native ON public.mobdekbkp_typonominal USING btree (typonominal_quality_level_native_id);


--
-- Name: idx_mobdekbkp_typonqualilevelimporsetti_uk_import_class_id1; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_mobdekbkp_typonqualilevelimporsetti_uk_import_class_id1 ON public.mobdekbkp_typonominal_quality_level_import_settings USING btree (import_class_id) WHERE (delete_ts IS NULL);


--
-- Name: idx_notificationsusers_message_on_attachment; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_notificationsusers_message_on_attachment ON public.notificationsusers_message USING btree (attachment_id);


--
-- Name: idx_notificationsusers_message_on_message_text; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_notificationsusers_message_on_message_text ON public.notificationsusers_message USING btree (message_text_id);


--
-- Name: idx_notificationsusers_message_on_meta; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_notificationsusers_message_on_meta ON public.notificationsusers_message USING btree (meta_id);


--
-- Name: idx_notificationsusers_message_on_recipient; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_notificationsusers_message_on_recipient ON public.notificationsusers_message USING btree (recipient_id);


--
-- Name: idx_notificationsusers_message_on_sender; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_notificationsusers_message_on_sender ON public.notificationsusers_message USING btree (sender_id);


--
-- Name: idx_report_group_uniq_title; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_report_group_uniq_title ON public.report_group USING btree (title) WHERE (delete_ts IS NULL);


--
-- Name: idx_report_report_uniq_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_report_report_uniq_name ON public.report_report USING btree (name) WHERE (delete_ts IS NULL);


--
-- Name: idx_reviews_entities_for_moderation_on_moderation_property; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_reviews_entities_for_moderation_on_moderation_property ON public.reviews_entities_for_moderation USING btree (moderation_property_id);


--
-- Name: idx_reviews_moderation_property_uk_entity; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_reviews_moderation_property_uk_entity ON public.reviews_moderation_property USING btree (entity) WHERE (delete_ts IS NULL);


--
-- Name: idx_reviews_review_on_author; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_reviews_review_on_author ON public.reviews_review USING btree (author_id);


--
-- Name: idx_rulesmodule_rule_data_script_uk_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_rulesmodule_rule_data_script_uk_name ON public.rulesmodule_rule_data_script USING btree (name) WHERE (delete_ts IS NULL);


--
-- Name: idx_sec_constraint_group; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_constraint_group ON public.sec_constraint USING btree (group_id);


--
-- Name: idx_sec_entity_log_entity_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_entity_log_entity_id ON public.sec_entity_log USING btree (entity_id);


--
-- Name: idx_sec_entity_log_ientity_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_entity_log_ientity_id ON public.sec_entity_log USING btree (int_entity_id);


--
-- Name: idx_sec_entity_log_lentity_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_entity_log_lentity_id ON public.sec_entity_log USING btree (long_entity_id);


--
-- Name: idx_sec_entity_log_sentity_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_entity_log_sentity_id ON public.sec_entity_log USING btree (string_entity_id);


--
-- Name: idx_sec_filter_component_user; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_filter_component_user ON public.sec_filter USING btree (component, user_id);


--
-- Name: idx_sec_group_uniq_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_sec_group_uniq_name ON public.sec_group USING btree (name) WHERE (delete_ts IS NULL);


--
-- Name: idx_sec_loc_cnstrnt_msg_unique; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_sec_loc_cnstrnt_msg_unique ON public.sec_localized_constraint_msg USING btree (entity_name, operation_type) WHERE (delete_ts IS NULL);


--
-- Name: idx_sec_logged_attr_entity; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_logged_attr_entity ON public.sec_logged_attr USING btree (entity_id);


--
-- Name: idx_sec_permission_unique; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_sec_permission_unique ON public.sec_permission USING btree (role_id, permission_type, target) WHERE (delete_ts IS NULL);


--
-- Name: idx_sec_presentation_component_user; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_presentation_component_user ON public.sec_presentation USING btree (component, user_id);


--
-- Name: idx_sec_remember_me_token; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_remember_me_token ON public.sec_remember_me USING btree (token);


--
-- Name: idx_sec_remember_me_user; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_remember_me_user ON public.sec_remember_me USING btree (user_id);


--
-- Name: idx_sec_role_uniq_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_sec_role_uniq_name ON public.sec_role USING btree (name) WHERE (delete_ts IS NULL);


--
-- Name: idx_sec_screen_hist_sub_user; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_screen_hist_sub_user ON public.sec_screen_history USING btree (substituted_user_id);


--
-- Name: idx_sec_screen_history_user; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_screen_history_user ON public.sec_screen_history USING btree (user_id);


--
-- Name: idx_sec_search_folder_user; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_search_folder_user ON public.sec_search_folder USING btree (user_id);


--
-- Name: idx_sec_session_attr_group; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_session_attr_group ON public.sec_session_attr USING btree (group_id);


--
-- Name: idx_sec_session_log_session; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_session_log_session ON public.sec_session_log USING btree (session_id);


--
-- Name: idx_sec_session_log_subuser; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_session_log_subuser ON public.sec_session_log USING btree (substituted_user_id);


--
-- Name: idx_sec_session_log_user; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_session_log_user ON public.sec_session_log USING btree (user_id);


--
-- Name: idx_sec_user_role_uniq_role; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_sec_user_role_uniq_role ON public.sec_user_role USING btree (user_id, role_id) WHERE (delete_ts IS NULL);


--
-- Name: idx_sec_user_substitution_user; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sec_user_substitution_user ON public.sec_user_substitution USING btree (user_id);


--
-- Name: idx_sec_user_uniq_login; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_sec_user_uniq_login ON public.sec_user USING btree (login_lc) WHERE (delete_ts IS NULL);


--
-- Name: idx_session_log_started_ts; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_session_log_started_ts ON public.sec_session_log USING btree (started_ts DESC);


--
-- Name: idx_sys_attr_value_entity; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_attr_value_entity ON public.sys_attr_value USING btree (entity_id);


--
-- Name: idx_sys_attr_value_ientity; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_attr_value_ientity ON public.sys_attr_value USING btree (int_entity_id);


--
-- Name: idx_sys_attr_value_lentity; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_attr_value_lentity ON public.sys_attr_value USING btree (long_entity_id);


--
-- Name: idx_sys_attr_value_sentity; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_attr_value_sentity ON public.sys_attr_value USING btree (string_entity_id);


--
-- Name: idx_sys_category_attr_category; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_category_attr_category ON public.sys_category_attr USING btree (category_id);


--
-- Name: idx_sys_category_uniq_name_entity_type; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_sys_category_uniq_name_entity_type ON public.sys_category USING btree (name, entity_type) WHERE (delete_ts IS NULL);


--
-- Name: idx_sys_config_uniq_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_sys_config_uniq_name ON public.sys_config USING btree (name);


--
-- Name: idx_sys_entity_snapshot_entity_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_entity_snapshot_entity_id ON public.sys_entity_snapshot USING btree (entity_id);


--
-- Name: idx_sys_entity_snapshot_ientity_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_entity_snapshot_ientity_id ON public.sys_entity_snapshot USING btree (int_entity_id);


--
-- Name: idx_sys_entity_snapshot_lentity_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_entity_snapshot_lentity_id ON public.sys_entity_snapshot USING btree (long_entity_id);


--
-- Name: idx_sys_entity_snapshot_sentity_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_entity_snapshot_sentity_id ON public.sys_entity_snapshot USING btree (string_entity_id);


--
-- Name: idx_sys_entity_statistics_uniq_name; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_sys_entity_statistics_uniq_name ON public.sys_entity_statistics USING btree (name);


--
-- Name: idx_sys_fts_queue_idxhost_crts; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_fts_queue_idxhost_crts ON public.sys_fts_queue USING btree (indexing_host, create_ts);


--
-- Name: idx_sys_query_result_entity_session_key; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_query_result_entity_session_key ON public.sys_query_result USING btree (entity_id, session_id, query_key);


--
-- Name: idx_sys_query_result_ientity_session_key; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_query_result_ientity_session_key ON public.sys_query_result USING btree (int_entity_id, session_id, query_key);


--
-- Name: idx_sys_query_result_lentity_session_key; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_query_result_lentity_session_key ON public.sys_query_result USING btree (long_entity_id, session_id, query_key);


--
-- Name: idx_sys_query_result_sentity_session_key; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_query_result_sentity_session_key ON public.sys_query_result USING btree (string_entity_id, session_id, query_key);


--
-- Name: idx_sys_query_result_session_key; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_query_result_session_key ON public.sys_query_result USING btree (session_id, query_key);


--
-- Name: idx_sys_scheduled_execution_task_finish_time; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_scheduled_execution_task_finish_time ON public.sys_scheduled_execution USING btree (task_id, finish_time);


--
-- Name: idx_sys_scheduled_execution_task_start_time; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_scheduled_execution_task_start_time ON public.sys_scheduled_execution USING btree (task_id, start_time);


--
-- Name: idx_sys_sending_message_date_sent; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_sending_message_date_sent ON public.sys_sending_message USING btree (date_sent);


--
-- Name: idx_sys_sending_message_status; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_sending_message_status ON public.sys_sending_message USING btree (status);


--
-- Name: idx_sys_sending_message_update_ts; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_sys_sending_message_update_ts ON public.sys_sending_message USING btree (update_ts);


--
-- Name: sys_sending_attachment_message_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX sys_sending_attachment_message_idx ON public.sys_sending_attachment USING btree (message_id);


--
-- Name: act_ru_identitylink act_fk_athrz_procedef; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_identitylink
    ADD CONSTRAINT act_fk_athrz_procedef FOREIGN KEY (proc_def_id_) REFERENCES public.act_re_procdef(id_);


--
-- Name: act_ge_bytearray act_fk_bytearr_depl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ge_bytearray
    ADD CONSTRAINT act_fk_bytearr_depl FOREIGN KEY (deployment_id_) REFERENCES public.act_re_deployment(id_);


--
-- Name: act_ru_event_subscr act_fk_event_exec; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_event_subscr
    ADD CONSTRAINT act_fk_event_exec FOREIGN KEY (execution_id_) REFERENCES public.act_ru_execution(id_);


--
-- Name: act_ru_execution act_fk_exe_parent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_execution
    ADD CONSTRAINT act_fk_exe_parent FOREIGN KEY (parent_id_) REFERENCES public.act_ru_execution(id_);


--
-- Name: act_ru_execution act_fk_exe_procdef; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_execution
    ADD CONSTRAINT act_fk_exe_procdef FOREIGN KEY (proc_def_id_) REFERENCES public.act_re_procdef(id_);


--
-- Name: act_ru_execution act_fk_exe_procinst; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_execution
    ADD CONSTRAINT act_fk_exe_procinst FOREIGN KEY (proc_inst_id_) REFERENCES public.act_ru_execution(id_);


--
-- Name: act_ru_execution act_fk_exe_super; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_execution
    ADD CONSTRAINT act_fk_exe_super FOREIGN KEY (super_exec_) REFERENCES public.act_ru_execution(id_);


--
-- Name: act_ru_identitylink act_fk_idl_procinst; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_identitylink
    ADD CONSTRAINT act_fk_idl_procinst FOREIGN KEY (proc_inst_id_) REFERENCES public.act_ru_execution(id_);


--
-- Name: act_procdef_info act_fk_info_json_ba; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_procdef_info
    ADD CONSTRAINT act_fk_info_json_ba FOREIGN KEY (info_json_id_) REFERENCES public.act_ge_bytearray(id_);


--
-- Name: act_procdef_info act_fk_info_procdef; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_procdef_info
    ADD CONSTRAINT act_fk_info_procdef FOREIGN KEY (proc_def_id_) REFERENCES public.act_re_procdef(id_);


--
-- Name: act_ru_job act_fk_job_exception; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_job
    ADD CONSTRAINT act_fk_job_exception FOREIGN KEY (exception_stack_id_) REFERENCES public.act_ge_bytearray(id_);


--
-- Name: act_id_membership act_fk_memb_group; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_id_membership
    ADD CONSTRAINT act_fk_memb_group FOREIGN KEY (group_id_) REFERENCES public.act_id_group(id_);


--
-- Name: act_id_membership act_fk_memb_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_id_membership
    ADD CONSTRAINT act_fk_memb_user FOREIGN KEY (user_id_) REFERENCES public.act_id_user(id_);


--
-- Name: act_re_model act_fk_model_deployment; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_re_model
    ADD CONSTRAINT act_fk_model_deployment FOREIGN KEY (deployment_id_) REFERENCES public.act_re_deployment(id_);


--
-- Name: act_re_model act_fk_model_source; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_re_model
    ADD CONSTRAINT act_fk_model_source FOREIGN KEY (editor_source_value_id_) REFERENCES public.act_ge_bytearray(id_);


--
-- Name: act_re_model act_fk_model_source_extra; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_re_model
    ADD CONSTRAINT act_fk_model_source_extra FOREIGN KEY (editor_source_extra_value_id_) REFERENCES public.act_ge_bytearray(id_);


--
-- Name: act_ru_task act_fk_task_exe; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_task
    ADD CONSTRAINT act_fk_task_exe FOREIGN KEY (execution_id_) REFERENCES public.act_ru_execution(id_);


--
-- Name: act_ru_task act_fk_task_procdef; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_task
    ADD CONSTRAINT act_fk_task_procdef FOREIGN KEY (proc_def_id_) REFERENCES public.act_re_procdef(id_);


--
-- Name: act_ru_task act_fk_task_procinst; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_task
    ADD CONSTRAINT act_fk_task_procinst FOREIGN KEY (proc_inst_id_) REFERENCES public.act_ru_execution(id_);


--
-- Name: act_ru_identitylink act_fk_tskass_task; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_identitylink
    ADD CONSTRAINT act_fk_tskass_task FOREIGN KEY (task_id_) REFERENCES public.act_ru_task(id_);


--
-- Name: act_ru_variable act_fk_var_bytearray; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_variable
    ADD CONSTRAINT act_fk_var_bytearray FOREIGN KEY (bytearray_id_) REFERENCES public.act_ge_bytearray(id_);


--
-- Name: act_ru_variable act_fk_var_exe; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_variable
    ADD CONSTRAINT act_fk_var_exe FOREIGN KEY (execution_id_) REFERENCES public.act_ru_execution(id_);


--
-- Name: act_ru_variable act_fk_var_procinst; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.act_ru_variable
    ADD CONSTRAINT act_fk_var_procinst FOREIGN KEY (proc_inst_id_) REFERENCES public.act_ru_execution(id_);


--
-- Name: mobdekbkp_application_common_dev_document_link fk_appcomdevdoc_on_application_common_dev; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_common_dev_document_link
    ADD CONSTRAINT fk_appcomdevdoc_on_application_common_dev FOREIGN KEY (application_common_dev_id) REFERENCES public.mobdekbkp_application_common_dev(id);


--
-- Name: mobdekbkp_application_common_dev_document_link fk_appcomdevdoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_common_dev_document_link
    ADD CONSTRAINT fk_appcomdevdoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_application_common_entry_document_link fk_appcomentdoc_on_application_common_entry; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_common_entry_document_link
    ADD CONSTRAINT fk_appcomentdoc_on_application_common_entry FOREIGN KEY (application_common_entry_id) REFERENCES public.mobdekbkp_application_common_entry(id);


--
-- Name: mobdekbkp_application_common_entry_document_link fk_appcomentdoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_common_entry_document_link
    ADD CONSTRAINT fk_appcomentdoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_application_new_typonominal_add_document_link fk_appnewtypadddoc_on_application_new_typonominal_add; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_new_typonominal_add_document_link
    ADD CONSTRAINT fk_appnewtypadddoc_on_application_new_typonominal_add FOREIGN KEY (application_new_typonominal_add_id) REFERENCES public.mobdekbkp_application_new_typonominal_add(id);


--
-- Name: mobdekbkp_application_new_typonominal_add_document_link fk_appnewtypadddoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_new_typonominal_add_document_link
    ADD CONSTRAINT fk_appnewtypadddoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: bpm_proc_actor fk_bpm_proc_actor_proc_instance_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_actor
    ADD CONSTRAINT fk_bpm_proc_actor_proc_instance_id FOREIGN KEY (proc_instance_id) REFERENCES public.bpm_proc_instance(id);


--
-- Name: bpm_proc_actor fk_bpm_proc_actor_proc_role_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_actor
    ADD CONSTRAINT fk_bpm_proc_actor_proc_role_id FOREIGN KEY (proc_role_id) REFERENCES public.bpm_proc_role(id);


--
-- Name: bpm_proc_actor fk_bpm_proc_actor_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_actor
    ADD CONSTRAINT fk_bpm_proc_actor_user_id FOREIGN KEY (user_id) REFERENCES public.sec_user(id);


--
-- Name: bpm_proc_attachment fk_bpm_proc_attachment_author_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_attachment
    ADD CONSTRAINT fk_bpm_proc_attachment_author_id FOREIGN KEY (author_id) REFERENCES public.sec_user(id);


--
-- Name: bpm_proc_attachment fk_bpm_proc_attachment_file_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_attachment
    ADD CONSTRAINT fk_bpm_proc_attachment_file_id FOREIGN KEY (file_id) REFERENCES public.sys_file(id);


--
-- Name: bpm_proc_attachment fk_bpm_proc_attachment_proc_instance_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_attachment
    ADD CONSTRAINT fk_bpm_proc_attachment_proc_instance_id FOREIGN KEY (proc_instance_id) REFERENCES public.bpm_proc_instance(id);


--
-- Name: bpm_proc_attachment fk_bpm_proc_attachment_proc_task_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_attachment
    ADD CONSTRAINT fk_bpm_proc_attachment_proc_task_id FOREIGN KEY (proc_task_id) REFERENCES public.bpm_proc_task(id);


--
-- Name: bpm_proc_attachment fk_bpm_proc_attachment_type_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_attachment
    ADD CONSTRAINT fk_bpm_proc_attachment_type_id FOREIGN KEY (type_id) REFERENCES public.bpm_proc_attachment_type(id);


--
-- Name: bpm_proc_definition fk_bpm_proc_definition_model_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_definition
    ADD CONSTRAINT fk_bpm_proc_definition_model_id FOREIGN KEY (model_id) REFERENCES public.bpm_proc_model(id);


--
-- Name: bpm_proc_instance fk_bpm_proc_instance_proc_definition_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_instance
    ADD CONSTRAINT fk_bpm_proc_instance_proc_definition_id FOREIGN KEY (proc_definition_id) REFERENCES public.bpm_proc_definition(id);


--
-- Name: bpm_proc_instance fk_bpm_proc_instance_started_by_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_instance
    ADD CONSTRAINT fk_bpm_proc_instance_started_by_id FOREIGN KEY (started_by_id) REFERENCES public.sec_user(id);


--
-- Name: bpm_proc_role fk_bpm_proc_role_proc_definition_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_role
    ADD CONSTRAINT fk_bpm_proc_role_proc_definition_id FOREIGN KEY (proc_definition_id) REFERENCES public.bpm_proc_definition(id);


--
-- Name: bpm_proc_task fk_bpm_proc_task_proc_actor_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_task
    ADD CONSTRAINT fk_bpm_proc_task_proc_actor_id FOREIGN KEY (proc_actor_id) REFERENCES public.bpm_proc_actor(id);


--
-- Name: bpm_proc_task fk_bpm_proc_task_proc_instance_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_task
    ADD CONSTRAINT fk_bpm_proc_task_proc_instance_id FOREIGN KEY (proc_instance_id) REFERENCES public.bpm_proc_instance(id);


--
-- Name: bpm_proc_task_user_link fk_bptul_proc_task; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_task_user_link
    ADD CONSTRAINT fk_bptul_proc_task FOREIGN KEY (proc_task_id) REFERENCES public.bpm_proc_task(id);


--
-- Name: bpm_proc_task_user_link fk_bptul_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bpm_proc_task_user_link
    ADD CONSTRAINT fk_bptul_user FOREIGN KEY (user_id) REFERENCES public.sec_user(id);


--
-- Name: cubaat_ssh_credentials fk_cubaat_ssh_credentials_on_private_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cubaat_ssh_credentials
    ADD CONSTRAINT fk_cubaat_ssh_credentials_on_private_key FOREIGN KEY (private_key_id) REFERENCES public.sys_file(id);


--
-- Name: ddcrd_diagnose_execution_log fk_ddcrd_diagnose_execution_log_execution_result_file; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ddcrd_diagnose_execution_log
    ADD CONSTRAINT fk_ddcrd_diagnose_execution_log_execution_result_file FOREIGN KEY (execution_result_file_id) REFERENCES public.sys_file(id);


--
-- Name: mobdekbkp_device_complect_document_link fk_devcomdoc_on_device_complect; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_complect_document_link
    ADD CONSTRAINT fk_devcomdoc_on_device_complect FOREIGN KEY (device_complect_id) REFERENCES public.mobdekbkp_device_complect(id);


--
-- Name: mobdekbkp_device_complect_document_link fk_devcomdoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_complect_document_link
    ADD CONSTRAINT fk_devcomdoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_device_device_filter_conditions_link fk_devdevfilcon_on_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_device_filter_conditions_link
    ADD CONSTRAINT fk_devdevfilcon_on_device FOREIGN KEY (device_id) REFERENCES public.mobdekbkp_device(id);


--
-- Name: mobdekbkp_device_device_filter_conditions_link fk_devdevfilcon_on_device_filter_conditions; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_device_filter_conditions_link
    ADD CONSTRAINT fk_devdevfilcon_on_device_filter_conditions FOREIGN KEY (device_filter_conditions_id) REFERENCES public.mobdekbkp_device_filter_conditions(id);


--
-- Name: mobdekbkp_device_document_link fk_devdoc_on_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_document_link
    ADD CONSTRAINT fk_devdoc_on_device FOREIGN KEY (device_id) REFERENCES public.mobdekbkp_device(id);


--
-- Name: mobdekbkp_device_document_link fk_devdoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_document_link
    ADD CONSTRAINT fk_devdoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_device_list_project_addition_document_link fk_devlisproadddoc_on_device_list_project_addition; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_addition_document_link
    ADD CONSTRAINT fk_devlisproadddoc_on_device_list_project_addition FOREIGN KEY (device_list_project_addition_id) REFERENCES public.mobdekbkp_device_list_project_addition(id);


--
-- Name: mobdekbkp_device_list_project_addition_document_link fk_devlisproadddoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_addition_document_link
    ADD CONSTRAINT fk_devlisproadddoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_device_list_project_application_document_link fk_devlisproappdoc_on_device_list_project_application; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_application_document_link
    ADD CONSTRAINT fk_devlisproappdoc_on_device_list_project_application FOREIGN KEY (device_list_project_application_id) REFERENCES public.mobdekbkp_device_list_project_application(id);


--
-- Name: mobdekbkp_device_list_project_application_document_link fk_devlisproappdoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_application_document_link
    ADD CONSTRAINT fk_devlisproappdoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_device_list_project_document_link fk_devlisprodoc_on_device_list_project; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_document_link
    ADD CONSTRAINT fk_devlisprodoc_on_device_list_project FOREIGN KEY (device_list_project_id) REFERENCES public.mobdekbkp_device_list_project(id);


--
-- Name: mobdekbkp_device_list_project_document_link fk_devlisprodoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_document_link
    ADD CONSTRAINT fk_devlisprodoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_device_part_document_link fk_devpardoc_on_device_part; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_document_link
    ADD CONSTRAINT fk_devpardoc_on_device_part FOREIGN KEY (device_part_id) REFERENCES public.mobdekbkp_device_part(id);


--
-- Name: mobdekbkp_device_part_document_link fk_devpardoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_document_link
    ADD CONSTRAINT fk_devpardoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_device_part_list_complect_document_link fk_devparliscomdoc_on_device_part_list_complect; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_complect_document_link
    ADD CONSTRAINT fk_devparliscomdoc_on_device_part_list_complect FOREIGN KEY (device_part_list_complect_id) REFERENCES public.mobdekbkp_device_part_list_complect(id);


--
-- Name: mobdekbkp_device_part_list_complect_document_link fk_devparliscomdoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_complect_document_link
    ADD CONSTRAINT fk_devparliscomdoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_device_part_list_planned_document_link fk_devparlispladoc_on_device_part_list_planned; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_planned_document_link
    ADD CONSTRAINT fk_devparlispladoc_on_device_part_list_planned FOREIGN KEY (device_part_list_planned_id) REFERENCES public.mobdekbkp_device_part_list_planned(id);


--
-- Name: mobdekbkp_device_part_list_planned_document_link fk_devparlispladoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_planned_document_link
    ADD CONSTRAINT fk_devparlispladoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: discuss_comment fk_discuss_comment_on_author; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discuss_comment
    ADD CONSTRAINT fk_discuss_comment_on_author FOREIGN KEY (author_id) REFERENCES public.sec_user(id);


--
-- Name: discuss_comment fk_discuss_comment_on_parent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discuss_comment
    ADD CONSTRAINT fk_discuss_comment_on_parent FOREIGN KEY (parent_id) REFERENCES public.discuss_comment(id);


--
-- Name: documents_document_file_descriptor_link fk_docfildes_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents_document_file_descriptor_link
    ADD CONSTRAINT fk_docfildes_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: documents_document_file_descriptor_link fk_docfildes_on_file_descriptor; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents_document_file_descriptor_link
    ADD CONSTRAINT fk_docfildes_on_file_descriptor FOREIGN KEY (file_descriptor_id) REFERENCES public.sys_file(id);


--
-- Name: documents_document_restriction_document_type_link fk_docresdoctyp_on_document_restriction; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents_document_restriction_document_type_link
    ADD CONSTRAINT fk_docresdoctyp_on_document_restriction FOREIGN KEY (document_restriction_id) REFERENCES public.documents_document_restriction(id);


--
-- Name: documents_document_restriction_document_type_link fk_docresdoctyp_on_document_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents_document_restriction_document_type_link
    ADD CONSTRAINT fk_docresdoctyp_on_document_type FOREIGN KEY (document_type_id) REFERENCES public.documents_document_type(id);


--
-- Name: documents_document fk_documents_document_on_document_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents_document
    ADD CONSTRAINT fk_documents_document_on_document_type FOREIGN KEY (document_type_id) REFERENCES public.documents_document_type(id);


--
-- Name: documents_document_type fk_documents_document_type_on_parent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents_document_type
    ADD CONSTRAINT fk_documents_document_type_on_parent FOREIGN KEY (parent_id) REFERENCES public.documents_document_type(id);


--
-- Name: reviews_entities_for_moderation_user_link fk_entformoduse_on_entities_for_moderation; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews_entities_for_moderation_user_link
    ADD CONSTRAINT fk_entformoduse_on_entities_for_moderation FOREIGN KEY (entities_for_moderation_id) REFERENCES public.reviews_entities_for_moderation(id);


--
-- Name: reviews_entities_for_moderation_user_link fk_entformoduse_on_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews_entities_for_moderation_user_link
    ADD CONSTRAINT fk_entformoduse_on_user FOREIGN KEY (user_id) REFERENCES public.sec_user(id);


--
-- Name: mobdekbkp_lib_attributes_document_link fk_libattdoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_lib_attributes_document_link
    ADD CONSTRAINT fk_libattdoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_lib_attributes_document_link fk_libattdoc_on_lib_attributes; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_lib_attributes_document_link
    ADD CONSTRAINT fk_libattdoc_on_lib_attributes FOREIGN KEY (lib_attributes_id) REFERENCES public.mobdekbkp_lib_attributes(id);


--
-- Name: mobdekbkp_advanced_setting fk_mobdekbkp_advanced_setting_import_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_advanced_setting
    ADD CONSTRAINT fk_mobdekbkp_advanced_setting_import_device FOREIGN KEY (import_device_id) REFERENCES public.mobdekbkp_import_device(id);


--
-- Name: mobdekbkp_alter_class_gost2710 fk_mobdekbkp_alter_class_gost2710_on_parent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_alter_class_gost2710
    ADD CONSTRAINT fk_mobdekbkp_alter_class_gost2710_on_parent FOREIGN KEY (parent_id) REFERENCES public.mobdekbkp_alter_class_gost2710(id);


--
-- Name: mobdekbkp_alter_class_mil fk_mobdekbkp_alter_class_mil_on_parent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_alter_class_mil
    ADD CONSTRAINT fk_mobdekbkp_alter_class_mil_on_parent FOREIGN KEY (parent_id) REFERENCES public.mobdekbkp_alter_class_mil(id);


--
-- Name: mobdekbkp_alter_class_reliability fk_mobdekbkp_alter_class_reliability_on_parent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_alter_class_reliability
    ADD CONSTRAINT fk_mobdekbkp_alter_class_reliability_on_parent FOREIGN KEY (parent_id) REFERENCES public.mobdekbkp_alter_class_reliability(id);


--
-- Name: mobdekbkp_alter_class_reliability fk_mobdekbkp_alter_class_reliability_on_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_alter_class_reliability
    ADD CONSTRAINT fk_mobdekbkp_alter_class_reliability_on_type FOREIGN KEY (type_id) REFERENCES public.mobdekbkp_type_class_type(id);


--
-- Name: mobdekbkp_applicability_devices fk_mobdekbkp_applicability_devices_import_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_applicability_devices
    ADD CONSTRAINT fk_mobdekbkp_applicability_devices_import_device FOREIGN KEY (import_device_id) REFERENCES public.mobdekbkp_import_device(id);


--
-- Name: mobdekbkp_applicability_devices fk_mobdekbkp_applicability_devices_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_applicability_devices
    ADD CONSTRAINT fk_mobdekbkp_applicability_devices_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_application_new_dev_entry fk_mobdekbkp_application_new_dev_entry_on_application; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_new_dev_entry
    ADD CONSTRAINT fk_mobdekbkp_application_new_dev_entry_on_application FOREIGN KEY (application_id) REFERENCES public.mobdekbkp_application_new_typonominal_dev(id);


--
-- Name: mobdekbkp_application_new_typonominal_dev fk_mobdekbkp_application_new_typonominal_dev_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_new_typonominal_dev
    ADD CONSTRAINT fk_mobdekbkp_application_new_typonominal_dev_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_application_okr_info fk_mobdekbkp_application_okr_info_on_application_common_entry; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_okr_info
    ADD CONSTRAINT fk_mobdekbkp_application_okr_info_on_application_common_entry FOREIGN KEY (application_common_entry_id) REFERENCES public.mobdekbkp_application_common_entry(id);


--
-- Name: mobdekbkp_application_okr_info fk_mobdekbkp_application_okr_info_on_responsible; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_okr_info
    ADD CONSTRAINT fk_mobdekbkp_application_okr_info_on_responsible FOREIGN KEY (responsible_id) REFERENCES public.mobdekbkp_company(id);


--
-- Name: mobdekbkp_application_common_entry fk_mobdekbkp_appliccommonentry_on_application_common_dev; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_common_entry
    ADD CONSTRAINT fk_mobdekbkp_appliccommonentry_on_application_common_dev FOREIGN KEY (application_common_dev_id) REFERENCES public.mobdekbkp_application_common_dev(id);


--
-- Name: mobdekbkp_application_new_typonominal_dev fk_mobdekbkp_applicnewtyponodev_on_common_application; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_new_typonominal_dev
    ADD CONSTRAINT fk_mobdekbkp_applicnewtyponodev_on_common_application FOREIGN KEY (common_application_id) REFERENCES public.mobdekbkp_application_common_entry(id);


--
-- Name: mobdekbkp_application_new_dev_entry fk_mobdekbkp_applinewdeventry_on_application_common_entry; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_application_new_dev_entry
    ADD CONSTRAINT fk_mobdekbkp_applinewdeventry_on_application_common_entry FOREIGN KEY (application_common_entry_id) REFERENCES public.mobdekbkp_application_common_entry(id);


--
-- Name: mobdekbkp_basic_information_import fk_mobdekbkp_basic_information_import_import_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_basic_information_import
    ADD CONSTRAINT fk_mobdekbkp_basic_information_import_import_device FOREIGN KEY (import_device_id) REFERENCES public.mobdekbkp_import_device(id);


--
-- Name: mobdekbkp_cables_wires_cords fk_mobdekbkp_cables_wires_cords_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_cables_wires_cords
    ADD CONSTRAINT fk_mobdekbkp_cables_wires_cords_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_capacitors fk_mobdekbkp_capacitors_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_capacitors
    ADD CONSTRAINT fk_mobdekbkp_capacitors_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_cathode_ray_tubes fk_mobdekbkp_cathode_ray_tubes_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_cathode_ray_tubes
    ADD CONSTRAINT fk_mobdekbkp_cathode_ray_tubes_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_company_license fk_mobdekbkp_company_license_on_company; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company_license
    ADD CONSTRAINT fk_mobdekbkp_company_license_on_company FOREIGN KEY (company_id) REFERENCES public.mobdekbkp_company(id);


--
-- Name: mobdekbkp_company_license fk_mobdekbkp_company_license_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company_license
    ADD CONSTRAINT fk_mobdekbkp_company_license_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_company_license fk_mobdekbkp_company_license_on_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company_license
    ADD CONSTRAINT fk_mobdekbkp_company_license_on_type FOREIGN KEY (type_id) REFERENCES public.mobdekbkp_license_type(id);


--
-- Name: mobdekbkp_company_need fk_mobdekbkp_company_need_on_company; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company_need
    ADD CONSTRAINT fk_mobdekbkp_company_need_on_company FOREIGN KEY (company_id) REFERENCES public.mobdekbkp_company(id);


--
-- Name: mobdekbkp_company_need fk_mobdekbkp_company_need_on_company_need_application; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company_need
    ADD CONSTRAINT fk_mobdekbkp_company_need_on_company_need_application FOREIGN KEY (company_need_application_id) REFERENCES public.mobdekbkp_company_need_application(id);


--
-- Name: mobdekbkp_company_need fk_mobdekbkp_company_need_on_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company_need
    ADD CONSTRAINT fk_mobdekbkp_company_need_on_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_company fk_mobdekbkp_company_on_country; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company
    ADD CONSTRAINT fk_mobdekbkp_company_on_country FOREIGN KEY (country_id) REFERENCES public.mobdekbkp_country(id);


--
-- Name: mobdekbkp_company fk_mobdekbkp_company_on_logo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company
    ADD CONSTRAINT fk_mobdekbkp_company_on_logo FOREIGN KEY (logo_id) REFERENCES public.sys_file(id);


--
-- Name: mobdekbkp_company_type_entry fk_mobdekbkp_company_type_entry_on_company; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company_type_entry
    ADD CONSTRAINT fk_mobdekbkp_company_type_entry_on_company FOREIGN KEY (company_id) REFERENCES public.mobdekbkp_company(id);


--
-- Name: mobdekbkp_company_type_entry fk_mobdekbkp_company_type_entry_on_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_company_type_entry
    ADD CONSTRAINT fk_mobdekbkp_company_type_entry_on_type FOREIGN KEY (type_id) REFERENCES public.mobdekbkp_company_type(id);


--
-- Name: mobdekbkp_corpus fk_mobdekbkp_corpus_import_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_corpus
    ADD CONSTRAINT fk_mobdekbkp_corpus_import_device FOREIGN KEY (import_device_id) REFERENCES public.mobdekbkp_import_device(id);


--
-- Name: mobdekbkp_cost fk_mobdekbkp_cost_on_currency; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_cost
    ADD CONSTRAINT fk_mobdekbkp_cost_on_currency FOREIGN KEY (currency_id) REFERENCES public.mobdekbkp_currency(id);


--
-- Name: mobdekbkp_current_sources fk_mobdekbkp_current_sources_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_current_sources
    ADD CONSTRAINT fk_mobdekbkp_current_sources_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_custom_parameters fk_mobdekbkp_custom_parameters_import_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_custom_parameters
    ADD CONSTRAINT fk_mobdekbkp_custom_parameters_import_device FOREIGN KEY (import_device_id) REFERENCES public.mobdekbkp_import_device(id);


--
-- Name: mobdekbkp_custom_parameters fk_mobdekbkp_custom_parameters_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_custom_parameters
    ADD CONSTRAINT fk_mobdekbkp_custom_parameters_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_device_complect fk_mobdekbkp_device_complect_on_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_complect
    ADD CONSTRAINT fk_mobdekbkp_device_complect_on_device FOREIGN KEY (device_id) REFERENCES public.mobdekbkp_device(id);


--
-- Name: mobdekbkp_device_list_project_addition_entry fk_mobdekbkp_device_list_project_addition_entry_on_edited; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_addition_entry
    ADD CONSTRAINT fk_mobdekbkp_device_list_project_addition_entry_on_edited FOREIGN KEY (edited_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_device_list_project_application fk_mobdekbkp_device_list_project_application_on_suggested; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_application
    ADD CONSTRAINT fk_mobdekbkp_device_list_project_application_on_suggested FOREIGN KEY (suggested_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_device_list_project_entry fk_mobdekbkp_device_list_project_entry_on_device_list_project; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_entry
    ADD CONSTRAINT fk_mobdekbkp_device_list_project_entry_on_device_list_project FOREIGN KEY (device_list_project_id) REFERENCES public.mobdekbkp_device_list_project(id);


--
-- Name: mobdekbkp_device_list_project_entry fk_mobdekbkp_device_list_project_entry_on_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_entry
    ADD CONSTRAINT fk_mobdekbkp_device_list_project_entry_on_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_device fk_mobdekbkp_device_on_developer; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device
    ADD CONSTRAINT fk_mobdekbkp_device_on_developer FOREIGN KEY (developer_id) REFERENCES public.mobdekbkp_company(id);


--
-- Name: mobdekbkp_device fk_mobdekbkp_device_on_device_project_list; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device
    ADD CONSTRAINT fk_mobdekbkp_device_on_device_project_list FOREIGN KEY (device_project_list_id) REFERENCES public.mobdekbkp_device_list_project(id);


--
-- Name: mobdekbkp_device_part_list_complect_entry fk_mobdekbkp_device_part_list_complect_entry_on_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_complect_entry
    ADD CONSTRAINT fk_mobdekbkp_device_part_list_complect_entry_on_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_device_part_list_complect fk_mobdekbkp_device_part_list_complect_on_device_complect; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_complect
    ADD CONSTRAINT fk_mobdekbkp_device_part_list_complect_on_device_complect FOREIGN KEY (device_complect_id) REFERENCES public.mobdekbkp_device_complect(id);


--
-- Name: mobdekbkp_device_part_list_complect fk_mobdekbkp_device_part_list_complect_on_device_part; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_complect
    ADD CONSTRAINT fk_mobdekbkp_device_part_list_complect_on_device_part FOREIGN KEY (device_part_id) REFERENCES public.mobdekbkp_device_part(id);


--
-- Name: mobdekbkp_device_part_list_planned_entry fk_mobdekbkp_device_part_list_planned_entry_on_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_planned_entry
    ADD CONSTRAINT fk_mobdekbkp_device_part_list_planned_entry_on_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_device_part_list_planned fk_mobdekbkp_device_part_list_planned_on_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_planned
    ADD CONSTRAINT fk_mobdekbkp_device_part_list_planned_on_device FOREIGN KEY (device_id) REFERENCES public.mobdekbkp_device(id);


--
-- Name: mobdekbkp_device_part_list_planned fk_mobdekbkp_device_part_list_planned_on_device_part; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_planned
    ADD CONSTRAINT fk_mobdekbkp_device_part_list_planned_on_device_part FOREIGN KEY (device_part_id) REFERENCES public.mobdekbkp_device_part(id);


--
-- Name: mobdekbkp_device_part fk_mobdekbkp_device_part_on_developer; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part
    ADD CONSTRAINT fk_mobdekbkp_device_part_on_developer FOREIGN KEY (developer_id) REFERENCES public.mobdekbkp_company(id);


--
-- Name: mobdekbkp_device_parts_entry fk_mobdekbkp_device_parts_entry_on_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_parts_entry
    ADD CONSTRAINT fk_mobdekbkp_device_parts_entry_on_device FOREIGN KEY (device_id) REFERENCES public.mobdekbkp_device(id);


--
-- Name: mobdekbkp_device_parts_entry fk_mobdekbkp_device_parts_entry_on_in_device_part_comlect; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_parts_entry
    ADD CONSTRAINT fk_mobdekbkp_device_parts_entry_on_in_device_part_comlect FOREIGN KEY (in_device_part_comlect_id) REFERENCES public.mobdekbkp_device_part_list_complect(id);


--
-- Name: mobdekbkp_device_parts_entry fk_mobdekbkp_device_parts_entry_on_part; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_parts_entry
    ADD CONSTRAINT fk_mobdekbkp_device_parts_entry_on_part FOREIGN KEY (part_id) REFERENCES public.mobdekbkp_device_part(id);


--
-- Name: mobdekbkp_device_list_project_addition_entry fk_mobdekbkp_devicelistprojecadditientry_on_newtyponominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_addition_entry
    ADD CONSTRAINT fk_mobdekbkp_devicelistprojecadditientry_on_newtyponominal FOREIGN KEY (newtyponominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_device_list_project_addition fk_mobdekbkp_deviclistprojeaddit_on_device_list_project; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_addition
    ADD CONSTRAINT fk_mobdekbkp_deviclistprojeaddit_on_device_list_project FOREIGN KEY (device_list_project_id) REFERENCES public.mobdekbkp_device_list_project(id);


--
-- Name: mobdekbkp_device_list_project_addition_entry fk_mobdekbkp_devilistprojaddientr_on_devilistprojaddi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_list_project_addition_entry
    ADD CONSTRAINT fk_mobdekbkp_devilistprojaddientr_on_devilistprojaddi FOREIGN KEY (device_list_project_addition_id) REFERENCES public.mobdekbkp_device_list_project_addition(id);


--
-- Name: mobdekbkp_device_part_list_complect_entry fk_mobdekbkp_devipartlistcompentr_on_device_part_list_complect; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_complect_entry
    ADD CONSTRAINT fk_mobdekbkp_devipartlistcompentr_on_device_part_list_complect FOREIGN KEY (device_part_list_complect_id) REFERENCES public.mobdekbkp_device_part_list_complect(id);


--
-- Name: mobdekbkp_device_part_list_planned_entry fk_mobdekbkp_devipartlistplanentr_on_devipartlistplaninve; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_device_part_list_planned_entry
    ADD CONSTRAINT fk_mobdekbkp_devipartlistplanentr_on_devipartlistplaninve FOREIGN KEY (device_part_list_planned_inverse_id) REFERENCES public.mobdekbkp_device_part_list_planned(id);


--
-- Name: mobdekbkp_digital_chips fk_mobdekbkp_digital_chips_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_digital_chips
    ADD CONSTRAINT fk_mobdekbkp_digital_chips_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_electric_light_sources fk_mobdekbkp_electric_light_sources_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_electric_light_sources
    ADD CONSTRAINT fk_mobdekbkp_electric_light_sources_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_electric_vacuum_lamps fk_mobdekbkp_electric_vacuum_lamps_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_electric_vacuum_lamps
    ADD CONSTRAINT fk_mobdekbkp_electric_vacuum_lamps_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_electrical_connectors fk_mobdekbkp_electrical_connectors_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_electrical_connectors
    ADD CONSTRAINT fk_mobdekbkp_electrical_connectors_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_factory fk_mobdekbkp_factory_import_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_factory
    ADD CONSTRAINT fk_mobdekbkp_factory_import_device FOREIGN KEY (import_device_id) REFERENCES public.mobdekbkp_import_device(id);


--
-- Name: mobdekbkp_fiber_optic_components fk_mobdekbkp_fiber_optic_components_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_fiber_optic_components
    ADD CONSTRAINT fk_mobdekbkp_fiber_optic_components_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_functional_devices fk_mobdekbkp_functional_devices_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_functional_devices
    ADD CONSTRAINT fk_mobdekbkp_functional_devices_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_handbook_cad fk_mobdekbkp_handbook_cad_on_handbook; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_handbook_cad
    ADD CONSTRAINT fk_mobdekbkp_handbook_cad_on_handbook FOREIGN KEY (handbook_id) REFERENCES public.sys_file(id);


--
-- Name: mobdekbkp_handbook_entry fk_mobdekbkp_handbook_entry_on_handbook; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_handbook_entry
    ADD CONSTRAINT fk_mobdekbkp_handbook_entry_on_handbook FOREIGN KEY (handbook_id) REFERENCES public.mobdekbkp_handbook(id);


--
-- Name: mobdekbkp_handbook_entry fk_mobdekbkp_handbook_entry_on_parent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_handbook_entry
    ADD CONSTRAINT fk_mobdekbkp_handbook_entry_on_parent FOREIGN KEY (parent_id) REFERENCES public.mobdekbkp_handbook_entry(id);


--
-- Name: mobdekbkp_import_device fk_mobdekbkp_import_device_import_class; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_import_device
    ADD CONSTRAINT fk_mobdekbkp_import_device_import_class FOREIGN KEY (import_class_id) REFERENCES public.mobdekbkp_import_class(id);


--
-- Name: mobdekbkp_import_docs_schemes fk_mobdekbkp_import_docs_schemes_import_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_import_docs_schemes
    ADD CONSTRAINT fk_mobdekbkp_import_docs_schemes_import_device FOREIGN KEY (import_device_id) REFERENCES public.mobdekbkp_import_device(id);


--
-- Name: mobdekbkp_integrated_circuits fk_mobdekbkp_integrated_circuits_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_integrated_circuits
    ADD CONSTRAINT fk_mobdekbkp_integrated_circuits_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_lib_attributes fk_mobdekbkp_lib_attributes_on_ide_attrib; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_lib_attributes
    ADD CONSTRAINT fk_mobdekbkp_lib_attributes_on_ide_attrib FOREIGN KEY (ide_attrib_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_lib_attributes fk_mobdekbkp_lib_attributes_on_land_model; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_lib_attributes
    ADD CONSTRAINT fk_mobdekbkp_lib_attributes_on_land_model FOREIGN KEY (land_model_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_lib_attributes fk_mobdekbkp_lib_attributes_on_view_model; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_lib_attributes
    ADD CONSTRAINT fk_mobdekbkp_lib_attributes_on_view_model FOREIGN KEY (view_model_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_main_parameters fk_mobdekbkp_main_parameters_import_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_main_parameters
    ADD CONSTRAINT fk_mobdekbkp_main_parameters_import_device FOREIGN KEY (import_device_id) REFERENCES public.mobdekbkp_import_device(id);


--
-- Name: mobdekbkp_microassemblies_multicrystals fk_mobdekbkp_microassemblies_multicrystals_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_microassemblies_multicrystals
    ADD CONSTRAINT fk_mobdekbkp_microassemblies_multicrystals_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_outer_certificate_tests fk_mobdekbkp_outer_certificate_tests_on_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_certificate_tests
    ADD CONSTRAINT fk_mobdekbkp_outer_certificate_tests_on_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_outer_db_fail fk_mobdekbkp_outer_db_fail_on_claimed_company; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_db_fail
    ADD CONSTRAINT fk_mobdekbkp_outer_db_fail_on_claimed_company FOREIGN KEY (claimed_company_id) REFERENCES public.mobdekbkp_company(id);


--
-- Name: mobdekbkp_outer_db_fail fk_mobdekbkp_outer_db_fail_on_manufacturer; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_db_fail
    ADD CONSTRAINT fk_mobdekbkp_outer_db_fail_on_manufacturer FOREIGN KEY (manufacturer_id) REFERENCES public.mobdekbkp_company(id);


--
-- Name: mobdekbkp_outer_db_fail fk_mobdekbkp_outer_db_fail_on_source; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_db_fail
    ADD CONSTRAINT fk_mobdekbkp_outer_db_fail_on_source FOREIGN KEY (source_id) REFERENCES public.mobdekbkp_outer_information_source(id);


--
-- Name: mobdekbkp_outer_db_fail fk_mobdekbkp_outer_db_fail_on_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_db_fail
    ADD CONSTRAINT fk_mobdekbkp_outer_db_fail_on_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_outer_db_refuse fk_mobdekbkp_outer_db_refuse_import_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_db_refuse
    ADD CONSTRAINT fk_mobdekbkp_outer_db_refuse_import_device FOREIGN KEY (import_device_id) REFERENCES public.mobdekbkp_import_device(id);


--
-- Name: mobdekbkp_outer_db_refuse fk_mobdekbkp_outer_db_refuse_on_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_db_refuse
    ADD CONSTRAINT fk_mobdekbkp_outer_db_refuse_on_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_outer_db_refuse fk_mobdekbkp_outer_db_refuse_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_db_refuse
    ADD CONSTRAINT fk_mobdekbkp_outer_db_refuse_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_outer_entrance_tests fk_mobdekbkp_outer_entrance_tests_on_cert_center; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_entrance_tests
    ADD CONSTRAINT fk_mobdekbkp_outer_entrance_tests_on_cert_center FOREIGN KEY (cert_center_id) REFERENCES public.mobdekbkp_company(id);


--
-- Name: mobdekbkp_outer_entrance_tests fk_mobdekbkp_outer_entrance_tests_on_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_entrance_tests
    ADD CONSTRAINT fk_mobdekbkp_outer_entrance_tests_on_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_outer_fail_and_refuses fk_mobdekbkp_outer_fail_and_refuses_on_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_fail_and_refuses
    ADD CONSTRAINT fk_mobdekbkp_outer_fail_and_refuses_on_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_outer_list_allowing_entry fk_mobdekbkp_outer_list_allowing_entry_on_outer_list_allowing; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_list_allowing_entry
    ADD CONSTRAINT fk_mobdekbkp_outer_list_allowing_entry_on_outer_list_allowing FOREIGN KEY (outer_list_allowing_id) REFERENCES public.mobdekbkp_outer_list_allowing(id);


--
-- Name: mobdekbkp_outer_list_allowing_entry fk_mobdekbkp_outer_list_allowing_entry_on_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_list_allowing_entry
    ADD CONSTRAINT fk_mobdekbkp_outer_list_allowing_entry_on_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_outer_list_allowing fk_mobdekbkp_outer_list_allowing_on_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_list_allowing
    ADD CONSTRAINT fk_mobdekbkp_outer_list_allowing_on_type FOREIGN KEY (type_id) REFERENCES public.mobdekbkp_outer_list_type(id);


--
-- Name: mobdekbkp_outer_persistence_info fk_mobdekbkp_outer_persistence_info_on_info_source; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_persistence_info
    ADD CONSTRAINT fk_mobdekbkp_outer_persistence_info_on_info_source FOREIGN KEY (info_source_id) REFERENCES public.mobdekbkp_outer_information_source(id);


--
-- Name: mobdekbkp_outer_persistence_info fk_mobdekbkp_outer_persistence_info_on_manufacturer; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_persistence_info
    ADD CONSTRAINT fk_mobdekbkp_outer_persistence_info_on_manufacturer FOREIGN KEY (manufacturer_id) REFERENCES public.mobdekbkp_company(id);


--
-- Name: mobdekbkp_outer_persistence_info fk_mobdekbkp_outer_persistence_info_on_tiponominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_persistence_info
    ADD CONSTRAINT fk_mobdekbkp_outer_persistence_info_on_tiponominal FOREIGN KEY (tiponominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_outer_rejection fk_mobdekbkp_outer_rejection_import_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_rejection
    ADD CONSTRAINT fk_mobdekbkp_outer_rejection_import_device FOREIGN KEY (import_device_id) REFERENCES public.mobdekbkp_import_device(id);


--
-- Name: mobdekbkp_outer_rejection fk_mobdekbkp_outer_rejection_on_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_rejection
    ADD CONSTRAINT fk_mobdekbkp_outer_rejection_on_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_outer_rejection fk_mobdekbkp_outer_rejection_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_rejection
    ADD CONSTRAINT fk_mobdekbkp_outer_rejection_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_parameter fk_mobdekbkp_parameter_on_parameter_category; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_parameter
    ADD CONSTRAINT fk_mobdekbkp_parameter_on_parameter_category FOREIGN KEY (parameter_category_id) REFERENCES public.mobdekbkp_parameter_category(id);


--
-- Name: mobdekbkp_parameter fk_mobdekbkp_parameter_on_unit; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_parameter
    ADD CONSTRAINT fk_mobdekbkp_parameter_on_unit FOREIGN KEY (unit_id) REFERENCES public.mobdekbkp_unit_val(id);


--
-- Name: mobdekbkp_parameter_value fk_mobdekbkp_parameter_value_on_operation_condition; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_parameter_value
    ADD CONSTRAINT fk_mobdekbkp_parameter_value_on_operation_condition FOREIGN KEY (operation_condition_id) REFERENCES public.mobdekbkp_operation_conditions(id);


--
-- Name: mobdekbkp_parameter_value fk_mobdekbkp_parameter_value_on_parameter; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_parameter_value
    ADD CONSTRAINT fk_mobdekbkp_parameter_value_on_parameter FOREIGN KEY (parameter_id) REFERENCES public.mobdekbkp_parameter(id);


--
-- Name: mobdekbkp_parameter_value fk_mobdekbkp_parameter_value_on_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_parameter_value
    ADD CONSTRAINT fk_mobdekbkp_parameter_value_on_type FOREIGN KEY (type_id) REFERENCES public.mobdekbkp_type(id);


--
-- Name: mobdekbkp_parameter_value fk_mobdekbkp_parameter_value_on_val_str_lib; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_parameter_value
    ADD CONSTRAINT fk_mobdekbkp_parameter_value_on_val_str_lib FOREIGN KEY (val_str_lib_id) REFERENCES public.mobdekbkp_str_lib(id);


--
-- Name: mobdekbkp_parameter_value fk_mobdekbkp_parameter_value_on_val_str_rec; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_parameter_value
    ADD CONSTRAINT fk_mobdekbkp_parameter_value_on_val_str_rec FOREIGN KEY (val_str_rec_id) REFERENCES public.mobdekbkp_str_rec(id);


--
-- Name: mobdekbkp_parameters_for_purchasing fk_mobdekbkp_parameters_for_purchasing_import_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_parameters_for_purchasing
    ADD CONSTRAINT fk_mobdekbkp_parameters_for_purchasing_import_device FOREIGN KEY (import_device_id) REFERENCES public.mobdekbkp_import_device(id);


--
-- Name: mobdekbkp_photosensitive_devices fk_mobdekbkp_photosensitive_devices_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_photosensitive_devices
    ADD CONSTRAINT fk_mobdekbkp_photosensitive_devices_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_piezoelectric_devices fk_mobdekbkp_piezoelectric_devices_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_piezoelectric_devices
    ADD CONSTRAINT fk_mobdekbkp_piezoelectric_devices_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_products_ferrites_magnetodielectrics fk_mobdekbkp_products_ferrites_magnetodielectrics_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_products_ferrites_magnetodielectrics
    ADD CONSTRAINT fk_mobdekbkp_products_ferrites_magnetodielectrics_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_quantum_electronics_products fk_mobdekbkp_quantum_electronics_products_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_quantum_electronics_products
    ADD CONSTRAINT fk_mobdekbkp_quantum_electronics_products_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_resistors fk_mobdekbkp_resistors_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_resistors
    ADD CONSTRAINT fk_mobdekbkp_resistors_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_sapr_data fk_mobdekbkp_sapr_data_import_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_sapr_data
    ADD CONSTRAINT fk_mobdekbkp_sapr_data_import_device FOREIGN KEY (import_device_id) REFERENCES public.mobdekbkp_import_device(id);


--
-- Name: mobdekbkp_semiconductor_emitters fk_mobdekbkp_semiconductor_emitters_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_semiconductor_emitters
    ADD CONSTRAINT fk_mobdekbkp_semiconductor_emitters_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_sign_synthesizing_indicators fk_mobdekbkp_sign_synthesizing_indicators_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_sign_synthesizing_indicators
    ADD CONSTRAINT fk_mobdekbkp_sign_synthesizing_indicators_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_small_electric_machines fk_mobdekbkp_small_electric_machines_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_small_electric_machines
    ADD CONSTRAINT fk_mobdekbkp_small_electric_machines_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_str_lib_settings fk_mobdekbkp_str_lib_settings_on_type_class; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_str_lib_settings
    ADD CONSTRAINT fk_mobdekbkp_str_lib_settings_on_type_class FOREIGN KEY (type_class_id) REFERENCES public.mobdekbkp_type_class(id);


--
-- Name: mobdekbkp_str_rec_settings fk_mobdekbkp_str_rec_settings_on_type_class; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_str_rec_settings
    ADD CONSTRAINT fk_mobdekbkp_str_rec_settings_on_type_class FOREIGN KEY (type_class_id) REFERENCES public.mobdekbkp_type_class(id);


--
-- Name: mobdekbkp_substrate_entry fk_mobdekbkp_substrate_entry_on_substrate; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_substrate_entry
    ADD CONSTRAINT fk_mobdekbkp_substrate_entry_on_substrate FOREIGN KEY (substrate_id) REFERENCES public.mobdekbkp_substrate(id);


--
-- Name: mobdekbkp_substrate_entry fk_mobdekbkp_substrate_entry_on_typonominal_install_parameters; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_substrate_entry
    ADD CONSTRAINT fk_mobdekbkp_substrate_entry_on_typonominal_install_parameters FOREIGN KEY (typonominal_install_parameters_id) REFERENCES public.mobdekbkp_typonominal_install_parameters(id);


--
-- Name: mobdekbkp_substrate fk_mobdekbkp_substrate_on_material; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_substrate
    ADD CONSTRAINT fk_mobdekbkp_substrate_on_material FOREIGN KEY (material_id) REFERENCES public.mobdekbkp_material(id);


--
-- Name: mobdekbkp_suppliers fk_mobdekbkp_suppliers_logo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_suppliers
    ADD CONSTRAINT fk_mobdekbkp_suppliers_logo FOREIGN KEY (logo_id) REFERENCES public.sys_file(id);


--
-- Name: mobdekbkp_support_info fk_mobdekbkp_support_info_on_instructions; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_support_info
    ADD CONSTRAINT fk_mobdekbkp_support_info_on_instructions FOREIGN KEY (instructions_id) REFERENCES public.sys_file(id);


--
-- Name: mobdekbkp_svch fk_mobdekbkp_svch_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_svch
    ADD CONSTRAINT fk_mobdekbkp_svch_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_switching_products fk_mobdekbkp_switching_products_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_switching_products
    ADD CONSTRAINT fk_mobdekbkp_switching_products_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_transformers_chokes fk_mobdekbkp_transformers_chokes_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_transformers_chokes
    ADD CONSTRAINT fk_mobdekbkp_transformers_chokes_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_type_calicoholder_entry fk_mobdekbkp_type_calicoholder_entry_on_name; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_calicoholder_entry
    ADD CONSTRAINT fk_mobdekbkp_type_calicoholder_entry_on_name FOREIGN KEY (name_id) REFERENCES public.mobdekbkp_company(id);


--
-- Name: mobdekbkp_type_calicoholder_entry fk_mobdekbkp_type_calicoholder_entry_on_type_inverse; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_calicoholder_entry
    ADD CONSTRAINT fk_mobdekbkp_type_calicoholder_entry_on_type_inverse FOREIGN KEY (type_inverse_id) REFERENCES public.mobdekbkp_type(id);


--
-- Name: mobdekbkp_type_class_characteristic fk_mobdekbkp_type_class_characteristic_on_parameter; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_class_characteristic
    ADD CONSTRAINT fk_mobdekbkp_type_class_characteristic_on_parameter FOREIGN KEY (parameter_id) REFERENCES public.mobdekbkp_parameter(id);


--
-- Name: mobdekbkp_type_class_characteristic fk_mobdekbkp_type_class_characteristic_on_type_class; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_class_characteristic
    ADD CONSTRAINT fk_mobdekbkp_type_class_characteristic_on_type_class FOREIGN KEY (type_class_id) REFERENCES public.mobdekbkp_type_class(id);


--
-- Name: mobdekbkp_type_class fk_mobdekbkp_type_class_on_parent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_class
    ADD CONSTRAINT fk_mobdekbkp_type_class_on_parent FOREIGN KEY (parent_id) REFERENCES public.mobdekbkp_type_class(id);


--
-- Name: mobdekbkp_type_manufacturer_entry fk_mobdekbkp_type_manufacturer_entry_on_name; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_manufacturer_entry
    ADD CONSTRAINT fk_mobdekbkp_type_manufacturer_entry_on_name FOREIGN KEY (name_id) REFERENCES public.mobdekbkp_company(id);


--
-- Name: mobdekbkp_type_manufacturer_entry fk_mobdekbkp_type_manufacturer_entry_on_type_inverse; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_manufacturer_entry
    ADD CONSTRAINT fk_mobdekbkp_type_manufacturer_entry_on_type_inverse FOREIGN KEY (type_inverse_id) REFERENCES public.mobdekbkp_type(id);


--
-- Name: mobdekbkp_type fk_mobdekbkp_type_on_alt_class_g2710; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type
    ADD CONSTRAINT fk_mobdekbkp_type_on_alt_class_g2710 FOREIGN KEY (alt_class_g2710_id) REFERENCES public.mobdekbkp_alter_class_gost2710(id);


--
-- Name: mobdekbkp_type fk_mobdekbkp_type_on_alt_class_g56649; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type
    ADD CONSTRAINT fk_mobdekbkp_type_on_alt_class_g56649 FOREIGN KEY (alt_class_g56649_id) REFERENCES public.mobdekbkp_alter_class_gost56649(id);


--
-- Name: mobdekbkp_type fk_mobdekbkp_type_on_alt_class_mil; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type
    ADD CONSTRAINT fk_mobdekbkp_type_on_alt_class_mil FOREIGN KEY (alt_class_mil_id) REFERENCES public.mobdekbkp_alter_class_mil(id);


--
-- Name: mobdekbkp_type fk_mobdekbkp_type_on_alt_class_rel; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type
    ADD CONSTRAINT fk_mobdekbkp_type_on_alt_class_rel FOREIGN KEY (alt_class_rel_id) REFERENCES public.mobdekbkp_alter_class_reliability(id);


--
-- Name: mobdekbkp_type fk_mobdekbkp_type_on_amount_unit; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type
    ADD CONSTRAINT fk_mobdekbkp_type_on_amount_unit FOREIGN KEY (amount_unit_id) REFERENCES public.mobdekbkp_unit_dev(id);


--
-- Name: mobdekbkp_type fk_mobdekbkp_type_on_climatic_implementation; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type
    ADD CONSTRAINT fk_mobdekbkp_type_on_climatic_implementation FOREIGN KEY (climatic_implementation_id) REFERENCES public.mobdekbkp_type_climatic_implementation(id);


--
-- Name: mobdekbkp_type fk_mobdekbkp_type_on_math_model_params; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type
    ADD CONSTRAINT fk_mobdekbkp_type_on_math_model_params FOREIGN KEY (math_model_params_id) REFERENCES public.mobdekbkp_type_math_model_parameters(id);


--
-- Name: mobdekbkp_type fk_mobdekbkp_type_on_type_class; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type
    ADD CONSTRAINT fk_mobdekbkp_type_on_type_class FOREIGN KEY (type_class_id) REFERENCES public.mobdekbkp_type_class(id);


--
-- Name: mobdekbkp_type_provider_entry fk_mobdekbkp_type_provider_entry_on_name; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_provider_entry
    ADD CONSTRAINT fk_mobdekbkp_type_provider_entry_on_name FOREIGN KEY (name_id) REFERENCES public.mobdekbkp_company(id);


--
-- Name: mobdekbkp_type_provider_entry fk_mobdekbkp_type_provider_entry_on_type_inverse; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_provider_entry
    ADD CONSTRAINT fk_mobdekbkp_type_provider_entry_on_type_inverse FOREIGN KEY (type_inverse_id) REFERENCES public.mobdekbkp_type(id);


--
-- Name: mobdekbkp_typonominal_install_parameters fk_mobdekbkp_typonoinstalparame_on_body_installation_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_install_parameters
    ADD CONSTRAINT fk_mobdekbkp_typonoinstalparame_on_body_installation_document FOREIGN KEY (body_installation_document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_typonominal_install_parameters fk_mobdekbkp_typonoinstalparame_on_pin_forming_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_install_parameters
    ADD CONSTRAINT fk_mobdekbkp_typonoinstalparame_on_pin_forming_document FOREIGN KEY (pin_forming_document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_typonominal fk_mobdekbkp_typonominal_advanced_setting; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal
    ADD CONSTRAINT fk_mobdekbkp_typonominal_advanced_setting FOREIGN KEY (advanced_setting_id) REFERENCES public.mobdekbkp_advanced_setting(id);


--
-- Name: mobdekbkp_typonominal_analog fk_mobdekbkp_typonominal_analog_import_device; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_analog
    ADD CONSTRAINT fk_mobdekbkp_typonominal_analog_import_device FOREIGN KEY (import_device_id) REFERENCES public.mobdekbkp_import_device(id);


--
-- Name: mobdekbkp_typonominal_analog fk_mobdekbkp_typonominal_analog_on_parent_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_analog
    ADD CONSTRAINT fk_mobdekbkp_typonominal_analog_on_parent_typonominal FOREIGN KEY (parent_typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_typonominal_analog fk_mobdekbkp_typonominal_analog_on_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_analog
    ADD CONSTRAINT fk_mobdekbkp_typonominal_analog_on_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_typonominal fk_mobdekbkp_typonominal_basicinformation; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal
    ADD CONSTRAINT fk_mobdekbkp_typonominal_basicinformation FOREIGN KEY (basicinformation_id) REFERENCES public.mobdekbkp_basic_information(id);


--
-- Name: mobdekbkp_typonominal_body fk_mobdekbkp_typonominal_body_on_body_material; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_body
    ADD CONSTRAINT fk_mobdekbkp_typonominal_body_on_body_material FOREIGN KEY (body_material_id) REFERENCES public.mobdekbkp_material(id);


--
-- Name: mobdekbkp_typonominal_body fk_mobdekbkp_typonominal_body_on_coating_material; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_body
    ADD CONSTRAINT fk_mobdekbkp_typonominal_body_on_coating_material FOREIGN KEY (coating_material_id) REFERENCES public.mobdekbkp_material(id);


--
-- Name: mobdekbkp_typonominal_body fk_mobdekbkp_typonominal_body_on_dimensions_and_body; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_body
    ADD CONSTRAINT fk_mobdekbkp_typonominal_body_on_dimensions_and_body FOREIGN KEY (dimensions_and_body_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_typonominal_body fk_mobdekbkp_typonominal_body_on_fixator_markup; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_body
    ADD CONSTRAINT fk_mobdekbkp_typonominal_body_on_fixator_markup FOREIGN KEY (fixator_markup_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_typonominal_body fk_mobdekbkp_typonominal_body_on_photo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_body
    ADD CONSTRAINT fk_mobdekbkp_typonominal_body_on_photo FOREIGN KEY (photo_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_typonominal_body fk_mobdekbkp_typonominal_body_on_pin_forming_designation; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_body
    ADD CONSTRAINT fk_mobdekbkp_typonominal_body_on_pin_forming_designation FOREIGN KEY (pin_forming_designation_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_typonominal_body fk_mobdekbkp_typonominal_body_on_pins_material; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_body
    ADD CONSTRAINT fk_mobdekbkp_typonominal_body_on_pins_material FOREIGN KEY (pins_material_id) REFERENCES public.mobdekbkp_material(id);


--
-- Name: mobdekbkp_typonominal fk_mobdekbkp_typonominal_corpus; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal
    ADD CONSTRAINT fk_mobdekbkp_typonominal_corpus FOREIGN KEY (corpus_id) REFERENCES public.mobdekbkp_corpus(id);


--
-- Name: mobdekbkp_typonominal fk_mobdekbkp_typonominal_factory; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal
    ADD CONSTRAINT fk_mobdekbkp_typonominal_factory FOREIGN KEY (factory_id) REFERENCES public.mobdekbkp_factory(id);


--
-- Name: mobdekbkp_typonominal_install_parameters fk_mobdekbkp_typonominal_install_parameters_on_flux_brand; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_install_parameters
    ADD CONSTRAINT fk_mobdekbkp_typonominal_install_parameters_on_flux_brand FOREIGN KEY (flux_brand_id) REFERENCES public.mobdekbkp_brand_flux(id);


--
-- Name: mobdekbkp_typonominal_install_parameters fk_mobdekbkp_typonominal_install_parameters_on_gasket_material; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_install_parameters
    ADD CONSTRAINT fk_mobdekbkp_typonominal_install_parameters_on_gasket_material FOREIGN KEY (gasket_material_id) REFERENCES public.mobdekbkp_material(id);


--
-- Name: mobdekbkp_typonominal_install_parameters fk_mobdekbkp_typonominal_install_parameters_on_glue_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_install_parameters
    ADD CONSTRAINT fk_mobdekbkp_typonominal_install_parameters_on_glue_type FOREIGN KEY (glue_type_id) REFERENCES public.mobdekbkp_glue_type(id);


--
-- Name: mobdekbkp_typonominal_install_parameters fk_mobdekbkp_typonominal_install_parameters_on_install_method; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_install_parameters
    ADD CONSTRAINT fk_mobdekbkp_typonominal_install_parameters_on_install_method FOREIGN KEY (install_method_id) REFERENCES public.mobdekbkp_install_method(id);


--
-- Name: mobdekbkp_typonominal_install_parameters fk_mobdekbkp_typonominal_install_parameters_on_solder_brand; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_install_parameters
    ADD CONSTRAINT fk_mobdekbkp_typonominal_install_parameters_on_solder_brand FOREIGN KEY (solder_brand_id) REFERENCES public.mobdekbkp_brand_solder(id);


--
-- Name: mobdekbkp_typonominal fk_mobdekbkp_typonominal_on_body; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal
    ADD CONSTRAINT fk_mobdekbkp_typonominal_on_body FOREIGN KEY (body_id) REFERENCES public.mobdekbkp_typonominal_body(id);


--
-- Name: mobdekbkp_typonominal fk_mobdekbkp_typonominal_on_install_parameters; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal
    ADD CONSTRAINT fk_mobdekbkp_typonominal_on_install_parameters FOREIGN KEY (install_parameters_id) REFERENCES public.mobdekbkp_typonominal_install_parameters(id);


--
-- Name: mobdekbkp_typonominal fk_mobdekbkp_typonominal_on_lib_attributes; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal
    ADD CONSTRAINT fk_mobdekbkp_typonominal_on_lib_attributes FOREIGN KEY (lib_attributes_id) REFERENCES public.mobdekbkp_lib_attributes(id);


--
-- Name: mobdekbkp_typonominal fk_mobdekbkp_typonominal_on_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal
    ADD CONSTRAINT fk_mobdekbkp_typonominal_on_type FOREIGN KEY (type_id) REFERENCES public.mobdekbkp_type(id);


--
-- Name: mobdekbkp_typonominal fk_mobdekbkp_typonominal_on_typonominal_quality_level_import; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal
    ADD CONSTRAINT fk_mobdekbkp_typonominal_on_typonominal_quality_level_import FOREIGN KEY (typonominal_quality_level_import_id) REFERENCES public.mobdekbkp_typonominal_quality_level_import(id);


--
-- Name: mobdekbkp_typonominal fk_mobdekbkp_typonominal_on_typonominal_quality_level_native; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal
    ADD CONSTRAINT fk_mobdekbkp_typonominal_on_typonominal_quality_level_native FOREIGN KEY (typonominal_quality_level_native_id) REFERENCES public.mobdekbkp_typonominal_quality_level_native(id);


--
-- Name: mobdekbkp_typonominal fk_mobdekbkp_typonominal_parameters_for_purchasing; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal
    ADD CONSTRAINT fk_mobdekbkp_typonominal_parameters_for_purchasing FOREIGN KEY (parameters_for_purchasing_id) REFERENCES public.mobdekbkp_parameters_for_purchasing(id);


--
-- Name: mobdekbkp_typonominal_purchase_parameters fk_mobdekbkp_typonominal_purchase_parameters_on_company; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_purchase_parameters
    ADD CONSTRAINT fk_mobdekbkp_typonominal_purchase_parameters_on_company FOREIGN KEY (company_id) REFERENCES public.mobdekbkp_company(id);


--
-- Name: mobdekbkp_typonominal_purchase_parameters fk_mobdekbkp_typonominal_purchase_parameters_on_cost; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_purchase_parameters
    ADD CONSTRAINT fk_mobdekbkp_typonominal_purchase_parameters_on_cost FOREIGN KEY (cost_id) REFERENCES public.mobdekbkp_cost(id);


--
-- Name: mobdekbkp_typonominal_purchase_parameters fk_mobdekbkp_typonominal_purchase_parameters_on_typonominal; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_purchase_parameters
    ADD CONSTRAINT fk_mobdekbkp_typonominal_purchase_parameters_on_typonominal FOREIGN KEY (typonominal_id) REFERENCES public.mobdekbkp_typonominal(id);


--
-- Name: mobdekbkp_typonominal fk_mobdekbkp_typonominal_reliability_indicators; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal
    ADD CONSTRAINT fk_mobdekbkp_typonominal_reliability_indicators FOREIGN KEY (reliability_indicators_id) REFERENCES public.mobdekbkp_reliability_indicators(id);


--
-- Name: mobdekbkp_typonominal_quality_level_import_settings fk_mobdekbkp_typonqualilevelimporsetti_on_import_class; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_quality_level_import_settings
    ADD CONSTRAINT fk_mobdekbkp_typonqualilevelimporsetti_on_import_class FOREIGN KEY (import_class_id) REFERENCES public.mobdekbkp_type_class(id);


--
-- Name: reviews_moderation_property_user_link fk_modprouse_on_moderation_property; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews_moderation_property_user_link
    ADD CONSTRAINT fk_modprouse_on_moderation_property FOREIGN KEY (moderation_property_id) REFERENCES public.reviews_moderation_property(id);


--
-- Name: reviews_moderation_property_user_link fk_modprouse_on_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews_moderation_property_user_link
    ADD CONSTRAINT fk_modprouse_on_user FOREIGN KEY (user_id) REFERENCES public.sec_user(id);


--
-- Name: notificationsusers_message fk_notificationsusers_message_on_attachment; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notificationsusers_message
    ADD CONSTRAINT fk_notificationsusers_message_on_attachment FOREIGN KEY (attachment_id) REFERENCES public.sys_file(id);


--
-- Name: notificationsusers_message fk_notificationsusers_message_on_message_text; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notificationsusers_message
    ADD CONSTRAINT fk_notificationsusers_message_on_message_text FOREIGN KEY (message_text_id) REFERENCES public.notificationsusers_message_text(id);


--
-- Name: notificationsusers_message fk_notificationsusers_message_on_meta; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notificationsusers_message
    ADD CONSTRAINT fk_notificationsusers_message_on_meta FOREIGN KEY (meta_id) REFERENCES public.notificationsusers_message_meta(id);


--
-- Name: notificationsusers_message fk_notificationsusers_message_on_recipient; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notificationsusers_message
    ADD CONSTRAINT fk_notificationsusers_message_on_recipient FOREIGN KEY (recipient_id) REFERENCES public.sec_user(id);


--
-- Name: notificationsusers_message fk_notificationsusers_message_on_sender; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notificationsusers_message
    ADD CONSTRAINT fk_notificationsusers_message_on_sender FOREIGN KEY (sender_id) REFERENCES public.sec_user(id);


--
-- Name: mobdekbkp_outer_certificate_tests_document_link fk_outcertesdoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_certificate_tests_document_link
    ADD CONSTRAINT fk_outcertesdoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_outer_certificate_tests_document_link fk_outcertesdoc_on_outer_certificate_tests; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_certificate_tests_document_link
    ADD CONSTRAINT fk_outcertesdoc_on_outer_certificate_tests FOREIGN KEY (outer_certificate_tests_id) REFERENCES public.mobdekbkp_outer_certificate_tests(id);


--
-- Name: mobdekbkp_outer_entrance_tests_document_link fk_outenttesdoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_entrance_tests_document_link
    ADD CONSTRAINT fk_outenttesdoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_outer_entrance_tests_document_link fk_outenttesdoc_on_outer_entrance_tests; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_entrance_tests_document_link
    ADD CONSTRAINT fk_outenttesdoc_on_outer_entrance_tests FOREIGN KEY (outer_entrance_tests_id) REFERENCES public.mobdekbkp_outer_entrance_tests(id);


--
-- Name: mobdekbkp_outer_fail_and_refuses_document_link fk_outfaiandrefdoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_fail_and_refuses_document_link
    ADD CONSTRAINT fk_outfaiandrefdoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_outer_fail_and_refuses_document_link fk_outfaiandrefdoc_on_outer_fail_and_refuses; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_fail_and_refuses_document_link
    ADD CONSTRAINT fk_outfaiandrefdoc_on_outer_fail_and_refuses FOREIGN KEY (outer_fail_and_refuses_id) REFERENCES public.mobdekbkp_outer_fail_and_refuses(id);


--
-- Name: mobdekbkp_outer_list_allowing_document_link fk_outlisalldoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_list_allowing_document_link
    ADD CONSTRAINT fk_outlisalldoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_outer_list_allowing_document_link fk_outlisalldoc_on_outer_list_allowing; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_list_allowing_document_link
    ADD CONSTRAINT fk_outlisalldoc_on_outer_list_allowing FOREIGN KEY (outer_list_allowing_id) REFERENCES public.mobdekbkp_outer_list_allowing(id);


--
-- Name: mobdekbkp_outer_rejection_document_link fk_outrejdoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_rejection_document_link
    ADD CONSTRAINT fk_outrejdoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_outer_rejection_document_link fk_outrejdoc_on_outer_rejection; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_outer_rejection_document_link
    ADD CONSTRAINT fk_outrejdoc_on_outer_rejection FOREIGN KEY (outer_rejection_id) REFERENCES public.mobdekbkp_outer_rejection(id);


--
-- Name: report_report fk_report_report_to_def_template; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.report_report
    ADD CONSTRAINT fk_report_report_to_def_template FOREIGN KEY (default_template_id) REFERENCES public.report_template(id);


--
-- Name: report_report fk_report_report_to_report_group; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.report_report
    ADD CONSTRAINT fk_report_report_to_report_group FOREIGN KEY (group_id) REFERENCES public.report_group(id);


--
-- Name: report_template fk_report_template_to_report; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.report_template
    ADD CONSTRAINT fk_report_template_to_report FOREIGN KEY (report_id) REFERENCES public.report_report(id) ON DELETE CASCADE;


--
-- Name: reviews_entities_for_moderation fk_reviews_entities_for_moderation_on_moderation_property; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews_entities_for_moderation
    ADD CONSTRAINT fk_reviews_entities_for_moderation_on_moderation_property FOREIGN KEY (moderation_property_id) REFERENCES public.reviews_moderation_property(id);


--
-- Name: reviews_review fk_reviews_review_on_author; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reviews_review
    ADD CONSTRAINT fk_reviews_review_on_author FOREIGN KEY (author_id) REFERENCES public.sec_user(id);


--
-- Name: rulesmodule_rule_manager_rule_data_script_link fk_rrmrdsl_rule_data_script; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rulesmodule_rule_manager_rule_data_script_link
    ADD CONSTRAINT fk_rrmrdsl_rule_data_script FOREIGN KEY (rule_data_script_id) REFERENCES public.rulesmodule_rule_data_script(id);


--
-- Name: rulesmodule_rule_manager_rule_data_script_link fk_rrmrdsl_rule_manager; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rulesmodule_rule_manager_rule_data_script_link
    ADD CONSTRAINT fk_rrmrdsl_rule_manager FOREIGN KEY (rule_manager_id) REFERENCES public.rulesmodule_rule_manager(id);


--
-- Name: rulesmodule_rule_manager_rule_script_link fk_rrmrsl_rule_manager; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rulesmodule_rule_manager_rule_script_link
    ADD CONSTRAINT fk_rrmrsl_rule_manager FOREIGN KEY (rule_manager_id) REFERENCES public.rulesmodule_rule_manager(id);


--
-- Name: rulesmodule_rule_manager_rule_script_link fk_rrmrsl_rule_script; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rulesmodule_rule_manager_rule_script_link
    ADD CONSTRAINT fk_rrmrsl_rule_script FOREIGN KEY (rule_script_id) REFERENCES public.rulesmodule_rule_script(id);


--
-- Name: sec_entity_log fk_sec_entity_log_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_entity_log
    ADD CONSTRAINT fk_sec_entity_log_user FOREIGN KEY (user_id) REFERENCES public.sec_user(id);


--
-- Name: sec_filter fk_sec_filter_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_filter
    ADD CONSTRAINT fk_sec_filter_user FOREIGN KEY (user_id) REFERENCES public.sec_user(id);


--
-- Name: sec_screen_history fk_sec_history_substituted_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_screen_history
    ADD CONSTRAINT fk_sec_history_substituted_user FOREIGN KEY (substituted_user_id) REFERENCES public.sec_user(id);


--
-- Name: sec_screen_history fk_sec_history_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_screen_history
    ADD CONSTRAINT fk_sec_history_user FOREIGN KEY (user_id) REFERENCES public.sec_user(id);


--
-- Name: sec_logged_attr fk_sec_logged_attr_entity; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_logged_attr
    ADD CONSTRAINT fk_sec_logged_attr_entity FOREIGN KEY (entity_id) REFERENCES public.sec_logged_entity(id);


--
-- Name: sec_remember_me fk_sec_remember_me_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_remember_me
    ADD CONSTRAINT fk_sec_remember_me_user FOREIGN KEY (user_id) REFERENCES public.sec_user(id);


--
-- Name: sec_search_folder fk_sec_search_folder_folder; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_search_folder
    ADD CONSTRAINT fk_sec_search_folder_folder FOREIGN KEY (folder_id) REFERENCES public.sys_folder(id);


--
-- Name: sec_search_folder fk_sec_search_folder_presentation; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_search_folder
    ADD CONSTRAINT fk_sec_search_folder_presentation FOREIGN KEY (presentation_id) REFERENCES public.sec_presentation(id) ON DELETE SET NULL;


--
-- Name: sec_search_folder fk_sec_search_folder_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_search_folder
    ADD CONSTRAINT fk_sec_search_folder_user FOREIGN KEY (user_id) REFERENCES public.sec_user(id);


--
-- Name: sec_session_log fk_sec_session_log_subuser; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_session_log
    ADD CONSTRAINT fk_sec_session_log_subuser FOREIGN KEY (substituted_user_id) REFERENCES public.sec_user(id);


--
-- Name: sec_session_log fk_sec_session_log_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_session_log
    ADD CONSTRAINT fk_sec_session_log_user FOREIGN KEY (user_id) REFERENCES public.sec_user(id);


--
-- Name: sec_user fk_sec_user_file; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_user
    ADD CONSTRAINT fk_sec_user_file FOREIGN KEY (file_id) REFERENCES public.sys_file(id);


--
-- Name: sec_user_substitution fk_sec_user_substitution_substituted_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_user_substitution
    ADD CONSTRAINT fk_sec_user_substitution_substituted_user FOREIGN KEY (substituted_user_id) REFERENCES public.sec_user(id);


--
-- Name: sec_user_substitution fk_sec_user_substitution_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_user_substitution
    ADD CONSTRAINT fk_sec_user_substitution_user FOREIGN KEY (user_id) REFERENCES public.sec_user(id);


--
-- Name: mobdekbkp_str_lib_settings_str_lib_link fk_strlibsetstrlib_on_str_lib; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_str_lib_settings_str_lib_link
    ADD CONSTRAINT fk_strlibsetstrlib_on_str_lib FOREIGN KEY (str_lib_id) REFERENCES public.mobdekbkp_str_lib(id);


--
-- Name: mobdekbkp_str_lib_settings_str_lib_link fk_strlibsetstrlib_on_str_lib_settings; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_str_lib_settings_str_lib_link
    ADD CONSTRAINT fk_strlibsetstrlib_on_str_lib_settings FOREIGN KEY (str_lib_settings_id) REFERENCES public.mobdekbkp_str_lib_settings(id);


--
-- Name: mobdekbkp_str_rec_settings_str_rec_link fk_strrecsetstrrec_on_str_rec; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_str_rec_settings_str_rec_link
    ADD CONSTRAINT fk_strrecsetstrrec_on_str_rec FOREIGN KEY (str_rec_id) REFERENCES public.mobdekbkp_str_rec(id);


--
-- Name: mobdekbkp_str_rec_settings_str_rec_link fk_strrecsetstrrec_on_str_rec_settings; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_str_rec_settings_str_rec_link
    ADD CONSTRAINT fk_strrecsetstrrec_on_str_rec_settings FOREIGN KEY (str_rec_settings_id) REFERENCES public.mobdekbkp_str_rec_settings(id);


--
-- Name: sys_app_folder fk_sys_app_folder_folder; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_app_folder
    ADD CONSTRAINT fk_sys_app_folder_folder FOREIGN KEY (folder_id) REFERENCES public.sys_folder(id);


--
-- Name: sys_entity_snapshot fk_sys_entity_snapshot_author_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_entity_snapshot
    ADD CONSTRAINT fk_sys_entity_snapshot_author_id FOREIGN KEY (author_id) REFERENCES public.sec_user(id);


--
-- Name: sys_folder fk_sys_folder_parent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_folder
    ADD CONSTRAINT fk_sys_folder_parent FOREIGN KEY (parent_id) REFERENCES public.sys_folder(id);


--
-- Name: sys_sending_attachment fk_sys_sending_attachment_content_file; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_sending_attachment
    ADD CONSTRAINT fk_sys_sending_attachment_content_file FOREIGN KEY (content_file_id) REFERENCES public.sys_file(id);


--
-- Name: sys_sending_attachment fk_sys_sending_attachment_sending_message; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_sending_attachment
    ADD CONSTRAINT fk_sys_sending_attachment_sending_message FOREIGN KEY (message_id) REFERENCES public.sys_sending_message(id);


--
-- Name: sys_sending_message fk_sys_sending_message_content_file; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_sending_message
    ADD CONSTRAINT fk_sys_sending_message_content_file FOREIGN KEY (content_text_file_id) REFERENCES public.sys_file(id);


--
-- Name: mobdekbkp_typonominal_body_document_link fk_typboddoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_body_document_link
    ADD CONSTRAINT fk_typboddoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_typonominal_body_document_link fk_typboddoc_on_typonominal_body; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_body_document_link
    ADD CONSTRAINT fk_typboddoc_on_typonominal_body FOREIGN KEY (typonominal_body_id) REFERENCES public.mobdekbkp_typonominal_body(id);


--
-- Name: mobdekbkp_type_document_link_appscheme fk_typdoclinapp_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_document_link_appscheme
    ADD CONSTRAINT fk_typdoclinapp_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_type_document_link_appscheme fk_typdoclinapp_on_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_document_link_appscheme
    ADD CONSTRAINT fk_typdoclinapp_on_type FOREIGN KEY (type_id) REFERENCES public.mobdekbkp_type(id);


--
-- Name: mobdekbkp_type_document_link_delivery fk_typdoclindel_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_document_link_delivery
    ADD CONSTRAINT fk_typdoclindel_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_type_document_link_delivery fk_typdoclindel_on_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_document_link_delivery
    ADD CONSTRAINT fk_typdoclindel_on_type FOREIGN KEY (type_id) REFERENCES public.mobdekbkp_type(id);


--
-- Name: mobdekbkp_type_math_model_parameters_document_link fk_typmatmodpardoc_on_document; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_math_model_parameters_document_link
    ADD CONSTRAINT fk_typmatmodpardoc_on_document FOREIGN KEY (document_id) REFERENCES public.documents_document(id);


--
-- Name: mobdekbkp_type_math_model_parameters_document_link fk_typmatmodpardoc_on_type_math_model_parameters; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_type_math_model_parameters_document_link
    ADD CONSTRAINT fk_typmatmodpardoc_on_type_math_model_parameters FOREIGN KEY (type_math_model_parameters_id) REFERENCES public.mobdekbkp_type_math_model_parameters(id);


--
-- Name: mobdekbkp_typonominal_q_l_import_set_typonominal_q_l_import fk_typqlimp_on_typonominal_quality_level_import_settings; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_q_l_import_set_typonominal_q_l_import
    ADD CONSTRAINT fk_typqlimp_on_typonominal_quality_level_import_settings FOREIGN KEY (typonominal_quality_level_import_settings_id) REFERENCES public.mobdekbkp_typonominal_quality_level_import_settings(id);


--
-- Name: mobdekbkp_typonominal_q_l_import_set_typonominal_q_l_import fk_typqlimpsettypqlimp_on_typonominal_quality_level_import; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mobdekbkp_typonominal_q_l_import_set_typonominal_q_l_import
    ADD CONSTRAINT fk_typqlimpsettypqlimp_on_typonominal_quality_level_import FOREIGN KEY (typonominal_quality_level_import_id) REFERENCES public.mobdekbkp_typonominal_quality_level_import(id);


--
-- Name: sec_constraint sec_constraint_group; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_constraint
    ADD CONSTRAINT sec_constraint_group FOREIGN KEY (group_id) REFERENCES public.sec_group(id);


--
-- Name: sec_group_hierarchy sec_group_hierarchy_group; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_group_hierarchy
    ADD CONSTRAINT sec_group_hierarchy_group FOREIGN KEY (group_id) REFERENCES public.sec_group(id);


--
-- Name: sec_group_hierarchy sec_group_hierarchy_parent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_group_hierarchy
    ADD CONSTRAINT sec_group_hierarchy_parent FOREIGN KEY (parent_id) REFERENCES public.sec_group(id);


--
-- Name: sec_group sec_group_parent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_group
    ADD CONSTRAINT sec_group_parent FOREIGN KEY (parent_id) REFERENCES public.sec_group(id);


--
-- Name: sec_permission sec_permission_role; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_permission
    ADD CONSTRAINT sec_permission_role FOREIGN KEY (role_id) REFERENCES public.sec_role(id);


--
-- Name: sec_presentation sec_presentation_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_presentation
    ADD CONSTRAINT sec_presentation_user FOREIGN KEY (user_id) REFERENCES public.sec_user(id);


--
-- Name: sec_session_attr sec_session_attr_group; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_session_attr
    ADD CONSTRAINT sec_session_attr_group FOREIGN KEY (group_id) REFERENCES public.sec_group(id);


--
-- Name: sec_user sec_user_group; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_user
    ADD CONSTRAINT sec_user_group FOREIGN KEY (group_id) REFERENCES public.sec_group(id);


--
-- Name: sec_user_role sec_user_role_profile; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_user_role
    ADD CONSTRAINT sec_user_role_profile FOREIGN KEY (user_id) REFERENCES public.sec_user(id);


--
-- Name: sec_user_role sec_user_role_role; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_user_role
    ADD CONSTRAINT sec_user_role_role FOREIGN KEY (role_id) REFERENCES public.sec_role(id);


--
-- Name: sec_user_setting sec_user_setting_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sec_user_setting
    ADD CONSTRAINT sec_user_setting_user FOREIGN KEY (user_id) REFERENCES public.sec_user(id);


--
-- Name: sys_attr_value sys_attr_value_attr_value_parent_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_attr_value
    ADD CONSTRAINT sys_attr_value_attr_value_parent_id FOREIGN KEY (parent_id) REFERENCES public.sys_attr_value(id);


--
-- Name: sys_attr_value sys_attr_value_category_attr_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_attr_value
    ADD CONSTRAINT sys_attr_value_category_attr_id FOREIGN KEY (category_attr_id) REFERENCES public.sys_category_attr(id);


--
-- Name: sys_category_attr sys_category_attr_category_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_category_attr
    ADD CONSTRAINT sys_category_attr_category_id FOREIGN KEY (category_id) REFERENCES public.sys_category(id);


--
-- Name: sys_scheduled_execution sys_scheduled_execution_task; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sys_scheduled_execution
    ADD CONSTRAINT sys_scheduled_execution_task FOREIGN KEY (task_id) REFERENCES public.sys_scheduled_task(id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;
GRANT USAGE ON SCHEMA public TO digitstream;


--
-- Name: TABLE "Manufacturers_technical"; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public."Manufacturers_technical" FROM PUBLIC;
REVOKE ALL ON TABLE public."Manufacturers_technical" FROM postgres;
GRANT ALL ON TABLE public."Manufacturers_technical" TO postgres;
GRANT ALL ON TABLE public."Manufacturers_technical" TO digitstream;


--
-- Name: TABLE act_evt_log; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_evt_log FROM PUBLIC;
REVOKE ALL ON TABLE public.act_evt_log FROM postgres;
GRANT ALL ON TABLE public.act_evt_log TO digitstream;


--
-- Name: TABLE act_ge_bytearray; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_ge_bytearray FROM PUBLIC;
REVOKE ALL ON TABLE public.act_ge_bytearray FROM postgres;
GRANT ALL ON TABLE public.act_ge_bytearray TO digitstream;


--
-- Name: TABLE act_ge_property; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_ge_property FROM PUBLIC;
REVOKE ALL ON TABLE public.act_ge_property FROM postgres;
GRANT ALL ON TABLE public.act_ge_property TO digitstream;


--
-- Name: TABLE act_hi_actinst; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_hi_actinst FROM PUBLIC;
REVOKE ALL ON TABLE public.act_hi_actinst FROM postgres;
GRANT ALL ON TABLE public.act_hi_actinst TO digitstream;


--
-- Name: TABLE act_hi_attachment; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_hi_attachment FROM PUBLIC;
REVOKE ALL ON TABLE public.act_hi_attachment FROM postgres;
GRANT ALL ON TABLE public.act_hi_attachment TO digitstream;


--
-- Name: TABLE act_hi_comment; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_hi_comment FROM PUBLIC;
REVOKE ALL ON TABLE public.act_hi_comment FROM postgres;
GRANT ALL ON TABLE public.act_hi_comment TO digitstream;


--
-- Name: TABLE act_hi_detail; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_hi_detail FROM PUBLIC;
REVOKE ALL ON TABLE public.act_hi_detail FROM postgres;
GRANT ALL ON TABLE public.act_hi_detail TO digitstream;


--
-- Name: TABLE act_hi_identitylink; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_hi_identitylink FROM PUBLIC;
REVOKE ALL ON TABLE public.act_hi_identitylink FROM postgres;
GRANT ALL ON TABLE public.act_hi_identitylink TO digitstream;


--
-- Name: TABLE act_hi_procinst; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_hi_procinst FROM PUBLIC;
REVOKE ALL ON TABLE public.act_hi_procinst FROM postgres;
GRANT ALL ON TABLE public.act_hi_procinst TO digitstream;


--
-- Name: TABLE act_hi_taskinst; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_hi_taskinst FROM PUBLIC;
REVOKE ALL ON TABLE public.act_hi_taskinst FROM postgres;
GRANT ALL ON TABLE public.act_hi_taskinst TO digitstream;


--
-- Name: TABLE act_hi_varinst; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_hi_varinst FROM PUBLIC;
REVOKE ALL ON TABLE public.act_hi_varinst FROM postgres;
GRANT ALL ON TABLE public.act_hi_varinst TO digitstream;


--
-- Name: TABLE act_id_group; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_id_group FROM PUBLIC;
REVOKE ALL ON TABLE public.act_id_group FROM postgres;
GRANT ALL ON TABLE public.act_id_group TO digitstream;


--
-- Name: TABLE act_id_info; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_id_info FROM PUBLIC;
REVOKE ALL ON TABLE public.act_id_info FROM postgres;
GRANT ALL ON TABLE public.act_id_info TO digitstream;


--
-- Name: TABLE act_id_membership; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_id_membership FROM PUBLIC;
REVOKE ALL ON TABLE public.act_id_membership FROM postgres;
GRANT ALL ON TABLE public.act_id_membership TO digitstream;


--
-- Name: TABLE act_id_user; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_id_user FROM PUBLIC;
REVOKE ALL ON TABLE public.act_id_user FROM postgres;
GRANT ALL ON TABLE public.act_id_user TO digitstream;


--
-- Name: TABLE act_procdef_info; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_procdef_info FROM PUBLIC;
REVOKE ALL ON TABLE public.act_procdef_info FROM postgres;
GRANT ALL ON TABLE public.act_procdef_info TO digitstream;


--
-- Name: TABLE act_re_deployment; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_re_deployment FROM PUBLIC;
REVOKE ALL ON TABLE public.act_re_deployment FROM postgres;
GRANT ALL ON TABLE public.act_re_deployment TO digitstream;


--
-- Name: TABLE act_re_model; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_re_model FROM PUBLIC;
REVOKE ALL ON TABLE public.act_re_model FROM postgres;
GRANT ALL ON TABLE public.act_re_model TO digitstream;


--
-- Name: TABLE act_re_procdef; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_re_procdef FROM PUBLIC;
REVOKE ALL ON TABLE public.act_re_procdef FROM postgres;
GRANT ALL ON TABLE public.act_re_procdef TO digitstream;


--
-- Name: TABLE act_ru_event_subscr; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_ru_event_subscr FROM PUBLIC;
REVOKE ALL ON TABLE public.act_ru_event_subscr FROM postgres;
GRANT ALL ON TABLE public.act_ru_event_subscr TO digitstream;


--
-- Name: TABLE act_ru_execution; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_ru_execution FROM PUBLIC;
REVOKE ALL ON TABLE public.act_ru_execution FROM postgres;
GRANT ALL ON TABLE public.act_ru_execution TO digitstream;


--
-- Name: TABLE act_ru_identitylink; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_ru_identitylink FROM PUBLIC;
REVOKE ALL ON TABLE public.act_ru_identitylink FROM postgres;
GRANT ALL ON TABLE public.act_ru_identitylink TO digitstream;


--
-- Name: TABLE act_ru_job; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_ru_job FROM PUBLIC;
REVOKE ALL ON TABLE public.act_ru_job FROM postgres;
GRANT ALL ON TABLE public.act_ru_job TO digitstream;


--
-- Name: TABLE act_ru_task; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_ru_task FROM PUBLIC;
REVOKE ALL ON TABLE public.act_ru_task FROM postgres;
GRANT ALL ON TABLE public.act_ru_task TO digitstream;


--
-- Name: TABLE act_ru_variable; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.act_ru_variable FROM PUBLIC;
REVOKE ALL ON TABLE public.act_ru_variable FROM postgres;
GRANT ALL ON TABLE public.act_ru_variable TO digitstream;


--
-- Name: TABLE bpm_proc_actor; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.bpm_proc_actor FROM PUBLIC;
REVOKE ALL ON TABLE public.bpm_proc_actor FROM postgres;
GRANT ALL ON TABLE public.bpm_proc_actor TO digitstream;


--
-- Name: TABLE bpm_proc_attachment; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.bpm_proc_attachment FROM PUBLIC;
REVOKE ALL ON TABLE public.bpm_proc_attachment FROM postgres;
GRANT ALL ON TABLE public.bpm_proc_attachment TO digitstream;


--
-- Name: TABLE bpm_proc_attachment_type; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.bpm_proc_attachment_type FROM PUBLIC;
REVOKE ALL ON TABLE public.bpm_proc_attachment_type FROM postgres;
GRANT ALL ON TABLE public.bpm_proc_attachment_type TO digitstream;


--
-- Name: TABLE bpm_proc_definition; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.bpm_proc_definition FROM PUBLIC;
REVOKE ALL ON TABLE public.bpm_proc_definition FROM postgres;
GRANT ALL ON TABLE public.bpm_proc_definition TO digitstream;


--
-- Name: TABLE bpm_proc_instance; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.bpm_proc_instance FROM PUBLIC;
REVOKE ALL ON TABLE public.bpm_proc_instance FROM postgres;
GRANT ALL ON TABLE public.bpm_proc_instance TO digitstream;


--
-- Name: TABLE bpm_proc_model; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.bpm_proc_model FROM PUBLIC;
REVOKE ALL ON TABLE public.bpm_proc_model FROM postgres;
GRANT ALL ON TABLE public.bpm_proc_model TO digitstream;


--
-- Name: TABLE bpm_proc_role; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.bpm_proc_role FROM PUBLIC;
REVOKE ALL ON TABLE public.bpm_proc_role FROM postgres;
GRANT ALL ON TABLE public.bpm_proc_role TO digitstream;


--
-- Name: TABLE bpm_proc_task; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.bpm_proc_task FROM PUBLIC;
REVOKE ALL ON TABLE public.bpm_proc_task FROM postgres;
GRANT ALL ON TABLE public.bpm_proc_task TO digitstream;


--
-- Name: TABLE bpm_proc_task_user_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.bpm_proc_task_user_link FROM PUBLIC;
REVOKE ALL ON TABLE public.bpm_proc_task_user_link FROM postgres;
GRANT ALL ON TABLE public.bpm_proc_task_user_link TO digitstream;


--
-- Name: TABLE bpm_stencil_set; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.bpm_stencil_set FROM PUBLIC;
REVOKE ALL ON TABLE public.bpm_stencil_set FROM postgres;
GRANT ALL ON TABLE public.bpm_stencil_set TO digitstream;


--
-- Name: TABLE cubaat_ssh_credentials; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.cubaat_ssh_credentials FROM PUBLIC;
REVOKE ALL ON TABLE public.cubaat_ssh_credentials FROM postgres;
GRANT SELECT ON TABLE public.cubaat_ssh_credentials TO PUBLIC;
GRANT ALL ON TABLE public.cubaat_ssh_credentials TO digitstream;


--
-- Name: TABLE ddcrd_diagnose_execution_log; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.ddcrd_diagnose_execution_log FROM PUBLIC;
REVOKE ALL ON TABLE public.ddcrd_diagnose_execution_log FROM postgres;
GRANT SELECT ON TABLE public.ddcrd_diagnose_execution_log TO PUBLIC;
GRANT ALL ON TABLE public.ddcrd_diagnose_execution_log TO digitstream;


--
-- Name: TABLE discuss_comment; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.discuss_comment FROM PUBLIC;
REVOKE ALL ON TABLE public.discuss_comment FROM postgres;
GRANT ALL ON TABLE public.discuss_comment TO digitstream;


--
-- Name: TABLE discuss_comment_preference; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.discuss_comment_preference FROM PUBLIC;
REVOKE ALL ON TABLE public.discuss_comment_preference FROM postgres;
GRANT ALL ON TABLE public.discuss_comment_preference TO digitstream;


--
-- Name: TABLE documents_document; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.documents_document FROM PUBLIC;
REVOKE ALL ON TABLE public.documents_document FROM postgres;
GRANT ALL ON TABLE public.documents_document TO digitstream;


--
-- Name: TABLE documents_document_file_descriptor_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.documents_document_file_descriptor_link FROM PUBLIC;
REVOKE ALL ON TABLE public.documents_document_file_descriptor_link FROM postgres;
GRANT ALL ON TABLE public.documents_document_file_descriptor_link TO digitstream;


--
-- Name: TABLE documents_document_restriction; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.documents_document_restriction FROM PUBLIC;
REVOKE ALL ON TABLE public.documents_document_restriction FROM postgres;
GRANT ALL ON TABLE public.documents_document_restriction TO digitstream;


--
-- Name: TABLE documents_document_restriction_document_type_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.documents_document_restriction_document_type_link FROM PUBLIC;
REVOKE ALL ON TABLE public.documents_document_restriction_document_type_link FROM postgres;
GRANT ALL ON TABLE public.documents_document_restriction_document_type_link TO digitstream;


--
-- Name: TABLE documents_document_type; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.documents_document_type FROM PUBLIC;
REVOKE ALL ON TABLE public.documents_document_type FROM postgres;
GRANT ALL ON TABLE public.documents_document_type TO digitstream;


--
-- Name: TABLE mobdekbkp_advanced_setting; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_advanced_setting FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_advanced_setting FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_advanced_setting TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_advanced_setting TO digitstream;


--
-- Name: TABLE mobdekbkp_alter_class_gost2710; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_alter_class_gost2710 FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_alter_class_gost2710 FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_alter_class_gost2710 TO digitstream;


--
-- Name: TABLE mobdekbkp_alter_class_gost56649; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_alter_class_gost56649 FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_alter_class_gost56649 FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_alter_class_gost56649 TO digitstream;


--
-- Name: TABLE mobdekbkp_alter_class_mil; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_alter_class_mil FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_alter_class_mil FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_alter_class_mil TO digitstream;


--
-- Name: TABLE mobdekbkp_alter_class_reliability; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_alter_class_reliability FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_alter_class_reliability FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_alter_class_reliability TO digitstream;


--
-- Name: TABLE mobdekbkp_applicability_devices; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_applicability_devices FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_applicability_devices FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_applicability_devices TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_applicability_devices TO digitstream;


--
-- Name: TABLE mobdekbkp_application_common_dev; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_application_common_dev FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_application_common_dev FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_application_common_dev TO digitstream;


--
-- Name: TABLE mobdekbkp_application_common_dev_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_application_common_dev_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_application_common_dev_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_application_common_dev_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_application_common_entry; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_application_common_entry FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_application_common_entry FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_application_common_entry TO digitstream;


--
-- Name: TABLE mobdekbkp_application_common_entry_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_application_common_entry_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_application_common_entry_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_application_common_entry_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_application_new_dev_entry; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_application_new_dev_entry FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_application_new_dev_entry FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_application_new_dev_entry TO digitstream;


--
-- Name: TABLE mobdekbkp_application_new_typonominal_add; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_application_new_typonominal_add FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_application_new_typonominal_add FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_application_new_typonominal_add TO digitstream;


--
-- Name: TABLE mobdekbkp_application_new_typonominal_add_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_application_new_typonominal_add_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_application_new_typonominal_add_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_application_new_typonominal_add_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_application_new_typonominal_dev; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_application_new_typonominal_dev FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_application_new_typonominal_dev FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_application_new_typonominal_dev TO digitstream;


--
-- Name: TABLE mobdekbkp_application_okr_info; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_application_okr_info FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_application_okr_info FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_application_okr_info TO digitstream;


--
-- Name: TABLE mobdekbkp_basic_information; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_basic_information FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_basic_information FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_basic_information TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_basic_information TO digitstream;


--
-- Name: TABLE mobdekbkp_basic_information_import; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_basic_information_import FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_basic_information_import FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_basic_information_import TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_basic_information_import TO digitstream;


--
-- Name: TABLE mobdekbkp_brand_flux; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_brand_flux FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_brand_flux FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_brand_flux TO digitstream;


--
-- Name: TABLE mobdekbkp_brand_solder; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_brand_solder FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_brand_solder FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_brand_solder TO digitstream;


--
-- Name: TABLE mobdekbkp_cables_wires_cords; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_cables_wires_cords FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_cables_wires_cords FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_cables_wires_cords TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_cables_wires_cords TO digitstream;


--
-- Name: TABLE mobdekbkp_capacitors; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_capacitors FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_capacitors FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_capacitors TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_capacitors TO digitstream;


--
-- Name: TABLE mobdekbkp_cathode_ray_tubes; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_cathode_ray_tubes FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_cathode_ray_tubes FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_cathode_ray_tubes TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_cathode_ray_tubes TO digitstream;


--
-- Name: TABLE mobdekbkp_company; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_company FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_company FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_company TO digitstream;


--
-- Name: TABLE mobdekbkp_company_license; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_company_license FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_company_license FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_company_license TO digitstream;


--
-- Name: TABLE mobdekbkp_company_need; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_company_need FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_company_need FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_company_need TO digitstream;


--
-- Name: TABLE mobdekbkp_company_need_application; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_company_need_application FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_company_need_application FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_company_need_application TO digitstream;


--
-- Name: TABLE mobdekbkp_company_type; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_company_type FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_company_type FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_company_type TO digitstream;


--
-- Name: TABLE mobdekbkp_company_type_entry; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_company_type_entry FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_company_type_entry FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_company_type_entry TO digitstream;


--
-- Name: TABLE mobdekbkp_corpus; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_corpus FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_corpus FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_corpus TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_corpus TO digitstream;


--
-- Name: TABLE mobdekbkp_cost; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_cost FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_cost FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_cost TO digitstream;


--
-- Name: TABLE mobdekbkp_country; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_country FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_country FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_country TO digitstream;


--
-- Name: TABLE mobdekbkp_currency; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_currency FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_currency FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_currency TO digitstream;


--
-- Name: TABLE mobdekbkp_current_sources; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_current_sources FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_current_sources FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_current_sources TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_current_sources TO digitstream;


--
-- Name: TABLE mobdekbkp_custom_parameters; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_custom_parameters FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_custom_parameters FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_custom_parameters TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_custom_parameters TO digitstream;


--
-- Name: TABLE mobdekbkp_device; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device TO digitstream;


--
-- Name: TABLE mobdekbkp_device_complect; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_complect FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_complect FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_complect TO digitstream;


--
-- Name: TABLE mobdekbkp_device_complect_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_complect_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_complect_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_complect_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_device_complect_list; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_complect_list FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_complect_list FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_complect_list TO digitstream;


--
-- Name: TABLE mobdekbkp_device_device_filter_conditions_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_device_filter_conditions_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_device_filter_conditions_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_device_filter_conditions_link TO digitstream;


--
-- Name: TABLE mobdekbkp_device_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_device_filter_conditions; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_filter_conditions FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_filter_conditions FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_filter_conditions TO digitstream;


--
-- Name: TABLE mobdekbkp_device_list_project; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_list_project FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_list_project FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_list_project TO digitstream;


--
-- Name: TABLE mobdekbkp_device_list_project_addition; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_list_project_addition FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_list_project_addition FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_list_project_addition TO digitstream;


--
-- Name: TABLE mobdekbkp_device_list_project_addition_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_list_project_addition_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_list_project_addition_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_list_project_addition_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_device_list_project_addition_entry; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_list_project_addition_entry FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_list_project_addition_entry FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_list_project_addition_entry TO digitstream;


--
-- Name: TABLE mobdekbkp_device_list_project_application; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_list_project_application FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_list_project_application FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_list_project_application TO digitstream;


--
-- Name: TABLE mobdekbkp_device_list_project_application_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_list_project_application_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_list_project_application_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_list_project_application_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_device_list_project_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_list_project_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_list_project_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_list_project_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_device_list_project_entry; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_list_project_entry FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_list_project_entry FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_list_project_entry TO digitstream;


--
-- Name: TABLE mobdekbkp_device_part; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_part FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_part FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_part TO digitstream;


--
-- Name: TABLE mobdekbkp_device_part_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_part_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_part_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_part_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_device_part_list_complect; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_part_list_complect FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_part_list_complect FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_part_list_complect TO digitstream;


--
-- Name: TABLE mobdekbkp_device_part_list_complect_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_part_list_complect_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_part_list_complect_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_part_list_complect_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_device_part_list_complect_entry; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_part_list_complect_entry FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_part_list_complect_entry FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_part_list_complect_entry TO digitstream;


--
-- Name: TABLE mobdekbkp_device_part_list_planned; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_part_list_planned FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_part_list_planned FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_part_list_planned TO digitstream;


--
-- Name: TABLE mobdekbkp_device_part_list_planned_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_part_list_planned_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_part_list_planned_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_part_list_planned_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_device_part_list_planned_entry; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_part_list_planned_entry FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_part_list_planned_entry FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_part_list_planned_entry TO digitstream;


--
-- Name: TABLE mobdekbkp_device_parts_entry; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_device_parts_entry FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_device_parts_entry FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_device_parts_entry TO digitstream;


--
-- Name: TABLE mobdekbkp_digital_chips; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_digital_chips FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_digital_chips FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_digital_chips TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_digital_chips TO digitstream;


--
-- Name: TABLE mobdekbkp_electric_light_sources; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_electric_light_sources FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_electric_light_sources FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_electric_light_sources TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_electric_light_sources TO digitstream;


--
-- Name: TABLE mobdekbkp_electric_vacuum_lamps; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_electric_vacuum_lamps FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_electric_vacuum_lamps FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_electric_vacuum_lamps TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_electric_vacuum_lamps TO digitstream;


--
-- Name: TABLE mobdekbkp_electrical_connectors; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_electrical_connectors FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_electrical_connectors FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_electrical_connectors TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_electrical_connectors TO digitstream;


--
-- Name: TABLE mobdekbkp_factory; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_factory FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_factory FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_factory TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_factory TO digitstream;


--
-- Name: TABLE mobdekbkp_fiber_optic_components; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_fiber_optic_components FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_fiber_optic_components FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_fiber_optic_components TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_fiber_optic_components TO digitstream;


--
-- Name: TABLE mobdekbkp_functional_devices; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_functional_devices FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_functional_devices FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_functional_devices TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_functional_devices TO digitstream;


--
-- Name: TABLE mobdekbkp_glue_type; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_glue_type FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_glue_type FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_glue_type TO digitstream;


--
-- Name: TABLE mobdekbkp_handbook; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_handbook FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_handbook FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_handbook TO digitstream;


--
-- Name: TABLE mobdekbkp_handbook_cad; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_handbook_cad FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_handbook_cad FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_handbook_cad TO digitstream;


--
-- Name: TABLE mobdekbkp_handbook_entry; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_handbook_entry FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_handbook_entry FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_handbook_entry TO digitstream;


--
-- Name: TABLE mobdekbkp_import_class; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_import_class FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_import_class FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_import_class TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_import_class TO digitstream;


--
-- Name: TABLE mobdekbkp_import_device; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_import_device FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_import_device FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_import_device TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_import_device TO digitstream;


--
-- Name: TABLE mobdekbkp_import_docs_schemes; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_import_docs_schemes FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_import_docs_schemes FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_import_docs_schemes TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_import_docs_schemes TO digitstream;


--
-- Name: TABLE mobdekbkp_install_method; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_install_method FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_install_method FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_install_method TO digitstream;


--
-- Name: TABLE mobdekbkp_integrated_circuits; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_integrated_circuits FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_integrated_circuits FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_integrated_circuits TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_integrated_circuits TO digitstream;


--
-- Name: TABLE mobdekbkp_lib_attributes; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_lib_attributes FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_lib_attributes FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_lib_attributes TO digitstream;


--
-- Name: TABLE mobdekbkp_lib_attributes_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_lib_attributes_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_lib_attributes_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_lib_attributes_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_license_type; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_license_type FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_license_type FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_license_type TO digitstream;


--
-- Name: TABLE mobdekbkp_log_file_data; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_log_file_data FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_log_file_data FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_log_file_data TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_log_file_data TO digitstream;


--
-- Name: TABLE mobdekbkp_main_parameters; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_main_parameters FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_main_parameters FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_main_parameters TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_main_parameters TO digitstream;


--
-- Name: TABLE mobdekbkp_maping_entity; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_maping_entity FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_maping_entity FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_maping_entity TO digitstream;


--
-- Name: TABLE mobdekbkp_material; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_material FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_material FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_material TO digitstream;


--
-- Name: TABLE mobdekbkp_microassemblies_multicrystals; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_microassemblies_multicrystals FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_microassemblies_multicrystals FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_microassemblies_multicrystals TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_microassemblies_multicrystals TO digitstream;


--
-- Name: TABLE mobdekbkp_montage_parameters; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_montage_parameters FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_montage_parameters FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_montage_parameters TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_montage_parameters TO digitstream;


--
-- Name: TABLE mobdekbkp_operation_conditions; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_operation_conditions FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_operation_conditions FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_operation_conditions TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_certificate_tests; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_certificate_tests FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_certificate_tests FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_certificate_tests TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_certificate_tests_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_certificate_tests_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_certificate_tests_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_certificate_tests_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_db_fail; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_db_fail FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_db_fail FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_db_fail TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_db_refuse; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_db_refuse FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_db_refuse FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_db_refuse TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_db_refuse_defects; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_db_refuse_defects FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_db_refuse_defects FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_db_refuse_defects TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_db_refuse_tests; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_db_refuse_tests FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_db_refuse_tests FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_db_refuse_tests TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_entrance_tests; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_entrance_tests FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_entrance_tests FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_entrance_tests TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_entrance_tests_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_entrance_tests_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_entrance_tests_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_entrance_tests_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_fail_and_refuses; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_fail_and_refuses FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_fail_and_refuses FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_fail_and_refuses TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_fail_and_refuses_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_fail_and_refuses_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_fail_and_refuses_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_fail_and_refuses_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_information_source; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_information_source FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_information_source FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_information_source TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_list_allowing; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_list_allowing FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_list_allowing FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_list_allowing TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_list_allowing_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_list_allowing_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_list_allowing_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_list_allowing_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_list_allowing_entry; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_list_allowing_entry FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_list_allowing_entry FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_list_allowing_entry TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_list_type; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_list_type FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_list_type FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_list_type TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_persistence_info; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_persistence_info FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_persistence_info FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_persistence_info TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_rejection; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_rejection FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_rejection FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_rejection TO digitstream;


--
-- Name: TABLE mobdekbkp_outer_rejection_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_outer_rejection_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_outer_rejection_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_outer_rejection_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_parameter; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_parameter FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_parameter FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_parameter TO digitstream;


--
-- Name: TABLE mobdekbkp_parameter_category; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_parameter_category FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_parameter_category FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_parameter_category TO digitstream;


--
-- Name: TABLE mobdekbkp_parameter_value; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_parameter_value FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_parameter_value FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_parameter_value TO digitstream;


--
-- Name: TABLE mobdekbkp_parameters_for_purchasing; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_parameters_for_purchasing FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_parameters_for_purchasing FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_parameters_for_purchasing TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_parameters_for_purchasing TO digitstream;


--
-- Name: TABLE mobdekbkp_photosensitive_devices; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_photosensitive_devices FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_photosensitive_devices FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_photosensitive_devices TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_photosensitive_devices TO digitstream;


--
-- Name: TABLE mobdekbkp_piezoelectric_devices; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_piezoelectric_devices FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_piezoelectric_devices FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_piezoelectric_devices TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_piezoelectric_devices TO digitstream;


--
-- Name: TABLE mobdekbkp_plis; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_plis FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_plis FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_plis TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_plis TO digitstream;


--
-- Name: TABLE mobdekbkp_products_ferrites_magnetodielectrics; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_products_ferrites_magnetodielectrics FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_products_ferrites_magnetodielectrics FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_products_ferrites_magnetodielectrics TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_products_ferrites_magnetodielectrics TO digitstream;


--
-- Name: TABLE mobdekbkp_quantum_electronics_products; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_quantum_electronics_products FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_quantum_electronics_products FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_quantum_electronics_products TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_quantum_electronics_products TO digitstream;


--
-- Name: TABLE mobdekbkp_reliability_indicators; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_reliability_indicators FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_reliability_indicators FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_reliability_indicators TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_reliability_indicators TO digitstream;


--
-- Name: TABLE mobdekbkp_resistors; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_resistors FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_resistors FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_resistors TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_resistors TO digitstream;


--
-- Name: TABLE mobdekbkp_sapr_data; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_sapr_data FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_sapr_data FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_sapr_data TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_sapr_data TO digitstream;


--
-- Name: TABLE mobdekbkp_semiconductor_diodes; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_semiconductor_diodes FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_semiconductor_diodes FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_semiconductor_diodes TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_semiconductor_diodes TO digitstream;


--
-- Name: TABLE mobdekbkp_semiconductor_emitters; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_semiconductor_emitters FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_semiconductor_emitters FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_semiconductor_emitters TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_semiconductor_emitters TO digitstream;


--
-- Name: TABLE mobdekbkp_sign_synthesizing_indicators; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_sign_synthesizing_indicators FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_sign_synthesizing_indicators FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_sign_synthesizing_indicators TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_sign_synthesizing_indicators TO digitstream;


--
-- Name: TABLE mobdekbkp_small_electric_machines; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_small_electric_machines FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_small_electric_machines FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_small_electric_machines TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_small_electric_machines TO digitstream;


--
-- Name: TABLE mobdekbkp_str_lib; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_str_lib FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_str_lib FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_str_lib TO digitstream;


--
-- Name: TABLE mobdekbkp_str_lib_settings; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_str_lib_settings FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_str_lib_settings FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_str_lib_settings TO digitstream;


--
-- Name: TABLE mobdekbkp_str_lib_settings_str_lib_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_str_lib_settings_str_lib_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_str_lib_settings_str_lib_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_str_lib_settings_str_lib_link TO digitstream;


--
-- Name: TABLE mobdekbkp_str_rec; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_str_rec FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_str_rec FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_str_rec TO digitstream;


--
-- Name: TABLE mobdekbkp_str_rec_settings; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_str_rec_settings FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_str_rec_settings FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_str_rec_settings TO digitstream;


--
-- Name: TABLE mobdekbkp_str_rec_settings_str_rec_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_str_rec_settings_str_rec_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_str_rec_settings_str_rec_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_str_rec_settings_str_rec_link TO digitstream;


--
-- Name: TABLE mobdekbkp_substrate; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_substrate FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_substrate FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_substrate TO digitstream;


--
-- Name: TABLE mobdekbkp_substrate_entry; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_substrate_entry FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_substrate_entry FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_substrate_entry TO digitstream;


--
-- Name: TABLE mobdekbkp_suppliers; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_suppliers FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_suppliers FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_suppliers TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_suppliers TO digitstream;


--
-- Name: TABLE mobdekbkp_support_info; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_support_info FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_support_info FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_support_info TO digitstream;


--
-- Name: TABLE mobdekbkp_svch; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_svch FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_svch FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_svch TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_svch TO digitstream;


--
-- Name: TABLE mobdekbkp_switching_products; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_switching_products FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_switching_products FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_switching_products TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_switching_products TO digitstream;


--
-- Name: TABLE mobdekbkp_technical_parameters; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_technical_parameters FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_technical_parameters FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_technical_parameters TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_technical_parameters TO digitstream;


--
-- Name: TABLE mobdekbkp_terms_and_definitions; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_terms_and_definitions FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_terms_and_definitions FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_terms_and_definitions TO digitstream;


--
-- Name: TABLE mobdekbkp_transformers_chokes; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_transformers_chokes FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_transformers_chokes FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_transformers_chokes TO postgres;
GRANT ALL ON TABLE public.mobdekbkp_transformers_chokes TO digitstream;


--
-- Name: TABLE mobdekbkp_type; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_type FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_type FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_type TO digitstream;


--
-- Name: TABLE mobdekbkp_type_calicoholder_entry; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_type_calicoholder_entry FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_type_calicoholder_entry FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_type_calicoholder_entry TO digitstream;


--
-- Name: TABLE mobdekbkp_type_class; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_type_class FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_type_class FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_type_class TO digitstream;


--
-- Name: TABLE mobdekbkp_type_class_characteristic; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_type_class_characteristic FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_type_class_characteristic FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_type_class_characteristic TO digitstream;


--
-- Name: TABLE mobdekbkp_type_class_type; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_type_class_type FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_type_class_type FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_type_class_type TO digitstream;


--
-- Name: TABLE mobdekbkp_type_climatic_implementation; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_type_climatic_implementation FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_type_climatic_implementation FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_type_climatic_implementation TO digitstream;


--
-- Name: TABLE mobdekbkp_type_document_link_appscheme; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_type_document_link_appscheme FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_type_document_link_appscheme FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_type_document_link_appscheme TO digitstream;


--
-- Name: TABLE mobdekbkp_type_document_link_delivery; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_type_document_link_delivery FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_type_document_link_delivery FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_type_document_link_delivery TO digitstream;


--
-- Name: TABLE mobdekbkp_type_manufacturer_entry; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_type_manufacturer_entry FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_type_manufacturer_entry FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_type_manufacturer_entry TO digitstream;


--
-- Name: TABLE mobdekbkp_type_math_model_parameters; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_type_math_model_parameters FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_type_math_model_parameters FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_type_math_model_parameters TO digitstream;


--
-- Name: TABLE mobdekbkp_type_math_model_parameters_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_type_math_model_parameters_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_type_math_model_parameters_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_type_math_model_parameters_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_type_provider_entry; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_type_provider_entry FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_type_provider_entry FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_type_provider_entry TO digitstream;


--
-- Name: TABLE mobdekbkp_typonominal; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_typonominal FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_typonominal FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_typonominal TO digitstream;


--
-- Name: TABLE mobdekbkp_typonominal_analog; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_typonominal_analog FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_typonominal_analog FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_typonominal_analog TO digitstream;


--
-- Name: TABLE mobdekbkp_typonominal_body; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_typonominal_body FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_typonominal_body FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_typonominal_body TO digitstream;


--
-- Name: TABLE mobdekbkp_typonominal_body_document_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_typonominal_body_document_link FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_typonominal_body_document_link FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_typonominal_body_document_link TO digitstream;


--
-- Name: TABLE mobdekbkp_typonominal_install_parameters; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_typonominal_install_parameters FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_typonominal_install_parameters FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_typonominal_install_parameters TO digitstream;


--
-- Name: TABLE mobdekbkp_typonominal_purchase_parameters; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_typonominal_purchase_parameters FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_typonominal_purchase_parameters FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_typonominal_purchase_parameters TO digitstream;


--
-- Name: TABLE mobdekbkp_typonominal_q_l_import_set_typonominal_q_l_import; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_typonominal_q_l_import_set_typonominal_q_l_import FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_typonominal_q_l_import_set_typonominal_q_l_import FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_typonominal_q_l_import_set_typonominal_q_l_import TO digitstream;


--
-- Name: TABLE mobdekbkp_typonominal_quality_level_import; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_typonominal_quality_level_import FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_typonominal_quality_level_import FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_typonominal_quality_level_import TO digitstream;


--
-- Name: TABLE mobdekbkp_typonominal_quality_level_import_settings; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_typonominal_quality_level_import_settings FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_typonominal_quality_level_import_settings FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_typonominal_quality_level_import_settings TO digitstream;


--
-- Name: TABLE mobdekbkp_typonominal_quality_level_native; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_typonominal_quality_level_native FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_typonominal_quality_level_native FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_typonominal_quality_level_native TO digitstream;


--
-- Name: TABLE mobdekbkp_unit_dev; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_unit_dev FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_unit_dev FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_unit_dev TO digitstream;


--
-- Name: TABLE mobdekbkp_unit_val; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.mobdekbkp_unit_val FROM PUBLIC;
REVOKE ALL ON TABLE public.mobdekbkp_unit_val FROM postgres;
GRANT ALL ON TABLE public.mobdekbkp_unit_val TO digitstream;


--
-- Name: TABLE modb_loader; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.modb_loader FROM PUBLIC;
REVOKE ALL ON TABLE public.modb_loader FROM postgres;
GRANT ALL ON TABLE public.modb_loader TO postgres;
GRANT ALL ON TABLE public.modb_loader TO digitstream;


--
-- Name: TABLE notificationsusers_message; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.notificationsusers_message FROM PUBLIC;
REVOKE ALL ON TABLE public.notificationsusers_message FROM postgres;
GRANT ALL ON TABLE public.notificationsusers_message TO digitstream;


--
-- Name: TABLE notificationsusers_message_meta; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.notificationsusers_message_meta FROM PUBLIC;
REVOKE ALL ON TABLE public.notificationsusers_message_meta FROM postgres;
GRANT ALL ON TABLE public.notificationsusers_message_meta TO digitstream;


--
-- Name: TABLE notificationsusers_message_text; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.notificationsusers_message_text FROM PUBLIC;
REVOKE ALL ON TABLE public.notificationsusers_message_text FROM postgres;
GRANT ALL ON TABLE public.notificationsusers_message_text TO digitstream;


--
-- Name: TABLE report_group; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.report_group FROM PUBLIC;
REVOKE ALL ON TABLE public.report_group FROM postgres;
GRANT ALL ON TABLE public.report_group TO digitstream;


--
-- Name: TABLE report_report; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.report_report FROM PUBLIC;
REVOKE ALL ON TABLE public.report_report FROM postgres;
GRANT ALL ON TABLE public.report_report TO digitstream;


--
-- Name: TABLE report_template; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.report_template FROM PUBLIC;
REVOKE ALL ON TABLE public.report_template FROM postgres;
GRANT ALL ON TABLE public.report_template TO digitstream;


--
-- Name: TABLE reviews_entities_for_moderation; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.reviews_entities_for_moderation FROM PUBLIC;
REVOKE ALL ON TABLE public.reviews_entities_for_moderation FROM postgres;
GRANT ALL ON TABLE public.reviews_entities_for_moderation TO digitstream;


--
-- Name: TABLE reviews_entities_for_moderation_user_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.reviews_entities_for_moderation_user_link FROM PUBLIC;
REVOKE ALL ON TABLE public.reviews_entities_for_moderation_user_link FROM postgres;
GRANT ALL ON TABLE public.reviews_entities_for_moderation_user_link TO digitstream;


--
-- Name: TABLE reviews_moderation_property; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.reviews_moderation_property FROM PUBLIC;
REVOKE ALL ON TABLE public.reviews_moderation_property FROM postgres;
GRANT ALL ON TABLE public.reviews_moderation_property TO digitstream;


--
-- Name: TABLE reviews_moderation_property_user_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.reviews_moderation_property_user_link FROM PUBLIC;
REVOKE ALL ON TABLE public.reviews_moderation_property_user_link FROM postgres;
GRANT ALL ON TABLE public.reviews_moderation_property_user_link TO digitstream;


--
-- Name: TABLE reviews_review; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.reviews_review FROM PUBLIC;
REVOKE ALL ON TABLE public.reviews_review FROM postgres;
GRANT ALL ON TABLE public.reviews_review TO digitstream;


--
-- Name: TABLE rulesmodule_rule_data_script; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.rulesmodule_rule_data_script FROM PUBLIC;
REVOKE ALL ON TABLE public.rulesmodule_rule_data_script FROM postgres;
GRANT ALL ON TABLE public.rulesmodule_rule_data_script TO digitstream;


--
-- Name: TABLE rulesmodule_rule_manager; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.rulesmodule_rule_manager FROM PUBLIC;
REVOKE ALL ON TABLE public.rulesmodule_rule_manager FROM postgres;
GRANT ALL ON TABLE public.rulesmodule_rule_manager TO digitstream;


--
-- Name: TABLE rulesmodule_rule_manager_rule_data_script_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.rulesmodule_rule_manager_rule_data_script_link FROM PUBLIC;
REVOKE ALL ON TABLE public.rulesmodule_rule_manager_rule_data_script_link FROM postgres;
GRANT ALL ON TABLE public.rulesmodule_rule_manager_rule_data_script_link TO digitstream;


--
-- Name: TABLE rulesmodule_rule_manager_rule_script_link; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.rulesmodule_rule_manager_rule_script_link FROM PUBLIC;
REVOKE ALL ON TABLE public.rulesmodule_rule_manager_rule_script_link FROM postgres;
GRANT ALL ON TABLE public.rulesmodule_rule_manager_rule_script_link TO digitstream;


--
-- Name: TABLE rulesmodule_rule_script; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.rulesmodule_rule_script FROM PUBLIC;
REVOKE ALL ON TABLE public.rulesmodule_rule_script FROM postgres;
GRANT ALL ON TABLE public.rulesmodule_rule_script TO digitstream;


--
-- Name: TABLE sec_constraint; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_constraint FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_constraint FROM postgres;
GRANT ALL ON TABLE public.sec_constraint TO digitstream;


--
-- Name: TABLE sec_entity_log; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_entity_log FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_entity_log FROM postgres;
GRANT ALL ON TABLE public.sec_entity_log TO digitstream;


--
-- Name: TABLE sec_filter; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_filter FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_filter FROM postgres;
GRANT ALL ON TABLE public.sec_filter TO digitstream;


--
-- Name: TABLE sec_group; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_group FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_group FROM postgres;
GRANT ALL ON TABLE public.sec_group TO digitstream;


--
-- Name: TABLE sec_group_hierarchy; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_group_hierarchy FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_group_hierarchy FROM postgres;
GRANT ALL ON TABLE public.sec_group_hierarchy TO digitstream;


--
-- Name: TABLE sec_localized_constraint_msg; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_localized_constraint_msg FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_localized_constraint_msg FROM postgres;
GRANT ALL ON TABLE public.sec_localized_constraint_msg TO digitstream;


--
-- Name: TABLE sec_logged_attr; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_logged_attr FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_logged_attr FROM postgres;
GRANT ALL ON TABLE public.sec_logged_attr TO digitstream;


--
-- Name: TABLE sec_logged_entity; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_logged_entity FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_logged_entity FROM postgres;
GRANT ALL ON TABLE public.sec_logged_entity TO digitstream;


--
-- Name: TABLE sec_permission; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_permission FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_permission FROM postgres;
GRANT ALL ON TABLE public.sec_permission TO digitstream;


--
-- Name: TABLE sec_presentation; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_presentation FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_presentation FROM postgres;
GRANT ALL ON TABLE public.sec_presentation TO digitstream;


--
-- Name: TABLE sec_remember_me; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_remember_me FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_remember_me FROM postgres;
GRANT ALL ON TABLE public.sec_remember_me TO digitstream;


--
-- Name: TABLE sec_role; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_role FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_role FROM postgres;
GRANT ALL ON TABLE public.sec_role TO digitstream;


--
-- Name: TABLE sec_screen_history; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_screen_history FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_screen_history FROM postgres;
GRANT ALL ON TABLE public.sec_screen_history TO digitstream;


--
-- Name: TABLE sec_search_folder; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_search_folder FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_search_folder FROM postgres;
GRANT ALL ON TABLE public.sec_search_folder TO digitstream;


--
-- Name: TABLE sec_session_attr; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_session_attr FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_session_attr FROM postgres;
GRANT ALL ON TABLE public.sec_session_attr TO digitstream;


--
-- Name: TABLE sec_session_log; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_session_log FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_session_log FROM postgres;
GRANT ALL ON TABLE public.sec_session_log TO digitstream;


--
-- Name: TABLE sec_user; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_user FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_user FROM postgres;
GRANT ALL ON TABLE public.sec_user TO digitstream;


--
-- Name: TABLE sec_user_role; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_user_role FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_user_role FROM postgres;
GRANT ALL ON TABLE public.sec_user_role TO digitstream;


--
-- Name: TABLE sec_user_setting; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_user_setting FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_user_setting FROM postgres;
GRANT ALL ON TABLE public.sec_user_setting TO digitstream;


--
-- Name: TABLE sec_user_substitution; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sec_user_substitution FROM PUBLIC;
REVOKE ALL ON TABLE public.sec_user_substitution FROM postgres;
GRANT ALL ON TABLE public.sec_user_substitution TO digitstream;


--
-- Name: TABLE sys_access_token; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_access_token FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_access_token FROM postgres;
GRANT ALL ON TABLE public.sys_access_token TO digitstream;


--
-- Name: TABLE sys_app_folder; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_app_folder FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_app_folder FROM postgres;
GRANT ALL ON TABLE public.sys_app_folder TO digitstream;


--
-- Name: TABLE sys_attr_value; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_attr_value FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_attr_value FROM postgres;
GRANT ALL ON TABLE public.sys_attr_value TO digitstream;


--
-- Name: TABLE sys_category; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_category FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_category FROM postgres;
GRANT ALL ON TABLE public.sys_category TO digitstream;


--
-- Name: TABLE sys_category_attr; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_category_attr FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_category_attr FROM postgres;
GRANT ALL ON TABLE public.sys_category_attr TO digitstream;


--
-- Name: TABLE sys_config; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_config FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_config FROM postgres;
GRANT ALL ON TABLE public.sys_config TO digitstream;


--
-- Name: TABLE sys_db_changelog; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_db_changelog FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_db_changelog FROM postgres;
GRANT ALL ON TABLE public.sys_db_changelog TO digitstream;


--
-- Name: TABLE sys_entity_snapshot; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_entity_snapshot FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_entity_snapshot FROM postgres;
GRANT ALL ON TABLE public.sys_entity_snapshot TO digitstream;


--
-- Name: TABLE sys_entity_statistics; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_entity_statistics FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_entity_statistics FROM postgres;
GRANT ALL ON TABLE public.sys_entity_statistics TO digitstream;


--
-- Name: TABLE sys_file; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_file FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_file FROM postgres;
GRANT ALL ON TABLE public.sys_file TO digitstream;


--
-- Name: TABLE sys_folder; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_folder FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_folder FROM postgres;
GRANT ALL ON TABLE public.sys_folder TO digitstream;


--
-- Name: TABLE sys_fts_queue; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_fts_queue FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_fts_queue FROM postgres;
GRANT ALL ON TABLE public.sys_fts_queue TO digitstream;


--
-- Name: TABLE sys_jmx_instance; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_jmx_instance FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_jmx_instance FROM postgres;
GRANT ALL ON TABLE public.sys_jmx_instance TO digitstream;


--
-- Name: TABLE sys_lock_config; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_lock_config FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_lock_config FROM postgres;
GRANT ALL ON TABLE public.sys_lock_config TO digitstream;


--
-- Name: TABLE sys_query_result; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_query_result FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_query_result FROM postgres;
GRANT ALL ON TABLE public.sys_query_result TO digitstream;


--
-- Name: TABLE sys_refresh_token; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_refresh_token FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_refresh_token FROM postgres;
GRANT ALL ON TABLE public.sys_refresh_token TO digitstream;


--
-- Name: TABLE sys_scheduled_execution; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_scheduled_execution FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_scheduled_execution FROM postgres;
GRANT ALL ON TABLE public.sys_scheduled_execution TO digitstream;


--
-- Name: TABLE sys_scheduled_task; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_scheduled_task FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_scheduled_task FROM postgres;
GRANT ALL ON TABLE public.sys_scheduled_task TO digitstream;


--
-- Name: TABLE sys_sending_attachment; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_sending_attachment FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_sending_attachment FROM postgres;
GRANT ALL ON TABLE public.sys_sending_attachment TO digitstream;


--
-- Name: TABLE sys_sending_message; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_sending_message FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_sending_message FROM postgres;
GRANT ALL ON TABLE public.sys_sending_message TO digitstream;


--
-- Name: TABLE sys_server; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE public.sys_server FROM PUBLIC;
REVOKE ALL ON TABLE public.sys_server FROM postgres;
GRANT ALL ON TABLE public.sys_server TO digitstream;


--
-- PostgreSQL database dump complete
--

