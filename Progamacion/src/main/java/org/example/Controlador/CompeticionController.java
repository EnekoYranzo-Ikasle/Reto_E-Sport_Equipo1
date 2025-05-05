package org.example.Controlador;

import org.example.Modelo.Competicion;
import org.example.Modelo.CompeticionDAO;
import java.sql.SQLException;
import java.time.LocalDate;

public class CompeticionController {
    private final CompeticionDAO competicionDAO;

    public CompeticionController(CompeticionDAO competicionDAO) {
        this.competicionDAO = competicionDAO;
    }

    public void nuevaCompeticion(Competicion competicion) throws SQLException {
        competicionDAO.nuevaCompeticion(competicion);
    }
    public void actualizarCompeticion(LocalDate fechaIni, LocalDate fechaFin, String nombre) throws SQLException {
        competicionDAO.editarCompeticion(fechaIni, fechaFin, nombre);
    }
}