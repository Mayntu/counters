CREATE SEQUENCE counter_group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE counter_group_model (
    id bigint not null default nextval('counter_group_id_seq'),
    name varchar(255),
    group_name varchar(255),
    primary key (id)
);


CREATE SEQUENCE counter_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE counter_model (
    id bigint not null default nextval('counter_id_seq'),
    name varchar(255),
    group_name varchar(255),
    primary key (id)
);


CREATE SEQUENCE counter_reading_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE counter_reading_model (
    id bigint not null,
    current_reading float4 not null,
    counter_id varchar(255),
    date varchar(255),
    group_id varchar(255),
    primary key (id)
);