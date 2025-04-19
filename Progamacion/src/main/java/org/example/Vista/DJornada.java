package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Jornada;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DJornada extends JDialog {
    private VistaController vistaController;
    private List<Jornada> listaJornadas;

    private JTable tablaJornada;
    private DefaultTableModel modeloTabla;
    private JButton btnEliminar;

    private JPanel contentPane;
    private JPanel pBorrar;

    public DJornada(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(contentPane);
        setModal(true);

        try {
            listaJornadas = vistaController.getJornadas();

            pBorrar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            configurarTabla();
            configurarBotones();

            pBorrar.add(new JScrollPane(tablaJornada), BorderLayout.CENTER);
            pBorrar.add(crearPanelBotones(), BorderLayout.EAST);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(contentPane, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Crea el panel de botones para el panel de borrado
     * @return Panel con los botones
     */
    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(new Color(30, 42, 56));
        panelBotones.add(btnEliminar);
        return panelBotones;
    }

    /**
     * Configura los botones de acción
     */
    private void configurarBotones() {
        btnEliminar = new JButton("");
        btnEliminar.setIcon(new ImageIcon("src/main/resources/Images/trashBin.png"));
        btnEliminar.setPreferredSize(new Dimension(50, 50));
        btnEliminar.setBackground(Color.WHITE);
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setFocusPainted(false);

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarJornadaSeleccionada();
            }
        });
    }

    /**
     * Configura la tabla de jugadores
     */
    private void configurarTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable
            }
        };

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Fecha jornada");

        tablaJornada = new JTable(modeloTabla);
        tablaJornada.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaJornada.getTableHeader().setReorderingAllowed(false);

        actualizarTabla();
    }

    /**
     * Actualiza los datos de la tabla
     */
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);

        for (Jornada jornada : listaJornadas) {
            Object[] fila = new Object[2];
            fila[0] = jornada.getCodJornada();
            fila[1] = jornada.getFechaJornada();

            modeloTabla.addRow(fila);
        }
    }

    /**
     * Actualiza la lista de jugadores desde el controlador
     */
    private void actualizarListaJornadas() {
        try {
            listaJornadas = vistaController.getJornadas();
            actualizarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la lista: " + e.getMessage());
        }
    }

    /**
     * Elimina la jornada seleccionada en la tabla
     */
    private void eliminarJornadaSeleccionada() {
        int filaSeleccionada = tablaJornada.getSelectedRow();

        if (filaSeleccionada >= 0) {
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que deseas eliminar la jornada " +
                            listaJornadas.get(filaSeleccionada).getCodJornada() + "?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    vistaController.eliminarJornada(listaJornadas.get(filaSeleccionada).getCodJornada());
                    listaJornadas.remove(filaSeleccionada);
                    actualizarTabla();

                    JOptionPane.showMessageDialog(
                            this,
                            "Jornada eliminado con éxito",
                            "Eliminación Completada",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor, selecciona una jornada para eliminar",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
