package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Roles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class DJugador extends JDialog {
    private VistaController vistaController;

    private JPanel pPrincipal;
    private JTabbedPane tabbedPane1;
    private JPanel pTextAlta;
    private JPanel pInputsAlta;
    private JTextField tfNombre;
    private JTextField tfNacimiento;
    private JTextField tfNickname;
    private JTextField tfSueldo;
    private JButton aceptarButton;
    private JTextField codigo;
    private JButton aceptarButton1;
    private JTextField tfApellido;
    private JTextField tfNacionalidad;
    private JComboBox cbRol;
    private JTextField tfNombreEquipo;

    public DJugador(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        cbRol.setModel(new DefaultComboBoxModel(Roles.values()));
        
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = tfNombre.getText();
                String apellido = tfApellido.getText();
                String nacionalidad = tfNacionalidad.getText();
                LocalDate fechaNacimiento = parsearFecha(tfNacimiento.getText());
                String nickname = tfNickname.getText();
                double sueldo = Double.parseDouble(tfSueldo.getText());
                String rol = cbRol.getSelectedItem().toString();

                vistaController.altaJugador(nombre, apellido, nacionalidad, fechaNacimiento, nickname, sueldo, rol);
            }
        });
    }





    private LocalDate parsearFecha(String fechaStr) {
        return LocalDate.parse(fechaStr);

    }
}
