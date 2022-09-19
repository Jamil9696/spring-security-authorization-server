INSERT INTO public.resource_user (first_name, last_name, email, user_pw, enabled) VALUES
('Bob', 'Leader', 'Bob@gmail.com','$2a$11$3xy6m5QYsUwB7DEP6WkA3eJG68odENIjDXa3ugyku/xmAgEm17GBe', true),
('Alice', 'Userie', 'Alice@gmail.com','$2a$11$UFvrGOHiRjcZZoxGlTMqhOIisd5YPekWD3zFQ0pUjojCfKqloo0le', true);


INSERT INTO public.authority (role_name) VALUES
('EMPLOYEE'),
('TEAM_LEADER'),
('DEPARTMENT_LEADER'),
('ADMIN');

INSERT INTO public.role_management(user_id, authority_id) VALUES
(1,3),
(1,4),
(1,2),
(2,1);


