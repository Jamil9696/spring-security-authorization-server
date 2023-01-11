CREATE TABLE public.resource_user(
    user_id BIGSERIAL,
    first_name VARCHAR(40),
    last_name VARCHAR(40),
    email VARCHAR(40) UNIQUE,
    user_pw VARCHAR(100),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT resource_user_pk PRIMARY KEY ( user_id )
);

CREATE TABLE public.otp(
    otp_id BIGSERIAL,
    user_id BIGINT,
    otp VARCHAR(200),
    created_at VARCHAR(30),
    CONSTRAINT otp_pk PRIMARY KEY (otp_id)
);

CREATE TABLE public.authority(
    authority_id BIGSERIAL,
    role_name VARCHAR(40),
    CONSTRAINT authority_pk PRIMARY KEY ( authority_id )
);

CREATE TABLE public.oauth2_registered_client(
    id VARCHAR NOT NULL ,
    client_authentication_methods VARCHAR NOT NULL,
    client_id VARCHAR NOT NULL UNIQUE,
    client_id_issued_at TIMESTAMP DEFAULT NULL,
    client_name VARCHAR NOT NULL,
    client_secret VARCHAR NOT NULL,
    client_secret_expires_at TIMESTAMP DEFAULT NULL,
    client_settings VARCHAR NOT NULL,
    authorization_grant_types VARCHAR NOT NULL,
    redirect_uris TEXT DEFAULT NULL,
    scopes VARCHAR DEFAULT NULL,
    token_settings TEXT DEFAULT NULL,
    constraint scope_pk PRIMARY KEY ( client_id )
);

CREATE TABLE public.oauth2_authorization_consent(
    registered_client_id VARCHAR NOT NULL,
    principal_name VARCHAR NOT NULL,
    authorities VARCHAR
);

CREATE TABLE oauth2_authorization(
    id VARCHAR NOT NULL,
    registered_client_id VARCHAR NOT NULL,
    principal_name VARCHAR NOT NULL,
    authorization_grant_type VARCHAR NOT NULL,
    attributes VARCHAR,
    state VARCHAR DEFAULT NULL,
    authorization_code_value VARCHAR DEFAULT NULL,
    authorization_code_issued_at TIMESTAMP DEFAULT NULL,
    authorization_code_expires_at TIMESTAMP DEFAULT NULL,
    authorization_code_metadata VARCHAR DEFAULT NULL,
    access_token_value VARCHAR DEFAULT NULL,
    access_token_issued_at TIMESTAMP DEFAULT NULL,
    access_token_expires_at TIMESTAMP DEFAULT NULL,
    access_token_type VARCHAR,
    access_token_metadata VARCHAR DEFAULT NULL,
    access_token_scopes VARCHAR DEFAULT NULL,
    oidc_id_token_value VARCHAR DEFAULT NULL,
    oidc_id_token_issued_at TIMESTAMP DEFAULT NULL,
    oidc_id_token_expires_at TIMESTAMP DEFAULT NULL,
    oidc_id_token_metadata VARCHAR DEFAULT NULL,
    refresh_token_value VARCHAR DEFAULT NULL,
    refresh_token_issued_at TIMESTAMP DEFAULT NULL,
    refresh_token_expires_at TIMESTAMP DEFAULT NULL,
    refresh_token_metadata VARCHAR
);


CREATE TABLE public.role_management(
    role_management_id BIGSERIAL,
    user_id BIGINT,
    authority_id BIGINT,
    client_id VARCHAR,
    global_enabled BOOLEAN DEFAULT FALSE,
    CONSTRAINT role_management_pk PRIMARY KEY ( role_management_id )
);


CREATE SEQUENCE IF NOT EXISTS user_id_seq INCREMENT BY 1 START WITH 1 OWNED BY public.resource_user.user_id;
CREATE SEQUENCE IF NOT EXISTS otp_id_seq INCREMENT BY 1 START WITH 1 OWNED BY public.otp.otp_id;
CREATE SEQUENCE IF NOT EXISTS authority_id_seq INCREMENT BY 1 START WITH 1 OWNED BY public.authority.authority_id;
CREATE SEQUENCE IF NOT EXISTS role_management_id_seq INCREMENT BY 1 START WITH 1 OWNED BY public.role_management.role_management_id;


ALTER TABLE public.otp
    ADD CONSTRAINT FK_OTP_RESOURCE_USER FOREIGN KEY (user_id)
        REFERENCES public.resource_user (user_id)
        ON DELETE CASCADE ON UPDATE RESTRICT;


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


