package org.example.Controlador;

import org.example.Vista.Login;

import java.sql.SQLException;

public class VistaController {
    private ModeloController modeloController;

    public VistaController(ModeloController modeloController) {
        this.modeloController = modeloController;

        Login login = new Login(this);
        login.setVisible(true);
    }

    public boolean iniciarSesion(String email, String pass) throws SQLException {
        return modeloController.iniciarSesion(email, pass);
    }

    public void crearCuenta(String email, String pass) throws SQLException {
        modeloController.crearCuenta(email, pass);
    }
}
