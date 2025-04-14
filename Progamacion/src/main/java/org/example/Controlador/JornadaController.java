package org.example.Controlador;

import org.example.Modelo.*;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class JornadaController {
    private final JornadaDAO jornadaDAO;
    private final EquipoDAO equipoDAO;
    private final EnfrentamientoDAO enfrentamientoDAO;

    public JornadaController(JornadaDAO jornadaDAO, EquipoDAO equipoDAO, EnfrentamientoDAO enfrentamientoDAO) {
        this.jornadaDAO = jornadaDAO;
        this.equipoDAO = equipoDAO;
        this.enfrentamientoDAO = enfrentamientoDAO;
    }

    public void mostrarJornadas() {
        jornadaDAO.mostrarJornadas();
    }

    public List<Integer> obtenercodjornada() throws SQLException {
        return jornadaDAO.obtenercodjornada();
    }

    public void generarCalendario(int numJornadas, List<Equipo> listaEquipos) throws Exception {
        jornadaDAO.generarJornadas(numJornadas, listaEquipos, enfrentamientoDAO);
    }
}
