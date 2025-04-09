DELETE jugadores WHERE codJugador = 100;
DELETE jugadores WHERE codJugador = 101;
DELETE enfrentamientos WHERE codEnfrentamiento = 201;
DELETE enfrentamientos WHERE codEnfrentamiento = 202;
DELETE enfrentamientos WHERE codEnfrentamiento = 203;
DELETE competiciones WHERE codCompeticion = 10;
DELETE jornadas WHERE codJornada = 50;

-- 1. Probar TRIGGER salario_minimo (sueldo menor al mínimo)
INSERT INTO jugadores (codJugador, nombre, codEquipo, sueldo)
    VALUES (100, 'Jugador SMI', 100, 1000); 

SELECT sueldo FROM jugadores WHERE codJugador = 100; 

-- 2. Probar TRIGGER max_jugadores_equipo (superar el límite de jugadores por equipo)
INSERT INTO jugadores (codJugador, nombre, codEquipo, sueldo)
    VALUES (101, 'Jugador Exceso', 100, 1500); 

-- 3. Probar TRIGGER trg_valida_ganador_Equipo01 (ganador inválido)
INSERT INTO enfrentamientos (codEnfrentamiento, jornada, equipo1, equipo2, ganador, hora)
    VALUES (201, 1, 100, 200, 999, TO_DATE('2025-04-10 15:00', 'YYYY-MM-DD HH24:MI'));  

-- 4. Probar TRIGGER minJugadoresPorEquipo (uno de los equipos tiene menos de 2 jugadores)
INSERT INTO enfrentamientos (codEnfrentamiento, jornada, equipo1, equipo2, ganador, hora)
    VALUES (202, 1, 300, 100, 100, TO_DATE('2025-04-10 16:00', 'YYYY-MM-DD HH24:MI')); 
    
-- 5. Probar TRIGGER HORA_ENFRE_EQUIPOS_TRIGGER (enfrentamientos con el mismo equipo en horas cercanas)
INSERT INTO enfrentamientos (codEnfrentamiento, jornada, equipo1, equipo2, ganador, hora)
    VALUES (203, 1, 100, 200, 100, TO_DATE('2025-04-10 15:00', 'YYYY-MM-DD HH24:MI'));  

-- 6. Probar TRIGGER FECHA_COMPETICIONES_TRIGGER (fecha fin antes de la fecha inicio)
INSERT INTO competiciones (codCompeticion, nombre, fechaInicio, fechaFin)
    VALUES (10, 'Comp Inválida', TO_DATE('2025-04-20', 'YYYY-MM-DD'), 
        TO_DATE('2025-04-10', 'YYYY-MM-DD'));  

-- 7. Probar TRIGGER FECHA_JORNADA_TRIGGER (fecha fuera del rango de la competición)
INSERT INTO jornadas (codJornada, competicion, fecha)
    VALUES (50, 1, TO_DATE('2025-05-05', 'YYYY-MM-DD')); 