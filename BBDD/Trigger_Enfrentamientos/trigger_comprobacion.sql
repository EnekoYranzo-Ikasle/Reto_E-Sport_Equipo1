--VER EL ESTADO DEL TRIGGER
DESC USER_TRIGGERS;

SELECT TRIGGER_NAME,STATUS
FROM USER_TRIGGERS
WHERE UPPER(TABLE_NAME)='ENFRENTAMIENTOS';

ALTER TRIGGER HORA_ENFRE_EQUIPOS_TRIGGER ENABLE;

--------------------------------------------------------------
--AHORA TENEMOS QUE VER EQUIPOS,COMPETICIONES Y JORNADAS QUE PODAMOS USAR
SELECT * FROM EQUIPOS;
/*
COD_EQUIPO NOMBRE                                             FECHAFUN
---------- -------------------------------------------------- --------
      7689 LEONES                                             01/01/00
      9876 LOBOS                                              12/12/01
      6789 PERROS                                             10/10/01
*/
SELECT * FROM COMPETICIONES;
/*
NOMBRE                  COD_COMP FECHAINI    FECHAFIN   ESTADO    
---------------------- -------- --------    ----------
Torneo Test           2         10/05/25     01/05/25   activo    
Liga Valorant         6         01/05/25     10/05/25   activo    
Valorant              0        1/04/25       03/04/25           
*/
SELECT * FROM JORNADAS;
/*
COD_JORNADA   COD_COMP FECHA   
----------- ---------- --------
          2          6 05/05/25
          1         11 01/04/25
*/
----------------------------------------------------------

-- Enfrentamiento 1: LEONES vs LOBOS a las 14:30
INSERT INTO enfrentamientos (cod_equipo1, cod_equipo2, cod_ganador, cod_jornada, hora)
VALUES (7689, 9876, NULL, 2, TO_DATE('14:30', 'HH24:MI'));

-- Enfrentamiento 2: LEONES vs PERROS a las 16:30 (2h después, válido)
INSERT INTO enfrentamientos (cod_equipo1, cod_equipo2, cod_ganador, cod_jornada, hora)
VALUES (7689, 6789, NULL, 2, TO_DATE('16:30', 'HH24:MI'));

-------------------------------------------------------------------------------
-- Esto DEBERÍA lanzar el error del trigger
-- Enfrentamiento 1: LEONES vs LOBOS a las 14:30
INSERT INTO enfrentamientos (cod_equipo1, cod_equipo2, cod_ganador, cod_jornada, hora)
VALUES (7689, 9876, NULL, 2, TO_DATE('14:30', 'HH24:MI'));

-- Enfrentamiento 2: LEONES vs PERROS a las 15:30 (solo 1h de diferencia – NO válido)
INSERT INTO enfrentamientos (cod_equipo1, cod_equipo2, cod_ganador, cod_jornada, hora)
VALUES (7689, 6789, NULL, 2, TO_DATE('15:30', 'HH24:MI'));

----------------------------------------------------------------------------
-- LOBOS vs PERROS a las 10:00
INSERT INTO enfrentamientos (cod_equipo1, cod_equipo2, cod_ganador, cod_jornada, hora)
VALUES (9876, 6789, NULL, 2, TO_DATE('10:00', 'HH24:MI'));

-- LOBOS vs LEONES a las 12:30 (2h30m después – válido)
INSERT INTO enfrentamientos (cod_equipo1, cod_equipo2, cod_ganador, cod_jornada, hora)
VALUES (9876, 7689, NULL, 2, TO_DATE('12:30', 'HH24:MI'));

-----------------------------------------------------------------------------    
-- Debería lanzar error-
- LEONES vs LOBOS a las 11:00
INSERT INTO enfrentamientos (cod_equipo1, cod_equipo2, cod_ganador, cod_jornada, hora)
VALUES (7689, 9876, NULL, 2, TO_DATE('11:00', 'HH24:MI'));

-- PERROS vs LOBOS a las 12:00 (LOBOS repite como equipo2 con 1h de diferencia)
INSERT INTO enfrentamientos (cod_equipo1, cod_equipo2, cod_ganador, cod_jornada, hora)
VALUES (6789, 9876, NULL, 2, TO_DATE('12:00', 'HH24:MI'));
    
----------BORRAR TODOS LOS ENFRENTAMIENTOS-----------------
DELETE FROM enfrentamientos;
COMMIT;
