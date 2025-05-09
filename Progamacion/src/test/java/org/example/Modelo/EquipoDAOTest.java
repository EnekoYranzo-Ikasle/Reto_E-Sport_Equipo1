package org.example.Modelo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class EquipoDAOTest {

    private static Connection conn;
    private static EquipoDAO dao;

    /**
     * Configuración de la conexión a la base de datos y el DAO.
     * Se ejecuta una vez antes de todos los tests.
     * @throws SQLException Si ocurre un error SQL.
     */
    @BeforeAll
    public static void setup() throws Exception {
        // Ajusta según tu configuración
        String url = "jdbc:oracle:thin:@172.20.225.114:1521:orcl";
        String user = "eqdaw01";
        String password = "eqdaw01";

        conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(false);  // No confirmar cambios en BD
        dao = new EquipoDAO(conn);
    }

    @Test
    void obtenerEquipos() {
        try {
            List<Equipo> listaEquipos = dao.obtenerEquipos();
            assertNotNull(listaEquipos);
            assertFalse(listaEquipos.isEmpty());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void buscarEquipoPorCod() {
        try {
            assertNotNull(dao.buscarEquipoPorCod(1));
            assertEquals(1, dao.buscarEquipoPorCod(1).getCodEquipo());
            assertNull(dao.buscarEquipoPorCod(777));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void buscarEquipoPorNombre() {
        try {
            assertNotNull(dao.buscarEquipoPorNombre("Equipo Alpha"));
            assertNull( dao.buscarEquipoPorNombre("Real Vardrid").getNombreEquipo());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void existe() {
        try {
            assertTrue(dao.Existe("Equipo Alpha"));
            assertTrue(dao.Existe("Equipo Beta"));
            assertFalse(dao.Existe("Los Wachiturros"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void getInformeEquipos() {
        try {
            assertNotNull(dao.getInformeEquipos(1));
            List<Object[]> informe = new ArrayList<>();
            assertEquals(informe,dao.getInformeEquipos(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Configuración de la desconexión a la base de datos y el DAO.
     * Se ejecuta una vez después de todos los tests.
     * @throws SQLException si ocurre un error al cerrar la conexión
     */
    @AfterAll
    public void tearDown() throws Exception {
        if (conn != null) {
            conn.rollback();
            conn.close();
        }
    }
}