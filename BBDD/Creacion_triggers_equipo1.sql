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
/
