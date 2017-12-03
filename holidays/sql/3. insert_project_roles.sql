ALTER SEQUENCE public.seq_proj_role_id RESTART WITH 1;
commit;

﻿INSERT INTO public.project_role (project_role_id,created,special_type,role_description,role_name,updated) VALUES 
(nextval('seq_proj_role_id'), now(),'PROJECT_ROLE_SPECIAL_TYPE_REGULAR','Обычная','Обычная',NULL);

INSERT INTO public.project_role (project_role_id,created,special_type,role_description,role_name,updated) VALUES 
(nextval('seq_proj_role_id'), now(),'PROJECT_ROLE_SPECIAL_TYPE_TEAM_LEAD','Тимлид','Тимлид',NULL);

INSERT INTO public.project_role (project_role_id,created,special_type,role_description,role_name,updated) VALUES 
(nextval('seq_proj_role_id'), now(),'PROJECT_ROLE_SPECIAL_TYPE_PROJECT_MANAGER','Руководитель проекта','Руководитель проекта',NULL);

INSERT INTO public.project_role (project_role_id,created,special_type,role_description,role_name,updated) VALUES 
(nextval('seq_proj_role_id'), now(),'PROJECT_ROLE_SPECIAL_TYPE_LINE_MANAGER','Линейный руководитель','Линейный руководитель',NULL);

INSERT INTO public.project_role (project_role_id,created,special_type,role_description,role_name,updated) VALUES 
(nextval('seq_proj_role_id'), now(),'PROJECT_ROLE_SPECIAL_TYPE_SUPERVISOR','Супервизор','Супервизор',NULL);

commit;
