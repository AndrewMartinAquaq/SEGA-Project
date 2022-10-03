CREATE TABLE Student(
    id integer not null AUTO_INCREMENT,
    first_name varchar(255) not null ,
    last_name varchar(255) not null,
    grad_year varchar(255) not null,
    primary key (id)
);

insert into Student
(first_name, last_name, grad_year)
values('John', 'Doe', 'DECEMBER2022');




