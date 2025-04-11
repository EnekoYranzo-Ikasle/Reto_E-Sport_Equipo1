/*Script que contenga todos los procedimientos PL/SQL almacenados y
funciones.*/

/*Segundo procedimiento: Procedimiento almacenado en la base de datos, que permita
despu�s en Java, ver el informe con la relaci�n de los jugadores de un equipo concreto.
De cada jugador se ver� el nombre, apellido, rol y salario. El nombre del equipo
le llegar� como par�metro. Las excepciones ser�n visualizadas en el programa Java.*/

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