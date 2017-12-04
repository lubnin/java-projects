--
-- PostgreSQL database dump
--

-- Dumped from database version 10.0
-- Dumped by pg_dump version 10.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: authority; Type: TABLE; Schema: public; Owner: holidays
--

CREATE TABLE authority (
    authority_id bigint NOT NULL,
    authority character varying(255),
    created timestamp without time zone,
    updated timestamp without time zone
);


ALTER TABLE authority OWNER TO holidays;

--
-- Name: department; Type: TABLE; Schema: public; Owner: holidays
--

CREATE TABLE department (
    dep_id bigint NOT NULL,
    created timestamp without time zone,
    name character varying(255) NOT NULL,
    updated timestamp without time zone,
    code character varying(255)
);


ALTER TABLE department OWNER TO holidays;

--
-- Name: employee; Type: TABLE; Schema: public; Owner: holidays
--

CREATE TABLE employee (
    emp_id bigint NOT NULL,
    created timestamp without time zone,
    email character varying(255),
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    login_name character varying(255) NOT NULL,
    middle_name character varying(255),
    password character varying(255) NOT NULL,
    updated timestamp without time zone,
    department_id bigint,
    project_role_id bigint,
    team_id bigint,
    special_code character varying(255)
);


ALTER TABLE employee OWNER TO holidays;

--
-- Name: employee_authorities; Type: TABLE; Schema: public; Owner: holidays
--

CREATE TABLE employee_authorities (
    employee_emp_id bigint NOT NULL,
    authorities_authority_id bigint NOT NULL
);


ALTER TABLE employee_authorities OWNER TO holidays;

--
-- Name: holiday_period; Type: TABLE; Schema: public; Owner: holidays
--

CREATE TABLE holiday_period (
    hol_period_id bigint NOT NULL,
    created timestamp without time zone,
    date_start timestamp without time zone NOT NULL,
    negotiation_mask smallint,
    num_days bigint NOT NULL,
    updated timestamp without time zone,
    emp_id bigint,
    hp_negotiation_status_id bigint
);


ALTER TABLE holiday_period OWNER TO holidays;

--
-- Name: holiday_period_neg_history; Type: TABLE; Schema: public; Owner: holidays
--

CREATE TABLE holiday_period_neg_history (
    hol_period_neg_history_id bigint NOT NULL,
    comment character varying(255) NOT NULL,
    created timestamp without time zone,
    new_status character varying(255),
    old_status character varying(255),
    updated timestamp without time zone,
    hol_period_id bigint
);


ALTER TABLE holiday_period_neg_history OWNER TO holidays;

--
-- Name: holiday_period_neg_status; Type: TABLE; Schema: public; Owner: holidays
--

CREATE TABLE holiday_period_neg_status (
    hol_period_neg_status_id bigint NOT NULL,
    created timestamp without time zone,
    negotiation_status_type character varying(255) NOT NULL,
    status_description character varying(255),
    status_name character varying(255) NOT NULL,
    updated timestamp without time zone
);


ALTER TABLE holiday_period_neg_status OWNER TO holidays;

--
-- Name: managed_teams; Type: TABLE; Schema: public; Owner: holidays
--

CREATE TABLE managed_teams (
    manager_id bigint NOT NULL,
    team_id bigint NOT NULL
);


ALTER TABLE managed_teams OWNER TO holidays;

--
-- Name: project_role; Type: TABLE; Schema: public; Owner: holidays
--

CREATE TABLE project_role (
    project_role_id bigint NOT NULL,
    created timestamp without time zone,
    special_type character varying(255) NOT NULL,
    role_description character varying(255) NOT NULL,
    role_name character varying(255) NOT NULL,
    updated timestamp without time zone
);


ALTER TABLE project_role OWNER TO holidays;

--
-- Name: seq_authority_id; Type: SEQUENCE; Schema: public; Owner: holidays
--

CREATE SEQUENCE seq_authority_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_authority_id OWNER TO holidays;

--
-- Name: seq_dep_id; Type: SEQUENCE; Schema: public; Owner: holidays
--

CREATE SEQUENCE seq_dep_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_dep_id OWNER TO holidays;

--
-- Name: seq_emp_id; Type: SEQUENCE; Schema: public; Owner: holidays
--

CREATE SEQUENCE seq_emp_id
    START WITH 2
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_emp_id OWNER TO holidays;

--
-- Name: seq_hol_period_id; Type: SEQUENCE; Schema: public; Owner: holidays
--

CREATE SEQUENCE seq_hol_period_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_hol_period_id OWNER TO holidays;

--
-- Name: seq_hol_period_neg_history_id; Type: SEQUENCE; Schema: public; Owner: holidays
--

CREATE SEQUENCE seq_hol_period_neg_history_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_hol_period_neg_history_id OWNER TO holidays;

--
-- Name: seq_hol_period_neg_status_id; Type: SEQUENCE; Schema: public; Owner: holidays
--

CREATE SEQUENCE seq_hol_period_neg_status_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_hol_period_neg_status_id OWNER TO holidays;

--
-- Name: seq_proj_role_id; Type: SEQUENCE; Schema: public; Owner: holidays
--

CREATE SEQUENCE seq_proj_role_id
    START WITH 7
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_proj_role_id OWNER TO holidays;

--
-- Name: seq_team_id; Type: SEQUENCE; Schema: public; Owner: holidays
--

CREATE SEQUENCE seq_team_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_team_id OWNER TO holidays;

--
-- Name: team; Type: TABLE; Schema: public; Owner: holidays
--

CREATE TABLE team (
    team_id bigint NOT NULL,
    created timestamp without time zone,
    team_name character varying(255) NOT NULL,
    updated timestamp without time zone,
    project_manager_id bigint,
    team_lead_id bigint
);


ALTER TABLE team OWNER TO holidays;

--
-- Data for Name: authority; Type: TABLE DATA; Schema: public; Owner: holidays
--

COPY authority (authority_id, authority, created, updated) FROM stdin;
\.


--
-- Data for Name: department; Type: TABLE DATA; Schema: public; Owner: holidays
--

COPY department (dep_id, created, name, updated, code) FROM stdin;
1	2017-11-28 19:51:05.205012	Департамент внедрения и развития CRM решений	2017-11-29 03:11:22.044	DepartmentCRM
3	2017-11-28 19:51:05.274464	Отдел внедрения решений сегмента B2C	2017-11-29 03:11:38.161	DivisionB2C
2	2017-11-28 19:51:05.253518	Отдел развития CRM решений	2017-11-29 03:11:54.1	DivisionCRM
\.


--
-- Data for Name: employee; Type: TABLE DATA; Schema: public; Owner: holidays
--

