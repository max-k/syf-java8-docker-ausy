CREATE ROLE syf LOGIN
  ENCRYPTED PASSWORD 'md50f6d9196c2901b1fad7fa865b1cbfc74'
  SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION;

CREATE DATABASE syf
  WITH ENCODING='UTF8'
       OWNER=syf
       CONNECTION LIMIT=-1;

	   
REVOKE ALL ON DATABASE syf FROM syf;
GRANT ALL ON DATABASE syf TO syf;

--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.5
-- Dumped by pg_dump version 9.3.5
-- Started on 2017-01-22 19:12:26

--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.5
-- Dumped by pg_dump version 9.3.5
-- Started on 2017-01-22 19:20:56

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;


CREATE SCHEMA rps;


ALTER SCHEMA rps OWNER TO syf;

--
-- TOC entry 174 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1954 (class 0 OID 0)
-- Dependencies: 174
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = rps, pg_catalog;

--
-- TOC entry 173 (class 1259 OID 24640)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: rps; Owner: rps
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rps.hibernate_sequence OWNER TO syf;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 24620)
-- Name: players; Type: TABLE; Schema: rps; Owner: rps; Tablespace: 
--

CREATE TABLE rps.players (
    id bigint NOT NULL,
    name character varying(30) NOT NULL,
    playertype character varying(20) NOT NULL
);


ALTER TABLE rps.players OWNER TO syf;

--
-- TOC entry 172 (class 1259 OID 24625)
-- Name: results; Type: TABLE; Schema: rps; Owner: rps; Tablespace: 
--

CREATE TABLE rps.results (
    id bigint NOT NULL,
    playeronemove character varying(20) NOT NULL,
    playeroneresult character varying(20) NOT NULL,
    playertwomove character varying(20) NOT NULL,
    playertworesult character varying(20) NOT NULL,
    playeroneid bigint,
    playertwoid bigint
);


ALTER TABLE rps.results OWNER TO syf;

--
-- TOC entry 1956 (class 0 OID 0)
-- Dependencies: 173
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: rps; Owner: rps
--

SELECT pg_catalog.setval('hibernate_sequence', 53, true);


--
-- TOC entry 1943 (class 0 OID 24620)
-- Dependencies: 171
-- Data for Name: players; Type: TABLE DATA; Schema: rps; Owner: rps
--

INSERT INTO rps.players (id, name, playertype) VALUES (11, 'Darlys', 'HUMAN');
INSERT INTO rps.players (id, name, playertype) VALUES (12, 'COMPUTER', 'COMPUTER');
INSERT INTO rps.players (id, name, playertype) VALUES (13, 'Oriana', 'HUMAN');
INSERT INTO rps.players (id, name, playertype) VALUES (31, 'Amada', 'HUMAN');
INSERT INTO rps.players (id, name, playertype) VALUES (52, 'Pedro', 'HUMAN');


--
-- TOC entry 1944 (class 0 OID 24625)
-- Dependencies: 172
-- Data for Name: results; Type: TABLE DATA; Schema: rps; Owner: rps
--

