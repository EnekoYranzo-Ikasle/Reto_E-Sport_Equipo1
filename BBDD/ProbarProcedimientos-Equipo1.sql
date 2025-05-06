/*Script que contenga todos los procedimientos PL/SQL anï¿½nimos
destinados a probar la funcionalidad de los procedimientos almacenados y
funciones.*/

<<<<<<< Updated upstream
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
                             '. Este equipo tiene ' || v_numJugadores || ' jugadores, con sueldo mÃ­nimo: ' || 
                             v_sueldoMinimo || ', sueldo medio: ' || v_sueldoMedio || 
                             ', y sueldo mÃ¡ximo: ' || v_sueldoMaximo);

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
DECLARE
    v_cursor_jugadores SYS_REFCURSOR;
    v_nombre_jugador jugadores.nombre%TYPE;
    v_apellidos_jugador jugadores.apellidos%TYPE;
    v_rol_jugador jugadores.rol%TYPE;
    v_sueldo_jugador jugadores.sueldo%TYPE;
    v_nombre_equipo equipos.nombre%TYPE := 'Equipo Alpha';
    
BEGIN
    obtenerJugadoresEquipos(v_nombre_equipo, v_cursor_jugadores);
    
    DBMS_OUTPUT.PUT_LINE ('INFORME DE JUGADORES DEL EQUIPO: ' || v_nombre_equipo);
        
    LOOP 
        FETCH v_cursor_jugadores INTO v_nombre_jugador, v_apellidos_jugador, v_rol_jugador,
        v_sueldo_jugador;
        EXIT WHEN v_cursor_jugadores%NOTFOUND;
        
        DBMS_OUTPUT.PUT_LINE('Nombre: ' || v_nombre_jugador || ' ' || v_apellidos_jugador);
        DBMS_OUTPUT.PUT_LINE('Rol: ' || v_rol_jugador);
        DBMS_OUTPUT.PUT_LINE('Salario: ' || v_sueldo_jugador);
        END LOOP;
    
        CLOSE v_cursor_jugadores;
        
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Ocurrió un error' || SQLERRM);
        CLOSE v_cursor_jugadores;
END;
/

=======
/*Probar primer procedimiento*/
DECLARE
    v_cursor SYS_REFCURSOR;
    v_nombre VARCHAR2(100);
    v_fecha DATE;
    v_jugadores NUMBER;
    v_sueldo_medio NUMBER;
    v_sueldo_max NUMBER;
    v_sueldo_min NUMBER;
    
    v_codigo_comp NUMBER := 1;
BEGIN 
    informeEquiposCompeticion(v_codigo_comp, v_cursor);
    
    DBMS_OUTPUT.PUT_LINE('INFORME DE EQUIPOS');
    
    LOOP
        FETCH v_cursor INTO v_nombre, v_fecha, v_jugadores, v_sueldo_medio, v_sueldo_max, v_sueldo_min;
        EXIT WHEN v_cursor%NOTFOUND;
        
        DBMS_OUTPUT.PUT_LINE('Equipo: ' || v_nombre);
        DBMS_OUTPUT.PUT_LINE('Fecha fundación: ' || TO_CHAR(v_fecha, 'DD/MM/YYYY'));
        DBMS_OUTPUT.PUT_LINE('Número de jugadores: ' || v_jugadores);
        DBMS_OUTPUT.PUT_LINE('Salario medio: ' || v_sueldo_medio);
        DBMS_OUTPUT.PUT_LINE('Salario máximo: ' || v_sueldo_max);
        DBMS_OUTPUT.PUT_LINE('Salario mínimo: ' || v_sueldo_min);
    END LOOP;
    
    CLOSE v_cursor_jugadores;
    
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        
        IF v_cursor%ISOPEN THEN  
            CLOSE v_cursor_jugadores; 
        END IF;
END;


    
    
    
    
    
    

/




     
    
    
    
    
    
    
    
>>>>>>> Stashed changes
