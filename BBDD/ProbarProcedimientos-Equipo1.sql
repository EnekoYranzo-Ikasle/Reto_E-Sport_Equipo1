/*Script que contenga todos los procedimientos PL/SQL an�nimos
destinados a probar la funcionalidad de los procedimientos almacenados y
funciones.*/

/*Probar la procedura informeEquiposCompeticion*/
DECLARE
    p_resultado SYS_REFCURSOR;
    
    v_nombre equipos.nombre%TYPE;
    v_fechaFundacion equipos.fechaFundacion%TYPE;
    v_numJugadores NUMBER;
    v_sueldoMedio NUMBER;
    v_sueldoMaximo NUMBER;
    v_sueldoMinimo NUMBER;

    v_error VARCHAR2(255);
    e_error EXCEPTION;

BEGIN
    informeEquiposCompeticion(1, p_resultado);
    
    FETCH p_resultado INTO v_nombre, v_fechaFundacion, v_numJugadores, 
        v_sueldoMedio, v_sueldoMaximo, v_sueldoMinimo;

    IF p_resultado%NOTFOUND THEN
        RAISE e_error;
    END IF;

    WHILE p_resultado%FOUND LOOP
        DBMS_OUTPUT.PUT_LINE('Equipo ' || v_nombre || ', fundado el ' || TO_CHAR(v_fechaFundacion) ||
                             '. Este equipo tiene ' || v_numJugadores || ' jugadores, con sueldo mínimo: ' || 
                             v_sueldoMinimo || ', sueldo medio: ' || v_sueldoMedio || 
                             ', y sueldo máximo: ' || v_sueldoMaximo);

        FETCH p_resultado INTO v_nombre, v_fechaFundacion, v_numJugadores, 
        v_sueldoMedio, v_sueldoMaximo, v_sueldoMinimo;
    END LOOP;

    CLOSE p_resultado;

EXCEPTION
    WHEN e_error THEN 
        v_error := 'NO SE HA ENCONTRADO NINGUN EQUIPO QUE CUMPLA LAS CONDICIONES';
        RAISE_APPLICATION_ERROR(-20001, v_error);

    WHEN OTHERS THEN
        v_error := 'ERROR ORACLE: ' || TO_CHAR(SQLCODE) || ', ' || SQLERRM;
        RAISE_APPLICATION_ERROR(-20000, v_error);    
END;

/*Probar la procedura obtenerJugadoresEquipos*/


