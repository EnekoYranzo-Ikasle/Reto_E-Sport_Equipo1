package org.example.Controlador;

import org.example.Modelo.Persona;
import org.example.Vista.Login;

import java.sql.SQLException;

public class VistaController {
    private ModeloController modeloController;

    public VistaController(ModeloController modeloController) {
        this.modeloController = modeloController;

        Login login = new Login(this);
        login.setVisible(true);
    }

    public Persona getPersona(String email) throws SQLException {
        return modeloController.getPersona(email);
    }

    public void crearCuenta(String email, String pass) throws SQLException {
        modeloController.crearCuenta(email, pass);
    }
    public void generarCalendario() throws Exception {
        modeloController.generarCalendario();
    }
}
