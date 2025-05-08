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

    @Test
    void jugadorExiste() {
        try {
            assertTrue(dao.jugadorExiste(1));
            assertFalse(dao.jugadorExiste(777));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void equipoDeJugador() {
        try {
            assertTrue(dao.equipoDeJugador(1));
            assertFalse(dao.equipoDeJugador(777));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getInformeJugadores() {

        try {
            assertNotNull(dao.getInformeJugadores("Equipo Alpha"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    @AfterAll
    public void tearDown() throws Exception {
        if (conn != null) {
            conn.rollback();
            conn.close();
        }
    }
}