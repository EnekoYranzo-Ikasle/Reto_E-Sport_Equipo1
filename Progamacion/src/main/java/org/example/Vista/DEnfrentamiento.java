package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalTime;

public class DEnfrentamiento extends JDialog {
    private JPanel pPrincipal;
    private JButton Modificar;
    private JTextField tfCodEnfrentamiento;
    private JTextField tfNuevaHora;
    private JPanel pBotones;
    private JPanel pBody;
    private JButton buttonCancel;

    public DEnfrentamiento(VistaController vistaController) {
        setContentPane(pPrincipal);
        setModal(true);
        setSize(500,500);
        getRootPane().setDefaultButton(Modificar);
        setLocationRelativeTo(null);


        /**
         * Ventana para modificar la hora de un enfrentamiento
         */
        Modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean seguir= false;
                if (!tfNuevaHora.getText().matches("([01]?\\d|2[0-3]):[0-5]\\d")) {
                    JOptionPane.showMessageDialog(null, "Formato de fecha mal introducido");
                    seguir= false;
                }else{
                    seguir= true;
                }

                if (!tfCodEnfrentamiento.getText().matches("[0-9]*")) {
                    JOptionPane.showMessageDialog(null, "El codigo de enfrentamiento no tiene el formato correcto");
                    seguir= false;
                }else {
                    seguir= true;
                }

                try {
                    if (!vistaController.enfrentamientoExiste(Integer.parseInt(tfCodEnfrentamiento.getText()))){
                        JOptionPane.showMessageDialog(null, "Ese enfrentamiento no existe");
                        seguir= false;
                    }else{
                        seguir= true;
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(pPrincipal, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                if (seguir) {
                    try {
                        LocalTime hora = LocalTime.parse(tfNuevaHora.getText());
                        vistaController.setHora(hora,Integer.parseInt(tfCodEnfrentamiento.getText()));

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(pPrincipal, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    dispose();
                }

            }
        });
    }




}
