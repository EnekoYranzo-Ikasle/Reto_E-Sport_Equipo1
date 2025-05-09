package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Equipo;
import org.example.Modelo.Jugador;
import org.example.Modelo.Roles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Diálogo para la gestión de jugadores (alta, edición y eliminación)
 */
public class DJugador extends JDialog {
    private final VistaController vistaController;
    private List<Jugador> listaJugadores;

    private JPanel pPrincipal;
    private JTabbedPane tabbedPane1;

    // Componentes panel Alta
    private JPanel pTextAlta;
    private JPanel pInputsAlta;
    private JTextField tfNombre;
    private JTextField tfApellido;
    private JTextField tfNacionalidad;
    private JTextField tfNacimiento;
    private JTextField tfNickname;
    private JTextField tfNombreEquipo;
    private JTextField tfSueldo;
    private JComboBox cbRol;
    private JButton aceptarButton;

    // Componentes panel Modificar
    private JTextField codigoJugad;
    private JTextField Nombre;
    private JTextField apellido;
    private JTextField Nacionalidad;
    private JTextField fechaNacimiento;
    private JTextField Nickname;
    private JTextField Sueldio;
    private JTextField NombrEquip;
    private JComboBox Rolesss;
    private JButton botonsico;

    // Componentes panel Borrar
    private JPanel pBorrar;
    private JTable tablaJugadores;
    private DefaultTableModel modeloTabla;
    private JButton btnEliminar;

    // Variables auxiliares
    private Boolean correcto;
    private LocalDate feca;
    private int CodEquip;

    public DJugador(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 550);
        setLocationRelativeTo(null);

        try {
//            Alta:
            cbRol.setModel(new DefaultComboBoxModel(Roles.values()));

            aceptarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    crearNuevoJugador();
                }
            });

//              Modificar:
            Rolesss.setModel(new DefaultComboBoxModel(Roles.values()));

            configurarValidacionesCampos();

            botonsico.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editarJugador();
                }
            });

