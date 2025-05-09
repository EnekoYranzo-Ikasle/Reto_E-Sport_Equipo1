package org.example.Modelo;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Clase de prueba unitaria para EnfrentamientoDAO.
 *
 * Esta clase utiliza JUnit 5 para verificar las operaciones principales sobre enfrentamientos:
 * - Obtener ganadores
 * - Obtener enfrentamientos
 * - Obtener nombre de equipo
 * - Establecer ganador
 * - Verificar existencia de enfrentamiento
 * - Establecer hora
 *
 * La conexión a la base de datos se establece una vez por toda la clase usando @BeforeAll y se cierra al final con @AfterAll.
 *
 * Nota: este test utiliza conexión directa a base de datos Oracle.
 *
 * @author Grupo 1
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EnfrentamientoTest {
    private Connection conn;
    private EnfrentamientoDAO enfrentamientoDAO;

    /**
     * Configuración de la conexión a la base de datos y el DAO.
     * Se ejecuta una vez antes de todos los tests.
     * @throws SQLException Si ocurre un error SQL.
     */
    @BeforeAll
    public void setup() throws Exception {
        String url = "jdbc:oracle:thin:@172.20.225.114:1521:orcl";
        String user = "eqdaw01";
        String password = "eqdaw01";

        conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(false); // No guardar cambios
        enfrentamientoDAO = new EnfrentamientoDAO(conn);
    }
    /**
     * Prueba obtener ganadores de una jornada que tiene ganadores registrados.
     *
     * @throws SQLException si ocurre un error de base de datos
     */
    @Test
    void obtenerGanadoresConGanadores() throws SQLException {
        int codJornada = 14;
        List<Integer> ganadores = enfrentamientoDAO.obtenerGanadores(codJornada);

        assertNotNull(ganadores, "La lista de ganadores no debe ser nula");
        assertFalse(ganadores.isEmpty(), "Debe haber al menos un ganador en la jornada " + codJornada);

        System.out.printf("Ganadores encontrados para jornada %d: %d%n", codJornada, ganadores.size());
    }
    /**
     * Prueba obtener ganadores de una jornada que no tiene ganadores registrados.
     *
     * @throws SQLException si ocurre un error de base de datos
     */
    @Test
    void obtenerGanadoresSinGanadores() throws SQLException {
        int codJornada = 14;
        List<Integer> ganadores = enfrentamientoDAO.obtenerGanadores(codJornada);

        assertNotNull(ganadores, "La lista de ganadores no debe ser nula");
        assertTrue(ganadores.isEmpty(), "No debe haber ganadores en la jornada " + codJornada);

        System.out.printf("Ganadores encontrados para jornada %d: %d%n", codJornada, ganadores.size());
    }
    /**
     * Prueba obtener todos los enfrentamientos existentes.
     *
     * @throws SQLException si ocurre un error de base de datos
     */
    @Test
    void obtenerEnfrentamientos() throws SQLException {
        List<Enfrentamiento> enfrentamientos = enfrentamientoDAO.obtenerEnfrentamientos();
        assertNotNull(enfrentamientos, "La lista de enfrentamientos no debe ser nula");
        System.out.println("Número de enfrentamientos obtenidos: " + enfrentamientos.size());
    }
    /**
     * Prueba obtener el nombre de un equipo dado su código.
     *
     * @throws SQLException si ocurre un error de base de datos
     */
    @Test
    void sacarNombreEquipo() throws SQLException {
        int codEquipo = 2; // Asegúrate que este equipo existe
        String nombre = enfrentamientoDAO.sacarNombrEquipo(codEquipo);
        assertNotNull(nombre, "El nombre del equipo no debe ser nulo");
        assertFalse(nombre.trim().isEmpty(), "El nombre del equipo no debe estar vacío");
        System.out.println("El nombre del equipo es: " + nombre);
    }
    /**
     * Prueba establecer un ganador para un enfrentamiento y verificarlo.
     *
     * @throws SQLException si ocurre un error de base de datos
     */
    @Test
    void setGanadorYVerificar() throws SQLException {
        int codEnfrentamiento = 11; // Asegúrate que existe
        int codGanador = 1;        // Asegúrate que es uno de los equipos del enfrentamiento

        enfrentamientoDAO.setGanador(codGanador, codEnfrentamiento);

        // Aquí asumimos que podemos saber a qué jornada pertenece el enfrentamiento 13
        int codJornadaDelEnfrentamiento = 13;

        List<Integer> ganadores = enfrentamientoDAO.obtenerGanadores(codJornadaDelEnfrentamiento);
        assertTrue(ganadores.contains(codGanador), "El ganador debe estar registrado en la jornada");
        System.out.println("Hay ganador: " + ganadores.size());
    }
    /**
     * Prueba verificar si un enfrentamiento existe.
     *
     * @throws SQLException si ocurre un error de base de datos
     */
    @Test
    void enfrentamientoExiste() throws SQLException {
        int codEnfrentamiento = 11;
        boolean existe = enfrentamientoDAO.enfrentamientoExiste(codEnfrentamiento);
        assertTrue(existe, "El enfrentamiento debería existir");
    }
    /**
     * Prueba establecer la hora de un enfrentamiento y verificarla.
     *
     * @throws SQLException si ocurre un error de base de datos
     */
    @Test
    void setHoraYVerificar() throws SQLException {
        int codEnfrentamiento = 12; // Asegúrate que existe
        LocalTime nuevaHora = LocalTime.of(15, 45);

        enfrentamientoDAO.setHora(nuevaHora, codEnfrentamiento);

        List<Enfrentamiento> enfrentamientos = enfrentamientoDAO.obtenerEnfrentamientos();
        Enfrentamiento enfrentamiento = enfrentamientos.stream()
                .filter(e -> e.getCodEnfrentamiento() == codEnfrentamiento)
                .findFirst()
                .orElse(null);

        assertNotNull(enfrentamiento, "El enfrentamiento debería existir");
        assertNotNull(enfrentamiento.getHora(), "La hora no debe ser nula");

        assertEquals(
                nuevaHora.truncatedTo(ChronoUnit.MINUTES),
                enfrentamiento.getHora().truncatedTo(ChronoUnit.MINUTES),
                "La hora debería coincidir (HH:mm)"
        );
    }
    /**
     * Ejecutado después de cada test individual.
     * Muestra mensaje indicando que terminó.
     */
    @AfterEach
    void tearDown() {
        System.out.println("Test completado.");
    }

    /**
     * Configuración de la desconexión a la base de datos y el DAO.
     * Se ejecuta una vez después de todos los tests.
     * @throws SQLException si ocurre un error al cerrar la conexión
     */
    @AfterAll
    void cerrarConexion() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            System.out.println("Conexión cerrada.");
        }
    }
}
