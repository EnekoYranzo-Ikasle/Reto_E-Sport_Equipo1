package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Equipo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DEquipo extends JDialog {
    private VistaController vistaController;
    private List<Equipo> listaEquipos;
    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;
    private JButton btnEliminar;

    private JPanel pPrincipal;
    private JTabbedPane tabbedPane1;
    private JPanel pTextAlta;
    private JPanel pInputsAlta;
    private JTextField tfNombre;
    private JTextField tfFechaFundacion;
    private JButton bCrearAlta;
    private JPanel pBorrar;

    public DEquipo(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setTitle(super.getTitle());
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);

        try {
//            Boton aceptar AltaJugador
            bCrearAlta.addActionListener(new ActionListener() {
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

            listaEquipos = vistaController.getEquipos();

            pBorrar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            configurarTabla();

//            Crear panel de botones
            JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panelBotones.setBackground(new Color(30, 42, 56)); // Color de fondo

            btnEliminar = new JButton("");
            btnEliminar.setIcon(new ImageIcon("src/main/resources/Images/trashBin.png"));
            btnEliminar.setPreferredSize(new Dimension(50, 50));
            btnEliminar.setBackground(Color.WHITE); // Cambiar color de fondo
            btnEliminar.setForeground(Color.BLACK); // Cambiar color de la letra
            btnEliminar.setBorderPainted(false); // Quitar borde pintado
            btnEliminar.setFocusPainted(false); // Quitar diseño focus

            btnEliminar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    eliminarEquipoSeleccionado();
                }
            });

            panelBotones.add(btnEliminar);

//            Agregar los componentes al padre
            pBorrar.add(new JScrollPane(tablaEquipos), BorderLayout.CENTER);
            pBorrar.add(panelBotones, BorderLayout.EAST);

        }catch (Exception e){
            JOptionPane.showMessageDialog(pPrincipal, e.getMessage());
        }
    }

//    Funciones para configurar la tabla de borrado.
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

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);

        for (Equipo equipo : listaEquipos) {
            Object[] fila = new Object[2];
            fila[0] = equipo.getCodEquipo();
            fila[1] = equipo.getNombreEquipo();

            modeloTabla.addRow(fila);
        }
    }

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

                }catch (Exception ex){
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

//    Parsear Fecha
    private LocalDate parsearFecha(String fechaStr) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fechaStr, formato);
    }
}
