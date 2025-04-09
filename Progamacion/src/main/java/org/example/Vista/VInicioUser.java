package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VInicioUser extends JFrame {
    private VistaController vistaController;
    private Login login;

    private JPanel pPrincipal;
    private JPanel pBody;
    private JPanel pBotones;
    private JButton bAdministrar;
    private JButton bVerInforme;
    private JPanel pHeader;
    private JButton bLogOut;

    public VInicioUser(VistaController vistaController) throws HeadlessException {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setTitle("Vista Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        bLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login = new Login(vistaController);
                login.setVisible(true);
                dispose();
            }
        });
    }
}
