CREATE TABLE public.department (
	dep_id int8 NOT NULL,
	created timestamp NULL,
	"name" varchar(255) NOT NULL,
	updated timestamp NULL,
	CONSTRAINT department_pkey PRIMARY KEY (dep_id)
)
WITH (
	OIDS=FALSE
) ;

CREATE TABLE public.holiday_period_neg_history (
	hol_period_neg_history_id int8 NOT NULL,
	"comment" varchar(255) NOT NULL,
	created timestamp NULL,
	new_status varchar(255) NULL,
	old_status varchar(255) NULL,
	updated timestamp NULL,
	hol_period_id int8 NULL,
	CONSTRAINT holiday_period_neg_history_pkey PRIMARY KEY (hol_period_neg_history_id),
	CONSTRAINT fkkw537hsp3qeyg7iaukkkemb45 FOREIGN KEY (hol_period_id) REFERENCES public.holiday_period(hol_period_id)
)
WITH (
	OIDS=FALSE
) ;