INSERT INTO rps.results (id, playeronemove, playeroneresult, playertwomove, playertworesult, playeroneid, playertwoid) VALUES (43, 'PAPER', 'LOSES', 'SCISSOR', 'WINS', 11, 12);
INSERT INTO rps.results (id, playeronemove, playeroneresult, playertwomove, playertworesult, playeroneid, playertwoid) VALUES (44, 'ROCK', 'LOSES', 'PAPER', 'WINS', 11, 12);
INSERT INTO rps.results (id, playeronemove, playeroneresult, playertwomove, playertworesult, playeroneid, playertwoid) VALUES (45, 'SCISSOR', 'LOSES', 'SCISSOR', 'LOSES', 11, 12);
INSERT INTO rps.results (id, playeronemove, playeroneresult, playertwomove, playertworesult, playeroneid, playertwoid) VALUES (46, 'PAPER', 'LOSES', 'SCISSOR', 'WINS', 11, 12);
INSERT INTO rps.results (id, playeronemove, playeroneresult, playertwomove, playertworesult, playeroneid, playertwoid) VALUES (47, 'ROCK', 'LOSES', 'ROCK', 'LOSES', 11, 12);
INSERT INTO rps.results (id, playeronemove, playeroneresult, playertwomove, playertworesult, playeroneid, playertwoid) VALUES (48, 'PAPER', 'LOSES', 'PAPER', 'LOSES', 13, 12);
INSERT INTO rps.results (id, playeronemove, playeroneresult, playertwomove, playertworesult, playeroneid, playertwoid) VALUES (49, 'PAPER', 'LOSES', 'SCISSOR', 'WINS', 13, 12);
INSERT INTO rps.results (id, playeronemove, playeroneresult, playertwomove, playertworesult, playeroneid, playertwoid) VALUES (50, 'PAPER', 'LOSES', 'SCISSOR', 'WINS', 13, 12);
INSERT INTO rps.results (id, playeronemove, playeroneresult, playertwomove, playertworesult, playeroneid, playertwoid) VALUES (51, 'PAPER', 'LOSES', 'SCISSOR', 'WINS', 11, 12);
INSERT INTO rps.results (id, playeronemove, playeroneresult, playertwomove, playertworesult, playeroneid, playertwoid) VALUES (53, 'PAPER', 'WINS', 'ROCK', 'LOSES', 52, 12);


--
-- TOC entry 1829 (class 2606 OID 24624)
-- Name: pk_players; Type: CONSTRAINT; Schema: rps; Owner: rps; Tablespace: 
--

ALTER TABLE ONLY rps.players
    ADD CONSTRAINT pk_players PRIMARY KEY (id);


--
-- TOC entry 1833 (class 2606 OID 24629)
-- Name: pk_results; Type: CONSTRAINT; Schema: rps; Owner: rps; Tablespace: 
--

ALTER TABLE ONLY rps.results
    ADD CONSTRAINT pk_results PRIMARY KEY (id);


--
-- TOC entry 1831 (class 2606 OID 24655)
-- Name: uk_players_name_and_type; Type: CONSTRAINT; Schema: rps; Owner: rps; Tablespace: 
--

ALTER TABLE ONLY rps.players
    ADD CONSTRAINT uk_players_name_and_type UNIQUE (name, playertype);


--
-- TOC entry 1834 (class 2606 OID 24647)
-- Name: fk_result_playertwo; Type: FK CONSTRAINT; Schema: rps; Owner: rps
--

ALTER TABLE ONLY rps.results
    ADD CONSTRAINT fk_result_playertwo FOREIGN KEY (playertwoid) REFERENCES players(id);


--
-- TOC entry 1835 (class 2606 OID 24642)
-- Name: fk_results_playerone; Type: FK CONSTRAINT; Schema: rps; Owner: rps
--

ALTER TABLE ONLY rps.results
    ADD CONSTRAINT fk_results_playerone FOREIGN KEY (playeroneid) REFERENCES players(id);


--
-- TOC entry 1952 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- TOC entry 1953 (class 0 OID 0)
-- Dependencies: 7
-- Name: rps; Type: ACL; Schema: -; Owner: rps
--

REVOKE ALL ON SCHEMA rps FROM PUBLIC;
REVOKE ALL ON SCHEMA rps FROM syf;
GRANT ALL ON SCHEMA rps TO syf;


--
-- TOC entry 1955 (class 0 OID 0)
-- Dependencies: 172
-- Name: results; Type: ACL; Schema: rps; Owner: rps
--

REVOKE ALL ON TABLE results FROM PUBLIC;
REVOKE ALL ON TABLE results FROM syf;
GRANT ALL ON TABLE results TO syf;


-- Completed on 2017-01-22 19:20:56
-- PostgreSQL database dump complete
--

