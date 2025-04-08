package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Persona;

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
        setResizable(false);

        iniciarSesionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String email = tfEmail.getText();
                    String pass = String.valueOf(pfPasword.getPassword());

                    Persona usuario = vistaController.getPersona(email);

                    if (!usuario.getEmail().equals(email) && !usuario.getPassword().equals(pass)) {
                        throw new Exception("Usuario / Contraseña incorrecta");
                    }

                    switch (usuario.getTipo()) {
                        case "user":{
                            VInicioUser vInicioUser = new VInicioUser(vistaController);
                            vInicioUser.setVisible(true);
                        }break;
                        case "admin": {
                            VInicioAdmin vInicioAdmin = new VInicioAdmin(vistaController);
                            vInicioAdmin.setVisible(true);
                        }break;
                        default: {
                            throw new Exception("Tipo de usuario incorrecto");
                        }
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(pPrincipal, ex.getMessage());
                }
            }
        });

        linkCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkCuenta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SignUp signUp = new SignUp(vistaController);
                signUp.setVisible(true);
            }
        });
    }
}
