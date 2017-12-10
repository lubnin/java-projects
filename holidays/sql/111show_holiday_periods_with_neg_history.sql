select 
	e.emp_id, 
	e.created, 	 
	e.last_name,
	e.first_name, 	  
	e.middle_name,
	e.email,
	e.login_name, 
	e.special_code,
	pr.role_name,
	t.team_name,
	d."name",
	t1.team_name,
	t1.team_id,
	hp.*
from 
	employee e
left join project_role pr on (e.project_role_id = pr.project_role_id)
left join team t on (e.team_id = t.team_id)
left join department d on (e.department_id = d.dep_id)
left join managed_teams mt on (mt.manager_id = e.emp_id)
left join team t1 on (mt.team_id = t1.team_id)
left join holiday_period hp on (hp.emp_id = e.emp_id)
--where --role_name <> 'Обычная'
--e.login_name = 'tl'
--e.team_id in (21,22)
order by last_name, first_name, middle_name;


select e.login_name, hp.date_start, hp.num_days, hp.negotiation_mask from employee e 
left join holiday_period hp on (hp.emp_id = e.emp_id)
where login_name = 'ba1';

select count(*) from holiday_period;
select count(*) from holiday_period_neg_history;
select count(*) from holiday_period_neg_history;


---------------------------------------------------------------------------------------------------
select emp.emp_id, emp.last_name, emp.first_name, emp.middle_name, hp.date_start, num_days, hpns.status_name 
from 
	employee emp
inner join holiday_period hp on (hp.emp_id = emp.emp_id)
inner join holiday_period_neg_status hpns on (hpns.hol_period_neg_status_id = hp.hp_negotiation_status_id)
order by last_name, first_name, middle_name;


select e.emp_id, e.last_name, e.first_name, e.middle_name, hp.date_start, hp.num_days, hpns.status_name, hpnh.old_status, hpnh.new_status, hpnh."comment" from 
employee e
left join holiday_period hp on (e.emp_id = hp.emp_id)
left join holiday_period_neg_status hpns on (hpns.hol_period_neg_status_id = hp.hp_negotiation_status_id)
left join holiday_period_neg_history hpnh on (hpnh.hol_period_id = hp.hol_period_id)
where e.emp_id not in (101, 1, 88, 90 116,135,102,104,103,107,105,106,126,134,136,108,109,115,141,96,93,100,97,122,130,123,131,128,129,117,121,137,138,118,91,95,124,132,94,125,133,127,113,139,140,120,119)
order by e.last_name;


--- Не заполнившие отпуска!
select concat(e.last_name, ' ', e.first_name, ' ', e.middle_name) full_name, e.email, tm.team_name, dp."name" from 
employee e
left join team tm on (tm.team_id = e.team_id)
left join department dp on (dp.dep_id = e.department_id)
where e.emp_id not in (select distinct hp.emp_id from holiday_period hp)
and e.emp_id not in (101, 88,90,1, 116,135,102,104,103,107,105,106,126,134,136,108,109,115,141,96,93,100,97,122,130,123,131,128,129,117,121,137,138,118,91,95,124,132,94,125,133,127,113,139,140,120,119)
--and tm.team_name like 'Бизнес-параметры РТИ';
--and tm.team_name like 'EPC РТИ';
--and tm.team_name like 'Техпродажи РТИ';
--and tm.team_name like 'ТП v2.0 РТИ';
--and tm.team_name like 'Техподдержка РТИ';
--and tm.team_name like 'Продажи РТИ';

select * from holiday_period_neg_status; --3


select concat(e.last_name, ' ', e.first_name, ' ', e.middle_name) full_name, hp.hol_period_id, hp.date_start, hp.num_days, hp.negotiation_mask, hp.hp_negotiation_status_id, st.status_name from 
employee e
left join holiday_period hp on (hp.emp_id = e.emp_id)
left join holiday_period_neg_status st on (st.hol_period_neg_status_id = hp.hp_negotiation_status_id)
where hp.negotiation_mask is not null and st.status_name = 'На согласовании';

update holiday_period hp set hp_negotiation_status_id = 3
where hol_period_id in (
61,
68,
69,
73,
233,
232,
241);


select concat(e.last_name, ' ', e.first_name, ' ', e.middle_name) full_name, e.email, tm.team_name, dp."name", hp.date_start, hp.num_days, hp.created from 
employee e
left join team tm on (tm.team_id = e.team_id)
left join holiday_period hp on (e.emp_id = hp.hol_period_id)
left join department dp on (dp.dep_id = e.department_id)
where
--where e.emp_id not in (select distinct hp.emp_id from holiday_period hp)
e.emp_id not in (101, 88,90,1, 116,135,102,104,103,107,105,106,126,134,136,108,109,115,141,96,93,100,97,122,130,123,131,128,129,117,121,137,138,118,91,95,124,132,94,125,133,127,113,139,140,120,119)
and tm.team_name = 'Техподдержка РТИ'
and e.last_name in ('Стариков');

-- Показать подвисшие
select e.last_name, to_char(hp.date_start, 'DD-MM-YYYY'), hp.hol_period_id, hp.num_days, hp.negotiation_mask, hp.hp_negotiation_status_id, st.status_name from 
employee e
left join holiday_period hp on (hp.emp_id = e.emp_id)
left join holiday_period_neg_status st on (st.hol_period_neg_status_id = hp.hp_negotiation_status_id)
where e.last_name in ('Одиноков', 'Самохин', 'Трофимов','Миненко')
and hp.hp_negotiation_status_id not in (4,5)
order by e.last_name;

-- Проталкивание в статус "Согласован"
update holiday_period set hp_negotiation_status_id = 5 where 
hol_period_id in (
168,
166,
167,
149,
147,
154,
151,
397,
314,
141,
139,
140,
137
);


select e.*, hp.* from 
holiday_period hp
inner join employee e on (e.emp_id = hp.emp_id)
where hp.negotiation_mask = 7 
and hp_negotiation_status_id <> 5;



select * from holiday_period_neg_status;



select * from employee where last_name = 'Волынин';
select * from managed_teams where manager_id = 6;

select count(*) from 
(
select 
	e.emp_id, concat(e.last_name, ' ', e.first_name, ' ', e.middle_name) full_name, e.email, tm.team_name, dp."name", hp.date_start, to_char(hp.date_start, 'DD/MM/YYYY') dt_start, hp.num_days, st.status_name, hist."comment", hist.created, hist.old_status, hist.new_status 
from 
	employee e
left join team tm on (tm.team_id = e.team_id)
left join department dp on (dp.dep_id = e.department_id)
left join holiday_period hp on (hp.emp_id = e.emp_id)
left join holiday_period_neg_status st on (st.hol_period_neg_status_id = hp.hp_negotiation_status_id)
left join holiday_period_neg_history hist on (hist.hol_period_id = hp.hol_period_id)
where e.emp_id not in (122,101, 88,90,1, 116,135,102,104,103,107,105,106,126,134,136,108,109,115,141,96,93,100,97,122,130,123,131,128,129,117,121,137,138,118,91,95,124,132,94,125,133,127,113,139,140,120,119)
and tm.team_name not like 'Эксплуатация%'
and dp."name" <> 'Отдел внедрения решений сегмента B2C'
--and e.last_name = 'Волынин'
order by full_name, hp.date_start asc
) q;



select * from table_territory;