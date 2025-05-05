package org.example.Modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompeticionDAO {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private String plantilla;

    public CompeticionDAO(Connection conn) {
        this.conn = conn;
    }

    public void nuevaCompeticion(Competicion competicion) throws SQLException {
        ps = conn.prepareStatement("INSERT INTO competiciones (codCompeticion, nombre, fechaInicio, fechaFin, estado) " +
                "VALUES(sec_codCompeticion.NEXTVAL,?,?,?, 'activo')");
        ps.setString(1, competicion.getNombre());
        ps.setDate(2, parsearFecha(competicion.getFechaInicio()));
        ps.setDate(3, parsearFecha(competicion.getFecha_fin()));
        ps.executeUpdate();
    }

    public void modificarCompeticion(Competicion c) throws SQLException {
        ps = conn.prepareStatement("UPDATE competiciones SET nombre = ?, fechaInicio = ?, fechaFin = ? WHERE codCompeticion = ?");
        ps.setString(1,c.getNombre());
        ps.setDate(2,parsearFecha(c.getFechaInicio()));
        ps.setDate(3,parsearFecha(c.getFecha_fin()));
        ps.executeUpdate();
    }

    public void eliminarCompeticion(Competicion c) throws SQLException {
        ps = conn.prepareStatement("DELETE FROM competiciones WHERE codCompeticion = ?");
        ps.setString(1, c.getNombre());
        ps.executeUpdate();
    }

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

    private Date parsearFecha(LocalDate fecha1){
        return Date.valueOf(fecha1);
    }

}