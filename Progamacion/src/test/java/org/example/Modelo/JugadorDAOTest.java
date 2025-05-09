package org.example.Modelo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JugadorDAOTest {

    private Connection conn;
    private JugadorDAO dao;

    /**
     * Configuración de la conexión a la base de datos y el DAO.
     * Se ejecuta una vez antes de todos los tests.
     */
    @BeforeAll
    public void setup() throws Exception {
        // Ajusta según tu configuración
        String url = "jdbc:oracle:thin:@172.20.225.114:1521:orcl";
        String user = "eqdaw01";
        String password = "eqdaw01";

        conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(false);  // No confirmar cambios en BD
        dao = new JugadorDAO(conn);
    }

    /**
     * Test que prueba que la función devuelva un ArrayList de los jugadores existentes en la base de datos.
     * @Return Lista de jugadores
     * @throws SQLException Si ocurre un error SQL.
     */
    @Test
    void getListaJugadores() {
        try {
            List<Jugador> listaJugadores = dao.getListaJugadores();
            assertNotNull(listaJugadores);
            assertFalse(listaJugadores.isEmpty());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test que prueba que la función el jugador cuyo código se le pase como parámetro.
     * @param "codJugador" El código del jugador.
     * @return El jugador encontrado.
     * @throws SQLException Si ocurre un error SQL.
     */
    @Test
    void mostrarJugador() {
        LocalDate fecha = LocalDate.of(1995, 3, 12);
        Jugador j = new Jugador(1, "Juan", "Pérez", "Española", fecha, "juanito", Roles.DUELISTA, 1500, 1);
        try {

            assertEquals(j.toString().toUpperCase(), dao.mostrarJugador(1).toString().toUpperCase());
            assertNotEquals(j, dao.mostrarJugador(1).toString().toUpperCase());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test que prueba que la función devuelva un jugador existente dentro de la base de datos.
     * @param "codJugador" El código del jugador.
     * @return true si el jugador existe, false en caso contrario.
     * @throws SQLException Si ocurre un error SQL.
     */
    @Test
    void jugadorExiste() {
        try {
            assertTrue(dao.jugadorExiste(1));
            assertFalse(dao.jugadorExiste(777));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test que prueba que la función verifique si un jugador pertenece a un equipo.
     * @param "codJug" El código del jugador.
     * @return true si el jugador pertenece a un equipo, false en caso contrario.
     * @throws SQLException Si ocurre un error SQL.
     */
    @Test
    void equipoDeJugador() {
        try {
            assertTrue(dao.equipoDeJugador(1));
            assertFalse(dao.equipoDeJugador(777));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test que prueba que la función devuelva un informe de jugadores pertenecientes a un equipo específico.
     * @param "nombreEquipo" El nombre del equipo.
     * @return Lista de jugadores del equipo.
     * @throws SQLException Si ocurre un error SQL.
     */
    @Test
    void getInformeJugadores() {

        try {
            assertNotNull(dao.getInformeJugadores("Equipo Alpha"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test que prueba que la función devuelva un ArrayList de jugadores que pertenecen a un equipo específico.
     * @param "codEquip" El código del equipo.
     * @return Lista de jugadores del equipo.
     * @throws SQLException Si ocurre un error SQL.
     */
    @Test
    void jugadorPorEquipo() {
        try {
            assertNotNull(dao.jugadorPorEquipo(1));

            List<Jugador> j = new ArrayList<>();
            assertEquals(j, dao.jugadorPorEquipo(777));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Configuración de la desconexión a la base de datos y el DAO.
     * Se ejecuta una vez después de todos los tests.
     */
    @AfterAll
    public void tearDown() throws Exception {
        if (conn != null) {
            conn.rollback();
            conn.close();
        }
    }
}