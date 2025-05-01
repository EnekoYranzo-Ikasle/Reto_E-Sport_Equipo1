package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;

public class DInsertarResultado extends JDialog {
    private JPanel PanelPrincipal;

    public DInsertarResultado(VistaController vistaController, Integer codEnfre) {
        setContentPane(PanelPrincipal);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setVisible(true);


    }
}
