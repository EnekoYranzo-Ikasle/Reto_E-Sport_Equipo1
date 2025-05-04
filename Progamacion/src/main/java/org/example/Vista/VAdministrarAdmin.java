package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VAdministrarAdmin extends JFrame {
    private final VistaController vistaController;
    private VLogin VLogin;

    private JPanel pPrincipal;
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 300);
        setLocationRelativeTo(null);

        // Bloquea los crud de jugadores y equipos
        if (vistaController.isCrudJugEquipBloqueado()) {
            bJugador.setEnabled(false);
            bEquipos.setEnabled(false);
        }

        // Bloquea los crud de enfrentamientos y de jornadas
        if (vistaController.isCrudEnfreJorBloqueado()) {
            bEnfrentamiento.setEnabled(false);
            bJornada.setEnabled(false);
        }

        // Activa los crud de enfrentamientos y de jornadas
        if (!vistaController.isCrudEnfreJorBloqueado()) {
            bEnfrentamiento.setEnabled(true);
            bJornada.setEnabled(true);
        }

        bVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VInicioAdmin vInicioAdmin = new VInicioAdmin(vistaController, VLogin);
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
                DJornada dJornada = new DJornada(vistaController);
                dJornada.setVisible(true);
            }
        });

        bCompeticion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DCompeticion dCompeticion = new DCompeticion(vistaController);
                dCompeticion.setVisible(true);
            }
        });
        bEnfrentamiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DEditarHora dEditarHora = new DEditarHora(vistaController);
                dEditarHora.setVisible(true);


            }
        });
    }
}
