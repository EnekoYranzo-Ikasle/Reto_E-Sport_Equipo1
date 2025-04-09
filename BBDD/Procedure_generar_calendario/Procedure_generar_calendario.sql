CREATE OR REPLACE PROCEDURE generar_calendario(
    p_fecha_inicio DATE, 
    p_fecha_fin DATE
) AS
    v_num_equipos INT;
    v_fecha_jornada DATE;
    v_hora_inicio VARCHAR2(5) := '10:00'; -- Hora de inicio de los enfrentamientos
    v_horas_por_jornada INT := 8; -- Número de horas disponibles por jornada
    v_id_jornada INT;
    v_num_enfrentamientos INT;
    v_id_local INT;
    v_id_visitante INT;
    v_num_competicion INT := 1; -- Ya que solo hay una competencia, tomamos la competencia 1
    equipos_mezclados SYS.ODCINUMBERLIST; -- Lista de equipos mezclados
BEGIN
    -- Obtener el número de equipos participantes en la competencia
    SELECT COUNT(*)
    INTO v_num_equipos
    FROM equipos
    WHERE cod_equipo IN (
        SELECT cod_equipo
        FROM jugadores
        WHERE cod_equipo IN (SELECT cod_equipo FROM equipos)
    );

    /* Calcular el número total de jornadas (basado en el número de días 
    entre las fechas)*/
    /* Suponemos que una jornada es un día (puedes cambiar la lógica 
    si se desea un mayor intervalo)*/
    v_fecha_jornada := p_fecha_inicio;

    -- Verificar si la fecha de inicio es anterior a la de fin
    IF p_fecha_inicio > p_fecha_fin THEN
        RAISE_APPLICATION_ERROR(-20001, 'La fecha de inicio no puede ser 
        posterior a la fecha de fin.');
    END IF;

    -- Generar jornadas entre la fecha de inicio y fin
    WHILE v_fecha_jornada <= p_fecha_fin LOOP
        -- Insertar la jornada
        INSERT INTO jornadas (fecha, competicion)
        VALUES (v_fecha_jornada, v_num_competicion);

        -- Obtener el ID de la jornada recién insertada
        SELECT cod_jornada
        INTO v_id_jornada
        FROM jornadas
        WHERE fecha = v_fecha_jornada
          AND competicion = v_num_competicion;

        -- Calcular el número de enfrentamientos
        v_num_enfrentamientos := v_num_equipos / 2;

        -- Mezclar aleatoriamente los equipos para esta jornada
        SELECT cod_equipo
        BULK COLLECT INTO equipos_mezclados
        FROM equipos
        ORDER BY DBMS_RANDOM.VALUE;

        -- Generar los enfrentamientos para esta jornada
        FOR j IN 1..v_num_enfrentamientos LOOP
            v_id_local := equipos_mezclados(j);
            v_id_visitante := equipos_mezclados(j + v_num_enfrentamientos);

            -- Insertar el enfrentamiento con hora y equipos correspondientes
            INSERT INTO enfrentamientos (hora, equipo1, equipo2, jornada)
            VALUES (
                TO_DATE(v_hora_inicio, 'HH24:MI') + ((j - 1) * 
                (1 / (24 * 60 / v_horas_por_jornada))), -- Hora del enfrentamiento
                v_id_local, -- ID del equipo local
                v_id_visitante, -- ID del equipo visitante
                v_id_jornada -- ID de la jornada
            );
        END LOOP;

        -- Avanzar a la siguiente jornada (en este caso, se avanza por un día)
        v_fecha_jornada := v_fecha_jornada + 1; /* Puede cambiarse por 2, 3 
                                                    o más días si es necesario*/
    END LOOP;

    -- Confirmar los cambios realizados
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        -- Manejar errores
        DBMS_OUTPUT.PUT_LINE('Error al generar el calendario: ' || SQLERRM);
        ROLLBACK;
END;
