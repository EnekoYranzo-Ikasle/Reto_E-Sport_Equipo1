/* View para visualizar los todas las jornadas creadas, con sus enfrentamientos
  y ver que equipos se enfrentan*/
CREATE OR REPLACE VIEW vista_enfre_equi AS
SELECT
    j.codJornada as "Codigo de jornada", en.codEnfrentamiento as "Código enfrentamiento",
    en.hora as "Hora del enfrentamiento", en.equipo1 as "Codigo de equipo local", 
    eq.nombre as "Nombre de equipo local", en.equipo2 as "Codigo de equipo visitante",
    eq.nombre as "Nombre de equipo visitante"
FROM 
    jornadas j 
    JOIN enfrentamientos en ON j.codJornada= en.jornada
    JOIN equipos eq ON eq.codEquipo=en.equipo1
    JOIN equipos equi ON equi.codEquipo=en.equipo2;
    
SELECT * FROM vista_enfre_equi;

/*View para visualizar los jugadores de cada equipo*/
CREATE OR REPLACE VIEW vista_equi_jugadores AS
SELECT 
    e.nombre as "Nombre del equipo", j.nombre as "Nombre del jugador",
    j.apellidos as "Apellidos del jugador", j.rol as "Rol"
FROM
    equipos e 
    JOIN jugadores j ON j.codEquipo=e.codEquipo
    ;

SELECT * FROM vista_equi_jugadores;

/*View para visualizar las competiciones con sus jornadas y cuantos 
    enfrentamientos contienen en total*/
CREATE OR REPLACE VIEW vista_compe_jor_enf AS
SELECT
    c.nombre AS "Nombre de la competición",
    j.codJornada AS "Código de jornada",
    COUNT(e.codEnfrentamiento) AS "Cantidad de enfrentamientos"
FROM 
    competiciones c
    JOIN jornadas j ON c.codCompeticion = j.competicion
    JOIN enfrentamientos e ON j.codJornada = e.jornada
GROUP BY c.nombre, j.codJornada;

SELECT * FROM vista_compe_jor_enf;
