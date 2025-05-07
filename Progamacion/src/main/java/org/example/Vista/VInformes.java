package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class VInformes extends JFrame {
    private JPanel contentPane;
    private JButton bVolver;
    private JPanel pBotones;
    private JPanel pBody;
    private JPanel pHeader;
    private JButton jugadoresButton;
    private JButton equiposButton;

    private VistaController vistaController;
    private JFrame ventanaAnterior;
    /**
     * Constructor de la ventana de informes que es la ventanta que ve un usuario no administrador
     *
     * @param vistaController controlador de la vista
     * @param ventanaAnterior  ventana anterior
     * @throws SQLException si hay un error en la base de datos
     */

    public VInformes(VistaController vistaController, JFrame ventanaAnterior) throws SQLException {
        this.vistaController = vistaController;
        this.ventanaAnterior = ventanaAnterior;

        setContentPane(contentPane);
        setTitle("Informes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        jugadoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DInformeJugadores dInformeJugadores = new DInformeJugadores(vistaController);
                dInformeJugadores.setVisible(true);
            }
        });

        equiposButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DInformeEquipos dInformeEquipos = new DInformeEquipos(vistaController);
                dInformeEquipos.setVisible(true);
            }
        });

        bVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ventanaAnterior.setVisible(true); // Muestra la ventana padre
            }
        });
    }
}
