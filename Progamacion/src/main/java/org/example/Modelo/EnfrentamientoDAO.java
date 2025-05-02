package org.example.Modelo;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnfrentamientoDAO {
    private final Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;

    public EnfrentamientoDAO(Connection conn) {
        this.conn = conn;
    }

    public void agregarResultados(String seleccion, List<Enfrentamiento> lista, String resultado){
        if (seleccion != null) {
            int index = lista.indexOf(lista.stream().filter(e -> e.toString().equals(seleccion)).findFirst().orElse(null));

            if (index != -1) {

                if (resultado != null && !resultado.isEmpty()) {
                    lista.get(index).setResultado(resultado);
                    JOptionPane.showMessageDialog(null, "Resultado actualizado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un resultado válido.");
                }
            }
        }
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
    public List<Enfrentamiento>obtenerEnfrentamientos()throws SQLException{
        List<Enfrentamiento> lista = new ArrayList<>();
        ps=conn.prepareStatement("select * from enfrentamientos");
        rs=ps.executeQuery();
        while (rs.next()) {

            Enfrentamiento enfrentamiento = new Enfrentamiento();
            Equipo euipo1 = new Equipo();
            Equipo euipo2 = new Equipo();
            enfrentamiento.setCodEnfrentamiento(rs.getInt("codenfrentamiento"));
            euipo1.setCodEquipo(rs.getInt("equipo1"));
            euipo2.setCodEquipo(rs.getInt("equipo2"));
            euipo1.setNombreEquipo(sacarNombrEquipo(euipo1.getCodEquipo()));
            euipo2.setNombreEquipo(sacarNombrEquipo(euipo2.getCodEquipo()));

            enfrentamiento.setEquipo1(euipo1);
            enfrentamiento.setEquipo2(euipo2);
            lista.add(enfrentamiento);
        }
        return lista;
    }
    public String sacarNombrEquipo(int codequipo) throws SQLException {
        ps=conn.prepareStatement("select nombre from equipos where codEquipo=?");
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
}
