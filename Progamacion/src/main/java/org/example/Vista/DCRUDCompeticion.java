package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DCRUDCompeticion extends JDialog{
    private JRadioButton eliminarCompeticiónRadioButton;
    private JRadioButton modificarCompeticiónRadioButton;
    private JTextField tfNombreCompeEliminar;
    private JButton bAceptarEliminar;
    private JButton bCancelarEliminar;
    private JTextField tfNuevaFechaInicio;
    private JTextField tfNuevaFechaFin;
    private JTextField tfNuevoEstado;
    private JTextField tfNombreNuevo;
    private JPanel pModificarCompe;
    private JPanel pEliminarCompe;
    private JButton bAceptarModi;
    private JButton bCancelarModi;
    private VistaController vistaController;

    public DCRUDCompeticion(VistaController vistaController) {
        this.vistaController = vistaController;

        eliminarCompeticiónRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pModificarCompe.setVisible(false);
                pModificarCompe.removeAll();
                pEliminarCompe.setVisible(true);
                bAceptarEliminar.setEnabled(false);
            }
        });
        tfNombreCompeEliminar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(!tfNombreCompeEliminar.getText().isEmpty()) {
                    bAceptarEliminar.setEnabled(true);
                }
            }
        });
        bAceptarEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vistaController.eliminarCompeticion(tfNombreCompeEliminar.getText());
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        bCancelarEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        modificarCompeticiónRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pEliminarCompe.setVisible(false);
                pEliminarCompe.removeAll();
                pModificarCompe.setVisible(true);
            }
        });
        tfNombreNuevo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(!tfNombreNuevo.getText().isEmpty()) {
                    bAceptarModi.setEnabled(true);
                }
            }
        });
        tfNuevaFechaInicio.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(!tfNuevaFechaInicio.getText().isEmpty()) {
                    bAceptarModi.setEnabled(true);
                }
            }
        });
        tfNuevaFechaFin.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(!tfNuevaFechaFin.getText().isEmpty()) {
                    bAceptarModi.setEnabled(true);
                }
            }
        });
        tfNuevoEstado.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(!tfNuevoEstado.getText().isEmpty()) {
                    bAceptarModi.setEnabled(true);
                }
            }
        });
        bAceptarModi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vistaController.modificarCompeticion(tfNombreNuevo.getText(),parsearFecha(tfNuevaFechaInicio.getText()),parsearFecha(tfNuevaFechaFin.getText()),tfNuevoEstado.getText());
                    JOptionPane.showMessageDialog(null,"Caompeticion modificada correctamente");
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        bCancelarModi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    private LocalDate parsearFecha(String fecha){
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fecha, formatter);
    }
}
