CREATE TABLE Student(
    id integer not null AUTO_INCREMENT,
    first_name varchar(255) not null ,
    last_name varchar(255) not null,
    grad_year varchar(255) not null,
    primary key (id)
);

CREATE TABLE Course(
    id integer not null AUTO_INCREMENT,
    course_name varchar(255) not null,
    capacity integer DEFAULT 0,
    credit integer not null,
    subject varchar(255) not null,
    semester varchar(255) not null,
    primary key (id)
);

CREATE TABLE Enrollment(
    id integer not null AUTO_INCREMENT,
    student_id integer not null,
    course_id integer not null,
    primary key (id),
    foreign key (student_id) references Student(id),
    foreign key (course_id) references course(id)
);

insert into Student
(first_name, last_name, grad_year)
values('John', 'Doe', 'DECEMBER2022');

insert into Course
(course_name, capacity, credit, subject, semester)
values('COM101', 100, 15, 'Java Programming', 'JANUARY2023');

insert into Enrollment(student_id, course_id)
values(1, 1);




