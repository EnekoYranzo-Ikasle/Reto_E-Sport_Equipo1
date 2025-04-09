package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.*;

public class VInicioAdmin extends JFrame {
    private VistaController vistaController;

    private JPanel pPrincipal;
    private JPanel pHeader;
    private JPanel pBody;
    private JButton bAdministrar;
    private JButton bCerrarEtapa;
    private JButton bVerInforme;
    private JButton bGenerarCalendario;
    private JButton bIntroducirResultados;
    private JPanel pArriba;
    private JPanel pAbajo;

    public VInicioAdmin(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setTitle("Vista Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
    }
}
