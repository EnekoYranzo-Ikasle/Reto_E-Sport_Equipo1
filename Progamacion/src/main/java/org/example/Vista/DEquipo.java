package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DEquipo extends JDialog {
    private VistaController vistaController;

    private JPanel pPrincipal;
    private JButton buttonOK;
    private JTabbedPane tabbedPane1;
    private JPanel pTextAlta;
    private JPanel pInputsAlta;
    private JTextField tfNombre;
    private JTextField tfFechaFundacion;
    private JButton aceptarButton;
    private JTextField codigo;
    private JButton aceptarButton11;

    public DEquipo(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = tfNombre.getText();
                    LocalDate fechaFundacion = parsearFecha(tfFechaFundacion.getText());

                    vistaController.nuevoEquipo(nombre, fechaFundacion);

                    JOptionPane.showMessageDialog(null, "Nuevo Equipo creado correctamente");

                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
    }

    private LocalDate parsearFecha(String fechaStr) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fechaStr, formato);
    }
}
