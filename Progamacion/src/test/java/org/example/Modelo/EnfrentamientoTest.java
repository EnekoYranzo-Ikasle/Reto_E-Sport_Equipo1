package org.example.Modelo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnfrentamientoTest {
    private static Connection conn;
    private EnfrentamientoDAO enfrentamientoDAO;

    private static void abrirConexion() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Para usar en clase
            String url = "jdbc:oracle:thin:@172.20.225.114:1521:orcl";
            String user = "eqdaw01";
            String password = "eqdaw01";

            conn = DriverManager.getConnection(url, user, password);

        }catch (ClassNotFoundException e) {
            System.out.println("Error en Class.forName " + e.getMessage());
        }catch (Exception e) {
            System.out.println("Error al abrir conexion " + e.getMessage());
        }

    }
    @BeforeEach
    void setUp() {
        enfrentamientoDAO = new EnfrentamientoDAO(conn);
    }

    @Test
    void obtenerGanadores() throws SQLException {
        int codJornadaPrueba = 1111;
        List<Integer> ganadores = enfrentamientoDAO.obtenerGanadores(codJornadaPrueba);
        assertNotNull(ganadores, "La lista de ganadores no debe ser nula");
        assertTrue(ganadores.size() >= 0, "Debe haber cero o más ganadores");
    }

    @Test
    void obtenerEnfrentamientos() throws SQLException {
        List<Enfrentamiento> enfrentamientos = enfrentamientoDAO.obtenerEnfrentamientos();
        assertNotNull(enfrentamientos, "La lista de enfrentamientos no debe ser nula");
        assertTrue(enfrentamientos.size() >= 0, "Debe haber cero o más enfrentamientos");
    }

    @Test
    void sacarNombrEquipo() throws SQLException {
        int codEquipoPrueba = 1111;
        String nombre = enfrentamientoDAO.sacarNombrEquipo(codEquipoPrueba);
        assertNotNull(nombre, "El nombre del equipo no debe ser nulo");
        assertFalse(nombre.isEmpty(), "El nombre del equipo no debe estar vacío");
    }

    @Test
    void setGanador() throws SQLException {
        int codEnfrentamientoPrueba = 1111;
        int codGanadorPrueba = 1222;
        enfrentamientoDAO.setGanador(codGanadorPrueba, codEnfrentamientoPrueba);

        List<Integer> ganadores = enfrentamientoDAO.obtenerGanadores(1111);
        assertTrue(ganadores.contains(codGanadorPrueba), "El ganador debe estar registrado");
    }

    @Test
    void enfrentamientoExiste() throws SQLException {
        int codEnfrentamientoPrueba = 1111;
        boolean existe = enfrentamientoDAO.enfrentamientoExiste(codEnfrentamientoPrueba);
        assertTrue(existe, "El enfrentamiento debería existir");
    }

    @Test
    void setHora() throws SQLException {
        int codEnfrentamientoPrueba = 1111;
        String nuevaHora = LocalTime.now().toString();
        enfrentamientoDAO.setHora(nuevaHora, codEnfrentamientoPrueba);

        List<Enfrentamiento> lista = enfrentamientoDAO.obtenerEnfrentamientos();
        Enfrentamiento enf = lista.stream()
                .filter(e -> e.getCodEnfrentamiento() == codEnfrentamientoPrueba)
                .findFirst()
                .orElse(null);

        assertNotNull(enf, "El enfrentamiento debería existir");
        assertEquals(nuevaHora.substring(0, 5), enf.getHora().toString().substring(0, 5), "La hora debería coincidir (HH:mm)");
    }

    @AfterEach
    void tearDown() {
        System.out.printf("Cerrando la conexion");
    }
    @AfterAll
    static void cerrarConexion() throws SQLException {
        if(conn!=null) {
            conn.close();
        }
    }
}