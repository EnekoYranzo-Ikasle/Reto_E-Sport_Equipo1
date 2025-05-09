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

public class DInformeJugadores extends JDialog {
    private JPanel pPrincipal;
    private JButton bVolver;
    private JPanel pBody;
    private JPanel pBotones;
    private JPanel pHeader;
    private JTextField tfNombreEquipo;
    private JPanel pText;
    private JPanel pInputs;
    private JPanel pContenido;

    private VistaController vistaController;

    public DInformeJugadores(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setModal(true);
        setTitle("Informes - Jugadores");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);

        tfNombreEquipo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    List<Jugador> listaInformeJugadores = vistaController.getInformeJugadores(tfNombreEquipo.getText());

                    String[] columnas = {"Nombre", "Apellidos", "Rol", "Sueldo"};
                    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

                    // Llenar tabla con datos
                    for (Jugador jugador : listaInformeJugadores) {
                        Object[] fila = {
                                jugador.getNombre(),
                                jugador.getApellidos(),
                                jugador.getRol(),
                                jugador.getSueldo()
                        };
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
                    JOptionPane.showMessageDialog(pPrincipal, ex.getMessage());
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
