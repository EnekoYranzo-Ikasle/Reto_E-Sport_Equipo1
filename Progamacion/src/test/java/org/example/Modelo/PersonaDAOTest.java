package org.example.Modelo;

import org.example.Util.ConexionDB;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonaDAOTest {

    private static Connection conn;
    private static PersonaDAO PersonaDAO;

    @BeforeAll
    public static void setup() throws Exception {
        String url = "jdbc:oracle:thin:@172.20.225.114:1521:orcl";
        String user = "eqdaw01";
        String password = "eqdaw01";

        conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(false);
        PersonaDAO = new PersonaDAO(conn);
    }

    @Test
    void getPersona_PersonaNoExistentePorEmail() throws SQLException {
        Persona persona = PersonaDAO.getPersona("nonexistent@example.com");
        assertNull(persona, "La persona no debería existir.");
    }

    /*Verifica si se puede crear un nuevo usuario correctamente */
    @Test
    void crearCuenta_InsertarNuevoUsuario() throws SQLException {
        String email = "usuario@gmail.com";
        String pass = "password123";
        PersonaDAO.crearCuenta(email, pass);

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM usuarios WHERE email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        assertTrue(rs.next(), "El usuario no fue insertado correctamente");
        assertEquals(email, rs.getString("email"), "El email no coincide");
        assertEquals(pass, rs.getString("password"), "La contraseña no coincide");
        assertEquals("user", rs.getString("tipo"), "El tipo de usuario no coincide");
    }

    /*Si el programa da un error al intentar usar un email demasido largo*/
    @Test
    void crearCuenta_EmailDemasiadoLargo() throws SQLException {
        String emailLargo = "a".repeat(256) + "@gmail.com";
        assertThrows(SQLException.class, () -> PersonaDAO.crearCuenta(emailLargo, "password123"), "excepcion por email demasiado largo");
    }

    /*Si el programa da un error al intentar usar un email vacío.*/
    @Test
    void crearCuenta_EmailNulo () throws SQLException {
        assertThrows(SQLException.class, () -> PersonaDAO.crearCuenta(null, "password123"),"excepcion por email nulo");
    }

    /*si el programa da un error al intentar usar una contraseña vacía.*/
    @Test
    void crearCuenta_PassNulo () throws SQLException {
        assertThrows(SQLException.class, () -> PersonaDAO.crearCuenta("usuario@gmail.com", null), "excepcion por pass nulo");
    }

    @AfterAll
    public static void tearDown() throws Exception {
        conn.rollback();
        conn.close();
    }
}
