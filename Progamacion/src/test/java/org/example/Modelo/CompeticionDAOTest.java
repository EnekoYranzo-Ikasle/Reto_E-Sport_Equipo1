package org.example.Modelo;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class CompeticionDAOTest {

    private Connection conn;
    private CompeticionDAO competicionDAO;

    /**
     * Abrimos conexión con la BD
     * @throws Exception
     */
    @BeforeAll
    public void setup() throws Exception {
        // Ajusta según tu configuración
        String url = "jdbc:oracle:thin:@172.20.225.114:1521:orcl";
        String user = "eqdaw01";
        String password = "eqdaw01";

        conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(false);
        competicionDAO = new CompeticionDAO(conn);
    }

    /**
     * Testeamos la funcionalidad de crear competiciones.
     * @throws Exception
     */
    @Order(1)
    @Test
    public void testNuevaCompeticion() throws Exception {
        Competicion comp = new Competicion(0, "Test Liga", LocalDate.of(2025, 6, 1), LocalDate.of(2025, 8, 1));
        competicionDAO.nuevaCompeticion(comp);

        List<Competicion> lista = competicionDAO.getCompeticiones();
        boolean existe = lista.stream().anyMatch(c -> c.getNombre().equals("Test Liga"));
        assertTrue(existe, "La competición debería haberse insertado");
    }

    /**
     * Testeamos que se edita correctamente una competicion.
     * @throws Exception
     */
    @Order(2)
    @Test
    public void testEditarCompeticion() throws Exception {
        Competicion actualizada = new Competicion("Liga Actualizada", LocalDate.of(2025, 1, 10), LocalDate.of(2025, 2, 10));
        competicionDAO.editarCompeticion(actualizada);

        // Como usa curval, debería editar la última insertada
        List<Competicion> lista = competicionDAO.getCompeticiones();
        boolean existe = lista.stream().anyMatch(c -> c.getNombre().equals("Liga Actualizada"));
        assertTrue(existe, "La competición debería haberse actualizado");
    }

    /**
     * Testeamos que una competicion se elimina correctamente
     * @throws Exception
     */
    @Order(3)
    @Test
    public void testEliminarCompeticion() throws Exception {
        Competicion comp = new Competicion("Liga a Eliminar", LocalDate.now(), LocalDate.now().plusDays(10));
        competicionDAO.nuevaCompeticion(comp);

        List<Competicion> listaAntes = competicionDAO.getCompeticiones();
        Competicion ultima = listaAntes.get(listaAntes.size() - 1);

        competicionDAO.eliminarCompeticion(ultima.getCodCompe());

        List<Competicion> listaDespues = competicionDAO.getCompeticiones();
        boolean existe = listaDespues.stream().anyMatch(c -> c.getCodCompe() == ultima.getCodCompe());
        assertFalse(existe, "La competición debería haberse eliminado");
    }

    /**
     * Una vez todos los test completados se hace un rollback para no almacenar los datos en la BD.
     * @throws Exception
     */
    @AfterAll
    public void tearDown() throws Exception {
        conn.rollback();
        conn.close();
    }
}
