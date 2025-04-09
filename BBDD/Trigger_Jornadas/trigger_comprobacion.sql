--VER EL ESTADO DEL TRIGGER
DESC USER_TRIGGERS;

SELECT TRIGGER_NAME,STATUS
FROM USER_TRIGGERS
WHERE UPPER(TABLE_NAME)='JORNADAS';

ALTER TRIGGER FECHA_JORNADA_TRIGGER ENABLE;
------------------------------------------------------------------
SELECT * FROM COMPETICIONES;

/*
NOMBRE              COD_COMP FECHAINI FECHAFIN ESTADO    
------------------ ---------- -------- -------- ----------
Torneo Test         2         10/05/25  01/05/25  activo    
Liga Valorant       6         01/05/25  10/05/25  activo    
Valorant            11        01/04/25  03/04/25           
*/

--USAREMOS LIGA VALORANT

-- Este debe funcionar:
INSERT INTO jornadas (cod_comp, fecha)
VALUES (6, TO_DATE('05/05/2025', 'DD/MM/YYYY'));

--Este debe fallar (fuera de rango):
INSERT INTO jornadas (cod_comp, fecha)
VALUES (6, TO_DATE('14/04/2025', 'DD/MM/YYYY'));

INSERT INTO jornadas (cod_comp, fecha)
VALUES (6, TO_DATE('11/05/2025', 'DD/MM/YYYY'));

-------------------------------------------------------
--BORRAR COLUMNAS
DELETE FROM JORNADAS 
WHERE COD_JORNADA=1;
