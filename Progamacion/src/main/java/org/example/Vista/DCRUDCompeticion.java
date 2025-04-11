package org.example.Vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class DCRUDCompeticion {
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

    public DCRUDCompeticion() {
        eliminarCompeticiónRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pModificarCompe.setVisible(false);
                pModificarCompe.removeAll();
                pEliminarCompe.setVisible(true);
            }
        });
        tfNombreCompeEliminar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
            }
        });
        bAceptarEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        bCancelarEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
            }
        });
        tfNuevaFechaInicio.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
            }
        });
        tfNuevaFechaFin.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
            }
        });
        tfNuevoEstado.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
            }
        });
        bAceptarModi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        bCancelarModi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
