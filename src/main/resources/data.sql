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
    credit integer DEFAULT 10 CHECK (credit BETWEEN 0 and 20),
    subject varchar(255) not null,
    semester varchar(255) not null,
    primary key (id)
);

CREATE TABLE Enrollment(
    student_id integer not null,
    course_id integer not null,
    PRIMARY KEY (student_id, course_id),
    foreign key (student_id) references Student(id)  ON DELETE CASCADE,
    foreign key (course_id) references course(id)  ON DELETE CASCADE
);

insert into Student
(first_name, last_name, grad_year)
values('John', 'Doe', '2024');

insert into Course
(course_name, capacity, credit, subject, semester)
values('COM101', 100, 15, 'Java Programming', 'WINTER2023');

insert into Enrollment(student_id, course_id)
values(1, 1);




