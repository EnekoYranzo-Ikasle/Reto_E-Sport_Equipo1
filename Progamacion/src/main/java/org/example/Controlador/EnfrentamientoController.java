package org.example.Controlador;

import org.example.Modelo.Enfrentamiento;
import org.example.Modelo.EnfrentamientoDAO;
import org.example.Modelo.Equipo;

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
    public List<Enfrentamiento> getEnfrentamientos() throws SQLException {
        return enfrentamientoDAO.obtenerEnfrentamientos();

    }
    public void setGanador(int codgGanador, int CodEnfrentamiento) throws SQLException {
        enfrentamientoDAO.setGanador(codgGanador, CodEnfrentamiento);
    }
    public boolean enfrentamientoExiste(int codEnfrentamiento) throws SQLException {
        return enfrentamientoDAO.enfrentamientoExiste(codEnfrentamiento);
    }
    public void setHora(String hora, int codEnfrentamiento) throws SQLException {
        enfrentamientoDAO.setHora(hora, codEnfrentamiento);
    }
}
