package org.example.Controlador;

import org.example.Vista.Login;

public class VistaController {
    private ModeloController modeloController;

    public VistaController(ModeloController modeloController) {
        this.modeloController = modeloController;

        Login login = new Login(this);
        login.setVisible(true);
    }
}
