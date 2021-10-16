CREATE TABLE IF NOT EXISTS users
(
    id    INTEGER PRIMARY KEY ,
    email    varchar(50) not null unique ,
    name  VARCHAR(50) NOT NULL
);
-- CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1;
create table if not exists tasks(
    id INTEGER PRIMARY KEY,
    user_id INTEGER references users(id),
    title varchar(50) not null,
    task_condition varchar(400) not null ,
    topic varchar(50) not null ,
    tag1 varchar(50) not null ,
    tag2 varchar(50) not null ,
    tag3 varchar(50) not null ,
    img1 varchar(200) not null ,
    img2 varchar(200) not null ,
    img3 varchar(200) not null ,
    right_answer1 varchar(200) not null ,
    right_answer2 varchar(200),
    right_answer3 varchar(200),
    decide int not null,
    cloudinary_id1 varchar(200) not null,
    cloudinary_id2 varchar(200) not null,
    cloudinary_id3 varchar(200) not null
);
-- CREATE SEQUENCE hibernate_sequence_tasks START WITH 1 INCREMENT BY 1;
create table if not exists topics(
    id integer primary key ,
    topic varchar(50) not null
);

create table if not exists users_answers(
    id integer primary key ,
    user_id integer,
    task_id integer,
    decided integer
);
-- CREATE SEQUENCE hibernate_sequence_answers START WITH 1 INCREMENT BY 1;

-- DROP SEQUENCE IF EXISTS hibernate_sequence;
--
-- drop table if exists topics;
-- drop table if exists users_answers;
-- drop table if exists tasks;
-- drop table if exists users;



