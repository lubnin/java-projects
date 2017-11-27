-- Please, be sure that table 'team' is EMPTY before running this script!
--delete from team;
--commit;
ALTER SEQUENCE seq_team_id RESTART WITH 1;
commit;
--SELECT * FROM team;

INSERT INTO team (team_id, created, team_name) VALUES (nextval('seq_team_id'), now(), 'EPC РТИ');
INSERT INTO team (team_id, created, team_name) VALUES (nextval('seq_team_id'), now(), 'Бизнес-параметры РТИ');
INSERT INTO team (team_id, created, team_name) VALUES (nextval('seq_team_id'), now(), 'Продажи РТИ');
INSERT INTO team (team_id, created, team_name) VALUES (nextval('seq_team_id'), now(), 'Системный анализ');
INSERT INTO team (team_id, created, team_name) VALUES (nextval('seq_team_id'), now(), 'Техподдержка РТИ');
INSERT INTO team (team_id, created, team_name) VALUES (nextval('seq_team_id'), now(), 'Техпродажи РТИ');
INSERT INTO team (team_id, created, team_name) VALUES (nextval('seq_team_id'), now(), 'Тимлиды команд');
INSERT INTO team (team_id, created, team_name) VALUES (nextval('seq_team_id'), now(), 'ТП v2.0 РТИ');
INSERT INTO team (team_id, created, team_name) VALUES (nextval('seq_team_id'), now(), 'Управление');
INSERT INTO team (team_id, created, team_name) VALUES (nextval('seq_team_id'), now(), 'Миграция');
INSERT INTO team (team_id, created, team_name) VALUES (nextval('seq_team_id'), now(), 'Руководство');

commit;
