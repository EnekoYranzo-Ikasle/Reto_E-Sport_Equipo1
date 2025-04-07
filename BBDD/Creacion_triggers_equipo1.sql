CREATE OR REPLACE TRIGGER trg_valida_ganador_Equipo01
BEFORE INSERT OR UPDATE ON enfrentamientos
FOR EACH ROW
declare
v_mensaje varchar2(250);
BEGIN
    IF :NEW.ganador != :NEW.equipo1 AND :NEW.ganador != :NEW.equipo2 THEN
        RAISE_APPLICATION_ERROR(-20002, 'El equipo ganador debe ser el equipo1 o el equipo2 del enfrentamiento.');
    END IF;
EXCEPTION
WHEN OTHERS THEN
v_mensaje:= 'Error desconcido ' || to_char(SQLCODE) ||  SQLERRM;
RAISE_APPLICATION_ERROR(-20099, v_mensaje);

END trg_valida_ganador_Equipo01;

/*NO MÁS DE 6 JUGADORES*/
CREATE OR REPLACE TRIGGER max_jugadores_equipo
BEFORE INSERT ON JUGADORES
FOR EACH ROW

DECLARE
    total_jugadores NUMBER;
    v_error VARCHAR2(250);
    v_exceso_jugadores EXCEPTION;
    
    
BEGIN
    SELECT COUNT(*) INTO total_jugadores
    FROM JUGADORES
    WHERE cod_equipo = :NEW.cod_equipo;
    
    IF total_jugadores >= 6 THEN
        RAISE v_exceso_jugadores;
    END IF;
    
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20020, 'Error: no se encontraron datos.');

    WHEN TOO_MANY_ROWS THEN
        RAISE_APPLICATION_ERROR(-20030, 'Error inesperado.');
        
    WHEN v_exceso_jugadores THEN
        RAISE_APPLICATION_ERROR(-20002, 'No se pueden registrar más de seis jugadores por equipo.');

    WHEN OTHERS THEN
        v_error_msg := 'Error Oracle: ' || TO_CHAR(SQLCODE) || ', ' || SQLERRM;
        RAISE_APPLICATION_ERROR(-20000, v_error);
END max_jugadores_equipo;  


/*SALARIO MINIMO SMI*/
CREATE OR REPLACE TRIGGER salario_minimo
BEFORE INSERT ON jugadores
FOR EACH ROW

BEGIN

IF (sueldo < 1184)THEN
raise_application_error(-20000, 'El salario no puede ser menor del SMI');
END IF;

EXCEPTION
WHEN NO_DATA_FOUND THEN
NULL;

WHEN TOO_MANY_ROWS THEN
raise_application_error(-20500,'No se puede insertar el salario.');

WHEN OTHERS THEN
raise_application_error(-20100,'Error desconocido.');

END salario_minimo;
