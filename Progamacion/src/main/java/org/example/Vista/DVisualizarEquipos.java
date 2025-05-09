package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Equipo;
import org.example.Modelo.Jugador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class DVisualizarEquipos extends JDialog {
    private JPanel pPrincipal;
    private JPanel pHeader;
    private JPanel pBody;
    private JTextArea taMostrar;
    private JButton bVolver;
    private JScrollPane spContent;
    private JButton buttonOK;
    private VistaController vistaController;

    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;
    private List<Equipo> listaEquipos;

    public DVisualizarEquipos(VistaController vistaController) throws SQLException {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setModal(true);
        setSize(500, 350);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);

        listaEquipos = vistaController.mostrar();

        configurarTabla();

        bVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
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
        modeloTabla.addColumn("Fecha fundación");

        tablaEquipos = new JTable(modeloTabla);
        tablaEquipos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEquipos.getTableHeader().setReorderingAllowed(false);

        // Aplicar diseño personalizado a la tabla
        tablaEquipos.setBackground(new Color(30, 42, 56));
        tablaEquipos.setForeground(Color.white);
        tablaEquipos.setShowGrid(false);
        tablaEquipos.setBorder(null);

        JTableHeader header = tablaEquipos.getTableHeader();
        header.setBackground(new Color(30, 42, 56).darker());
        header.setForeground(Color.white);
        header.setBorder(null);

        spContent.setBackground(new Color(30, 42, 56));

        spContent.setViewportView(tablaEquipos);

        actualizarTabla();
    }

    /**
     * Actualiza los datos de la tabla
     */
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);

        for (Equipo equipo : listaEquipos) {
            Object[] fila = new Object[3];
            fila[0] = equipo.getCodEquipo();
            fila[1] = equipo.getNombreEquipo();
            fila[2] = equipo.getFechaFund();

            modeloTabla.addRow(fila);
        }
    }
}
