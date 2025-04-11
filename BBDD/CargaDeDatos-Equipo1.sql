-- Inserción de datos en la tabla USUARIOS
INSERT INTO usuarios (email, password, tipo) 
    VALUES('user@email.com', '12345', 'user');

-- Inserción de datos en la tabla USUARIOS
INSERT INTO usuarios (email, password, tipo) 
    VALUES('admin@email.com', '12345', 'admin');

-- Inserción de datos en la tabla EQUIPOS
INSERT INTO equipos (nombre, fechaFundacion) 
    VALUES ('Equipo Alpha', TO_DATE('20-05-2010', 'DD-MM-YYYY'));

INSERT INTO equipos (nombre, fechaFundacion) 
    VALUES ('Equipo Beta', TO_DATE('15-08-2012', 'DD-MM-YYYY'));

-- Inserción de datos en la tabla JUGADORES
INSERT INTO jugadores (nombre, apellidos, nacionalidad, fechaNacimiento, 
    nickname, rol, sueldo, codEquipo) 
    VALUES ('Juan', 'Pérez', 'Española', TO_DATE('12-03-1995', 'DD-MM-YYYY'), 
        'juanito', 'duelista', 1500, 3);

INSERT INTO jugadores (nombre, apellidos, nacionalidad, fechaNacimiento, 
    nickname, rol, sueldo, codEquipo) 
    VALUES ('Carlos', 'Gomez', 'Española', TO_DATE('22-07-1997', 'DD-MM-YYYY'), 
        'carlitos', 'controlador', 1200, 3);

INSERT INTO jugadores (nombre, apellidos, nacionalidad, fechaNacimiento, 
    nickname, rol, sueldo, codEquipo) 
    VALUES ('Lucas', 'Méndez', 'Méxicana', TO_DATE('30-11-1996', 'DD-MM-YYYY'), 
        'lucas', 'iniciador', 1600, 4);

INSERT INTO jugadores (nombre, apellidos, nacionalidad, fechaNacimiento, 
    nickname, rol, sueldo, codEquipo) 
    VALUES ('Raúl', 'Sánchez', 'Colombiana', TO_DATE('02-04-1998', 'DD-MM-YYYY'), 
        'raulito', 'centinela', 1100, 4);
