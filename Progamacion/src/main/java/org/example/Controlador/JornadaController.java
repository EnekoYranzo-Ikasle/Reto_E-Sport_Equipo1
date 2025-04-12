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

    public void generarJornada() {
        try {
            int numJornadas = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántas jornadas deseas generar?"));

//            jornadaDAO.generarJornadas(numJornadas, equipoDAO.obtenerEquipos(), enfrentamientoDAO);

        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void borrarJornada() {
        try {
            String codJornada = JOptionPane.showInputDialog(null, "Escribe el código de jornada que deseas borrar");
            int codigo = Integer.parseInt(codJornada);
            jornadaDAO.eliminarJornadaPorCod(codigo);
            
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }

    public void modificarJornada() {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            String codJornada = JOptionPane.showInputDialog(null, "Escribe el código de jornada que deseas modificar");
            int codigo = Integer.parseInt(codJornada);
            LocalDate fechaModificada = LocalDate.parse(JOptionPane.showInputDialog(null, "Introduce la nueva fecha de la jornada (dd/MM/yyyy)"), formatoFecha);

            Jornada j = new Jornada(codigo, fechaModificada);

            jornadaDAO.modificarJornadaPorCod(j);

        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }

    public void buscarJornadasPorEquipo() throws SQLException {
        StringBuilder mensaje;

        String nombreEquipo = JOptionPane.showInputDialog("Ingrese el nombre del equipo para buscar sus jornadas:");
        Equipo equipoBuscado = equipoDAO.buscarEquipoPorNombre(nombreEquipo);

        if (equipoBuscado != null) {
            mensaje = jornadaDAO.mostrarJornadasPorEquipo(equipoBuscado);
            JOptionPane.showMessageDialog(null, mensaje.toString(), "Jornadas del Equipo", JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(null, "Equipo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarJornadas() {
        jornadaDAO.mostrarJornadas();
    }
    public List<Integer> obtenercodjornada() throws SQLException {
        return jornadaDAO.obtenercodjornada();
    }
}