COPY employee (emp_id, created, email, first_name, last_name, login_name, middle_name, password, updated, department_id, project_role_id, team_id, special_code) FROM stdin;
122	2017-11-30 00:10:26.372	dev1team1	dev1team1	dev1team1	dev1team1	dev1team1	$2a$10$W9aQRt/cSU/oEOacdqK.TOCrc0OnZccFa2Zt0ZRtL8/ccaHXTsEwO	\N	2	6	24	
123	2017-11-30 00:11:01.417	dev2team1	dev2team1	dev2team1	dev2team1	dev2team1	$2a$10$rFq2pQtldO3aRY0NqoRq2.AeDu/8PXKnKqVXbVLFax89BAZdDsIgK	\N	2	6	24	
124	2017-11-30 00:11:22.961	test1team1	test1team1	test1team1	test1team1	test1team1	$2a$10$9Glb9SuDGlGSEBzKRYWfyeZe4nq8sIUQvgVgetLc73wKXzrnFUxnO	\N	2	7	24	
125	2017-11-30 00:11:45.366	test2team1	test2team1	test2team1	test2team1	test2team1	$2a$10$UqgT0iO7/sygHdow8ULgle1grMKN1/nbobr3mLFdPE8Z9SMlksavG	\N	2	7	24	
126	2017-11-30 00:12:55.864	ba1team1	ba1team1	ba1team1	ba1team1	ba1team1	$2a$10$8FLKahuD5D3F637B1z4HuOE8ZKKdLeCUclgfYyCwIHiULUG9ehOPS	\N	3	9	24	
128	2017-11-30 00:16:32.474	devmgr	devmgr	devmgr	devmgr	devmgr	$2a$10$nD.B2qJSyRNzLXNyTdNJeOGd4.0iKgTTX.OqHW3Q9.1xLQlCrZGUO	2017-11-30 00:17:11.639	2	5	9	DevManager
127	2017-11-30 00:15:26.974	testmgr	testmgr	testmgr	testmgr	testmgr	$2a$10$F6cCbzsJjqNYm0TeR47dDeBd8Nga3k.ecayg4uaROvGNfzKLCgEWe	2017-11-30 00:17:26.91	2	4	9	TestManager
129	2017-11-30 00:17:54.824	pm1team1	pm1team1	pm1team1	pm1team1	pm1team1	$2a$10$rb5OEe6KshnpvJ4DJE5LqO9wk0n3VS0d4XO0gON.k65eL9noq6DY.	\N	1	3	9	
130	2017-11-30 00:26:31.192	dev1team2	dev1team2	dev1team2	dev1team2	dev1team2	$2a$10$vL1otCi5h2pENlmlgwpS4OJSGM.lcpsnMJiaRW6DTXKs7dFOe3wnq	\N	2	6	25	
131	2017-11-30 00:26:49.291	dev2team2	dev2team2	dev2team2	dev2team2	dev2team2	$2a$10$TcfmsplwMCRpAyT3V2XyVeX2r/MtNrVXh9MR6YLO9sOXC9dmroJVu	\N	2	6	25	
132	2017-11-30 00:27:09.264	test1team2	test1team2	test1team2	test1team2	test1team2	$2a$10$ceM9vZRJWl9RwXWaTm5r1OGqXBFInq9avGI/4K0QvLlTFTANZpET6	\N	2	7	25	
133	2017-11-30 00:27:30.151	test2team2	test2team2	test2team2	test2team2	test2team2	$2a$10$qSvwdJTABKheHFWNz1JG9up4h5IktG7Fphrb6nrkmYhq0pzRetL4C	\N	2	7	25	
134	2017-11-30 00:27:55.916	ba1team2	ba1team2	ba1team2	ba1team2	ba1team2	$2a$10$mMwcw7sNRC0lyI/cLi1CleduIqNyv9lp4GVpoBNSKg7h806hN0DAW	\N	3	9	25	
135	2017-11-30 00:29:45.823	b2cmgr	b2cmgr	b2cmgr	b2cmgr	b2cmgr	$2a$10$ihCfHTt/s0V5oqIMKn1C3uJP9VMLiaZh.MamEBvapnDRZF5p94SkC	2017-11-30 00:30:22.787	3	5	9	B2CManager
136	2017-11-30 00:32:02.42	bamgr	bamgr	bamgr	bamgr	bamgr	$2a$10$QPqrAcNXQgfELwUj5j0zHe/SDjHl6Ey8JqjXiMsHqGS5WdVRCHKUq	2017-11-30 00:32:25.379	3	4	9	BAManager
137	2017-11-30 00:33:52.959	sa1	sa1	sa1	sa1	sa1	$2a$10$tb.z0hWrYMytFzsCVxLIp.odfPrtsgtQ3jnOIWRnDtPbhkpVpstii	\N	3	10	26	
138	2017-11-30 00:34:30.187	sa2	sa2	sa2	sa2	sa2	$2a$10$CbwakJJGCqDRbfRii1QwAek0KxdUXNPruGCwnQ7rao4bL6SI39NDe	\N	3	10	26	
139	2017-11-30 00:40:25.926	tl1team1	tl1team1	tl1team1	tl1team1	tl1team1	$2a$10$kcNuq37j3buVCz4j27fq7.GekiwROg8BSI0vWfCgAD3fuv3k/p1KW	\N	2	2	7	
140	2017-11-30 00:41:12.173	tl1team2	tl1team2	tl1team2	tl1team2	tl1team2	$2a$10$K5lsgQC1lXDUgnP80/5EJ.1QtL0.nZZ1H8IyatgsjhZMYbWI8mxee	\N	2	2	7	
2	2017-11-28 19:54:00.097936	Elena.Koroleva2@RT.RU	Елена	Королёва	Elena.Koroleva2	Ильинична	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 13:04:49.161	1	5	11	
1	2017-11-28 19:54:00.052331	mabramkin@gmail.com	admin	admin	admin	admin	$2a$10$qB6GAVVVP4Agg.iyS2qsj.CNLjjiNIMxyT6SyI24FKiziq1gputNK	2017-11-30 15:57:20.186	\N	\N	\N	
141	2017-11-30 03:16:46.171	deptmgr	deptmgr	deptmgr	deptmgr	deptmgr	$2a$10$IAtL23glW7dqQ9SB0.prdOGJiSHn6K0ecfnkIqhzGEeZKpTQkqb5K	\N	1	5	11	
69	2017-11-28 19:54:02.618525	Evgeniy.Samokhin@RT.RU	Евгений	Самохин	Evgeniy.Samokhin	Викторович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:19:12.073	2	7	6	
87	2017-11-28 19:54:03.397477	Natalya.Usmanova@RT.RU	Наталья	Усманова	Natalya.Usmanova	Гафуровна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:28:43.287	2	11	10	
86	2017-11-28 19:54:03.362072	Aleksandr.Bekichev@RT.RU	Александр	Бекичев	Aleksandr.Bekichev	Вадимович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:28:54.164	2	7	2	
59	2017-11-28 19:54:02.227571	Dmitriy.Larin@RT.RU	Дмитрий	Ларин	Dmitriy.Larin	Калимуллович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:33:29.493	2	7	1	
142	2017-11-30 12:17:35.717	Aleksey.Mischenko@rt.ru	Алексей	Мищенко	Aleksey.Mischenko	Сергеевич	$2a$10$zrsugXa6lGkf0bdxEpi.qeBsFaG/vX8AJyOul6NgY6Ap/2R52zbWu	2017-11-30 17:49:24.215	3	9	6	
64	2017-11-28 19:54:02.362906	Nikolay.Odinokov@RT.RU	Николай	Одиноков	Nikolay.Odinokov	Николаевич	$2a$10$.60ochT5j/GQQ7FXuhPOJejjNt/dhX6GlqbphB7vROWfBzmNV2SPG	2017-12-01 11:46:00.697	2	6	6	
53	2017-11-28 19:54:01.92725	Andrey.Churin@RT.RU	Андрей	Чурин	Andrey.Churin	Васильевич	$2a$10$E.TRN3eOk6TDA91W0AYoguzwA2TY5len..gEgpWkMdeUcgh48F2Ki	2017-12-01 12:30:42.673	2	6	1	
13	2017-11-28 19:54:00.631156	Ekaterina.Belkova@RT.RU	Екатерина	Белькова	Ekaterina.Belkova	Сергеевна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:19:35.142	3	10	4	
14	2017-11-28 19:54:00.640132	Fedor.Bryukhovetskiy@RT.RU	Федор	Брюховецкий	Fedor.Bryukhovetskiy	Михайлович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:19:47.633	3	10	4	
18	2017-11-28 19:54:00.746788	Vladimir.Mazin@RT.RU	Владимир	Мазин	Vladimir.Mazin	Владимирович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-12-01 13:12:45.03	3	9	6	
6	2017-11-28 19:54:00.292211	Denis.Volynin@RT.RU	Денис	Волынин	Denis.Volynin	Николаевич	$2a$10$MMXgOlhp28jAh34cIjq75eIzX45Feh6WWDgNAPT0TQs8wvjGs3XwW	2017-11-30 17:48:41.972	1	3	9	
35	2017-11-28 19:54:01.297189	Nataliya.I.Lebedeva@RT.RU	Наталья	Лебедева	Nataliya.I.Lebedeva	Игоревна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:36:05.46	2	6	5	
117	2017-11-29 01:42:00.704	ivan.artemev@rt.ru	1	pp	pp1	1	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	1	3	9	\N
113	2017-11-29 01:28:09.574	ivan.artemev@rt.ru	1	tl	tl	1	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	2	4	9	TestManager
24	2017-11-28 19:54:00.963494	Darya.Rykhovskaya@RT.RU	Дарья	Рыховская	Darya.Rykhovskaya	Александровна	$2a$10$iObgG9x3GvdCnh8sDEr/yuWc6F0XwvYGu6X0ChuxGi0q6tQsApSlm	2017-12-01 13:26:49.764	3	9	3	
22	2017-11-28 19:54:00.85361	Vadim.Sinkevich@RT.RU	Вадим	Синкевич	Vadim.Sinkevich	Вадимович	$2a$10$3MmNABO0DYAOosBBVgGMNuuner4WOYjLUezFdNwiFoFo.ZHj4T9Ei	2017-12-01 14:25:21.005	3	9	3	
116	2017-11-29 01:38:56.633	ivan.artemev@rt.ru	1	al	al	1	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	2	4	9	BAManager
119	2017-11-29 02:57:09.99	ivan.artemev@rt.ru	1	tm	tm1	1	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	2	2	7	\N
118	2017-11-29 02:19:30.77	ivan.artemev@rt.ru	1	super	super	1	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	2	5	9	DevManager
120	2017-11-29 03:00:28.82	ivan.artemev@rt.ru	2	tm	tm2	2	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	2	2	7	\N
115	2017-11-29 01:37:33.084	ivan.artemev@rt.ru	1	cl	cl	1	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-29 02:57:27.413	2	5	9	B2CManager
16	2017-11-28 19:54:00.696275	Maksim.S.Kirillov@RT.RU	Максим	Кириллов	Maksim.S.Kirillov	Сергеевич	$2a$10$4MdYl5ka76jxiXcApZxCougGRWIt4gb62nlROBWMGbAIu/L9L9Nzm	2017-11-30 17:46:26.562	3	9	6	
15	2017-11-28 19:54:00.647072	Andrey.Vyazov@RT.RU	Андрей	Вязов	Andrey.Vyazov	Дмитриевич	$2a$10$mKX9KlxnGoxecsEhVZInl.xcoUf1WSJM8I3ZqRznpYjS99p1bk4qi	2017-12-01 17:43:28.37	3	9	8	
10	2017-11-28 19:54:00.550615	Vitaliy.Ruchkin@RT.RU	Виталий	Ручкин	Vitaliy.Ruchkin	Сергеевич	$2a$10$UFEWQegYQrTUc4vYs/CQhO0COOLA9/HfdMUe5HKHiyEJlRo0Lm8PC	2017-12-01 15:03:26.549	1	3	9	
20	2017-11-28 19:54:00.782795	Pavel.Romashov@RT.RU	Павел	Ромашов	Pavel.Romashov	Владимирович	$2a$10$hFxpK./qc4W8BxRji4jGfu/wz3rE58F3XE8FlgKm/YjyLpriNPXaS	2017-12-01 15:31:18.017	3	10	4	
17	2017-11-28 19:54:00.729664	Natalya.S.Kovaleva@RT.RU	Наталья	Ковалева	Natalya.S.Kovaleva	Сергеевна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:44:56.937	3	10	4	
11	2017-11-28 19:54:00.568299	Kseniya.Pankratova@RT.RU	Ксения	Панкратова	Kseniya.Pankratova	Игоревна	$2a$10$CcrXoiaDz9BAeIQKekR1m.nEqaWLDcHZRlbpiAJowFK5eqRRSD.BC	2017-12-01 10:02:09.276	1	3	9	\N
23	2017-11-28 19:54:00.941238	Dmitriy.Ushakov@RT.RU	Дмитрий	Ушаков	Dmitriy.Ushakov	Александрович	$2a$10$1dmvAMd/y0oaHZsojrH5/u3Dt6zMhxU7RZKd9H2oOPM8N9DtfV7/O	2017-12-01 14:41:55.788	3	9	5	
121	2017-11-29 03:03:12.974	ivan.artemev@rt.ru	2	pp	pp2	2	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 14:48:37.197	1	3	9	
12	2017-11-28 19:54:00.610924	Margarita.Popova@RT.RU	Маргарита	Андриановская	Margarita.Popova	Владимировна	$2a$10$EsOUYz8hS.yXR1/HxvUhi.Krwg1S/ml4/OYIng07dmhdFukOrVHGC	2017-11-30 16:46:50.225	3	4	9	BAManager
8	2017-11-28 19:54:00.397615	Yuriy.Lukashov@RT.RU	Юрий	Лукашов	Yuriy.Lukashov	Александрович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:48:12.516	1	3	9	
7	2017-11-28 19:54:00.301166	Vadim.Kashin@RT.RU	Вадим	Кашин	Vadim.Kashin	Васильевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:48:21.29	1	3	9	
3	2017-11-28 19:54:00.152237	Maksim.Abramkin@RT.RU	Максим	Абрамкин	Maksim.Abramkin	Андреевич	$2a$10$CbccY5DHlyRCSm10I8yPBu3AVcaFugWgRJJby6cvATUeEzD49dseW	2017-12-01 10:31:53.779	2	5	9	DevManager
5	2017-11-28 19:54:00.282648	Regina.Davytova@RT.RU	Регина	Давытова	Regina.Davytova	Алмазовна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:48:40.762	1	3	9	
21	2017-11-28 19:54:00.837083	Anastasiya.Roor@RT.RU	Анастасия	Роор	Anastasiya.Roor	Викторовна	$2a$10$eV1qGwD31MQs4kr9gSlymu9kAf9Z3cf1N68lNg0vPyJC0BiH2RnoC	2017-12-01 12:34:53.413	3	9	3	
4	2017-11-28 19:54:00.215123	Farkhad.G.Tokhtamov@RT.RU	Фархад	Тохтамов	Farkhad.G.Tokhtamov	Гапарович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 13:05:12.863	3	5	9	B2CManager
19	2017-11-28 19:54:00.764075	Kristina.K.Potapova@RT.RU	Кристина	Потапова	Kristina.K.Potapova	Хикматовна	$2a$10$hoQPfpaZtQ1SM4CnKCpPLu9Qnrxr3YInIPTuqGukI57eqMduTU1fu	2017-11-30 17:47:02.29	3	9	8	
9	2017-11-28 19:54:00.454912	Ivan.Artemev@RT.RU	Иван	Артемьев	Ivan.Artemev	Сергеевич	$2a$10$eThhhTy3wP2EweHoJbEdZOU14QzkM226YlYqKpWxgLzInS9YK2XsK	2017-12-01 10:32:29.774	2	4	9	TestManager
25	2017-11-28 19:54:00.985411	Aleksandra.Yashukova@RT.RU	Александра	Яшукова	Aleksandra.Yashukova	Дмитриевна	$2a$10$ttiq6W1MArizql9F/xuL1ubcP6N5tg1Xa7eP5amNXtDGEYiFvFrbK	2017-12-01 14:53:27.534	3	9	8	
57	2017-11-28 19:54:02.072491	Andrey.Kuzmin@RT.RU	Андрей	Кузьмин	Andrey.Kuzmin	Юрьевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:33:43.04	2	7	1	
39	2017-11-28 19:54:01.401052	Anastasiya.Konopleva@RT.RU	Анастасия	Коноплева	Anastasiya.Konopleva	Сергеевна	$2a$10$DFcSOSARqHJvW6fuQpCiVehPjgECAsp8KalX/bOqZqUWL.8DECztm	2017-12-01 14:21:29.609	2	7	5	
55	2017-11-28 19:54:01.981138	Tatyana.Garyaeva@RT.RU	Татьяна	Гаряева	Tatyana.Garyaeva	Ивановна	$2a$10$/hUWi2uOildEQEaygJshb.hNkG3qS0vVjhkffGAhiztSBIQriNTsO	2017-12-01 13:59:21.083	2	8	1	
54	2017-11-28 19:54:01.961407	Olga.Krayushkina@RT.RU	Ольга	Краюшкина	Olga.Krayushkina	Дмитриевна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:34:05.636	2	8	1	
52	2017-11-28 19:54:01.872385	Elena.S.Artemeva@RT.RU	Елена	Артемьева	Elena.S.Artemeva	Сергеевна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:34:23.675	2	7	1	
58	2017-11-28 19:54:02.129721	Svetlana.M.Dmitrieva@RT.RU	Светлана	Дмитриева	Svetlana.M.Dmitrieva	Михайловна	$2a$10$8mm7k0xi/if7HUEA/EVNhuVu8jiXHDfdGyJq1.BhKTfZH05j9zaai	2017-12-01 14:35:17.101	2	8	1	
50	2017-11-28 19:54:01.844066	Aleksandr.Pavlov@RT.RU	Александр	Павлов	Aleksandr.Pavlov	Дмитриевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:34:39.789	2	6	3	
49	2017-11-28 19:54:01.821689	Vasiliy.Efremov@RT.RU	Василий	Ефремов	Vasiliy.Efremov	Михайлович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:34:46.545	2	6	3	
48	2017-11-28 19:54:01.771544	Stanislav.Kuznets@RT.RU	Станислав	Кузнец	Stanislav.Kuznets	Юрьевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:34:53.036	2	6	3	
47	2017-11-28 19:54:01.750276	Andrey.Fomichev@RT.RU	Андрей	Фомичев	Andrey.Fomichev	Игоревич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:34:58.741	2	6	3	
46	2017-11-28 19:54:01.742968	Nikolay.Lobachev@RT.RU	Николай	Лобачев	Nikolay.Lobachev	Александрович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:35:04.884	2	6	3	
45	2017-11-28 19:54:01.686804	Elisey.Novikov@RT.RU	Елисей	Новиков	Elisey.Novikov	Игоревич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:35:09.473	2	6	3	
44	2017-11-28 19:54:01.595992	Ivan.Samarskiy@RT.RU	Иван	Самарский	Ivan.Samarskiy	Николаевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:35:15.201	2	7	3	
43	2017-11-28 19:54:01.577537	Petr.A.Kononov@RT.RU	Петр	Кононов	Petr.A.Kononov	Александрович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:35:21.294	2	7	3	
42	2017-11-28 19:54:01.505129	Kirill.Velichko@RT.RU	Кирилл	Величко	Kirill.Velichko	Сергеевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:35:26.612	2	7	3	
40	2017-11-28 19:54:01.468496	Ivan.Lubnin@RT.RU	Иван	Лубнин	Ivan.Lubnin	Игоревич	$2a$10$sxbDHGtopTL41dAaFK1zHelhT1xLk5axJMkLRM6dw675qaJlziXJm	2017-12-01 02:16:08.571	2	7	5	
36	2017-11-28 19:54:01.352628	Robert.Mirzakhanyan@RT.RU	Роберт	Мирзаханян	Robert.Mirzakhanyan	Масисович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:35:39.15	2	6	5	
32	2017-11-28 19:54:01.174414	Aleksey.Starikov@RT.RU	Алексей	Стариков	Aleksey.Starikov	Евгеньевич	$2a$10$998K8nvm2kmTYLYV0mrtYuAkK4BxNsQqN1UGeJqFaxr6qRilvny7.	2017-12-01 11:40:59.472	2	6	5	
37	2017-11-28 19:54:01.358888	Aleksey.Elnikov@RT.RU	Алексей	Ельников	Aleksey.Elnikov	Сергеевич	$2a$10$XKUqWs/EVHvYzGnBWZ0eJuEum7v5nkxIR03.erqdTJo5EMvyd5aC.	2017-12-01 09:26:41.331	2	6	5	
51	2017-11-28 19:54:01.853657	Igor.Komarov@RT.RU	Игорь	Комаров	Igor.Komarov	Вячеславович	$2a$10$5fPxCgjQTxHJuayop3c8buy.WrwyZmAdYQPqvQbi//GOhFbwiscNy	2017-12-01 14:32:29.409	2	2	7	
41	2017-11-28 19:54:01.486969	Anastasiya.Egorova@RT.RU	Анастасия	Егорова	Anastasiya.Egorova	Юрьевна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:36:00.026	2	2	7	
34	2017-11-28 19:54:01.262108	Elizaveta.Sukhanova@RT.RU	Елизавета	Суханова	Elizaveta.Sukhanova	Александровна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:36:10.879	2	6	5	
33	2017-11-28 19:54:01.191581	Sergey.I.Shevtsov@RT.RU	Сергей	Шевцов	Sergey.I.Shevtsov	Иванович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:36:16.293	2	6	5	
56	2017-11-28 19:54:02.053989	Lyudmila.Dmitrieva@RT.RU	Людмила	Дмитриева	Lyudmila.Dmitrieva	Владимировна	$2a$10$iXL55NB.n0TPYwAaAHhwYOhqjcZgz4MVtLHNE/BfYbty9RSxPfI02	2017-12-01 12:12:12.873	2	8	1	
30	2017-11-28 19:54:01.118826	Stanislav.Nastaschuk@RT.RU	Станислав	Настащук	Stanislav.Nastaschuk	Дмитриевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:36:30.631	3	10	4	
38	2017-11-28 19:54:01.389788	Polina.Churbanova@RT.RU	Полина	Чурбанова	Polina.Churbanova	Сергеевна	$2a$10$muOLgc6e8rWgAfIwQNlotuhOq8MAH.Hq7fcLfEBFVzz4N5Pp0.hz.	2017-11-30 18:10:29.377	2	7	5	
28	2017-11-28 19:54:01.047674	Arusyak.Ambaryan@RT.RU	Арусяк	Амбарян	Arusyak.Ambaryan	Сагателовна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:43:30.811	3	10	4	
26	2017-11-28 19:54:01.032005	Evgeniy.Monakov@RT.RU	Евгений	Монаков	Evgeniy.Monakov	Александрович	$2a$10$GWzT07DlCBct89rbEQzTDuLI1Qrd7UR3ZbNbRw2a9EcLuEjun2/7W	2017-12-01 09:54:28.714	3	9	6	
27	2017-11-28 19:54:01.04031	Anastasiya.Y.Karpova@RT.RU	Анастасия	Карпова	Anastasiya.Y.Karpova	Юрьевна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:51:48.66	3	10	4	
31	2017-11-28 19:54:01.153728	Maksim.Koshkaryov@RT.RU	Максим	Кошкарёв	Maksim.Koshkaryov	Игоревич	$2a$10$aAylrA89FspVAuIGP8Rpz.BlhUfKPnaLxyV51S7GdJlWZmQgU9f.a	2017-11-30 17:42:26.885	2	2	7	
88	2017-11-28 23:40:32.447	ivan.artemev@rt.ru	1	тестер	test1	1	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	2	1	21	\N
103	2017-11-29 00:57:33.649	ivan.artemev@rt.ru	2	ba	ba2	2	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	3	1	21	\N
104	2017-11-29 01:00:52.228	ivan.artemev@rt.ru	2	ba	ba2	2	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	3	1	21	\N
90	2017-11-28 23:44:50.901	ivan.artemev@rt.ru	2	тестер	test2	2	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	2	1	21	\N
91	2017-11-28 23:45:40.233	ivan.artemev@rt.ru	11	test	test11	11	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	2	1	22	\N
93	2017-11-28 23:48:13.196	ivan.artemev@rt.ru	1	dev	dev1	1	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	2	1	21	\N
105	2017-11-29 01:04:18.264	ivan.artemev@rt.ru	3	ba	ba3	3	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	3	1	21	\N
94	2017-11-28 23:50:14.488	ivan.artemev@rt.ru	1	test22for test1	test22	1	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	\N	\N	\N	\N
95	2017-11-28 23:53:49.482	ivan.artemev@rt.ru	22	test	test22	22	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	2	1	22	\N
96	2017-11-28 23:54:45.775	ivan.artemev@rt.ru	2	dev	dev2	2	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	2	1	21	\N
97	2017-11-28 23:55:24.263	ivan.artemev@rt.ru	11	dev	dev11	11	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	2	1	22	\N
84	2017-11-28 19:54:03.253004	Evgeniya.Volkova@RT.RU	Евгения	Волкова	Evgeniya.Volkova	Алексеевна	$2a$10$tGtAkWuBdMqZtvIxNqi3s.KPa362yz.0iWaQGCwd36vsCkRnlBRJO	2017-12-01 09:30:26.111	2	12	2	
76	2017-11-28 19:54:02.924957	Viktor.Romanov@RT.RU	Виктор	Романов	Viktor.Romanov	Викторович	$2a$10$txI/NUeb3G7AiShwz28WG.d3up.pv5CJqahFHyRJwenageCZPSn6m	2017-12-01 12:07:12.124	2	6	8	
82	2017-11-28 19:54:03.217139	Pavel.Kulikovskiy@RT.RU	Павел	Куликовский	Pavel.Kulikovskiy	Николаевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:30:16.391	2	6	8	
81	2017-11-28 19:54:03.173585	Shamil.Rakipov@RT.RU	Шамиль	Ракипов	Shamil.Rakipov	Фатыхович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:30:23.537	2	7	8	
80	2017-11-28 19:54:03.099889	Dmitriy.Vakhromeev@RT.RU	Дмитрий	Вахромеев	Dmitriy.Vakhromeev	Игоревич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:30:29.619	2	7	8	
79	2017-11-28 19:54:03.071952	Nikolay.Aleksandrov@RT.RU	Николай	Александров	Nikolay.Aleksandrov	Владимирович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:30:37.583	2	7	8	
67	2017-11-28 19:54:02.477267	A.E.Trofimov@RT.RU	Андрей	Трофимов	A.E.Trofimov	Евгеньевич	$2a$10$Wzj7dYIEGRyoU4g9tVIuIesfVU9fvKU.UHsWnJCswb6wqw9gdEGHe	2017-12-01 09:25:35.446	2	6	6	
77	2017-11-28 19:54:02.998714	Aleksandr.Kolokolov@RT.RU	Александр	Колоколов	Aleksandr.Kolokolov	Андреевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:30:49.057	2	6	8	
71	2017-11-28 19:54:02.797377	Igor.Minenko@RT.RU	Игорь	Миненко	Igor.Minenko	Владимирович	$2a$10$CwaSbopyCfaX62ih2FOrMuO6NeFGVqx2RLQbAmthjhoX5kdvQQ3mu	2017-12-01 12:14:12.464	2	7	6	
75	2017-11-28 19:54:02.889685	Ayvar.Nadeev@RT.RU	Айвар	Надеев	Ayvar.Nadeev	Адельевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:31:21.096	2	6	8	
78	2017-11-28 19:54:03.016995	Vladimir.Buravtsov@RT.RU	Владимир	Буравцов	Vladimir.Buravtsov	Сергеевич	$2a$10$Y2wEVIgqvdvwgp/OJ0r.Z.aVVGHkVs4cYFosfOYlElZJCxWlRHHQu	2017-11-30 20:06:15.723	2	7	8	
73	2017-11-28 19:54:02.815218	Dmitriy.Kosenko@RT.RU	Дмитрий	Косенко	Dmitriy.Kosenko	Владимирович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:31:32.007	2	6	8	
72	2017-11-28 19:54:02.805279	Vyacheslav.Kuleshov@RT.RU	Вячеслав	Кулешов	Vyacheslav.Kuleshov	Витальевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:31:38.432	2	2	7	
62	2017-11-28 19:54:02.323888	Dmitriy.Orlov@RT.RU	Дмитрий	Орлов	Dmitriy.Orlov	Евгеньевич	$2a$10$pO4H8ft3nh8f2vsQM.gKkeZqKgtsUjP62TilMPgIUpBxuafUd2MjS	2017-12-01 14:57:22.906	2	2	7	
83	2017-11-28 19:54:03.234993	Sergey.Vidavskiy@RT.RU	Сергей	Видавский	Sergey.Vidavskiy	Алексеевич	$2a$10$wx5nJXP5AugrlOivu8w7h.XCFHnS62Uqre409wJpYvC3/n.3q4k3O	2017-12-01 10:12:09.614	2	2	7	
65	2017-11-28 19:54:02.417909	Igor.N.Seleznev@RT.RU	Игорь	Селезнев	Igor.N.Seleznev	Николаевич	$2a$10$vHrhHVJKOFmYYvs1SUkzoOkpANqLYdyLFy2Orb6q8KTbt9f3Ty6oi	2017-11-30 19:16:06.75	2	6	6	
70	2017-11-28 19:54:02.75251	Ilya.Kryatov@RT.RU	Илья	Крятов	Ilya.Kryatov	Юрьевич	$2a$10$8Mta.RuCu1dXao4G0B8Utui5ESOaFbULh1.CrRvbyiP8fEDDEj.be	2017-12-01 10:01:41.407	2	7	6	
66	2017-11-28 19:54:02.458696	Vadim.Kurbatov@RT.RU	Вадим	Курбатов	Vadim.Kurbatov	Вячеславович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:32:13.026	2	6	6	
63	2017-11-28 19:54:02.345711	Sofya.Goryunova@RT.RU	Софья	Горюнова	Sofya.Goryunova	Александровна	$2a$10$86Hzmw318Yfr0dEktcIn5O5rUeq4uLl92qhmShrCmWoNxQDq2CAD.	2017-11-30 19:33:58.503	2	6	6	
74	2017-11-28 19:54:02.834175	Ekaterina.Zvereva@RT.RU	Екатерина	Зверева	Ekaterina.Zvereva	Владимировна	$2a$10$gMLAds9HZ2OBCnkrgruAZeUeY8733jte7btb6PZYFRRYGWyr9jF4q	2017-11-30 19:38:14.307	2	6	8	
85	2017-11-28 19:54:03.325919	Nikolay.Naybauer@RT.RU	Николай	Найбауер	Nikolay.Naybauer	Юрьевич	$2a$10$vlsGRkRLbsRoxR/uv8ok6e3EQ3KBiaxOzkNpeHEWQZTXK4rJ6cgh6	2017-12-01 15:03:26.102	2	12	2	
61	2017-11-28 19:54:02.263595	Nikolaj.Epifanov@RT.RU	Николай	Епифанов	Nikolaj.Epifanov	Михайлович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:33:16.78	2	6	1	
60	2017-11-28 19:54:02.254298	Elvira.Utepeshova@RT.RU	Эльвира	Утепешова	Elvira.Utepeshova	Исатаевна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:33:22.868	2	8	1	
68	2017-11-28 19:54:02.546336	Aleksandr.Fomenko@RT.RU	Александр	Фоменко	Aleksandr.Fomenko	Андреевич	$2a$10$obI2FRJHfJlTg3APqlPoy.EKpR.4WwHvC3EwgEYPkinmX/I9xwbQ2	2017-11-30 18:03:55.987	2	7	6	
100	2017-11-28 23:58:06.669	ivan.artemev@rt.ru	3	dev	dev3	3	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	2	1	21	\N
101	2017-11-29 00:29:57.465	ivan.artemev@rt.ru	123	123	123	123	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	2	1	21	\N
102	2017-11-29 00:56:09.875	ivan.artemev@rt.ru	1	ba	ba1	1	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	3	1	21	\N
106	2017-11-29 01:07:23.17	ivan.artemev@rt.ru	11	ba	ba11	11	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	2	1	22	\N
107	2017-11-29 01:10:33.323	ivan.artemev@rt.ru	22	ba	ba22	22	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	3	1	21	\N
108	2017-11-29 01:20:00.899	ivan.artemev@rt.ru	1	ca	ca1	1	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	3	1	23	\N
109	2017-11-29 01:20:30.496	ivan.artemev@rt.ru	2	ca	ca2	2	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	\N	3	1	23	\N
143	2017-11-30 12:58:50.703	Irina.Andropova@RT.RU	Ирина	Андропова	Irina.Andropova	Вячеславовна	$2a$10$p/dDuJ.gk5dvlwrham4LM.DM6LvOiqSXf4wPxl2VGMdFiH4WxcUuS	\N	3	9	5	
145	2017-11-30 13:12:44.574	Evgeniy.Kaminskiy@RT.RU	Евгений	Каминский	Evgeniy.Kaminskiy	Дмитриевич	$2a$10$swD2Gg394Y7n1BtcinlXK.4.UHVRU6Xo43979lPZMmhyYx5cJux.m	\N	1	7	5	
146	2017-11-30 13:14:08.434	Roman.Volosko@RT.RU	Роман	Волоско	Roman.Volosko	Геннадьевич	$2a$10$mqjJMiXQWKZZ.TBaw/t6xubkAdp3L6KIkZpw0xoVEONu2SyojlDBy	\N	2	7	3	
144	2017-11-30 13:12:00.015	Yuriy.Naumochkin@RT.RU	Юрий	Наумочкин	Yuriy.Naumochkin	Алексеевич	$2a$10$TzMgDUhylcG6XMXWiIbg1ezKDeRB9YmFSaEXsFDv.pa2zWdxkG.ti	2017-12-01 16:09:00.002	1	7	5	
\.


