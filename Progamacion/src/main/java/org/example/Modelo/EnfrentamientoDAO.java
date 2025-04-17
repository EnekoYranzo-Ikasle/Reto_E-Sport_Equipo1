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
}
