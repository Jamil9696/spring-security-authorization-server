INSERT INTO public.resource_user (first_name, last_name, email, user_pw, enabled) VALUES
('Bob', 'Leader', 'Bob@gmail.com','$2a$11$3xy6m5QYsUwB7DEP6WkA3eJG68odENIjDXa3ugyku/xmAgEm17GBe', true),
('Alice', 'Userie', 'Alice@gmail.com','$2a$11$UFvrGOHiRjcZZoxGlTMqhOIisd5YPekWD3zFQ0pUjojCfKqloo0le', true);


INSERT INTO public.authority (role_name) VALUES
('EMPLOYEE'),
('TEAM_LEADER'),
('DEPARTMENT_LEADER'),
('ADMIN');

INSERT INTO public.oauth2_registered_client(id, client_authentication_methods, client_id, client_id_issued_at, client_name, client_secret, client_secret_expires_at, client_settings, authorization_grant_types, redirect_uris, scopes, token_settings) VALUES
('74c869df-166c-4904-ae33-39b85e7ac7f4','client_secret_basic','client','2022-09-20 19:43:08.820980','74c869df-166c-4904-ae33-39b85e7ac7f4','$2a$10$GXaAH1mYcQk0FBmLlTcsfO61LQn0qxubKNWrTDhTm89IzoFyDevTi',null,'{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}','refresh_token,client_credentials,password,authorization_code','http://127.0.0.1:3000/authorized','openid','{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",36000.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",36000.000000000]}'),
('233f4433-b6db-43de-895e-5de90961b72d','client_secret_basic','client2','2022-09-20 19:43:09.002006','233f4433-b6db-43de-895e-5de90961b72d','$2a$10$ukjcwqZM/eTmMg5JHTbySuGwGZAUiYnAZl8qle/30Ql5oMt1sZpJe',null,'{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}','refresh_token,client_credentials,password,authorization_code','http://127.0.0.1:3000/authorized','openid','{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",36000.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",36000.000000000]}');

INSERT INTO public.role_management(user_id, authority_id, client_id, global_enabled) VALUES
(1,3,'client',false),
(1,4,'client2',false),
(1,2,'client',false),
(1,1,'client',true),
(2,1,'client',false);


