drop table if exists author CASCADE;
drop table if exists tweet CASCADE;
drop table if exists following CASCADE;

create table author
(
    id varchar(20)  primary key,
    create_time     timestamp
);

create table tweet
(
    id              bigint auto_increment primary key,
    author_id       varchar(10),
    text            varchar(140),
    create_time     timestamp
);

create table following
(
    id                  bigint auto_increment primary key,
    user_id             varchar(10),
    followed_user_id    varchar(10),
    create_time         timestamp
);

alter table following
    add constraint fk_following_user_id foreign key (user_id) references author (id);
alter table following
    add constraint fk_following_followed_user_id foreign key (followed_user_id) references author (id);