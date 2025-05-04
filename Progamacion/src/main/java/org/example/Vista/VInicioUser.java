package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class VInicioUser extends JFrame {
    private VistaController vistaController;
    private VLogin VLogin;

    private JPanel pPrincipal;
    private JPanel pBody;
    private JPanel pBotones;
    private JButton bMostrarEquipos;
    private JButton bVerInforme;
    private JPanel pHeader;
    private JButton bLogOut;

    public VInicioUser(VistaController vistaController, VLogin VLogin) throws HeadlessException {
        this.vistaController = vistaController;
        this.VLogin = VLogin;

        setContentPane(pPrincipal);
        setTitle("Vista Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 300);
        setLocationRelativeTo(null);

        bLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VLogin VLogin = new VLogin(vistaController);
                VLogin.setVisible(true);
                dispose();
            }
        });

        bMostrarEquipos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DVisualizarEquipos dVisualizarEquipos = null;
                try {
                    dVisualizarEquipos = new DVisualizarEquipos(vistaController);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dVisualizarEquipos.setVisible(true);

            }
        });

        bVerInforme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               try {
                   VInformes VInformes = new VInformes(vistaController, VInicioUser.this);
                   VInformes.setVisible(true);
                   dispose();

               }catch (SQLException ex) {
                   throw new RuntimeException(ex);
               }
            }
        });

    }
}
