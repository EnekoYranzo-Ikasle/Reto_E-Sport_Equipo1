package org.example.Modelo;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JornadaTest {

    private static Connection conn;
    private JornadaDAO jornadaDAO;


    private static void abrirConexion() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
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
        jornadaDAO = new JornadaDAO(conn);
    }

    @Test
    void generarJornadas() throws Exception {
        List<Equipo> equipos = new ArrayList<>();
        equipos.add(new Equipo(1, "Equipo1"));
        equipos.add(new Equipo(2, "Equipo2"));

        assertDoesNotThrow(() -> {
            jornadaDAO.generarJornadas(1, equipos);
        }, "Debería generar jornadas sin errores con equipos pares");

        // Prueba con número impar de equipos
        equipos.add(new Equipo(3, "Equipo3"));
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            jornadaDAO.generarJornadas(1, equipos);
        });
        assertEquals("No se puede generar la jornada si no hay equipos pares", exception.getMessage());
    }

    @Test
    void obtenerCodJornada() throws Exception {
        List<Integer> codigos = jornadaDAO.obtenerCodJornada();
        assertNotNull(codigos, "La lista de códigos no debe ser nula");
        assertTrue(codigos.size() >= 0, "Debe haber cero o más códigos");
    }

    @Test
    void getJornadas() throws Exception {
        List<Jornada> jornadas = jornadaDAO.getJornadas();
        assertNotNull(jornadas, "La lista de jornadas no debe ser nula");
        assertTrue(jornadas.size() >= 0, "Debe haber cero o más jornadas");
    }

    @Test
    void eliminarJornada() throws Exception {
        // Primero creouna jornada
        int codJornada = jornadaDAO.obtenerCodJornada().get(0);
        jornadaDAO.eliminarJornada(codJornada);

        List<Integer> codigos = jornadaDAO.obtenerCodJornada();
        assertFalse(codigos.contains(codJornada), "El código eliminado no debería estar en la lista");
    }

    @Test
    void editarJornada() throws Exception {
        // Primero creo una jornada
        int codJornada = jornadaDAO.obtenerCodJornada().get(0);
        LocalDate nuevaFecha = LocalDate.now().plusDays(10);

        jornadaDAO.editarJornada(codJornada, nuevaFecha);

        List<Jornada> jornadas = jornadaDAO.getJornadas();
        Jornada editada = jornadas.stream()
                .filter(j -> j.getCodJornada() == codJornada)
                .findFirst()
                .orElse(null);

        assertNotNull(editada, "La jornada editada debería existir");
        assertEquals(nuevaFecha, editada.getFechaJornada(), "La fecha debería haber sido actualizada");
    }
    @AfterEach
    void tearDown() {
        System.out.printf("Cerrando la conexion");
    }

    @AfterAll
    static void cerraConexion() throws SQLException {
        if(conn!=null) {
            conn.close();
        }
    }
}
