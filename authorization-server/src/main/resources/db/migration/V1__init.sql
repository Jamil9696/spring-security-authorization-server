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
    scope_id BIGSERIAL,
    scope_name varchar(40),
    id varchar(100) NOT NULL,
    registered_client_id varchar(100) NOT NULL,
    principal_name varchar(200) NOT NULL,
    authorization_grant_type varchar(100) NOT NULL,
    attributes TEXT DEFAULT NULL,
    state varchar(500) DEFAULT NULL,
    authorization_code_value TEXT DEFAULT NULL,
    authorization_code_issued_at timestamp DEFAULT NULL,
    authorization_code_expires_at timestamp DEFAULT NULL,
    authorization_code_metadata TEXT DEFAULT NULL,
    access_token_value TEXT  DEFAULT NULL,
    access_token_issued_at timestamp DEFAULT NULL,
    access_token_expires_at timestamp DEFAULT NULL,
    access_token_metadata TEXT  DEFAULT NULL,
    access_token_type varchar(100) DEFAULT NULL,
    access_token_scopes varchar(1000) DEFAULT NULL,
    oidc_id_token_value TEXT  DEFAULT NULL,
    oidc_id_token_issued_at timestamp DEFAULT NULL,
    oidc_id_token_expires_at timestamp DEFAULT NULL,
    oidc_id_token_metadata TEXT  DEFAULT NULL,
    refresh_token_value TEXT  DEFAULT NULL,
    refresh_token_issued_at timestamp DEFAULT NULL,
    refresh_token_expires_at timestamp DEFAULT NULL,
    refresh_token_metadata TEXT  DEFAULT NULL,
    constraint scope_pk PRIMARY KEY ( scope_id )
);

CREATE TABLE public.role_management(
    role_management_id BIGSERIAL,
    user_id BIGINT,
    authority_id BIGINT,
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


