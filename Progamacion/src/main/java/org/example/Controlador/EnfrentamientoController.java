package org.example.Controlador;

import org.example.Modelo.Enfrentamiento;
import org.example.Modelo.EnfrentamientoDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class EnfrentamientoController {
    private final EnfrentamientoDAO enfrentamientoDAO;

    public EnfrentamientoController(EnfrentamientoDAO enfrentamientoDAO) {
        this.enfrentamientoDAO = enfrentamientoDAO;
    }

    public void modificar() {
        
    }


    public List<Integer> getganador(int codigoJorn) throws SQLException {
        return enfrentamientoDAO.obtenerGanadores(codigoJorn);
    }
}
