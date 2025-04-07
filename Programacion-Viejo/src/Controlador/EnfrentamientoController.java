package Controlador;

import Modelo.Enfrentamiento;
import Modelo.EnfrentamientoDAO;

import javax.swing.*;
import java.util.List;

public class EnfrentamientoController {
    private final EnfrentamientoDAO enfrentamientoDAO;

    public EnfrentamientoController(EnfrentamientoDAO enfrentamientoDAO) {
        this.enfrentamientoDAO = enfrentamientoDAO;
    }

    public void modificar() {
        
    }

    public void agregarResultados() {
        List<Enfrentamiento> lista = enfrentamientoDAO.getListaEnfrentamientos();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay enfrentamientos disponibles.");
            return;
        }

        // Convertir enfrentamientos en un array.
        String[] opciones = lista.stream().map(Enfrentamiento::toString).toArray(String[]::new);

        String seleccion = (String) JOptionPane.showInputDialog(null, "Seleccione un enfrentamiento:",
                "Seleccionar Enfrentamiento", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        String resultado = JOptionPane.showInputDialog("Ingrese el resultado del enfrentamiento:");

        enfrentamientoDAO.agregarResultados(seleccion, lista, resultado);
    }
}
