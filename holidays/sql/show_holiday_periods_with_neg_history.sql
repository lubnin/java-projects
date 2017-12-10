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


select emp.emp_id, emp.last_name, hp.* from 
employee emp
left join holiday_period hp on (emp.emp_id = hp.emp_id)
where emp.last_name = 'Тохтамов';

/*
delete from holiday_period hp 
where hp.emp_id = 4;

delete from holiday_period_neg_history h 
where hol_period_id in (
489,
490,
269,
273,
274);

*/




select 
	e.emp_id, 
	e.last_name,
	concat(e.last_name, ' ', e.first_name, ' ', e.middle_name) full_name,
	--e.first_name, e.middle_name, e.login_name, 
	to_char(hp.date_start, 'DD.MM.YYYY') dt_start,
	to_char(hp.date_start, 'DD.MM.YYYY') dt_start,
	hp.num_days, 
	case hp.negotiation_mask
		when 0 then 'None'
		when 1 then 'TL'
		when 2 then 'PM'
		when 3 then 'PM+TL'
		when 4 then 'LM'
		when 5 then 'LM+TL'
		when 6 then 'LM+PM'
		when 7 then 'ALL'
	end ng_mask,
	hpns.status_name
	--hpnh.old_status, 
	--hpnh.new_status, 
	--hpnh."comment", 
	--hpnh.created 
from 
employee e
left join holiday_period hp on (e.emp_id = hp.emp_id)
left join holiday_period_neg_status hpns on (hpns.hol_period_neg_status_id = hp.hp_negotiation_status_id)
--left join holiday_period_neg_history hpnh on (hpnh.hol_period_id = hp.hol_period_id)
where e.emp_id not in (101, 1, 88,90,116,135,102,104,103,107,105,106,126,134,136,108,109,115,141,96,93,100,97,122,130,123,131,128,129,117,121,137,138,118,91,95,124,132,94,125,133,127,113,139,140,120,119)
--and e.last_name like 'Фомичев'
and hpns.status_name not in ('Отклонён')
order by e.last_name, hp.date_start;
--, hpnh.created;



