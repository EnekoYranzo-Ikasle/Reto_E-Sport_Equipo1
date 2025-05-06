package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Competicion;
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

public class DCompeticion extends JDialog {
    private VistaController vistaController;
    private List<Competicion> listaCompeticiones;

    private JTable tablaJornada;
    private DefaultTableModel modeloTabla;
    private JButton btnEliminar;

    private JPanel pPrincipal;
    private JTabbedPane tabbedPane1;
    private JPanel pTextAlta;
    private JPanel pInputsAlta;
    private JTextField tfNombre;
    private JTextField tfFechaInicio;
    private JTextField tfFechaFin;
    private JButton aceptarButton;
    private JPanel pBorrar;
    private JTextField Nombre;
    private JTextField apellido;
    private JTextField Nacionalidad;
    private JTextField fechaNacimiento;
    private JTextField Nickname;
    private JTextField Sueldio;
    private JComboBox Rolesss;
    private JTextField NombrEquip;
    private JTextField codigoJugad;
    private JButton botonsico;
    private JPanel pNuevaComp;

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

        try {
            listaCompeticiones = vistaController.getCompeticiones();

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
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Fecha Inicio");
        modeloTabla.addColumn("Fecha Fin");

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

        for (Competicion competicion : listaCompeticiones) {
            Object[] fila = new Object[4];
            fila[0] = competicion.getCodCompe();
            fila[1] = competicion.getNombre();
            fila[2] = competicion.getFechaInicio();
            fila[3] = competicion.getFecha_fin();

            modeloTabla.addRow(fila);
        }
    }

    /**
     * Actualiza la lista de las jornadas desde el controlador
     */
    private void actualizarListaJornadas() {
        try {
            listaCompeticiones = vistaController.getCompeticiones();
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
                            listaCompeticiones.get(filaSeleccionada).getCodCompe() + "?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    vistaController.eliminarJornada(listaCompeticiones.get(filaSeleccionada).getCodCompe());
                    listaCompeticiones.remove(filaSeleccionada);
                    actualizarTabla();

                    JOptionPane.showMessageDialog(
                            this,
                            "Competición eliminada con éxito",
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
                    "Por favor, selecciona una competición para eliminar",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private LocalDate parsearFecha(String fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fecha, formato);
    }
}
