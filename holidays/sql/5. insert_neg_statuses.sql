-- Please, be sure that table 'employee' is EMPTY before running this script!
--delete from employee;
ALTER SEQUENCE seq_hol_period_neg_status_id RESTART WITH 1;
commit;


﻿INSERT INTO public.holiday_period_neg_status (hol_period_neg_status_id,created,negotiation_status_type,status_description,status_name,updated) VALUES (
nextval('seq_hol_period_neg_status_id'), now(),'NEGOTIATION_STATUS_TYPE_NEW','Период отпуска переводится в данный статус при добавлении отпуска сотрудником. Черновик, который не виден никому из руководителей и виден лишь сотруднику.','Новый',NULL);

INSERT INTO public.holiday_period_neg_status (hol_period_neg_status_id,created,negotiation_status_type,status_description,status_name,updated) VALUES (
nextval('seq_hol_period_neg_status_id'), now(),'NEGOTIATION_STATUS_TYPE_NEGOTIATING','Период отпуска переводится в данный статус при отправке сотрудником на согласование руководителей. В данном статусе период отпуска уже не может быть удалён сотрудником.','На согласовании',NULL);

INSERT INTO public.holiday_period_neg_status (hol_period_neg_status_id,created,negotiation_status_type,status_description,status_name,updated) VALUES (
nextval('seq_hol_period_neg_status_id'), now(),'NEGOTIATION_STATUS_TYPE_PARTLY_NEGOTIATED','Частично согласованный период отпуска.','Частично согласован',NULL);

INSERT INTO public.holiday_period_neg_status (hol_period_neg_status_id,created,negotiation_status_type,status_description,status_name,updated) VALUES (
nextval('seq_hol_period_neg_status_id'), now(),'NEGOTIATION_STATUS_TYPE_REJECTED','Период отпуска, отклонённый руководителями','Отклонён',NULL);

INSERT INTO public.holiday_period_neg_status (hol_period_neg_status_id,created,negotiation_status_type,status_description,status_name,updated) VALUES (
nextval('seq_hol_period_neg_status_id'), now(),'NEGOTIATION_STATUS_TYPE_OK','Период отпуска, полностью согласованный руководителями сотрудника.','Согласован',NULL);

commit;