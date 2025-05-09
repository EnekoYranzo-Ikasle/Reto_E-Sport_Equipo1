package org.example.Modelo;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
/**
 * Clase de prueba unitaria para JornadaDAO.
 *
 * Esta clase usa JUnit 5 para verificar las operaciones principales sobre jornadas:
 * - Generar jornadas
 * - Obtener códigos de jornadas
 * - Obtener listado de jornadas
 * - Eliminar jornada
 * - Editar jornada (cambiar fecha)
 *
 * Las pruebas se conectan directamente a una base de datos Oracle.
 * La conexión se mantiene durante toda la clase y se cierra al final.
 *
 * Nota: Se usa @Assume para evitar fallos si no existen datos previos en la base.
 *
 * Autor: Grupo 1
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JornadaTest {

    private Connection conn;
    private JornadaDAO jornadaDAO;

    /**
     * Tenemos que conectarnos la base de datos de Oracle para cada test
     * Usamos esta base de datos para poder hacer las operaciones y consultas
     *
     * @throws Exception Si ocurre un error al conectarnos a la BD
     */
    @BeforeAll
    void setup() throws Exception {
        String url = "jdbc:oracle:thin:@172.20.225.114:1521:orcl";
        String user = "eqdaw01";
        String password = "eqdaw01";

        conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(false); // Para evitar cambios reales en la base
        jornadaDAO = new JornadaDAO(conn);
    }

    /**
     * Prueba la generación de jornadas con una lista de equipos.
     * Verifica que:
     * - Con equipos pares no lanza errores.
     * - Con equipos impares lanza una excepción específica.
     *
     * @throws Exception si ocurre un error
     */
    @Test
    void generarJornadas() throws Exception {
        List<Equipo> equipos = new ArrayList<>();
        equipos.add(new Equipo(4, "Equipo1"));
        equipos.add(new Equipo(5, "Equipo2"));

        assertDoesNotThrow(() -> {
            jornadaDAO.generarJornadas(1, equipos);
        }, "Debería generar jornadas sin errores con equipos pares");

        // Prueba con número impar de equipos
        equipos.add(new Equipo(6, "Equipo3"));
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            jornadaDAO.generarJornadas(1, equipos);
        });
        assertEquals("No se puede generar la jornada si no hay equipos pares", exception.getMessage());
    }

    /**
     * Prueba obtener la lista de códigos de jornada.
     *
     * @throws Exception si ocurre un error
     */
    @Test
    void obtenerCodJornada() throws Exception {
        List<Integer> codigos = jornadaDAO.obtenerCodJornada();
        assertNotNull(codigos, "La lista de códigos no debe ser nula");
        assertTrue(codigos.size() >= 0, "Debe haber cero o más códigos");
        System.out.println(codigos);
    }

    /**
     * Prueba obtener la lista completa de jornadas.
     *
     * @throws Exception si ocurre un error
     */
    @Test
    void getJornadas() throws Exception {
        List<Jornada> jornadas = jornadaDAO.getJornadas();
        assertNotNull(jornadas, "La lista de jornadas no debe ser nula");
        assertTrue(jornadas.size() >= 0, "Debe haber cero o más jornadas");
        System.out.println(jornadas.size());
    }

    /**
     * Prueba eliminar una jornada específica.
     * Usa el primer código de la lista para eliminarlo.
     *
     * @throws Exception si ocurre un error
     */
    @Test
    void eliminarJornada() throws Exception {
        List<Integer> codigos = jornadaDAO.obtenerCodJornada();
        assumeFalse(codigos.isEmpty(), "No hay jornadas para eliminar");

        int codJornada = codigos.get(0);
        jornadaDAO.eliminarJornada(codJornada);

        List<Integer> codigosActualizados = jornadaDAO.obtenerCodJornada();
        assertFalse(codigosActualizados.contains(codJornada), "El código eliminado no debería estar en la lista");
    }

    /**
     * Prueba editar una jornada, cambiando su fecha.
     *
     * @throws Exception si ocurre un error
     */
    @Test
    void editarJornada() throws Exception {
        List<Integer> codigos = jornadaDAO.obtenerCodJornada();
        assumeFalse(codigos.isEmpty(), "No hay jornadas para editar");

        int codJornada = codigos.get(0);
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

    /**
     * Ejecutado después de cada prueba.
     * Muestra mensaje indicando finalización.
     */
    @AfterEach
    void tearDown() {
        System.out.println("Test completado.");
    }

    /**
     * Cierra la conexión a la base de datos al finalizar todas las pruebas.
     *
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
