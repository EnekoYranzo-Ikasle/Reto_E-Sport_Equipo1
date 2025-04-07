package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame {
    private VistaController vistaController;

    private JPanel pPrincipal;
    private JPanel pHeader;
    private JLabel lLogo;
    private JPanel pBody;
    private JPanel pTexto;
    private JPanel pInputs;
    private JTextField tfEmail;
    private JLabel accountIcon;
    private JLabel lockIcon;
    private JPanel pBotones;
    private JButton iniciarSesionButton;
    private JLabel linkCuenta;
    private JPasswordField pfPasword;

    public Login(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setTitle("Consultoria E-Sports");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);

        iniciarSesionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        linkCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        linkCuenta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Esto es una prueba");
            }
        });
    }
}
