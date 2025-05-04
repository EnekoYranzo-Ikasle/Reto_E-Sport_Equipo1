package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Jornada;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DJornada extends JDialog {
    private final VistaController vistaController;
    private List<Jornada> listaJornadas;

    private JTable tablaJornada;
    private DefaultTableModel modeloTabla;
    private JButton btnEliminar;
    private JButton btnEditar;

    private JPanel pPrincipal;
    private JPanel pBorrar;

    public DJornada(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        try {
            listaJornadas = vistaController.getJornadas();

            pBorrar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            configurarTabla();
            configurarBotones();

            // Crear y configurar el JScrollPane
            JScrollPane scrollPane = new JScrollPane(tablaJornada);
            scrollPane.getViewport().setBackground(new Color(30, 42, 56));
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.getViewport().setBorder(null);

            pBorrar.add(scrollPane, BorderLayout.CENTER);
            pBorrar.add(crearPanelBotones(), BorderLayout.EAST);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(pPrincipal, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Crea el panel de botones para el panel de borrado
     * @return Panel con los botones
     */
    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(new Color(30, 42, 56));
        panelBotones.add(btnEliminar);
        panelBotones.add(btnEditar);
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

        // Botón editar
        btnEditar = new JButton("");
        btnEditar.setIcon(new ImageIcon("src/main/resources/Images/edit.png"));
        btnEditar.setPreferredSize(new Dimension(50, 50));
        btnEditar.setBackground(Color.WHITE);
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setBorderPainted(false);
        btnEditar.setFocusPainted(false);

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarJornadaSeleccionada();
            }
        });
    }

    /**
     * Configura la tabla de las jornadas
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

        // Aplicar diseño personalizado a la tabla
        tablaJornada.setBackground(new Color(30, 42, 56));
        tablaJornada.setForeground(Color.white);
        tablaJornada.setShowGrid(false);
        tablaJornada.setBorder(null);

        JTableHeader header = tablaJornada.getTableHeader();
        header.setBackground(new Color(30, 42, 56).darker());
        header.setForeground(Color.white);
        header.setBorder(null);

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
     * Actualiza la lista de las jornadas desde el controlador
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
                            "Jornada eliminada con éxito",
                            "Eliminación Completada",
                            JOptionPane.INFORMATION_MESSAGE);

                    actualizarListaJornadas();

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

    /**
     * Actualizar la jornada seleccionada en la tabla
     */
    private void editarJornadaSeleccionada() {
        int filaSeleccionada = tablaJornada.getSelectedRow();

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (filaSeleccionada >= 0) {
            LocalDate fechaNueva = LocalDate.parse(JOptionPane.showInputDialog(pPrincipal,
                    "Introduce la nueva fecha (dd/MM/yyyy):)"), formato);

            try {
                vistaController.editarJornada(listaJornadas.get(filaSeleccionada).getCodJornada(), fechaNueva);
                listaJornadas.remove(filaSeleccionada);
                actualizarTabla();

                JOptionPane.showMessageDialog(
                        this,
                        "Jornada actualizada con éxito",
                        "Actualización Completada",
                        JOptionPane.INFORMATION_MESSAGE);

                actualizarListaJornadas();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }
}
