package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Persona;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

                    boolean emailValido = validarFormatoEmail(email);

                    if (!emailValido) {
                        throw new Exception("Formato de email incorrecto");
                    }

                    String pass = String.valueOf(pfPasword.getPassword());

                    vistaController.logIn(email, pass);

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

    private boolean validarFormatoEmail(String email) {
        Pattern patron = Pattern.compile("^[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = patron.matcher(email);
        return matcher.matches();
    }
}
