package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DInsertarResultado extends JDialog {
    private JPanel pPrincipal;
    private JButton Equipo1;
    private JButton Equipo2;

    public DInsertarResultado(VistaController vistaController, String nombreEquip1, String nombreEquip2, int codEnfrentamiento, int codEquip1, int codEquip2) {

        setContentPane(pPrincipal);
        setModal(true);
        setTitle("Insertar Resultados");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        Equipo1.setText(nombreEquip1);
        Equipo2.setText(nombreEquip2);

        Equipo1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vistaController.setGanador(codEquip1, codEnfrentamiento);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        Equipo2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vistaController.setGanador(codEquip2, codEnfrentamiento);
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
}
