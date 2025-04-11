package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Equipo;
import org.example.Modelo.Jugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class VInicioUser extends JFrame {
    private VistaController vistaController;
    private Login login;

    private JPanel pPrincipal;
    private JPanel pBody;
    private JPanel pBotones;
    private JButton bMostrarEquipos;
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
    }
}
