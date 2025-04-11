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
    private VistaController vistaController;

    public DVisualizarResultados(VistaController vistaController) throws SQLException {
        this.vistaController = vistaController;

        setContentPane(contentPane);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        List<Integer> codJornadas = vistaController.obtenerCodJornada();
        for (int i = 0; i < codJornadas.size(); i++) {
            taMostrar.append("Jornada "+codJornadas.get(i) + "\n");
            taMostrar.append("Ganadores");
            List<Integer> codGanadores=vistaController.getGanador(codJornadas.get(i));
            for (int j = 0; j < codGanadores.size(); j++) {
                taMostrar.append("Enfrentamiento "+ j + "\n");
                taMostrar.append(vistaController.getGanadorEquipo(codGanadores.get(j)).getNombreEquipo() + "\n");
            }
        }

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
