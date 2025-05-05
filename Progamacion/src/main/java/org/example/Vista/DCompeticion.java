package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DCompeticion extends JDialog {
    private VistaController vistaController;

    private JPanel pPrincipal;
    private JTabbedPane tabbedPane1;
    private JPanel pTextAlta;
    private JPanel pInputsAlta;
    private JTextField tfNombre;
    private JTextField tfFechaInicio;
    private JTextField tfFechaFin;
    private JButton aceptarButton;
    private JPanel pBorrar;
    private JTextField NuevaIni;
    private JTextField NuevaFin;
    private JButton botonsico;
    private JPanel pNuevaComp;
    private JTextField NuevoNom;

    public DCompeticion(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        if (vistaController.isCompeticionCreada()){
            tabbedPane1.setEnabledAt(0, false);
            tabbedPane1.setSelectedIndex(1);
        }

        if (vistaController.isEtapaCerrada()){
            tabbedPane1.setEnabledAt(0, true);
            tabbedPane1.setSelectedIndex(0);
        }

        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = tfNombre.getText();
                    LocalDate fechaInicio = parsearFecha(tfFechaInicio.getText());
                    LocalDate fechaFin = parsearFecha(tfFechaFin.getText());

                    vistaController.nuevaCompeticion(nombre, fechaInicio, fechaFin);
                    JOptionPane.showMessageDialog(DCompeticion.this, "Competición creada correctamente");

                    vistaController.competicionCreada();
                    tabbedPane1.setEnabledAt(0, false); // Deshabilitar nueva competición temporalmente.
                    tabbedPane1.setSelectedIndex(1);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(DCompeticion.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        botonsico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!NuevoNom.getText().matches("^[a-zA-Z0-9 ]+$")){
                    JOptionPane.showMessageDialog(null, "Formato del nombre mal insertado");
                }else{
                    try {
                        vistaController.actualizarCompeticion(NuevaIni.getText(),NuevaFin.getText(),NuevoNom.getText());
                    }catch (DateTimeException exception){
                        JOptionPane.showMessageDialog(null,"Formato de fecha mal insertado");
                    }catch (SQLException ex){
                        JOptionPane.showMessageDialog(DCompeticion.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });
    }

    private LocalDate parsearFecha(String fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fecha, formato);
    }
}
