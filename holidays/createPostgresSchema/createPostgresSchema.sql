--select * from information_schema.sql_features;
--select * from information_schema.collations;
--select rolname from pg_catalog.pg_roles;

create role holidays LOGIN PASSWORD 'holidays';
create database holidays owner holidays;


select * from employee;

--drop role holidays;


--drop database holidays;

--select * from employee;
--select * from team;
--select * from project_role;
--update employee set first_name = 'Maxim' where emp_id = 1;
--delete from holiday_period;
--select * from holiday_period;

--update holiday_period set emp_id = 8, updated = current_timestamp where hol_period_id = 8;
--select * from holiday_period_neg_status;

--alter table holiday_period drop column date_start;

--drop table holiday_period;
--create table salary (
--emp_id NUMERIC,
--salary NUMERIC
--);
--insert into salary (emp_id, salary) values (1, 2000);
--insert into salary (emp_id, salary) values (3, 2000);


--select nextval ('employee_pkey');
--============== DROP TABLES
--drop table employee;
--drop table holiday_period;

--delete from employee;
--commit;