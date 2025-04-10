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
                DVisualizarEquipos dVisualizarEquipos = new DVisualizarEquipos(vistaController);
                dVisualizarEquipos.setVisible(true);
                try {
                    List<Equipo> equipos = vistaController.mostrar();
                    StringBuilder equiposText = new StringBuilder();
                    for ( int i = 0; i < equipos.size(); i++ ) {

                        equiposText.append(equipos.get(i));
                        equiposText.append("\n");
                        List < Jugador> jugadors = vistaController.mostrarJugadores(equipos.get(i).getCodEquipo());
                        for (int j = i + 1; j < equipos.size(); j++ ) {
                            equiposText.append(jugadors.get(j).getNombre());
                            equiposText.append(" Alias: ");
                            equiposText.append(jugadors.get(j).getNickname());
                            equiposText.append("\n");
                        }
                        equiposText.append("\n \n \n");
                        equiposText.append("---------------------------------");

                    }


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
