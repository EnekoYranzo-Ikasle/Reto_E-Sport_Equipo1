-- Inserción de datos en la tabla USUARIOS
INSERT INTO usuarios (id, email, password, tipo) 
    VALUES (sec_codUsuarios.NEXTVAL, 'user@email.com', '12345', 'user');

INSERT INTO usuarios (id, email, password, tipo) 
    VALUES (sec_codUsuarios.NEXTVAL, 'admin@email.com', '12345', 'admin');

-- Inserción de datos en la tabla EQUIPOS para el equipo 1
INSERT INTO equipos (codEquipo, nombre, fechaFundacion) 
    VALUES (sec_codEquipo.NEXTVAL, 'Equipo Alpha', TO_DATE('20-05-2010', 'DD-MM-YYYY'));
    
-- Inserción de datos en la tabla JUGADORES para el equipo 1
INSERT INTO jugadores (codJugador, nombre, apellidos, nacionalidad, fechaNacimiento, 
    nickname, rol, sueldo, codEquipo) 
    VALUES (sec_codJugadores.NEXTVAL, 'Juan', 'Pérez', 'Española', TO_DATE('12-03-1995', 'DD-MM-YYYY'), 
        'juanito', 'duelista', 1500, sec_codEquipo.CURRVAL);

INSERT INTO jugadores (codJugador, nombre, apellidos, nacionalidad, fechaNacimiento, 
    nickname, rol, sueldo, codEquipo) 
    VALUES (sec_codJugadores.NEXTVAL, 'Carlos', 'Gomez', 'Española', TO_DATE('22-07-1997', 'DD-MM-YYYY'), 
        'carlitos', 'controlador', 1200, sec_codEquipo.CURRVAL);

-- Inserción de datos en la tabla EQUIPOS para el equipo 2 
INSERT INTO equipos (codEquipo, nombre, fechaFundacion) 
    VALUES (sec_codEquipo.NEXTVAL, 'Equipo Beta', TO_DATE('15-08-2012', 'DD-MM-YYYY'));

-- Inserción de datos en la tabla JUGADORES para el equipo 2
INSERT INTO jugadores (codJugador, nombre, apellidos, nacionalidad, fechaNacimiento, 
    nickname, rol, sueldo, codEquipo) 
    VALUES (sec_codJugadores.NEXTVAL, 'Lucas', 'Méndez', 'Méxicana', TO_DATE('30-11-1996', 'DD-MM-YYYY'), 
        'lucas', 'iniciador', 1600, sec_codEquipo.CURRVAL);

INSERT INTO jugadores (codJugador, nombre, apellidos, nacionalidad, fechaNacimiento, 
    nickname, rol, sueldo, codEquipo) 
    VALUES (sec_codJugadores.NEXTVAL, 'Raúl', 'Sánchez', 'Colombiana', TO_DATE('02-04-1998', 'DD-MM-YYYY'), 
        'raulito', 'centinela', 1100, sec_codEquipo.CURRVAL);

COMMIT;