--
-- Data for Name: employee_authorities; Type: TABLE DATA; Schema: public; Owner: holidays
--

COPY employee_authorities (employee_emp_id, authorities_authority_id) FROM stdin;
\.


--
-- Data for Name: holiday_period; Type: TABLE DATA; Schema: public; Owner: holidays
--

COPY holiday_period (hol_period_id, created, date_start, negotiation_mask, num_days, updated, emp_id, hp_negotiation_status_id) FROM stdin;
253	2017-12-01 15:38:24.195	2018-05-14 00:00:00	\N	14	\N	20	1
254	2017-12-01 15:39:58.193	2018-06-13 00:00:00	\N	3	\N	20	1
256	2017-12-01 15:41:50.874	2018-03-05 00:00:00	\N	3	\N	20	1
257	2017-12-01 15:43:50	2018-08-30 00:00:00	\N	14	\N	20	1
46	2017-11-30 17:48:34.542	2018-04-23 00:00:00	\N	8	2017-11-30 18:00:17.642	19	2
47	2017-11-30 17:49:48.115	2018-07-18 00:00:00	\N	14	2017-11-30 18:00:17.649	19	2
50	2017-11-30 17:53:15.908	2018-12-24 00:00:00	\N	6	2017-11-30 18:00:17.657	19	2
56	2017-11-30 18:00:07.368	2018-10-25 00:00:00	\N	7	2017-12-01 17:14:22.355	19	2
58	2017-11-30 18:08:55.572	2018-06-18 00:00:00	\N	14	2017-11-30 18:09:37.866	6	2
59	2017-11-30 18:29:29.237	2018-05-03 00:00:00	\N	2	2017-11-30 18:29:50.53	6	2
95	2017-12-01 08:42:33.872	2018-06-13 00:00:00	\N	5	2017-12-01 08:43:48.4	6	2
62	2017-11-30 19:18:55.557	2018-02-26 00:00:00	\N	14	2017-11-30 19:20:57.197	65	2
63	2017-11-30 19:19:15.125	2018-06-04 00:00:00	\N	14	2017-11-30 19:20:57.204	65	2
64	2017-11-30 19:19:32.708	2018-12-27 00:00:00	\N	2	2017-11-30 19:20:57.211	65	2
60	2017-11-30 19:16:16.888	2018-10-22 00:00:00	\N	12	2017-11-30 19:24:11.219	38	2
65	2017-11-30 19:20:48.977	2018-03-12 00:00:00	\N	14	2017-11-30 19:24:11.227	38	2
258	2017-12-01 16:04:54.833	2018-05-03 00:00:00	\N	2	2017-12-01 16:05:54.351	145	2
66	2017-11-30 19:23:53.504	2018-07-27 00:00:00	\N	8	2017-11-30 19:24:11.236	38	2
260	2017-12-01 16:05:22.992	2018-12-24 00:00:00	\N	6	2017-12-01 16:05:54.482	145	2
61	2017-11-30 19:18:26.197	2018-01-09 00:00:00	3	14	2017-12-01 16:05:54.482	65	2
51	2017-11-30 17:54:48.775	2018-09-03 00:00:00	\N	5	2017-12-01 16:05:54.482	142	2
113	2017-12-01 09:54:07.438	2018-04-02 00:00:00	\N	14	2017-12-01 09:55:01.486	31	2
114	2017-12-01 09:54:44.205	2018-07-02 00:00:00	\N	14	2017-12-01 09:55:01.508	31	2
259	2017-12-01 16:05:05.766	2018-09-10 00:00:00	\N	20	2017-12-01 16:05:54.413	145	2
71	2017-11-30 19:32:13.175	2018-12-13 00:00:00	\N	2	2017-11-30 19:34:28.562	34	2
72	2017-11-30 19:32:58.615	2018-03-01 00:00:00	\N	2	2017-11-30 19:34:28.57	34	2
146	2017-12-01 11:45:53.791	2018-03-05 00:00:00	5	3	2017-12-01 16:56:28.83	24	2
96	2017-12-01 08:43:06.401	2018-07-02 00:00:00	\N	2	2017-12-01 08:43:48.415	6	2
74	2017-11-30 19:33:57.822	2018-08-27 00:00:00	\N	5	2017-11-30 19:34:28.585	34	2
255	2017-12-01 15:40:37.963	2018-07-23 00:00:00	\N	14	2017-12-01 17:44:13.314	80	2
75	2017-11-30 19:38:49.436	2018-07-02 00:00:00	\N	14	2017-11-30 19:46:24.293	74	2
76	2017-11-30 19:39:12.812	2018-09-03 00:00:00	\N	7	2017-11-30 19:46:24.304	74	2
78	2017-11-30 19:44:28.877	2018-12-28 00:00:00	\N	4	2017-11-30 19:46:24.314	74	2
79	2017-11-30 19:45:33.064	2018-10-03 00:00:00	\N	3	2017-11-30 19:46:24.324	74	2
116	2017-12-01 09:58:13.445	2018-11-01 00:00:00	\N	2	2017-12-01 16:05:54.482	16	2
278	2017-12-01 17:43:36.486	2018-09-10 00:00:00	\N	14	2017-12-01 17:44:13.381	80	2
39	2017-11-30 16:35:38.141	2018-02-09 00:00:00	\N	10	2017-11-30 16:44:00.66	3	2
81	2017-11-30 20:11:01.551	2018-06-13 00:00:00	\N	3	2017-11-30 20:52:10.844	78	2
82	2017-11-30 20:42:40.014	2018-07-09 00:00:00	\N	19	2017-11-30 20:52:10.861	78	2
84	2017-11-30 20:47:06.525	2018-07-30 00:00:00	\N	5	2017-11-30 20:52:10.885	78	2
85	2017-11-30 20:48:21.038	2018-12-29 00:00:00	\N	1	2017-11-30 20:52:10.909	78	2
86	2017-12-01 02:10:47.807	2018-02-26 00:00:00	\N	5	2017-12-01 02:13:58.394	40	2
87	2017-12-01 02:11:30.191	2018-04-23 00:00:00	\N	6	2017-12-01 02:13:58.412	40	2
88	2017-12-01 02:11:53.67	2018-06-18 00:00:00	\N	5	2017-12-01 02:13:58.426	40	2
89	2017-12-01 02:12:12.567	2018-08-06 00:00:00	\N	22	2017-12-01 02:13:58.442	40	2
90	2017-12-01 02:12:55.571	2018-11-06 00:00:00	\N	4	2017-12-01 02:13:58.458	40	2
91	2017-12-01 08:30:00.027	2018-11-06 00:00:00	\N	4	2017-12-01 08:43:48.335	6	2
92	2017-12-01 08:32:21.812	2018-12-24 00:00:00	\N	6	2017-12-01 08:43:48.354	6	2
93	2017-12-01 08:37:02.777	2018-09-10 00:00:00	\N	12	2017-12-01 08:43:48.369	6	2
94	2017-12-01 08:38:44.781	2018-07-06 00:00:00	\N	1	2017-12-01 08:43:48.386	6	2
68	2017-11-30 19:28:05.288	2018-07-16 00:00:00	1	14	2017-12-01 09:17:43.172	34	2
69	2017-11-30 19:31:20.447	2018-10-01 00:00:00	1	7	2017-12-01 09:18:20.899	34	2
73	2017-11-30 19:33:23.358	2018-05-28 00:00:00	1	4	2017-12-01 09:19:13.378	34	2
97	2017-12-01 09:22:06.281	2018-05-07 00:00:00	\N	4	2017-12-01 09:23:02.597	82	2
98	2017-12-01 09:22:16.976	2018-06-13 00:00:00	\N	3	2017-12-01 09:23:02.613	82	2
99	2017-12-01 09:22:33.902	2018-07-09 00:00:00	\N	14	2017-12-01 09:23:02.631	82	2
100	2017-12-01 09:22:42.968	2018-10-08 00:00:00	\N	7	2017-12-01 09:23:02.645	82	2
101	2017-12-01 09:27:13.14	2018-07-02 00:00:00	\N	14	2017-12-01 09:28:16.554	37	2
102	2017-12-01 09:27:28.276	2018-08-20 00:00:00	\N	14	2017-12-01 09:28:16.573	37	2
103	2017-12-01 09:30:59.448	2018-02-20 00:00:00	\N	3	2017-12-01 09:32:32.758	84	2
104	2017-12-01 09:31:08.468	2018-03-05 00:00:00	\N	3	2017-12-01 09:32:32.798	84	2
106	2017-12-01 09:31:38.112	2018-08-06 00:00:00	\N	14	2017-12-01 09:32:32.837	84	2
107	2017-12-01 09:31:50.961	2018-09-10 00:00:00	\N	4	2017-12-01 09:32:32.854	84	2
117	2017-12-01 10:06:59.078	2018-02-26 00:00:00	\N	5	2017-12-01 10:11:06.202	12	2
118	2017-12-01 10:07:19.465	2018-04-23 00:00:00	\N	6	2017-12-01 10:11:06.227	12	2
119	2017-12-01 10:08:21.146	2018-07-18 00:00:00	\N	3	2017-12-01 10:11:06.254	12	2
120	2017-12-01 10:08:38.393	2018-10-08 00:00:00	\N	14	2017-12-01 10:11:06.283	12	2
121	2017-12-01 10:15:10.553	2018-04-19 00:00:00	\N	10	2017-12-01 10:16:59.685	11	2
122	2017-12-01 10:15:29.735	2018-06-04 00:00:00	\N	5	2017-12-01 10:16:59.709	11	2
123	2017-12-01 10:15:44.641	2018-08-18 00:00:00	\N	14	2017-12-01 10:16:59.733	11	2
124	2017-12-01 10:38:14.222	2018-05-03 00:00:00	\N	2	2017-12-01 10:41:44.266	83	2
125	2017-12-01 10:38:28.001	2018-05-07 00:00:00	\N	2	2017-12-01 10:41:44.293	83	2
126	2017-12-01 10:38:42.44	2018-06-13 00:00:00	\N	3	2017-12-01 10:41:44.32	83	2
127	2017-12-01 10:38:55.999	2018-07-23 00:00:00	\N	14	2017-12-01 10:41:44.345	83	2
128	2017-12-01 10:39:38.854	2018-11-06 00:00:00	\N	4	2017-12-01 10:41:44.368	83	2
129	2017-12-01 10:39:54.728	2018-12-19 00:00:00	\N	3	2017-12-01 10:41:44.394	83	2
134	2017-12-01 10:54:15.297	2018-04-21 00:00:00	\N	5	\N	35	1
135	2017-12-01 10:54:32.105	2018-05-14 00:00:00	\N	12	\N	35	1
136	2017-12-01 10:55:13.112	2018-09-26 00:00:00	\N	15	\N	35	1
137	2017-12-01 11:00:01.755	2018-10-15 00:00:00	\N	12	2017-12-01 11:10:30.985	67	2
139	2017-12-01 11:01:02.469	2018-03-05 00:00:00	\N	3	2017-12-01 11:10:31.018	67	2
140	2017-12-01 11:06:55.63	2018-05-28 00:00:00	\N	14	2017-12-01 11:10:31.048	67	2
141	2017-12-01 11:08:02.982	2018-04-23 00:00:00	\N	6	2017-12-01 11:10:31.078	67	2
143	2017-12-01 11:42:40.586	2018-08-20 00:00:00	\N	14	\N	33	1
147	2017-12-01 11:46:18.621	2018-05-03 00:00:00	\N	2	2017-12-01 11:49:58.577	64	2
149	2017-12-01 11:46:36.596	2018-06-18 00:00:00	\N	14	2017-12-01 11:49:58.612	64	2
145	2017-12-01 11:45:45.318	2018-02-19 00:00:00	\N	4	2017-12-01 11:51:23.594	15	2
130	2017-12-01 10:46:39.724	2018-08-13 00:00:00	\N	14	2017-12-01 13:45:30.769	70	2
194	2017-12-01 14:23:50.419	2018-09-10 00:00:00	\N	12	2017-12-01 16:05:54.482	18	2
49	2017-11-30 17:52:46.679	2018-06-13 00:00:00	\N	3	2017-12-01 16:05:54.482	142	2
151	2017-12-01 11:46:56.446	2018-11-06 00:00:00	\N	5	2017-12-01 11:49:58.644	64	2
154	2017-12-01 11:47:44.462	2018-11-12 00:00:00	\N	7	2017-12-01 11:49:58.679	64	2
148	2017-12-01 11:46:23.015	2018-05-03 00:00:00	\N	6	2017-12-01 11:51:23.625	15	2
150	2017-12-01 11:46:43.855	2018-07-30 00:00:00	\N	14	2017-12-01 11:51:23.652	15	2
152	2017-12-01 11:47:04.478	2018-11-06 00:00:00	\N	4	2017-12-01 11:51:23.678	15	2
158	2017-12-01 11:50:34.988	2018-02-01 00:00:00	\N	18	2017-12-01 11:51:23.705	15	2
54	2017-11-30 17:57:08.779	2018-11-19 00:00:00	\N	5	2017-12-01 16:05:54.482	142	2
161	2017-12-01 11:52:22.172	2018-06-13 00:00:00	\N	14	\N	33	1
159	2017-12-01 11:51:06.728	2018-06-13 00:00:00	5	3	2017-12-01 16:57:52.682	24	2
153	2017-12-01 11:47:05.046	2018-05-03 00:00:00	5	2	2017-12-01 16:58:16.727	24	2
235	2017-12-01 15:05:39.029	2018-08-06 00:00:00	1	14	2017-12-01 18:04:20.198	39	3
57	2017-11-30 18:01:50.691	2018-11-26 00:00:00	\N	5	2017-12-01 16:05:54.482	142	2
115	2017-12-01 09:56:49.225	2018-08-01 00:00:00	\N	10	2017-12-01 16:05:54.482	16	2
224	2017-12-01 14:48:39.602	2018-11-12 00:00:00	5	14	2017-12-01 16:06:06.977	23	2
229	2017-12-01 14:57:32.104	2018-06-13 00:00:00	\N	3	2017-12-01 15:34:14.111	25	2
226	2017-12-01 14:55:17.15	2018-05-03 00:00:00	\N	4	2017-12-01 15:34:14.271	25	2
168	2017-12-01 12:16:30.455	2018-09-10 00:00:00	\N	7	2017-12-01 12:23:09.917	71	2
166	2017-12-01 12:15:30.51	2018-06-25 00:00:00	\N	14	2017-12-01 12:23:09.957	71	2
167	2017-12-01 12:16:10.936	2018-11-12 00:00:00	\N	7	2017-12-01 12:23:09.995	71	2
176	2017-12-01 12:32:10.581	2018-03-01 00:00:00	\N	14	\N	53	1
178	2017-12-01 12:33:05.62	2018-05-21 00:00:00	\N	7	\N	53	1
179	2017-12-01 12:33:40.099	2018-12-25 00:00:00	\N	7	\N	53	1
175	2017-12-01 12:31:30.442	2018-06-04 00:00:00	5	6	2017-12-01 16:55:15.095	21	2
157	2017-12-01 11:49:32.516	2018-10-01 00:00:00	5	7	2017-12-01 16:58:31.228	24	2
180	2017-12-01 13:10:03.345	2018-02-05 00:00:00	\N	14	2017-12-01 13:12:11.428	68	2
181	2017-12-01 13:10:14.413	2018-07-23 00:00:00	\N	14	2017-12-01 13:12:11.464	68	2
183	2017-12-01 13:29:19.677	2018-10-01 00:00:00	\N	7	2017-12-01 13:34:01.815	63	2
184	2017-12-01 13:32:38.232	2018-06-11 00:00:00	\N	14	2017-12-01 13:34:01.862	63	2
185	2017-12-01 13:33:48.893	2018-12-03 00:00:00	\N	7	2017-12-01 13:34:01.909	63	2
186	2017-12-01 13:44:49.736	2018-06-18 00:00:00	\N	7	2017-12-01 13:45:30.806	70	2
187	2017-12-01 13:45:06.663	2018-12-17 00:00:00	\N	7	2017-12-01 13:45:30.843	70	2
192	2017-12-01 14:02:11.797	2018-03-05 00:00:00	\N	3	\N	55	1
188	2017-12-01 13:56:42.426	2017-12-25 00:00:00	\N	5	2017-12-01 14:02:42.984	32	2
190	2017-12-01 13:58:28.528	2018-01-09 00:00:00	\N	14	2017-12-01 14:02:43.028	32	2
191	2017-12-01 13:58:59.48	2018-06-04 00:00:00	\N	14	2017-12-01 14:02:43.074	32	2
193	2017-12-01 14:02:50.888	2018-05-03 00:00:00	\N	2	\N	55	1
174	2017-12-01 12:24:59.824	2018-08-06 00:00:00	5	12	2017-12-01 16:55:34.531	21	2
195	2017-12-01 14:25:08.307	2018-08-20 00:00:00	0	12	2017-12-01 14:25:08.311	18	4
238	2017-12-01 15:08:20.128	2018-08-20 00:00:00	0	12	2017-12-01 16:33:18.577	18	4
261	2017-12-01 16:06:09.181	2018-05-14 00:00:00	\N	7	2017-12-01 16:08:13.786	144	2
223	2017-12-01 14:48:09.106	2018-05-14 00:00:00	5	7	2017-12-01 16:12:58.225	23	2
164	2017-12-01 12:10:43.159	2018-07-02 00:00:00	\N	19	2017-12-01 16:50:18.705	56	2
262	2017-12-01 16:06:33.796	2018-08-27 00:00:00	\N	7	2017-12-01 16:08:13.856	144	2
206	2017-12-01 14:34:24.612	2018-06-13 00:00:00	\N	3	\N	55	1
208	2017-12-01 14:35:13.113	2018-08-27 00:00:00	\N	14	\N	55	1
209	2017-12-01 14:35:38.077	2018-12-24 00:00:00	\N	6	\N	55	1
263	2017-12-01 16:06:50.651	2018-11-06 00:00:00	\N	14	2017-12-01 16:08:13.927	144	2
165	2017-12-01 12:11:05.956	2018-08-13 00:00:00	\N	12	2017-12-01 16:50:18.781	56	2
213	2017-12-01 14:37:33.675	2018-05-18 00:00:00	\N	14	2017-12-01 14:39:39.573	59	2
216	2017-12-01 14:38:32.428	2018-07-16 00:00:00	\N	11	2017-12-01 14:39:39.625	59	2
211	2017-12-01 14:37:02.915	2018-05-03 00:00:00	\N	2	2017-12-01 14:42:52.144	58	2
212	2017-12-01 14:37:31.019	2018-05-07 00:00:00	\N	2	2017-12-01 14:42:52.196	58	2
214	2017-12-01 14:37:43.251	2018-05-10 00:00:00	\N	2	2017-12-01 14:42:52.242	58	2
218	2017-12-01 14:39:40.829	2018-08-13 00:00:00	\N	19	2017-12-01 14:42:52.293	58	2
219	2017-12-01 14:42:10.638	2018-06-13 00:00:00	\N	3	2017-12-01 14:42:52.346	58	2
204	2017-12-01 14:33:33.093	2018-06-11 00:00:00	\N	14	2017-12-01 14:43:28.921	51	2
202	2017-12-01 14:33:15.783	2018-02-26 00:00:00	\N	5	2017-12-01 14:43:28.968	51	2
205	2017-12-01 14:34:19.502	2018-10-15 00:00:00	\N	5	2017-12-01 14:43:29.018	51	2
207	2017-12-01 14:34:29.941	2018-10-22 00:00:00	\N	5	2017-12-01 14:43:29.065	51	2
222	2017-12-01 14:45:18.261	2018-02-05 00:00:00	5	7	2017-12-01 16:13:15.64	23	2
177	2017-12-01 12:32:16.088	2018-10-22 00:00:00	5	5	2017-12-01 16:55:50.356	21	2
203	2017-12-01 14:33:31.16	2018-07-23 00:00:00	\N	7	2017-12-01 14:48:53.435	26	2
173	2017-12-01 12:21:33.319	2018-04-02 00:00:00	5	14	2017-12-01 16:54:34.093	21	2
163	2017-12-01 12:02:21.217	2018-08-20 00:00:00	5	14	2017-12-01 16:56:47.781	24	2
162	2017-12-01 11:52:35.623	2018-12-27 00:00:00	5	3	2017-12-01 16:57:25.654	24	2
233	2017-12-01 15:04:44.878	2018-09-01 00:00:00	1	7	2017-12-01 15:07:04.76	85	2
210	2017-12-01 14:35:50.824	2018-01-09 00:00:00	\N	7	2017-12-01 14:49:05.829	22	2
230	2017-12-01 14:57:45.042	2018-04-23 00:00:00	\N	7	2017-12-01 14:58:14.591	22	2
221	2017-12-01 14:44:48.858	2018-12-24 00:00:00	\N	9	2017-12-01 14:49:05.999	22	2
232	2017-12-01 15:04:30.31	2018-08-01 00:00:00	1	7	2017-12-01 15:08:27.713	85	2
225	2017-12-01 14:54:41.117	2018-03-12 00:00:00	\N	14	2017-12-01 15:34:14.204	25	2
234	2017-12-01 15:05:28.445	2018-04-09 00:00:00	\N	12	2017-12-01 15:08:20.131	39	2
237	2017-12-01 15:08:00.803	2018-10-02 00:00:00	\N	11	2017-12-01 15:08:20.264	39	2
228	2017-12-01 14:56:24.79	2018-10-22 00:00:00	\N	7	2017-12-01 15:34:14.349	25	2
231	2017-12-01 15:04:15.558	2018-07-01 00:00:00	1	14	2017-12-01 15:32:32.037	85	3
108	2017-12-01 09:48:40.803	2018-07-02 00:00:00	\N	7	2017-12-01 15:08:20.265	26	2
241	2017-12-01 15:14:38.427	2018-06-25 00:00:00	1	4	2017-12-01 15:23:21.055	84	2
242	2017-12-01 15:26:55.283	2018-02-14 00:00:00	\N	3	\N	62	1
243	2017-12-01 15:27:08.386	2018-02-19 00:00:00	\N	5	\N	62	1
245	2017-12-01 15:34:14.104	2018-07-02 00:00:00	\N	4	2017-12-01 15:34:14.112	84	2
48	2017-11-30 17:50:49.397	2018-03-26 00:00:00	\N	14	2017-12-01 16:05:54.482	142	2
109	2017-12-01 09:49:01.072	2018-08-20 00:00:00	\N	14	2017-12-01 16:05:54.482	26	2
110	2017-12-01 09:49:17.768	2018-12-24 00:00:00	\N	7	2017-12-01 16:05:54.482	26	2
112	2017-12-01 09:52:44.888	2018-12-24 00:00:00	\N	6	2017-12-01 16:05:54.482	16	2
170	2017-12-01 12:17:04.372	2018-05-03 00:00:00	\N	9	2017-12-01 16:05:54.482	18	2
171	2017-12-01 12:17:28.307	2018-07-02 00:00:00	\N	19	2017-12-01 16:05:54.482	18	2
247	2017-12-01 15:34:14.196	2018-07-02 00:00:00	\N	4	2017-12-01 15:34:14.205	84	2
111	2017-12-01 09:50:34.452	2018-05-18 00:00:00	\N	14	2017-12-01 16:05:54.482	16	2
169	2017-12-01 12:16:46.941	2018-01-29 00:00:00	\N	5	2017-12-01 16:05:54.482	18	2
249	2017-12-01 15:34:14.266	2018-07-02 00:00:00	\N	4	2017-12-01 15:34:14.272	84	2
251	2017-12-01 15:34:14.344	2018-07-02 00:00:00	\N	4	2017-12-01 15:34:14.35	84	2
240	2017-12-01 15:08:20.261	2018-08-20 00:00:00	0	12	2017-12-01 16:31:27.972	18	4
239	2017-12-01 15:08:20.192	2018-08-20 00:00:00	0	12	2017-12-01 16:33:35.597	18	4
268	2017-12-01 16:29:45.486	2018-04-23 00:00:00	\N	14	2017-12-01 16:34:29.361	4	2
269	2017-12-01 16:30:21.557	2018-08-01 00:00:00	\N	14	2017-12-01 16:34:29.432	4	2
273	2017-12-01 16:31:49.14	2018-10-01 00:00:00	\N	7	2017-12-01 16:34:29.505	4	2
274	2017-12-01 16:32:38.957	2018-06-01 00:00:00	\N	5	2017-12-01 16:34:29.562	4	2
275	2017-12-01 16:54:15.994	2018-07-02 00:00:00	\N	21	2017-12-01 16:54:26.225	22	2
267	2017-12-01 16:21:35.659	2018-08-01 00:00:00	5	5	2017-12-01 17:05:28.14	143	2
264	2017-12-01 16:19:23.299	2018-02-01 00:00:00	\N	2	2017-12-01 17:06:27.226	143	2
265	2017-12-01 16:20:40.959	2018-06-01 00:00:00	5	5	2017-12-01 17:06:28.881	143	2
276	2017-12-01 17:06:20.599	2018-09-01 00:00:00	5	14	2017-12-01 17:06:59.748	143	2
266	2017-12-01 16:21:00.787	2018-05-03 00:00:00	5	2	2017-12-01 17:07:29.497	143	2
277	2017-12-01 17:14:09.47	2018-10-04 00:00:00	\N	7	2017-12-01 17:14:22.355	19	2
\.


