create table IF NOT EXISTS person
(
    id       serial primary key not null,
    login    varchar(2000),
    password varchar(2000)
);
DELETE
FROM person;
insert into person (login, password)
values ('user', '123');
insert into person (login, password)
values ('admin', '123');
