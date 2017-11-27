-- Please, be sure that table 'department' is EMPTY before running this script!
--delete from department;
--commit;
ALTER SEQUENCE seq_dep_id RESTART WITH 1;
commit;
--SELECT * FROM department;

INSERT INTO department (dep_id, created, name) VALUES (nextval('seq_dep_id'), now(), 'Департамент внедрения и развития CRM решений');
INSERT INTO department (dep_id, created, name) VALUES (nextval('seq_dep_id'), now(), 'Отдел развития CRM решений');
INSERT INTO department (dep_id, created, name) VALUES (nextval('seq_dep_id'), now(), 'Отдел внедрения решений сегмента B2C');

commit;
