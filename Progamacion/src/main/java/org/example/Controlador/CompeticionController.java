package org.example.Controlador;

import org.example.Modelo.Competicion;
import org.example.Modelo.CompeticionDAO;
import java.sql.SQLException;

public class CompeticionController {
    private final CompeticionDAO competicionDAO;
    private Competicion competicion;

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

    public String getNombreCompeticion() throws SQLException {
        return competicion.getNombre();
    }
    public int getCodigoCompeticion() throws SQLException {
        return competicion.getCodCompe();
    }
    public void cargarCompeticionActiva() throws SQLException {
        Competicion competicionActiva = new Competicion();
        competicionActiva=competicionDAO.obtenerCompeticionActiva();
        if(competicionActiva==null){
            throw new SQLException();
        }
    }


}