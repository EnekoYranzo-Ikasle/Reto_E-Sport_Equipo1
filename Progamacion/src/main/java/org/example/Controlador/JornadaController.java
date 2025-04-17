package org.example.Controlador;

import org.example.Modelo.*;

import java.sql.SQLException;
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

    public List<Integer> obtenerCodJornada() throws SQLException {
        return jornadaDAO.obtenerCodJornada();
    }

    public void generarCalendario(int numJornadas, List<Equipo> listaEquipos) throws Exception {
        jornadaDAO.generarJornadas(numJornadas, listaEquipos);
    }
}