--
-- Data for Name: holiday_period_neg_history; Type: TABLE DATA; Schema: public; Owner: holidays
--

COPY holiday_period_neg_history (hol_period_neg_history_id, comment, created, new_status, old_status, updated, hol_period_id) FROM stdin;
162	Сотрудник добавил период отпуска.	2017-11-30 18:08:55.577	Новый	Новый	\N	58
163	Отпуск отправлен на согласование	2017-11-30 18:09:37.869	На согласовании	Новый	\N	58
164	Сотрудник добавил период отпуска.	2017-11-30 18:29:29.241	Новый	Новый	\N	59
165	Отпуск отправлен на согласование	2017-11-30 18:29:50.533	На согласовании	Новый	\N	59
166	Сотрудник добавил период отпуска.	2017-11-30 19:16:16.892	Новый	Новый	\N	60
167	Сотрудник добавил период отпуска.	2017-11-30 19:18:26.201	Новый	Новый	\N	61
168	Сотрудник добавил период отпуска.	2017-11-30 19:18:55.561	Новый	Новый	\N	62
169	Сотрудник добавил период отпуска.	2017-11-30 19:19:15.129	Новый	Новый	\N	63
170	Сотрудник добавил период отпуска.	2017-11-30 19:19:32.711	Новый	Новый	\N	64
171	Сотрудник добавил период отпуска.	2017-11-30 19:20:48.979	Новый	Новый	\N	65
172	Отпуск отправлен на согласование	2017-11-30 19:20:57.213	На согласовании	Новый	\N	61
173	Отпуск отправлен на согласование	2017-11-30 19:20:57.215	На согласовании	Новый	\N	62
174	Отпуск отправлен на согласование	2017-11-30 19:20:57.219	На согласовании	Новый	\N	63
175	Отпуск отправлен на согласование	2017-11-30 19:20:57.23	На согласовании	Новый	\N	64
176	Сотрудник добавил период отпуска.	2017-11-30 19:23:53.508	Новый	Новый	\N	66
177	Отпуск отправлен на согласование	2017-11-30 19:24:11.239	На согласовании	Новый	\N	60
178	Отпуск отправлен на согласование	2017-11-30 19:24:11.241	На согласовании	Новый	\N	65
179	Отпуск отправлен на согласование	2017-11-30 19:24:11.243	На согласовании	Новый	\N	66
181	Сотрудник добавил период отпуска.	2017-11-30 19:28:05.29	Новый	Новый	\N	68
182	Сотрудник добавил период отпуска.	2017-11-30 19:31:20.452	Новый	Новый	\N	69
184	Сотрудник добавил период отпуска.	2017-11-30 19:32:13.179	Новый	Новый	\N	71
185	Сотрудник добавил период отпуска.	2017-11-30 19:32:58.618	Новый	Новый	\N	72
186	Сотрудник добавил период отпуска.	2017-11-30 19:33:23.362	Новый	Новый	\N	73
187	Сотрудник добавил период отпуска.	2017-11-30 19:33:57.826	Новый	Новый	\N	74
189	Отпуск отправлен на согласование	2017-11-30 19:34:28.591	На согласовании	Новый	\N	68
190	Отпуск отправлен на согласование	2017-11-30 19:34:28.592	На согласовании	Новый	\N	69
191	Отпуск отправлен на согласование	2017-11-30 19:34:28.594	На согласовании	Новый	\N	71
192	Отпуск отправлен на согласование	2017-11-30 19:34:28.595	На согласовании	Новый	\N	72
193	Отпуск отправлен на согласование	2017-11-30 19:34:28.597	На согласовании	Новый	\N	73
194	Отпуск отправлен на согласование	2017-11-30 19:34:28.598	На согласовании	Новый	\N	74
195	Сотрудник добавил период отпуска.	2017-11-30 19:38:49.44	Новый	Новый	\N	75
196	Сотрудник добавил период отпуска.	2017-11-30 19:39:12.816	Новый	Новый	\N	76
198	Сотрудник добавил период отпуска.	2017-11-30 19:44:28.881	Новый	Новый	\N	78
199	Сотрудник добавил период отпуска.	2017-11-30 19:45:33.068	Новый	Новый	\N	79
200	Отпуск отправлен на согласование	2017-11-30 19:46:24.326	На согласовании	Новый	\N	75
201	Отпуск отправлен на согласование	2017-11-30 19:46:24.328	На согласовании	Новый	\N	76
202	Отпуск отправлен на согласование	2017-11-30 19:46:24.331	На согласовании	Новый	\N	78
203	Отпуск отправлен на согласование	2017-11-30 19:46:24.343	На согласовании	Новый	\N	79
205	Сотрудник добавил период отпуска.	2017-11-30 20:11:01.554	Новый	Новый	\N	81
206	Сотрудник добавил период отпуска.	2017-11-30 20:42:40.019	Новый	Новый	\N	82
208	Сотрудник добавил период отпуска.	2017-11-30 20:47:06.528	Новый	Новый	\N	84
209	Сотрудник добавил период отпуска.	2017-11-30 20:48:21.042	Новый	Новый	\N	85
210	Отпуск отправлен на согласование	2017-11-30 20:52:10.914	На согласовании	Новый	\N	81
211	Отпуск отправлен на согласование	2017-11-30 20:52:10.916	На согласовании	Новый	\N	82
212	Отпуск отправлен на согласование	2017-11-30 20:52:10.918	На согласовании	Новый	\N	84
213	Отпуск отправлен на согласование	2017-11-30 20:52:10.919	На согласовании	Новый	\N	85
267	Сотрудник добавил период отпуска.	2017-12-01 09:48:40.807	Новый	Новый	\N	108
268	Сотрудник добавил период отпуска.	2017-12-01 09:49:01.078	Новый	Новый	\N	109
269	Сотрудник добавил период отпуска.	2017-12-01 09:49:17.771	Новый	Новый	\N	110
270	Отпуск отправлен на согласование	2017-12-01 09:49:56.01	На согласовании	Новый	\N	108
271	Отпуск отправлен на согласование	2017-12-01 09:49:56.012	На согласовании	Новый	\N	109
272	Отпуск отправлен на согласование	2017-12-01 09:49:56.014	На согласовании	Новый	\N	110
273	Сотрудник добавил период отпуска.	2017-12-01 09:50:34.456	Новый	Новый	\N	111
214	Сотрудник добавил период отпуска.	2017-12-01 02:10:47.811	Новый	Новый	\N	86
215	Сотрудник добавил период отпуска.	2017-12-01 02:11:30.195	Новый	Новый	\N	87
216	Сотрудник добавил период отпуска.	2017-12-01 02:11:53.673	Новый	Новый	\N	88
217	Сотрудник добавил период отпуска.	2017-12-01 02:12:12.571	Новый	Новый	\N	89
218	Сотрудник добавил период отпуска.	2017-12-01 02:12:55.575	Новый	Новый	\N	90
219	Отпуск отправлен на согласование	2017-12-01 02:13:58.46	На согласовании	Новый	\N	86
220	Отпуск отправлен на согласование	2017-12-01 02:13:58.469	На согласовании	Новый	\N	87
221	Отпуск отправлен на согласование	2017-12-01 02:13:58.471	На согласовании	Новый	\N	88
222	Отпуск отправлен на согласование	2017-12-01 02:13:58.473	На согласовании	Новый	\N	89
223	Отпуск отправлен на согласование	2017-12-01 02:13:58.475	На согласовании	Новый	\N	90
224	Сотрудник добавил период отпуска.	2017-12-01 08:30:00.031	Новый	Новый	\N	91
225	Сотрудник добавил период отпуска.	2017-12-01 08:32:21.816	Новый	Новый	\N	92
226	Сотрудник добавил период отпуска.	2017-12-01 08:37:02.781	Новый	Новый	\N	93
227	Сотрудник добавил период отпуска.	2017-12-01 08:38:44.784	Новый	Новый	\N	94
228	Сотрудник добавил период отпуска.	2017-12-01 08:42:33.876	Новый	Новый	\N	95
229	Сотрудник добавил период отпуска.	2017-12-01 08:43:06.404	Новый	Новый	\N	96
230	Отпуск отправлен на согласование	2017-12-01 08:43:48.417	На согласовании	Новый	\N	91
231	Отпуск отправлен на согласование	2017-12-01 08:43:48.419	На согласовании	Новый	\N	92
232	Отпуск отправлен на согласование	2017-12-01 08:43:48.42	На согласовании	Новый	\N	93
233	Отпуск отправлен на согласование	2017-12-01 08:43:48.422	На согласовании	Новый	\N	94
234	Отпуск отправлен на согласование	2017-12-01 08:43:48.423	На согласовании	Новый	\N	95
235	Отпуск отправлен на согласование	2017-12-01 08:43:48.425	На согласовании	Новый	\N	96
237	Кошкарёв Максим Игоревич согласовал Ваш период отпуска	2017-12-01 09:17:43.175	Частично согласован	На согласовании	\N	68
238	Кошкарёв Максим Игоревич согласовал Ваш период отпуска	2017-12-01 09:18:20.901	Частично согласован	На согласовании	\N	69
239	Кошкарёв Максим Игоревич согласовал Ваш период отпуска	2017-12-01 09:19:13.38	Частично согласован	На согласовании	\N	73
240	Сотрудник добавил период отпуска.	2017-12-01 09:22:06.285	Новый	Новый	\N	97
241	Сотрудник добавил период отпуска.	2017-12-01 09:22:16.978	Новый	Новый	\N	98
242	Сотрудник добавил период отпуска.	2017-12-01 09:22:33.904	Новый	Новый	\N	99
243	Сотрудник добавил период отпуска.	2017-12-01 09:22:42.971	Новый	Новый	\N	100
244	Отпуск отправлен на согласование	2017-12-01 09:23:02.648	На согласовании	Новый	\N	97
245	Отпуск отправлен на согласование	2017-12-01 09:23:02.65	На согласовании	Новый	\N	98
246	Отпуск отправлен на согласование	2017-12-01 09:23:02.651	На согласовании	Новый	\N	99
247	Отпуск отправлен на согласование	2017-12-01 09:23:02.652	На согласовании	Новый	\N	100
248	Сотрудник добавил период отпуска.	2017-12-01 09:27:13.166	Новый	Новый	\N	101
249	Сотрудник добавил период отпуска.	2017-12-01 09:27:28.279	Новый	Новый	\N	102
250	Отпуск отправлен на согласование	2017-12-01 09:28:16.585	На согласовании	Новый	\N	101
85	Сотрудник добавил период отпуска.	2017-11-30 16:35:38.21	Новый	Новый	\N	39
86	Отпуск отправлен на согласование	2017-11-30 16:44:00.676	На согласовании	Новый	\N	39
251	Отпуск отправлен на согласование	2017-12-01 09:28:16.588	На согласовании	Новый	\N	102
252	Сотрудник добавил период отпуска.	2017-12-01 09:30:59.45	Новый	Новый	\N	103
253	Сотрудник добавил период отпуска.	2017-12-01 09:31:08.471	Новый	Новый	\N	104
255	Сотрудник добавил период отпуска.	2017-12-01 09:31:38.116	Новый	Новый	\N	106
256	Сотрудник добавил период отпуска.	2017-12-01 09:31:50.963	Новый	Новый	\N	107
257	Отпуск отправлен на согласование	2017-12-01 09:32:32.856	На согласовании	Новый	\N	103
258	Отпуск отправлен на согласование	2017-12-01 09:32:32.857	На согласовании	Новый	\N	104
260	Отпуск отправлен на согласование	2017-12-01 09:32:32.86	На согласовании	Новый	\N	106
261	Отпуск отправлен на согласование	2017-12-01 09:32:32.861	На согласовании	Новый	\N	107
262	Отпуск отправлен на согласование	2017-12-01 09:33:18.387	На согласовании	Новый	\N	48
263	Отпуск отправлен на согласование	2017-12-01 09:33:18.388	На согласовании	Новый	\N	49
146	Сотрудник добавил период отпуска.	2017-11-30 17:48:34.547	Новый	Новый	\N	46
147	Сотрудник добавил период отпуска.	2017-11-30 17:49:48.118	Новый	Новый	\N	47
148	Сотрудник добавил период отпуска.	2017-11-30 17:50:49.4	Новый	Новый	\N	48
149	Сотрудник добавил период отпуска.	2017-11-30 17:52:46.683	Новый	Новый	\N	49
150	Сотрудник добавил период отпуска.	2017-11-30 17:53:15.913	Новый	Новый	\N	50
151	Сотрудник добавил период отпуска.	2017-11-30 17:54:48.779	Новый	Новый	\N	51
264	Отпуск отправлен на согласование	2017-12-01 09:33:18.391	На согласовании	Новый	\N	51
265	Отпуск отправлен на согласование	2017-12-01 09:33:18.394	На согласовании	Новый	\N	54
154	Сотрудник добавил период отпуска.	2017-11-30 17:57:08.783	Новый	Новый	\N	54
266	Отпуск отправлен на согласование	2017-12-01 09:33:18.396	На согласовании	Новый	\N	57
156	Сотрудник добавил период отпуска.	2017-11-30 18:00:07.372	Новый	Новый	\N	56
157	Отпуск отправлен на согласование	2017-11-30 18:00:17.664	На согласовании	Новый	\N	46
158	Отпуск отправлен на согласование	2017-11-30 18:00:17.669	На согласовании	Новый	\N	47
274	Сотрудник добавил период отпуска.	2017-12-01 09:52:44.892	Новый	Новый	\N	112
275	Сотрудник добавил период отпуска.	2017-12-01 09:54:07.442	Новый	Новый	\N	113
159	Отпуск отправлен на согласование	2017-11-30 18:00:17.67	На согласовании	Новый	\N	50
160	Отпуск отправлен на согласование	2017-11-30 18:00:17.672	На согласовании	Новый	\N	56
161	Сотрудник добавил период отпуска.	2017-11-30 18:01:50.694	Новый	Новый	\N	57
276	Сотрудник добавил период отпуска.	2017-12-01 09:54:44.208	Новый	Новый	\N	114
277	Отпуск отправлен на согласование	2017-12-01 09:55:01.51	На согласовании	Новый	\N	113
278	Отпуск отправлен на согласование	2017-12-01 09:55:01.513	На согласовании	Новый	\N	114
279	Сотрудник добавил период отпуска.	2017-12-01 09:56:49.229	Новый	Новый	\N	115
280	Сотрудник добавил период отпуска.	2017-12-01 09:58:13.449	Новый	Новый	\N	116
281	Отпуск отправлен на согласование	2017-12-01 10:02:24.738	На согласовании	Новый	\N	115
282	Отпуск отправлен на согласование	2017-12-01 10:02:24.741	На согласовании	Новый	\N	116
283	Отпуск отправлен на согласование	2017-12-01 10:02:24.743	На согласовании	Новый	\N	111
284	Отпуск отправлен на согласование	2017-12-01 10:02:24.745	На согласовании	Новый	\N	112
285	Сотрудник добавил период отпуска.	2017-12-01 10:06:59.081	Новый	Новый	\N	117
286	Сотрудник добавил период отпуска.	2017-12-01 10:07:19.468	Новый	Новый	\N	118
287	Сотрудник добавил период отпуска.	2017-12-01 10:08:21.149	Новый	Новый	\N	119
288	Сотрудник добавил период отпуска.	2017-12-01 10:08:38.396	Новый	Новый	\N	120
289	Отпуск отправлен на согласование	2017-12-01 10:11:06.286	На согласовании	Новый	\N	117
290	Отпуск отправлен на согласование	2017-12-01 10:11:06.287	На согласовании	Новый	\N	118
291	Отпуск отправлен на согласование	2017-12-01 10:11:06.289	На согласовании	Новый	\N	119
292	Отпуск отправлен на согласование	2017-12-01 10:11:06.29	На согласовании	Новый	\N	120
293	Сотрудник добавил период отпуска.	2017-12-01 10:15:10.556	Новый	Новый	\N	121
294	Сотрудник добавил период отпуска.	2017-12-01 10:15:29.738	Новый	Новый	\N	122
295	Сотрудник добавил период отпуска.	2017-12-01 10:15:44.644	Новый	Новый	\N	123
296	Отпуск отправлен на согласование	2017-12-01 10:16:59.736	На согласовании	Новый	\N	121
297	Отпуск отправлен на согласование	2017-12-01 10:16:59.738	На согласовании	Новый	\N	122
298	Отпуск отправлен на согласование	2017-12-01 10:16:59.739	На согласовании	Новый	\N	123
299	Сотрудник добавил период отпуска.	2017-12-01 10:38:14.225	Новый	Новый	\N	124
300	Сотрудник добавил период отпуска.	2017-12-01 10:38:28.005	Новый	Новый	\N	125
301	Сотрудник добавил период отпуска.	2017-12-01 10:38:42.447	Новый	Новый	\N	126
302	Сотрудник добавил период отпуска.	2017-12-01 10:38:56.002	Новый	Новый	\N	127
303	Сотрудник добавил период отпуска.	2017-12-01 10:39:38.858	Новый	Новый	\N	128
304	Сотрудник добавил период отпуска.	2017-12-01 10:39:54.732	Новый	Новый	\N	129
305	Отпуск отправлен на согласование	2017-12-01 10:41:44.396	На согласовании	Новый	\N	124
306	Отпуск отправлен на согласование	2017-12-01 10:41:44.398	На согласовании	Новый	\N	125
307	Отпуск отправлен на согласование	2017-12-01 10:41:44.4	На согласовании	Новый	\N	126
308	Отпуск отправлен на согласование	2017-12-01 10:41:44.402	На согласовании	Новый	\N	127
309	Отпуск отправлен на согласование	2017-12-01 10:41:44.404	На согласовании	Новый	\N	128
310	Отпуск отправлен на согласование	2017-12-01 10:41:44.405	На согласовании	Новый	\N	129
311	Сотрудник добавил период отпуска.	2017-12-01 10:46:39.727	Новый	Новый	\N	130
315	Сотрудник добавил период отпуска.	2017-12-01 10:54:15.301	Новый	Новый	\N	134
316	Сотрудник добавил период отпуска.	2017-12-01 10:54:32.107	Новый	Новый	\N	135
317	Сотрудник добавил период отпуска.	2017-12-01 10:55:13.115	Новый	Новый	\N	136
318	Сотрудник добавил период отпуска.	2017-12-01 11:00:01.758	Новый	Новый	\N	137
320	Сотрудник добавил период отпуска.	2017-12-01 11:01:02.471	Новый	Новый	\N	139
321	Сотрудник добавил период отпуска.	2017-12-01 11:06:55.634	Новый	Новый	\N	140
322	Сотрудник добавил период отпуска.	2017-12-01 11:08:02.985	Новый	Новый	\N	141
323	Отпуск отправлен на согласование	2017-12-01 11:10:31.081	На согласовании	Новый	\N	137
324	Отпуск отправлен на согласование	2017-12-01 11:10:31.082	На согласовании	Новый	\N	139
325	Отпуск отправлен на согласование	2017-12-01 11:10:31.084	На согласовании	Новый	\N	140
326	Отпуск отправлен на согласование	2017-12-01 11:10:31.085	На согласовании	Новый	\N	141
328	Сотрудник добавил период отпуска.	2017-12-01 11:42:40.589	Новый	Новый	\N	143
330	Сотрудник добавил период отпуска.	2017-12-01 11:45:45.322	Новый	Новый	\N	145
331	Сотрудник добавил период отпуска.	2017-12-01 11:45:53.794	Новый	Новый	\N	146
332	Сотрудник добавил период отпуска.	2017-12-01 11:46:18.624	Новый	Новый	\N	147
333	Сотрудник добавил период отпуска.	2017-12-01 11:46:23.018	Новый	Новый	\N	148
334	Сотрудник добавил период отпуска.	2017-12-01 11:46:36.599	Новый	Новый	\N	149
335	Сотрудник добавил период отпуска.	2017-12-01 11:46:43.859	Новый	Новый	\N	150
336	Сотрудник добавил период отпуска.	2017-12-01 11:46:56.449	Новый	Новый	\N	151
337	Сотрудник добавил период отпуска.	2017-12-01 11:47:04.481	Новый	Новый	\N	152
338	Сотрудник добавил период отпуска.	2017-12-01 11:47:05.049	Новый	Новый	\N	153
339	Сотрудник добавил период отпуска.	2017-12-01 11:47:44.465	Новый	Новый	\N	154
342	Сотрудник добавил период отпуска.	2017-12-01 11:49:32.518	Новый	Новый	\N	157
343	Отпуск отправлен на согласование	2017-12-01 11:49:58.687	На согласовании	Новый	\N	147
344	Отпуск отправлен на согласование	2017-12-01 11:49:58.69	На согласовании	Новый	\N	149
345	Отпуск отправлен на согласование	2017-12-01 11:49:58.691	На согласовании	Новый	\N	151
346	Отпуск отправлен на согласование	2017-12-01 11:49:58.693	На согласовании	Новый	\N	154
347	Сотрудник добавил период отпуска.	2017-12-01 11:50:34.991	Новый	Новый	\N	158
348	Сотрудник добавил период отпуска.	2017-12-01 11:51:06.732	Новый	Новый	\N	159
349	Отпуск отправлен на согласование	2017-12-01 11:51:23.707	На согласовании	Новый	\N	145
350	Отпуск отправлен на согласование	2017-12-01 11:51:23.709	На согласовании	Новый	\N	148
351	Отпуск отправлен на согласование	2017-12-01 11:51:23.71	На согласовании	Новый	\N	150
352	Отпуск отправлен на согласование	2017-12-01 11:51:23.712	На согласовании	Новый	\N	152
353	Отпуск отправлен на согласование	2017-12-01 11:51:23.713	На согласовании	Новый	\N	158
355	Сотрудник добавил период отпуска.	2017-12-01 11:52:22.175	Новый	Новый	\N	161
356	Сотрудник добавил период отпуска.	2017-12-01 11:52:35.626	Новый	Новый	\N	162
357	Сотрудник добавил период отпуска.	2017-12-01 12:02:21.221	Новый	Новый	\N	163
358	Отпуск отправлен на согласование	2017-12-01 12:05:27.986	На согласовании	Новый	\N	163
359	Отпуск отправлен на согласование	2017-12-01 12:05:27.987	На согласовании	Новый	\N	162
360	Отпуск отправлен на согласование	2017-12-01 12:05:27.989	На согласовании	Новый	\N	159
361	Отпуск отправлен на согласование	2017-12-01 12:05:27.99	На согласовании	Новый	\N	157
362	Отпуск отправлен на согласование	2017-12-01 12:05:27.992	На согласовании	Новый	\N	153
363	Отпуск отправлен на согласование	2017-12-01 12:05:27.993	На согласовании	Новый	\N	146
364	Сотрудник добавил период отпуска.	2017-12-01 12:10:43.163	Новый	Новый	\N	164
365	Сотрудник добавил период отпуска.	2017-12-01 12:11:05.959	Новый	Новый	\N	165
366	Сотрудник добавил период отпуска.	2017-12-01 12:15:30.513	Новый	Новый	\N	166
367	Сотрудник добавил период отпуска.	2017-12-01 12:16:10.938	Новый	Новый	\N	167
368	Сотрудник добавил период отпуска.	2017-12-01 12:16:30.458	Новый	Новый	\N	168
369	Сотрудник добавил период отпуска.	2017-12-01 12:16:46.945	Новый	Новый	\N	169
370	Сотрудник добавил период отпуска.	2017-12-01 12:17:04.375	Новый	Новый	\N	170
371	Сотрудник добавил период отпуска.	2017-12-01 12:17:28.31	Новый	Новый	\N	171
373	Отпуск отправлен на согласование	2017-12-01 12:19:43.466	На согласовании	Новый	\N	169
374	Отпуск отправлен на согласование	2017-12-01 12:19:43.468	На согласовании	Новый	\N	170
375	Отпуск отправлен на согласование	2017-12-01 12:19:43.47	На согласовании	Новый	\N	171
377	Сотрудник добавил период отпуска.	2017-12-01 12:21:33.322	Новый	Новый	\N	173
378	Отпуск отправлен на согласование	2017-12-01 12:23:09.998	На согласовании	Новый	\N	168
379	Отпуск отправлен на согласование	2017-12-01 12:23:10	На согласовании	Новый	\N	166
380	Отпуск отправлен на согласование	2017-12-01 12:23:10.002	На согласовании	Новый	\N	167
381	Сотрудник добавил период отпуска.	2017-12-01 12:24:59.827	Новый	Новый	\N	174
382	Сотрудник добавил период отпуска.	2017-12-01 12:31:30.445	Новый	Новый	\N	175
383	Сотрудник добавил период отпуска.	2017-12-01 12:32:10.585	Новый	Новый	\N	176
384	Сотрудник добавил период отпуска.	2017-12-01 12:32:16.091	Новый	Новый	\N	177
385	Сотрудник добавил период отпуска.	2017-12-01 12:33:05.623	Новый	Новый	\N	178
386	Сотрудник добавил период отпуска.	2017-12-01 12:33:40.102	Новый	Новый	\N	179
387	Отпуск отправлен на согласование	2017-12-01 12:33:54.763	На согласовании	Новый	\N	173
388	Отпуск отправлен на согласование	2017-12-01 12:33:54.764	На согласовании	Новый	\N	174
389	Отпуск отправлен на согласование	2017-12-01 12:33:54.766	На согласовании	Новый	\N	175
390	Отпуск отправлен на согласование	2017-12-01 12:33:54.768	На согласовании	Новый	\N	177
391	Сотрудник добавил период отпуска.	2017-12-01 13:10:03.349	Новый	Новый	\N	180
392	Сотрудник добавил период отпуска.	2017-12-01 13:10:14.416	Новый	Новый	\N	181
393	Отпуск отправлен на согласование	2017-12-01 13:12:11.466	На согласовании	Новый	\N	180
394	Отпуск отправлен на согласование	2017-12-01 13:12:11.468	На согласовании	Новый	\N	181
396	Сотрудник добавил период отпуска.	2017-12-01 13:29:19.679	Новый	Новый	\N	183
397	Сотрудник добавил период отпуска.	2017-12-01 13:32:38.234	Новый	Новый	\N	184
398	Сотрудник добавил период отпуска.	2017-12-01 13:33:48.896	Новый	Новый	\N	185
399	Отпуск отправлен на согласование	2017-12-01 13:34:01.912	На согласовании	Новый	\N	183
400	Отпуск отправлен на согласование	2017-12-01 13:34:01.914	На согласовании	Новый	\N	184
401	Отпуск отправлен на согласование	2017-12-01 13:34:01.915	На согласовании	Новый	\N	185
402	Сотрудник добавил период отпуска.	2017-12-01 13:44:49.739	Новый	Новый	\N	186
403	Сотрудник добавил период отпуска.	2017-12-01 13:45:06.666	Новый	Новый	\N	187
404	Отпуск отправлен на согласование	2017-12-01 13:45:30.851	На согласовании	Новый	\N	130
405	Отпуск отправлен на согласование	2017-12-01 13:45:30.855	На согласовании	Новый	\N	186
406	Отпуск отправлен на согласование	2017-12-01 13:45:30.857	На согласовании	Новый	\N	187
407	Сотрудник добавил период отпуска.	2017-12-01 13:56:42.429	Новый	Новый	\N	188
409	Сотрудник добавил период отпуска.	2017-12-01 13:58:28.53	Новый	Новый	\N	190
410	Сотрудник добавил период отпуска.	2017-12-01 13:58:59.483	Новый	Новый	\N	191
411	Сотрудник добавил период отпуска.	2017-12-01 14:02:11.8	Новый	Новый	\N	192
412	Отпуск отправлен на согласование	2017-12-01 14:02:43.077	На согласовании	Новый	\N	188
413	Отпуск отправлен на согласование	2017-12-01 14:02:43.079	На согласовании	Новый	\N	190
414	Отпуск отправлен на согласование	2017-12-01 14:02:43.081	На согласовании	Новый	\N	191
415	Сотрудник добавил период отпуска.	2017-12-01 14:02:50.891	Новый	Новый	\N	193
417	Сотрудник добавил период отпуска.	2017-12-01 14:23:50.423	Новый	Новый	\N	194
418	Отпуск отправлен на согласование	2017-12-01 14:24:06.583	На согласовании	Новый	\N	194
419	Отпуск отправлен на согласование	2017-12-01 14:25:08.308	На согласовании	Новый	\N	195
420	Сотрудник добавил период отпуска.	2017-12-01 14:25:08.309	Новый	Новый	\N	195
421	Андриановская Маргарита Владимировна отклонил Ваш период отпуска	2017-12-01 14:25:08.31	Отклонён	На согласовании	\N	195
422	Андриановская Маргарита Владимировна отклонил Ваш период отпуска	2017-12-01 14:25:08.315	Отклонён	На согласовании	\N	108
426	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 14:26:33.141	Частично согласован	На согласовании	\N	109
428	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 14:26:49.901	Частично согласован	На согласовании	\N	110
435	Сотрудник добавил период отпуска.	2017-12-01 14:33:15.786	Новый	Новый	\N	202
436	Сотрудник добавил период отпуска.	2017-12-01 14:33:31.163	Новый	Новый	\N	203
437	Сотрудник добавил период отпуска.	2017-12-01 14:33:33.096	Новый	Новый	\N	204
438	Отпуск отправлен на согласование	2017-12-01 14:33:39.207	На согласовании	Новый	\N	203
439	Сотрудник добавил период отпуска.	2017-12-01 14:34:19.506	Новый	Новый	\N	205
440	Сотрудник добавил период отпуска.	2017-12-01 14:34:24.615	Новый	Новый	\N	206
441	Сотрудник добавил период отпуска.	2017-12-01 14:34:29.944	Новый	Новый	\N	207
442	Сотрудник добавил период отпуска.	2017-12-01 14:35:13.122	Новый	Новый	\N	208
443	Сотрудник добавил период отпуска.	2017-12-01 14:35:38.08	Новый	Новый	\N	209
444	Сотрудник добавил период отпуска.	2017-12-01 14:35:50.826	Новый	Новый	\N	210
445	Сотрудник добавил период отпуска.	2017-12-01 14:37:02.918	Новый	Новый	\N	211
446	Сотрудник добавил период отпуска.	2017-12-01 14:37:31.021	Новый	Новый	\N	212
447	Сотрудник добавил период отпуска.	2017-12-01 14:37:33.678	Новый	Новый	\N	213
448	Сотрудник добавил период отпуска.	2017-12-01 14:37:43.253	Новый	Новый	\N	214
450	Сотрудник добавил период отпуска.	2017-12-01 14:38:32.431	Новый	Новый	\N	216
452	Отпуск отправлен на согласование	2017-12-01 14:39:39.629	На согласовании	Новый	\N	213
453	Отпуск отправлен на согласование	2017-12-01 14:39:39.631	На согласовании	Новый	\N	216
454	Сотрудник добавил период отпуска.	2017-12-01 14:39:40.831	Новый	Новый	\N	218
455	Сотрудник добавил период отпуска.	2017-12-01 14:42:10.642	Новый	Новый	\N	219
456	Отпуск отправлен на согласование	2017-12-01 14:42:52.35	На согласовании	Новый	\N	211
457	Отпуск отправлен на согласование	2017-12-01 14:42:52.351	На согласовании	Новый	\N	212
458	Отпуск отправлен на согласование	2017-12-01 14:42:52.353	На согласовании	Новый	\N	214
459	Отпуск отправлен на согласование	2017-12-01 14:42:52.354	На согласовании	Новый	\N	218
460	Отпуск отправлен на согласование	2017-12-01 14:42:52.355	На согласовании	Новый	\N	219
461	Отпуск отправлен на согласование	2017-12-01 14:43:29.068	На согласовании	Новый	\N	204
462	Отпуск отправлен на согласование	2017-12-01 14:43:29.069	На согласовании	Новый	\N	202
463	Отпуск отправлен на согласование	2017-12-01 14:43:29.071	На согласовании	Новый	\N	205
464	Отпуск отправлен на согласование	2017-12-01 14:43:29.072	На согласовании	Новый	\N	207
466	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 14:44:15.379	Частично согласован	На согласовании	\N	112
467	Сотрудник добавил период отпуска.	2017-12-01 14:44:48.86	Новый	Новый	\N	221
468	Сотрудник добавил период отпуска.	2017-12-01 14:45:18.264	Новый	Новый	\N	222
469	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 14:46:05.452	Частично согласован	На согласовании	\N	203
470	Сотрудник добавил период отпуска.	2017-12-01 14:48:09.109	Новый	Новый	\N	223
471	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 14:48:33.369	Частично согласован	На согласовании	\N	111
472	Сотрудник добавил период отпуска.	2017-12-01 14:48:39.611	Новый	Новый	\N	224
473	Отпуск отправлен на согласование	2017-12-01 14:48:53.439	На согласовании	Новый	\N	224
474	Отпуск отправлен на согласование	2017-12-01 14:48:53.441	На согласовании	Новый	\N	223
475	Отпуск отправлен на согласование	2017-12-01 14:48:53.443	На согласовании	Новый	\N	222
477	Отпуск отправлен на согласование	2017-12-01 14:49:06.005	На согласовании	Новый	\N	210
480	Отпуск отправлен на согласование	2017-12-01 14:49:06.011	На согласовании	Новый	\N	221
528	Сотрудник добавил период отпуска.	2017-12-01 15:26:55.286	Новый	Новый	\N	242
529	Сотрудник добавил период отпуска.	2017-12-01 15:27:08.389	Новый	Новый	\N	243
530	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-01 15:30:51.448	Частично согласован	На согласовании	\N	61
481	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 14:54:11.855	Частично согласован	На согласовании	\N	48
482	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 14:54:21.985	Частично согласован	На согласовании	\N	51
483	Сотрудник добавил период отпуска.	2017-12-01 14:54:41.12	Новый	Новый	\N	225
484	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 14:54:47.286	Частично согласован	На согласовании	\N	49
485	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 14:55:00.624	Частично согласован	На согласовании	\N	54
486	Сотрудник добавил период отпуска.	2017-12-01 14:55:17.153	Новый	Новый	\N	226
487	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 14:55:41.532	Частично согласован	На согласовании	\N	57
489	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 14:56:08.321	Частично согласован	На согласовании	\N	169
490	Сотрудник добавил период отпуска.	2017-12-01 14:56:24.793	Новый	Новый	\N	228
491	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 14:56:28.358	Частично согласован	На согласовании	\N	170
493	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 14:57:19.396	Частично согласован	На согласовании	\N	171
494	Сотрудник добавил период отпуска.	2017-12-01 14:57:32.107	Новый	Новый	\N	229
495	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 14:57:41.111	Частично согласован	На согласовании	\N	194
496	Сотрудник добавил период отпуска.	2017-12-01 14:57:45.045	Новый	Новый	\N	230
497	Отпуск отправлен на согласование	2017-12-01 14:58:14.595	На согласовании	Новый	\N	230
498	Сотрудник добавил период отпуска.	2017-12-01 15:04:15.561	Новый	Новый	\N	231
499	Сотрудник добавил период отпуска.	2017-12-01 15:04:30.313	Новый	Новый	\N	232
500	Сотрудник добавил период отпуска.	2017-12-01 15:04:44.881	Новый	Новый	\N	233
501	Отпуск отправлен на согласование	2017-12-01 15:05:18.754	На согласовании	Новый	\N	233
502	Отпуск отправлен на согласование	2017-12-01 15:05:18.756	На согласовании	Новый	\N	231
503	Отпуск отправлен на согласование	2017-12-01 15:05:18.757	На согласовании	Новый	\N	232
504	Сотрудник добавил период отпуска.	2017-12-01 15:05:28.448	Новый	Новый	\N	234
505	Сотрудник добавил период отпуска.	2017-12-01 15:05:39.032	Новый	Новый	\N	235
507	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 15:06:41.574	Частично согласован	На согласовании	\N	115
508	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-01 15:07:04.763	Частично согласован	На согласовании	\N	233
509	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 15:07:32.919	Частично согласован	На согласовании	\N	116
510	Сотрудник добавил период отпуска.	2017-12-01 15:08:00.807	Новый	Новый	\N	237
511	Отпуск отправлен на согласование	2017-12-01 15:08:20.129	На согласовании	Новый	\N	238
512	Сотрудник добавил период отпуска.	2017-12-01 15:08:20.13	Новый	Новый	\N	238
513	Отпуск отправлен на согласование	2017-12-01 15:08:20.193	На согласовании	Новый	\N	239
514	Сотрудник добавил период отпуска.	2017-12-01 15:08:20.193	Новый	Новый	\N	239
515	Отпуск отправлен на согласование	2017-12-01 15:08:20.262	На согласовании	Новый	\N	240
516	Сотрудник добавил период отпуска.	2017-12-01 15:08:20.262	Новый	Новый	\N	240
517	Отпуск отправлен на согласование	2017-12-01 15:08:20.275	На согласовании	Новый	\N	234
518	Отпуск отправлен на согласование	2017-12-01 15:08:20.276	На согласовании	Новый	\N	235
519	Отпуск отправлен на согласование	2017-12-01 15:08:20.278	На согласовании	Новый	\N	237
520	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-01 15:08:27.717	Частично согласован	На согласовании	\N	232
521	Видавский Сергей Алексеевич отклонил Ваш период отпуска	2017-12-01 15:09:25.375	Отклонён	На согласовании	\N	231
522	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-01 15:09:49.971	Частично согласован	На согласовании	\N	61
524	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-01 15:11:38.845	Частично согласован	Отклонён	\N	231
525	Сотрудник добавил период отпуска.	2017-12-01 15:14:38.431	Новый	Новый	\N	241
526	Отпуск отправлен на согласование	2017-12-01 15:22:45.376	На согласовании	Новый	\N	241
527	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-01 15:23:21.06	Частично согласован	На согласовании	\N	241
531	Отпуск отправлен на согласование	2017-12-01 15:31:48.465	На согласовании	Новый	\N	231
532	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-01 15:32:32.041	Частично согласован	На согласовании	\N	231
534	Сотрудник добавил период отпуска.	2017-12-01 15:34:14.105	Новый	Новый	\N	245
535	Отпуск отправлен на согласование	2017-12-01 15:34:14.106	На согласовании	Новый	\N	245
538	Сотрудник добавил период отпуска.	2017-12-01 15:34:14.197	Новый	Новый	\N	247
539	Отпуск отправлен на согласование	2017-12-01 15:34:14.197	На согласовании	Новый	\N	247
542	Сотрудник добавил период отпуска.	2017-12-01 15:34:14.267	Новый	Новый	\N	249
543	Отпуск отправлен на согласование	2017-12-01 15:34:14.267	На согласовании	Новый	\N	249
546	Сотрудник добавил период отпуска.	2017-12-01 15:34:14.345	Новый	Новый	\N	251
547	Отпуск отправлен на согласование	2017-12-01 15:34:14.345	На согласовании	Новый	\N	251
550	Отпуск отправлен на согласование	2017-12-01 15:34:14.361	На согласовании	Новый	\N	229
551	Отпуск отправлен на согласование	2017-12-01 15:34:14.362	На согласовании	Новый	\N	225
552	Отпуск отправлен на согласование	2017-12-01 15:34:14.364	На согласовании	Новый	\N	226
553	Отпуск отправлен на согласование	2017-12-01 15:34:14.365	На согласовании	Новый	\N	228
554	Сотрудник добавил период отпуска.	2017-12-01 15:38:24.199	Новый	Новый	\N	253
555	Сотрудник добавил период отпуска.	2017-12-01 15:39:58.196	Новый	Новый	\N	254
556	Сотрудник добавил период отпуска.	2017-12-01 15:40:37.966	Новый	Новый	\N	255
557	Сотрудник добавил период отпуска.	2017-12-01 15:41:50.877	Новый	Новый	\N	256
558	Сотрудник добавил период отпуска.	2017-12-01 15:43:50.003	Новый	Новый	\N	257
562	Сотрудник добавил период отпуска.	2017-12-01 16:04:54.836	Новый	Новый	\N	258
563	Сотрудник добавил период отпуска.	2017-12-01 16:05:05.769	Новый	Новый	\N	259
564	Сотрудник добавил период отпуска.	2017-12-01 16:05:22.994	Новый	Новый	\N	260
565	Отпуск отправлен на согласование	2017-12-01 16:05:54.491	На согласовании	Новый	\N	258
566	Отпуск отправлен на согласование	2017-12-01 16:05:54.493	На согласовании	Новый	\N	259
567	Отпуск отправлен на согласование	2017-12-01 16:05:54.495	На согласовании	Новый	\N	260
568	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 16:06:06.981	Частично согласован	На согласовании	\N	224
569	Сотрудник добавил период отпуска.	2017-12-01 16:06:09.197	Новый	Новый	\N	261
570	Сотрудник добавил период отпуска.	2017-12-01 16:06:33.798	Новый	Новый	\N	262
572	Сотрудник добавил период отпуска.	2017-12-01 16:06:50.654	Новый	Новый	\N	263
573	Отпуск отправлен на согласование	2017-12-01 16:08:13.931	На согласовании	Новый	\N	261
574	Отпуск отправлен на согласование	2017-12-01 16:08:13.932	На согласовании	Новый	\N	262
575	Отпуск отправлен на согласование	2017-12-01 16:08:13.934	На согласовании	Новый	\N	263
579	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 16:12:58.229	Частично согласован	На согласовании	\N	223
580	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 16:13:15.644	Частично согласован	На согласовании	\N	222
581	Сотрудник добавил период отпуска.	2017-12-01 16:19:23.303	Новый	Новый	\N	264
582	Сотрудник добавил период отпуска.	2017-12-01 16:20:40.961	Новый	Новый	\N	265
583	Сотрудник добавил период отпуска.	2017-12-01 16:21:00.789	Новый	Новый	\N	266
584	Сотрудник добавил период отпуска.	2017-12-01 16:21:35.662	Новый	Новый	\N	267
586	Отпуск отправлен на согласование	2017-12-01 16:21:58.486	На согласовании	Новый	\N	264
587	Отпуск отправлен на согласование	2017-12-01 16:21:58.488	На согласовании	Новый	\N	265
588	Отпуск отправлен на согласование	2017-12-01 16:21:58.49	На согласовании	Новый	\N	266
589	Отпуск отправлен на согласование	2017-12-01 16:21:58.492	На согласовании	Новый	\N	267
590	Сотрудник добавил период отпуска.	2017-12-01 16:29:45.489	Новый	Новый	\N	268
591	Сотрудник добавил период отпуска.	2017-12-01 16:30:21.56	Новый	Новый	\N	269
601	Андриановская Маргарита Владимировна отклонил Ваш период отпуска	2017-12-01 16:31:27.98	Отклонён	На согласовании	\N	240
602	Сотрудник добавил период отпуска.	2017-12-01 16:31:49.142	Новый	Новый	\N	273
603	Сотрудник добавил период отпуска.	2017-12-01 16:32:38.96	Новый	Новый	\N	274
604	Андриановская Маргарита Владимировна отклонил Ваш период отпуска	2017-12-01 16:33:18.581	Отклонён	На согласовании	\N	238
605	Андриановская Маргарита Владимировна отклонил Ваш период отпуска	2017-12-01 16:33:35.601	Отклонён	На согласовании	\N	239
606	Отпуск отправлен на согласование	2017-12-01 16:34:29.566	На согласовании	Новый	\N	268
607	Отпуск отправлен на согласование	2017-12-01 16:34:29.567	На согласовании	Новый	\N	269
608	Отпуск отправлен на согласование	2017-12-01 16:34:29.568	На согласовании	Новый	\N	273
609	Отпуск отправлен на согласование	2017-12-01 16:34:29.569	На согласовании	Новый	\N	274
611	Отпуск отправлен на согласование	2017-12-01 16:50:18.785	На согласовании	Новый	\N	164
612	Отпуск отправлен на согласование	2017-12-01 16:50:18.786	На согласовании	Новый	\N	165
615	Сотрудник добавил период отпуска.	2017-12-01 16:54:15.996	Новый	Новый	\N	275
616	Отпуск отправлен на согласование	2017-12-01 16:54:26.229	На согласовании	Новый	\N	275
617	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 16:54:34.096	Частично согласован	На согласовании	\N	173
618	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 16:55:15.099	Частично согласован	На согласовании	\N	175
619	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 16:55:34.536	Частично согласован	На согласовании	\N	174
620	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 16:55:50.359	Частично согласован	На согласовании	\N	177
621	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 16:56:28.834	Частично согласован	На согласовании	\N	146
622	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 16:56:47.785	Частично согласован	На согласовании	\N	163
623	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 16:57:25.658	Частично согласован	На согласовании	\N	162
624	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 16:57:52.686	Частично согласован	На согласовании	\N	159
625	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 16:58:16.745	Частично согласован	На согласовании	\N	153
626	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 16:58:31.231	Частично согласован	На согласовании	\N	157
628	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 17:05:28.145	Частично согласован	На согласовании	\N	267
629	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 17:06:08.465	Частично согласован	На согласовании	\N	264
630	Сотрудник добавил период отпуска.	2017-12-01 17:06:20.603	Новый	Новый	\N	276
631	Отпуск отправлен на согласование	2017-12-01 17:06:27.23	На согласовании	Новый	\N	276
632	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 17:06:28.885	Частично согласован	На согласовании	\N	265
633	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 17:06:59.753	Частично согласован	На согласовании	\N	276
634	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-01 17:07:29.502	Частично согласован	На согласовании	\N	266
635	Андриановская Маргарита Владимировна отклонил Ваш период отпуска	2017-12-01 17:13:35.411	Отклонён	На согласовании	\N	56
636	Сотрудник добавил период отпуска.	2017-12-01 17:14:09.473	Новый	Новый	\N	277
637	Отпуск отправлен на согласование	2017-12-01 17:14:22.359	На согласовании	Новый	\N	277
638	Сотрудник добавил период отпуска.	2017-12-01 17:43:36.49	Новый	Новый	\N	278
639	Отпуск отправлен на согласование	2017-12-01 17:44:13.384	На согласовании	Новый	\N	255
640	Отпуск отправлен на согласование	2017-12-01 17:44:13.386	На согласовании	Новый	\N	278
641	Кошкарёв Максим Игоревич согласовал Ваш период отпуска	2017-12-01 18:03:42.005	Частично согласован	На согласовании	\N	235
642	Кошкарёв Максим Игоревич согласовал Ваш период отпуска	2017-12-01 18:04:20.201	Частично согласован	На согласовании	\N	235
\.


