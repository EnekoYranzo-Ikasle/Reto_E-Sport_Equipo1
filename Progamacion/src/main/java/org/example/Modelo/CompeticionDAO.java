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

    public void agregarCompeticion(Competicion c) throws SQLException {
        ps = conn.prepareStatement("INSERT INTO competiciones VALUES(?,?,?,?)");
        ps.setString(1, c.getNombre());
        ps.setDate(2, parsearFecha(c.getFechaInicio()));
        ps.setDate(3, parsearFecha(c.getFecha_fin()));
        ps.setString(4, c.getEstado());
        ps.executeUpdate();
    }
    public void modificarCompeticion(Competicion c) throws SQLException {
        ps = conn.prepareStatement("UPDATE competiciones SET nombre = ?, fechaInicio = ?, fechaFin = ?, estado= ? WHERE nombre = ?");
        ps.setString(1,c.getNombre());
        ps.setDate(2,parsearFecha(c.getFechaInicio()));
        ps.setDate(3,parsearFecha(c.getFecha_fin()));
        ps.setString(4, c.getEstado());
        ps.executeUpdate();
    }
    public void eliminarCompeticion(Competicion c) throws SQLException {
        ps = conn.prepareStatement("DELETE FROM competiciones WHERE nombre = ?");
        ps.setString(1, c.getNombre());
        ps.executeUpdate();
    }

    public Competicion obtenerCompeticionActiva() throws SQLException {
        ps = conn.prepareStatement("SELECT * FROM competiciones WHERE estado = 'activa' LIMIT 1");
        rs = ps.executeQuery();

        if (rs.next()) {
            Competicion c = new Competicion();
            c.setCodCompe(rs.getInt(1));
            c.setNombre(rs.getString(2));
            c.setFecha_inicia((rs.getDate(3).toLocalDate()));
            c.setFecha_fin(rs.getDate(4).toLocalDate());
            c.setEstado(rs.getString(5));
            return c;
        } else {
            return null; // No hay competición activa
        }
    }


    private Date parsearFecha(LocalDate fecha1){
        return Date.valueOf(fecha1);
    }

}