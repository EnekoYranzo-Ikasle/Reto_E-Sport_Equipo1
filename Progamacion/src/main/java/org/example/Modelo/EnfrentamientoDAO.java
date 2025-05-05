package org.example.Modelo;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnfrentamientoDAO {
    private final Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;

    public EnfrentamientoDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Integer> obtenerGanadores(int codjornada) throws SQLException {
        ps=conn.prepareStatement("select ganador from enfrentamientos where jornada=?");
        ps.setInt(1, codjornada);
        rs=ps.executeQuery();
        
        List<Integer> ganadores = new ArrayList<>();

        while (rs.next()) {
            ganadores.add(rs.getInt("ganador"));
        }
        return ganadores;
    }

    public List<Enfrentamiento> obtenerEnfrentamientos()throws SQLException{
        List<Enfrentamiento> lista = new ArrayList<>();

        ps = conn.prepareStatement("select * from enfrentamientos");
        rs = ps.executeQuery();

        while (rs.next()) {
            Enfrentamiento enfrentamiento = new Enfrentamiento();
            Equipo equipo1 = new Equipo();
            Equipo equipo2 = new Equipo();

            enfrentamiento.setCodEnfrentamiento(rs.getInt("codenfrentamiento"));
            equipo1.setCodEquipo(rs.getInt("equipo1"));
            equipo2.setCodEquipo(rs.getInt("equipo2"));
            equipo1.setNombreEquipo(sacarNombrEquipo(equipo1.getCodEquipo()));
            equipo2.setNombreEquipo(sacarNombrEquipo(equipo2.getCodEquipo()));

            enfrentamiento.setEquipo1(equipo1);
            enfrentamiento.setEquipo2(equipo2);
            lista.add(enfrentamiento);
        }
        return lista;
    }

    public String sacarNombrEquipo(int codequipo) throws SQLException {
        ps=conn.prepareStatement("select nombre from equipos where codequipo=?");
        ps.setInt(1, codequipo);
        rs=ps.executeQuery();

        if (rs.next()) {
            return rs.getString("nombre");
        }else {
            return null;
        }

    }
    public void setGanador(int codGanador, int codEnfrentamiento) throws SQLException {
        ps=conn.prepareStatement("UPDATE enfrentamientos SET ganador=? WHERE codenfrentamiento=?");
        ps.setInt(1, codGanador);
        ps.setInt(2, codEnfrentamiento);
        ps.executeUpdate();
    }

    public boolean enfrentamientoExiste(int codenfrentamiento) throws SQLException {
        ps=conn.prepareStatement("select * from enfrentamientos where codEnfrentamiento=?");
        ps.setInt(1, codenfrentamiento);
        rs=ps.executeQuery();

        if (rs.next()) {
            return true;
        } else
            return false;
    }

    public void setHora(String tiempo, int codEnfentamiento)throws SQLException{
        ps=conn.prepareStatement("update enfrentamientos set hora=? where codenfrentamiento=?");
        ps.setString(1, tiempo);
        ps.setInt(2, codEnfentamiento);
        ps.executeUpdate();
    }
}
