-- CREATE SEQUENCE user_sequence START WITH 1 INCREMENT BY 1;
-- CREATE SEQUENCE task_sequence START WITH 1 INCREMENT BY 1;
-- CREATE SEQUENCE user_answer_sequence START WITH 1 INCREMENT BY 1;
-- CREATE SEQUENCE estimation_sequence START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS user_info
(
    id    INTEGER PRIMARY KEY ,
    email    varchar(50) not null unique ,
    name  VARCHAR(50) NOT NULL
);

create table if not exists topics(
    id integer primary key ,
    topic varchar(50) not null
);
create table if not exists tasks(
    id INTEGER PRIMARY KEY,
    user_id integer references user_info(id),
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
    cloudinary_id3 varchar(200) not null,
    estimation_AVG int ,
    user_estimation int
);

create table if not exists user_answer(
    id integer primary key ,
    user_id integer references user_info(id),
    task_id integer references tasks(id),
    decided integer
);

create table if not exists estimations(
    id integer primary key ,
    user_id integer not null references user_info(id),
    task_id integer not null references tasks(id),
    estimation int not null
);


-- DROP SEQUENCE IF EXISTS user_sequence;
-- DROP SEQUENCE IF EXISTS task_sequence;
-- DROP SEQUENCE IF EXISTS user_answer_sequence;
-- DROP SEQUENCE IF EXISTS estimation_sequence;
--
-- drop table if exists topics;
-- drop table if exists estimations;
-- drop table if exists user_answer;
-- drop table if exists tasks;
-- drop table if exists user_info;