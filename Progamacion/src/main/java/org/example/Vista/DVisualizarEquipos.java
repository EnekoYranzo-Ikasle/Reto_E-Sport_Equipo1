package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;

public class DVisualizarEquipos extends JDialog {
    private JPanel pPrincipal;
    private JPanel pHeader;
    private JPanel pBody;
    private JTextArea taMostrar;
    private JButton bVolver;
    private JButton buttonOK;
    private VistaController vistaController;
    public DVisualizarEquipos(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


    }
}
