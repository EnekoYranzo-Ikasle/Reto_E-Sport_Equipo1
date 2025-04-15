package org.example.Controlador;

import org.example.Modelo.Competicion;
import org.example.Modelo.CompeticionDAO;
import java.sql.SQLException;

public class CompeticionController {
    private final CompeticionDAO competicionDAO;

    public CompeticionController(CompeticionDAO competicionDAO) {
        this.competicionDAO = competicionDAO;
    }
}