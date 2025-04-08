package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SignUp extends JDialog {
    private VistaController vistaController;
    private Login login;

    private JPanel pPrincipal;
    private JButton bCrear;
    private JPanel bBotones;
    private JPanel pBody;
    private JPanel pHeader;
    private JPanel pText;
    private JPanel pInputs;
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JLabel linkCuenta;

    public SignUp(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setModal(true);
        setTitle("Consultoria E-Sports");
        setSize(500, 300);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(bCrear);


        linkCuenta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
    }
}
