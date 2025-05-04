package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DSignUp extends JDialog {
    private VistaController vistaController;
    private VLogin VLogin;

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

    public DSignUp(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setModal(true);
        setTitle("Consultoria E-Sports");
        setSize(500, 300);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(bCrear);
        setResizable(false);

        linkCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        linkCuenta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });

        bCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String email = tfEmail.getText();
                    String pass = String.valueOf(pfPassword.getPassword());

                    boolean emailValido = validarFormatoEmail(email);

                    if (!emailValido) {
                        throw new Exception("Formato de email incorrecto");
                    }

                    vistaController.crearCuenta(email, pass);

                    JOptionPane.showMessageDialog(pPrincipal, "Cuenta creada correctamente");
                    dispose();

                }catch (Exception ex){
                    JOptionPane.showMessageDialog(pPrincipal, ex.getMessage());
                }
            }
        });
    }
    private boolean
    validarFormatoEmail(String email) {
        Pattern patron = Pattern.compile("^[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = patron.matcher(email);
        return matcher.matches();
    }
}
