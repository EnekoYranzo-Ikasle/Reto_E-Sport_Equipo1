package org.example.Modelo;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EnfrentamientoTest {
    private Connection conn;
    private EnfrentamientoDAO enfrentamientoDAO;

    @BeforeAll
    public void setup() throws Exception {
        String url = "jdbc:oracle:thin:@172.20.225.114:1521:orcl";
        String user = "eqdaw01";
        String password = "eqdaw01";

        conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(false); // No guardar cambios
        enfrentamientoDAO = new EnfrentamientoDAO(conn);
    }

    @Test
    void obtenerGanadoresConGanadores() throws SQLException {
        int codJornada = 14;
        List<Integer> ganadores = enfrentamientoDAO.obtenerGanadores(codJornada);

        assertNotNull(ganadores, "La lista de ganadores no debe ser nula");
        assertFalse(ganadores.isEmpty(), "Debe haber al menos un ganador en la jornada " + codJornada);

        System.out.printf("Ganadores encontrados para jornada %d: %d%n", codJornada, ganadores.size());
    }

    @Test
    void obtenerGanadoresSinGanadores() throws SQLException {
        int codJornada = 14;
        List<Integer> ganadores = enfrentamientoDAO.obtenerGanadores(codJornada);

        assertNotNull(ganadores, "La lista de ganadores no debe ser nula");
        assertTrue(ganadores.isEmpty(), "No debe haber ganadores en la jornada " + codJornada);

        System.out.printf("Ganadores encontrados para jornada %d: %d%n", codJornada, ganadores.size());
    }

    @Test
    void obtenerEnfrentamientos() throws SQLException {
        List<Enfrentamiento> enfrentamientos = enfrentamientoDAO.obtenerEnfrentamientos();
        assertNotNull(enfrentamientos, "La lista de enfrentamientos no debe ser nula");
        System.out.println("Número de enfrentamientos obtenidos: " + enfrentamientos.size());
    }

    @Test
    void sacarNombreEquipo() throws SQLException {
        int codEquipo = 2; // Asegúrate que este equipo existe
        String nombre = enfrentamientoDAO.sacarNombrEquipo(codEquipo);
        assertNotNull(nombre, "El nombre del equipo no debe ser nulo");
        assertFalse(nombre.trim().isEmpty(), "El nombre del equipo no debe estar vacío");
        System.out.println("El nombre del equipo es: " + nombre);
    }

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

    @Test
    void enfrentamientoExiste() throws SQLException {
        int codEnfrentamiento = 11;
        boolean existe = enfrentamientoDAO.enfrentamientoExiste(codEnfrentamiento);
        assertTrue(existe, "El enfrentamiento debería existir");
    }

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

    @AfterEach
    void tearDown() {
        System.out.println("Test completado.");
    }

    @AfterAll
    void cerrarConexion() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            System.out.println("Conexión cerrada.");
        }
    }
}
