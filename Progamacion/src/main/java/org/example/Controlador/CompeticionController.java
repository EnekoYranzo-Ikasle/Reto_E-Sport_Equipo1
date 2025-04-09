package org.example.Controlador;

import org.example.Excepcion.DatoNoValido;
import org.example.Modelo.Competicion;
import org.example.Modelo.CompeticionDAO;
import org.example.Modelo.Jornada;
import org.example.Modelo.JornadaDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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