package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Jugador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.List;

public class DInformeEquipos extends JDialog {
    private JPanel pPrincipal;
    private JPanel pBotones;
    private JButton bVolver;
    private JPanel pBody;
    private JPanel pText;
    private JPanel pInputs;
    private JTextField tfCodCompeticion;
    private JPanel pContenido;
    private JTextArea taMostrar;
    private JPanel pHeader;

    private VistaController vistaController;

    public DInformeEquipos(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setModal(true);
        setTitle("Informes - Equipos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);

        tfCodCompeticion.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    List<Object[]> listaInformeEquipos = vistaController.getInformeEquipos(Integer.parseInt(tfCodCompeticion.getText()));

                    String[] columnas = {"Nombre", "Fecha fundación", "Número de jugadores", "Sueldo medio",
                            "Sueldo máximo", "Sueldo mínimo"};
                    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

                    // Llenar tabla con datos
                    for (Object[] fila : listaInformeEquipos) {
                        modelo.addRow(fila);
                    }

                    JTable tabla = new JTable(modelo);
                    JScrollPane scrollPane = new JScrollPane(tabla);

                    // Aplicar el diseño a la tabla
                    tabla.setBackground(new Color(30, 42, 56));
                    tabla.setForeground(Color.white);
                    tabla.setShowGrid(false);
                    tabla.setBorder(null);

                    JTableHeader header = tabla.getTableHeader();
                    header.setBackground(new Color(30, 42, 56).darker());
                    header.setForeground(Color.white);
                    header.setBorder(null);

                    scrollPane.getViewport().setBackground(new Color(30, 42, 56));
                    scrollPane.setBorder(BorderFactory.createEmptyBorder());
                    scrollPane.getViewport().setBorder(null);

                    // Limpiar el panel antes de insertar nueva tabla
                    pContenido.removeAll();
                    pContenido.setLayout(new BorderLayout());
                    pContenido.add(scrollPane, BorderLayout.CENTER);
                    pContenido.revalidate();
                    pContenido.repaint();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        bVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
