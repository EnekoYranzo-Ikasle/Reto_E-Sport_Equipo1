package org.example.Modelo;

import org.example.Util.ConexionDB;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonaDAOTest {

    private static Connection conn;
    private static PersonaDAO PersonaDAO;


    /**
     * Configuración de la conexión a la base de datos y el DAO.
     * Se ejecuta una vez antes de todos los tests.
     * @throws SQLException Si ocurre un error SQL.
     */
    @BeforeAll
    public static void setup() throws Exception {
        String url = "jdbc:oracle:thin:@172.20.225.114:1521:orcl";
        String user = "eqdaw01";
        String password = "eqdaw01";

        conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(false);
        PersonaDAO = new PersonaDAO(conn);
    }

    /*Obtener una persona existente por su email*/
    @Test
    void getPersona_UsuarioExistente () throws Exception {
        String email = "usuario@gmail.com";
        String pass = "password123";
        PersonaDAO.crearCuenta(email, pass);

        Persona persona = PersonaDAO.getPersona(email);

        assertNotNull(persona, "La persona no debe ser nula");
        assertEquals(email, persona.getEmail(), "El email no coincide");
        assertEquals(pass, persona.getPassword(), "La contraseña no coincide");
        assertEquals("user", persona.getTipo(), "El tipo de usuario no coincide");
    }

    /*Verifica si se lanza una excepción al buscar una persona con un email inexistente*/
    @Test
    void getPersona_UsuarioNoExistente () {
        String emailInexistente = "noexistente@gmail.com";

        Exception exception = assertThrows(Exception.class, () -> PersonaDAO.getPersona(emailInexistente), "Debería lanzar una excepción");
        assertEquals("Email / Contraseña incorrecta", exception.getMessage(), "El mensaje de error no coincide");
    }

    /**
     * Verifica si se puede crear un nuevo usuario correctamente
     */
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

    /**
     * Si el programa da un error al intentar usar un email demasido largo
     */
    @Test
    void crearCuenta_EmailDemasiadoLargo() throws SQLException {
        String emailLargo = "a".repeat(256) + "@gmail.com";
        assertThrows(SQLException.class, () -> PersonaDAO.crearCuenta(emailLargo, "password123"), "excepcion por email demasiado largo");
    }

    /**
     * Si el programa da un error al intentar usar un email vacío.
     */
    @Test
    void crearCuenta_EmailNulo () throws SQLException {
        assertThrows(SQLException.class, () -> PersonaDAO.crearCuenta(null, "password123"),"excepcion por email nulo");
    }

    /**
     * si el programa da un error al intentar usar una contraseña vacía.
     */
    @Test
    void crearCuenta_PassNulo () throws SQLException {
        assertThrows(SQLException.class, () -> PersonaDAO.crearCuenta("usuario@gmail.com", null), "excepcion por pass nulo");
    }

    /**
     * Configuración de la desconexión a la base de datos y el DAO.
     * Se ejecuta una vez después de todos los tests.
     * @throws SQLException si ocurre un error al cerrar la conexión
     */
    @AfterAll
    public static void tearDown() throws Exception {
        conn.rollback();
        conn.close();
    }
}
