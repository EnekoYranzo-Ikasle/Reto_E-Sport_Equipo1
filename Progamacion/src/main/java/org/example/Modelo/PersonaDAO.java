package org.example.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaDAO {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public PersonaDAO(Connection conn) {
        this.conn = conn;
    }

    public Persona getPersona(String email) throws Exception {
        Persona persona;

        try {
            ps = conn.prepareStatement("SELECT * FROM usuarios WHERE UPPER(email) = ?");
            ps.setString(1, email.toUpperCase());
            rs = ps.executeQuery();

            if (!rs.next()) {
                throw new Exception("Email / Contraseña incorrecta");
            }

            persona = crearObjeto(rs);
            
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return persona;
    }

    public void crearCuenta(String email, String pass) throws SQLException {
        ps = conn.prepareStatement("INSERT INTO usuarios (id, email, password, tipo) " +
                "VALUES(sec_codUsuarios.NEXTVAL, ?, ?, 'user')");
        ps.setString(1, email);
        ps.setString(2, pass);

        ps.executeUpdate();
    }

    private Persona crearObjeto(ResultSet rs) throws SQLException {
        return new Persona(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("tipo")
        );
    }
}
