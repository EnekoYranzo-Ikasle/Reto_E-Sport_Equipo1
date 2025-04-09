
DROP TRIGGER trg_valida_ganador_Equipo01;
DROP TRIGGER maximoJugadoresEquipo;
DROP TRIGGER sueldoMinimo;
DROP TRIGGER minJugadoresPorEquipo;
DROP TRIGGER fechaCompeticiones;
DROP TRIGGER horaEnfrentamientosEquipos;
DROP TRIGGER fechaJornada;
DROP TRIGGER noModificarJugadores;
DROP TRIGGER noModificarEquipos;

CREATE OR REPLACE TRIGGER trg_valida_ganador_Equipo01
BEFORE INSERT OR UPDATE ON enfrentamientos
FOR EACH ROW
declare
v_mensaje varchar2(250);
e_equipos exception;
BEGIN
    IF :NEW.ganador != :NEW.equipo1 AND :NEW.ganador != :NEW.equipo2 THEN
      raise e_equipos;
    END IF;
EXCEPTION
WHEN e_equipos then
    v_mensaje:= 'El equipo ganador debe ser el equipo1 o el equipo2 del enfrentamiento.';
     RAISE_APPLICATION_ERROR(-20002,v_mensaje);
WHEN OTHERS THEN
    v_mensaje:= 'Error desconcido ' || to_char(SQLCODE) ||  SQLERRM;
    RAISE_APPLICATION_ERROR(-20099, v_mensaje);
END trg_valida_ganador_Equipo01;


/*NO Mï¿½S DE 6 JUGADORES*/
CREATE OR REPLACE TRIGGER maximoJugadoresEquipo
BEFORE INSERT ON jugadores
FOR EACH ROW

DECLARE
    total_jugadores NUMBER;
    v_mensaje VARCHAR2(250);
    v_exceso_jugadores EXCEPTION;
    
BEGIN
    SELECT COUNT(*) INTO total_jugadores
    FROM jugadores
    WHERE codEquipo = :NEW.codEquipo;
    
    IF total_jugadores > 6 THEN
        RAISE v_exceso_jugadores;
    END IF;
    
EXCEPTION
    WHEN v_exceso_jugadores THEN
        RAISE_APPLICATION_ERROR(-20002, 'No se pueden registrar mï¿½s de seis jugadores por equipo.');
    WHEN OTHERS THEN
        v_mensaje := 'Error Oracle: ' || TO_CHAR(SQLCODE) || ', ' || SQLERRM;
        RAISE_APPLICATION_ERROR(-20000, v_mensaje);
END maximoJugadoresEquipo;  


/*SALARIO MINIMO SMI*/
CREATE OR REPLACE TRIGGER sueldoMinimo
    BEFORE INSERT ON jugadores
    FOR EACH ROW
    DECLARE
        V_MENSAJE VARCHAR2(255);
    BEGIN
        IF (:new.sueldo < 1184) THEN
            :new.sueldo := 1184;
        END IF;
    
    EXCEPTION
        WHEN OTHERS THEN
            V_MENSAJE := 'Err. desconocido, ' || TO_CHAR(SQLCODE) || SQLERRM;
            RAISE_APPLICATION_ERROR(-20099, V_MENSAJE);
    
END sueldoMinimo;

/*Validacion al crear calendario todos los equipo tienen mas de 2 jugadores*/
CREATE OR REPLACE TRIGGER minJugadoresPorEquipo
    BEFORE INSERT ON enfrentamientos
    FOR EACH ROW
    DECLARE
        numJugEquipo1 NUMBER;
        numJugEquipo2 NUMBER;
        v_mensaje VARCHAR2(255);
        e_menosDe2Jugadores EXCEPTION;
    BEGIN
        SELECT COUNT(*) INTO numJugEquipo1
        FROM jugadores
        WHERE codEquipo = :NEW.equipo1;
    
        SELECT COUNT(*) INTO numJugEquipo2
        FROM jugadores
        WHERE codEquipo = :NEW.equipo2;
    
        IF numJugEquipo1 < 2 OR numJugEquipo2 < 2 THEN
            RAISE e_menosDe2Jugadores;
        END IF;
    
    EXCEPTION
        WHEN e_menosDe2Jugadores THEN
            RAISE_APPLICATION_ERROR(-20020, 'Uno o ambos equipos tienen menos de 2 jugadores.');
        WHEN OTHERS THEN
            v_mensaje := 'Err. desconocido, ' || TO_CHAR(SQLCODE) || SQLERRM;
            RAISE_APPLICATION_ERROR(-20099, v_mensaje);
END minJugadoresPorEquipo;

/* 
Trigger para que la fecha fin de competiciones no sea anterior a la de inicio y 
la de inicio no sea mayor a la de fin
*/
CREATE OR REPLACE TRIGGER fechaCompeticiones
    BEFORE INSERT OR UPDATE ON competiciones
    FOR EACH ROW
    DECLARE 
        e_fecha EXCEPTION;
    BEGIN
        IF :NEW.fechaFin < :NEW.fechaInicio THEN
            RAISE e_fecha;
        END IF;
    
    EXCEPTION   
        WHEN e_fecha THEN
            RAISE_APPLICATION_ERROR(-20001, 'LA FECHA FIN NO PUEDE SER ANTERIOR A LA FECHA DE INICIO');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'ERROR ORACLE: ' || SQLCODE || ' - ' || SQLERRM);
