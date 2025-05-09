
DROP TRIGGER sueldoMinimo_Equipo1;
DROP TRIGGER maximoJugadoresEquipo;
DROP TRIGGER minJugadoresPorEquipo;
DROP TRIGGER noModificarJugadores;
DROP TRIGGER noModificarEquipos;
DROP TRIGGER fechaCompeticiones;
DROP TRIGGER trg_valida_ganador_Equipo01;
DROP TRIGGER horaEnfrentamientos;
  
/*SALARIO MINIMO SMI*/
CREATE OR REPLACE TRIGGER sueldoMinimo_Equipo1
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
    
END sueldoMinimo_Equipo1;

/*No más de 6 jugadores por equipo*/
CREATE OR REPLACE TRIGGER maximoJugadoresEquipo_Equipo1
FOR INSERT ON jugadores
COMPOUND TRIGGER

    -- Variable para contar cuántos jugadores se intentan insertar por equipo
    TYPE t_equipo_contador IS TABLE OF PLS_INTEGER INDEX BY PLS_INTEGER;
    v_nuevos_por_equipo t_equipo_contador;

    -- BEFORE EACH ROW: Contar cuántos jugadores se intentan insertar por cada equipo
    BEFORE EACH ROW IS
    BEGIN
        IF :NEW.codEquipo IS NOT NULL THEN
            -- Si ya existe un conteo para el equipo, incrementarlo
            IF v_nuevos_por_equipo.EXISTS(:NEW.codEquipo) THEN
                v_nuevos_por_equipo(:NEW.codEquipo) := v_nuevos_por_equipo(:NEW.codEquipo) + 1;
            ELSE
                -- Si no existe, inicializar el conteo en 1
                v_nuevos_por_equipo(:NEW.codEquipo) := 1;
            END IF;
        END IF;
    END BEFORE EACH ROW;

    -- AFTER STATEMENT: Validar que la suma de actuales + nuevos no supere 6
    AFTER STATEMENT IS
        v_total_actual NUMBER;
        v_mensaje VARCHAR2(250);
        e_jugadores EXCEPTION;
    BEGIN
        -- Iterar sobre todos los equipos para los que se intentaron insertar jugadores
        FOR idx IN v_nuevos_por_equipo.FIRST .. v_nuevos_por_equipo.LAST LOOP
            -- Verificar cuántos jugadores tiene ya el equipo
            SELECT COUNT(*) INTO v_total_actual
            FROM jugadores
            WHERE codEquipo = idx; -- Aquí usamos el índice (codEquipo)

            -- Si el total de jugadores supera 6, lanzar una excepción
            IF v_total_actual + v_nuevos_por_equipo(idx) > 6 THEN
                RAISE e_jugadores;
            END IF;
        END LOOP;

    EXCEPTION
        WHEN e_jugadores THEN
            v_mensaje := 'No se pueden registrar más de 6 jugadores por equipo.';
            RAISE_APPLICATION_ERROR(-20002, v_mensaje); -- Muestra el error correspondiente
    END AFTER STATEMENT;

END maximoJugadoresEquipo_Equipo1;


/*Validacion al crear calendario todos los equipo tienen mas de 2 jugadores*/
CREATE OR REPLACE TRIGGER minJugadoresPorEquipo_Equipo1
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
END minJugadoresPorEquipo_Equipo1;

/* Trigger para controlar que una vez generado el calendario de la competici n, no se pueden
modificar, ni los equipos, ni los jugadores de cada equipo*/
CREATE OR REPLACE TRIGGER noModificarEquipos_Equipo1
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

END noModificarEquipos_Equipo1;

CREATE OR REPLACE TRIGGER noModificarJugadores_Equipo1
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

END noModificarJugadores_Equipo1;

/* 
Trigger para que la fecha fin de competiciones no sea anterior a la de inicio y 
la de inicio no sea mayor a la de finalización.
*/
CREATE OR REPLACE TRIGGER fechaCompeticiones_Equipo1
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
END fechaCompeticiones_Equipo1;

/*Validar que el ganador sea uno de los 2 equipos pertenecientes de cada enfrentamiento.*/
CREATE OR REPLACE TRIGGER trg_valida_ganador_Equipo1
BEFORE INSERT OR UPDATE ON enfrentamientos
FOR EACH ROW
DECLARE
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
END trg_valida_ganador_Equipo1;

/*Entre cada enfrentamiento pasen minimo 2 horas*/
CREATE OR REPLACE TRIGGER horaEnfrentamientos_Equipo1
BEFORE UPDATE OF hora ON enfrentamientos
FOR EACH ROW
DECLARE
    V_EXISTE NUMBER;

    V_MENSAJE VARCHAR2(255);
    E_MENOS_2_HORAS EXCEPTION;
BEGIN
    SELECT COUNT(*) INTO V_EXISTE
    FROM enfrentamientos
    WHERE CodEnfrentamiento != :OLD.CodEnfrentamiento
      AND ABS((CAST(:NEW.hora AS DATE) - CAST(hora AS DATE)) * 24) < 2;

    IF V_EXISTE > 0 THEN
        RAISE E_MENOS_2_HORAS;
    END IF;

EXCEPTION
    WHEN E_MENOS_2_HORAS THEN
        RAISE_APPLICATION_ERROR(-20010, 'Deben pasar mínimo 2 horas entre enfrentamientos');
    WHEN OTHERS THEN
        V_MENSAJE:= 'Error desconcido ' || to_char(SQLCODE) ||  SQLERRM;
        RAISE_APPLICATION_ERROR(-20099, V_MENSAJE);
END horaEnfrentamientos_Equipo1;