--
-- Data for Name: holiday_period_neg_status; Type: TABLE DATA; Schema: public; Owner: holidays
--

COPY holiday_period_neg_status (hol_period_neg_status_id, created, negotiation_status_type, status_description, status_name, updated) FROM stdin;
1	2017-11-28 20:07:41.531853	NEGOTIATION_STATUS_TYPE_NEW	Период отпуска переводится в данный статус при добавлении отпуска сотрудником. Черновик, который не виден никому из руководителей и виден лишь сотруднику.	Новый	\N
2	2017-11-28 20:07:44.247353	NEGOTIATION_STATUS_TYPE_NEGOTIATING	Период отпуска переводится в данный статус при отправке сотрудником на согласование руководителей. В данном статусе период отпуска уже не может быть удалён сотрудником.	На согласовании	\N
3	2017-11-28 20:07:46.594638	NEGOTIATION_STATUS_TYPE_PARTLY_NEGOTIATED	Частично согласованный период отпуска.	Частично согласован	\N
4	2017-11-28 20:07:49.008832	NEGOTIATION_STATUS_TYPE_REJECTED	Период отпуска, отклонённый руководителями	Отклонён	\N
5	2017-11-28 20:07:50.638173	NEGOTIATION_STATUS_TYPE_OK	Период отпуска, полностью согласованный руководителями сотрудника.	Согласован	\N
\.


