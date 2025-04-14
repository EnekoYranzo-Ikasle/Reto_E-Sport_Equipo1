package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Equipo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Diálogo para la gestión de equipos (alta y eliminación)
 */
public class DEquipo extends JDialog {
    private VistaController vistaController;
    private List<Equipo> listaEquipos;

    private JPanel pPrincipal;
    private JTabbedPane tabbedPane1;

    // Componentes panel Alta
    private JPanel pTextAlta;
    private JPanel pInputsAlta;
    private JTextField tfNombre;
    private JTextField tfFechaFundacion;
    private JButton bCrearAlta;

    // Componentes panel Borrar
    private JPanel pBorrar;

    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;
    private JButton btnEliminar;
    private JTextField NombreEquipo;
    private JTextField NuevoNombreEquipo;
    private JTextField FechaFundacion;
    private JButton ModificarButton;


    public DEquipo(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setTitle(super.getTitle());
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);

        try {
            ModificarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean error = false;
                    if (NuevoNombreEquipo.getText().matches("^[a-zA-Z]*$")) {
                        JOptionPane.showMessageDialog(pPrincipal, "Nombre incorrecto");
                        error = true;
                    }
                    try {
                        LocalDate fechaFundacion = parsearFecha(tfFechaFundacion.getText());
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(pPrincipal, "Fecha incorrecto");
                        error = true;

                    }
                    try {
                        boolean existe2 = vistaController.existeEquipo(NombreEquipo.getText());
                        if (!error && existe2) {


                        }

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }




                }
            });
            /**
             * Configura el panel de alta de equipos
             */
            bCrearAlta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    crearNuevoEquipo();
                }
            });

            // Configurar componentes
            configurarPanelBorrar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(pPrincipal, e.getMessage());
        }

        
    }

    /**
     * Crea un nuevo equipo con los datos del formulario
     */
    private void crearNuevoEquipo() {
        try {
            String nombre = tfNombre.getText();
            LocalDate fechaFundacion = parsearFecha(tfFechaFundacion.getText());

            vistaController.nuevoEquipo(nombre, fechaFundacion);

            JOptionPane.showMessageDialog(null, "Nuevo Equipo creado correctamente");
            limpiarFormularioAlta();
            actualizarListaEquipos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void limpiarFormularioAlta() {
        tfNombre.setText("");
        tfFechaFundacion.setText("");
    }


    /**
     * Configura el panel de borrado de equipos
     */
    private void configurarPanelBorrar() {
        try {
            listaEquipos = vistaController.getEquipos();

            pBorrar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            configurarTabla();
            configurarBotones();

            // Agregar los componentes al panel
            pBorrar.add(new JScrollPane(tablaEquipos), BorderLayout.CENTER);
            pBorrar.add(crearPanelBotones(), BorderLayout.EAST);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(pPrincipal, e.getMessage());
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
     * Configura los botones
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
                eliminarEquipoSeleccionado();
            }
        });
    }

    /**
     * Configura la tabla de equipos
     */
    private void configurarTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable
            }
        };

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");

        tablaEquipos = new JTable(modeloTabla);
        tablaEquipos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEquipos.getTableHeader().setReorderingAllowed(false);

        actualizarTabla();
    }

    /**
     * Actualiza los datos de la tabla
     */
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);

        for (Equipo equipo : listaEquipos) {
            Object[] fila = new Object[2];
            fila[0] = equipo.getCodEquipo();
            fila[1] = equipo.getNombreEquipo();

            modeloTabla.addRow(fila);
        }
    }

    /**
     * Actualiza la lista de equipos desde el controlador
     */
    private void actualizarListaEquipos() {
        try {
            listaEquipos = vistaController.getEquipos();
            actualizarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la lista: " + e.getMessage());
        }
    }

    /**
     * Elimina el equipo seleccionado en la tabla
     */
    private void eliminarEquipoSeleccionado() {
        int filaSeleccionada = tablaEquipos.getSelectedRow();

        if (filaSeleccionada >= 0) {
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que deseas eliminar el equipo " +
                            listaEquipos.get(filaSeleccionada).getNombreEquipo() + "?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    vistaController.eliminarEquipo(listaEquipos.get(filaSeleccionada).getCodEquipo());
                    listaEquipos.remove(filaSeleccionada);
                    actualizarTabla();

                    JOptionPane.showMessageDialog(
                            this,
                            "Equipo eliminado con éxito",
                            "Eliminación Completada",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor, selecciona un equipo para eliminar",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Convierte una cadena de texto a un objeto LocalDate
     */



    private LocalDate parsearFecha(String fechaStr) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fechaStr, formato);
    }
}