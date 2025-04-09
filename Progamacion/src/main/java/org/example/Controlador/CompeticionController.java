package org.example.Controlador;

import org.example.Modelo.Competicion;
import org.example.Modelo.CompeticionDAO;
import java.sql.SQLException;

public class CompeticionController {
    private final CompeticionDAO competicionDAO;

    public CompeticionController(CompeticionDAO competicionDAO) {
        this.competicionDAO = competicionDAO;
    }

    public void agregarCompetcion(Competicion competicion) throws SQLException {
        competicionDAO.agregarCompeticion(competicion);
    }

    public void modificarCompeticion(Competicion competicion) throws SQLException {
        competicionDAO.modificarCompeticion(competicion);
    }

    public void eliminarCompeticion(Competicion competicion) throws SQLException {
        competicionDAO.eliminarCompeticion(competicion);
    }

    public void generarCalendario() throws Exception {
        competicionDAO.generarCalendario();
    }
}