--
-- Data for Name: managed_teams; Type: TABLE DATA; Schema: public; Owner: holidays
--

COPY managed_teams (manager_id, team_id) FROM stdin;
117	21
116	21
116	22
115	23
113	21
113	22
118	21
118	22
119	21
120	22
121	22
127	24
128	25
128	24
129	24
127	25
135	25
135	24
136	25
136	24
139	24
140	25
128	7
135	26
141	9
141	7
83	2
72	8
62	6
51	1
41	3
31	5
12	6
12	5
12	8
12	3
10	6
9	6
9	5
9	1
9	8
9	3
9	2
8	8
7	1
6	5
5	3
4	6
4	5
4	8
4	3
4	4
3	6
3	5
3	1
3	8
3	3
3	2
3	7
2	9
2	7
121	7
118	7
118	9
6	2
\.


--
-- Data for Name: project_role; Type: TABLE DATA; Schema: public; Owner: holidays
--

COPY project_role (project_role_id, created, special_type, role_description, role_name, updated) FROM stdin;
1	2017-11-28 19:53:02.456553	PROJECT_ROLE_SPECIAL_TYPE_REGULAR	Обычная	Обычная	\N
2	2017-11-28 19:53:04.347301	PROJECT_ROLE_SPECIAL_TYPE_TEAM_LEAD	Тимлид	Тимлид	\N
3	2017-11-28 19:53:06.564717	PROJECT_ROLE_SPECIAL_TYPE_PROJECT_MANAGER	Руководитель проекта	Руководитель проекта	\N
4	2017-11-28 19:53:08.254622	PROJECT_ROLE_SPECIAL_TYPE_LINE_MANAGER	Линейный руководитель	Линейный руководитель	\N
5	2017-11-28 19:53:09.940665	PROJECT_ROLE_SPECIAL_TYPE_SUPERVISOR	Супервизор	Супервизор	\N
6	2017-11-30 00:07:11.372	PROJECT_ROLE_SPECIAL_TYPE_REGULAR	Разработчик	Разработчик	\N
7	2017-11-30 00:08:07.796	PROJECT_ROLE_SPECIAL_TYPE_REGULAR	Тестировщик	Тестировщик	\N
8	2017-11-30 00:08:19.203	PROJECT_ROLE_SPECIAL_TYPE_REGULAR	Аналитик EPC	Аналитик EPC	\N
9	2017-11-30 00:12:15.863	PROJECT_ROLE_SPECIAL_TYPE_REGULAR	Бизнес-аналитик	Бизнес-аналитик	\N
10	2017-11-30 00:12:24.754	PROJECT_ROLE_SPECIAL_TYPE_REGULAR	Системный аналитик	Системный аналитик	\N
11	2017-11-30 12:28:26.58	PROJECT_ROLE_SPECIAL_TYPE_REGULAR	Специалист по миграции данных	Специалист по миграции данных	\N
12	2017-11-30 12:29:11.121	PROJECT_ROLE_SPECIAL_TYPE_REGULAR	Специалист по бизнес-параметрам	Специалист по бизнес-параметрам	\N
\.


