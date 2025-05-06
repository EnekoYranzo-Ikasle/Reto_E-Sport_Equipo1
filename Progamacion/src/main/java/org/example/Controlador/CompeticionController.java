package org.example.Controlador;

import org.example.Modelo.Competicion;
import org.example.Modelo.CompeticionDAO;
import java.sql.SQLException;
import java.util.List;

public class CompeticionController {
    private final CompeticionDAO competicionDAO;

    public CompeticionController(CompeticionDAO competicionDAO) {
        this.competicionDAO = competicionDAO;
    }

    public void nuevaCompeticion(Competicion competicion) throws SQLException {
        competicionDAO.nuevaCompeticion(competicion);
    }

    public List<Competicion> getCompeticiones() throws SQLException {
        return competicionDAO.getCompeticiones();
    }
}