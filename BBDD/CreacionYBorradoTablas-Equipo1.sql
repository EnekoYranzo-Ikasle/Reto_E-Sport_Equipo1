-- Equipo 1

-- BORRADO DE TABLAS
DROP TABLE competiciones CASCADE CONSTRAINTS;
DROP TABLE jornadas CASCADE CONSTRAINTS;
DROP TABLE equipos CASCADE CONSTRAINTS;
DROP TABLE jugadores CASCADE CONSTRAINTS;
DROP TABLE enfrentamientos CASCADE CONSTRAINTS;
DROP TABLE usuarios CASCADE CONSTRAINTS;

-- CREACION DE TABLAS y OTROS OBJETOS   
CREATE TABLE competiciones (
    codCompeticion NUMBER(4) NOT NULL
        CONSTRAINT comp_cod_pk PRIMARY KEY,
    nombre VARCHAR2(50) UNIQUE,
    fechaInicio DATE NOT NULL,
    fechaFin DATE NOT NULL,
    estado VARCHAR(10) CHECK(estado IN('activo', 'inactivo'))
);

CREATE SEQUENCE sec_codCompeticion
    START WITH 1
    INCREMENT BY 1
    NOCYCLE
    NOCACHE
    MAXVALUE 9999;


CREATE TABLE jornadas (
    codJornada NUMBER(4) NOT NULL
        CONSTRAINT jor_cod_pk PRIMARY KEY,
    fecha DATE NOT NULL,
    competicion NUMBER(4) NULL,
    CONSTRAINT jor_codComp_fk FOREIGN KEY (competicion) -- Competicion
        REFERENCES competiciones(codCompeticion)
);

CREATE SEQUENCE sec_codJornada
    START WITH 1
    INCREMENT BY 1
    NOCYCLE
    NOCACHE
    MAXVALUE 9999;

CREATE TABLE equipos (
    codEquipo NUMBER(4) NOT NULL
        CONSTRAINT equi_cod_pk PRIMARY KEY,
    nombre VARCHAR2(50) UNIQUE NOT NULL,
    fechaFundacion DATE NOT NULL
);

CREATE SEQUENCE sec_codEquipo
    START WITH 1
    INCREMENT BY 1
    NOCYCLE
    NOCACHE
    MAXVALUE 9999;

CREATE TABLE jugadores (
    codJugador NUMBER(4) NOT NULL
        CONSTRAINT jug_cod_pk PRIMARY KEY,
    nombre VARCHAR2(50) NOT NULL,
    apellidos VARCHAR2(50) NOT NULL,
    nacionalidad VARCHAR2(50) NOT NULL,
    fechaNacimiento DATE NOT NULL,
    nickname VARCHAR2(50) NOT NULL,
    rol VARCHAR2(20) CHECK(rol IN('duelista', 'iniciador', 'centinela', 
        'controlador')) NOT NULL,
    sueldo NUMBER(7, 2) NOT NULL,
    codEquipo NUMBER(4) NULL,
    CONSTRAINT jug_codEqui_fk FOREIGN KEY (codEquipo) -- Equipo
        REFERENCES equipos(codEquipo)
);

CREATE SEQUENCE sec_codJugadores
    START WITH 1
    INCREMENT BY 1
    NOCYCLE
    NOCACHE
    MAXVALUE 9999;

CREATE TABLE enfrentamientos (
    codEnfrentamiento NUMBER(4) NOT NULL
        CONSTRAINT enfre_cod_pk PRIMARY KEY,
    hora VARCHAR2(5) NOT NULL,
    equipo1 NUMBER(4) NOT NULL,
    equipo2 NUMBER(4) NOT NULL,
    ganador NUMBER(4) NULL,
    jornada NUMBER(4) NOT NULL,
    CONSTRAINT enfre_equipo1_fk FOREIGN KEY (equipo1) -- Equipo 1
        REFERENCES equipos(codEquipo),
    CONSTRAINT enfre_equipo2_fk FOREIGN KEY (equipo2) -- Equipo 2
        REFERENCES equipos(codEquipo),
    CONSTRAINT enfre_ganador_fk FOREIGN KEY (ganador) -- Ganador
        REFERENCES equipos(codEquipo),
    CONSTRAINT enfre_codJor_fk FOREIGN KEY (jornada) -- Jornada
        REFERENCES jornadas(codJornada) ON DELETE CASCADE
);

CREATE SEQUENCE sec_codEnfrentamientos
    START WITH 1
    INCREMENT BY 1
    NOCYCLE
    NOCACHE
    MAXVALUE 9999;

CREATE TABLE usuarios (
    id NUMBER(4) NOT NULL
        CONSTRAINT user_id_pk PRIMARY KEY,
    email VARCHAR2(255) NOT NULL,
    password VARCHAR2(255) NOT NULL,
    tipo VARCHAR2(5) CHECK(tipo IN('admin', 'user')) NOT NULL
);

CREATE SEQUENCE sec_codUsuarios
    START WITH 1
    INCREMENT BY 1
    NOCYCLE
    NOCACHE
    MAXVALUE 9999;

COMMIT;
