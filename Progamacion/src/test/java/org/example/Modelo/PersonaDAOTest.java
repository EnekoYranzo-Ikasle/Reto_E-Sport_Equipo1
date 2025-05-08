package org.example.Modelo;

import org.example.Util.ConexionDB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PersonaDAOTest {

    private static Connection con;
    private PersonaDAO PersonaDAO;

    @BeforeAll
    static void setUpAll() {
        ConexionDB.connect();
        con = ConexionDB.getConnection();
    }

    @BeforeEach
    void setUp() throws Exception {
        con.setAutoCommit(false);
        PersonaDAO = new PersonaDAO(con);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (con != null) {
            con.rollback();
        }
    }

    @Test
    void getPersona() {
    }

    @Test
    void crearCuenta_InsertarNuevoUsuario() throws SQLException {
        String email = "usuario@gmail.com";
        String pass = "password123";
        PersonaDAO.crearCuenta(email, pass);

        PreparedStatement ps = con.prepareStatement("SELECT * FROM usuarios WHERE email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        assertTrue(rs.next(), "El usuario no fue insertado correctamente");
        assertEquals(email, rs.getString("email"), "El email no coincide");
        assertEquals(pass, rs.getString("password"), "La contraseña no coincide");
        assertEquals("user", rs.getString("tipo"), "El tipo de usuario no coincide");
    }

    @Test
    void crearCuenta_EmailDemasiadoLargo() throws SQLException {
        String emailLargo = "a".repeat(256) + "@gmail.com";
        assertThrows(SQLException.class, () -> PersonaDAO.crearCuenta(emailLargo, "password123"), "excepcion por email demasiado largo");
    }

    @Test
    void crearCuenta_EmailNulo () throws SQLException {
        assertThrows(SQLException.class, () -> PersonaDAO.crearCuenta(null, "password123"),"excepcion por email nulo");
    }

    @Test
    void crearCuenta_PassNulo () throws SQLException {
        assertThrows(SQLException.class, () -> PersonaDAO.crearCuenta("usuario@gmail.com", null), "excepcion por pass nulo");
    }
}
