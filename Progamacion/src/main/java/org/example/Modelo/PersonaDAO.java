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

    /**
     * Obtiene la información de una persona (usuario) desde la base de datos a partir del email.
     * @param email El email del usuario.
     * @return Un objeto Persona con los datos del usuario.
     * @throws Exception Si no se encuentra el usuario o ocurre un error durante la consulta.
     */
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

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return persona;
    }

    /**
     * Crea una nueva cuenta de usuario en la base de datos.
     * @param email El email del nuevo usuario.
     * @param pass  La contraseña del nuevo usuario.
     * @throws SQLException Si ocurre un error durante la inserción.
     */
    public void crearCuenta(String email, String pass) throws SQLException {
        ps = conn.prepareStatement("INSERT INTO usuarios (id, email, password, tipo) " +
                "VALUES(sec_codUsuarios.NEXTVAL, ?, ?, 'user')");
        ps.setString(1, email);
        ps.setString(2, pass);

        ps.executeUpdate();
    }

    /**
     * Crea un objeto Persona a partir de un ResultSet con los datos obtenidos de la base de datos.
     * @param rs El ResultSet de la consulta a la base de datos.
     * @return Un objeto Persona con los datos del usuario.
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet.
     */
    private Persona crearObjeto(ResultSet rs) throws SQLException {
        return new Persona(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("tipo")
        );
    }
}