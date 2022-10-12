CREATE TABLE public.resource_user(
    user_id BIGSERIAL,
    first_name VARCHAR(40),
    last_name VARCHAR(40),
    email VARCHAR(40) UNIQUE,
    user_pw VARCHAR(100),
    enabled BOOLEAN not null DEFAULT true,
    CONSTRAINT resource_user_pk PRIMARY KEY ( user_id )
);

CREATE TABLE public.authority(
    authority_id BIGSERIAL,
    role_name varchar(40),
    constraint authority_pk PRIMARY KEY ( authority_id )
);

CREATE TABLE public.oauth2_registered_client(
    id varchar(100) NOT NULL ,
    client_authentication_methods varchar(200) NOT NULL,
    client_id varchar(100) NOT NULL UNIQUE,
    client_id_issued_at timestamp DEFAULT NULL,
    client_name varchar(200) NOT NULL,
    client_secret varchar(100) NOT NULL,
    client_secret_expires_at timestamp DEFAULT NULL,
    client_settings varchar(200) NOT NULL,
    authorization_grant_types varchar(200) NOT NULL,
    redirect_uris TEXT DEFAULT NULL,
    scopes TEXT DEFAULT NULL,
    token_settings TEXT DEFAULT NULL,
    constraint scope_pk PRIMARY KEY ( client_id )
);

CREATE TABLE public.role_management(
    role_management_id BIGSERIAL,
    user_id BIGINT,
    authority_id BIGINT,
    client_id varchar(100),
    constraint role_management_pk PRIMARY KEY ( role_management_id )
);


CREATE SEQUENCE IF NOT EXISTS user_id_seq INCREMENT BY 1 START WITH 1 OWNED BY public.resource_user.user_id;
CREATE SEQUENCE IF NOT EXISTS authority_id_seq INCREMENT BY 1 START WITH 1 OWNED BY public.authority.authority_id;
CREATE SEQUENCE IF NOT EXISTS role_management_id_seq INCREMENT BY 1 START WITH 1 OWNED BY public.role_management.role_management_id;

ALTER TABLE public.role_management
    ADD CONSTRAINT FK_ROLE_MANAGEMENT_AUTHORITY FOREIGN KEY (authority_id)
        REFERENCES public.authority (authority_id)
        ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE public.role_management
    ADD CONSTRAINT FK_ROLE_MANAGEMENT_RESOURCE_USER FOREIGN KEY (user_id)
        REFERENCES public.resource_user (user_id)
        ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE public.role_management
    ADD CONSTRAINT FK_ROLE_MANAGEMENT_OAUTH2_REGISTERED_CLIENT FOREIGN KEY (client_id)
        REFERENCES public.oauth2_registered_client (client_id)
        ON DELETE RESTRICT ON UPDATE RESTRICT;


