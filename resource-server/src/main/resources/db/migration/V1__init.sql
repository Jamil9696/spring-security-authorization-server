CREATE TABLE public.project(
    project_id serial,
    name varchar(40),
    description varchar(200) not null,
    constraint project_pk primary key (project_id)
);
CREATE SEQUENCE IF NOT EXISTS project_id_seq increment by 1 start with 1 OWNED BY public.project.project_id;
