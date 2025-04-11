package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VInicioAdmin extends JFrame {
    private VistaController vistaController;
    private Login login;
    
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
    private JButton bLogOut;

    public VInicioAdmin(VistaController vistaController, Login login) {
        this.vistaController = vistaController;
        this.login = login;

        setContentPane(pPrincipal);
        setTitle("Vista Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);

        bLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login(vistaController);
                login.setVisible(true);
                dispose();
            }
        });

        bAdministrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VAdministrarAdmin vAdministrarAdmin = new VAdministrarAdmin(vistaController);
                vAdministrarAdmin.setVisible(true);
                dispose();
            }
        });
    }
}