--
-- Data for Name: team; Type: TABLE DATA; Schema: public; Owner: holidays
--

COPY team (team_id, created, team_name, updated, project_manager_id, team_lead_id) FROM stdin;
1	2017-11-28 19:51:50.575358	EPC РТИ	\N	\N	\N
2	2017-11-28 19:51:50.64297	Бизнес-параметры РТИ	\N	\N	\N
3	2017-11-28 19:51:50.652147	Продажи РТИ	\N	\N	\N
4	2017-11-28 19:51:50.660523	Системный анализ	\N	\N	\N
5	2017-11-28 19:51:50.722978	Техподдержка РТИ	\N	\N	\N
6	2017-11-28 19:51:50.758893	Техпродажи РТИ	\N	\N	\N
7	2017-11-28 19:51:50.813759	Тимлиды команд	\N	\N	\N
8	2017-11-28 19:51:50.902298	ТП v2.0 РТИ	\N	\N	\N
9	2017-11-28 19:51:50.959607	Управление	\N	\N	\N
10	2017-11-28 19:51:50.997841	Миграция	\N	\N	\N
11	2017-11-28 19:51:51.020297	Руководство	\N	\N	\N
21	2017-11-28 23:37:30.57	1тест1	\N	\N	\N
22	2017-11-28 23:37:54.346	2тест2	\N	\N	\N
23	2017-11-29 01:19:11.147	ca	\N	\N	\N
24	2017-11-30 00:09:53.001	team1	\N	\N	\N
25	2017-11-30 00:09:58.215	team2	\N	\N	\N
26	2017-11-30 00:33:00.381	sateam	\N	\N	\N
\.


