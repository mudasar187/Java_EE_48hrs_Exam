create sequence hibernate_sequence start with 1 increment by 1;

create table user_roles (user_username varchar(255) not null, roles varchar(255));
create table users (username varchar(255) not null, enabled boolean not null, password varchar(255) not null, primary key (username));

alter table user_roles add constraint FKs9rxtuttxq2ln7mtp37s4clce foreign key (user_username) references users;