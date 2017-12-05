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
1	2017-11-28 19:54:00.052331	mabramkin@gmail.com	admin	admin	admin	admin	$2a$10$qB6GAVVVP4Agg.iyS2qsj.CNLjjiNIMxyT6SyI24FKiziq1gputNK	2017-11-30 15:57:20.186	\N	\N	\N	
147	2017-12-04 08:59:41.928	Maksim.Abramkin@RT.RU	Максим	Абрамкин	mabramkin	Андреевич	$2a$10$9jbuV.OdYEwtGWDTYgIGs.sJ8ydtQYLya0Rcjpn4OhPx/yQhabRyC	2017-12-04 09:00:14.554	2	4	9	DevManager
2	2017-11-28 19:54:00.097936	Elena.Koroleva2@RT.RU	Елена	Королёва	Elena.Koroleva2	Ильинична	$2a$10$8VpkMYACVSv9Eh5zyDbt3uRC4FveXGsDbqGoCdIM/oxiKk2wdcqQK	2017-12-04 16:06:22.319	1	5	11	
141	2017-11-30 03:16:46.171	deptmgr	deptmgr	deptmgr	deptmgr	deptmgr	$2a$10$IAtL23glW7dqQ9SB0.prdOGJiSHn6K0ecfnkIqhzGEeZKpTQkqb5K	\N	1	5	11	
148	2017-12-04 15:29:36.261	Aleksey.Sangadzhiev@rt.ru	Алексей	Сангаджиев	san	Викторович	$2a$10$oucPgyQs9C/BLTzLybg23ek.GyO5lQkwWuAraoPy79ukTBtf4j3Se	2017-12-04 15:39:03.613	2	4	27	
59	2017-11-28 19:54:02.227571	Dmitriy.Larin@RT.RU	Дмитрий	Ларин	Dmitriy.Larin	Калимуллович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:33:29.493	2	7	1	
142	2017-11-30 12:17:35.717	Aleksey.Mischenko@rt.ru	Алексей	Мищенко	Aleksey.Mischenko	Сергеевич	$2a$10$zrsugXa6lGkf0bdxEpi.qeBsFaG/vX8AJyOul6NgY6Ap/2R52zbWu	2017-11-30 17:49:24.215	3	9	6	
64	2017-11-28 19:54:02.362906	Nikolay.Odinokov@RT.RU	Николай	Одиноков	Nikolay.Odinokov	Николаевич	$2a$10$.60ochT5j/GQQ7FXuhPOJejjNt/dhX6GlqbphB7vROWfBzmNV2SPG	2017-12-01 11:46:00.697	2	6	6	
53	2017-11-28 19:54:01.92725	Andrey.Churin@RT.RU	Андрей	Чурин	Andrey.Churin	Васильевич	$2a$10$E.TRN3eOk6TDA91W0AYoguzwA2TY5len..gEgpWkMdeUcgh48F2Ki	2017-12-01 12:30:42.673	2	6	1	
69	2017-11-28 19:54:02.618525	Evgeniy.Samokhin@RT.RU	Евгений	Самохин	Evgeniy.Samokhin	Викторович	$2a$10$M9oXRnW27zDc5Het5vCEPOd728dNHfmbjKzpYnFHKfDe1ajsxmEMG	2017-12-04 12:29:27.22	2	7	6	
86	2017-11-28 19:54:03.362072	Aleksandr.Bekichev@RT.RU	Александр	Бекичев	Aleksandr.Bekichev	Вадимович	$2a$10$/ORZrGu0.cOjwjvVodx84Ozea1GYk11JPjgB8PiNo2OAI0B5bM8X2	2017-12-04 13:06:27.411	2	7	2	
87	2017-11-28 19:54:03.397477	Natalya.Usmanova@RT.RU	Наталья	Усманова	Natalya.Usmanova	Гафуровна	$2a$10$RKqPzbBGVa34/sqlz1QXIO1LDeuvZoskMCqOmYX8dGCMWzux31HhW	2017-12-04 15:54:27.192	2	11	10	
6	2017-11-28 19:54:00.292211	Denis.Volynin@RT.RU	Денис	Волынин	Denis.Volynin	Николаевич	$2a$10$AgbE8eahD5JW1ilSrX.kh.yG2IkXhthZS3ZmB9umiwT8fQHc62LYK	2017-12-04 17:48:47.501	1	3	9	
14	2017-11-28 19:54:00.640132	Fedor.Bryukhovetskiy@RT.RU	Федор	Брюховецкий	Fedor.Bryukhovetskiy	Михайлович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:19:47.633	3	10	4	
18	2017-11-28 19:54:00.746788	Vladimir.Mazin@RT.RU	Владимир	Мазин	Vladimir.Mazin	Владимирович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-12-01 13:12:45.03	3	9	6	
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
17	2017-11-28 19:54:00.729664	Natalya.S.Kovaleva@RT.RU	Наталья	Ковалева	Natalya.S.Kovaleva	Сергеевна	$2a$10$zjRdEz6VW5shkLUgu5oJ1OoHKKlD4GmRNUEMwbrJIK2zkft2KNYhi	2017-12-04 10:16:47.188	3	10	4	
11	2017-11-28 19:54:00.568299	Kseniya.Pankratova@RT.RU	Ксения	Панкратова	Kseniya.Pankratova	Игоревна	$2a$10$CcrXoiaDz9BAeIQKekR1m.nEqaWLDcHZRlbpiAJowFK5eqRRSD.BC	2017-12-01 10:02:09.276	1	3	9	\N
23	2017-11-28 19:54:00.941238	Dmitriy.Ushakov@RT.RU	Дмитрий	Ушаков	Dmitriy.Ushakov	Александрович	$2a$10$1dmvAMd/y0oaHZsojrH5/u3Dt6zMhxU7RZKd9H2oOPM8N9DtfV7/O	2017-12-01 14:41:55.788	3	9	5	
121	2017-11-29 03:03:12.974	ivan.artemev@rt.ru	2	pp	pp2	2	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 14:48:37.197	1	3	9	
8	2017-11-28 19:54:00.397615	Yuriy.Lukashov@RT.RU	Юрий	Лукашов	Yuriy.Lukashov	Александрович	$2a$10$.57vUKlUK71ZRIBgdLnnkemb8GOcEATkx3ZAPH8.VOARrn0McIxYC	2017-12-04 12:15:53.466	1	3	9	
12	2017-11-28 19:54:00.610924	Margarita.Popova@RT.RU	Маргарита	Андриановская	Margarita.Popova	Владимировна	$2a$10$EsOUYz8hS.yXR1/HxvUhi.Krwg1S/ml4/OYIng07dmhdFukOrVHGC	2017-11-30 16:46:50.225	3	4	9	BAManager
7	2017-11-28 19:54:00.301166	Vadim.Kashin@RT.RU	Вадим	Кашин	Vadim.Kashin	Васильевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:48:21.29	1	3	9	
3	2017-11-28 19:54:00.152237	Maksim.Abramkin@RT.RU	Максим	Абрамкин	Maksim.Abramkin	Андреевич	$2a$10$CbccY5DHlyRCSm10I8yPBu3AVcaFugWgRJJby6cvATUeEzD49dseW	2017-12-01 10:31:53.779	2	5	9	DevManager
5	2017-11-28 19:54:00.282648	Regina.Davytova@RT.RU	Регина	Давытова	Regina.Davytova	Алмазовна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:48:40.762	1	3	9	
21	2017-11-28 19:54:00.837083	Anastasiya.Roor@RT.RU	Анастасия	Роор	Anastasiya.Roor	Викторовна	$2a$10$eV1qGwD31MQs4kr9gSlymu9kAf9Z3cf1N68lNg0vPyJC0BiH2RnoC	2017-12-01 12:34:53.413	3	9	3	
13	2017-11-28 19:54:00.631156	Ekaterina.Belkova@RT.RU	Екатерина	Белькова	Ekaterina.Belkova	Сергеевна	$2a$10$gBhzmSK0ugimpuk8PHNuteVdI1dhMHuuaXKlzucrf5BOB2R.DoikW	2017-12-04 12:17:36.191	3	10	4	
4	2017-11-28 19:54:00.215123	Farkhad.G.Tokhtamov@RT.RU	Фархад	Тохтамов	Farkhad.G.Tokhtamov	Гапарович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 13:05:12.863	3	5	9	B2CManager
19	2017-11-28 19:54:00.764075	Kristina.K.Potapova@RT.RU	Кристина	Потапова	Kristina.K.Potapova	Хикматовна	$2a$10$hoQPfpaZtQ1SM4CnKCpPLu9Qnrxr3YInIPTuqGukI57eqMduTU1fu	2017-11-30 17:47:02.29	3	9	8	
9	2017-11-28 19:54:00.454912	Ivan.Artemev@RT.RU	Иван	Артемьев	Ivan.Artemev	Сергеевич	$2a$10$eThhhTy3wP2EweHoJbEdZOU14QzkM226YlYqKpWxgLzInS9YK2XsK	2017-12-01 10:32:29.774	2	4	9	TestManager
25	2017-11-28 19:54:00.985411	Aleksandra.Yashukova@RT.RU	Александра	Яшукова	Aleksandra.Yashukova	Дмитриевна	$2a$10$ttiq6W1MArizql9F/xuL1ubcP6N5tg1Xa7eP5amNXtDGEYiFvFrbK	2017-12-01 14:53:27.534	3	9	8	
39	2017-11-28 19:54:01.401052	Anastasiya.Konopleva@RT.RU	Анастасия	Коноплева	Anastasiya.Konopleva	Сергеевна	$2a$10$DFcSOSARqHJvW6fuQpCiVehPjgECAsp8KalX/bOqZqUWL.8DECztm	2017-12-01 14:21:29.609	2	7	5	
55	2017-11-28 19:54:01.981138	Tatyana.Garyaeva@RT.RU	Татьяна	Гаряева	Tatyana.Garyaeva	Ивановна	$2a$10$/hUWi2uOildEQEaygJshb.hNkG3qS0vVjhkffGAhiztSBIQriNTsO	2017-12-01 13:59:21.083	2	8	1	
54	2017-11-28 19:54:01.961407	Olga.Krayushkina@RT.RU	Ольга	Краюшкина	Olga.Krayushkina	Дмитриевна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:34:05.636	2	8	1	
52	2017-11-28 19:54:01.872385	Elena.S.Artemeva@RT.RU	Елена	Артемьева	Elena.S.Artemeva	Сергеевна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:34:23.675	2	7	1	
58	2017-11-28 19:54:02.129721	Svetlana.M.Dmitrieva@RT.RU	Светлана	Дмитриева	Svetlana.M.Dmitrieva	Михайловна	$2a$10$pmAsSKhUrTdEt/iyk/YdeuLBI0kuBI.YcOQJ9p15zf9MkmxhZyo66	2017-12-04 11:38:11.15	2	8	1	
50	2017-11-28 19:54:01.844066	Aleksandr.Pavlov@RT.RU	Александр	Павлов	Aleksandr.Pavlov	Дмитриевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:34:39.789	2	6	3	
49	2017-11-28 19:54:01.821689	Vasiliy.Efremov@RT.RU	Василий	Ефремов	Vasiliy.Efremov	Михайлович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:34:46.545	2	6	3	
48	2017-11-28 19:54:01.771544	Stanislav.Kuznets@RT.RU	Станислав	Кузнец	Stanislav.Kuznets	Юрьевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:34:53.036	2	6	3	
47	2017-11-28 19:54:01.750276	Andrey.Fomichev@RT.RU	Андрей	Фомичев	Andrey.Fomichev	Игоревич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:34:58.741	2	6	3	
46	2017-11-28 19:54:01.742968	Nikolay.Lobachev@RT.RU	Николай	Лобачев	Nikolay.Lobachev	Александрович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:35:04.884	2	6	3	
41	2017-11-28 19:54:01.486969	Anastasiya.Egorova@RT.RU	Анастасия	Егорова	Anastasiya.Egorova	Юрьевна	$2a$10$aaIh4AmGGJRmVXD1b.9NJeFlvwKem717nA/w8vm68IWegIKnutmSq	2017-12-04 12:24:56.825	2	2	7	
44	2017-11-28 19:54:01.595992	Ivan.Samarskiy@RT.RU	Иван	Самарский	Ivan.Samarskiy	Николаевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:35:15.201	2	7	3	
43	2017-11-28 19:54:01.577537	Petr.A.Kononov@RT.RU	Петр	Кононов	Petr.A.Kononov	Александрович	$2a$10$fxMgeDltdng3VwFGnMqrreJuFyrnKXY8A3WA1qgq0hNhoodz22KmG	2017-12-04 12:21:26.819	2	7	3	
45	2017-11-28 19:54:01.686804	Elisey.Novikov@RT.RU	Елисей	Новиков	Elisey.Novikov	Игоревич	$2a$10$6ukBxwJ8d3yKRsgvuYEQJ.3BzEja4hCZ9aEyHvc0QJKJPbHq90ZeO	2017-12-04 12:23:53.294	2	6	3	
40	2017-11-28 19:54:01.468496	Ivan.Lubnin@RT.RU	Иван	Лубнин	Ivan.Lubnin	Игоревич	$2a$10$sxbDHGtopTL41dAaFK1zHelhT1xLk5axJMkLRM6dw675qaJlziXJm	2017-12-01 02:16:08.571	2	7	5	
30	2017-11-28 19:54:01.118826	Stanislav.Nastaschuk@RT.RU	Станислав	Настащук	Stanislav.Nastaschuk	Дмитриевич	$2a$10$IJtleHEi8xJ6SNOYdlNkiuTXBkqpVGtiHejDWcdpw2.6e8tvosYtW	2017-12-04 12:38:19.374	3	10	4	
32	2017-11-28 19:54:01.174414	Aleksey.Starikov@RT.RU	Алексей	Стариков	Aleksey.Starikov	Евгеньевич	$2a$10$998K8nvm2kmTYLYV0mrtYuAkK4BxNsQqN1UGeJqFaxr6qRilvny7.	2017-12-01 11:40:59.472	2	6	5	
37	2017-11-28 19:54:01.358888	Aleksey.Elnikov@RT.RU	Алексей	Ельников	Aleksey.Elnikov	Сергеевич	$2a$10$XKUqWs/EVHvYzGnBWZ0eJuEum7v5nkxIR03.erqdTJo5EMvyd5aC.	2017-12-01 09:26:41.331	2	6	5	
51	2017-11-28 19:54:01.853657	Igor.Komarov@RT.RU	Игорь	Комаров	Igor.Komarov	Вячеславович	$2a$10$5fPxCgjQTxHJuayop3c8buy.WrwyZmAdYQPqvQbi//GOhFbwiscNy	2017-12-01 14:32:29.409	2	2	7	
36	2017-11-28 19:54:01.352628	Robert.Mirzakhanyan@RT.RU	Роберт	Мирзаханян	Robert.Mirzakhanyan	Масисович	$2a$10$UAS9TbVRTnSUCZ.MlO4JYezJ8iz0Pn6eYr.ZL5IsKbM3m6Z161B42	2017-12-04 12:26:39.889	2	6	5	
34	2017-11-28 19:54:01.262108	Elizaveta.Sukhanova@RT.RU	Елизавета	Суханова	Elizaveta.Sukhanova	Александровна	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:36:10.879	2	6	5	
33	2017-11-28 19:54:01.191581	Sergey.I.Shevtsov@RT.RU	Сергей	Шевцов	Sergey.I.Shevtsov	Иванович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:36:16.293	2	6	5	
56	2017-11-28 19:54:02.053989	Lyudmila.Dmitrieva@RT.RU	Людмила	Дмитриева	Lyudmila.Dmitrieva	Владимировна	$2a$10$iXL55NB.n0TPYwAaAHhwYOhqjcZgz4MVtLHNE/BfYbty9RSxPfI02	2017-12-01 12:12:12.873	2	8	1	
28	2017-11-28 19:54:01.047674	Arusyak.Ambaryan@RT.RU	Арусяк	Амбарян	Arusyak.Ambaryan	Сагателовна	$2a$10$mQ3qlBAvDUkfLNIJmZ1unuyNNNhYBiBcfsDvA95f7JR9mUI5ag/9S	2017-12-04 12:42:33.178	3	10	4	
38	2017-11-28 19:54:01.389788	Polina.Churbanova@RT.RU	Полина	Чурбанова	Polina.Churbanova	Сергеевна	$2a$10$muOLgc6e8rWgAfIwQNlotuhOq8MAH.Hq7fcLfEBFVzz4N5Pp0.hz.	2017-11-30 18:10:29.377	2	7	5	
57	2017-11-28 19:54:02.072491	Andrey.Kuzmin@RT.RU	Андрей	Кузьмин	Andrey.Kuzmin	Юрьевич	$2a$10$8ld7lut0AJTHnuEjKIbrJe6AoS5Ga116r4rnuCz5sVdKzyga.mBI2	2017-12-04 12:58:00.941	2	7	1	
27	2017-11-28 19:54:01.04031	Anastasiya.Y.Karpova@RT.RU	Анастасия	Карпова	Anastasiya.Y.Karpova	Юрьевна	$2a$10$JTOl/.h5Vj3/K4h7.LIz9ugQnV4CMryXXa1xRQc91lhTC2amSoe1W	2017-12-04 09:18:40.081	3	10	4	
26	2017-11-28 19:54:01.032005	Evgeniy.Monakov@RT.RU	Евгений	Монаков	Evgeniy.Monakov	Александрович	$2a$10$GWzT07DlCBct89rbEQzTDuLI1Qrd7UR3ZbNbRw2a9EcLuEjun2/7W	2017-12-01 09:54:28.714	3	9	6	
42	2017-11-28 19:54:01.505129	Kirill.Velichko@RT.RU	Кирилл	Величко	Kirill.Velichko	Сергеевич	$2a$10$dcs2HfWZcS877B3D864Py.20ePvQDFoSfbRNpOFdDN9bQxf6ND8v2	2017-12-04 12:23:47.42	2	7	3	
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
81	2017-11-28 19:54:03.173585	Shamil.Rakipov@RT.RU	Шамиль	Ракипов	Shamil.Rakipov	Фатыхович	$2a$10$8KTjE2wpTagc8QmC4gWs1OPKtezlBzRuCBePS5jI.ZVo.H/r4F/TG	2017-12-04 12:26:58.087	2	7	8	
84	2017-11-28 19:54:03.253004	Evgeniya.Volkova@RT.RU	Евгения	Волкова	Evgeniya.Volkova	Алексеевна	$2a$10$tGtAkWuBdMqZtvIxNqi3s.KPa362yz.0iWaQGCwd36vsCkRnlBRJO	2017-12-01 09:30:26.111	2	12	2	
76	2017-11-28 19:54:02.924957	Viktor.Romanov@RT.RU	Виктор	Романов	Viktor.Romanov	Викторович	$2a$10$txI/NUeb3G7AiShwz28WG.d3up.pv5CJqahFHyRJwenageCZPSn6m	2017-12-01 12:07:12.124	2	6	8	
82	2017-11-28 19:54:03.217139	Pavel.Kulikovskiy@RT.RU	Павел	Куликовский	Pavel.Kulikovskiy	Николаевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:30:16.391	2	6	8	
62	2017-11-28 19:54:02.323888	Dmitriy.Orlov@RT.RU	Дмитрий	Орлов	Dmitriy.Orlov	Евгеньевич	$2a$10$LnSfnAUTqiWyq6oYngAX0OuuTtJTGNb5DAPyt448g7SFrp8zC57N.	2017-12-04 12:33:13.539	2	2	7	
80	2017-11-28 19:54:03.099889	Dmitriy.Vakhromeev@RT.RU	Дмитрий	Вахромеев	Dmitriy.Vakhromeev	Игоревич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:30:29.619	2	7	8	
77	2017-11-28 19:54:02.998714	Aleksandr.Kolokolov@RT.RU	Александр	Колоколов	Aleksandr.Kolokolov	Андреевич	$2a$10$63Ibt9GxqBK6n5MiSKXnbei4OELlXwACv2W2iUBMc4wxD02T9JW5m	2017-12-04 13:17:51.468	2	6	8	
67	2017-11-28 19:54:02.477267	A.E.Trofimov@RT.RU	Андрей	Трофимов	A.E.Trofimov	Евгеньевич	$2a$10$Wzj7dYIEGRyoU4g9tVIuIesfVU9fvKU.UHsWnJCswb6wqw9gdEGHe	2017-12-01 09:25:35.446	2	6	6	
71	2017-11-28 19:54:02.797377	Igor.Minenko@RT.RU	Игорь	Миненко	Igor.Minenko	Владимирович	$2a$10$CwaSbopyCfaX62ih2FOrMuO6NeFGVqx2RLQbAmthjhoX5kdvQQ3mu	2017-12-01 12:14:12.464	2	7	6	
75	2017-11-28 19:54:02.889685	Ayvar.Nadeev@RT.RU	Айвар	Надеев	Ayvar.Nadeev	Адельевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:31:21.096	2	6	8	
78	2017-11-28 19:54:03.016995	Vladimir.Buravtsov@RT.RU	Владимир	Буравцов	Vladimir.Buravtsov	Сергеевич	$2a$10$Y2wEVIgqvdvwgp/OJ0r.Z.aVVGHkVs4cYFosfOYlElZJCxWlRHHQu	2017-11-30 20:06:15.723	2	7	8	
73	2017-11-28 19:54:02.815218	Dmitriy.Kosenko@RT.RU	Дмитрий	Косенко	Dmitriy.Kosenko	Владимирович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:31:32.007	2	6	8	
72	2017-11-28 19:54:02.805279	Vyacheslav.Kuleshov@RT.RU	Вячеслав	Кулешов	Vyacheslav.Kuleshov	Витальевич	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:31:38.432	2	2	7	
79	2017-11-28 19:54:03.071952	Nikolay.Aleksandrov@RT.RU	Николай	Александров	Nikolay.Aleksandrov	Владимирович	$2a$10$fdLfJxwfdLvHLkZgwcQFZuz3ROw7sVUCBFXGnDwHS3QovHDGCgr0i	2017-12-04 12:45:02.821	2	7	8	
83	2017-11-28 19:54:03.234993	Sergey.Vidavskiy@RT.RU	Сергей	Видавский	Sergey.Vidavskiy	Алексеевич	$2a$10$wx5nJXP5AugrlOivu8w7h.XCFHnS62Uqre409wJpYvC3/n.3q4k3O	2017-12-01 10:12:09.614	2	2	7	
65	2017-11-28 19:54:02.417909	Igor.N.Seleznev@RT.RU	Игорь	Селезнев	Igor.N.Seleznev	Николаевич	$2a$10$vHrhHVJKOFmYYvs1SUkzoOkpANqLYdyLFy2Orb6q8KTbt9f3Ty6oi	2017-11-30 19:16:06.75	2	6	6	
70	2017-11-28 19:54:02.75251	Ilya.Kryatov@RT.RU	Илья	Крятов	Ilya.Kryatov	Юрьевич	$2a$10$8Mta.RuCu1dXao4G0B8Utui5ESOaFbULh1.CrRvbyiP8fEDDEj.be	2017-12-01 10:01:41.407	2	7	6	
66	2017-11-28 19:54:02.458696	Vadim.Kurbatov@RT.RU	Вадим	Курбатов	Vadim.Kurbatov	Вячеславович	$2a$10$Y3rnhDtTCSsADDxyDACRW.uAt6Ipuv9V0vsKDfumsyb5CAPaqkzi2	2017-11-30 12:32:13.026	2	6	6	
63	2017-11-28 19:54:02.345711	Sofya.Goryunova@RT.RU	Софья	Горюнова	Sofya.Goryunova	Александровна	$2a$10$86Hzmw318Yfr0dEktcIn5O5rUeq4uLl92qhmShrCmWoNxQDq2CAD.	2017-11-30 19:33:58.503	2	6	6	
74	2017-11-28 19:54:02.834175	Ekaterina.Zvereva@RT.RU	Екатерина	Зверева	Ekaterina.Zvereva	Владимировна	$2a$10$gMLAds9HZ2OBCnkrgruAZeUeY8733jte7btb6PZYFRRYGWyr9jF4q	2017-11-30 19:38:14.307	2	6	8	
85	2017-11-28 19:54:03.325919	Nikolay.Naybauer@RT.RU	Николай	Найбауер	Nikolay.Naybauer	Юрьевич	$2a$10$vlsGRkRLbsRoxR/uv8ok6e3EQ3KBiaxOzkNpeHEWQZTXK4rJ6cgh6	2017-12-01 15:03:26.102	2	12	2	
61	2017-11-28 19:54:02.263595	Nikolaj.Epifanov@RT.RU	Николай	Епифанов	Nikolaj.Epifanov	Михайлович	$2a$10$6Kk2Mohtxaa.LJpeQeeWzOmwXlGiD75ygh62GwyuQabGLJNSGp9Fm	2017-12-04 13:36:26.085	2	6	1	
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
424	2017-12-04 14:54:30.684	2018-11-06 00:00:00	\N	4	2017-12-04 14:56:08.711	14	2
421	2017-12-04 14:52:53.299	2018-03-05 00:00:00	\N	3	2017-12-04 14:56:08.902	14	2
253	2017-12-01 15:38:24.195	2018-05-14 00:00:00	\N	14	\N	20	1
254	2017-12-01 15:39:58.193	2018-06-13 00:00:00	\N	3	\N	20	1
59	2017-11-30 18:29:29.237	2018-05-03 00:00:00	7	2	2017-12-04 17:58:43.681	6	5
256	2017-12-01 15:41:50.874	2018-03-05 00:00:00	\N	3	\N	20	1
257	2017-12-01 15:43:50	2018-08-30 00:00:00	\N	14	\N	20	1
95	2017-12-01 08:42:33.872	2018-06-13 00:00:00	7	5	2017-12-04 17:58:43.681	6	5
96	2017-12-01 08:43:06.401	2018-07-02 00:00:00	7	2	2017-12-04 17:58:43.681	6	5
94	2017-12-01 08:38:44.781	2018-07-06 00:00:00	7	1	2017-12-04 17:58:43.681	6	5
113	2017-12-01 09:54:07.438	2018-04-02 00:00:00	2	14	2017-12-04 18:01:09.666	31	3
114	2017-12-01 09:54:44.205	2018-07-02 00:00:00	2	14	2017-12-04 18:01:09.749	31	3
123	2017-12-01 10:15:44.641	2018-08-18 00:00:00	7	14	2017-12-04 18:01:12.473	11	5
128	2017-12-01 10:39:38.854	2018-11-06 00:00:00	2	4	2017-12-04 19:43:29.106	83	3
458	2017-12-04 19:15:44.104	2018-08-08 00:00:00	\N	23	2017-12-04 19:15:52.168	79	2
449	2017-12-04 17:41:17.802	2018-04-01 00:00:00	5	18	2017-12-04 19:43:27.065	15	3
124	2017-12-01 10:38:14.222	2018-05-03 00:00:00	2	2	2017-12-04 19:43:28.615	83	3
74	2017-11-30 19:33:57.822	2018-08-27 00:00:00	2	5	2017-12-04 19:28:49.802	34	3
425	2017-12-04 14:54:45.284	2018-12-25 00:00:00	\N	5	2017-12-04 14:56:08.605	14	2
423	2017-12-04 14:54:01.509	2018-06-25 00:00:00	\N	14	2017-12-04 14:56:08.804	14	2
422	2017-12-04 14:53:33.86	2018-05-03 00:00:00	\N	2	2017-12-04 14:56:08.991	14	2
72	2017-11-30 19:32:58.615	2018-03-01 00:00:00	2	2	2017-12-04 19:23:13	34	3
102	2017-12-01 09:27:28.276	2018-08-20 00:00:00	2	14	2017-12-04 19:28:49.93	37	3
106	2017-12-01 09:31:38.112	2018-08-06 00:00:00	2	14	2017-12-04 19:43:01.457	84	3
104	2017-12-01 09:31:08.468	2018-03-05 00:00:00	2	3	2017-12-04 19:43:01.578	84	3
103	2017-12-01 09:30:59.448	2018-02-20 00:00:00	2	3	2017-12-04 19:43:01.715	84	3
295	2017-12-04 12:21:17.824	2018-01-09 00:00:00	\N	4	2017-12-04 14:11:53.499	13	2
129	2017-12-01 10:39:54.728	2018-12-19 00:00:00	2	3	2017-12-04 19:43:29.234	83	3
125	2017-12-01 10:38:28.001	2018-05-07 00:00:00	2	2	2017-12-04 19:43:28.732	83	3
350	2017-12-04 13:06:36.023	2018-03-05 00:00:00	1	3	2017-12-04 19:43:29.234	43	3
86	2017-12-01 02:10:47.807	2018-02-26 00:00:00	4	5	2017-12-04 19:43:29.234	40	3
258	2017-12-01 16:04:54.833	2018-05-03 00:00:00	4	2	2017-12-04 19:43:29.234	145	3
450	2017-12-04 17:45:04.589	2018-08-27 00:00:00	4	14	2017-12-04 19:43:29.234	144	3
101	2017-12-01 09:27:13.14	2018-07-02 00:00:00	2	14	2017-12-04 19:23:13.739	37	3
90	2017-12-01 02:12:55.571	2018-11-06 00:00:00	4	4	2017-12-04 19:43:29.234	40	3
135	2017-12-01 10:54:32.105	2018-05-14 00:00:00	\N	12	2017-12-04 19:46:04.076	35	2
136	2017-12-01 10:55:13.112	2018-09-26 00:00:00	\N	15	2017-12-04 19:46:04.142	35	2
56	2017-11-30 18:00:07.368	2018-10-25 00:00:00	0	7	2017-12-04 07:47:38.538	19	4
126	2017-12-01 10:38:42.44	2018-06-13 00:00:00	2	3	2017-12-04 19:43:28.882	83	3
71	2017-11-30 19:32:13.175	2018-12-13 00:00:00	2	2	2017-12-04 19:23:14.494	34	3
294	2017-12-04 12:15:18.491	2018-06-04 00:00:00	7	8	2017-12-04 17:40:16.296	8	5
127	2017-12-01 10:38:55.999	2018-07-23 00:00:00	2	14	2017-12-04 19:43:28.994	83	3
116	2017-12-01 09:58:13.445	2018-11-01 00:00:00	5	2	2017-12-04 19:50:55.391	16	3
51	2017-11-30 17:54:48.775	2018-09-03 00:00:00	5	5	2017-12-04 19:50:55.391	142	3
134	2017-12-01 10:54:15.297	2018-04-21 00:00:00	1	5	2017-12-04 19:51:55.486	35	3
91	2017-12-01 08:30:00.027	2018-11-06 00:00:00	\N	4	2017-12-01 08:43:48.335	6	2
146	2017-12-01 11:45:53.791	2018-03-05 00:00:00	5	3	2017-12-04 07:07:30.715	24	3
118	2017-12-01 10:07:19.465	2018-04-23 00:00:00	\N	6	2017-12-01 10:11:06.227	12	2
143	2017-12-01 11:42:40.586	2018-08-20 00:00:00	\N	14	\N	33	1
237	2017-12-01 15:08:00.803	2018-10-02 00:00:00	5	11	2017-12-04 19:50:55.391	39	3
231	2017-12-01 15:04:15.558	2018-07-01 00:00:00	1	14	2017-12-04 19:50:55.391	85	3
261	2017-12-01 16:06:09.181	2018-05-14 00:00:00	4	7	2017-12-04 19:43:29.235	144	3
107	2017-12-01 09:31:50.961	2018-09-10 00:00:00	2	4	2017-12-04 19:43:01.341	84	3
145	2017-12-01 11:45:45.318	2018-02-19 00:00:00	3	4	2017-12-04 19:50:55.392	15	3
85	2017-11-30 20:48:21.038	2018-12-29 00:00:00	2	1	2017-12-04 19:50:55.392	78	3
278	2017-12-01 17:43:36.486	2018-09-10 00:00:00	2	14	2017-12-04 19:50:55.392	80	3
191	2017-12-01 13:58:59.48	2018-06-04 00:00:00	2	14	2017-12-04 19:23:13.579	32	3
431	2017-12-04 15:46:30.802	2018-07-09 00:00:00	4	17	2017-12-04 19:43:29.235	40	3
157	2017-12-01 11:49:32.516	2018-10-01 00:00:00	5	7	2017-12-04 07:07:30.974	24	3
296	2017-12-04 12:26:08.027	2018-07-09 00:00:00	\N	14	2017-12-04 12:38:05.173	30	2
163	2017-12-01 12:02:21.217	2018-08-20 00:00:00	5	14	2017-12-04 07:07:31.061	24	3
75	2017-11-30 19:38:49.436	2018-07-02 00:00:00	2	14	2017-12-04 19:50:55.392	74	3
161	2017-12-01 11:52:22.172	2018-06-13 00:00:00	\N	14	\N	33	1
162	2017-12-01 11:52:35.623	2018-12-27 00:00:00	5	3	2017-12-04 07:07:31.146	24	3
210	2017-12-01 14:35:50.824	2018-01-09 00:00:00	5	7	2017-12-04 07:16:03.352	22	3
39	2017-11-30 16:35:38.141	2018-02-09 00:00:00	7	10	2017-12-04 17:58:43.681	3	5
429	2017-12-04 15:20:45.525	2018-11-06 00:00:00	7	4	2017-12-04 17:40:16.169	10	5
426	2017-12-04 15:18:56.958	2018-03-05 00:00:00	7	5	2017-12-04 17:40:16.295	10	5
234	2017-12-01 15:05:28.445	2018-04-09 00:00:00	5	12	2017-12-04 19:50:55.274	39	3
76	2017-11-30 19:39:12.812	2018-09-03 00:00:00	2	7	2017-12-04 19:50:55.392	74	3
225	2017-12-01 14:54:41.117	2018-03-12 00:00:00	5	14	2017-12-04 12:36:25.076	25	3
194	2017-12-01 14:23:50.419	2018-09-10 00:00:00	5	12	2017-12-04 19:50:55.392	18	3
57	2017-11-30 18:01:50.691	2018-11-26 00:00:00	5	5	2017-12-04 19:50:55.392	142	3
228	2017-12-01 14:56:24.79	2018-10-22 00:00:00	5	7	2017-12-04 12:36:25.076	25	3
221	2017-12-01 14:44:48.858	2018-12-24 00:00:00	5	9	2017-12-04 07:16:03.503	22	3
159	2017-12-01 11:51:06.728	2018-06-13 00:00:00	5	3	2017-12-04 07:07:30.807	24	3
230	2017-12-01 14:57:45.042	2018-04-23 00:00:00	5	7	2017-12-04 07:16:03.431	22	3
351	2017-12-04 13:06:46.39	2018-05-03 00:00:00	1	2	2017-12-04 19:43:29.235	43	3
355	2017-12-04 13:07:13.238	2018-07-23 00:00:00	3	7	2017-12-04 19:43:29.235	86	3
153	2017-12-01 11:47:05.046	2018-05-03 00:00:00	5	2	2017-12-04 07:07:30.888	24	3
432	2017-12-04 15:47:05.062	2018-08-06 00:00:00	4	5	2017-12-04 19:43:29.235	40	3
353	2017-12-04 13:07:04.344	2018-04-26 00:00:00	3	3	2017-12-04 19:43:29.235	86	3
213	2017-12-01 14:37:33.675	2018-05-18 00:00:00	4	14	2017-12-04 18:25:49.263	59	3
54	2017-11-30 17:57:08.779	2018-11-19 00:00:00	5	5	2017-12-04 19:50:55.392	142	3
192	2017-12-01 14:02:11.797	2018-03-05 00:00:00	\N	3	\N	55	1
216	2017-12-01 14:38:32.428	2018-07-16 00:00:00	4	11	2017-12-04 18:25:49.399	59	3
193	2017-12-01 14:02:50.888	2018-05-03 00:00:00	\N	2	\N	55	1
49	2017-11-30 17:52:46.679	2018-06-13 00:00:00	5	3	2017-12-04 19:50:55.392	142	3
195	2017-12-01 14:25:08.307	2018-08-20 00:00:00	0	12	2017-12-01 14:25:08.311	18	4
203	2017-12-01 14:33:31.16	2018-07-23 00:00:00	5	7	2017-12-04 19:50:55.392	26	3
427	2017-12-04 15:19:11.021	2018-08-27 00:00:00	\N	14	2017-12-04 15:20:50.408	10	2
428	2017-12-04 15:20:09.605	2018-04-23 00:00:00	\N	8	2017-12-04 15:20:50.486	10	2
238	2017-12-01 15:08:20.128	2018-08-20 00:00:00	0	12	2017-12-01 16:33:18.577	18	4
164	2017-12-01 12:10:43.159	2018-07-02 00:00:00	\N	19	2017-12-01 16:50:18.705	56	2
206	2017-12-01 14:34:24.612	2018-06-13 00:00:00	\N	3	\N	55	1
208	2017-12-01 14:35:13.113	2018-08-27 00:00:00	\N	14	\N	55	1
209	2017-12-01 14:35:38.077	2018-12-24 00:00:00	\N	6	\N	55	1
165	2017-12-01 12:11:05.956	2018-08-13 00:00:00	\N	12	2017-12-01 16:50:18.781	56	2
115	2017-12-01 09:56:49.225	2018-08-01 00:00:00	5	10	2017-12-04 19:50:55.392	16	3
430	2017-12-04 15:23:49.541	2018-11-05 00:00:00	4	7	2017-12-04 19:43:27.065	81	3
188	2017-12-01 13:56:42.426	2017-12-25 00:00:00	7	5	2017-12-04 19:50:55.392	32	3
190	2017-12-01 13:58:28.528	2018-01-09 00:00:00	7	14	2017-12-04 19:50:55.392	32	3
204	2017-12-01 14:33:33.093	2018-06-11 00:00:00	\N	14	2017-12-01 14:43:28.921	51	2
202	2017-12-01 14:33:15.783	2018-02-26 00:00:00	\N	5	2017-12-01 14:43:28.968	51	2
205	2017-12-01 14:34:19.502	2018-10-15 00:00:00	\N	5	2017-12-01 14:43:29.018	51	2
207	2017-12-01 14:34:29.941	2018-10-22 00:00:00	\N	5	2017-12-01 14:43:29.065	51	2
173	2017-12-01 12:21:33.319	2018-04-02 00:00:00	5	14	2017-12-04 07:17:12.82	21	3
177	2017-12-01 12:32:16.088	2018-10-22 00:00:00	5	5	2017-12-04 07:17:12.912	21	3
174	2017-12-01 12:24:59.824	2018-08-06 00:00:00	5	12	2017-12-04 07:17:12.994	21	3
175	2017-12-01 12:31:30.442	2018-06-04 00:00:00	5	6	2017-12-04 07:17:13.075	21	3
284	2017-12-04 09:11:38.031	2018-09-17 00:00:00	\N	5	2017-12-04 09:15:29.03	27	2
283	2017-12-04 09:11:07.215	2018-07-16 00:00:00	\N	19	2017-12-04 09:15:29.079	27	2
282	2017-12-04 09:10:39.6	2018-05-14 00:00:00	\N	5	2017-12-04 09:15:29.127	27	2
279	2017-12-04 08:37:28.247	2018-03-14 00:00:00	\N	3	2017-12-04 09:15:29.173	27	2
176	2017-12-01 12:32:10.581	2018-03-01 00:00:00	\N	14	2017-12-04 10:20:42.537	53	2
178	2017-12-01 12:33:05.62	2018-05-21 00:00:00	\N	7	2017-12-04 10:20:42.591	53	2
179	2017-12-01 12:33:40.099	2018-12-25 00:00:00	\N	7	2017-12-04 10:20:42.642	53	2
302	2017-12-04 12:27:36.459	2018-06-25 00:00:00	\N	5	2017-12-04 12:31:20.912	41	2
297	2017-12-04 12:26:21.063	2018-09-17 00:00:00	\N	14	2017-12-04 12:31:20.971	41	2
300	2017-12-04 12:26:40.034	2017-12-18 00:00:00	\N	5	2017-12-04 12:34:00.335	45	2
275	2017-12-01 16:54:15.994	2018-07-02 00:00:00	5	21	2017-12-04 07:16:03.584	22	3
348	2017-12-04 12:59:09.438	2018-07-30 00:00:00	1	16	2017-12-04 18:28:49.565	43	3
308	2017-12-04 12:29:12.504	2018-07-20 00:00:00	0	14	2017-12-04 19:30:42.912	36	4
170	2017-12-01 12:17:04.372	2018-05-03 00:00:00	5	9	2017-12-04 19:50:55.392	18	3
311	2017-12-04 12:29:31.547	2018-12-03 00:00:00	2	14	2017-12-04 19:23:14.602	36	3
314	2017-12-04 12:31:05.152	2018-06-01 00:00:00	6	14	2017-12-04 15:48:03.589	69	5
169	2017-12-01 12:16:46.941	2018-01-29 00:00:00	5	5	2017-12-04 19:50:55.392	18	3
171	2017-12-01 12:17:28.307	2018-07-02 00:00:00	5	19	2017-12-04 19:50:55.392	18	3
111	2017-12-01 09:50:34.452	2018-05-18 00:00:00	5	14	2017-12-04 19:50:55.392	16	3
48	2017-11-30 17:50:49.397	2018-03-26 00:00:00	5	14	2017-12-04 19:50:55.392	142	3
112	2017-12-01 09:52:44.888	2018-12-24 00:00:00	5	6	2017-12-04 19:50:55.392	16	3
346	2017-12-04 12:57:02.72	2018-05-14 00:00:00	4	7	2017-12-04 19:43:29.235	146	3
433	2017-12-04 15:53:45.402	2018-10-08 00:00:00	4	19	2017-12-04 23:19:25.753	87	3
108	2017-12-01 09:48:40.803	2018-07-02 00:00:00	5	7	2017-12-04 07:46:13.998	26	4
316	2017-12-04 12:31:59.612	2018-10-10 00:00:00	0	9	2017-12-04 16:01:37.477	45	4
326	2017-12-04 12:36:39.842	2018-08-27 00:00:00	4	14	2017-12-04 19:43:27.065	81	3
251	2017-12-01 15:34:14.344	2018-07-02 00:00:00	0	4	2017-12-04 19:41:21.346	84	4
344	2017-12-04 12:50:48.072	2018-09-03 00:00:00	1	14	2017-12-04 18:30:01.267	49	3
286	2017-12-04 09:49:06.137	2018-07-16 00:00:00	\N	20	\N	7	1
345	2017-12-04 12:51:19.597	2018-06-25 00:00:00	1	14	2017-12-04 18:30:01.37	49	3
287	2017-12-04 09:49:29.697	2018-09-10 00:00:00	\N	20	\N	7	1
288	2017-12-04 09:50:11.538	2018-10-08 00:00:00	\N	20	\N	7	1
247	2017-12-01 15:34:14.196	2018-07-02 00:00:00	0	4	2017-12-04 19:41:21.436	84	4
245	2017-12-01 15:34:14.104	2018-07-02 00:00:00	0	4	2017-12-04 19:41:21.527	84	4
289	2017-12-04 09:50:28.945	2018-12-10 00:00:00	\N	10	\N	7	1
435	2017-12-04 16:08:42.303	2018-04-09 00:00:00	\N	5	2017-12-04 16:09:44.847	2	2
336	2017-12-04 12:44:03.652	2018-09-24 00:00:00	4	7	2017-12-04 19:43:29.235	146	3
290	2017-12-04 10:18:37.87	2018-12-09 00:00:00	\N	7	\N	17	1
436	2017-12-04 16:08:54.256	2018-05-03 00:00:00	\N	2	2017-12-04 16:09:44.954	2	2
437	2017-12-04 16:09:04.197	2018-06-09 00:00:00	\N	1	2017-12-04 16:09:45.051	2	2
438	2017-12-04 16:09:13.679	2018-08-20 00:00:00	\N	14	2017-12-04 16:09:45.145	2	2
292	2017-12-04 10:21:13.217	2018-05-04 00:00:00	\N	3	\N	17	1
293	2017-12-04 10:21:56.491	2018-08-25 00:00:00	\N	14	\N	17	1
439	2017-12-04 16:09:22.193	2018-11-06 00:00:00	\N	4	2017-12-04 16:09:45.255	2	2
304	2017-12-04 12:28:12.724	2018-07-16 00:00:00	1	14	2017-12-04 18:30:01.58	45	3
434	2017-12-04 16:08:31.525	2018-03-15 00:00:00	\N	2	2017-12-04 16:09:54.285	2	2
440	2017-12-04 16:11:11.025	2018-06-01 00:00:00	\N	10	\N	60	1
441	2017-12-04 16:11:38.668	2018-07-15 00:00:00	\N	18	\N	60	1
249	2017-12-01 15:34:14.266	2018-07-02 00:00:00	2	4	2017-12-04 19:43:00.736	84	3
319	2017-12-04 12:35:10.596	2018-05-03 00:00:00	4	7	2017-12-04 19:43:27.065	81	3
240	2017-12-01 15:08:20.261	2018-08-20 00:00:00	0	12	2017-12-01 16:31:27.972	18	4
307	2017-12-04 12:28:24.677	2018-04-02 00:00:00	4	7	2017-12-04 19:43:29.235	42	3
352	2017-12-04 13:06:54.279	2018-05-07 00:00:00	1	2	2017-12-04 19:43:29.235	43	3
459	2017-12-04 19:50:01.843	2018-10-22 00:00:00	\N	6	2017-12-04 19:50:45.864	6	2
239	2017-12-01 15:08:20.192	2018-08-20 00:00:00	0	12	2017-12-01 16:33:35.597	18	4
268	2017-12-01 16:29:45.486	2018-04-23 00:00:00	\N	14	2017-12-01 16:34:29.361	4	2
317	2017-12-04 12:33:11.255	2018-03-12 00:00:00	1	5	2017-12-04 18:30:01.471	45	3
347	2017-12-04 12:58:45.666	2018-12-03 00:00:00	4	28	2017-12-04 19:09:25.813	79	3
313	2017-12-04 12:29:57.723	2018-11-05 00:00:00	4	14	2017-12-04 19:43:29.235	42	3
47	2017-11-30 17:49:48.115	2018-07-18 00:00:00	5	14	2017-12-04 12:36:25.076	19	3
321	2017-12-04 12:35:26.316	2018-01-09 00:00:00	\N	7	2017-12-04 12:37:03.784	76	2
330	2017-12-04 12:40:03.045	2018-07-16 00:00:00	\N	5	\N	54	1
325	2017-12-04 12:36:12.173	2018-06-01 00:00:00	\N	14	2017-12-04 12:37:03.838	76	2
327	2017-12-04 12:36:40.236	2018-10-01 00:00:00	\N	7	2017-12-04 12:37:03.891	76	2
332	2017-12-04 12:40:19.778	2018-10-01 00:00:00	\N	14	\N	54	1
46	2017-11-30 17:48:34.542	2018-04-23 00:00:00	5	8	2017-12-04 12:36:25.076	19	3
50	2017-11-30 17:53:15.908	2018-12-24 00:00:00	5	6	2017-12-04 12:36:25.076	19	3
224	2017-12-01 14:48:39.602	2018-11-12 00:00:00	5	14	2017-12-04 12:36:25.076	23	3
223	2017-12-01 14:48:09.106	2018-05-14 00:00:00	5	7	2017-12-04 12:36:25.076	23	3
222	2017-12-01 14:45:18.261	2018-02-05 00:00:00	5	7	2017-12-04 12:36:25.076	23	3
333	2017-12-04 12:40:44.914	2018-12-24 00:00:00	\N	4	\N	54	1
320	2017-12-04 12:35:11.839	2018-10-03 00:00:00	\N	5	2017-12-04 12:38:05.241	30	2
322	2017-12-04 12:35:50.431	2018-12-20 00:00:00	\N	2	2017-12-04 12:38:05.305	30	2
323	2017-12-04 12:36:07.447	2018-12-24 00:00:00	\N	7	2017-12-04 12:38:05.369	30	2
342	2017-12-04 12:48:56.044	2018-10-01 00:00:00	\N	7	2017-12-04 12:58:16.845	77	2
329	2017-12-04 12:38:29.77	2018-05-10 00:00:00	\N	2	\N	54	1
226	2017-12-01 14:55:17.15	2018-05-03 00:00:00	5	4	2017-12-04 12:36:25.076	25	3
229	2017-12-01 14:57:32.104	2018-06-13 00:00:00	5	3	2017-12-04 12:36:25.076	25	3
277	2017-12-01 17:14:09.47	2018-10-04 00:00:00	5	7	2017-12-04 12:36:25.076	19	3
267	2017-12-01 16:21:35.659	2018-08-01 00:00:00	5	5	2017-12-04 12:36:25.076	143	3
264	2017-12-01 16:19:23.299	2018-02-01 00:00:00	5	2	2017-12-04 12:36:25.076	143	3
265	2017-12-01 16:20:40.959	2018-06-01 00:00:00	5	5	2017-12-04 12:36:25.076	143	3
276	2017-12-01 17:06:20.599	2018-09-01 00:00:00	5	14	2017-12-04 12:36:25.076	143	3
266	2017-12-01 16:21:00.787	2018-05-03 00:00:00	5	2	2017-12-04 12:36:25.076	143	3
328	2017-12-04 12:36:43.566	2018-03-05 00:00:00	\N	3	\N	54	1
339	2017-12-04 12:48:05.557	2018-04-09 00:00:00	\N	7	2017-12-04 12:58:16.737	77	2
340	2017-12-04 12:48:19.146	2018-07-02 00:00:00	\N	14	2017-12-04 12:58:16.791	77	2
349	2017-12-04 13:05:39.567	2018-03-12 00:00:00	\N	19	2017-12-04 13:16:12.863	61	2
359	2017-12-04 13:07:27.96	2018-06-13 00:00:00	\N	10	2017-12-04 13:16:12.948	61	2
242	2017-12-01 15:26:55.283	2018-02-14 00:00:00	\N	3	2017-12-04 13:16:16.94	62	2
393	2017-12-04 13:43:34.686	2018-12-18 00:00:00	4	9	2017-12-04 18:25:49.523	52	3
364	2017-12-04 13:09:50.823	2018-09-17 00:00:00	\N	7	2017-12-04 13:10:04.345	75	2
363	2017-12-04 13:08:42.654	2018-07-16 00:00:00	\N	14	2017-12-04 13:10:04.422	75	2
361	2017-12-04 13:07:32.95	2018-05-02 00:00:00	\N	7	2017-12-04 13:10:04.497	75	2
445	2017-12-04 17:13:05.747	2018-10-08 00:00:00	\N	26	2017-12-04 17:14:19.445	61	2
406	2017-12-04 14:09:49.093	2018-05-10 00:00:00	\N	2	2017-12-04 14:11:53.82	13	2
405	2017-12-04 14:09:37.109	2018-05-07 00:00:00	\N	2	2017-12-04 14:11:53.714	13	2
235	2017-12-01 15:05:39.029	2018-08-06 00:00:00	1	14	2017-12-04 19:50:55.392	39	3
408	2017-12-04 14:10:31.08	2018-12-27 00:00:00	\N	3	2017-12-04 14:11:53.998	13	2
365	2017-12-04 13:11:05.291	2018-09-03 00:00:00	\N	19	2017-12-04 13:16:13.026	61	2
444	2017-12-04 16:59:27.361	2018-08-13 00:00:00	\N	7	2017-12-04 18:46:56.191	65	2
366	2017-12-04 13:11:25.583	2018-12-24 00:00:00	\N	6	2017-12-04 13:16:13.094	61	2
443	2017-12-04 16:53:14.272	2018-05-21 00:00:00	\N	7	2017-12-04 18:46:56.278	65	2
407	2017-12-04 14:10:15.376	2018-10-22 00:00:00	\N	14	2017-12-04 14:11:53.909	13	2
243	2017-12-01 15:27:08.386	2018-02-19 00:00:00	\N	5	2017-12-04 13:16:17.011	62	2
68	2017-11-30 19:28:05.288	2018-07-16 00:00:00	3	14	2017-12-04 19:44:45.816	34	3
367	2017-12-04 13:13:32.775	2018-08-27 00:00:00	\N	14	2017-12-04 13:16:17.1	62	2
373	2017-12-04 13:19:36.55	2018-09-10 00:00:00	0	14	2017-12-04 16:16:36.376	9	4
374	2017-12-04 13:19:53.956	2018-11-19 00:00:00	7	5	2017-12-04 16:17:08.107	9	5
92	2017-12-01 08:32:21.812	2018-12-24 00:00:00	7	6	2017-12-04 17:58:43.681	6	5
93	2017-12-01 08:37:02.777	2018-09-10 00:00:00	7	12	2017-12-04 17:58:43.681	6	5
58	2017-11-30 18:08:55.572	2018-06-18 00:00:00	7	14	2017-12-04 17:58:43.681	6	5
442	2017-12-04 16:16:10.818	2018-12-24 00:00:00	\N	8	2017-12-04 17:58:55.869	2	2
382	2017-12-04 13:25:26.005	2018-07-20 00:00:00	0	14	2017-12-04 19:44:58.147	146	4
69	2017-11-30 19:31:20.447	2018-10-01 00:00:00	3	7	2017-12-04 19:23:14.184	34	3
383	2017-12-04 13:25:26.094	2018-07-20 00:00:00	0	14	2017-12-04 19:44:58.206	146	4
241	2017-12-01 15:14:38.427	2018-06-25 00:00:00	3	4	2017-12-04 19:43:00.871	84	3
377	2017-12-04 13:22:55.18	2018-07-16 00:00:00	1	14	2017-12-04 19:43:27.064	146	3
60	2017-11-30 19:16:16.888	2018-10-22 00:00:00	4	12	2017-12-04 19:43:29.235	38	3
384	2017-12-04 13:25:26.192	2018-07-20 00:00:00	0	14	2017-12-04 19:44:58.264	146	4
375	2017-12-04 13:20:33.567	2018-12-25 00:00:00	7	4	2017-12-04 16:17:08.226	9	5
385	2017-12-04 13:25:26.271	2018-07-20 00:00:00	0	14	2017-12-04 19:44:58.322	146	4
381	2017-12-04 13:25:25.904	2018-07-20 00:00:00	0	14	2017-12-04 19:44:58.38	146	4
306	2017-12-04 12:28:22.333	2018-04-25 00:00:00	\N	5	2017-12-04 13:25:26.275	41	2
303	2017-12-04 12:28:05.516	2018-12-24 00:00:00	\N	5	2017-12-04 13:25:26.275	41	2
399	2017-12-04 14:06:39.669	2018-08-27 00:00:00	\N	14	2017-12-04 14:07:13.65	28	2
260	2017-12-01 16:05:22.992	2018-12-24 00:00:00	4	6	2017-12-04 19:43:29.235	145	3
110	2017-12-01 09:49:17.768	2018-12-24 00:00:00	5	7	2017-12-04 19:50:55.392	26	3
357	2017-12-04 13:07:21.375	2018-08-06 00:00:00	3	14	2017-12-04 19:43:29.235	86	3
380	2017-12-04 13:24:39.121	2018-03-14 00:00:00	3	8	2017-12-04 19:43:29.235	86	3
65	2017-11-30 19:20:48.977	2018-03-12 00:00:00	4	14	2017-12-04 19:43:29.235	38	3
400	2017-12-04 14:06:59.942	2018-12-24 00:00:00	\N	7	2017-12-04 14:07:13.724	28	2
386	2017-12-04 13:26:31.794	2018-10-26 00:00:00	\N	1	2017-12-04 13:27:25.198	62	2
87	2017-12-01 02:11:30.191	2018-04-23 00:00:00	4	6	2017-12-04 19:43:29.235	40	3
387	2017-12-04 13:26:47.506	2018-10-29 00:00:00	\N	5	2017-12-04 13:27:25.292	62	2
354	2017-12-04 13:07:04.973	2018-05-10 00:00:00	1	2	2017-12-04 19:43:29.235	43	3
391	2017-12-04 13:42:26.499	2018-09-10 00:00:00	0	14	2017-12-04 18:24:58.727	52	4
392	2017-12-04 13:42:52.188	2018-11-19 00:00:00	4	5	2017-12-04 18:25:49.641	52	3
109	2017-12-01 09:49:01.072	2018-08-20 00:00:00	5	14	2017-12-04 19:50:55.392	26	3
390	2017-12-04 13:42:06.219	2018-06-04 00:00:00	0	5	2017-12-04 18:24:58.792	52	4
370	2017-12-04 13:18:14.541	2018-04-23 00:00:00	4	7	2017-12-04 18:25:49.755	57	3
66	2017-11-30 19:23:53.504	2018-07-27 00:00:00	4	8	2017-12-04 19:43:29.235	38	3
73	2017-11-30 19:33:23.358	2018-05-28 00:00:00	3	4	2017-12-04 19:23:13.458	34	3
394	2017-12-04 13:49:19.074	2018-07-20 00:00:00	0	14	2017-12-04 13:49:19.078	146	4
371	2017-12-04 13:18:29.588	2018-10-01 00:00:00	4	14	2017-12-04 18:25:49.864	57	3
396	2017-12-04 13:59:31.933	2018-07-30 00:00:00	\N	28	2017-12-04 14:00:07.785	50	2
259	2017-12-01 16:05:05.766	2018-09-10 00:00:00	4	20	2017-12-04 19:43:29.235	145	3
88	2017-12-01 02:11:53.67	2018-06-18 00:00:00	4	5	2017-12-04 19:43:29.235	40	3
358	2017-12-04 13:07:23.125	2018-06-13 00:00:00	1	3	2017-12-04 19:43:29.235	43	3
398	2017-12-04 14:06:12.284	2018-01-18 00:00:00	\N	7	2017-12-04 14:07:13.572	28	2
395	2017-12-04 13:55:07.972	2018-07-09 00:00:00	1	7	2017-12-04 19:43:29.235	42	3
360	2017-12-04 13:07:29.12	2018-12-26 00:00:00	3	4	2017-12-04 19:43:29.235	86	3
404	2017-12-04 14:09:10.31	2018-03-05 00:00:00	\N	3	2017-12-04 14:11:53.613	13	2
410	2017-12-04 14:21:09.755	2018-08-20 00:00:00	1	15	2017-12-04 18:28:19.566	47	3
412	2017-12-04 14:22:04.798	2018-06-14 00:00:00	1	9	2017-12-04 18:28:19.692	47	3
409	2017-12-04 14:21:00.337	2018-11-26 00:00:00	1	5	2017-12-04 18:28:19.817	47	3
446	2017-12-04 17:13:43.918	2018-05-03 00:00:00	\N	2	2017-12-04 17:14:19.331	61	2
411	2017-12-04 14:21:43.194	2018-12-24 00:00:00	1	8	2017-12-04 18:28:19.944	47	3
388	2017-12-04 13:29:28.908	2018-07-30 00:00:00	1	14	2017-12-04 18:28:20.069	46	3
397	2017-12-04 14:03:10.152	2018-08-26 00:00:00	6	14	2017-12-04 15:48:03.539	69	5
389	2017-12-04 13:30:03.623	2018-10-01 00:00:00	1	14	2017-12-04 18:28:20.19	46	3
166	2017-12-01 12:15:30.51	2018-06-25 00:00:00	6	14	2017-12-04 17:58:43.682	71	5
168	2017-12-01 12:16:30.455	2018-09-10 00:00:00	6	7	2017-12-04 17:58:43.682	71	5
184	2017-12-01 13:32:38.232	2018-06-11 00:00:00	6	14	2017-12-04 17:58:43.682	63	5
183	2017-12-01 13:29:19.677	2018-10-01 00:00:00	6	7	2017-12-04 17:58:43.682	63	5
185	2017-12-01 13:33:48.893	2018-12-03 00:00:00	6	7	2017-12-04 17:58:43.682	63	5
141	2017-12-01 11:08:02.982	2018-04-23 00:00:00	6	6	2017-12-04 17:58:43.682	67	5
140	2017-12-01 11:06:55.63	2018-05-28 00:00:00	6	14	2017-12-04 17:58:43.682	67	5
139	2017-12-01 11:01:02.469	2018-03-05 00:00:00	6	3	2017-12-04 17:58:43.682	67	5
448	2017-12-04 17:36:39.275	2018-07-02 00:00:00	4	7	2017-12-04 19:43:29.235	144	3
137	2017-12-01 11:00:01.755	2018-10-15 00:00:00	6	12	2017-12-04 17:58:43.682	67	5
61	2017-11-30 19:18:26.197	2018-01-09 00:00:00	7	14	2017-12-04 17:58:43.682	65	5
62	2017-11-30 19:18:55.557	2018-02-26 00:00:00	6	14	2017-12-04 17:58:43.682	65	5
64	2017-11-30 19:19:32.708	2018-12-27 00:00:00	2	2	2017-12-04 17:58:43.682	65	5
212	2017-12-01 14:37:31.019	2018-05-07 00:00:00	1	2	2017-12-04 19:50:55.392	58	3
151	2017-12-01 11:46:56.446	2018-11-06 00:00:00	6	5	2017-12-04 17:58:43.682	64	5
154	2017-12-01 11:47:44.462	2018-11-12 00:00:00	6	7	2017-12-04 17:58:43.682	64	5
149	2017-12-01 11:46:36.596	2018-06-18 00:00:00	6	14	2017-12-04 17:58:43.682	64	5
233	2017-12-01 15:04:44.878	2018-09-01 00:00:00	3	7	2017-12-04 19:43:01.077	85	3
232	2017-12-01 15:04:30.31	2018-08-01 00:00:00	3	7	2017-12-04 19:43:01.209	85	3
414	2017-12-04 14:22:21.851	2018-08-06 00:00:00	0	7	2017-12-04 19:44:57.852	42	4
416	2017-12-04 14:22:21.98	2018-08-06 00:00:00	0	7	2017-12-04 19:44:57.929	42	4
63	2017-11-30 19:19:15.125	2018-06-04 00:00:00	\N	14	2017-12-04 17:48:47.503	65	2
453	2017-12-04 17:48:47.498	2018-08-06 00:00:00	0	22	2017-12-04 18:21:28.73	40	4
418	2017-12-04 14:22:22.114	2018-08-06 00:00:00	0	7	2017-12-04 19:44:58.007	42	4
420	2017-12-04 14:22:22.236	2018-08-06 00:00:00	0	7	2017-12-04 19:44:58.081	42	4
187	2017-12-01 13:45:06.663	2018-12-17 00:00:00	6	7	2017-12-04 17:58:43.682	70	5
130	2017-12-01 10:46:39.724	2018-08-13 00:00:00	6	14	2017-12-04 17:58:43.682	70	5
186	2017-12-01 13:44:49.736	2018-06-18 00:00:00	6	7	2017-12-04 17:58:43.682	70	5
180	2017-12-01 13:10:03.345	2018-02-05 00:00:00	6	14	2017-12-04 17:58:43.682	68	5
181	2017-12-01 13:10:14.413	2018-07-23 00:00:00	6	14	2017-12-04 17:58:43.682	68	5
167	2017-12-01 12:16:10.936	2018-11-12 00:00:00	6	7	2017-12-04 17:58:43.682	71	5
147	2017-12-01 11:46:18.621	2018-05-03 00:00:00	6	2	2017-12-04 17:58:43.682	64	5
269	2017-12-01 16:30:21.557	2018-08-01 00:00:00	7	14	2017-12-04 17:58:43.682	4	5
273	2017-12-01 16:31:49.14	2018-10-01 00:00:00	7	7	2017-12-04 17:58:43.682	4	5
274	2017-12-01 16:32:38.957	2018-06-01 00:00:00	7	5	2017-12-04 17:58:43.682	4	5
285	2017-12-04 09:48:46.622	2018-04-19 00:00:00	7	10	2017-12-04 17:58:43.682	7	5
281	2017-12-04 09:10:05.586	2018-09-12 00:00:00	7	17	2017-12-04 17:58:43.682	147	5
280	2017-12-04 09:07:29.888	2018-05-28 00:00:00	7	14	2017-12-04 17:58:43.682	147	5
117	2017-12-01 10:06:59.078	2018-02-26 00:00:00	7	5	2017-12-04 17:58:43.682	12	5
119	2017-12-01 10:08:21.146	2018-07-18 00:00:00	7	3	2017-12-04 17:58:43.682	12	5
120	2017-12-01 10:08:38.393	2018-10-08 00:00:00	7	14	2017-12-04 17:58:43.682	12	5
451	2017-12-04 17:48:47.47	2018-11-06 00:00:00	0	14	2017-12-04 18:24:05.603	144	4
452	2017-12-04 17:48:47.495	2018-08-27 00:00:00	0	7	2017-12-04 18:24:05.668	144	4
219	2017-12-01 14:42:10.638	2018-06-13 00:00:00	1	3	2017-12-04 19:50:55.392	58	3
148	2017-12-01 11:46:23.015	2018-05-03 00:00:00	3	6	2017-12-04 19:50:55.392	15	3
152	2017-12-01 11:47:04.478	2018-11-06 00:00:00	3	4	2017-12-04 19:50:55.392	15	3
447	2017-12-04 17:34:33.331	2018-08-01 00:00:00	5	14	2017-12-04 19:43:27.065	15	3
214	2017-12-01 14:37:43.251	2018-05-10 00:00:00	1	2	2017-12-04 19:50:55.392	58	3
218	2017-12-01 14:39:40.829	2018-08-13 00:00:00	1	19	2017-12-04 19:50:55.392	58	3
81	2017-11-30 20:11:01.551	2018-06-13 00:00:00	2	3	2017-12-04 19:50:55.392	78	3
84	2017-11-30 20:47:06.525	2018-07-30 00:00:00	2	5	2017-12-04 19:50:55.392	78	3
100	2017-12-01 09:22:42.968	2018-10-08 00:00:00	2	7	2017-12-04 19:50:55.392	82	3
255	2017-12-01 15:40:37.963	2018-07-23 00:00:00	2	14	2017-12-04 19:50:55.392	80	3
82	2017-11-30 20:42:40.014	2018-07-09 00:00:00	2	19	2017-12-04 19:50:55.392	78	3
98	2017-12-01 09:22:16.976	2018-06-13 00:00:00	2	3	2017-12-04 19:50:55.392	82	3
78	2017-11-30 19:44:28.877	2018-12-28 00:00:00	2	4	2017-12-04 19:50:55.392	74	3
79	2017-11-30 19:45:33.064	2018-10-03 00:00:00	2	3	2017-12-04 19:50:55.392	74	3
99	2017-12-01 09:22:33.902	2018-07-09 00:00:00	2	14	2017-12-04 19:50:55.392	82	3
97	2017-12-01 09:22:06.281	2018-05-07 00:00:00	2	4	2017-12-04 19:50:55.392	82	3
211	2017-12-01 14:37:02.915	2018-05-03 00:00:00	1	2	2017-12-04 19:50:55.392	58	3
122	2017-12-01 10:15:29.735	2018-06-04 00:00:00	7	5	2017-12-04 17:58:43.682	11	5
121	2017-12-01 10:15:10.553	2018-04-19 00:00:00	7	10	2017-12-04 17:58:43.682	11	5
454	2017-12-04 18:27:45.204	2018-06-25 00:00:00	\N	5	2017-12-04 18:27:51.481	9	2
455	2017-12-04 18:30:58.139	2018-06-25 00:00:00	4	5	2017-12-04 18:33:50.016	52	3
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
218	Сотрудник добавил период отпуска.	2017-12-01 02:12:55.575	Новый	Новый	\N	90
219	Отпуск отправлен на согласование	2017-12-01 02:13:58.46	На согласовании	Новый	\N	86
220	Отпуск отправлен на согласование	2017-12-01 02:13:58.469	На согласовании	Новый	\N	87
221	Отпуск отправлен на согласование	2017-12-01 02:13:58.471	На согласовании	Новый	\N	88
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
336	Сотрудник добавил период отпуска.	2017-12-01 11:46:56.449	Новый	Новый	\N	151
337	Сотрудник добавил период отпуска.	2017-12-01 11:47:04.481	Новый	Новый	\N	152
338	Сотрудник добавил период отпуска.	2017-12-01 11:47:05.049	Новый	Новый	\N	153
339	Сотрудник добавил период отпуска.	2017-12-01 11:47:44.465	Новый	Новый	\N	154
342	Сотрудник добавил период отпуска.	2017-12-01 11:49:32.518	Новый	Новый	\N	157
343	Отпуск отправлен на согласование	2017-12-01 11:49:58.687	На согласовании	Новый	\N	147
344	Отпуск отправлен на согласование	2017-12-01 11:49:58.69	На согласовании	Новый	\N	149
345	Отпуск отправлен на согласование	2017-12-01 11:49:58.691	На согласовании	Новый	\N	151
346	Отпуск отправлен на согласование	2017-12-01 11:49:58.693	На согласовании	Новый	\N	154
348	Сотрудник добавил период отпуска.	2017-12-01 11:51:06.732	Новый	Новый	\N	159
349	Отпуск отправлен на согласование	2017-12-01 11:51:23.707	На согласовании	Новый	\N	145
350	Отпуск отправлен на согласование	2017-12-01 11:51:23.709	На согласовании	Новый	\N	148
352	Отпуск отправлен на согласование	2017-12-01 11:51:23.712	На согласовании	Новый	\N	152
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
573	Отпуск отправлен на согласование	2017-12-01 16:08:13.931	На согласовании	Новый	\N	261
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
645	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:07:31.156	Частично согласован	На согласовании	\N	146
646	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:07:31.16	Частично согласован	На согласовании	\N	159
647	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:07:31.162	Частично согласован	На согласовании	\N	153
648	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:07:31.167	Частично согласован	На согласовании	\N	157
649	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:07:31.17	Частично согласован	На согласовании	\N	163
650	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:07:31.173	Частично согласован	На согласовании	\N	162
651	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:16:03.596	Частично согласован	На согласовании	\N	210
652	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:16:03.599	Частично согласован	На согласовании	\N	230
653	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:16:03.602	Частично согласован	На согласовании	\N	221
654	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:16:03.603	Частично согласован	На согласовании	\N	275
655	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:17:13.082	Частично согласован	На согласовании	\N	173
656	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:17:13.086	Частично согласован	На согласовании	\N	177
657	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:17:13.087	Частично согласован	На согласовании	\N	174
658	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:17:13.091	Частично согласован	На согласовании	\N	175
659	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:18:28.39	Частично согласован	На согласовании	\N	224
660	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:18:28.392	Частично согласован	На согласовании	\N	223
661	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:18:28.395	Частично согласован	На согласовании	\N	222
662	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:18:28.397	Частично согласован	На согласовании	\N	267
663	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:18:28.401	Частично согласован	На согласовании	\N	264
664	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:18:28.404	Частично согласован	На согласовании	\N	265
665	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:18:28.406	Частично согласован	На согласовании	\N	276
666	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:18:28.408	Частично согласован	На согласовании	\N	266
667	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:19:14.756	Частично согласован	На согласовании	\N	194
668	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:19:14.758	Частично согласован	На согласовании	\N	48
669	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:19:14.762	Частично согласован	На согласовании	\N	57
670	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:19:14.764	Частично согласован	На согласовании	\N	54
671	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:19:14.767	Частично согласован	На согласовании	\N	49
672	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:19:14.769	Частично согласован	На согласовании	\N	51
673	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:25:21.915	Частично согласован	На согласовании	\N	108
674	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:25:21.917	Частично согласован	На согласовании	\N	170
675	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:25:21.919	Частично согласован	На согласовании	\N	171
676	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:25:21.921	Частично согласован	На согласовании	\N	169
677	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:29:00.234	Частично согласован	На согласовании	\N	111
678	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:29:00.236	Частично согласован	На согласовании	\N	112
679	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:29:00.241	Частично согласован	На согласовании	\N	115
680	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:29:00.244	Частично согласован	На согласовании	\N	116
682	Андриановская Маргарита Владимировна отклонил Ваш период отпуска	2017-12-04 07:46:14.001	Отклонён	Частично согласован	\N	108
683	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:46:57.758	Частично согласован	На согласовании	\N	203
684	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:46:57.759	Частично согласован	На согласовании	\N	109
685	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:46:57.761	Частично согласован	На согласовании	\N	110
686	Андриановская Маргарита Владимировна отклонил Ваш период отпуска	2017-12-04 07:47:38.542	Отклонён	На согласовании	\N	56
687	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:52:33.958	Частично согласован	На согласовании	\N	277
688	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:52:33.96	Частично согласован	На согласовании	\N	50
689	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:52:33.961	Частично согласован	На согласовании	\N	47
690	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:52:33.964	Частично согласован	На согласовании	\N	46
691	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:53:44.71	Частично согласован	На согласовании	\N	228
692	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:53:44.712	Частично согласован	На согласовании	\N	225
693	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:53:44.719	Частично согласован	На согласовании	\N	226
694	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 07:53:44.721	Частично согласован	На согласовании	\N	229
695	Сотрудник добавил период отпуска.	2017-12-04 08:37:28.25	Новый	Новый	\N	279
696	Сотрудник добавил период отпуска.	2017-12-04 09:07:29.893	Новый	Новый	\N	280
697	Сотрудник добавил период отпуска.	2017-12-04 09:10:05.591	Новый	Новый	\N	281
698	Отпуск отправлен на согласование	2017-12-04 09:10:15.775	На согласовании	Новый	\N	280
699	Отпуск отправлен на согласование	2017-12-04 09:10:15.778	На согласовании	Новый	\N	281
700	Сотрудник добавил период отпуска.	2017-12-04 09:10:39.605	Новый	Новый	\N	282
701	Сотрудник добавил период отпуска.	2017-12-04 09:11:07.219	Новый	Новый	\N	283
702	Сотрудник добавил период отпуска.	2017-12-04 09:11:38.035	Новый	Новый	\N	284
703	Отпуск отправлен на согласование	2017-12-04 09:15:29.178	На согласовании	Новый	\N	284
704	Отпуск отправлен на согласование	2017-12-04 09:15:29.181	На согласовании	Новый	\N	283
705	Отпуск отправлен на согласование	2017-12-04 09:15:29.185	На согласовании	Новый	\N	282
706	Отпуск отправлен на согласование	2017-12-04 09:15:29.189	На согласовании	Новый	\N	279
707	Сотрудник добавил период отпуска.	2017-12-04 09:48:46.626	Новый	Новый	\N	285
708	Сотрудник добавил период отпуска.	2017-12-04 09:49:06.139	Новый	Новый	\N	286
709	Сотрудник добавил период отпуска.	2017-12-04 09:49:29.7	Новый	Новый	\N	287
710	Сотрудник добавил период отпуска.	2017-12-04 09:50:11.541	Новый	Новый	\N	288
711	Сотрудник добавил период отпуска.	2017-12-04 09:50:28.949	Новый	Новый	\N	289
712	Отпуск отправлен на согласование	2017-12-04 09:50:48.317	На согласовании	Новый	\N	285
713	Сотрудник добавил период отпуска.	2017-12-04 10:18:37.873	Новый	Новый	\N	290
715	Отпуск отправлен на согласование	2017-12-04 10:20:42.646	На согласовании	Новый	\N	176
716	Отпуск отправлен на согласование	2017-12-04 10:20:42.648	На согласовании	Новый	\N	178
717	Отпуск отправлен на согласование	2017-12-04 10:20:42.65	На согласовании	Новый	\N	179
718	Сотрудник добавил период отпуска.	2017-12-04 10:21:13.221	Новый	Новый	\N	292
719	Сотрудник добавил период отпуска.	2017-12-04 10:21:56.513	Новый	Новый	\N	293
720	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 11:27:33.903	Частично согласован	На согласовании	\N	188
721	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 11:27:53.751	Частично согласован	На согласовании	\N	190
722	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 11:32:10.066	Частично согласован	Частично согласован	\N	188
723	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 11:32:10.069	Частично согласован	Частично согласован	\N	190
724	Комаров Игорь Вячеславович согласовал Ваш период отпуска	2017-12-04 11:42:33	Частично согласован	На согласовании	\N	211
725	Комаров Игорь Вячеславович согласовал Ваш период отпуска	2017-12-04 11:42:33.002	Частично согласован	На согласовании	\N	212
726	Комаров Игорь Вячеславович согласовал Ваш период отпуска	2017-12-04 11:42:33.004	Частично согласован	На согласовании	\N	214
727	Комаров Игорь Вячеславович согласовал Ваш период отпуска	2017-12-04 11:42:33.007	Частично согласован	На согласовании	\N	218
728	Комаров Игорь Вячеславович согласовал Ваш период отпуска	2017-12-04 11:42:33.009	Частично согласован	На согласовании	\N	219
729	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:10:08.745	Согласован	Частично согласован	\N	46
730	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:10:08.748	Согласован	Частично согласован	\N	47
731	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:10:08.749	Согласован	Частично согласован	\N	50
732	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.631	Частично согласован	На согласовании	\N	145
733	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.633	Частично согласован	На согласовании	\N	148
734	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.635	Частично согласован	На согласовании	\N	152
735	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.637	Согласован	Частично согласован	\N	277
736	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.639	Согласован	Частично согласован	\N	228
782	Сотрудник добавил период отпуска.	2017-12-04 12:29:57.726	Новый	Новый	\N	313
737	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.641	Согласован	Частично согласован	\N	225
738	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.642	Согласован	Частично согласован	\N	226
739	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.644	Согласован	Частично согласован	\N	229
740	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.647	Частично согласован	На согласовании	\N	97
741	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.648	Частично согласован	На согласовании	\N	98
742	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.65	Частично согласован	На согласовании	\N	78
743	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.651	Частично согласован	На согласовании	\N	76
744	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.653	Частично согласован	На согласовании	\N	75
745	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.655	Частично согласован	На согласовании	\N	85
746	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.656	Частично согласован	На согласовании	\N	84
747	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.658	Частично согласован	На согласовании	\N	82
748	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.66	Частично согласован	На согласовании	\N	81
749	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.662	Частично согласован	На согласовании	\N	255
750	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.663	Частично согласован	На согласовании	\N	278
751	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.666	Частично согласован	На согласовании	\N	100
752	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.668	Частично согласован	На согласовании	\N	99
753	Лукашов Юрий Александрович согласовал Ваш период отпуска	2017-12-04 12:12:04.67	Частично согласован	На согласовании	\N	79
754	Сотрудник добавил период отпуска.	2017-12-04 12:15:18.495	Новый	Новый	\N	294
755	Отпуск отправлен на согласование	2017-12-04 12:15:28.569	На согласовании	Новый	\N	294
756	Сотрудник добавил период отпуска.	2017-12-04 12:21:17.827	Новый	Новый	\N	295
757	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 12:23:46.91	Согласован	Частично согласован	\N	224
758	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 12:23:46.914	Согласован	Частично согласован	\N	223
759	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 12:23:46.916	Согласован	Частично согласован	\N	222
760	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 12:23:46.92	Согласован	Частично согласован	\N	267
761	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 12:23:46.923	Согласован	Частично согласован	\N	264
762	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 12:23:46.925	Согласован	Частично согласован	\N	265
763	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 12:23:46.927	Согласован	Частично согласован	\N	276
764	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 12:23:46.929	Согласован	Частично согласован	\N	266
765	Сотрудник добавил период отпуска.	2017-12-04 12:26:08.031	Новый	Новый	\N	296
766	Сотрудник добавил период отпуска.	2017-12-04 12:26:21.067	Новый	Новый	\N	297
769	Сотрудник добавил период отпуска.	2017-12-04 12:26:40.037	Новый	Новый	\N	300
771	Сотрудник добавил период отпуска.	2017-12-04 12:27:36.463	Новый	Новый	\N	302
772	Сотрудник добавил период отпуска.	2017-12-04 12:28:05.52	Новый	Новый	\N	303
773	Сотрудник добавил период отпуска.	2017-12-04 12:28:12.727	Новый	Новый	\N	304
775	Сотрудник добавил период отпуска.	2017-12-04 12:28:22.336	Новый	Новый	\N	306
776	Сотрудник добавил период отпуска.	2017-12-04 12:28:24.681	Новый	Новый	\N	307
777	Сотрудник добавил период отпуска.	2017-12-04 12:29:12.506	Новый	Новый	\N	308
780	Сотрудник добавил период отпуска.	2017-12-04 12:29:31.551	Новый	Новый	\N	311
783	Сотрудник добавил период отпуска.	2017-12-04 12:31:05.156	Новый	Новый	\N	314
785	Отпуск отправлен на согласование	2017-12-04 12:31:11.94	На согласовании	Новый	\N	313
786	Отпуск отправлен на согласование	2017-12-04 12:31:11.943	На согласовании	Новый	\N	307
787	Отпуск отправлен на согласование	2017-12-04 12:31:13.927	На согласовании	Новый	\N	311
788	Отпуск отправлен на согласование	2017-12-04 12:31:13.933	На согласовании	Новый	\N	308
790	Отпуск отправлен на согласование	2017-12-04 12:31:20.979	На согласовании	Новый	\N	306
791	Отпуск отправлен на согласование	2017-12-04 12:31:20.981	На согласовании	Новый	\N	303
792	Отпуск отправлен на согласование	2017-12-04 12:31:20.983	На согласовании	Новый	\N	302
793	Отпуск отправлен на согласование	2017-12-04 12:31:20.985	На согласовании	Новый	\N	297
794	Сотрудник добавил период отпуска.	2017-12-04 12:31:59.615	Новый	Новый	\N	316
795	Отпуск отправлен на согласование	2017-12-04 12:32:20.306	На согласовании	Новый	\N	314
797	Сотрудник добавил период отпуска.	2017-12-04 12:33:11.259	Новый	Новый	\N	317
798	Отпуск отправлен на согласование	2017-12-04 12:34:00.506	На согласовании	Новый	\N	300
799	Отпуск отправлен на согласование	2017-12-04 12:34:00.507	На согласовании	Новый	\N	316
800	Отпуск отправлен на согласование	2017-12-04 12:34:00.509	На согласовании	Новый	\N	317
801	Отпуск отправлен на согласование	2017-12-04 12:34:00.51	На согласовании	Новый	\N	304
803	Сотрудник добавил период отпуска.	2017-12-04 12:35:10.599	Новый	Новый	\N	319
804	Сотрудник добавил период отпуска.	2017-12-04 12:35:11.849	Новый	Новый	\N	320
805	Сотрудник добавил период отпуска.	2017-12-04 12:35:26.319	Новый	Новый	\N	321
806	Сотрудник добавил период отпуска.	2017-12-04 12:35:50.433	Новый	Новый	\N	322
807	Сотрудник добавил период отпуска.	2017-12-04 12:36:07.45	Новый	Новый	\N	323
809	Сотрудник добавил период отпуска.	2017-12-04 12:36:12.176	Новый	Новый	\N	325
810	Кошкарёв Максим Игоревич согласовал Ваш период отпуска	2017-12-04 12:36:25.095	Частично согласован	На согласовании	\N	60
811	Кошкарёв Максим Игоревич согласовал Ваш период отпуска	2017-12-04 12:36:25.097	Частично согласован	На согласовании	\N	188
812	Кошкарёв Максим Игоревич согласовал Ваш период отпуска	2017-12-04 12:36:25.098	Частично согласован	На согласовании	\N	190
813	Сотрудник добавил период отпуска.	2017-12-04 12:36:39.845	Новый	Новый	\N	326
814	Сотрудник добавил период отпуска.	2017-12-04 12:36:40.239	Новый	Новый	\N	327
815	Сотрудник добавил период отпуска.	2017-12-04 12:36:43.569	Новый	Новый	\N	328
816	Отпуск отправлен на согласование	2017-12-04 12:37:03.894	На согласовании	Новый	\N	321
817	Отпуск отправлен на согласование	2017-12-04 12:37:03.896	На согласовании	Новый	\N	325
818	Отпуск отправлен на согласование	2017-12-04 12:37:03.898	На согласовании	Новый	\N	327
819	Отпуск отправлен на согласование	2017-12-04 12:38:05.372	На согласовании	Новый	\N	296
820	Отпуск отправлен на согласование	2017-12-04 12:38:05.374	На согласовании	Новый	\N	320
821	Отпуск отправлен на согласование	2017-12-04 12:38:05.376	На согласовании	Новый	\N	322
822	Отпуск отправлен на согласование	2017-12-04 12:38:05.377	На согласовании	Новый	\N	323
823	Сотрудник добавил период отпуска.	2017-12-04 12:38:29.774	Новый	Новый	\N	329
824	Отпуск отправлен на согласование	2017-12-04 12:39:07.52	На согласовании	Новый	\N	319
825	Отпуск отправлен на согласование	2017-12-04 12:39:07.529	На согласовании	Новый	\N	326
826	Сотрудник добавил период отпуска.	2017-12-04 12:40:03.047	Новый	Новый	\N	330
828	Сотрудник добавил период отпуска.	2017-12-04 12:40:19.78	Новый	Новый	\N	332
829	Сотрудник добавил период отпуска.	2017-12-04 12:40:44.918	Новый	Новый	\N	333
832	Сотрудник добавил период отпуска.	2017-12-04 12:44:03.657	Новый	Новый	\N	336
835	Сотрудник добавил период отпуска.	2017-12-04 12:48:05.561	Новый	Новый	\N	339
836	Сотрудник добавил период отпуска.	2017-12-04 12:48:19.151	Новый	Новый	\N	340
838	Сотрудник добавил период отпуска.	2017-12-04 12:48:56.048	Новый	Новый	\N	342
840	Сотрудник добавил период отпуска.	2017-12-04 12:50:48.076	Новый	Новый	\N	344
841	Сотрудник добавил период отпуска.	2017-12-04 12:51:19.599	Новый	Новый	\N	345
842	Отпуск отправлен на согласование	2017-12-04 12:53:29.756	На согласовании	Новый	\N	344
843	Отпуск отправлен на согласование	2017-12-04 12:53:29.759	На согласовании	Новый	\N	345
844	Сотрудник добавил период отпуска.	2017-12-04 12:57:02.724	Новый	Новый	\N	346
848	Сотрудник добавил период отпуска.	2017-12-04 12:58:45.669	Новый	Новый	\N	347
849	Сотрудник добавил период отпуска.	2017-12-04 12:59:09.442	Новый	Новый	\N	348
850	Отпуск отправлен на согласование	2017-12-04 13:00:08.657	На согласовании	Новый	\N	336
852	Отпуск отправлен на согласование	2017-12-04 13:00:08.66	На согласовании	Новый	\N	346
853	Сотрудник добавил период отпуска.	2017-12-04 13:05:39.571	Новый	Новый	\N	349
854	Сотрудник добавил период отпуска.	2017-12-04 13:06:36.029	Новый	Новый	\N	350
855	Сотрудник добавил период отпуска.	2017-12-04 13:06:46.394	Новый	Новый	\N	351
856	Сотрудник добавил период отпуска.	2017-12-04 13:06:54.281	Новый	Новый	\N	352
857	Сотрудник добавил период отпуска.	2017-12-04 13:07:04.347	Новый	Новый	\N	353
858	Сотрудник добавил период отпуска.	2017-12-04 13:07:04.98	Новый	Новый	\N	354
859	Сотрудник добавил период отпуска.	2017-12-04 13:07:13.241	Новый	Новый	\N	355
861	Сотрудник добавил период отпуска.	2017-12-04 13:07:21.378	Новый	Новый	\N	357
862	Сотрудник добавил период отпуска.	2017-12-04 13:07:23.129	Новый	Новый	\N	358
863	Сотрудник добавил период отпуска.	2017-12-04 13:07:27.964	Новый	Новый	\N	359
864	Сотрудник добавил период отпуска.	2017-12-04 13:07:29.125	Новый	Новый	\N	360
865	Сотрудник добавил период отпуска.	2017-12-04 13:07:32.959	Новый	Новый	\N	361
867	Сотрудник добавил период отпуска.	2017-12-04 13:08:42.657	Новый	Новый	\N	363
868	Сотрудник добавил период отпуска.	2017-12-04 13:09:50.826	Новый	Новый	\N	364
869	Отпуск отправлен на согласование	2017-12-04 13:10:04.501	На согласовании	Новый	\N	364
870	Отпуск отправлен на согласование	2017-12-04 13:10:04.503	На согласовании	Новый	\N	363
871	Отпуск отправлен на согласование	2017-12-04 13:10:04.505	На согласовании	Новый	\N	361
872	Сотрудник добавил период отпуска.	2017-12-04 13:11:05.294	Новый	Новый	\N	365
873	Сотрудник добавил период отпуска.	2017-12-04 13:11:25.586	Новый	Новый	\N	366
874	Сотрудник добавил период отпуска.	2017-12-04 13:13:32.778	Новый	Новый	\N	367
877	Отпуск отправлен на согласование	2017-12-04 13:16:10.575	На согласовании	Новый	\N	348
878	Отпуск отправлен на согласование	2017-12-04 13:16:10.578	На согласовании	Новый	\N	350
879	Отпуск отправлен на согласование	2017-12-04 13:16:10.58	На согласовании	Новый	\N	351
880	Отпуск отправлен на согласование	2017-12-04 13:16:10.582	На согласовании	Новый	\N	352
881	Отпуск отправлен на согласование	2017-12-04 13:16:10.584	На согласовании	Новый	\N	354
882	Отпуск отправлен на согласование	2017-12-04 13:16:10.585	На согласовании	Новый	\N	358
883	Отпуск отправлен на согласование	2017-12-04 13:16:13.102	На согласовании	Новый	\N	349
884	Отпуск отправлен на согласование	2017-12-04 13:16:13.105	На согласовании	Новый	\N	359
885	Отпуск отправлен на согласование	2017-12-04 13:16:13.109	На согласовании	Новый	\N	365
886	Отпуск отправлен на согласование	2017-12-04 13:16:13.115	На согласовании	Новый	\N	366
845	Отпуск отправлен на согласование	2017-12-04 12:58:16.848	На согласовании	Новый	\N	339
846	Отпуск отправлен на согласование	2017-12-04 12:58:16.852	На согласовании	Новый	\N	340
847	Отпуск отправлен на согласование	2017-12-04 12:58:16.854	На согласовании	Новый	\N	342
887	Отпуск отправлен на согласование	2017-12-04 13:16:17.267	На согласовании	Новый	\N	242
888	Отпуск отправлен на согласование	2017-12-04 13:16:17.269	На согласовании	Новый	\N	243
889	Отпуск отправлен на согласование	2017-12-04 13:16:17.271	На согласовании	Новый	\N	367
892	Кошкарёв Максим Игоревич согласовал Ваш период отпуска	2017-12-04 13:16:36.746	Частично согласован	На согласовании	\N	234
893	Кошкарёв Максим Игоревич согласовал Ваш период отпуска	2017-12-04 13:16:36.748	Частично согласован	На согласовании	\N	87
894	Сотрудник добавил период отпуска.	2017-12-04 13:18:14.545	Новый	Новый	\N	370
895	Сотрудник добавил период отпуска.	2017-12-04 13:18:29.591	Новый	Новый	\N	371
897	Сотрудник добавил период отпуска.	2017-12-04 13:19:36.553	Новый	Новый	\N	373
898	Сотрудник добавил период отпуска.	2017-12-04 13:19:53.959	Новый	Новый	\N	374
899	Отпуск отправлен на согласование	2017-12-04 13:20:14.085	На согласовании	Новый	\N	370
900	Отпуск отправлен на согласование	2017-12-04 13:20:14.087	На согласовании	Новый	\N	371
901	Сотрудник добавил период отпуска.	2017-12-04 13:20:33.569	Новый	Новый	\N	375
903	Отпуск отправлен на согласование	2017-12-04 13:21:15.433	На согласовании	Новый	\N	373
904	Отпуск отправлен на согласование	2017-12-04 13:21:15.435	На согласовании	Новый	\N	374
905	Отпуск отправлен на согласование	2017-12-04 13:21:15.437	На согласовании	Новый	\N	375
908	Абрамкин Максим Андреевич отклонил Ваш период отпуска	2017-12-04 13:22:13.457	Отклонён	На согласовании	\N	306
909	Абрамкин Максим Андреевич отклонил Ваш период отпуска	2017-12-04 13:22:13.46	Отклонён	На согласовании	\N	303
910	Сотрудник добавил период отпуска.	2017-12-04 13:22:55.183	Новый	Новый	\N	377
911	Отпуск отправлен на согласование	2017-12-04 13:23:08.807	На согласовании	Новый	\N	377
912	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:23:18.68	Частично согласован	На согласовании	\N	180
913	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:23:18.681	Частично согласован	На согласовании	\N	314
914	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:23:18.683	Частично согласован	На согласовании	\N	186
915	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:23:18.685	Частично согласован	На согласовании	\N	166
916	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:23:18.686	Частично согласован	На согласовании	\N	181
917	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:23:18.688	Частично согласован	На согласовании	\N	130
918	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:23:18.689	Частично согласован	На согласовании	\N	167
919	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:23:18.69	Частично согласован	На согласовании	\N	187
928	Сотрудник добавил период отпуска.	2017-12-04 13:24:39.123	Новый	Новый	\N	380
929	Сотрудник добавил период отпуска.	2017-12-04 13:25:25.905	Новый	Новый	\N	381
930	Отпуск отправлен на согласование	2017-12-04 13:25:25.906	На согласовании	Новый	\N	381
931	Сотрудник добавил период отпуска.	2017-12-04 13:25:26.006	Новый	Новый	\N	382
932	Отпуск отправлен на согласование	2017-12-04 13:25:26.006	На согласовании	Новый	\N	382
933	Сотрудник добавил период отпуска.	2017-12-04 13:25:26.095	Новый	Новый	\N	383
934	Отпуск отправлен на согласование	2017-12-04 13:25:26.095	На согласовании	Новый	\N	383
935	Сотрудник добавил период отпуска.	2017-12-04 13:25:26.192	Новый	Новый	\N	384
936	Отпуск отправлен на согласование	2017-12-04 13:25:26.193	На согласовании	Новый	\N	384
937	Сотрудник добавил период отпуска.	2017-12-04 13:25:26.272	Новый	Новый	\N	385
938	Отпуск отправлен на согласование	2017-12-04 13:25:26.272	На согласовании	Новый	\N	385
939	Отпуск отправлен на согласование	2017-12-04 13:25:26.286	На согласовании	Новый	\N	353
940	Отпуск отправлен на согласование	2017-12-04 13:25:26.287	На согласовании	Новый	\N	355
941	Отпуск отправлен на согласование	2017-12-04 13:25:26.289	На согласовании	Новый	\N	357
942	Отпуск отправлен на согласование	2017-12-04 13:25:26.29	На согласовании	Новый	\N	360
943	Отпуск отправлен на согласование	2017-12-04 13:25:26.292	На согласовании	Новый	\N	380
944	Сотрудник добавил период отпуска.	2017-12-04 13:26:31.797	Новый	Новый	\N	386
945	Сотрудник добавил период отпуска.	2017-12-04 13:26:47.509	Новый	Новый	\N	387
946	Отпуск отправлен на согласование	2017-12-04 13:27:25.32	На согласовании	Новый	\N	386
947	Отпуск отправлен на согласование	2017-12-04 13:27:25.322	На согласовании	Новый	\N	387
948	Сотрудник добавил период отпуска.	2017-12-04 13:29:28.912	Новый	Новый	\N	388
949	Сотрудник добавил период отпуска.	2017-12-04 13:30:03.626	Новый	Новый	\N	389
950	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:30:30.467	Частично согласован	На согласовании	\N	141
951	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:30:30.468	Частично согласован	На согласовании	\N	147
952	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:30:30.47	Частично согласован	На согласовании	\N	149
953	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:30:30.471	Частично согласован	На согласовании	\N	184
954	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:30:30.472	Частично согласован	На согласовании	\N	185
955	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:30:30.474	Частично согласован	На согласовании	\N	183
956	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:30:30.475	Частично согласован	На согласовании	\N	137
957	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:30:30.476	Частично согласован	На согласовании	\N	151
958	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:30:30.477	Частично согласован	На согласовании	\N	154
959	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:30:30.479	Частично согласован	На согласовании	\N	64
960	Отпуск отправлен на согласование	2017-12-04 13:31:30.49	На согласовании	Новый	\N	388
961	Отпуск отправлен на согласование	2017-12-04 13:31:30.492	На согласовании	Новый	\N	389
962	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:34:00.685	Частично согласован	На согласовании	\N	86
963	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:34:00.686	Частично согласован	На согласовании	\N	65
964	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:34:00.688	Частично согласован	На согласовании	\N	234
965	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:34:00.689	Частично согласован	На согласовании	\N	87
966	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:34:00.691	Частично согласован	На согласовании	\N	258
967	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:34:00.692	Частично согласован	На согласовании	\N	261
968	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:34:00.693	Частично согласован	На согласовании	\N	88
969	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:34:00.694	Частично согласован	На согласовании	\N	66
971	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:34:00.697	Частично согласован	На согласовании	\N	259
972	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:34:00.699	Частично согласован	На согласовании	\N	237
973	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:34:00.7	Частично согласован	На согласовании	\N	60
974	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 13:34:00.702	Частично согласован	На согласовании	\N	260
975	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:34:27.454	Частично согласован	Частично согласован	\N	180
976	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:34:27.456	Частично согласован	Частично согласован	\N	314
977	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:34:27.458	Частично согласован	Частично согласован	\N	186
978	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:34:27.459	Частично согласован	Частично согласован	\N	166
979	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:34:27.461	Частично согласован	Частично согласован	\N	181
980	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:34:27.463	Частично согласован	Частично согласован	\N	130
981	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:34:27.464	Частично согласован	Частично согласован	\N	167
982	Орлов Дмитрий Евгеньевич согласовал Ваш период отпуска	2017-12-04 13:34:27.466	Частично согласован	Частично согласован	\N	187
983	Сотрудник добавил период отпуска.	2017-12-04 13:42:06.223	Новый	Новый	\N	390
984	Сотрудник добавил период отпуска.	2017-12-04 13:42:26.502	Новый	Новый	\N	391
985	Сотрудник добавил период отпуска.	2017-12-04 13:42:52.191	Новый	Новый	\N	392
986	Сотрудник добавил период отпуска.	2017-12-04 13:43:34.689	Новый	Новый	\N	393
987	Отпуск отправлен на согласование	2017-12-04 13:45:30.062	На согласовании	Новый	\N	393
988	Отпуск отправлен на согласование	2017-12-04 13:45:30.063	На согласовании	Новый	\N	392
989	Отпуск отправлен на согласование	2017-12-04 13:45:30.065	На согласовании	Новый	\N	391
990	Отпуск отправлен на согласование	2017-12-04 13:45:30.067	На согласовании	Новый	\N	390
991	Сотрудник добавил период отпуска.	2017-12-04 13:49:19.075	Новый	Новый	\N	394
992	Отпуск отправлен на согласование	2017-12-04 13:49:19.075	На согласовании	Новый	\N	394
993	Егорова Анастасия Юрьевна отклонил Ваш период отпуска	2017-12-04 13:49:19.076	Отклонён	На согласовании	\N	394
995	Сотрудник добавил период отпуска.	2017-12-04 13:55:07.975	Новый	Новый	\N	395
996	Отпуск отправлен на согласование	2017-12-04 13:55:16.528	На согласовании	Новый	\N	395
997	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 13:55:21.678	Частично согласован	На согласовании	\N	388
998	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 13:55:21.68	Частично согласован	На согласовании	\N	389
999	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 13:56:29.672	Частично согласован	На согласовании	\N	313
1000	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 13:56:29.673	Частично согласован	На согласовании	\N	307
1001	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 13:56:29.675	Частично согласован	На согласовании	\N	395
1002	Сотрудник добавил период отпуска.	2017-12-04 13:59:31.938	Новый	Новый	\N	396
1003	Отпуск отправлен на согласование	2017-12-04 14:00:07.789	На согласовании	Новый	\N	396
1005	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 14:02:47.443	Частично согласован	На согласовании	\N	350
1006	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 14:02:47.445	Частично согласован	На согласовании	\N	348
1007	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 14:02:47.446	Частично согласован	На согласовании	\N	351
1008	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 14:02:47.448	Частично согласован	На согласовании	\N	352
1009	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 14:02:47.455	Частично согласован	На согласовании	\N	354
1010	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 14:02:47.456	Частично согласован	На согласовании	\N	358
1011	Сотрудник добавил период отпуска.	2017-12-04 14:03:10.155	Новый	Новый	\N	397
1012	Отпуск отправлен на согласование	2017-12-04 14:03:28.451	На согласовании	Новый	\N	397
1013	Сотрудник добавил период отпуска.	2017-12-04 14:06:12.287	Новый	Новый	\N	398
1014	Сотрудник добавил период отпуска.	2017-12-04 14:06:39.673	Новый	Новый	\N	399
1015	Сотрудник добавил период отпуска.	2017-12-04 14:06:59.946	Новый	Новый	\N	400
1022	Отпуск отправлен на согласование	2017-12-04 14:07:13.732	На согласовании	Новый	\N	398
1023	Отпуск отправлен на согласование	2017-12-04 14:07:13.733	На согласовании	Новый	\N	399
1024	Отпуск отправлен на согласование	2017-12-04 14:07:13.735	На согласовании	Новый	\N	400
1025	Сотрудник добавил период отпуска.	2017-12-04 14:09:10.314	Новый	Новый	\N	404
1026	Сотрудник добавил период отпуска.	2017-12-04 14:09:37.113	Новый	Новый	\N	405
1027	Сотрудник добавил период отпуска.	2017-12-04 14:09:49.096	Новый	Новый	\N	406
1028	Сотрудник добавил период отпуска.	2017-12-04 14:10:15.379	Новый	Новый	\N	407
1029	Сотрудник добавил период отпуска.	2017-12-04 14:10:31.083	Новый	Новый	\N	408
1030	Отпуск отправлен на согласование	2017-12-04 14:11:54.011	На согласовании	Новый	\N	295
1031	Отпуск отправлен на согласование	2017-12-04 14:11:54.013	На согласовании	Новый	\N	404
1032	Отпуск отправлен на согласование	2017-12-04 14:11:54.015	На согласовании	Новый	\N	405
1033	Отпуск отправлен на согласование	2017-12-04 14:11:54.016	На согласовании	Новый	\N	406
1034	Отпуск отправлен на согласование	2017-12-04 14:11:54.017	На согласовании	Новый	\N	407
1035	Отпуск отправлен на согласование	2017-12-04 14:11:54.02	На согласовании	Новый	\N	408
1036	Сотрудник добавил период отпуска.	2017-12-04 14:21:00.341	Новый	Новый	\N	409
1037	Сотрудник добавил период отпуска.	2017-12-04 14:21:09.758	Новый	Новый	\N	410
1038	Сотрудник добавил период отпуска.	2017-12-04 14:21:43.198	Новый	Новый	\N	411
1039	Сотрудник добавил период отпуска.	2017-12-04 14:22:04.801	Новый	Новый	\N	412
1042	Отпуск отправлен на согласование	2017-12-04 14:22:21.851	На согласовании	Новый	\N	414
1043	Сотрудник добавил период отпуска.	2017-12-04 14:22:21.852	Новый	Новый	\N	414
1046	Отпуск отправлен на согласование	2017-12-04 14:22:21.98	На согласовании	Новый	\N	416
1047	Сотрудник добавил период отпуска.	2017-12-04 14:22:21.981	Новый	Новый	\N	416
1050	Отпуск отправлен на согласование	2017-12-04 14:22:22.114	На согласовании	Новый	\N	418
1051	Сотрудник добавил период отпуска.	2017-12-04 14:22:22.115	Новый	Новый	\N	418
1054	Отпуск отправлен на согласование	2017-12-04 14:22:22.236	На согласовании	Новый	\N	420
1055	Сотрудник добавил период отпуска.	2017-12-04 14:22:22.236	Новый	Новый	\N	420
1056	Отпуск отправлен на согласование	2017-12-04 14:22:22.259	На согласовании	Новый	\N	409
1057	Отпуск отправлен на согласование	2017-12-04 14:22:22.262	На согласовании	Новый	\N	410
1058	Отпуск отправлен на согласование	2017-12-04 14:22:22.264	На согласовании	Новый	\N	411
1059	Отпуск отправлен на согласование	2017-12-04 14:22:22.268	На согласовании	Новый	\N	412
1060	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-04 14:50:50.71	Частично согласован	На согласовании	\N	353
1061	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-04 14:50:50.712	Частично согласован	На согласовании	\N	355
1062	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-04 14:50:50.713	Частично согласован	На согласовании	\N	357
1063	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-04 14:50:50.716	Частично согласован	На согласовании	\N	360
1064	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-04 14:50:50.717	Частично согласован	На согласовании	\N	380
1065	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-04 14:51:15.051	Частично согласован	На согласовании	\N	233
1066	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-04 14:51:15.052	Частично согласован	На согласовании	\N	232
1067	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-04 14:52:50.358	Частично согласован	На согласовании	\N	103
1068	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-04 14:52:50.36	Частично согласован	На согласовании	\N	104
1188	Отпуск отправлен на согласование	2017-12-04 16:09:45.261	На согласовании	Новый	\N	436
1189	Отпуск отправлен на согласование	2017-12-04 16:09:45.263	На согласовании	Новый	\N	437
1190	Отпуск отправлен на согласование	2017-12-04 16:09:45.265	На согласовании	Новый	\N	438
1191	Отпуск отправлен на согласование	2017-12-04 16:09:45.267	На согласовании	Новый	\N	439
1192	Отпуск отправлен на согласование	2017-12-04 16:09:54.289	На согласовании	Новый	\N	434
1193	Сотрудник добавил период отпуска.	2017-12-04 16:11:11.029	Новый	Новый	\N	440
1194	Сотрудник добавил период отпуска.	2017-12-04 16:11:38.671	Новый	Новый	\N	441
1195	Сотрудник добавил период отпуска.	2017-12-04 16:16:10.823	Новый	Новый	\N	442
1241	Сотрудник добавил период отпуска.	2017-12-04 17:48:47.493	Новый	Новый	\N	451
1069	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-04 14:52:50.362	Частично согласован	На согласовании	\N	106
1070	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-04 14:52:50.364	Частично согласован	На согласовании	\N	241
1071	Видавский Сергей Алексеевич согласовал Ваш период отпуска	2017-12-04 14:52:50.367	Частично согласован	На согласовании	\N	107
1072	Сотрудник добавил период отпуска.	2017-12-04 14:52:53.301	Новый	Новый	\N	421
1073	Сотрудник добавил период отпуска.	2017-12-04 14:53:33.863	Новый	Новый	\N	422
1074	Сотрудник добавил период отпуска.	2017-12-04 14:54:01.511	Новый	Новый	\N	423
1075	Сотрудник добавил период отпуска.	2017-12-04 14:54:30.687	Новый	Новый	\N	424
1076	Видавский Сергей Алексеевич отклонил Ваш период отпуска	2017-12-04 14:54:31.664	Отклонён	На согласовании	\N	245
1077	Видавский Сергей Алексеевич отклонил Ваш период отпуска	2017-12-04 14:54:31.666	Отклонён	На согласовании	\N	247
1078	Видавский Сергей Алексеевич отклонил Ваш период отпуска	2017-12-04 14:54:31.668	Отклонён	На согласовании	\N	249
1079	Видавский Сергей Алексеевич отклонил Ваш период отпуска	2017-12-04 14:54:31.669	Отклонён	На согласовании	\N	251
1080	Сотрудник добавил период отпуска.	2017-12-04 14:54:45.286	Новый	Новый	\N	425
1081	Отпуск отправлен на согласование	2017-12-04 14:56:08.997	На согласовании	Новый	\N	425
1082	Отпуск отправлен на согласование	2017-12-04 14:56:08.998	На согласовании	Новый	\N	424
1083	Отпуск отправлен на согласование	2017-12-04 14:56:08.999	На согласовании	Новый	\N	423
1084	Отпуск отправлен на согласование	2017-12-04 14:56:09.001	На согласовании	Новый	\N	421
1085	Отпуск отправлен на согласование	2017-12-04 14:56:09.002	На согласовании	Новый	\N	422
1086	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:05:51.41	Частично согласован	На согласовании	\N	61
1087	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:05:51.413	Согласован	Частично согласован	\N	169
1088	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:05:51.415	Частично согласован	На согласовании	\N	180
1089	Ручкин Виталий Сергеевич отклонил Ваш период отпуска	2017-12-04 15:06:35.389	Отклонён	На согласовании	\N	181
1092	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:09:08.52	Частично согласован	На согласовании	\N	187
1093	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:09:08.522	Частично согласован	На согласовании	\N	186
1094	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:09:08.523	Частично согласован	На согласовании	\N	130
1095	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:09:08.524	Частично согласован	Отклонён	\N	181
1096	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:09:08.526	Частично согласован	Частично согласован	\N	180
1097	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:09:08.527	Частично согласован	На согласовании	\N	167
1098	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:09:08.529	Частично согласован	На согласовании	\N	166
1099	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:09:08.53	Частично согласован	На согласовании	\N	168
1100	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:09:08.531	Частично согласован	На согласовании	\N	397
1101	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:09:08.532	Частично согласован	На согласовании	\N	314
1107	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:11:50.819	Частично согласован	На согласовании	\N	149
1108	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:11:50.821	Частично согласован	На согласовании	\N	147
1109	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:11:50.823	Частично согласован	На согласовании	\N	154
1110	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:11:50.825	Частично согласован	На согласовании	\N	151
1111	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:11:50.826	Частично согласован	На согласовании	\N	141
1112	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:11:50.827	Частично согласован	На согласовании	\N	137
1113	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:11:50.828	Частично согласован	На согласовании	\N	139
1114	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:11:50.83	Частично согласован	На согласовании	\N	140
1115	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:11:50.831	Частично согласован	На согласовании	\N	62
1116	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:11:50.832	Частично согласован	На согласовании	\N	64
1117	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:11:50.834	Частично согласован	На согласовании	\N	185
1118	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:11:50.835	Частично согласован	На согласовании	\N	183
1119	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:11:50.836	Частично согласован	На согласовании	\N	184
1120	Ручкин Виталий Сергеевич отклонил Ваш период отпуска	2017-12-04 15:12:07.093	Отклонён	На согласовании	\N	63
1121	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:16:06.173	Согласован	Частично согласован	\N	54
1122	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:16:06.175	Согласован	Частично согласован	\N	49
1123	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:16:06.176	Согласован	Частично согласован	\N	57
1124	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:16:06.178	Согласован	Частично согласован	\N	48
1125	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:16:06.179	Согласован	Частично согласован	\N	194
1126	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:16:06.181	Согласован	Частично согласован	\N	170
1127	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:16:06.182	Согласован	Частично согласован	\N	169
1128	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:16:06.183	Согласован	Частично согласован	\N	171
1129	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:16:06.184	Согласован	Частично согласован	\N	116
1130	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:16:06.186	Согласован	Частично согласован	\N	115
1131	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:16:06.187	Согласован	Частично согласован	\N	111
1132	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:16:06.188	Согласован	Частично согласован	\N	112
1133	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:16:06.19	Согласован	Частично согласован	\N	203
1134	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:16:06.191	Согласован	Частично согласован	\N	110
1135	Ручкин Виталий Сергеевич согласовал Ваш период отпуска	2017-12-04 15:16:06.192	Согласован	Частично согласован	\N	109
1136	Ручкин Виталий Сергеевич отклонил Ваш период отпуска	2017-12-04 15:16:25.314	Отклонён	Частично согласован	\N	51
1137	Сотрудник добавил период отпуска.	2017-12-04 15:18:57.06	Новый	Новый	\N	426
1138	Сотрудник добавил период отпуска.	2017-12-04 15:19:11.024	Новый	Новый	\N	427
1139	Сотрудник добавил период отпуска.	2017-12-04 15:20:09.608	Новый	Новый	\N	428
1140	Сотрудник добавил период отпуска.	2017-12-04 15:20:45.527	Новый	Новый	\N	429
1141	Отпуск отправлен на согласование	2017-12-04 15:20:50.489	На согласовании	Новый	\N	429
1142	Отпуск отправлен на согласование	2017-12-04 15:20:50.491	На согласовании	Новый	\N	426
1143	Отпуск отправлен на согласование	2017-12-04 15:20:50.493	На согласовании	Новый	\N	427
1144	Отпуск отправлен на согласование	2017-12-04 15:20:50.494	На согласовании	Новый	\N	428
1145	Сотрудник добавил период отпуска.	2017-12-04 15:23:49.544	Новый	Новый	\N	430
1146	Отпуск отправлен на согласование	2017-12-04 15:23:58.25	На согласовании	Новый	\N	430
1147	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:25:49.489	Частично согласован	Частично согласован	\N	62
1240	Отпуск отправлен на согласование	2017-12-04 17:48:47.471	На согласовании	Новый	\N	451
1148	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:25:49.49	Согласован	Частично согласован	\N	61
1149	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:25:49.492	Частично согласован	Частично согласован	\N	186
1150	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:25:49.493	Частично согласован	Частично согласован	\N	130
1151	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:25:49.495	Частично согласован	Частично согласован	\N	187
1152	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:25:49.496	Частично согласован	Частично согласован	\N	184
1153	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:25:49.498	Частично согласован	Частично согласован	\N	185
1154	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:25:49.499	Частично согласован	Частично согласован	\N	183
1155	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:25:49.501	Частично согласован	Частично согласован	\N	181
1156	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:25:49.502	Частично согласован	Частично согласован	\N	180
1157	Артемьев Иван Сергеевич отклонил Ваш период отпуска	2017-12-04 15:44:13.346	Отклонён	Частично согласован	\N	235
1159	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:45:27.249	Частично согласован	Частично согласован	\N	167
1160	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:45:27.25	Частично согласован	Частично согласован	\N	168
1161	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:45:27.252	Частично согласован	Частично согласован	\N	166
1162	Сотрудник добавил период отпуска.	2017-12-04 15:46:30.805	Новый	Новый	\N	431
1163	Сотрудник добавил период отпуска.	2017-12-04 15:47:05.064	Новый	Новый	\N	432
1164	Отпуск отправлен на согласование	2017-12-04 15:47:33.248	На согласовании	Новый	\N	431
1165	Отпуск отправлен на согласование	2017-12-04 15:47:33.25	На согласовании	Новый	\N	432
1166	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:48:03.777	Частично согласован	Частично согласован	\N	140
1167	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:48:03.779	Частично согласован	Частично согласован	\N	141
1168	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:48:03.781	Частично согласован	Частично согласован	\N	139
1169	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:48:03.782	Частично согласован	Частично согласован	\N	137
1170	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:48:03.784	Частично согласован	Частично согласован	\N	397
1171	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:48:03.786	Частично согласован	Частично согласован	\N	314
1172	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:48:03.787	Частично согласован	Частично согласован	\N	151
1173	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:48:03.789	Частично согласован	Частично согласован	\N	154
1174	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:48:03.791	Частично согласован	Частично согласован	\N	147
1175	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 15:48:03.793	Частично согласован	Частично согласован	\N	149
1176	Сотрудник добавил период отпуска.	2017-12-04 15:53:45.406	Новый	Новый	\N	433
1177	Отпуск отправлен на согласование	2017-12-04 15:56:44.798	На согласовании	Новый	\N	433
1178	Егорова Анастасия Юрьевна отклонил Ваш период отпуска	2017-12-04 16:01:37.481	Отклонён	На согласовании	\N	316
1179	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 16:07:26.074	Согласован	На согласовании	\N	280
1180	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 16:07:26.076	Согласован	На согласовании	\N	281
1181	Сотрудник добавил период отпуска.	2017-12-04 16:08:31.529	Новый	Новый	\N	434
1182	Сотрудник добавил период отпуска.	2017-12-04 16:08:42.306	Новый	Новый	\N	435
1183	Сотрудник добавил период отпуска.	2017-12-04 16:08:54.26	Новый	Новый	\N	436
1184	Сотрудник добавил период отпуска.	2017-12-04 16:09:04.2	Новый	Новый	\N	437
1185	Сотрудник добавил период отпуска.	2017-12-04 16:09:13.682	Новый	Новый	\N	438
1186	Сотрудник добавил период отпуска.	2017-12-04 16:09:22.196	Новый	Новый	\N	439
1187	Отпуск отправлен на согласование	2017-12-04 16:09:45.259	На согласовании	Новый	\N	435
1197	Королёва Елена Ильинична отклонил Ваш период отпуска	2017-12-04 16:16:36.384	Отклонён	На согласовании	\N	373
1198	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 16:17:08.231	Согласован	На согласовании	\N	374
1199	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 16:17:08.233	Согласован	На согласовании	\N	375
1200	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 16:27:40.089	Согласован	На согласовании	\N	39
1201	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 16:47:09.019	Частично согласован	Частично согласован	\N	380
1202	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 16:47:09.021	Частично согласован	Частично согласован	\N	360
1203	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 16:47:09.023	Частично согласован	Частично согласован	\N	357
1204	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 16:47:09.025	Частично согласован	Частично согласован	\N	355
1205	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 16:47:09.026	Частично согласован	Частично согласован	\N	353
1206	Сотрудник добавил период отпуска.	2017-12-04 16:53:14.275	Новый	Новый	\N	443
1207	Сотрудник добавил период отпуска.	2017-12-04 16:59:27.365	Новый	Новый	\N	444
1208	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 16:59:47.938	Согласован	На согласовании	\N	273
1209	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 16:59:47.941	Согласован	На согласовании	\N	274
1210	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:00:23.308	Согласован	На согласовании	\N	285
1211	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:01:10.756	Согласован	На согласовании	\N	121
1212	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:01:10.758	Согласован	На согласовании	\N	269
1213	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:02:17.178	Согласован	На согласовании	\N	120
1214	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:02:17.182	Согласован	На согласовании	\N	119
1215	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:02:17.184	Согласован	На согласовании	\N	117
1216	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:03:27.404	Согласован	На согласовании	\N	122
1217	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:03:27.406	Согласован	На согласовании	\N	294
1218	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:04:49.61	Согласован	На согласовании	\N	58
1219	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:04:49.612	Согласован	На согласовании	\N	95
1220	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:04:49.614	Согласован	На согласовании	\N	96
1221	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:04:49.616	Согласован	На согласовании	\N	94
1222	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:04:49.617	Согласован	На согласовании	\N	93
1223	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:04:49.619	Согласован	На согласовании	\N	92
1224	Сотрудник добавил период отпуска.	2017-12-04 17:13:05.751	Новый	Новый	\N	445
1225	Сотрудник добавил период отпуска.	2017-12-04 17:13:43.921	Новый	Новый	\N	446
1226	Отпуск отправлен на согласование	2017-12-04 17:14:19.455	На согласовании	Новый	\N	446
1227	Отпуск отправлен на согласование	2017-12-04 17:14:19.456	На согласовании	Новый	\N	445
1228	Сотрудник добавил период отпуска.	2017-12-04 17:34:33.335	Новый	Новый	\N	447
1229	Отпуск отправлен на согласование	2017-12-04 17:34:51.922	На согласовании	Новый	\N	447
1230	Сотрудник добавил период отпуска.	2017-12-04 17:36:39.278	Новый	Новый	\N	448
1231	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:40:16.305	Согласован	На согласовании	\N	429
1232	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:40:16.307	Согласован	На согласовании	\N	426
1233	Сотрудник добавил период отпуска.	2017-12-04 17:41:17.805	Новый	Новый	\N	449
1234	Отпуск отправлен на согласование	2017-12-04 17:41:25.755	На согласовании	Новый	\N	449
1237	Сотрудник добавил период отпуска.	2017-12-04 17:45:04.591	Новый	Новый	\N	450
1238	Отпуск отправлен на согласование	2017-12-04 17:45:36.582	На согласовании	Новый	\N	448
1239	Отпуск отправлен на согласование	2017-12-04 17:45:36.584	На согласовании	Новый	\N	450
1242	Отпуск отправлен на согласование	2017-12-04 17:48:47.496	На согласовании	Новый	\N	452
1243	Сотрудник добавил период отпуска.	2017-12-04 17:48:47.497	Новый	Новый	\N	452
1244	Отпуск отправлен на согласование	2017-12-04 17:48:47.499	На согласовании	Новый	\N	453
1245	Сотрудник добавил период отпуска.	2017-12-04 17:48:47.499	Новый	Новый	\N	453
1246	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 17:58:43.696	Согласован	На согласовании	\N	59
1247	Отпуск отправлен на согласование	2017-12-04 17:58:55.872	На согласовании	Новый	\N	442
1248	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 18:01:09.753	Частично согласован	На согласовании	\N	113
1249	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 18:01:09.754	Частично согласован	На согласовании	\N	114
1281	Сотрудник добавил период отпуска.	2017-12-04 18:27:45.206	Новый	Новый	\N	454
1250	Королёва Елена Ильинична согласовал Ваш период отпуска	2017-12-04 18:01:12.477	Согласован	На согласовании	\N	123
1251	Артемьев Иван Сергеевич отклонил Ваш период отпуска	2017-12-04 18:21:28.734	Отклонён	На согласовании	\N	453
1252	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.92	Частично согласован	На согласовании	\N	234
1253	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.922	Частично согласован	На согласовании	\N	86
1254	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.923	Частично согласован	На согласовании	\N	65
1255	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.924	Частично согласован	На согласовании	\N	87
1256	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.925	Частично согласован	На согласовании	\N	258
1257	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.926	Частично согласован	На согласовании	\N	261
1258	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.927	Частично согласован	На согласовании	\N	88
1259	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.928	Частично согласован	На согласовании	\N	448
1260	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.929	Частично согласован	На согласовании	\N	431
1261	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.93	Частично согласован	На согласовании	\N	66
1262	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.931	Частично согласован	На согласовании	\N	432
1263	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.932	Частично согласован	На согласовании	\N	450
1264	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.933	Частично согласован	На согласовании	\N	259
1265	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.934	Частично согласован	На согласовании	\N	237
1266	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.935	Частично согласован	На согласовании	\N	60
1267	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.936	Частично согласован	На согласовании	\N	90
1268	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:23:20.937	Частично согласован	На согласовании	\N	260
1269	Артемьев Иван Сергеевич отклонил Ваш период отпуска	2017-12-04 18:24:05.671	Отклонён	На согласовании	\N	451
1270	Артемьев Иван Сергеевич отклонил Ваш период отпуска	2017-12-04 18:24:05.672	Отклонён	На согласовании	\N	452
1271	Артемьев Иван Сергеевич отклонил Ваш период отпуска	2017-12-04 18:24:58.795	Отклонён	На согласовании	\N	391
1272	Артемьев Иван Сергеевич отклонил Ваш период отпуска	2017-12-04 18:24:58.796	Отклонён	На согласовании	\N	390
1273	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:25:49.868	Частично согласован	На согласовании	\N	213
1274	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:25:49.87	Частично согласован	На согласовании	\N	216
1275	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:25:49.871	Частично согласован	На согласовании	\N	393
1276	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:25:49.872	Частично согласован	На согласовании	\N	392
1277	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:25:49.874	Частично согласован	На согласовании	\N	370
1278	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:25:49.875	Частично согласован	На согласовании	\N	371
1279	Андриановская Маргарита Владимировна отклонил Ваш период отпуска	2017-12-04 18:26:45.376	Отклонён	На согласовании	\N	148
1280	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 18:27:43.034	Частично согласован	На согласовании	\N	433
1282	Отпуск отправлен на согласование	2017-12-04 18:27:51.487	На согласовании	Новый	\N	454
1283	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:28:20.194	Частично согласован	На согласовании	\N	410
1284	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:28:20.195	Частично согласован	На согласовании	\N	412
1285	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:28:20.196	Частично согласован	На согласовании	\N	409
1286	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:28:20.198	Частично согласован	На согласовании	\N	411
1287	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:28:20.199	Частично согласован	На согласовании	\N	388
1288	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:28:20.201	Частично согласован	На согласовании	\N	389
1289	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:28:50.211	Частично согласован	На согласовании	\N	348
1290	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:28:50.213	Частично согласован	На согласовании	\N	350
1291	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:28:50.214	Частично согласован	На согласовании	\N	351
1292	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:28:50.215	Частично согласован	На согласовании	\N	352
1293	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:28:50.217	Частично согласован	На согласовании	\N	354
1294	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:28:50.218	Частично согласован	На согласовании	\N	358
1295	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:30:01.585	Частично согласован	На согласовании	\N	344
1296	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:30:01.588	Частично согласован	На согласовании	\N	345
1297	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:30:01.589	Частично согласован	На согласовании	\N	317
1298	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:30:01.591	Частично согласован	На согласовании	\N	304
1299	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:30:37.002	Частично согласован	На согласовании	\N	85
1300	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:30:37.004	Частично согласован	На согласовании	\N	430
1301	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:30:37.006	Частично согласован	На согласовании	\N	278
1302	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:30:37.007	Частично согласован	На согласовании	\N	326
1303	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:30:37.008	Частично согласован	На согласовании	\N	319
1304	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:30:37.01	Частично согласован	На согласовании	\N	81
1305	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 18:30:43.792	Частично согласован	На согласовании	\N	145
1306	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 18:30:43.794	Частично согласован	На согласовании	\N	449
1307	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 18:30:43.796	Частично согласован	На согласовании	\N	447
1308	Андриановская Маргарита Владимировна согласовал Ваш период отпуска	2017-12-04 18:30:43.798	Частично согласован	На согласовании	\N	152
1309	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:30:54.108	Частично согласован	На согласовании	\N	313
1310	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 18:30:54.11	Частично согласован	На согласовании	\N	307
1311	Сотрудник добавил период отпуска.	2017-12-04 18:30:58.141	Новый	Новый	\N	455
1312	Отпуск отправлен на согласование	2017-12-04 18:31:06.867	На согласовании	Новый	\N	455
1313	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:33:50.023	Частично согласован	На согласовании	\N	455
1314	Отпуск отправлен на согласование	2017-12-04 18:46:56.283	На согласовании	Новый	\N	444
1315	Отпуск отправлен на согласование	2017-12-04 18:46:56.285	На согласовании	Новый	\N	443
1318	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:54:11.974	Частично согласован	Частично согласован	\N	350
1319	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:54:11.976	Частично согласован	На согласовании	\N	307
1320	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:54:11.977	Частично согласован	Частично согласован	\N	351
1321	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:54:11.98	Частично согласован	Частично согласован	\N	352
1322	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:54:11.982	Частично согласован	Частично согласован	\N	354
1323	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:54:11.983	Частично согласован	На согласовании	\N	346
1324	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:54:11.984	Частично согласован	Частично согласован	\N	358
1325	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:54:11.985	Частично согласован	Частично согласован	\N	395
1326	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:54:11.986	Частично согласован	На согласовании	\N	336
1327	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:54:11.987	Частично согласован	На согласовании	\N	313
1328	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:54:50.137	Согласован	Частично согласован	\N	353
1329	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:54:50.138	Согласован	Частично согласован	\N	355
1330	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:54:50.139	Согласован	Частично согласован	\N	380
1331	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:54:50.14	Согласован	Частично согласован	\N	360
1332	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 18:54:50.141	Согласован	Частично согласован	\N	357
1334	Отпуск отправлен на согласование	2017-12-04 19:03:43.04	На согласовании	Новый	\N	347
1335	Артемьев Иван Сергеевич согласовал Ваш период отпуска	2017-12-04 19:09:25.817	Частично согласован	На согласовании	\N	347
1337	Сотрудник добавил период отпуска.	2017-12-04 19:15:44.107	Новый	Новый	\N	458
1338	Отпуск отправлен на согласование	2017-12-04 19:15:52.171	На согласовании	Новый	\N	458
1339	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.613	Частично согласован	Частично согласован	\N	86
1340	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.614	Частично согласован	На согласовании	\N	72
1341	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.615	Частично согласован	Частично согласован	\N	65
1342	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.617	Частично согласован	Частично согласован	\N	234
1343	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.618	Частично согласован	Частично согласован	\N	87
1344	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.619	Частично согласован	Частично согласован	\N	258
1345	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.62	Частично согласован	Частично согласован	\N	261
1346	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.623	Частично согласован	На согласовании	\N	73
1347	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.624	Частично согласован	На согласовании	\N	191
1348	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.626	Частично согласован	Частично согласован	\N	88
1349	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.627	Частично согласован	На согласовании	\N	101
1350	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.629	Частично согласован	Частично согласован	\N	448
1351	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.631	Частично согласован	Частично согласован	\N	431
1352	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.632	Частично согласован	Частично согласован	\N	66
1353	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.634	Частично согласован	Частично согласован	\N	432
1354	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.635	Частично согласован	Частично согласован	\N	450
1355	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.637	Частично согласован	Частично согласован	\N	259
1356	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.638	Частично согласован	На согласовании	\N	69
1357	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.639	Частично согласован	Частично согласован	\N	237
1358	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.64	Частично согласован	Частично согласован	\N	60
1359	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.641	Частично согласован	Частично согласован	\N	90
1360	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.643	Частично согласован	Частично согласован	\N	260
1361	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.644	Частично согласован	На согласовании	\N	71
1362	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:23:14.645	Частично согласован	На согласовании	\N	311
1363	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:28:49.935	Частично согласован	На согласовании	\N	74
1364	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:28:49.937	Частично согласован	На согласовании	\N	102
1365	Волынин Денис Николаевич отклонил Ваш период отпуска	2017-12-04 19:30:42.915	Отклонён	На согласовании	\N	308
1366	Волынин Денис Николаевич отклонил Ваш период отпуска	2017-12-04 19:41:21.532	Отклонён	На согласовании	\N	251
1367	Волынин Денис Николаевич отклонил Ваш период отпуска	2017-12-04 19:41:21.533	Отклонён	На согласовании	\N	247
1368	Волынин Денис Николаевич отклонил Ваш период отпуска	2017-12-04 19:41:21.535	Отклонён	На согласовании	\N	245
1369	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:43:01.722	Частично согласован	На согласовании	\N	249
1370	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:43:01.724	Частично согласован	На согласовании	\N	241
1371	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:43:01.726	Частично согласован	Частично согласован	\N	231
1372	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:43:01.728	Частично согласован	На согласовании	\N	233
1373	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:43:01.73	Частично согласован	На согласовании	\N	232
1374	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:43:01.732	Частично согласован	На согласовании	\N	107
1375	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:43:01.733	Частично согласован	На согласовании	\N	106
1376	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:43:01.735	Частично согласован	На согласовании	\N	104
1377	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:43:01.737	Частично согласован	На согласовании	\N	103
1378	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 19:43:27.103	Частично согласован	На согласовании	\N	336
1379	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 19:43:27.107	Частично согласован	На согласовании	\N	346
1380	Егорова Анастасия Юрьевна согласовал Ваш период отпуска	2017-12-04 19:43:27.111	Частично согласован	На согласовании	\N	377
1381	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:43:29.246	Частично согласован	На согласовании	\N	124
1382	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:43:29.247	Частично согласован	На согласовании	\N	125
1383	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:43:29.249	Частично согласован	На согласовании	\N	126
1384	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:43:29.25	Частично согласован	На согласовании	\N	127
1385	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:43:29.251	Частично согласован	На согласовании	\N	128
1386	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:43:29.252	Частично согласован	На согласовании	\N	129
1387	Волынин Денис Николаевич согласовал Ваш период отпуска	2017-12-04 19:44:45.82	Частично согласован	На согласовании	\N	68
1388	Егорова Анастасия Юрьевна отклонил Ваш период отпуска	2017-12-04 19:44:58.383	Отклонён	На согласовании	\N	414
1389	Егорова Анастасия Юрьевна отклонил Ваш период отпуска	2017-12-04 19:44:58.384	Отклонён	На согласовании	\N	416
1390	Егорова Анастасия Юрьевна отклонил Ваш период отпуска	2017-12-04 19:44:58.386	Отклонён	На согласовании	\N	418
1391	Егорова Анастасия Юрьевна отклонил Ваш период отпуска	2017-12-04 19:44:58.387	Отклонён	На согласовании	\N	420
1392	Егорова Анастасия Юрьевна отклонил Ваш период отпуска	2017-12-04 19:44:58.388	Отклонён	На согласовании	\N	382
1393	Егорова Анастасия Юрьевна отклонил Ваш период отпуска	2017-12-04 19:44:58.389	Отклонён	На согласовании	\N	383
1394	Егорова Анастасия Юрьевна отклонил Ваш период отпуска	2017-12-04 19:44:58.39	Отклонён	На согласовании	\N	384
1395	Егорова Анастасия Юрьевна отклонил Ваш период отпуска	2017-12-04 19:44:58.392	Отклонён	На согласовании	\N	385
1396	Егорова Анастасия Юрьевна отклонил Ваш период отпуска	2017-12-04 19:44:58.394	Отклонён	На согласовании	\N	381
1397	Отпуск отправлен на согласование	2017-12-04 19:46:04.145	На согласовании	Новый	\N	134
1398	Отпуск отправлен на согласование	2017-12-04 19:46:04.147	На согласовании	Новый	\N	135
1399	Отпуск отправлен на согласование	2017-12-04 19:46:04.149	На согласовании	Новый	\N	136
1400	Сотрудник добавил период отпуска.	2017-12-04 19:50:01.847	Новый	Новый	\N	459
1401	Отпуск отправлен на согласование	2017-12-04 19:50:45.867	На согласовании	Новый	\N	459
1402	Кошкарёв Максим Игоревич согласовал Ваш период отпуска	2017-12-04 19:50:55.407	Частично согласован	На согласовании	\N	234
1403	Кошкарёв Максим Игоревич согласовал Ваш период отпуска	2017-12-04 19:50:55.409	Частично согласован	На согласовании	\N	237
1404	Кошкарёв Максим Игоревич согласовал Ваш период отпуска	2017-12-04 19:51:55.492	Частично согласован	На согласовании	\N	134
1405	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 23:19:25.758	Частично согласован	На согласовании	\N	433
1406	Абрамкин Максим Андреевич согласовал Ваш период отпуска	2017-12-04 23:20:17.863	Частично согласован	Частично согласован	\N	433
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
147	10
147	6
147	5
147	1
147	8
147	3
147	2
147	7
6	7
148	27
6	10
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
13	2017-12-04 15:29:50.585	PROJECT_ROLE_SPECIAL_TYPE_REGULAR	Администратор Linux	Администратор Linux	\N
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
27	2017-12-04 15:27:55.188	Эксплуатация (Администраторы Linux)	\N	\N	\N
28	2017-12-04 15:28:08.989	Эксплуатация (Поддержка)	\N	\N	\N
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

SELECT pg_catalog.setval('seq_emp_id', 148, true);


--
-- Name: seq_hol_period_id; Type: SEQUENCE SET; Schema: public; Owner: holidays
--

SELECT pg_catalog.setval('seq_hol_period_id', 459, true);


--
-- Name: seq_hol_period_neg_history_id; Type: SEQUENCE SET; Schema: public; Owner: holidays
--

SELECT pg_catalog.setval('seq_hol_period_neg_history_id', 1406, true);


--
-- Name: seq_hol_period_neg_status_id; Type: SEQUENCE SET; Schema: public; Owner: holidays
--

SELECT pg_catalog.setval('seq_hol_period_neg_status_id', 5, true);


--
-- Name: seq_proj_role_id; Type: SEQUENCE SET; Schema: public; Owner: holidays
--

SELECT pg_catalog.setval('seq_proj_role_id', 13, true);


--
-- Name: seq_team_id; Type: SEQUENCE SET; Schema: public; Owner: holidays
--

SELECT pg_catalog.setval('seq_team_id', 28, true);


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

