package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

                    vistaController.crearCuenta(email, pass);

                    JOptionPane.showMessageDialog(pPrincipal, "Cuenta creada correctamente");
                    dispose();

                }catch (Exception ex){
                    JOptionPane.showMessageDialog(pPrincipal, ex.getMessage());
                }
            }
        });
    }
}
