package org.example.Modelo;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class JornadaDAO {
    private final Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;
    private final List<Jornada> listaJornadas;

    public JornadaDAO(Connection conn) {
        this.conn = conn;
        this.listaJornadas = new ArrayList<>();
    }

    public void eliminarJornadaPorCod(int codJornada) {
        Optional<Jornada> jornadaAEliminar = listaJornadas.stream()
                .filter(j -> j.getCodJornada() == codJornada)
                .findFirst();

        jornadaAEliminar.ifPresent(listaJornadas::remove);
    }

    public void modificarJornadaPorCod(Jornada jornada) {
        int codJornada = jornada.getCodJornada();
        LocalDate fechaJornada = jornada.getFechaJornada();

        Optional<Jornada> jornadaAModificar = listaJornadas.stream()
                .filter(j -> j.getCodJornada() == codJornada)
                .findFirst();

        if (jornadaAModificar.isPresent()) {
            jornadaAModificar.get().setFechaJornada(fechaJornada);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró la jornada con código: " +
                    codJornada, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Jornada buscarJornadaPorCod(int codJornada) {
        
        for (Jornada j : listaJornadas) {
            if (j.getCodJornada() == codJornada) {
                return j;
            }
        }
        return null;
    }

    public List<Jornada> getJornadasPorEquipo(Equipo equipo) {
        return listaJornadas.stream()
                .filter(jornada -> jornada.contieneEquipo(equipo))
                .collect(Collectors.toList());
    }

    public void mostrarJornadas() {
        StringBuilder mensajeFinal = new StringBuilder("JORNADAS\n");
        for (Jornada j : listaJornadas) {
            mensajeFinal.append(j.mostrarJornada()).append("\n");
        }

        JOptionPane.showMessageDialog(null, mensajeFinal.toString(), "Jornadas", JOptionPane.INFORMATION_MESSAGE);
    }
    public List<String> obtenercodjornada() throws SQLException {
        ps = conn.prepareStatement("select codJornada from jornadas");
        rs = ps.executeQuery();
        List<String> codjornada = new ArrayList<>();
        while(rs.next()) {
            codjornada.add(rs.getString("codJornada"));
        }
        return codjornada;
    }

    public StringBuilder mostrarJornadasPorEquipo(Equipo equipo) {
        StringBuilder mensaje = new StringBuilder();

        if (listaJornadas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El equipo " + equipo.getNombreEquipo() +
                            " no tiene jornadas registradas.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            mensaje.append(equipo.getNombreEquipo()).append(" participa en las siguientes jornadas:\n");
            for (Jornada jornada : listaJornadas) {
                mensaje.append("- Jornada: ").append(jornada.getCodJornada()).append("\n");
            }
        }
        return mensaje;
    }
}
