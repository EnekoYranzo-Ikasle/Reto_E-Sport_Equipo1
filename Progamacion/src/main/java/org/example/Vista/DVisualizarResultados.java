package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class DVisualizarResultados extends JDialog {
    private JPanel contentPane;
    private JButton volverButton;
    private JTextArea taMostrar;
    private JPanel pHeader;
    private JButton buttonOK;
    private VistaController vistaController;

    public DVisualizarResultados(VistaController vistaController) throws SQLException {
        this.vistaController = vistaController;
        setContentPane(contentPane);
        setModal(true);
        setSize(500, 500);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);


        List<Integer> codJornadas = vistaController.obtenerCodJornada();
        for (int i = 0; i < codJornadas.size(); i++) {
            taMostrar.append("Jornada "+codJornadas.get(i) + "\n");
            taMostrar.append("Ganadores");
            List<Integer> codGanadores=vistaController.getganador(codJornadas.get(i));
            for (int j = 0; j < codGanadores.size(); j++) {
                taMostrar.append("Enfrentamiento "+ j + "\n");
                taMostrar.append(vistaController.getGanador(codGanadores.get(j)).getNombreEquipo() + "\n");
            }

        }
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    getRootPane().setDefaultButton(buttonOK);
                    dispose();

            }
        });
    }
}