--
-- Name: seq_authority_id; Type: SEQUENCE SET; Schema: public; Owner: holidays
--

SELECT pg_catalog.setval('seq_authority_id', 1, false);


--
-- Name: seq_dep_id; Type: SEQUENCE SET; Schema: public; Owner: holidays
--

SELECT pg_catalog.setval('seq_dep_id', 3, true);


--
-- Name: seq_emp_id; Type: SEQUENCE SET; Schema: public; Owner: holidays
--

SELECT pg_catalog.setval('seq_emp_id', 146, true);


--
-- Name: seq_hol_period_id; Type: SEQUENCE SET; Schema: public; Owner: holidays
--

SELECT pg_catalog.setval('seq_hol_period_id', 278, true);


--
-- Name: seq_hol_period_neg_history_id; Type: SEQUENCE SET; Schema: public; Owner: holidays
--

SELECT pg_catalog.setval('seq_hol_period_neg_history_id', 642, true);


--
-- Name: seq_hol_period_neg_status_id; Type: SEQUENCE SET; Schema: public; Owner: holidays
--

SELECT pg_catalog.setval('seq_hol_period_neg_status_id', 5, true);


--
-- Name: seq_proj_role_id; Type: SEQUENCE SET; Schema: public; Owner: holidays
--

SELECT pg_catalog.setval('seq_proj_role_id', 12, true);


--
-- Name: seq_team_id; Type: SEQUENCE SET; Schema: public; Owner: holidays
--

SELECT pg_catalog.setval('seq_team_id', 26, true);


--
-- Name: authority authority_pkey; Type: CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY authority
    ADD CONSTRAINT authority_pkey PRIMARY KEY (authority_id);


--
-- Name: department department_pkey; Type: CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY department
    ADD CONSTRAINT department_pkey PRIMARY KEY (dep_id);


--
-- Name: employee_authorities employee_authorities_pkey; Type: CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY employee_authorities
    ADD CONSTRAINT employee_authorities_pkey PRIMARY KEY (employee_emp_id, authorities_authority_id);


--
-- Name: employee employee_pkey; Type: CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (emp_id);


--
-- Name: holiday_period_neg_history holiday_period_neg_history_pkey; Type: CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY holiday_period_neg_history
    ADD CONSTRAINT holiday_period_neg_history_pkey PRIMARY KEY (hol_period_neg_history_id);


--
-- Name: holiday_period_neg_status holiday_period_neg_status_pkey; Type: CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY holiday_period_neg_status
    ADD CONSTRAINT holiday_period_neg_status_pkey PRIMARY KEY (hol_period_neg_status_id);


--
-- Name: holiday_period holiday_period_pkey; Type: CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY holiday_period
    ADD CONSTRAINT holiday_period_pkey PRIMARY KEY (hol_period_id);


--
-- Name: managed_teams managed_teams_pkey; Type: CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY managed_teams
    ADD CONSTRAINT managed_teams_pkey PRIMARY KEY (manager_id, team_id);


--
-- Name: project_role project_role_pkey; Type: CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY project_role
    ADD CONSTRAINT project_role_pkey PRIMARY KEY (project_role_id);


--
-- Name: team team_pkey; Type: CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY team
    ADD CONSTRAINT team_pkey PRIMARY KEY (team_id);


--
-- Name: employee_authorities fk1w7droufxb770uqilpn10icef; Type: FK CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY employee_authorities
    ADD CONSTRAINT fk1w7droufxb770uqilpn10icef FOREIGN KEY (authorities_authority_id) REFERENCES authority(authority_id);


--
-- Name: managed_teams fk3t3a8elglip2sgu8g1cf898xe; Type: FK CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY managed_teams
    ADD CONSTRAINT fk3t3a8elglip2sgu8g1cf898xe FOREIGN KEY (team_id) REFERENCES team(team_id);


--
-- Name: team fk5fif8wcogckhf03r3gevv3vu1; Type: FK CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY team
    ADD CONSTRAINT fk5fif8wcogckhf03r3gevv3vu1 FOREIGN KEY (project_manager_id) REFERENCES employee(emp_id);


--
-- Name: employee fk8d7lrsr6kwirr93rx0tafnoqa; Type: FK CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY employee
    ADD CONSTRAINT fk8d7lrsr6kwirr93rx0tafnoqa FOREIGN KEY (team_id) REFERENCES team(team_id);


--
-- Name: team fk8xejugtavdvxommm82txxsam2; Type: FK CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY team
    ADD CONSTRAINT fk8xejugtavdvxommm82txxsam2 FOREIGN KEY (team_lead_id) REFERENCES employee(emp_id);


--
-- Name: employee_authorities fk9gwl8kmc5djfoy35yljn8po4b; Type: FK CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY employee_authorities
    ADD CONSTRAINT fk9gwl8kmc5djfoy35yljn8po4b FOREIGN KEY (employee_emp_id) REFERENCES employee(emp_id);


--
-- Name: employee fkbejtwvg9bxus2mffsm3swj3u9; Type: FK CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY employee
    ADD CONSTRAINT fkbejtwvg9bxus2mffsm3swj3u9 FOREIGN KEY (department_id) REFERENCES department(dep_id);


--
-- Name: holiday_period fke2e5lyw8ul49bm2iork1y1pg4; Type: FK CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY holiday_period
    ADD CONSTRAINT fke2e5lyw8ul49bm2iork1y1pg4 FOREIGN KEY (hp_negotiation_status_id) REFERENCES holiday_period_neg_status(hol_period_neg_status_id);


--
-- Name: holiday_period fkfdi78p9y8nunvlavwukl9s2o2; Type: FK CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY holiday_period
    ADD CONSTRAINT fkfdi78p9y8nunvlavwukl9s2o2 FOREIGN KEY (emp_id) REFERENCES employee(emp_id);


--
-- Name: employee fkk9pmkq2reu5b07hqs9x55y1mv; Type: FK CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY employee
    ADD CONSTRAINT fkk9pmkq2reu5b07hqs9x55y1mv FOREIGN KEY (project_role_id) REFERENCES project_role(project_role_id);


--
-- Name: managed_teams fkkus0xo9ru94udlndb7flwbo5b; Type: FK CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY managed_teams
    ADD CONSTRAINT fkkus0xo9ru94udlndb7flwbo5b FOREIGN KEY (manager_id) REFERENCES employee(emp_id);


--
-- Name: holiday_period_neg_history fkkw537hsp3qeyg7iaukkkemb45; Type: FK CONSTRAINT; Schema: public; Owner: holidays
--

ALTER TABLE ONLY holiday_period_neg_history
    ADD CONSTRAINT fkkw537hsp3qeyg7iaukkkemb45 FOREIGN KEY (hol_period_id) REFERENCES holiday_period(hol_period_id);


--
-- PostgreSQL database dump complete
--

