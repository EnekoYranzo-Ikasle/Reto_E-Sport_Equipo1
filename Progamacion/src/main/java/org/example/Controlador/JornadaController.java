package org.example.Controlador;

import org.example.Modelo.*;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JornadaController {
    private final JornadaDAO jornadaDAO;
    private final EquipoDAO equipoDAO;
    private final EnfrentamientoDAO enfrentamientoDAO;

    public JornadaController(JornadaDAO jornadaDAO, EquipoDAO equipoDAO, EnfrentamientoDAO enfrentamientoDAO) {
        this.jornadaDAO = jornadaDAO;
        this.equipoDAO = equipoDAO;
        this.enfrentamientoDAO = enfrentamientoDAO;
    }

    public void generarJornada(int codCompeticion, int numJornadas) throws SQLException {
        try {
            jornadaDAO.generarJornadas(numJornadas, equipoDAO.obtenerEquipos(), enfrentamientoDAO, codCompeticion);
            JOptionPane.showMessageDialog(null, "Jornadas generadas correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar jornadas: " + e.getMessage());
        }
    }


    public void borrarJornada(Jornada jornada) throws SQLException {
        try {
            jornadaDAO.eliminarJornadaPorCodJornada(jornada);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }

    public void modificarJornada(Jornada jornada) throws SQLException {
        try {
            jornadaDAO.modificarJornada(jornada);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }

    public void buscarJornadaCodigo(int codJornada) throws SQLException {
        jornadaDAO.buscarJornadaPorCodigo(codJornada);
    }

    public void mostrarJornadas() throws SQLException {
        jornadaDAO.mostrarJornadas();
    }
}
