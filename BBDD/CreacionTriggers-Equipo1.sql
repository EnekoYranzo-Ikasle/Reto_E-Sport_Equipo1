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