END fechaCompeticiones;

/* 
Trigger para uno de los dos equipos no juegue a la misma hora, pero solo pueden
jugar con 2 de diferencia.
*/
CREATE OR REPLACE TRIGGER horaEnfrentamientosEquipos
BEFORE INSERT OR UPDATE OF hora, equipo1, equipo2, jornada
    ON enfrentamientos
FOR EACH ROW
DECLARE
    v_count NUMBER;
    e_hora EXCEPTION;
BEGIN
    -- Verificar equipo 1
    SELECT COUNT(*) INTO v_count
        FROM enfrentamientos
        WHERE jornada = :NEW.jornada
            AND codEnfrentamiento != NVL(:NEW.codEnfrentamiento, -1)
            AND (equipo1 = :NEW.equipo1 OR 
            equipo2 = :NEW.equipo1)
      AND ABS((hora - :NEW.hora) * 24) < 2;  
    IF v_count > 0 THEN
        RAISE e_hora;
    END IF;

    -- Verificar equipo 2
    SELECT COUNT(*) INTO v_count
    FROM enfrentamientos
    WHERE jornada = :NEW.jornada
      AND codEnfrentamiento != NVL(:NEW.codEnfrentamiento, -1)
      AND (
            equipo1 = :NEW.equipo2 OR equipo2 = :NEW.equipo2)
      AND ABS((hora - :NEW.hora) * 24) < 2;  
    IF v_count > 0 THEN
        RAISE e_hora;
    END IF;

EXCEPTION
    WHEN e_hora THEN
        RAISE_APPLICATION_ERROR(-20010, 'Uno de los equipos tiene un 
            enfrentamiento demasiado cercano en la misma jornada (menos de 2h).');
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20099, 'Error inesperado: ' || TO_CHAR(SQLCODE) 
            || SQLERRM);
END horaEnfrentamientosEquipos;

--------------------------------------------------
/*
TRIGGER PARA QUE AL INSERTAR O ACTUALIZAR LA FECHA DE JORNADAS, SEA ENTRE LA 
FECHAINICIO Y LA FECHA FIN DE LA COMPETICION
*/
CREATE OR REPLACE TRIGGER fechaJornada
BEFORE INSERT OR UPDATE OF fecha ON jornadas
FOR EACH ROW
DECLARE
    v_fechaInicio competiciones.fechaInicio%TYPE;
    v_fechaFin competiciones.fechaFin%TYPE;
    e_fecha EXCEPTION;
BEGIN
    SELECT fechaInicio,fechaFin INTO v_fechaInicio,v_fechaFin
    FROM competiciones
    WHERE codCompeticion = :NEW.competicion;
    
    IF :NEW.fecha < v_fechaInicio OR :NEW.fecha > v_fechaFin THEN
    RAISE e_fecha;
    END IF;
    
EXCEPTION
    WHEN e_fecha THEN
    RAISE_APPLICATION_ERROR(-20001,'LA FECHA TIENE QUE ESTAR ENTRE LAS FECHAS '||
                            TO_CHAR(v_fechaInicio)||' - '||TO_CHAR(v_fechaFin));
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20002,'ERROR ORACLE: '||SQLCODE||'-'||TO_CHAR(SQLERRM));
END fechaJornada;


/* Trigger para controlar que una vez generado el calendario de la competición, no se pueden
modificar, ni los equipos, ni los jugadores de cada equipo*/
CREATE OR REPLACE TRIGGER noModificarEquipos
BEFORE INSERT OR UPDATE ON equipos
FOR EACH ROW

DECLARE
    v_mensaje VARCHAR2(225);
    v_contar NUMBER;
    e_excepcion EXCEPTION;

BEGIN
    SELECT COUNT(*) INTO v_contar
        FROM jornadas;
    
    IF v_contar != 0 THEN
        RAISE e_excepcion;
    END IF;    
        
EXCEPTION

    WHEN e_excepcion THEN
        RAISE_APPLICATION_ERROR(-20001,'El calendario de la competicion ya a sido generado.');

    WHEN OTHERS THEN
        v_mensaje := 'Error desconocido, ' || TO_CHAR(SQLCODE) || SQLERRM;
        RAISE_APPLICATION_ERROR(-20099, v_mensaje);

END noModificarEquipos;

CREATE OR REPLACE TRIGGER noModificarJugadores
BEFORE INSERT OR UPDATE ON jugadores
FOR EACH ROW

DECLARE
    v_mensaje VARCHAR2(225);
    v_contar NUMBER;
    e_excepcion EXCEPTION;

BEGIN
    SELECT COUNT(*) INTO v_contar
        FROM jornadas;
    
    IF v_contar != 0 THEN
        RAISE e_excepcion;
    END IF;    
        
EXCEPTION

    WHEN e_excepcion THEN
        RAISE_APPLICATION_ERROR(-20001,'El calendario de la competicion ya a sido generado.');

    WHEN OTHERS THEN
        v_mensaje := 'Error desconocido, ' || TO_CHAR(SQLCODE) || SQLERRM;
        RAISE_APPLICATION_ERROR(-20099, v_mensaje);

END noModificarJugadores;