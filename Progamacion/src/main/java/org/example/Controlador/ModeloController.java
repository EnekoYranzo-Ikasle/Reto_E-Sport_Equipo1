package org.example.Controlador;

import org.example.Modelo.Competicion;
import org.example.Modelo.Persona;

import java.sql.SQLException;

public class ModeloController {
    private EquipoController equipoController;
    private JugadorController jugadorController;
    private EnfrentamientoController enfrentamientoController;
    private JornadaController jornadaController;
    private CompeticionController competicionController;
    private PersonaController personaController;
    private VistaController vistaController;

    public ModeloController(EquipoController equipoController, JugadorController jugadorController,
                            EnfrentamientoController enfrentamientoController, JornadaController jornadaController,
                            CompeticionController competicionController, PersonaController personaController) {
        this.equipoController = equipoController;
        this.jugadorController = jugadorController;
        this.enfrentamientoController = enfrentamientoController;
        this.jornadaController = jornadaController;
        this.competicionController = competicionController;
        this.personaController = personaController;
    }

    public ModeloController(VistaController vistaController) {
        this.vistaController = vistaController;
    }

    public boolean iniciarSesion(String email, String pass) throws SQLException {
        Persona persona = getPersona(email);

        if (!persona.getEmail().equals(email) && !persona.getPassword().equals(pass)) {
            return false;
        }else
            return true;
    }

    public void crearCuenta(String email, String pass) throws SQLException {
        personaController.crearCuenta(email, pass);
    }

    public Persona getPersona(String email) throws SQLException {
        return personaController.getPersona(email);
    }
}
