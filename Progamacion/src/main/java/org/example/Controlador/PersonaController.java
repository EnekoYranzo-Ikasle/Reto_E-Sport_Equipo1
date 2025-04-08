package org.example.Controlador;

import org.example.Modelo.Persona;
import org.example.Modelo.PersonaDAO;

import java.sql.SQLException;

public class PersonaController {
    private final PersonaDAO personaDAO;

    public PersonaController(PersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }

    public Persona getPersona(String email) throws SQLException {
        return personaDAO.getPersona(email);
    }

    public void crearCuenta(String email, String pass) throws SQLException {
        personaDAO.crearCuenta(email, pass);
    }
}
