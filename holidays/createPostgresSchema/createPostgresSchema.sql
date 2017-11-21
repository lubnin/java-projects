--select * from information_schema.sql_features;
--select * from information_schema.collations;
--select rolname from pg_catalog.pg_roles;

create role holidays LOGIN PASSWORD 'holidays';
create database holidays owner holidays;


select e.last_name, e.first_name, e.login_name, e.team_id, t.team_name, hp.date_start, hp.num_days, hpns.negotiation_status_type, hpns.status_name from employee e
left join holiday_period hp on (hp.emp_id = e.emp_id)
left join team t on (e.team_id = t.team_id)
left join holiday_period_neg_status hpns on (hp.hp_negotiation_status_id = hpns.hol_period_neg_status_id);

--update employee set password='$2a$10$U5KvCQNEWbBXK3uPPUUwyutezoQLFf5W1pY1vIj..FnxU7VbmFa2O' where emp_id=39; 
--update employee set password='$2a$10$GkR7oPSZwVU8ewQjha3oZeLTu11AK7RY0XMCSySNUKECxV0QldzM2' where emp_id<>39;
--drop role holidays;

select manager.* 
from employee manager
inner join managed_teams mt on (manager.emp_id = mt.manager_id)
where mt.team_id in (select team_id from employee where emp_id = 40);
update employee set email='abmaksim@ya.ru' where emp_id = '32';

select * from authority;
select * from employee_authorities;

select hol_period_id, date_start, date_start + interval '1' day dt_inc from holiday_period hp

select hol_period_id, date_start, date_start + interval '1' day dt_inc from holiday_period hp
where (hp.date_start + interval '1' day) <= to_date('2017-11-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS');

select hol_period_id, date_start, date_start + interval ('''' || '7' || '''') day from holiday_period hp
where (hp.date_start + interval '1' day) <= to_date('2017-11-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS');

select trim(to_char(2,'999'));
select * from holiday_period_neg_status;
select * from holiday_period;
SELECT to_date('2014/04/25', 'YYYY/MM/DD');

select to_date('2017-11-01 00:00:00', 'DD-MM-YYYY HH24:MI:SS');

/*
 * Плоские запросы к основным таблицам
 */
select * from authority;
select * from employee_authorities


/*
 * Показать все отпуска
 */
select emp.emp_id, emp.last_name, emp.first_name, emp.middle_name, to_char(hp.date_start, 'DD/MM/YYYY') date_start, hp.num_days, hpns.status_name, hpns.negotiation_status_type 
from holiday_period hp
left join employee emp on (hp.emp_id = emp.emp_id)
left join holiday_period_neg_status hpns on (hp.hp_negotiation_status_id = hpns.hol_period_neg_status_id);

select * from employee;
update employee set password = '$2a$10$U5KvCQNEWbBXK3uPPUUwyutezoQLFf5W1pY1vIj..FnxU7VbmFa2O' where login_name = 'admin';
--$2a$10$U5KvCQNEWbBXK3uPPUUwyutezoQLFf5W1pY1vIj..FnxU7VbmFa2O
select * from team;
select * from holiday_period;
select * from department;
select * from managed_teams;
select * from project_role;
select * from holiday_period_neg_history;

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