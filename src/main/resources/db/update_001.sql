create table IF NOT EXISTS person
(
    id       serial primary key not null,
    username varchar(2000),
    password varchar(2000)
);
DELETE
FROM person;
insert into person (username, password)
values ('user', '123'),
       ('admin', '123');
