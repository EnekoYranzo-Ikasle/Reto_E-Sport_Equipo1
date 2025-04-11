package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VAdministrarAdmin extends JFrame {
    private VistaController vistaController;
    private Login login;

    private JPanel pPrincipal;
    private JButton buttonOK;
    private JPanel pHeader;
    private JButton bVolver;
    private JPanel pBody;
    private JPanel pArriba;
    private JButton bJugador;
    private JButton bEquipos;
    private JButton bEnfrentamiento;
    private JButton bJornada;
    private JButton bCompeticion;

    public VAdministrarAdmin(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setTitle("Vista Admin - Administrar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 300);
        setLocationRelativeTo(null);

        bVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VInicioAdmin vInicioAdmin = new VInicioAdmin(vistaController, login);
                vInicioAdmin.setVisible(true);
                dispose();
            }
        });

        bJugador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DJugador dJugador = new DJugador(vistaController);
                dJugador.setVisible(true);
            }
        });

        bEquipos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DEquipo dEquipo = new DEquipo(vistaController);
                dEquipo.setVisible(true);
            }
        });

        bEnfrentamiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        bJornada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        bCompeticion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
