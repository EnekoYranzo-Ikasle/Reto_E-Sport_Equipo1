package org.example.Modelo;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompeticionDAOTest {

    private Connection conn;
    private CompeticionDAO dao;

    @BeforeAll
    public void setup() throws Exception {
        // Ajusta según tu configuración
        String url = "jdbc:oracle:thin:@172.20.225.114:1521:orcl";
        String user = "eqdaw01";
        String password = "eqdaw01";

        conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(false);  // No confirmar cambios en BD
        dao = new CompeticionDAO(conn);
    }

    @Test
    public void testNuevaCompeticion() throws Exception {
        Competicion comp = new Competicion(0, "Test Liga", LocalDate.of(2025, 6, 1), LocalDate.of(2025, 8, 1));
        dao.nuevaCompeticion(comp);

        List<Competicion> lista = dao.getCompeticiones();
        boolean existe = lista.stream().anyMatch(c -> c.getNombre().equals("Test Liga"));
        assertTrue(existe, "La competición debería haberse insertado");
    }

    @Test
    public void testEditarCompeticion() throws Exception {
        Competicion comp = new Competicion("Temp Liga", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 2, 1));
        dao.nuevaCompeticion(comp);

        Competicion actualizada = new Competicion("Liga Actualizada", LocalDate.of(2025, 1, 10), LocalDate.of(2025, 2, 10));
        dao.editarCompeticion(actualizada);

        // Como usa curval, debería editar la última insertada
        List<Competicion> lista = dao.getCompeticiones();
        boolean existe = lista.stream().anyMatch(c -> c.getNombre().equals("Liga Actualizada"));
        assertTrue(existe, "La competición debería haberse actualizado");
    }

    @Test
    public void testEliminarCompeticion() throws Exception {
        Competicion comp = new Competicion("Liga a Eliminar", LocalDate.now(), LocalDate.now().plusDays(10));
        dao.nuevaCompeticion(comp);

        List<Competicion> listaAntes = dao.getCompeticiones();
        Competicion ultima = listaAntes.get(listaAntes.size() - 1);

        dao.eliminarCompeticion(ultima.getCodCompe());

        List<Competicion> listaDespues = dao.getCompeticiones();
        boolean existe = listaDespues.stream().anyMatch(c -> c.getCodCompe() == ultima.getCodCompe());
        assertFalse(existe, "La competición debería haberse eliminado");
    }

    @AfterAll
    public void tearDown() throws Exception {
        if (conn != null) {
            conn.rollback();
            conn.close();
        }
    }
}
