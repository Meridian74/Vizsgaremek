drop table if exists daily_attendance CASCADE;
drop table if exists employees CASCADE;
drop table if exists shifts CASCADE;

create table daily_attendance
(
    emp_id               bigint  not null,
    duration_in_hours    integer not null,
    expected_start_time  time,
    shift_id             bigint,
    rest_time_in_minutes integer not null,
    shift_name           varchar(255),
    entry_time           timestamp,
    exit_time            timestamp,
    date                 date    not null,
    primary key (emp_id, date)
);

create table employees
(
    emp_id     bigint generated by default as identity,
    birth_date date,
    name       varchar(255),
    primary key (emp_id)
);

create table shifts
(
    shift_id             bigint generated by default as identity,
    duration_in_hours    integer,
    expected_start_time  time,
    rest_time_in_minutes integer,
    shift_name           varchar(255),
    primary key (shift_id)
);

alter table daily_attendance
    add constraint FKh3xb9tquvpv9ad4a8ilfluw46 foreign key (emp_id) references employees;


insert into employees (birth_date, name)
values ('2000-01-01', 'Teszt Jakab');
insert into shifts (shift_name, expected_start_time, duration_in_hours, rest_time_in_minutes)
values ('DEFAULT SHIFT', '08:30:00', 8, 30);