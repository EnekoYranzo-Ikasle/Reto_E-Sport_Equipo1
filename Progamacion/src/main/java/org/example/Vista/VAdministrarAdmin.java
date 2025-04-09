package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VAdministrarAdmin extends JFrame {
    private VistaController vistaController;

    private JPanel pPrincipal;
    private JButton buttonOK;
    private JPanel pHeader;
    private JButton bVolver;
    private JPanel pBody;
    private JPanel pArriba;
    private JButton bAdministrar;
    private JButton bGenerarCalendario;
    private JButton bCerrarEtapa;
    private JButton bIntroducirResultados;

    public VAdministrarAdmin(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setTitle("Vista Admin - Administrar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 300);
        setLocationRelativeTo(null);

        bVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VInicioAdmin vInicioAdmin = new VInicioAdmin(vistaController, null);
                vInicioAdmin.setVisible(true);
                dispose();
            }
        });
    }
}
