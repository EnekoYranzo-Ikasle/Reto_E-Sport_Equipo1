# Reto Consultoría E-Sport
La empresa E-Sport ha encargado el desarrollo de una aplicación para gestionar una competición de e-sports asociada a un juego específico. La aplicación permitirá la inscripción de equipos y jugadores, la generación de un calendario de enfrentamientos y el registro de resultados.

## Características

**Etapa 1: Inscripción y generación del calendario**

- Registro de equipos y jugadores.

- Creación de un calendario de enfrentamientos en formato "todos contra todos".

- Cierre de inscripciones antes de iniciar la competición.

**Etapa 2: Campeonato y registro de resultados**

- Almacenamiento de resultados de los enfrentamientos.

- Restricción de modificación de equipos y jugadores una vez iniciado el campeonato.

## Reglas y Restricciones

- El número de equipos debe ser **par**.

- Cada equipo puede tener **hasta 6 jugadores**.

- El salario mínimo de los jugadores debe ser **superior al salario mínimo interprofesional**.

- Las jornadas se jugarán **una vez por semana** y todos los enfrentamientos se disputarán el mismo día.

- No se pueden generar jornadas si hay equipos con menos de **2 jugadores**.

- Una vez generado el calendario, **se cierra la inscripción** y no se pueden modificar equipos ni jugadores.
## Roles de Usuario

**Administrador**

- Realizar operaciones CRUD sobre los datos del sistema.

- Cerrar la etapa de inscripción de una competición.

- Generar el calendario de enfrentamientos.

- Introducir los resultados de los partidos.

- Visualizar informes de la competición.

**Usuario**

- Ver el informe de los equipos participantes.

- Consultar los resultados de la última jornada.

## Como Usar
1. Ejecutar el Main
2. En la pantalla login, segun que tipo de usuario hay una cuenta o otra. ADMIN: admin@email.com / 12345 //USER: user@email.com / 12345

ADMIN:
1. Crea Competion.
2. Jugadores / Eqipos
3. Generar Calendario
4. Enfrentamientos / Jornadas
5. Cerrar etapa
6. Agregar Resultados
7. Volver al 1. de admin para volver a otra competición.

USER:
Elegir ver equipos o Informes


## Autores

Eneko Yranzo

Koldo Soriguren

Fatima Din

Bryan Dave

Bouya Chrif
