package org.example.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public PersonaDAO(Connection conn) {
        this.conn = conn;
    }

    public Persona getPersona(String email) throws SQLException {
        ps = conn.prepareStatement("SELECT * FROM usuarios WHERE UPPER(email) = ?");
        ps.setString(1, email.toUpperCase());
        rs = ps.executeQuery();

        rs.next();
        return crearObjeto(rs);
    }

    public void crearCuenta(String email, String pass) throws SQLException {
        ps = conn.prepareStatement("INSERT INTO usuarios (email, password, tipo) VALUES(?,?, 'user')");
        ps.setString(1, email);
        ps.setString(2, pass);
        ps.executeUpdate();
    }

    private Persona crearObjeto(ResultSet rs) throws SQLException {
        Persona persona = new Persona(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("tipo")
        );
        return persona;
    }
}
