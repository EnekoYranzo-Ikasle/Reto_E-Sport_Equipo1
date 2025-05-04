package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class DEditarHora extends JDialog {
    private JPanel contentPane;
    private JButton Modificar;
    private JTextField CodigoField;
    private JTextField textField2;
    private JButton buttonCancel;

    public DEditarHora(VistaController vistaController) {
        setContentPane(contentPane);
        setModal(true);
        setSize(500,500);
        getRootPane().setDefaultButton(Modificar);
        setLocationRelativeTo(null);



        Modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean seguir= false;
                if (!textField2.getText().matches("([01]?\\d|2[0-3]):[0-5]\\d")) {
                    JOptionPane.showMessageDialog(null, "Formato de fecha mal introducido");
                    seguir= false;
                }else{
                    seguir= true;
                }
                if (!CodigoField.getText().matches("[0-9]*")) {
                    JOptionPane.showMessageDialog(null, "El codigo de enfrentamiento no tiene el formato correcto");
                    seguir= false;
                }else {
                    seguir= true;
                }
                try {
                    if (!vistaController.enfrentamientoExiste(Integer.parseInt(CodigoField.getText()))){
                        JOptionPane.showMessageDialog(null, "Ese enfrentamiento no existe");
                        seguir= false;
                    }else{
                        seguir= true;
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (seguir) {
                    try {
                        vistaController.setHora(textField2.getText(),Integer.parseInt(CodigoField.getText()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    dispose();
                }

            }
        });
    }




}
