create table users
(
 id varchar(255) not null,
 pw varchar(255) not null,
 nickname varchar(255),
 name varchar(255),
 phone varchar(255),
 email varchar(255),
 joinDate timestamp default current_timestamp,
 primary key (id)
);