
DROP TRIGGER trg_valida_ganador_Equipo01;
DROP TRIGGER max_jugadores_equipo;
DROP TRIGGER salario_minimo;
DROP TRIGGER minJugadoresPorEquipo;


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
WHEN OTHERS THEN
v_mensaje:= 'Error desconcido ' || to_char(SQLCODE) ||  SQLERRM;
RAISE_APPLICATION_ERROR(-20099, v_mensaje);
when e_equipos then
v_mensaje:= 'El equipo ganador debe ser el equipo1 o el equipo2 del enfrentamiento.';
 RAISE_APPLICATION_ERROR(-20002,v_mensaje);
END trg_valida_ganador_Equipo01;


/*NO M�S DE 6 JUGADORES*/
CREATE OR REPLACE TRIGGER max_jugadores_equipo
BEFORE INSERT ON JUGADORES
FOR EACH ROW

DECLARE
    total_jugadores NUMBER;
    v_mensaje VARCHAR2(250);
    v_exceso_jugadores EXCEPTION;
    
BEGIN
    SELECT COUNT(*) INTO total_jugadores
    FROM JUGADORES
    WHERE cod_equipo = :NEW.cod_equipo;
    
    IF total_jugadores > 6 THEN
        RAISE v_exceso_jugadores;
    END IF;
    
EXCEPTION
    WHEN v_exceso_jugadores THEN
        RAISE_APPLICATION_ERROR(-20002, 'No se pueden registrar m�s de seis jugadores por equipo.');
    WHEN OTHERS THEN
        v_mensaje := 'Error Oracle: ' || TO_CHAR(SQLCODE) || ', ' || SQLERRM;
        RAISE_APPLICATION_ERROR(-20000, v_mensaje);
END max_jugadores_equipo;  


/*SALARIO MINIMO SMI*/
CREATE OR REPLACE TRIGGER salario_minimo
    BEFORE INSERT ON jugadores
    FOR EACH ROW
    BEGIN
        IF (:new.sueldo < 1184) THEN
            :new.sueldo := 1184;
        END IF;
    
    EXCEPTION
        WHEN OTHERS THEN
            v_mensaje := 'Err. desconocido, ' || TO_CHAR(SQLCODE) || SQLERRM;
            RAISE_APPLICATION_ERROR(-20099, v_mensaje);
    
END salario_minimo;

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
        WHERE cod_equipo = :NEW.equipo1;
    
        SELECT COUNT(*) INTO numJugEquipo2
        FROM jugadores
        WHERE cod_equipo = :NEW.equipo2;
    
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

-------------------------------------------------------------------------------------------

/*TRIGGERS COMPETICIONES, ENFRENTAMIENTOS Y JORNADAS*/
/* 
Trigger para que la fecha fin de competiciones no sea anterior a la de inicio y 
la de inicio no sea mayor a la de fin
*/
CREATE OR REPLACE TRIGGER FECHA_COMPETICIONES_TRIGGER
BEFORE INSERT OR UPDATE ON COMPETICIONES
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
END FECHA_COMPETICIONES_TRIGGER;

----------------------------------------------------------------------------------------------
/* 
Trigger para uno de los dos equipos no juegue a la misma hora, pero solo pueden
jugar con 2 de diferencia.
Ejemplo para saltar el trigger:
Leones versus Gatos - 14:30, Leones versus Loros -14:30
Ejemplo bueno:
Leones versus Gatos -14:30, Leones versus Loros 16:30
*/
CREATE OR REPLACE TRIGGER HORA_ENFRE_EQUIPOS_TRIGGER
BEFORE INSERT OR UPDATE OF hora, cod_equipo1, cod_equipo2, cod_jornada
                        ON enfrentamientos
FOR EACH ROW
DECLARE
    v_count NUMBER;
    e_hora EXCEPTION;
BEGIN
    -- Verificar equipo 1
    SELECT COUNT(*) INTO v_count
    FROM enfrentamientos
    WHERE cod_jornada = :NEW.cod_jornada
      
      AND cod_enfre != NVL(:NEW.cod_enfre, -1)  -- Evita conflicto consigo mismo
      
      AND (
            cod_equipo1 = :NEW.cod_equipo1 OR cod_equipo2 = :NEW.cod_equipo1
          )
          
      AND ABS(TO_NUMBER(TO_CHAR(hora, 'HH24')) - TO_NUMBER(TO_CHAR(:NEW.hora, 'HH24'))) < 2;

    IF v_count > 0 THEN
        RAISE e_hora;
    END IF;

    -- Verificar equipo 2
    SELECT COUNT(*) INTO v_count
    FROM enfrentamientos
    WHERE cod_jornada = :NEW.cod_jornada
    
      AND cod_enfre != NVL(:NEW.cod_enfre, -1)
      
      AND (
            cod_equipo1 = :NEW.cod_equipo2 OR cod_equipo2 = :NEW.cod_equipo2
          )
          
      AND ABS(TO_NUMBER(TO_CHAR(hora, 'HH24')) - TO_NUMBER(TO_CHAR(:NEW.hora, 'HH24'))) < 2;

    IF v_count > 0 THEN
        RAISE e_hora;
    END IF;

EXCEPTION
    WHEN e_hora THEN
        RAISE_APPLICATION_ERROR(-20010, 'Uno de los equipos tiene un 
        enfrentamiento demasiado cercano en la misma jornada (menos de 2h).');
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20099, 'Error inesperado: ' || SQLCODE || ' - '
                                || SQLERRM);
END HORA_ENFRE_EQUIPOS_TRIGGER;
--------------------------------------------------
/*
TRIGGER PARA QUE AL INSERTAR O ACTUALIZAR LA FECHA DE JORNADAS, SEA ENTRE LA 
FECHAINICIO Y LA FECHA FIN DE LA COMPETICION
*/
CREATE OR REPLACE TRIGGER FECHA_JORNADA_TRIGGER 
BEFORE INSERT OR UPDATE OF FECHA ON JORNADAS
FOR EACH ROW
DECLARE
    V_FECHAINICIO COMPETICIONES.FECHAINICIO%TYPE;
    V_FECHAFIN COMPETICIONES.FECHAFIN%TYPE;
    E_FECHA EXCEPTION;
BEGIN
    SELECT FECHAINICIO,FECHAFIN INTO V_FECHAINICIO,V_FECHAFIN
    FROM COMPETICIONES
    WHERE COD_COMP = :NEW.COD_COMP;
    
    IF :NEW.FECHA < V_FECHAINICIO OR :NEW.FECHA > V_FECHAFIN THEN
    RAISE E_FECHA;
    END IF;
EXCEPTION
    WHEN E_FECHA THEN
    RAISE_APPLICATION_ERROR(-20001,'LA FECHA TIENE QUE ESTAR ENTRE LAS FECHAS '||
                            TO_CHAR(V_FECHAINICIO)||' - '||TO_CHAR(V_FECHAFIN));
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20002,'ERROR ORACLE: '||SQLCODE||'-'||TO_CHAR(SQLERRM));
END FECHA_JORNADA_TRIGGER;