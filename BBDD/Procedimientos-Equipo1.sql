/*Script que contenga todos los procedimientos PL/SQL almacenados y
funciones.*/

/*Primer procedimiento:Se debe crear un procedimiento almacenado que, dado un código
de competición, devuelva un informe con los equipos que participan, incluyendo el nombre,
fecha de creación, cantidad de jugadores y los salarios (máximo, mínimo y medio)
de los jugadores. Si la competición no existe, debe generar una excepción. El procedimiento
debe manejar errores y ser accesible desde un programa en Java.*/

CREATE OR REPLACE PROCEDURE informeEquiposCompeticion (
    p_codCompe IN competiciones.codCompeticion%TYPE, 
    p_cursor_equipos OUT SYS_REFCURSOR)
    
AS
    v_existe NUMBER;
    e_compeNoExiste EXCEPTION;
    v_error VARCHAR2(250);
    
BEGIN    
    SELECT COUNT(*) INTO v_existe
    FROM competiciones
    WHERE codCompeticion = p_codCompe;
    
    IF v_existe = 0 THEN
        RAISE e_compeNoExiste;
    END IF;
    
OPEN p_cursor_equipos FOR
    SELECT 
        e.nombre AS nombre_equipo,
        e.fechaFundacion AS fecha_creacion,
        COUNT(j.codJugador) AS cantidad_de_jugadores,
        AVG(j.sueldo) AS sueldo_medio,
        MAX(j.sueldo) AS sueldo_maximo,
        MIN(j.sueldo) AS sueldo_minimo
        
    FROM equipos e
    LEFT JOIN jugadores j ON e.codEquipo = j.codEquipo
    WHERE e.codEquipo IN (
    
        SELECT DISTINCT enf.equipo1
        FROM enfrentamientos enf
        JOIN jornadas jor ON enf.jornada = jor.codJornada
        WHERE jor.competicion = p_codCompe
        
        UNION
        
        SELECT DISTINCT enf.equipo2
        FROM enfrentamientos enf
        JOIN jornadas jor ON enf.jornada = jor.codJornada
        WHERE jor.competicion = p_codCompe
        )
        
   GROUP BY e.nombre, e.fechaFundacion
   ORDER BY e.nombre;
   
EXCEPTION  
    WHEN e_compeNoExiste THEN
        v_error := 'La competicion con ' || p_codCompe || ' no existe';
        RAISE_APPLICATION_ERROR(-20001, v_error);
        
    WHEN OTHERS THEN
        v_error := 'Error Oracle: ' || to_char(SQLCODE) || ', ' || SQLERRM;
        RAISE_APPLICATION_ERROR(-20000, v_error);
        
END informeEquiposCompeticion;   

/*Segundo procedimiento: Procedimiento almacenado en la base de datos, que permita
despuï¿½s en Java, ver el informe con la relaciï¿½n de los jugadores de un equipo concreto.
De cada jugador se verï¿½ el nombre, apellido, rol y salario. El nombre del equipo
le llegarï¿½ como parï¿½metro. Las excepciones serï¿½n visualizadas en el programa Java.*/

CREATE OR REPLACE PROCEDURE ObtenerJugadoresEquipos (
    p_nombreEquipo IN equipos.nombre%TYPE,
    p_cursor_jugadores OUT SYS_REFCURSOR)
AS
    v_codEquipo equipos.codEquipo%TYPE;
    e_equipoNoExiste EXCEPTION;
    v_error varchar2(250);
    v_existe NUMBER;
    
BEGIN
        SELECT COUNT(*) INTO v_existe
        FROM equipos
        WHERE UPPER(nombre) = UPPER(p_nombreEquipo);
        
        IF v_existe = 0 THEN
            RAISE e_equipoNoExiste;
        END IF;    
    
        SELECT codEquipo INTO v_codEquipo
        FROM equipos
        WHERE UPPER(nombre) = UPPER(p_nombreEquipo);
        
    OPEN p_cursor_jugadores FOR
        SELECT nombre, apellidos, rol, sueldo
        FROM jugadores
        WHERE codEquipo = v_codEquipo;
        
EXCEPTION
    WHEN e_equipoNoExiste THEN
        v_error := 'El equipo no existe';
        RAISE_APPLICATION_ERROR (-20020, v_error);
    WHEN OTHERS THEN
        v_error := 'Error Oracle:' || TO_CHAR(SQLCODE) || ',' || SQLERRM;
        RAISE_APPLICATION_ERROR(-20000, v_error);
        
END ObtenerJugadoresEquipos;  