package org.example.Modelo;

import java.sql.*;
import java.time.LocalDate;

public class CompeticionDAO {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private String plantilla;

    public CompeticionDAO(Connection conn) {
        this.conn = conn;
    }

    public void nuevaCompeticion(Competicion competicion) throws SQLException {
        ps = conn.prepareStatement("INSERT INTO competiciones (nombre, fechaInicio, fechaFin) VALUES(?,?,?)");
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

    private Date parsearFecha(LocalDate fecha1){
        return Date.valueOf(fecha1);
    }

}