//            Borrar:
            listaJugadores = vistaController.getJugadores();

            pBorrar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            configurarTabla();
            configurarBotones();

            JScrollPane scrollPane = new JScrollPane(tablaJugadores);
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
     * Crea un nuevo jugador con los datos del formulario
     */
    private void crearNuevoJugador() {
        try {
            String nombre = tfNombre.getText();
            String apellido = tfApellido.getText();
            String nacionalidad = tfNacionalidad.getText();
            LocalDate fechaNacimiento = parsearFecha(tfNacimiento.getText());
            String nickname = tfNickname.getText();
            String nombreEquipo = tfNombreEquipo.getText();
            double sueldo = Double.parseDouble(tfSueldo.getText());
            String rol = cbRol.getSelectedItem().toString();

            vistaController.altaJugador(nombre, apellido, nacionalidad, fechaNacimiento,
                    nickname, sueldo, rol, nombreEquipo);

            JOptionPane.showMessageDialog(null, "Jugador dado de alta correctamente");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(DJugador.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Configura las validaciones de los campos de edición
     */
    private void configurarValidacionesCampos() {
        // Validación del código de jugador
        codigoJugad.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarCodigoJugador();
            }
        });

        // Validación del nombre
        Nombre.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarCampoTexto(Nombre, "El nombre del jugador no cumple el patrón correcto");
            }
        });

        // Validación del apellido
        apellido.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarCampoTexto(apellido, "El apellido del jugador no cumple el patrón correcto");
            }
        });

        // Validación de la nacionalidad
        Nacionalidad.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarCampoTexto(Nacionalidad, "La nacionalidad está mal escrita");
            }
        });

        // Validación de la fecha de nacimiento
        fechaNacimiento.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarFechaNacimiento();
            }
        });

        // Validación del nombre de equipo
        NombrEquip.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarNombreEquipo();
            }
        });

        // Validación del sueldo
        Sueldio.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validarSueldo();
            }
        });
    }

    /**
     * Valida el código de jugador introducido
     */
    private void validarCodigoJugador() {
        if (codigoJugad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ese código no pertenece a ningún jugador");
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoJugad.getText());
            boolean existe = vistaController.jugadorExiste(codigo);

            if (!existe) {
                JOptionPane.showMessageDialog(null, "Ese código no pertenece a ningún jugador");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Valida un campo de texto con un patrón de solo letras
     * @param campo Campo de texto a validar
     * @param mensaje Mensaje de error
     */
    private void validarCampoTexto(JTextField campo, String mensaje) {
        if (!parsearPatron(campo.getText(), Pattern.compile("^[a-zA-Z]*$"))) {
            JOptionPane.showMessageDialog(null, mensaje);
        }
    }

    /**
     * Valida la fecha de nacimiento
     */
    private void validarFechaNacimiento() {
        try {
            feca = parsearFecha(fechaNacimiento.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "La fecha está mal insertada");
        }
    }

    /**
     * Válida el nombre del equipo
     */
    private void validarNombreEquipo() {
        try {
            Equipo equipo = vistaController.mostrarEquipo(NombrEquip.getText());
            if (equipo == null) {
                JOptionPane.showMessageDialog(null, "No existe ese equipo");
            } else {
                CodEquip = equipo.getCodEquipo();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Valida el sueldo
     */
    private void validarSueldo() {
        if (!parsearPatron(Sueldio.getText(), Pattern.compile("[0-9]*"))) {
            JOptionPane.showMessageDialog(null, "El sueldo está mal insertado");
        }
    }

    /**
     * Edita un jugador con los datos del formulario
     */
    private void editarJugador() {
        try {
            vistaController.editarJugador(
                    Integer.parseInt(codigoJugad.getText()),
                    Nombre.getText(),
                    apellido.getText(),
                    Nacionalidad.getText(),
                    feca,
                    Nickname.getText(),
                    Rolesss.getSelectedItem().toString(),
                    Double.parseDouble(Sueldio.getText()),
                    CodEquip
            );

            JOptionPane.showMessageDialog(null, "Jugador editado correctamente");
            actualizarListaJugadores();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al editar el jugador: " + ex.getMessage());
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
                eliminarEquipoSeleccionado();
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
        modeloTabla.addColumn("Nombre");

        tablaJugadores = new JTable(modeloTabla);
        tablaJugadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaJugadores.getTableHeader().setReorderingAllowed(false);

        // Aplicar diseño personalizado a la tabla
        tablaJugadores.setBackground(new Color(30, 42, 56));
        tablaJugadores.setForeground(Color.white);
        tablaJugadores.setShowGrid(false);
        tablaJugadores.setBorder(null);

        JTableHeader header = tablaJugadores.getTableHeader();
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

        for (Jugador jugador : listaJugadores) {
            Object[] fila = new Object[2];
            fila[0] = jugador.getCodJugador();
            fila[1] = jugador.getNombre();

            modeloTabla.addRow(fila);
        }
    }

    /**
     * Actualiza la lista de jugadores desde el controlador
     */
    private void actualizarListaJugadores() {
        try {
            listaJugadores = vistaController.getJugadores();
            actualizarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la lista: " + e.getMessage());
        }
    }

    /**
     * Elimina el jugador seleccionado en la tabla
     */
    private void eliminarEquipoSeleccionado() {
        int filaSeleccionada = tablaJugadores.getSelectedRow();

        if (filaSeleccionada >= 0) {
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que deseas eliminar el jugador " +
                            listaJugadores.get(filaSeleccionada).getNombre() + "?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    vistaController.eliminarJugador(listaJugadores.get(filaSeleccionada).getCodJugador());
                    listaJugadores.remove(filaSeleccionada);
                    actualizarTabla();

                    JOptionPane.showMessageDialog(
                            this,
                            "Jugador eliminado con éxito",
                            "Eliminación Completada",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor, selecciona un jugador para eliminar",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Comprueba si una cadena cumple con un patrón dado
     * @param frase Cadena a comprobar
     * @param patron Patrón a aplicar
     * @return true si la cadena cumple el patrón, false en caso contrario
     */
    private boolean parsearPatron(String frase, Pattern patron) {
        return frase.matches(patron.pattern());
    }

    /**
     * Convierte una cadena de texto a un objeto LocalDate
     * @param fechaStr Cadena de texto con formato dd/MM/yyyy
     * @return Objeto LocalDate
     */
    private LocalDate parsearFecha(String fechaStr) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fechaStr, formatoFecha);
    }
}