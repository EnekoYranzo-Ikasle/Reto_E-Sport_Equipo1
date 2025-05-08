package org.example.Modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CompeticionDAO {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public CompeticionDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Función que guarda las nuevas competiciones en la BD
     * @param competicion como dato de entrada.
     * @throws SQLException para manejar los errores de la BD.
     */
    public void nuevaCompeticion(Competicion competicion) throws SQLException {
        ps = conn.prepareStatement("INSERT INTO competiciones (codCompeticion, nombre, fechaInicio, fechaFin, estado) " +
                "VALUES(sec_codCompeticion.NEXTVAL,?,?,?, 'activo')");
        ps.setString(1, competicion.getNombre());
        ps.setDate(2, parsearFecha(competicion.getFechaInicio()));
        ps.setDate(3, parsearFecha(competicion.getFecha_fin()));
        ps.executeUpdate();
    }

    /**
     * Función para modificar el nombre y las fechas en la BD.
     * @param competicion como dato de entrada
     * @throws SQLException para manejar los errores de la BD.
     */
    public void editarCompeticion(Competicion competicion) throws SQLException {
        ps = conn.prepareStatement("update competiciones set nombre = ?, fechaInicio = ?, fechaFin = ? " +
                "where codCompeticion = sec_codCompeticion.CURVAL");
        ps.setString(1, competicion.getNombre());
        ps.setDate(2, parsearFecha(competicion.getFechaInicio()));
        ps.setDate(3, parsearFecha(competicion.getFecha_fin()));
        ps.executeUpdate();
    }

    /**
     * Función para eliminar la competición de la BD.
     * @param codCompeticion como dato de entrada
     * @throws SQLException para manejar los errores de la BD.
     */
    public void eliminarCompeticion(int codCompeticion) throws SQLException {
        ps = conn.prepareStatement("DELETE FROM competiciones WHERE codCompeticion = ?");
        ps.setInt(1, codCompeticion);
        ps.executeUpdate();
    }

    /**
     * Función para obtener las competiciones
     * @return List
     * @throws SQLException para manejar los errores de la BD.
     */
    public List<Competicion> getCompeticiones() throws SQLException {
        ps = conn.prepareStatement("SELECT * FROM competiciones");
        rs = ps.executeQuery();

        List<Competicion> competiciones = new ArrayList<>();
        while (rs.next()) {
            Competicion competicion = new Competicion(
                    rs.getInt("CODCOMPETICION"),
                    rs.getString("NOMBRE"),
                    rs.getDate("FECHAINICIO").toLocalDate(),
                    rs.getDate("FECHAFIN").toLocalDate()
            );

            competiciones.add(competicion);
        }
        return competiciones;
    }

    /**
     * Función para parsear la fecha a formato admitido por SQL
     * @param fecha1 como dato de entrada
     * @return Date
     */
    private Date parsearFecha(LocalDate fecha1){
        return Date.valueOf(fecha1);
    }

}