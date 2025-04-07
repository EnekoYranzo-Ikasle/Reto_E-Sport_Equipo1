CREATE OR REPLACE TRIGGER trg_valida_ganador_Equipo01
BEFORE INSERT OR UPDATE ON enfrentamientos
FOR EACH ROW
BEGIN
    IF :NEW.ganador != :NEW.equipo1 AND :NEW.ganador != :NEW.equipo2 THEN
        RAISE_APPLICATION_ERROR(-20002, 'El equipo ganador debe ser el equipo1 o el equipo2 del enfrentamiento.');
    END IF;
END;
/
