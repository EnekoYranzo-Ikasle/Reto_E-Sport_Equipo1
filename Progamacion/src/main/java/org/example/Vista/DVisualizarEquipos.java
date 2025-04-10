package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;

public class DVisualizarEquipos extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private VistaController vistaController;
    public DVisualizarEquipos(VistaController vistaController) {
        this.vistaController = vistaController;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }
}
