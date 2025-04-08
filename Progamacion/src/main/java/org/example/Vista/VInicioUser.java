package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.*;

public class VInicioUser extends JFrame {
    private VistaController vistaController;

    private JPanel pPrincipal;
    private JPanel pHeader;
    private JPanel pBody;
    private JPanel pBotones;
    private JButton bAdministrar;
    private JButton bVerInforme;

    public VInicioUser(VistaController vistaController) throws HeadlessException {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setTitle("Vista Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
    }
}
