package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;

public class vJornada extends JFrame {
    private JPanel pJornada;
    private JTextField tfNumJornadas;
    private JButton bGenerarJornadas;
    private VistaController vistaController;
    private int tfNumJornadass;

    public vJornada(VistaController vistaController) {
        this.vistaController = vistaController;
        setTitle("Jornada");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        bGenerarJornadas.setEnabled(false);

        tfNumJornadas.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                tfNumJornadass = Integer.parseInt(tfNumJornadas.getText());
            }
        });

    }
}
