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
